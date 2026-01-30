package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.DevolucionDao;
import es.eroski.misumi.dao.iface.DevolucionDaoSIA;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.DevolucionCatalogoDescripcion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.model.DevolucionFinCampana;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionOrdenRecogida;
import es.eroski.misumi.model.DevolucionOrdenRetirada;
import es.eroski.misumi.model.DevolucionPlataforma;
import es.eroski.misumi.model.DevolucionTipos;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.DevolucionService;
import es.eroski.misumi.util.Constantes;


@Service(value = "devolucionService")
public class DevolucionServiceImpl implements DevolucionService {

	@Autowired
	private DevolucionDaoSIA devolucionDaoSIA;

	@Autowired
	private DevolucionDao devolucionDao;

	private static Logger logger = Logger.getLogger(DevolucionServiceImpl.class);
	
	private static final String mensajeNoFinalizar = "Para poder FINALIZAR hay que revisar TODAS la referencias con stock.";

	@Override
	public List<SeccionBean> findAllDevolucionFinCampanaSecciones(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las secciones. 

		List<SeccionBean> listaDevoluciones = new ArrayList<SeccionBean>();

		DevolucionFinCampana devFinCampana = new DevolucionFinCampana();
		devFinCampana.setCodCentro(codCentro);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionFinCampanaSecciones(devFinCampana);

		return listaDevoluciones;
	}

	@Override
	public List<DevolucionFinCampana> findAllDevolucionFinCampana(Long codCentro, Long codSeccion) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las referencias para mostrarlos. 

		List<DevolucionFinCampana> listaDevoluciones = new ArrayList<DevolucionFinCampana>();

		DevolucionFinCampana devFinCampana = new DevolucionFinCampana();
		devFinCampana.setCodCentro(codCentro);
		devFinCampana.setGrupo2(codSeccion);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionFinCampana(devFinCampana);

		return listaDevoluciones;
	}

	@Override
	public Long findAllDevolucionFinCampanaCount(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se calcula el total de referencias. 

		Long countDevoluciones = 0L;

		DevolucionFinCampana devFinCampana = new DevolucionFinCampana();
		devFinCampana.setCodCentro(codCentro);

		countDevoluciones = this.devolucionDaoSIA.findAllDevolucionFinCampanaCount(devFinCampana);

		return countDevoluciones;
	}

	@Override
	public List<SeccionBean> findAllDevolucionOrdenRetiradaSecciones(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las secciones. 

		List<SeccionBean> listaDevoluciones = new ArrayList<SeccionBean>();

		DevolucionOrdenRetirada devOrdenRetirada = new DevolucionOrdenRetirada();
		devOrdenRetirada.setCodCentro(codCentro);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRetiradaSecciones(devOrdenRetirada);

		return listaDevoluciones;
	}

	@Override
	public List<DevolucionOrdenRetirada> findAllDevolucionOrdenRetirada(Long codCentro, Long codSeccion) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las referencias para mostrarlos. 

		List<DevolucionOrdenRetirada> listaDevoluciones = new ArrayList<DevolucionOrdenRetirada>();

		DevolucionOrdenRetirada devOrdenRetirada = new DevolucionOrdenRetirada();
		devOrdenRetirada.setCodCentro(codCentro);
		devOrdenRetirada.setGrupo2(codSeccion);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRetirada(devOrdenRetirada);

		return listaDevoluciones;
	}

	@Override
	public Long findAllDevolucionOrdenRetiradaCount(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se calcula el total de referencias. 

		Long countDevoluciones = 0L;

		DevolucionOrdenRetirada devOrdenRetirada = new DevolucionOrdenRetirada();
		devOrdenRetirada.setCodCentro(codCentro);

		countDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRetiradaCount(devOrdenRetirada);

		return countDevoluciones;
	}

	@Override
	public List<SeccionBean> findAllDevolucionOrdenRecogidaSecciones(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las secciones. 

		List<SeccionBean> listaDevoluciones = new ArrayList<SeccionBean>();

		DevolucionOrdenRecogida devOrdenRecogida = new DevolucionOrdenRecogida();
		devOrdenRecogida.setCodCentro(codCentro);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRecogidaSecciones(devOrdenRecogida);

		return listaDevoluciones;
	}

	@Override
	public List<DevolucionOrdenRecogida> findAllDevolucionOrdenRecogida(Long codCentro, Long codSeccion) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se guardan en una lista todos los códigos de las referencias para mostrarlos. 

		List<DevolucionOrdenRecogida> listaDevoluciones = new ArrayList<DevolucionOrdenRecogida>();

		DevolucionOrdenRecogida devOrdenRecogida = new DevolucionOrdenRecogida();
		devOrdenRecogida.setCodCentro(codCentro);
		devOrdenRecogida.setGrupo2(codSeccion);

		listaDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRecogida(devOrdenRecogida);

		return listaDevoluciones;
	}

	@Override
	public Long findAllDevolucionOrdenRecogidaCount(Long codCentro) throws Exception {
		//En este método se tratan todos las devoluciones para un centro.
		//Se calcula el total de referencias. 

		Long countDevoluciones = 0L;

		DevolucionOrdenRecogida devOrdenRecogida = new DevolucionOrdenRecogida();
		devOrdenRecogida.setCodCentro(codCentro);

		countDevoluciones = this.devolucionDaoSIA.findAllDevolucionOrdenRecogidaCount(devOrdenRecogida);

		return countDevoluciones;
	}

	@Override
	public DevolucionCatalogoEstado cargarCabeceraDevoluciones(final Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		DevolucionCatalogoEstado devolucionCatalogoEstado = null;

		devolucionCatalogoEstado =  this.devolucionDaoSIA.cargarCabeceraDevoluciones(devolucion);
		return devolucionCatalogoEstado;
	}

	@Override
	public DevolucionCatalogoEstado cargarEstadoDevoluciones(Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDaoSIA.cargarEstadoDevoluciones(devolucion);
	}

	@Override
	public DevolucionCatalogoEstado cargarAllDevoluciones(Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDaoSIA.cargarAllDevoluciones(devolucion);
	}

	@Override
	public DevolucionCatalogoDescripcion cargarDenominacionesDevoluciones(Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDaoSIA.cargarDenominacionesDevoluciones(devolucion);
	}

	@Override
	public DevolucionAvisos cargarAvisosDevoluciones(Long codCentro) throws Exception {
		return this.devolucionDaoSIA.cargarAvisosDevoluciones(codCentro);
	}

	@Override
	public void insertAll(List<TDevolucionLinea> listaTDevolucionLinea) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.insertAll(listaTDevolucionLinea);
	}

	@Override
	public void deleteHistorico() throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.deleteHistorico();
	}

	@Override
	public void delete(TDevolucionLinea tDevolLinea) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.delete(tDevolLinea);

	}

	@Override
	public List<TDevolucionLinea> findLineasDevolucion(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor,String flagRefPermanentes) throws Exception {

		String filtroReferencia = "";
		// TODO Auto-generated method stub
		return this.findLineasDevolucion(session, devolucion, pagination, codArticulo, proveedor,flagRefPermanentes,filtroReferencia);
	}

	@Override
	public List<TDevolucionLinea> findLineasDevolucion(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor,String flagRefPermanentes, String filtroReferencia) throws Exception {
		// TODO Auto-generated method stub
		int bultoSeleccionado = devolucion.getBultoSeleccionado();
		
		if (bultoSeleccionado > 0){
			return this.devolucionDao.findLineasDevBulto(session, devolucion, pagination, codArticulo, proveedor,flagRefPermanentes,filtroReferencia);
		}else{
			return this.devolucionDao.findLineasDevolucion(session, devolucion, pagination, codArticulo, proveedor,flagRefPermanentes,filtroReferencia);
		}

	}

	@Override
	public List<TDevolucionLinea> findLineasDevolucionPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findLineasDevolucionPDF(session, devolucion);
	}
	
	@Override
	public List<TDevolucionLinea> findLineasDevolucionAgrupadasPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findLineasDevolucionAgrupadasPDF(session, devolucion);
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresPorProveedorPDF(session, devolucion);
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresPorProveedorPDF(session, devolucion, contarReferenciasSinRetirada);
	}

	@Override
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresBultoPorProveedorPDF(session, devolucion);
	}

	@Override
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresBultoPorProveedorPDF(session, devolucion, contarReferenciasSinRetirada);
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorBultoPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresPorProveedorBultoPDF(session, devolucion);
	}

	@Override
	public Long findContadoresReferenciasSinRetiradaPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresReferenciasSinRetiradaPDF(session, devolucion);
	}

	@Override
	public List<OptionSelectBean> obtenerProveedoresLineasDevolucion(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.obtenerProveedoresLineasDevolucion(session,devolucion);
	}

	@Override
	public void updateTablaSesionLineaDevolucion(String session, Devolucion devolucion, boolean isSaveData) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.updateTablaSesionLineaDevolucion(session,devolucion, isSaveData);
	}

	@Override
	public List<DevolucionLinea> findLineasDevolucionEditadas(String session, Long codError) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findLineasDevolucionEditadas(session, codError);
	}

	@Override
	public DevolucionCatalogoEstado actualizarDevolucion(Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDaoSIA.actualizarDevolucion(devolucion);
	}

	@Override
	public DevolucionEmail finalizarDevolucion(Devolucion devolucion, String flgRellenarHuecos, String dispositivo) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDaoSIA.finalizarDevolucion(devolucion, flgRellenarHuecos,dispositivo);
	}

	@Override
	public Devolucion duplicarDevolucion(Devolucion devolucion) throws Exception {
		return this.devolucionDaoSIA.duplicarDevolucion(devolucion);
	} 

	@Override
	public Devolucion eliminarDevolucion(Devolucion devolucion) throws Exception {
		return this.devolucionDaoSIA.eliminarDevolucion(devolucion);
	} 

	@Override
	public void resetDevolEstados(String session) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.resetDevolEstados(session);
	}

	@Override
	public int firstLineaDevolucionStockOrBultoNull(Integer posicionInicial, List<TDevolucionLinea> listaTDevolucionLinea) throws Exception {
		int tDevLinea = 0;
		int inicio = posicionInicial != null ? posicionInicial.intValue() : 0;
		if (listaTDevolucionLinea != null){
			int totalLineas = listaTDevolucionLinea.size();
			tDevLinea = totalLineas - 1;
			for (int i = inicio; i < totalLineas; i++){
				TDevolucionLinea linea = listaTDevolucionLinea.get(i);
				if (linea.getStockDevuelto() == null || linea.getBulto() == null){
					tDevLinea = i;
					break;
				}
			}
		}
		return tDevLinea;
	}

	@Override
	public int findLineasDevolucionCompletadas(List<TDevolucionLinea> listaTDevolucionLinea) throws Exception {
		int lineasCompletadas = 0;
		if (listaTDevolucionLinea != null){
			int totalLineas = listaTDevolucionLinea.size();
			for (int i = 0; i < totalLineas; i++){
				TDevolucionLinea linea = listaTDevolucionLinea.get(i);
				if (linea.getStockDevuelto() != null && linea.getBulto() != null){
					lineasCompletadas++;
				}
			}
		}
		return lineasCompletadas;
	}

	@Override
	public void updateConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.updateConCeroTablaSesionLineaDevolucion(session, devolucion);
	}

	@Override
	public boolean existenRefPermanentes(String session, Devolucion devolucion) throws Exception {
		return this.devolucionDao.existenRefPermanentes(session, devolucion);
	}

	@Override
	public String existeStockDevueltoConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion)
			throws Exception {
		// TODO Auto-generated method stub
		String existeStockDevueltoConCeroTablaSesionLineaDevolucion = Constantes.SIN_DATOS_BULTO;
		if(this.devolucionDao.existeStockDevueltoConCeroTablaSesionLineaDevolucion(session, devolucion)){
			existeStockDevueltoConCeroTablaSesionLineaDevolucion = Constantes.CON_DATOS_BULTO;
		}
		return existeStockDevueltoConCeroTablaSesionLineaDevolucion;
	}

//	@Override
//	public String esProveedorSinFinalizar(String session, Long devolucion, String proveedor) throws Exception{
//		return (devolucionDao.esProveedorSinFinalizar(session, devolucion, proveedor)?Constantes.NO:Constantes.SI);
//	}

	@Override
	public String esProveedorSinFinalizar(String session, Long devolucion, String proveedor, String origen) throws Exception{
		return (devolucionDao.esProveedorSinFinalizar(session, devolucion, proveedor, origen)?Constantes.NO:Constantes.SI);
	}

	@Override 
	public String devolucionConStockVacio(Devolucion devolucion) throws Exception{
		return this.devolucionDaoSIA.devolucionConStockVacio(devolucion);
	}

	@Override
	public String existeReferenciaEnDevolucion(String session,DevolucionLinea devLin) throws Exception {
		// TODO Auto-generated method stub
		String existeReferenciaEnDevolucion = Constantes.NO;
		if(this.devolucionDao.existeReferenciaEnDevolucion(session,devLin)){
			existeReferenciaEnDevolucion = Constantes.SI;
		}
		return existeReferenciaEnDevolucion;
	}

	@Override
	public void deleteLineasDevolucion(String session, Devolucion devolucionAEliminar) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionDao.deleteLineasDevolucion(session, devolucionAEliminar);
	}

	@Override
	public List<Long> getRefByPattern(String term, String session ,Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.getRefByPattern(term,session,devolucion);
	}

	public DevolucionTipos obtenerDatosCombo(Centro centro) throws Exception{
		return this.devolucionDaoSIA.obtenerDatosCombo(centro);
	}

	public DevolucionPlataforma obtenerPlataformasDevolucionCreadaPorCentro(DevolucionPlataforma devolucionPlataforma) throws Exception{
		return this.devolucionDaoSIA.obtenerPlataformasDevolucionCreadaPorCentro(devolucionPlataforma);
	}

	public DevolucionCatalogoEstado altaDevolucionCreadaPorCentro(Devolucion devolucion) throws Exception{
		return this.devolucionDaoSIA.altaDevolucionCreadaPorCentro(devolucion);
	}

	public List<DevolucionLinea> obtenerCantidadADevolver(Devolucion devolucion) throws Exception{
		return this.devolucionDaoSIA.obtenerCantidadADevolver(devolucion);
	}
	
	@Override 
	public Double getSumaCosteFinal(Devolucion devolucion, String sessionId) throws Exception{
		return this.devolucionDao.getSumaCosteFinal(devolucion, sessionId);
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorBultoPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		return this.devolucionDao.findContadoresPorBultoPDF(session, devolucion);
	}
	
	@Override
	public String hayRefsPdtes(String session){
		try{
			return (devolucionDao.hayRefsPdtes(session)?Constantes.SI:Constantes.NO);
		} catch (Exception e){
			logger.error("ERROR DevolucionServicesImpl.esCentroRevisado",e);
			return Constantes.NO;
		}
	}
	
	@Override
	public String puedeFinalizar(Long centro, String localizador) throws Exception{
		return Constantes.FLG_SIA_SI.equals(this.devolucionDaoSIA.puedeFinalizar(centro, localizador))?Constantes.FLG_SIA_SI:mensajeNoFinalizar;
	}
}
