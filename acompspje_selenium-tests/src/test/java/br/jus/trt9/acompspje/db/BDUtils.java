package br.jus.trt9.acompspje.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;

import oracle.sql.CLOB;

public class BDUtils {
	public enum PerfilUsuario {
		Secretario("S"),
		Magistrado("J"),
		Gabinete("G"),
		SecretarioGabinete("X")
		
		;
		
		private String _codigoPerfil;
		
		private PerfilUsuario(String codigo) {
			_codigoPerfil = codigo;
		}
		
		public String getCodigoPerfil() {
			return _codigoPerfil;
		}
	}
	
	private static BDUtils instance;
	
	private Connection conexao;
	
	private BDUtils() {}

	public static BDUtils getInstance() throws ClassNotFoundException, SQLException, IOException {
		if (instance == null) {
			instance = new BDUtils();
		}
		if (instance.conexao == null) {
			instance.conectar();
		}
		return instance;
	}

	private void conectar() throws ClassNotFoundException, SQLException, IOException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		// Forçar carga do driver do Oracle
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conexao = DriverManager.getConnection(
				propriedades.getProperty("enderecoBD"),
				propriedades.getProperty("usuarioBD"),
				propriedades.getProperty("senhaBD"));
	}
	
	/**
	 * Limpa todos os dados de uma determinada sessão no banco de dados.
	 * 
	 * @param numeroSessao O número da sessão que terá os dados removidos.
	 * 
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void limparDados(int numeroSessao) throws SQLException, ClassNotFoundException, IOException {
		// Limpar dados antigos da tabela
		conexao.prepareStatement("delete from orapje.inscricao_sustentacao_oral where id_pauta_sessao in (select id_pauta_sessao from orapje.pauta_sessao_roteiro where id_sessao_pje = " + numeroSessao + ")").executeUpdate();
		conexao.prepareStatement("delete from orapje.vista_regimental where id_pauta_sessao in (select id_pauta_sessao from orapje.pauta_sessao_roteiro where id_sessao_pje = " + numeroSessao + ")").executeUpdate();
		conexao.prepareStatement("delete from orapje.composicao_sessao where id_sessao_pje = " + numeroSessao).executeUpdate();
		conexao.prepareStatement("delete from orapje.pauta_sessao_roteiro_comp where id_pauta_sessao in (select id_pauta_sessao from orapje.pauta_sessao_roteiro where id_sessao_pje = " + numeroSessao + ")").executeUpdate();
		conexao.prepareStatement("delete from orapje.pauta_sessao_roteiro where id_sessao_pje = " + numeroSessao).executeUpdate();
		conexao.prepareStatement("delete from orapje.sessao_julgamento where id_sessao_pje = " + numeroSessao).executeUpdate();
	}
	
	/**
	 * Carrega a lista de processos para o banco de dados.
	 * @throws SQLException 
	 */
	public void carregarDados(SessaoJulgamento sessao, List<RoteiroPautaSessao> processos, List<ComposicaoSessao> magistrados, List<VistaRegimental> vistasRegimentais) throws SQLException {
		// Preparar o insert para sessão de julgamento
		PreparedStatement psSessaoJulgamento = conexao.prepareStatement("insert into orapje.sessao_julgamento (ID_SESSAO_PJE, DT_SESSAO, ID_ORGAO_JULGADOR_COLEGIADO, DT_IMPORTACAO, NM_USUARIO_IMPORTACAO, DS_SALA, DS_TIPO_SESSAO, NM_PROCURADOR, IN_SEXO, IN_STATUS, CD_MAGISTRADO_PRESIDENTE) " +
								 										"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		psSessaoJulgamento.setInt(1, sessao.getID_SESSAO_PJE());
		psSessaoJulgamento.setTimestamp(2, new java.sql.Timestamp(sessao.getDT_SESSAO().getTime()));
		psSessaoJulgamento.setInt(3, sessao.getID_ORGAO_JULGADOR_COLEGIADO());
		if (sessao.getDT_IMPORTACAO() != null)
			psSessaoJulgamento.setTimestamp(4, new java.sql.Timestamp(sessao.getDT_IMPORTACAO().getTime()));
		else
			psSessaoJulgamento.setTimestamp(4, null);
		psSessaoJulgamento.setString(5, sessao.getNM_USUARIO_IMPORTACAO());
		psSessaoJulgamento.setString(6, sessao.getDS_SALA());
		psSessaoJulgamento.setString(7, sessao.getDS_TIPO_SESSAO());
		psSessaoJulgamento.setString(8, sessao.getNM_PROCURADOR());
		if (sessao.getIN_SEXO() != null)
			psSessaoJulgamento.setString(9, sessao.getIN_SEXO().toString());
		else
			psSessaoJulgamento.setString(9, null);
		if (sessao.getIN_STATUS() != null)
			psSessaoJulgamento.setString(10, sessao.getIN_STATUS().toString());
		else
			psSessaoJulgamento.setString(10, null);
		psSessaoJulgamento.setLong(11, sessao.getCD_MAGISTRADO_PRESIDENTE());
		
		// Executar inserção da sessão de julgamento
		psSessaoJulgamento.executeUpdate();
		
		// Prepara os insert padrões para inserir os processos no banco de dados
		PreparedStatement psPautaSessaoRoteiro = conexao.prepareStatement("insert into orapje.pauta_sessao_roteiro (ID_SESSAO_PJE, ID_PROCESSO_PJE, DS_PROCESSO, NM_RELATOR, NM_REVISOR, NM_TERCEIRO, NR_ORDEM, IN_TIPO_INCLUSAO, IN_DIVERGENCIA, IN_DESTAQUE, IN_SUSTENTACAO_ORAL, IN_PREFERENCIA, DT_SITUACAO_PAUTA, DT_PEDIDO_SUSTENTACAO_ORAL, DS_ADV_SUSTENTACAO_ORAL, DS_DISPOSITIVO, IN_REVISAO, NR_SEQUENCIAL_PROCESSO, DT_ATUALIZACAO, NM_USUARIO, IN_IMPEDIMENTO, NR_SEQUENCIA, NR_ANO, DS_ACORDAO_COMPLETO, ID_PAUTA_SESSAO, DS_CLASSE_JUDICIAL, DS_CLASSE_JUDICIAL_SIGLA, NR_PROCESSO_CNJ, DS_POLO_ATIVO, DS_POLO_PASSIVO, DS_ORGAO_JULGADOR_1GRAU, CD_RELATOR, CD_REVISOR, CD_TERCEIRO) " + 
		                                                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement psPautaSessaoRoteiroComp = conexao.prepareStatement("insert into orapje.pauta_sessao_roteiro_comp (ID_PAUTA_SESSAO, NR_SEQUENCIAL_PROCESSO_COMP, DS_DISPOSITIVO_ALTERADO, DS_STATUS) " +
		                                                "values (?, ?, ?, ?)");
		
		// Para cada um dos processo na lista, executa a inserção
		for (RoteiroPautaSessao processo : processos) {
			psPautaSessaoRoteiro.clearParameters();
			psPautaSessaoRoteiroComp.clearParameters();
			
			psPautaSessaoRoteiro.setInt(1, processo.getID_SESSAO_PJE());
			psPautaSessaoRoteiro.setInt(2, processo.getID_PROCESSO_PJE());
			psPautaSessaoRoteiro.setString(3, processo.getDS_PROCESSO());
			psPautaSessaoRoteiro.setString(4, processo.getNM_RELATOR());
			psPautaSessaoRoteiro.setString(5, processo.getNM_REVISOR());
			psPautaSessaoRoteiro.setString(6, processo.getNM_TERCEIRO());
			psPautaSessaoRoteiro.setInt(7, processo.getNR_ORDEM());
			psPautaSessaoRoteiro.setString(8, processo.getIN_TIPO_INCLUSAO());
			if (processo.getIN_DIVERGENCIA() != null)
				psPautaSessaoRoteiro.setString(9, processo.getIN_DIVERGENCIA().toString());
			else
				psPautaSessaoRoteiro.setString(9, null);
			if (processo.getIN_DESTAQUE() != null)
				psPautaSessaoRoteiro.setString(10, processo.getIN_DESTAQUE().toString());
			else
				psPautaSessaoRoteiro.setString(10, null);
			if (processo.getIN_SUSTENTACAO_ORAL() != null)
				psPautaSessaoRoteiro.setString(11, processo.getIN_SUSTENTACAO_ORAL().toString());
			else
				psPautaSessaoRoteiro.setString(11, null);
			if (processo.getIN_PREFERENCIA() != null)
				psPautaSessaoRoteiro.setString(12, processo.getIN_PREFERENCIA().toString());
			else
				psPautaSessaoRoteiro.setString(12, null);
			if (processo.getDT_SITUACAO_PAUTA() != null)
				psPautaSessaoRoteiro.setDate(13, new java.sql.Date(processo.getDT_SITUACAO_PAUTA().getTime()));
			else
				psPautaSessaoRoteiro.setDate(13, null);
			if (processo.getDT_PEDIDO_SUSTENTACAO_ORAL() != null)
				psPautaSessaoRoteiro.setDate(14, new java.sql.Date(processo.getDT_PEDIDO_SUSTENTACAO_ORAL().getTime()));
			else
				psPautaSessaoRoteiro.setDate(14, null);
			psPautaSessaoRoteiro.setString(15, processo.getDS_ADV_SUSTENTACAO_ORAL());
			psPautaSessaoRoteiro.setString(16, processo.getDS_DISPOSITIVO());
			if (processo.getIN_REVISAO() != null)
				psPautaSessaoRoteiro.setString(17, processo.getIN_REVISAO().toString());
			else
				psPautaSessaoRoteiro.setString(17, null);
			psPautaSessaoRoteiro.setInt(18, processo.getNR_SEQUENCIAL_PROCESSO());
			if (processo.getDT_ATUALIZACAO() != null)
				psPautaSessaoRoteiro.setDate(19, new java.sql.Date(processo.getDT_ATUALIZACAO().getTime()));
			else
				psPautaSessaoRoteiro.setDate(19, null);
			psPautaSessaoRoteiro.setString(20, processo.getNM_USUARIO());
			if (processo.getIN_IMPEDIMENTO() != null)
				psPautaSessaoRoteiro.setString(21, processo.getIN_IMPEDIMENTO().toString());
			else
				psPautaSessaoRoteiro.setString(21, null);
			psPautaSessaoRoteiro.setInt(22, processo.getNR_SEQUENCIA());
			psPautaSessaoRoteiro.setInt(23, processo.getNR_ANO());
			CLOB clobDS_ACORDAO_COMPLETO = CLOB.createTemporary(conexao, false, CLOB.DURATION_SESSION);
			clobDS_ACORDAO_COMPLETO.setString(1, processo.getDS_ACORDAO_COMPLETO());
			psPautaSessaoRoteiro.setClob(24, clobDS_ACORDAO_COMPLETO);
			psPautaSessaoRoteiro.setInt(25, processo.getID_PAUTA_SESSAO());
			psPautaSessaoRoteiro.setString(26, processo.getDS_CLASSE_JUDICIAL());
			psPautaSessaoRoteiro.setString(27, processo.getDS_CLASSE_JUDICIAL_SIGLA());
			psPautaSessaoRoteiro.setString(28, processo.getNR_PROCESSO_CNJ());
			psPautaSessaoRoteiro.setString(29, processo.getDS_POLO_ATIVO());
			psPautaSessaoRoteiro.setString(30, processo.getDS_POLO_PASSIVO());
			psPautaSessaoRoteiro.setString(31, processo.getDS_ORGAO_JULGADOR_1GRAU());
			if (processo.getCD_RELATOR() != null)
				psPautaSessaoRoteiro.setLong(32, processo.getCD_RELATOR());
			else
				psPautaSessaoRoteiro.setNull(32, Types.NUMERIC);
			if (processo.getCD_REVISOR() != null)
				psPautaSessaoRoteiro.setLong(33, processo.getCD_REVISOR());
			else
				psPautaSessaoRoteiro.setNull(33, Types.NUMERIC);
			if (processo.getCD_TERCEIRO() != null)
				psPautaSessaoRoteiro.setLong(34, processo.getCD_TERCEIRO());
			else
				psPautaSessaoRoteiro.setNull(34, Types.NUMERIC);
			
			psPautaSessaoRoteiroComp.setInt(1, processo.getID_PAUTA_SESSAO());
			psPautaSessaoRoteiroComp.setInt(2, processo.getNR_SEQUENCIAL_PROCESSO());
			psPautaSessaoRoteiroComp.setString(3, processo.getDS_DISPOSITIVO());
			psPautaSessaoRoteiroComp.setString(4, processo.getDS_STATUS());
			
			// Executar a inserção
			psPautaSessaoRoteiro.executeUpdate();
			psPautaSessaoRoteiroComp.executeUpdate();
			
			// Libera o clob criado temporariamente
			clobDS_ACORDAO_COMPLETO.freeTemporary();
		}

		// Preparar o insert para magistrados da sessão
		PreparedStatement psComposicaoSessao = conexao.prepareStatement("insert into orapje.composicao_sessao (ID_COMPOSICAO_SESSAO, ID_SESSAO_PJE, CD_MAGISTRADO_TITULAR, NM_MAGISTRADO_TITULAR, CD_MAGISTRADO_SUBSTITUTO, NM_MAGISTRADO_SUBSTITUTO, DT_ATUALIZACAO, NM_USUARIO, IN_MARCADO_APTO) " +
													"values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		// Para cada magistrado da lista, executa a inserção
		for (ComposicaoSessao magistrado: magistrados) {
			psComposicaoSessao.setInt(1, magistrado.getID_COMPOSICAO_SESSAO());
			psComposicaoSessao.setInt(2, magistrado.getID_SESSAO_PJE());
			psComposicaoSessao.setLong(3, magistrado.getCD_MAGISTRADO_TITULAR());
			psComposicaoSessao.setString(4, magistrado.getNM_MAGISTRADO_TITULAR());
			if (magistrado.getCD_MAGISTRADO_SUBSTITUTO() != null)
				psComposicaoSessao.setLong(5, magistrado.getCD_MAGISTRADO_SUBSTITUTO());
			else
				psComposicaoSessao.setNull(5, Types.BIGINT);
			psComposicaoSessao.setString(6, magistrado.getNM_MAGISTRADO_SUBSTITUTO());
			if (magistrado.getDT_ATUALIZACAO() != null)
				psComposicaoSessao.setTimestamp(7, new java.sql.Timestamp(magistrado.getDT_ATUALIZACAO().getTime()));
			else
				psComposicaoSessao.setTimestamp(7, null);
			psComposicaoSessao.setString(8, magistrado.getNM_USUARIO());
			if (magistrado.getIN_MARCADO_APTO() != null)
				psComposicaoSessao.setString(9, magistrado.getIN_MARCADO_APTO().toString());
			else
				psComposicaoSessao.setString(9, null);
			
			// Executar inserção da sessão de julgamento
			psComposicaoSessao.executeUpdate();
		}

		// Preparar o insert para vista regimental da sessão
		PreparedStatement psVistaRegimental = conexao.prepareStatement("insert into orapje.vista_regimental (ID_PAUTA_SESSAO, CD_MAGISTRADO) " +
													"values (?, ?)");
		
		// Para cada vista regimental da lista, executa a inserção
		for (VistaRegimental vistaRegimental: vistasRegimentais) {
			psVistaRegimental.setInt(1, vistaRegimental.getID_PAUTA_SESSAO());
			psVistaRegimental.setLong(2, vistaRegimental.getCD_MAGISTRADO());
			
			// Executar inserção da vista regimental
			psVistaRegimental.executeUpdate();
		}
	}
	
	public void atualizarSessao(SessaoJulgamento sessao) throws SQLException {
		PreparedStatement psSessaoJulgamento = conexao.prepareStatement("update ORAPJE.SESSAO_JULGAMENTO set DT_SESSAO=?, ID_ORGAO_JULGADOR_COLEGIADO=?, DT_IMPORTACAO=?, NM_USUARIO_IMPORTACAO=?, DS_SALA=?, DS_TIPO_SESSAO=?, NM_PROCURADOR=?, IN_SEXO=?, IN_STATUS=?, CD_MAGISTRADO_PRESIDENTE=? " +
																		"where ID_SESSAO_PJE=?");
		
		// Preencher campos atualizados
		psSessaoJulgamento.setTimestamp(1, new java.sql.Timestamp(sessao.getDT_SESSAO().getTime()));
		psSessaoJulgamento.setInt(2, sessao.getID_ORGAO_JULGADOR_COLEGIADO());
		psSessaoJulgamento.setTimestamp(3, new java.sql.Timestamp(sessao.getDT_IMPORTACAO().getTime()));
		psSessaoJulgamento.setString(4, sessao.getNM_USUARIO_IMPORTACAO());
		psSessaoJulgamento.setString(5, sessao.getDS_SALA());
		psSessaoJulgamento.setString(6, sessao.getDS_TIPO_SESSAO());
		psSessaoJulgamento.setString(7, sessao.getNM_PROCURADOR());
		if (sessao.getIN_SEXO() != null)
			psSessaoJulgamento.setString(8, sessao.getIN_SEXO().toString());
		else
			psSessaoJulgamento.setString(8, null);
		if (sessao.getIN_STATUS() != null)
			psSessaoJulgamento.setString(9, sessao.getIN_STATUS().toString());
		else
			psSessaoJulgamento.setString(9, null);
		psSessaoJulgamento.setLong(10, sessao.getCD_MAGISTRADO_PRESIDENTE());
		psSessaoJulgamento.setInt(11, sessao.getID_SESSAO_PJE());
		
		// Executar a atualização
		psSessaoJulgamento.executeUpdate();
	}
	
	public void atualizarProcesso(RoteiroPautaSessao processo) throws SQLException {
		// Prepara os insert padrões para inserir os processos no banco de dados
		PreparedStatement psPautaSessaoRoteiro = conexao.prepareStatement("update orapje.pauta_sessao_roteiro set DS_PROCESSO=?, NM_RELATOR=?, NM_REVISOR=?, NM_TERCEIRO=?, NR_ORDEM=?, IN_TIPO_INCLUSAO=?, IN_DIVERGENCIA=?, IN_DESTAQUE=?, IN_SUSTENTACAO_ORAL=?, IN_PREFERENCIA=?, DT_SITUACAO_PAUTA=?, DT_PEDIDO_SUSTENTACAO_ORAL=?, DS_ADV_SUSTENTACAO_ORAL=?, DS_DISPOSITIVO=?, IN_REVISAO=?, NR_SEQUENCIAL_PROCESSO=?, DT_ATUALIZACAO=?, NM_USUARIO=?, IN_IMPEDIMENTO=?, NR_SEQUENCIA=?, NR_ANO=?, DS_ACORDAO_COMPLETO=?, ID_PAUTA_SESSAO=?, DS_CLASSE_JUDICIAL=?, DS_CLASSE_JUDICIAL_SIGLA=?, NR_PROCESSO_CNJ=?, DS_POLO_ATIVO=?, DS_POLO_PASSIVO=?, DS_ORGAO_JULGADOR_1GRAU=?, CD_RELATOR=?, CD_REVISOR=?, CD_TERCEIRO=? " + 
				                                                "where ID_SESSAO_PJE=? and ID_PROCESSO_PJE=?");
		PreparedStatement psPautaSessaoRoteiroComp = conexao.prepareStatement("update orapje.pauta_sessao_roteiro_comp set NR_SEQUENCIAL_PROCESSO_COMP=?, DS_DISPOSITIVO_ALTERADO=?, DS_STATUS=? " +
				                                                "where ID_PAUTA_SESSAO=?");
				
		// Preencher campos a serem atualizados
		psPautaSessaoRoteiro.setString(1, processo.getDS_PROCESSO());
		psPautaSessaoRoteiro.setString(2, processo.getNM_RELATOR());
		psPautaSessaoRoteiro.setString(3, processo.getNM_REVISOR());
		psPautaSessaoRoteiro.setString(4, processo.getNM_TERCEIRO());
		psPautaSessaoRoteiro.setInt(5, processo.getNR_ORDEM());
		psPautaSessaoRoteiro.setString(6, processo.getIN_TIPO_INCLUSAO());
		if (processo.getIN_DIVERGENCIA() != null)
			psPautaSessaoRoteiro.setString(7, processo.getIN_DIVERGENCIA().toString());
		else
			psPautaSessaoRoteiro.setString(7, null);
		if (processo.getIN_DESTAQUE() != null)
			psPautaSessaoRoteiro.setString(8, processo.getIN_DESTAQUE().toString());
		else
			psPautaSessaoRoteiro.setString(8, null);
		if (processo.getIN_SUSTENTACAO_ORAL() != null)
			psPautaSessaoRoteiro.setString(9, processo.getIN_SUSTENTACAO_ORAL().toString());
		else
			psPautaSessaoRoteiro.setString(9, null);
		if (processo.getIN_PREFERENCIA() != null)
			psPautaSessaoRoteiro.setString(10, processo.getIN_PREFERENCIA().toString());
		else
			psPautaSessaoRoteiro.setString(10, null);
		if (processo.getDT_SITUACAO_PAUTA() != null)
			psPautaSessaoRoteiro.setDate(11, new java.sql.Date(processo.getDT_SITUACAO_PAUTA().getTime()));
		else
			psPautaSessaoRoteiro.setDate(11, null);
		if (processo.getDT_PEDIDO_SUSTENTACAO_ORAL() != null)
			psPautaSessaoRoteiro.setDate(12, new java.sql.Date(processo.getDT_PEDIDO_SUSTENTACAO_ORAL().getTime()));
		else
			psPautaSessaoRoteiro.setDate(12, null);
		psPautaSessaoRoteiro.setString(13, processo.getDS_ADV_SUSTENTACAO_ORAL());
		psPautaSessaoRoteiro.setString(14, processo.getDS_DISPOSITIVO());
		if (processo.getIN_REVISAO() != null)
			psPautaSessaoRoteiro.setString(15, processo.getIN_REVISAO().toString());
		else
			psPautaSessaoRoteiro.setString(15, null);
		psPautaSessaoRoteiro.setInt(16, processo.getNR_SEQUENCIAL_PROCESSO());
		if (processo.getDT_ATUALIZACAO() != null)
			psPautaSessaoRoteiro.setDate(17, new java.sql.Date(processo.getDT_ATUALIZACAO().getTime()));
		else
			psPautaSessaoRoteiro.setDate(17, null);
		psPautaSessaoRoteiro.setString(18, processo.getNM_USUARIO());
		if (processo.getIN_IMPEDIMENTO() != null)
			psPautaSessaoRoteiro.setString(19, processo.getIN_IMPEDIMENTO().toString());
		else
			psPautaSessaoRoteiro.setString(19, null);
		psPautaSessaoRoteiro.setInt(20, processo.getNR_SEQUENCIA());
		psPautaSessaoRoteiro.setInt(21, processo.getNR_ANO());
		CLOB clobDS_ACORDAO_COMPLETO = CLOB.createTemporary(conexao, false, CLOB.DURATION_SESSION);
		clobDS_ACORDAO_COMPLETO.setString(1, processo.getDS_ACORDAO_COMPLETO());
		psPautaSessaoRoteiro.setClob(22, clobDS_ACORDAO_COMPLETO);
		psPautaSessaoRoteiro.setInt(23, processo.getID_PAUTA_SESSAO());
		psPautaSessaoRoteiro.setString(24, processo.getDS_CLASSE_JUDICIAL());
		psPautaSessaoRoteiro.setString(25, processo.getDS_CLASSE_JUDICIAL_SIGLA());
		psPautaSessaoRoteiro.setString(26, processo.getNR_PROCESSO_CNJ());
		psPautaSessaoRoteiro.setString(27, processo.getDS_POLO_ATIVO());
		psPautaSessaoRoteiro.setString(28, processo.getDS_POLO_PASSIVO());
		psPautaSessaoRoteiro.setString(29, processo.getDS_ORGAO_JULGADOR_1GRAU());
		if (processo.getCD_RELATOR() != null)
			psPautaSessaoRoteiro.setLong(30, processo.getCD_RELATOR());
		else
			psPautaSessaoRoteiro.setNull(30, Types.NUMERIC);
		if (processo.getCD_REVISOR() != null)
			psPautaSessaoRoteiro.setLong(31, processo.getCD_REVISOR());
		else
			psPautaSessaoRoteiro.setNull(31, Types.NUMERIC);
		if (processo.getCD_TERCEIRO() != null)
			psPautaSessaoRoteiro.setLong(32, processo.getCD_TERCEIRO());
		else
			psPautaSessaoRoteiro.setNull(32, Types.NUMERIC);
		psPautaSessaoRoteiro.setInt(33, processo.getID_SESSAO_PJE());
		psPautaSessaoRoteiro.setInt(34, processo.getID_PROCESSO_PJE());
		
		psPautaSessaoRoteiroComp.setInt(1, processo.getNR_SEQUENCIAL_PROCESSO());
		psPautaSessaoRoteiroComp.setString(2, processo.getDS_DISPOSITIVO());
		psPautaSessaoRoteiroComp.setString(3, processo.getDS_STATUS());
		psPautaSessaoRoteiroComp.setInt(4, processo.getID_PAUTA_SESSAO());
		
		// Executar a atualização
		psPautaSessaoRoteiro.executeUpdate();
		psPautaSessaoRoteiroComp.executeUpdate();
		
		// Libera o clob criado temporariamente
		clobDS_ACORDAO_COMPLETO.freeTemporary();
	}
	
	/**
	 * Recupera a ordenação original dos processos da tabela PAUTA_SESSAO_ROTEIRO para a
	 * tabela PAUTA_SESSAO_ROTEIRO_COMP e também na lista passada como parâmetro.
	 * 
	 * @throws SQLException 
	 */
	public void recuperarOrdenacaoProcessos(List<RoteiroPautaSessao> processos) throws SQLException {
		for (RoteiroPautaSessao processo : processos) {
			// Buscar posição original na tabela PAUTA_SESSAO_ROTEIRO
			ResultSet rs = conexao.prepareStatement("select NR_SEQUENCIAL_PROCESSO from ORAPJE.PAUTA_SESSAO_ROTEIRO where ID_PAUTA_SESSAO = " + processo.getID_PAUTA_SESSAO()).executeQuery();
			if (rs.next()) {
				int posicaoOriginal = rs.getInt(1);
				
				// Atualiza tabela PAUTA_SESSAO_ROTEIRO_COMP com a posicao original
				PreparedStatement ps = conexao.prepareStatement("update ORAPJE.PAUTA_SESSAO_ROTEIRO_COMP set NR_SEQUENCIAL_PROCESSO_COMP=? where ID_PAUTA_SESSAO=?");
				ps.setInt(1, posicaoOriginal);
				ps.setInt(2, processo.getID_PAUTA_SESSAO());
				ps.executeUpdate();
				
				// Atualiza processo da lista com sua posicao original
				processo.mudarSequencialProcesso(posicaoOriginal);
			}
		}
	}
	
	/**
	 * Busca pela descrição do órgão julgador de uma determinada sessão.
	 * 
	 * @param numeroSessao O número da sessão no PJE.
	 * 
	 * @return A descrição do órgão julgador no banco de dados. Ou NULL caso não seja entrada nenhuma descrição.
	 * 
	 * @throws SQLException
	 */
	public String buscarDescricaoOrgaoJulgador(int numeroSessao) throws SQLException {
		String sql = "select loc.DS_LOCAL from PORTAL.LOCAIS loc, ORAPJE.SESSAO_JULGAMENTO sj, ORAPJE.ORGAO_JULGADOR_SUAP_PJE ojs " +
					 "where loc.CD_LOCAL = ojs.CD_ORGAO_JULGADOR_SUAP and " +
				           "ojs.ID_ORGAO_JULGADOR_PJE = sj.ID_ORGAO_JULGADOR_COLEGIADO and " +
					       "sj.ID_SESSAO_PJE = " + numeroSessao;
		ResultSet resultadoSql = conexao.prepareStatement(sql).executeQuery();
		
		String retorno = null;
		if (resultadoSql.next()) {
			retorno = resultadoSql.getString("DS_LOCAL");
		}
		
		return retorno;
	}
	
	/**
	 * Busca pelo nome do advogado inscrito para sustentacao oral.
	 * 
	 * @param nome do advogado.
	 * 
	 * @return nome do advogado.
	 * 
	 * @throws SQLException
	 */
	public String buscarNomedoAdvogadoSustentacaoOral(String nome) throws SQLException {
		/*String sql = "select loc.DS_LOCAL from PORTAL.LOCAIS loc, ORAPJE.SESSAO_JULGAMENTO sj, ORAPJE.ORGAO_JULGADOR_SUAP_PJE ojs " +
					 "where loc.CD_LOCAL = ojs.CD_ORGAO_JULGADOR_SUAP and " +
				           "ojs.ID_ORGAO_JULGADOR_PJE = sj.ID_ORGAO_JULGADOR_COLEGIADO and " +
					       "sj.ID_SESSAO_PJE = " + numeroSessao;*/
		String sql = "select * from ORAPJE.INSCRICAO_SUSTENTACAO_ORAL WHERE nm_advogado like '"+nome+"'" ;
		
		ResultSet resultadoSql = conexao.prepareStatement(sql).executeQuery();
		
		String retorno = null;
		if (resultadoSql.next()) {
			retorno = resultadoSql.getString("NM_ADVOGADO");
		}
		
		return retorno;
	}
	
	/**
	 * Busca pelo nome do advogado inscrito para sustentacao oral.
	 * 
	 * @param nome do advogado.
	 * 
	 * @return nome do advogado.
	 * 
	 * @throws SQLException
	 */
	public boolean preparaBancoTesteSustentacaoOral(int pautaSessao, boolean incluir) throws SQLException {
		String sql = "select count(*) from ORAPJE.INSCRICAO_SUSTENTACAO_ORAL WHERE id_pauta_sessao = "+ pautaSessao;
		
		ResultSet resultadoSql = conexao.prepareStatement(sql).executeQuery();
		
		boolean retorno = false;
        int rowCount = 0;
        while(resultadoSql.next()) {
            rowCount = Integer.parseInt(resultadoSql.getString("count(*)"));
        }
        if(rowCount > 0){
			String sqlExclui = "delete from ORAPJE.INSCRICAO_SUSTENTACAO_ORAL WHERE id_pauta_sessao = "+ pautaSessao;
			int apagados = conexao.prepareStatement(sqlExclui).executeUpdate();
		}
/*		if(incluir){
			String sqlInclui = "insert into ORAPJE.INSCRICAO_SUSTENTACAO_ORAL (NM_ADVOGADO,"
		}*/
		
		return true;
	}	

	/**
	 * Fechar a conexão com o banco de dados.
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		conexao.close();
		conexao = null;
	}
	
	public void inserirPerfilUsuario(PerfilUsuario perfilUsuario) throws IOException, SQLException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		PreparedStatement ps = conexao.prepareStatement("insert into ORAPJE.USUARIO_COMPLETO (LOGIN, NOME, CPF, PROFILE) values (?, ?, ?, ?)");
		ps.setString(1, propriedades.getProperty("usuarioSistema").toUpperCase());
		ps.setString(2, "Selenium Test User");
		ps.setLong(3, 11111111111L);
		ps.setString(4, perfilUsuario.getCodigoPerfil());
		
		Assert.assertEquals("Não foi possível atualizar o perfil do usuário de teste.", 1, ps.executeUpdate());
	}

	/**
	 * Altera o perfil do usuário de teste no banco de dados.
	 * 
	 * @param perfilUsuario Perfil desejado para o usuário de teste.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	public void alterarPerfilUsuario(PerfilUsuario perfilUsuario) throws IOException, SQLException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		PreparedStatement ps = conexao.prepareStatement("update ORAPJE.USUARIO_COMPLETO set PROFILE=? where LOGIN=?");
		ps.setString(1, perfilUsuario.getCodigoPerfil());
		ps.setString(2, propriedades.getProperty("usuarioSistema").toUpperCase());
		
		Assert.assertEquals("Não foi possível atualizar o perfil do usuário de teste.", 1, ps.executeUpdate());
	}

	/**
	 * Remove o perfil do usuário de teste no banco de dados.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void removerPerfilUsuario() throws SQLException, IOException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		PreparedStatement ps = conexao.prepareStatement("delete from ORAPJE.USUARIO_COMPLETO where LOGIN=?");
		ps.setString(1, propriedades.getProperty("usuarioSistema").toUpperCase());
		
		Assert.assertEquals("Não foi possível remover o perfil do usuário de teste.", 1, ps.executeUpdate());
	}

	/**
	 * Inserir no perfil do usuário de teste o acesso a turma passada.
	 * 
	 * @param idTurma Identificador da turma no PJ-e.
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void inserirTurmaDoUsuario(int idTurma) throws IOException, SQLException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		PreparedStatement ps = conexao.prepareStatement("insert into ORAPJE.USUARIO_ORGAO_JULGADOR_PJE (LOGIN, ID_ORGAO_JULGADOR_PJE) values (?, ?)");
		ps.setString(1, propriedades.getProperty("usuarioSistema").toUpperCase());
		ps.setInt(2, idTurma);
		
		Assert.assertEquals("Não foi possível inserir o acesso a turma ao usuário de teste.", 1, ps.executeUpdate());
	}

	/**
	 * Remove do perfil do usuário de teste o acesso a turma passada.
	 * 
	 * @param idTurma Identificador da turma no PJ-e.
	 * 
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void removerTurmaDoUsuario(int idTurma) throws IOException, SQLException {
		Properties propriedades = new Properties();
		propriedades.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		
		PreparedStatement ps = conexao.prepareStatement("delete from ORAPJE.USUARIO_ORGAO_JULGADOR_PJE where LOGIN=? and ID_ORGAO_JULGADOR_PJE=?");
		ps.setString(1, propriedades.getProperty("usuarioSistema").toUpperCase());
		ps.setInt(2, idTurma);
		
		Assert.assertEquals("Não foi possível remover o acesso a turma do usuário de teste.", 1, ps.executeUpdate());
	}

	/**
	 * Recupera o perfil padrão do usuário de teste.
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void recuperarPerfilUsuario() throws IOException, SQLException, ClassNotFoundException {
		BDUtils.getInstance().alterarPerfilUsuario(PerfilUsuario.Secretario);
	}

	public void alterarPermissaoVotoCompletoProcurador(int numeroSessao, boolean habilitar) throws SQLException {
		PreparedStatement ps = conexao.prepareStatement("update ORAPJE.CONFIGURACAO_ASPJE set IN_VOTO_COMPLETO_PROCURADOR=? where ID_ORGAO_JULGADOR_COLEGIADO=?");
		if (habilitar) {
			ps.setString(1, "S");
		}
		else {
			ps.setString(1, "N");
		}
		ps.setInt(2, numeroSessao);
		
		Assert.assertEquals("Não foi possível alterar a permissão de visualização do voto completo para o procurador.", 1, ps.executeUpdate());
	}

	public void limparVistaRegimental(RoteiroPautaSessao processoVistaRegimental) throws SQLException {
		conexao.prepareStatement("delete from orapje.vista_regimental where id_pauta_sessao in (select id_pauta_sessao from orapje.pauta_sessao_roteiro where id_sessao_pje = " + processoVistaRegimental.getID_PAUTA_SESSAO() + ")").executeUpdate();
	}

}
