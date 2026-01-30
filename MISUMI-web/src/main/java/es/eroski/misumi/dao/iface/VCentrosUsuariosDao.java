package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;

public interface VCentrosUsuariosDao {
	public List<Centro> findByCodDesc(String matcher, String codUser) throws Exception;
	public List<Centro> findAll(Centro centro, String codUser) throws Exception;
	public List<Centro> listZonasByRegion(Centro centro, String codUser);
}
