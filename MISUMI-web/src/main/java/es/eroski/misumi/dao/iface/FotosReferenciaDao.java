package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.FotosReferencia;

public interface FotosReferenciaDao {

	FotosReferencia findImage(FotosReferencia fotosReferencia) throws Exception;

	Long checkImage(FotosReferencia fotosReferencia) throws Exception;

}