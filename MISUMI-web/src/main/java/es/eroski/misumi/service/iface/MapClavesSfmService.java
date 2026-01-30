package es.eroski.misumi.service.iface;

import java.util.HashMap;
import java.util.List;

import es.eroski.misumi.model.VArtSfm;


public interface MapClavesSfmService {

	public HashMap<String, Integer> getMapClavesFromSession() throws Exception;
	
	public void setMapClavesInSession(HashMap<String, Integer> mapClavesSfm) throws Exception;
	
	public void updateMapClavesInSession(List<VArtSfm> listaGuardada) throws Exception;
	
	public boolean existeMapClavesInSession() throws Exception;
	
	public void removeMapClavesFromSession() throws Exception;

}

