package es.eroski.misumi.model.pda.packingList;

public class RdoValidarCecoOK extends RdoApiPacking{

	private String resultado;
	
	public RdoValidarCecoOK(){
		super();
	}
			
	public RdoValidarCecoOK(String resultado, String tipoRespuesta, String codigoError, String token) {
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
