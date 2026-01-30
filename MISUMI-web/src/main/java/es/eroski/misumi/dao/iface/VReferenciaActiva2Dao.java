package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VReferenciaActiva2;

public interface VReferenciaActiva2Dao {

	List<VReferenciaActiva2> getNextDiaPedido(Long codCentro, Long codArt) throws Exception;

}