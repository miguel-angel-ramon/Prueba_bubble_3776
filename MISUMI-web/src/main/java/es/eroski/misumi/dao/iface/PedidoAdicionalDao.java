package es.eroski.misumi.dao.iface;


import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.pedidosPBLWS.ArrayOfDetalladoPedidoDetalladoPedido;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoModificarReponse;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.DetalladoPedidoObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalContarClasesResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;



public interface PedidoAdicionalDao {

	public abstract ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] validarArticulo(ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] validarRequest, HttpSession session) throws Exception;

	public abstract PedidoAdicionalContarClasesResponse contarClases(PedidoAdicionalContarClasesRequest contarClasesRequest, HttpSession session) throws Exception;

	public abstract PedidoAdicionalResponse[] eliminarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] eliminarRequest, HttpSession session) throws Exception;

	public abstract PedidoAdicionalResponse[] insertarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] insertarRequest, HttpSession session) throws Exception;

	public abstract PedidoAdicionalResponse[] modificarPedido(ArrayOfPedidoAdicionalPedidoAdicional[] modificarRequest, HttpSession session) throws Exception;

	public abstract PedidoAdicionalObtenerResponse obtenerPedido(PedidoAdicionalObtenerRequest obtenerRequest, HttpSession session) throws Exception;

	public abstract DetalladoPedidoObtenerResponse detalladoPedidoObtener(
			DetalladoPedidoObtenerRequest detalladoPedidoObtenerRequest, HttpSession session)
			throws Exception;

	public abstract DetalladoPedidoModificarReponse detalladoPedidoModificar(
			ArrayOfDetalladoPedidoDetalladoPedido[] detalladoPedidoModificarRequest, HttpSession session)
			throws Exception; 

}