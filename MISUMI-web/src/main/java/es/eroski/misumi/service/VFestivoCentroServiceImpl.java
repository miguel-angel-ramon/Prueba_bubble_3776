package es.eroski.misumi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.service.iface.VFestivoCentroService;
import es.eroski.misumi.util.Utilidades;

@Service(value = "VFestivoCentroService")
public class VFestivoCentroServiceImpl implements VFestivoCentroService {

	@Autowired
	private VFestivoCentroDao vFestivoCentroDao;
	
	@Override
	public String getNextDay(VFestivoCentro vFestivoCentro) throws Exception {
		String fecha = this.vFestivoCentroDao.getNextDay(vFestivoCentro);
		if (null == fecha){
			return Utilidades.formatearFecha(new Date());
		} else {
			return fecha;
		}
	}
	
	@Override
	public String getDiaLaborable(VFestivoCentro vFestivoCentro) throws Exception {
		String fecha = this.vFestivoCentroDao.getDiaLaborable(vFestivoCentro);
		if (null == fecha){
			return Utilidades.formatearFecha(new Date());
		} else {
			return fecha;
		}
	}

	@Override
	public String getFechaFinNuevaReferencia(VFestivoCentro vFestivoCentro, Integer numDias) throws Exception{
		
		//En caso de que no exista la nueva fecha se devuelve la fecha actual
		String fecha = Utilidades.formatearFecha(new Date());
		List<Map<String,Object>> listaFechasSiguientes = null;
		listaFechasSiguientes = this.getNextDays(vFestivoCentro, numDias);

	    if (listaFechasSiguientes != null && listaFechasSiguientes.size() == numDias) {
	    	Map<String,Object> m = listaFechasSiguientes.get(numDias -1);
			fecha = (String) m.get("SIGUIENTE_DIA");
	    }
				
	    return fecha;
	}

	@Override
	public List<Map<String,Object>> getNextDays(VFestivoCentro vFestivoCentro, Integer numMaximoDias) throws Exception{
		return this.vFestivoCentroDao.getNextDays(vFestivoCentro, numMaximoDias);
	}
}
