package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosPainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosVotoCompleto;

public abstract class PainelAcompanhamento {
	enum CoresEstadoProcesso {
		APREGOADO("rgba(255, 0, 0, 1)"),
		NAO_JULGADO("rgba(115, 136, 10, 1)"),
		JULGADO("rgba(0, 0, 255, 1)"),
		REVISAR("rgba(210, 49, 224, 1)"),
		RETIRADO("rgba(128, 0, 128, 1)"),
		ADIADO("rgba(236, 92, 21, 1)"),
		VISTA_MESA("rgba(54, 57, 61, 1)"),
		VISTA_REGIMENTAL("rgba(128, 64, 0, 1)")
		;
		private String _codigoCor;
		
		private CoresEstadoProcesso(String codigoCor) {
			_codigoCor = codigoCor;
		}
		
		public String getCodigoCor() {
			return _codigoCor;
		}
		
		public static String getCodigoCor(EstadoProcesso estadoProcesso) {
			switch (estadoProcesso) {
			case ADIADO:
				return ADIADO._codigoCor;
			case APREGOADO:
				return APREGOADO._codigoCor;
			case JULGADO:
				return JULGADO._codigoCor;
			case NAO_JULGADO:
				return NAO_JULGADO._codigoCor;
			case RETIRADO:
				return RETIRADO._codigoCor;
			case REVISAR:
				return REVISAR._codigoCor;
			case VISTA_MESA:
				return VISTA_MESA._codigoCor;
			case VISTA_REGIMENTAL:
				return VISTA_REGIMENTAL._codigoCor;
			}
			return "<desconhecido>";
		}
	}
	
	public enum IconesProcesso {
		DESTAQUE,
		DIVERGENCIA_RECUSADA,
		DIVERGENCIA_ACOLHIDA,
		SUSTENTACAO_ORAL,
		PREFERENCIA,
		IMPEDIMENTO,
		SEM_REVISOR
	}
	
	protected WebDriver _driver;
	protected WebDriverWait _waitDriver;

	protected PainelAcompanhamento(WebDriver driver) {
		_driver = driver;
		_waitDriver = new WebDriverWait(_driver, 10);
	}

	protected void validarPaginaCarregada(int quantidadeProcessosSessao) {
		// Verificar que o título e o cabeçalho foram carregados corretamente
		_waitDriver.until(ExpectedConditions.titleIs("ASPJE"));
		_waitDriver.until(ExpectedConditions.textToBePresentInElementLocated(ElementosPainelAcompanhamento.localizadorTituloDoSistema(), "Acompanhamento de Sessão do PJ-e"));
		
		// Aguardar que todos os processos sejam carregados na lista de processos da sessão
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorLinhaDaListaDeProcessos(quantidadeProcessosSessao)));
	}

	/**
	 * Seleciona o processo desejado na lista sem aguardar pelo carregamento da lista.
	 * 
	 * @param numeroProcessoCnj Número do processo no CNJ a ser selecionado.
	 * 
	 * @return O PainelAcompanhamento após a seleção do processo.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PainelAcompanhamento selecionarProcesso(RoteiroPautaSessao processo) throws SQLException, ClassNotFoundException, IOException {
		// Procurar pelo processo na lista de processos
		int indiceDaLista = procurarProcessoNaLista(processo);
		
		// Pegar elemento da lista de processos pelo indice e clicar
		ElementosPainelAcompanhamento.numeroDoProcessoNaListaDeProcessos(_driver, indiceDaLista).click();

		// Aguardar até que o processo seja carregado (condição válida para todos os painéis de acompanhamento)
		String numeroProcesso = processo.getDS_CLASSE_JUDICIAL_SIGLA() + " " + processo.getNR_PROCESSO_CNJ();
		_waitDriver.until(ExpectedConditions.textToBePresentInElementLocated(ElementosPainelAcompanhamento.localizadorNumeroDoProcessoAtual(), numeroProcesso));

		return this;
	}

	protected int procurarProcessoNaLista(RoteiroPautaSessao processo) {
		String numeroProcesso = processo.getNR_SEQUENCIAL_PROCESSO().toString() + " " + processo.getDS_CLASSE_JUDICIAL_SIGLA() + " - " + processo.getNR_PROCESSO_CNJ();
		// Procurar o processo na lista de processos
		int indiceDaLista = 1;
		try {
			while (!ElementosPainelAcompanhamento.numeroDoProcessoNaListaDeProcessos(_driver, indiceDaLista).getText().equals(numeroProcesso)) {
				indiceDaLista++;
			}
		}
		catch(NoSuchElementException e) {
			Assert.fail("Não foi possível encontrar o processo '" + numeroProcesso + "' na lista de processos.");
		}
		return indiceDaLista;
	}

	public PainelAcompanhamento validarDetalhesSessao(SessaoJulgamento sessao) throws SQLException, ClassNotFoundException, IOException {
		// Buscar órgão julgador e data pelo número da sessão
		String orgaoJulgador = BDUtils.getInstance().buscarDescricaoOrgaoJulgador(sessao.getID_SESSAO_PJE());
		String dataSessao = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(sessao.getDT_SESSAO());
		
		// Aguardar até que o número da sessão esteja disponível
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorNumeroDaSessao()));
		
		// Validar que cabeçalho da listagem de processos indica o órgão julgador e o número da sessão corretos.
		Assert.assertEquals("Órgão julgador: " + orgaoJulgador, ElementosPainelAcompanhamento.orgaoJulgadorNaListaDeProcessos(_driver).getText());
		Assert.assertEquals("Sessão: " + sessao.getID_SESSAO_PJE() + " -", ElementosPainelAcompanhamento.numeroSessaoNaListaDeProcessos(_driver).getText());
		Assert.assertEquals(dataSessao, ElementosPainelAcompanhamento.dataSessaoNaListaDeProcessos(_driver).getText());
		
		return this;
	}
	
	public PainelAcompanhamento validarDetalhesSessaoAssistente(SessaoJulgamento sessao) throws SQLException, ClassNotFoundException, IOException {
		// Aguardar até que o botão de salvar sustentação oral esteja disponível
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoSalvarAlteracoesSustentacaoOral()));
		
		// Validar 
		Assert.assertEquals("Nenhuma inscrição cadastrada no sistema de Acompanhamento", ElementosPainelAcompanhamento.conteudoTabelaInscricaoOral(_driver).getText());
		return this;
	}	
	
	public PainelAcompanhamento validarSessaoOnline() {
		// Validar que o indicador de sessão online está presente
		Assert.assertTrue("Ícone indicativo de sessão online não está presente.", ElementosPainelAcompanhamento.iconeSessaoOnline(_driver).isDisplayed());
		
		return this;
	}
	
	public PainelAcompanhamento validarListaDeProcessos(final List<RoteiroPautaSessao> processos) throws SQLException, ClassNotFoundException, IOException {
		// Ordenar os processos da lista por número sequencial
		Collections.sort(processos, new Comparator<RoteiroPautaSessao>() {
			@Override
			public int compare(RoteiroPautaSessao p1, RoteiroPautaSessao p2) {
				return p1.getNR_SEQUENCIAL_PROCESSO().compareTo(p2.getNR_SEQUENCIAL_PROCESSO());
			}
		});
		
		// Aguardar até que a quantidade de processos na lista seja igual a quantidade esperada
		_waitDriver.until(new com.google.common.base.Predicate<WebDriver> () {
			@Override
			public boolean apply(WebDriver driver) {
				return ElementosPainelAcompanhamento.linhasListaDeProcessos(driver).size() == processos.size();
			}
		});
		
		// Validar cada um dos processos na listagem de processos é apresentada no painel do usuário
		int i;
		for (i=0; i<processos.size(); i++) {
			WebElement processo = ElementosPainelAcompanhamento.numeroDoProcessoNaListaDeProcessos(_driver, i+1);
			
			// Validar que o processo lido da listagem está na lista carregada do banco de dados
			RoteiroPautaSessao p = processos.get(i);
			String dsProcesso = p.getNR_SEQUENCIAL_PROCESSO().toString() + " " + p.getDS_CLASSE_JUDICIAL_SIGLA() + " - " + p.getNR_PROCESSO_CNJ();
			Assert.assertEquals(dsProcesso, processo.getText());
			
			// Validar que a cor da fonte do processo está de acordo com o estado do processo
			EstadoProcesso estadoProcesso = EstadoProcesso.valueOf(p.getDS_STATUS());
			Assert.assertEquals(CoresEstadoProcesso.getCodigoCor(estadoProcesso), processo.getCssValue("color"));
		}
		
		// Ao final não devem haver mais nenhum processo no painel que não tenha sido chegado
		try {
			ElementosPainelAcompanhamento.numeroDoProcessoNaListaDeProcessos(_driver, i+1);
			Assert.fail("Lista de processos no painel possui mais elementos que a lista de processos do banco de dados.");
		}
		catch (NoSuchElementException e) {}
		
		return this;
	}

	public PainelAcompanhamento validarListaDeProcessosVazia() {
		// Aguardar até que a quantidade de processos na lista seja igual a 1
		_waitDriver.until(new com.google.common.base.Predicate<WebDriver> () {
			@Override
			public boolean apply(WebDriver driver) {
				return ElementosPainelAcompanhamento.linhasListaDeProcessos(driver).size() == 1;
			}
		});
		
		Assert.assertEquals("Nenhum processo localizado.", ElementosPainelAcompanhamento.numeroDoProcessoNaListaDeProcessos(_driver, 1).getText());
		
		return this;
	}

	public PainelAcompanhamento validarQuadroResumo(List<RoteiroPautaSessao> processos) throws ClassNotFoundException, SQLException, IOException {
		// Buscar quantidades de cada tipo de processo usando o número da sessão
		int quantidadeNaoJulgado = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.NAO_JULGADO));
		int quantidadeJulgado = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.JULGADO));
		int quantidadeApregoado = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.APREGOADO));
		int quantidadeRevisar = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.REVISAR));
		int quantidadeRetirado = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.RETIRADO));
		int quantidadeAdiado = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.ADIADO));
		int quantidadeVistaMesa = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.VISTA_MESA));
		int quantidadeVistaRegimental = CollectionUtils.countMatches(processos, new EstadoProcessoPredicate(EstadoProcesso.VISTA_REGIMENTAL));
		
		// Buscar elementos contentdo as quantidades de cada tipo de processo
		WebElement apregoados = ElementosPainelAcompanhamento.numeroProcessosApregoadosQuadroResumo(_driver);
		WebElement nao_julgados = ElementosPainelAcompanhamento.numeroProcessosNaoJulgadosQuadroResumo(_driver);
		WebElement julgados = ElementosPainelAcompanhamento.numeroProcessosJulgadosQuadroResumo(_driver);
		WebElement revisar = ElementosPainelAcompanhamento.numeroProcessosRevisarQuadroResumo(_driver);
		WebElement retirados = ElementosPainelAcompanhamento.numeroProcessosRetiradosQuadroResumo(_driver);
		WebElement adiados = ElementosPainelAcompanhamento.numeroProcessosAdiadosQuadroResumo(_driver);
		WebElement vista_mesa = ElementosPainelAcompanhamento.numeroProcessosVistaMesaQuadroResumo(_driver);
		WebElement vista_regimental = ElementosPainelAcompanhamento.numeroProcessosVistaRegimentalQuadroResumo(_driver);
		
		// Verificar que as quantidade de cada tipo de processo estão corretas
		Assert.assertEquals("Em julgamento (" + quantidadeApregoado + ") |", apregoados.getText());
		Assert.assertEquals("Não julgado (" + quantidadeNaoJulgado + ") |", nao_julgados.getText());
		Assert.assertEquals("Julgado (" + quantidadeJulgado + ")", julgados.getText());
		Assert.assertEquals("Julgado/Revisar (" + quantidadeRevisar + ") |", revisar.getText());
		Assert.assertEquals("Retirado de pauta (" + quantidadeRetirado + ")", retirados.getText());
		Assert.assertEquals("Adiado (" + quantidadeAdiado + ") |", adiados.getText());
		Assert.assertEquals("Vista mesa (" + quantidadeVistaMesa + ") |", vista_mesa.getText());
		Assert.assertEquals("Vista regimental (" + quantidadeVistaRegimental + ")", vista_regimental.getText());
		
		// Verificar que as cores de cada tipo de processo estão corretas
		Assert.assertEquals(CoresEstadoProcesso.APREGOADO.getCodigoCor(), apregoados.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.NAO_JULGADO.getCodigoCor(), nao_julgados.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.JULGADO.getCodigoCor(), julgados.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.REVISAR.getCodigoCor(), revisar.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.RETIRADO.getCodigoCor(), retirados.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.ADIADO.getCodigoCor(), adiados.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.VISTA_MESA.getCodigoCor(), vista_mesa.getCssValue("color"));
		Assert.assertEquals(CoresEstadoProcesso.VISTA_REGIMENTAL.getCodigoCor(), vista_regimental.getCssValue("color"));
		
		return this;
	}

	public PainelAcompanhamento validarIconesListaDeProcessos(
			List<RoteiroPautaSessao> processos) {
		
		// Verificar cada um dos processos da lista
		for(RoteiroPautaSessao processo: processos) {
			// Encontra indice do processo na lista e pegar linha para verificações
			int indiceDaLinha = procurarProcessoNaLista(processo);
			WebElement linha = ElementosPainelAcompanhamento.linhaDaListaDeProcessos(_driver, indiceDaLinha);
			
			// Verificar ícones do processo
			try {
				ElementosPainelAcompanhamento.iconeDestaqueProcesso(linha, indiceDaLinha);
				Assert.assertEquals(
						"Ícone de destaque está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo NÂO é igual a 'Q'",
						new Character('Q'), processo.getIN_DESTAQUE());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(
						"Ícone de destaque NÂO está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo é igual a 'Q'",
						new Character('Q'), processo.getIN_DESTAQUE());
			}
			try {
				ElementosPainelAcompanhamento.iconeDivergenciaRecusadaProcesso(linha, indiceDaLinha);
				Assert.assertEquals(new Character('A'), processo.getIN_DIVERGENCIA());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(new Character('A'), processo.getIN_DIVERGENCIA());
			}
			try {
				ElementosPainelAcompanhamento.iconeDivergenciaParcialmenteAcolhidaProcesso(linha, indiceDaLinha);
				Assert.assertEquals(new Character('B'), processo.getIN_DIVERGENCIA());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(new Character('B'), processo.getIN_DIVERGENCIA());
			}
			try {
				ElementosPainelAcompanhamento.iconeDivergenciaEmAnaliseProcesso(linha, indiceDaLinha);
				Assert.assertEquals(new Character('C'), processo.getIN_DIVERGENCIA());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(new Character('C'), processo.getIN_DIVERGENCIA());
			}
			try {
				ElementosPainelAcompanhamento.iconeDivergenciaAcolhidaProcesso(linha, indiceDaLinha);
				Assert.assertEquals(new Character('D'), processo.getIN_DIVERGENCIA());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(new Character('D'), processo.getIN_DIVERGENCIA());
			}
			try {
				ElementosPainelAcompanhamento.iconeSustentacaoOralProcesso(linha, indiceDaLinha);
				Assert.assertEquals(
						"Ícone de sustentação oral está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo NÂO é igual a 'S'",
						new Character('S'), processo.getIN_SUSTENTACAO_ORAL());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(
						"Ícone de sustentação oral NÂO está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo é igual a 'S'",
						new Character('S'), processo.getIN_SUSTENTACAO_ORAL());
			}
			try {
				ElementosPainelAcompanhamento.iconePreferenciaProcesso(linha, indiceDaLinha);
				Assert.assertEquals(
						"Ícone de preferência está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo NÂO é igual a 'S'",
						new Character('S'), processo.getIN_PREFERENCIA());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(
						"Ícone de preferência NÂO está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo é igual a 'S'",
						new Character('S'), processo.getIN_PREFERENCIA());
			}
			try {
				ElementosPainelAcompanhamento.iconeImpedimentoProcesso(linha, indiceDaLinha);
				Assert.assertEquals(
						"Ícone de impedimento está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo NÂO é igual a 'S'",
						new Character('S'), processo.getIN_IMPEDIMENTO());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(
						"Ícone de impedimento NÂO está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo é igual a 'S'",
						new Character('S'), processo.getIN_IMPEDIMENTO());
			}
			try {
				ElementosPainelAcompanhamento.iconeSemRevisorProcesso(linha, indiceDaLinha);
				Assert.assertEquals(
						"Ícone de ausência de revisor está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo NÂO é igual a 'N'",
						new Character('N'), processo.getIN_REVISAO());
			} catch (NoSuchElementException e) {
				Assert.assertNotEquals(
						"Ícone de ausência de revisor NÂO está presente para o processo " + processo.getNR_PROCESSO_CNJ() + "(" + indiceDaLinha + ") porém o valor do campo é igual a 'N'",
						new Character('N'), processo.getIN_REVISAO());
			}
		}
		
		return this;
	}

	/**
	 * Verificar se o processo desejado está selecionado corretamento na lista e se
	 * os detalhes do processo estão sendo mostrados corretamente (resumo, dispositivo
	 * e botões de ação).
	 * 
	 * @param processo Os dados do processo que deve estar selecionado.
	 * 
	 * @return A referência para a tela atual (this).
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public PainelAcompanhamento validarProcessoSelecionado(RoteiroPautaSessao processo) throws SQLException, ClassNotFoundException, IOException {
		// Pegar processo da lista de processos pelo indice
		int indiceDaLista = procurarProcessoNaLista(processo);
		
		// Verificar que o processo foi selecionado (fundo de tela na linha da lista de processos ficou amarelo)
		Assert.assertEquals("rgba(255, 255, 0, 1)", ElementosPainelAcompanhamento.linhaDaListaDeProcessos(_driver, indiceDaLista).getCssValue("background-color"));
		
		return validarDetalhesProcessoSelecionado(processo);
	}

	/**
	 * Verificar se os dados do processo selecionado estão sendo mostrados corretamente.
	 * Cada tipo de painel (Secretário, Assistente, etc) mostra os dados de forma diferente.
	 * 
	 * @param processo Os dados do processo que deve estar sendo mostrado.
	 * 
	 * @return A referência para a tela atual (this).
	 */
	public abstract PainelAcompanhamento validarDetalhesProcessoSelecionado(RoteiroPautaSessao processo);

	public PainelAcompanhamento validarResumoProcesso(RoteiroPautaSessao processo) {
		// Organizar valores do processo a serem verificados
		String numeroProcesso = processo.getDS_CLASSE_JUDICIAL_SIGLA() + " " + processo.getNR_PROCESSO_CNJ();
		String orgaoJulgadorPrimeiroGrau = processo.getDS_ORGAO_JULGADOR_1GRAU() != null ? processo.getDS_ORGAO_JULGADOR_1GRAU() : "";
		String relator = processo.getNM_RELATOR() != null ? processo.getNM_RELATOR() : "";
		String revisor = processo.getNM_REVISOR() != null ? processo.getNM_REVISOR() : "";
		String terceiro = processo.getNM_TERCEIRO();
		String sustentacaoOral = processo.getDS_ADV_SUSTENTACAO_ORAL();
		String poloAtivo = processo.getDS_POLO_ATIVO() != null ? processo.getDS_POLO_ATIVO() : "";
		String poloPassivo = processo.getDS_POLO_PASSIVO() != null ? processo.getDS_POLO_PASSIVO() : "";

		Assert.assertEquals(numeroProcesso, ElementosPainelAcompanhamento.numeroDoProcessoAtual(_driver).getText());
		Assert.assertEquals(orgaoJulgadorPrimeiroGrau, ElementosPainelAcompanhamento.orgaoJulgadorPrimeiroGrau(_driver).getText());
		Assert.assertEquals(relator, ElementosPainelAcompanhamento.relatorDoProcessoAtual(_driver).getText());
		Assert.assertEquals(revisor, ElementosPainelAcompanhamento.revisorDoProcessoAtual(_driver).getText());
		try {
			Assert.assertEquals(terceiro, ElementosPainelAcompanhamento.terceiroVotoDoProcessoAtual(_driver).getText());
		}
		catch (NoSuchElementException e) {
			// Se o elemento não foi encotrado, então o terceiro deve estar nulo.
			Assert.assertNull(terceiro);
		}
		try {
			Assert.assertEquals(sustentacaoOral, ElementosPainelAcompanhamento.sustentacaoOralDoProcessoAtual(_driver).getText());
		}
		catch (NoSuchElementException e) {
			// Se o elemento não foi encontrado, então o processo não deve ter sustentação oral.
			Assert.assertNull(sustentacaoOral);
		}
		Assert.assertEquals(poloAtivo, ElementosPainelAcompanhamento.poloAtivoDoProcessoAtual(_driver).getText());
		Assert.assertEquals(poloPassivo, ElementosPainelAcompanhamento.poloPassivoDoProcessoAtual(_driver).getText());
		
		return this;
	}

	public PainelAcompanhamento mudarDispositivo(RoteiroPautaSessao processo) {
		// Altera o dispositivo através do editor no painel
		WebElement editor = ElementosPainelAcompanhamento.dispositivoDoProcessoAtual(_driver);
		if (editor.getText().length() > 0) {
			editor.clear();
		}
		editor.sendKeys(processo.getDS_DISPOSITIVO());
		
		return this;
	}

	public PainelAcompanhamento validarDispositivo(RoteiroPautaSessao processo, boolean editorPresente) {
		if (editorPresente) {
			String dispositivo = StringEscapeUtils.unescapeHtml4(processo.getDS_DISPOSITIVO());
			Assert.assertEquals(dispositivo, ElementosPainelAcompanhamento.dispositivoDoProcessoAtual(_driver).getText());
		}
		else {
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorEditorDispositivo()));
		}
		
		return this;
	}

	public PainelAcompanhamento validarDispositivoEditavel(boolean editavel) {
		if (editavel) {
			Assert.assertEquals("O dispositivo do processo deveria ser editável.", "true", ElementosPainelAcompanhamento.dispositivoDoProcessoAtual(_driver).getAttribute("contenteditable"));
		}
		else {
			Assert.assertEquals("O dispositivo do processo não deveria ser editável.", "false", ElementosPainelAcompanhamento.dispositivoDoProcessoAtual(_driver).getAttribute("contenteditable"));
		}
		
		return this;
	}
	
	/**
	 * Valida se os botões de ação sobre o processo estão corretos (Adiar, Julgado, Apregoar, etc).
	 * 
	 * @param processo O RoteiroPautaSessao do processo selecionado atualmente.
	 * @param botoesPresentes True, se os botões devem estar presentes. False, se os botões não devem aparecer.
	 * @return
	 */
	public PainelAcompanhamento validarBotoesDeAcaoNoProcesso(RoteiroPautaSessao processo, boolean botoesPresentes) {
		WebElement remover = null, apregoar = null, julgado = null, adiar = null, retirar = null, revisar = null,
				vistaMesa = null, vistaRegimental = null;

		if (botoesPresentes) {
			// Verificar se todos os botões estão presentes
			remover = ElementosPainelAcompanhamento.botaoRemover(_driver);
			apregoar = ElementosPainelAcompanhamento.botaoApregoar(_driver);
			julgado = ElementosPainelAcompanhamento.botaoJulgado(_driver);
			adiar = ElementosPainelAcompanhamento.botaoAdiar(_driver);
			retirar = ElementosPainelAcompanhamento.botaoRetirar(_driver);
			revisar = ElementosPainelAcompanhamento.botaoRevisar(_driver);
			vistaMesa = ElementosPainelAcompanhamento.botaoVistaMesa(_driver);
			vistaRegimental = ElementosPainelAcompanhamento.botaoVistaRegimental(_driver);
			
			// Verificar que os rótulos dos botões estão corretos
			Assert.assertEquals("REMOVER", remover.getText());
			Assert.assertEquals("APREGOAR", apregoar.getText());
			Assert.assertEquals("JULGADO", julgado.getText());
			Assert.assertEquals("ADIAR", adiar.getText());
			Assert.assertEquals("RETIRAR", retirar.getText());
			Assert.assertEquals("REVISAR", revisar.getText());
			Assert.assertEquals("VISTA EM MESA", vistaMesa.getText());
			Assert.assertEquals("VISTA REGIMENTAL", vistaRegimental.getText());
			
			// Verificar se os estados dos botões estão corretos
			EstadoProcesso estadoProcesso = EstadoProcesso.valueOf(processo.getDS_STATUS());
			Assert.assertEquals(estadoProcesso==EstadoProcesso.NAO_JULGADO ? "true" : null, remover.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.APREGOADO ? "true" : null, apregoar.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.JULGADO ? "true" : null, julgado.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.ADIADO ? "true" : null, adiar.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.RETIRADO ? "true" : null, retirar.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.REVISAR ? "true" : null, revisar.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.VISTA_MESA ? "true" : null, vistaMesa.getAttribute("disabled"));
			Assert.assertEquals(estadoProcesso==EstadoProcesso.VISTA_REGIMENTAL ? "true" : null, vistaRegimental.getAttribute("disabled"));
		}
		else {
			// Verificar se todos os botões não estão presentes
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoRemover()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoApregoar()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoJulgado()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoAdiar()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoRetirar()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoRevisar()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoVistaMesa()));
			_waitDriver.until(ExpectedConditions.invisibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorBotaoVistaRegimental()));
		}
		
		return this;
	}
	
	public PainelAcompanhamento validarMensagemProcesso(RoteiroPautaSessao processoAlterado, boolean aguardarSumir) {
		return validarMensagemProcesso(processoAlterado, aguardarSumir, "");
	}
	
	public PainelAcompanhamento validarMensagemProcesso(RoteiroPautaSessao processoAlterado, boolean aguardarSumir, String mensagemAdicional) {
		EstadoProcesso estadoProcesso = EstadoProcesso.valueOf(processoAlterado.getDS_STATUS());
		
		// Aguardar que mensagens de atualização apareçam na tela
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorMensagensDeAtualização()));
		
		// Verificar entre as mensagens na tela se o processo foi atualizado para o estado esperado
		for (WebElement mensagem: ElementosPainelAcompanhamento.mensagensDeAtualização(_driver)) {
			String textoMensagem = mensagem.getText();
			if (textoMensagem.contains(processoAlterado.getNR_PROCESSO_CNJ())) {
				Assert.assertEquals("Processo " + processoAlterado.getNR_PROCESSO_CNJ() + " " + estadoProcesso.toString() + mensagemAdicional + ".", textoMensagem);

				if (aguardarSumir) _waitDriver.until(ExpectedConditions.stalenessOf(mensagem));
				
				return this;
			}
		}
		
		Assert.fail("Mensagem de atualização do estado do processo " + processoAlterado.getNR_PROCESSO_CNJ() + " (" + estadoProcesso + ") não foi recebida.");
		
		return this;
	}
	
	public PainelAcompanhamento validarMensagemProcessoGenerica(boolean aguardarSumir, String mensagemEsperada) {
		// Aguardar que mensagens de atualização apareçam na tela
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorMensagensDeAtualização()));
		
		//Verificar se o texto da mensagem é compatível com o texto esperado
		for (WebElement mensagem: ElementosPainelAcompanhamento.mensagensDeAtualização(_driver)) {
			String textoMensagem = mensagem.getText();
			if (textoMensagem.contains(mensagemEsperada)) {
				Assert.assertEquals(mensagemEsperada, textoMensagem);
				if (aguardarSumir) _waitDriver.until(ExpectedConditions.stalenessOf(mensagem));
				return this;
			}
		}
		Assert.fail("Mensagem esperada: " + mensagemEsperada + " não foi verificada no popup.");
		
		return this;
	}
	
	public void aguardarIconeProcesso(RoteiroPautaSessao processo, IconesProcesso icone) {
		// Aguarda na linha do processo pelo ícone desejado
		By localizador = null;
		switch (icone) {
		case DESTAQUE:
			localizador = ElementosPainelAcompanhamento.localizadorIconeDestaqueProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case DIVERGENCIA_ACOLHIDA:
			localizador = ElementosPainelAcompanhamento.localizadorIconeDivergenciaAcolhidaProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case DIVERGENCIA_RECUSADA:
			localizador = ElementosPainelAcompanhamento.localizadorIconeDivergenciaRecusadaProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case IMPEDIMENTO:
			localizador = ElementosPainelAcompanhamento.localizadorIconeImpedimentoProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case PREFERENCIA:
			localizador = ElementosPainelAcompanhamento.localizadorIconePreferenciaProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case SEM_REVISOR:
			localizador = ElementosPainelAcompanhamento.localizadorIconeSemRevisorProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
			
		case SUSTENTACAO_ORAL:
			localizador = ElementosPainelAcompanhamento.localizadorIconeSustentacaoOralProcesso(processo.getNR_SEQUENCIAL_PROCESSO());
			break;
		}
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(localizador));
	}
	
	public PainelAcompanhamento visualizarVotoCompleto() {
		// Clicar no botão para visualizar o voto completo do processo atual
		ElementosPainelAcompanhamento.botaoVotoCompleto(_driver).click();
		
		return this;
	}
	
	public boolean verificarBotãoVotoCompleto(){
		//Verifica presença da opção de Visualizar o Voto Completo na página
		try{
			if(ElementosPainelAcompanhamento.botaoVotoCompleto(_driver).isDisplayed()){
				return true;
			}
		}catch(NoSuchElementException e){
			return false;
			
		}
		{
			
		}
		return false;
	}
	
	class EstadoProcessoPredicate implements Predicate {

		private EstadoProcesso _estadoProcesso;

		EstadoProcessoPredicate(EstadoProcesso e) {
			_estadoProcesso = e;
		}
		
		@Override
		public boolean evaluate(Object o) {
			if (o instanceof RoteiroPautaSessao) {
				EstadoProcesso e = EstadoProcesso.valueOf(((RoteiroPautaSessao) o).getDS_STATUS());
				return e.equals(_estadoProcesso);
			}
			return false;
		}
		
	}

	public PainelAcompanhamento validarVotoCompletoNaoDisponivel() {
		try {
			ElementosPainelAcompanhamento.botaoVotoCompleto(_driver).isDisplayed();
			Assert.fail("Botão para visualizar voto completo está disponível.");
		}
		catch (NoSuchElementException e) {
			// Exceção esperada para confirmar que o botão não está disponível
		}
		
		return this;
	}

	public void validarVotoCompleto(RoteiroPautaSessao primeiroProcesso) {
		// Aguardar até que a janela de voto completo seja aberta
		_waitDriver.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				return _driver.getWindowHandles().size() == 2;
			}
		});
		
		// Aguardar até que o voto completo seja mostrado
		String janelaOriginal = _driver.getWindowHandle();
		Set<String> handles = _driver.getWindowHandles();
		for (String janela : handles) {
			// Verifica se o voto completo está presente dentro da janela
			try {
				if (!janela.equals(janelaOriginal)) {
					_driver.switchTo().window(janela);
					_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(ElementosVotoCompleto.localizadorConteudoVotoCompleto()));
				}
			}
			catch (NoSuchElementException e) {
				// Caso a janela não possua o voto completo, continua procurando
			}
		}
		
		// Pegar texto do voto completo do banco
		Source htmlSource = new Source(primeiroProcesso.getDS_ACORDAO_COMPLETO());
	    Segment htmlSeg = new Segment(htmlSource, 0, htmlSource.length());
	    Renderer htmlRend = new Renderer(htmlSeg);
		
		// Compara o conteúdo do frame correspondente ao voto completo com o conteudo no banco
	    // Note: ignorando espaços em branco para evitar problemas na conversão dos textos
		Assert.assertEquals(
			htmlRend.toString().replaceAll("\\s+", ""),
			_driver.findElement(By.tagName("body")).getText().replaceAll("\\s+", ""));
		
		// Retorna o WebDriver para a janela original
		_driver.switchTo().window(janelaOriginal);
		
	}

	public PainelAcompanhamento mudarAtualizacaoAutomatica() {
		// Clica no checkbox para alterar o seu estado
		ElementosPainelAcompanhamento.checkboxAtualizacaoAutomatica(_driver).click();
		
		return this;
	}
	
	public PainelAcompanhamento validarAtualizacaoAutomatica(boolean habilitado) {
		// Verifica que o estado do checkbox (escondido) está de acordo com o parâmetro
		if (habilitado)
			Assert.assertTrue(ElementosPainelAcompanhamento.checkboxAtualizacaoAutomatica(_driver).getAttribute("class").contains("ui-state-active"));
		else
			Assert.assertFalse(ElementosPainelAcompanhamento.checkboxAtualizacaoAutomatica(_driver).getAttribute("class").contains("ui-state-active"));
		
		return this;
	}
	
	
	public PainelAcompanhamento localizarProcesso(String numeroProcesso) {
		ElementosPainelAcompanhamento.localizarProcesso(_driver).sendKeys(numeroProcesso);
		
		return this;
	}

	public PainelAcompanhamento limparLocalizarProcesso() {
		WebElement filtroProcessos = ElementosPainelAcompanhamento.localizarProcesso(_driver);
		filtroProcessos.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		filtroProcessos.sendKeys(Keys.DELETE);
		
		return this;
	}

	public PainelSessoes sairSessao() {
		ElementosPainelAcompanhamento.fecharSessao(_driver).click();
		
		PainelSessoes painel = new PainelSessoes(_driver);
		painel.validarPaginaCarregada();
		
		return painel;
	}

}
