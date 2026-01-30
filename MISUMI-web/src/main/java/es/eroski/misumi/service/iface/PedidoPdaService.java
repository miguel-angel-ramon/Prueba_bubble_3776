package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoPda;
import es.eroski.misumi.model.VDatosDiarioArt;

public interface PedidoPdaService {
	/**
	 * 
	 * @param session
	 * @param codArt
	 * @param codArtCaprabo
	 * @param descArticulo
	 * @param vDatosDiarioArt
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public PedidoPda lanzarEncargoPda3(final HttpSession session,final String codArt,final String codArtCaprabo, final String descArticulo, final VDatosDiarioArt vDatosDiarioArt, HttpServletResponse response)throws Exception;
	/**
	 * 
	 * @param pedidoAdicionalCompleto
	 * @param pedidoAdicionalCompletoPda
	 * @param session
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Integer insertarPedido(final PedidoAdicionalCompleto pedidoAdicionalCompleto, final PedidoAdicionalCompleto pedidoAdicionalCompletoPda, HttpSession session,HttpServletResponse response) throws Exception;
}
