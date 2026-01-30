package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;


public interface MotivoTengoMuchoPocoService {

	public MotivoTengoMuchoPocoLista consultaMotivosTengoMuchoPoco(MotivoTengoMuchoPoco motivoTengoMuchoPoco) throws Exception;
}
