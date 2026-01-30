package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CentroRefAprov;

public interface CentroRefAprovDao {

	List<String> findAll(CentroRefAprov centroRefAprov) throws Exception;

}