package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.VAgruComerRefPedidos;
import es.eroski.misumi.model.ui.Pagination;

public interface VAgruComerRefPedidosDao  {

	public List<VAgruComerRefPedidos> findAll(VAgruComerRefPedidos vAgruComerRefPedidos, Pagination pagination) throws Exception ;

}
