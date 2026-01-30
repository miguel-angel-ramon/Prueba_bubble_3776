package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ListadoRefSegundaReposicion;
import es.eroski.misumi.model.ListadoRefSegundaReposicionExcel;
import es.eroski.misumi.model.ListadoRefSegundaReposicionSalida;
import es.eroski.misumi.model.ReferenciasSegundaReposicion;
import es.eroski.misumi.model.ui.Page;


public interface ListadoRefSegundaReposicionService {

	public ReferenciasSegundaReposicion findAll(ListadoRefSegundaReposicion listadoRefSegundaReposicion) throws Exception;
	public ReferenciasSegundaReposicion findAllExcel(ListadoRefSegundaReposicionExcel filtroListadoRefSegundaReposicionExcel) throws Exception;
	public List<ListadoRefSegundaReposicionSalida> ordenarLista(List<ListadoRefSegundaReposicionSalida> listaSfm, String index, String sortOrder) throws Exception;
	public Page<ListadoRefSegundaReposicionSalida> crearListaPaginada( List<ListadoRefSegundaReposicionSalida> listaSfm, Long page, Long max, String index, String sortOrder) throws Exception;
	
	
}
