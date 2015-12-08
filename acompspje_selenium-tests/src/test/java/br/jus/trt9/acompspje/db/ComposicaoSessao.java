package br.jus.trt9.acompspje.db;

import java.util.Date;

public class ComposicaoSessao {
	private int ID_COMPOSICAO_SESSAO;
	private int ID_SESSAO_PJE;
	private long CD_MAGISTRADO_TITULAR;
	private String NM_MAGISTRADO_TITULAR;
	private Long CD_MAGISTRADO_SUBSTITUTO;
	private String NM_MAGISTRADO_SUBSTITUTO;
	private Date DT_ATUALIZACAO;
	private String NM_USUARIO;
	private Character IN_MARCADO_APTO;
	
	public ComposicaoSessao(int iD_COMPOSICAO_SESSAO,
			int iD_SESSAO_PJE, long cD_MAGISTRADO_TITULAR,
			String nM_MAGISTRADO_TITULAR, Long cD_MAGISTRADO_SUBSTITUTO,
			String nM_MAGISTRADO_SUBSTITUTO, Date dT_ATUALIZACAO,
			String nM_USUARIO, Character iN_MARCADO_APTO) {
		super();
		ID_COMPOSICAO_SESSAO = iD_COMPOSICAO_SESSAO;
		ID_SESSAO_PJE = iD_SESSAO_PJE;
		CD_MAGISTRADO_TITULAR = cD_MAGISTRADO_TITULAR;
		NM_MAGISTRADO_TITULAR = nM_MAGISTRADO_TITULAR;
		CD_MAGISTRADO_SUBSTITUTO = cD_MAGISTRADO_SUBSTITUTO;
		NM_MAGISTRADO_SUBSTITUTO = nM_MAGISTRADO_SUBSTITUTO;
		DT_ATUALIZACAO = dT_ATUALIZACAO;
		NM_USUARIO = nM_USUARIO;
		IN_MARCADO_APTO = iN_MARCADO_APTO;
	}

	public int getID_COMPOSICAO_SESSAO() {
		return ID_COMPOSICAO_SESSAO;
	}

	public int getID_SESSAO_PJE() {
		return ID_SESSAO_PJE;
	}

	public long getCD_MAGISTRADO_TITULAR() {
		return CD_MAGISTRADO_TITULAR;
	}

	public String getNM_MAGISTRADO_TITULAR() {
		return NM_MAGISTRADO_TITULAR;
	}

	public Long getCD_MAGISTRADO_SUBSTITUTO() {
		return CD_MAGISTRADO_SUBSTITUTO;
	}

	public String getNM_MAGISTRADO_SUBSTITUTO() {
		return NM_MAGISTRADO_SUBSTITUTO;
	}

	public Date getDT_ATUALIZACAO() {
		return DT_ATUALIZACAO;
	}

	public String getNM_USUARIO() {
		return NM_USUARIO;
	}

	public Character getIN_MARCADO_APTO() {
		return IN_MARCADO_APTO;
	}
	
}
