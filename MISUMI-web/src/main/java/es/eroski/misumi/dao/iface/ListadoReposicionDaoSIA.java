package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionCmbSeccion;
import es.eroski.misumi.model.ReposicionDatosTalla;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionHayDatos;



public interface ListadoReposicionDaoSIA {
	public ReposicionHayDatos hayDatosReposicion(final Reposicion reposicion) throws Exception;
	public Reposicion borrarReposicion(final Reposicion reposicion) throws Exception;
	public ReposicionGuardar guardarReposicion(final ReposicionGuardar reposicionGuardar) throws Exception;
	public Reposicion finalizarTareaReposicion(final Reposicion reposicion) throws Exception;
	public Reposicion obtenerReposicion(final Reposicion reposicion) throws Exception;
	public ReposicionCmbSeccion obtenerSecciones(final Reposicion reposicion) throws Exception;
	public ReposicionDatosTalla obtenerDatosAdicionalesTalla(final Reposicion reposicion) throws Exception;
}
