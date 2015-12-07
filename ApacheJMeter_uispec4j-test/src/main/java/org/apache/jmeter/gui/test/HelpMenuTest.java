package org.apache.jmeter.gui.test;

import org.apache.jmeter.NewDriver;
import org.apache.jmeter.uispec4j.JMeterAboutWindow;
import org.apache.jmeter.uispec4j.JMeterHelpWindow;
import org.apache.jmeter.uispec4j.JMeterMainWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.UISpec4J;
import org.uispec4j.interception.MainClassAdapter;

public class HelpMenuTest {
	static {
		UISpec4J.init();
	}
	
	private static JMeterMainWindow mainWindow;

	@BeforeClass
	public static void startApplication() throws Exception {
		mainWindow = new JMeterMainWindow(
				new MainClassAdapter(NewDriver.class, new String[0]).getMainWindow());
	}
	
	/**
	 * Test case open the help window using the Help menu
	 * and verify it opens successfully.
	 * 
	 * Test reference: 5.1.
	 */
	@Test
	public void accessHelpFromMenu() {
		// Open help window from menu
		JMeterHelpWindow helpWindow = mainWindow.getMenu().helpWindowFromHelpMenu();
		
		// Close it
		helpWindow.close();
	}
	
	/**
	 * Test case open the help window using the keyboard
	 * shortcut and verify it opens successfully.
	 * 
	 * Test reference: 5.2.
	 */
	@Test
	public void accessHelpFromKeyboard() {
		// Open help window from menu
		JMeterHelpWindow helpWindow = mainWindow.getMenu().helpWindowFromKeyboard();
		
		// Close it
		helpWindow.close();
	}
	
	/**
	 * Test case open the about window using the Help menu
	 * and verify it opens successfully.
	 * 
	 * Test reference: 5.3.
	 */
	@Test
	public void accessAboutFromMenu() {
		// Open help window from menu
		JMeterAboutWindow aboutWindow = mainWindow.getMenu().aboutWindowFromMenu();
		
		// Close it
		aboutWindow.close();
	}
}
