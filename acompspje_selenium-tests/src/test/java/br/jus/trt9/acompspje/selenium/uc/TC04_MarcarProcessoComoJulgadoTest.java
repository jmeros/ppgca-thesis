package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;

public class TC04_MarcarProcessoComoJulgadoTest extends TodosUsuariosTestTemplate {
	
	/**
	 * Cenário 1.1 - O secretário seleciona um processo no estado "Não julgado" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoNãoJulgado() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoNaoJulgado, processoApregoado);
	}

	/**
	 * Cenário 1.2 - O secretário seleciona um processo no estado "Adiado" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoAdiado() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoAdiado = encontrarProcessoPorEstado(EstadoProcesso.ADIADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoAdiado, processoApregoado);
	}

	/**
	 * Cenário 1.3 - O secretário seleciona um processo no estado "Retirado" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoRetirado() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoRetirado = encontrarProcessoPorEstado(EstadoProcesso.RETIRADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoRetirado, processoApregoado);
	}

	/**
	 * Cenário 1.4 - O secretário seleciona um processo no estado "Revisar/Julgado" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoRevisar() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoRevisar = encontrarProcessoPorEstado(EstadoProcesso.REVISAR);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoRevisar, processoApregoado);
	}

	/**
	 * Cenário 1.5 - O secretário seleciona um processo no estado "Vista em Mesa" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoVistaMesa() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoVistaMesa = encontrarProcessoPorEstado(EstadoProcesso.VISTA_MESA);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoVistaMesa, processoApregoado);
	}

	/**
	 * Cenário 1.6 - O secretário seleciona um processo no estado "Vista Regimental" 
	 * e marca o processo como julgado. Depois verifica em todos os painéis que
	 * a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoVistaRegimental() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao processoVistaRegimental = encontrarProcessoPorEstado(EstadoProcesso.VISTA_REGIMENTAL);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		julgarProcesso(processoVistaRegimental, processoApregoado);
	}

	/**
	 * Cenário 1.7 - O secretário marca o processo apregoado como "Julgado". 
	 * Depois verifica em todos os painéis que a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoApregoado() throws SQLException, ClassNotFoundException, IOException {
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		try {
			// Logar todos os usuário na sessão de teste
			logarTodosOsUsuarios();
			
			// Verifica que todos os usuário estão com o processo apregoado selecionado
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
			}
			
			// Julgar o processo apregoado que já deve estar pré-selecionado
			((PainelSecretario) paineis.get(Usuario.SECRETARIO)).marcarJulgado();
			processoApregoado.mudarEstado(EstadoProcesso.JULGADO);
	
			// Verificar que todos os usuário receberam a mensagem de processo julgado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoApregoado, false);
				}
			}
			
			// Verificar que todos os usuário continuam com o processo apregoado selecionado
			// (exceto o secretário), a lista de processos foi atualizada e o quadro de
			// resumo foi atualizado corretamente.
			for (Usuario usuario: Usuario.values()) {
				if (usuario == Usuario.SECRETARIO) {
					// No caso do secretário, o primeiro processo da lista
					// (que é "Não Julgado", deve estar selecionado)
					paineis.get(usuario)
						.validarProcessoSelecionado(primeiroProcesso)
						.validarListaDeProcessos(PROCESSOS_PAUTA);
				}
				else {
					paineis.get(usuario)
						.validarProcessoSelecionado(processoApregoado)
						.validarListaDeProcessos(PROCESSOS_PAUTA);
				}
			}
		}
		finally {
			// Ao final restaurar o estado anterior do processo
			processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
		}
	}

	/**
	 * Cenário 2 - O secretário marca o processo apregoado como "Julgado" e 
	 * não existem outros processos no estado "Não Julgado" apenas em "Vista em Mesa".
	 * Depois verifica em todos os painéis que a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoSemNenhumNaoJulgadoDisponivel() throws SQLException, ClassNotFoundException, IOException {
		// Marcar todos os processos "Não Julgado" como "Julgado"
		List<RoteiroPautaSessao> naoJulgados = buscarTodosProcessos(EstadoProcesso.NAO_JULGADO);
		for (RoteiroPautaSessao processo: naoJulgados) {
			processo.mudarEstado(EstadoProcesso.JULGADO);
			BDUtils.getInstance().atualizarProcesso(processo);
		}
		
		// Buscar processo apregoado e primeiro processo em "Vista em Mesa"
		RoteiroPautaSessao processoVistaEmMesa = encontrarPrimeiroProcessoNoEstado(EstadoProcesso.VISTA_MESA);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		try {
			// Logar todos os usuário na sessão de teste
			logarTodosOsUsuarios();
			
			// Verifica que todos os usuário estão com o processo apregoado selecionado
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
			}
			
			// Julgar o processo apregoado que já deve estar pré-selecionado
			((PainelSecretario) paineis.get(Usuario.SECRETARIO)).marcarJulgado();
			processoApregoado.mudarEstado(EstadoProcesso.JULGADO);
	
			// Verificar que todos os usuário receberam a mensagem de processo julgado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoApregoado, false);
				}
			}
			
			// Verificar que todos os usuário continuam com o processo apregoado selecionado
			// (exceto o secretário), a lista de processos foi atualizada e o quadro de
			// resumo foi atualizado corretamente.
			for (Usuario usuario: Usuario.values()) {
				if (usuario == Usuario.SECRETARIO) {
					// No caso do secretário, o primeiro processo da lista
					// (que é "Não Julgado", deve estar selecionado)
					paineis.get(usuario)
						.validarProcessoSelecionado(processoVistaEmMesa)
						.validarListaDeProcessos(PROCESSOS_PAUTA);
				}
				else {
					paineis.get(usuario)
						.validarProcessoSelecionado(processoApregoado)
						.validarListaDeProcessos(PROCESSOS_PAUTA);
				}
			}
		}
		finally {
			// Ao final restaurar o estado anterior dos processos
			processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
			for (RoteiroPautaSessao processo: naoJulgados) {
				processo.mudarEstado(EstadoProcesso.NAO_JULGADO);
				BDUtils.getInstance().atualizarProcesso(processo);
			}
		}
	}

	/**
	 * Cenário 3 - O secretário marca o processo apregoado como "Julgado" e 
	 * não existem outros processos no estado "Não Julgado" nem "Vista em Mesa".
	 * Depois verifica em todos os painéis que a mudança de estado teve efeito.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void julgarProcessoSemNenhumNaoJulgadoOuVistaMesaDisponivel() throws SQLException, ClassNotFoundException, IOException {
		// Marcar todos os processos "Não Julgado" como "Julgado"
		List<RoteiroPautaSessao> naoJulgados = buscarTodosProcessos(EstadoProcesso.NAO_JULGADO);
		for (RoteiroPautaSessao processo: naoJulgados) {
			processo.mudarEstado(EstadoProcesso.JULGADO);
			BDUtils.getInstance().atualizarProcesso(processo);
		}
		
		// Marca todos os processo "Vista em Mesa" como "Julgado"
		List<RoteiroPautaSessao> vistaMesas = buscarTodosProcessos(EstadoProcesso.VISTA_MESA);
		for (RoteiroPautaSessao processo: vistaMesas) {
			processo.mudarEstado(EstadoProcesso.JULGADO);
			BDUtils.getInstance().atualizarProcesso(processo);
		}
		
		// Buscar processo apregoado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		try {
			// Logar todos os usuário na sessão de teste
			logarTodosOsUsuarios();
			
			// Verifica que todos os usuário estão com o processo apregoado selecionado
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
			}
			
			// Julgar o processo apregoado que já deve estar pré-selecionado
			((PainelSecretario) paineis.get(Usuario.SECRETARIO)).marcarJulgado();
			processoApregoado.mudarEstado(EstadoProcesso.JULGADO);
	
			// Verificar que todos os usuário receberam a mensagem de processo julgado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoApregoado, false);
				}
			}
			
			// Verificar que todos os usuário continuam com o processo apregoado selecionado
			// (inclusive o secretário), a lista de processos foi atualizada e o quadro de
			// resumo foi atualizado corretamente.
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoApregoado)
					.validarListaDeProcessos(PROCESSOS_PAUTA);
			}
		}
		finally {
			// Ao final restaurar o estado anterior dos processos
			processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
			for (RoteiroPautaSessao processo: naoJulgados) {
				processo.mudarEstado(EstadoProcesso.NAO_JULGADO);
				BDUtils.getInstance().atualizarProcesso(processo);
			}
			for (RoteiroPautaSessao processo: vistaMesas) {
				processo.mudarEstado(EstadoProcesso.VISTA_MESA);
				BDUtils.getInstance().atualizarProcesso(processo);
			}
		}
	}
	
	private void julgarProcesso(RoteiroPautaSessao processoAJulgar, RoteiroPautaSessao processoApregoado)
			throws IOException,	SQLException, ClassNotFoundException {
		EstadoProcesso estadoOriginal = EstadoProcesso.valueOf(processoAJulgar.getDS_STATUS());
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a julgar no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoAJulgar);

		try {
			// Marca o processo como julgado no secretário
			painel.marcarJulgado();
			processoAJulgar.mudarEstado(EstadoProcesso.JULGADO);
	
			
			// Verificar que todos os usuário receberam a mensagem de processo julgado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoAJulgar, false);
				}
			}
			
			// Verificar que todos os usuário continuam com o processo apregoado selecionado
			// (inclusive o secretário), a lista de processos foi atualizada e o quadro de
			// resumo foi atualizado corretamente.
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoApregoado)
					.validarListaDeProcessos(PROCESSOS_PAUTA);
			}
		}
		finally {
			// Ao final restaurar o estado anterior do processo
			processoAJulgar.mudarEstado(estadoOriginal);
			BDUtils.getInstance().atualizarProcesso(processoAJulgar);
		}
	}

}
