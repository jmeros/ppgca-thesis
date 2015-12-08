package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento.IconesProcesso;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;

public class TC09a_AlterarStatusProcessoTest extends TodosUsuariosTestTemplate {

	/**
	 * Cenário 1 - Selecionar um processo "Nâo Julgado" e marcá-lo como "Adiado".
	 * Depois disso, todos os usuário recebem a atualização do estado do processo.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void adiarProcessoNãoJulgado() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e o processo apregoado
		RoteiroPautaSessao processoAdiar = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a adiar no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoAdiar);

		try {
			// Marca o processo como adiado no secretário
			painel.marcarAdiado();
			processoAdiar.mudarEstado(EstadoProcesso.ADIADO);
	
			
			// Verificar que todos os usuário receberam a mensagem de processo adiado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoAdiar, false);
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
			processoAdiar.mudarEstado(EstadoProcesso.NAO_JULGADO);
			BDUtils.getInstance().atualizarProcesso(processoAdiar);
		}
	}

	/**
	 * Cenário 2 - Selecionar um processo "Nâo Julgado" e marcá-lo como "Retirado de Pauta".
	 * Depois disso, todos os usuário recebem a atualização do estado do processo.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void retirarProcessoNãoJulgado() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e o processo apregoado
		RoteiroPautaSessao processoRetirar = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a retirar no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoRetirar);

		try {
			// Marca o processo como retirado no secretário
			painel.marcarRetirado();
			processoRetirar.mudarEstado(EstadoProcesso.RETIRADO);
	
			
			// Verificar que todos os usuário receberam a mensagem de processo adiado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoRetirar, false);
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
			processoRetirar.mudarEstado(EstadoProcesso.NAO_JULGADO);
			BDUtils.getInstance().atualizarProcesso(processoRetirar);
		}
	}

	/**
	 * Cenário 3 - Selecionar um processo "Nâo Julgado" e marcá-lo como "Julgado/Revisar".
	 * Depois disso, todos os usuário recebem a atualização do estado do processo.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void revisarProcessoNãoJulgado() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e o processo apregoado
		RoteiroPautaSessao processoRevisar = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a revisar no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoRevisar);

		try {
			// Marca o processo como julgado/revisar no secretário
			painel.marcarRevisar();
			processoRevisar.mudarEstado(EstadoProcesso.REVISAR);
	
			
			// Verificar que todos os usuário receberam a mensagem de processo revisado (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoRevisar, false);
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
			processoRevisar.mudarEstado(EstadoProcesso.NAO_JULGADO);
			BDUtils.getInstance().atualizarProcesso(processoRevisar);
		}
	}
	@Test
	public void vistaEmMesaProcessoNãoJulgado() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e o processo apregoado
		RoteiroPautaSessao processoVistaMesa = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a por em vista em mesa no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoVistaMesa);

		try {
			// Marca o processo como vista em mesa no secretário
			painel.marcarVistaMesa();
			processoVistaMesa.mudarEstado(EstadoProcesso.VISTA_MESA);
	
			
			// Verificar que todos os usuário receberam a mensagem de processo em vista em mesa (exceto secretário)
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoVistaMesa, false);
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
			processoVistaMesa.mudarEstado(EstadoProcesso.NAO_JULGADO);
			BDUtils.getInstance().atualizarProcesso(processoVistaMesa);
		}
	}
	/**
	 * Cenário 4 e 13 - Selecionar um processo "Nâo Julgado" e marcá-lo como "Vista Regimental".
	 * Depois disso, verifica se todos os desembargadores cadastrados aparecem na lista e seleciona
	 * os desembargadores que pediram a vista regimental e todos os usuário recebem a atualização 
	 * do estado do processo.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void vistaRegimentalProcessoNãoJulgado() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e o processo apregoado
		RoteiroPautaSessao processoVistaRegimental = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a revisar no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoVistaRegimental);
		
		try {
			// Marca o processo como vista regimental no secretário
			painel.marcarVistaRegimental();
			processoVistaRegimental.mudarEstado(EstadoProcesso.VISTA_REGIMENTAL);
	
			// Verificar lista de desembargadores esta correta
			painel.validarListaDeMagistrados(MAGISTRADOS);
			
			// Seleciona os desembargadores que pediram a vista regimental
			String nomeMagistrado1 = MAGISTRADOS.get(0).getNM_MAGISTRADO_TITULAR();
			String nomeMagistrado2 = MAGISTRADOS.get(1).getNM_MAGISTRADO_TITULAR();
			painel.selecionarMagistrado(nomeMagistrado1);
			painel.selecionarMagistrado(nomeMagistrado2);
			painel.confirmarVistaRegimental();
			
			// Verificar que todos os usuário receberam a mensagem de processo em vista regimental (exceto secretário)
			String nomesMagistrados = ": " + nomeMagistrado1.substring(0, nomeMagistrado1.indexOf(' '))
					+ " , " + nomeMagistrado2.substring(0, nomeMagistrado2.indexOf(' '));
			for (Usuario usuario: Usuario.values()) {
				if (usuario != Usuario.SECRETARIO) {
					paineis.get(usuario).validarMensagemProcesso(processoVistaRegimental, false, nomesMagistrados);
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
			processoVistaRegimental.mudarEstado(EstadoProcesso.NAO_JULGADO);
			BDUtils.getInstance().atualizarProcesso(processoVistaRegimental);
			BDUtils.getInstance().limparVistaRegimental(processoVistaRegimental);
		}
	}

	/**
	 * Cenário 5 - Selecionar um processo que não esteja marcado como "Preferencial"
	 * e marcá-lo como "Preferencial". Depois disso, todos os usuário recebem a atualização
	 * do estado do processo.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void marcarPreferencialProcesso() throws IOException, SQLException, ClassNotFoundException {
		// Busca um processo não julgado e não preferencial e o processo apregoado
		RoteiroPautaSessao processoPreferencial = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO, false, false);
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Seleciona o processo a dar preferência no secretário
		PainelSecretario painel = (PainelSecretario) paineis.get(Usuario.SECRETARIO);
		painel.selecionarProcesso(processoPreferencial);

		try {
			// Marca o processo como preferencial no secretário
			painel.marcarPreferencial();
			marcarProcessoPreferencial(processoPreferencial);
			
			// Verificar que todos os usuários receberam a atualização do novo processo preferencial
			for (Usuario usuario: Usuario.values()) {
				paineis.get(usuario).aguardarIconeProcesso(processoPreferencial, IconesProcesso.PREFERENCIA);
			}
	
			// Verificar que todos os usuário continuam com o processo apregoado selecionado
			// (exceto o secretário), a lista de processos foi atualizada e o quadro de
			// resumo está correto.
			for (Usuario usuario: Usuario.values()) {
				if (usuario == Usuario.SECRETARIO) {
					paineis.get(usuario)
						.validarProcessoSelecionado(processoPreferencial)
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
			processoPreferencial.mudarPreferencial(false);
			BDUtils.getInstance().recuperarOrdenacaoProcessos(PROCESSOS_PAUTA);
			BDUtils.getInstance().atualizarProcesso(processoPreferencial);
		}
	}
	
	/*
	 * Cenários 6-9 - Os cenários para validação dos botôes desabilitados já é feita
	 * no caso de teste TC03_PainelAcompanhamentoTest.verificarListaProcessosSecretario()
	 * 
	 */
	
	/**
	 * Cenário 11 - Este cenário já foi implementado no caso de teste 
	 * TC04_MarcarProcessoComoJulgadoTest.julgarProcessoSemNenhumNaoJulgadoDisponivel()
	 * 
	 */

	/**
	 * Cenário 12 - Este cenário já foi implementado no caso de teste
	 * TC04_MarcarProcessoComoJulgadoTest.julgarProcessoSemNenhumNaoJulgadoOuVistaMesaDisponivel()
	 * 
	 */
	
	/**
	 * Altera o estado do processo para "Preferencial" e reordena a lista de processos de
	 * acordo com esta mudança.
	 *  
	 * @param processo Processo da lista de processos que deve ser marcado como preferencial.
	 */
	private void marcarProcessoPreferencial(RoteiroPautaSessao processo) {
		// Se processo já era preferencial nada muda
		if (processo.getIN_PREFERENCIA().charValue() == 'S') return;
		
		// Pega posição atual do processo antes de marcá-lo como preferencial
		int posicaoAntigaProcesso = processo.getNR_SEQUENCIAL_PROCESSO();
		
		// Calcula qual deve ser a nova posição do processo marcado como preferencial
		int primeiroNaoPreferencial = 1;
		boolean naoPreferencialEncontrado = false;
		while (!naoPreferencialEncontrado) {
			// Procura pelo processo na posição sendo verificada
			RoteiroPautaSessao naoPreferencial = null;
			for (RoteiroPautaSessao p : PROCESSOS_PAUTA) {
				if (p.getNR_SEQUENCIAL_PROCESSO() == primeiroNaoPreferencial) {
					naoPreferencial = p;
				}
			}
			
			// Verifica se este processo é não preferencial ou sustentação oral
			if (naoPreferencial.getIN_PREFERENCIA().charValue() == 'S' || naoPreferencial.getIN_SUSTENTACAO_ORAL().charValue() == 'S')
				primeiroNaoPreferencial++;
			else
				naoPreferencialEncontrado = true;
		}
		
		// Se o processo marcado como preferencial estiver antes do primeiro processo
		// não preferencial, nada muda
		if (primeiroNaoPreferencial > posicaoAntigaProcesso) return;
		
		// Altera estado do processo para preferencial e posição para a posição encontrada
		processo.mudarPreferencial(true);
		processo.mudarSequencialProcesso(primeiroNaoPreferencial);
		
		// Reposiciona os processos subsequentes uma posicao para baixo
		for (int i = posicaoAntigaProcesso - 1; i >= primeiroNaoPreferencial; i--) {
			// Encontra processo na lista de processos pela posicao atual
			RoteiroPautaSessao p = null;
			for (RoteiroPautaSessao p2 : PROCESSOS_PAUTA) {
				if (p2.getNR_SEQUENCIAL_PROCESSO() == i && p2 != processo) {
					p = p2;
				}
			}
			
			// Incrementa uma posicao ao processo encontrado
			p.mudarSequencialProcesso(i + 1);
		}
	}

}
