package br.jus.trt9.acompspje.selenium.telas.elementos;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ElementosRelatorioSessao {

	public static By localizadorTabelaDeProcessos() {
		return By.id("lista_processos");
	}

	public static WebElement labelOrgaoJulgador(SearchContext contexto) {
		return By.id("lista_processos:orgao_julgador").findElement(contexto);
	}
	
	public static WebElement labelDataSessao(SearchContext contexto) {
		return By.id("lista_processos:data_sessao").findElement(contexto);
	}

	private static WebElement linhaTabelaProcessos(SearchContext contexto, int indiceLinha) {
		return By.xpath(".//*[@id='lista_processos_data']/tr[" + indiceLinha + "]").findElement(contexto);
	}

	public static WebElement colunaOrdemProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[1]"));
	}

	public static WebElement colunaDescricaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[2]"));
	}

	public static WebElement colunaDivergenciaProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[3]"));
	}

	public static WebElement colunaComposicaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[4]"));
	}

	public static WebElement colunaTipoInclusaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[5]"));
	}

	public static WebElement colunaAdvogadoSustentacaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[6]"));
	}

	public static WebElement colunaSituacaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[7]"));
	}

	public static boolean iconeDivergenciaAcolhidaPresente(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElements(By.id("lista_processos:" + (indiceLinha-1) + ":icone_divergenciaPR")).size() == 1;
	}

	public static boolean iconeDivergenciaParcialmenteAcolhidaPresente(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElements(By.id("lista_processos:" + (indiceLinha-1) + ":icone_divergenciaPA")).size() == 1;
	}

	public static boolean iconeDivergenciaEmAnalisePresente(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElements(By.id("lista_processos:" + (indiceLinha-1) + ":icone_divergenciaEA")).size() == 1;
	}

	public static boolean iconeDivergenciaRecusadaPresente(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElements(By.id("lista_processos:" + (indiceLinha-1) + ":icone_divergencia")).size() == 1;
	}

	public static WebElement conteudoDispositivoSessaoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[8]/div"));
	}

	public static WebElement conteudoDispositivoVotoProcesso(SearchContext contexto, int indiceLinha) {
		return linhaTabelaProcessos(contexto, indiceLinha).findElement(By.xpath(".//td[9]/div"));
	}

}
