package br.jus.trt9.acompspje.performance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.xml.XMLUtils;

@Ignore
public class TesteCarga {
	private final static Logger LOGGER = Logger.getLogger(TesteCarga.class.getName());
	
	final int MAGISTRADOS_POR_SESSAO = 4;
	final int PROCURADORES_POR_SESSAO = 2;
	final int GABINETES_POR_SESSAO = 1;
	final int ASSISTENTES_POR_SESSAO = 2;
	
	boolean abortExecution = false;
	
	Set<String> basesDadosDisponiveis = new HashSet<String>(Arrays.asList(new String[]{"cargaBD_1.xml","cargaBD_2.xml","cargaBD_3.xml","cargaBD_4.xml"}));
	String[] MAQUINAS_REMOTAS = {"10.9.62.49", "10.9.62.204", "10.9.3.53", "10.9.62.175"};
	HashMap<String, SessaoTeste> usuariosPorMaquina = new HashMap<String, SessaoTeste>();
	
	void inicializaBrowsers() throws MalformedURLException {
		// Inicializa os usuarios remotos
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		for (int i=0; i<MAQUINAS_REMOTAS.length; i++) {
			logInfo("Inicializando browsers na máquina " + MAQUINAS_REMOTAS[i]);
			
			// Criar uma lista de usuarios remotos para cada máquina remota
			SessaoTeste sessaoTeste = new SessaoTeste();
			usuariosPorMaquina.put(MAQUINAS_REMOTAS[i], sessaoTeste);
			sessaoTeste.maquinaTeste = MAQUINAS_REMOTAS[i];
			
			// Criar secretario
			sessaoTeste.driverSecretario = new RemoteWebDriver(new URL("http://"+MAQUINAS_REMOTAS[i]+":4444/wd/hub"), capabilities);
			
			// Criar magistrados
			for (int j=0; j<MAGISTRADOS_POR_SESSAO; j++) {
				sessaoTeste.driverMagistrados.add(new RemoteWebDriver(new URL("http://"+MAQUINAS_REMOTAS[i]+":4444/wd/hub"), capabilities));
			}

			// Criar procuradores
			for (int j=0; j<PROCURADORES_POR_SESSAO; j++) {
				sessaoTeste.driverProcuradores.add(new RemoteWebDriver(new URL("http://"+MAQUINAS_REMOTAS[i]+":4444/wd/hub"), capabilities));
			}

			// Criar gabinetes
			for (int j=0; j<GABINETES_POR_SESSAO; j++) {
				sessaoTeste.driverGabinetes.add(new RemoteWebDriver(new URL("http://"+MAQUINAS_REMOTAS[i]+":4444/wd/hub"), capabilities));
			}

			// Criar assistentes
			for (int j=0; j<ASSISTENTES_POR_SESSAO; j++) {
				sessaoTeste.driverAssistentes.add(new RemoteWebDriver(new URL("http://"+MAQUINAS_REMOTAS[i]+":4444/wd/hub"), capabilities));
			}
			
		}
	}
	
	void carregaDadosBD() throws DOMException, SAXException, IOException, ParserConfigurationException, ParseException, SQLException, ClassNotFoundException {
		// Para cada grupo de usuário carrega uma sessão diferente
		for (SessaoTeste sessaoTeste: usuariosPorMaquina.values()) {
			
			// Pegar uma base de dados disponível em arquivo
			String bdFile = null;
			for (String file: basesDadosDisponiveis)
				bdFile = file;
			basesDadosDisponiveis.remove(bdFile);
			
			// Ler os dados do arquivo
			logInfo("Carregando base de dados de arquivo " + bdFile);
			XMLUtils xmlUtils = new XMLUtils(bdFile);
			sessaoTeste.sessao = xmlUtils.readSessao();
			sessaoTeste.processos = xmlUtils.readProcessos();
			sessaoTeste.composicaoSessao = xmlUtils.readComposicaoSessao();
			sessaoTeste.vistasRegimentais = xmlUtils.readVistaRegimental();
			
			// Carregar os dados na base de dados
			logInfo("Inicializando base de dados para a sessão " + sessaoTeste.sessao.getID_SESSAO_PJE());
			BDUtils.getInstance().limparDados(sessaoTeste.sessao.getID_SESSAO_PJE());
			BDUtils.getInstance().carregarDados(sessaoTeste.sessao , sessaoTeste.processos, sessaoTeste.composicaoSessao, sessaoTeste.vistasRegimentais);
		}
	}
	
	void limpaDadosBD() throws SQLException, ClassNotFoundException, IOException {
		for (SessaoTeste sessaoTeste: usuariosPorMaquina.values()) {
			if (sessaoTeste.sessao != null) {
				logInfo("Limpando sessão " + sessaoTeste.sessao.getID_SESSAO_PJE() + " da base de dados");
				BDUtils.getInstance().limparDados(sessaoTeste.sessao.getID_SESSAO_PJE());
			}
		}
	}
	
	void fechaTodosOsBrowsers() {
		for (SessaoTeste sessaoTeste: usuariosPorMaquina.values()) {
			logInfo("Fechando browsers na máquina " + sessaoTeste.maquinaTeste);
			
			if (sessaoTeste.secretario != null) {
				sessaoTeste.driverSecretario.quit();
			}
			for (WebDriver driver: sessaoTeste.driverAssistentes) {
				if (driver != null)
					driver.quit();
			}
			for (WebDriver driver: sessaoTeste.driverGabinetes) {
				if (driver != null)
					driver.quit();
			}
			for (WebDriver driver: sessaoTeste.driverMagistrados) {
				if (driver != null)
					driver.quit();
			}
			for (WebDriver driver: sessaoTeste.driverProcuradores) {
				if (driver != null)
					driver.quit();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void test() throws Exception {
		// Criar botão para abortar execução
		criarDialogParaAbortar();
		
		 try {
			 // Inicializa todos os browsers remotos
			 inicializaBrowsers();
			 logInfo("Navegadores inicializados.");

			 // Carrega os dados das sessões
			 carregaDadosBD();
			 logInfo("Base de dados inicializada.");
			 
			 // Inicializa execução do teste em cada sessão (grupo de usuários)
			 List<Thread> threadsExecucao = new ArrayList<Thread>();
			 for (SessaoTeste usuarios: usuariosPorMaquina.values()) {
				 logInfo("Iniciando cenário de teste [máquina: " + usuarios.maquinaTeste + ", sessao: " + usuarios.sessao.getID_SESSAO_PJE() + "]");
				 CenarioTeste ct = new CenarioTeste(usuarios);
				 Thread t = new Thread(ct);
				 threadsExecucao.add(t);
				 t.start();
				 
				 // Aguardar até que todos os usuários estejam logados
				 while (!ct.isUsuariosLogados()) {
					 try {
						 Thread.sleep(10000);
					 } catch (InterruptedException e) {
						 logWarn("Thread interrompida enquanto aguardava usuarios logarem.");
					 }
				 }
				 logInfo("Inicialização do cenário de teste concluída.");
			 }
			 
			 // Aguardar até que todas as thread de execução terminem
			 logInfo("Aguardando threads de execução terminarem.");
			 while ((!threadsExecucao.isEmpty()) &&  (!abortExecution)) {
				 Thread t = threadsExecucao.get(0);
				 if (t.isAlive()) {
					 try {
						 Thread.sleep(10000);
					 } catch (InterruptedException e) {
						 logWarn("Thread interrompida enquando aguardava threads de execução: " + e.getMessage());
					 }
				 }
				 else {
					 threadsExecucao.remove(t);
				 }
			 }
			 
			 if (abortExecution) {
				 logInfo("Interrompendo as threads de execução.");
				 for (Thread t: threadsExecucao) {
					 t.stop();
				 }
			 }
		 }
		 finally {
			 // Limpa os dados da base
			 limpaDadosBD();
			 logInfo("Limpeza do banco de dados executada.");
			 
			 // Fecha todos os browsers remotos
			 fechaTodosOsBrowsers();
			 logInfo("Browsers de teste finalizados.");
		 }
	}

	private void logWarn(String message) {
		LOGGER.warning(message);
	}

	private void logInfo(String message) {
		LOGGER.info(message);
	}

	private void criarDialogParaAbortar() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int result = JOptionPane.showConfirmDialog(null, "Abortar o teste de carga?", "Teste de carga", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					abortExecution = true;
				}
			}
			
		}).start();
	}

	public static void main(String[] args) throws Exception {
		new TesteCarga().test();
	}
}
