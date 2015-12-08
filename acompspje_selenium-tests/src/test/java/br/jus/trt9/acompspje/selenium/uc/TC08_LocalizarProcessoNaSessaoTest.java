package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC08_LocalizarProcessoNaSessaoTest extends UsuarioUnicoTestTemplate {

	/**
	 * Cenário 1 - O usuário entra com o número completo de um processo no filtro da lista
	 * de processos e apenas o processo desejado é mostrado na lista de processos.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void localizarProcessoPeloNumeroCompleto() throws IOException, SQLException, ClassNotFoundException {
		// Pegar um processo não julgado da lista de processos
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar na sessão de teste como secretário
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Localizar processo na sessão
		painel.localizarProcesso(processoNaoJulgado.getNR_PROCESSO_CNJ())
		
		// Validar que apenas um processo é mostrado na lista
			.validarListaDeProcessos(Arrays.asList(processoNaoJulgado))
		
		// Seleciona o processo filtrado
			.selecionarProcesso(processoNaoJulgado)
			
		// Valida que o processo é selecinado corretamente
			.validarProcessoSelecionado(processoNaoJulgado);
	}

	/**
	 * Cenário 2 - O usuário entra com parte do número de um processo no filtro da lista
	 * de processos e todos os processos que possuem este número são mostrados na lista 
	 * de processos.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void localizarProcessoUsandoParteDoNumero() throws IOException, SQLException, ClassNotFoundException {
		// Pegar um processo não julgado da lista de processos
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Pegar os últimos 4 dígitos do processo e montar a lista de processos esperados
		String numeroCompleto = processoNaoJulgado.getNR_PROCESSO_CNJ();
		String numeroParcial = numeroCompleto.substring(numeroCompleto.length() - 4);
		List<RoteiroPautaSessao> listaParcial = new ArrayList<RoteiroPautaSessao>();
		for (RoteiroPautaSessao processo : PROCESSOS_PAUTA) {
			if (processo.getNR_PROCESSO_CNJ().contains(numeroParcial))
				listaParcial.add(processo);
		}
		
		// Entrar na sessão de teste como secretário
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Localizar processos na sessão
		painel.localizarProcesso(numeroParcial)
		
		// Validar que apenas um processo é mostrado na lista
			.validarListaDeProcessos(listaParcial)
		
		// Seleciona o processo filtrado
			.selecionarProcesso(processoNaoJulgado)
			
		// Valida que o processo é selecinado corretamente
			.validarProcessoSelecionado(processoNaoJulgado);
	}

	/**
	 * Cenário 3 - O usuário entra com o número inválido de processo no filtro da lista
	 * de processos e nenhum processo é mostrado na lista de processos.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void localizarProcessoComNumeroIncorreto() throws IOException, SQLException, ClassNotFoundException {
		// Cria uma string com um número inválido de processo
		String numeroProcessoInvalido = "0099";
		
		// Entrar na sessão de teste como secretário
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Localizar processo na sessão
		painel.localizarProcesso(numeroProcessoInvalido)
		
		// Validar que apenas um processo é mostrado na lista
			.validarListaDeProcessosVazia();
	}

	/**
	 * Cenário 4 - O usuário entra com o número completo de um processo no filtro da lista
	 * de processos e apenas o processo desejado é mostrado na lista de processos, depois 
	 * ele limpa o filtro e todos os processos são mostrados novamente.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void limparFiltroAposLocalizarProcesso() throws IOException, SQLException, ClassNotFoundException {
		// Pegar um processo não julgado da lista de processos
		RoteiroPautaSessao processoNaoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar na sessão de teste como secretário
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Localizar processo na sessão
		painel.localizarProcesso(processoNaoJulgado.getNR_PROCESSO_CNJ())
		
		// Validar que apenas um processo é mostrado na lista
			.validarListaDeProcessos(Arrays.asList(processoNaoJulgado))
		
		// Seleciona o processo filtrado
			.selecionarProcesso(processoNaoJulgado)
			
		// Valida que o processo é selecinado corretamente
			.validarProcessoSelecionado(processoNaoJulgado)
			
		// Limpar filtro da lista de processos
			.limparLocalizarProcesso()
			
		// Valida que a lista com todos os processos é mostrada 
		// e que o processo filtardo permanece selecionado
			.validarListaDeProcessos(PROCESSOS_PAUTA)
			.validarProcessoSelecionado(processoNaoJulgado);
	}

}
