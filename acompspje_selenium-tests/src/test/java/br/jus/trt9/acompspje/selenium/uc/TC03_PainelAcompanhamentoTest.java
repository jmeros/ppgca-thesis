package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.PainelAssistente;
import br.jus.trt9.acompspje.selenium.telas.PainelGabinete;
import br.jus.trt9.acompspje.selenium.telas.PainelMagistrado;
import br.jus.trt9.acompspje.selenium.telas.PainelProcurador;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

/**
 * Classe de teste para o painel de acompanhamento. Implementa os casos de teste:
 *    - TC03aApresentarPainelDoSecretario
 *    - TC03bApresentarPainelDoAssistente
 * 
 * @author jadermeros
 */
public class TC03_PainelAcompanhamentoTest extends UsuarioUnicoTestTemplate {

	// Dados usados nos testes para verificar detalhes de processo
	private RoteiroPautaSessao primeiroProcesso;
	private HashMap<EstadoProcesso, RoteiroPautaSessao> listaProcessosPorEstado;
	
	@Before
	public void prepararProcessosDeTeste() {
		// Pegar primeiro processo na lista (número do processo: X000074-51.2013.5.09.0122)
		primeiroProcesso = encontrarPrimeiroProcessoDaSessao();
		
		// Pegar um processo de cada estado possível
		listaProcessosPorEstado = new HashMap<EstadoProcesso, RoteiroPautaSessao>();
		listaProcessosPorEstado.put(EstadoProcesso.ADIADO, encontrarProcessoPorEstado(EstadoProcesso.ADIADO));
		listaProcessosPorEstado.put(EstadoProcesso.APREGOADO, encontrarProcessoPorEstado(EstadoProcesso.APREGOADO));
		listaProcessosPorEstado.put(EstadoProcesso.JULGADO, encontrarProcessoPorEstado(EstadoProcesso.JULGADO));
		listaProcessosPorEstado.put(EstadoProcesso.NAO_JULGADO, encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO));
		listaProcessosPorEstado.put(EstadoProcesso.RETIRADO, encontrarProcessoPorEstado(EstadoProcesso.RETIRADO));
		listaProcessosPorEstado.put(EstadoProcesso.REVISAR, encontrarProcessoPorEstado(EstadoProcesso.REVISAR));
		listaProcessosPorEstado.put(EstadoProcesso.VISTA_MESA, encontrarProcessoPorEstado(EstadoProcesso.VISTA_MESA));
		listaProcessosPorEstado.put(EstadoProcesso.VISTA_REGIMENTAL, encontrarProcessoPorEstado(EstadoProcesso.VISTA_REGIMENTAL));
		
	}
	
	private void validarListaProcessos(PainelAcompanhamento painel) throws ClassNotFoundException, SQLException, IOException {
		// Validar as informações sobre a sessão no cabeçalho da lista de processos
		painel.validarDetalhesSessao(SESSAO)
		
		// Validar a lista de processos da sessao
			.validarListaDeProcessos(PROCESSOS_PAUTA)
		
		// Validar o quadro-resumo da sessão
			.validarQuadroResumo(PROCESSOS_PAUTA)
		
		// Validar ícones da lista de processos da sessão
			.validarIconesListaDeProcessos(PROCESSOS_PAUTA);

		// Validar que a sessão está online (exceto no secretário)
		if (!(painel instanceof PainelSecretario))
			painel.validarSessaoOnline();
		
	}

	/**
	 * Cenários 1-4, 6 e 7 - Verificar as informações da lista de processos da sessão:
	 *                  (1) Verificar se lista está completa
	 *                  (2) Verificar se ordem dos processos está correta
	 *                  (3) Verificar quadro-resumo da sessão
	 *                  (4) Verificar ícones de atributos dos processos
	 *                  (6) Verificar detalhes da sessão
	 *                  (7) Verificar cores dos processos
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis e verificar que todos os processos
	 * associados a sessão escolhida são mostrados na listagem de processos corretamente.
	 * 
	 * Este cenário deve ser executado para todos os perfis (Secretário, Magistrado, Assistente,
	 * Gabinete e Procurador).
	 */
	@Test
	public void verificarListaProcessosSecretario() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
			
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarListaProcessos(painel);
	}
	
	@Test
	public void verificarListaProcessosAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarListaProcessos(painel);
	}

	@Test
	public void verificarListaProcessosGabinete() throws IOException, SQLException, ClassNotFoundException {
		// Alterar perfil do usuario de teste
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
		
		try {
			// Entrar no sistema
			PainelGabinete painel = new TelaInicial(driver)
				.autenticarUsuario()
				
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
			
			validarListaProcessos(painel);
		}
		finally {
			// Recuperar perfil padrão do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	@Test
	public void verificarListaProcessosProcurador() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelProcurador painel = new TelaInicial(driver)
			.autenticarUsuario()
			
		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarListaProcessos(painel);
	}

	@Test
	public void verificarListaProcessosMagistrado() throws IOException, SQLException, ClassNotFoundException {
		// Alterar o perfil do usuário de teste
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try {
			// Entrar no sistema
			PainelMagistrado painel = new TelaInicial(driver)
				.autenticarUsuario()
				
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
			
			validarListaProcessos(painel);
		}
		finally {
			// Recuperar o perfil padrão do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}
	
	/**
	 * Cenários 5, 8 e 9 - Verificar se a seleção de processo na lista funciona corretamente:
	 *                  (5) Verificar detalhes do processo
	 *                  (8) Verificar botões de ação exclusivos do secretário
	 *                  (9) Verificar o editor de dispositivo
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 * 
	 * Este cenário deve ser executado para todos os perfis (Secretário, Magistrado, Assistente, 
	 * Gabinete e Procurador).
	 */
	@Test
	public void verficarDetalhesProcessoSecretario() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarDetalhesProcessos(painel);
	}

	@Test
	public void verficarDetalhesProcessoAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarDetalhesProcessos(painel);
	}

	@Test
	public void verficarDetalhesProcessoGabinete() throws IOException, SQLException, ClassNotFoundException {
		// Alterar o perfil do usuário de teste
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
		
		try {
			// Entrar no sistema
			PainelGabinete painel = new TelaInicial(driver)
				.autenticarUsuario()
	
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
			
			validarDetalhesProcessos(painel);
		}
		finally {
			// Recuperar o perfil padrão do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	@Test
	public void verficarDetalhesProcessoMagistrado() throws IOException, SQLException, ClassNotFoundException {
		// Alterar o perfil do usuário de teste
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try {
			// Entrar no sistema
			PainelMagistrado painel = new TelaInicial(driver)
				.autenticarUsuario()
	
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
			
			validarDetalhesProcessos(painel);
		}
		finally {
			// Recuperar o perfil padrão do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	@Test
	public void verficarDetalhesProcessoProcurador() throws IOException, SQLException, ClassNotFoundException {
		// Entrar no sistema
		PainelProcurador painel = new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		validarDetalhesProcessos(painel);
	}

	/**
	 * ACOMPSPJE-348 - Verificar se a seleção de processo na lista funciona corretamente para
	 * um processo de classe judicial diferente de RO no secretário.
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 */
	@Test
	public void verficarDetalhesProcessoDiferenteROSecretario() throws IOException, SQLException, ClassNotFoundException {
		// Encotrar processo diferentes de Recurso Ordinário na lista de processos
		RoteiroPautaSessao processoAIRO = encontrarProcessoPorClasseJudicial("AIRO");
		RoteiroPautaSessao processoReenec = encontrarProcessoPorClasseJudicial("Reenec/RO");
		
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())
		
		// Selecionar o processo AIRO
			.selecionarProcesso(processoAIRO)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoAIRO)

		// Selecionar o processo Reenec
			.selecionarProcesso(processoReenec)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoReenec);
				
	}

	/**
	 * ACOMPSPJE-348 - Verificar se a seleção de processo na lista funciona corretamente para
	 * um processo de classe judicial diferente de RO no procurador.
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 */
	@Test
	public void verficarDetalhesProcessoDiferenteROProcurador() throws IOException, SQLException, ClassNotFoundException {
		// Encotrar processo diferentes de Recurso Ordinário na lista de processos
		RoteiroPautaSessao processoAIRO = encontrarProcessoPorClasseJudicial("AIRO");
		RoteiroPautaSessao processoReenec = encontrarProcessoPorClasseJudicial("Reenec/RO");
				
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())

		// Selecionar o processo AIRO
			.selecionarProcesso(processoAIRO)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoAIRO)

		// Selecionar o processo Reenec
			.selecionarProcesso(processoReenec)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoReenec);
	}

	/**
	 * ACOMPSPJE-348 - Verificar se a seleção de processo na lista funciona corretamente para
	 * um processo de classe judicial diferente de RO no assistente.
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 */
	@Test
	public void verficarDetalhesProcessoDiferenteROAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Encotrar processo diferentes de Recurso Ordinário na lista de processos
		RoteiroPautaSessao processoAIRO = encontrarProcessoPorClasseJudicial("AIRO");
		RoteiroPautaSessao processoReenec = encontrarProcessoPorClasseJudicial("Reenec/RO");
		
		// Entrar no sistema
		new TelaInicial(driver)
			.autenticarUsuario()

		// Acessar lista de sessões disponíveis e escolher uma sessão
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())

		// Selecionar o processo AIRO
			.selecionarProcesso(processoAIRO)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoAIRO)

		// Selecionar o processo Reenec
			.selecionarProcesso(processoReenec)
		
		// Validar os detalhes do processo (todos os dados completos)
			.validarProcessoSelecionado(processoReenec);
	}

	/**
	 * ACOMPSPJE-348 - Verificar se a seleção de processo na lista funciona corretamente para
	 * um processo de classe judicial diferente de RO no gabinete.
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 */
	@Test
	public void verficarDetalhesProcessoDiferenteROGabinete() throws IOException, SQLException, ClassNotFoundException {
		// Alterar o perfil do usuário de teste para gabinete
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
		
		try {
			// Encotrar processo diferentes de Recurso Ordinário na lista de processos
			RoteiroPautaSessao processoAIRO = encontrarProcessoPorClasseJudicial("AIRO");
			RoteiroPautaSessao processoReenec = encontrarProcessoPorClasseJudicial("Reenec/RO");
			
			// Entrar no sistema
			new TelaInicial(driver)
				.autenticarUsuario()
	
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())

			// Selecionar o processo AIRO
				.selecionarProcesso(processoAIRO)
			
			// Validar os detalhes do processo (todos os dados completos)
				.validarProcessoSelecionado(processoAIRO)

			// Selecionar o processo Reenec
				.selecionarProcesso(processoReenec)
			
			// Validar os detalhes do processo (todos os dados completos)
				.validarProcessoSelecionado(processoReenec);
		}
		finally {
			// Recuperar o perfil do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	/**
	 * ACOMPSPJE-348 - Verificar se a seleção de processo na lista funciona corretamente para
	 * um processo de classe judicial diferente de RO no magistrado.
	 * 
	 * Acessar uma sessão através da lista de sessões disponíveis, selecionar um dos processos
	 * na listagem de processos e verificar se a seleção funciona corretamente e os dados do
	 * processo são mostrados pro usuário.
	 */
	@Test
	public void verficarDetalhesProcessoDiferenteROMagistrado() throws IOException, SQLException, ClassNotFoundException {
		// Alterar o perfil do usuário de teste para gabinete
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
		
		try {
			// Encotrar processo diferentes de Recurso Ordinário na lista de processos
			RoteiroPautaSessao processoAIRO = encontrarProcessoPorClasseJudicial("AIRO");
			RoteiroPautaSessao processoReenec = encontrarProcessoPorClasseJudicial("Reenec/RO");
			
			// Entrar no sistema
			new TelaInicial(driver)
				.autenticarUsuario()
	
			// Acessar lista de sessões disponíveis e escolher uma sessão
				.entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size())

			// Selecionar o processo AIRO
				.selecionarProcesso(processoAIRO)
			
			// Validar os detalhes do processo (todos os dados completos)
				.validarProcessoSelecionado(processoAIRO)

			// Selecionar o processo Reenec
				.selecionarProcesso(processoReenec)
			
			// Validar os detalhes do processo (todos os dados completos)
				.validarProcessoSelecionado(processoReenec);
		}
		finally {
			// Recuperar o perfil do usuário de teste
			BDUtils.getInstance().recuperarPerfilUsuario();
		}
	}

	private void validarDetalhesProcessos(PainelAcompanhamento painel) throws SQLException, ClassNotFoundException, IOException {
		// Selecionar o primeiro processo
		painel.selecionarProcesso(primeiroProcesso)
		
		// Validar os detalhes do primeiro processo (todos os dados completos)
			.validarProcessoSelecionado(primeiroProcesso)
		
		// Validar os detalhes para cada estado de processo disponível
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.ADIADO))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.ADIADO))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.APREGOADO))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.APREGOADO))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.JULGADO))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.JULGADO))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.NAO_JULGADO))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.NAO_JULGADO))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.RETIRADO))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.RETIRADO))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.REVISAR))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.REVISAR))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.VISTA_MESA))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.VISTA_MESA))
			
			.selecionarProcesso(listaProcessosPorEstado.get(EstadoProcesso.VISTA_REGIMENTAL))
			.validarProcessoSelecionado(listaProcessosPorEstado.get(EstadoProcesso.VISTA_REGIMENTAL));
	}

}
