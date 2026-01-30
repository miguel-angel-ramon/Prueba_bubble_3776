package es.eroski.misumi.service.iface;

import java.util.List;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.CamposSeleccionadosVC;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.ui.Pagination;


public interface TPedidoAdicionalService {

	  public void delete(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void deleteHistorico() throws Exception;
	  public void deleteArticulo(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updateErrorArticulo(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updateModifCantArticulo(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updateErroresTesteo(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updateErrores(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void resetearGuardados(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updateModifFechaFinArticulo(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void insertAll(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception;
	  public List<TPedidoAdicional> findAll(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public TPedidoAdicional findOne(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public Long findAllCount(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updatePedidosValidar(final  List<CamposSeleccionadosVC> listToUpdate, final String sesionID) throws Exception;
	  public void updatePedido(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void updatePedidoVegalsa(Long idVegalsa, Double cantidad, String fechaIni, String fechaFin, String user) throws Exception;	  
	  public void insertAllNuevoOferta(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception;
	  public List<TPedidoAdicional> findAllPaginate(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception;
	  public void deleteCalendario(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void insertPlanogramadas(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void insertPedidosHTNoPbl(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void insertPedidosNoAliSIA(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public void deleteDatosSesionPedidoAdicional(Long codCentro, String idSesion) throws Exception;
	  public void obtenerPedidosAdicionales(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception;
	  public void obtenerPedidosAdicionales(List<TPedidoAdicional> listaPedidoAdicional, Long codCentro, String idSesion, HttpSession session) throws Exception;
	  public List<GenericExcelVO> findAllExcel(TPedidoAdicional tPedidoAdicional,String[] columnModel) throws Exception;
	  public List<String> findComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public String findSelectedComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public List<TPedidoAdicional> findAllBloqueos(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception;
	  public Long findAllBloqueosCont(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public List<String> findComboOfertaPeriodoMO(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public List<String> findComboEspacioPromoMO(TPedidoAdicional tPedidoAdicional) throws Exception;
	  
	  public void insertNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception;
	  public void updateNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception;
	  public int deleteNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception;
	  public void deleteAllNuevoReferencia(TPedidoAdicional tPedidoAdicional) throws Exception;
	  public Long findMontajesAdicionalesVegalsa(PedidoAdicionalE pedidoAdicionalE, Boolean oferta) throws Exception;
	  public void insertarPedidosVegalsa(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception;
	  public void deletePedidosVegalsa(Long idVegalsa) throws Exception;
	  public String getPrimeraFechaVentaDisponible(Long codCentro, Long codArticulo) throws Exception;
	  public MontajeVegalsa getPedidosVegalsa(Long codCentro,Long codArt) throws Exception;
}
