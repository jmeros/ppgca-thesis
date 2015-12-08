package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;

public class TC05_ApregoarProcessoTest extends TodosUsuariosTestTemplate {

	/**
	 * Cenário 2, 3, 5-7 - Selecionar um processo "Não Julgado", apregoá-lo
	 * e verificar que a tela de todos os usuários é atualizada corretamente.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void apregoarProcessoNãoJulgado() throws SQLException, ClassNotFoundException, IOException {
		// Localiza o primeiro processo da lista e o processo apregoado
		RoteiroPautaSessao primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Localiza o processo que já está apregoado e marca como não julgado
		processoApregoado.mudarEstado(EstadoProcesso.NAO_JULGADO);
		BDUtils.getInstance().atualizarProcesso(processoApregoado);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o primeiro processo selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(primeiroProcesso);
		}
		
		// Apregoar o processo que já estava apregoado antes de iniciar o teste
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.selecionarProcesso(processoApregoado)
			.marcarApregoado();
		processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);

		// Verificar que todos os usuário receberam a mensagem de processo apregoado (exceto secretário)
		for (Usuario usuario: Usuario.values()) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario).validarMensagemProcesso(processoApregoado, false);
			}
		}
		
		// Verificar que todos os usuário passam a estar com o processo apregoado selecionado
		// (exceto o Assistente que permanece no processo seleciona anteriormente), a lista 
		// de processos foi atualizada e o quadro de resumo foi atualizado corretamente.
		for (Usuario usuario: Usuario.values()) {
			if (usuario != Usuario.ASSISTENTE) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoApregoado)
					.validarListaDeProcessos(PROCESSOS_PAUTA);
			}
			else {
				paineis.get(usuario)
					.validarProcessoSelecionado(primeiroProcesso)
					.validarListaDeProcessos(PROCESSOS_PAUTA);
			}
		}
	}
	
	/**
	 * Cenário 8-10 - Selecionar o processo "Apregoado", remove o apregoamento e
	 * verifica que a tela de todos os usuários é atualizada corretamente.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void removerProcessoApregoado() throws SQLException, ClassNotFoundException, IOException {
		// Localiza o primeiro processo da lista e o processo apregoado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		
		// Logar todos os usuário na sessão de teste
		logarTodosOsUsuarios();
		
		// Verifica que todos os usuário estão com o processo apregoado selecionado
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario).validarProcessoSelecionado(processoApregoado);
		}
		
		// Remover o processo que está apregoado e já foi pré-selecionado ao entrar na sessão
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.marcarNaoJulgado();
		processoApregoado.mudarEstado(EstadoProcesso.NAO_JULGADO);

		// Verificar que todos os usuário receberam a mensagem de processo apregoado (exceto secretário)
		for (Usuario usuario: Usuario.values()) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario).validarMensagemProcesso(processoApregoado, false);
			}
		}
		
		// Verificar que todos os usuário continuam com o processo apregoado selecionado
		// (incluindo o secretário), a lista de processos foi atualizada e o quadro de resumo
		// foi atualizado corretamente.
		for (Usuario usuario: Usuario.values()) {
			paineis.get(usuario)
				.validarProcessoSelecionado(processoApregoado)
				.validarListaDeProcessos(PROCESSOS_PAUTA);
		}
	}

}
