package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CausaInactividad;

public interface CausasInactividadDao {

	public abstract List<CausaInactividad> findAll() throws Exception;

	public abstract CausaInactividad findOne(String descripcion)
			throws Exception;

	public abstract Integer getCausaSequence() throws Exception;

	public abstract void insertCausaInactividad(
			CausaInactividad causaInactividad);

}