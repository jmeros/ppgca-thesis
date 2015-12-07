package org.apache.jmeter.uispec4j;

import org.uispec4j.Table;
import org.uispec4j.Window;

public class JMeterFunctionHelperWindow {
	private Window functionHelperWindow;

	public JMeterFunctionHelperWindow(Window funcHelperWindow) {
		this.functionHelperWindow = funcHelperWindow;
		
		functionHelperWindow.titleEquals("Function Helper").check();
	}
	
	public void selectFunction(String functionName) {
		functionHelperWindow.getComboBox().select(functionName);
	}

	public void generateFunction() {
		functionHelperWindow.getButton("Generate").click();
	}

	public void addParameters(String[] params) {
		Table paramsTable = functionHelperWindow.getTable();
		
		// Check parameter count is equal to available table lines
		paramsTable.rowCountEquals(params.length);
		
		// Write parameters to table
		for (int i=0; i<params.length; i++)
			paramsTable.editCell(i, 1, params[i], true);
	}

	public void assertFunctionStringEquals(String functionResult) {
		functionHelperWindow.getInputTextBox().textEquals(functionResult).check();
	}

	public void close() {
		// Force window to close
		functionHelperWindow.dispose();
		
		// Release window pointer
		functionHelperWindow = null;
	}
}
