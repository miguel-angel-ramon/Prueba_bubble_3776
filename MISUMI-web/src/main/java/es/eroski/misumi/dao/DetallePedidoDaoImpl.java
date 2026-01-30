package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DetalladoSIADao;
import es.eroski.misumi.dao.iface.DetallePedidoDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetalladoSIALista;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoLista;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.PropuestaDetalladoVegalsa;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VRotacionRefEuros;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ParamCentrosOpcService;
import es.eroski.misumi.service.iface.VReferenciaActiva2Service;
import es.eroski.misumi.service.iface.VRotacionRefEurosService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class DetallePedidoDaoImpl implements DetallePedidoDao {

	@Autowired
	private VReferenciaActiva2Service vReferenciaActiva2Service;
	@Autowired
	private VRotacionRefEurosService vRotacionRefEurosService;
	@Autowired
	private ParamCentrosOpcService paramCentrosOpcService;

	private JdbcTemplate jdbcTemplate;

	private static Logger logger = Logger.getLogger(DetallePedidoDaoImpl.class);

	private RowMapper<DetallePedido> rwDetPedidoMap= new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DetallePedido det = new DetallePedido(resultSet.getLong("CENTRO"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), resultSet.getLong("REFERENCIA"), resultSet.getString("DESCRIPCION"),
					resultSet.getDouble("STOCK"), resultSet.getDouble("ENCURSO1"), resultSet.getDouble("ENCURSO2"),
					resultSet.getDouble("UNIDADESCAJA"), resultSet.getLong("CAJASPEDIDAS"),
					resultSet.getLong("PROPUESTA"), resultSet.getLong("CANTIDAD"), resultSet.getLong("CANTIDAD"),
					resultSet.getLong("CANTIDAD"), resultSet.getString("TIPODETALLADO"),
					resultSet.getString("ESTADOGRID"), resultSet.getString("ESTADO"), null,
					resultSet.getString("CODIGOERROR"), resultSet.getLong("CANTIDADANT"),
					resultSet.getString("TEMPORADA"), resultSet.getLong("NUM_ORDEN"),
					resultSet.getString("MODELO_PROVEEDOR"), resultSet.getString("DESCR_TALLA"),
					resultSet.getString("DESCR_COLOR"), resultSet.getDouble("FACING"),
					resultSet.getString("TIPO_APROV"), resultSet.getString("TIPO"), resultSet.getLong("NIVEL_LOTE"),
					resultSet.getString("ID"), resultSet.getString("TIPO_UFP"), resultSet.getDouble("UFP"),
					resultSet.getLong("REFERENCIA_EROSKI"), resultSet.getString("DESCRIPCION_EROSKI"),
					resultSet.getString("FLG_OFERTA"), resultSet.getString("OFERTA"));
			det.setNextDayPedido(resultSet.getString("SEG_DIA_PED"));
			det.setFlgSIA(resultSet.getString("FLG_SIA"));
			det.setFechaEntrega(resultSet.getDate("FEC_ENTREGA"));
			det.setUnidFlEmpuje(
					resultSet.getString("UNID_FL_EMPUJE") != null ? resultSet.getLong("UNID_FL_EMPUJE") : null);
			det.setCodNecesidad(
					resultSet.getString("COD_NECESIDAD") != null ? resultSet.getLong("COD_NECESIDAD") : null);
			det.setDexenx(resultSet.getDouble("DEXENX"));
			det.setDexenxEntero(resultSet.getDouble("DEXENXENTERO"));
			// GESTION DE EUROS
			det.setDenomArea(resultSet.getString("DENOM_AREA"));
			det.setDenomSeccion(resultSet.getString("DENOM_SECCION"));
			det.setDenomCategoria(resultSet.getString("DENOM_CATEGORIA"));
			det.setDenomSubcategoria(resultSet.getString("DENOM_SUBCATEGORIA"));
			det.setDenomSegmento(resultSet.getString("DENOM_SEGMENTO"));
			det.setPrecioCostoArticulo(resultSet.getDouble("PRECIO_COSTO_ARTICULO"));
			det.setPrecioCostoInicial(resultSet.getDouble("PRECIO_COSTO_INICIAL"));
			det.setPrecioCostoFinal(resultSet.getDouble("PRECIO_COSTO_FINAL"));
			det.setRotacion(resultSet.getString("ROTACION"));
			det.setDiferencia(resultSet.getLong("DIFERENCIA"));
			det.setRefCumple(resultSet.getString("REF_CUMPLE"));
			det.setAviso(resultSet.getString("AVISO"));

			int codMapa = resultSet.getInt("COD_MAPA");
			det.setCodMapa(codMapa == 0 ? null : codMapa);
			det.setMotivoPedido(resultSet.getString("MOTIVO_PEDIDO"));
			
			det.setCajasCortadas(resultSet.getLong("cajas_cortadas"));
			det.setIncPrevisionVenta(resultSet.getString("inc_prevision_venta"));
			det.setSmEstatico(resultSet.getLong("sm_estatico"));
			
			return det;
		}

	};

	private RowMapper<GestionEurosRefs> rwGestionEurosMap= new RowMapper<GestionEurosRefs>() {
		public GestionEurosRefs mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new GestionEurosRefs(resultSet.getLong("REFERENCIA"), resultSet.getLong("COD_NECESIDAD"),resultSet.getDouble("UNIDADESCAJA"), resultSet.getDouble("PRECIO_COSTO_ARTICULO"), resultSet.getDouble("PRECIO_COSTO_FINAL"), resultSet.getLong("CANTIDAD"), null, null,null);}};

	private RowMapper<Cronometro> rwCronoMap= new RowMapper<Cronometro>() {
		public Cronometro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Cronometro(resultSet.getString("HORA_LIMITE"),resultSet.getString("NUM_HORAS_LIMITE"),resultSet.getString("CUENTA"));}};

	private RowMapper<DetallePedido> rwDetPedidoTextilN2= new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DetallePedido(resultSet.getLong("COD_CENTRO"),
					resultSet.getLong("COD_ART"), resultSet.getString("DESCRIP_ART"),
					resultSet.getLong("GRUPO1"),resultSet.getLong("GRUPO2"),
					resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
					resultSet.getLong("GRUPO5"), resultSet.getDouble("STOCK"),
					resultSet.getString("TIPO_APROV"), resultSet.getDouble("UNI_CAJA_SERV"),
					resultSet.getDouble("FACING_MA1"),  resultSet.getString("DESCR_COLOR"),
					resultSet.getString("COD_TALLA"), resultSet.getString("TEMPORADA"),
					resultSet.getLong("NUM_ORDEN"),resultSet.getString("MODELO_PROVEEDOR"));}

	};

	private RowMapper<DetallePedido> rwDetPedidoFullMap = new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DetallePedido det =  new DetallePedido(resultSet.getLong("CENTRO"),resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"),
					resultSet.getLong("SUBCATEGORIA"),resultSet.getLong("SEGMENTO"),
					resultSet.getLong("REFERENCIA"), resultSet.getString("DESCRIPCION"),
					resultSet.getDouble("STOCK"), resultSet.getDouble("ENCURSO1"),
					resultSet.getDouble("ENCURSO2"), resultSet.getDouble("UNIDADESCAJA"),
					resultSet.getLong("CAJASPEDIDAS"),  resultSet.getLong("PROPUESTA"),
					resultSet.getLong("CANTIDAD"), resultSet.getLong("CANTIDAD"), resultSet.getLong("CANTIDAD"),
					resultSet.getString("TIPODETALLADO"),
					resultSet.getString("ESTADOGRID"),
					resultSet.getString("ESTADO"),  resultSet.getString("HORALIMITE"),
					resultSet.getString("CODIGOERROR"), null, 
					resultSet.getString("TEMPORADA"), resultSet.getLong("NUM_ORDEN"),
					resultSet.getString("MODELO_PROVEEDOR"),resultSet.getString("DESCR_TALLA"),
					resultSet.getString("DESCR_COLOR"),resultSet.getDouble("FACING"),
					resultSet.getString("TIPO_APROV"),resultSet.getString("TIPO"),
					resultSet.getLong("NIVEL_LOTE"),resultSet.getString("ID"),
					resultSet.getString("TIPO_UFP"),resultSet.getDouble("UFP"));det.setNextDayPedido(resultSet.getString("SEG_DIA_PED"));return det;}

	};

	private RowMapper<VAgruComerRef> rwAgruComerRefMapSeccion = new RowMapper<VAgruComerRef>() {
		public VAgruComerRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			VAgruComerRef vAgruComerRef = new VAgruComerRef(null,resultSet.getLong("AREA"), 
					resultSet.getLong("SECCION"), null,
					null,null,null);vAgruComerRef.setFlgPropuesta(resultSet.getString("FLG_PROPUESTA"));return vAgruComerRef;}

	};

	private RowMapper<VAgruComerRef> rwAgruComerRefMapCategoria = new RowMapper<VAgruComerRef>() {
		public VAgruComerRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			VAgruComerRef vAgruComerRef = new VAgruComerRef(null,resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), 
					null,null,null);vAgruComerRef.setFlgPropuesta(resultSet.getString("FLG_PROPUESTA"));return vAgruComerRef;}

	};

	private RowMapper<DetallePedido> rwDetPedidoTextil = new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DetallePedido(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
					resultSet.getString("TEMPORADA"), resultSet.getLong("NUM_ORDEN"),
					resultSet.getString("MODELO_PROVEEDOR"),resultSet.getString("DESCR_TALLA"),
					resultSet.getString("DESCR_COLOR"),resultSet.getDouble("STOCK_MIN_COMER_LINEAL"));}

	};

	// Tipo Aprovisionamiento
	private RowMapper<VSurtidoTienda> rwDetPedidoAprovisionamiento = new RowMapper<VSurtidoTienda>() {
		public VSurtidoTienda mapRow(ResultSet resultSet,int rowNum)throws SQLException{VSurtidoTienda vSurtidoTienda=new VSurtidoTienda();vSurtidoTienda.setTipoAprov(resultSet.getString("TIPO_APROV"));vSurtidoTienda.setUfp(resultSet.getFloat("UNI_FORMA_PED"));vSurtidoTienda.setMarcaMaestroCentro(resultSet.getString("MARCA_MAESTRO_CENTRO"));vSurtidoTienda.setFechaGen(resultSet.getDate("FECHA_GEN"));return vSurtidoTienda;}

	};

	private RowMapper<GenericExcelVO> rwDetPedidoExcelMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new GenericExcelVO(Utilidades.obtenerValorExcelString(resultSet, 1),Utilidades.obtenerValorExcelString(resultSet,2),
					Utilidades.obtenerValorExcelString(resultSet,3),Utilidades.obtenerValorExcelString(resultSet,4),Utilidades.obtenerValorExcelString(resultSet,5),Utilidades.obtenerValorExcelString(resultSet,6)
					,Utilidades.obtenerValorExcelString(resultSet,7),Utilidades.obtenerValorExcelString(resultSet,8),Utilidades.obtenerValorExcelString(resultSet,9),Utilidades.obtenerValorExcelString(resultSet,10)
					,Utilidades.obtenerValorExcelString(resultSet,11),Utilidades.obtenerValorExcelString(resultSet,12),Utilidades.obtenerValorExcelString(resultSet,13),Utilidades.obtenerValorExcelString(resultSet,14)
					,Utilidades.obtenerValorExcelString(resultSet,15),Utilidades.obtenerValorExcelString(resultSet,16),Utilidades.obtenerValorExcelString(resultSet,17),Utilidades.obtenerValorExcelString(resultSet,18)
					,Utilidades.obtenerValorExcelString(resultSet,19),Utilidades.obtenerValorExcelString(resultSet,20),Utilidades.obtenerValorExcelString(resultSet,21),Utilidades.obtenerValorExcelString(resultSet,22)
					,Utilidades.obtenerValorExcelString(resultSet,23),Utilidades.obtenerValorExcelString(resultSet,24),Utilidades.obtenerValorExcelString(resultSet,25),Utilidades.obtenerValorExcelString(resultSet,26)
					,Utilidades.obtenerValorExcelString(resultSet,27),Utilidades.obtenerValorExcelString(resultSet,28),Utilidades.obtenerValorExcelString(resultSet,29),Utilidades.obtenerValorExcelString(resultSet,30)
					,Utilidades.obtenerValorExcelString(resultSet,31),Utilidades.obtenerValorExcelString(resultSet,32),Utilidades.obtenerValorExcelString(resultSet,33),Utilidades.obtenerValorExcelString(resultSet,34)
					,Utilidades.obtenerValorExcelString(resultSet,35),Utilidades.obtenerValorExcelString(resultSet,36),Utilidades.obtenerValorExcelString(resultSet,37),Utilidades.obtenerValorExcelString(resultSet,38)
					,Utilidades.obtenerValorExcelString(resultSet,39),Utilidades.obtenerValorExcelString(resultSet,40),Utilidades.obtenerValorExcelString(resultSet,41),Utilidades.obtenerValorExcelString(resultSet,42)
					,Utilidades.obtenerValorExcelString(resultSet,43),Utilidades.obtenerValorExcel(resultSet,44),Utilidades.obtenerValorExcel(resultSet,45),Utilidades.obtenerValorExcel(resultSet,46),
					Utilidades.obtenerValorExcel(resultSet,47));}

	};

	private RowMapper<String> rwStringMap = new RowMapper<String>() {
		public String mapRow(ResultSet resultSet,int i)throws SQLException{return resultSet.getString(1);}};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	private DetalladoSIADao detalladoSIADao;

	@Override
	public List<DetallePedido> findDetallePedido(Centro centro, HttpSession session) throws Exception {

		List<DetallePedido> resultado = null;

		// A Partir de la Petición MISUMI-43 solo se llama al procedimiento de
		// SIA, este cargara los datos tanto de SIA como de PBL.
		// Se diferenciaran unos de otro por el flgSIA.

		// Se llama tanto a PBL(WS) como a SIA(procedimiento)
		// resultado = this.findDetallePedidoPBL(centro, session);
		// resultado.addAll(this.findDetallePedidoSIA(centro));

		resultado = this.findDetallePedidoSIA(centro);

		return resultado;
	}

	@Override
	public List<DetallePedido> findDetallePedidoSIA(Centro centro) throws Exception {

		DetalladoSIA detalladoSIA = new DetalladoSIA();
		detalladoSIA.setCodLoc(centro.getCodCentro());

		// Llamamos al DAO que llama al procedimient para obtener el resultado.
		DetalladoSIALista resultProc = this.detalladoSIADao.consultaDetallado(detalladoSIA);

		List<DetallePedido> resultado = new ArrayList<DetallePedido>();
		if (resultProc != null) {
			resultado = tratarDatosObtenerProc(resultProc);
		}

		return resultado;
	}

	private List<DetallePedido> tratarDatosObtenerProc(DetalladoSIALista resultProc) throws Exception {

		// Transformación de datos para estructura de DetallePedido
		List<DetallePedido> resultado = new ArrayList<DetallePedido>();
		List<DetalladoSIA> detalladoSIAListaAux = new ArrayList<DetalladoSIA>();
		DetallePedido filaResultado = new DetallePedido();

		List<DetallePedido> resultadoTextil = new ArrayList<DetallePedido>();
		Integer indexOf = null;

		if (null != resultProc.getDatos()) {
			detalladoSIAListaAux = resultProc.getDatos();
		}

		// String listaArticulostTextil = "(";
		String listaArticulostTextil = "";

		// Nos recorremos la lista principal
		Long codCentro = null;
		String opcionesCentro = null;
		for (int i = 0; i < detalladoSIAListaAux.size(); i++) {
			filaResultado = new DetallePedido();

			// Indicamos que es una referencia de SIA

			if (detalladoSIAListaAux.get(i).getFlgSIA() != null) {
				if (detalladoSIAListaAux.get(i).getFlgSIA().toString().equals("S")) {
					filaResultado.setFlgSIA(detalladoSIAListaAux.get(i).getFlgSIA().toString());
				} else {
					filaResultado.setFlgSIA(null);
				}
			}
			filaResultado.setFlgSIA((detalladoSIAListaAux.get(i).getFlgSIA() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getFlgSIA().toString())))
							? detalladoSIAListaAux.get(i).getFlgSIA().toString() : null);

			filaResultado.setCodCentro((detalladoSIAListaAux.get(i).getCodLoc() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodLoc().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodLoc().toString()) : null);
			filaResultado.setCodArticulo((detalladoSIAListaAux.get(i).getCodArticulo() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodArticulo().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodArticulo().toString()) : null);
			filaResultado.setDescriptionArt((detalladoSIAListaAux.get(i).getDenomRef() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomRef().toString())))
							? detalladoSIAListaAux.get(i).getDenomRef().toString() : null);
			filaResultado.setGrupo1((detalladoSIAListaAux.get(i).getCodN1Ref() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodN1Ref().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodN1Ref().toString()) : null);
			filaResultado.setGrupo2((detalladoSIAListaAux.get(i).getCodN2Ref() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodN2Ref().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodN2Ref().toString()) : null);
			filaResultado.setGrupo3((detalladoSIAListaAux.get(i).getCodN3Ref() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodN3Ref().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodN3Ref().toString()) : null);
			filaResultado.setGrupo4((detalladoSIAListaAux.get(i).getCodN4Ref() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodN4Ref().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodN4Ref().toString()) : null);
			filaResultado.setGrupo5((detalladoSIAListaAux.get(i).getCodN5Ref() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodN5Ref().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodN5Ref().toString()) : null);
			filaResultado.setStock((detalladoSIAListaAux.get(i).getStockTienda() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getStockTienda().toString())))
							? new Double(detalladoSIAListaAux.get(i).getStockTienda().toString()) : null);
			filaResultado.setEnCurso1((detalladoSIAListaAux.get(i).getPendienteTienda() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getPendienteTienda().toString())))
							? new Double(detalladoSIAListaAux.get(i).getPendienteTienda().toString()) : null);
			filaResultado.setEnCurso2((detalladoSIAListaAux.get(i).getPendienteTiendaManana() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getPendienteTiendaManana().toString())))
							? new Double(detalladoSIAListaAux.get(i).getPendienteTiendaManana().toString()) : null);
			filaResultado.setUnidadesCaja((detalladoSIAListaAux.get(i).getUnidCaja() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUnidCaja().toString())))
							? new Double(detalladoSIAListaAux.get(i).getUnidCaja().toString()) : null);
			filaResultado.setCajasPedidas((detalladoSIAListaAux.get(i).getUnidEncargoFl() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUnidEncargoFl().toString())))
							? new Long(detalladoSIAListaAux.get(i).getUnidEncargoFl().toString()) : null);
			filaResultado.setHoraLimite((detalladoSIAListaAux.get(i).getHoraLimite() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getHoraLimite().toString())))
							? detalladoSIAListaAux.get(i).getHoraLimite().toString() : null);
			filaResultado.setPropuesta((detalladoSIAListaAux.get(i).getUnidPropuestasFlOrigen() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUnidPropuestasFlOrigen().toString())))
							? new Long(detalladoSIAListaAux.get(i).getUnidPropuestasFlOrigen().toString()) : null);
			// filaResultado.setCantidad((detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt()
			// != null &&
			// !("".equals(detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt().toString())))?new
			// Long(detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt().toString()):null);

			if (detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt().toString()))) {

				filaResultado.setCantidad(detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt());

			} else {
				filaResultado.setCantidad(detalladoSIAListaAux.get(i).getUnidPropuestasFlModif().longValue());
			}

			filaResultado.setTipoDetallado((detalladoSIAListaAux.get(i).getTipoDetallado() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getTipoDetallado().toString())))
							? detalladoSIAListaAux.get(i).getTipoDetallado().toString() : null);
			filaResultado.setEstadoPedido((detalladoSIAListaAux.get(i).getEstado() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getEstado().toString())))
							? detalladoSIAListaAux.get(i).getEstado().toString() : null);

			filaResultado.setFechaEntrega((detalladoSIAListaAux.get(i).getFechaEntrega() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getFechaEntrega().toString())))
							? detalladoSIAListaAux.get(i).getFechaEntrega() : null);
			filaResultado.setUnidFlEmpuje((detalladoSIAListaAux.get(i).getUnidFlEmpuje() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUnidFlEmpuje().toString())))
							? new Long(detalladoSIAListaAux.get(i).getUnidFlEmpuje().toString()) : null);
			filaResultado.setCodNecesidad((detalladoSIAListaAux.get(i).getCodNecesidad() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getCodNecesidad().toString())))
							? new Long(detalladoSIAListaAux.get(i).getCodNecesidad().toString()) : null);
			filaResultado.setFlgOferta((detalladoSIAListaAux.get(i).getFlgOferta() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getFlgOferta().toString())))
							? detalladoSIAListaAux.get(i).getFlgOferta().toString() : null);
			filaResultado.setOferta((detalladoSIAListaAux.get(i).getOferta() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getOferta().toString())))
							? detalladoSIAListaAux.get(i).getOferta().toString() : null);
			filaResultado.setTipoOferta((detalladoSIAListaAux.get(i).getTipoOferta() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getTipoOferta().toString())))
							? new Long(detalladoSIAListaAux.get(i).getTipoOferta().toString()) : null);

			filaResultado.setFlgPropuesta((detalladoSIAListaAux.get(i).getFlgPropuesta() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getFlgPropuesta().toString())))
							? detalladoSIAListaAux.get(i).getFlgPropuesta().toString() : null);

			filaResultado.setConverArt((detalladoSIAListaAux.get(i).getConverArt() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getConverArt().toString())))
							? detalladoSIAListaAux.get(i).getConverArt().toString() : null);

			if (detalladoSIAListaAux.get(i).getFechaPedidoProx() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(detalladoSIAListaAux.get(i).getFechaPedidoProx());

				String nextFechaStr = null;
				StringBuffer nextFechaBuffer = new StringBuffer();
				nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.DAY_OF_MONTH) + "", '0', 2));
				nextFechaBuffer.append(Utilidades.rellenarIzquierda(cal.get(Calendar.MONTH) + 1 + "", '0', 2));
				nextFechaBuffer.append(cal.get(Calendar.YEAR) + "");

				nextFechaStr = nextFechaBuffer.toString();

				filaResultado.setNextDayPedido(nextFechaStr);

			} else {
				filaResultado.setNextDayPedido(this.vReferenciaActiva2Service
						.getNextFechaPedido(filaResultado.getCodCentro(), filaResultado.getCodArticulo()));
			}

			filaResultado.setTipoAprovisionamiento((detalladoSIAListaAux.get(i).getTipoAprov() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getTipoAprov().toString())))
							? detalladoSIAListaAux.get(i).getTipoAprov().toString() : null);
			filaResultado.setUfp((detalladoSIAListaAux.get(i).getUfp() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getUfp().toString())))
							? new Double(detalladoSIAListaAux.get(i).getUfp().toString()) : null);
			filaResultado.setTipo((detalladoSIAListaAux.get(i).getTipo() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getTipo().toString())))
							? detalladoSIAListaAux.get(i).getTipo().toString() : null);

			/*
			 * VSurtidoTienda vSurtidoTienda =
			 * findDatosTipoAprovisionamiento(filaResultado.getCodCentro(),
			 * filaResultado.getCodArticulo());
			 * 
			 * filaResultado.setTipoAprovisionamiento(vSurtidoTienda.
			 * getTipoAprov()); filaResultado.setUfp(new
			 * Double(vSurtidoTienda.getUfp()));
			 * 
			 * //TODO Control referencias nuevas if (null !=
			 * vSurtidoTienda.getFechaGen() && null !=
			 * vSurtidoTienda.getMarcaMaestroCentro()){ Calendar cal =
			 * Calendar.getInstance();
			 * cal.setTime(vSurtidoTienda.getFechaGen()); Integer intervalo = 3;
			 * if (filaResultado.getTipoAprovisionamiento() != null) { if
			 * (filaResultado.getTipoAprovisionamiento().equals("G")){ intervalo
			 * = 6; } }
			 * 
			 * cal.add(Calendar.DAY_OF_MONTH, intervalo); if
			 * (vSurtidoTienda.getMarcaMaestroCentro().equals("S") &&
			 * cal.getTime().after(new Date())){ String MMC =
			 * this.comprobarReferenciaNueva(filaResultado.getCodCentro(),
			 * filaResultado.getCodArticulo(), vSurtidoTienda.getFechaGen()); if
			 * (null != MMC && MMC.equalsIgnoreCase("N")){ //logger.info(
			 * "Hay Franquicia"); filaResultado.setTipo("N"); } } }
			 */

			if (filaResultado.getUfp() != null) {
				if ((filaResultado.getGrupo2().intValue() == Constantes.SECCION_CARNICERIA
						|| (filaResultado.getGrupo2().intValue() == Constantes.SECCION_CHARCUTERIA
								&& filaResultado.getGrupo3() == Constantes.CATEGORIA_QUESOS_RECIEN_CORTADOS)
						|| (filaResultado.getGrupo2().intValue() == Constantes.SECCION_PESCADERIA
								&& filaResultado.getGrupo3() == Constantes.CATEGORIA_ESPECIALIDADES))
						&& !filaResultado.getUfp().equals(new Double(0))) {
					filaResultado.setTipoUFP("U");
				}

			}

			if ((filaResultado.getTipoUFP() != null) && (filaResultado.getTipoUFP().equals("U"))) {

				if ((filaResultado.getUfp() != null) && (filaResultado.getUnidadesCaja() != null)) {

					Double dexenx = Math.ceil(filaResultado.getUfp() / filaResultado.getUnidadesCaja());
					filaResultado.setDexenx(dexenx); // Para pintarlo en el Grid

					// Así si se hace P.ej 5/2 devuelve 2 y no 2.5
					// Double dexenxEntero =
					// Math.ceil(filaResultado.getUfp()/filaResultado.getUnidadesCaja());
					// filaResultado.setDexenxEntero(dexenxEntero); //Para
					// calcular los multiplos
				}

			}

			filaResultado.setNivelLote((detalladoSIAListaAux.get(i).getNivelLote() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getNivelLote().toString())))
							? new Long(detalladoSIAListaAux.get(i).getNivelLote().toString()) : new Long(0));

			filaResultado.setTemporada((detalladoSIAListaAux.get(i).getTemporada() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getTemporada().toString())))
							? detalladoSIAListaAux.get(i).getTemporada().toString() : null);
			filaResultado.setNumOrden((detalladoSIAListaAux.get(i).getNumOrden() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getNumOrden().toString())))
							? new Long(detalladoSIAListaAux.get(i).getNumOrden().toString()) : null);
			filaResultado.setModeloProveedor((detalladoSIAListaAux.get(i).getModeloProveedor() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getModeloProveedor().toString())))
							? detalladoSIAListaAux.get(i).getModeloProveedor().toString() : null);
			filaResultado.setDescrTalla((detalladoSIAListaAux.get(i).getDescrTalla() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDescrTalla().toString())))
							? detalladoSIAListaAux.get(i).getDescrTalla().toString() : null);
			filaResultado.setDescrColor((detalladoSIAListaAux.get(i).getDescrColor() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDescrColor().toString())))
							? detalladoSIAListaAux.get(i).getDescrColor().toString() : null);
			filaResultado.setFacing((detalladoSIAListaAux.get(i).getFacing() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getFacing().toString())))
							? new Double(detalladoSIAListaAux.get(i).getFacing().toString()) : null);

			// GESTION DE EUROS
			filaResultado.setDenomArea((detalladoSIAListaAux.get(i).getDenomArea() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomArea().toString())))
							? detalladoSIAListaAux.get(i).getDenomArea().toString() : null);
			filaResultado.setDenomSeccion((detalladoSIAListaAux.get(i).getDenomSeccion() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomSeccion().toString())))
							? detalladoSIAListaAux.get(i).getDenomSeccion().toString() : null);
			filaResultado.setDenomCategoria((detalladoSIAListaAux.get(i).getDenomCategoria() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomCategoria().toString())))
							? detalladoSIAListaAux.get(i).getDenomCategoria().toString() : null);
			filaResultado.setDenomSubcategoria((detalladoSIAListaAux.get(i).getDenomSubcategoria() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomSubcategoria().toString())))
							? detalladoSIAListaAux.get(i).getDenomSubcategoria().toString() : null);
			filaResultado.setDenomSegmento((detalladoSIAListaAux.get(i).getDenomSegmento() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getDenomSegmento().toString())))
							? detalladoSIAListaAux.get(i).getDenomSegmento().toString() : null);
			filaResultado.setPrecioCostoArticulo((detalladoSIAListaAux.get(i).getPrecioCostoArticulo() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getPrecioCostoArticulo().toString())))
							? new Double(detalladoSIAListaAux.get(i).getPrecioCostoArticulo().toString()) : null);
			filaResultado.setPrecioCostoInicial((detalladoSIAListaAux.get(i).getPrecioCostoInicial() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getPrecioCostoInicial().toString())))
							? new Double(detalladoSIAListaAux.get(i).getPrecioCostoInicial().toString()) : null);
			filaResultado.setPrecioCostoFinal((detalladoSIAListaAux.get(i).getPrecioCostoFinal() != null
					&& !("".equals(detalladoSIAListaAux.get(i).getPrecioCostoFinal().toString())))
							? new Double(detalladoSIAListaAux.get(i).getPrecioCostoFinal().toString()) : null);
			if (codCentro == null) {
				codCentro = filaResultado.getCodCentro();
				final ParamCentrosOpc paramCentrosOpc = new ParamCentrosOpc();
				paramCentrosOpc.setCodLoc(codCentro);

				final ParamCentrosOpc paramCentroOpciones = this.paramCentrosOpcService.findOne(paramCentrosOpc);
				opcionesCentro = paramCentroOpciones.getOpcHabil();
			}
			if (opcionesCentro.contains("50_DETALLADO_GESTION_EUROS")) {

				VRotacionRefEuros vRotacionRefEuros = new VRotacionRefEuros();
				vRotacionRefEuros.setCodCentro(filaResultado.getCodCentro());
				vRotacionRefEuros.setCodArt(filaResultado.getCodArticulo());

				VRotacionRefEuros vRotacionRefEurosRes = this.vRotacionRefEurosService.findOne(vRotacionRefEuros);
				if (vRotacionRefEurosRes != null) {
					filaResultado.setRotacion(vRotacionRefEurosRes.getTipoRotTotal());
				}
			}

			resultado.add(filaResultado);
		}

		return resultado;
	}

	@Override
	public List<DetallePedido> findDatosEspecificosTextil(Long codCentro, String listaArticulos) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE  1=1	   	");

		StringBuffer query = new StringBuffer(
				" SELECT  p.cod_centro cod_centro, p.cod_art cod_art, temporada,num_orden,modelo_proveedor, descr_talla, descr_color, stock_min_comer_lineal "
						+ " FROM  v_datos_especificos_textil T, planograma_vigente P");

		// if(codCentro!=null){
		where.append(" AND P.COD_CENTRO = ? ");
		params.add(codCentro);
		// }

		// INC-209076-16. Al haber mas de 1000 articulos en lista Articulo da un
		// error de oracle, no se permite la clausula In con mas de 1000
		// elementos
		// Lo vamos meterlo cada 500 elementos.

		if (listaArticulos != null) { // Filtramos por articulos

			where.append(" AND (");

			String[] arrayListaArticulos = listaArticulos.trim().split(",");

			int numeroDeArticulos = arrayListaArticulos.length;

			int divLista = numeroDeArticulos / 100;
			int restoLista = numeroDeArticulos % 100;

			int numParticionesLista = 0;
			if (restoLista > 0) {
				numParticionesLista = divLista + 1;
			} else {
				numParticionesLista = divLista;
			}

			int inicio = 0;
			int fin = 100;
			if (fin > numeroDeArticulos) {
				fin = numeroDeArticulos;
			}
			for (int j = 1; j <= numParticionesLista; j++) { // Veces que
																// debemos
																// partir la
																// lista

				String listaArticulosAuxiliar = " (";

				for (int i = inicio; i < fin; i++) {
					listaArticulosAuxiliar = listaArticulosAuxiliar + arrayListaArticulos[i] + ",";
				}

				inicio = fin;
				fin = fin + 100;
				if (fin > numeroDeArticulos) {
					fin = numeroDeArticulos;
				}

				if (j < numParticionesLista) { // No es la ultima particiones
					listaArticulosAuxiliar = listaArticulosAuxiliar.substring(0, listaArticulosAuxiliar.length() - 1)
							+ ") OR ";
				} else {
					listaArticulosAuxiliar = listaArticulosAuxiliar.substring(0, listaArticulosAuxiliar.length() - 1)
							+ ") ) ";
				}

				if (listaArticulosAuxiliar != null) {
					where.append(" P.COD_ART in " + listaArticulosAuxiliar);
				}
			}
		}

		where.append(" AND T.COD_ART = P.COD_ART ");

		query.append(where);
		// StringBuffer order = new
		// StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		List<DetallePedido> lista = null;

		try {

			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoTextil,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	@Override
	public Long countHijasLote(Long codArt) throws Exception {
		StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		query.append("SELECT COUNT(*) ");

		query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E, V_DATOS_ESPECIFICOS_TEXTIL D ");
		query.append(" WHERE E.COD_ARTICULO_LOTE = ? ");
		query.append("   AND E.COD_ARTICULO_ASOCIADO = D.COD_ART ");

		params.add(codArt);

		Long cont = null;

		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;

	}

	@Override
	public List<DetallePedido> findAllTextilN2ByLote(DetallePedido detallePedido) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_ARTICULO_LOTE, COD_CENTRO, COD_ART, DESCRIP_ART, GRUPO1, "
				+ " GRUPO2, GRUPO3, GRUPO4, GRUPO5, STOCK, TIPO_APROV, " + " UNI_CAJA_SERV, FACING_MA1, "
				+ " DESCR_COLOR, COD_TALLA, TEMPORADA, NUM_ORDEN, MODELO_PROVEEDOR" + " FROM V_ART_CENTRO_ALTA_TEX_N2 "
				+ " WHERE COD_ARTICULO_LOTE = " + new Long(detallePedido.getId()) + " 	AND COD_CENTRO =  "
				+ detallePedido.getCodCentro() + " ORDER BY COD_ART");

		List<DetallePedido> lista = null;
		try {

			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoTextilN2,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	@Override
	public Long insertListIntoTemp(final List<DetallePedido> listToInsert, final String sesionID) throws Exception {

		String sql = "INSERT INTO t_detallado_pedido "
				 		+ "( fecha_pedido, centro, area, seccion, categoria, subCategoria, segmento, referencia"
				 		+ ", descripcion, stock, enCurso1, enCurso2, unidadesCaja, cajasPedidas, propuesta, cantidad"
				 		+ ", tipoDetallado, horaLimite, estado, idDsesion, cantidadAnt, temporada, num_orden, modelo_proveedor"
				 		+ ", descr_talla, descr_color, facing, tipo_aprov, ufp, tipo, nivel_lote, tipo_ufp"
				 		+ ", seg_dia_ped, flg_sia, fec_entrega, unid_fl_empuje, cod_necesidad, referencia_eroski, descripcion_eroski, flg_oferta"
				 		+ ", oferta, tipo_oferta, conver_art, dexenx, flg_propuesta, denom_area, denom_seccion, denom_categoria"
				 		+ ", denom_subcategoria, denom_segmento, precio_costo_articulo, precio_costo_inicial, precio_costo_final, rotacion, cod_mapa, motivo_pedido"
				 		+ ", cajas_cortadas, inc_prevision_venta, sm_estatico"
				 		+ ") "
				 	+ "VALUES "
				 		+ "( ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?, ?, ?, ?, ?, ?"
				 		+ ", ?, ?, ?"
				 		+ ")";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				//

				DetallePedido myPojo = listToInsert.get(i);
				// Fechas
				ps.setDate(1, Utilidades.convertirStringAFechaSqlDate(myPojo.getFechaPedido()));
				ps.setLong(2, myPojo.getCodCentro());
				ps.setLong(3, myPojo.getGrupo1());
				ps.setLong(4, myPojo.getGrupo2());
				//
				//
				ps.setLong(5, myPojo.getGrupo3());
				ps.setLong(6, myPojo.getGrupo4());
				ps.setLong(7, myPojo.getGrupo5());

				ps.setLong(8, myPojo.getCodArticulo());
				ps.setString(9, myPojo.getDescriptionArt());
				// ps.setString(9, String.valueOf(myPojo.getStock()));
				ps.setDouble(10, myPojo.getStock() != null && !("".equals(myPojo.getStock().toString()))
						? new Double(myPojo.getStock().toString()) : new Double(0));
				// ps.setDouble(9, myPojo.getStock());

				Double enCurso1 = myPojo.getEnCurso1() != null && !("".equals(myPojo.getEnCurso1().toString()))
						? new Double(myPojo.getEnCurso1().toString()) : new Double(0);
				Double enCurso2 = myPojo.getEnCurso2() != null && !("".equals(myPojo.getEnCurso2().toString()))
						? new Double(myPojo.getEnCurso2().toString()) : new Double(0);
				Double unidadesCaja = myPojo.getUnidadesCaja() != null
						&& !("".equals(myPojo.getUnidadesCaja().toString()))
								? new Double(myPojo.getUnidadesCaja().toString()) : new Double(1);

				// ps.setDouble(10,enCurso1/unidadesCaja);
				ps.setDouble(11, (double) Math.round((enCurso1 / unidadesCaja) * 10) / 10d);

				// if (myPojo.getFlgSIA()!= null &&
				// myPojo.getFlgSIA().equals("S")){
				// ps.setDouble(11,new Double(0));
				// ps.setDouble(11,enCurso2);
				// } else {
				// ps.setDouble(11,enCurso2/unidadesCaja);
				// }

				// ps.setDouble(11,enCurso2/unidadesCaja);
				ps.setDouble(12, (double) Math.round((enCurso2 / unidadesCaja) * 10) / 10d);

				ps.setDouble(13, myPojo.getUnidadesCaja() != null && !("".equals(myPojo.getUnidadesCaja().toString()))
						? new Double(myPojo.getUnidadesCaja().toString()) : new Double(0));

				if (myPojo.getCajasPedidas() != null) {
					ps.setLong(14, myPojo.getCajasPedidas());
				} else {
					ps.setNull(14, Types.NUMERIC);
				}
				if (myPojo.getPropuesta() != null) {
					ps.setLong(15, myPojo.getPropuesta());
				} else {
					ps.setNull(15, Types.NUMERIC);
				}
				if (myPojo.getCantidad() != null) {
					ps.setLong(16, myPojo.getCantidad());
				} else {
					ps.setNull(16, Types.NUMERIC);
				}

				ps.setString(17, myPojo.getTipoDetallado());
				ps.setString(18, myPojo.getHoraLimite());
				ps.setString(19, myPojo.getEstadoPedido());

				ps.setString(20, sesionID);

				// cantidadant
				if (myPojo.getCantidad() != null) {
					ps.setLong(21, myPojo.getCantidad());
				} else {
					ps.setNull(21, Types.NUMERIC);
				}

				// Textil
				ps.setString(22, myPojo.getTemporada());
				if (myPojo.getNumOrden() != null) {
					ps.setLong(23, myPojo.getNumOrden());
				} else {
					ps.setNull(23, Types.NUMERIC);
				}
				ps.setString(24, myPojo.getModeloProveedor());
				ps.setString(25, myPojo.getDescrTalla());
				ps.setString(26, myPojo.getDescrColor());

				// if (myPojo.getFacing() != null) {
				// ps.setDouble(26, myPojo.getFacing());
				// } else {
				// ps.setNull(26, Types.DOUBLE);
				// }
				ps.setDouble(27, myPojo.getFacing() != null && !("".equals(myPojo.getFacing().toString()))
						? new Double(myPojo.getFacing().toString()) : new Double(0));

				ps.setString(28, myPojo.getTipoAprovisionamiento());

				ps.setDouble(29, myPojo.getUfp() != null && !("".equals(myPojo.getUfp().toString()))
						? new Double(myPojo.getUfp().toString()) : new Double(0));

				ps.setString(30, myPojo.getTipo());

				ps.setLong(31, myPojo.getNivelLote());
				if (null != myPojo.getTipoUFP()) {
					ps.setString(32, myPojo.getTipoUFP());
				} else {
					ps.setNull(32, Types.VARCHAR);
				}
				if (null != myPojo.getNextDayPedido()) {
					ps.setString(33, myPojo.getNextDayPedido());
				} else {
					ps.setNull(33, Types.VARCHAR);
				}
				if (null != myPojo.getFlgSIA()) {
					ps.setString(34, myPojo.getFlgSIA());
				} else {
					ps.setNull(34, Types.VARCHAR);
				}

				if (null != myPojo.getFechaEntrega()) {
					ps.setDate(35, new java.sql.Date(myPojo.getFechaEntrega().getTime()));
				} else {
					ps.setNull(35, Types.DATE);
				}

				if (myPojo.getUnidFlEmpuje() != null) {
					ps.setLong(36, myPojo.getUnidFlEmpuje());
				} else {
					ps.setNull(36, Types.NUMERIC);
				}

				if (myPojo.getCodNecesidad() != null) {
					ps.setLong(37, myPojo.getCodNecesidad());
				} else {
					ps.setNull(37, Types.NUMERIC);
				}

				ps.setLong(38, myPojo.getCodArticulo());
				ps.setString(39, myPojo.getDescriptionArt());
				ps.setString(40, myPojo.getFlgOferta());

				if (null != myPojo.getOferta()) {
					ps.setString(41, myPojo.getOferta());
				} else {
					ps.setNull(41, Types.VARCHAR);
				}
				if (null != myPojo.getTipoOferta()) {
					ps.setLong(42, myPojo.getTipoOferta());
				} else {
					ps.setNull(42, Types.NUMERIC);
				}
				if (null != myPojo.getConverArt()) {
					ps.setString(43, myPojo.getOferta());
				} else {
					ps.setNull(43, Types.VARCHAR);
				}
				if (null != myPojo.getDexenx()) {
					ps.setDouble(44, myPojo.getDexenx());
				} else {
					ps.setNull(44, Types.NUMERIC);
				}
				if (null != myPojo.getFlgPropuesta()) {
					ps.setString(45, myPojo.getFlgPropuesta());
				} else {
					ps.setNull(45, Types.VARCHAR);
				}

				// GESTION DE EUROS
				ps.setString(46, myPojo.getDenomArea());
				ps.setString(47, myPojo.getDenomSeccion());
				ps.setString(48, myPojo.getDenomCategoria());
				ps.setString(49, myPojo.getDenomSubcategoria());
				ps.setString(50, myPojo.getDenomSegmento());
				ps.setDouble(51,
						myPojo.getPrecioCostoArticulo() != null
								&& !("".equals(myPojo.getPrecioCostoArticulo().toString()))
										? new Double(myPojo.getPrecioCostoArticulo().toString()) : new Double(0));
				ps.setDouble(52,
						myPojo.getPrecioCostoInicial() != null
								&& !("".equals(myPojo.getPrecioCostoInicial().toString()))
										? new Double(myPojo.getPrecioCostoInicial().toString()) : new Double(0));

				Double precioCostoFinal = (Double) myPojo.getPrecioCostoFinal();
				if (precioCostoFinal != null && !("".equals(myPojo.getPrecioCostoFinal().toString()))) {
					ps.setDouble(53, (double) Math.round(precioCostoFinal * 10d) / 10d);
				} else {
					ps.setDouble(53, new Double(0));
				}

				ps.setString(54, myPojo.getRotacion());
				
				/*
				 * MISUMI-300
				 */
				if (myPojo.getCodMapa() != null) {
					ps.setInt(55, myPojo.getCodMapa());
				} else {
					ps.setNull(55, Types.NUMERIC);
				}
				ps.setString(56, myPojo.getMotivoPedido());
				
				if (myPojo.getCajasCortadas() != null){
					ps.setLong(57, myPojo.getCajasCortadas());
				} else {
					ps.setNull(57, Types.NUMERIC);
				}

				ps.setString(58, myPojo.getIncPrevisionVenta());

				if (myPojo.getSmEstatico() != null){
					ps.setLong(59, myPojo.getSmEstatico());
				} else {
					ps.setNull(59, Types.NUMERIC);
				}
				
			}

			@Override
			public int getBatchSize() {
				return listToInsert.size();
			}
		});

		return new Long(0);

	}

	@Override
	public void DeleteTemp(String sesionID) throws Exception {
		if (sesionID != null) {
			List<Object> params = new ArrayList<Object>();
			StringBuffer query = new StringBuffer("DELETE t_detallado_pedido WHERE idDsesion = ?");
			params.add(sesionID);
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		}
	}

	@Override
	public Double sumarEurosIniciales(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT TRUNC(SUM(NVL(precio_costo_inicial, 0))) "
											+ "FROM t_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND seccion = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND categoria = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND referencia = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (propuesta <> 0 OR cantidad <> 0 OR unid_fl_empuje <> 0) ");
				}
			}
			if (sesionID != null) {
				where.append(" AND iddsesion = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Double cont = null;

		try {
			cont = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public Double sumarEurosFinales(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT TRUNC(SUM(NVL(PRECIO_COSTO_FINAL,0))) "
											+ "FROM t_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}
			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Double cont = null;

		try {
			cont = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public Double sumarCajasIniciales(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT SUM(NVL(propuesta, 0)) "
											+ "FROM t_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}
			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Double cont = null;

		try {
			cont = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public Double sumarCajasFinales(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer(" SELECT SUM(NVL(CANTIDAD,0)) " + " FROM T_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Double cont = null;

		try {
			cont = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public List<DetallePedido> findSessionInfo(DetallePedido detallePedido, String sesionID, Pagination pagination, String filtrosTabla)
			throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT centro, area , seccion ,categoria ,subcategoria, segmento ,referencia,descripcion"
												 + ", stock, encurso1, encurso2, unidadescaja, cajaspedidas, propuesta, cantidad"
												 + ", tipodetallado, horalimite, estado, iddsesion, estadogrid, NVL(codigoerror,'') codigoerror"
												 + ", cantidadant, temporada, num_orden, modelo_proveedor, descr_talla, descr_color"
												 + ", facing, tipo_aprov, tipo, nivel_lote, referencia as id, tipo_ufp, ufp, seg_dia_ped, flg_sia"
												 + ", fec_entrega,unid_fl_empuje,cod_necesidad, referencia_eroski, descripcion_eroski"
												 + ", flg_oferta, oferta, dexenx, CEIL(dexenx) dexenxentero, precio_costo_articulo"
												 + ", denom_area, denom_seccion, denom_categoria, denom_subcategoria, denom_segmento"
												 + ", precio_costo_inicial, precio_costo_final, rotacion, diferencia, ref_cumple, aviso"
												 //MISUMI-300
												 + ", cod_mapa, motivo_pedido"
												 + ", cajas_cortadas, inc_prevision_venta, sm_estatico "
										    + "FROM t_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}

			if (detallePedido.getFlgOferta() != null) {
				if (detallePedido.getFlgOferta().equals("S")) {
					where.append(" AND FLG_OFERTA = ? ");
					params.add(detallePedido.getFlgOferta());
				} else {
					if (detallePedido.getFlgOferta().equals("N")) {
						where.append(" AND FLG_OFERTA is null ");
					}
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
			
			//MISUMI-300
			if (detallePedido.getCodMapa() != null) {
				where.append(" AND COD_MAPA = ? ");
				params.add(detallePedido.getCodMapa());
			}
		}

		if (filtrosTabla != null && filtrosTabla.length()>0) {
			String [] lstFiltrosTabla = filtrosTabla.split("\\*\\*");
			for(String filtroTabla:lstFiltrosTabla){
				String [] valoresFiltrosTabla = filtroTabla.split("=");
				where.append(this.aniadirFiltrosTabla(valoresFiltrosTabla[0]));
				if (valoresFiltrosTabla[0].equals("unidadesCaja")){
					params.add("%"+valoresFiltrosTabla[1].replace(".", ",")+"%");
				}else{
					params.add("%"+valoresFiltrosTabla[1]+"%");
				}
			}
		}
		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {

				if (pagination.getSort().equals("agrupacion")) {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort(), pagination.getAscDsc()));
					query.append(order);
				} else {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort(), pagination.getAscDsc()));
					query.append(order);
				}
			}
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		} else {
			order.append(" ORDER BY " + this.getMappedField("agrupacion", "ASC"));
			query.append(order);
		}

		List<DetallePedido> lista = null;
		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMap,
					params.toArray());

		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	@Override
	public List<GenericExcelVO> findSessionInfoExcel(DetallePedido detallePedido, String[] columnModel, String sesionID,
			Pagination pagination) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
		// params.add(lang);

		String nombreCampoColmodel = "";
		int j = 1;
		String fields = "";
		List<String> listColumns = Arrays.asList(columnModel);

		String unidflempuje1 = null;
		for (int i = 0; i < listColumns.size(); i++) {
			nombreCampoColmodel = this.getMappedField(listColumns.get(i), 1).toUpperCase();

			if (fields.equals("")) {
				fields = nombreCampoColmodel;
			} else {
				fields = fields + ", " + nombreCampoColmodel;
			}

			if (nombreCampoColmodel.equals("UNID_FL_EMPUJE")) {
				unidflempuje1 = " AS UNID_FL_EMPUJE_1 ";
			}

			j++;
		}

		fields = fields + ", CODIGOERROR"; // Para mostar una nueva columna con
											// el mensaje de error que ha dado
											// el WS al guardar
		j++;

		while (j <= 40) { // Completamos las columnas restantes
			fields = fields + ", null";
			j++;
		}

		// En la posicion 41 se metera el estado, esta columna no se pinta, es
		// para colorear el campo cantida si viene,
		fields = fields + ", ESTADO";

		// En la posicion 42 se metera el unidFlEmpuje, esta columna se pinta a
		// veces, pero la pintaremos si está entre los 40 primeros campos.El del
		// campo 42 es un control para decidir si pintamos vacío o no
		// en el excel.
		fields = fields + ", UNID_FL_EMPUJE";

		// Si entre los campos se busca dos veces unid_fl_empuje, renombramos el
		// segundo por si el usuario reordena el grid por empuje,
		// en el order by unid_fl_empuje no se lie y sepa por qué campo ordenar.
		// Es posible que se busque en la query dos veces unif_fl_empuje.
		// La segunda vez es un campo de control y la primera para pintar en el
		// excel. No se puede quitar uno de los dos porque nos quedamos o
		// sin campo de control o sin valor del excel.
		if (unidflempuje1 != null) {
			fields = fields + unidflempuje1;
		}

		// DESDE NO HACE FALTA BORRAR
		// int posicionColumnaCantidad = listColumns.indexOf("cantidad") + 3;
		// fields = posicionColumnaCantidad + fields; //Necesitamos saber la
		// columna catidad en que posición viene para mas tarde en el excel
		// controlar
		// en que color se pinta esta columna, la columna correspondiente a
		// cantidad.
		// fields = "ESTADO,CANTIDAD" + fields; //La primera columna del excel
		// siempre sera el estado. No se pinta en el excel pero lo necesitamos
		// para pintar el color de la cantidad.
		// HASTA NO HACE FALTA BORRAR

		StringBuffer query = new StringBuffer(" SELECT ");
		query.append(fields);
		query.append(" FROM t_detallado_pedido t ");

		/*
		 * StringBuffer query = new StringBuffer(
		 * " SELECT CENTRO, AREA , SECCION ,CATEGORIA ,SUBCATEGORIA, SEGMENTO ,REFERENCIA,DESCRIPCION,"
		 * +
		 * " STOCK,ENCURSO1,ENCURSO2,UNIDADESCAJA,  CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO,"
		 * +
		 * " HORALIMITE,ESTADO,IDDSESION ,ESTADOGRID , nvl(CODIGOERROR,'') CODIGOERROR, CANTIDADANT,"
		 * +
		 * " TEMPORADA,NUM_ORDEN,MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING"
		 * + " FROM T_detallado_pedido ");
		 */

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}

			if (detallePedido.getFlgOferta() != null) {
				if (detallePedido.getFlgOferta().equals("S")) {
					where.append(" AND FLG_OFERTA = ? ");
					params.add(detallePedido.getFlgOferta());
				} else {
					if (detallePedido.getFlgOferta().equals("N")) {
						where.append(" AND FLG_OFERTA IS NULL ");
					}
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
			
			//MISUMI-300
			if (detallePedido.getCodMapa() != null) {
				where.append(" AND COD_MAPA = ? ");
				params.add(detallePedido.getCodMapa());
			}
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination.getSort() != null) {
			order.append(" ORDER BY " + this.getMappedFieldExcel(pagination.getSort(), pagination.getAscDsc()));
			query.append(order);
		} else {
			order.append(" ORDER BY " + this.getMappedField("agrupacion", pagination.getAscDsc()) + ", "
					+ this.getMappedField("tipo", pagination.getAscDsc()));
			query.append(order);
		}

		List<GenericExcelVO> lista = null;
		
		try {
			lista = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoExcelMap,
					params.toArray());

		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	/*
	 * StringBuffer query = new StringBuffer(
	 * " SELECT CENTRO, AREA , SECCION ,CATEGORIA ,SUBCATEGORIA, SEGMENTO ,REFERENCIA,DESCRIPCION,"
	 * +
	 * " STOCK,ENCURSO1,ENCURSO2,UNIDADESCAJA,  CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO,"
	 * +
	 * " HORALIMITE,ESTADO,IDDSESION ,ESTADOGRID , nvl(CODIGOERROR,'') CODIGOERROR, CANTIDADANT,"
	 * + " TEMPORADA,NUM_ORDEN,MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING"
	 * + " FROM T_detallado_pedido ");
	 */

	private String getMappedField(String fieldName, int target) {
		if (fieldName.toUpperCase().equals("FECHAPEDIDO")) {
			return "FECHACREACION";
		} else if (fieldName.toUpperCase().equals("AGRUPACION")) {
			return "AREA || '-'|| SECCION || '-'|| CATEGORIA || '-'|| SUBCATEGORIA || '-'|| SEGMENTO";
		} else if (fieldName.toUpperCase().equals("TEMPORADA")) {
			return "TEMPORADA";
		} else if (fieldName.toUpperCase().equals("ESTRUCTURA")) {
			return "LPAD(SECCION,2,'0') || LPAD(CATEGORIA,2,'0')  || LPAD(SUBCATEGORIA,2,'0') || LPAD(SEGMENTO,2,'0')";
		} else if (fieldName.toUpperCase().equals("NUMORDEN")) {
			return "NUM_ORDEN";
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "MODELO_PROVEEDOR";
		} else if (fieldName.toUpperCase().equals("DESCRTALLA")) {
			return "DESCR_TALLA";
		} else if (fieldName.toUpperCase().equals("DESCRCOLOR")) {
			return "DESCR_COLOR";
		} else if (fieldName.toUpperCase().equals("CODARTICULO")) {
			return "REFERENCIA";
		} else if (fieldName.toUpperCase().equals("DESCRIPTIONART")) {
			return "DESCRIPCION";
		} else if (fieldName.toUpperCase().equals("STOCK")) {
			return "STOCK";
		} else if (fieldName.toUpperCase().equals("FACING")) {
			return "FACING";
		} else if (fieldName.toUpperCase().equals("ENCURSO1")) {
			return "ENCURSO1";
		} else if (fieldName.toUpperCase().equals("ENCURSO2")) {
			return "ENCURSO2";
		} else if (fieldName.toUpperCase().equals("UNIDADESCAJA")) {
			// return "DECODE(TIPO_UFP,'U', TRUNC(UFP, 0), UNIDADESCAJA)
			// UNIDADESCAJA";
			return "UNIDADESCAJA";
		} else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")) {
			return "CAJASPEDIDAS";
		} else if (fieldName.toUpperCase().equals("PROPUESTA")) {
			return "PROPUESTA";
		} else if (fieldName.toUpperCase().equals("CANTIDAD")) {
			// return "CANTIDAD || ' ' || NVL(TIPO_UFP, '') AS CANTIDAD";
			return "CANTIDAD  || ' ' || case when diferencia < 0 then '-' when diferencia > 0 then '+'  end  || DECODE(DIFERENCIA, NULL , '', 0 , '', DIFERENCIA) || ' ' || DECODE(DEXENX , NULL , '', 0 , '', 1 , '' , 'De' || DEXENX || 'en' || DEXENX) AS CANTIDAD ";
		} else if (fieldName.toUpperCase().equals("TIPODETALLADO")) {
			return "TIPODETALLADO";
		} else if (fieldName.toUpperCase().equals("ESTADOGRID")) {
			return "ESTADO";
		} else if (fieldName.toUpperCase().equals("ESTADOPEDIDO")) {
			return "ESTADO";
		} else if (fieldName.toUpperCase().equals("GRUPO1")) {
			return "GRUPO1";
		} else if (fieldName.toUpperCase().equals("GRUPO2")) {
			return "GRUPO2";
		} else if (fieldName.toUpperCase().equals("GRUPO3")) {
			return "GRUPO3";
		} else if (fieldName.toUpperCase().equals("GRUPO4")) {
			return "GRUPO4";
		} else if (fieldName.toUpperCase().equals("GRUPO5")) {
			return "GRUPO5";
		} else if (fieldName.toUpperCase().equals("TIPOAPROVISIONAMIENTO")) {
			return "TIPO_APROV";
		} else if (fieldName.toUpperCase().equals("RESULTADOWS")) {
			return "0";
		} else if (fieldName.toUpperCase().equals("TIPO")) {
			return "DECODE(TIPO , 'N' , 'NUEVA' , NULL )";
		} else if (fieldName.toUpperCase().equals("TIPOUFP")) {
			return "TIPO_UFP";
		} else if (fieldName.toUpperCase().equals("NEXTDAYPEDIDO")) {
			return "SEG_DIA_PED";
		} else if (fieldName.toUpperCase().equals("FECHAENTREGA")) {
			return "TO_CHAR(FEC_ENTREGA,'DDMMYYYY')";
		} else if (fieldName.toUpperCase().equals("UNIDFLEMPUJE")) {
			return "UNID_FL_EMPUJE";
		} else if (fieldName.toUpperCase().equals("PRECIOCOSTOFINAL")) {
			return "PRECIO_COSTO_FINAL";
		}else if (fieldName.toUpperCase().equals("CODMAPA")) {
			return "COD_MAPA";
		}else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "MOTIVO_PEDIDO";
		}else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas";
		}else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "NVL(inc_prevision_venta,0)||'%'";
		}else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico";
		}else {
			return fieldName;
		}
	}

	@Override
	public List<DetallePedido> findSessionInfoUpdateable(DetallePedido detallePedido, String sesionID, Pagination pagination) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT centro, area, seccion, categoria, subcategoria, segmento, referencia, descripcion"
												// " STOCK,ENCURSO1,ENCURSO2,DECODE(TRUNC(UFP, 0), 0,
												// UNIDADESCAJA, TRUNC(UFP, 0)) UNIDADESCAJA ,
												// CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO," +
												 + ", stock, encurso1, encurso2, unidadescaja, cajaspedidas, propuesta, cantidad, tipodetallado"
												 + ", horalimite, estado, iddsesion, estadogrid, NVL(codigoerror, '') codigoerror, cantidadant"
												 + ", temporada,num_orden,modelo_proveedor,descr_talla,descr_color,facing,tipo_aprov,tipo"
												 + ", nivel_lote, referencia as id, tipo_ufp, ufp,seg_dia_ped, flg_sia, fec_entrega, unid_fl_empuje"
												 + ", cod_necesidad, referencia_eroski, descripcion_eroski, flg_oferta, oferta, dexenx, ceil(dexenx) dexenxentero"
												 + ", precio_costo_articulo, denom_area, denom_seccion, denom_categoria, denom_subcategoria, denom_segmento"
												 + ", precio_costo_inicial, precio_costo_final, rotacion, diferencia, ref_cumple, aviso"
												 //MISUMI-300
												 + ", cod_mapa, motivo_pedido, cajas_cortadas, inc_prevision_venta, sm_estatico "
											+ "FROM t_detallado_pedido ");

		where.append("WHERE (estado IS NULL OR estado NOT IN ( 'I' , 'B', 'E' )) AND NVL(estadogrid, 0) <> -2");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND seccion = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND categoria = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND referencia = ? ");
				params.add(detallePedido.getCodArticulo());
			}

			// Siempre filtramos por el flg_SIA, si es igual a null, son los de
			// PBL y si es igual a S, son los de SIA
			if (detallePedido.getFlgSIA() != null) {
				where.append(" AND flg_sia = ? ");
				params.add(detallePedido.getFlgSIA());
			} else {
				where.append(" AND flg_sia IS NULL ");
			}

			if (detallePedido.getCodMapa() != null){
				where.append(" AND cod_mapa = ? ");
				params.add(detallePedido.getCodMapa());
			}
			
			if (sesionID != null) {
				where.append(" AND iddsesion = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" ORDER BY " + this.getMappedField(pagination.getSort(), pagination.getAscDsc()));
				query.append(order);
			}
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}

		List<DetallePedido> lista = null;
		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMap,
					params.toArray());

		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	@Override
	public Long countSessionInfo(DetallePedido detallePedido, String sesionID, String filtrosTabla) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT COUNT(1) "
											+ "FROM t_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgSIA() != null) {
				where.append(" AND FLG_SIA = ? ");
				params.add(detallePedido.getFlgSIA());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}
			// Para los contadores

			if (detallePedido.getFlgOferta() != null) {
				if (detallePedido.getFlgOferta().equals("S")) {
					where.append(" AND FLG_OFERTA = ? ");
					params.add(detallePedido.getFlgOferta());
				} else {
					if (detallePedido.getFlgOferta().equals("N")) {
						where.append(" AND FLG_OFERTA is null ");
					}
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
			
			//MISUMI-300
			if (detallePedido.getCodMapa() != null) {
				where.append(" AND COD_MAPA = ? ");
				params.add(detallePedido.getCodMapa());
			}

		}
		if (filtrosTabla != null && filtrosTabla.length()>0) {
			String [] lstFiltrosTabla = filtrosTabla.split("\\*\\*");
			for(String filtroTabla:lstFiltrosTabla){
				String [] valoresFiltrosTabla = filtroTabla.split("=");
				where.append(this.aniadirFiltrosTabla(valoresFiltrosTabla[0]));
				if (valoresFiltrosTabla[0].equals("unidadesCaja")){
					params.add("%"+valoresFiltrosTabla[1].replace(".", ",")+"%");
				}else{
					params.add("%"+valoresFiltrosTabla[1]+"%");
				}
				
			}
		}
		query.append(where);

		Long cont = null;

		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;

	}

	@Override
	public List<VAgruComerRef> findFilterMatchesSection(VAgruComerRef vAgruComerRef, String sessionId)
			throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE  1=1	   	");

		// params.add(lang);
		StringBuffer query = new StringBuffer(
				" SELECT   distinct(to_char(area)||' '||to_char(seccion) ),AREA, seccion, MAX(T1.FLG_PROPUESTA) FLG_PROPUESTA "
						+ " FROM  t_detallado_pedido t1");

		where.append(" AND nvl(estadogrid,'3') not in ( '1' , '-3' ) ");
		if (sessionId != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sessionId);
		}

		query.append(where);
		query.append("GROUP BY AREA, SECCION, CATEGORIA");

		List<VAgruComerRef> lista = null;
		// Ejecutamos el comando SQL
		try {

			lista = (List<VAgruComerRef>) this.jdbcTemplate.query(query.toString(), this.rwAgruComerRefMapSeccion,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;

	}

	@Override
	public List<VAgruComerRef> findFilterMatchesCategoria(VAgruComerRef vAgruComerRef, String sessionId)
			throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE  1=1	   	");

		StringBuffer query = new StringBuffer(
				" SELECT   distinct(to_char(area)||' '||to_char(seccion) ||' '||to_char(categoria) ),AREA, seccion,categoria, MAX(T1.FLG_PROPUESTA) FLG_PROPUESTA "
						+ " FROM  t_detallado_pedido t1");

		where.append(" AND seccion = ?");
		where.append(" AND nvl(estadogrid,'3') not in ( '1' , '-3' ) ");
		params.add(vAgruComerRef.getGrupo2());

		if (sessionId != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sessionId);
		}

		query.append(where);
		query.append("GROUP BY AREA, SECCION, CATEGORIA");

		List<VAgruComerRef> lista = null;
		// Ejecutamos el comando SQL
		try {

			lista = (List<VAgruComerRef>) this.jdbcTemplate.query(query.toString(), this.rwAgruComerRefMapCategoria,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;

	}

	private String getMappedField(String fieldName, String sortOrder) {
		if (fieldName.toUpperCase().equals("CODARTICULO")) {
			return "to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("AGRUPACION")) {
			return "AREA " + sortOrder + ", SECCION " + sortOrder + ",CATEGORIA " + sortOrder + ",SUBCATEGORIA "
					+ sortOrder + ",SEGMENTO " + sortOrder + ",to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRIPTIONART")) {
			return "DESCRIPCION " + sortOrder;
		} else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")) {
			return "to_number(CAJASPEDIDAS) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ESTADOPEDIDO")) {
			return "ESTADOGRID " + sortOrder;
		} else if (fieldName.toUpperCase().equals("STOCK")) {
			return "to_number(STOCK) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ENCURSO1")) {
			return "to_number(ENCURSO1) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ENCURSO2")) {
			return "to_number(ENCURSO2) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("CANTIDAD")) {
			return "to_number(CANTIDAD) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("PROPUESTA")) {
			return "to_number(PROPUESTA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("TIPOAPROVISIONAMIENTO")) {
			return "TIPO_APROV " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ESTRUCTURA")) {
			return "AREA " + sortOrder + ", SECCION " + sortOrder + ",CATEGORIA " + sortOrder + ",SUBCATEGORIA "
					+ sortOrder + ",SEGMENTO " + sortOrder + ",to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "MODELO_PROVEEDOR " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRTALLA")) {
			return "DESCR_TALLA " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRCOLOR")) {
			return "DESCR_COLOR " + sortOrder;
		} else if (fieldName.toUpperCase().equals("NUMORDEN")) {
			return "to_number(NUM_ORDEN) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("TIPO")) {
			return "DECODE(TIPO , 'N' , 'NUEVA' , NULL )";
		} else if (fieldName.toUpperCase().equals("TIPOUFP")) {
			return "TIPO_UFP " + sortOrder;
		} else if (fieldName.toUpperCase().equals("NEXTDAYPEDIDO")) {
			return "SEG_DIA_PED " + sortOrder;
		} else if (fieldName.toUpperCase().equals("UNIDFLEMPUJE")) {
			return "UNID_FL_EMPUJE " + sortOrder;
		} else if (fieldName.toUpperCase().equals("FECHAENTREGA")) {
			return "FEC_ENTREGA " + sortOrder;
		} else if (fieldName.toUpperCase().equals("PRECIOCOSTOFINAL")) {
			return "PRECIO_COSTO_FINAL " + sortOrder;
		}else if (fieldName.toUpperCase().equals("CODMAPA")) {
			return "COD_MAPA " + sortOrder;
		}else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "MOTIVO_PEDIDO " + sortOrder;
		}else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas " + sortOrder;
		}else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "inc_prevision_venta " + sortOrder;
		}else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico " + sortOrder;
		}else {
			return fieldName + " " + sortOrder;
		}
	}

	private String getMappedFieldExcel(String fieldName, String sortOrder) {
		if (fieldName.toUpperCase().equals("CODARTICULO")) {
			return "to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("AGRUPACION")) {
			return "AREA " + sortOrder + ", SECCION " + sortOrder + ",CATEGORIA " + sortOrder + ",SUBCATEGORIA "
					+ sortOrder + ",SEGMENTO " + sortOrder + ",to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRIPTIONART")) {
			return "DESCRIPCION " + sortOrder;
		} else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")) {
			return "to_number(CAJASPEDIDAS) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ESTADOPEDIDO")) {
			return "ESTADOGRID " + sortOrder;
		} else if (fieldName.toUpperCase().equals("STOCK")) {
			return "to_number(STOCK) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ENCURSO1")) {
			return "to_number(ENCURSO1) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ENCURSO2")) {
			return "to_number(ENCURSO2) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("CANTIDAD")) {
			return "to_number(T.CANTIDAD) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("PROPUESTA")) {
			return "to_number(PROPUESTA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("TIPOAPROVISIONAMIENTO")) {
			return "TIPO_APROV " + sortOrder;
		} else if (fieldName.toUpperCase().equals("ESTRUCTURA")) {
			return "AREA " + sortOrder + ", SECCION " + sortOrder + ",CATEGORIA " + sortOrder + ",SUBCATEGORIA "
					+ sortOrder + ",SEGMENTO " + sortOrder + ",to_number(REFERENCIA) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "MODELO_PROVEEDOR " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRTALLA")) {
			return "DESCR_TALLA " + sortOrder;
		} else if (fieldName.toUpperCase().equals("DESCRCOLOR")) {
			return "DESCR_COLOR " + sortOrder;
		} else if (fieldName.toUpperCase().equals("NUMORDEN")) {
			return "to_number(NUM_ORDEN) " + sortOrder;
		} else if (fieldName.toUpperCase().equals("TIPO")) {
			return "DECODE(TIPO , 'N' , 'NUEVA' , NULL )";
		} else if (fieldName.toUpperCase().equals("TIPOUFP")) {
			return "TIPO_UFP " + sortOrder;
		} else if (fieldName.toUpperCase().equals("NEXTDAYPEDIDO")) {
			return "SEG_DIA_PED " + sortOrder;
		} else if (fieldName.toUpperCase().equals("UNIDFLEMPUJE")) {
			return "UNID_FL_EMPUJE " + sortOrder;
		} else if (fieldName.toUpperCase().equals("FECHAENTREGA")) {
			return "FEC_ENTREGA " + sortOrder;
		} else if (fieldName.toUpperCase().equals("PRECIOCOSTOFINAL")) {
			return "PRECIO_COSTO_FINAL";
		}else if (fieldName.toUpperCase().equals("CODMAPA")) {
			return "COD_MAPA " + sortOrder;
		}else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "MOTIVO_PEDIDO " + sortOrder;
		}else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas " + sortOrder;
		}else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "inc_prevision_venta " + sortOrder;
		}else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico " + sortOrder;
		}else {
			return fieldName;
		}
	}

	@Override
	public DetallePedido add(DetallePedido detalladoPedido, String sessionId) throws Exception {

		// Completamos los campos del registro

		// Preparamos la SQL
		String insertSQL = "INSERT INTO t_detallado_pedido "
								+ " ( centro, area, seccion,categoria"
								 + ", subcategoria, segmento, referencia, descripcion"
								 + ", stock, encurso1, encurso2, unidadescaja, cajaspedidas"
								 + ", propuesta, cantidad, tipodetallado, estado"
								 + ", iddsesion, estadogrid, temporada, num_orden"
								 + ", modelo_proveedor, descr_talla, descr_color, facing"
								 + ", tipo_aprov, ufp, nivel_lote, tipo_ufp"
								 + ", seg_dia_ped, flg_sia, referencia_eroski, descripcion_eroski"
								 + ", dexenx, flg_oferta, denom_area, denom_seccion"
								 + ", denom_categoria, denom_subcategoria, denom_segmento, precio_costo_articulo"
								 + ", precio_costo_inicial, precio_costo_final, rotacion"
								 + ") "
						 + "VALUES "
						 		+ "( ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ", ?, ?, ?, ?"
						 		+ ")";

		List<Object> values = new ArrayList<Object>();
		values.add(detalladoPedido.getCodCentro());
		values.add(detalladoPedido.getGrupo1());
		values.add(detalladoPedido.getGrupo2());
		values.add(detalladoPedido.getGrupo3());
		values.add(detalladoPedido.getGrupo4());
		values.add(detalladoPedido.getGrupo5());
		values.add(detalladoPedido.getCodArticulo());
		values.add(detalladoPedido.getDescriptionArt());
		values.add(detalladoPedido.getStock());
		values.add(detalladoPedido.getEnCurso1());
		values.add(detalladoPedido.getEnCurso2());
		values.add(detalladoPedido.getUnidadesCaja());
		values.add(detalladoPedido.getCajasPedidas());
		values.add(detalladoPedido.getPropuesta());
		values.add(detalladoPedido.getCantidad());
		values.add(detalladoPedido.getTipoDetallado());
		values.add(detalladoPedido.getEstadoPedido());
		values.add(sessionId);
		values.add(detalladoPedido.getEstadoGrid());
		values.add(detalladoPedido.getTemporada());
		values.add(detalladoPedido.getNumOrden());
		values.add(detalladoPedido.getModeloProveedor());
		values.add(detalladoPedido.getDescrTalla());
		values.add(detalladoPedido.getDescrColor());
		values.add(detalladoPedido.getFacing());
		values.add(detalladoPedido.getTipoAprovisionamiento());
		values.add(detalladoPedido.getUfp());
		values.add(detalladoPedido.getNivelLote());
		values.add(detalladoPedido.getTipoUFP());
		values.add(detalladoPedido.getNextDayPedido());
		values.add(detalladoPedido.getFlgSIA());
		values.add(detalladoPedido.getCodArticuloEroski());
		values.add(detalladoPedido.getDescriptionArtEroski());
		values.add(detalladoPedido.getDexenx());
		values.add(detalladoPedido.getFlgOferta());

		values.add(detalladoPedido.getDenomArea());
		values.add(detalladoPedido.getDenomSeccion());
		values.add(detalladoPedido.getDenomCategoria());
		values.add(detalladoPedido.getDenomSubcategoria());
		values.add(detalladoPedido.getDenomSegmento());
		values.add(detalladoPedido.getPrecioCostoArticulo() != null
				&& !("".equals(detalladoPedido.getPrecioCostoArticulo().toString()))
						? new Double(detalladoPedido.getPrecioCostoArticulo().toString()) : new Double(0));
		values.add(detalladoPedido.getPrecioCostoInicial() != null
				&& !("".equals(detalladoPedido.getPrecioCostoInicial().toString()))
						? new Double(detalladoPedido.getPrecioCostoInicial().toString()) : new Double(0));
		values.add(detalladoPedido.getPrecioCostoFinal() != null
				&& !("".equals(detalladoPedido.getPrecioCostoFinal().toString()))
						? new Double(detalladoPedido.getPrecioCostoFinal().toString()) : new Double(0));
		values.add(detalladoPedido.getRotacion());

		// Ejecutamos el comando SQL
		try {

			this.jdbcTemplate.update(insertSQL, values.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(insertSQL.toString(), values, e);
		}

		return detalladoPedido;
	}

	@Override
	public DetallePedido updateEmpuje(DetallePedido detalladoPedido, String sessionId) throws Exception {

		// Completamos los campos del registro

		// Preparamos la SQL
		String updateSQL = "UPDATE T_DETALLADO_PEDIDO SET ESTADO = ?,  TIPODETALLADO= ? , CANTIDAD = ? , PROPUESTA = ?, ESTADOGRID = ? "
				+ "WHERE IDDSESION=? AND REFERENCIA=?  AND CENTRO=? ";

		List<Object> values = new ArrayList<Object>();
		values.add(detalladoPedido.getEstadoPedido());
		values.add(detalladoPedido.getTipoDetallado());
		values.add(detalladoPedido.getCantidad());
		values.add(detalladoPedido.getPropuesta());
		values.add(detalladoPedido.getEstadoGrid());
		values.add(sessionId);
		values.add(detalladoPedido.getCodArticulo());
		values.add(detalladoPedido.getCodCentro());

		// Ejecutamos el comando SQL

		try {

			this.jdbcTemplate.update(updateSQL, values.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(updateSQL.toString(), values, e);
		}

		return detalladoPedido;
	}

	@Override
	public DetallePedido updateDetalladoNuevo(DetallePedido detalladoPedido, String sessionId) throws Exception {

		// Completamos los campos del registro

		// Preparamos la SQL
		String updateSQL = "UPDATE T_DETALLADO_PEDIDO SET ESTADO = ? , CANTIDAD = ? , UNIDADESCAJA = ?, ESTADOGRID = ? "
				+ "WHERE IDDSESION=? AND REFERENCIA=?  AND CENTRO=? ";

		List<Object> values = new ArrayList<Object>();
		values.add(detalladoPedido.getEstadoPedido());
		values.add(detalladoPedido.getCantidad());
		values.add(detalladoPedido.getUnidadesCaja());
		values.add(detalladoPedido.getEstadoGrid());
		values.add(sessionId);
		values.add(detalladoPedido.getCodArticulo());
		values.add(detalladoPedido.getCodCentro());

		// Ejecutamos el comando SQL

		try {
			this.jdbcTemplate.update(updateSQL, values.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(updateSQL.toString(), values, e);
		}

		return detalladoPedido;
	}

	@Override
	public void updateGridState(final List<DetallePedido> listaDetalladoModif, final String sessionId)
			throws Exception {

		// Completamos los campos del registro
		// Preparamos la SQL
		String sql = "UPDATE t_detallado_pedido "
				   + "SET estadoGrid  = ?"
				   	 + ", cantidad 	  = ?"
				   	 + ", codigoerror = ''"
				   + "WHERE IDDSESION = ?"
				   + "AND referencia  = ?"
				   + "AND centro	  = ?"
				   + "AND NVL(estadoGrid, '0') <> 9";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				DetallePedido myPojo = listaDetalladoModif.get(i);
				ps.setLong(1, new Long(myPojo.getEstadoGrid()));
				ps.setLong(2, myPojo.getCantidad());
				ps.setString(3, sessionId);
				ps.setLong(4, myPojo.getCodArticulo());
				ps.setLong(5, myPojo.getCodCentro());
			}

			@Override
			public int getBatchSize() {
				return listaDetalladoModif.size();
			}
		});

	}

	@Override
	public void updateOnlyGridState(final List<DetallePedido> listaDetalladoModif, final String sessionId,
			final Long estadoGrid, final Long nuevoEstadoGrid) throws Exception {

		// Completamos los campos del registro
		try {
			// Preparamos la SQL
			String sql = "update T_detallado_pedido set  estadoGrid= ? "
					+ " , CODIGOERROR=''  where IDDSESION=? and REFERENCIA=? and CENTRO=? and estadoGrid = ?";

			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					DetallePedido myPojo = listaDetalladoModif.get(i);
					ps.setLong(1, nuevoEstadoGrid);
					ps.setString(2, sessionId);
					ps.setLong(3, myPojo.getCodArticulo());
					ps.setLong(4, myPojo.getCodCentro());
					ps.setLong(5, estadoGrid);

				}

				@Override
				public int getBatchSize() {
					return listaDetalladoModif.size();
				}
			});
		} catch (Exception e) {

		}

	}

	@Override
	public void deleteNewRecords(final String sessionId) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE nvl(estadogrid,'3') in ( '1' , '-3' ) ");
		StringBuffer query = new StringBuffer(" DELETE " + " T_detallado_pedido  ");

		if (sessionId != null) {

			where.append(" and  idDsesion = ? ");
			params.add(sessionId);

		}
		query.append(where);

		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}

	@Override
	public void resetSessionData(String sessionId) throws Exception {

		// Completamos los campos del registro

		// Preparamos la SQL
		String sql = "update T_detallado_pedido set  estadoGrid=null where "
				+ " IDDSESION= ? and estadoGrid is not null ";
		List<Object> params = new ArrayList<Object>();
		params.add(sessionId);

		try {

			this.jdbcTemplate.update(sql, params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params, e);
		}

	}

	@Override
	public List<DetallePedido> findModifies(String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE ESTADOGRID in ('1','2') ");

		StringBuffer query = new StringBuffer(
				" SELECT CENTRO, AREA , SECCION ,CATEGORIA ,SUBCATEGORIA, SEGMENTO ,REFERENCIA,DESCRIPCION,"
						+ " STOCK,ENCURSO1,ENCURSO2,UNIDADESCAJA,  CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO,"
						+ " HORALIMITE,ESTADO,IDDSESION ,ESTADOGRID,nvl(CODIGOERROR,'') CODIGOERROR, "
						+ " TEMPORADA, NUM_ORDEN,MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING, NIVEL_LOTE, REFERENCIA as ID,TIPO_UFP, UFP, SEG_DIA_PED, TIPO, TIPO_APROV"
						+ " FROM T_detallado_pedido ");

		if (sesionID != null) {
			where.append(" AND IDDSESION = '" + sesionID + "'");
		}

		query.append(where);

		List<DetallePedido> lista = null;

		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoFullMap);

		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), null, e);
		}

		return lista;

	}

	@Override
	public void updateData(final List<DetallePedidoModificados> listaDetalladoModif, final String sessionId) throws Exception {

		String sql = "";
		
		// Completamos los campos del registro
		try {
			// Preparamos la SQL
			sql = "UPDATE t_detallado_pedido "
				+ "SET cantidad 		= ?"
				  + ", cantidadant 		= ?"
				  + ", estadoGrid		= ?"
				  + ", estado			= NVL(?, estado)"
				  + ", tipoDetallado	= NVL(?, tipodetallado)"
				  + ", codigoerror		= ?"
				  + ", cod_necesidad	= NVL(?, cod_necesidad)"
				  + ", unidadescaja		= NVL(?, unidadescaja) "
				+ "WHERE iddsesion		 = ? "
				+ "AND referencia = ?"
				+ "AND centro 			 = ?";
			
//			if (listaDetalladoModif != null && listaDetalladoModif.size() > 0 && listaDetalladoModif.get(0).getCodMapa()==null){
//				sql = sql+"AND cod_mapa IS NOT NULL ";
//			}

			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					DetallePedidoModificados myPojo = listaDetalladoModif.get(i);
					ps.setLong(1, myPojo.getCantidad());
//					ps.setLong(2, myPojo.getCantidadAnt()); 
					if (myPojo.getCantidadAnt() != null) { 
						ps.setLong(2, myPojo.getCantidadAnt()); 
					} else { 
						ps.setNull(2, Types.NUMERIC); 
					} 
					
					ps.setString(3, myPojo.getEstadoGrid());
					ps.setString(4, myPojo.getEstadoPedido());
					ps.setString(5, myPojo.getTipoDetallado());
					
					String mensajeError = "";
					if ("".equals(myPojo.getResultadoWS()) || "null".equals(myPojo.getResultadoWS())){
						if ("".equals(myPojo.getCodigoError()) || "null".equals(myPojo.getCodigoError())){
							mensajeError = "";
						}else{
							mensajeError = myPojo.getCodigoError();
						}
					}else{
						mensajeError = myPojo.getResultadoWS();
					}
					ps.setString(6, mensajeError);
					
					if (myPojo.getCodNecesidad() != null) {
						ps.setLong(7, myPojo.getCodNecesidad());
					} else {
						ps.setNull(7, Types.NUMERIC);
					}
					if (myPojo.getUnidadesCaja() != null) {
						ps.setDouble(8, myPojo.getUnidadesCaja());
					} else {
						ps.setNull(8, Types.NUMERIC);
					}

					ps.setString(9, sessionId);
					ps.setLong(10, myPojo.getCodArticulo());
					ps.setLong(11, myPojo.getCodCentro());

				}

				@Override
				public int getBatchSize() {
					return listaDetalladoModif.size();
				}
			});
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(sql, null, e);
		}

	}

	@Override
	public Long sumBoxes(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer(" SELECT SUM(NVL(CANTIDAD,0)) " + " FROM T_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Long cont = null;

		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;

	}

	@Override
	public Long existsSType(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE TIPODETALLADO='S' ");
		// params.add(lang);
		StringBuffer query = new StringBuffer(" SELECT count(1) " + " FROM T_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
		}

		query.append(where);

		Long cont = null;
		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;

	}

	@Override
	public VSurtidoTienda findDatosTipoAprovisionamiento(Long codCentro, Long referencia) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		VSurtidoTienda vSurtidoTienda = new VSurtidoTienda();
		List<VSurtidoTienda> ListAprovisionamiento = null;
		where.append(" WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT TIPO_APROV, UNI_FORMA_PED, MARCA_MAESTRO_CENTRO, FECHA_GEN " + " FROM  V_SURTIDO_TIENDA");

		where.append(" AND COD_CENTRO = ? ");
		params.add(codCentro);

		where.append(" AND COD_ART = ? ");
		params.add(referencia);

		query.append(where);

		try {

			ListAprovisionamiento = this.jdbcTemplate.query(query.toString(), this.rwDetPedidoAprovisionamiento,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		if ((ListAprovisionamiento != null) && (ListAprovisionamiento.size() > 0)) {
			vSurtidoTienda = ListAprovisionamiento.get(0);
		} else {
			vSurtidoTienda.setTipoAprov("N");
			vSurtidoTienda.setUfp(new Float(0));
		}

		return vSurtidoTienda;
	}

	@Override
	public String comprobarReferenciaNueva(Long codCentro, Long referencia, Date fechaGen) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		where.append(" WHERE  1=1	   	");

		StringBuffer query = new StringBuffer(" SELECT MARCA_MAESTRO_CENTRO FROM SURTIDO_TIENDA");

		where.append(" AND COD_CENTRO = ? ");
		params.add(codCentro);

		where.append(" AND COD_ART = ? ");
		params.add(referencia);

		where.append(" AND  FECHA_GEN < TRUNC(?)");
		params.add(fechaGen);

		query.append(where);
		query.append(" ORDER BY FECHA_GEN DESC ");

		String MMC = null;
		List<String> listMMC = null;
		try {

			listMMC = this.jdbcTemplate.query(query.toString(), this.rwStringMap, params.toArray());
			if (!listMMC.isEmpty()) {
				MMC = listMMC.get(0);
				if (MMC == null) {
					MMC = "";
				}
			}

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return MMC;
	}

	@Override
	public int updateAllCodigoCaprabo(Long codCentro, String sesionID) throws Exception {

		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(" UPDATE T_DETALLADO_PEDIDO ");
		query.append(
				" SET REFERENCIA = (SELECT COD_ART_CAPRABO FROM V_SURTIDO_TIENDA WHERE T_DETALLADO_PEDIDO.CENTRO = V_SURTIDO_TIENDA.COD_CENTRO AND T_DETALLADO_PEDIDO.REFERENCIA = V_SURTIDO_TIENDA.COD_ART) ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE  1=1 ");
		where.append(" AND EXISTS (SELECT COD_ART_CAPRABO ");
		where.append("             FROM V_SURTIDO_TIENDA ");
		where.append("             WHERE T_DETALLADO_PEDIDO.CENTRO = V_SURTIDO_TIENDA.COD_CENTRO ");
		where.append("             AND T_DETALLADO_PEDIDO.REFERENCIA = V_SURTIDO_TIENDA.COD_ART ");
		where.append("             AND V_SURTIDO_TIENDA.COD_ART_CAPRABO IS NOT NULL) ");

		if (codCentro != null) {
			where.append(" AND T_DETALLADO_PEDIDO.CENTRO = ? ");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND T_DETALLADO_PEDIDO.IDDSESION = ? ");
			params.add(sesionID);
		}

		query.append(where);

		int cont = 0;
		try {

			cont = this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public int updateAllDescripcionCaprabo(Long codCentro, String sesionID) throws Exception {

		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(" UPDATE T_DETALLADO_PEDIDO ");
		query.append(
				" SET DESCRIPCION = (SELECT DESCRIP_ART FROM V_DAT_DIARIO_CAP WHERE T_DETALLADO_PEDIDO.REFERENCIA = V_DAT_DIARIO_CAP.COD_ART) ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE  1=1 ");

		if (codCentro != null) {
			where.append(" AND T_DETALLADO_PEDIDO.CENTRO = ? ");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND T_DETALLADO_PEDIDO.IDDSESION = ? ");
			params.add(sesionID);
		}

		query.append(where);

		int cont = 0;
		try {
			cont = this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public List<DetallePedido> findAllCaprabo(Long codCentro, String sesionID) throws Exception {

		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(
				" SELECT CENTRO, AREA , SECCION ,CATEGORIA ,SUBCATEGORIA, SEGMENTO ,REFERENCIA,DESCRIPCION, ");
		query.append(
				" STOCK,ENCURSO1,ENCURSO2, DECODE(TIPO_UFP,'U', TRUNC(UFP, 0), UNIDADESCAJA) UNIDADESCAJA ,  CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO, ");
		query.append(" HORALIMITE,ESTADO,IDDSESION ,ESTADOGRID , nvl(CODIGOERROR,'') CODIGOERROR, CANTIDADANT, ");
		query.append(
				" TEMPORADA,NUM_ORDEN,MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING,TIPO_APROV,TIPO, NIVEL_LOTE, REFERENCIA as ID, TIPO_UFP, UFP, SEG_DIA_PED, FLG_SIA, ");
		query.append(
				" FEC_ENTREGA,UNID_FL_EMPUJE,COD_NECESIDAD, REFERENCIA_EROSKI, DESCRIPCION_EROSKI, FLG_OFERTA, OFERTA, DEXENX, CEIL(DEXENX) DEXENXENTERO, PRECIO_COSTO_ARTICULO, DENOM_AREA, DENOM_SECCION, DENOM_CATEGORIA, DENOM_SUBCATEGORIA, DENOM_SEGMENTO, PRECIO_COSTO_INICIAL, PRECIO_COSTO_FINAL, ROTACION, REF_CUMPLE, AVISO");
		
		//MISUMI-300
		query.append( " , COD_MAPA, MOTIVO_PEDIDO ");
		
		query.append(" FROM T_detallado_pedido ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		if (codCentro != null) {
			where.append(" AND CENTRO = ? ");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sesionID);
		}

		query.append(where);

		List<DetallePedido> lista = null;
		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMap,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	@Override
	public void deleteAllNoCaprabo(Long codCentro, String sesionID) throws Exception {

		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(" DELETE ");
		query.append(" FROM T_DETALLADO_PEDIDO ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE  1=1 ");
		where.append(" AND NOT EXISTS (SELECT COD_ART_CAPRABO ");
		where.append("             FROM V_SURTIDO_TIENDA ");
		where.append("             WHERE T_DETALLADO_PEDIDO.CENTRO = V_SURTIDO_TIENDA.COD_CENTRO ");
		where.append("             AND T_DETALLADO_PEDIDO.REFERENCIA = V_SURTIDO_TIENDA.COD_ART_CAPRABO ");
		where.append("             AND V_SURTIDO_TIENDA.COD_ART_CAPRABO IS NOT NULL) ");

		if (codCentro != null) {
			where.append(" AND CENTRO = ? ");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sesionID);
		}

		query.append(where);
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	}

	@Override
	public DetallePedido findOne(Long referencia, String codCentro, String sesionID) throws Exception {

		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(" SELECT * FROM ( ");
		query.append(" SELECT CENTRO, AREA , SECCION ,CATEGORIA ,SUBCATEGORIA, SEGMENTO ,REFERENCIA,DESCRIPCION, ");
		query.append(
				" STOCK,ENCURSO1,ENCURSO2, DECODE(TIPO_UFP,'U', TRUNC(UFP, 0), UNIDADESCAJA) UNIDADESCAJA ,  CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO, ");
		query.append(" HORALIMITE,ESTADO,IDDSESION ,ESTADOGRID , nvl(CODIGOERROR,'') CODIGOERROR, CANTIDADANT, ");
		query.append(
				" TEMPORADA,NUM_ORDEN,MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING,TIPO_APROV,TIPO, NIVEL_LOTE, REFERENCIA as ID, TIPO_UFP, UFP, SEG_DIA_PED, FLG_SIA, ");
		query.append(
				" FEC_ENTREGA,UNID_FL_EMPUJE,COD_NECESIDAD, REFERENCIA_EROSKI, DESCRIPCION_EROSKI, FLG_OFERTA, OFERTA, DEXENX, CEIL(DEXENX) DEXENXENTERO, REF_CUMPLE, AVISO ");
		
		//MISUMI-300
		query.append( " , COD_MAPA, MOTIVO_PEDIDO ");
		
		query.append(" FROM T_detallado_pedido ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		if (referencia != null) {
			where.append(" AND REFERENCIA = ? ");
			params.add(referencia);
		}

		if (codCentro != null) {
			where.append(" AND CENTRO = ? ");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sesionID);
		}

		query.append(where);

		query.append(" ) WHERE ROWNUM = 1 ");

		List<DetallePedido> detallePedidoLista = null;
		try {
			detallePedidoLista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMap,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		DetallePedido detallePedidoRes = null;
		if (!detallePedidoLista.isEmpty()) {
			detallePedidoRes = detallePedidoLista.get(0);
		}
		return detallePedidoRes;
	}

	@Override
	public DetallePedidoLista referenciaNuevaSIA(Long referencia, Long codCentro) throws Exception {

		DetalladoSIA detalladoSIA = new DetalladoSIA();
		detalladoSIA.setCodLoc(codCentro);
		detalladoSIA.setCodArticulo(referencia);

		// Llamamos al DAO que llama al procedimient para obtener la referencia
		// nueva de SIA.
		DetalladoSIALista resultProc = this.detalladoSIADao.referenciaNueva(detalladoSIA);
		DetallePedidoLista detallePedidoLista = new DetallePedidoLista();

		List<DetallePedido> resultado = new ArrayList<DetallePedido>();
		if (resultProc != null) {
			resultado = tratarDatosObtenerProc(resultProc);
		}

		detallePedidoLista.setDatos(resultado);
		detallePedidoLista.setCodError(resultProc.getEstado());
		detallePedidoLista.setDescError(resultProc.getDescEstado());

		return detallePedidoLista;
	}

	@Override
	public Cronometro calculoCronometroYNumeroHorasLimite(DetallePedido detallePedido, String sesionID)
			throws Exception {
		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer(
				" SELECT MAX(HORALIMITE) AS HORA_LIMITE, COUNT(DISTINCT(DECODE(ESTADO,'I',NULL, HORALIMITE))) AS NUM_HORAS_LIMITE, COUNT(*) CUENTA ");
		query.append(" FROM T_DETALLADO_PEDIDO ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		if (detallePedido.getGrupo2() != null) {
			where.append(" AND SECCION = ? ");
			params.add(detallePedido.getGrupo2());
		}

		if (detallePedido.getGrupo3() != null) {
			where.append(" AND CATEGORIA = ? ");
			params.add(detallePedido.getGrupo3());
		}

		if (detallePedido.getCodCentro() != null) {
			where.append(" AND CENTRO = ? ");
			params.add(detallePedido.getCodCentro());
		}

		if (detallePedido.getCodArticulo() != null) {
			where.append(" AND REFERENCIA = ? ");
			params.add(detallePedido.getCodArticulo());
		}

		if (sesionID != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sesionID);
		}
		// where.append(" AND FLG_SIA = 'S' ");
		// where.append(" AND estado NOT IN ('B','I') ");
		// where.append(" AND HORALIMITE IS NOT NULL ");
		where.append(" AND ((TIPODETALLADO = 'A' AND HORALIMITE IS NOT NULL) OR TIPODETALLADO <> 'A') ");
		
		//MISUMI-300
		if (detallePedido.getCodMapa() != null) {
			where.append(" AND COD_MAPA = ? ");
			params.add(detallePedido.getCodMapa());
		}
		
		query.append(where);

		Cronometro cronometro = null;
		try {
			cronometro = (Cronometro) this.jdbcTemplate.queryForObject(query.toString(), this.rwCronoMap,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return cronometro;
	}

	@Override
	public Long countFlgPropuesta(String sessionId, Long center, Long seccion, Long categoria) throws Exception {
		StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		query.append("SELECT COUNT(*) ");

		query.append(" FROM T_DETALLADO_PEDIDO");
		query.append(" WHERE IDDSESION = ? ");
		query.append("   AND CENTRO = ?");
		query.append("   AND FLG_PROPUESTA IS NOT NULL");

		params.add(sessionId);
		params.add(center);

		if (seccion != null && seccion > 0) {
			query.append(" AND SECCION = ? ");
			params.add(seccion);
		}

		if (categoria != null && categoria > 0) {
			query.append(" AND CATEGORIA = ? ");
			params.add(categoria);
		}

		Long cont = new Long(0);

		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;

	}

	@Override
	public DetallePedido actualizarPrecioCostoFinal(DetallePedido detalladoPedido, String sessionId) throws Exception {

		// Completamos los campos del registro

		// Preparamos la SQL
		String updateSQL = "UPDATE T_DETALLADO_PEDIDO SET PRECIO_COSTO_FINAL = ?, CANTIDAD = ?, ESTADOGRID = ?, DIFERENCIA = null, AVISO = null "
				+ "WHERE IDDSESION=? AND REFERENCIA=?  AND CENTRO=? ";

		List<Object> values = new ArrayList<Object>();
		values.add(detalladoPedido.getPrecioCostoFinal());
		values.add(detalladoPedido.getCantidad());
		values.add("2");
		values.add(sessionId);
		values.add(detalladoPedido.getCodArticulo());
		values.add(detalladoPedido.getCodCentro());

		// Ejecutamos el comando SQL

		try {

			this.jdbcTemplate.update(updateSQL, values.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(updateSQL.toString(), values, e);
		}

		return detalladoPedido;
	}

	@Override
	public List<GestionEurosRefs> findSessionInfoGestionEurosRefs(DetallePedido detallePedido, String sesionID,
			Pagination pagination) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT REFERENCIA, COD_NECESIDAD, "
				+ " UNIDADESCAJA, PRECIO_COSTO_ARTICULO, PRECIO_COSTO_FINAL, CANTIDAD " + " FROM T_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(detallePedido.getGrupo4());
			}
			if (detallePedido.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(detallePedido.getGrupo5());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}

			if (detallePedido.getFlgOferta() != null) {
				if (detallePedido.getFlgOferta().equals("S")) {
					where.append(" AND FLG_OFERTA = ? ");
					params.add(detallePedido.getFlgOferta());
				} else {
					if (detallePedido.getFlgOferta().equals("N")) {
						where.append(" AND FLG_OFERTA is null ");
					}
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}

			if (detallePedido.getRotacion() != null) {
				String[] rotacionTipos = detallePedido.getRotacion().split("\\*");

				where.append(" AND ROTACION IN ( ");
				for (int i = 0; i < rotacionTipos.length; i++) {
					where.append(i + 1 == rotacionTipos.length ? "?" : " ?, ");
					params.add(rotacionTipos[i]);
				}
				where.append(" ) ");
			}

			where.append(" AND ESTADO IN ('P','R') ");
		}

		query.append(where);

		List<GestionEurosRefs> lista = null;
		try {

			lista = (List<GestionEurosRefs>) this.jdbcTemplate.query(query.toString(), this.rwGestionEurosMap,
					params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	@Override
	public DetallePedido findDatosVegalsaModif(DetallePedidoModificados detallePedidoModif, String idSession) throws Exception {
		
		List<Object> params = new ArrayList<Object>();

		String query = "SELECT TO_CHAR(fecha_pedido, 'YYYY-MM-DD') fecha_pedido, cod_mapa, horalimite, ufp"
						  + ", NVL((SELECT 'S' "
						  		 + "FROM relacion_articulo ra "
						  		 + "WHERE ra.cod_centro = dep.centro "
						  		 + "AND ra.cod_art_rela = dep.referencia "
						  		 + "AND tipo_rela = 2 "
						  		 + "AND a_b_m != 'B' "
						  		 + "AND fecha_gen = (SELECT MAX(fecha_gen)"
						  		 				  + "FROM relacion_articulo ra1 "
						  		 				  + "WHERE ra1.cod_centro = ra.cod_centro "
						  		 				  + "AND ra1.cod_art = ra.cod_art "
						  		 				  + "AND ra1.cod_art_rela = ra.cod_art_rela "
						  		 				  + "AND ra1.tipo_rela = ra.tipo_rela"
						  		 				  + ")"
						    	+ "), null) ffpp "
					 + "FROM t_detallado_pedido dep "
					 + "WHERE iddsesion = ? "
					 + "AND centro = ? "
					 + "AND referencia = ? "
					 + "AND flg_sia = 'N' "
					 + "AND ROWNUM = 1";

		params.add(idSession);
		params.add(detallePedidoModif.getCodCentro());
		params.add(detallePedidoModif.getCodArticulo());

		logger.info("---> findDatosVegalsaModif: " + query);
		
		DetallePedido detallePedido = null;
		
		try {
			detallePedido = jdbcTemplate.queryForObject(query, params.toArray(), DetallePedidoMapper);
		} catch (EmptyResultDataAccessException e) {
			logger.info("<--- findDatosVegalsaModif");
			return null;
		} catch (Exception ex){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, ex);
		}
		logger.info("<--- findDatosVegalsaModif");
		return detallePedido;
	}
	
	@Override
	public void updateGridStateGestionEurosRefs(final List<DetallePedido> listaDetalladoModif, final String sessionId)
			throws Exception {
		// TODO Auto-generated method stub
		// Completamos los campos del registro
		try {
			// Preparamos la SQL
			String sql = "update T_detallado_pedido set  estadoGrid= decode(cantidad,?,decode(?,'N', 4, estadogrid),2)  , CANTIDAD = ? "
					+ " , CODIGOERROR='', PRECIO_COSTO_FINAL = ?, DIFERENCIA = ?,  REF_CUMPLE = ?, AVISO = ?  where IDDSESION=? and REFERENCIA=? and CENTRO=? and Nvl(estadoGrid, '0') <> 9 ";

			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					DetallePedido myPojo = listaDetalladoModif.get(i);
					ps.setLong(1, myPojo.getCantidad());
					ps.setString(2, myPojo.getRefCumple());
					ps.setLong(3, myPojo.getCantidad());
					ps.setDouble(4, myPojo.getPrecioCostoFinal());
					ps.setLong(5, myPojo.getDiferencia());
					ps.setString(6, myPojo.getRefCumple());
					ps.setString(7, myPojo.getAviso());
					ps.setString(8, sessionId);
					ps.setLong(9, myPojo.getCodArticulo());
					ps.setLong(10, myPojo.getCodCentro());

				}

				@Override
				public int getBatchSize() {
					return listaDetalladoModif.size();
				}
			});
		} catch (Exception e) {

		}
	}

	@Override
	public void updatePrevioCalcular(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" UPDATE T_DETALLADO_PEDIDO SET DIFERENCIA=NULL, AVISO=NULL, REF_CUMPLE = DECODE(ROTACION,NULL,'N',NULL), ESTADOGRID = DECODE(ROTACION,NULL,4,DECODE(ESTADOGRID,3,NULL,ESTADOGRID)) ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}

		}

		where.append(" AND ESTADO IN ('P','R') ");

		query.append(where);

		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}

	@Override
	public void updatePropuestaInicial(DetallePedido detallePedido, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" UPDATE T_DETALLADO_PEDIDO set  CANTIDAD = PROPUESTA , PRECIO_COSTO_FINAL = PRECIO_COSTO_INICIAL, AVISO = null, DIFERENCIA = null, ESTADOGRID = null, CODIGOERROR = null ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}

			if (detallePedido.getFlgOferta() != null) {
				if (detallePedido.getFlgOferta().equals("S")) {
					where.append(" AND FLG_OFERTA = ? ");
					params.add(detallePedido.getFlgOferta());
				} else {
					if (detallePedido.getFlgOferta().equals("N")) {
						where.append(" AND FLG_OFERTA is null ");
					}
				}
			}

			if (sesionID != null) {
				where.append(" AND IDDSESION = ? ");
				params.add(sesionID);
			}
			/*
			 * if(detallePedido.getRotacion() != null){ String[] rotacionTipos =
			 * detallePedido.getRotacion().split("\\*");
			 * 
			 * where.append(" AND ROTACION IN ( "); for (int i = 0; i <
			 * rotacionTipos.length; i++){
			 * where.append(rotacionTipos[i].toString()).append((i+1 ==
			 * rotacionTipos.length) ? "" : " , "); } where.append(" ) "); }
			 */

			if (detallePedido.getRotacion() != null) {
				String[] rotacionTipos = detallePedido.getRotacion().split("\\*");

				where.append(" AND ROTACION IN ( ");
				for (int i = 0; i < rotacionTipos.length; i++) {
					where.append(i + 1 == rotacionTipos.length ? "?" : " ?, ");
					params.add(rotacionTipos[i]);
				}
				where.append(" ) ");
			}

			where.append(" AND ESTADO IN ('P','R') ");
		}

		query.append(where);

		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}

	@Override
	public int countEmpuje(String sessionId, DetallePedido detallePedido) throws Exception {
		// TODO Auto-generated method stub

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer(" SELECT count(*) " + " FROM T_detallado_pedido ");

		if (detallePedido != null) {
			if (detallePedido.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(detallePedido.getGrupo2());
			}
			if (detallePedido.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(detallePedido.getGrupo3());
			}
			if (detallePedido.getCodArticulo() != null) {
				where.append(" AND REFERENCIA = ? ");
				params.add(detallePedido.getCodArticulo());
			}
			if (detallePedido.getFlgIncluirPropPed() != null) {
				if (("N").equals(detallePedido.getFlgIncluirPropPed())) {
					where.append(" AND (PROPUESTA <> 0 OR CANTIDAD <> 0 or UNID_FL_EMPUJE<>0) ");
				}
			}
		}
		if (sessionId != null) {
			where.append(" AND IDDSESION = ? ");
			params.add(sessionId);
		}

		query.append(where);

		int cont = 0;

		try {

			cont = this.jdbcTemplate.queryForObject(query.toString(), Integer.class, params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}

	@Override
	public int countCentroVegalsa(Long codCentro) throws Exception {

		String query = "SELECT count(cod_centro) " + " FROM v_centros_plataformas " + " WHERE cod_centro=?"
				+ " AND cod_soc = 13";

		int cont = 0;

		try {

			cont = this.jdbcTemplate.queryForObject(query, new Object[] { codCentro }, Integer.class);

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(),
					new ArrayList<Object>(Arrays.asList(new Object[] { codCentro })), e);
		}

		return cont;
	}

	@Override
	public Long countDetallePedidoFromSIA(Long codCentro, String sesionID) throws Exception {

		String query = "SELECT count(centro) " + " FROM T_DETALLADO_PEDIDO " + " WHERE centro = ? "
				+ " AND IDDSESION = ?" + " AND FLG_SIA = 'S'";
		
		Long cont = new Long(0);

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(sesionID);
		
		try {

			cont = this.jdbcTemplate.queryForObject(query, params.toArray(), Long.class);

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return cont;
	}
	
	@Override
	public List<DetallePedido> getDetalladoPedidoVegalsa(Long codCentro) throws Exception {

		logger.info("getDetalladoPedidoVegalsa --->");

		String query = "SELECT *"
					 + "FROM (SELECT fecha_pedido"
						  + ", cod_centro AS centro"
						  + ", grupo1 AS area"
						  + ", grupo2 AS seccion"
						  + ", grupo3 AS categoria"
						  + ", grupo4 AS subcategoria"
						  + ", grupo5 AS segmento"
						  + ", cod_art AS referencia"
						  + ", denom_ref AS descripcion"
						  + ", stock_tienda AS stock"
						  + ", pendiente_tienda AS encurso1"
						  + ", pendiente_tienda_manana AS encurso2"
						  + ", unid_caja AS unidadescaja"
						  + ", unid_encargo_fl AS cajaspedidas"
						  + ", CASE WHEN (SELECT 1 "
						  			   + "FROM t_mis_detallado_vegalsa_modif dem "
						  			   + "WHERE dem.fecha_pedido = TRUNC(t.fecha_pedido) "
						  			   + "AND dem.cod_centro = t.cod_centro "
						  			   + "AND dem.cod_art = t.cod_art"
						  			   + ") IS NULL THEN unid_propuestas_fl_origen "
						    + "ELSE (SELECT dem.cantidad_propuesta "
						    	  + "FROM t_mis_detallado_vegalsa_modif dem "
						    	  + "WHERE dem.fecha_pedido = TRUNC(t.fecha_pedido) "
						    	  + "AND dem.cod_centro = t.cod_centro "
						    	  + "AND dem.cod_art = t.cod_art"
						    	  + ") "
						    + "END propuesta"
						  + ", CASE WHEN (SELECT 1 "
						  			   + "FROM t_mis_detallado_vegalsa_modif dem "
						  			   + "WHERE dem.fecha_pedido = TRUNC(t.fecha_pedido) "
						  			   + "AND dem.cod_centro = t.cod_centro "
						  			   + "AND dem.cod_art = t.cod_art "
						  			   + ") IS NULL THEN unid_propuestas_fl_modif "
						    + "ELSE (SELECT dem.cantidad "
						    	  + "FROM t_mis_detallado_vegalsa_modif dem "
						    	  + "WHERE dem.fecha_pedido = TRUNC(t.fecha_pedido) "
						    	  + "AND dem.cod_centro = t.cod_centro "
						    	  + "AND dem.cod_art = t.cod_art "
						    	  + ") "
						    + "END cantidad"
						  + ", tipo_detallado AS tipodetallado"
						  + ", SUBSTR(hora_limite, 1, 2)||':'||SUBSTR(hora_limite, 3, 2) AS horalimite"
						  + ", CASE WHEN (estado = 'P' "
						  			   + "AND ((TRUNC(SYSDATE) = TRUNC(fecha_pedido) AND SUBSTR(hora_limite, 1, 2)||':'||SUBSTR(hora_limite, 3, 2) >= TO_CHAR(SYSDATE, 'HH24:MI')) "
						  			   		+ "OR TRUNC(SYSDATE) < TRUNC(fecha_pedido) "
						  			   		+ ")"
						  			   	+ ") THEN "
						  			   	+ "(CASE WHEN (SELECT 1 "
						  			   				+ "FROM t_mis_detallado_vegalsa_modif dem "
						  			   				+ "WHERE dem.fecha_pedido = TRUNC(t.fecha_pedido) "
						  			   				+ "AND dem.cod_centro = t.cod_centro "
						  			   				+ "AND dem.cod_art = t.cod_art "
						  			   				+ ") IS NULL THEN 'P' "
						  			   	 + "ELSE 'R' "
						  			   	 + "END"
						  			   	+ ") "
						  		 + "WHEN (estado = 'P' "
						  		 	   + "AND TRUNC(SYSDATE) = TRUNC(fecha_pedido) "
						  		 	   + "AND SUBSTR(hora_limite, 1, 2)||':'||SUBSTR(hora_limite, 3, 2) < TO_CHAR(SYSDATE, 'HH24:MI') "
						  		 	   + ") THEN 'I' "
						  		 + "ELSE estado "
						  		 + "END estado" 
						  + ", '' AS estadogrid"
						  + ", '' AS codigoerror"
//						  + ", '' AS cantidadant"
						  + ", temporada AS temporada"
						  + ", num_orden AS num_orden"
						  + ", modelo_proveedor AS modelo_proveedor"
						  + ", descr_talla AS descr_talla"
						  + ", descr_color AS descr_color"
						  + ", (SELECT f.facing FROM t_mis_surtido_vegalsa f WHERE f.cod_centro = t.cod_centro AND f.cod_art = t.cod_art AND fecha_gen in (select max(fecha_gen)from t_mis_surtido_vegalsa t2 where t2.cod_Centro = t.cod_centro and t2.cod_art = t.cod_art)) AS facing"
						  + ", tipo_aprov AS tipo_aprov"
						  + ", ufp AS ufp"
						  + ", CASE WHEN TRUNC(fecha_pedido) > TRUNC(SYSDATE) THEN TIPO||'F' "
						    + "ELSE tipo "
						    + "END AS tipo"
						  + ", nivel_lote AS nivel_lote"
					      + ", CASE WHEN ufp > 0 THEN 'U' "
						    + "ELSE null "
						    + "END AS tipo_ufp"
						  + ", TO_CHAR(fec_pedido_prox,'ddMMyyyy') AS seg_dia_ped"
						  + ", 'N' AS flg_sia"
						  + ", fec_entrega AS fec_entrega"
						  + ", unid_fl_empuje AS unid_fl_empuje"
						  + ", '' AS cod_necesidad"
						  + ", cod_art AS referencia_eroski"
						  + ", denom_ref AS descripcion_eroski"
						  + ", DECODE(flg_oferta, 'N', NULL, flg_oferta) AS flg_oferta"
						  + ", oferta AS oferta"
						  + ", tipo_oferta AS tipo_oferta"
						  + ", conver_art AS conver_art"
					      + ", CASE WHEN ufp > 0 THEN ufp/unid_caja "
						    + "ELSE null "
						    + "END AS dexenx"
						  + ", 'X' AS flg_propuesta"
						  + ", '' AS aviso"
						  + ", denom_area AS denom_area"
						  + ", denom_seccion AS denom_seccion"
						  + ", denom_categoria AS denom_categoria"
						  + ", denom_subcategoria AS denom_subcategoria"
						  + ", denom_segmento AS denom_segmento"
						  + ", precio_costo_articulo AS precio_costo_articulo"
						  + ", precio_costo_art_linea_inicial AS precio_costo_inicial"
						  + ", precio_costo_art_linea_final AS precio_costo_final"
						  + ", '' AS diferencia"
						  + ", '' AS rotacion"
						  + ", '' AS ref_cumple"
						  + ", mapa"
						  + ", motivo_pedido" 
						  + ", cajas_cortadas"
						  + ", inc_prevision_venta"
						  + ", sm_estatico"
						  + ", MAX(fecha_pedido) KEEP (dense_rank FIRST ORDER BY fecha_pedido DESC) "
						    + "OVER (PARTITION BY cod_centro, mapa) max_fecha_pedido "
				+ "FROM t_mis_detallado_vegalsa t "
				+ "WHERE cod_centro = ? "
				+ "AND fecha_pedido >= TRUNC(SYSDATE) "
				+ "AND EXISTS (SELECT 's' FROM t_mis_surtido_VEGALSA s where s.cod_centro=t.cod_centro and s.cod_art = t.cod_art and s.MARCA_MAESTRO_CENTRO='S' "
				+ "AND S.FECHA_GEN IN (SELECT MAX(FECHA_GEN) FROM T_MIS_SURTIDO_VEGALSA S2 WHERE S2.COD_CENTRO = S.COD_cENTRO AND S2.COD_ART = S.COD_ART))) "
/*
				+ "AND fecha_pedido IN (SELECT MAX(t2.fecha_pedido) "
			            			 + "FROM t_mis_detallado_vegalsa t2 "
			            			 + "WHERE t2.cod_centro = t.cod_centro "
			            			 + "AND t2.mapa 		= t.mapa "
			            			 + "AND t2.fecha_pedido >= TRUNC(SYSDATE)"
			            			 + ")";
*/
				+ "WHERE fecha_pedido = max_fecha_pedido";
		
		List<DetallePedido> lstDetallePedido = null;
		
		try {
			lstDetallePedido = (List<DetallePedido>) this.jdbcTemplate.query(query, new Object[]{codCentro}, DetalladoPedidoVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, Arrays.asList(new Object[]{codCentro}), e);
		}

		logger.info("<--- getDetalladoPedidoVegalsa");
		
		return lstDetallePedido;
	}
	
	@Override
	public void insertVegalsaModif(DetallePedido detalladoPedido, HttpSession session) throws DataIntegrityViolationException, DataAccessException, Exception {
		
		User usuario = (User)session.getAttribute("user");
		String codigoUsuario = usuario.getCode();
		
		String insertSQL = "INSERT INTO t_mis_detallado_vegalsa_modif "
					 		+ "( fecha_pedido"
					 		+ ", cod_centro"
					 		+ ", cod_art"
					 		+ ", cantidad"
					 		+ ", cantidad_propuesta"
					 		+ ", mapa"
					 		+ ", hora_limite"
					 		+ ", ufp"
					 		+ ", ffpp"
					 		+ ", uc"
					 		+ ", usuario_creacion"
					 		+ ", usuario_modif"
					 		+ ") "
					 	+ "VALUES "
					 		+ "( ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ", ?"
					 		+ ")";

		List<Object> values = new ArrayList<Object>();
		values.add(Utilidades.convertirStringAFechaSqlDate(detalladoPedido.getFechaPedido()));
		values.add(detalladoPedido.getCodCentro());
		values.add(detalladoPedido.getCodArticulo());
		values.add(detalladoPedido.getCantidad());
		values.add(detalladoPedido.getCantidadOriginal());
		values.add(detalladoPedido.getCodMapa());
		values.add(detalladoPedido.getHoraLimite().substring(0, detalladoPedido.getHoraLimite().indexOf(":"))
					+detalladoPedido.getHoraLimite().substring(detalladoPedido.getHoraLimite().indexOf(":")+1));
		values.add(detalladoPedido.getUfp());
		values.add(detalladoPedido.getFFPP()); // ffpp
		values.add(detalladoPedido.getUnidadesCaja());
		values.add(codigoUsuario); // usuario_creacion
		values.add(codigoUsuario); // usuario_modif

		// Ejecutamos el comando SQL
//		try {
			this.jdbcTemplate.update(insertSQL, values.toArray());
//		} catch (DataIntegrityViolationException din) {
//			return -1;
//		} catch (DataAccessException dae) {
//			return -2;
//		} catch (Exception e){
//			Utilidades.mostrarMensajeErrorSQL(insertSQL.toString(), values, e);
//		}

//		return 0;
	}

	/**
	 * Modificación de registros modificados de Vegalsa en el GRID.
	 * @param dpm
	 * @return
	 */
	@Override
	public void updateVegalsaModif(DetallePedido detallePedido, HttpSession session) throws DataAccessException, Exception{
		User usuario = (User)session.getAttribute("user");
		String codigoUsuario = usuario.getCode();

		String updateSQL = "UPDATE t_mis_detallado_vegalsa_modif "
						 + "SET cantidad 			= ?"
						   + ", usuario_modif 		= ?"
						   + ", last_update_date	= CURRENT_TIMESTAMP "
						 + "WHERE fecha_pedido 	= ? "
						 + "AND cod_centro 		= ? "
						 + "AND cod_art 		= ?";

		List<Object> values = new ArrayList<Object>();
		values.add(detallePedido.getCantidad());
		values.add(codigoUsuario); // usuario_modif
		values.add(Utilidades.convertirStringAFechaSqlDate(detallePedido.getFechaPedido()));
		values.add(detallePedido.getCodCentro());
		values.add(detallePedido.getCodArticulo());

		// Ejecutamos el comando SQL
//		try {
			this.jdbcTemplate.update(updateSQL, values.toArray());
//		} catch (DataAccessException dae) {
//			return -1;
//		} catch (Exception e){
//			Utilidades.mostrarMensajeErrorSQL(updateSQL.toString(), values, e);
//		}
		
//		return 0;
	}
	
	@Override
	public List<MapaVegalsa> getMapasVegalsaByCenterAndIdSessionId(Long codCentro, String sesionID) throws Exception {
		logger.info("getLstMapasVegalsa --> ");
		
		String query = "SELECT DISTINCT COD_MAPA , RTRIM(DESC_MAPA) DESC_MAPA "
					+ "FROM T_MIS_MAPAS_VEGALSA tmmv WHERE EXISTS "
						+ " ( SELECT 'x' FROM T_DETALLADO_PEDIDO d "
						+ " WHERE CENTRO = ? AND iddsesion= ? "
						+ " AND tmmv.cod_mapa = d.cod_mapa )"
					+ " ORDER BY COD_MAPA";

		List<MapaVegalsa> lstMapas = null;
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(sesionID);
		
		try {
			lstMapas = (List<MapaVegalsa>) this.jdbcTemplate.query(query, params.toArray(), MapaVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, params, e);
		}

		return lstMapas;
	}
	private RowMapper<MapaVegalsa> MapaVegalsaMapper = new RowMapper<MapaVegalsa>() {
		public MapaVegalsa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new MapaVegalsa(resultSet.getInt("COD_MAPA"), resultSet.getString("DESC_MAPA"));
		}
	};

	@Override
	public List<PropuestaDetalladoVegalsa> resumenPropuestaVegalsa(Long codCentro, Long codMapa) {
		logger.info("resumenPropuestaVegalsa --> ");
		
		String query = "SELECT ID_PEDIDO,COD_MAPA,"
				+ " DECODE(HORA_LANZA,NULL,NULL,SUBSTR(LPAD(HORA_LANZA,4,'0'),1,2)||':'||SUBSTR(LPAD(HORA_LANZA,4,'0'),3,2)) HORA_LANZA_FORMATTED, "
				+ " FECHA_REPO,JORNADA,UMBRAL_PALETS,PALETS_PEDIDOS,PALETS_CORTADOS,PALETS_ADELANTADOS,FFPP_UFP,"
				+ " CAJAS_PEDIDAS,CAJAS_CORTADAS,CAJAS_ADELANTADAS,FECHA_SIG_PEDIDO, "
				+ " NVL((SELECT SUM(CANTIDAD)-SUM(CANTIDAD_PROPUESTA) FROM T_MIS_DETALLADO_VEGALSA_MODIF MODIF"
				+ " WHERE MODIF.COD_CENTRO=R.COD_CENTRO AND MODIF.MAPA=R.COD_MAPA AND FFPP IS NULL AND UFP =0 AND MODIF.FECHA_PEDIDO = R.FECHA_PEDIDO),0) CAJAS_EDIT," 
				+ " NVL((SELECT SUM(CANTIDAD)-SUM(CANTIDAD_PROPUESTA) FROM T_MIS_DETALLADO_VEGALSA_MODIF MODIF"
				+ "	WHERE MODIF.COD_CENTRO=R.COD_CENTRO AND MODIF.MAPA=R.COD_MAPA AND FFPP IS NOT NULL AND MODIF.FECHA_PEDIDO = R.FECHA_PEDIDO),0) "
				+ "	+ "
				+ "	NVL((SELECT SUM(CANTIDAD*UC/UFP) -SUM(CANTIDAD_PROPUESTA*UC/UFP) FROM T_MIS_DETALLADO_VEGALSA_MODIF MODIF"
				+ "	WHERE MODIF.COD_CENTRO=R.COD_CENTRO AND MODIF.MAPA=R.COD_MAPA AND  UFP > 0 AND MODIF.FECHA_PEDIDO = R.FECHA_PEDIDO),0) UFP_FFPP_EDIT"
				+ " FROM T_MIS_RESUMEN_DETALLADO R "
				+ " WHERE COD_CENTRO = ? AND TRUNC(FECHA_PEDIDO) >= TRUNC(SYSDATE) AND "
				+ " R.fecha_pedido IN ( SELECT max(t2.fecha_pedido) FROM t_mis_resumen_detallado t2 WHERE t2.cod_centro = R.cod_centro AND t2.cod_mapa = R.cod_mapa) ";
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		if (codMapa != 0L){
			query += " AND COD_MAPA = ? ";
			params.add(codMapa);
		}
		query+=" ORDER BY COD_MAPA ";
		List<PropuestaDetalladoVegalsa> output = null;
		
		try {
			output = (List<PropuestaDetalladoVegalsa>) this.jdbcTemplate.query(query, params.toArray(), PropuestaDetalladoVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, params, e);
		}

		return output;
	}
	
	private RowMapper<PropuestaDetalladoVegalsa> PropuestaDetalladoVegalsaMapper = new RowMapper<PropuestaDetalladoVegalsa>() {
		public PropuestaDetalladoVegalsa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			final PropuestaDetalladoVegalsa item =  new PropuestaDetalladoVegalsa();
			
			final Long idPedido = resultSet.getLong("ID_PEDIDO");
			final Long codMapa = resultSet.getLong("COD_MAPA");
			final String horaLanzamiento = resultSet.getString("HORA_LANZA_FORMATTED");
			final Date fechaReposicion = resultSet.getDate("FECHA_REPO");
			final String jornadaReposicion = resultSet.getString("JORNADA");
			final Double umbralPalets = resultSet.getDouble("UMBRAL_PALETS");
			final Double paletsPedidos = resultSet.getDouble("PALETS_PEDIDOS");
			final Double paletsCortados = resultSet.getDouble("PALETS_CORTADOS");
			final Double paletsAdelantados = resultSet.getDouble("PALETS_ADELANTADOS");
			final Long ffppUfp = resultSet.getLong("FFPP_UFP");
			final Long cajasPedidas = resultSet.getLong("CAJAS_PEDIDAS");
			final Long cajasCortadas = resultSet.getLong("CAJAS_CORTADAS");
			final Long cajasAdelantadas = resultSet.getLong("CAJAS_ADELANTADAS");
			final Date fechaSigPedido = resultSet.getDate("FECHA_SIG_PEDIDO");	
			final Long ffppUfpEdit = resultSet.getLong("UFP_FFPP_EDIT");
			final Long cajasPedidasEdit = resultSet.getLong("CAJAS_EDIT");

			item.setIdPedido(idPedido);
			item.setCodMapa(codMapa);
			item.setHoraLanzamiento(horaLanzamiento);
			item.setFechaReposicion(fechaReposicion);
			item.setJornadaReposicion(jornadaReposicion);
			item.setUmbralPalets(umbralPalets);
			item.setPaletsPedidos(paletsPedidos);
			item.setPaletsCortados(paletsCortados);
			item.setPaletsAdelantados(paletsAdelantados);
			item.setFfppUfp(ffppUfp);
			item.setCajasPedidas(cajasPedidas);
			item.setCajasCortadas(cajasCortadas);
			item.setCajasAdelantadas(cajasAdelantadas);
			item.setFechaSigPedido(fechaSigPedido);
			item.setFfppUfpEdit(ffppUfpEdit);
			item.setCajasPedidasEdit(cajasPedidasEdit);
			return item;			

		}
	};

	private RowMapper<DetallePedido> DetalladoPedidoVegalsaMapper = new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			DetallePedido item =  new DetallePedido();
			
			item.setFechaPedido(resultSet.getString("fecha_pedido").substring(0, 10));
			item.setCodCentro(resultSet.getLong("centro"));
			item.setGrupo1(resultSet.getLong("area"));
			item.setGrupo2(resultSet.getLong("seccion"));
			item.setGrupo3(resultSet.getLong("categoria"));
			item.setGrupo4(resultSet.getLong("subcategoria"));
			item.setGrupo5(resultSet.getLong("segmento"));
			item.setCodArticulo(resultSet.getLong("referencia"));
			item.setDescriptionArt(resultSet.getString("descripcion"));
			item.setStock(resultSet.getDouble("stock"));
			item.setEnCurso1(resultSet.getDouble("encurso1"));
			item.setEnCurso2(resultSet.getDouble("encurso2"));
			item.setUnidadesCaja(resultSet.getDouble("unidadescaja"));
			item.setCajasPedidas(resultSet.getLong("cajaspedidas"));
			item.setPropuesta(resultSet.getLong("propuesta"));
			item.setCantidad(resultSet.getLong("cantidad"));
			item.setTipoDetallado(resultSet.getString("tipodetallado"));
			item.setHoraLimite(resultSet.getString("horalimite"));
			item.setEstadoPedido(resultSet.getString("estado"));
			item.setEstadoGrid(null);
			item.setCodError(null);
			item.setTemporada(resultSet.getString("temporada"));
			item.setNumOrden(resultSet.getLong("num_orden"));
			item.setModeloProveedor(resultSet.getString("modelo_proveedor"));
			item.setDescrTalla(resultSet.getString("descr_talla"));
			item.setDescrColor(resultSet.getString("descr_color"));
			item.setFacing(resultSet.getDouble("facing"));
			item.setTipoAprovisionamiento(resultSet.getString("tipo_aprov"));
			item.setUfp(resultSet.getDouble("ufp"));
			item.setTipo(resultSet.getString("tipo"));
			item.setNivelLote(resultSet.getLong("nivel_lote"));
			item.setTipoUFP(resultSet.getString("tipo_ufp"));
			item.setNextDayPedido(resultSet.getString("seg_dia_ped"));
			item.setFlgSIA(resultSet.getString("flg_sia"));//N
			item.setFechaEntrega(resultSet.getDate("fec_entrega"));
			item.setUnidFlEmpuje(resultSet.getLong("unid_fl_empuje"));
			item.setCodNecesidad(null);
			item.setCodArticuloEroski(resultSet.getLong("referencia_eroski"));
			item.setDescriptionArtEroski(resultSet.getString("descripcion_eroski"));
			item.setFlgOferta(resultSet.getString("flg_oferta"));
			item.setOferta(resultSet.getString("oferta"));
			item.setTipoOferta(resultSet.getLong("tipo_oferta"));
			item.setConverArt(resultSet.getString("conver_art"));
			item.setDexenx(resultSet.getDouble("dexenx"));
			item.setFlgPropuesta(resultSet.getString("flg_propuesta"));
			item.setAviso(resultSet.getString("aviso"));
			item.setDenomArea(resultSet.getString("denom_area"));
			item.setDenomSeccion(resultSet.getString("denom_SECCION"));
			item.setDenomCategoria(resultSet.getString("denom_CATEGORIA"));
			item.setDenomSubcategoria(resultSet.getString("denom_SUBCATEGORIA"));
			item.setDenomSegmento(resultSet.getString("denom_SEGMENTO"));
			item.setPrecioCostoArticulo(resultSet.getDouble("PRECIO_COSTO_ARTICULO"));
			item.setPrecioCostoInicial(resultSet.getDouble("PRECIO_COSTO_INICIAL"));
			//MISUMI-463
			if(item.getCantidad()==0L){
				item.setPrecioCostoFinal(new Double(0));
			}else{
				Double precioCostoFinal=item.getCantidad().doubleValue()*item.getPrecioCostoArticulo().doubleValue()*item.getUnidadesCaja();
				item.setPrecioCostoFinal(precioCostoFinal);
			}
			item.setDiferencia(null);
			item.setRotacion(null);
			item.setRefCumple(null);
			item.setCodMapa(resultSet.getInt("mapa"));
			item.setMotivoPedido(resultSet.getString("motivo_pedido"));
			item.setCajasCortadas(resultSet.getLong("cajas_cortadas"));
			item.setIncPrevisionVenta(resultSet.getString("inc_prevision_venta"));
//			item.setIncPrevisionVenta(resultSet.getString("inc_prevision_venta")==""?"":resultSet.getString("inc_prevision_venta")+"%");
			item.setSmEstatico(resultSet.getLong("sm_estatico"));
			
			return item;			

		}
	};

	private RowMapper<DetallePedido> DetallePedidoMapper = new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DetallePedido item = new DetallePedido();
			
			item.setFechaPedido(resultSet.getString("fecha_pedido"));
			item.setCodMapa(resultSet.getInt("cod_mapa"));
			item.setHoraLimite(resultSet.getString("horalimite"));
			item.setUfp(resultSet.getDouble("ufp"));
			item.setFFPP(resultSet.getString("ffpp"));
			
			return item;			
		}
	};
	
	@Override
	public List<DetallePedido> referenciasSinPLUAAniadir(Long codCentro,  String idSesion) throws Exception {

		StringBuffer query = new StringBuffer("SELECT GRUPO_BALANZA,DENOMINACION_BALANZA,CENTRO,AREA,SECCION,CATEGORIA,SUBCATEGORIA,SEGMENTO,REFERENCIA,DESCRIPCION,T.STOCK,ENCURSO1,ENCURSO2,UNIDADESCAJA, "
				+ "CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO,HORALIMITE,ESTADO,IDDSESION,ESTADOGRID,nvl(CODIGOERROR, '') CODIGOERROR,CANTIDADANT,TEMPORADA,NUM_ORDEN, "
				+ "MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING,TIPO_APROV,TIPO,NIVEL_LOTE,REFERENCIA AS ID,TIPO_UFP,UFP,SEG_DIA_PED,FLG_SIA,FEC_ENTREGA,UNID_FL_EMPUJE, "
				+ "COD_NECESIDAD,REFERENCIA_EROSKI,DESCRIPCION_EROSKI,FLG_OFERTA,OFERTA,DEXENX,CEIL(DEXENX) DEXENXENTERO,PRECIO_COSTO_ARTICULO,DENOM_AREA,DENOM_SECCION, "
				+ "DENOM_CATEGORIA,DENOM_SUBCATEGORIA,DENOM_SEGMENTO,PRECIO_COSTO_INICIAL,PRECIO_COSTO_FINAL,ROTACION,DIFERENCIA,REF_CUMPLE,AVISO,COD_MAPA,MOTIVO_PEDIDO  "
				+ "FROM t_detallado_pedido T,t_mis_alarmas_plu al WHERE 1 = 1 AND CENTRO = ? AND EXISTS ( SELECT 'S' FROM v_mis_param_centros_opc WHERE cod_loc = CENTRO "
				+ "AND OPC_HABIL = '107_DETALLADO_ALARMAS_PLU') AND iddsesion = ? AND (ENCURSO1 > 0 OR ENCURSO2 > 0 OR CANTIDAD > 0) AND EXISTS (SELECT "
				+ "'S' FROM t_mis_param_agru_plu O WHERE T.AREA = O.COD_AREA AND T.SECCION = O.COD_SECCION AND T.CATEGORIA = O.COD_CATEGORIA) AND centro = al.cod_centro(+) "
				+ "AND referencia = al.cod_articulo(+) AND fecha_gen(+) >= trunc(sysdate) AND grupo_balanza IS NULL AND NOT EXISTS ( SELECT 'S' FROM t_mis_alarmas_plu T "
				+ "WHERE T.COD_CENTRO = CENTRO AND T.COD_ARTICULO = REFERENCIA AND T.FECHA_GEN >= TRUNC(SYSDATE)-10 AND T.PLU <> 0 ) AND EXISTS ( SELECT 'S' FROM "
				+ "v_datos_diario_art D, DATOS_DIARIO_ART DD WHERE D.COD_ART = REFERENCIA AND D.BALANZA > 0 AND D.FORMATO = 'KG' AND DD.COD_ART = D.COD_ART "
				+ "AND DD.FECHA_GEN = D.FECHA_GEN AND DD.KILOS_UNI = 1 AND DD.PESO_VARI = 'S' AND D.TIPO_COMPRA_VENTA = 'T') AND NOT EXISTS ( SELECT 'S' FROM "
				+ "HISTORICO_UNIDADES_VENTA H WHERE H.COD_LOC = T.CENTRO AND H.COD_ARTICULO = T.REFERENCIA AND H.FECHA_VENTA >= TRUNC(SYSDATE)-10)");
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(idSesion);

		List<DetallePedido> lista = null;
		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMapSinPlu,params.toArray());
		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}
	
	@Override
	public List<DetallePedido> referenciasSinPLUAMostrar(Long codCentro,  String idSesion, Long seccionId) throws Exception {

		StringBuffer query = new StringBuffer("SELECT GRUPO_BALANZA,DENOMINACION_BALANZA,CENTRO,AREA,SECCION,CATEGORIA,SUBCATEGORIA,SEGMENTO,REFERENCIA,DESCRIPCION,T.STOCK,ENCURSO1,ENCURSO2,UNIDADESCAJA, "
				+ "CAJASPEDIDAS,PROPUESTA,CANTIDAD,TIPODETALLADO,HORALIMITE,ESTADO,IDDSESION,ESTADOGRID,nvl(CODIGOERROR, '') CODIGOERROR,CANTIDADANT,TEMPORADA,NUM_ORDEN, "
				+ "MODELO_PROVEEDOR,DESCR_TALLA,DESCR_COLOR,FACING,TIPO_APROV,TIPO,NIVEL_LOTE,REFERENCIA AS ID,TIPO_UFP,UFP,SEG_DIA_PED,FLG_SIA,FEC_ENTREGA,UNID_FL_EMPUJE, "
				+ "COD_NECESIDAD,REFERENCIA_EROSKI,DESCRIPCION_EROSKI,FLG_OFERTA,OFERTA,DEXENX,CEIL(DEXENX) DEXENXENTERO,PRECIO_COSTO_ARTICULO,DENOM_AREA,DENOM_SECCION, "
				+ "DENOM_CATEGORIA,DENOM_SUBCATEGORIA,DENOM_SEGMENTO,PRECIO_COSTO_INICIAL,PRECIO_COSTO_FINAL,ROTACION,DIFERENCIA,REF_CUMPLE,AVISO,COD_MAPA,MOTIVO_PEDIDO  "
				+ "FROM t_detallado_pedido T,t_mis_alarmas_plu al WHERE 1 = 1 AND CENTRO = ? AND EXISTS ( SELECT 'S' FROM v_mis_param_centros_opc WHERE cod_loc = CENTRO "
				+ "AND OPC_HABIL = '107_DETALLADO_ALARMAS_PLU') AND iddsesion = ? AND (ENCURSO1 > 0 OR ENCURSO2 > 0 OR CANTIDAD > 0) AND EXISTS (SELECT "
				+ "'S' FROM t_mis_param_agru_plu O WHERE T.AREA = O.COD_AREA AND T.SECCION = O.COD_SECCION AND T.CATEGORIA = O.COD_CATEGORIA) AND centro = al.cod_centro(+) "
				+ "AND referencia = al.cod_articulo(+) AND fecha_gen(+) >= trunc(sysdate) AND grupo_balanza IS NOT NULL AND NOT EXISTS ( SELECT 'S' FROM t_mis_alarmas_plu T "
				+ "WHERE T.COD_CENTRO = CENTRO AND T.COD_ARTICULO = REFERENCIA AND T.FECHA_GEN >= TRUNC(SYSDATE)-10 AND T.PLU <> 0 ) AND EXISTS ( SELECT 'S' FROM "
				+ "v_datos_diario_art D, DATOS_DIARIO_ART DD WHERE D.COD_ART = REFERENCIA AND D.BALANZA > 0 AND D.FORMATO = 'KG' AND DD.COD_ART = D.COD_ART "
				+ "AND DD.FECHA_GEN = D.FECHA_GEN AND DD.KILOS_UNI = 1 AND DD.PESO_VARI = 'S' AND D.TIPO_COMPRA_VENTA = 'T') AND NOT EXISTS ( SELECT 'S' FROM "
				+ "HISTORICO_UNIDADES_VENTA H WHERE H.COD_LOC = T.CENTRO AND H.COD_ARTICULO = T.REFERENCIA AND H.FECHA_VENTA >= TRUNC(SYSDATE)-10) ");
		if(seccionId!=null){
			query.append("AND exists (select 's' from t_mis_param_agru_plu where cod_seccion=?)");
		}
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(idSesion);
		if(seccionId!=null){
			params.add(seccionId);
		}
		List<DetallePedido> lista = null;
		try {
			lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(), this.rwDetPedidoMapSinPlu,params.toArray());
		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	private RowMapper<DetallePedido> rwDetPedidoMapSinPlu= new RowMapper<DetallePedido>() {
		public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DetallePedido det = new DetallePedido(resultSet.getLong("GRUPO_BALANZA"), resultSet.getString("DENOMINACION_BALANZA"),
					resultSet.getLong("CENTRO"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), resultSet.getLong("REFERENCIA"), resultSet.getString("DESCRIPCION"),
					resultSet.getDouble("STOCK"), resultSet.getDouble("ENCURSO1"), resultSet.getDouble("ENCURSO2"),
					resultSet.getDouble("UNIDADESCAJA"), resultSet.getLong("CAJASPEDIDAS"),
					resultSet.getLong("PROPUESTA"), resultSet.getLong("CANTIDAD"), resultSet.getLong("CANTIDAD"),
					resultSet.getLong("CANTIDAD"), resultSet.getString("TIPODETALLADO"),
					resultSet.getString("ESTADOGRID"), resultSet.getString("ESTADO"), null,
					resultSet.getString("CODIGOERROR"), resultSet.getLong("CANTIDADANT"),
					resultSet.getString("TEMPORADA"), resultSet.getLong("NUM_ORDEN"),
					resultSet.getString("MODELO_PROVEEDOR"), resultSet.getString("DESCR_TALLA"),
					resultSet.getString("DESCR_COLOR"), resultSet.getDouble("FACING"),
					resultSet.getString("TIPO_APROV"), resultSet.getString("TIPO"), resultSet.getLong("NIVEL_LOTE"),
					resultSet.getString("ID"), resultSet.getString("TIPO_UFP"), resultSet.getDouble("UFP"),
					resultSet.getLong("REFERENCIA_EROSKI"), resultSet.getString("DESCRIPCION_EROSKI"),
					resultSet.getString("FLG_OFERTA"), resultSet.getString("OFERTA"));
			det.setNextDayPedido(resultSet.getString("SEG_DIA_PED"));
			det.setFlgSIA(resultSet.getString("FLG_SIA"));
			det.setFechaEntrega(resultSet.getDate("FEC_ENTREGA"));
			det.setUnidFlEmpuje(
					resultSet.getString("UNID_FL_EMPUJE") != null ? resultSet.getLong("UNID_FL_EMPUJE") : null);
			det.setCodNecesidad(
					resultSet.getString("COD_NECESIDAD") != null ? resultSet.getLong("COD_NECESIDAD") : null);
			det.setDexenx(resultSet.getDouble("DEXENX"));
			det.setDexenxEntero(resultSet.getDouble("DEXENXENTERO"));
			// GESTION DE EUROS
			det.setDenomArea(resultSet.getString("DENOM_AREA"));
			det.setDenomSeccion(resultSet.getString("DENOM_SECCION"));
			det.setDenomCategoria(resultSet.getString("DENOM_CATEGORIA"));
			det.setDenomSubcategoria(resultSet.getString("DENOM_SUBCATEGORIA"));
			det.setDenomSegmento(resultSet.getString("DENOM_SEGMENTO"));
			det.setPrecioCostoArticulo(resultSet.getDouble("PRECIO_COSTO_ARTICULO"));
			det.setPrecioCostoInicial(resultSet.getDouble("PRECIO_COSTO_INICIAL"));
			det.setPrecioCostoFinal(resultSet.getDouble("PRECIO_COSTO_FINAL"));
			det.setRotacion(resultSet.getString("ROTACION"));
			det.setDiferencia(resultSet.getLong("DIFERENCIA"));
			det.setRefCumple(resultSet.getString("REF_CUMPLE"));
			det.setAviso(resultSet.getString("AVISO"));

			int codMapa = resultSet.getInt("COD_MAPA");
			det.setCodMapa(codMapa == 0 ? null : codMapa);
			det.setMotivoPedido(resultSet.getString("MOTIVO_PEDIDO"));
			
			return det;
		}

	};
	
	private String aniadirFiltrosTabla(String filtroTabla ){
		String queryWhere="";
		if(filtroTabla.equals("agrupacion")){
			queryWhere=(" AND area||'-'||seccion||'-'||categoria||'-'||subcategoria||'-'||segmento LIKE ? ");
		}else if (filtroTabla.equals("codMapa")){
			queryWhere=(" AND cod_mapa LIKE ? ");
		}else if (filtroTabla.equals("tipo")){
			queryWhere=(" AND DECODE(tipo,'N','NUEVO')  LIKE UPPER(?) ");
		}else if (filtroTabla.equals("codArticulo")){
			queryWhere=(" AND referencia LIKE ? ");
		}else if (filtroTabla.equals("descriptionArt")){
			queryWhere=(" AND descripcion LIKE UPPER(?) ");
		}else if (filtroTabla.equals("tipoAprovisionamiento")){
			queryWhere=(" AND tipo_aprov LIKE UPPER(?) ");
		}else if (filtroTabla.equals("stock")){
			queryWhere=(" AND DECODE (INSTR(stock,',') , 1 ,  REPLACE( stock , ',', '0,' ) , to_char(stock) ) LIKE ? ");
		}else if (filtroTabla.equals("facing")){
			queryWhere=(" AND facing LIKE ? ");
		}else if (filtroTabla.equals("enCurso1")){
			queryWhere=(" AND DECODE (INSTR(encurso1,',') , 1 ,  REPLACE( encurso1 , ',', '0,' ) , to_char(encurso1) ) LIKE ? ");
		}else if (filtroTabla.equals("enCurso2")){
			queryWhere=(" AND DECODE (INSTR(encurso2,',') , 1 ,  REPLACE( encurso2 , ',', '0,' ) , to_char(encurso2) ) LIKE ? ");
		}else if (filtroTabla.equals("unidadesCaja")){
			queryWhere=(" AND unidadescaja LIKE ? ");
		}else if (filtroTabla.equals("propuesta")){
			queryWhere=(" AND propuesta LIKE ? ");
		}else if (filtroTabla.equals("cantidad")){
			queryWhere=(" AND cantidad LIKE ? ");
		}else if (filtroTabla.equals("motivoPedido")){
			queryWhere=(" AND motivo_pedido LIKE ? ");
		}else if (filtroTabla.equals("precioCostoFinal")){
			queryWhere=(" AND DECODE (INSTR(precio_costo_final,',') , 1 ,  REPLACE( precio_costo_final , ',', '0,' ) , to_char(precio_costo_final) ) LIKE ? ");
		}else if (filtroTabla.equals("unidFlEmpuje")){
			queryWhere=(" AND unid_fl_empuje LIKE ? ");
		}else if (filtroTabla.equals("fechaEntrega")){
			queryWhere=(" AND SUBSTR(REPLACE(TO_CHAR(FEC_ENTREGA,'DY DD-MON'),'MI','XI'),1,1)||SUBSTR(TO_CHAR(FEC_ENTREGA,'DY DD-MON'),4) LIKE UPPER(?) ");
		}else if (filtroTabla.equals("nexDayPedido")){
			queryWhere=(" AND SUBSTR(REPLACE(TO_CHAR(TO_DATE(SEG_DIA_PED,'DDMMYYYY'),'DY DD-MON'),'MI','XI'),1,1)||SUBSTR(TO_CHAR(TO_DATE(SEG_DIA_PED,'DDMMYYYY'),'DY DD-MON'),4) LIKE UPPER(?) ");
		}else if(filtroTabla.equals("rotacion")){
			queryWhere=(" AND rotacion LIKE UPPER(?) ");
		}else if (filtroTabla.equals("cajasCortadas")){
			queryWhere=(" AND cajas_cortadas LIKE ? ");
		}else if (filtroTabla.equals("incPrevisionVenta")){
			queryWhere=(" AND inc_prevision_venta LIKE ? ");
		}else if (filtroTabla.equals("smEstatico")){
			queryWhere=(" AND sm_estatico LIKE ? ");
		}else if(filtroTabla.equals("temporada")){
			queryWhere=(" AND temporada LIKE UPPER(?) ");
		}else if(filtroTabla.equals("estructura")){
			queryWhere=(" AND LPAD(seccion,2,'0') || LPAD(categoria,2,'0') || LPAD(subcategoria,2,'0') || LPAD(segmento,2,'0') LIKE ? ");
		}else if(filtroTabla.equals("numOrden")){
			queryWhere=(" AND num_orden LIKE ? ");
		}else if(filtroTabla.equals("modeloProveedor")){
			queryWhere=(" AND modelo_proveedor LIKE UPPER(?) ");
		}else if(filtroTabla.equals("descrTalla")){
			queryWhere=(" AND descr_talla LIKE ? ");
		}else if(filtroTabla.equals("descrColor")){
			queryWhere=(" AND descr_color LIKE UPPER(?) ");
		}else if(filtroTabla.equals("cajasPedidas")){
			queryWhere=(" AND cajasPedidas LIKE ? ");
		}
		return queryWhere;
	}
}
