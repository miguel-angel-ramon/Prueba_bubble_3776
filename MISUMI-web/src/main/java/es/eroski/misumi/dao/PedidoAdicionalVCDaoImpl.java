package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoAdicionalDao;
import es.eroski.misumi.dao.iface.PedidoAdicionalVCDao;
import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.model.PedidoAdicionalVC;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalPedidoAdicional;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalResponse;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PedidoAdicionalVCDaoImpl implements PedidoAdicionalVCDao{
	 
	 @Autowired
	 private PedidoAdicionalDao pedidoAdicionalDao;
	 
	@Autowired
	 private PedidosAdCentralDao pedidosAdCentralDao;

	 @Override
	 public List<PedidoAdicionalVC> findAll(PedidoAdicionalVC pedidoAdicionalVC, HttpSession session) throws Exception {
	    	
			PedidoAdicionalObtenerRequest argument = new PedidoAdicionalObtenerRequest();
			
			argument.setClasePedidoAdicional((pedidoAdicionalVC.getClasePedido() != null && !("".equals(pedidoAdicionalVC.getClasePedido().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getClasePedido()):null);
			
			argument.setCentro((pedidoAdicionalVC.getCodCentro() != null && !("".equals(pedidoAdicionalVC.getCodCentro().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getCodCentro()):null);
			argument.setArea((pedidoAdicionalVC.getGrupo1() != null && !("".equals(pedidoAdicionalVC.getGrupo1().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo1()):null);
			argument.setSeccion((pedidoAdicionalVC.getGrupo2() != null && !("".equals(pedidoAdicionalVC.getGrupo2().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo2()):null);
			argument.setCategoria((pedidoAdicionalVC.getGrupo3() != null && !("".equals(pedidoAdicionalVC.getGrupo3().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo3()):null);
			argument.setReferencia((pedidoAdicionalVC.getCodArticulo() != null && !("".equals(pedidoAdicionalVC.getCodArticulo().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getCodArticulo()):null);
			
			PedidoAdicionalObtenerResponse resultWS = new PedidoAdicionalObtenerResponse();
			
			//Llamamos al DAO que llama al WS para obtener el resultado.
			resultWS = this.pedidoAdicionalDao.obtenerPedido(argument,session);
			
			List<PedidoAdicionalVC> resultado = null;
			if (resultWS != null)
			{
				resultado = tratarDatosObtenerWS(resultWS,pedidoAdicionalVC);
			}
	
			return resultado;
		 }
	 
	 @Override
	 public int count(PedidoAdicionalVC pedidoAdicionalVC, HttpSession session) throws Exception {
	    	
		int cuenta = 0;
		
		PedidoAdicionalObtenerRequest argument = new PedidoAdicionalObtenerRequest();
		
		argument.setClasePedidoAdicional((pedidoAdicionalVC.getClasePedido() != null && !("".equals(pedidoAdicionalVC.getClasePedido().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getClasePedido()):null);
		
		argument.setCentro((pedidoAdicionalVC.getCodCentro() != null && !("".equals(pedidoAdicionalVC.getCodCentro().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getCodCentro()):null);
		argument.setArea((pedidoAdicionalVC.getGrupo1() != null && !("".equals(pedidoAdicionalVC.getGrupo1().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo1()):null);
		argument.setSeccion((pedidoAdicionalVC.getGrupo2() != null && !("".equals(pedidoAdicionalVC.getGrupo2().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo2()):null);
		argument.setCategoria((pedidoAdicionalVC.getGrupo3() != null && !("".equals(pedidoAdicionalVC.getGrupo3().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getGrupo3()):null);
		argument.setReferencia((pedidoAdicionalVC.getCodArticulo() != null && !("".equals(pedidoAdicionalVC.getCodArticulo().toString())))?BigInteger.valueOf(pedidoAdicionalVC.getCodArticulo()):null);
		
		PedidoAdicionalObtenerResponse resultWS = new PedidoAdicionalObtenerResponse();
		
		//Llamamos al DAO que llama al WS para obtener el resultado.
		resultWS = this.pedidoAdicionalDao.obtenerPedido(argument,session);
		
		if (resultWS != null)
		{
	 		List<PedidoAdicional> pedidoAdicional = new ArrayList<PedidoAdicional>();
	 		if (null != resultWS.getPedidoAdicional()){
	 			pedidoAdicional = Arrays.asList(resultWS.getPedidoAdicional());
	 			cuenta = pedidoAdicional.size();
	 		}
		}

		return cuenta;
	 }
	 
	 	@Override
		 public List<PedidoAdicionalVC> modifyAll(List<PedidoAdicionalVC> listaPedidoAdicionalVC, String tipoModificado, HttpSession session) throws Exception {
		    	
	 		ArrayOfPedidoAdicionalPedidoAdicional[] argument = new ArrayOfPedidoAdicionalPedidoAdicional[listaPedidoAdicionalVC.size()];
			 	
	 		ArrayOfPedidoAdicionalPedidoAdicional campo = new ArrayOfPedidoAdicionalPedidoAdicional();


			 	for (int i=0;i<listaPedidoAdicionalVC.size();i++){
			 		
			 		campo = new ArrayOfPedidoAdicionalPedidoAdicional();
			 		campo.setCentro((listaPedidoAdicionalVC.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalVC.get(i).getCodCentro().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getCodCentro()):null);
			 		campo.setReferencia((listaPedidoAdicionalVC.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalVC.get(i).getCodArticulo().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getCodArticulo()):null);
			 		campo.setIdentificador((listaPedidoAdicionalVC.get(i).getIdentificador() != null && !("".equals(listaPedidoAdicionalVC.get(i).getIdentificador().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getIdentificador()):null);
			 		campo.setDescripcion(listaPedidoAdicionalVC.get(i).getDescriptionArt());
			 		campo.setClasePedidoAdicional((listaPedidoAdicionalVC.get(i).getClasePedido() != null && !("".equals(listaPedidoAdicionalVC.get(i).getClasePedido().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getClasePedido()):null);
			 		campo.setOferta((listaPedidoAdicionalVC.get(i).getOferta() != null)? listaPedidoAdicionalVC.get(i).getOferta():"");
					campo.setPerfil(BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getPerfil()));
					campo.setTipoAprovisionamiento(listaPedidoAdicionalVC.get(i).getTipoAprovisionamiento());
					campo.setUnidadesCaja(BigDecimal.valueOf(listaPedidoAdicionalVC.get(i).getUniCajaServ()));
					campo.setAgrupacion((listaPedidoAdicionalVC.get(i).getAgrupacion() != null && !("".equals(listaPedidoAdicionalVC.get(i).getAgrupacion())))?listaPedidoAdicionalVC.get(i).getAgrupacion():null);					
					campo.setUsuario(listaPedidoAdicionalVC.get(i).getUsuario());
			 		
			 		List<ArrayOfPedidoAdicionalLineaLinea> lista = obtenerLineasPedidoModificar(listaPedidoAdicionalVC.get(i),tipoModificado);
			 		
			 		campo.setLineas(lista.toArray(new ArrayOfPedidoAdicionalLineaLinea[lista.size()] ));
			 		argument[i] = campo;
			 	}
			 	
			 	
				
				//Llamamos al DAO que llama al WS para obtener el resultado.
				PedidoAdicionalResponse[] resultWS = this.pedidoAdicionalDao.modificarPedido(argument,session);
				
				//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
				List<PedidoAdicionalVC> resultado = tratarDatosModificarWS(resultWS);

				return resultado;
		    }
	 	 
	 	 private List<PedidoAdicionalVC> tratarDatosModificarWS(PedidoAdicionalResponse[] resultWS){
	 		
	 		//Transformaci�n de datos para estructura de PedidoAdicionalVC
	 		List<PedidoAdicionalVC> resultado = new ArrayList<PedidoAdicionalVC>();
	 		List<PedidoAdicionalResponse> pedidoAdicional = new ArrayList<PedidoAdicionalResponse>();
	 		PedidoAdicionalVC filaResultado = new PedidoAdicionalVC();
	 		pedidoAdicional = Arrays.asList(resultWS);
	 		
	 		//Nos recorremos la lista 
			for (int i=0;i<pedidoAdicional.size();i++){
				filaResultado = new PedidoAdicionalVC();
				filaResultado.setCodCentro((pedidoAdicional.get(i).getCentro() != null && !("".equals(pedidoAdicional.get(i).getCentro().toString())))?new Long(pedidoAdicional.get(i).getCentro().toString()):null);
				filaResultado.setCodArticulo((pedidoAdicional.get(i).getReferencia() != null && !("".equals(pedidoAdicional.get(i).getReferencia().toString())))?new Long(pedidoAdicional.get(i).getReferencia().toString()):null);
				filaResultado.setIdentificador((pedidoAdicional.get(i).getIdentificador() != null && !("".equals(pedidoAdicional.get(i).getIdentificador().toString())))?new Long(pedidoAdicional.get(i).getIdentificador().toString()):null);
				filaResultado.setCodigoRespuesta(pedidoAdicional.get(i).getCodigoRespuesta());
				filaResultado.setDescripcionRespuesta(pedidoAdicional.get(i).getDescripcionRespuesta());
				
				resultado.add(filaResultado);
			}
	 		return resultado;
	 	}
	 	 
	 	private List<ArrayOfPedidoAdicionalLineaLinea> obtenerLineasPedidoModificar(PedidoAdicionalVC pedido, String tipoModificado) throws Exception{
	 		
	 		List<ArrayOfPedidoAdicionalLineaLinea> listaLineas = new ArrayList<ArrayOfPedidoAdicionalLineaLinea>();
	 		ArrayOfPedidoAdicionalLineaLinea linea = new ArrayOfPedidoAdicionalLineaLinea();
			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
	 		//Tenemos que a�adir tantas l�neas como tuviese el pedido a modificar.
			//Primera línea.
	 		//Fesco Puro
			if (pedido.getTipoPedido().equals(Constantes.TIPO_PEDIDO_ENCARGO))
			{
				//Se trata de un Fresco Puro
		 		linea = new ArrayOfPedidoAdicionalLineaLinea();
		 		char[] arrayModifIndiv = pedido.getModificableIndiv().toCharArray();
		 		
	
				linea.setFechaInicio(df.parse(pedido.getFechaInicio()));
				/****Cambio 1.31 ya no se tiene que hacer.
		 		//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
				if (pedido.getCantidad1() != null &&!("".equals(pedido.getCantidad1().toString()))&&
						pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
				{
					Double cantidad = pedido.getCantidad1()*pedido.getUniCajaServ();
					linea.setCantidad(new BigDecimal(cantidad.toString()));
				}*/
				linea.setCantidad((pedido.getCantidad1() != null && !("".equals(pedido.getCantidad1().toString())))?new BigDecimal(pedido.getCantidad1().toString()):null);
				
		 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
		 		if (arrayModifIndiv != null && arrayModifIndiv.length>0)
		 		{
		 			linea.setModificable(String.valueOf(arrayModifIndiv[0]));
		 		}
		 		
		 		linea.setExcluir(pedido.isExcluir());
		 		linea.setCajas(pedido.isCajas());
		 		linea.setTratamiento(pedido.getTratamiento());
		 		
		 		if ((tipoModificado.equals(Constantes.BORRADO_LOGICO))||(pedido.getFecha2() != null && !pedido.getFecha2().equals("")))
				{
		 			listaLineas.add(linea);
				}
		 		else if (pedido.getFecha2() == null)
		 		{
		 			//En este caso tiene una única línea que es la que hay que modificar.
		 			//Primero borramos.
		 			BigDecimal cantLinea = linea.getCantidad();
		 			linea.setCantidad(new BigDecimal(new Long(0)));
		 			listaLineas.add(linea);
		 			//Ahora insertamos con la nueva fecha.
		 			ArrayOfPedidoAdicionalLineaLinea lineaNueva = linea.clone();
		 			lineaNueva.setCantidad(cantLinea);
		 			lineaNueva.setFechaInicio(df.parse(pedido.getFechaFin()));
		 			listaLineas.add(lineaNueva);
		 		}
		 		
		 		//Segunda l�nea.
		 		linea = new ArrayOfPedidoAdicionalLineaLinea();
		 		
		 		if (pedido.getFecha2() != null && !pedido.getFecha2().equals(""))
		 		{
	
					linea.setFechaInicio(df.parse(pedido.getFecha2()));
					/****Cambio 1.31 ya no se tiene que hacer.
			 		//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
					if (pedido.getCantidad2() != null &&!("".equals(pedido.getCantidad2().toString()))&&
							pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
					{
						Double cantidad = pedido.getCantidad2()*pedido.getUniCajaServ();
						linea.setCantidad(new BigDecimal(cantidad.toString()));
					}*/
					linea.setCantidad((pedido.getCantidad2() != null && !("".equals(pedido.getCantidad2().toString())))?new BigDecimal(pedido.getCantidad2().toString()):null);
					
			 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
			 		if (arrayModifIndiv != null && arrayModifIndiv.length>1)
			 		{
			 			linea.setModificable(String.valueOf(arrayModifIndiv[1]));
			 		}
			 		
			 		linea.setExcluir(pedido.isExcluir());
			 		linea.setCajas(pedido.isCajas());
			 		linea.setTratamiento(pedido.getTratamiento());
			 		
			 		if ((tipoModificado.equals(Constantes.BORRADO_LOGICO))||(pedido.getFecha3() != null && !pedido.getFecha3().equals("")))
					{
			 			listaLineas.add(linea);
					}
			 		else if (pedido.getFecha3() == null)
			 		{
			 			//En este caso tiene sólo dos líneas y habrá que modificar la segunda
			 			//Primero borramos.
			 			BigDecimal cantLinea = linea.getCantidad();
			 			linea.setCantidad(new BigDecimal(new Long(0)));
			 			listaLineas.add(linea);
			 			//Ahora insertamos con la nueva fecha.
			 			ArrayOfPedidoAdicionalLineaLinea lineaNueva = linea.clone();
			 			lineaNueva.setCantidad(cantLinea);
			 			lineaNueva.setFechaInicio(df.parse(pedido.getFechaFin()));
			 			listaLineas.add(lineaNueva);
			 		}
			 		
			 		//Tercera l�nea.
			 		linea = new ArrayOfPedidoAdicionalLineaLinea();
			 		
			 		if (pedido.getFecha3() != null && !pedido.getFecha3().equals(""))
			 		{
						linea.setFechaInicio(df.parse(pedido.getFecha3()));
						/****Cambio 1.31 ya no se tiene que hacer.
						//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
						if (pedido.getCantidad3() != null &&!("".equals(pedido.getCantidad3().toString()))&&
								pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
						{
							Double cantidad = pedido.getCantidad3()*pedido.getUniCajaServ();
							linea.setCantidad(new BigDecimal(cantidad.toString()));
						}*/
						linea.setCantidad((pedido.getCantidad3() != null && !("".equals(pedido.getCantidad3().toString())))?new BigDecimal(pedido.getCantidad3().toString()):null);
						
				 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
				 		if (arrayModifIndiv != null && arrayModifIndiv.length>2)
				 		{
				 			linea.setModificable(String.valueOf(arrayModifIndiv[2]));
				 		}
				 		
				 		linea.setExcluir(pedido.isExcluir());
				 		linea.setCajas(pedido.isCajas());
				 		linea.setTratamiento(pedido.getTratamiento());
				 		
				 		if ((tipoModificado.equals(Constantes.BORRADO_LOGICO))||(pedido.getFecha4() != null && !pedido.getFecha4().equals("")))
						{
				 			listaLineas.add(linea);
						}
				 		else if (pedido.getFecha4() == null)
				 		{
				 			//En este caso tiene sólo tres líneas y habrá que modificar la segunda
				 			//Primero borramos.
				 			BigDecimal cantLinea = linea.getCantidad();
				 			linea.setCantidad(new BigDecimal(new Long(0)));
				 			listaLineas.add(linea);
				 			//Ahora insertamos con la nueva fecha.
				 			ArrayOfPedidoAdicionalLineaLinea lineaNueva = linea.clone();
				 			lineaNueva.setCantidad(cantLinea);
				 			lineaNueva.setFechaInicio(df.parse(pedido.getFechaFin()));
				 			listaLineas.add(lineaNueva);
				 		}
				 		
				 		//Cuarta l�nea.
				 		linea = new ArrayOfPedidoAdicionalLineaLinea();
				 		
				 		if (pedido.getFecha4() != null && !pedido.getFecha4().equals(""))
				 		{
							linea.setFechaInicio(df.parse(pedido.getFecha4()));
							
							//Para realizar un borrado lógico se envía un 1 en la capacidad.(Correo de Carus del 01/08/2013)
							if (tipoModificado.equals(Constantes.BORRADO_LOGICO))
							{
								linea.setFechaFin(df.parse(pedido.getFechaFin()));
								linea.setCantidad(new BigDecimal(new Long(1)));
							}
							else
							{
								linea.setFechaFin(df.parse(pedido.getFechaPilada()));
							}
							
					 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_PILADA);
					 		if (arrayModifIndiv != null && arrayModifIndiv.length>3)
					 		{
					 			linea.setModificable(String.valueOf(arrayModifIndiv[3]));
					 		}
					 		linea.setExcluir(pedido.isExcluir());
					 		
					 		if (tipoModificado.equals(Constantes.BORRADO_LOGICO))
							{
					 			listaLineas.add(linea);
							}
					 		else
					 		{
					 			//En este caso tiene tiene las cuatro líneas incluido la pilada, habrá que modificar directamente la línea
					 			linea.setFechaFin(df.parse(pedido.getFechaFin()));
					 			listaLineas.add(linea);
					 		}
				 		}
			 		}
		 		}
			}
			else
			{
				//No es un Fresco Puro.
				linea = new ArrayOfPedidoAdicionalLineaLinea();
		 		
				linea.setFechaInicio(df.parse(pedido.getFechaInicio()));
		 		linea.setCapacidad(BigInteger.valueOf(pedido.getCapMax().longValue()));
		 		linea.setImplantacionMinima(BigInteger.valueOf(pedido.getCapMin().longValue()));
		 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_PILADA);
		 		linea.setExcluir(pedido.isExcluir());
		 		linea.setCajas(pedido.isCajas());
		 		linea.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);

		 		//Habrá que modificar directamente la línea
	 			linea.setFechaFin(df.parse(pedido.getFechaFin()));
	 			listaLineas.add(linea);
			}
	 		
	 		return listaLineas;
	 	}
	 	
	 	private List<ArrayOfPedidoAdicionalLineaLinea> obtenerLineasPedidoBorrar(PedidoAdicionalVC pedido) throws Exception{
	 		
	 		List<ArrayOfPedidoAdicionalLineaLinea> listaLineas = new ArrayList<ArrayOfPedidoAdicionalLineaLinea>();
	 		ArrayOfPedidoAdicionalLineaLinea linea = new ArrayOfPedidoAdicionalLineaLinea();
	 		
	 		//Tenemos que a�adir tantas l�neas como tuviese el pedido a borrar.
		 	//Primera l�nea.
	 		linea = new ArrayOfPedidoAdicionalLineaLinea();
	 		

			SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
			linea.setFechaInicio(df.parse(pedido.getFechaInicio()));
			/****Cambio 1.31 ya no se tiene que hacer.
	 		//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
			if (pedido.getCantidad1() != null &&!("".equals(pedido.getCantidad1().toString()))&&
					pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
			{
				Double cantidad = pedido.getCantidad1()*pedido.getUniCajaServ();
				linea.setCantidad(new BigDecimal(cantidad.toString()));
			}*/
			linea.setCantidad((pedido.getCantidad1() != null && !("".equals(pedido.getCantidad1().toString())))?new BigDecimal(pedido.getCantidad1().toString()):null);
	 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
	 		linea.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
	 		listaLineas.add(linea);
	 		
	 		//Segunda l�nea.
	 		linea = new ArrayOfPedidoAdicionalLineaLinea();
	 		
	 		if (pedido.getFecha2() != null && !pedido.getFecha2().equals(""))
	 		{

				linea.setFechaInicio(df.parse(pedido.getFecha2()));
				/****Cambio 1.31 ya no se tiene que hacer.
				//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
				if (pedido.getCantidad2() != null &&!("".equals(pedido.getCantidad2().toString()))&&
						pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
				{
					Double cantidad = pedido.getCantidad2()*pedido.getUniCajaServ();
					linea.setCantidad(new BigDecimal(cantidad.toString()));
				}*/
				linea.setCantidad((pedido.getCantidad2() != null && !("".equals(pedido.getCantidad2().toString())))?new BigDecimal(pedido.getCantidad2().toString()):null);
		 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
		 		linea.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
		 		
		 		listaLineas.add(linea);
		 		
		 		//Tercera l�nea.
		 		linea = new ArrayOfPedidoAdicionalLineaLinea();
		 		
		 		if (pedido.getFecha3() != null && !pedido.getFecha3().equals(""))
		 		{
					linea.setFechaInicio(df.parse(pedido.getFecha3()));
					/****Cambio 1.31 ya no se tiene que hacer.
			 		//Volvemos a obtener la cantidad que habíamos dividido por las unidades caja al obtenerlo del WS.
					if (pedido.getCantidad3() != null &&!("".equals(pedido.getCantidad3().toString()))&&
							pedido.getUniCajaServ() != null &&!("".equals(pedido.getUniCajaServ().toString())))
					{
						Double cantidad = pedido.getCantidad3()*pedido.getUniCajaServ();
						linea.setCantidad(new BigDecimal(cantidad.toString()));
					}*/
					linea.setCantidad((pedido.getCantidad3() != null && !("".equals(pedido.getCantidad3().toString())))?new BigDecimal(pedido.getCantidad3().toString()):null);

			 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_ENCARGO);
			 		linea.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
			 		
			 		listaLineas.add(linea);
			 		
			 		//Cuarta l�nea.
			 		linea = new ArrayOfPedidoAdicionalLineaLinea();
			 		
			 		if (pedido.getFecha4() != null && !pedido.getFecha4().equals(""))
			 		{

						linea.setFechaInicio(df.parse(pedido.getFecha4()));

						linea.setFechaFin(df.parse(pedido.getFechaFin()));
				 		
						linea.setCantidad(new BigDecimal(new Long(0)));
				 		linea.setTipoPedidoAdicional(Constantes.TIPO_PEDIDO_PILADA);
				 		linea.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
				 		
				 		listaLineas.add(linea);
			 		}
		 		}
	 		}
	 		
	 		return listaLineas;
	 	}	 	
	 	
	 	@Override
		 public List<PedidoAdicionalVC> validateAll(List<PedidoAdicionalVC> listaPedidoAdicionalVC, HttpSession session) throws Exception {
		    	
	 		ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[] argument = new ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo[listaPedidoAdicionalVC.size()] ;
			 	
	 			
			 	ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo campo = new ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo();

			 	for (int i=0;i<listaPedidoAdicionalVC.size();i++){
			 		
			 		campo = new ArrayOfValidarArticuloRequestValidarArticuloValidarArticulo();
			 		campo.setCentro((listaPedidoAdicionalVC.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalVC.get(i).getCodCentro().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getCodCentro()):null);
			 		campo.setReferencia((listaPedidoAdicionalVC.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalVC.get(i).getCodArticulo().toString())))?BigInteger.valueOf(listaPedidoAdicionalVC.get(i).getCodArticulo()):null);

			 		argument[i] = campo;
			 	}
			 	
				
				//Llamamos al DAO que llama al WS para obtener el resultado.
			 	ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[]  resultWS = this.pedidoAdicionalDao.validarArticulo(argument, session);
				
				//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
				List<PedidoAdicionalVC> resultado = tratarDatosValidarWS(resultWS);

				return resultado;
		    }
	 	
	 	
	 	private List<PedidoAdicionalVC> tratarDatosValidarWS(ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo[] resultWS){
	 		
	 		//Transformación de datos para estructura de PedidoAdicionalE
	 		List<PedidoAdicionalVC> resultado = new ArrayList<PedidoAdicionalVC>();
	 		List<ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo> pedidoAdicional = new ArrayList<ArrayOfValidarArticuloResponseValidarArticuloValidarArticulo>();
	 		PedidoAdicionalVC filaResultado = new PedidoAdicionalVC();
	 		pedidoAdicional = Arrays.asList(resultWS);
	 		
	 		//Nos recorremos la lista 
			for (int i=0;i<pedidoAdicional.size();i++){
				filaResultado = new PedidoAdicionalVC();
				filaResultado.setCodCentro((pedidoAdicional.get(i).getCentro() != null && !("".equals(pedidoAdicional.get(i).getCentro().toString())))?new Long(pedidoAdicional.get(i).getCentro().toString()):null);
				filaResultado.setCodArticulo((pedidoAdicional.get(i).getReferencia() != null && !("".equals(pedidoAdicional.get(i).getReferencia().toString())))?new Long(pedidoAdicional.get(i).getReferencia().toString()):null);
				filaResultado.setCodigoRespuesta(pedidoAdicional.get(i).getCodigoRespuesta());
				filaResultado.setDescripcionRespuesta(pedidoAdicional.get(i).getDescripcionRespuesta());
				
				if (!filaResultado.getCodigoRespuesta().equals("1"))
				{
					//Si no ha fallado el WS.
					filaResultado.setFechaInicio(Utilidades.formatearFecha(pedidoAdicional.get(i).getDatos().getPrimeraFechaServicio()));
				}
				
				resultado.add(filaResultado);
			}
	 		return resultado;
	 	}
	 
	 	/*
	     * Converts XMLGregorianCalendar to java.util.Date in Java
	    */
	    private static String toStringDate(XMLGregorianCalendar calendar){
	        if(calendar == null) {
	            return null;
	        }
	        Date fecDate = calendar.toGregorianCalendar().getTime();
	        
	        return Utilidades.formatearFecha(fecDate);
	    }
	    
	 	private List<PedidoAdicionalVC> tratarDatosObtenerWS(PedidoAdicionalObtenerResponse resultWS, PedidoAdicionalVC pedidoAdicionalVC) throws Exception{
	 		
	 		//Transformaci�n de datos para estructura de PedidoAdicionalM
	 		List<PedidoAdicionalVC> resultado = new ArrayList<PedidoAdicionalVC>();
	 		List<PedidoAdicional> pedidoAdicional = new ArrayList<PedidoAdicional>();
	 		PedidoAdicionalVC filaResultado = new PedidoAdicionalVC();
	 		if (null != resultWS.getPedidoAdicional()){
	 			pedidoAdicional = Arrays.asList(resultWS.getPedidoAdicional());
	 		}
	 		boolean esFrescoPuro = false;
	 		
	 		//Nos recorremos la lista principal
	 		int indice = 0;
			for (int i=0;i<pedidoAdicional.size();i++){
				filaResultado = new PedidoAdicionalVC();
				filaResultado.setCodCentro((pedidoAdicional.get(i).getCentro() != null && !("".equals(pedidoAdicional.get(i).getCentro().toString())))?new Long(pedidoAdicional.get(i).getCentro().toString()):null);
				filaResultado.setClasePedido((pedidoAdicional.get(i).getClasePedidoAdicional() != null && !("".equals(pedidoAdicional.get(i).getClasePedidoAdicional().toString())))?new Long(pedidoAdicional.get(i).getClasePedidoAdicional().toString()):null);
				filaResultado.setIdentificador((pedidoAdicional.get(i).getIdentificador() != null && !("".equals(pedidoAdicional.get(i).getIdentificador().toString())))?new Long(pedidoAdicional.get(i).getIdentificador().toString()):null);
				filaResultado.setCodArticulo((pedidoAdicional.get(i).getReferencia() != null && !("".equals(pedidoAdicional.get(i).getReferencia().toString())))?new Long(pedidoAdicional.get(i).getReferencia().toString()):null);
				filaResultado.setDescriptionArt((pedidoAdicional.get(i).getDescripcion() != null && !("".equals(pedidoAdicional.get(i).getDescripcion())))?pedidoAdicional.get(i).getDescripcion():null);
				filaResultado.setUniCajaServ((pedidoAdicional.get(i).getUnidadesCaja() != null && !("".equals(pedidoAdicional.get(i).getUnidadesCaja().toString())))?new Double(pedidoAdicional.get(i).getUnidadesCaja().toString()):null);
				filaResultado.setUsuario((pedidoAdicional.get(i).getUsuario() != null && !("".equals(pedidoAdicional.get(i).getUsuario())))?pedidoAdicional.get(i).getUsuario():null);
				filaResultado.setPerfil((pedidoAdicional.get(i).getPerfil() != null && !("".equals(pedidoAdicional.get(i).getPerfil().toString())))?new Long(pedidoAdicional.get(i).getPerfil().toString()):null);
				filaResultado.setAgrupacion((pedidoAdicional.get(i).getAgrupacion() != null && !("".equals(pedidoAdicional.get(i).getAgrupacion())))?pedidoAdicional.get(i).getAgrupacion():null);
				filaResultado.setOferta((pedidoAdicional.get(i).getOferta() != null && !("".equals(pedidoAdicional.get(i).getOferta())))?pedidoAdicional.get(i).getOferta():null);
				filaResultado.setTipoAprovisionamiento(pedidoAdicional.get(i).getTipoAprovisionamiento());
				
				 ArrayOfPedidoAdicionalLineaLinea[] datoLinea = pedidoAdicional.get(i).getLineas();
				List<ArrayOfPedidoAdicionalLineaLinea> listaLineas = Arrays.asList(datoLinea);
				
				//Inicializamos a Borrable el pedido.
				filaResultado.setBorrable(Constantes.PEDIDO_BORRABLE);
				//Inicializamos a NO Modificable el pedido.
				filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				//Inicializamos a vacío el estado del pedido.
				filaResultado.setEstado("");

				//Nos recorremos la lista de lineas
				for (int j=0;j<listaLineas.size();j++){
					
					filaResultado.setFechaFin(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
					
					if (j==0)
					{	
						//Si estamos en la primera l�nea, obtenemos la fecha de inicio.
						filaResultado.setFechaInicio(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
						filaResultado.setExcluir(listaLineas.get(j).isExcluir());
						filaResultado.setCajas(listaLineas.get(j).getCajas());
						filaResultado.setTratamiento(listaLineas.get(j).getTratamiento());
						//Si la primera l�nea que me llega es un encargo, se trata de un Fresco Puro si no, no.
						if (listaLineas.get(j).getTipoPedidoAdicional().equals(Constantes.TIPO_PEDIDO_ENCARGO))
						{
							//Se trata de un Fresco Puro
							esFrescoPuro = true;
							
							//Al asignar la cantidades pedidas, tenemos que dividir la cantidad que me llega del WS por las Unidades Caja.
							filaResultado.setCantidad1((listaLineas.get(j).getCantidad() != null && !("".equals(listaLineas.get(j).getCantidad().toString())))?new Double(listaLineas.get(j).getCantidad().toString()):null);
							/****Cambio 1.31 ya no se tiene que hacer.
							if (filaResultado.getUniCajaServ() != null && filaResultado.getUniCajaServ()>0 && filaResultado.getCantidad1() != null)
							{
								filaResultado.setCantidad1(filaResultado.getCantidad1()/filaResultado.getUniCajaServ());
							}*/
						}
						else
						{
							//No es un Fresco Puro
							esFrescoPuro = false;
							
							filaResultado.setCapMin((listaLineas.get(j).getImplantacionMinima() != null && !("".equals(listaLineas.get(j).getImplantacionMinima().toString())))?new Double(listaLineas.get(j).getImplantacionMinima().toString()):null);
							filaResultado.setCapMax((listaLineas.get(j).getCapacidad() != null && !("".equals(listaLineas.get(j).getCapacidad().toString())))?new Double(listaLineas.get(j).getCapacidad().toString()):null);
						}
						
						//Añadimos el tipo de pedido.
						filaResultado.setTipoPedido((listaLineas.get(j).getTipoPedidoAdicional() != null && !("".equals(listaLineas.get(j).getTipoPedidoAdicional())))?listaLineas.get(j).getTipoPedidoAdicional():null);
						
						//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la primera línea
						filaResultado.setModificableIndiv(listaLineas.get(j).getModificable());
						
					}

					//Control de si el registro es modificable y borrable y en qué grado.
					if (esFrescoPuro)
					{
						//Segunda línea
						if (j==1)
						{
							filaResultado.setCantidad2((listaLineas.get(j).getCantidad() != null && !("".equals(listaLineas.get(j).getCantidad().toString())))?new Double(listaLineas.get(j).getCantidad().toString()):null);
							/****Cambio 1.31 ya no se tiene que hacer.
							if (filaResultado.getUniCajaServ() != null && filaResultado.getUniCajaServ()>0 && filaResultado.getCantidad2() != null)
							{
								filaResultado.setCantidad2(filaResultado.getCantidad2()/filaResultado.getUniCajaServ());
							}*/
							filaResultado.setFecha2(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							
							//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la segunda línea
							filaResultado.setModificableIndiv(filaResultado.getModificableIndiv()+listaLineas.get(j).getModificable());
							
						}
						//Tercera línea
						else if (j==2)
						{
							filaResultado.setCantidad3((listaLineas.get(j).getCantidad() != null && !("".equals(listaLineas.get(j).getCantidad().toString())))?new Double(listaLineas.get(j).getCantidad().toString()):null);
							/****Cambio 1.31 ya no se tiene que hacer.
							if (filaResultado.getUniCajaServ() != null && filaResultado.getUniCajaServ()>0 && filaResultado.getCantidad3() != null)
							{
								filaResultado.setCantidad3(filaResultado.getCantidad3()/filaResultado.getUniCajaServ());
							}*/
							filaResultado.setFecha3(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							
							//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la tercera línea
							filaResultado.setModificableIndiv(filaResultado.getModificableIndiv()+listaLineas.get(j).getModificable());
							
						}
						//Cuarta línea
						else if (j==3)
						{
							if (Constantes.TIPO_PEDIDO_ENCARGO.equals(listaLineas.get(j).getTipoPedidoAdicional())
									) {
							filaResultado.setCantidad4((listaLineas.get(j).getCantidad() != null && !("".equals(listaLineas.get(j).getCantidad().toString())))?new Double(listaLineas.get(j).getCantidad().toString()):null);
							filaResultado.setFecha4(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							} else {
								filaResultado.setFechaPilada(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							}
							
							//Para que sea modificable tiene que tener la línea modificable "S" o "P" o "T" o "Q". No será modificable si tiene valores "N" o "B"
							if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) && !Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()))
							{
								filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
							}

							//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la cuarta línea
							filaResultado.setModificableIndiv(filaResultado.getModificableIndiv()+listaLineas.get(j).getModificable());
						}
						else if (j==4)
						{
							if (Constantes.TIPO_PEDIDO_ENCARGO.equals(listaLineas.get(j).getTipoPedidoAdicional())
									) {
							filaResultado.setCantidad5((listaLineas.get(j).getCantidad() != null && !("".equals(listaLineas.get(j).getCantidad().toString())))?new Double(listaLineas.get(j).getCantidad().toString()):null);
							filaResultado.setFecha5(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							} else {
								filaResultado.setFechaPilada(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							}
							
							//Para que sea modificable tiene que tener la línea modificable "S" o "P" o "T" o "Q". No será modificable si tiene valores "N" o "B"
							if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) && !Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()))
							{
								filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
							}

							//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la cuarta línea
							filaResultado.setModificableIndiv(filaResultado.getModificableIndiv()+listaLineas.get(j).getModificable());
						}
						else if (j==5)
						{
							
							filaResultado.setFechaPilada(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
							
							
							//Para que sea modificable tiene que tener la línea modificable "S" o "P" o "T" o "Q". No será modificable si tiene valores "N" o "B"
							if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) && !Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()))
							{
								filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
							}

							//Para el caso de las individuales, añadimos directamente el valor que me llega del WS de la cuarta línea
							filaResultado.setModificableIndiv(filaResultado.getModificableIndiv()+listaLineas.get(j).getModificable());
						}
						
						if (Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()) || 
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF.equals(listaLineas.get(j).getModificable()) ||
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF.equals(listaLineas.get(j).getModificable()))
						{
							filaResultado.setEstado(Constantes.PEDIDO_ESTADO_NO_ACTIVA);
						}

						if (Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable())){
							filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
						}
						
						if (!Constantes.PEDIDO_NO_BORRABLE.equals(filaResultado.getBorrable()) && Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()))
						{
							//Si hay alguna fila no modificable, se permitir� borrar, pero realmente será una modificación de las
							//cantidades a cero.
							filaResultado.setBorrable(Constantes.PEDIDO_BORRABLE_MODIF);
						}
						
						if (j == (listaLineas.size()-1))
						{
							//Miramos la última línea, si no es modificable sea encargo o pilada, no se permitirá borrar. Además si es "T" o "Q" tampoco se permitirá borrar
							if (Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) || 
									Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()) || 
									Constantes.PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF.equals(listaLineas.get(j).getModificable()) || 
									Constantes.PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF.equals(listaLineas.get(j).getModificable()))
							{
								filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
							}
						}
					}
					else
					{
						//Miramos la última línea, si no es modificable sea encargo o pilada, no se permitirá borrar. Además si es "T" o "Q" tampoco se permitirá borrar
						if (Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) || 
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()) || 
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF.equals(listaLineas.get(j).getModificable()) || 
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF.equals(listaLineas.get(j).getModificable()))
						{
							//Si no es fresco puro, sólo se podrá borrar si es modificable.
							filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
						}
						if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) && 
								!Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable())){
							//Si no es fresco puro, sólo se podrá modificar si es no modificable.
							filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
						}
						if (Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()) || 
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF.equals(listaLineas.get(j).getModificable()) ||
								Constantes.PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF.equals(listaLineas.get(j).getModificable()))
						{
							filaResultado.setEstado(Constantes.PEDIDO_ESTADO_NO_ACTIVA);
						}
					}
					
					if (listaLineas.get(j).getTipoPedidoAdicional().equals(Constantes.TIPO_PEDIDO_PILADA))
					{
						//En este caso se trata de una pilada, con lo que tendr� que obtener la fecha fin.
						filaResultado.setFechaFin(Utilidades.formatearFecha(listaLineas.get(j).getFechaFin()));
						
					}
				}
				//Controlamos que si el pedido es borrable_modificable que no tenga las cantidades a cero, en cuyo caso ya
				//ha sido borrado lógicamente y no se podrá volver a borrar ni a modificar.
				if ((filaResultado.getBorrable().equals(Constantes.PEDIDO_BORRABLE_MODIF))&&(filaResultado.getCantidad1() == null)&&
						(filaResultado.getCantidad2() == null)&&(filaResultado.getCantidad3() == null))
				{
					//En este caso ya se había borrado lógicamente por lo que no se permitirá ni borrar ni modificar.
					filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
					filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				}
				if ((new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4)).equals(filaResultado.getClasePedido()) ||
						(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5)).equals(filaResultado.getClasePedido())	) {
					PedidosAdCentral pedidosAdCentral = new PedidosAdCentral();
					pedidosAdCentral.setIdentificador(filaResultado.getIdentificador());
					
					List<PedidosAdCentral> listPedidosAdCentral = this.pedidosAdCentralDao.findAll(pedidosAdCentral);
					if (!listPedidosAdCentral.isEmpty()){
						PedidosAdCentral aux = listPedidosAdCentral.get(0);
						filaResultado.setCantMax(aux.getCantMax());
						filaResultado.setCantMin(aux.getCantMin());
						filaResultado.setDescOferta(aux.getDescripcion());
						if(aux.getFechaHasta()!=null){
							filaResultado.setFechaHasta(Utilidades.formatearFecha(aux.getFechaHasta()));
						}
					}
				}
				
				//Añadimos el índice para mantener la posición de cada registro.
				filaResultado.setIndice(indice);
				indice ++;
				if (pedidoAdicionalVC.getIdentificador() != null)
				{	
					if (filaResultado.getIdentificador().compareTo(pedidoAdicionalVC.getIdentificador()) == 0)
					{
						resultado.add(filaResultado);
					}
				}
				else
				{
					resultado.add(filaResultado);
				}
			}

			return resultado;
	 	}
}
