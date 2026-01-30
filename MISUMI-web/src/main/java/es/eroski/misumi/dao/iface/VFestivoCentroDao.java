package es.eroski.misumi.dao.iface;

import java.util.List;
import java.util.Map;

import es.eroski.misumi.model.VFestivoCentro;

public interface VFestivoCentroDao {
	
	public String getNextDay(VFestivoCentro vFestivoCentro) throws Exception;
	public String getDiaLaborable(VFestivoCentro vFestivoCentro) throws Exception;
	public Integer getNumDias(VFestivoCentro vFestivoCentro)  throws Exception;
	public List<Map<String,Object>> getNextDays(VFestivoCentro vFestivoCentro, Integer numMaximoDias) throws Exception;
}
