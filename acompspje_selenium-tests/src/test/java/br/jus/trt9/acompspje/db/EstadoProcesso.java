package br.jus.trt9.acompspje.db;

public enum EstadoProcesso {
	NAO_JULGADO("Não Julgado"),
	JULGADO("Julgado"),
	APREGOADO("Apregoado"),
	REVISAR("Revisar"),
	RETIRADO("Retirado"),
	ADIADO("Adiado"),
	VISTA_MESA("Vista Em Mesa"),
	VISTA_REGIMENTAL("Vista Regimental");
	
	private String _estado;
	
	private EstadoProcesso(String estado) {
		_estado = estado;
	}
	
	@Override
	public String toString() {
		return _estado;
	}
}
