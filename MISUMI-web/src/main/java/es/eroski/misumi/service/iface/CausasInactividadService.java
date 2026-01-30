package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CausaInactividad;

public interface CausasInactividadService {

	public abstract List<CausaInactividad> findAll() throws Exception;

}