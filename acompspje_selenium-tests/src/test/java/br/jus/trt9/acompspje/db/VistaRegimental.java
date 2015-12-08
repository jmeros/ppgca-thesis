package br.jus.trt9.acompspje.db;


public class VistaRegimental {
	private int ID_PAUTA_SESSAO;
	private long CD_MAGISTRADO;
	
	public VistaRegimental(int iD_PAUTA_SESSAO, long cD_MAGISTRADO) {
		super();
		ID_PAUTA_SESSAO = iD_PAUTA_SESSAO;
		CD_MAGISTRADO = cD_MAGISTRADO;
	}

	public int getID_PAUTA_SESSAO() {
		return ID_PAUTA_SESSAO;
	}

	public long getCD_MAGISTRADO() {
		return CD_MAGISTRADO;
	}
}
