package br.jus.trt9.acompspje.db;

import java.util.Date;

public class RoteiroPautaSessao {
	private int ID_SESSAO_PJE;
	private int ID_PROCESSO_PJE;
	private String DS_PROCESSO;
	private String NM_RELATOR;
	private String NM_REVISOR;
	private String NM_TERCEIRO;
	private Integer NR_ORDEM;
	private String IN_TIPO_INCLUSAO;
	private Character IN_DIVERGENCIA;
	private Character IN_DESTAQUE;
	private Character IN_SUSTENTACAO_ORAL;
	private Character IN_PREFERENCIA;
	private Date DT_SITUACAO_PAUTA;
	private Date DT_PEDIDO_SUSTENTACAO_ORAL;
	private String DS_ADV_SUSTENTACAO_ORAL;
	private String DS_DISPOSITIVO;
	private Character IN_REVISAO;
	private Integer NR_SEQUENCIAL_PROCESSO;
	private Date DT_ATUALIZACAO;
	private String NM_USUARIO;
	private Character IN_IMPEDIMENTO;
	private Integer NR_SEQUENCIA;
	private Integer NR_ANO;
	private String DS_ACORDAO_COMPLETO;
	private Integer ID_PAUTA_SESSAO;
	private String DS_CLASSE_JUDICIAL;
	private String DS_CLASSE_JUDICIAL_SIGLA;
	private String NR_PROCESSO_CNJ;
	private String DS_POLO_ATIVO;
	private String DS_POLO_PASSIVO;
	private String DS_ORGAO_JULGADOR_1GRAU;
	private String DS_STATUS;
	private String DS_DISPOSITIVO_SESSAO;
	private Long CD_RELATOR;
	private Long CD_REVISOR;
	private Long CD_TERCEIRO;
	
	public RoteiroPautaSessao(int iD_SESSAO_PJE, int iD_PROCESSO_PJE,
			String dS_PROCESSO, String nM_RELATOR, String nM_REVISOR,
			String nM_TERCEIRO, Integer nR_ORDEM, String iN_TIPO_INCLUSAO,
			Character iN_DIVERGENCIA, Character iN_DESTAQUE, Character iN_SUSTENTACAO_ORAL,
			Character iN_PREFERENCIA, Date dT_SITUACAO_PAUTA,
			Date dT_PEDIDO_SUSTENTACAO_ORAL, String dS_ADV_SUSTENTACAO_ORAL,
			String dS_DISPOSITIVO, Character iN_REVISAO, int nR_SEQUENCIAL_PROCESSO,
			Date dT_ATUALIZACAO, String nM_USUARIO, Character iN_IMPEDIMENTO,
			int nR_SEQUENCIA, Integer nR_ANO, String dS_ACORDAO_COMPLETO,
			int iD_PAUTA_SESSAO, String dS_CLASSE_JUDICIAL,
			String dS_CLASSE_JUDICIAL_SIGLA, String nR_PROCESSO_CNJ,
			String dS_POLO_ATIVO, String dS_POLO_PASSIVO, String dS_ORGAO_JULGADOR_1GRAU,
			String dS_STATUS, String dS_DISPOSITIVO_SESSAO, Long cD_RELATOR, 
			Long cD_REVISOR, Long cD_TERCEIRO) {
		ID_SESSAO_PJE = iD_SESSAO_PJE;
		ID_PROCESSO_PJE = iD_PROCESSO_PJE;
		DS_PROCESSO = dS_PROCESSO;
		NM_RELATOR = nM_RELATOR;
		NM_REVISOR = nM_REVISOR;
		NM_TERCEIRO = nM_TERCEIRO;
		NR_ORDEM = nR_ORDEM;
		IN_TIPO_INCLUSAO = iN_TIPO_INCLUSAO;
		IN_DIVERGENCIA = iN_DIVERGENCIA;
		IN_DESTAQUE = iN_DESTAQUE;
		IN_SUSTENTACAO_ORAL = iN_SUSTENTACAO_ORAL;
		IN_PREFERENCIA = iN_PREFERENCIA;
		DT_SITUACAO_PAUTA = dT_SITUACAO_PAUTA;
		DT_PEDIDO_SUSTENTACAO_ORAL = dT_PEDIDO_SUSTENTACAO_ORAL;
		DS_ADV_SUSTENTACAO_ORAL = dS_ADV_SUSTENTACAO_ORAL;
		DS_DISPOSITIVO = dS_DISPOSITIVO;
		IN_REVISAO = iN_REVISAO;
		NR_SEQUENCIAL_PROCESSO = nR_SEQUENCIAL_PROCESSO;
		DT_ATUALIZACAO = dT_ATUALIZACAO;
		NM_USUARIO = nM_USUARIO;
		IN_IMPEDIMENTO = iN_IMPEDIMENTO;
		NR_SEQUENCIA = nR_SEQUENCIA;
		NR_ANO = nR_ANO;
		DS_ACORDAO_COMPLETO = dS_ACORDAO_COMPLETO;
		ID_PAUTA_SESSAO = iD_PAUTA_SESSAO;
		DS_CLASSE_JUDICIAL = dS_CLASSE_JUDICIAL;
		DS_CLASSE_JUDICIAL_SIGLA = dS_CLASSE_JUDICIAL_SIGLA;
		NR_PROCESSO_CNJ = nR_PROCESSO_CNJ;
		DS_POLO_ATIVO = dS_POLO_ATIVO;
		DS_POLO_PASSIVO = dS_POLO_PASSIVO;
		DS_ORGAO_JULGADOR_1GRAU = dS_ORGAO_JULGADOR_1GRAU;
		DS_STATUS = dS_STATUS;
		DS_DISPOSITIVO_SESSAO = dS_DISPOSITIVO_SESSAO;
		CD_RELATOR = cD_RELATOR;
		CD_REVISOR = cD_REVISOR;
		CD_TERCEIRO = cD_TERCEIRO;
	}
	
	public int getID_SESSAO_PJE() {
		return ID_SESSAO_PJE;
	}
	public int getID_PROCESSO_PJE() {
		return ID_PROCESSO_PJE;
	}
	public String getDS_PROCESSO() {
		return DS_PROCESSO;
	}
	public String getNM_RELATOR() {
		return NM_RELATOR;
	}
	public String getNM_REVISOR() {
		return NM_REVISOR;
	}
	public String getNM_TERCEIRO() {
		return NM_TERCEIRO;
	}
	public Integer getNR_ORDEM() {
		return NR_ORDEM;
	}
	public String getIN_TIPO_INCLUSAO() {
		return IN_TIPO_INCLUSAO;
	}
	public Character getIN_DIVERGENCIA() {
		return IN_DIVERGENCIA;
	}
	public Character getIN_DESTAQUE() {
		return IN_DESTAQUE;
	}
	public Character getIN_SUSTENTACAO_ORAL() {
		return IN_SUSTENTACAO_ORAL;
	}
	public Character getIN_PREFERENCIA() {
		return IN_PREFERENCIA;
	}
	public Date getDT_SITUACAO_PAUTA() {
		return DT_SITUACAO_PAUTA;
	}
	public Date getDT_PEDIDO_SUSTENTACAO_ORAL() {
		return DT_PEDIDO_SUSTENTACAO_ORAL;
	}
	public String getDS_ADV_SUSTENTACAO_ORAL() {
		return DS_ADV_SUSTENTACAO_ORAL;
	}
	public String getDS_DISPOSITIVO() {
		return DS_DISPOSITIVO;
	}
	public Character getIN_REVISAO() {
		return IN_REVISAO;
	}
	public Integer getNR_SEQUENCIAL_PROCESSO() {
		return NR_SEQUENCIAL_PROCESSO;
	}
	public Date getDT_ATUALIZACAO() {
		return DT_ATUALIZACAO;
	}
	public String getNM_USUARIO() {
		return NM_USUARIO;
	}
	public Character getIN_IMPEDIMENTO() {
		return IN_IMPEDIMENTO;
	}
	public Integer getNR_SEQUENCIA() {
		return NR_SEQUENCIA;
	}
	public Integer getNR_ANO() {
		return NR_ANO;
	}
	public String getDS_ACORDAO_COMPLETO() {
		return DS_ACORDAO_COMPLETO;
	}
	public Integer getID_PAUTA_SESSAO() {
		return ID_PAUTA_SESSAO;
	}
	public String getDS_CLASSE_JUDICIAL() {
		return DS_CLASSE_JUDICIAL;
	}
	public String getDS_CLASSE_JUDICIAL_SIGLA() {
		return DS_CLASSE_JUDICIAL_SIGLA;
	}
	public String getNR_PROCESSO_CNJ() {
		return NR_PROCESSO_CNJ;
	}
	public String getDS_POLO_ATIVO() {
		return DS_POLO_ATIVO;
	}
	public String getDS_POLO_PASSIVO() {
		return DS_POLO_PASSIVO;
	}
	public String getDS_ORGAO_JULGADOR_1GRAU() {
		return DS_ORGAO_JULGADOR_1GRAU;
	}
	public String getDS_STATUS() {
		return DS_STATUS;
	}
	public String getDS_DISPOSITIVO_SESSAO() {
		return DS_DISPOSITIVO_SESSAO;
	}
	public Long getCD_RELATOR() {
		return CD_RELATOR;
	}
	public Long getCD_REVISOR() {
		return CD_REVISOR;
	}
	public Long getCD_TERCEIRO() {
		return CD_TERCEIRO;
	}

	public void mudarEstado(EstadoProcesso novoEstado) {
		DS_STATUS = novoEstado.name();
	}

	public void mudarPreferencial(boolean preferencial) {
		if (preferencial)
			IN_PREFERENCIA = new Character('S');
		else
			IN_PREFERENCIA = new Character('N');
	}

	public void mudarSequencialProcesso(int posicao) {
		NR_SEQUENCIAL_PROCESSO = posicao;
	}

	public void mudarDispositivo(String novoTexto) {
		DS_DISPOSITIVO = novoTexto;
	}

}
