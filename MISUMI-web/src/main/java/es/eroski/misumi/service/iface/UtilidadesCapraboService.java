package es.eroski.misumi.service.iface;

public interface UtilidadesCapraboService {

	public boolean esCentroCaprabo(Long codCentro, String codUser) throws Exception;
	public boolean esCentroCapraboEspecial(Long codCentro, String codUser) throws Exception;
	public boolean esCentroCapraboNuevo(Long codCentro, String codUser) throws Exception;
	public Long obtenerCodigoEroski(Long codCentro, Long codArtCaprabo) throws Exception;
	public Long obtenerCodigoCaprabo(Long codCentro, Long codArtEroski) throws Exception;
	public String obtenerDescArtCaprabo(Long codArtCaprabo) throws Exception;
	public String obtenerMotivoCapraboEspecial(String motivoTxt, String codArtSustituidaPorEroski, String codArtSustituidaPorCaprabo) throws Exception;

}
