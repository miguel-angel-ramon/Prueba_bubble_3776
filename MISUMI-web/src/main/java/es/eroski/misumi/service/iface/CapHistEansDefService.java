package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CapHistEansDef;


public interface CapHistEansDefService {

	public List<CapHistEansDef> findAll(CapHistEansDef capHistEansDef) throws Exception;
	public Long findAllCont(CapHistEansDef capHistEansDef) throws Exception;
	public CapHistEansDef findOne(CapHistEansDef capHistEansDef) throws Exception;
	public Long obtenerReferenciaEan(Long codigoEan) throws Exception;
}
