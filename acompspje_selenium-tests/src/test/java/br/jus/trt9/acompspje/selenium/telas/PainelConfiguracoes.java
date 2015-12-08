package br.jus.trt9.acompspje.selenium.telas;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosConfiguracoes;



public class PainelConfiguracoes {
	enum TipoUsuario { 
		SECRETARIO,
		ASSISTENTE,
		MAGISTRADO,
		PROCURADOR,
		GABINETE
	}
	
	private WebDriver _driver;
	protected WebDriverWait _waitDriver;
	
	protected PainelConfiguracoes(WebDriver driver){
		_driver = driver;
		_waitDriver = new WebDriverWait(_driver, 10);
	}
	
	public boolean verificarVisibilidadeProcurador(){
		if(ElementosConfiguracoes.checkBoxAcessoVotoCompleto(_driver).getAttribute("class").contains("ui-state-active")){
			return true;
		}else{
				if(!ElementosConfiguracoes.checkBoxAcessoVotoCompleto(_driver).getAttribute("class").contains("ui-state-active")){
					return false;
				}
			}
		return true;
		
	}
	
	public PainelSessoes sairConfiguracoes(){
		PainelSessoes painel = null;
		ElementosConfiguracoes.botaoVoltar(_driver).click();
		painel = new PainelSessoes(_driver);
		painel.validarPaginaCarregada();
		return painel;
	}
	
	
	private PainelConfiguracoes alterarTurma(String turma){
		// Verifica se a turma desejada não está selecionada
		if (ElementosConfiguracoes.comboBoxOrgaoJulgadorSelecaoAtual(_driver).getText().equals(turma)) {
			return this;
		}
		
		// Pegar referência ao checkbox de voto completo antes de alterar a turma
		WebElement checkboxVotoCompleto = ElementosConfiguracoes.checkBoxAcessoVotoCompleto(_driver);
		
		// Alterar a seleção da turma na combobox
		ElementosConfiguracoes.comboBoxOrgaoJulgador(_driver).click();
		ElementosConfiguracoes.itemComboBoxJulgador(_driver, turma).click();
		
		// Verificar que o checkbox foi atualizado com a configuração para a turma atual
		_waitDriver.until(ExpectedConditions.stalenessOf(checkboxVotoCompleto));
		
		return this;
	}
	
	
	public PainelConfiguracoes ativarVisibilidadeProcurador(String turma) {
		// Acessa a turma desejada
		alterarTurma(turma);
		
		// Verifica se a visibilidade do voto completo já está ativada
		if (!verificarVisibilidadeProcurador()) {
			// Clica no checkbox para alterar o seu estado
			ElementosConfiguracoes.checkBoxAcessoVotoCompleto(_driver).click();
		}
		
		return this;
	}

	protected PainelConfiguracoes validarPaginaCarregada() {
		// Aguardar até que o subtítulo da página de configurações apareca
		_waitDriver.until(ExpectedConditions.textToBePresentInElementLocated(ElementosConfiguracoes.localizadorLabelConfiguracoes(), "Configurações"));
		
		// Verificar o título da página e o cabeçalho
		Assert.assertEquals("ASPJE", _driver.getTitle());
		Assert.assertEquals("Acompanhamento de Sessão do PJ-e", ElementosConfiguracoes.tituloDoSistema(_driver).getText());
		return this;
	}

	public PainelConfiguracoes desativarVisibilidadeProcurador(String turma) {
		// Acessa a turma desejada
		alterarTurma(turma);
		
		// Verifica se a visibilidade do voto completo já está desativada
		if (verificarVisibilidadeProcurador()) {
			// Clica no checkbox para alterar o seu estado
			ElementosConfiguracoes.checkBoxAcessoVotoCompleto(_driver).click();
		}
		
		return this;
	}
	
		
	
	
	
}
