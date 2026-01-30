package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.FotosReferencia;

public interface FotosReferenciaService {

	public FotosReferencia findImage(FotosReferencia fotosReferencia) throws Exception;

	public boolean checkImage(FotosReferencia fotosReferencia) throws Exception;

}