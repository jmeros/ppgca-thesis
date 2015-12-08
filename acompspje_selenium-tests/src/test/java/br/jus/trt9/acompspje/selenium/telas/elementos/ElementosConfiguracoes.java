package br.jus.trt9.acompspje.selenium.telas.elementos;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;


public class ElementosConfiguracoes {
	
	private ElementosConfiguracoes(){}
	
	public static WebElement checkBoxAcessoVotoCompleto(SearchContext contexto){
		return By.xpath(".//*[@id='configForm:acessoVC']/div[contains(@class,'ui-chkbox-box')]").findElement(contexto);
		
	}
	
	public static WebElement comboBoxOrgaoJulgadorSelecaoAtual(SearchContext contexto) {
		return By.id("configForm:orgaos_label").findElement(contexto);
	}
	
	public static WebElement comboBoxOrgaoJulgador(SearchContext contexto){
		return By.xpath(".//*[@id='configForm:orgaos']/div[contains(@class,'ui-selectonemenu-trigger')]").findElement(contexto);
	}
	
	public static WebElement itemComboBoxJulgador(SearchContext contexto, String turma){
		return By.xpath(".//*[@id='configForm:orgaos_panel']/div/ul/li[text()='"+turma+"']").findElement(contexto);
	}

	public static WebElement tituloDoSistema(SearchContext contexto) {
		return By.id("titulo").findElement(contexto);
	}
	
	public static WebElement botaoVoltar(SearchContext contexto){
		return By.xpath(".//*[@id='configForm:voltarButton']").findElement(contexto);
	}

	public static By localizadorLabelConfiguracoes() {
		return By.xpath(".//*[@id='configForm:configuracoes_content']/h2");
	}

}
