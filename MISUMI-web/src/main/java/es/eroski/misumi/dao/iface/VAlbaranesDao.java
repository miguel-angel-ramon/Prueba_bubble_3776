package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.VAlbaran;
import es.eroski.misumi.model.ui.Pagination;

public interface VAlbaranesDao  {

	public List<VAlbaran> findAll(VAlbaran vAlbaran, Pagination pagination) throws Exception ;
	public Long findAllCont(VAlbaran vAlbaran) throws Exception ;
	public List<VAlbaran> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, List<Long> listaReferencias, Pagination pagination) throws Exception ;
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception ; 

}
