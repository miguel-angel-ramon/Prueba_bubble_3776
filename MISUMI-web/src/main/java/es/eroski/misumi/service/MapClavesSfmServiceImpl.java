package es.eroski.misumi.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.service.iface.MapClavesSfmService;
import es.eroski.misumi.util.Constantes;


@Service(value = "mapClavesSfmService")
public class MapClavesSfmServiceImpl implements MapClavesSfmService {

    @Autowired
	private HttpSession httpSession;

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Integer> getMapClavesFromSession() throws Exception {
		HashMap<String, Integer> mapClavesSfm = new HashMap<String, Integer>();
		if (existeMapClavesInSession())
		{
			mapClavesSfm = (HashMap<String, Integer>) httpSession.getAttribute(Constantes.HASH_MAP_CLAVES_SFM_IN_SESSION);
		}
		return mapClavesSfm;
	}

	@Override
	public void setMapClavesInSession(HashMap<String, Integer> mapClavesSfm) throws Exception {
		//Actualizamos en sesión el map
		if (httpSession != null){
			httpSession.setAttribute(Constantes.HASH_MAP_CLAVES_SFM_IN_SESSION, mapClavesSfm);
		}
	}

	@Override
	public void updateMapClavesInSession(List<VArtSfm> listaSfm) throws Exception {
		//Actualizamos en sesión el map
		HashMap<String, Integer> mapClaves = this.getMapClavesFromSession();
		
		if (listaSfm != null) {
			//Nos recorremos la lista.
			VArtSfm campo = new VArtSfm();
			for (int i =0;i<listaSfm.size();i++)
			{
				campo = (VArtSfm)listaSfm.get(i);

				//Actualizamos el índice dentro de la lista
				campo.setIndice(i);
				listaSfm.set(i, campo);

				//Actualizamos el hashmap
				mapClaves.put(campo.getClave(), campo.getIndice());
			}

			this.setMapClavesInSession(mapClaves);
		}
	}

	@Override
	public boolean existeMapClavesInSession() throws Exception {
		return (httpSession != null && httpSession.getAttribute(Constantes.HASH_MAP_CLAVES_SFM_IN_SESSION) != null);
	}

	@Override
	public void removeMapClavesFromSession() throws Exception {
		//Borramos de sesión el map
		if (httpSession != null){
			httpSession.removeAttribute(Constantes.HASH_MAP_CLAVES_SFM_IN_SESSION);
		}
	}

}
