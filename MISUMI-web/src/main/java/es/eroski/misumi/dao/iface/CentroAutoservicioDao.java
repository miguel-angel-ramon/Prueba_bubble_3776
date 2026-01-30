package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CentroAutoservicio;

public interface CentroAutoservicioDao  {

	public List<CentroAutoservicio> findAll(CentroAutoservicio centroAutoservicio) throws Exception ;

	public Long findAllCont(CentroAutoservicio centroAutoservicio) throws Exception;
	
	public Boolean esAutoservicio(CentroAutoservicio centroAutoservicio) throws Exception;

}
