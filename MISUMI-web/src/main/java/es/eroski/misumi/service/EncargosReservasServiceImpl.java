package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EncargosReservasDao;
import es.eroski.misumi.dao.iface.TPedidoAdicionalDao;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.service.iface.EncargosReservasService;
import es.eroski.misumi.util.Constantes;

@Service(value = "EncargosReservasService")
public class EncargosReservasServiceImpl implements EncargosReservasService {
	//private static Logger logger = LoggerFactory.getLogger(EncargosReservasServiceImpl.class);
	//private static Logger logger = Logger.getLogger(EncargosReservasServiceImpl.class);
   
	@Autowired
	private EncargosReservasDao encargosReservasDao;
    
	@Autowired
	private TPedidoAdicionalDao tPedidoAdicionalDao;
    @Override
    public EncargosReservasLista obtenerEncReservas(EncargoReserva encargoReserva) throws Exception {
    	return this.encargosReservasDao.obtenerEncReservas(encargoReserva);
    }
    
    @Override
	public EncargosReservasLista insertarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
    	return this.encargosReservasDao.insertarEncReservas(listaEncargosReservas);
    }
    
    @Override
	public EncargosReservasLista contarEncReservas(EncargoReserva encargoReserva) throws Exception {
    	return this.encargosReservasDao.contarEncReservas(encargoReserva);
    }
    
    @Override
	public EncargoReserva validarArticulo(EncargoReserva encargoReserva) throws Exception {
    	return this.encargosReservasDao.validarArticulo(encargoReserva);
    }
    
    @Override
	public EncargosReservasLista borrarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
    	return this.encargosReservasDao.borrarEncReservas(listaEncargosReservas);
    }
    
    @Override
	public EncargosReservasLista modifEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
    	return this.encargosReservasDao.modifEncReservas(listaEncargosReservas);
    }
    
	@Override
	public EncargosReservasLista insertarPedido(List<PedidoAdicionalCompleto> list) throws Exception {

		
		List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
		EncargoReserva encargoReserva = new EncargoReserva();
		
		
		for (PedidoAdicionalCompleto nuevoPedidoReferencia : list){
			
			encargoReserva = new EncargoReserva();
			
			
			encargoReserva.setCodigoCentro(nuevoPedidoReferencia.getCodCentro());
			encargoReserva.setArticulo(nuevoPedidoReferencia.getCodArt());	
			encargoReserva.setIdentificador(nuevoPedidoReferencia.getIdentificadorSIA());
			encargoReserva.setDescripcion(nuevoPedidoReferencia.getDescArt());
			encargoReserva.setOrigenEncargo(Constantes.PERFIL_CENTRO.toString());
			encargoReserva.setTipoAprovisionamiento(nuevoPedidoReferencia.getTipoAprovisionamiento());
			
			
			Long tipoPedidoAdicional = nuevoPedidoReferencia.getTipoPedido().longValue();
			if ((null != nuevoPedidoReferencia.getOferta() && !nuevoPedidoReferencia.getOferta().equals("0")) && tipoPedidoAdicional.equals(new Long(2))){
				tipoPedidoAdicional = new Long(3);
				encargoReserva.setOferta(nuevoPedidoReferencia.getOferta());
			}
			encargoReserva.setTipoPedidoAdicional(tipoPedidoAdicional);
			
			
			Double uniCajas;
			if (null == nuevoPedidoReferencia.getUniCajas() || nuevoPedidoReferencia.getUniCajas().equals(new Long(0))){
				uniCajas = new Double(1);
			} else {
				uniCajas = nuevoPedidoReferencia.getUniCajas();
			}
			
			encargoReserva.setUnidadesCaja(uniCajas);
			encargoReserva.setUsuario(nuevoPedidoReferencia.getUser());
			
			if (nuevoPedidoReferencia.getTipoPedido().equals(new Integer(1))){
				
				encargoReserva.setTipo("E");
				encargoReserva.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
				encargoReserva.setFlgExcluirVentas(nuevoPedidoReferencia.getExcluir());
				encargoReserva.setCantidad1(nuevoPedidoReferencia.getCantidad1());
				encargoReserva.setFlgForzarUnitaria(nuevoPedidoReferencia.getCajas());
				encargoReserva.setTratamiento(nuevoPedidoReferencia.getTratamiento());
				encargoReserva.setEstructuraComercial("0000000000");
				
			} else {
				
				if (nuevoPedidoReferencia.getFrescoPuro()){
					encargoReserva.setTipo("E");
					encargoReserva.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
					encargoReserva.setCantidad1(nuevoPedidoReferencia.getCantidad1());
					if (null!=nuevoPedidoReferencia.getFecha2()){
						encargoReserva.setFecha2(nuevoPedidoReferencia.getFecha2());
						encargoReserva.setCantidad2(nuevoPedidoReferencia.getCantidad2());
					}
					if (null!=nuevoPedidoReferencia.getFecha3()){
						encargoReserva.setFecha3(nuevoPedidoReferencia.getFecha3());
						encargoReserva.setCantidad3(nuevoPedidoReferencia.getCantidad3());
					}
					if (null!=nuevoPedidoReferencia.getFechaPilada()){
						encargoReserva.setFechaInicioPilada(nuevoPedidoReferencia.getFechaPilada());
					}
					encargoReserva.setFechaFin(nuevoPedidoReferencia.getFechaFin());
					encargoReserva.setTratamiento("");
				}
				else {
					encargoReserva.setTipo("P");
					encargoReserva.setFechaInicio(nuevoPedidoReferencia.getFechaIni());
					encargoReserva.setFechaFin(nuevoPedidoReferencia.getFechaFin());
					encargoReserva.setImplantacionInicial(nuevoPedidoReferencia.getCapacidadMaxima());
					encargoReserva.setImplantacionFinal(nuevoPedidoReferencia.getImplantacionMinima());
					encargoReserva.setTratamiento("");
				}
				
				if (tipoPedidoAdicional.equals(new Long(3)))
				{
					//Si se trata de un montaje adicional con oferta, el campo excluir siempre va a true y no es modificable
					encargoReserva.setFlgExcluirVentas("N");
				}
				else
				{
					if (null != nuevoPedidoReferencia.getReferenciaNueva()){
						if (nuevoPedidoReferencia.getReferenciaNueva()){
							encargoReserva.setFlgExcluirVentas("N");
						} else {
							if (nuevoPedidoReferencia.getExcluir() != null)
							{
								encargoReserva.setFlgExcluirVentas(nuevoPedidoReferencia.getExcluir());
							}
							else
							{
								encargoReserva.setFlgExcluirVentas("S");
							}
						}
					} else {
						if (nuevoPedidoReferencia.getExcluir() != null)
						{
							encargoReserva.setFlgExcluirVentas(nuevoPedidoReferencia.getExcluir());
						}
						else
						{
							encargoReserva.setFlgExcluirVentas("S");
						}
					}
				}
				

	
			}
			
	
		}
		
		listaEncargosReservas.add(encargoReserva);
		EncargosReservasLista resultProc = new EncargosReservasLista();
		resultProc = this.encargosReservasDao.insertarEncReservas(listaEncargosReservas);
		
		return resultProc;
	}

	@Override
	public EncargosReservasLista modificarPedido(PedidoAdicionalCompleto pedidoAdicional) throws Exception {
	

			EncargoReserva encargoReserva = new EncargoReserva();
			List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
			
			encargoReserva.setCodigoCentro(pedidoAdicional.getCodCentro());
			encargoReserva.setArticulo(pedidoAdicional.getCodArt());	
			encargoReserva.setIdentificador(pedidoAdicional.getIdentificadorSIA());
			encargoReserva.setDescripcion(pedidoAdicional.getDescArt());
			encargoReserva.setOrigenEncargo(String.valueOf(pedidoAdicional.getPerfil()));
			encargoReserva.setTipoAprovisionamiento(pedidoAdicional.getTipoAprovisionamiento());
			
			Long tipoPedidoAdicional = pedidoAdicional.getTipoPedido().longValue();
			if (null != pedidoAdicional.getOferta() && tipoPedidoAdicional.equals(new Long(2))){
				tipoPedidoAdicional = new Long(3);
			}
			encargoReserva.setTipoPedidoAdicional(tipoPedidoAdicional);
			encargoReserva.setOferta(pedidoAdicional.getOferta());
			Double uniCajas;
			if (null == pedidoAdicional.getUniCajas() || pedidoAdicional.getUniCajas().equals(new Long(0))){
				uniCajas = new Double(1);
			} else {
				uniCajas = pedidoAdicional.getUniCajas();
			}
			encargoReserva.setUnidadesCaja(uniCajas);
			encargoReserva.setUsuario(pedidoAdicional.getUser());
			

			if (pedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))){ //ENCARGOS
				
				PedidoAdicionalE registroGuardado = this.obtenerArticuloTablaSesionE(pedidoAdicional.getSesion(),new Long(pedidoAdicional.getTipoPedido()), pedidoAdicional.getCodCentro(), pedidoAdicional.getCodArt(), pedidoAdicional.getIdentificador(), pedidoAdicional.getIdentificadorSIA());
				
				encargoReserva.setTipo("E");
				encargoReserva.setFlgForzarUnitaria(pedidoAdicional.getCajas());
				
				
				encargoReserva.setFlgExcluirVentas(pedidoAdicional.getExcluir());

				encargoReserva.setCantidad1(pedidoAdicional.getCantidad1());
				encargoReserva.setTratamiento(pedidoAdicional.getTratamiento());
				
				encargoReserva.setFechaInicio(pedidoAdicional.getFechaIni());
				
				
			} else { //MONTAJE ADICIONAL Y MONTAJE ADICIONAL EN OFERTA 
				
				PedidoAdicionalM registroGuardadoM = new PedidoAdicionalM();
				PedidoAdicionalMO registroGuardadoMO = new PedidoAdicionalMO();
				String tratamiento = "";

				if (pedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE)) ||
						pedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL))	)
				{
					
					registroGuardadoM = this.obtenerArticuloTablaSesionM(pedidoAdicional.getSesion(),new Long(pedidoAdicional.getTipoPedido()), pedidoAdicional.getCodCentro(), pedidoAdicional.getCodArt(),pedidoAdicional.getIdentificador(), pedidoAdicional.getIdentificadorSIA());
					tratamiento = registroGuardadoM.getTratamiento();
					
					if (registroGuardadoM.isCajas()) {
						encargoReserva.setFlgForzarUnitaria("S");
					}else {
						encargoReserva.setFlgForzarUnitaria("N");
					}
				}
				else if (pedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL)) || 
						pedidoAdicional.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA)))
				{
					
					registroGuardadoMO = this.obtenerArticuloTablaSesionMO(pedidoAdicional.getSesion(),new Long(pedidoAdicional.getTipoPedido()), pedidoAdicional.getCodCentro(), pedidoAdicional.getCodArt(),pedidoAdicional.getIdentificador(), pedidoAdicional.getIdentificadorSIA());
					tratamiento = registroGuardadoMO.getTratamiento();

					if (registroGuardadoMO.isCajas()) {
						encargoReserva.setFlgForzarUnitaria("S");
					}else {
						encargoReserva.setFlgForzarUnitaria("N");
					}
				}
				
				//SE HA AÑADIDO EL IF DE FRESCO PURO. 
				if (pedidoAdicional.getFrescoPuro()){
					encargoReserva.setTipo("E");
					encargoReserva.setFechaInicio(pedidoAdicional.getFechaIni());
					encargoReserva.setCantidad1(pedidoAdicional.getCantidad1());
					if (null!=pedidoAdicional.getFecha2()){
						encargoReserva.setFecha2(pedidoAdicional.getFecha2());
						encargoReserva.setCantidad2(pedidoAdicional.getCantidad2());
					}
					if (null!=pedidoAdicional.getFecha3()){
						encargoReserva.setFecha3(pedidoAdicional.getFecha3());
						encargoReserva.setCantidad3(pedidoAdicional.getCantidad3());
					}
					if (null!=pedidoAdicional.getFechaPilada()){
						encargoReserva.setFechaInicioPilada(pedidoAdicional.getFechaPilada());
					}
					encargoReserva.setFechaFin(pedidoAdicional.getFechaFin());
				}
				else {
					encargoReserva.setTipo("P");
					encargoReserva.setFechaInicio(pedidoAdicional.getFechaIni());
					encargoReserva.setFechaFin(pedidoAdicional.getFechaFin());
					encargoReserva.setImplantacionInicial(pedidoAdicional.getCapacidadMaxima());
					encargoReserva.setImplantacionFinal(pedidoAdicional.getImplantacionMinima());
				}
				
				encargoReserva.setFlgExcluirVentas(pedidoAdicional.getExcluir());
				encargoReserva.setTratamiento(tratamiento);
				
			}
			
			
			
			listaEncargosReservas.add(encargoReserva);
			EncargosReservasLista resultProc = new EncargosReservasLista();
			resultProc = this.encargosReservasDao.modifEncReservas(listaEncargosReservas);
			
			return resultProc;

	}
	
	
	
	public PedidoAdicionalE obtenerArticuloTablaSesionE(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_ENCARGO)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		
		PedidoAdicionalE articulo = new PedidoAdicionalE();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalDao.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				articulo.setUnidadesPedidas((registro.getCajasPedidas() != null && !("".equals(registro.getCajasPedidas().toString())))?new Double(registro.getCajasPedidas().toString()):null);
				articulo.setFecEntrega((registro.getFecEntrega() != null && !("".equals(registro.getFecEntrega())))?registro.getFecEntrega():null);
				articulo.setCajas((registro.getCajas() != null && !("".equals(registro.getCajas().toString())))?(("N".equals(registro.getCajas().toString())?false:true)):null);
				articulo.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("N".equals(registro.getExcluir().toString())?false:true)):null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return articulo;
	}
	
	
	public PedidoAdicionalM obtenerArticuloTablaSesionM(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		
		PedidoAdicionalM articulo = new PedidoAdicionalM();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalDao.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				//articulo.setCajas((registro.getCajas() != null && !("".equals(registro.getCajas().toString())))?(("S".equals(registro.getCajas().toString())?true:false)):null);
				articulo.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("S".equals(registro.getExcluir().toString())?true:false)):null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				articulo.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				articulo.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				articulo.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				articulo.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				articulo.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				articulo.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				articulo.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return articulo;
	}
	
	
	public PedidoAdicionalMO obtenerArticuloTablaSesionMO(String idSesion, Long clasePedido, Long codCentro, Long codArticulo, Long identificador, Long identificadorSIA){

		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setClasePedido(clasePedido);
		registro.setListaFiltroClasePedido(Arrays.asList(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL)));
		registro.setCodCentro(codCentro);
		registro.setCodArticulo(codArticulo);
		registro.setIdentificador(identificador);
		registro.setIdentificadorSIA(identificadorSIA);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);
		
		PedidoAdicionalMO articulo = new PedidoAdicionalMO();
		
		try {
			List<TPedidoAdicional> listaTPedidoAdicional = this.tPedidoAdicionalDao.findAll(registro);
			
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0)
			{
				registro = (TPedidoAdicional)listaTPedidoAdicional.get(0);
				
				articulo.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
				articulo.setIdentificador((registro.getIdentificador() != null && !("".equals(registro.getIdentificador().toString())))?new Long(registro.getIdentificador().toString()):null);
				articulo.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
				articulo.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
				articulo.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
				articulo.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
				articulo.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
				articulo.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
				articulo.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
				//articulo.setCajas((registro.getCajas() != null && !("".equals(registro.getCajas().toString())))?(("S".equals(registro.getCajas().toString())?true:false)):null);
				articulo.setExcluir((registro.getExcluir() != null && !("".equals(registro.getExcluir().toString())))?(("S".equals(registro.getExcluir().toString())?true:false)):null);
				articulo.setModificable((registro.getModificable() != null && !("".equals(registro.getModificable())))?registro.getModificable():null);
				articulo.setClasePedido((registro.getClasePedido() != null && !("".equals(registro.getClasePedido())))?registro.getClasePedido():null);
				articulo.setCodError((registro.getCodError() != null && !("".equals(registro.getCodError())))?registro.getCodError():null);
				articulo.setDescError((registro.getDescError() != null && !("".equals(registro.getDescError())))?registro.getDescError():null);
				articulo.setTratamiento(registro.getTratamiento());
				articulo.setEstado((registro.getEstado() != null && !("".equals(registro.getEstado())))?registro.getEstado():null);
				articulo.setIdentificadorSIA((registro.getIdentificadorSIA() != null && !("".equals(registro.getIdentificadorSIA().toString())))?new Long(registro.getIdentificadorSIA().toString()):null);
				articulo.setCapMax((registro.getCapMax() != null && !("".equals(registro.getCapMax().toString())))?new Double(registro.getCapMax().toString()):null);
				articulo.setCapMin((registro.getCapMin() != null && !("".equals(registro.getCapMin().toString())))?new Double(registro.getCapMin().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
				articulo.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
				articulo.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
				articulo.setOferta((registro.getOferta() != null && !("".equals(registro.getOferta())))?registro.getOferta():null);
				articulo.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
				articulo.setBorrable((registro.getBorrable() != null && !("".equals(registro.getBorrable())))?registro.getBorrable():null);
				articulo.setModificableIndiv((registro.getModificableIndiv() != null && !("".equals(registro.getModificableIndiv())))?registro.getModificableIndiv():null);
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
			
		return articulo;
	}
	
}
