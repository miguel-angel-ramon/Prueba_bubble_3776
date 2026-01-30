package es.eroski.misumi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EncargosReservasDao;
import es.eroski.misumi.dao.iface.PedidoHTNoPblDao;
import es.eroski.misumi.dao.iface.TPedidoAdicionalDao;
import es.eroski.misumi.dao.iface.VPlanPedidoAdicionalDao;
import es.eroski.misumi.model.CamposSeleccionadosVC;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.PedidoAdicionalCompleto;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Service(value = "TPedidoAdicionalService")
public class TPedidoAdicionalServiceImpl implements TPedidoAdicionalService {
	@Autowired
	private TPedidoAdicionalDao tPedidoAdicionalDao;

	@Autowired
	private VPlanPedidoAdicionalDao vPlanPedidoAdicionalDao;

	@Autowired
	private PedidoHTNoPblDao pedidoHTNoPblDao;

	@Autowired
	private EncargosReservasDao encargosReservasDao;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;

	@Override
	public void delete(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.delete(tPedidoAdicional);
	}

	@Override
	public void deleteCalendario(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.deleteCalendario(tPedidoAdicional);
	}

	@Override
	public void deleteHistorico() throws Exception {
		this.tPedidoAdicionalDao.deleteHistorico();
	}

	@Override
	public void deleteArticulo(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.deleteArticulo(tPedidoAdicional);
	}

	@Override
	public void updateErrorArticulo(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updateErrorArticulo(tPedidoAdicional);
	}

	@Override
	public void updateModifCantArticulo(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updateModifCantArticulo(tPedidoAdicional);
	}

	@Override
	public void updateErroresTesteo(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updateErroresTesteo(tPedidoAdicional);
	}

	@Override
	public void updateErrores(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updateErrores(tPedidoAdicional);
	}

	@Override
	public void resetearGuardados(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.resetearGuardados(tPedidoAdicional);
	}

	@Override
	public void updateModifFechaFinArticulo(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updateModifFechaFinArticulo(tPedidoAdicional);
	}

	@Override
	public void insertAll(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.insertAll(listaTPedidoAdicional);
	}

	@Override
	public List<TPedidoAdicional> findAll(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findAll(tPedidoAdicional);
	}

	@Override
	public TPedidoAdicional findOne(TPedidoAdicional tPedidoAdicional) throws Exception {
		TPedidoAdicional tPedidoAdicionalRes = null;
		List<TPedidoAdicional> lista = this.tPedidoAdicionalDao.findAll(tPedidoAdicional);
		if (lista != null && lista.size() > 0) {
			tPedidoAdicionalRes = lista.get(0);
		}
		return tPedidoAdicionalRes;
	}

	@Override
	public Long findAllCount(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findAllCount(tPedidoAdicional);
	}

	@Override
	public void insertAllNuevoOferta(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.insertAllNuevoOferta(listaTPedidoAdicional);
	}

	@Override
	public List<TPedidoAdicional> findAllPaginate(TPedidoAdicional tPedidoAdicional, Pagination pagination)
			throws Exception {
		return this.tPedidoAdicionalDao.findAllPaginate(tPedidoAdicional, pagination);
	}

	@Override
	public void updatePedidosValidar(final List<CamposSeleccionadosVC> listToUpdate, final String sesionID)
			throws Exception {
		this.tPedidoAdicionalDao.updatePedidosValidar(listToUpdate, sesionID);
	}

	@Override
	public void updatePedido(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.updatePedido(tPedidoAdicional);
	}

	@Override
	public void updatePedidoVegalsa(final Long idVegalsa, final Double cantidad, final String fechaIni, final String fechaFin, final String user) throws Exception {
		this.tPedidoAdicionalDao.updatePedidoVegalsa(idVegalsa, cantidad, fechaIni, fechaFin, user);
	}
	
	@Override
	public void insertPlanogramadas(TPedidoAdicional tPedidoAdicional) throws Exception {
		this.tPedidoAdicionalDao.insertPlanogramadas(tPedidoAdicional);
	}

	@Override
	public void insertPedidosHTNoPbl(TPedidoAdicional tPedidoAdicional) throws Exception {
		List<TPedidoAdicional> listaPedidoAdicional = null;
		PedidoHTNoPbl pedidoHTNoPbl = new PedidoHTNoPbl();
		pedidoHTNoPbl.setCodCentro(tPedidoAdicional.getCodCentro());
		pedidoHTNoPbl.setCodArticulo(tPedidoAdicional.getCodArticulo());
		pedidoHTNoPbl.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_TODOS);
		PedidoHTNoPblLista listaNoGestionadosPbl = this.pedidoHTNoPblDao.consultaPedidosHTNoPbl(pedidoHTNoPbl);
		listaPedidoAdicional = this.addPedidosHTNoPbl(listaNoGestionadosPbl, tPedidoAdicional, Constantes.PANTALLA_CALENDARIO);
		this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);
	}

	@Override
	public void insertPedidosNoAliSIA(TPedidoAdicional tPedidoAdicional) throws Exception {

		List<TPedidoAdicional> listaPedidoAdicional = null;
		EncargoReserva encargoReserva = new EncargoReserva();

		encargoReserva.setCodLoc((tPedidoAdicional.getCodCentro() != null && !("".equals(tPedidoAdicional.getCodCentro().toString())))?tPedidoAdicional.getCodCentro():null);
		encargoReserva.setCodArtFormlog((tPedidoAdicional.getCodArticulo() != null && !("".equals(tPedidoAdicional.getCodArticulo().toString())))?tPedidoAdicional.getCodArticulo():null);
		encargoReserva.setTipoPedidoAdicional((tPedidoAdicional.getClasePedido() != null && !("".equals(tPedidoAdicional.getClasePedido().toString())))?tPedidoAdicional.getClasePedido():null);

		EncargosReservasLista resultProc = new EncargosReservasLista();

		//Llamamos al DAO que llama al procedimiento para obtener el resultado.
		resultProc = this.encargosReservasDao.obtenerEncReservas(encargoReserva);


		listaPedidoAdicional = this.addPedidosNoAliSIA(resultProc, tPedidoAdicional);
		this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);



	}
	
	@Override
	public void deleteDatosSesionPedidoAdicional(Long codCentro, String idSesion) throws Exception{
		this.tPedidoAdicionalDao.deleteDatosSesionPedidoAdicional(codCentro,idSesion);
	}

	public void obtenerPedidosAdicionales(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception {
		// Eliminar temporales
		this.eliminarTablaSesion(tPedidoAdicional.getIdSesion(), tPedidoAdicional.getCodCentro());

		List<TPedidoAdicional> listaPedidoAdicional;
		// Planogramadas
		VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
		vPlanPedidoAdicional.setCodCentro(tPedidoAdicional.getCodCentro());
		vPlanPedidoAdicional.setCodArt(tPedidoAdicional.getCodArticulo());
		List<VPlanPedidoAdicional> listPlanogramas = this.vPlanPedidoAdicionalDao.findAll(vPlanPedidoAdicional);
		listaPedidoAdicional = this.addPlanogramas(listPlanogramas, tPedidoAdicional);
		this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);

		// No gestionadas por PBL
		PedidoHTNoPbl pedidoHTNoPbl = new PedidoHTNoPbl();
		pedidoHTNoPbl.setCodCentro(tPedidoAdicional.getCodCentro());
		pedidoHTNoPbl.setCodArticulo(tPedidoAdicional.getCodArticulo());
		pedidoHTNoPbl.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_TODOS);
		PedidoHTNoPblLista listaNoGestionadosPbl = this.pedidoHTNoPblDao.consultaPedidosHTNoPbl(pedidoHTNoPbl);
		listaPedidoAdicional = this.addPedidosHTNoPbl(listaNoGestionadosPbl, tPedidoAdicional, Constantes.PANTALLA_LISTADOS);
		this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);
	}

	public void obtenerPedidosAdicionales(List<TPedidoAdicional> listaTPedidoAdicional, Long codCentro, String idSesion, HttpSession session)
			throws Exception {

		// Eliminar temporales
		this.eliminarTablaSesion(idSesion, codCentro);
		// ObtenerPedidoAdicional de las distintas referencias
		for (TPedidoAdicional tPedidoAdicional : listaTPedidoAdicional) {

			// Se actualiza el id de sesion con el proporcionado como parámetro
			tPedidoAdicional.setIdSesion(idSesion);

			List<TPedidoAdicional> listaPedidoAdicional;
			// Planogramadas
			VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
			vPlanPedidoAdicional.setCodCentro(tPedidoAdicional.getCodCentro());
			vPlanPedidoAdicional.setCodArt(tPedidoAdicional.getCodArticulo());
			List<VPlanPedidoAdicional> listPlanogramas = this.vPlanPedidoAdicionalDao.findAll(vPlanPedidoAdicional);
			listaPedidoAdicional = this.addPlanogramas(listPlanogramas, tPedidoAdicional);
			this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);

			// No gestionadas por PBL
			PedidoHTNoPbl pedidoHTNoPbl = new PedidoHTNoPbl();
			pedidoHTNoPbl.setCodCentro(tPedidoAdicional.getCodCentro());
			pedidoHTNoPbl.setCodArticulo(tPedidoAdicional.getCodArticulo());
			pedidoHTNoPbl.setValidados(Constantes.NO_GESTIONADO_PBL_VALIDADOS_TODOS);
			PedidoHTNoPblLista listaNoGestionadosPbl = this.pedidoHTNoPblDao.consultaPedidosHTNoPbl(pedidoHTNoPbl);
			listaPedidoAdicional = this.addPedidosHTNoPbl(listaNoGestionadosPbl, tPedidoAdicional, Constantes.PANTALLA_LISTADOS);
			this.tPedidoAdicionalDao.insertAll(listaPedidoAdicional);

		}
	}

	public List<GenericExcelVO> findAllExcel(TPedidoAdicional tPedidoAdicional, String[] columnModel) throws Exception {
		return this.tPedidoAdicionalDao.findAllExcel(tPedidoAdicional, columnModel);
	}

	private void eliminarTablaSesion(String idSesion, Long codCentro) {

		TPedidoAdicional registro = new TPedidoAdicional();

		registro.setIdSesion(idSesion);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_LISTADOS);

		try {
			this.tPedidoAdicionalDao.delete(registro);
		} catch (Exception e) {

		}
	}

	private List<TPedidoAdicional> addPlanogramas(List<VPlanPedidoAdicional> listPlanogramas,
			TPedidoAdicional tPedidoAdicional) throws Exception {

		List<TPedidoAdicional> list = new ArrayList<TPedidoAdicional>();

		for (VPlanPedidoAdicional vPlanPedidoAdicional : listPlanogramas) {
			TPedidoAdicional tPedidoAdicionalAux = new TPedidoAdicional();
			tPedidoAdicionalAux.setIdSesion(tPedidoAdicional.getIdSesion());
			tPedidoAdicionalAux.setPantalla(Constantes.PANTALLA_LISTADOS);
			tPedidoAdicionalAux.setCodCentro(vPlanPedidoAdicional.getCodCentro());
			tPedidoAdicionalAux.setCodArticulo(vPlanPedidoAdicional.getCodArt());
			tPedidoAdicionalAux.setDescriptionArt(vPlanPedidoAdicional.getDescripArt());
			tPedidoAdicionalAux.setAgrupacion(vPlanPedidoAdicional.getAgrupacion());
			tPedidoAdicionalAux.setUniCajaServ(vPlanPedidoAdicional.getUniCajaServ());
			tPedidoAdicionalAux.setFechaInicio((vPlanPedidoAdicional.getFechaInicio() != null)
					? Utilidades.formatearFecha(vPlanPedidoAdicional.getFechaInicio()) : null);
			tPedidoAdicionalAux.setFechaFin((vPlanPedidoAdicional.getFechaFin() != null)
					? Utilidades.formatearFecha(vPlanPedidoAdicional.getFechaFin()) : null);
			tPedidoAdicionalAux.setCapMax(vPlanPedidoAdicional.getImpInicial());
			tPedidoAdicionalAux.setCapMin(vPlanPedidoAdicional.getImpFinal());
			tPedidoAdicionalAux.setPerfil(vPlanPedidoAdicional.getPerfil());
			tPedidoAdicionalAux.setExcluir(vPlanPedidoAdicional.getExcluir());
			tPedidoAdicionalAux.setEsPlanograma("S");
			tPedidoAdicionalAux.setNoGestionaPbl("N");
			tPedidoAdicionalAux.setTipoAprovisionamiento(vPlanPedidoAdicional.getTipoAprovisionamiento());
			if (null != vPlanPedidoAdicional.getAnoOferta() && null != vPlanPedidoAdicional.getCodOferta()) {
				tPedidoAdicionalAux
				.setOferta(vPlanPedidoAdicional.getAnoOferta() + "-" + vPlanPedidoAdicional.getCodOferta());
				tPedidoAdicionalAux.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
			} else {
				tPedidoAdicionalAux.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
			}
			// Las planogramadas no son ni modificables ni borrables.
			tPedidoAdicionalAux.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			tPedidoAdicionalAux.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);
			tPedidoAdicionalAux.setDescPeriodo(vPlanPedidoAdicional.getDescPeriodo());
			tPedidoAdicionalAux.setEspacioPromo(vPlanPedidoAdicional.getEspacioPromo());

			list.add(tPedidoAdicionalAux);

		}

		return list;
	}

	private List<TPedidoAdicional> addPedidosHTNoPbl(PedidoHTNoPblLista listPedidosHTNoPbl,
			TPedidoAdicional tPedidoAdicional, String pantalla) throws Exception {

		List<TPedidoAdicional> list = new ArrayList<TPedidoAdicional>();
		List<PedidoHTNoPbl> datosPedidosHTNoPbl = null;
		if (listPedidosHTNoPbl != null && listPedidosHTNoPbl.getDatos() != null) {
			datosPedidosHTNoPbl = listPedidosHTNoPbl.getDatos();
		}
		if (datosPedidosHTNoPbl != null) {
			for (PedidoHTNoPbl pedidoHTNoPbl : datosPedidosHTNoPbl) {
				TPedidoAdicional tPedidoAdicionalAux = new TPedidoAdicional();
				tPedidoAdicionalAux.setIdSesion(tPedidoAdicional.getIdSesion());
				tPedidoAdicionalAux.setPantalla(pantalla);
				tPedidoAdicionalAux.setCodCentro(pedidoHTNoPbl.getCodCentro());
				tPedidoAdicionalAux.setCodArticulo(pedidoHTNoPbl.getCodArticulo());
				tPedidoAdicionalAux.setDescriptionArt(pedidoHTNoPbl.getDescriptionArt());
				tPedidoAdicionalAux.setAgrupacion(pedidoHTNoPbl.getAgrupacion());
				tPedidoAdicionalAux.setUniCajaServ(pedidoHTNoPbl.getUniCajaServ());
				tPedidoAdicionalAux.setFechaInicio(pedidoHTNoPbl.getFechaInicio());
				tPedidoAdicionalAux.setFechaFin(pedidoHTNoPbl.getFechaFin());
				tPedidoAdicionalAux.setCapMax(pedidoHTNoPbl.getCantMax());
				tPedidoAdicionalAux.setCapMin(pedidoHTNoPbl.getCantMin());
				tPedidoAdicionalAux.setPerfil(new Long(1)); //Pet. 58538
				tPedidoAdicionalAux.setExcluir("S");// En la pantalla no
				// aparecerá pero tendrá
				// "S" por defecto
				tPedidoAdicionalAux.setEsPlanograma("N");
				tPedidoAdicionalAux.setNoGestionaPbl("S");
				tPedidoAdicionalAux.setTipoAprovisionamiento(pedidoHTNoPbl.getTipoAprovisionamiento());
				if (null != pedidoHTNoPbl.getAnoOferta() && null != pedidoHTNoPbl.getNumOferta()) {
					tPedidoAdicionalAux.setOferta(pedidoHTNoPbl.getAnoOferta() + "-" + pedidoHTNoPbl.getNumOferta());
					tPedidoAdicionalAux.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
				} else {
					tPedidoAdicionalAux.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
				}


				// Las no gestionadas por PBL no son borrables.
				tPedidoAdicionalAux.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				tPedidoAdicionalAux.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);

				list.add(tPedidoAdicionalAux);

			}
		}

		return list;
	}

	private List<TPedidoAdicional> addPedidosNoAliSIA(EncargosReservasLista encargosReservasLista,
			TPedidoAdicional tPedidoAdicional) throws Exception {


		List<TPedidoAdicional> list = new ArrayList<TPedidoAdicional>();
		List<EncargoReserva> listaEncargosReservas = new ArrayList<EncargoReserva>();

		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");

		if (encargosReservasLista != null) {
			listaEncargosReservas = encargosReservasLista.getDatos();
		}

		if (encargosReservasLista != null) {
			for (EncargoReserva encargoReserva : listaEncargosReservas) {
				TPedidoAdicional tPedidoAdicionalAux = new TPedidoAdicional();
				tPedidoAdicionalAux.setIdSesion(tPedidoAdicional.getIdSesion());
				tPedidoAdicionalAux.setPantalla(Constantes.PANTALLA_CALENDARIO);
				tPedidoAdicionalAux.setCodCentro(encargoReserva.getCodigoCentro());
				tPedidoAdicionalAux.setCodArticulo(encargoReserva.getArticulo());
				tPedidoAdicionalAux.setDescriptionArt(encargoReserva.getDescripcion());
				tPedidoAdicionalAux.setAgrupacion(encargoReserva.getEstructuraComercial());
				//Pet. 55674
				//tPedidoAdicionalAux.setUniCajaServ(encargoReserva.getCantidadUnidades());
				tPedidoAdicionalAux.setCantidad1(encargoReserva.getCantidad1());
				tPedidoAdicionalAux.setCantidad2(encargoReserva.getCantidad2());
				tPedidoAdicionalAux.setCantidad3(encargoReserva.getCantidad3());
				tPedidoAdicionalAux.setFecha2((encargoReserva.getFecha2() != null && !("".equals(encargoReserva.getFecha2())))?df.format(encargoReserva.getFecha2()):null);
				tPedidoAdicionalAux.setFecha3((encargoReserva.getFecha3() != null && !("".equals(encargoReserva.getFecha3())))?df.format(encargoReserva.getFecha3()):null);
				tPedidoAdicionalAux.setFechaPilada((encargoReserva.getFechaInicioPilada() != null && !("".equals(encargoReserva.getFechaInicioPilada())))?df.format(encargoReserva.getFechaInicioPilada()):null);
				tPedidoAdicionalAux.setTratamiento(encargoReserva.getTratamiento());
				tPedidoAdicionalAux.setCajas(encargoReserva.getFlgForzarUnitaria());
				tPedidoAdicionalAux.setExcluir(encargoReserva.getFlgExcluirVentas());


				if (encargoReserva.getTipoPedidoAdicional().equals(new Long(Constantes.CLASE_PEDIDO_ENCARGO))) { // Peticion 58538. Si es una Encargo la fecha inicio devuelta por el procedimiento de SIA hay que insertarla en FECENTREGA. 
					//Es lo que se hace en el BUSCAR.
					tPedidoAdicionalAux.setFecEntrega((encargoReserva.getFechaInicio() != null && !("".equals(encargoReserva.getFechaInicio())))?df.format(encargoReserva.getFechaInicio()):null);
				} else {
					tPedidoAdicionalAux.setFechaInicio((encargoReserva.getFechaInicio() != null && !("".equals(encargoReserva.getFechaInicio())))?df.format(encargoReserva.getFechaInicio()):null);
				}

				tPedidoAdicionalAux.setFechaFin((encargoReserva.getFechaFin() != null && !("".equals(encargoReserva.getFechaFin())))?df.format(encargoReserva.getFechaFin()):null);
				tPedidoAdicionalAux.setCapMax(encargoReserva.getImplantacionInicial());
				tPedidoAdicionalAux.setCapMin(encargoReserva.getImplantacionFinal());
				if (encargoReserva.getOrigenEncargo() != null) {
					tPedidoAdicionalAux.setPerfil(new Long(encargoReserva.getOrigenEncargo()));//Pet. 58538
				}
				//tPedidoAdicionalAux.setExcluir("S");// En la pantalla no
				// aparecerá pero tendrá
				// "S" por defecto
				tPedidoAdicionalAux.setEsPlanograma("N");
				tPedidoAdicionalAux.setNoGestionaPbl("S");
				tPedidoAdicionalAux.setTipoAprovisionamiento(encargoReserva.getTipoAprovisionamiento());

				//Pet. 59408
				tPedidoAdicionalAux.setDescPeriodo((encargoReserva.getDescPeriodo() != null && !("".equals(encargoReserva.getDescPeriodo())))?encargoReserva.getDescPeriodo():null);
				tPedidoAdicionalAux.setEspacioPromo((encargoReserva.getEspacioPromo() != null && !("".equals(encargoReserva.getEspacioPromo())))?encargoReserva.getEspacioPromo():null);

				tPedidoAdicionalAux.setIdentificadorSIA(encargoReserva.getIdentificador());
				tPedidoAdicionalAux.setIdentificador(null);

				if (null != encargoReserva.getOferta()) {
					tPedidoAdicionalAux.setOferta(encargoReserva.getOferta());	
				} 
				tPedidoAdicionalAux.setClasePedido(encargoReserva.getTipoPedidoAdicional());

				// Las no gestionadas por PBL no son borrables.
				tPedidoAdicionalAux.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
				tPedidoAdicionalAux.setModificable(Constantes.PEDIDO_MODIFICABLE_SI);


				list.add(tPedidoAdicionalAux);

			}
		}

		return list;
	}

	public List<String> findComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findComboValidarVC(tPedidoAdicional);
	}

	public List<String> findComboOfertaPeriodoMO(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findComboOfertaPeriodoMO(tPedidoAdicional);
	}

	public List<String> findComboEspacioPromoMO(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findComboEspacioPromoMO(tPedidoAdicional);
	}

	public String findSelectedComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findSelectedComboValidarVC(tPedidoAdicional);
	}

	public List<TPedidoAdicional> findAllBloqueos(TPedidoAdicional tPedidoAdicional, Pagination pagination)
			throws Exception {
		return this.tPedidoAdicionalDao.findAllBloqueos(tPedidoAdicional, pagination);
	}

	public Long findAllBloqueosCont(TPedidoAdicional tPedidoAdicional) throws Exception {
		return this.tPedidoAdicionalDao.findAllBloqueosCont(tPedidoAdicional);
	}

	@Override
	public void insertNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		
		if (pedidoAdicional.getIdentificadorVegalsa()!=null){
			tPedidoAdicional.setIdentificadorVegalsa(pedidoAdicional.getIdentificadorVegalsa());
		}
		//MISUMI-453
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(pedidoAdicional.getCodArt());
		VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		tPedidoAdicional.setIdSesion(pedidoAdicional.getIdSession()+"_"+vDatosDiarioArtAux.getCodFpMadre());
		// tPedidoAdicional.setClasePedido(pedidoAdicional.getTipoPedido());
		tPedidoAdicional.setCodCentro(pedidoAdicional.getCodCentro());
		tPedidoAdicional.setCodArticulo(pedidoAdicional.getCodArt());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_NUEVO_REFERENCIA);
		if (pedidoAdicional.getTipoPedido().equals(1)) {
			tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
			tPedidoAdicional.setFecEntrega(df.format(pedidoAdicional.getFechaIni()));
		} else {
			tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
			tPedidoAdicional.setFechaInicio(df.format(pedidoAdicional.getFechaIni()));
			tPedidoAdicional.setFechaFin(df.format(pedidoAdicional.getFechaFin()));
			if (null != pedidoAdicional.getFecha2()){
				tPedidoAdicional.setFecha2(df.format(pedidoAdicional.getFecha2()));
			}
			if (null != pedidoAdicional.getFecha3()){
				tPedidoAdicional.setFecha3(df.format(pedidoAdicional.getFecha3()));
			}
			if (null != pedidoAdicional.getFecha4()){
				tPedidoAdicional.setFecha4(df.format(pedidoAdicional.getFecha4()));
			}
			if (pedidoAdicional.getFrescoPuro().booleanValue()) {
				tPedidoAdicional.setTipoPedido(Constantes.TIPO_PEDIDO_ENCARGO);
			} else {
				tPedidoAdicional.setTipoPedido(Constantes.TIPO_PEDIDO_PILADA);
			}
		}
		tPedidoAdicional.setEsPlanograma("N");
		tPedidoAdicional.setNoGestionaPbl("N");
		tPedidoAdicional.setUuid(pedidoAdicional.getUuid().toString());

		this.tPedidoAdicionalDao.insertNuevoReferencia(tPedidoAdicional);

	}

	@Override
	public void updateNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(pedidoAdicional.getIdSession()+"_"+pedidoAdicional.getCodArt());
		// tPedidoAdicional.setClasePedido(pedidoAdicional.getTipoPedido());
		tPedidoAdicional.setCodCentro(pedidoAdicional.getCodCentro());
		tPedidoAdicional.setCodArticulo(pedidoAdicional.getCodArt());
		if (pedidoAdicional.getTipoPedido().equals(1)) {
			tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_ENCARGO));
			tPedidoAdicional.setFecEntrega(df.format(pedidoAdicional.getFechaIni()));
		} else {
			tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE));
			tPedidoAdicional.setFechaInicio(df.format(pedidoAdicional.getFechaIni()));
			tPedidoAdicional.setFechaFin(df.format(pedidoAdicional.getFechaFin()));
			if (null != pedidoAdicional.getFecha2()){
				tPedidoAdicional.setFecha2(df.format(pedidoAdicional.getFecha2()));
			}
			if (null != pedidoAdicional.getFecha3()){
				tPedidoAdicional.setFecha3(df.format(pedidoAdicional.getFecha3()));
			}
			if (null != pedidoAdicional.getFecha4()){
				tPedidoAdicional.setFecha4(df.format(pedidoAdicional.getFecha4()));
			}
			if (pedidoAdicional.getFrescoPuro().booleanValue()) {
				tPedidoAdicional.setTipoPedido(Constantes.TIPO_PEDIDO_ENCARGO);
			} else {
				tPedidoAdicional.setTipoPedido(Constantes.TIPO_PEDIDO_PILADA);
			}
		}
		tPedidoAdicional.setUuid(pedidoAdicional.getUuid().toString());

		this.tPedidoAdicionalDao.updateNuevoReferencia(tPedidoAdicional);

	}

	@Override
	public int deleteNuevoReferencia(PedidoAdicionalCompleto pedidoAdicional) throws Exception {
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(pedidoAdicional.getCodArt());
		VDatosDiarioArt vDatosDiarioArtAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(pedidoAdicional.getIdSession()+"_"+vDatosDiarioArtAux.getCodFpMadre());
		// tPedidoAdicional.setClasePedido(pedidoAdicional.getTipoPedido());
		tPedidoAdicional.setCodCentro(pedidoAdicional.getCodCentro());
		tPedidoAdicional.setCodArticulo(pedidoAdicional.getCodArt());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_NUEVO_REFERENCIA);
		int correcto=0;
		if (null != pedidoAdicional.getUuid() && vDatosDiarioArtAux != null){
			tPedidoAdicional.setUuid(pedidoAdicional.getUuid().toString());
			this.tPedidoAdicionalDao.deleteArticulo(tPedidoAdicional);
		}else{
			correcto=1;
		}
		return correcto;
	}

	@Override
	public void deleteAllNuevoReferencia(TPedidoAdicional tPedidoAdicional) throws Exception{
		this.tPedidoAdicionalDao.deleteAllNuevoReferencia(tPedidoAdicional);
	}

	@Override
	public Long findMontajesAdicionalesVegalsa(PedidoAdicionalE pedidoAdicionalE, Boolean oferta) throws Exception {
		//si viene MCA = ‘S’ devuelva un 0
		if (Constantes.PEDIDO_ADICIONAL_MAC.equalsIgnoreCase(pedidoAdicionalE.getMca())){
			return Long.valueOf(0);
		}
		
		return this.tPedidoAdicionalDao.findMontajesAdicionalesVegalsa(pedidoAdicionalE, oferta);
	}

	@Override
	public void insertarPedidosVegalsa(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {
		this.tPedidoAdicionalDao.insertarPedidoAdicionalVegalsa(pedidoAdicionalE, session);
	}
	
	@Override
	public void deletePedidosVegalsa(Long idVegalsa) throws Exception{
		this.tPedidoAdicionalDao.deletePedidosVegalsa(idVegalsa);
	}

	@Override
	public String getPrimeraFechaVentaDisponible(Long codCentro, Long codArticulo) throws Exception {
		return this.tPedidoAdicionalDao.getPrimeraFechaVentaDisponible(codCentro, codArticulo);
	}
	
	@Override
	public MontajeVegalsa getPedidosVegalsa(Long codCentro,Long codArt) throws Exception {
		List<MontajeVegalsa> pedidosVegalsa =this.tPedidoAdicionalDao.findMontajeAdicionalCentro(codCentro, codArt);
		if(pedidosVegalsa.size() > 0){
			return pedidosVegalsa.get(0);
		}else{
			return null;
		}
		
	}

}
