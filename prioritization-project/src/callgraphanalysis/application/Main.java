package callgraphanalysis.application;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.jmeter.gui.test.EditMenuTest;
import org.apache.jmeter.gui.test.FileMenuTest;
import org.apache.jmeter.gui.test.HelpMenuTest;
import org.apache.jmeter.gui.test.OptionsMenuTest;
import org.apache.jmeter.gui.test.RunMenuTest;

import br.jus.trt9.acompspje.selenium.uc.TC01_AcessarSistemaTest;
import br.jus.trt9.acompspje.selenium.uc.TC02_ManterAtualizacaoAutomaticaTest;
import br.jus.trt9.acompspje.selenium.uc.TC03_PainelAcompanhamentoTest;
import br.jus.trt9.acompspje.selenium.uc.TC04_MarcarProcessoComoJulgadoTest;
import br.jus.trt9.acompspje.selenium.uc.TC05_ApregoarProcessoTest;
import br.jus.trt9.acompspje.selenium.uc.TC06_EditarDispositivoTest;
import br.jus.trt9.acompspje.selenium.uc.TC07_VisualizarVotoCompletoTest;
import br.jus.trt9.acompspje.selenium.uc.TC08_LocalizarProcessoNaSessaoTest;
import br.jus.trt9.acompspje.selenium.uc.TC09a_AlterarStatusProcessoTest;
import br.jus.trt9.acompspje.selenium.uc.TC09b_AlterarStatusProcessoTest;
import br.jus.trt9.acompspje.selenium.uc.TC10_EncerrarSessaoTest;
import br.jus.trt9.acompspje.selenium.uc.TC11_ManterCadastroSustentacaoOralTest;
import br.jus.trt9.acompspje.selenium.uc.TC12_GerarRelatorioSessaoTest;
import br.jus.trt9.acompspje.selenium.uc.TC13_GerarRoteiroSessaoTest;
import br.jus.trt9.acompspje.selenium.uc.TC15_ManterCadastroVisibilidadeProcuradoresTest;
import callgraphanalysis.coverage.TestSuiteCoverage;
import callgraphanalysis.coverage.TestSuiteCoverageFactory;
import callgraphanalysis.coverage.impl.TestCaseLineCoverage;
import callgraphanalysis.coverage.impl.TestCaseStaticCallGraphCoverage;
import callgraphanalysis.staticcallgraph.Node;
import callgraphanalysis.staticcallgraph.StaticCallGraph;
import callgraphanalysis.staticcallgraph.StaticCallGraphFactory;
import net.sourceforge.cobertura.xml.RootCoverage;
import soot.Modifier;
import soot.options.Options;

public class Main {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		// Define which project will be prioritized
		boolean prioritizationForJMeter = true;
		boolean prioritizationForAcompsPJe = false;
		
		// Define what to do
		boolean listTestCases = true;
		boolean prioritizeByLineCoverage = false;
		boolean prioritizeByCallGraph = false;
		boolean prioritizeRandomically = false;
		
		Class[] testClasses = null;
		if (prioritizationForJMeter) {
			// Test classes for JMeter
			testClasses = new Class[] {
				FileMenuTest.class,
				EditMenuTest.class,
				RunMenuTest.class,
				OptionsMenuTest.class,
				HelpMenuTest.class
			};
		}
		
		if (prioritizationForAcompsPJe) {
			// Test classes form AcompsPJe
			testClasses = new Class[] {
				TC01_AcessarSistemaTest.class,
				TC02_ManterAtualizacaoAutomaticaTest.class,
				TC03_PainelAcompanhamentoTest.class,
				TC04_MarcarProcessoComoJulgadoTest.class,
				TC05_ApregoarProcessoTest.class,
				TC06_EditarDispositivoTest.class,
				TC07_VisualizarVotoCompletoTest.class,
				TC08_LocalizarProcessoNaSessaoTest.class,
				TC09a_AlterarStatusProcessoTest.class,
				TC09b_AlterarStatusProcessoTest.class,
				TC10_EncerrarSessaoTest.class,
				TC11_ManterCadastroSustentacaoOralTest.class,
				TC12_GerarRelatorioSessaoTest.class,
				TC13_GerarRoteiroSessaoTest.class,
				TC15_ManterCadastroVisibilidadeProcuradoresTest.class
			};
		}

		if (listTestCases)
			listAllTestCases(testClasses);
		
		if (prioritizeByLineCoverage)
			prioritizeByLineCoverage();
		
		if (prioritizeByCallGraph)
			prioritizeByStaticCallPath(testClasses);
		
		if (prioritizeRandomically)
			prioritizeByRandom(testClasses);
	}

	@SuppressWarnings("rawtypes")
	private static void listAllTestCases(Class[] testClasses) {   
		Options.v().set_whole_program(true);    
		try {
			// Retrieve test scenarios from test classes individually
			for (Class testClass: testClasses) {
	
				// Retrieve test methods
				for (Method testMethod: testClass.getDeclaredMethods()) {
					if (Modifier.isPublic(testMethod.getModifiers())) {
						System.out.println("Test case: " + testClass.getSimpleName() + "." + testMethod.getName() + "()");
						StaticCallGraph callGraph = StaticCallGraphFactory.construct(testClass, testMethod);
						for (Node n: new TestCaseStaticCallGraphCoverage(callGraph).getTestCaseNodes())
							System.out.println("    - " + n.getMethodName());
					}
				}
				System.out.println("\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static void prioritizeByRandom(Class[] testClasses) {
		Options.v().set_whole_program(true);
       
		try {
			
			// Retrieve test scenarios from test classes individually
			Set<TestCaseStaticCallGraphCoverage> testCases = new HashSet<TestCaseStaticCallGraphCoverage>();
			for (Class testClass: testClasses) {
	
				// Retrieve test methods
				for (Method testMethod: testClass.getDeclaredMethods()) {
					if (Modifier.isPublic(testMethod.getModifiers())) {
						System.out.println("Calculating call graph for " + testClass.getSimpleName() + "." + testMethod.getName() + "()");
						StaticCallGraph callGraph = StaticCallGraphFactory.construct(testClass, testMethod);
						testCases.add(new TestCaseStaticCallGraphCoverage(callGraph));
					}
				}				
			}
			
			for (int i=1; i<=10; i++) {
				// Calculate test suite order using random algorithm
				TestSuiteCoverage<TestCaseStaticCallGraphCoverage> testSuite = TestSuiteCoverageFactory.createTestSuiteUsingRandom(testCases);
				
				// Print test suite order
				System.out.println("\n\nTest suite " + i + ":");
				for (TestCaseStaticCallGraphCoverage testCase : testSuite.getTestCases()) {
					System.out.println("  - " + testCase.getTestCaseName());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void prioritizeByLineCoverage() throws JAXBException {
		// List all coverage files
        List<File> xmlFiles = retrieveCoverageFiles();
        
        // Parse all coverage files
        Set<TestCaseLineCoverage> testCases = new HashSet<TestCaseLineCoverage>();
        for (File xmlFile: xmlFiles) {
        	// Mount test case name based on directory structure
        	String testcaseName = xmlFile.getPath().replaceFirst("reports\\\\", "").replaceFirst("\\\\xml\\\\coverage.xml", "").replace('\\', '.').concat("()");
        	
        	// Parse test case coverage
        	JAXBContext context = JAXBContext.newInstance(RootCoverage.class);
    		Unmarshaller unmarshaller = context.createUnmarshaller();
    		RootCoverage coverage = (RootCoverage) unmarshaller.unmarshal(xmlFile);
    		
    		// Add testcase to set
    		testCases.add(new TestCaseLineCoverage(testcaseName, coverage));
        }
        
        // Calculate test suite order using greedy algorithm
		TestSuiteCoverage<TestCaseLineCoverage> testSuite = TestSuiteCoverageFactory.createTestSuiteUsingGreedyAlgorithm(testCases, true);
		
		// Print test suite order
		System.out.println("Test suite:");
		for (TestCaseLineCoverage testCase : testSuite.getTestCases()) {
			System.out.println("  - " + testCase.getTestCaseName());
		}
	}
	
	private static List<File> retrieveCoverageFiles() {
		return retrieveCoverageFiles(new File("reports"));
	}

	private static List<File> retrieveCoverageFiles(File file) {
		List<File> files = new ArrayList<File>();
		if (file.isDirectory()) {
			for (File f: file.listFiles()) {
				files.addAll(retrieveCoverageFiles(f));
			}
		}
		else {
			if (file.getName().equals("coverage.xml")) {
				files.add(file);
			}
		}
		return files;
	}

	@SuppressWarnings("rawtypes")
	private static void prioritizeByStaticCallPath(Class[] testClasses) {
		//Options.v().set_src_prec(Options.src_prec_java);
        Options.v().set_whole_program(true);
        //Options.v().set_time(true);
        //Options.v().set_verbose(true);
        //Options.v().set_debug(true);
        //Options.v().set_debug_resolver(true);
        //Options.v().set_output_format(Options.output_format_none);
        //Options.v().set_allow_phantom_refs(true);
        //Options.v().set_omit_excepting_unit_edges(true);
        //Options.v().set_show_exception_dests(false);
       
		try {
			System.out.println("Execution start time (ms): " + Calendar.getInstance().getTimeInMillis());
			// Retrieve test scenarios from test classes individually
			Set<TestCaseStaticCallGraphCoverage> testCases = new HashSet<TestCaseStaticCallGraphCoverage>();
			for (Class testClass: testClasses) {
	
				// Retrieve test methods
				for (Method testMethod: testClass.getDeclaredMethods()) {
					if (Modifier.isPublic(testMethod.getModifiers())) {
						System.out.println("Calculating call graph for " + testClass.getSimpleName() + "." + testMethod.getName() + "()");
						StaticCallGraph callGraph = StaticCallGraphFactory.construct(testClass, testMethod);
						testCases.add(new TestCaseStaticCallGraphCoverage(callGraph));
					}
				}				
			}
			
			// Print test cases after they were calculated
			//printTestCases(testCases);
			
			// Calculate test suite order using greedy algorithm
			System.out.println("Prioritization start time (ms): " + Calendar.getInstance().getTimeInMillis());
			TestSuiteCoverage<TestCaseStaticCallGraphCoverage> testSuite = TestSuiteCoverageFactory.createTestSuiteUsingGreedyAlgorithm(testCases, true);
			
			// Print test suite order
			System.out.println("Test suite:");
			for (TestCaseStaticCallGraphCoverage testCase : testSuite.getTestCases()) {
				System.out.println("  - " + testCase.getTestCaseName());
			}
			System.out.println("Execution end time (ms): " + Calendar.getInstance().getTimeInMillis());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
