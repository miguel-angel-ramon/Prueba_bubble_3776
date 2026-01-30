package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;

public interface VCentrosUsuariosService {
	public List<Centro> findByCodDesc(String matcher, String codUser) throws Exception;
	public List<Centro> findAll(Centro centro, String codUser) throws Exception;
	public Centro findOne(Centro centro, String codUser) throws Exception;
	public List<Centro> listZonaByRegion(Centro centro, String codUser) throws Exception;
}
