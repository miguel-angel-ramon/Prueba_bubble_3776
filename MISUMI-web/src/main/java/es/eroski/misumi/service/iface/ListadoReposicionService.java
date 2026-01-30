package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionCmbSeccion;
import es.eroski.misumi.model.ReposicionDatosTalla;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionHayDatos;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ui.Pagination;
;

public interface ListadoReposicionService {
	

	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/**************************************CONSULTAS PROCEDIMIENTOS PLSQL LISTADO REPO***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	
	/**************************************      PK_APR_LISTADO_REPO_MISUMI         ***********************************/

	public ReposicionHayDatos hayDatosReposicion(final Reposicion reposicion) throws Exception;
	public Reposicion borrarReposicion(final Reposicion reposicion) throws Exception;
	public ReposicionGuardar guardarReposicion(final ReposicionGuardar reposicionGuardar) throws Exception;
	public Reposicion finalizarTareaReposicion(final Reposicion reposicion) throws Exception;
	public Reposicion obtenerReposicion(final Reposicion reposicion) throws Exception;
	public ReposicionCmbSeccion obtenerSecciones(final Reposicion reposicion) throws Exception;
	public ReposicionDatosTalla obtenerDatosAdicionalesTalla(final Reposicion reposicion) throws Exception;
	
	
	public void eliminarTempListadoRepo(String codMac) throws Exception;
	public void insertarTempListadoRepo(Reposicion reposicion) throws Exception;
	public List<ReposicionLinea> findTempListadoRepo(Reposicion reposicion, Pagination pagination) throws Exception;
	public Long countTempListadoRepo(String codMac) throws Exception;
	
	
}
