package es.eroski.misumi.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoAdicionalDao;
import es.eroski.misumi.dao.iface.PedidoAdicionalEMDao;
import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.model.PedidoAdicionalEM;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.model.pedidosPBLWS.ArrayOfPedidoAdicionalLineaLinea;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicional;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerRequest;
import es.eroski.misumi.model.pedidosPBLWS.PedidoAdicionalObtenerResponse;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PedidoAdicionalEMDaoImpl implements PedidoAdicionalEMDao{
	 
	 @Autowired
	 private PedidoAdicionalDao pedidoAdicionalDao;
	 
	 @Autowired
	 private PedidosAdCentralDao pedidosAdCentralDao;

	 @Override
	 public List<PedidoAdicionalEM> findAll(PedidoAdicionalEM pedidoAdicionalEM, HttpSession session) throws Exception {
	    	
			PedidoAdicionalObtenerRequest argument = new PedidoAdicionalObtenerRequest();
			
			argument.setClasePedidoAdicional((pedidoAdicionalEM.getClasePedido() != null && !("".equals(pedidoAdicionalEM.getClasePedido().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getClasePedido()):null);
			
			argument.setCentro((pedidoAdicionalEM.getCodCentro() != null && !("".equals(pedidoAdicionalEM.getCodCentro().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getCodCentro()):null);
			argument.setArea((pedidoAdicionalEM.getGrupo1() != null && !("".equals(pedidoAdicionalEM.getGrupo1().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getGrupo1()):null);
			argument.setSeccion((pedidoAdicionalEM.getGrupo2() != null && !("".equals(pedidoAdicionalEM.getGrupo2().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getGrupo2()):null);
			argument.setCategoria((pedidoAdicionalEM.getGrupo3() != null && !("".equals(pedidoAdicionalEM.getGrupo3().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getGrupo3()):null);
			argument.setReferencia((pedidoAdicionalEM.getCodArticulo() != null && !("".equals(pedidoAdicionalEM.getCodArticulo().toString())))?BigInteger.valueOf(pedidoAdicionalEM.getCodArticulo()):null);
			
			PedidoAdicionalObtenerResponse resultWS = new PedidoAdicionalObtenerResponse();
			
			//Llamamos al DAO que llama al WS para obtener el resultado.
			resultWS = this.pedidoAdicionalDao.obtenerPedido(argument,session);
			
			List<PedidoAdicionalEM> resultado = null;
			if (resultWS != null)
			{
				resultado = tratarDatosObtenerWS(resultWS,pedidoAdicionalEM);
			}

			return resultado;
	    }
	    
	 	private List<PedidoAdicionalEM> tratarDatosObtenerWS(PedidoAdicionalObtenerResponse resultWS, PedidoAdicionalEM pedidoAdicionalEM) throws Exception{
	 		
	 		//Transformación de datos para estructura de PedidoAdicionalEM
	 		List<PedidoAdicionalEM> resultado = new ArrayList<PedidoAdicionalEM>();
	 		List<PedidoAdicional> pedidoAdicional = new ArrayList<PedidoAdicional>();
	 		PedidoAdicionalEM filaResultado = new PedidoAdicionalEM();
	 		if (null != resultWS.getPedidoAdicional()){
	 			pedidoAdicional = Arrays.asList(resultWS.getPedidoAdicional());
	 		}
	 		boolean esFrescoPuro = false;
	 		
	 		//Nos recorremos la lista principal
	 		int indice = 0;
			for (int i=0;i<pedidoAdicional.size();i++){
				filaResultado = new PedidoAdicionalEM();
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
						//Si estamos en la primera línea, obtenemos la fecha de inicio.
						filaResultado.setFechaInicio(Utilidades.formatearFecha(listaLineas.get(j).getFechaInicio()));
						filaResultado.setExcluir(listaLineas.get(j).isExcluir());
						//Si la primera línea que me llega es un encargo, se trata de un Fresco Puro si no, no.
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
						
						filaResultado.setTratamiento(listaLineas.get(j).getTratamiento());
					}

					//Control de si el registro es modificable y borrable y en que grado.
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
							if ( listaLineas.get(j).getTipoPedidoAdicional().equals(Constantes.TIPO_PEDIDO_ENCARGO)) {
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
							if ( Constantes.TIPO_PEDIDO_ENCARGO.equals(listaLineas.get(j).getTipoPedidoAdicional())) {
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
						//Miramos la última línea, si no es modificable sea encargo o pilada, no se permitirá borrar. Además si es "T" o "Q" tampoco se permitirá borrar
						if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(listaLineas.get(j).getModificable()) && 
								!Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(listaLineas.get(j).getModificable()))
						{
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
				if ((Constantes.PEDIDO_BORRABLE_MODIF.equals(filaResultado.getBorrable()))&&(filaResultado.getCantidad1() == null)&&
						(filaResultado.getCantidad2() == null)&&(filaResultado.getCantidad3() == null))
				{
					//En este caso ya se había borrado lógicamente por lo que no se permitirá ni borrar ni modificar.
					filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
					filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				}
				
				if ((new Long(Constantes.CLASE_PEDIDO_EMPUJE)).equals(filaResultado.getClasePedido())) {
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
				if (pedidoAdicionalEM.getIdentificador() != null)
				{	
					if (filaResultado.getIdentificador().compareTo(pedidoAdicionalEM.getIdentificador()) == 0)
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
