package es.eroski.misumi.model.pda.packingList;

public class RdoApiPacking {
	private String tipoRespuesta;
	private String codigoError;
	private String token;
	
	public RdoApiPacking(){
	}
	
	public RdoApiPacking(String tipoRespuesta, String codigoError, String token) {
		super();
		
		this.tipoRespuesta = tipoRespuesta;
		this.codigoError = codigoError;
		this.token = token;
	}

	public String getTipoRespuesta() {
		return tipoRespuesta;
	}

	public void setTipoRespuesta(String tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
