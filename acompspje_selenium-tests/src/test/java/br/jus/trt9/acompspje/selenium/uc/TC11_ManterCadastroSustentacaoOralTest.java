package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelAcompanhamento;
import br.jus.trt9.acompspje.selenium.telas.PainelAssistente;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC11_ManterCadastroSustentacaoOralTest extends UsuarioUnicoTestTemplate{
	
	/**
	 * Cenário 1 - O usuário acessa uma sessão disponível com o perfil de assistente e cadastra
	 * uma nova inscrição para sustentação oral
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException 
	 */
	@Test
	public void cadastrarNovaInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException, InterruptedException {
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		validarListaProcessos(painel);
		painel.preencherDadosSustentacaoOral(true);
		painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");		
		painel.validarInclusaoSustentacaoOral();
	
	}
	
	/**
	 * Cenário 2 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta cadastrar
	 * uma nova inscrição para sustentação oral porém não preenche todos os campos
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void cadastrarNovaInscricaoParaSustentacaoOralSemPreencherCampos() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		

		validarListaProcessos(painel);
		
		painel.preencherDadosSustentacaoOral(false);
		
		painel.validarProblemaInclusaoSustentacaoOral(1);
	
	}
	
	/**
	 * Cenário 3 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta cadastrar
	 * uma nova inscrição para sustentação oral porém tenta usar uma posição não disponivel
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void cadastrarNovaInscricaoParaSustentacaoOralPosicaoOcupada() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		validarListaProcessos(painel);
		try{		
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");		
			painel.preencherDadosSustentacaoOral(true);
			//validarMensagemProcessoGenerica(aguardarSumir,mensagemComparada)			
			painel.validarProblemaInclusaoSustentacaoOral(2);
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}
	
	}
	
	/**
	 * Cenário 4 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta excluir
	 * uma inscrição já cadastrada anteriormente
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void excluirInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		

		validarListaProcessos(painel);
		
		painel.preencherDadosSustentacaoOral(true);
		painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
		painel.excluirInscricaoSustentacaoOral(true);
		painel.validarMensagemProcessoGenerica(true, "Inscrição excluída.");		
		painel.validarDetalhesSessaoAssistente(SESSAO);
	
	}	
	
	/**
	 * Cenário 5 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta excluir
	 * uma inscrição já cadastrada anteriormente, mas cancela para não fazer a exclusao
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void cancelarExclusaoInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		validarListaProcessos(painel);
		try{		
			//Para preencher todos os dados enviar: true
			painel.preencherDadosSustentacaoOral(true);
			//Para pressionar o botão cancelar enviar: false
			painel.excluirInscricaoSustentacaoOral(false);
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}		
	}
	
	/**
	 * Cenário 6 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta 
	 * alterar o status de uma inscrição já realizada.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void alterarPresencaInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
			// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		validarListaProcessos(painel);
		try{
			//Para preencher todos os dados enviar: true
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");			
			painel.alterarStatusSustentacaoOral();
			//validarMensagemProcessoGenerica(aguardarSumir,mensagemComparada)			
			painel.validarMensagemProcessoGenerica(true, "Informações alteradas com sucesso.");
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}
	}
	
	/**
	 * Cenário 7 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta 
	 * alterar os dados (Parte) de uma inscrição já realizada.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void alterarInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		

		validarListaProcessos(painel);
		try{		
			//Para preencher todos os dados enviar: true
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");			
			//alteraDadosSustentacaoOral(Posicao,AlterarParte,Salvar/Cancelar)
			painel.alterarDadosSustentacaoOral(1,true,true);
			//validarMensagemProcessoGenerica(aguardarSumir,mensagemComparada)			
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}
	}
	
	/**
	 * Cenário 8 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta 
	 * alterar os dados (Parte) de uma inscrição já realizada, porém cancela sem confirmar
	 * não alterando assim os dados.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void cancelaAlterarInscricaoParaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		

		validarListaProcessos(painel);
		try{
			//Para preencher todos os dados enviar: true
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
			//alteraDadosSustentacaoOral(Posicao,AlterarParte,Salvar)
			painel.alterarDadosSustentacaoOral(1,false,false);
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}
	}
	
	/**
	 * Cenário 9 - O usuário acessa uma sessão disponível com o perfil de assistente e tenta 
	 * alterar os dados de uma inscrição já realizada porém tenta utilizar uma posição já em uso
	 * recebendo assim uma mensagem de erro.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void alterarInscricaoPosicaoJaUtilizadaSustentacaoOral() throws IOException, SQLException, ClassNotFoundException {
		
		// Entrar na sessão de teste assistente
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		

		validarListaProcessos(painel);
		//Para preencher todos os dados enviar: true
		try{		
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
			//alteraDadosSustentacaoOral(Posicao,AlterarParte,Salvar/Cancelar)
			painel.alterarDadosSustentacaoOral(2,false,true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
			painel.preencherDadosSustentacaoOral(true);
			painel.validarMensagemProcessoGenerica(true, "Informações salvas com sucesso.");
			painel.alterarDadosSustentacaoOral(2,false,true);
			//validarMensagemProcessoGenerica(aguardarSumir,mensagemComparada)
			painel.validarProblemaInclusaoSustentacaoOral(2);
		}finally{
			RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO,null,true);		
			painel.preparaBancoTesteSustentacaoOral(processoApregoado, false);
		}
	}
	
	private void validarListaProcessos(PainelAssistente painel) throws ClassNotFoundException, SQLException, IOException {
		// Validar as informações sobre a sessão no cabeçalho da lista de processos
		painel.validarDetalhesSessao(SESSAO)
			.validarDetalhesSessaoAssistente(SESSAO)
		
		// Validar a lista de processos da sessao
			.validarListaDeProcessos(PROCESSOS_PAUTA);

	}
}
