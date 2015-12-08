package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.seleniumhq.jetty7.io.nio.SelectorManager.SelectSet;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosLogin;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosPainelAcompanhamento;

public class PainelAssistente extends PainelAcompanhamento {

	protected PainelAssistente(WebDriver driver) {
		super(driver);
	}

	@Override
	public PainelAssistente selecionarProcesso(RoteiroPautaSessao processo) 
			throws SQLException ,ClassNotFoundException ,IOException {
		// Usar frame com conteúdo do dispositivo para garantir mudança na tela após o click
		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizadorEditorDispositivo()));
		WebElement editorDispositivo = ElementosPainelAcompanhamento.localizadorEditorDispositivo().findElement(_driver);
		
		PainelAssistente p = (PainelAssistente) super.selecionarProcesso(processo);
		
		// Aguardar até que o frame antigo seja removido da interface após o click (redesenhado com novo valor)
		_waitDriver.until(ExpectedConditions.stalenessOf(editorDispositivo));
		
		return p;
	}
	

	public PainelAssistente preencherDadosSustentacaoOral(boolean todos) 
			throws SQLException ,ClassNotFoundException ,IOException {

		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizadorEditorDispositivo()));

		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoCadastrarSustentacaoOral()));
		ElementosPainelAcompanhamento.botaoCadastrarInscricaoSustentaOral(_driver).click();
		
		//Aguardar até que os elementos do dialogo de inclusao da sustentacao oral estejam disponiveis
		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizarBotaoSalvarSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizarBotaoSalvarSustentacaoOral()));
		ElementosPainelAcompanhamento.inputAdvogadoSustentaOral(_driver).sendKeys("Andre Valadares");
		WebElement selectSexo = ElementosPainelAcompanhamento.selecionaSexoAdvogadoSustentaOral(_driver);
		selectSexo.click();
		selectSexo.sendKeys("M");
		selectSexo.click(); 
		
		if(todos){
			ElementosPainelAcompanhamento.selecionaParteSustentacaoOral(_driver);			
		}
		ElementosPainelAcompanhamento.selecionaTipoSustentacaoOral(_driver);
		ElementosPainelAcompanhamento.inputPosicaoSustentaOral(_driver).sendKeys("1");
		ElementosPainelAcompanhamento.selecionaProcuracaoSustentacaoOral(_driver);
		ElementosPainelAcompanhamento.selecionaSubestabelecimentoSustentacaoOral(_driver);
		
		ElementosPainelAcompanhamento.botaoSalvarSustentacaoOral(_driver).click();
		return this;
	}
	
	public PainelAssistente alterarDadosSustentacaoOral(int posicao, boolean parte, boolean salva) 
			throws SQLException ,ClassNotFoundException ,IOException {

		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoAlteraSustentacaoOral()));
		ElementosPainelAcompanhamento.botaoAlteraSustentacaoOral(_driver).click();
		
		//Aguardar até que os elementos do dialogo de inclusao da sustentacao oral estejam disponiveis
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizarBotaoSalvarSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizarBotaoSalvarSustentacaoOral()));

		if(parte){
			ElementosPainelAcompanhamento.selecionaParteSustentacaoOral(_driver);			
		}else{
			ElementosPainelAcompanhamento.inputPosicaoSustentaOral(_driver).sendKeys(String.valueOf(posicao));			
		}
		
		if(salva){
			ElementosPainelAcompanhamento.botaoSalvarSustentacaoOral(_driver).click();			
		}else{
			ElementosPainelAcompanhamento.botaoCancelarCadastroSustentacaoOral(_driver).click();
		}

		return this;
	}
	
	public PainelAssistente validarInclusaoSustentacaoOral() 
			throws SQLException ,ClassNotFoundException ,IOException, InterruptedException {

		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizadorEditorDispositivo()));

		WebElement listaInscricoesOrais = ElementosPainelAcompanhamento.conteudoTabelaInscricaoOral(_driver);
		String conteudoListaInscricoes = listaInscricoesOrais.getText();
		String[] stringsListaInscricoes = conteudoListaInscricoes.split(" ");
		String nomeAdvogadoBanco = BDUtils.getInstance().buscarNomedoAdvogadoSustentacaoOral(stringsListaInscricoes[1]+" "+stringsListaInscricoes[2]);
		/*_waitDriver.until(new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver arg0) {
				return (nomeAdvogadoBanco != null && nomeAdvogadoBanco.equals(" "));
			}});*/
			

		Assert.assertEquals("Andre Valadares", nomeAdvogadoBanco);
		return this;
	}
	
	public PainelAssistente excluirInscricaoSustentacaoOral(boolean confirma){
		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizadorBotaoExcluirSustentacaoOral()));
		
		ElementosPainelAcompanhamento.botaoExcluirSustentacaoOral(_driver).click();
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorPainelConfirmaExclusaoSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoConfirmaExclusaoSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoConfirmaExclusaoSustentacaoOral()));
		if(confirma){
			ElementosPainelAcompanhamento.botaoConfirmaExcluirSustentacaoOral(_driver).click();			
		}else{
			ElementosPainelAcompanhamento.botaoCancelaExcluirSustentacaoOral(_driver).click();
		}

		
		return this;
	}
	
	public PainelAssistente alterarStatusSustentacaoOral(){

		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorSelectPresencaSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorSelectPresencaSustentacaoOral()));
		ElementosPainelAcompanhamento.selecionaPresencaSustentacaoOral(_driver);

		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorSelectDeclinaSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorSelectDeclinaSustentacaoOral()));

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ElementosPainelAcompanhamento.selecionaDeclinaSustentacaoOral(_driver);

		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoSalvarAlteracoesSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoSalvarAlteracoesSustentacaoOral()));
		ElementosPainelAcompanhamento.botaoSalvarAlteracoesSustentacaoOral(_driver).click();
		
		return this;
	}
	
	public PainelAssistente validarProblemaInclusaoSustentacaoOral(int caso) 
			throws SQLException ,ClassNotFoundException ,IOException {

		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizarMsgCadastrarSustentacaoOral()));
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorInputPosicaoSustentacaoOral()));
		String pos = ElementosPainelAcompanhamento.inputPosicaoSustentaOral(_driver).getAttribute("value");
		switch(caso){
		case 1:
			Assert.assertEquals("Todas as informações devem ser preenchidas, por favor verifique.", ElementosPainelAcompanhamento.msgCadastrarSustentacaoOral(_driver).getText());
			break;
		case 2:
			Assert.assertEquals("A posição "+pos+" já está em uso. Por favor escolha uma posição livre.", ElementosPainelAcompanhamento.msgCadastrarSustentacaoOral(_driver).getText());
			break;
		}

		ElementosPainelAcompanhamento.botaoCancelarCadastroSustentacaoOral(_driver).click();
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorConteudoTabelaSustentacaoOral()));
		return this;
	}	
	
	@Override
	public PainelAcompanhamento validarDetalhesProcessoSelecionado(RoteiroPautaSessao processo) {
		validarResumoProcesso(processo);
		validarDispositivo(processo, true);
		validarBotoesDeAcaoNoProcesso(processo, false);
		
		return this;
	}

	@Override
	public PainelAcompanhamento validarResumoProcesso(RoteiroPautaSessao processo) {
		// Organizar valores do processo a serem verificados
		String numeroProcesso = processo.getDS_CLASSE_JUDICIAL_SIGLA() + " " + processo.getNR_PROCESSO_CNJ();
		String sustentacaoOral = processo.getDS_ADV_SUSTENTACAO_ORAL();
		
		Assert.assertEquals(numeroProcesso, ElementosPainelAcompanhamento.numeroDoProcessoAtual(_driver).getText());
		try {
			Assert.assertEquals(sustentacaoOral, ElementosPainelAcompanhamento.sustentacaoOralDoProcessoAtual(_driver).getText());
		}
		catch (NoSuchElementException e) {
			// Se o elemento não foi encontrado, então o processo não deve ter sustentação oral.
			Assert.assertNull(sustentacaoOral);
		}
		
		return this;
	}
	
	public boolean preparaBancoTesteSustentacaoOral (RoteiroPautaSessao pautaSessao, boolean incluir) throws ClassNotFoundException, SQLException, IOException{
		return BDUtils.getInstance().preparaBancoTesteSustentacaoOral(pautaSessao.getID_PAUTA_SESSAO(), incluir); 
	}
}
