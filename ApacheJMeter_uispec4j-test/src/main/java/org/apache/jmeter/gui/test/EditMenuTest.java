package org.apache.jmeter.gui.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.NewDriver;
import org.apache.jmeter.uispec4j.JMeterHelpWindow;
import org.apache.jmeter.uispec4j.JMeterMainWindow;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.uispec4j.UISpec4J;
import org.uispec4j.interception.MainClassAdapter;

public class EditMenuTest {
	static {
		UISpec4J.init();
	}
	
	private static JMeterMainWindow mainWindow;
	private static String testfilesPath;

	@BeforeClass
	public static void startApplication() throws Exception {
		mainWindow = new JMeterMainWindow(
				new MainClassAdapter(NewDriver.class, new String[0]).getMainWindow());
	}

	@BeforeClass
	public static void getTestfilesPath() {
		File classDir = new File(FileMenuTest.class.getClassLoader().getResource(".").getPath());
		testfilesPath = new File(classDir.getParentFile().getParentFile(), "testfiles").getAbsolutePath();
	}
	
	/**
	 * Add a Thread Group element to Test Plan using the Add 
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.1.
	 */
	@Test
	public void addThreadGroupToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Thread Group");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Assertion Results element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.2.
	 */
	@Test
	public void addAssertionResultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("Assertion Results");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Assertion Results");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Graph Full Results element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.3.
	 */
	@Test
	public void addGraphFullResultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("Graph Full Results");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Graph Full Results");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Graph Results element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.4.
	 */
	@Test
	public void addGraphResultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("Graph Results");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Graph Results");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Spline Visualizer element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.5.
	 */
	@Test
	public void addSplineVisualizerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("Spline Visualizer");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Spline Visualizer");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Aggregate Report element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.6.
	 */
	@Test
	public void addAggregateReportToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("Aggregate Report");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Aggregate Report");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an View Results in Table element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.7.
	 */
	@Test
	public void addViewResultsInTableToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("View Results in Table");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("View Results in Table");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an View Results Tree element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.8.
	 */
	@Test
	public void addViewResultsTreeToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addListenerElementFromMenu("View Results Tree");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("View Results Tree");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add an Login Config Element element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.9.
	 */
	@Test
	public void addLoginConfigElementToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Login Config");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Login Config Element");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Counter element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.10.
	 */
	@Test
	public void addCounterToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Counter");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Counter");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a User Parameters element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.11.
	 */
	@Test
	public void addUserParametersToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("User Parameters");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("User Parameters");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a FTP Request Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.12.
	 */
	@Test
	public void addFTPRequestDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("FTP Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("FTP Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP Request Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.13.
	 */
	@Test
	public void addHTTPRequestDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP Authorization Manager element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.14.
	 */
	@Test
	public void addHTTPAuthorizationManagerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Authorization Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP Authorization Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP Cookie Manager element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.15.
	 */
	@Test
	public void addHTTPCookieManagerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Cookie Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP Cookie Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP Header Manager element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.16.
	 */
	@Test
	public void addHTTPHeaderManagerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Header Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP Header Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Java Request Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.17.
	 */
	@Test
	public void addJavaRequestDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Java Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Java Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a JDBC Database Login Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.18.
	 */
	@Test
	public void addJDBCDatabaseLoginDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC Database Login Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("JDBC Database Login Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a JDBC Database Connection Pool Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.19.
	 */
	@Test
	public void addJDBCDatabaseConnectionPoolDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC Database Connection Pool Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("JDBC Database Connection Pool Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a JDBC SQL Query Defaults element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.20.
	 */
	@Test
	public void addJDBCSQLQueryDefaultsToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC SQL Query Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("JDBC SQL Query Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Response Assertion element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.21.
	 */
	@Test
	public void addResponseAssertionToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addAssertionFromMenu("Response Assertion");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Response Assertion");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Duration Assertion element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.22.
	 */
	@Test
	public void addDurationAssertionToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addAssertionFromMenu("Duration Assertion");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Duration Assertion");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Size Assertion element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.23.
	 */
	@Ignore("Not available on version v1")
	@Test
	public void addSizeAssertionToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addAssertionFromMenu("Size Assertion");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Size Assertion");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTML Parameter Mask element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.24.
	 */
	@Test
	public void addHTMLParameterMaskToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addModifierFromMenu("HTML Parameter Mask");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTML Parameter Mask");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP User Parameter Modifier element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.25.
	 */
	@Test
	public void addHTTPUserParameterModifierToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addModifierFromMenu("HTTP User Parameter Modifier");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP User Parameter Modifier");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTML Link Parser element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.26.
	 */
	@Test
	public void addHTMLLinkParserToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addResponseBasedModifierFromMenu("HTML Link Parser");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTML Link Parser");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a HTTP URL Re-writing Modifier element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.27.
	 */
	@Test
	public void addHTTPURLRewritingModifierToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addResponseBasedModifierFromMenu("HTTP URL Re-writing Modifier");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("HTTP URL Re-writing Modifier");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Constant Throughput Timer element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.28.
	 */
	@Ignore("Not available on version v1")
	@Test
	public void addConstantThroughputTimerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addTimerFromMenu("Constant Throughput Timer");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Constant Throughput Timer");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Constant Timer element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.29.
	 */
	@Test
	public void addConstantTimerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addTimerFromMenu("Constant Timer");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Constant Timer");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Gaussian Random Timer element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.30.
	 */
	@Test
	public void addGaussianRandomTimerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addTimerFromMenu("Gaussian Random Timer");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Gaussian Random Timer");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Uniform Random Timer element to Test Plan using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.1.31.
	 */
	@Test
	public void addUniformRandomTimerToTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addTimerFromMenu("Uniform Random Timer");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Uniform Random Timer");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
	}
	
	/**
	 * Add a Interleave Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.1.
	 */
	@Test
	public void addInterleaveControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Interleave Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Interleave Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Simple Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.2.
	 */
	@Test
	public void addSimpleControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Simple Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Simple Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Loop Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.3.
	 */
	@Test
	public void addLoopControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Loop Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Loop Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Once Only Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.4.
	 */
	@Test
	public void addOnceOnlyControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Once Only Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Once Only Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Random Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.5.
	 */
	@Test
	public void addRandomControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Random Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Random Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Recording Controller element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.6.
	 */
	@Test
	public void addRecordingControllerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addLogicControllerFromMenu("Recording Controller");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Recording Controller");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a FTP Request element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.7.
	 */
	@Test
	public void addFTPRequestToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addSamplerFromMenu("FTP Request");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("FTP Request");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Request element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.8.
	 */
	@Test
	public void addHTTPRequestToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addSamplerFromMenu("HTTP Request");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Request");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a SOAP/XML-RPC Request element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.9.
	 */
	@Test
	public void addSOAPXMLRPCRequestToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addSamplerFromMenu("SOAP/XML-RPC Request");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("SOAP/XML-RPC Request");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Java Request element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.10.
	 */
	@Test
	public void addJavaRequestToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addSamplerFromMenu("Java Request");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Java Request");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a JDBC Request element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.11.
	 */
	@Test
	public void addJDBCRequestToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addSamplerFromMenu("JDBC Request");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("JDBC Request");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Login Config Element element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.12.
	 */
	@Test
	public void addLoginConfigElementToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Login Config Element");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Login Config Element");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Counter element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.13.
	 */
	@Test
	public void addCounterToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Counter");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Counter");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a User Parameters element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.14.
	 */
	@Test
	public void addUserParametersToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("User Parameters");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("User Parameters");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a FTP Request Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.15.
	 */
	@Test
	public void addFTPRequestDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("FTP Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("FTP Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Request Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.16.
	 */
	@Test
	public void addHTTPRequestDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Authorization Manager element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.17.
	 */
	@Test
	public void addHTTPAuthorizationManagerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Authorization Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Authorization Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Cookie Manager element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.18.
	 */
	@Test
	public void addHTTPCookieManagerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Cookie Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Cookie Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Header Manager element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.19.
	 */
	@Test
	public void addHTTPHeaderManagerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("HTTP Header Manager");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Header Manager");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a Java Request Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.20.
	 */
	@Test
	public void addJavaRequestDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("Java Request Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Java Request Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a JDBC Database Login Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.21.
	 */
	@Test
	public void addJDBCDatabaseLoginDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC Database Login Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("JDBC Database Login Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a JDBC Database Connection Pool Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.22.
	 */
	@Test
	public void addJDBCDatabaseConnectionPoolDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC Database Connection Pool Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("JDBC Database Connection Pool Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a JDBC SQL Query Defaults element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.23.
	 */
	@Test
	public void addJDBCSQLQueryDefaultsToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addConfigElementFromMenu("JDBC SQL Query Defaults");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("JDBC SQL Query Defaults");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Add a HTTP Proxy Server element to WorkBench using the Add
	 * submenu inside Edit menu. Verify if it was added successfully
	 * and recover initial state of the application (new file).
	 * 
	 * Test reference: 2.2.24.
	 */
	@Test
	public void addHTTPProxyServerToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addNonTestElementFromMenu("HTTP Proxy Server");
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("HTTP Proxy Server");
		
		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
	}
	
	/**
	 * Remove Thread Group element from Test Plan using the remove
	 * option from Edit menu. Verify element was removed sucessfully.
	 * 
	 * Test reference: 2.3.1.
	 */
	@Test
	public void removeThreadGroupFromTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Thread Group");
		
		// Select remove option at Edit menu
		mainWindow.getMenu().removeSelectedElementUsingMenu();

		// Verify workbench name was returned to default value
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench");
	}
	
	/**
	 * Cut element from Test Plan using the cut option from Edit 
	 * menu. 
	 * 
	 * FEATURE NOT IMPLEMENTED YET.
	 * 
	 * Test reference: 2.3.2.
	 */
	@Test
	public void cutThreadGroupFromTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Thread Group");
		
		// Select cut option at Edit menu
		mainWindow.getMenu().cutSelectedElementUsingMenu();

		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
		
	}
	
	/**
	 * Copy element from Test Plan using the copy option from Edit 
	 * menu. 
	 * 
	 * FEATURE NOT IMPLEMENTED YET.
	 * 
	 * Test reference: 2.3.3.
	 */
	@Test
	public void copyThreadGroupFromTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add new element using Edit menu
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Thread Group");
		
		// Select copy option at Edit menu
		mainWindow.getMenu().copySelectedElementUsingMenu();

		// Clear application using new file
		mainWindow.getMenu().newFileUsingFileMenu(true);
		
	}
	
	/**
	 * Paste element to WorkBench using the paste option from Edit 
	 * menu. 
	 * 
	 * FEATURE NOT IMPLEMENTED YET.
	 * 
	 * Test reference: 2.3.4.
	 */
	@Test
	public void pasteElementToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Select paste option at Edit menu
		mainWindow.getMenu().pasteElementUsingMenu();
		
	}
	
	/**
	 * Paste element to WorkBench using the paste as insert option 
	 * from Edit menu. 
	 * 
	 * FEATURE NOT IMPLEMENTED YET.
	 * 
	 * Test reference: 2.3.5.
	 */
	@Test
	public void pasteAsInsertElementToWorkBench() {
		// Select WorkBench element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Select paste option at Edit menu
		mainWindow.getMenu().pasteAsInsertElementUsingMenu();
		
	}

	/**
	 * Test case open a template testfile using the Edit menu
	 * and verify if the testfile was loaded sucessfully on
	 * Test Plan section.
	 * 
	 * Test reference: 2.4.1.
	 */
	@Test
	public void openFileOnTestPlan() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Open urlrewriting test plan (testfiles/urlrewriting.jmx)
		String urlrewritingJmx = new File(testfilesPath, "urlrewriting.jmx").getAbsolutePath();
		mainWindow.getMenu().openFileUsingEditMenu(urlrewritingJmx);
		
		// Check tree view was populated with proxy test plan
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"    URL Re-writing Example\n" +
				"      Simple Controller\n" +
				"        Login\n" +
				"        Do Something interesting\n" +
				"        Another Interesting Request\n" +
				"        HTTP URL-Rewriting Modifier\n" +
				"  WorkBench");
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case open a template testfile using the Edit menu
	 * and verify if the testfile was loaded sucessfully on
	 * Test Plan section.
	 * 
	 * Test reference: 2.4.2.
	 */
	@Test
	public void openFileOnWorkBench() {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Open urlrewriting test plan (testfiles/urlrewriting.jmx)
		String urlrewritingJmx = new File(testfilesPath, "urlrewriting.jmx").getAbsolutePath();
		mainWindow.getMenu().openFileUsingEditMenu(urlrewritingJmx);
		
		// Check tree view was populated with proxy test plan
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench\n" +
				"    URL Re-writing Example\n" +
				"      Simple Controller\n" +
				"        Login\n" +
				"        Do Something interesting\n" +
				"        Another Interesting Request\n" +
				"        HTTP URL-Rewriting Modifier");
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case save a testfile from Test Plan using the Edit menu
	 * and verify if the testfile was saved sucessfully.
	 * 
	 * Test reference: 2.4.3.
	 * 
	 * @throws IOException 
	 */
	@Test
	public void saveFileFromTestPlan() throws IOException {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectTestPlan();
		
		// Add Counter to Test Plan
		mainWindow.getMenu().addConfigElementFromMenu("Counter");

		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToTestPlan("Counter");
		
		// Save Counter to file
		File savedFile = File.createTempFile("counter", ".jmx");
		mainWindow.getMenu().saveFileAsUsingEditMenu(savedFile.getAbsolutePath());
		
		// Compare with template file
		File templateFile = new File(testfilesPath, "counter.jmx");
		Assert.assertTrue(FileUtils.contentEquals(templateFile, savedFile));
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case save a testfile from WorkBench using the Edit menu
	 * and verify if the testfile was saved sucessfully.
	 * 
	 * Test reference: 2.4.4.
	 * 
	 * @throws IOException 
	 */
	@Test
	public void saveFileFromWorkBench() throws IOException {
		// Select Test Plan element on treeview
		mainWindow.getTree().selectWorkbench();
		
		// Add Counter to Test Plan
		mainWindow.getMenu().addConfigElementFromMenu("Counter");

		// Check treeview to check new element was inserted
		// and is selected
		checkElementAddedToWorkBench("Counter");
		
		// Save Counter to file
		File savedFile = File.createTempFile("counter", ".jmx");
		mainWindow.getMenu().saveFileAsUsingEditMenu(savedFile.getAbsolutePath());
		
		// Compare with template file
		File templateFile = new File(testfilesPath, "counter.jmx");
		Assert.assertTrue(FileUtils.contentEquals(templateFile, savedFile));
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}
	
	/**
	 * Test case enables a disabled element from Test Plan and
	 * verify the element was sucessfully disabled.
	 * 
	 * Test reference: 2.4.5.
	 */
	@Test
	public void enableDisabledElement() {
		// Add element to Test Plan
		mainWindow.getTree().selectTestPlan();
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Disable added element and select WorkBench
		mainWindow.getMenu().disableSelectedElement();
		mainWindow.getTree().selectWorkbench();
		
		// Select disabled element
		mainWindow.getTree().select("Test Plan->Thread Group");
		
		// Enable selected element
		mainWindow.getMenu().enableSelectedElement();
		
		// Verify selected element was enabled
		mainWindow.getTree().assertElementEnabled("Test Plan->Thread Group");

		// Reset application Test Plan
		mainWindow.getMenu().newFileUsingAccelerator(true);
	}
	
	/**
	 * Test case enables an enabled element from Test Plan and
	 * verify it is not possible.
	 * 
	 * Test reference: 2.4.6.
	 */
	@Test
	public void enableEnabledElement() {
		// Add element to Test Plan
		mainWindow.getTree().selectTestPlan();
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Enable selected element
		mainWindow.getMenu().assertEnableOptionIsDisabled();

		// Reset application Test Plan
		mainWindow.getMenu().newFileUsingAccelerator(true);
	}
	
	/**
	 * Test case disables a enabled element from Test Plan and
	 * verify the element was sucessfully enabled.
	 * 
	 * Test reference: 2.4.7.
	 */
	@Test
	public void disableEnabledElement() {
		// Add element to Test Plan
		mainWindow.getTree().selectTestPlan();
		mainWindow.getMenu().addThreadGroupFromMenu();
		
		// Disable added element and select WorkBench
		mainWindow.getMenu().disableSelectedElement();
		mainWindow.getTree().selectWorkbench();
		
		// Select disabled element
		mainWindow.getTree().select("Test Plan->Thread Group");
		
		// Verify selected element was disabled
		mainWindow.getTree().assertElementDisabled("Test Plan->Thread Group");

		// Reset application Test Plan
		mainWindow.getMenu().newFileUsingAccelerator(true);
	}
	
	/**
	 * Test case disables a disabled element from Test Plan and
	 * verify it is not possible.
	 * 
	 * Test reference: 2.4.8.
	 */
	@Test
	public void disableDisabledElement() {
		// Add element to Test Plan
		mainWindow.getTree().selectTestPlan();
		mainWindow.getMenu().addThreadGroupFromMenu();

		// Disable added element and select WorkBench
		mainWindow.getMenu().disableSelectedElement();
		mainWindow.getTree().selectWorkbench();
		
		// Select disabled element
		mainWindow.getTree().select("Test Plan->Thread Group");
	
		// Enable selected element
		mainWindow.getMenu().assertDisableOptionIsDisabled();

		// Reset application Test Plan
		mainWindow.getMenu().newFileUsingAccelerator(true);
	}
	
	/**
	 * Test case open the help window from Edit menu.
	 * 
	 * Test reference: 2.5.
	 */
	@Test
	public void openHelpWindow() {
		JMeterHelpWindow helpWindow = mainWindow.getMenu().helpWindowFromEditMenu();
		helpWindow.close();
	}

	private void checkElementAddedToTestPlan(String elementName) {
		mainWindow.getTree().assertEquals(
			"Root\n" +
			"  Test Plan\n" +
			"    " + elementName + "\n" +
			"  WorkBench");
		mainWindow.getTree().assertSelected("Test Plan->" + elementName);
	}

	private void checkElementAddedToWorkBench(String elementName) {
		mainWindow.getTree().assertEquals(
			"Root\n" +
			"  Test Plan\n" +
			"  WorkBench\n" +
			"    " + elementName + "\n");
		mainWindow.getTree().assertSelected("WorkBench->" + elementName);
	}

}
