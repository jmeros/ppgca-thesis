package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.ComposicaoSessao;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.db.VistaRegimental;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosRelatorioSessao;

public class PainelRelatorioSessao {

	private WebDriver _driver;
	protected WebDriverWait _waitDriver;

	protected PainelRelatorioSessao(WebDriver driver) {
		_driver = driver;
		_waitDriver = new WebDriverWait(_driver, 10);
	}

	protected void validarPaginaCarregada() {
		// Aguardar até que a tabela de sessões apareça na tela
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosRelatorioSessao.localizadorTabelaDeProcessos()));
		
		// Verificar o título da página e o cabeçalho
		Assert.assertEquals("ASPJE", _driver.getTitle());
	}

	public void validarRelatorioSessao(SessaoJulgamento sessao, List<RoteiroPautaSessao> processos, List<ComposicaoSessao> magistrados, List<VistaRegimental> vistasRegimentais) throws SQLException, ClassNotFoundException, IOException {
		// Ordenar os processos da lista por número sequencial
		Collections.sort(processos, new Comparator<RoteiroPautaSessao>() {
			@Override
			public int compare(RoteiroPautaSessao p1, RoteiroPautaSessao p2) {
				return p1.getNR_SEQUENCIAL_PROCESSO().compareTo(p2.getNR_SEQUENCIAL_PROCESSO());
			}
		});
				
		// Validar os dados da sessão
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Assert.assertEquals(
				"Órgão julgador: " + BDUtils.getInstance().buscarDescricaoOrgaoJulgador(sessao.getID_SESSAO_PJE()), 
				ElementosRelatorioSessao.labelOrgaoJulgador(_driver).getText());
		Assert.assertEquals(
				dateFormat.format(sessao.getDT_SESSAO()), 
				ElementosRelatorioSessao.labelDataSessao(_driver).getText());
		
		// Validar lista de processos
		for (int indiceLinha = 1; indiceLinha<=processos.size(); indiceLinha++) {
			RoteiroPautaSessao processo = processos.get(indiceLinha-1);

			// Validar a coluna com o índice de ordem do processo
			Assert.assertEquals(
					processo.getNR_SEQUENCIAL_PROCESSO().toString(), 
					ElementosRelatorioSessao.colunaOrdemProcesso(_driver, indiceLinha).getText());
			
			// Validar a coluna com o dados do processo (ignorando espaços em branco)
			Assert.assertEquals(
					processo.getDS_PROCESSO().replaceAll("\\s+", ""), 
					ElementosRelatorioSessao.colunaDescricaoProcesso(_driver, indiceLinha).getText().replaceAll("\\s+", ""));
			
			// Validar a coluna de divergências
			if (processo.getIN_DIVERGENCIA() == null) {
				Assert.assertTrue("A coluna de divergência não está vazia para o processo " + processo.getNR_PROCESSO_CNJ() + ".", 
						ElementosRelatorioSessao.colunaDivergenciaProcesso(_driver, indiceLinha).findElements(By.tagName("img")).size() == 0);
			}
			else if (processo.getIN_DIVERGENCIA().charValue() == 'A') {
				Assert.assertTrue("Ícone de divergência recusada não está presente para o processo " + processo.getNR_PROCESSO_CNJ() + ".",
						ElementosRelatorioSessao.iconeDivergenciaRecusadaPresente(_driver, indiceLinha));
			}
			else if (processo.getIN_DIVERGENCIA().charValue() == 'B') {
				Assert.assertTrue("Ícone de divergência parcialmente acolhida não está presente para o processo " + processo.getNR_PROCESSO_CNJ() + ".", 
						ElementosRelatorioSessao.iconeDivergenciaParcialmenteAcolhidaPresente(_driver, indiceLinha));
			}
			else if (processo.getIN_DIVERGENCIA().charValue() == 'C') {
				Assert.assertTrue("Ícone de divergência em análise não está presente para o processo " + processo.getNR_PROCESSO_CNJ() + ".", 
						ElementosRelatorioSessao.iconeDivergenciaEmAnalisePresente(_driver, indiceLinha));
			}
			else if (processo.getIN_DIVERGENCIA().charValue() == 'D') {
				Assert.assertTrue("Ícone de divergência acolhida não está presente para o processo " + processo.getNR_PROCESSO_CNJ() + ".", 
						ElementosRelatorioSessao.iconeDivergenciaAcolhidaPresente(_driver, indiceLinha));
			}
			else {
				Assert.fail("Tipo de divergência desconhecido para o processo " + processo.getNR_PROCESSO_CNJ() + ".");
			}
			
			// Validar a coluna de composição da sessão (ignorando espaços em branco)
			String composicaoProcesso = "Relator: " + processo.getNM_RELATOR();
			if (processo.getNM_REVISOR() != null)
				composicaoProcesso += " 2o voto: " + processo.getNM_REVISOR();
			else
				composicaoProcesso += " 2o voto: Não definido";
			if (processo.getNM_TERCEIRO() != null)
				composicaoProcesso += " 3o voto: " + processo.getNM_TERCEIRO();
			else
				composicaoProcesso += " 3o voto: Não definido";
			Assert.assertEquals(composicaoProcesso.replaceAll("\\s+", ""), 
					ElementosRelatorioSessao.colunaComposicaoProcesso(_driver, indiceLinha).getText().replaceAll("\\s+", ""));
			
			// Validar tipo da inclusão
			Assert.assertEquals(processo.getIN_TIPO_INCLUSAO(), 
					ElementosRelatorioSessao.colunaTipoInclusaoProcesso(_driver, indiceLinha).getText());
			
			// Validar coluna sustentações
			if (processo.getDS_ADV_SUSTENTACAO_ORAL() != null) {
				Assert.assertEquals(processo.getDS_ADV_SUSTENTACAO_ORAL(), 
						ElementosRelatorioSessao.colunaAdvogadoSustentacaoProcesso(_driver, indiceLinha).getText());
			}
			else {
				Assert.assertEquals("", 
						ElementosRelatorioSessao.colunaAdvogadoSustentacaoProcesso(_driver, indiceLinha).getText());
			}
			
			// Validar coluna da situação do processo
			EstadoProcesso estado = EstadoProcesso.valueOf(processo.getDS_STATUS());
			if (estado == EstadoProcesso.VISTA_REGIMENTAL) {
				// No caso de vista regimental, pegar os nomes dos desembargadores que requisitaram vista
				String situacao = null;
				for (VistaRegimental vistaRegimental: vistasRegimentais) {
					if (vistaRegimental.getID_PAUTA_SESSAO() == processo.getID_PAUTA_SESSAO()) {
						String nomeDesembargador = null;
						for (ComposicaoSessao magistrado: magistrados) {
							if (magistrado.getCD_MAGISTRADO_TITULAR() == vistaRegimental.getCD_MAGISTRADO()) {
								// Usar apenas o primeiro nome do magistrado
								nomeDesembargador = magistrado.getNM_MAGISTRADO_TITULAR();
								nomeDesembargador = nomeDesembargador.substring(0, nomeDesembargador.indexOf(' '));
							}
						}
						if (situacao == null) {
							situacao = EstadoProcesso.VISTA_REGIMENTAL.toString() + ": " + nomeDesembargador;
						}
						else {
							situacao += " , " + nomeDesembargador;
						}
					}
				}
				Assert.assertEquals(situacao, 
						ElementosRelatorioSessao.colunaSituacaoProcesso(_driver, indiceLinha).getText());
			}
			else {
				Assert.assertEquals(estado.toString(), 
						ElementosRelatorioSessao.colunaSituacaoProcesso(_driver, indiceLinha).getText());
			}
			
			// Validar coluna do dispositivo da sessão
			Assert.assertEquals(processo.getDS_DISPOSITIVO_SESSAO(), 
					ElementosRelatorioSessao.conteudoDispositivoSessaoProcesso(_driver, indiceLinha).getText());
			
			// Validar coluna do dispositivo do voto
			Assert.assertEquals(StringEscapeUtils.unescapeHtml4(processo.getDS_DISPOSITIVO()), 
					ElementosRelatorioSessao.conteudoDispositivoVotoProcesso(_driver, indiceLinha).getText());
		}
	}

}
