package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionEnviado;
import es.eroski.misumi.model.ui.Pagination;

public interface VConfirmacionEnviadoDao  {

	public List<VConfirmacionEnviado> findAll(VConfirmacionEnviado vConfirmacionEnviado) throws Exception ;
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, List<Long> listaReferencias, Pagination pagination) throws Exception ;
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception ;
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception;
	public Long findTotalReferenciasBajoPedido(SeguimientoMiPedido seguimientoMiPedido);
	public Float findTotalCajasBajoPedido(SeguimientoMiPedido seguimientoMiPedido);
	public Long findTotalReferenciasEmpuje(SeguimientoMiPedido seguimientoMiPedido);
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido);
	public Long findTotalReferenciasImplCab(SeguimientoMiPedido seguimientoMiPedido);
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido);
	public Long findTotalReferenciasIntertienda(SeguimientoMiPedido seguimientoMiPedido);
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido);
	/** MISUMI-355
	 * Devuelve el numero de registros encontrados con cod_soc=13 (Centro Vegalsa) para el cod_centro indicado.
	 * 0 = No centro Vegalsa
	 * >0 = Centro Vegalsa
	 * 
	 * @param codCentro
	 * @return
	 * @throws Exception
	 */
	public int countCentroVegalsa(Long codCentro) throws Exception; 

}
