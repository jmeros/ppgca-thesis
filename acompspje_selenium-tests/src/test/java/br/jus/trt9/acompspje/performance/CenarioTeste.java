package br.jus.trt9.acompspje.performance;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.BDUtils.PerfilUsuario;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class CenarioTeste implements Runnable {
	private final static Logger LOGGER = Logger.getLogger(CenarioTeste.class.getName());
	
	final String _idExecucao;
	SessaoTeste _sessaoTeste;
	boolean usuariosLogados = false;
	
	public CenarioTeste(SessaoTeste sessaoTeste) {
		_idExecucao = "Sessao[" + sessaoTeste.sessao.getID_SESSAO_PJE() + "] ";
		_sessaoTeste = sessaoTeste;
	}

	public boolean isUsuariosLogados() {
		return usuariosLogados;
	}
	
	@Override
	public void run() {
		logInfo("Iniciando execução na sessão " + _sessaoTeste.sessao.getID_SESSAO_PJE());
		
		// Logar todos os usuário na sessão de teste
		logInfo("Logando os usuários na sessão de teste.");
		logarUsuariosNaSessao();
		usuariosLogados = true;
		
		// Montar de lista de usuário passivos na sessão (todos exceto o secretário)
		ArrayList<PainelAcompanhamento> usuariosPassivos = new ArrayList<PainelAcompanhamento>();
		usuariosPassivos.addAll(_sessaoTeste.assistentes);
		usuariosPassivos.addAll(_sessaoTeste.procuradores);
		usuariosPassivos.addAll(_sessaoTeste.magistrados);
		usuariosPassivos.addAll(_sessaoTeste.gabinetes);
		
		// Executar a rotina de teste que consiste em apregoar e julgar todos os processos não julgados
		RoteiroPautaSessao processoSelecionado;
		while ((processoSelecionado = encontrarProcessoNaoJulgado()) != null) {

			// Verificar que todos os usuários estão online
			logInfo("Verificando se todos os usuários estão online.");
			for (PainelAcompanhamento painel: usuariosPassivos) {
				try {
					painel.validarSessaoOnline();
				} catch (Exception e) {
					logWarn("Exceção ao verificar se os usuários estão online: " + e.getMessage());
				} catch (AssertionError e) {
					logWarn("Assert error ao tentar verificar se o usuário está online: " + e.getMessage());
				}
			}
			
			// Selecionar o processo a ser julgado no secretário
			logInfo("Selecionando um processo não julgado no secretário.");
			try {
				_sessaoTeste.secretario
					.selecionarProcesso(processoSelecionado)
					.validarProcessoSelecionado(processoSelecionado);
			} catch (Exception e) {
				logWarn("Exceção ao selecionar processo não julgado no secretário: " + e.getMessage());
			} catch (AssertionError e) {
				logWarn("Assert error ao selecionar processo não julgado no secretário: " + e.getMessage());
			}
			
			// Apregoar o processo no secretário
			logInfo("Apregoando o processo não julgado.");
			try {
				_sessaoTeste.secretario.marcarApregoado();
				processoSelecionado.mudarEstado(EstadoProcesso.APREGOADO);
			} catch (Exception e) {
				logWarn("Exceção ao marcar processo como apregoado: " + e.getMessage());
			} catch (AssertionError e) {
				logWarn("Assert error ao tentar marcar processo como apregoado: " + e.getMessage());
			}
			
			// Aguardar 5 segundos para chegar as alterações nos usuários passivos
			logInfo("Aguardando 5 segundos para validar processo selecionado.");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logWarn("Thread interrompida ao aguardar processo ser apregoado.");
			}
			
			// Verificar que o processo é selecionado automaticamente nos outros usuários (exceto assistentes)
			logInfo("Verificando se o processo apregoado foi selecionado nos usuários passivos.");
			for (PainelAcompanhamento painel: usuariosPassivos) {
				if (!_sessaoTeste.assistentes.contains(painel)) {
					try {
						painel.validarProcessoSelecionado(processoSelecionado);
					} catch (Exception e) {
						logWarn("Exceção ao verificar processo apregoado selecionado no usuário passivo: " + e.getMessage());
					} catch (AssertionError e) {
						logWarn("Assert error ao tentar verificar processo apregoado está selecionado no usuário passivo: " + e.getMessage());
					}
				}
			}

			// Verificar que a lista de processos é atualizada corretamente em todos os usuários
			logInfo("Verificando a lista de processos da sessão nos usuários passivos.");
			for (PainelAcompanhamento painel: usuariosPassivos) {
				try {
					painel.validarListaDeProcessos(_sessaoTeste.processos);
				} catch (Exception e) {
					logWarn("Exceção ao validar lista de processos em usuário passivo: " + e.getMessage());
				} catch (AssertionError e) {
					logWarn("Assert error ao tentar validar lista de processos em usuário passivo: " + e.getMessage());
				}
			}
			
			// Julgar processo apregoado no secretário
			logInfo("Marcar processo apregoado como julgado.");
			try {
				_sessaoTeste.secretario.marcarJulgado();
				processoSelecionado.mudarEstado(EstadoProcesso.JULGADO);
			} catch (Exception e) {
				logWarn("Exceção ao marcar processo como julgado: " + e.getMessage());
			} catch (AssertionError e) {
				logWarn("Assert error ao tentar marcar processo como julgado: " + e.getMessage());
			}

			// Aguardar 5 segundos para chegar as alterações nos usuários passivos
			logInfo("Aguardar 5 segundos para verificar processo selecionado.");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logWarn("Thread interrompida ao aguardar processo ser julgado.");
			}
			
			// Verificar que a lista de processos é atualizada corretamente em todos os usuários
			logInfo("Verificar a lista de processos nos usuário passivos.");
			for (PainelAcompanhamento painel: usuariosPassivos) {
				try {
					painel.validarListaDeProcessos(_sessaoTeste.processos);
				} catch (Exception e) {
					logWarn("Exceção ao validar lista de processos em usuário passivo: " + e.getMessage());
				} catch (AssertionError e) {
					logWarn("Assert error ao validar lista de processos em usuário passivo: " + e.getMessage());
				}
			}
			
		}
		logInfo("Execução do cenário de teste finalizada.");
	}

	/**
	 * Abre a página principal do sistema, autentica usando o usuário de teste e entra na
	 * sessão de teste com cada um dos usuário de teste.
	 */
	private void logarUsuariosNaSessao() {
		// Logar secretário, assistentes e procuradores (mesmo perfil)
		try {
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
			logInfo("Alterado perfil do usuário de teste para secretário.");
		} catch (Exception e) {
			logWarn("Exceção ao tentar alterar o perfil do usuário de teste para secretário: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar alterar o perfil do usuário de teste para secretário: " + e.getMessage());
		}
		try {
			_sessaoTeste.secretario = new TelaInicial(_sessaoTeste.driverSecretario)
				.autenticarUsuario()
				.entrarSessaoComoSecretarioPorNumeroPauta(_sessaoTeste.sessao.getID_SESSAO_PJE(), _sessaoTeste.processos.size());
			logInfo("Usuário com perfil de secretário autenticado no sistema.");
		} catch (Exception e) {
			logWarn("Exceção ao tentar autenticar o usuário no perfil de secretário: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar autenticar o usuário no perfil de secretário: " + e.getMessage());
		}
		try {
			for (WebDriver driver: _sessaoTeste.driverAssistentes) {
				_sessaoTeste.assistentes.add(
					new TelaInicial(driver)
						.autenticarUsuario()
						.entrarSessaoComoAssistentePorNumeroPauta(_sessaoTeste.sessao.getID_SESSAO_PJE(), _sessaoTeste.processos.size())
				);
			}
			logInfo("Usuários com perfil de assistente autenticados no sistema.");
		}
		catch (Exception e) {
			logWarn("Exceção ao tentar autenticar usuários no perfil de assistente: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar autenticar usuários no perfil de assistente: " + e.getMessage());
		}
		try {
			for (WebDriver driver: _sessaoTeste.driverProcuradores) {
				_sessaoTeste.procuradores.add(
					new TelaInicial(driver)
						.autenticarUsuario()
						.entrarSessaoComoProcuradorPorNumeroPauta(_sessaoTeste.sessao.getID_SESSAO_PJE(), _sessaoTeste.processos.size())
				);
			}
			logInfo("Usuários com perfil de procurador autenticados no sistema.");
		}
		catch (Exception e) {
			logWarn("Exceção ao tentar autenticar usuários no perfil de procurador: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar autenticar usuários no perfil de procurador: " + e.getMessage());
		}
		
		// Logar magistrados
		try {
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Magistrado);
			logInfo("Alterado perfil do usuário de teste para magistrado.");
		} catch (Exception e) {
			logWarn("Exceção ao tentar alterar o perfil do usuário de teste para magistrado: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar alterar o perfil de usuário de teste para magistrado: " + e.getMessage());
		}
		try {
			for (WebDriver driver: _sessaoTeste.driverMagistrados) {
				_sessaoTeste.magistrados.add(
					new TelaInicial(driver)
						.autenticarUsuario()
						.entrarSessaoComoMagistradoPorNumeroPauta(_sessaoTeste.sessao.getID_SESSAO_PJE(), _sessaoTeste.processos.size())
				);
			}
			logInfo("Usuários com perfil de magistrado autenticados no sistema.");
		}
		catch (Exception e) {
			logWarn("Exceção ao tentar autenticar usuários no perfil de magistrado: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar autenticar usuários no perfil de magistrado: " + e.getMessage());
		}

		// Logar gabinetes
		try {
			BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Gabinete);
			logInfo("Alterado perfil do usuário de teste para gabinete.");
		} catch (Exception e) {
			logWarn("Exceção ao tentar alterar o perfil do usuário de teste para gabinete: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar alterar o perfil do usuário de teste para gabinete: " + e.getMessage());
		}
		try {
			for (WebDriver driver: _sessaoTeste.driverGabinetes) {
				_sessaoTeste.gabinetes.add(
					new TelaInicial(driver)
						.autenticarUsuario()
						.entrarSessaoComoGabinetePorNumeroPauta(_sessaoTeste.sessao.getID_SESSAO_PJE(), _sessaoTeste.processos.size())
				);
			}
			logInfo("Usuários com perfil de gabinete autenticados no sistema.");			
		}
		catch (Exception e) {
			logWarn("Exceção ao tentar autenticar usuários no perfil de gabinete: " + e.getMessage());
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar autenticar usuário no perfil de gabinete: " + e.getMessage());
		}
		

		try {
			BDUtils.getInstance().recuperarPerfilUsuario();
		} catch (Exception e) {
			logWarn("Exceção ao tentar recuperar perfil original do usuário de teste.");
		} catch (AssertionError e) {
			logWarn("Assert error ao tentar recuperar perfil original do usuário de teste: " + e.getMessage());
		}
	}

	/**
	 * Procura por um processo não julgado na lista de processo da sessão de teste.
	 * 
	 * @return Um processo não julgado ou null caso nenhum processo seja encontrado.
	 */
	private RoteiroPautaSessao encontrarProcessoNaoJulgado() {
		for (RoteiroPautaSessao processo : _sessaoTeste.processos) {
			if (EstadoProcesso.valueOf(processo.getDS_STATUS()) == EstadoProcesso.NAO_JULGADO)
				return processo;
		}
		return null;
	}

	private void logInfo(String mensagem) {
		LOGGER.info(_idExecucao + mensagem);
	}

	private void logWarn(String mensagem) {
		LOGGER.warning(_idExecucao + mensagem);
	}

}
