package org.apache.jmeter.uispec4j;

import org.uispec4j.Window;

public class JMeterWorkbenchPanel {
	private Window mainWindow;
	
	protected JMeterWorkbenchPanel(Window mainWindow) {
		this.mainWindow = mainWindow;		
	}
	
	public void changeWorkbenchName(String newName) {
		mainWindow.getInputTextBox().setText(newName);
	}
}
