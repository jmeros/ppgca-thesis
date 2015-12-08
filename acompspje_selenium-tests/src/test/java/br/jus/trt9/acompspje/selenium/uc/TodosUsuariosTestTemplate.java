package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

/**
 * Esta classe apesar de não implementar nenhum caso de teste, implementa as atividades
 * comuns de inicialização e finalização dos casos de teste que testam todos os tipos
 * de usuário simultaneamente.
 * 
 * @author jadermeros
 */
public abstract class TodosUsuariosTestTemplate extends UsuarioUnicoTestTemplate {
	enum Usuario {
		ASSISTENTE,
		GABINETE,
		MAGISTRADO,
		PROCURADOR,
		SECRETARIO
	}

	HashMap<Usuario, WebDriver> drivers = new HashMap<Usuario, WebDriver>();
	HashMap<Usuario, PainelAcompanhamento> paineis = new HashMap<Usuario, PainelAcompanhamento>();
	
	@Before
	public void inicializarMultiplosNavegadores() throws IOException {
		// Inicializar um browser para cada usuário
		for (Usuario u: Usuario.values()) {
			if (u == Usuario.SECRETARIO) {
				// Usar o browser aberto pelo UsuarioUnicoTestTemplate ao secretário
				drivers.put(Usuario.SECRETARIO, driver);
			}
			else {
				// Para os outros usuários, abrir novas janelas
				drivers.put(u, createWebDriver());
			}
		}
	}

	@After
	public void fecharTodosNavegadores() {
		// Browser do secretário será fechado pelo UsuarioUnicoTestTemplate.fecharNavegador()
		drivers.remove(Usuario.SECRETARIO);
		
		// Fechar as outras janelas
		for (WebDriver wd : drivers.values()) {
			wd.quit();
		}
	}

	/**
	 * Acessa a mesma sessão de julgamento com todos os usuário disponíveis para teste.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	protected void logarTodosOsUsuarios() throws IOException, SQLException, ClassNotFoundException {
		// Logar todos os usuário na mesma sessão
		logarUsuarios(Usuario.values());
	}
	
	protected void logarUsuarios(Usuario[] usuarios) throws IOException, SQLException, ClassNotFoundException {
		// Logar os usuário desejados na mesma sessão
		for (Usuario u: usuarios) {
			try {
				switch (u) {
					case ASSISTENTE:
						// Alterar perfil do usuário de teste
						BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
						// Entrar no sistema
						paineis.put(Usuario.ASSISTENTE, new TelaInicial(drivers.get(u)).autenticarUsuario().entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size()));
						break;
				
					case GABINETE:
						// Alterar perfil do usuário de teste
						BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
						// Entrar no sistema
						paineis.put(Usuario.GABINETE, new TelaInicial(drivers.get(u)).autenticarUsuario().entrarSessaoComoGabinetePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size()));
						break;
						
					case MAGISTRADO:
						// Alterar perfil do usuário de teste
						BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
						// Entrar no sistema
						paineis.put(Usuario.MAGISTRADO, new TelaInicial(drivers.get(u)).autenticarUsuario().entrarSessaoComoMagistradoPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size()));
						break;
						
					case PROCURADOR:
						// Alterar perfil do usuário de teste
						BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
						// Entrar no sistema
						paineis.put(Usuario.PROCURADOR, new TelaInicial(drivers.get(u)).autenticarUsuario().entrarSessaoComoProcuradorPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size()));
						break;
						
					case SECRETARIO:
						// Alterar perfil do usuário de teste
						BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
						// Entrar no sistema
						paineis.put(Usuario.SECRETARIO, new TelaInicial(drivers.get(u)).autenticarUsuario().entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size()));
						break;
						
					default:
						Assert.fail("Login do usuário de teste utilizando o perfil " + u + " não foi implementado.");
				}
			}
			finally {
				// Alterar o perfil do usuário para o perfil padrão
				BDUtils.getInstance().recuperarPerfilUsuario();
			}
		}
	}

}
