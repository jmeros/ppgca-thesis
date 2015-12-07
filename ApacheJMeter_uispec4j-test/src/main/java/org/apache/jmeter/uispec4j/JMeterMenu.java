package org.apache.jmeter.uispec4j;

import org.junit.Assert;
import org.uispec4j.Key;
import org.uispec4j.MenuBar;
import org.uispec4j.MenuItem;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.BasicHandler;
import org.uispec4j.interception.FileChooserHandler;
import org.uispec4j.interception.PopupMenuInterceptor;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import javax.swing.AbstractButton;

public class JMeterMenu {
	private Window mainWindow;
	private MenuBar menuBar;

	protected JMeterMenu(Window mainWindow) {
		this.mainWindow = mainWindow;
		this.menuBar = mainWindow.getMenuBar();
	}
	
	private MenuItem accessFileMenu() {
		return menuBar.getMenu("File");
	}
	
	public void newFileUsingFileMenu(boolean confirmDialogExpected) {
		if (confirmDialogExpected) {
			WindowInterceptor
				.init(accessFileMenu().getSubMenu("New").triggerClick())
				.process(closeDialogWithoutSave())
				.run();
		}
		else
			accessFileMenu().getSubMenu("New").click();
	}

	public void newFileUsingAccelerator(boolean confirmDialogExpected) {
		if (confirmDialogExpected) {
			WindowInterceptor
				.init(new Trigger() {
					@Override
					public void run() throws Exception {
						mainWindow.typeKey(Key.control(Key.N));
					}
				})
				.process(closeDialogWithoutSave())
				.run();
		}
		else
			mainWindow.typeKey(Key.control(Key.N));
	}

	public void newFileUsingMnemonic(boolean confirmDialogExpected) {
		final MenuItem fileMenu = openFileMenuUsingMnemonic();

		// Workaround to check mnemonic on submenu
		// Get submenu by name and check its mnemonic
		final MenuItem newSubmenu = fileMenu.getSubMenu("New");
		Assert.assertEquals(Key.N.getCode(), ((AbstractButton) newSubmenu.getAwtComponent()).getMnemonic());
		
		if (confirmDialogExpected) {
			WindowInterceptor
				.init(new Trigger() {
					@Override
					public void run() throws Exception {
						// Use mnemonicNO (not working on uispec4j)
						//fileMenu.typeKey(Key.N);
						// Then click on it
						newSubmenu.click();
					}
				})
				.process(closeDialogWithoutSave())
				.run();
		}
		else {
			// Use mnemonicNO (not working on uispec4j)
			//fileMenu.typeKey(Key.N);
			// Then click on it
			newSubmenu.click();
		}
	}

	private MenuItem openFileMenuUsingMnemonic() {
		// Press ALT+f to open File menu
		return PopupMenuInterceptor
				.run(new Trigger() {
					@Override
					public void run() throws Exception {
						mainWindow.typeKey(Key.alt(Key.F));
					}
				});
	}

	private WindowHandler openFilenameOnFileChooser(String filename) {
		return FileChooserHandler.init()
		           .titleEquals("Open")
		           .assertAcceptsFilesOnly()
		           .assertIsOpenDialog()
		           .select(filename);
	}

	public void openFileUsingFileMenu(String filename) {
		WindowInterceptor
		   .init(accessFileMenu().getSubMenu("Open...").triggerClick())
		   .process(openFilenameOnFileChooser(filename))
		   .run();
	}

	public void openFileUsingAccelerator(String filename) {
		WindowInterceptor
		   .init(new Trigger() {
				@Override
				public void run() throws Exception {
					mainWindow.typeKey(Key.control(Key.O));
				}
		   })
		   .process(openFilenameOnFileChooser(filename))
		   .run();
	}

	public void openFileUsingMnemonic(String filename) {
		final MenuItem fileMenu = openFileMenuUsingMnemonic();
		
		// Workaround to check mnemonic on submenu
		// Get submenu by name and check its mnemonic
		final MenuItem openSubmenu = fileMenu.getSubMenu("Open...");
		Assert.assertEquals(Key.O.getCode(), ((AbstractButton) openSubmenu.getAwtComponent()).getMnemonic());
		
		WindowInterceptor
			.init(new Trigger() {
				@Override
				public void run() throws Exception {
					// Use mnemonic O (not working on uispec4j)
					//fileMenu.typeKey(Key.O);
					// Then click on it
					openSubmenu.click();
				}
			})
//			.init(fileMenu.getSubMenu("Open").triggerClick())
			.process(openFilenameOnFileChooser(filename))
			.run();
	}

	public void openInvalidFileUsingMenu(String filename) {
		WindowInterceptor
		   .init(accessFileMenu().getSubMenu("Open...").triggerClick())
		   .process(openFilenameOnFileChooser(filename))
		   .processWithButtonClick("Error", "OK")
		   .run();
	}

	private WindowHandler saveFilenameOnFileChooser(String filename) {
		return FileChooserHandler.init()
		           .titleEquals("Save")
		           .assertAcceptsFilesOnly()
		           .assertIsSaveDialog()
		           .select(filename);
	}

	public void saveFileUsingFileMenu(String filename) {
		WindowInterceptor
			.init(accessFileMenu().getSubMenu("Save Test Plan").triggerClick())
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	public void saveFileUsingAccelerator(String filename) {
		WindowInterceptor
		.init(new Trigger() {
			@Override
			public void run() throws Exception {
				mainWindow.typeKey(Key.control(Key.S));
			}
	   })
		.process(saveFilenameOnFileChooser(filename))
		.run();
	}

	public void saveFileUsingMnemonic(String filename) {
		final MenuItem fileMenu = openFileMenuUsingMnemonic();

		// Workaround to check mnemonic on submenu
		// Get submenu by name and check its mnemonic
		final MenuItem saveSubmenu = fileMenu.getSubMenu("Save Test Plan");
		Assert.assertEquals(Key.S.getCode(), ((AbstractButton) saveSubmenu.getAwtComponent()).getMnemonic());
		
		WindowInterceptor
			.init(new Trigger() {
				@Override
				public void run() throws Exception {
					// Use mnemonic S (not working on uispec4j)
					//fileMenu.typeKey(Key.S);
					// Then click on it
					saveSubmenu.click();
				}
			})
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	public void saveFileAsUsingFileMenu(String filename) {
		WindowInterceptor
			.init(accessFileMenu().getSubMenu("Save Test Plan as").triggerClick())
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	public void saveFileAsUsingAccelerator(String filename) {
		WindowInterceptor
			.init(new Trigger() {
				@Override
				public void run() throws Exception {
					mainWindow.typeKey(Key.control(Key.A));
				}
		   })
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	public void saveFileAsUsingMnemonic(String filename) {
		final MenuItem fileMenu = openFileMenuUsingMnemonic();

		// Workaround to check mnemonic on submenu
		// Get submenu by name and check its mnemonic
		final MenuItem saveAsSubmenu = fileMenu.getSubMenu("Save Test Plan as");
		Assert.assertEquals(Key.A.getCode(), ((AbstractButton) saveAsSubmenu.getAwtComponent()).getMnemonic());
		
		WindowInterceptor
			.init(new Trigger() {
				@Override
				public void run() throws Exception {
					// Use mnemonic A (not working on uispec4j)
					//fileMenu.typeKey(Key.A);
					// Then click on it
					saveAsSubmenu.click();
				}
			})
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	private WindowHandler closeDialogWithoutSave() {
		return new WindowHandler() {
		    public Trigger process(Window dialog) {
		        return dialog.getButton("No").triggerClick();
		      }
		};
	}

	public void addThreadGroupFromMenu() {
		accessAddSubMenu().getSubMenu("Thread Group").click();
	}

	public void addListenerElementFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Listener").getSubMenu(elementName).click();
	}

	public void addConfigElementFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Config Element").getSubMenu(elementName).click();
	}

	public void addAssertionFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Assertions").getSubMenu(elementName).click();
	}

	public void addModifierFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Modifiers").getSubMenu(elementName).click();
	}

	public void addResponseBasedModifierFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Response Based Modifiers").getSubMenu(elementName).click();
	}

	public void addTimerFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Timer").getSubMenu(elementName).click();
	}

	public void addLogicControllerFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Logic Controller").getSubMenu(elementName).click();
	}

	public void addSamplerFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Sampler").getSubMenu(elementName).click();
	}

	public void addNonTestElementFromMenu(String elementName) {
		accessAddSubMenu().getSubMenu("Non-Test Element").getSubMenu(elementName).click();
	}

	public void removeSelectedElementUsingMenu() {
		accessEditMenu().getSubMenu("Remove").click();
	}

	public void cutSelectedElementUsingMenu() {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Cut").triggerClick())
			.process(closeFeatureNotImplementedDialog("Cut"))
			.run();
	}

	public void copySelectedElementUsingMenu() {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Copy").triggerClick())
			.process(closeFeatureNotImplementedDialog("Copy"))
			.run();
	}

	public void pasteElementUsingMenu() {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Paste").triggerClick())
			.process(closeFeatureNotImplementedDialog("Paste"))
			.run();
	}

	public void pasteAsInsertElementUsingMenu() {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Paste As Insert").triggerClick())
			.process(closeFeatureNotImplementedDialog("Paste Insert"))
			.run();
	}

	public void openFileUsingEditMenu(String filename) {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Open...").triggerClick())
			.process(openFilenameOnFileChooser(filename))
			.run();
	}

	public void saveFileAsUsingEditMenu(String filename) {
		WindowInterceptor
			.init(accessEditMenu().getSubMenu("Save As...").triggerClick())
			.process(saveFilenameOnFileChooser(filename))
			.run();
	}

	public void enableSelectedElement() {
		accessEditMenu().getSubMenu("Activate").click();
	}

	public void disableSelectedElement() {
		accessEditMenu().getSubMenu("Disable").click();
	}

	public void assertEnableOptionIsDisabled() {
		Assert.assertFalse(accessEditMenu().getSubMenu("Activate").isEnabled().isTrue());
	}

	public void assertDisableOptionIsDisabled() {
		Assert.assertFalse(accessEditMenu().getSubMenu("Disable").isEnabled().isTrue());
	}

	public JMeterHelpWindow helpWindowFromEditMenu() {
		// Open help window from menu
		return new JMeterHelpWindow(WindowInterceptor.run(
			accessEditMenu().getSubMenu("Help").triggerClick()));
	}

	public void startTestPlanUsingMenu() {
		// Start execution using Start menu option
		accessRunMenu().getSubMenu("Start").click();
	}

	public void startTestPlanUsingKeyboard() {
		mainWindow.typeKey(Key.control(Key.R));
	}

	public void startTestPlanRemotelly() {
		// Try to start remote execution and expect error message
		WindowInterceptor
			.init(accessRunMenu().getSubMenu("Remote Start").getSubMenu("127.0.0.1").triggerClick())
			.process("Error", BasicHandler
				.init()
				.assertContainsText("Bad call to remote host")
				.triggerButtonClick("OK"))
			.run();
	}

	public void stopTestPlanUsingMenu() {
		WindowInterceptor
			.init(accessRunMenu().getSubMenu("Stop").triggerClick())
			.processTransientWindow("Stopping Test")
			.run();
	}

	public void stopTestPlanUsingKeyboard() {
		WindowInterceptor
			.init(new Trigger() {
				@Override
				public void run() throws Exception {
					mainWindow.typeKey(Key.control(Key.DECIMAL));
				}
			})
			.processTransientWindow("Stopping Test")
			.run();
	}

	public void stopTestPlanRemotelly() {
		Assert.assertFalse(accessRunMenu().getSubMenu("Remote Stop").getSubMenu("127.0.0.1").isEnabled().isTrue());
	}

	public void checkTestPlanIsRunning() {
		// Check if stop option is enabled (test plan is running)
		accessRunMenu().getSubMenu("Stop").isEnabled().check();
	}

	public void checkTestPlanIsStopped() {
		// Check if start option is enabled (test plan is stopped)
		accessRunMenu().getSubMenu("Start").isEnabled().check();
	}

	public void clearTestPlanExecution() {
		accessRunMenu().getSubMenu("Clear").click();
	}

	public void clearAllTestPlanExecution() {
		accessRunMenu().getSubMenu("Clear All").click();
	}

	public JMeterFunctionHelperWindow openFunctionHelperWindow() {
		// Open funcion helper form menu
		return new JMeterFunctionHelperWindow(
				WindowInterceptor
					.run(accessOptionsMenu().getSubMenu("Function Helper Dialog").triggerClick()));
	}

	public JMeterHelpWindow helpWindowFromHelpMenu() {
		// Open help window from menu
		return new JMeterHelpWindow(WindowInterceptor.run(
			accessHelpMenu().getSubMenu("Help").triggerClick()));
	}

	public JMeterHelpWindow helpWindowFromKeyboard() {
		// Open help window from keyboard
		return new JMeterHelpWindow(WindowInterceptor.run(new Trigger() {
			@Override
			public void run() throws Exception {
				mainWindow.typeKey(Key.control(Key.H));
			}
	   }));
	}

	public JMeterAboutWindow aboutWindowFromMenu() {
		// Open help window from menu
		return new JMeterAboutWindow(WindowInterceptor.run(
				accessHelpMenu().getSubMenu("About Apache JMeter").triggerClick()));
	}

	private MenuItem accessEditMenu() {
		return menuBar.getMenu("Edit");
	}

	private MenuItem accessAddSubMenu() {
		return accessEditMenu().getSubMenu("Add");
	}

	private MenuItem accessRunMenu() {
		return menuBar.getMenu("Run");
	}

	private MenuItem accessOptionsMenu() {
		return menuBar.getMenu("Options");
	}

	private MenuItem accessHelpMenu() {
		return menuBar.getMenu("Help");
	}

	private WindowHandler closeFeatureNotImplementedDialog(final String featureName) {
		return new WindowHandler() {			
			@Override
			public Trigger process(Window window) throws Exception {
				window.containsLabel("Sorry, this feature (" + featureName + ") not yet implemented").check();
				return window.getButton("OK").triggerClick();
			}
		};
	}
}
