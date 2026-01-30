package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.EncargosReservasDao;
import es.eroski.misumi.dao.iface.PedidoAdicionalMODao;
import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.dao.iface.VSurtidoTiendaDao;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PedidoAdicionalMODaoImpl implements PedidoAdicionalMODao{	 
	
	private static Logger logger = Logger.getLogger(Utilidades.class);

	private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
	 @Autowired
	 private EncargosReservasDao encargosReservasDao;
	 
	 @Autowired
	 private PedidosAdCentralDao pedidosAdCentralDao;

	 @Autowired
	 private VSurtidoTiendaDao vSurtidoTiendaDao;
	 
	 @Autowired
	 private UtilidadesCapraboService utilidadesCapraboService;
	 
	 @Override
	 public List<PedidoAdicionalMO> findAll(PedidoAdicionalMO pedidoAdicionalMO, HttpSession session) throws Exception {
	    	
		 List<PedidoAdicionalMO> resultado = null;
	 	 int indice = 0;
	 	 
		 //Búsqueda en bloque
		resultado = this.findAllSIA(pedidoAdicionalMO, indice);
		 
		 return resultado;
	 }

	 	private List<PedidoAdicionalMO> findAllSIA(PedidoAdicionalMO pedidoAdicionalMO, int indice) throws Exception {
	    	
		 	EncargoReserva encargoReserva = new EncargoReserva();

		 	encargoReserva.setCodLoc((pedidoAdicionalMO.getCodCentro() != null && !("".equals(pedidoAdicionalMO.getCodCentro().toString())))?pedidoAdicionalMO.getCodCentro():null);
		 	encargoReserva.setCodN1((pedidoAdicionalMO.getGrupo1() != null && !("".equals(pedidoAdicionalMO.getGrupo1().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalMO.getGrupo1().toString(), '0', 2):null);
		 	encargoReserva.setCodN2((pedidoAdicionalMO.getGrupo2() != null && !("".equals(pedidoAdicionalMO.getGrupo2().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalMO.getGrupo2().toString(), '0', 2):null);
		 	encargoReserva.setCodN3((pedidoAdicionalMO.getGrupo3() != null && !("".equals(pedidoAdicionalMO.getGrupo3().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalMO.getGrupo3().toString(), '0', 2):null);
		 	encargoReserva.setCodArtFormlog((pedidoAdicionalMO.getCodArticulo() != null && !("".equals(pedidoAdicionalMO.getCodArticulo().toString())))?pedidoAdicionalMO.getCodArticulo():null);
		 	encargoReserva.setTipoPedidoAdicional((pedidoAdicionalMO.getClasePedido() != null && !("".equals(pedidoAdicionalMO.getClasePedido().toString())))?pedidoAdicionalMO.getClasePedido():null);
		 	
			EncargosReservasLista resultProc = new EncargosReservasLista();
			
			//Llamamos al DAO que llama al procedimiento para obtener el resultado.
			resultProc = this.encargosReservasDao.obtenerEncReservas(encargoReserva);
			
			//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
			List<PedidoAdicionalMO> resultado = null;
			if (resultProc != null)
			{
				resultado = tratarDatosObtenerEncargosProc(resultProc, pedidoAdicionalMO, indice);
			}

			return resultado;
		 }

		 @Override
		 public List<PedidoAdicionalMO> removeAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception {
		    	
			 List<PedidoAdicionalMO> resultado = null;
			 List<PedidoAdicionalMO> listaPedidoAdicionalMOPBL = new ArrayList<PedidoAdicionalMO>();
			 List<PedidoAdicionalMO> listaPedidoAdicionalMOSIA = new ArrayList<PedidoAdicionalMO>();

			 //Se obtienen listas separadas para el tratamiento por medio de PBL y SIA
			 for (int i=0;i<listaPedidoAdicionalMO.size();i++){
				 if (listaPedidoAdicionalMO.get(i).getIdentificadorSIA()!=null){
					 listaPedidoAdicionalMOSIA.add(listaPedidoAdicionalMO.get(i));
				 }else{
					 listaPedidoAdicionalMOPBL.add(listaPedidoAdicionalMO.get(i));
				 }
			 }
			 resultado = this.removeAllSIA(listaPedidoAdicionalMOSIA);
			 
			 return resultado;
	     }
	 
		 private List<PedidoAdicionalMO> removeAllSIA(List<PedidoAdicionalMO> listaPedidoAdicionalMO) throws Exception {
		    	
			EncargoReserva encargoReserva = null;
			List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
			
		 	for (int i=0;i<listaPedidoAdicionalMO.size();i++){
		 		
		 		encargoReserva = new EncargoReserva();
		 		encargoReserva.setCodigoCentro((listaPedidoAdicionalMO.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalMO.get(i).getCodCentro().toString())))?listaPedidoAdicionalMO.get(i).getCodCentro():null);
		 		encargoReserva.setArticulo((listaPedidoAdicionalMO.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalMO.get(i).getCodArticulo().toString())))?listaPedidoAdicionalMO.get(i).getCodArticulo():null);
		 		encargoReserva.setIdentificador((listaPedidoAdicionalMO.get(i).getIdentificadorSIA() != null && !("".equals(listaPedidoAdicionalMO.get(i).getIdentificadorSIA().toString())))?listaPedidoAdicionalMO.get(i).getIdentificadorSIA():null);
		 		encargoReserva.setTipoPedidoAdicional((listaPedidoAdicionalMO.get(i).getClasePedido() != null && !("".equals(listaPedidoAdicionalMO.get(i).getClasePedido().toString())))?listaPedidoAdicionalMO.get(i).getClasePedido():null);
		 		
		 		listaEncargosReservas.add(encargoReserva);
		 	}
			
		 	EncargosReservasLista resultProc = new EncargosReservasLista();
			
			//Llamamos al DAO que llama al procedimiento para obtener el resultado.
			resultProc = this.encargosReservasDao.borrarEncReservas(listaEncargosReservas);
			
			//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
			List<PedidoAdicionalMO> resultado = null;
			if (resultProc != null)
			{
				resultado = tratarDatosEliminarProc(resultProc);
			}

			return resultado;
	    }
	 	
	 	private List<PedidoAdicionalMO> tratarDatosEliminarProc(EncargosReservasLista resultProc){
	 		
	 		//Transformación de datos para estructura de PedidoAdicionalMO
	 		List<PedidoAdicionalMO> resultado = new ArrayList<PedidoAdicionalMO>();
	 		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
	 		PedidoAdicionalMO filaResultado = new PedidoAdicionalMO();
	 		if (null != resultProc.getDatos()){
	 			encargosReservasLista = resultProc.getDatos();
	 		}
	 		
	 		//Nos recorremos la lista principal
			for (int i=0;i<encargosReservasLista.size();i++){
				filaResultado = new PedidoAdicionalMO();
				filaResultado.setCodCentro((encargosReservasLista.get(i).getCodigoCentro() != null && !("".equals(encargosReservasLista.get(i).getCodigoCentro().toString())))?new Long(encargosReservasLista.get(i).getCodigoCentro().toString()):null);
				filaResultado.setCodArticulo((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
				filaResultado.setIdentificador(null);
				filaResultado.setIdentificadorSIA((encargosReservasLista.get(i).getIdentificador() != null && !("".equals(encargosReservasLista.get(i).getIdentificador().toString())))?new Long(encargosReservasLista.get(i).getIdentificador().toString()):null);
				filaResultado.setCodigoRespuesta(encargosReservasLista.get(i).getCodError().toString());
				filaResultado.setDescripcionRespuesta(encargosReservasLista.get(i).getDescError());
				
				resultado.add(filaResultado);
			}
	 		return resultado;
	 	}
	 	
	 	@Override
		public List<PedidoAdicionalMO> modifyAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, String tipoModificado, HttpSession session) throws Exception {

	 		 List<PedidoAdicionalMO> resultado = null;
			 List<PedidoAdicionalMO> listaPedidoAdicionalMOPBL = new ArrayList<PedidoAdicionalMO>();
			 List<PedidoAdicionalMO> listaPedidoAdicionalMOSIA = new ArrayList<PedidoAdicionalMO>();

			 //Se obtienen listas separadas para el tratamiento por medio de PBL y SIA
			 for (int i=0;i<listaPedidoAdicionalMO.size();i++){
				 if (listaPedidoAdicionalMO.get(i).getIdentificadorSIA()!=null){
					 listaPedidoAdicionalMOSIA.add(listaPedidoAdicionalMO.get(i));
				 }else{
					 listaPedidoAdicionalMOPBL.add(listaPedidoAdicionalMO.get(i));
				 }
			 }
			 
			 resultado = this.modifyAllSIA(listaPedidoAdicionalMOSIA, tipoModificado);
			 
			 return resultado;
		 }

		 private List<PedidoAdicionalMO> modifyAllSIA(List<PedidoAdicionalMO> listaPedidoAdicionalMO, String tipoModificado) throws Exception {
		    	
			List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
			PedidoAdicionalMO pedidoAdicional = null;
			Long clasePedido = null;
			
			for (int i=0;i<listaPedidoAdicionalMO.size();i++){
				pedidoAdicional = listaPedidoAdicionalMO.get(i);
				clasePedido = pedidoAdicional.getClasePedido();
				
				EncargoReserva encargoReserva = new EncargoReserva();
				
				encargoReserva.setCodigoCentro(pedidoAdicional.getCodCentro());
				encargoReserva.setArticulo(pedidoAdicional.getCodArticulo());	
				encargoReserva.setIdentificador(pedidoAdicional.getIdentificadorSIA());
				encargoReserva.setDescripcion(pedidoAdicional.getDescriptionArt());
				encargoReserva.setOrigenEncargo(String.valueOf(pedidoAdicional.getPerfil()));
				encargoReserva.setTipoAprovisionamiento(pedidoAdicional.getTipoAprovisionamiento());
				encargoReserva.setTipoPedidoAdicional(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
				encargoReserva.setOferta(pedidoAdicional.getOferta());
				Double uniCajas;
				if (null == pedidoAdicional.getUniCajaServ() || pedidoAdicional.getUniCajaServ().equals(new Long(0))){
					uniCajas = new Double(1);
				} else {
					uniCajas = pedidoAdicional.getUniCajaServ();
				}
				encargoReserva.setUnidadesCaja(uniCajas);
				encargoReserva.setUsuario(pedidoAdicional.getUsuario());
				
				SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
				
				
				//55674
				encargoReserva.setTipo(pedidoAdicional.getTipoPedido());
				//PILADA
				if (encargoReserva.getTipo().equals(Constantes.TIPO_PEDIDO_PILADA)){
					encargoReserva.setFechaInicio(df.parse(pedidoAdicional.getFechaInicio()));
					encargoReserva.setFechaFin(df.parse(pedidoAdicional.getFechaFin()));
					encargoReserva.setImplantacionInicial(pedidoAdicional.getCapMax());
					encargoReserva.setImplantacionFinal(pedidoAdicional.getCapMin());
					if (pedidoAdicional.isExcluir()) {
						encargoReserva.setFlgExcluirVentas("S");
					}else {
						encargoReserva.setFlgExcluirVentas("N");
					}
				}
				//ENCARGOS
				else {
					encargoReserva.setFechaInicio(df.parse(pedidoAdicional.getFechaInicio()));
					encargoReserva.setFechaFin(df.parse(pedidoAdicional.getFechaFin()));
					encargoReserva.setCantidad1(pedidoAdicional.getCantidad1());
					encargoReserva.setFecha2(df.parse(pedidoAdicional.getFecha2()));
					encargoReserva.setCantidad2(pedidoAdicional.getCantidad2());
					encargoReserva.setFecha3(df.parse(pedidoAdicional.getFecha3()));
					encargoReserva.setCantidad3(pedidoAdicional.getCantidad3());
					encargoReserva.setFechaInicioPilada(df.parse(pedidoAdicional.getFechaPilada()));
					encargoReserva.setTratamiento(pedidoAdicional.getTratamiento());
					if (pedidoAdicional.isCajas()) {
						encargoReserva.setFlgForzarUnitaria("S");
					} else {
						encargoReserva.setFlgForzarUnitaria("N");
					}		
				}
			

				listaEncargosReservas.add(encargoReserva);
			}
			
			EncargosReservasLista encargosReservasLista = new EncargosReservasLista();
			encargosReservasLista = this.encargosReservasDao.modifEncReservas(listaEncargosReservas);
				
	 		//Transformación de datos para estructura de PedidoAdicionalMO
			List<PedidoAdicionalMO> resultado = new ArrayList<PedidoAdicionalMO>();
			
			if (encargosReservasLista!=null) {
		 		List<EncargoReserva> encargoReservaRes = encargosReservasLista.getDatos();
		 		PedidoAdicionalMO filaResultado = new PedidoAdicionalMO();
		 		
		 		//Nos recorremos la lista 
				for (int i=0;i<encargoReservaRes.size();i++){
					filaResultado = new PedidoAdicionalMO();
					filaResultado.setCodCentro((encargoReservaRes.get(i).getCodigoCentro() != null && !("".equals(encargoReservaRes.get(i).getCodigoCentro().toString())))?new Long(encargoReservaRes.get(i).getCodigoCentro().toString()):null);
					filaResultado.setCodArticulo((encargoReservaRes.get(i).getArticulo() != null && !("".equals(encargoReservaRes.get(i).getArticulo().toString())))?new Long(encargoReservaRes.get(i).getArticulo().toString()):null);
					filaResultado.setIdentificador((encargoReservaRes.get(i).getIdentificador() != null && !("".equals(encargoReservaRes.get(i).getIdentificador().toString())))?new Long(encargoReservaRes.get(i).getIdentificador().toString()):null);
					filaResultado.setClasePedido(clasePedido);
					filaResultado.setFechaFin((encargoReservaRes.get(i).getFechaFin() != null)?Utilidades.formatearFecha(encargoReservaRes.get(i).getFechaFin()):null);
					filaResultado.setCodigoRespuesta(encargoReservaRes.get(i).getCodError().toString());
					filaResultado.setDescripcionRespuesta(encargoReservaRes.get(i).getDescError());
									
					resultado.add(filaResultado);
				}
			}

			return resultado;
		}	 	
	 	
		 @Override
		 public List<PedidoAdicionalMO> validateAll(
					List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception {

			 List<PedidoAdicionalMO> resultado = null;
			 List<PedidoAdicionalMO> listaPedidoAdicionalMOSIA = new ArrayList<PedidoAdicionalMO>();

			 //Se obtienen listas separadas para el tratamiento por medio de PBL y SIA
			 for (int i=0;i<listaPedidoAdicionalMO.size();i++){
				listaPedidoAdicionalMOSIA.add(listaPedidoAdicionalMO.get(i));
			 }
			 resultado = this.validateAllSIA(listaPedidoAdicionalMOSIA);
			 
			 return resultado;
		 }

	 	
		 private List<PedidoAdicionalMO> validateAllSIA(
					List<PedidoAdicionalMO> listaPedidoAdicionalMO) throws Exception {

			 	EncargoReserva encargoReserva = null;
			 	List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
				EncargosReservasLista encargosReservasLista = new EncargosReservasLista();
				
				//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
				List<PedidoAdicionalMO> resultado = null;
				
				for (int i=0;i<listaPedidoAdicionalMO.size();i++){
					
					encargoReserva = new EncargoReserva();
					encargoReserva.setCodLoc((listaPedidoAdicionalMO.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalMO.get(i).getCodCentro().toString())))?listaPedidoAdicionalMO.get(i).getCodCentro():null);
					encargoReserva.setCodArtFormlog((listaPedidoAdicionalMO.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalMO.get(i).getCodArticulo().toString())))?listaPedidoAdicionalMO.get(i).getCodArticulo():null);
					
					EncargoReserva resultProc = new EncargoReserva();
					
					//Llamamos al DAO que llama al procedimiento para obtener el resultado.
					resultProc = this.encargosReservasDao.validarArticulo(encargoReserva);
					
					if (resultProc != null)
					{
						listaEncargosReservas.add(resultProc);
					}
				}
				encargosReservasLista.setDatos(listaEncargosReservas);
				resultado = tratarDatosValidarProc(encargosReservasLista);
				
				return resultado;
		 }

	 	private List<PedidoAdicionalMO> tratarDatosValidarProc(EncargosReservasLista resultProc){
	 		
	 		//Transformación de datos para estructura de PedidoAdicionalMO
	 		List<PedidoAdicionalMO> resultado = new ArrayList<PedidoAdicionalMO>();
	 		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
	 		PedidoAdicionalMO filaResultado = new PedidoAdicionalMO();
	 		if (null != resultProc.getDatos()){
	 			encargosReservasLista = resultProc.getDatos();
	 		}
	 		
	 		//Nos recorremos la lista principal
			for (int i=0;i<encargosReservasLista.size();i++){
				filaResultado = new PedidoAdicionalMO();
				filaResultado.setCodCentro((encargosReservasLista.get(i).getCodigoCentro() != null && !("".equals(encargosReservasLista.get(i).getCodigoCentro().toString())))?new Long(encargosReservasLista.get(i).getCodigoCentro().toString()):null);
				filaResultado.setCodArticulo((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
				filaResultado.setCodigoRespuesta(encargosReservasLista.get(i).getCodError().toString());
				filaResultado.setDescripcionRespuesta(encargosReservasLista.get(i).getDescError());

				if (!filaResultado.getCodigoRespuesta().equals("1"))
				{
					//Si no ha fallado el WS.
					filaResultado.setFechaInicio(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaVenta()));
					filaResultado.setBloqueoPilada(encargosReservasLista.get(i).getBloqueoPilada());
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
	 	
	 	private List<PedidoAdicionalMO> tratarDatosObtenerEncargosProc(EncargosReservasLista resultProc, PedidoAdicionalMO pedidoAdicionalMO, int indice) throws Exception{
	 		
	 		//Transformación de datos para estructura de PedidoAdicionalMO
	 		List<PedidoAdicionalMO> resultado = new ArrayList<PedidoAdicionalMO>();
	 		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
	 		PedidoAdicionalMO filaResultado = new PedidoAdicionalMO();
	 		if (null != resultProc.getDatos()){
	 			encargosReservasLista = resultProc.getDatos();
	 		}
	 		
	 		//Nos recorremos la lista principal
			for (int i=0;i<encargosReservasLista.size();i++){
				filaResultado = new PedidoAdicionalMO();
				filaResultado.setCodCentro((encargosReservasLista.get(i).getCodigoCentro() != null && !("".equals(encargosReservasLista.get(i).getCodigoCentro().toString())))?new Long(encargosReservasLista.get(i).getCodigoCentro().toString()):null);
				filaResultado.setClasePedido((encargosReservasLista.get(i).getTipoPedidoAdicional() != null && !("".equals(encargosReservasLista.get(i).getTipoPedidoAdicional().toString())))?new Long(encargosReservasLista.get(i).getTipoPedidoAdicional().toString()):null);
				filaResultado.setIdentificador(null);
				filaResultado.setIdentificadorSIA((encargosReservasLista.get(i).getIdentificador() != null && !("".equals(encargosReservasLista.get(i).getIdentificador().toString())))?new Long(encargosReservasLista.get(i).getIdentificador().toString()):null);
				filaResultado.setCodArticulo((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
				filaResultado.setDescriptionArt((encargosReservasLista.get(i).getDescripcion() != null && !("".equals(encargosReservasLista.get(i).getDescripcion())))?encargosReservasLista.get(i).getDescripcion():null);
				if (pedidoAdicionalMO.getEsCaprabo()) {
					filaResultado.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(filaResultado.getCodCentro(), filaResultado.getCodArticulo()));
					filaResultado.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(filaResultado.getCodArticuloGrid()));
				} else {
					filaResultado.setCodArticuloGrid((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
					filaResultado.setDescriptionArtGrid((encargosReservasLista.get(i).getDescripcion() != null && !("".equals(encargosReservasLista.get(i).getDescripcion())))?encargosReservasLista.get(i).getDescripcion():null);
				}
				
				
				filaResultado.setUniCajaServ((encargosReservasLista.get(i).getUnidadesCaja() != null && !("".equals(encargosReservasLista.get(i).getUnidadesCaja().toString())))?new Double(encargosReservasLista.get(i).getUnidadesCaja().toString()):null);
				filaResultado.setUsuario((encargosReservasLista.get(i).getUsuario() != null && !("".equals(encargosReservasLista.get(i).getUsuario())))?encargosReservasLista.get(i).getUsuario():null);
				
				
				filaResultado.setPerfil((encargosReservasLista.get(i).getOrigenEncargo() != null && !("".equals(encargosReservasLista.get(i).getOrigenEncargo().toString())))?new Long(encargosReservasLista.get(i).getOrigenEncargo().toString()):null);
				if (filaResultado.getPerfil() == 1 || filaResultado.getPerfil() == 4) {	
					filaResultado.setEsPlanograma("S");
				}
				
				filaResultado.setAgrupacion((encargosReservasLista.get(i).getEstructuraComercial() != null && !("".equals(encargosReservasLista.get(i).getEstructuraComercial())))?encargosReservasLista.get(i).getEstructuraComercial():null);
				filaResultado.setOferta((encargosReservasLista.get(i).getOferta() != null && !("".equals(encargosReservasLista.get(i).getOferta())))?encargosReservasLista.get(i).getOferta():null);
				
				//Obtención de tipo de aprovisionamiento no proporcionado por el procedimiento
				VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
				vSurtidoTienda.setCodCentro(filaResultado.getCodCentro());
				vSurtidoTienda.setCodArt(filaResultado.getCodArticulo());
				
				VSurtidoTienda surtidoAux = null;
				List<VSurtidoTienda> listSurtidoTienda = this.vSurtidoTiendaDao.findAll(vSurtidoTienda);
				if (!listSurtidoTienda.isEmpty()){
					surtidoAux = listSurtidoTienda.get(0);
				}
				if (null != surtidoAux){
					filaResultado.setTipoAprovisionamiento(surtidoAux.getTipoAprov());
				}

				//Inicializamos a Borrable el pedido.
				filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				//Inicializamos a NO Modificable el pedido.
				filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
				//Inicializamos a vacío el estado del pedido.

				filaResultado.setEstado("");

				//55674
				filaResultado.setTipoPedido(encargosReservasLista.get(i).getTipo());
				
				//Pet. 59408
				filaResultado.setDescPeriodo((encargosReservasLista.get(i).getDescPeriodo() != null && !("".equals(encargosReservasLista.get(i).getDescPeriodo())))?encargosReservasLista.get(i).getDescPeriodo():null);
				filaResultado.setEspacioPromo((encargosReservasLista.get(i).getEspacioPromo() != null && !("".equals(encargosReservasLista.get(i).getEspacioPromo())))?encargosReservasLista.get(i).getEspacioPromo():null);
				
				// PILADA
				if (filaResultado.getTipoPedido().equals(Constantes.TIPO_PEDIDO_PILADA)){
					filaResultado.setFechaInicio(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaInicio()));
					filaResultado.setFechaFin(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaFin()));
					filaResultado.setCapMin((encargosReservasLista.get(i).getImplantacionFinal() != null && !("".equals(encargosReservasLista.get(i).getImplantacionFinal().toString())))?encargosReservasLista.get(i).getImplantacionFinal():null);
					filaResultado.setCapMax((encargosReservasLista.get(i).getImplantacionInicial() != null && !("".equals(encargosReservasLista.get(i).getImplantacionInicial().toString())))?encargosReservasLista.get(i).getImplantacionInicial():null);
					filaResultado.setExcluir("S".equals(encargosReservasLista.get(i).getFlgExcluirVentas()));
					filaResultado.setOferta(encargosReservasLista.get(i).getOferta());
					
					filaResultado.setModificableIndiv(encargosReservasLista.get(i).getFlgModificablePilada());
					
					//Para que sea modificable tiene que tener el FlgModificablePilada "S" o "P" . No será modificable si tiene valor "N" 
					if (!Constantes.PEDIDO_MODIFICABLE_NO.equals(encargosReservasLista.get(i).getFlgModificablePilada()))
					{
						filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
						filaResultado.setBorrable(Constantes.PEDIDO_BORRABLE);
					}
			
				}
				// ENCARGOS
				else {
					filaResultado.setCantidad1((encargosReservasLista.get(i).getCantidad1() != null && !("".equals(encargosReservasLista.get(i).getCantidad1().toString())))?new Double(encargosReservasLista.get(i).getCantidad1().toString()):null);
					filaResultado.setFechaInicio(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaInicio()));
					filaResultado.setCantidad2((encargosReservasLista.get(i).getCantidad2() != null && !("".equals(encargosReservasLista.get(i).getCantidad2().toString())))?new Double(encargosReservasLista.get(i).getCantidad2().toString()):null);
					if (null!=encargosReservasLista.get(i).getFecha2()){
						filaResultado.setFecha2(Utilidades.formatearFecha(encargosReservasLista.get(i).getFecha2()));
					}
					filaResultado.setCantidad3((encargosReservasLista.get(i).getCantidad3() != null && !("".equals(encargosReservasLista.get(i).getCantidad3().toString())))?new Double(encargosReservasLista.get(i).getCantidad3().toString()):null);
					if (null!=encargosReservasLista.get(i).getFecha3()){
						filaResultado.setFecha3(Utilidades.formatearFecha(encargosReservasLista.get(i).getFecha3()));
					}
					if (null!=encargosReservasLista.get(i).getFechaInicioPilada()){
						filaResultado.setFechaPilada(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaInicioPilada()));
					}
					filaResultado.setFechaFin(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaFin()));
					filaResultado.setTratamiento(encargosReservasLista.get(i).getTratamiento());
					filaResultado.setCajas("S".equals(encargosReservasLista.get(i).getFlgForzarUnitaria()));
				
					String modificableIndiv="";
					if (null!=encargosReservasLista.get(i).getFlgModificableEnc1()){
						modificableIndiv=modificableIndiv+encargosReservasLista.get(i).getFlgModificableEnc1();
					}
					if (null!=encargosReservasLista.get(i).getFlgModificableEnc2()){
						modificableIndiv=modificableIndiv+encargosReservasLista.get(i).getFlgModificableEnc2();
					}
					if (null!=encargosReservasLista.get(i).getFlgModificableEnc3()){
						modificableIndiv=modificableIndiv+encargosReservasLista.get(i).getFlgModificableEnc3();
					}
					if (null!=encargosReservasLista.get(i).getFlgModificablePilada()){
						modificableIndiv=modificableIndiv+encargosReservasLista.get(i).getFlgModificablePilada();
					}
					filaResultado.setModificableIndiv(modificableIndiv);
					
					
					//Para que sea modificable tiene que tener algún FlgModificable "S" o "P" . No será modificable si tiene valor "N" 
					if ((!Constantes.PEDIDO_MODIFICABLE_NO.equals(encargosReservasLista.get(i).getFlgModificableEnc1()) && (encargosReservasLista.get(i).getFlgModificableEnc1() != null))
							|| (!Constantes.PEDIDO_MODIFICABLE_NO.equals(encargosReservasLista.get(i).getFlgModificableEnc2()) && (encargosReservasLista.get(i).getFlgModificableEnc2() != null))
							|| (!Constantes.PEDIDO_MODIFICABLE_NO.equals(encargosReservasLista.get(i).getFlgModificableEnc3()) && (encargosReservasLista.get(i).getFlgModificableEnc3() != null))
							|| (!Constantes.PEDIDO_MODIFICABLE_NO.equals(encargosReservasLista.get(i).getFlgModificablePilada()) && (encargosReservasLista.get(i).getFlgModificablePilada() != null)))
					{
						filaResultado.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);
						filaResultado.setBorrable(Constantes.PEDIDO_BORRABLE);
					}
									
				}
				
				/*
				//Si no es modificable, no se permitirá borrar. 
				if (Constantes.PEDIDO_MODIFICABLE_NO.equals(filaResultado.getModificable()))
				{
					filaResultado.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
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
				*/
				
				//Añadimos el índice para mantener la posición de cada registro.
				filaResultado.setIndice(indice);
				indice ++;
				if (pedidoAdicionalMO.getIdentificador() != null)
				{	
					if (filaResultado.getIdentificadorSIA().compareTo(pedidoAdicionalMO.getIdentificador()) == 0)
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

		public List<PedidoAdicionalMO> findAllVegalsa(PedidoAdicionalMO pedidoAdicionalMO, HttpSession session) {
			List<Object> params = new ArrayList<Object>();
			params.add(pedidoAdicionalMO.getIdentificadorVegalsa());
			
			String query = "SELECT * FROM T_PEDIDO_ADICIONAL WHERE IDENTIFICADOR_VEGALSA = ? AND IDSESION = '"+session.getId()+"'";
			return this.jdbcTemplate.query(query, this.rwTPedidoAdicionalMap, params.toArray());
		}
		
		private RowMapper<PedidoAdicionalMO> rwTPedidoAdicionalMap = new RowMapper<PedidoAdicionalMO>() {
			public PedidoAdicionalMO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				PedidoAdicionalMO output = new PedidoAdicionalMO();
				//final String idSession = resultSet.getString("IDSESION");
				final Long clasePedido = resultSet.getObject("CLASEPEDIDO")!=null?resultSet.getLong("CLASEPEDIDO"):null;
				output.setClasePedido(clasePedido);
				final Long codCentro = resultSet.getLong("CODCENTRO");
				output.setCodCentro(codCentro);
				final Long codArticulo = resultSet.getLong("CODARTICULO");
				output.setCodArticulo(codArticulo);
				//final String pantalla = resultSet.getString("PANTALLA");
				final Long identificador = (resultSet.getObject("IDENTIFICADOR")!=null?resultSet.getLong("IDENTIFICADOR"):null);
				output.setIdentificador(identificador);
				final String descriptionArt = resultSet.getString("DESCRIPTIONART");
				output.setDescriptionArt(descriptionArt);
				final Double unicajaServ = resultSet.getObject("UNICAJASERV")!=null?resultSet.getDouble("UNICAJASERV"):null;
				output.setUniCajaServ(unicajaServ);
				final String usuario = resultSet.getObject("USUARIO")!=null?resultSet.getString("USUARIO"):null;
				output.setUsuario(usuario);
				final Long perfil = resultSet.getLong("PERFIL");
				output.setPerfil(perfil);
				final String agrupacion = resultSet.getString("AGRUPACION");
				output.setAgrupacion(agrupacion);
				final String oferta = resultSet.getObject("OFERTA")!=null?resultSet.getString("OFERTA"):null;
				output.setOferta(oferta);
			    final String tipoAprovisionamiento = resultSet.getObject("TIPOAPROVISIONAMIENTO")!=null?resultSet.getString("TIPOAPROVISIONAMIENTO"):null;
			    output.setTipoAprovisionamiento(tipoAprovisionamiento);
			    final String borrable = resultSet.getString("BORRABLE");
			    output.setBorrable(borrable);
			    final String modificable = resultSet.getString("MODIFICABLE");
			    output.setModificable(modificable);
			    final String modificableIndiv = resultSet.getString("MODIFICABLEINDIV");
			    output.setModificableIndiv(modificableIndiv);
			    //final Double cajasPedidas = resultSet.getDouble("CAJASPEDIDAS");
			    //final String fechaEntrega = resultSet.getString("FECENTREGA");
			    final String fechaInicio = resultSet.getString("FECHAINICIO");
			    output.setFechaInicio(fechaInicio);
			    final String fechaFin = resultSet.getString("FECHAFIN");
			    output.setFechaFin(fechaFin);
//			    final String fecha2 = resultSet.getString("FECHA2");
//			    final String fecha3 = resultSet.getString("FECHA3");
//			    final String fecha4 = resultSet.getString("FECHA4");
//			    final String fecha5 = resultSet.getString("FECHA5");
			    final Double capMax = resultSet.getDouble("CAPMAX");
			    output.setCapMax(capMax);
			    final Double capMin = resultSet.getDouble("CAPMIN");
			    output.setCapMin(capMin);
//			    final Double cantidad1 = resultSet.getDouble("CANTIDAD1");
//			    final Double cantidad2 = resultSet.getDouble("CANTIDAD2");
//			    final Double cantidad3 = resultSet.getDouble("CANTIDAD3");
//			    final Double cantidad4 = resultSet.getDouble("CANTIDAD4");
//			    final Double cantidad5 = resultSet.getDouble("CANTIDAD5");
			    final String tipoPedido = resultSet.getString("TIPOPEDIDO");
			    output.setTipoPedido(tipoPedido);
//			    final String cajas = resultSet.getString("CAJAS");
			    final String excluir = resultSet.getString("EXCLUIR");
			    output.setExcluir(excluir!=null?excluir.equalsIgnoreCase("S"):false);
//			    final Date fechaCreacion = resultSet.getDate("FECHACREACION");
//			    final String codError = resultSet.getString("CODERROR");
//			    final String descError = resultSet.getString("DESCERROR");
//			    final String esPlanograma = resultSet.getString("ESPLANOGRAMA");
//			    final String denomOferta = resultSet.getString("DENOM_OFERTA");
//			    final Double cantMin = resultSet.getDouble("CANT_MIN");
//			    final Double cantMax = resultSet.getDouble("CANT_MAX");
//			    final String fechaInPil = resultSet.getString("FECHAINPIL");
//			    final String tratamiento = resultSet.getString("TRATAMIENTO");
//			    final String fechaHasta = resultSet.getString("FECHA_HASTA");
//			    final String estado = resultSet.getString("ESTADO");
//			    final String noGestionaPBL = resultSet.getString("NO_GESTIONA_PBL");
//			    final String descPeriodo = resultSet.getString("DESC_PERIODO");
//			    final String espacioPromo = resultSet.getString("ESPACIO_PROMO");
			    final Long codArtGrid = resultSet.getLong("COD_ART_GRID");
			    output.setCodArticuloGrid(codArtGrid);
			    final String descripArtGrid = resultSet.getString("DESCRIP_ART_GRID");
			    output.setDescriptionArtGrid(descripArtGrid);
			    final Long identificadorSIA = (resultSet.getObject("IDENTIFICADOR_SIA")!=null?resultSet.getLong("IDENTIFICADOR_SIA"):null);
			    output.setIdentificadorSIA(identificadorSIA);
			    final Long identificadorVegalsa = (resultSet.getObject("IDENTIFICADOR_VEGALSA")!=null?resultSet.getLong("IDENTIFICADOR_VEGALSA"):null);
			    output.setIdentificadorVegalsa(identificadorVegalsa);

			    return output;
			}

		};

}
