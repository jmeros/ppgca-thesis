package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC07_VisualizarVotoCompletoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - Verificar que o secretário consegue visualizar o voto completo de um
	 * processo da sessão atual.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void visualizarVotoCompletoSecretario() throws IOException, SQLException, ClassNotFoundException {
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);
		
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
			.validarProcessoSelecionado(processoApregoado)
		
		// Seleciona o primeiro processo da lista na seção
			.selecionarProcesso(primeiroProcesso)
		
		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso)
		
		// Selecionar a opção para visualizar o voto completo
			.visualizarVotoCompleto()
		
		// Validar o conteúdo do voto completo
			.validarVotoCompleto(primeiroProcesso);
	}

	/**
	 * Cenário 2 - Verificar que o magistrado consegue visualizar o voto completo de um
	 * processo da sessão atual.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void visualizarVotoCompletoMagistrado() throws IOException, SQLException, ClassNotFoundException {
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);
		
		// Alterar o perfil do usuário de teste
		try {
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			
			// Entrar no sistema
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
				.validarProcessoSelecionado(processoApregoado)
			
			// Seleciona o primeiro processo da lista na seção
				.selecionarProcesso(primeiroProcesso)
			
			// Validar os detalhes do primeiro processo (todos os dados completos)
				.validarProcessoSelecionado(primeiroProcesso)
			
			// Selecionar a opção para visualizar o voto completo
				.visualizarVotoCompleto()
			
			// Validar o conteúdo do voto completo
				.validarVotoCompleto(primeiroProcesso);
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 3 - Verificar que o procurador consegue visualizar o voto completo de um
	 * processo da sessão atual.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void visualizarVotoCompletoProcurador() throws IOException, SQLException, ClassNotFoundException {
		// Habilitar procurador a visualizar o voto completo
		BDUtils.getInstance().alterarPermissaoVotoCompletoProcurador(SESSAO.getID_ORGAO_JULGADOR_COLEGIADO(), true);
		
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);
		
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
			.validarProcessoSelecionado(processoApregoado)
		
		// Seleciona o primeiro processo da lista na seção
			.selecionarProcesso(primeiroProcesso)
		
		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso)
		
		// Selecionar a opção para visualizar o voto completo
			.visualizarVotoCompleto()
		
		// Validar o conteúdo do voto completo
			.validarVotoCompleto(primeiroProcesso);
	}

	/**
	 * Cenário 4 - Verificar que o gabinete consegue visualizar o voto completo de um
	 * processo da sessão atual.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void visualizarVotoCompletoGabinete() throws IOException, SQLException, ClassNotFoundException {
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);
		
		try {
			// Alterar o perfil do usuário de teste
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			
			// Entrar no sistema
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
				.validarProcessoSelecionado(processoApregoado)
				
			// Seleciona o primeiro processo da lista na seção
				.selecionarProcesso(primeiroProcesso)
			
			// Validar os detalhes do primeiro processo (todos os dados completos)
				.validarProcessoSelecionado(primeiroProcesso)
			
			// Selecionar a opção para visualizar o voto completo
				.visualizarVotoCompleto()
			
			// Validar o conteúdo do voto completo
				.validarVotoCompleto(primeiroProcesso);
		}
		finally {
			// Recuperar o perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * Cenário 5 - Verificar que o assistente não consegue visualizar o voto completo 
	 * de um processo da sessão atual.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void visualizarVotoCompletoAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Selecionar os processos a serem utilizados no teste
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.APREGOADO);
		
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
			.validarProcessoSelecionado(processoApregoado)
		
		// Seleciona o primeiro processo da lista na seção
			.selecionarProcesso(primeiroProcesso)
		
		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso)
		
		// Verificar que a opção para visualizar o voto completo não está disponível
			.validarVotoCompletoNaoDisponivel();
	}
}
