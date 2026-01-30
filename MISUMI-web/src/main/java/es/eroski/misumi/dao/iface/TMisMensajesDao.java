package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.Aviso;

public interface TMisMensajesDao {

	Aviso findOne(Aviso aviso) throws Exception;
	Long findCont(Aviso aviso) throws Exception;

}