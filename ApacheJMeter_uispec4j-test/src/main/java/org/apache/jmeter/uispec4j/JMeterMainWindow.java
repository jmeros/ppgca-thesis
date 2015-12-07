package org.apache.jmeter.uispec4j;

import org.uispec4j.Window;

public class JMeterMainWindow {
	private JMeterMenu menu;
	private JMeterTree tree;
	
	public JMeterMainWindow(Window window) {
		this.menu = new JMeterMenu(window);
		this.tree = new JMeterTree(window);
		
		// Assert window title is correct
		window.titleEquals("Apache JMeter").check();
	}
	
	public JMeterMenu getMenu() {
		return menu;
	}
	
	public JMeterTree getTree() {
		return tree;
	}
}
