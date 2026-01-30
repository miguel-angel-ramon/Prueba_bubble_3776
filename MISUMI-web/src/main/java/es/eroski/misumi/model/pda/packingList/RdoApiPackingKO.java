package es.eroski.misumi.model.pda.packingList;

public class RdoApiPackingKO extends RdoApiPacking{

	private String resultado;
	
	public RdoApiPackingKO(){
		super();
	}
	
	public RdoApiPackingKO(String tipoRespuesta, String resultado, String codigoError, String token) {
		super(tipoRespuesta, codigoError, token);
		this.resultado = resultado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
