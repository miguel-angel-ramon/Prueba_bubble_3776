package es.eroski.misumi.model.pda.packingList;

public class RdoRecepcionadoOK extends RdoApiPacking{

	private Palet resultado;
	
	public RdoRecepcionadoOK(){
		super();
	}
	
	public RdoRecepcionadoOK(String tipoRespuesta, Palet resultado, String codigoError, String token) {
		super(tipoRespuesta, codigoError, token);
		this.resultado = resultado;
	}

	public Palet getResultado() {
		return resultado;
	}

	public void setResultado(Palet resultado) {
		this.resultado = resultado;
	}

}
