package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoRequestType;
import es.eroski.misumi.model.inventarioWS.ComunicarNoServidoResponseType;

public interface InventarioTiendaDao {

	public abstract ComunicarNoServidoResponseType comunicarNoServido(
			ComunicarNoServidoRequestType comunicarRequest) throws Exception;

}