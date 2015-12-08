package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosLogin;

public class TelaInicial {
	private Properties _propriedades;
	private WebDriver _driver;

	public TelaInicial(WebDriver driver) throws IOException {
		this(driver, "", true);
	}

	public TelaInicial(WebDriver driver, String pageUrl) throws IOException {
		this(driver, pageUrl, true);
	}

	public TelaInicial(WebDriver driver, boolean validarTelaInicial) throws IOException {
		_propriedades = new Properties();
		_propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		_driver = driver;
		
		// Acessar a página inicial
		acessar("", validarTelaInicial);
	}
	public TelaInicial(WebDriver driver, String pageUrl, boolean validarTelaInicial) throws IOException {
		_propriedades = new Properties();
		_propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		_driver = driver;
		
		// Acessar a página inicial
		acessar(pageUrl, validarTelaInicial);
	}

	/**
	 * Acessa a página inicial do sistema através da URL base.
	 * 
	 * @param pageUrl 
	 * @param validarTelaInicial 
	 */
	protected void acessar(String pageUrl, boolean validarTelaInicial) {
		_driver.get(_propriedades.getProperty("baseurl") + pageUrl);
		
		if (validarTelaInicial) {
			validarPaginaCarregada();
		}
	}

	public PainelSessoes autenticarUsuario() {
		return autenticarUsuario(true);
	}

	public PainelSessoes autenticarUsuario(boolean loginComSucesso) {
		return autenticarUsuario(_propriedades.getProperty("usuarioSistema"), _propriedades.getProperty("senhaSistema"), loginComSucesso);
	}

	public PainelSessoes autenticarUsuario(String login, String senha, boolean loginComSucesso) {
		// Entrar com o usuário e a senha para entrar no sistema
		ElementosLogin.usuarioSistema(_driver).sendKeys(login);
		ElementosLogin.senhaSistema(_driver).sendKeys(senha);
		
		// Clicar no botão entrar
		ElementosLogin.botaoEntrar(_driver).click();
		
		if (loginComSucesso) {
			// Validar login efetuado com sucesso
			PainelSessoes ps = new PainelSessoes(_driver);
			ps.validarPaginaCarregada();
			
			return ps;
		}
		else {
			return null;
		}	
	}
	
	/**
	 * Executa uma validação genérica para todas as páginas do sistema.
	 */
	protected void validarPaginaCarregada() {
		Assert.assertEquals("Acompanhamento de Sessão do PJe - Login", _driver.getTitle());
		Assert.assertEquals("Acompanhamento de Sessão do PJ-e", ElementosLogin.tituloDoSistema(_driver).getText());
	}

	public void validarFalhaNaAutenticacao() {
		Assert.assertEquals("Falha na autenticação.", ElementosLogin.mensagemFalhaNoLogin(_driver).getText());
	}

	public void validarUsuarioOuSenhaIncorretos() {
		Assert.assertEquals("Usuário e/ou senha incorreto(s)", ElementosLogin.mensagemFalhaNoLogin(_driver).getText());
	}

	public PainelConfiguracoes acessarPainelConfiguracoesViaUrl() {
		_driver.get(_propriedades.getProperty("baseurl") + "configuracoes.xhtml");
		
		PainelConfiguracoes painel = new PainelConfiguracoes(_driver);
		painel.validarPaginaCarregada();
		
		return painel;
	}

	public PainelSessoes acessarPainelSessoesViaUrl() {
		_driver.get(_propriedades.getProperty("baseurl"));
		
		// Validar acesso efetuado com sucesso
		PainelSessoes ps = new PainelSessoes(_driver);
		ps.validarPaginaCarregada();
		
		return ps;
	}

	public void validarAcessoNegado() {
		// Validar página de acesso negado está sendo mostrada
		Assert.assertEquals("Acesso negado.", ElementosLogin.mensagemAcessoNegado(_driver).getText());
	}

}
