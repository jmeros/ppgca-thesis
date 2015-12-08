package br.jus.trt9.acompspje.selenium.telas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import br.jus.trt9.acompspje.db.ComposicaoSessao;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.elementos.ElementosPainelAcompanhamento;

public class PainelSecretario extends PainelAcompanhamento {
	
	protected PainelSecretario(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public PainelSecretario selecionarProcesso(RoteiroPautaSessao processo) 
			throws SQLException ,ClassNotFoundException ,IOException {
		// Usar frame com conteúdo do dispositivo para garantir mudança na tela após o click
		_waitDriver.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ElementosPainelAcompanhamento.localizadorEditorDispositivo()));
		WebElement editorDispositivo = ElementosPainelAcompanhamento.localizadorEditorDispositivo().findElement(_driver);
		
		PainelSecretario p = (PainelSecretario) super.selecionarProcesso(processo);
		
		// Aguardar até que o frame antigo seja removido da interface após o click (redesenhado com novo valor)
		_waitDriver.until(ExpectedConditions.stalenessOf(editorDispositivo));
		
		return p;
	}
	
	@Override
	public PainelSecretario validarDetalhesProcessoSelecionado(RoteiroPautaSessao processo) {
		validarResumoProcesso(processo);
		validarDispositivo(processo, true);
		validarBotoesDeAcaoNoProcesso(processo, true);

		return this;
	}

	public void marcarJulgado() {
		ElementosPainelAcompanhamento.botaoJulgado(_driver).click();
	}

	public void marcarApregoado() {
		ElementosPainelAcompanhamento.botaoApregoar(_driver).click();
	}

	public void marcarNaoJulgado() {
		ElementosPainelAcompanhamento.botaoRemover(_driver).click();
	}

	public void marcarAdiado() {
		ElementosPainelAcompanhamento.botaoAdiar(_driver).click();
	}

	public void marcarRetirado() {
		ElementosPainelAcompanhamento.botaoRetirar(_driver).click();
	}

	public void marcarRevisar() {
		ElementosPainelAcompanhamento.botaoRevisar(_driver).click();
	}

	public void marcarVistaMesa() {
		ElementosPainelAcompanhamento.botaoVistaMesa(_driver).click();
	}

	public void marcarVistaRegimental() {
		ElementosPainelAcompanhamento.botaoVistaRegimental(_driver).click();
	}

	/**
	 * Procura pela linha dentro da lista de magistrados diponíveis para vista regimental
	 * onde encontra-se o magistrado com o nome desejado.
	 * 
	 * @param nome O nome do magistrado procurado.
	 * 
	 * @return WebElement referente a linha da lista onde o magistrado se encontra.
	 */
	protected int procurarMagistradoNaLista(String nomeMagistrado) {
		// Procurar o processo na lista de magistrados para vista regimental
		int indiceDaLista = 1;
		try {
			while (!ElementosPainelAcompanhamento.nomeMagistradoNaListaVistaRegimental(_driver, indiceDaLista).getText().equals(nomeMagistrado)) {
				indiceDaLista++;
			}
		}
		catch(NoSuchElementException e) {
			Assert.fail("Não foi possível encontrar o magistrado '" + nomeMagistrado + "' na lista de magistrados para vista regimental.");
		}
		return indiceDaLista;
	}

	public void selecionarMagistrado(String nomeMagistrado) {
		// Validar que tela para seleção do magistrado para vista regimental está disponível
		_waitDriver.until(ExpectedConditions.visibilityOfElementLocated(ElementosPainelAcompanhamento.localizadorPopupVistaRegimental()));
		
		// Procurar pela linha contendo nome do magistrado na lista de magistrados disponíveis
		int indiceLista = procurarMagistradoNaLista(nomeMagistrado);
		
		// Seleciona o magistrado
		ElementosPainelAcompanhamento.checkboxMagistradoVistaRegimental(_driver, indiceLista).click();
	}

	public void confirmarVistaRegimental() {
		// Aguardar até que o botão de confirmação fique habilitado
		_waitDriver.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return !ElementosPainelAcompanhamento.botaoConfirmarVistaRegimental(driver).getAttribute("class").contains("ui-state-disabled");
				} catch (StaleElementReferenceException e) {
					return false;
				}
			}
		});
		
		// Clicar no botâo
		ElementosPainelAcompanhamento.botaoConfirmarVistaRegimental(_driver).click();
	}

	public void marcarPreferencial() {
		ElementosPainelAcompanhamento.botaoPreferencial(_driver).click();
	}

	public void validarListaDeMagistrados(List<ComposicaoSessao> magistrados) {
		// Criar cópia da lista para validação
		List<ComposicaoSessao> listaControle = new ArrayList<ComposicaoSessao>(magistrados);
		
		// Aguardar até que dialog da vista regimental apareça
		_waitDriver.until(ExpectedConditions.presenceOfElementLocated(ElementosPainelAcompanhamento.localizadorPopupVistaRegimental()));
		
		// Verificar que cada uma das linhas da lista possui um magistrado válido
		int indice = 0;
		boolean fimDaLista = false;
		while (!fimDaLista) {
			try {
				// Pega o próximo nome de magistrado na lista
				WebElement nomeMagistrado = ElementosPainelAcompanhamento.nomeMagistradoNaListaVistaRegimental(_driver, ++indice);
				
				// Encontra o magistrado na lista de controle passada como parâmetro
				ComposicaoSessao magistrado = null;
				for (ComposicaoSessao cs : listaControle) {
					if (cs.getNM_MAGISTRADO_TITULAR().equals(nomeMagistrado.getText())) {
						magistrado = cs;
					}
				}
				
				// Verifica que o magistrado foi encontrado e remove o nome da lista de controle
				Assert.assertNotNull("Magistrado presenta na lista não foi encontrado.", magistrados);
				listaControle.remove(magistrado);
			}
			catch (NoSuchElementException e) {
				// Quando chegar ao final da lista de nomes, nenhum magistrado deve permanecer na lista de controle
				Assert.assertTrue("Um ou mais magistrados não foram encontrados na lista diponível para vista regimental.", listaControle.isEmpty());
				fimDaLista = true;
			}
		}
	}

}
