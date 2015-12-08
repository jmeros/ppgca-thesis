package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC01_AcessarSistemaTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - Logar sem e-Token no perfil de Magistrado e verifica que o painel de 
	 * sessões mostra apenas a opção de a sessão como magistrado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void logarComoMagistrado() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para Magistrado
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			
			// Entrar no sistema usando o perfil de magistrado
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que a tela de sessões do magistrado
				.validarPerfilMagistrado(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}

	/**
	 * Cenário 2 - Logar sem e-Token no perfil de Secretário e verifica que o painel de 
	 * sessões mostra apenas a opção de a sessão como secretário.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void logarComoSecretario() throws IOException, ClassNotFoundException, SQLException {
		// Alterar perfil no banco para Secretario (deveria ser o padrão, mas por garantia)
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		
		// Entrar no sistema usando o perfil de Secretario
		new TelaInicial(driver)
			.autenticarUsuario()
		
		// Validar que a tela de sessões do Secretario
			.validarPerfilSecretario(SESSAO.getID_SESSAO_PJE());
	}

	/**
	 * Cenário 3 - Logar sem e-Token no perfil de Gabinete e verifica que o painel de 
	 * sessões mostra apenas a opção de a sessão como gabinete.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void logarComoGabinete() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para Gabinete
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			
			// Entrar no sistema usando o perfil de gabinete
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que a tela de sessões do gabinete
				.validarPerfilGabinete(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}

	/**
	 * Cenário 4 - Logar sem e-Token no perfil de Secretário + Gabinete e verifica 
	 * que o painel de sessões mostra as opção de a sessão como secretário e gabinete.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void logarComoSecretarioGabinete() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para secretario + gabinete
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);
			
			// Entrar no sistema usando o perfil de secretario + gabinete
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que a tela de sessões do secretario + gabinete
				.validarPerfilSecretarioGabinete(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}

	/**
	 * Cenário 5-8 e 12 - São testes usando o e-Token e não podem ser implementados 
	 * usando o selenium.
	 * 
	 * @throws IOException 
	 */
	
	/**
	 * Cenário 9 - Teste de SQL injection no formulário de login.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testeSQLInjection() throws IOException {
		// Entrar no sistema usando o perfil de secretario + gabinete
		TelaInicial telaInicial = new TelaInicial(driver);
		telaInicial.autenticarUsuario("teste or '1='1", "teste or '1='1", false);
		
		// Validar falhas no login
		telaInicial.validarFalhaNaAutenticacao();
	}
	
	/**
	 * Cenário 10a - Tentar autenticar sem senha.
	 * 
	 * @throws IOException
	 */
	@Test
	public void loginSemSenha() throws IOException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		// Entrar no sistema usando o perfil de secretario + gabinete
		TelaInicial telaInicial = new TelaInicial(driver);
		telaInicial.autenticarUsuario(propriedades.getProperty("usuarioSistema"), "", false);
		
		// Validar falhas no login
		telaInicial.validarUsuarioOuSenhaIncorretos();
	}
	
	/**
	 * Cenário 10b - Tentar autenticar sem usuário.
	 * 
	 * @throws IOException
	 */
	@Test
	public void loginSemUsuario() throws IOException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		// Entrar no sistema usando o perfil de secretario + gabinete
		TelaInicial telaInicial = new TelaInicial(driver);
		telaInicial.autenticarUsuario("", propriedades.getProperty("senhaSistema"), false);
		
		// Validar falhas no login
		telaInicial.validarFalhaNaAutenticacao();
	}
	
	/**
	 * Cenário 11 - Tentar autenticar com dados de acesso inválidos.
	 * 
	 * @throws IOException
	 */
	@Test
	public void loginComDadosInvalidos() throws IOException {
		// Entrar no sistema usando usuário e senha inválidos
		TelaInicial telaInicial = new TelaInicial(driver);
		telaInicial.autenticarUsuario("usuario", "senha", false);
		
		// Validar falhas no login
		telaInicial.validarFalhaNaAutenticacao();
	}

	/**
	 * Cenário 13 - Logar sem e-Token no perfil de Magistrado e verifica que o painel de 
	 * sessões mostra apenas a opção de a sessão como magistrado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void logarComUsuarioSemPerfil() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Remover perfil no banco para o usuario
			BDUtils.getInstance().removerPerfilUsuario();
			
			// Entrar no sistema usando o perfil de magistrado
			TelaInicial telaInicial = new TelaInicial(driver);
			telaInicial.autenticarUsuario(false);
			
			// Validar que o login falhou por falta de permissão
			telaInicial.validarAcessoNegado();
		}
		finally {
			// Inserir perfil no banco novamente
			BDUtils.getInstance().inserirPerfilUsuario(PerfilUsuario.Secretario);
		}
	}

	/**
	 * Cenário 14 - Tentar acessar módulo restrito do sistema sem estar logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarModuloRestrito() throws IOException, ClassNotFoundException, SQLException {
		// Entrar no sistema diretamente em um módulo restrito
		new TelaInicial(driver, "configuracoes.xhtml");
	}

	/**
	 * Cenário 15 - Tentar acessar módulo restrito do sistema estando logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarModuloRestritoLogado() throws IOException, ClassNotFoundException, SQLException {
		// Entrar no sistema usando o perfil de secretário
		TelaInicial telaInicial = new TelaInicial(driver);
		telaInicial.autenticarUsuario();
		
		// Entrar no sistema diretamente em um módulo restrito
		telaInicial.acessarPainelConfiguracoesViaUrl();
	}
	
	/**
	 * Cenário 16 - Acessar tela inicial com usuário Magistrado já logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarTelaInicialComMagistradoLogado() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para Magistrado
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			
			// Entrar no sistema usando o perfil de magistrado
			new TelaInicial(driver)
				.autenticarUsuario();
				
			// Acessar novamente a tela inicial com o usuário já logado
			new TelaInicial(driver, false)
				.acessarPainelSessoesViaUrl()
			
			// Validar que a tela de sessões do magistrado
				.validarPerfilMagistrado(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}
	
	/**
	 * Cenário 16 - Acessar tela inicial com usuário Secretário já logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarTelaInicialComSecretarioLogado() throws IOException, ClassNotFoundException, SQLException {
		// Alterar perfil no banco para Secretario (só pra garantir)
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		
		// Entrar no sistema usando o perfil de secretario
		new TelaInicial(driver)
			.autenticarUsuario();
			
		// Acessar novamente a tela inicial com o usuário já logado
		new TelaInicial(driver, false)
			.acessarPainelSessoesViaUrl()
		
		// Validar que a tela de sessões do secretario
			.validarPerfilSecretario(SESSAO.getID_SESSAO_PJE());
	}
	
	/**
	 * Cenário 18 - Acessar tela inicial com usuário Gabinete já logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarTelaInicialComGabineteLogado() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para Gabinete
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			
			// Entrar no sistema usando o perfil de Gabinete
			new TelaInicial(driver)
				.autenticarUsuario();
				
			// Acessar novamente a tela inicial com o usuário já logado
			new TelaInicial(driver, false)
				.acessarPainelSessoesViaUrl()
			
			// Validar que a tela de sessões do Gabinete
				.validarPerfilGabinete(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}
	
	/**
	 * Cenário 19 - Acessar tela inicial com usuário Secretario + Gabinete já logado.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void acessarTelaInicialComSecretarioGabineteLogado() throws IOException, ClassNotFoundException, SQLException {
		try {
			// Alterar perfil no banco para Secretario + Gabinete
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);
			
			// Entrar no sistema usando o perfil de Secretario + Gabinete
			new TelaInicial(driver)
				.autenticarUsuario();
				
			// Acessar novamente a tela inicial com o usuário já logado
			new TelaInicial(driver, false)
				.acessarPainelSessoesViaUrl()
			
			// Validar que a tela de sessões do Secretario + Gabinete
				.validarPerfilSecretarioGabinete(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar o perfil do usuário para Secretário
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
		}
	}

	/**
	 * Cenário 20 - Verificar que o botão de logout sai do sistema com sucesso.
	 * 
	 * @throws IOException
	 */
	@Test
	public void logoutDoSistema() throws IOException {
		// Entrar no sistema e autenticar o usuário
		new TelaInicial(driver)
			.autenticarUsuario()
			
		// Sair do sistema atráves do link de logout
			.sairDoSistema();
		
		// Verificar que acessando a página inicial novamente
		// o usuário não está mais autenticado
		new TelaInicial(driver);
	}
	
	/**
	 * Cenário 21 - Verificar que a listagem de sessões mostra as sessões em ordem 
	 * decrescente de data/hora e apenas as sessões a que o usuário tem acesso são
	 * mostradas.
	 * 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void verficarListagemDeSessoes() throws IOException, ParseException, SQLException, ClassNotFoundException {
		try {
			// Remover a sessão de teste do lista de turmas do usuário
			BDUtils.getInstance().removerTurmaDoUsuario(SESSAO.getID_ORGAO_JULGADOR_COLEGIADO());
			
			// Entrar no sistema e autenticar o usuário
			new TelaInicial(driver)
				.autenticarUsuario()
				
			// Validar a ordem da listagem de sessões
				.validarOrdemDasSessoes()
			
			// Validar que a sessão de teste não aparece na listagem de sessões
				.validarSessaoNaoDisponivel(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Incluir novamente a sessão de teste as turmas do usuário
			BDUtils.getInstance().inserirTurmaDoUsuario(SESSAO.getID_ORGAO_JULGADOR_COLEGIADO());
		}
	}
	
	/*
	 * Cenário 22 - Este cenário já está implementado pelos cenário 1-4.
	 */
	
	/**
	 * Cenário 23 - Verificar os dados da sessão de teste são mostrados corretamente
	 * na listagem de sessões do usuário.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void verificarDadosDaSessão() throws SQLException, ClassNotFoundException, IOException {
		// Entrar no sistema e autenticar o usuario
		new TelaInicial(driver)
			.autenticarUsuario()
			
		// Validar dados da sessão de teste
			.validarDadosSessao(SESSAO);
	}
}
