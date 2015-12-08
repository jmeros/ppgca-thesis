package br.jus.trt9.acompspje.selenium.telas.elementos;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ElementosRoteiroSessao {
	
	private ElementosRoteiroSessao(){
		
	}
	
	public static By localizadorTabelaDeProcessos() {
		return By.id("j_idt8:lista_processos_data");
	}

	
	public static WebElement botaoVoltar(SearchContext contexto){
		//adicionar ID 
		
		return By.id("j_idt8:voltarButton").findElement(contexto);
	}
	
	public static WebElement botaoImprimir(SearchContext contexto){
		
		return By.id("j_idt8:imprimirButton").findElement(contexto);
		
	}
	
	public static WebElement labelOrgaoJulgador(SearchContext contexto){
		
		return By.xpath(".//*[@id='j_idt8:lista_processos:j_idt11']").findElement(contexto);
	}
	
	public static WebElement labelDataSessao(SearchContext contexto){
		
		return By.xpath(".//*[@id='j_idt8:lista_processos:data_sessao']").findElement(contexto);
		
	}
		
	
	public static WebElement colunaOrdemRoteiroSessao(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[1]").findElement(contexto);
	}
	
	public static WebElement colunaNumeracaoCNJ(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[2]").findElement(contexto);
	}
	
	public static WebElement colunaRelatorRoteiroSessao(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[3]").findElement(contexto);
	}
	
	public static WebElement colunaSegundoVoto(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[4]").findElement(contexto);
		
	}
	
	public static WebElement colunaTerceiroVoto(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[5]").findElement(contexto);
		
	}
	
	public static WebElement colunaAdvogadoSustentacao(SearchContext contexto, int indiceLinha){
		return By.xpath(".//*[@id='j_idt8:lista_processos_data']/tr["+indiceLinha+"]/td[6]").findElement(contexto);
		
	}
	
	
	public static WebElement botaoVoltarRodape(SearchContext contexto){
		//adicionar ID 
		
		return By.id("j_idt8:voltarButtonFim").findElement(contexto);
	}
	
	public static WebElement botaoImprimirRodape(SearchContext contexto){
		
		return By.id("j_idt8:imprimirButtonFim").findElement(contexto);
		
	}
	
	
	
	

}
