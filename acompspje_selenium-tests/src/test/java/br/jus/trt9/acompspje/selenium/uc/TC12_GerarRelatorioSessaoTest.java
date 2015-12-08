package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC12_GerarRelatorioSessaoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1, 4 e 6 - Gerar relatório da sessão encerrada, validando os dados do
	 * relatório e a lista de magistrados com vista regimental.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRelatorioDaSessaoEncerrada() throws IOException, SQLException, ClassNotFoundException {
		try {
			// Entrar na tela inicial e autenticar o usuário
			new TelaInicial(driver)
				.autenticarUsuario()
			
			// Encerrar a sessão de teste
				.encerrarSessao(SESSAO.getID_SESSAO_PJE())
				.validarConfirmacaoEncerramentoSessao()
				.confirmarEncerramentoSessao()
				.validarSessaoEncerradaComSucesso(false)
				
			// Gerar o relatório
				.gerarRelatorioSessao(SESSAO.getID_SESSAO_PJE())
				.validarRelatorioSessao(SESSAO, PROCESSOS_PAUTA, MAGISTRADOS, VISTAS_REGIMENTAIS);
		}
		finally {
			// Reabrir sessão de teste
			SESSAO.mudarStatus('D');
			BDUtils.getInstance().atualizarSessao(SESSAO);
		}
	}
	
	/**
	 * Cenário 2 - O teste de acesso ao relatório da sessão com usuários sem o perfil 
	 * correto já é feito no caso de teste TC01_AcessarSistemaTest.
	 */

	/**
	 * Cenário 3, 4 e 6 - Gerar relatório da sessão disponível, validando os dados do
	 * relatório e a lista de magistrados com vista regimental.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void gerarRelatorioDaSessaoDisponivel() throws IOException, SQLException, ClassNotFoundException {
		try {
			// Entrar na tela inicial e autenticar o usuário
			new TelaInicial(driver)
				.autenticarUsuario()
				
			// Gerar o relatório
				.gerarRelatorioSessao(SESSAO.getID_SESSAO_PJE())
				.validarRelatorioSessao(SESSAO, PROCESSOS_PAUTA, MAGISTRADOS, VISTAS_REGIMENTAIS);
		}
		finally {
			// Reabrir sessão de teste
			SESSAO.mudarStatus('D');
			BDUtils.getInstance().atualizarSessao(SESSAO);
		}
	}
	
}
