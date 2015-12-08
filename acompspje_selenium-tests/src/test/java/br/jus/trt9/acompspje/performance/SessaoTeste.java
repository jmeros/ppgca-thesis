package br.jus.trt9.acompspje.performance;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.db.ComposicaoSessao;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.db.VistaRegimental;
import br.jus.trt9.acompspje.selenium.telas.PainelAssistente;
import br.jus.trt9.acompspje.selenium.telas.PainelGabinete;
import br.jus.trt9.acompspje.selenium.telas.PainelMagistrado;
import br.jus.trt9.acompspje.selenium.telas.PainelProcurador;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;

public class SessaoTeste {
	// Dados da máquina de teste
	String maquinaTeste;
	
	// Dados relativos a sessão de teste sendo usada
	SessaoJulgamento sessao;
	List<RoteiroPautaSessao> processos;
	List<ComposicaoSessao> composicaoSessao;
	List<VistaRegimental> vistasRegimentais;
	
	// Referências para os browsers abertos para esta sessão de teste
	WebDriver driverSecretario = null;
	ArrayList<WebDriver> driverProcuradores = new ArrayList<WebDriver>();
	ArrayList<WebDriver> driverMagistrados = new ArrayList<WebDriver>();
	ArrayList<WebDriver> driverAssistentes = new ArrayList<WebDriver>();
	ArrayList<WebDriver> driverGabinetes = new ArrayList<WebDriver>();
	
	// Referências para os paineis de acompanhamento desta sessão de teste
	PainelSecretario secretario = null;
	ArrayList<PainelAssistente> assistentes = new ArrayList<PainelAssistente>();
	ArrayList<PainelGabinete> gabinetes = new ArrayList<PainelGabinete>();
	ArrayList<PainelMagistrado> magistrados = new ArrayList<PainelMagistrado>();
	ArrayList<PainelProcurador> procuradores = new ArrayList<PainelProcurador>();
}
