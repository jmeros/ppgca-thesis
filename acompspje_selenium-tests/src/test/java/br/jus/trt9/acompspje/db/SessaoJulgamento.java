package br.jus.trt9.acompspje.db;

import java.util.Date;

public class SessaoJulgamento {
	private int ID_SESSAO_PJE;
	private Date DT_SESSAO;
	private int ID_ORGAO_JULGADOR_COLEGIADO;
	private Date DT_IMPORTACAO;
	private String NM_USUARIO_IMPORTACAO;
	private String DS_SALA;
	private String DS_TIPO_SESSAO;
	private String NM_PROCURADOR;
	private Character IN_SEXO;
	private Character IN_STATUS;
	private Long CD_MAGISTRADO_PRESIDENTE;
	
	public SessaoJulgamento(int iD_SESSAO_PJE, Date dT_SESSAO,
			int iD_ORGAO_JULGADOR_COLEGIADO, Date dT_IMPORTACAO,
			String nM_USUARIO_IMPORTACAO, String dS_SALA,
			String dS_TIPO_SESSAO, String nM_PROCURADOR,
			Character iN_SEXO, Character iN_STATUS,
			Long cD_MAGISTRADO_PRESIDENTE) {
		super();
		ID_SESSAO_PJE = iD_SESSAO_PJE;
		DT_SESSAO = dT_SESSAO;
		ID_ORGAO_JULGADOR_COLEGIADO = iD_ORGAO_JULGADOR_COLEGIADO;
		DT_IMPORTACAO = dT_IMPORTACAO;
		NM_USUARIO_IMPORTACAO = nM_USUARIO_IMPORTACAO;
		DS_SALA = dS_SALA;
		DS_TIPO_SESSAO = dS_TIPO_SESSAO;
		NM_PROCURADOR = nM_PROCURADOR;
		IN_SEXO = iN_SEXO;
		IN_STATUS = iN_STATUS;
		CD_MAGISTRADO_PRESIDENTE = cD_MAGISTRADO_PRESIDENTE;
	}

	public int getID_SESSAO_PJE() {
		return ID_SESSAO_PJE;
	}

	public Date getDT_SESSAO() {
		return DT_SESSAO;
	}

	public int getID_ORGAO_JULGADOR_COLEGIADO() {
		return ID_ORGAO_JULGADOR_COLEGIADO;
	}

	public Date getDT_IMPORTACAO() {
		return DT_IMPORTACAO;
	}

	public String getNM_USUARIO_IMPORTACAO() {
		return NM_USUARIO_IMPORTACAO;
	}

	public String getDS_SALA() {
		return DS_SALA;
	}

	public String getDS_TIPO_SESSAO() {
		return DS_TIPO_SESSAO;
	}

	public String getNM_PROCURADOR() {
		return NM_PROCURADOR;
	}
	
	public Character getIN_SEXO() {
		return IN_SEXO;
	}
	
	public Character getIN_STATUS() {
		return IN_STATUS;
	}
	
	public Long getCD_MAGISTRADO_PRESIDENTE() {
		return CD_MAGISTRADO_PRESIDENTE;
	}
	
	public void mudarStatus(Character novoStatus) {
		IN_STATUS = novoStatus;
	}
}
