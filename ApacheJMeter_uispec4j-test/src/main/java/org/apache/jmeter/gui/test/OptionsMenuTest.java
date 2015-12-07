package org.apache.jmeter.gui.test;

import org.apache.jmeter.NewDriver;
import org.apache.jmeter.uispec4j.JMeterFunctionHelperWindow;
import org.apache.jmeter.uispec4j.JMeterMainWindow;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.uispec4j.UISpec4J;
import org.uispec4j.interception.MainClassAdapter;

public class OptionsMenuTest {
	static {
		UISpec4J.init();
	}
	
	private static JMeterMainWindow mainWindow;
	private static JMeterFunctionHelperWindow funcHelperWindow;
	
	@BeforeClass
	public static void startApplication() throws Exception {
		mainWindow = new JMeterMainWindow(
				new MainClassAdapter(NewDriver.class, new String[0]).getMainWindow());
		
		funcHelperWindow = mainWindow.getMenu().openFunctionHelperWindow();
	}
	
	/**
	 * Test case that check functionality on function _intSum.
	 * 
	 * Test reference: 4.1.1.
	 */
	@Ignore("Not available on version v1.")
	@Test
	public void testIntSumFunctionHelper() {
		// Force one function change to guarantee that parameters
		// are populated correctly
		funcHelperWindow.selectFunction("_counter");
		funcHelperWindow.selectFunction("_intSum");
		
		// Set function parameters
		//funcHelperWindow.addParameters(new String[]{"1","2","3"});
		
		// Generate function and check result
		funcHelperWindow.generateFunction();
		funcHelperWindow.assertFunctionStringEquals("${__intSum(,,)}");
	}

	/**
	 * Test case that check functionality on function _counter.
	 * 
	 * Test reference: 4.1.2.
	 */
	@Test
	public void testCounterFunctionHelper() {
		// Select counter function
		funcHelperWindow.selectFunction("_counter");
		
		// Set function parameters
		//funcHelperWindow.addParameters(new String[]{"1","2"});
		
		// Generate function and check result
		funcHelperWindow.generateFunction();
		funcHelperWindow.assertFunctionStringEquals("${__counter(,)}");
	}

	/**
	 * Test case that check functionality on function _regexFunction.
	 * 
	 * Test reference: 4.1.3.
	 */
	@Test
	public void testRegexFunctionHelper() {
		// Select regex function
		funcHelperWindow.selectFunction("_regexFunction");
		
		// Set function parameters
		//funcHelperWindow.addParameters(new String[]{"1","2","3","4","5","6"});
		
		// Generate function and check result
		funcHelperWindow.generateFunction();
		funcHelperWindow.assertFunctionStringEquals("${__regexFunction(,,,,,)}");
	}

	/**
	 * Test case that check functionality on function _threadNum
	 * 
	 * Test reference: 4.1.4.
	 */
	@Test
	public void testThreadNumFunctionHelper() {
		// Select threadNum function
		funcHelperWindow.selectFunction("_threadNum");
		
		// Set function parameters
		funcHelperWindow.addParameters(new String[]{});
		
		// Generate function and check result
		funcHelperWindow.generateFunction();
		funcHelperWindow.assertFunctionStringEquals("${__threadNum}");
	}

}
