package br.jus.trt9.acompspje.selenium.telas;

import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosAcessoSessao;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosLogin;

public class PainelSessoes {
	enum TipoUsuario {
		SECRETARIO, ASSISTENTE, MAGISTRADO, PROCURADOR, GABINETE
	}

	enum PerfilUsuario {
		SECRETARIO, MAGISTRADO, GABINETE, SECRETARIO_GABINETE
	}

	private WebDriver _driver;
	protected WebDriverWait _waitDriver;

	protected PainelSessoes(WebDriver driver) {
		_driver = driver;
		_waitDriver = new WebDriverWait(_driver, 10);
	}

	/**
	 * Localiza a sessão desejada na lista de sessões através do número da
	 * pauta.
	 * 
	 * @param numeroPauta
	 *            O número da pauta sendo procurada.
	 * 
	 * @return O índice da linha onde a pauta desejada se encontra.
	 */
	protected int localizarSessaoPorNumeroPauta(int numeroPauta) {
		int indiceDaLinha = 0;
		String numPauta = Integer.toString(numeroPauta);
		try {
			while (true) {
				indiceDaLinha++;
				WebElement elementoNumeroPauta = ElementosAcessoSessao
						.numeroSessaoPorIndice(_driver, indiceDaLinha);
				if (elementoNumeroPauta.getText().equals(numPauta)) {
					return indiceDaLinha;
				}
			}
		} catch (NoSuchElementException e) {
			// Quando o índice da lista excede o número de linhas da tabela,
			// esta exceção é lançada.
			Assert.fail("Não foi possível encontrar o número de pauta desejado na tela.");
		}

		return -1; // Não deve ocorrer, pois a excessão deve ocorrer primeiro
	}

	public PainelConfiguracoes entrarConfiguracoes() {
		ElementosAcessoSessao.botaoConfiguracoes(_driver).click();

		PainelConfiguracoes painel = new PainelConfiguracoes(_driver);
		painel.validarPaginaCarregada();
		return painel;
	}

	public PainelRoteiroSessao entrarRoteiroSessaoPorNumeroPauta(int numeroPauta) {
		// Encontrar sessão na lista pelo número da pauta
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);

		// Clicar no menu para acompanhar sessão e escolher o perfil desejado
		PainelRoteiroSessao painel = null;
		ElementosAcessoSessao.menuAcompanharSessao(_driver, indiceDaLinha)
				.click();
		ElementosAcessoSessao.opcaoGerarRoteiroSessao(_driver, indiceDaLinha)
				.click();
		painel = new PainelRoteiroSessao(_driver);
		painel.validarPaginaCarregada();
		return painel;
	}

	public PainelSessoes verificarOpcaoRoteiroSessaoPorNumeroPauta(int numeroPauta,
			boolean habilitado) throws NoSuchElementException {
		// Buscar linha na lista de sessões com a pauta desejada
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);
		
		WebElement opcao = ElementosAcessoSessao.opcaoGerarRoteiroSessao(
				_driver, indiceDaLinha);
		if (habilitado) {
			Assert.assertFalse("Botão roteiro de sessão desativado", opcao
					.getAttribute("class").contains("ui-state-disabled"));
		} else {
			Assert.assertTrue("Botão roteiro de sessão ativado", opcao
					.getAttribute("class").contains("ui-state-disabled"));
		}

		return this;
	}

	public PainelSessoes verificarOpcaoRoteiroSessaoNaoDisponivel(int numeroPauta) {
		// Buscar linha na lista de sessões com a pauta desejada
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);
		
		// Verificar que a opção para gerar o roteiro da sessão não está disponível
		Assert.assertEquals("Opção para gerar roteiro da sessão está disponível para a sessão " + numeroPauta + ".", 
				0, ElementosAcessoSessao.localizadorGerarRoteiroSessao(indiceDaLinha).findElements(_driver).size());
		
		return this;
	}
	
	protected PainelAcompanhamento entrarSessaoPorNumeroPauta(int numeroPauta,
			TipoUsuario tipoUsuario, int quantidadeProcessosSessao) {
		// Encontrar sessão na lista pelo número da pauta
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);

		// Clicar no menu para acompanhar sessão e escolher o perfil desejado
		PainelAcompanhamento painel = null;
		ElementosAcessoSessao.menuAcompanharSessao(_driver, indiceDaLinha)
				.click();
		switch (tipoUsuario) {
		case SECRETARIO:
			ElementosAcessoSessao.opcaoSecretarioMenuAcompanharSessao(_driver,
					indiceDaLinha).click();
			painel = new PainelSecretario(_driver);
			break;

		case ASSISTENTE:
			ElementosAcessoSessao.opcaoAssistenteMenuAcompanharSessao(_driver,
					indiceDaLinha).click();
			painel = new PainelAssistente(_driver);
			break;

		case GABINETE:
			ElementosAcessoSessao.opcaoGabineteMenuAcompanharSessao(_driver,
					indiceDaLinha).click();
			painel = new PainelGabinete(_driver);
			break;

		case PROCURADOR:
			ElementosAcessoSessao.opcaoProcuradorMenuAcompanharSessao(_driver,
					indiceDaLinha).click();
			painel = new PainelProcurador(_driver);
			break;

		case MAGISTRADO:
			ElementosAcessoSessao.opcaoMagistradoMenuAcompanharSessao(_driver,
					indiceDaLinha).click();
			painel = new PainelMagistrado(_driver);
			break;

		default:
			Assert.fail("Funcionalidade não implementada");
		}

		painel.validarPaginaCarregada(quantidadeProcessosSessao);
		return painel;
	}

	public PainelSecretario entrarSessaoComoSecretarioPorNumeroPauta(
			int numeroPauta, int quantidadeProcessosSessao) {
		return (PainelSecretario) entrarSessaoPorNumeroPauta(numeroPauta,
				TipoUsuario.SECRETARIO, quantidadeProcessosSessao);
	}

	public PainelAssistente entrarSessaoComoAssistentePorNumeroPauta(
			int numeroPauta, int quantidadeProcessosSessao) {
		return (PainelAssistente) entrarSessaoPorNumeroPauta(numeroPauta,
				TipoUsuario.ASSISTENTE, quantidadeProcessosSessao);
	}

	public PainelGabinete entrarSessaoComoGabinetePorNumeroPauta(
			int numeroPauta, int quantidadeProcessosSessao) {
		return (PainelGabinete) entrarSessaoPorNumeroPauta(numeroPauta,
				TipoUsuario.GABINETE, quantidadeProcessosSessao);
	}

	public PainelMagistrado entrarSessaoComoMagistradoPorNumeroPauta(
			int numeroPauta, int quantidadeProcessosSessao) {
		return (PainelMagistrado) entrarSessaoPorNumeroPauta(numeroPauta,
				TipoUsuario.MAGISTRADO, quantidadeProcessosSessao);
	}

	public PainelProcurador entrarSessaoComoProcuradorPorNumeroPauta(
			int numeroPauta, int quantidadeProcessosSessao) {
		return (PainelProcurador) entrarSessaoPorNumeroPauta(numeroPauta,
				TipoUsuario.PROCURADOR, quantidadeProcessosSessao);
	}

	protected void validarPaginaCarregada() {
		// Aguardar até que a tabela de sessões apareça na tela
		_waitDriver.until(ExpectedConditions
				.presenceOfElementLocated(ElementosAcessoSessao
						.localizadorTabelaDeSessoes()));

		// Verificar o título da página e o cabeçalho
		Assert.assertEquals("ASPJE", _driver.getTitle());
		Assert.assertEquals("Acompanhamento de Sessão do PJ-e", ElementosLogin
				.tituloDoSistema(_driver).getText());
	}

	public PainelSessoes encerrarSessao(int numeroPauta) {
		// Localizar a sessão de teste na lista de sessões
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);

		// Clicar no menu "Acompanhar sessão" e depois selecionar
		// "Encerrar Sessão"
		ElementosAcessoSessao.menuAcompanharSessao(_driver, indiceDaLinha)
				.click();
		ElementosAcessoSessao.opcaoEncerrarSessao(_driver, indiceDaLinha)
				.click();

		return this;
	}

	public PainelSessoes validarConfirmacaoEncerramentoSessao() {
		// Aguardar até que modal com a confirmação de encerramento seja
		// mostrada
		_waitDriver.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(ElementosAcessoSessao
						.localizadorDialogoConfirmacaoEncerramento()));

		// Verificar título do diálogo e conteúdo da mensagem
		Assert.assertEquals("Confirmação de encerramento da sessão",
				ElementosAcessoSessao
						.tituloDialogoConfirmacaoEncerramentoSessao(_driver)
						.getText());
		Assert.assertEquals(
				"Atenção! Após encerrar a Sessão os dispositivos dos votos não poderão mais ser editados e os votos completos poderão ser visualizados somente no PJe. Confirma Encerrar ?",
				ElementosAcessoSessao
						.mensagemDialogoConfirmacaoEncerramentoSessao(_driver)
						.getText());

		return this;
	}

	public PainelSessoes confirmarEncerramentoSessao() {
		ElementosAcessoSessao.botaoSimDialogoConfirmacaoEncerramentoSessao(
				_driver).click();

		return this;
	}

	public PainelSessoes cancelarEncerramentoSessao() {
		ElementosAcessoSessao.botaoNaoDialogoConfirmacaoEncerramentoSessao(
				_driver).click();

		return this;
	}

	public PainelSessoes validarDadosSessao(SessaoJulgamento sessao)
			throws SQLException, ClassNotFoundException, IOException {
		// Localizar linha da sessão a ser validada
		int indiceDaLinha = localizarSessaoPorNumeroPauta(sessao
				.getID_SESSAO_PJE());

		// Validar número e hora da pauta, órgão julgador, tipo, sala e situação
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Assert.assertEquals(
				Integer.toString(sessao.getID_SESSAO_PJE()),
				ElementosAcessoSessao.numeroSessaoPorIndice(_driver,
						indiceDaLinha).getText());
		Assert.assertEquals(
				dateFormat.format(sessao.getDT_SESSAO()),
				ElementosAcessoSessao.dataSessaoPorIndice(_driver,
						indiceDaLinha).getText());
		Assert.assertEquals(
				BDUtils.getInstance().buscarDescricaoOrgaoJulgador(
						sessao.getID_SESSAO_PJE()), ElementosAcessoSessao
						.orgaoJulgadorSessaoPorIndice(_driver, indiceDaLinha)
						.getText());
		Assert.assertEquals(sessao.getDS_TIPO_SESSAO(), ElementosAcessoSessao
				.tipoSessaoPorIndice(_driver, indiceDaLinha).getText());
		Assert.assertEquals(sessao.getDS_SALA(), ElementosAcessoSessao
				.salaSessaoPorIndice(_driver, indiceDaLinha).getText());
		if (sessao.getIN_STATUS().charValue() == 'D')
			Assert.assertEquals("DISPONÍVEL", ElementosAcessoSessao
					.situacaoSessaoDisponivelPorIndice(_driver, indiceDaLinha)
					.getText());
		else
			Assert.assertEquals("ENCERRADO", ElementosAcessoSessao
					.situacaoSessaoEncerradaPorIndice(_driver, indiceDaLinha)
					.getText());

		return this;
	}

	public PainelSessoes validarSessaoEncerradaComSucesso(
			boolean aguardarMensagemSumir) {
		// Aguardar a mensagem avisando que a sessão foi encerrada
		_waitDriver.until(ExpectedConditions
				.visibilityOfElementLocated(ElementosAcessoSessao
						.localizadorMensagensDeAtualização()));

		// Verificar entre as mensagens na tela se o processo foi atualizado
		// para o estado esperado
		boolean mensagemEncontrada = false;
		for (WebElement mensagem : ElementosAcessoSessao
				.mensagensDeAtualização(_driver)) {
			String textoMensagem = mensagem.getText();
			if (textoMensagem.contains("Sessão encerrada")) {
				Assert.assertEquals("Sessão encerrada", textoMensagem);

				if (aguardarMensagemSumir)
					_waitDriver.until(ExpectedConditions.stalenessOf(mensagem));

				mensagemEncontrada = true;
			}
		}
		Assert.assertTrue(
				"Mensagem de encerramento da sessão não foi recebida.",
				mensagemEncontrada);

		// Verificar se a mensagem no topo da tela está visível
		Assert.assertEquals("Sessão encerrada", ElementosAcessoSessao
				.mensagemTopoDaTela(_driver).getText());

		return this;
	}

	public PainelSessoes validarOpcaoEncerramentoDesabilitada(int numeroPauta) {
		// Localizar a sessão de teste na lista de sessões
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);

		// Clicar no menu "Acompanhar sessão" e depois validar "Encerrar Sessão"
		// está desabilitado
		ElementosAcessoSessao.menuAcompanharSessao(_driver, indiceDaLinha)
				.click();
		Assert.assertThat(
				ElementosAcessoSessao.opcaoEncerrarSessao(_driver,
						indiceDaLinha).getAttribute("class"),
				containsString("ui-state-disabled"));
		ElementosAcessoSessao.opcaoEncerrarSessao(_driver, indiceDaLinha)
				.click();

		return this;
	}

	public PainelSessoes validarPerfilMagistrado(int numeroPauta) {
		return validarPerfil(numeroPauta, PerfilUsuario.MAGISTRADO);
	}

	public PainelSessoes validarPerfilSecretario(int numeroPauta) {
		return validarPerfil(numeroPauta, PerfilUsuario.SECRETARIO);
	}

	public PainelSessoes validarPerfilGabinete(int numeroPauta) {
		return validarPerfil(numeroPauta, PerfilUsuario.GABINETE);
	}

	public PainelSessoes validarPerfilSecretarioGabinete(int numeroPauta) {
		return validarPerfil(numeroPauta, PerfilUsuario.SECRETARIO_GABINETE);
	}

	public PainelSessoes validarPerfil(int numeroPauta,
			PerfilUsuario perfilUsuario) {
		// Validar que o profile escolhido esta correto
		Assert.assertEquals(perfilUsuario.toString(), ElementosAcessoSessao
				.perfilUsuario(_driver).getText());

		// Validar nome do usuário
		Assert.assertEquals("Selenium Test User - Logout",
				ElementosAcessoSessao.nomeUsuario(_driver).getText());

		// Validar que o usuário pode acessar somente as opções associadas ao
		// seu perfil
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);
		try {
			ElementosAcessoSessao.opcaoMagistradoMenuAcompanharSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Magistrado deve ter acesso a opção de acompanhamento de sessão como Magistrado.",
					perfilUsuario == PerfilUsuario.MAGISTRADO);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Magistrado deve ter acesso a opção de acompanhamento de sessão como Magistrado.",
					perfilUsuario != PerfilUsuario.MAGISTRADO);
		}
		try {
			ElementosAcessoSessao.opcaoSecretarioMenuAcompanharSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso a opção de acompanhamento de sessão como Secretário.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso a opção de acompanhamento de sessão como Secretário.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoAssistenteMenuAcompanharSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso a opção de acompanhamento de sessão como Assistente.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso a opção de acompanhamento de sessão como Assistente.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoProcuradorMenuAcompanharSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso a opção de acompanhamento de sessão como Procurador.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso a opção de acompanhamento de sessão como Procurador.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoGabineteMenuAcompanharSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Gabinete pode ter acesso a opção de acompanhamento de sessão como Gabinete.",
					perfilUsuario == PerfilUsuario.GABINETE
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Gabinete deve ter acesso a opção de acompanhamento de sessão como Gabinete.",
					perfilUsuario != PerfilUsuario.GABINETE
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoGerarRoteiroSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso ao roteiro da sessão.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso ao roteiro da sessão.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoGerarRelatorioSessao(_driver,
					indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso ao relatório da sessão.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso ao relatório da sessão.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}
		try {
			ElementosAcessoSessao.opcaoEncerrarSessao(_driver, indiceDaLinha);
			Assert.assertTrue(
					"Somente o perfil de Secretário pode ter acesso a opção para encerrar a sessão.",
					perfilUsuario == PerfilUsuario.SECRETARIO
							|| perfilUsuario == PerfilUsuario.SECRETARIO_GABINETE);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(
					"O perfil de Secretário deve ter acesso a opção para encerrar a sessão.",
					perfilUsuario != PerfilUsuario.SECRETARIO
							&& perfilUsuario != PerfilUsuario.SECRETARIO_GABINETE);
		}

		return this;
	}

	public TelaLogout sairDoSistema() {
		// Clicar no link de logout do sistema
		ElementosAcessoSessao.nomeUsuario(_driver).click();

		TelaLogout telaLogout = new TelaLogout(_driver);
		telaLogout.validarPaginaCarregada();

		return telaLogout;

	}

	public PainelSessoes validarOrdemDasSessoes() throws ParseException {
		// Validar que todas as sessões da lista estão na ordem correta
		int indiceDaLinha = 0;
		Date dataUltimaSessao = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			WebElement elementoDataSessao = null;
			while ((elementoDataSessao = ElementosAcessoSessao
					.dataSessaoPorIndice(_driver, ++indiceDaLinha)) != null) {
				Date dataSessao = dateFormat
						.parse(elementoDataSessao.getText());
				Assert.assertTrue(
						"Data da sessão é maior que a data da sessão anterior ("
								+ ElementosAcessoSessao.numeroSessaoPorIndice(
										_driver, indiceDaLinha) + ").",
						dataUltimaSessao.after(dataSessao));
				dataUltimaSessao = dataSessao;
			}
		} catch (NoSuchElementException ex) {
			Assert.assertNotEquals(
					"Nenhuma sessão foi encontrada na listagem de sessões.", 1,
					indiceDaLinha);
		}

		return this;
	}

	/**
	 * Verificar que a sessáo passada não está presente na listagem de sessões.
	 * 
	 * @param numeroPauta
	 *            Número da pauta de sessão que não deve estar presente.
	 * 
	 * @return PainelSessoes atual.
	 */
	public PainelSessoes validarSessaoNaoDisponivel(int numeroPauta) {
		// Verificar que a sessão não está na listagem
		int indiceDaLinha = 0;
		String numPauta = Integer.toString(numeroPauta);
		try {
			while (true) {
				indiceDaLinha++;
				WebElement elementoNumeroPauta = ElementosAcessoSessao
						.numeroSessaoPorIndice(_driver, indiceDaLinha);
				Assert.assertFalse("A sessão com número de pauta " + numPauta
						+ " foi encontrada na listagem de sessões.",
						elementoNumeroPauta.getText().equals(numPauta));
			}
		} catch (NoSuchElementException e) {
			/*	
			 * Exceção esperada ao não encontrar o número de pauta e chegar ao
			 * final da listagem
			 */
		}

		return this;
	}

	public PainelRelatorioSessao gerarRelatorioSessao(int numeroPauta) {
		// Localizar a sessão de teste na lista de sessões
		int indiceDaLinha = localizarSessaoPorNumeroPauta(numeroPauta);

		// Clicar no menu "Acompanhar sessão" e depois selecionar
		// "Relatório da Sessão"
		ElementosAcessoSessao.menuAcompanharSessao(_driver, indiceDaLinha)
				.click();
		ElementosAcessoSessao.opcaoGerarRelatorioSessao(_driver, indiceDaLinha)
				.click();

		PainelRelatorioSessao painel = new PainelRelatorioSessao(_driver);
		painel.validarPaginaCarregada();

		return painel;
	}

	public boolean verificarBotaoConfiguracoes() {
		try {
			ElementosAcessoSessao.botaoConfiguracoes(_driver).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
