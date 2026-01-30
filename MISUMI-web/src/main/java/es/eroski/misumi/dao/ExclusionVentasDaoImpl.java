package es.eroski.misumi.dao;

import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ExclusionVentasDao;
import es.eroski.misumi.dao.pedidosPBLWS.PedidosPBLSOAP;
import es.eroski.misumi.dao.pedidosPBLWS.WebServiceVRInboundLocator;
import es.eroski.misumi.model.ExclusionVentas;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarRequestType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaBorrarResponseType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarRequestType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaInsertarResponseType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerRequestType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentaObtenerResponseType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasBorrarType;
import es.eroski.misumi.model.pedidosPBLWS.ExclusionVentasType;
import es.eroski.misumi.model.pedidosPBLWS.TiendaType;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ExclusionVentasDaoImpl implements ExclusionVentasDao{
	 
	@Value( "${ws.pedidoAdicional}" )
	
	private String pedidosAdicionalWsdl;
	
	private static Logger logger = Logger.getLogger(ExclusionVentasDaoImpl.class);
	 
	private PedidosPBLSOAP proxy;
	 
	private void defineClient() throws Exception{
		WebServiceVRInboundLocator locator = new WebServiceVRInboundLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		
		QName qname = new QName("http://www.pblservices.com/wsdl/PedidosPBL/ServicioV1.0", "PedidosPBLSOAPInbound");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
		
		try {
			URL address = new URL(pedidosAdicionalWsdl);
			this.proxy = locator.getPedidosPBLSOAPInbound(address);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
	 }
		
	public ExclusionVentaObtenerResponseType exclusionObtener(ExclusionVentaObtenerRequestType exclusionVentaObtenerRequest) throws Exception{
		 
		this.defineClient();
		try {
			return proxy.exclusionVentaObtener(exclusionVentaObtenerRequest);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			return null;
		}
		
	} 

	public ExclusionVentaInsertarResponseType exclusionVentaInsertar(ExclusionVentaInsertarRequestType exclusionVentaInsertarRequest) throws Exception{
		 
		this.defineClient();
		try {
			return proxy.exclusionVentaInsertar(exclusionVentaInsertarRequest);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			return null;
		}
		
	}
	 
 	public ExclusionVentaBorrarResponseType exclusionVentaBorrar(ExclusionVentaBorrarRequestType exclusionVentaBorrarRequest) throws Exception{
		 
		this.defineClient();
		try {
			return proxy.exclusionVentaBorrar(exclusionVentaBorrarRequest);
		} catch (Exception e) {
			logger.error(StackTraceManager.getStackTrace(e));
			return null;
		}
		
	}

	@Override
	public List<ExclusionVentas> findAll(ExclusionVentas exclusionVentas) throws Exception {
	
		ExclusionVentaObtenerRequestType argument = new ExclusionVentaObtenerRequestType();
		
		TiendaType tiendaType = new TiendaType();
		tiendaType.setCodigoTienda((exclusionVentas.getCodCentro() != null && !("".equals(exclusionVentas.getCodCentro().toString())))?exclusionVentas.getCodCentro().toString():null);
		argument.setTienda(tiendaType);
		
		ExclusionVentaObtenerResponseType resultWS = new ExclusionVentaObtenerResponseType();
		
		//Llamamos al DAO que llama al WS para obtener el resultado.
		
		
		resultWS = this.exclusionObtener(argument);
		
		List<ExclusionVentas> resultado = null;
		if (resultWS != null)
		{
			resultado = tratarDatosObtenerWS(resultWS);
		}
	
		return resultado;

    }	
	 
 	 @Override
	 public List<ExclusionVentas> insertAll(List<ExclusionVentas> listaExclusionVentas) throws Exception {
	    	
 		ExclusionVentaInsertarRequestType argument = new ExclusionVentaInsertarRequestType();

 		ExclusionVentasType[] listaExclusionVentasTypes = new ExclusionVentasType[listaExclusionVentas.size()];
 		ExclusionVentasType campo = new ExclusionVentasType();

	 	SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
	 	
	 	for (int i=0;i<listaExclusionVentas.size();i++){
	 		
	 		campo = new ExclusionVentasType();
	 		campo.setIdentificador((listaExclusionVentas.get(i).getIdentificador() != null && !("".equals(listaExclusionVentas.get(i).getIdentificador().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getIdentificador()):null);
	 		campo.setCentro((listaExclusionVentas.get(i).getCodCentro() != null && !("".equals(listaExclusionVentas.get(i).getCodCentro().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getCodCentro()):null);
	 		campo.setArea((listaExclusionVentas.get(i).getGrupo1() != null && !("".equals(listaExclusionVentas.get(i).getGrupo1().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo1()):null);
	 		campo.setAreaDescripcion((listaExclusionVentas.get(i).getDescripGrupo1() != null)? listaExclusionVentas.get(i).getDescripGrupo1():"");
	 		campo.setSeccion((listaExclusionVentas.get(i).getGrupo2() != null && !("".equals(listaExclusionVentas.get(i).getGrupo2().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo2()):null);
	 		campo.setSeccionDescripcion((listaExclusionVentas.get(i).getDescripGrupo2() != null)? listaExclusionVentas.get(i).getDescripGrupo2():"");
			campo.setCategoria((listaExclusionVentas.get(i).getGrupo3() != null && !("".equals(listaExclusionVentas.get(i).getGrupo3().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo3()):null);
			campo.setCategoriaDescripcion((listaExclusionVentas.get(i).getDescripGrupo3() != null)? listaExclusionVentas.get(i).getDescripGrupo3():"");
			campo.setSubcategoria((listaExclusionVentas.get(i).getGrupo4() != null && !("".equals(listaExclusionVentas.get(i).getGrupo4().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo4()):null);
			campo.setSubcategoriaDescripcion((listaExclusionVentas.get(i).getDescripGrupo4() != null)? listaExclusionVentas.get(i).getDescripGrupo4():"");
			campo.setSegmento((listaExclusionVentas.get(i).getGrupo5() != null && !("".equals(listaExclusionVentas.get(i).getGrupo5().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo5()):null);
			campo.setSegmentoDescripcion((listaExclusionVentas.get(i).getDescripGrupo5() != null)? listaExclusionVentas.get(i).getDescripGrupo5():"");
			campo.setReferencia((listaExclusionVentas.get(i).getCodArt() != null && !("".equals(listaExclusionVentas.get(i).getCodArt().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getCodArt()):null);
			campo.setReferenciaDescripcion((listaExclusionVentas.get(i).getDescripArt() != null)? listaExclusionVentas.get(i).getDescripArt():"");
			campo.setCodigoRespuesta(new BigInteger("0"));
			campo.setDescripcionRespuesta("");
			
			Calendar calFecha = Calendar.getInstance();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			calFecha.setTime((listaExclusionVentas.get(i).getFecha() != null)? df.parse(listaExclusionVentas.get(i).getFecha()):null);
			calFecha.setTimeZone(tz);
			calFecha.set(Calendar.HOUR_OF_DAY, 0);
			calFecha.set(Calendar.MINUTE, 0);
			calFecha.set(Calendar.SECOND, 0);
			calFecha.set(Calendar.MILLISECOND, 0);

			campo.setFecha(calFecha);

			listaExclusionVentasTypes[i] = campo;
	 	}
		
	 	List<ExclusionVentas> resultado = null;
	 	
	 	if (listaExclusionVentas != null && listaExclusionVentas.size()>0){
			//Llamamos al DAO que llama al WS para obtener el resultado.
	 		argument.setListaExclusionVentas(listaExclusionVentasTypes);
		 	ExclusionVentaInsertarResponseType resultWS = this.exclusionVentaInsertar(argument);
			
	 		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
	 		
			if (resultWS != null)
			{
				resultado = tratarDatosInsertarWS(resultWS);
			}
	 	}
		return resultado;
    }
	 
	@Override
	public List<ExclusionVentas> removeAll(List<ExclusionVentas> listaExclusionVentas) throws Exception {

		
 		ExclusionVentaBorrarRequestType argument = new ExclusionVentaBorrarRequestType();

 		ExclusionVentasType[] listaExclusionVentasTypes = new ExclusionVentasType[listaExclusionVentas.size()];
 		ExclusionVentasType campo = new ExclusionVentasType();

	 	SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
	 	
	 	for (int i=0;i<listaExclusionVentas.size();i++){
	 		
	 		campo = new ExclusionVentasType();
	 		campo.setIdentificador((listaExclusionVentas.get(i).getIdentificador() != null && !("".equals(listaExclusionVentas.get(i).getIdentificador().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getIdentificador()):null);
	 		campo.setCentro((listaExclusionVentas.get(i).getCodCentro() != null && !("".equals(listaExclusionVentas.get(i).getCodCentro().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getCodCentro()):null);
	 		campo.setArea((listaExclusionVentas.get(i).getGrupo1() != null && !("".equals(listaExclusionVentas.get(i).getGrupo1().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo1()):null);
	 		campo.setAreaDescripcion((listaExclusionVentas.get(i).getDescripGrupo1() != null)? listaExclusionVentas.get(i).getDescripGrupo1():"");
	 		campo.setSeccion((listaExclusionVentas.get(i).getGrupo2() != null && !("".equals(listaExclusionVentas.get(i).getGrupo2().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo2()):null);
	 		campo.setSeccionDescripcion((listaExclusionVentas.get(i).getDescripGrupo2() != null)? listaExclusionVentas.get(i).getDescripGrupo2():"");
			campo.setCategoria((listaExclusionVentas.get(i).getGrupo3() != null && !("".equals(listaExclusionVentas.get(i).getGrupo3().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo3()):null);
			campo.setCategoriaDescripcion((listaExclusionVentas.get(i).getDescripGrupo3() != null)? listaExclusionVentas.get(i).getDescripGrupo3():"");
			campo.setSubcategoria((listaExclusionVentas.get(i).getGrupo4() != null && !("".equals(listaExclusionVentas.get(i).getGrupo4().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo4()):null);
			campo.setSubcategoriaDescripcion((listaExclusionVentas.get(i).getDescripGrupo4() != null)? listaExclusionVentas.get(i).getDescripGrupo4():"");
			campo.setSegmento((listaExclusionVentas.get(i).getGrupo5() != null && !("".equals(listaExclusionVentas.get(i).getGrupo5().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getGrupo5()):null);
			campo.setSegmentoDescripcion((listaExclusionVentas.get(i).getDescripGrupo5() != null)? listaExclusionVentas.get(i).getDescripGrupo5():"");
			campo.setReferencia((listaExclusionVentas.get(i).getCodArt() != null && !("".equals(listaExclusionVentas.get(i).getCodArt().toString())))?BigInteger.valueOf(listaExclusionVentas.get(i).getCodArt()):null);
			campo.setReferenciaDescripcion((listaExclusionVentas.get(i).getDescripArt() != null)? listaExclusionVentas.get(i).getDescripArt():"");
			campo.setCodigoRespuesta(new BigInteger("0"));
			campo.setDescripcionRespuesta("");

			Calendar calFecha = Calendar.getInstance();
			TimeZone tz = TimeZone.getTimeZone("UTC");
			calFecha.setTime((listaExclusionVentas.get(i).getFecha() != null)? df.parse(listaExclusionVentas.get(i).getFecha()):null);
			calFecha.setTimeZone(tz);
			calFecha.set(Calendar.HOUR_OF_DAY, 0);
			calFecha.set(Calendar.MINUTE, 0);
			calFecha.set(Calendar.SECOND, 0);
			calFecha.set(Calendar.MILLISECOND, 0);
			campo.setFecha(calFecha);

			listaExclusionVentasTypes[i] = campo;
	 	}

	 	List<ExclusionVentas> resultado = null;
	 	
	 	if (listaExclusionVentas != null && listaExclusionVentas.size()>0){
			//Llamamos al DAO que llama al WS para obtener el resultado.
	 		argument.setListaExclusionVentas(listaExclusionVentasTypes);
		 	ExclusionVentaBorrarResponseType resultWS = this.exclusionVentaBorrar(argument);
			
	 		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
	 		
			if (resultWS != null)
			{
				resultado = tratarDatosBorrarWS(resultWS, listaExclusionVentas.get(0).getCodCentro()); //Todos coincidirán en el centro, se coge el del primero
			}
	 	}
		return resultado;
    }
	 
 	private List<ExclusionVentas> tratarDatosObtenerWS(ExclusionVentaObtenerResponseType resultWS) throws Exception{
 		
 		//Transformación de datos para estructura de ExclusionVentas
 		List<ExclusionVentas> resultado = new ArrayList<ExclusionVentas>();
 		List<ExclusionVentasType> listaExclusionVentas = new ArrayList<ExclusionVentasType>();
 		ExclusionVentas filaResultado = new ExclusionVentas();
 		if (null != resultWS && null != resultWS.getListaExclusionVentas()){
 			listaExclusionVentas = Arrays.asList(resultWS.getListaExclusionVentas());
 		}

 		//Nos recorremos la lista principal
		for (int i=0;i<listaExclusionVentas.size();i++){

			filaResultado = new ExclusionVentas();
			filaResultado.setIdentificador((listaExclusionVentas.get(i).getIdentificador() != null && !("".equals(listaExclusionVentas.get(i).getIdentificador().toString())))?new Long(listaExclusionVentas.get(i).getIdentificador().toString()):0);
			filaResultado.setCodCentro((listaExclusionVentas.get(i).getCentro() != null && !("".equals(listaExclusionVentas.get(i).getCentro().toString())))?new Long(listaExclusionVentas.get(i).getCentro().toString()):0);
			filaResultado.setGrupo1((listaExclusionVentas.get(i).getArea() != null && !("".equals(listaExclusionVentas.get(i).getArea().toString())))?new Long(listaExclusionVentas.get(i).getArea().toString()):0);
			filaResultado.setDescripGrupo1((listaExclusionVentas.get(i).getAreaDescripcion() != null)? listaExclusionVentas.get(i).getAreaDescripcion():"");
			filaResultado.setGrupo2((listaExclusionVentas.get(i).getSeccion() != null && !("".equals(listaExclusionVentas.get(i).getSeccion().toString())))?new Long(listaExclusionVentas.get(i).getSeccion().toString()):0);
			filaResultado.setDescripGrupo2((listaExclusionVentas.get(i).getSeccionDescripcion() != null)? listaExclusionVentas.get(i).getSeccionDescripcion():"");
			filaResultado.setGrupo3((listaExclusionVentas.get(i).getCategoria() != null && !("".equals(listaExclusionVentas.get(i).getCategoria().toString())))?new Long(listaExclusionVentas.get(i).getCategoria().toString()):0);
			filaResultado.setDescripGrupo3((listaExclusionVentas.get(i).getCategoriaDescripcion() != null)? listaExclusionVentas.get(i).getCategoriaDescripcion():"");
			filaResultado.setGrupo4((listaExclusionVentas.get(i).getSubcategoria() != null && !("".equals(listaExclusionVentas.get(i).getSubcategoria().toString())))?new Long(listaExclusionVentas.get(i).getSubcategoria().toString()):0);
			filaResultado.setDescripGrupo4((listaExclusionVentas.get(i).getSubcategoriaDescripcion() != null)? listaExclusionVentas.get(i).getSubcategoriaDescripcion():"");
			filaResultado.setGrupo5((listaExclusionVentas.get(i).getSegmento() != null && !("".equals(listaExclusionVentas.get(i).getSegmento().toString())))?new Long(listaExclusionVentas.get(i).getSegmento().toString()):0);
			filaResultado.setDescripGrupo5((listaExclusionVentas.get(i).getSegmentoDescripcion() != null)? listaExclusionVentas.get(i).getSegmentoDescripcion():"");
			filaResultado.setCodArt((listaExclusionVentas.get(i).getReferencia() != null && !("".equals(listaExclusionVentas.get(i).getReferencia().toString())))?new Long(listaExclusionVentas.get(i).getReferencia().toString()):0);
			filaResultado.setDescripArt((listaExclusionVentas.get(i).getReferenciaDescripcion() != null)? listaExclusionVentas.get(i).getReferenciaDescripcion():"");
			filaResultado.setFecha((listaExclusionVentas.get(i).getFecha() != null)? Utilidades.formatearFecha(listaExclusionVentas.get(i).getFecha().getTime()):"");
			filaResultado.setCodError((listaExclusionVentas.get(i).getCodigoRespuesta() != null && !("".equals(listaExclusionVentas.get(i).getCodigoRespuesta().toString())))?new Long(listaExclusionVentas.get(i).getCodigoRespuesta().toString()):0);
			filaResultado.setDescripError((listaExclusionVentas.get(i).getDescripcionRespuesta() != null)? listaExclusionVentas.get(i).getDescripcionRespuesta():"");

			resultado.add(filaResultado);
		}

		return resultado;
 	}
	
 	private List<ExclusionVentas> tratarDatosInsertarWS(ExclusionVentaInsertarResponseType resultWS){
  		
  		//Transformación de datos para estructura de ExclusionVentas
  		List<ExclusionVentas> resultado = new ArrayList<ExclusionVentas>();
  		List<ExclusionVentasType> listaExclusionVentas = new ArrayList<ExclusionVentasType>();
  		ExclusionVentas filaResultado = new ExclusionVentas();
  		if (null != resultWS && null != resultWS.getListaExclusionVentas()){
 			listaExclusionVentas = Arrays.asList(resultWS.getListaExclusionVentas());
 		}
  		
  		//Nos recorremos la lista principal
 		for (int i=0;i<listaExclusionVentas.size();i++){

			filaResultado = new ExclusionVentas();
			filaResultado.setIdentificador((listaExclusionVentas.get(i).getIdentificador() != null && !("".equals(listaExclusionVentas.get(i).getIdentificador().toString())))?new Long(listaExclusionVentas.get(i).getIdentificador().toString()):null);
			filaResultado.setCodCentro((listaExclusionVentas.get(i).getCentro() != null && !("".equals(listaExclusionVentas.get(i).getCentro().toString())))?new Long(listaExclusionVentas.get(i).getCentro().toString()):null);
			filaResultado.setGrupo1((listaExclusionVentas.get(i).getArea() != null && !("".equals(listaExclusionVentas.get(i).getArea().toString())))?new Long(listaExclusionVentas.get(i).getArea().toString()):null);
			filaResultado.setDescripGrupo1((listaExclusionVentas.get(i).getAreaDescripcion() != null)? listaExclusionVentas.get(i).getAreaDescripcion():"");
			filaResultado.setGrupo2((listaExclusionVentas.get(i).getSeccion() != null && !("".equals(listaExclusionVentas.get(i).getSeccion().toString())))?new Long(listaExclusionVentas.get(i).getSeccion().toString()):null);
			filaResultado.setDescripGrupo2((listaExclusionVentas.get(i).getSeccionDescripcion() != null)? listaExclusionVentas.get(i).getSeccionDescripcion():"");
			filaResultado.setGrupo3((listaExclusionVentas.get(i).getCategoria() != null && !("".equals(listaExclusionVentas.get(i).getCategoria().toString())))?new Long(listaExclusionVentas.get(i).getCategoria().toString()):null);
			filaResultado.setDescripGrupo3((listaExclusionVentas.get(i).getCategoriaDescripcion() != null)? listaExclusionVentas.get(i).getCategoriaDescripcion():"");
			filaResultado.setGrupo4((listaExclusionVentas.get(i).getSubcategoria() != null && !("".equals(listaExclusionVentas.get(i).getSubcategoria().toString())))?new Long(listaExclusionVentas.get(i).getSubcategoria().toString()):null);
			filaResultado.setDescripGrupo4((listaExclusionVentas.get(i).getSubcategoriaDescripcion() != null)? listaExclusionVentas.get(i).getSubcategoriaDescripcion():"");
			filaResultado.setGrupo5((listaExclusionVentas.get(i).getSegmento() != null && !("".equals(listaExclusionVentas.get(i).getSegmento().toString())))?new Long(listaExclusionVentas.get(i).getSegmento().toString()):null);
			filaResultado.setDescripGrupo5((listaExclusionVentas.get(i).getSegmentoDescripcion() != null)? listaExclusionVentas.get(i).getSegmentoDescripcion():"");
			filaResultado.setCodArt((listaExclusionVentas.get(i).getReferencia() != null && !("".equals(listaExclusionVentas.get(i).getReferencia().toString())))?new Long(listaExclusionVentas.get(i).getReferencia().toString()):null);
			filaResultado.setDescripArt((listaExclusionVentas.get(i).getReferenciaDescripcion() != null)? listaExclusionVentas.get(i).getReferenciaDescripcion():"");
			filaResultado.setFecha((listaExclusionVentas.get(i).getFecha() != null)? Utilidades.formatearFecha(listaExclusionVentas.get(i).getFecha().getTime()):"");
			filaResultado.setCodError((listaExclusionVentas.get(i).getCodigoRespuesta() != null && !("".equals(listaExclusionVentas.get(i).getCodigoRespuesta().toString())))?new Long(listaExclusionVentas.get(i).getCodigoRespuesta().toString()):null);
			filaResultado.setDescripError((listaExclusionVentas.get(i).getDescripcionRespuesta() != null)? listaExclusionVentas.get(i).getDescripcionRespuesta():"");

 			resultado.add(filaResultado);
 		}

 		return resultado;
  	}

	private List<ExclusionVentas> tratarDatosBorrarWS(ExclusionVentaBorrarResponseType  resultWS, Long codCentro){
	 		
 		//Transformación de datos para estructura de ExclusionVentas
 		List<ExclusionVentas> resultado = new ArrayList<ExclusionVentas>();
 		List<ExclusionVentasBorrarType> listaExclusionVentas = new ArrayList<ExclusionVentasBorrarType>();
 		ExclusionVentas filaResultado = new ExclusionVentas();
  		if (null != resultWS && null != resultWS.getListaExclusionVentas()){
 			listaExclusionVentas = Arrays.asList(resultWS.getListaExclusionVentas());
 		}
 		
 		//Nos recorremos la lista principal
		for (int i=0;i<listaExclusionVentas.size();i++){
	           
			filaResultado = new ExclusionVentas();
			filaResultado.setCodCentro(codCentro);
			filaResultado.setIdentificador((listaExclusionVentas.get(i).getIdentificador() != null && !("".equals(listaExclusionVentas.get(i).getIdentificador().toString())))?new Long(listaExclusionVentas.get(i).getIdentificador().toString()):null);
			filaResultado.setCodError((listaExclusionVentas.get(i).getCodigoRespuesta() != null && !("".equals(listaExclusionVentas.get(i).getCodigoRespuesta().toString())))?new Long(listaExclusionVentas.get(i).getCodigoRespuesta().toString()):null);
			filaResultado.setDescripError((listaExclusionVentas.get(i).getDescripcionRespuesta() != null)? listaExclusionVentas.get(i).getDescripcionRespuesta():"");

			resultado.add(filaResultado);
		}

		return resultado;
 	}
}
