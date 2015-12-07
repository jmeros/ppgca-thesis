package org.apache.jmeter.uispec4j;

import org.uispec4j.Window;

public class JMeterHelpWindow {
	private Window helpWindow;

	public JMeterHelpWindow(Window helpWindow) {
		this.helpWindow = helpWindow;
		
		// Assert window title is correct
		helpWindow.titleEquals("Help").check();
	}
	
	public void close() {
		helpWindow.dispose();
	}
}
