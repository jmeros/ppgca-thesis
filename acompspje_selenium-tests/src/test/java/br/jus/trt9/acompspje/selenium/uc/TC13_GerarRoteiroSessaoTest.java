package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC13_GerarRoteiroSessaoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - Gerar roteiro de uma sessão disponível com o secretário.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoDisponivelSecretario() throws IOException,
			SQLException, ClassNotFoundException {
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);

		try {
			// Entrar no sistema e autenticar o usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Verificar opção para gerar roteiro está habilitada
				.verificarOpcaoRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), true)
			
			// Gerar o roteiro da sessão e validar o seu conteúdo
				.entrarRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE())
				.validarRoteiroSessao(SESSAO, PROCESSOS_PAUTA);

		} finally {
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 2 - Verificar que a opção para gerar o roteiro da sessão está desabilitada
	 * para sessões encerradas com o perfil de secretário.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoEncerradaSecretario() throws IOException,
			SQLException, ClassNotFoundException {
		SESSAO.mudarStatus(new Character('E'));
		BDUtils.getInstance().atualizarSessao(SESSAO);
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);

		try {
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()

			// Validar que a opção para gerar o roteiro está desabilitada
				.verificarOpcaoRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), false);
		} finally {
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 3 - Gerar roteiro de uma sessão disponível com o secretário/gabinete.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoDisponivelSecretarioGabinete() throws IOException,
			SQLException, ClassNotFoundException {
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);

		try {
			// Entrar no sistema e autenticar o usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Verificar opção para gerar roteiro está habilitada
				.verificarOpcaoRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), true)
			
			// Gerar o roteiro da sessão e validar o seu conteúdo
				.entrarRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE())
				.validarRoteiroSessao(SESSAO, PROCESSOS_PAUTA);

		} finally {
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 4 - Verificar que a opção para gerar o roteiro da sessão está desabilitada
	 * para sessões encerradas com o perfil de secretário/gabinete.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoEncerradaSecretarioGabinete() throws IOException,
			SQLException, ClassNotFoundException {
		SESSAO.mudarStatus(new Character('E'));
		BDUtils.getInstance().atualizarSessao(SESSAO);
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);

		try {
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()

			// Validar que a opção para gerar o roteiro está desabilitada
				.verificarOpcaoRoteiroSessaoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), false);
		} finally {
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}
	/**
	 * Cenário 5 - Verificar que o magistrado não possui a opção para gerar o roteiro
	 * de uma sessão disponível.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoDisponívelMagistrado() throws IOException,
			SQLException, ClassNotFoundException {
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try{
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que o menu para gerar o roteiro de sessão não está disponível
				.verificarOpcaoRoteiroSessaoNaoDisponivel(SESSAO.getID_SESSAO_PJE());
		} finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 6 - Verificar que o magistrado não possui a opção para gerar o roteiro
	 * de uma sessão encerrada.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoEncerradaMagistrado() throws IOException,
			SQLException, ClassNotFoundException {
		SESSAO.mudarStatus(new Character('E'));
		BDUtils.getInstance().atualizarSessao(SESSAO);
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try {
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que o menu para gerar o roteiro de sessão não está disponível
				.verificarOpcaoRoteiroSessaoNaoDisponivel(SESSAO.getID_SESSAO_PJE());
		} finally {
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 7 - Verificar que o gabinete não possui a opção para gerar o roteiro
	 * de uma sessão disponível.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoDisponívelGabinete() throws IOException,
			SQLException, ClassNotFoundException {
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try{
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que o menu para gerar o roteiro de sessão não está disponível
				.verificarOpcaoRoteiroSessaoNaoDisponivel(SESSAO.getID_SESSAO_PJE());
		} finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 8 - Verificar que o gabinete não possui a opção para gerar o roteiro
	 * de uma sessão encerrada.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRoteiroSessaoEncerradaGabinete() throws IOException,
			SQLException, ClassNotFoundException {
		SESSAO.mudarStatus(new Character('E'));
		BDUtils.getInstance().atualizarSessao(SESSAO);
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try {
			// Entrar no sistema e autenticar usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Validar que o menu para gerar o roteiro de sessão não está disponível
				.verificarOpcaoRoteiroSessaoNaoDisponivel(SESSAO.getID_SESSAO_PJE());
		} finally {
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}
}
