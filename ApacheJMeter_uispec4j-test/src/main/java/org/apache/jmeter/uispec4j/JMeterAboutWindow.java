package org.apache.jmeter.uispec4j;

import org.uispec4j.Window;

public class JMeterAboutWindow {
	private Window aboutWindow;

	public JMeterAboutWindow(Window aboutWindow) {
		this.aboutWindow = aboutWindow;
		
		// Assert window title is correct
		aboutWindow.titleEquals("About Apache JMeter...").check();
	}
	
	public void close() {
		aboutWindow.dispose();
	}
}
