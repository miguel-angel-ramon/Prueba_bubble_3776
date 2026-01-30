/**
 * 
 */
package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CalendarioVegalsaDao;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.calendariovegalsa.DiaCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.GridFilterBean;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.CalendarioVegalsaService;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

/**
 * MISUMI-301
 * Implemetacion de la capa de servicio para el Calendario de Vegalsa
 * @author BICUGUAL
 *
 */
@Service
public class CalendarioVegalsaServiceImpl implements CalendarioVegalsaService {

	@Autowired
	CalendarioVegalsaDao calendarioDao;
	
	@Override
	public List<OptionSelectBean> getLstMapasVegalsa() {

		List<MapaVegalsa> lstMapas = calendarioDao.getLstMapasVegalsa();
		List<OptionSelectBean> lstOptions = new ArrayList<OptionSelectBean>();
		for (MapaVegalsa mapa: lstMapas){
			OptionSelectBean option = new OptionSelectBean(String.valueOf(mapa.getCodMapa()), mapa.getCodMapa() + "-" + mapa.getDescMapa());
			lstOptions.add(option);
		}
		return lstOptions;
	}

	@Override
	public List<DiaCalendarioVegalsa> getLstDiasCalendarioVegalsa(Long codCentro, Integer codMapa, String mes) {
		
		List<DiaCalendarioVegalsa> lstDias = calendarioDao.getLstDiasCalendarioVegalsa(codCentro, codMapa, mes);
		
		return lstDias;
	}

	@Override
	public Page<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaGrid(Long codCentro, Integer codMapa, GridFilterBean filtros) {
		
		PaginationManager<DiaDetalleCalendarioVegalsa> paginationManager = new PaginationManagerImpl<DiaDetalleCalendarioVegalsa>();
		
		Long max=filtros.getMax()!=null?filtros.getMax():new Long(10);
		Long page=filtros.getPage()!=null?filtros.getPage():new Long(1);
		
		Pagination pagination= new Pagination(max,page);
		
		if (filtros.getSidx()!=null){
			pagination.setSort(filtros.getSidx());
        }
        
		if (filtros.getSord()!=null){
            pagination.setAscDsc(filtros.getSord());
        }
		
		List<DiaDetalleCalendarioVegalsa> lstDias = calendarioDao.getLstDiasDetalleCalendarioVegalsaForGrid(codCentro, codMapa, filtros.getFiltros(), pagination);
		
		Page<DiaDetalleCalendarioVegalsa> result = null;
		
		if (lstDias != null) {
			//Obtengo el numero total del registros mediante otra consulta porque la lista de referencias obtenida está ya paginada y no incluye todos los registros.
			Long records = calendarioDao.getCountDiasDetalleCalendarioVegalsaForGrid(codCentro, codMapa, filtros.getFiltros());

			result = paginationManager.paginate(new Page<DiaDetalleCalendarioVegalsa>(), lstDias,
					max.intValue(), records.intValue(), page.intValue());	

		} 
		else {
			return new Page<DiaDetalleCalendarioVegalsa>();
		}
		
		return result;

	}	
	
	@Override
	public List<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaExport(Long codCentro, Integer codMapa,String filtrosJson) throws Exception {
		
		List<DiaDetalleCalendarioVegalsa> lstDias = new ArrayList<DiaDetalleCalendarioVegalsa>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			GridFilterBean filtros = new GridFilterBean();

			// Realizo el marshing de JSON to Object
			filtros= mapper.readValue(filtrosJson, GridFilterBean.class);

			Pagination pagination= new Pagination();
			
			if (filtros.getSidx()!=null){
				pagination.setSort(filtros.getSidx());
	        }
	        
			if (filtros.getSord()!=null){
	            pagination.setAscDsc(filtros.getSord());
	        }
			
			lstDias = calendarioDao.getLstDiasDetalleCalendarioVegalsaForGrid(codCentro, codMapa, filtros.getFiltros(), pagination);
			
		} catch (Exception e) {
			throw e;
		}
		
		
		return lstDias;
	}

}
