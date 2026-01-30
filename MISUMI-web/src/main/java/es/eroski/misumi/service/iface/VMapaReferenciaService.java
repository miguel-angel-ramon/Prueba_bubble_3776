package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.VMapaReferencia;

public interface VMapaReferenciaService {
	
	public VMapaReferencia findMapa(Long codArt) throws Exception;
	
	public boolean existsMapa(Long codArt);
	
}
