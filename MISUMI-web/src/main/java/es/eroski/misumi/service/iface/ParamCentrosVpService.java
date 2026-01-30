package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.model.ParamCentrosVp;


public interface ParamCentrosVpService {

	public List<ParamCentrosVp> findAll(ParamCentrosVp paramCentrosVp) throws Exception;
	public Long findAllCont(ParamCentrosVp paramCentrosVp) throws Exception;
	public boolean esAutoservicio(ParamCentrosVp paramCentrosVp) throws Exception;
	public List<CentroAutoservicio> findCentroAutoServicioAll(CentroAutoservicio centroAutoservicio) throws Exception;
	public boolean esFacingCentro(ParamCentrosVp paramCentrosVp) throws Exception;
}
