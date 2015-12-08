package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.Test;

import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;

public class TC02_ManterAtualizacaoAutomaticaTest extends TodosUsuariosTestTemplate {

	/**
	 * Cenário 1 e 3 - O Magistrado e o Procurador ativam a atualização automática previamente 
	 * desativada e a sessão passada a receber as atualizações efetuadas pelo Secretário.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void ativarAtualizaçãoAutomática() throws IOException, SQLException, ClassNotFoundException {
		// Pegar o processo apregoado atualmente, um processo não julgado e um já julgado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoJulgado = encontrarProcessoPorEstado(EstadoProcesso.JULGADO);
		
		// Logar o secretário, o magistrado e o procurador
		Usuario[] usuarios = new Usuario[]{Usuario.SECRETARIO, Usuario.MAGISTRADO, Usuario.PROCURADOR};
		logarUsuarios(usuarios);
		
		// Executa as mesmas ações no magistrado e no procurador
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				// Desabilita a atualização automática
				paineis.get(usuario)
					.mudarAtualizacaoAutomatica()
					.validarAtualizacaoAutomatica(false)
					
				// Seleciona o processo julgado
					.selecionarProcesso(processoJulgado)
					.validarProcessoSelecionado(processoJulgado);
			}
		}
		
		// Secretário apregoa o processo não julgado
		paineis.get(Usuario.SECRETARIO)
			.selecionarProcesso(processoNaoJulgado)
			.validarProcessoSelecionado(processoNaoJulgado);
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.marcarApregoado();
		processoNaoJulgado.mudarEstado(EstadoProcesso.APREGOADO);
		processoApregoado.mudarEstado(EstadoProcesso.NAO_JULGADO);

		// Validar que a mensagem de processo apregoado é recebida,
		// porém a seleção permanece no processo julgado
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarMensagemProcesso(processoNaoJulgado, false);
			}
		}
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoJulgado);
			}
		}
		
		// Executa as mesmas ações para o magistrado e o procurador
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				// Habilita a atualização automática
				paineis.get(usuario)
					.mudarAtualizacaoAutomatica()
					.validarAtualizacaoAutomatica(true)
					
				// Verifica que a seleção passa automaticamente para o processo apregoado
					.validarProcessoSelecionado(processoNaoJulgado);
			}
		}
		
		// Secretário apregoa novamente o processo apregado no início do cenário
		paineis.get(Usuario.SECRETARIO)
			.selecionarProcesso(processoApregoado)
			.validarProcessoSelecionado(processoApregoado);
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.marcarApregoado();
		processoNaoJulgado.mudarEstado(EstadoProcesso.NAO_JULGADO);
		processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);
		
		// Magistrado e procurador são atualizados automaticamente
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarMensagemProcesso(processoApregoado, false);
			}
		}
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoApregoado);
			}
		}
	}

	/**
	 * Cenário 2 e 4 - O Magistrado e o Procurador desativam a atualização automática e a sessão 
	 * para de receber as atualizações efetuadas pelo Secretário.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void desativarAtualizaçãoAutomática() throws IOException, SQLException, ClassNotFoundException {
		// Pegar o processo apregoado atualmente, um processo não julgado e um já julgado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		RoteiroPautaSessao processoJulgado = encontrarProcessoPorEstado(EstadoProcesso.JULGADO);
		
		// Logar o secretário, o magistrado e o procurador
		Usuario[] usuarios = new Usuario[]{Usuario.SECRETARIO, Usuario.MAGISTRADO, Usuario.PROCURADOR};
		logarUsuarios(usuarios);

		// Secretário apregoa o processo não julgado
		paineis.get(Usuario.SECRETARIO)
			.selecionarProcesso(processoNaoJulgado)
			.validarProcessoSelecionado(processoNaoJulgado);
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.marcarApregoado();
		processoNaoJulgado.mudarEstado(EstadoProcesso.APREGOADO);
		processoApregoado.mudarEstado(EstadoProcesso.NAO_JULGADO);
		
		// Magistrado e procurador são atualizados automaticamente
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarMensagemProcesso(processoNaoJulgado, false);
			}
		}
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoNaoJulgado);
			}
		}
		
		// Executa as mesmas ações no magistrado e no procurador
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				// Desabilita a atualização automática
				paineis.get(usuario)
					.mudarAtualizacaoAutomatica()
					.validarAtualizacaoAutomatica(false)
					
				// Seleciona o processo julgado
					.selecionarProcesso(processoJulgado)
					.validarProcessoSelecionado(processoJulgado);
			}
		}
		
		// Secretário apregoa o processo apregoado anteriormente
		paineis.get(Usuario.SECRETARIO)
			.selecionarProcesso(processoApregoado)
			.validarProcessoSelecionado(processoApregoado);
		((PainelSecretario) paineis.get(Usuario.SECRETARIO))
			.marcarApregoado();
		processoNaoJulgado.mudarEstado(EstadoProcesso.NAO_JULGADO);
		processoApregoado.mudarEstado(EstadoProcesso.APREGOADO);

		// Validar que a mensagem de processo apregoado é recebida,
		// porém a seleção permanece no processo julgado
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarMensagemProcesso(processoApregoado, false);
			}
		}
		for (Usuario usuario : usuarios) {
			if (usuario != Usuario.SECRETARIO) {
				paineis.get(usuario)
					.validarProcessoSelecionado(processoJulgado);
			}
		}
		
	}

}
