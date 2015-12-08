package br.jus.trt9.acompspje.selenium.telas.elementos;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ElementosAcessoSessao {

	private ElementosAcessoSessao() {}
	
	public static WebElement numeroSessaoPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":numero").findElement(contexto);
	}
	
	public static WebElement dataSessaoPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":data").findElement(contexto);
	}
	
	public static WebElement orgaoJulgadorSessaoPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":orgaoJulgador").findElement(contexto);
	}
	
	public static WebElement tipoSessaoPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":tipo").findElement(contexto);
	}
	
	public static WebElement salaSessaoPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":sala").findElement(contexto);
	}

	public static WebElement situacaoSessaoDisponivelPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":situacaoDisponivel").findElement(contexto);
	}
	
	public static WebElement situacaoSessaoEncerradaPorIndice(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":situacaoEncerrado").findElement(contexto);
	}
	
	public static WebElement menuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.xpath(".//*[@id='main:tabelaSessao_data']/tr[" + indiceDaLinha + "]/td[6]/span/button").findElement(contexto);
	}
	
	public static WebElement opcaoSecretarioMenuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":secretario").findElement(contexto);
	}
	
	public static WebElement opcaoAssistenteMenuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":assistente").findElement(contexto);
	}
	
	public static WebElement opcaoGabineteMenuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":gabinete").findElement(contexto);
	}
	
	public static WebElement opcaoProcuradorMenuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":procurador").findElement(contexto);
	}

	public static WebElement opcaoMagistradoMenuAcompanharSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":magistrado").findElement(contexto);
	}

	public static By localizadorGerarRoteiroSessao(int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":roteiro");
	}

	public static WebElement opcaoGerarRoteiroSessao(SearchContext contexto, int indiceDaLinha) {
		return localizadorGerarRoteiroSessao(indiceDaLinha).findElement(contexto);
	}

	public static WebElement opcaoGerarRelatorioSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":relatorio").findElement(contexto);
	}

	public static WebElement opcaoEncerrarSessao(SearchContext contexto, int indiceDaLinha) {
		return By.id("main:tabelaSessao:" + (indiceDaLinha - 1) + ":encerrar").findElement(contexto);
	}

	public static WebElement dialogoConfirmacaoEncerramento(SearchContext contexto) {
		return localizadorDialogoConfirmacaoEncerramento().findElement(contexto);
	}

	public static By localizadorDialogoConfirmacaoEncerramento() {
		return By.id("main:confirmacaoEncerrarSessaoDialog");
	}

	public static WebElement tituloDialogoConfirmacaoEncerramentoSessao(SearchContext contexto) {
		return By.id("main:confirmacaoEncerrarSessaoDialog_title").findElement(contexto);
	}
	
	public static WebElement mensagemDialogoConfirmacaoEncerramentoSessao(SearchContext contexto) {
		return By.xpath(".//*[@id='main:pnlgrdMensagemConfirmacaoEncerrarSessao']/tbody/tr").findElement(contexto);
	}
	
	public static WebElement botaoSimDialogoConfirmacaoEncerramentoSessao(SearchContext contexto) {
		return By.id("main:btnConfirmacaoEncerrarSessao").findElement(contexto);
	}
	
	public static WebElement botaoNaoDialogoConfirmacaoEncerramentoSessao(SearchContext contexto) {
		return By.id("main:btnCancelaEncerrarSessao").findElement(contexto);
	}
	
	public static WebElement botaoConfiguracoes(SearchContext contexto){
		return By.id("main:configuracoesButton").findElement(contexto);
	}

	/**
	 * Retorna a lista de mensagens de atualização mostradas no painel.
	 * 
	 * @param contexto O contexto onde os elementos devem ser procurados.
	 * 
	 * @return WebElement que representa as mensagens mostradas no painel.
	 */
	public static List<WebElement> mensagensDeAtualização(SearchContext contexto) {
		return localizadorMensagensDeAtualização().findElements(contexto);
	}

	public static By localizadorMensagensDeAtualização() {
		return By.xpath(".//*[@id='growl_container']//div[@class='ui-growl-message']/span");
	}

	public static WebElement mensagemTopoDaTela(SearchContext contexto) {
		return By.xpath(".//*[@id='messages']//span[@class='ui-messages-info-summary']").findElement(contexto);
	}

	public static By localizadorTabelaDeSessoes() {
		return By.id("main:tabelaSessao");
	}

	public static WebElement perfilUsuario(SearchContext contexto) {
		return By.id("profileLabel").findElement(contexto);
	}

	public static WebElement nomeUsuario(SearchContext contexto) {
		return By.id("logoutLabel").findElement(contexto);
	}
}
