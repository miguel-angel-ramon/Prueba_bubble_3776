package es.eroski.misumi.model.pda.packingList;

public class RdoListarMatriculas extends RdoApiPacking{

	private Resultado resultado;
	
	public RdoListarMatriculas(){
		super();
	}

	public RdoListarMatriculas(String tipoRespuesta, Resultado resultado, String codigoError, String token) {
		super(tipoRespuesta, codigoError, token);
		this.resultado = resultado;
	}

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}

}
