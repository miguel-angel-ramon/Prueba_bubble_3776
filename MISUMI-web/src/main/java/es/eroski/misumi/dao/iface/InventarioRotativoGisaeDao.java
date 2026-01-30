package es.eroski.misumi.dao.iface;

import java.util.LinkedHashMap;
import java.util.List;

import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.InventarioRotativoGisae;

public interface InventarioRotativoGisaeDao {

	public abstract void insert(InventarioRotativoGisae inventarioRotativoGisae)
			throws Exception;

	public abstract List<InventarioRotativoGisae> findAll(
			InventarioRotativoGisae inventarioRotativoGisae) throws Exception;
	
	public abstract LinkedHashMap<Long, String> findAllSeccion(
			InventarioRotativoGisae inventarioRotativoGisae) throws Exception;
	
	public void updateInventarioATratado(InventarioRotativoGisae inventarioRotativoGisae) throws Exception;
	
	public Long getInformeHuecosCount(Long codCentro) throws Exception;
	
	public List<InformeHuecos> getInformeHuecos(Long codCentro) throws Exception;

}