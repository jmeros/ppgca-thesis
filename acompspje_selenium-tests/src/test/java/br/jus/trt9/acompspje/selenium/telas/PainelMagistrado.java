package br.jus.trt9.acompspje.selenium.telas;

import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.db.RoteiroPautaSessao;

public class PainelMagistrado extends PainelAcompanhamento {

	protected PainelMagistrado(WebDriver driver) {
		super(driver);
	}

	@Override
	public PainelMagistrado validarDetalhesProcessoSelecionado(RoteiroPautaSessao processo) {
		validarResumoProcesso(processo);
		validarDispositivo(processo, true);
		validarBotoesDeAcaoNoProcesso(processo, false);
		
		return this;
	}

}
