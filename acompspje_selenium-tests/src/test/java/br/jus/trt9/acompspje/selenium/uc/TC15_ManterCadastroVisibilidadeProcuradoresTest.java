package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.PainelSessoes;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC15_ManterCadastroVisibilidadeProcuradoresTest extends
		UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - Habilitar visibilidade de voto aos procuradores
	 * 
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	@Test
	public void ativarVisibilidadeProcuradores() throws IOException,
			SQLException, ClassNotFoundException {
		// Entrar no sistema e autenticar o usuário
		PainelSessoes painel = new TelaInicial(driver).autenticarUsuario();
		
		// Entrar no painel de configurações
		String turma = BDUtils.getInstance().buscarDescricaoOrgaoJulgador(SESSAO.getID_SESSAO_PJE());
		painel.entrarConfiguracoes()
		
		//Ativa visibilidade do procurador caso esteja desabilitada e verifica
			.ativarVisibilidadeProcurador(turma)

		// Sair do painel de configurações
			.sairConfiguracoes();
		
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);


		// Acessar lista de sessões disponíveis e escolher uma sessão
		PainelAcompanhamento painelAcomp = painel.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
			.validarProcessoSelecionado(processoApregoado)

		// Seleciona o primeiro processo da lista na seção
			.selecionarProcesso(primeiroProcesso)

		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso);

		// Verifica a exibição do botão de Voto Completo
		Assert.assertTrue("Botão de voto completo não está disponível para o procurador.", painelAcomp.verificarBotãoVotoCompleto());
	}

	/**
	 * Cenário 2 - Desabilitar visibilidade de voto aos procuradores
	 * 
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * 
	 */
	@Test
	public void desativarVisibilidadeProcuradores() throws IOException,
			SQLException, ClassNotFoundException {
		// Entrar no sistema e autenticar o usuário
		PainelSessoes painel = new TelaInicial(driver).autenticarUsuario();
		
		// Entrar no painel de configurações
		String turma = BDUtils.getInstance().buscarDescricaoOrgaoJulgador(SESSAO.getID_SESSAO_PJE());
		painel.entrarConfiguracoes()
		
		// Desabilita visibilidade do voto completo ao procurador
			.desativarVisibilidadeProcurador(turma)

		// Sair do painel de configurações
			.sairConfiguracoes();
		
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);


		// Acessar lista de sessões disponíveis e escolher uma sessão
		PainelAcompanhamento painelAcomp = painel.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
			.validarProcessoSelecionado(processoApregoado)

		// Seleciona o primeiro processo da lista na seção
			.selecionarProcesso(primeiroProcesso)

		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso);

		// Verifica que o botão de Voto Completo não está visível
		Assert.assertFalse("Botão de voto completo está disponível para o procurador.", painelAcomp.verificarBotãoVotoCompleto());
	}

	/**
	 * Cenário 3a - Verifica que a opção de acesso ao menu de configurações não está disponível ao magistrado.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void verificarBotaoConfiguracoesMagistrado() throws IOException, SQLException, ClassNotFoundException{
		try {
			// Alterar o perfil do usuário de teste
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			
			// Entrar no sistema
			PainelSessoes painel = new TelaInicial(driver)
				.autenticarUsuario();
			
				
			Assert.assertFalse("Botão configurações presente para perfil Magistrado", painel.verificarBotaoConfiguracoes());
			
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 3b - Verifica que a opção de acesso ao menu de configurações não está disponível ao gabinete.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void verificarBotaoConfiguracoesGabinete() throws IOException, SQLException, ClassNotFoundException {
		
		try {
			// Alterar o perfil do usuário de teste
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			
			// Entrar no sistema e autenticar o usuário
			PainelSessoes painel = new TelaInicial(driver)
				.autenticarUsuario();
			
			Assert.assertFalse("Botão configurações presente para perfil Gabinete", painel.verificarBotaoConfiguracoes());
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 3c - Verifica que a opção de acesso ao menu de configurações está disponível ao secretário.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void verificarBotaoConfiguracoesSecretario() throws IOException, SQLException, ClassNotFoundException {
		
		try {
			// Alterar o perfil do usuário de teste
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
			
			// Entrar no sistema e autenticar o usuário
			PainelSessoes painel = new TelaInicial(driver)
				.autenticarUsuario();
				
			Assert.assertTrue("Botão configurações não presente para perfil Secretário", painel.verificarBotaoConfiguracoes());
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 3d - Verifica que a opção de acesso ao menu de configurações está disponível ao secretário/gabinete.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void verificarBotaoConfiguracoesSecretarioGabinete() throws IOException, SQLException, ClassNotFoundException {
		
		try {
			// Alterar o perfil do usuário de teste
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);
			
			// Entrar no sistema e autenticar o usuário
			PainelSessoes painel = new TelaInicial(driver)
				.autenticarUsuario();
				
			Assert.assertTrue("Botão configurações não presente para perfil Secretário/Gabinete", painel.verificarBotaoConfiguracoes());
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}
	
}
