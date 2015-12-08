package br.jus.trt9.acompspje.selenium.telas.elementos;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Esta classe serve de classe auxiliar para emular o comportamento de um WebElement
 * encapsulando um iframe que necessita de mudança de contexto do WebDriver para que
 * seu conteúdo seja acessado.
 * 
 * @author jadermeros
 */
public class IFrameWebElement implements WebElement {

	private WebDriver _driver;
	private WebDriverWait _waitDriver;
	private By _iframe; // Localizador para o iframe dentro da janela
	private By _element; // Localizador do elemento dentro do iframe

	public IFrameWebElement(WebDriver driver, By iframe, By element) {
		_driver = driver;
		_waitDriver = new WebDriverWait(driver, 10);
		_iframe = iframe;
		_element = element;
	}
	
	@Override
	public void click() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		_element.findElement(_driver).click();
		_driver.switchTo().defaultContent();
	}

	@Override
	public void submit() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		_element.findElement(_driver).submit();
		_driver.switchTo().defaultContent();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		_element.findElement(_driver).sendKeys(keysToSend);
		_driver.switchTo().defaultContent();
	}

	@Override
	public void clear() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		_element.findElement(_driver).clear();
		_driver.switchTo().defaultContent();
	}

	@Override
	public String getTagName() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		String tagName = _element.findElement(_driver).getTagName();
		_driver.switchTo().defaultContent();
		return tagName;
	}

	@Override
	public String getAttribute(String name) {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		String attribute = _element.findElement(_driver).getAttribute(name);
		_driver.switchTo().defaultContent();
		return attribute;
	}

	@Override
	public boolean isSelected() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		boolean isSelected = _element.findElement(_driver).isSelected();
		_driver.switchTo().defaultContent();
		return isSelected;
	}

	@Override
	public boolean isEnabled() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		boolean isEnabled = _element.findElement(_driver).isEnabled();
		_driver.switchTo().defaultContent();
		return isEnabled;
	}

	@Override
	public String getText() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		String text = _element.findElement(_driver).getText();
		_driver.switchTo().defaultContent();
		return text;
	}

	@Override
	public List<WebElement> findElements(By by) {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		List<WebElement> list = _element.findElement(_driver).findElements(by);
		_driver.switchTo().defaultContent();
		return list;
	}

	@Override
	public WebElement findElement(By by) {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		WebElement element = _element.findElement(_driver).findElement(by);
		_driver.switchTo().defaultContent();
		return element;
	}

	@Override
	public boolean isDisplayed() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		boolean isDisplayed = _element.findElement(_driver).isDisplayed();
		_driver.switchTo().defaultContent();
		return isDisplayed;
	}

	@Override
	public Point getLocation() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		Point point = _element.findElement(_driver).getLocation();
		_driver.switchTo().defaultContent();
		return point;
	}

	@Override
	public Dimension getSize() {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		Dimension dimension = _element.findElement(_driver).getSize();
		_driver.switchTo().defaultContent();
		return dimension;
	}

	@Override
	public String getCssValue(String propertyName) {
		_waitDriver.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(_iframe));
		String cssValue = _element.findElement(_driver).getCssValue(propertyName);
		_driver.switchTo().defaultContent();
		return cssValue;
	}

}
