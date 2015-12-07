package org.apache.jmeter.gui.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.NewDriver;
import org.apache.jmeter.uispec4j.JMeterMainWindow;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.UISpec4J;
import org.uispec4j.interception.MainClassAdapter;

public class FileMenuTest {
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
	 * Test case that changes something on the current project (rename WorkBench)
	 * and then start a new project by selection File->New on the menu.
	 * 
	 * Test reference: 1.1.
	 */
	@Test
	public void newFileUsingMenu() {
		// Select workbench
		mainWindow.getTree()
			.selectWorkbench()
		
		// Change workbench name on text box
			.changeWorkbenchName("New WorkBench");
		
		// Verify workbench name was changed
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  New WorkBench");
		
		// Select new option on File menu
		mainWindow.getMenu().newFileUsingFileMenu(false);
		
		// Verify workbench name was returned to default value
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench");
	}

	/**
	 * Test case that changes something on the current project (rename WorkBench)
	 * and then start a new project by using keyboard shortcut CTRL+n.
	 * 
	 * Test reference: 1.2.
	 */
	@Test
	public void newFileUsingAccelerator() {
		// Select workbench
		mainWindow.getTree()
			.selectWorkbench()
		
		// Change workbench name on text box
			.changeWorkbenchName("New WorkBench");
		
		// Verify workbench name was changed
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  New WorkBench");
		
		// Select new option on File menu
		mainWindow.getMenu().newFileUsingAccelerator(false);
		
		// Verify workbench name was returned to default value
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench");
	}

	/**
	 * Test case that changes something on the current project (rename WorkBench)
	 * and then start a new project by using mnemonic shortcut ALT+f (File) and n (New).
	 * 
	 * Test reference: 1.3.
	 */
	@Test
	public void newFileUsingMnemonic() {
		// Select workbench
		mainWindow.getTree()
			.selectWorkbench()
		
		// Change workbench name on text box
			.changeWorkbenchName("New WorkBench");
		
		// Verify workbench name was changed
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  New WorkBench");
		
		// Select new option on File menu
		mainWindow.getMenu().newFileUsingMnemonic(false);
		
		// Verify workbench name was returned to default value
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench");
	}

	/**
	 * Test case open a template testfile using the File menu
	 * and verify if the testfile was loaded sucessfully.
	 * 
	 * Test reference: 1.4.
	 */
	@Test
	public void openFileUsingMenu() {
		// Open proxy test plan (testfiles/proxy.jmx)
		String proxyJmx = new File(testfilesPath, "proxy.jmx").getAbsolutePath();
		mainWindow.getMenu().openFileUsingFileMenu(proxyJmx);
		
		// Check tree view was populated with proxy test plan
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"    Thread Group\n" +
				"      Simple Controller\n" +
				"        HTTP Request Defaults\n" +
				"  WorkBench");
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case open a template testfile using the keyboard shortcut
	 * (CTRL+o) and verify if the testfile was loaded sucessfully.
	 * 
	 * Test reference: 1.5.
	 */
	@Test
	public void openFileUsingAccelerator() {
		// Open proxy test plan (testfiles/proxy.jmx)
		String proxyJmx = new File(testfilesPath, "proxy.jmx").getAbsolutePath();
		mainWindow.getMenu().openFileUsingAccelerator(proxyJmx);
		
		// Check tree view was populated with proxy test plan
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"    Thread Group\n" +
				"      Simple Controller\n" +
				"        HTTP Request Defaults\n" +
				"  WorkBench");
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case open a template testfile using the keyboard mnemonic
	 * (ALT+f,o) and verify if the testfile was loaded sucessfully.
	 * 
	 * Test reference: 1.6.
	 */
	@Test
	public void openFileUsingMnemonic() {
		// Open proxy test plan (testfiles/proxy.jmx)
		String proxyJmx = new File(testfilesPath, "proxy.jmx").getAbsolutePath();
		mainWindow.getMenu().openFileUsingMnemonic(proxyJmx);
		
		// Check tree view was populated with proxy test plan
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"    Thread Group\n" +
				"      Simple Controller\n" +
				"        HTTP Request Defaults\n" +
				"  WorkBench");
		
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case open a invalid testfile using the File menu
	 * and verify if the error popup was showed.
	 * 
	 * Test reference: 1.7.
	 * 
	 * @throws IOException 
	 */
	@Test
	public void openInvalidFileUsingMenu() throws IOException {
		// Open invalid test plan (testfiles/invalid.jmx)
		String invalidJmx = new File(testfilesPath, "invalid.jmx").getAbsolutePath();
		mainWindow.getMenu().openInvalidFileUsingMenu(invalidJmx);
		
		// Check tree view was not populated with any information
		mainWindow.getTree().assertEquals(
				"Root\n" +
				"  Test Plan\n" +
				"  WorkBench");
		
		// Check that when file is new file is saved, the file chooser is displayed
		File emptyFile = File.createTempFile("empty", ".jmx");
		mainWindow.getMenu().saveFileUsingFileMenu(emptyFile.getAbsolutePath());
	}

	/**
	 * Test case save the empty test plan to file using the File
	 * menu and verify if the saved file is correct (compare with template).
	 * 
	 * Test reference: 1.8.
	 */
	@Test
	public void saveFileUsingMenu() throws Exception {
		// Save empty test plan using menu
		File savedFile = File.createTempFile("empty", ".jmx");
		mainWindow.getMenu().saveFileUsingFileMenu(savedFile.getAbsolutePath());
		
		// Compare saved file with template
		File templateFile = new File(testfilesPath, "empty.jmx");
		assertTrue(FileUtils.contentEquals(templateFile, savedFile));
	}

	/**
	 * Test case save the empty test plan to file using the keyboard
	 * shortcut and verify if the saved file is correct (compare with template).
	 * 
	 * Test reference: 1.9.
	 */
	@Test
	public void saveFileUsingAccelerator() throws Exception {
		// Save empty test plan using menu
		File savedFile = File.createTempFile("empty", ".jmx");
		mainWindow.getMenu().saveFileUsingAccelerator(savedFile.getAbsolutePath());
		
		// Compare saved file with template
		File templateFile = new File(testfilesPath, "empty.jmx");
		assertTrue(FileUtils.contentEquals(templateFile, savedFile));
	}

	/**
	 * Test case save the empty test plan to file using the keyboard
	 * shortcut and verify if the saved file is correct (compare with template).
	 * 
	 * Test reference: 1.10.
	 */
	@Test
	public void saveFileUsingMnemonic() throws Exception {
		// Save empty test plan using menu
		File savedFile = File.createTempFile("empty", ".jmx");
		mainWindow.getMenu().saveFileUsingMnemonic(savedFile.getAbsolutePath());
		
		// Compare saved file with template
		File templateFile = new File(testfilesPath, "empty.jmx");
		assertTrue(FileUtils.contentEquals(templateFile, savedFile));
		
		// Clear application
		mainWindow.getMenu().newFileUsingAccelerator(false);
	}

	/**
	 * Test case save as the opened test plan to other file using the File
	 * menu and verify if the saved file is correct (compare with original).
	 * 
	 * Test reference: 1.11.
	 */
	@Test
	public void saveFileAsUsingMenu() throws Exception {
		// Open proxy test plan using menu
		File originalFile = new File(testfilesPath, "assertion.jmx");
		mainWindow.getMenu().openFileUsingFileMenu(originalFile.getAbsolutePath());
		
		// Save As proxy test plan using menu
		File savedFile = File.createTempFile("assertion", ".jmx");
		mainWindow.getMenu().saveFileAsUsingFileMenu(savedFile.getAbsolutePath());
		
		// Reset application with new file
		mainWindow.getMenu().newFileUsingFileMenu(false);
		
		// Compare saved file with template
		assertTrue(FileUtils.contentEquals(originalFile, savedFile));
	}

	/**
	 * Test case save as the opened test plan to other file using the keyboard
	 * shortcut and verify if the saved file is correct (compare with original).
	 * 
	 * Test reference: 1.12.
	 */
	@Test
	public void saveFileAsUsingAccelerator() throws Exception {
		// Open assertion test plan using menu
		File originalFile = new File(testfilesPath, "assertion.jmx");
		mainWindow.getMenu().openFileUsingAccelerator(originalFile.getAbsolutePath());
		
		// Save As assertion test plan using menu
		File savedFile = File.createTempFile("assertion", ".jmx");
		mainWindow.getMenu().saveFileAsUsingAccelerator(savedFile.getAbsolutePath());
		
		// Reset application with new file
		mainWindow.getMenu().newFileUsingAccelerator(false);
		
		// Compare saved file with template
		assertTrue(FileUtils.contentEquals(originalFile, savedFile));
	}

	/**
	 * Test case save as the opened test plan to other file using the keyboard
	 * mnemonic (ALT+f,a) and verify if the saved file is correct (compare with original).
	 * 
	 * Test reference: 1.13.
	 */
	@Test
	public void saveFileAsUsingMnemonic() throws Exception {
		// Open assertion test plan using menu
		File originalFile = new File(testfilesPath, "assertion.jmx");
		mainWindow.getMenu().openFileUsingAccelerator(originalFile.getAbsolutePath());
		
		// Save As assertion test plan using menu
		File savedFile = File.createTempFile("assertion", ".jmx");
		mainWindow.getMenu().saveFileAsUsingMnemonic(savedFile.getAbsolutePath());
		
		// Reset application with new file
		mainWindow.getMenu().newFileUsingAccelerator(false);
		
		// Compare saved file with template
		assertTrue(FileUtils.contentEquals(originalFile, savedFile));
	}
	
}
