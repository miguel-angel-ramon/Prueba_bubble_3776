package es.eroski.misumi.service.iface;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.InventarioRotativoGisae;

public interface InventarioRotativoGisaeService {

	public abstract LinkedHashMap<Long, String> findAllSeccion(
			InventarioRotativoGisae inventarioRotativo) throws Exception;
	
	public void updateInventarioATratado(InventarioRotativoGisae inventarioRotativoGisae) throws Exception;
	
	public Long getInformeHuecosCount(Long codCentro) throws Exception;
	
	public List<InformeHuecos> getInformeHuecos(Long codCentro, HttpSession session) throws Exception;

}