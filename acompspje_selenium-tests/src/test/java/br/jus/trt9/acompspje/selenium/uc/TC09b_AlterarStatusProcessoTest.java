package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosPainelAcompanhamento;

public class TC09b_AlterarStatusProcessoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 10 - Selecionar um processo que já esteja marcado como Preferência, verificar
	 * que o botão preferência fica desabilitado.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void tentarMarcarPreferenciaEmProcessoComPreferencia() throws IOException, SQLException, ClassNotFoundException {
		// Busca por processos com preferencia marcada
		List<RoteiroPautaSessao> processosComPreferencia = new ArrayList<RoteiroPautaSessao>();
		processosComPreferencia.add(encontrarProcessoPorEstado(null, true, false));
		processosComPreferencia.add(encontrarProcessoPorEstado(null, false, true));
		processosComPreferencia.add(encontrarProcessoPorEstado(null, true, true));
		
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		for (RoteiroPautaSessao processo : processosComPreferencia) {
			// Seleciona o processo a dar preferência no secretário
			painel.selecionarProcesso(processo);
			
			// Verificar que o botão de Preferencia está desabilitado
			Assert.assertFalse("Botão Preferencial está habilitado para o processo " + processo.getNR_PROCESSO_CNJ() + ".", ElementosPainelAcompanhamento.botaoPreferencial(driver).isEnabled());
		}
	}

}
