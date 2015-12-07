package org.apache.jmeter.uispec4j;

import org.uispec4j.Key;
import org.uispec4j.MenuItem;
import org.uispec4j.Tree;
import org.uispec4j.Window;
import org.uispec4j.assertion.Assertion;
import org.uispec4j.interception.PopupMenuInterceptor;

public class JMeterTree {
	private Window mainWindow;
	private Tree tree;
	
	protected JMeterTree(Window mainWindow) {
		this.mainWindow = mainWindow;
		this.tree = mainWindow.getTree();
		this.tree.setSeparator("->"); // Avoid problems with "/" on element name
	}
	
	public void selectTestPlan() {
		// Select workbench on tree
		tree.select("Test Plan");

		return;
	}

	public JMeterWorkbenchPanel selectWorkbench() {
		// Select workbench on tree
		tree.select("WorkBench");

		return new JMeterWorkbenchPanel(mainWindow);
	}

	public Assertion assertContent(String treeContent) {
		return tree.contentEquals(treeContent);
	}

	public void assertEquals(String treeContent) {
		tree.contentEquals(treeContent).check();
	}
	
	public void assertSelected(String path) {
		tree.selectionEquals(path).check();
	}

	public void select(String path) {
		tree.select(path);
	}

	public void assertElementEnabled(String path) {
		//tree.foregroundEquals(path, "red").check();
		MenuItem popup = PopupMenuInterceptor.run(tree.triggerRightClick(path));
		popup.getSubMenu("Disable").isEnabled().check();
		popup.typeKey(Key.ESCAPE);
	}

	public void assertElementDisabled(String path) {
		//tree.foregroundEquals(path, "red").check();
		MenuItem popup = PopupMenuInterceptor.run(tree.triggerRightClick(path));
		popup.getSubMenu("Activate").isEnabled().check();
		popup.typeKey(Key.ESCAPE);
	}

}
