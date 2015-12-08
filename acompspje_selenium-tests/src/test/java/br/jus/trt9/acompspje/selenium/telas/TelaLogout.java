package br.jus.trt9.acompspje.selenium.telas;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosTelaLogout;

public class TelaLogout {

	private WebDriver _driver;
	private WebDriverWait _waitDriver;
	
	public TelaLogout(WebDriver driver) {
		_driver = driver;
		_waitDriver = new WebDriverWait(driver, 10);
	}

	public void validarPaginaCarregada() {
		// Aguardar até que a mensagem de logout com sucesso seja exibida
		_waitDriver.until(ExpectedConditions.textToBePresentInElementLocated(ElementosTelaLogout.localizadorMensagemLogout(), "A sua saída do sistema foi efetuada com sucesso"));
		
		// Validar título da página
		Assert.assertEquals("Tribunal Regional do Trabalho da 9ª Região", _driver.getTitle());
	}

}
