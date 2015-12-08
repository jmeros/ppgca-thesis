package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.PainelSessoes;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC10_EncerrarSessaoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - Encerra a sessão de teste e verifica que as ações exclusivas de sessões
	 * disponíveis não podem ser executadas (editar dispositivo e visualizar voto completo).
	 * 
	 * Nota: Emitir o relatório de sessão é testado em um caso de teste específico.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void encerrarSessaoDisponivelSecretário() throws IOException, SQLException, ClassNotFoundException {
		// Logar usuário no sistema
		PainelSessoes painel = new TelaInicial(driver)
			.autenticarUsuario();
		
		try {
			// Validar dialogo de encerramento e encerrar sessão de teste
			painel.encerrarSessao(SESSAO.getID_SESSAO_PJE())
				.validarConfirmacaoEncerramentoSessao()
				.confirmarEncerramentoSessao();
			
			// Validar que sessão foi encerrada
			SESSAO.mudarStatus('E');
			painel.validarSessaoEncerradaComSucesso(false)
				.validarDadosSessao(SESSAO);
			
			// Validar que os dispositivos não podem mais ser editados como Secretário
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
			PainelSecretario secretario = painel.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
			secretario.validarProcessoSelecionado(processoApregoado)
				.validarDispositivoEditavel(false)
			
			// Validar que o voto completo não pode ser visualizado pelo Secretário
				.validarVotoCompletoNaoDisponivel()
				.sairSessao();
			
			// Validar que os dispositivos não podem mais ser editados como Assistente
			RoteiroPautaSessao processo = encontrarProcessoPorEstado(EstadoProcesso.JULGADO);
			painel.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
				.validarProcessoSelecionado(processoApregoado)
				.selecionarProcesso(processo)
				.validarProcessoSelecionado(processo)
				.validarDispositivoEditavel(false)
				.sairSessao();
			
			// Validar que o voto completo não pode ser visualizado pelo Procurador
			processo = encontrarPrimeiroProcessoDaSessao();
			painel.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
				.validarProcessoSelecionado(processoApregoado)
				.selecionarProcesso(processo)
				.validarProcessoSelecionado(processo)
				.validarVotoCompletoNaoDisponivel()
				.sairSessao()
				.sairDoSistema();
			
			// Validar que o voto completo não pode ser visualizado pelo Magistrado
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			try {
				new TelaInicial(driver)
					.autenticarUsuario()
					.entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
					.validarProcessoSelecionado(processoApregoado)
					.selecionarProcesso(processo)
					.validarProcessoSelecionado(processo)
					.validarVotoCompletoNaoDisponivel()
					.sairSessao()
					.sairDoSistema();
			}
			finally {
				BDUtils.getInstance().recuperarPerfilUsuario();
			}
			
			// Validar que o voto completo não pode ser visualizado pelo Gabinete
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			try {
				new TelaInicial(driver)
					.autenticarUsuario()
					.entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
					.validarProcessoSelecionado(processoApregoado)
					.selecionarProcesso(processo)
					.validarProcessoSelecionado(processo)
					.validarVotoCompletoNaoDisponivel()
					.sairSessao();
			}
			finally {
				BDUtils.getInstance().recuperarPerfilUsuario();
			}
		}
		finally {
			// Abrir sessão novamento diretamente no banco
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
		}
	}
	
	/**
	 * Cenário 2 - Selecionar a opção de encerramento da sessão, porém não confirmar o 
	 * encerramento da sessão e verificar que a sessão permanece disponível.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void naoConfirmarEncerramentoDeSessao() throws IOException, SQLException, ClassNotFoundException {
		// Logar usuário no sistema
		PainelSessoes painel = new TelaInicial(driver)
			.autenticarUsuario();
		
		// Validar dialogo de encerramento e não encerrar sessão de teste
		painel.encerrarSessao(SESSAO.getID_SESSAO_PJE())
			.validarConfirmacaoEncerramentoSessao()
			.cancelarEncerramentoSessao();
		
		// Validar que sessão não foi encerrada
		painel.validarDadosSessao(SESSAO);
	}

	@Test
	public void tentarEncerrarSessaoEncerrada() throws SQLException, ClassNotFoundException, IOException {
		// Encerrar sessão no banco de dados
		SESSAO.mudarStatus(new Character('E'));
		BDUtils.getInstance().atualizarSessao(SESSAO);
		
		try {
			// Logar usuário no sistema
			PainelSessoes painel = new TelaInicial(driver)
				.autenticarUsuario();
			
			// Validar que opção de encerramento não está habilitada
			painel.validarOpcaoEncerramentoDesabilitada(SESSAO.getID_SESSAO_PJE());
		}
		finally {
			// Retornar sessão para estado disponível
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
		}
	}

	/**
	 * ACOMPSPJE-346 - Encerra a sessão de teste usando o perfil secretário/gabinete.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void encerrarSessaoDisponivelSecretárioGabiente() throws IOException, SQLException, ClassNotFoundException {
		// Mudar o perfil do usuário de teste
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.SecretarioGabinete);
		
		// Logar usuário no sistema
		PainelSessoes painel = new TelaInicial(driver)
			.autenticarUsuario();
		
		try {
			// Validar dialogo de encerramento e encerrar sessão de teste
			painel.encerrarSessao(SESSAO.getID_SESSAO_PJE())
				.validarConfirmacaoEncerramentoSessao()
				.confirmarEncerramentoSessao();
			
			// Validar que sessão foi encerrada
			SESSAO.mudarStatus('E');
			painel.validarSessaoEncerradaComSucesso(false)
				.validarDadosSessao(SESSAO);
		}
		finally {
			// Abrir sessão novamento diretamente no banco
			SESSAO.mudarStatus(new Character('D'));
			BDUtils.getInstance().atualizarSessao(SESSAO);
			
			// Recuperar perfil original do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}
	
}
