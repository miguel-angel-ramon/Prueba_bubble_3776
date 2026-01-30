package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ListadoRefSegundaReposicionDao;
import es.eroski.misumi.model.ListadoRefSegundaReposicion;
import es.eroski.misumi.model.ListadoRefSegundaReposicionExcel;
import es.eroski.misumi.model.ListadoRefSegundaReposicionSalida;
import es.eroski.misumi.model.ReferenciasSegundaReposicion;
import es.eroski.misumi.model.ui.Page;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ListadoRefSegundaReposicionService;
import es.eroski.misumi.util.ListadoRefSegundaReposicionComparator;
import es.eroski.misumi.util.PaginationManagerImpl;
import es.eroski.misumi.util.iface.PaginationManager;

@Service(value = "ListadoRefSegundaReposicionService")
public class ListadoRefSegundaReposicionServiceImpl implements ListadoRefSegundaReposicionService {
	//private static Logger logger = LoggerFactory.getLogger(ListadoRefSegundaReposicionServiceImpl.class);
	//private static Logger logger = Logger.getLogger(ListadoRefSegundaReposicionServiceImpl.class);
	private PaginationManager<ListadoRefSegundaReposicionSalida> paginationManager = new PaginationManagerImpl<ListadoRefSegundaReposicionSalida>();
    @Autowired
	private ListadoRefSegundaReposicionDao listadoRefSegundaReposicionDao;
    
	@Override
	 public ReferenciasSegundaReposicion findAll(ListadoRefSegundaReposicion filtroListadoRefSegundaReposicion) throws Exception {
		
		ReferenciasSegundaReposicion lista = this.listadoRefSegundaReposicionDao.findAll(filtroListadoRefSegundaReposicion);
		
		return lista;
				
	}
	
	@Override
	 public ReferenciasSegundaReposicion findAllExcel(ListadoRefSegundaReposicionExcel filtroListadoRefSegundaReposicionExcel) throws Exception {
		ListadoRefSegundaReposicion filtroListadoRefSegundaReposicion= new ListadoRefSegundaReposicion();
		filtroListadoRefSegundaReposicion.setCodCentro(filtroListadoRefSegundaReposicionExcel.getCodCentro());
		filtroListadoRefSegundaReposicion.setGrupo1(filtroListadoRefSegundaReposicionExcel.getCodgrupo1());
		filtroListadoRefSegundaReposicion.setGrupo2(filtroListadoRefSegundaReposicionExcel.getCodgrupo2());
		filtroListadoRefSegundaReposicion.setMes(filtroListadoRefSegundaReposicionExcel.getMes());
		filtroListadoRefSegundaReposicion.setListadoSeleccionados(filtroListadoRefSegundaReposicionExcel.getListadoSeleccionados());
		ReferenciasSegundaReposicion lista = this.listadoRefSegundaReposicionDao.findAll(filtroListadoRefSegundaReposicion);
		
		return lista;
				
	}
	
	@Override
	public List<ListadoRefSegundaReposicionSalida> ordenarLista(List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList, String index, String sortOrder) throws Exception {
		
		if (listadoRefSegundaReposicionSalidaList != null && index != null && !index.equals("")){
			Comparator<ListadoRefSegundaReposicionSalida> comparator = ListadoRefSegundaReposicionComparator.getComparator(index);
			if (null != listadoRefSegundaReposicionSalidaList && listadoRefSegundaReposicionSalidaList.size() > 0){
				Collections.sort(listadoRefSegundaReposicionSalidaList, comparator);
				if ("desc".equals(sortOrder)) {//El 'sort' siempre ordena en ascendente, por lo tanto si se quiere 
					//ordenar en descendente le damos la vuelta
					Collections.reverse(listadoRefSegundaReposicionSalidaList);
				}
			}
		}
		return listadoRefSegundaReposicionSalidaList;
	}
	
	@Override
	public Page<ListadoRefSegundaReposicionSalida> crearListaPaginada( List<ListadoRefSegundaReposicionSalida> listaRefSegundaReposicion, Long page, Long max, String index, String sortOrder) throws Exception {
		List<ListadoRefSegundaReposicionSalida> subListaRefSegundaReposicion = new ArrayList<ListadoRefSegundaReposicionSalida>();
		Page<ListadoRefSegundaReposicionSalida> listaRefSegundaReposicionPaginada = null;
		int records = 0;
		Pagination pagination= new Pagination(max,page);
		if (index!=null){
			pagination.setSort(index);
		}
		if (sortOrder!=null){
			pagination.setAscDsc(sortOrder);
		}
		Long elemInicio = (pagination.getPage()-1)*pagination.getRows();
		Long elemFinal = ((pagination.getPage())*pagination.getRows()) + 1;
		
		if (listaRefSegundaReposicion != null){ 
			records = listaRefSegundaReposicion.size();
			if (elemInicio <= records){
				if (elemFinal > records){
					elemFinal = new Long(records);
				}
				subListaRefSegundaReposicion = (listaRefSegundaReposicion).subList(Integer.parseInt(elemInicio.toString()), Integer.parseInt(elemFinal.toString()));
			}
		}
		listaRefSegundaReposicionPaginada = this.paginationManager.paginate(new Page<ListadoRefSegundaReposicionSalida>(), subListaRefSegundaReposicion,
					max.intValue(), records, page.intValue());	
		return listaRefSegundaReposicionPaginada;
	}
	
}
