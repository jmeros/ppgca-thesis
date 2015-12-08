package br.jus.trt9.acompspje.selenium.telas;

import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.db.RoteiroPautaSessao;

public class PainelGabinete extends PainelAcompanhamento {

	public PainelGabinete(WebDriver driver) {
		super(driver);
	}

	@Override
	public PainelGabinete validarDetalhesProcessoSelecionado(RoteiroPautaSessao processo) {
		validarResumoProcesso(processo);
		validarDispositivo(processo, true);
		validarBotoesDeAcaoNoProcesso(processo, false);
		
		return this;
	}

}
