package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.User;

public interface TMisMensajesService {

	public Aviso obtenerAviso(Aviso aviso) throws Exception;
	public Boolean existeAviso(Aviso aviso) throws Exception;
	public Boolean existenAvisos(User user,boolean isPda) throws Exception;
}
