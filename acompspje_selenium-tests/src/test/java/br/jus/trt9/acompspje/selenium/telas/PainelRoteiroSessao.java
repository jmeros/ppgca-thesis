package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.db.SessaoJulgamento;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosRoteiroSessao;

public class PainelRoteiroSessao {

	private WebDriver _driver;
	protected WebDriverWait _waitDriver;

	protected PainelRoteiroSessao(WebDriver driver) {
		_driver = driver;
		_waitDriver = new WebDriverWait(_driver, 10);
	}

	protected void validarPaginaCarregada() {
		_waitDriver.until(ExpectedConditions
				.presenceOfElementLocated(ElementosRoteiroSessao
						.localizadorTabelaDeProcessos()));
	}
	

	public void validarRoteiroSessao(SessaoJulgamento sessao,
			List<RoteiroPautaSessao> processos) throws SQLException,
			ClassNotFoundException, IOException {
		// Ordenar os processos da lista por número sequencial
		Collections.sort(processos, new Comparator<RoteiroPautaSessao>() {
			@Override
			public int compare(RoteiroPautaSessao p1, RoteiroPautaSessao p2) {
				return p1.getNR_SEQUENCIAL_PROCESSO().compareTo(
						p2.getNR_SEQUENCIAL_PROCESSO());
			}
		});

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Assert.assertEquals(
				"Órgão julgador: "
						+ BDUtils.getInstance().buscarDescricaoOrgaoJulgador(
								sessao.getID_SESSAO_PJE()),
				ElementosRoteiroSessao.labelOrgaoJulgador(_driver).getText());
		Assert.assertEquals(dateFormat.format(sessao.getDT_SESSAO()),
				ElementosRoteiroSessao.labelDataSessao(_driver).getText());

		for (int indiceLinha = 1; indiceLinha <= processos.size(); indiceLinha++) {
			RoteiroPautaSessao processo = processos.get(indiceLinha - 1);
			
			if(processo.getNR_SEQUENCIAL_PROCESSO()!=null){
				Assert.assertEquals(processo.getNR_SEQUENCIAL_PROCESSO().intValue(), 
					Integer.parseInt(ElementosRoteiroSessao
							.colunaOrdemRoteiroSessao(_driver, indiceLinha)
							.getText()));
			}
			if(processo.getNR_PROCESSO_CNJ()!=null){
				Assert.assertEquals(
					processo.getNR_PROCESSO_CNJ(),
					ElementosRoteiroSessao.colunaNumeracaoCNJ(_driver,
							indiceLinha).getText());
			}
			if(processo.getNM_RELATOR()!=null){				
				Assert.assertEquals(
					processo.getNM_RELATOR(),
					ElementosRoteiroSessao.colunaRelatorRoteiroSessao(
							_driver, indiceLinha).getText());
			}
			if(processo.getNM_REVISOR() !=null ){
				Assert.assertEquals(
					processo.getNM_REVISOR(),
					ElementosRoteiroSessao.colunaSegundoVoto(_driver,
							indiceLinha).getText());
			}
			if (processo.getNM_TERCEIRO() != null) {
				Assert.assertEquals(
						processo.getNM_TERCEIRO(),
						ElementosRoteiroSessao.colunaTerceiroVoto(_driver,
								indiceLinha).getText());
			}
			if (processo.getDS_ADV_SUSTENTACAO_ORAL() != null) {
				Assert.assertEquals(
						processo.getDS_ADV_SUSTENTACAO_ORAL(),
						ElementosRoteiroSessao.colunaAdvogadoSustentacao(
								_driver, indiceLinha).getText());
			}
		}

	}



}
