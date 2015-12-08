package br.jus.trt9.acompspje.selenium.telas.elementos;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementosPainelAcompanhamento {

	private ElementosPainelAcompanhamento() {}

	public static By localizadorTituloDoSistema() {
		return By.id("formProcessos:titulo");
	}

	public static By localizadorEditorDispositivo() {
		return By.xpath(".//*[@id='cke_formProcessos:ckEditor']//iframe");
	}

	/**
	 * Busca pelo rótulo no cabeçalho da tela contendo o título do sistema.
	 * 
	 * @param contexto SearchContext no qual o elemento deve ser buscado.
	 * 
	 * @return WebElement que representa o título do sistema no cabeçalho da tela.
	 */
	public static WebElement tituloDoSistema(SearchContext contexto) {
		return localizadorTituloDoSistema().findElement(contexto);
	}

	/**
	 * Busca pelo órgão julgador no topo da lista de processos.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o órgão julgador.
	 */
	public static WebElement orgaoJulgadorNaListaDeProcessos(SearchContext contexto) {
		return By.xpath(".//*[@id='formProcessos:lista_processos:orgaoJulgador']").findElement(contexto);
	}

	/**
	 * Busca pelo número da sessão no topo da lista de processos.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o número da sessão.
	 */
	public static WebElement numeroSessaoNaListaDeProcessos(SearchContext contexto) {
		return localizadorNumeroDaSessao().findElement(contexto);
	}

	public static By localizadorNumeroDaSessao() {
		return By.xpath(".//*[@id='formProcessos:lista_processos:sessao']");
	}

	/**
	 * Busca pela data/hora da sessão no topo da lista de processos.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa a data/hora da sessão.
	 */
	public static WebElement dataSessaoNaListaDeProcessos(SearchContext contexto) {
		return By.xpath(".//*[@id='formProcessos:lista_processos:dataSessao']").findElement(contexto);
	}
	
	/**
	 * Busca pela número do processo indicado pelo índice na lista de processos.
	 *  
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceLista Índice da linha desejada na lista de processos (primeira linha possui índice 1).
	 * 
	 * @return WebElement que representa o número do processo na linha desejada da lista de processos.
	 */
	public static WebElement numeroDoProcessoNaListaDeProcessos(SearchContext contexto, int indiceLista) {
		return By.xpath(".//*[@id='formProcessos:lista_processos_data']/tr[" + indiceLista + "]/td[1]").findElement(contexto);
	}

	public static By localizadorLinhaDaListaDeProcessos(int indiceLista) {
		return By.xpath(".//*[@id='formProcessos:lista_processos_data']/tr[" + indiceLista + "]");
	}

	/**
	 * Busca pela linha dentro da lista de processos indicada pelo índice desejado.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceLista O índice da linha desejada na lista de processos (primeira linha possui índice 1).
	 * 
	 * @return WebElement que representa a linha desejada na lista de processos.
	 */
	public static WebElement linhaDaListaDeProcessos(SearchContext contexto, int indiceLista) {
		return localizadorLinhaDaListaDeProcessos(indiceLista).findElement(contexto);
	}

	public static List<WebElement> linhasListaDeProcessos(SearchContext contexto) {
		return By.xpath(".//*[@id='formProcessos:lista_processos_data']/tr").findElements(contexto);
	}

	/**
	 * Busca pelo campo indicando a quantidade de processos apregoados no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos apregoados.
	 */
	public static WebElement numeroProcessosApregoadosQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:apregoado").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos não julgados no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos não julgados.
	 */
	public static WebElement numeroProcessosNaoJulgadosQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:nao_julgado").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos julgados no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos julgados.
	 */
	public static WebElement numeroProcessosJulgadosQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:julgado").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos marcados para revisão no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos a revisar.
	 */
	public static WebElement numeroProcessosRevisarQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:revisar").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos retirados no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos retirados.
	 */
	public static WebElement numeroProcessosRetiradosQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:retirado").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos adiados no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos adiados.
	 */
	public static WebElement numeroProcessosAdiadosQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:adiado").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos marcados como vista de mesa no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos em vista de mesa.
	 */
	public static WebElement numeroProcessosVistaMesaQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:vista_mesa").findElement(contexto);
	}
	
	/**
	 * Busca pelo campo indicando a quantidade de processos marcados como vista regimental no quadro-resumo da sessão.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * 
	 * @return WebElement que representa o campo indicando a quantidade de processos em vista regimental.
	 */
	public static WebElement numeroProcessosVistaRegimentalQuadroResumo(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:vista_regimental").findElement(contexto);
	}

	/**
	 * Busca pelo ícone de destaque no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de destaque do processo.
	 */
	public static WebElement iconeDestaqueProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeDestaqueProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeDestaqueProcesso(int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_destaque");
	}

	/**
	 * Busca pelo ícone de divergência recusada no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de divergência recusada do processo.
	 */
	public static WebElement iconeDivergenciaRecusadaProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeDivergenciaRecusadaProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeDivergenciaRecusadaProcesso(
			int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_divergencia");
	}

	/**
	 * Busca pelo ícone de divergência parcialmente acolhida no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de divergência parcialmente acolhida do processo.
	 */
	public static WebElement iconeDivergenciaParcialmenteAcolhidaProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeDivergenciaParcialmenteAcolhidaProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeDivergenciaParcialmenteAcolhidaProcesso(
			int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_divergenciaPA");
	}

	/**
	 * Busca pelo ícone de divergência em análise no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de divergência parcialmente acolhida do processo.
	 */
	public static WebElement iconeDivergenciaEmAnaliseProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeDivergenciaEmAnaliseProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeDivergenciaEmAnaliseProcesso(
			int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_divergenciaEA");
	}
	
	/**
	 * Busca pelo ícone de divergência acolhida no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de divergência acolhida do processo.
	 */
	public static WebElement iconeDivergenciaAcolhidaProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeDivergenciaAcolhidaProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeDivergenciaAcolhidaProcesso(
			int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_divergenciaPR");
	}

	/**
	 * Busca pelo ícone de sustentação oral no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de sustentação oral do processo.
	 */
	public static WebElement iconeSustentacaoOralProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeSustentacaoOralProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeSustentacaoOralProcesso(int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_sustentacao");
	}

	/**
	 * Busca pelo ícone de preferência no processo localizado na linha dada.
	 * 
	 * @param contexto O contexto a partir do qual será feita a busca pelo elemento.
	 * @param indiceDaLinha O índice da linha onde o processo está localizado.
	 * 
	 * @return WebElement que representa o ícone de destaque do preferência.
	 */
	public static WebElement iconePreferenciaProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconePreferenciaProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconePreferenciaProcesso(int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_preferencia");
	}

	public static WebElement iconeImpedimentoProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeImpedimentoProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeImpedimentoProcesso(int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_impedimento");
	}

	public static WebElement iconeSemRevisorProcesso(SearchContext contexto, int indiceDaLinha) {
		return localizadorIconeSemRevisorProcesso(indiceDaLinha).findElement(contexto);
	}

	public static By localizadorIconeSemRevisorProcesso(int indiceDaLinha) {
		return By.id("formProcessos:lista_processos:" + (indiceDaLinha-1) + ":icone_sem_revisor");
	}

	public static By localizadorNumeroDoProcessoAtual() {
		return By.id("formProcessos:classeENumeroCnj");
	}
	
	/**
	 * Busca pelo número do processo no CNJ do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o número do processo selecionado no momento.
	 */
	public static WebElement numeroDoProcessoAtual(SearchContext contexto) {
		return localizadorNumeroDoProcessoAtual().findElement(contexto);
	}
	
	/**
	 * Busca pelo órgão julgador no primeiro grau do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o órgão julgador no primeiro grau do processo
	 * selecionado no momento.
	 */
	public static WebElement orgaoJulgadorPrimeiroGrau(SearchContext contexto) {
		return By.id("formProcessos:orgaoJulgadorPrimeiroGrau").findElement(contexto);
	}

	/**
	 * Busca pelo relator do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o relator do processo selecionado no momento.
	 */
	public static WebElement relatorDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:relator").findElement(contexto);
	}

	/**
	 * Busca pelo revisor do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o revisor do processo selecionado no momento.
	 */
	public static WebElement revisorDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:revisor").findElement(contexto);
	}

	/**
	 * Busca pelo terceiro voto do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o terceiro voto do processo selecionado no momento.
	 */
	public static WebElement terceiroVotoDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:terceiro").findElement(contexto);
	}

	/**
	 * Busca pelo campo de sustentação oral do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa a sustentação oral do processo selecionado no momento.
	 */
	public static WebElement sustentacaoOralDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:sustentacaoOral").findElement(contexto);
	}
	
	/**
	 * Trata os vários campos do diálogo de SUSTENTAÇÃO ORAL que são 
	 * específicos do Painel do assistente
	 * 
	 * Inicio ElementosPainelAcompanhamentoAssistente:
	 */	
	
	public static By localizarDialogoCadastrarSustentacao() {
		return By.id("formProcessos:pnlDialogCadastrarInscricao");
	}
	
	public static WebElement inputAdvogadoSustentaOral(SearchContext contexto){
		return By.id("formProcessos:acAdvogadoDlg_input").findElement(contexto);
	}
	
	public static WebElement conteudoTabelaInscricaoOral(SearchContext contexto){
		return By.id("formProcessos:dtInscricoesSustentacaoOral_data").findElement(contexto);
	}

	public static By localizadorConteudoTabelaSustentacaoOral() {
		return By.id("formProcessos:dtInscricoesSustentacaoOral_data");
	}
	
	public static WebElement selecionaSexoAdvogadoSustentaOral(SearchContext contexto){
		return By.id("formProcessos:sctSexoAdvogadoDlg").findElement(contexto);
	}
	//Botao Cadastrar sustentacao oral e seu localizador
	public static WebElement botaoCadastrarInscricaoSustentaOral(SearchContext contexto){
		return By.id("formProcessos:cadastrarInscricao").findElement(contexto);
	}
	public static By localizadorBotaoCadastrarSustentacaoOral() {
		return By.id("formProcessos:cadastrarInscricao");
	}	
	
	public static void selecionaParteSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:acParteDlg",contexto);
	}
	
	public static void selecionaTipoSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:sctTipoInscriçãoDlg",contexto);
	}
	
	//Input de posicao da sustentacao oral e seu localizador
	public static WebElement inputPosicaoSustentaOral(SearchContext contexto){
		return By.id("formProcessos:itPosicaoDlg").findElement(contexto);
	}
	public static By localizadorInputPosicaoSustentacaoOral() {
		return By.id("formProcessos:itPosicaoDlg");
	}	
	

	
	public static void selecionaProcuracaoSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:sctProcuracaoDlg",contexto);
	}
	
	
	public static void selecionaSubestabelecimentoSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:sctSubstabelecimentoDlg",contexto);
	}
	
	public static WebElement botaoSalvarSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:btnConfirmacaoCadastro").findElement(contexto);
	}
	
	public static By localizarBotaoSalvarSustentacaoOral() {
		return By.id("formProcessos:btnConfirmacaoCadastro");
	}	

	//Botao cancela cadastro de inscrição para sustentação oral
	public static WebElement botaoCancelarCadastroSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:btnCancelaCadastro").findElement(contexto);
	}
	public static By localizarBotaoCancelarCadastroSustentacaoOral() {
		return By.id("formProcessos:btnCancelaCadastro");
	}
	
	public static By localizarMsgCadastrarSustentacaoOral() {
		return By.id("formProcessos:msgCadastrarInscricao");
	}

	public static WebElement msgCadastrarSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:msgCadastrarInscricao").findElement(contexto);
	}
	
	//Botão de confirmar exclusão da sustentação oral
	public static WebElement botaoConfirmaExcluirSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:btnExcluirInscricaoSustentacaoOral").findElement(contexto);
	}
	public static By localizadorBotaoConfirmaExclusaoSustentacaoOral() {
		return By.id("formProcessos:btnExcluirInscricaoSustentacaoOral");
	}
	
	//Botão cancela exclusão da sustentação oral
	public static WebElement botaoCancelaExcluirSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:btnCancelaExclusãoInscricaoSustentacaoOral").findElement(contexto);
	}
	public static By localizadorBotaoCancelaExclusaoSustentacaoOral() {
		return By.id("formProcessos:btnCancelaExclusãoInscricaoSustentacaoOral");
	}
	
	public static By localizadorPainelConfirmaExclusaoSustentacaoOral() {
		return By.id("formProcessos:pnlgrdExcluirInscricaoSustentacaoOral");
	}
	
	//Botão excluir Sustentação Oral
	public static WebElement botaoExcluirSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:excluirInscricao").findElement(contexto);
	}
	public static By localizadorBotaoExcluirSustentacaoOral() {
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:excluirInscricao");
	}
	
	//Select presença sustentação oral
	public static void selecionaPresencaSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:dtInscricoesSustentacaoOral:0:sctPresencaSustentacaoOral",contexto);
	}
	public static By localizadorSelectPresencaSustentacaoOral() {
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:sctPresencaSustentacaoOral_label");
	}
	
	//Select sustentou/declinou sustentação oral
	public static void selecionaDeclinaSustentacaoOral(SearchContext contexto){
		selectOne("formProcessos:dtInscricoesSustentacaoOral:0:sctSustentacaoOral",contexto);
	}
	public static By localizadorSelectDeclinaSustentacaoOral() {
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:sctSustentacaoOral_label");
	}	
	
	//Botão altera Sustentação Oral
	public static WebElement botaoAlteraSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:alterarInscricao").findElement(contexto);
	}
	public static By localizadorBotaoAlteraSustentacaoOral() {
		return By.id("formProcessos:dtInscricoesSustentacaoOral:0:alterarInscricao");
	}
	
	//Botão salvar alterações Sustentação Oral
	public static WebElement botaoSalvarAlteracoesSustentacaoOral(SearchContext contexto){
		return By.id("formProcessos:salvarAlteracoesInscricao").findElement(contexto);
	}
	public static By localizadorBotaoSalvarAlteracoesSustentacaoOral() {
		return By.id("formProcessos:salvarAlteracoesInscricao");
	}	
	
	public static void selectOne(String idPrefix, SearchContext contexto){
		By.id(idPrefix+"_label").findElement(contexto).click();
		WebElement painel = By.id(idPrefix+"_panel").findElement(contexto);
		String conteudoPainel = painel.getText();		
		String[] opcoesPainel = conteudoPainel.trim().split("\n");
		String opcao = escolheAleatorio(opcoesPainel);
		By.xpath("//div[@id='" + idPrefix + "_panel']/div/ul/li[text()='" + opcao + "']").findElement(contexto).click();
	}
	
	public static String escolheAleatorio(String[] opcoes){
		String random = (opcoes[new Random().nextInt(opcoes.length)]);
		return random;
	}
	

	/**
	 * Fim ElementosPainelAcompanhamentoAssistente!
	 */

	/**
	 * Busca pelo polo ativo do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o polo ativo do processo selecionado no momento.
	 */
	public static WebElement poloAtivoDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:poloAtivo").findElement(contexto);
	}

	/**
	 * Busca pelo polo passivo do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o polo passivo do processo selecionado no momento.
	 */
	public static WebElement poloPassivoDoProcessoAtual(SearchContext contexto) {
		return By.id("formProcessos:poloPassivo").findElement(contexto);
	}
	
	/**
	 * Busca pelo dispositivo do processo selecionado no momento.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o dispositivo do processo selecionado no momento.
	 */
	public static WebElement dispositivoDoProcessoAtual(final WebDriver driver) {
		return new IFrameWebElement(driver, localizadorEditorDispositivo(), By.tagName("body"));
	}
	
	/**
	 * Busca pelo botão REMOVER processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão REMOVER.
	 */
	public static WebElement botaoRemover(SearchContext contexto) {
		return localizadorBotaoRemover().findElement(contexto);
	}

	public static By localizadorBotaoRemover() {
		return By.id("formProcessos:botaoNAO_JULGADO");
	}
	
	/**
	 * Busca pelo botão APREGOAR processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão APREGOAR.
	 */
	public static WebElement botaoApregoar(SearchContext contexto) {
		return localizadorBotaoApregoar().findElement(contexto);
	}

	public static By localizadorBotaoApregoar() {
		return By.id("formProcessos:botaoAPREGOADO");
	}
	
	/**
	 * Busca pelo botão JULGADO processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão JULGADO.
	 */
	public static WebElement botaoJulgado(SearchContext contexto) {
		return localizadorBotaoJulgado().findElement(contexto);
	}

	public static By localizadorBotaoJulgado() {
		return By.id("formProcessos:botaoJULGADO");
	}
	
	/**
	 * Busca pelo botão ADIAR processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão ADIAR.
	 */
	public static WebElement botaoAdiar(SearchContext contexto) {
		return localizadorBotaoAdiar().findElement(contexto);
	}

	public static By localizadorBotaoAdiar() {
		return By.id("formProcessos:botaoADIADO");
	}
	
	/**
	 * Busca pelo botão RETIRAR processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão RETIRAR.
	 */
	public static WebElement botaoRetirar(SearchContext contexto) {
		return localizadorBotaoRetirar().findElement(contexto);
	}

	public static By localizadorBotaoRetirar() {
		return By.id("formProcessos:botaoRETIRADO");
	}
	
	/**
	 * Busca pelo botão REVISAR processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão REVISAR.
	 */
	public static WebElement botaoRevisar(SearchContext contexto) {
		return localizadorBotaoRevisar().findElement(contexto);
	}

	public static By localizadorBotaoRevisar() {
		return By.id("formProcessos:botaoREVISAR");
	}
	
	/**
	 * Busca pelo botão VISTA EM MESA processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão VISTA EM MESA.
	 */
	public static WebElement botaoVistaMesa(SearchContext contexto) {
		return localizadorBotaoVistaMesa().findElement(contexto);
	}

	public static By localizadorBotaoVistaMesa() {
		return By.id("formProcessos:botaoVISTA_MESA");
	}
	
	/**
	 * Busca pelo botão VISTA REGIMENTAL processo no painel do Secretário.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o botão VISTA REGIMENTAL.
	 */
	public static WebElement botaoVistaRegimental(SearchContext contexto) {
		return localizadorBotaoVistaRegimental().findElement(contexto);
	}

	public static By localizadorBotaoVistaRegimental() {
		return By.id("formProcessos:botaoVISTA_REGIMENTAL");
	}
	
	/**
	 * Retorna a lista de mensagens de atualização mostradas no painel.
	 * 
	 * @param contexto O contexto onde os elementos devem ser procurados.
	 * 
	 * @return WebElement que representa as mensagens mostradas no painel.
	 */
	public static List<WebElement> mensagensDeAtualização(SearchContext contexto) {
		return localizadorMensagensDeAtualização().findElements(contexto);
	}

	public static By localizadorMensagensDeAtualização() {
		return By.xpath(".//*[@id='formProcessos:growl_container']//div[@class='ui-growl-message']/span");
	}

	public static By localizadorPopupVistaRegimental() {
		return By.id("dialogVistaRegimental");
	}

	/**
	 * Retorna o popup com a lista de magistrados a serem selecionados para vista regimental.
	 * 
	 * @param contexto O contexto onde o elemento deve ser procurado.
	 * 
	 * @return WebElement que representa o popup mostrado no painel.
	 */
	public static WebElement popupVistaRegimental(SearchContext contexto) {
		return localizadorPopupVistaRegimental().findElement(contexto);
	}

	public static WebElement nomeMagistradoNaListaVistaRegimental(SearchContext contexto, int indiceDaLista) {
		return By.id("formVistaRegimental:tabelaVistaRegimental:" + (indiceDaLista-1) + ":labelMagistradoTitular").findElement(contexto);
	}

	public static WebElement checkboxMagistradoVistaRegimental(SearchContext contexto, int indiceLista) {
		return By.xpath("//*[@id='formVistaRegimental:tabelaVistaRegimental_data']/tr[" + indiceLista + "]/td[1]/div/div[2]/span").findElement(contexto);
	}

	public static WebElement botaoConfirmarVistaRegimental(SearchContext contexto) {
		return By.id("formVistaRegimental:tabelaVistaRegimental:botaoCONFIRMAR_VISTA_REGIMENTAL").findElement(contexto);
	}

	public static WebElement botaoPreferencial(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:preferencial").findElement(contexto);
	}

	public static WebElement botaoVotoCompleto(SearchContext contexto) {
		return localizadorBotaoVotoCompleto().findElement(contexto);
	}

	public static By localizadorBotaoVotoCompleto() {
		return By.id("formProcessos:visualizarVotoCompleto");
	}

	public static WebElement checkboxAtualizacaoAutomatica(SearchContext contexto) {
		return By.xpath("//*[@id='formProcessos:lista_processos:atualizacaoAutomatica']/div[contains(@class,'ui-chkbox-box')]").findElement(contexto);
	}

	public static WebElement localizarProcesso(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:listaNumeroCnj:filter").findElement(contexto);
	}

	public static WebElement fecharSessao(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:alterarButton").findElement(contexto);
	}

	public static WebElement iconeSessaoOnline(SearchContext contexto) {
		return By.id("formProcessos:lista_processos:icon_user_online").findElement(contexto);
	}

}
