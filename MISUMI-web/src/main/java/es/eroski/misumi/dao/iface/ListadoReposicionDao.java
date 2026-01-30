package es.eroski.misumi.dao.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionHayDatos;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ui.Pagination;



public interface ListadoReposicionDao {
	
	
	public void eliminarTempListadoRepo(String codMac) throws Exception;
	public void insertarTempListadoRepo(Reposicion reposicion) throws Exception;
	public List<ReposicionLinea> findTempListadoRepo(Reposicion reposicion, Pagination pagination) throws Exception;
	public Long countTempListadoRepo(String codMac) throws Exception;


}
