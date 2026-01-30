package es.eroski.misumi.dao.iface;

public interface UtilidadesCapraboDao {

	//Método que transforma un código de caprabo a eroski.
	public Long obtenerCodigoEroski(final Long codCentro, final Long codArtCaprabo) throws Exception;
	
	//Te cambia la referencia de caprabo a eroski en centros caprabo especial.
	public String obtenerMotivoCapraboEspecial(String motivoTxt, String codArtSustituidaPorEroski, String codArtSustituidaPorCaprabo) throws Exception;
}
