package br.jus.trt9.acompspje.selenium.uc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.ComposicaoSessao;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.db.VistaRegimental;
import br.jus.trt9.acompspje.junit.TakeScreenshotRule;
import br.jus.trt9.acompspje.xml.XMLUtils;

/**
 * Esta classe apesar de não implementar nenhum teste, implementa as atividades
 * comuns de inicialização e finalização usadas em todos os casos de teste onde
 * apenas um usuário é testado simultaneamente, além de outras funções auxiliares.
 * 
 * @author jadermeros
 */
public abstract class UsuarioUnicoTestTemplate {
	
	WebDriver driver;

	// Dados recuperados do banco de dados para execução dos teste
	static SessaoJulgamento SESSAO;
	static List<RoteiroPautaSessao> PROCESSOS_PAUTA;
	static List<ComposicaoSessao> MAGISTRADOS;
	static List<VistaRegimental> VISTAS_REGIMENTAIS;
	
	@BeforeClass
	public static void prepararBD() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SAXException, ParserConfigurationException, DOMException, ParseException {
		// Carregar a sessão de teste e os processos da sessão do arquivo Xml
		XMLUtils xmlUtils = new XMLUtils("importarBD.xml");
		SESSAO = xmlUtils.readSessao();
		PROCESSOS_PAUTA = xmlUtils.readProcessos();
		MAGISTRADOS = xmlUtils.readComposicaoSessao();
		VISTAS_REGIMENTAIS = xmlUtils.readVistaRegimental();
		
		// Recarregar os processos no banco de dados
		BDUtils.getInstance().limparDados(SESSAO.getID_SESSAO_PJE());
		BDUtils.getInstance().carregarDados(SESSAO, PROCESSOS_PAUTA, MAGISTRADOS, VISTAS_REGIMENTAIS);
	}
	
	@Before
	public void prepararNavegador() throws IOException, ClassNotFoundException, SQLException {
		if ("Linux".equals(System.getProperty("os.name"))) {
			System.setProperty("webdriver.firefox.bin", "/u01/app/jenkins/firefox-16.0.2/firefox");
		}
//		else {
//			System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox 16.0\\firefox.exe");
//		}
		
		driver = createWebDriver();
		//System.out.println("Firefox version: " + ((FirefoxDriver) driver).getCapabilities().getVersion());
	}

	public WebDriver createWebDriver() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		return new FirefoxDriver(profile);
	}

	@After
	public void fecharNavegador() {
		((TakeScreenshotRule)screenshotOnFailure).takeScreenshot(name.getMethodName(), driver);
		driver.quit();
	}
	
	@AfterClass
	public static void desconectarBD() throws ClassNotFoundException, SQLException, IOException {
		// Limpar a sessão de teste do banco e fechar a conexão
		BDUtils.getInstance().limparDados(SESSAO.getID_SESSAO_PJE());
		BDUtils.getInstance().close();
	}
	
	@Rule
	public TestName name = new TestName();
	
	@Rule
	public TestRule screenshotOnFailure = new TakeScreenshotRule();

	/**
	 * Procura na lista de processos carregada no banco, qual o processo que deveria estar no topo da lista.
	 * 
	 * @return O processo que deveria estar na primeira posição da lista de processos da sessão.
	 */
	protected RoteiroPautaSessao encontrarPrimeiroProcessoDaSessao() {
		for (RoteiroPautaSessao processo : PROCESSOS_PAUTA) {
			if (processo.getNR_SEQUENCIAL_PROCESSO() == 1) {
				return processo;
			}
		}
		
		Assert.fail("Não foi possível encontrar o primeiro processo na lista de processos importada para o banco de dados.");
		return null;
	}

	protected RoteiroPautaSessao encontrarPrimeiroProcessoNoEstado(EstadoProcesso estadoProcesso) {
		// Separa todos os processos no estado desejado
		List<RoteiroPautaSessao> processos = buscarTodosProcessos(estadoProcesso);
		Assert.assertNotEquals(0, processos.size());
		
		// Seleciona o processo com menor número de sequência na sessão
		RoteiroPautaSessao primeiroProcesso = processos.get(0);
		for (RoteiroPautaSessao processo: processos) {
			if (processo.getNR_SEQUENCIAL_PROCESSO() < primeiroProcesso.getNR_SEQUENCIAL_PROCESSO()) {
				primeiroProcesso = processo;
			}
		}
		
		return primeiroProcesso;
	}
	
	/**
	 * Procura na lista de processos carregado no banco por um processo no estado desejado.
	 * 
	 * @param estadoProcesso Estado desejado para o processo.
	 * 
	 * @return Um processo que está no estado desejado.
	 */
	protected RoteiroPautaSessao encontrarProcessoPorEstado(EstadoProcesso estadoProcesso) {
		return encontrarProcessoPorEstado(estadoProcesso, null, null);
	}

	/**
	 * Procura na lista de processos carregado no banco por um processo no estado desejado
	 * e com o estado de preferência desejado.
	 * 
	 * @param estadoProcesso Estado desejado para o processo (null = retornar um processo em qualquer estado)
	 * @param preferencial Estado de preferência desejado (true = preferencial, false = não preferencial, null = ignorar preferência)
	 * @param sustentacaoOral Estado de sustentação oral desejado (true = possui, false = não possui, null = ignorar sustentação oral)
	 * 
	 * @return Um processo que está no estado desejado.
	 */
	protected RoteiroPautaSessao encontrarProcessoPorEstado(EstadoProcesso estadoProcesso, Boolean preferencial, Boolean sustentacaoOral) {
		for (RoteiroPautaSessao processo: PROCESSOS_PAUTA) {
			if (estadoProcesso != null && EstadoProcesso.valueOf(processo.getDS_STATUS()) != estadoProcesso) {
				continue;
			}
			
			if (preferencial != null && (preferencial ^ processo.getIN_PREFERENCIA().charValue() == 'S')) {
				continue;
			}
			
			if (sustentacaoOral != null && (sustentacaoOral ^ processo.getIN_SUSTENTACAO_ORAL().charValue() == 'S')) {
				continue;
			}
			
			return processo;
		}
		
		Assert.fail("Não foi possível encontrar um processo no estado desejado na lista de processos importada para o banco de dados: estado[" + estadoProcesso + "], preferecial[" + preferencial + "], sustentacao oral[" + sustentacaoOral + "])");
		return null;
	}

	protected RoteiroPautaSessao encontrarProcessoPorClasseJudicial(String siglaClasseJudicial) {
		for (RoteiroPautaSessao processo: PROCESSOS_PAUTA) {
			if (processo.getDS_CLASSE_JUDICIAL_SIGLA().equals(siglaClasseJudicial))
				return processo;
		}
		
		Assert.fail("Não foi possível encontrar um processo com a classe judicial desejada: " + siglaClasseJudicial);
		return null;
	}

	/**
	 * Procura por todos os processos na lista de processos que estão no estado desejado.
	 * 
	 * @param estado O EstadoProcesso desejado para buscar os processos.
	 * 
	 * @return A lista de todos os processos encontrados no estado desejado.
	 */
	public List<RoteiroPautaSessao> buscarTodosProcessos(EstadoProcesso estado) {
		List<RoteiroPautaSessao> lista = new ArrayList<RoteiroPautaSessao>();
		for (RoteiroPautaSessao processo : PROCESSOS_PAUTA) {
			if (processo.getDS_STATUS().equals(estado.name())) {
				lista.add(processo);
			}
		}
		return lista;
	}

}
