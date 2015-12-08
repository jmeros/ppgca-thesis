package br.jus.trt9.acompspje.selenium.telas.elementos;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ElementosLogin {

	private ElementosLogin() {}
	
	/**
	 * Busca pelo rótulo no cabeçalho da tela contendo o título do sistema.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o título do sistema no cabeçalho da tela.
	 */
	public static WebElement tituloDoSistema(SearchContext contexto) {
		return By.id("titulo").findElement(contexto);
	}

	/**
	 * Busca pelo campo de entrada do usuário de login no sistema.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o campo de entrada do usuário para login.
	 */
	public static WebElement usuarioSistema(SearchContext contexto) {
		return By.id("username").findElement(contexto);
	}

	/**
	 * Busca pelo campo de entrada da senha de login no sistema.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o campo de entrada da senha para login.
	 */
	public static WebElement senhaSistema(SearchContext contexto) {
		return By.id("password").findElement(contexto);
	}

	/**
	 * Busca pelo botão para efetuar o login no sistema.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o botão de login no sistema.
	 */
	public static WebElement botaoEntrar(SearchContext contexto) {
		return By.id("botao").findElement(contexto);
	}

	public static WebElement mensagemFalhaNoLogin(SearchContext contexto) {
		return By.id("status").findElement(contexto);
	}

	/**
	 * Busca pela mensagem de acesso negado na tela de login.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o botão de login do sistema.
	 */
	public static WebElement mensagemAcessoNegado(SearchContext contexto) {
		return By.xpath(".//*[@id='centro']/div/blockquote/b").findElement(contexto);
	}

}
