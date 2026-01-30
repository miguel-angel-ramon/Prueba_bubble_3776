package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionNoServido;
import es.eroski.misumi.model.ui.Pagination;



public interface VConfirmacionNoServidoService {
	public List<VConfirmacionNoServido> findAll(VConfirmacionNoServido vConfirmacionNoServido) throws Exception ;
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, Pagination pagination) throws Exception ;
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception ;
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception;
	public Long findTotalReferenciasNoServidas(SeguimientoMiPedido seguimientoMiPedido)	throws Exception;
	public Float findTotalCajasNoServidas(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Boolean checkNSR(SeguimientoMiPedido seguimientoPedido);
	public Boolean isCentroVegalsa(Long codCentro) throws Exception;
	
}
