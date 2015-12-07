package org.apache.jmeter.gui.test;

import java.io.File;

import org.apache.jmeter.NewDriver;
import org.apache.jmeter.uispec4j.JMeterMainWindow;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.uispec4j.UISpec4J;
import org.uispec4j.interception.MainClassAdapter;

public class RunMenuTest {
	static {
		UISpec4J.init();
	}
	
	private static JMeterMainWindow mainWindow;

	@BeforeClass
	public static void startApplication() throws Exception {
		// Open JMeter
		mainWindow = new JMeterMainWindow(
				new MainClassAdapter(NewDriver.class, new String[0]).getMainWindow());
		
		// Find test file used to run test (threadgroup.jmx)
		File classDir = new File(FileMenuTest.class.getClassLoader().getResource(".").getPath());
		File testfilesDir = new File(classDir.getParentFile().getParentFile(), "testfiles");
		File threadGroupTestFile = new File(testfilesDir, "threadgroup.jmx");
		
		// Open test file in JMeter
		mainWindow.getMenu().openFileUsingFileMenu(threadGroupTestFile.getAbsolutePath());
	}
	
	/**
	 * Test case that start the test plan execution using the Start
	 * menu option on Run menu.
	 * 
	 * Test reference: 3.1.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void startTestExecutionUsingMenu() throws InterruptedException {
		// Start execution using menu
		mainWindow.getMenu().startTestPlanUsingMenu();
		
		// Verify test plan is running
		mainWindow.getMenu().checkTestPlanIsRunning();
		
		// Wait until test plan finishes (around 3 seconds)
		Thread.sleep(3000);
		
		mainWindow.getMenu().checkTestPlanIsStopped();
	}

	/**
	 * Test case that start the test plan execution using the keyboard
	 * shortcut.
	 * 
	 * Test reference: 3.2.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void startTestExecutionUsingKeyboard() throws InterruptedException {
		// Start execution using keyboard
		mainWindow.getMenu().startTestPlanUsingKeyboard();
		
		// Verify test plan is running
		mainWindow.getMenu().checkTestPlanIsRunning();
		
		// Wait until test plan finishes (around 3 seconds)
		Thread.sleep(3000);
		
		mainWindow.getMenu().checkTestPlanIsStopped();
	}
	
	/**
	 * Test case that start a remote execution of the test plan using
	 * the Remote Start menu option on Run menu.
	 * 
	 * Test reference: 3.3.
	 */
	@Test
	public void startRemoteTestExecutionUsingMenu() {
		// Start remote test using menu
		mainWindow.getMenu().startTestPlanRemotelly();
	}
	
	/**
	 * Test case that stop the test plan execution using the Stop
	 * menu option on Run menu.
	 * 
	 * Test reference: 3.4.
	 * 
	 * @throws InterruptedException 
	 */
	@Test
	public void stopTestExecutionUsingMenu() throws InterruptedException {
		// Start execution using menu
		mainWindow.getMenu().startTestPlanUsingMenu();
		
		// Verify test plan is running
		mainWindow.getMenu().checkTestPlanIsRunning();
		
		// Give some time to the execution start
		Thread.sleep(500);
		
		// Stop execution using menu
		mainWindow.getMenu().stopTestPlanUsingMenu();
		
		// Verify test plan is stopped
		mainWindow.getMenu().checkTestPlanIsStopped();
	}
	
	/**
	 * Test case that stop the test plan execution using the keyboard
	 * shortcut.
	 * 
	 * Test reference: 3.5.
	 * 
	 * @throws InterruptedException 
	 */
	@Ignore("Cannot simulate CTRL+. sucessfully.")
	@Test
	public void stopTestExecutionUsingKeyboard() throws InterruptedException {
		// Start execution using keyboard
		mainWindow.getMenu().startTestPlanUsingKeyboard();
		
		// Verify test plan is running
		mainWindow.getMenu().checkTestPlanIsRunning();

		// Give some time to the execution start
		Thread.sleep(500);
		
		// Stop execution using keyboard
		mainWindow.getMenu().stopTestPlanUsingKeyboard();
		
		// Verify test plan is stopped
		mainWindow.getMenu().checkTestPlanIsStopped();
	}
	
	/**
	 * Test case that check stop a remote execution of the test plan
	 * is disabled while test is not running.
	 * 
	 * Test reference: 3.6.
	 */
	@Test
	public void stopRemoteTestExecutionUsingMenu() {
		// Start remote test using menu
		mainWindow.getMenu().stopTestPlanRemotelly();
	}
	
	/**
	 * Test case that select menu option to clear execution.
	 * 
	 * Test reference: 3.7.
	 */
	@Test
	public void clearExecutionUsingMenu() {
		// Clear execution using menu
		mainWindow.getMenu().clearTestPlanExecution();
	}
	
	/**
	 * Test case that select menu option to clear all execution.
	 * 
	 * Test reference: 3.8.
	 */
	@Test
	public void clearAllExecutionUsingMenu() {
		// Clear all execution using menu
		mainWindow.getMenu().clearAllTestPlanExecution();
	}
}
