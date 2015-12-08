package br.jus.trt9.acompspje.selenium.telas.elementos;

import org.openqa.selenium.By;

public class ElementosVotoCompleto {

	private ElementosVotoCompleto() {}

	/**
	 * Localizador para o iframe onde o conteúdo do voto completo é mostrado.
	 * 
	 * @return O localizador que representa o iframe contendo o voto completo.
	 */
	public static By localizadorConteudoVotoCompleto() {
		return By.xpath(".//*[@id='popVotoCompleto']//iframe");
	}
}
