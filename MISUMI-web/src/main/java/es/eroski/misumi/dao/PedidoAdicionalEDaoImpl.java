package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.EncargosReservasDao;
import es.eroski.misumi.dao.iface.PedidoAdicionalEDao;
import es.eroski.misumi.dao.iface.VSurtidoTiendaDao;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.service.iface.UtilidadesCapraboService;
import es.eroski.misumi.util.Utilidades;



@Repository
public class PedidoAdicionalEDaoImpl implements PedidoAdicionalEDao{
	private static Logger logger = Logger.getLogger(PedidoAdicionalEDaoImpl.class);

	@Autowired
	private EncargosReservasDao encargosReservasDao;

	@Autowired
	private VSurtidoTiendaDao vSurtidoTiendaDao;


	@Autowired
	private UtilidadesCapraboService utilidadesCapraboService;

	@Override
	public List<PedidoAdicionalE> findAll(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {

		List<PedidoAdicionalE> resultado = null;
		int indice = 0;

		resultado = this.findAllSIA(pedidoAdicionalE, indice);

		return resultado;
	}

	private List<PedidoAdicionalE> findAllSIA(PedidoAdicionalE pedidoAdicionalE, int indice) throws Exception {

		EncargoReserva encargoReserva = new EncargoReserva();

		encargoReserva.setCodLoc((pedidoAdicionalE.getCodCentro() != null && !("".equals(pedidoAdicionalE.getCodCentro().toString())))?pedidoAdicionalE.getCodCentro():null);
		encargoReserva.setCodN1((pedidoAdicionalE.getGrupo1() != null && !("".equals(pedidoAdicionalE.getGrupo1().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo1().toString(), '0', 2):null);
		encargoReserva.setCodN2((pedidoAdicionalE.getGrupo2() != null && !("".equals(pedidoAdicionalE.getGrupo2().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo2().toString(), '0', 2):null);
		encargoReserva.setCodN3((pedidoAdicionalE.getGrupo3() != null && !("".equals(pedidoAdicionalE.getGrupo3().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo3().toString(), '0', 2):null);
		encargoReserva.setCodArtFormlog((pedidoAdicionalE.getCodArticulo() != null && !("".equals(pedidoAdicionalE.getCodArticulo().toString())))?pedidoAdicionalE.getCodArticulo():null);
		encargoReserva.setTipoPedidoAdicional((pedidoAdicionalE.getClasePedido() != null && !("".equals(pedidoAdicionalE.getClasePedido().toString())))?pedidoAdicionalE.getClasePedido():null);

		EncargosReservasLista resultProc = new EncargosReservasLista();

		//Llamamos al DAO que llama al procedimiento para obtener el resultado.
		resultProc = this.encargosReservasDao.obtenerEncReservas(encargoReserva);

		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
		List<PedidoAdicionalE> resultado = null;
		if (resultProc != null)
		{
			resultado = tratarDatosObtenerEncargosProc(resultProc, pedidoAdicionalE, indice);
		}

		return resultado;
	}


	@Override
	public PedidoAdicionalContadores getContadores(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {

		PedidoAdicionalContadores resultado  = null;
		//Se añaden las cuentas de SIA a las halladas desde PBL
		resultado = this.getContadoresSIA(pedidoAdicionalE, resultado);

		return resultado;
	}

	private PedidoAdicionalContadores getContadoresSIA(PedidoAdicionalE pedidoAdicionalE, PedidoAdicionalContadores pedidoAdicionalContadores) throws Exception {

		EncargoReserva encargoReserva = new EncargoReserva();

		encargoReserva.setCodLoc((pedidoAdicionalE.getCodCentro() != null && !("".equals(pedidoAdicionalE.getCodCentro().toString())))?pedidoAdicionalE.getCodCentro():null);
		encargoReserva.setCodN1((pedidoAdicionalE.getGrupo1() != null && !("".equals(pedidoAdicionalE.getGrupo1().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo1().toString(), '0', 2):null);
		encargoReserva.setCodN2((pedidoAdicionalE.getGrupo2() != null && !("".equals(pedidoAdicionalE.getGrupo2().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo2().toString(), '0', 2):null);
		encargoReserva.setCodN3((pedidoAdicionalE.getGrupo3() != null && !("".equals(pedidoAdicionalE.getGrupo3().toString())))?Utilidades.rellenarIzquierda(pedidoAdicionalE.getGrupo3().toString(), '0', 2):null);
		//La oferta no la utilizamos de momento, como campo se está tratando como numérico cuando desde Misumi se compone de año-numOferta
		encargoReserva.setOferta(null);
		//Faltaría filtrar por tipo de pedido pero el método no lo soporta. Actualmente no se utiliza getContadores

		EncargosReservasLista resultProc = new EncargosReservasLista();

		//Llamamos al DAO que llama al procedimiento para obtener el resultado.
		resultProc = this.encargosReservasDao.contarEncReservas(encargoReserva);

		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
		PedidoAdicionalContadores resultado = null;
		if (resultProc != null)
		{
			resultado = tratarDatosContarClasesProc(resultProc, pedidoAdicionalContadores);
		}

		return resultado;
	}

	@Override
	public List<PedidoAdicionalE> removeAll(List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception {

		List<PedidoAdicionalE> resultado = new ArrayList<PedidoAdicionalE>();
		List<PedidoAdicionalE> listaPedidoAdicionalEPBL = new ArrayList<PedidoAdicionalE>();
		List<PedidoAdicionalE> listaPedidoAdicionalESIA = new ArrayList<PedidoAdicionalE>();

		//Se obtienen listas separadas para el tratamiento por medio de PBL y SIA
		for (int i=0;i<listaPedidoAdicionalE.size();i++){
			if (listaPedidoAdicionalE.get(i).getIdentificadorSIA()!=null){
				listaPedidoAdicionalESIA.add(listaPedidoAdicionalE.get(i));
			}else{
				listaPedidoAdicionalEPBL.add(listaPedidoAdicionalE.get(i));
			}
		}
		resultado.addAll(this.removeAllSIA(listaPedidoAdicionalESIA));

		return resultado;
	}

	private List<PedidoAdicionalE> removeAllSIA(List<PedidoAdicionalE> listaPedidoAdicionalE) throws Exception {

		EncargoReserva encargoReserva = null;
		List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();

		for (int i=0;i<listaPedidoAdicionalE.size();i++){

			encargoReserva = new EncargoReserva();
			encargoReserva.setCodigoCentro((listaPedidoAdicionalE.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalE.get(i).getCodCentro().toString())))?listaPedidoAdicionalE.get(i).getCodCentro():null);
			encargoReserva.setArticulo((listaPedidoAdicionalE.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalE.get(i).getCodArticulo().toString())))?listaPedidoAdicionalE.get(i).getCodArticulo():null);
			encargoReserva.setIdentificador((listaPedidoAdicionalE.get(i).getIdentificadorSIA() != null && !("".equals(listaPedidoAdicionalE.get(i).getIdentificadorSIA().toString())))?listaPedidoAdicionalE.get(i).getIdentificadorSIA():null);
			encargoReserva.setTipoPedidoAdicional((listaPedidoAdicionalE.get(i).getClasePedido() != null && !("".equals(listaPedidoAdicionalE.get(i).getClasePedido().toString())))?listaPedidoAdicionalE.get(i).getClasePedido():null);

			listaEncargosReservas.add(encargoReserva);
		}

		EncargosReservasLista resultProc = new EncargosReservasLista();

		//Llamamos al DAO que llama al procedimiento para obtener el resultado.
		resultProc = this.encargosReservasDao.borrarEncReservas(listaEncargosReservas);

		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
		List<PedidoAdicionalE> resultado = null;
		if (resultProc != null)
		{
			resultado = tratarDatosEliminarProc(resultProc);
		}

		return resultado;
	}

	@Override
	public List<PedidoAdicionalE> validateAll(
			List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception {

		List<PedidoAdicionalE> resultado = null;
		List<PedidoAdicionalE> listaPedidoAdicionalEPBL = new ArrayList<PedidoAdicionalE>();
		List<PedidoAdicionalE> listaPedidoAdicionalESIA = new ArrayList<PedidoAdicionalE>();

		//Se obtienen listas separadas para el tratamiento por medio de PBL y SIA
		for (int i=0;i<listaPedidoAdicionalE.size();i++){
			if (listaPedidoAdicionalE.get(i).getIdentificadorSIA()!=null){
				listaPedidoAdicionalESIA.add(listaPedidoAdicionalE.get(i));
			}else{
				listaPedidoAdicionalEPBL.add(listaPedidoAdicionalE.get(i));
			}
		}
		
		resultado = this.validateAllSIA(listaPedidoAdicionalESIA);

		return resultado;
	}

	private List<PedidoAdicionalE> validateAllSIA(
			List<PedidoAdicionalE> listaPedidoAdicionalE) throws Exception {

		EncargoReserva encargoReserva = null;
		List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();
		EncargosReservasLista encargosReservasLista = new EncargosReservasLista();

		//Una vez obtenido el resultado tenemos que tratarlo para obtener la lista de objetos a devolver.
		List<PedidoAdicionalE> resultado = null;

		for (int i=0;i<listaPedidoAdicionalE.size();i++){

			encargoReserva = new EncargoReserva();
			encargoReserva.setCodLoc((listaPedidoAdicionalE.get(i).getCodCentro() != null && !("".equals(listaPedidoAdicionalE.get(i).getCodCentro().toString())))?listaPedidoAdicionalE.get(i).getCodCentro():null);
			encargoReserva.setCodArtFormlog((listaPedidoAdicionalE.get(i).getCodArticulo() != null && !("".equals(listaPedidoAdicionalE.get(i).getCodArticulo().toString())))?listaPedidoAdicionalE.get(i).getCodArticulo():null);

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

	private List<PedidoAdicionalE> tratarDatosValidarProc(EncargosReservasLista resultProc){

		//Transformación de datos para estructura de PedidoAdicionalE
		List<PedidoAdicionalE> resultado = new ArrayList<PedidoAdicionalE>();
		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
		PedidoAdicionalE filaResultado = new PedidoAdicionalE();
		if (null != resultProc.getDatos()){
			encargosReservasLista = resultProc.getDatos();
		}

		//Nos recorremos la lista principal
		for (int i=0;i<encargosReservasLista.size();i++){
			filaResultado = new PedidoAdicionalE();
			filaResultado.setCodCentro((encargosReservasLista.get(i).getCodigoCentro() != null && !("".equals(encargosReservasLista.get(i).getCodigoCentro().toString())))?new Long(encargosReservasLista.get(i).getCodigoCentro().toString()):null);
			filaResultado.setCodArticulo((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
			filaResultado.setCodigoRespuesta(encargosReservasLista.get(i).getCodError().toString());
			filaResultado.setDescripcionRespuesta(encargosReservasLista.get(i).getDescError());

			if (!filaResultado.getCodigoRespuesta().equals("1")) {
				// Si no ha fallado el procedimiento.
				filaResultado.setFecEntrega(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaVenta()));
			}

			resultado.add(filaResultado);
		}
		return resultado;
	}

	private List<PedidoAdicionalE> tratarDatosObtenerEncargosProc(EncargosReservasLista resultProc, PedidoAdicionalE pedidoAdicionalE, int indice) throws Exception{

		//Transformación de datos para estructura de PedidoAdicionalE
		List<PedidoAdicionalE> resultado = new ArrayList<PedidoAdicionalE>();
		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
		PedidoAdicionalE filaResultado = new PedidoAdicionalE();
		if (null != resultProc.getDatos()){
			encargosReservasLista = resultProc.getDatos();
		}

		try {
			//Nos recorremos la lista principal
			for (int i=0;i<encargosReservasLista.size();i++){
				filaResultado = new PedidoAdicionalE();
				filaResultado.setCodCentro((encargosReservasLista.get(i).getCodigoCentro() != null && !("".equals(encargosReservasLista.get(i).getCodigoCentro().toString())))?new Long(encargosReservasLista.get(i).getCodigoCentro().toString()):null);
				filaResultado.setClasePedido((encargosReservasLista.get(i).getTipoPedidoAdicional() != null && !("".equals(encargosReservasLista.get(i).getTipoPedidoAdicional().toString())))?new Long(encargosReservasLista.get(i).getTipoPedidoAdicional().toString()):null);
				filaResultado.setIdentificador(null);
				filaResultado.setIdentificadorSIA((encargosReservasLista.get(i).getIdentificador() != null && !("".equals(encargosReservasLista.get(i).getIdentificador().toString())))?new Long(encargosReservasLista.get(i).getIdentificador().toString()):null);
				filaResultado.setCodArticulo((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
				filaResultado.setDescriptionArt((encargosReservasLista.get(i).getDescripcion() != null && !("".equals(encargosReservasLista.get(i).getDescripcion())))?encargosReservasLista.get(i).getDescripcion():null);

				if (pedidoAdicionalE.getEsCaprabo()) {
					filaResultado.setCodArticuloGrid(utilidadesCapraboService.obtenerCodigoCaprabo(filaResultado.getCodCentro(), filaResultado.getCodArticulo()));
					filaResultado.setDescriptionArtGrid(utilidadesCapraboService.obtenerDescArtCaprabo(filaResultado.getCodArticuloGrid()));
				} else {
					filaResultado.setCodArticuloGrid((encargosReservasLista.get(i).getArticulo() != null && !("".equals(encargosReservasLista.get(i).getArticulo().toString())))?new Long(encargosReservasLista.get(i).getArticulo().toString()):null);
					filaResultado.setDescriptionArtGrid((encargosReservasLista.get(i).getDescripcion() != null && !("".equals(encargosReservasLista.get(i).getDescripcion())))?encargosReservasLista.get(i).getDescripcion():null);
				}


				filaResultado.setUniCajaServ((encargosReservasLista.get(i).getUnidadesCaja() != null && !("".equals(encargosReservasLista.get(i).getUnidadesCaja().toString())))?new Double(encargosReservasLista.get(i).getUnidadesCaja().toString()):null);
				filaResultado.setUsuario((encargosReservasLista.get(i).getUsuario() != null && !("".equals(encargosReservasLista.get(i).getUsuario())))?encargosReservasLista.get(i).getUsuario():null);
				filaResultado.setPerfil((encargosReservasLista.get(i).getOrigenEncargo() != null && !("".equals(encargosReservasLista.get(i).getOrigenEncargo().toString())))?new Long(encargosReservasLista.get(i).getOrigenEncargo().toString()):null);
				filaResultado.setAgrupacion((encargosReservasLista.get(i).getEstructuraComercial() != null && !("".equals(encargosReservasLista.get(i).getEstructuraComercial())))?encargosReservasLista.get(i).getEstructuraComercial():null);

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

				filaResultado.setEstado("");
				//Pet. 55674
				//filaResultado.setUnidadesPedidas((encargosReservasLista.get(i).getCantidadUnidades() != null && !("".equals(encargosReservasLista.get(i).getCantidadUnidades().toString())))?new Double(encargosReservasLista.get(i).getCantidadUnidades().toString()):null);
				filaResultado.setUnidadesPedidas((encargosReservasLista.get(i).getCantidad1() != null && !("".equals(encargosReservasLista.get(i).getCantidad1().toString())))?new Double(encargosReservasLista.get(i).getCantidad1().toString()):null);
				filaResultado.setFecEntrega(Utilidades.formatearFecha(encargosReservasLista.get(i).getFechaInicio()));
				filaResultado.setCajas("S".equals(encargosReservasLista.get(i).getFlgForzarUnitaria()));
				filaResultado.setExcluir("S".equals(encargosReservasLista.get(i).getFlgExcluirVentas()));
				//Pet. 55674
				//filaResultado.setModificable(encargosReservasLista.get(i).getFlgModificable());
				filaResultado.setModificable(encargosReservasLista.get(i).getFlgModificableEnc1());
				filaResultado.setTratamiento(encargosReservasLista.get(i).getTratamiento());

				//Control bloqueos
				//if (Constantes.PEDIDO_MODIFICABLE_BLOQUEO.equals(filaResultado.getModificable()) || 
				//		Constantes.PEDIDO_MODIFICABLE_BLOQUEO_PARCIAL_MODIF.equals(filaResultado.getModificable()) ||
				//		Constantes.PEDIDO_MODIFICABLE_BLOQUEO_TOTAL_MODIF.equals(filaResultado.getModificable()))
				//{
				//	filaResultado.setEstado(Constantes.PEDIDO_ESTADO_NO_ACTIVA);
				//}

				//Añadimos el índice para mantener la posición de cada registro.
				filaResultado.setIndice(indice);

				indice ++;
				if (pedidoAdicionalE.getIdentificador() != null)
				{	
					if (filaResultado.getIdentificadorSIA().compareTo(pedidoAdicionalE.getIdentificador()) == 0)
					{
						resultado.add(filaResultado);
					}
				}
				else
				{
					resultado.add(filaResultado);
				}
			}

		} catch (Exception e) {
			logger.error("tratarDatosObtenerEncargosProc="+e.toString());
			e.printStackTrace();
		}
		return resultado;
	}

	private List<PedidoAdicionalE> tratarDatosEliminarProc(EncargosReservasLista resultProc){

		//Transformación de datos para estructura de PedidoAdicionalE
		List<PedidoAdicionalE> resultado = new ArrayList<PedidoAdicionalE>();
		List<EncargoReserva> encargosReservasLista = new ArrayList<EncargoReserva>();
		PedidoAdicionalE filaResultado = new PedidoAdicionalE();
		if (null != resultProc.getDatos()){
			encargosReservasLista = resultProc.getDatos();
		}

		//Nos recorremos la lista principal
		for (int i=0;i<encargosReservasLista.size();i++){
			filaResultado = new PedidoAdicionalE();
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

	private PedidoAdicionalContadores tratarDatosContarClasesProc(EncargosReservasLista resultProc, PedidoAdicionalContadores pedidoAdicionalContadores){

		//Transformación de datos para estructura de PedidoAdicionalContadores
		PedidoAdicionalContadores resultado = new PedidoAdicionalContadores();
		List<EncargoReserva> listaProc = new ArrayList<EncargoReserva>();

		if (null != resultProc.getDatos()){
			listaProc = resultProc.getDatos();
		}
		resultado.setCodError(new Long(resultProc.getEstado()));
		resultado.setDescError(resultProc.getDescEstado());
		//Nos recorremos la lista 
		Long clasePedido = null;
		for (int i=0;i<listaProc.size();i++){

			//Obtenemos la clase de pedido.
			clasePedido = (listaProc.get(i).getTipoPedidoAdicional() != null && !("".equals(listaProc.get(i).getTipoPedidoAdicional().toString())))?new Long(listaProc.get(i).getTipoPedidoAdicional().toString()):null;

			//Por cada clase de pedido se hallan sus ocurrencias y se le suma lo que proviene de cálculos anteriores
			if (clasePedido == 1)
			{
				resultado.setContadorEncargos((pedidoAdicionalContadores.getContadorEncargos()!=null?pedidoAdicionalContadores.getContadorEncargos():new Long(0)) + ((listaProc.get(i).getNumeroOcurrencias() != null && !("".equals(listaProc.get(i).getNumeroOcurrencias().toString())))?new Long(listaProc.get(i).getNumeroOcurrencias().toString()):new Long(0)));
			}
			else if (clasePedido == 2)
			{
				resultado.setContadorMontaje((pedidoAdicionalContadores.getContadorMontaje()!=null?pedidoAdicionalContadores.getContadorMontaje():new Long(0)) + ((listaProc.get(i).getNumeroOcurrencias() != null && !("".equals(listaProc.get(i).getNumeroOcurrencias().toString())))?new Long(listaProc.get(i).getNumeroOcurrencias().toString()):new Long(0)));
			}
			else if (clasePedido == 3)
			{
				resultado.setContadorMontajeOferta((pedidoAdicionalContadores.getContadorMontajeOferta()!=null?pedidoAdicionalContadores.getContadorMontajeOferta():new Long(0)) + ((listaProc.get(i).getNumeroOcurrencias() != null && !("".equals(listaProc.get(i).getNumeroOcurrencias().toString())))?new Long(listaProc.get(i).getNumeroOcurrencias().toString()):new Long(0)));
			}
		}
		return resultado;
	}

}
