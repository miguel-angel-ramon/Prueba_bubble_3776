package es.eroski.misumi.service.iface;

import java.util.List;
import java.util.Map;

import es.eroski.misumi.model.VFestivoCentro;

public interface VFestivoCentroService {
	
	public String getNextDay(VFestivoCentro vFestivoCentro) throws Exception;
	
	public String getDiaLaborable(VFestivoCentro vFestivoCentro) throws Exception;
	
	public String getFechaFinNuevaReferencia(VFestivoCentro vFestivoCentro, Integer numDias) throws Exception;
	
	public List<Map<String,Object>> getNextDays(VFestivoCentro vFestivoCentro, Integer numMaximoDias) throws Exception;
}
