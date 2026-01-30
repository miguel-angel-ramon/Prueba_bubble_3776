package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;

public interface MotivoTengoMuchoPocoDao  {

	public MotivoTengoMuchoPocoLista consultaMotivosTengoMuchoPoco(MotivoTengoMuchoPoco motivoTengoMuchoPoco) throws Exception ;
}
