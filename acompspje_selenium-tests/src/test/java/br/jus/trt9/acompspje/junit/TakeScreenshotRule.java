package br.jus.trt9.acompspje.junit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshotRule extends TestWatcher {
	String currentTest = null;
	List<File> snapshotFiles = new ArrayList<File>();
	
	public void takeScreenshot(String testName, WebDriver driver) {
		currentTest = testName;
		if (driver != null)
			snapshotFiles.add(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE));
		else
			System.out.println("Não foi possível gerar screenshot do browser (driver é nulo).");
	}
	
	@Override
	protected void succeeded(Description description) {
		snapshotFiles.clear();
	}

	@Override
	protected void failed(Throwable e, Description description) {
		for (File file: snapshotFiles) {
			File destFile;
			try {
				destFile = new File(getClass().getClassLoader().getResource(".").toURI().getPath(), file.getName());
				FileUtils.copyFile(file, destFile);
				System.out.println("Screenshot [" + currentTest + "]: " + destFile.getAbsolutePath());
			} catch (Exception ex) {
				System.out.println("Exceção ao tentar criar o arquivo com o screenshot: " + ex.getMessage());
			}
		}
		snapshotFiles.clear();
	}

}
