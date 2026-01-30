package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionPedido;
import es.eroski.misumi.model.ui.Pagination;



public interface VConfirmacionPedidoService {

	public List<VConfirmacionPedido> findAll(VConfirmacionPedido vConfirmacionPedido) throws Exception ;
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, Pagination pagination) throws Exception ;
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception ;
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception;
	public Long findTotalReferenciasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Float findTotalCajasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Long findTotalReferenciasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Long findTotalReferenciasImplCab(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido)	throws Exception;
	public Long findTotalReferenciasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception;
	public VConfirmacionPedido findDetalleTipoPedido(SeguimientoMiPedido seguimientoMiPedido)	throws Exception;
	public Boolean isCentroVegalsa(Long codCentro) throws Exception;
	
	
	/**
	 * MISUMI-674
	 * Comprueba si un centro tiene lineas con ajuste de pedido y con fecha de entrega en tienda mayor o igual a la fecha del sistema
	 * @param codCentro
	 * @return
	 */
	boolean hasAjustePedido(Long codCentro);
	
}
