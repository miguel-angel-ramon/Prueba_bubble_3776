package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DetalladoMostradorSIADao;
import es.eroski.misumi.dao.iface.DetalleMostradorDao;
import es.eroski.misumi.model.Cronometro;
import es.eroski.misumi.model.DetalladoMostradorInfo;
import es.eroski.misumi.model.DetalladoMostradorSIA;
import es.eroski.misumi.model.DetalladoMostradorSIALista;
import es.eroski.misumi.model.DetalleMostrador;
import es.eroski.misumi.model.DetalleMostradorModificados;
import es.eroski.misumi.model.EstrucComercialMostrador;
import es.eroski.misumi.model.FiltrosDetalleMostrador;
import es.eroski.misumi.model.OfertaDetalleMostrador;
import es.eroski.misumi.model.ReferenciaNoGamaMostrador;
import es.eroski.misumi.model.VMisDetalladoMostrador;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class DetalleMostradorDaoImpl implements DetalleMostradorDao {

	@Autowired
	private DetalladoMostradorSIADao detalladoMostradorSIADao;

//	private static Logger logger = Logger.getLogger(DetalleMostradorDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	/**
	 * @author BICUGUAL
	 */
	@Override
	public List<DetalleMostrador> findDetalleMostradorSIA(FiltrosDetalleMostrador filtros) throws Exception {
		
		// 1º CONSULTA.
		//
		// Llamada al DAO que invoca al procedimiento "PK_APR_DET_MOSTRADOR_MISUMI.P_CONSULTA()"
		// para cargar la tabla temporal "T_DETALLADO_MOSTRADOR".
		DetalladoMostradorSIALista resultProc = this.detalladoMostradorSIADao.consultaDetallado(filtros);

		List<DetalleMostrador> resultado = new ArrayList<DetalleMostrador>();
		
		// 2º TRATAMIENTO de datos recuperados.
		if (resultProc != null) {
			resultado = tratarDatosObtenerProc(resultProc);
		}

		return resultado;
	}
	
	/**
	 * @author BICUGUAL
	 */
	@Override
	public List<VMisDetalladoMostrador> findDetalleNivel1Mostrador(Long codCentro, String sesionID) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT nivel"
												 + ", ident"
												 + ", parentid"
												 + ", tiene_ventas"
												 + ", id_sesion"
												 + ", centro"
												 + ", area"
												 + ", denom_area"
												 + ", seccion"
												 + ", denom_seccion"
												 + ", categoria"
												 + ", denom_categoria"
												 + ", subcategoria"
												 + ", denom_subcategoria"
												 + ", segmento"
												 + ", tipo_aprov"
												 + ", seccion_grid"
												 + ", referencia"
												 + ", descripcion"
												 + ", referencia_eroski"
												 + ", descripcion_eroski"
												 + ", fecha_transmision"
												 + ", fecha_venta"
												 + ", unidades_caja"
												 + ", precio_costo_articulo"
												 + ", precio_costo_inicial"
												 + ", precio_costo_final"
												 + ", cod_necesidad"
												 + ", fecha_sgte_transmision"
												 + ", dias_cubre_pedido"
												 + ", margen"
												 + ", tipo_gama"
												 + ", pdte_recibir_manana"
												 + ", empuje_pdte_recibir"
												 + ", pdte_recibir_venta_grid"
												 + ", pdte_recibir_venta"
												 + ", marca_compra"
												 + ", marca_venta"
												 + ", referencia_compra"
												 + ", pvp_tarifa"
												 + ", iva"
												 + ", tirado"
												 + ", tirado_parasitos"
												 + ", prevision_venta"
												 + ", propuesta_pedido"
												 + ", propuesta_pedido_ant"
												 + ", redondeo_propuesta"
												 + ", total_ventas_espejo"
												 + ", tot_importe_ventas_espejo"
												 + ", multiplicador_ventas"
												 + ", orden"
												 + ", oferta_ab"
												 + ", oferta_cd"
												 + ", oferta_ab_inicio"
												 + ", oferta_ab_fin"
												 + ", oferta_cd_inicio"
												 + ", oferta_cd_fin"
												 + ", pvp"
												 + ", dia_espejo"
												 + ", hora_limite"
												 + ", estado"
												 + ", estado_grid"
												 + ", descripcion_error"
												 + ", total_ventas_espejo_tarifa"
												 + ", total_ventas_espejo_oferta "
											+ "FROM v_mis_detallado_mostrador "
											);

		where.append("WHERE nivel=1 ");
		
		if (codCentro != null) {
				where.append(" AND centro = ?");
				params.add(codCentro);
		}
		if (sesionID != null) {
			where.append(" AND id_sesion = ?");
			params.add(sesionID);
		}
			
		String orden = " ORDER BY seccion, categoria, subcategoria, segmento, orden";
		query.append(where);
		query.append(orden);
		
		List<VMisDetalladoMostrador> lista = null;
		
		try {
			lista = (List<VMisDetalladoMostrador>) this.jdbcTemplate.query( query.toString(), this.rwVDetMostradorMap
																		  , params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}
	
	@Override
	public DetalladoMostradorInfo obtenerDatosCabecera(Long codCentro, String sesionId) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT NVL((SELECT DECODE(ESTADO,'A','S','N') "
												 		+ "FROM festivo_centro f "
												 		+ "WHERE f.cod_centro  = ? "
												 		+ "AND f.fecha_festivo = FECHA_VENTA + 1 " // PONER LA FECHA DE VENTA
												 		+ "AND TO_CHAR(f.fecha_festivo,'D') <> 7 "
												 		+ "AND f.fecha_gen     = (SELECT MAX(f2.fecha_gen) "
												 							   + "FROM festivo_centro f2 "
												 							   + "WHERE f2.cod_centro      = f.cod_centro "
												 							   + "AND F2.FECHA_FESTIVO = F.FECHA_FESTIVO "
												 							   + ")"
												 		+ "),'N') vispera_festivo "
												 + ", SUBSTR(DECODE(TO_CHAR(fecha_venta,'D'),'3','X',TO_CHAR(fecha_venta,'DY')),1,1)|| TO_CHAR(fecha_venta,' dd-MON') fecha_venta"
												 + ", SUBSTR(DECODE(TO_CHAR(fecha_sgte_transmision,'D'),'3','X',TO_CHAR(fecha_sgte_transmision,'DY')),1,1)|| TO_CHAR(fecha_sgte_transmision,' dd-MON') fecha_sgte_transmision "
											+ "FROM (SELECT stats_mode(fecha_venta) FECHA_VENTA, stats_mode(fecha_sgte_transmision) fecha_sgte_transmision "
												  + "FROM t_detallado_mostrador "
												  + "WHERE id_sesion = ? "
												  + ") dem"
											);
		params.add(codCentro);
		params.add(sesionId);
		
		DetalladoMostradorInfo userData = new DetalladoMostradorInfo();
		
		try {
			userData = (DetalladoMostradorInfo) this.jdbcTemplate.queryForObject( query.toString(), this.rwMostradorInfoMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return userData;
	}
	
	/**
	 * @author BICUGUAL
	 */
	@Override
	public Cronometro calculoCronometroYNumeroHorasLimite(Long codCentro, String sesionID)
			throws Exception {
		List<Object> params = new ArrayList<Object>();

		// Preparamos la SQL
		StringBuffer query = new StringBuffer("SELECT MAX(hora_limite) AS HORA_LIMITE"
												 + ", COUNT(DISTINCT(DECODE(estado, 'I', NULL, hora_limite))) AS NUM_HORAS_LIMITE"
												 + ", COUNT(1) CUENTA "
											+ "FROM t_detallado_mostrador");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		
		if (codCentro != null) {
			where.append(" AND centro = ?");
			params.add(codCentro);
		}

		if (sesionID != null) {
			where.append(" AND id_sesion = ?");
			params.add(sesionID);
		}
		
		where.append(" AND hora_limite IS NOT NULL");
		
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
	public List<VMisDetalladoMostrador> findDetalleNivel2Mostrador(String sessionId, Long ident) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT nivel"
												 + ", ident"
												 + ", parentid"
												 + ", tiene_ventas"
												 + ", id_sesion"
												 + ", centro"
												 + ", area"
												 + ", denom_area"
												 + ", seccion"
												 + ", denom_seccion"
												 + ", categoria"
												 + ", denom_categoria"
												 + ", subcategoria"
												 + ", denom_subcategoria"
												 + ", segmento"
												 + ", tipo_aprov"
												 + ", seccion_grid"
												 + ", referencia"
												 + ", descripcion"
												 + ", referencia_eroski"
												 + ", descripcion_eroski"
												 + ", fecha_transmision"
												 + ", fecha_venta"
												 + ", unidades_caja"
												 + ", precio_costo_articulo"
												 + ", precio_costo_inicial"
												 + ", precio_costo_final"
												 + ", cod_necesidad"
												 + ", fecha_sgte_transmision"
												 + ", dias_cubre_pedido"
												 + ", margen"
												 + ", tipo_gama"
												 + ", pdte_recibir_manana"
												 + ", empuje_pdte_recibir"
												 + ", pdte_recibir_venta_grid"
												 + ", pdte_recibir_venta"
												 + ", marca_compra"
												 + ", marca_venta"
												 + ", referencia_compra"
												 + ", pvp_tarifa"
												 + ", iva"
												 + ", tirado"
												 + ", tirado_parasitos"
												 + ", prevision_venta"
												 + ", propuesta_pedido"
												 + ", propuesta_pedido_ant"
												 + ", redondeo_propuesta"
												 + ", total_ventas_espejo"
												 + ", tot_importe_ventas_espejo"
												 + ", multiplicador_ventas"
												 + ", orden"
												 + ", oferta_ab"
												 + ", oferta_cd"
												 + ", oferta_ab_inicio"
												 + ", oferta_ab_fin"
												 + ", oferta_cd_inicio"
												 + ", oferta_cd_fin"
												 + ", pvp"
												 + ", dia_espejo"
												 + ", hora_limite"
												 + ", estado"
												 + ", estado_grid"
												 + ", descripcion_error"
												 + ", total_ventas_espejo_tarifa"
												 + ", total_ventas_espejo_oferta "
											+ "FROM v_mis_detallado_mostrador "
											);

		where.append("WHERE nivel=2 ");
		
		if (sessionId != null) {
			where.append(" AND id_sesion = ?");
			params.add(sessionId);
		}
		if (ident != null) {
			where.append(" AND parentid = ?");
			params.add(ident);
		}

		query.append(where);
		
		List<VMisDetalladoMostrador> lista = null;
		
		try {
			lista = (List<VMisDetalladoMostrador>) this.jdbcTemplate.query( query.toString(), this.rwVDetMostradorMap
																		  , params.toArray()
																		  );
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	@Override
	public void deleteTemp(String sesionID) throws Exception {

		// Borrar los registros de la sesion pasada como parámetro.
		if (!"".equals(sesionID) || null!=sesionID){
			
			List<Object> params = new ArrayList<Object>();
	
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		//		where.append("WHERE fecha_creacion < (SYSDATE-1) ");
			StringBuffer query = new StringBuffer("DELETE t_detallado_mostrador ");
		
			where.append("WHERE id_sesion = ?");
			params.add(sesionID);
				
			query.append(where);
	
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}
			
	}
	
	@Override
	public Long insertListIntoTemp(final List<DetalleMostrador> listToInsert, final String sesionId) throws Exception {

		String sql = "INSERT INTO t_detallado_mostrador "
						+ "( id_sesion"
						+ ", centro"
						+ ", area, denom_area, seccion, denom_seccion, categoria, denom_categoria"
						+ ", subcategoria, denom_subcategoria, segmento, denom_segmento"
						+ ", referencia, descripcion"
//						+ ", referencia_eroski, descripcion_eroski"
						+ ", fecha_transmision"
						+ ", fecha_venta"
						+ ", unidades_caja"
						+ ", precio_costo_articulo"
//						+ ", precio_costo_inicial"
//						+ ", precio_costo_final"
						+ ", cod_necesidad"
						+ ", fecha_sgte_transmision"
						+ ", dias_cubre_pedido"
						+ ", tipo_gama"
						+ ", pdte_recibir_manana"
						+ ", empuje_pdte_recibir"
						+ ", pdte_recibir_venta"
						+ ", marca_compra"
						+ ", marca_venta"
						+ ", referencia_compra"
						+ ", pvp_tarifa"
						+ ", iva"
						+ ", tirado"
						+ ", tirado_parasitos"
						+ ", prevision_venta"
						+ ", propuesta_pedido"
						+ ", propuesta_pedido_ant"
						+ ", redondeo_propuesta"
						+ ", total_ventas_espejo"
						+ ", tot_importe_ventas_espejo"
						+ ", multiplicador_ventas"
						+ ", orden"
						+ ", oferta_ab_inicio"
						+ ", oferta_ab_fin"
						+ ", oferta_cd_inicio"
						+ ", oferta_cd_fin"
						+ ", pvp"
						+ ", nivel"
						+ ", dia_espejo"
						+ ", hora_limite"
						+ ", estado"
						+ ", estado_grid"
						+ ", descripcion_error"
						+ ", flg_sia"
						+ ", total_ventas_espejo_tarifa"
						+ ", total_ventas_espejo_oferta"
						+ ", tipo_aprov"
						+ ")"
				   + "VALUES "
						+ "( ?" 				// id_sesion
						+ ", ?" 				// centro
						+ ", ?, ?, ?, ?, ?, ?"	// area, denom_area, seccion, denom_seccion,...
						+ ", ?, ?, ?, ?"		// categoria, denom_categoria, subcategoria, denom_subcategoria
						+ ", ?, ?" 				// referencia, descripcion
						+ ", ?" 				// fecha_transmision
						+ ", ?" 				// fecha_venta
						+ ", ?" 				// unidades_caja
						+ ", ?" 				// precio_costo_articulo
//						+ ", ?" 				// precio_costo_inicial
//						+ ", ?" 				// precio_costo_final
						+ ", ?" 				// cod_necesidad
						+ ", ?"					// fecha_sgte_transmision
						+ ", ?"					// dias_cubre_pedido
						+ ", ?" 				// tipo_gama
						+ ", ?" 				// pdte_recibir_manana
						+ ", ?" 				// empuje_pdte_recibir
						+ ", ?" 				// pdte_recibir_venta
						+ ", ?" 				// marca_compra
						+ ", ?" 				// marca_venta
						+ ", ?" 				// referencia_compra
						+ ", ?" 				// pvp_tarifa
						+ ", ?" 				// iva
						+ ", ?" 				// tirado
						+ ", ?" 				// tirado_parasitos
						+ ", ?" 				// prevision_venta
						+ ", ?" 				// propuesta_pedido
						+ ", ?" 				// propuesta_pedido_ant
						+ ", ?" 				// redondeo_propuesta
						+ ", ?" 				// total_ventas_espejo
						+ ", ?" 				// tot_importe_ventas_espejo
						+ ", ?" 				// multiplicador_ventas
						+ ", ?"					// orden
						+ ", ?"					// oferta_ab_inicio
						+ ", ?"					// oferta_ab_fin
						+ ", ?"					// oferta_cd_inicio
						+ ", ?"					// oferta_cd_fin
						+ ", ?"					// pvp
						+ ", ?"					// nivel
						+ ", ?" 				// dia_espejo 
						+ ", ?" 				// hora_limite
						+ ", ?" 				// estado
						+ ", ?" 				// estado_grid
						+ ", ?" 				// descripcion_error
						+ ", ?" 				// flg_sia
						+ ", ?" 				// venta_espejo_tarifa
						+ ", ?" 				// venta_espejo_oferta
						+ ", ?" 				// tipo_aprov
						+ ")";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				DetalleMostrador myPojo = listToInsert.get(i);
				ps.setString(1, sesionId);
				ps.setLong(2, myPojo.getCodCentro());
				ps.setLong(3, myPojo.getArea());
				ps.setString(4, myPojo.getDenomArea());
				ps.setLong(5, myPojo.getSeccion());
				ps.setString(6, myPojo.getDenomSeccion());
				ps.setLong(7, myPojo.getCategoria());
				ps.setString(8, myPojo.getDenomCategoria());
				ps.setLong(9, myPojo.getSubcategoria());
				ps.setString(10, myPojo.getDenomSubcategoria());
				ps.setLong(11, myPojo.getSegmento());
				ps.setString(12, myPojo.getDenomSegmento());
				
				ps.setLong(13, myPojo.getReferencia());
				ps.setString(14, myPojo.getDescReferencia());

				if (null != myPojo.getFechaTransmision()) {
					ps.setDate(15, new java.sql.Date(myPojo.getFechaTransmision().getTime()));
				} else {
					ps.setNull(15, Types.DATE);
				}
				
				if (null != myPojo.getFechaVenta()) {
					ps.setDate(16, new java.sql.Date(myPojo.getFechaVenta().getTime()));
				} else {
					ps.setNull(16, Types.DATE);
				}

				ps.setDouble(17, myPojo.getUnidadesCaja() != null && !("".equals(myPojo.getUnidadesCaja().toString()))
						? new Double(myPojo.getUnidadesCaja().toString()) : new Double(0));

				// Gestion de Euros
				ps.setDouble(18, myPojo.getPrecioCostoArticulo() != null && !("".equals(myPojo.getPrecioCostoArticulo().toString()))
						? new Double(myPojo.getPrecioCostoArticulo().toString()) : new Double(0));

//				ps.setDouble(19,
//						myPojo.getPrecioCostoInicial() != null
//								&& !("".equals(myPojo.getPrecioCostoInicial().toString()))
//										? new Double(myPojo.getPrecioCostoInicial().toString()) : new Double(0));
//
//				ps.setDouble(20,
//						myPojo.getPrecioCostoFinal() != null
//								&& !("".equals(myPojo.getPrecioCostoInicial().toString()))
//										? (double) Math.round(myPojo.getPrecioCostoInicial() * 10d) / 10d : new Double(0));

//				Double precioCostoFinal = (Double) myPojo.getPrecioCostoFinal();
//				if (precioCostoFinal != null && !("".equals(myPojo.getPrecioCostoFinal().toString()))) {
//					ps.setDouble(20, (double) Math.round(precioCostoFinal * 10d) / 10d);
//				} else {
//					ps.setDouble(20, new Double(0));
//				}
				
				if (myPojo.getCodNecesidad() != null) {
					ps.setLong(19, myPojo.getCodNecesidad());
				} else {
					ps.setNull(19, Types.NUMERIC);
				}

				if (null != myPojo.getFechaSgteTransmision()) {
					ps.setDate(20, new java.sql.Date(myPojo.getFechaSgteTransmision().getTime()));
				} else {
					ps.setNull(20, Types.DATE);
				}

				if (myPojo.getDiasCubrePedido() != null) {
					ps.setLong(21, myPojo.getDiasCubrePedido());
				} else {
					ps.setNull(21, Types.NUMERIC);
				}
				
				ps.setString(22, myPojo.getTipoGama());

				if (myPojo.getPendienteRecibirManana() != null) {
					ps.setDouble(23, myPojo.getPendienteRecibirManana());
				} else {
					ps.setNull(23, Types.NUMERIC);
				}

				ps.setString(24, myPojo.getEmpujePdteRecibir());
				
				if (myPojo.getPdteRecibirVenta() != null) {
					ps.setDouble(25, myPojo.getPdteRecibirVenta());
				} else {
					ps.setNull(25, Types.NUMERIC);
				}
				
				ps.setString(26, myPojo.getMarcaCompra());
				ps.setString(27, myPojo.getMarcaVenta());

				if (myPojo.getReferenciaCompra() != null) {
					ps.setLong(28, myPojo.getReferenciaCompra());
				} else {
					ps.setNull(28, Types.NUMERIC);
				}

				ps.setDouble(29,
						myPojo.getPvpTarifa() != null
								&& !("".equals(myPojo.getPvpTarifa().toString()))
										? new Double(myPojo.getPvpTarifa().toString()) : new Double(0));
				
				ps.setDouble(30,
						myPojo.getIva() != null
								&& !("".equals(myPojo.getIva().toString()))
										? new Double(myPojo.getIva().toString()) : new Double(0));
				
				ps.setDouble(31,
						myPojo.getTirado() != null
								&& !("".equals(myPojo.getTirado().toString()))
										? new Double(myPojo.getTirado().toString()) : new Double(0));

				ps.setDouble(32,
						myPojo.getTiradoParasitos() != null
								&& !("".equals(myPojo.getTiradoParasitos().toString()))
										? new Double(myPojo.getTiradoParasitos().toString()) : new Double(0));

				if (myPojo.getPrevisionVenta() != null) {
					ps.setDouble(33, myPojo.getPrevisionVenta());
				} else {
					ps.setNull(33, Types.NUMERIC);
				}

				if (myPojo.getPropuestaPedido() != null) {
					ps.setLong(34, myPojo.getPropuestaPedido());
				} else {
					ps.setNull(34, Types.NUMERIC);
				}

				if (myPojo.getPropuestaPedidoAnt() != null) {
					ps.setLong(35, myPojo.getPropuestaPedidoAnt());
				} else {
					ps.setNull(35, Types.NUMERIC);
				}

				ps.setString(36, myPojo.getRedondeoPropuesta());

				if (myPojo.getTotalVentasEspejo() != null) {
					ps.setDouble(37, myPojo.getTotalVentasEspejo());
				} else {
					ps.setNull(37, Types.NUMERIC);
				}

				if (myPojo.getTotImporteVentasEspejo() != null) {
					ps.setDouble(38, myPojo.getTotImporteVentasEspejo());
				} else {
					ps.setNull(38, Types.NUMERIC);
				}

				if (myPojo.getMultiplicadorVentas() != null) {
					ps.setDouble(39, myPojo.getMultiplicadorVentas());
				} else {
					ps.setNull(39, Types.NUMERIC);
				}

				if (myPojo.getOrden() != null) {
					ps.setDouble(40, myPojo.getOrden());
				} else {
					ps.setNull(40, Types.NUMERIC);
				}

				if (null != myPojo.getOfertaABInicio()) {
					ps.setDate(41, new java.sql.Date(myPojo.getOfertaABInicio().getTime()));
				} else {
					ps.setNull(41, Types.DATE);
				}
				
				if (null != myPojo.getOfertaABFin()) {
					ps.setDate(42, new java.sql.Date(myPojo.getOfertaABFin().getTime()));
				} else {
					ps.setNull(42, Types.DATE);
				}

				if (null != myPojo.getOfertaCDInicio()) {
					ps.setDate(43, new java.sql.Date(myPojo.getOfertaCDInicio().getTime()));
				} else {
					ps.setNull(43, Types.DATE);
				}
				
				if (null != myPojo.getOfertaCDFin()) {
					ps.setDate(44, new java.sql.Date(myPojo.getOfertaCDFin().getTime()));
				} else {
					ps.setNull(44, Types.DATE);
				}

				ps.setDouble(45,
						myPojo.getPvp() != null
								&& !("".equals(myPojo.getPvp().toString()))
										? new Double(myPojo.getPvp().toString()) : new Double(0));

				if (myPojo.getNivel() != null) {
					ps.setLong(46, myPojo.getNivel());
				} else {
					ps.setNull(46, Types.NUMERIC);
				}
				
				if (null != myPojo.getDiaEspejo()) {
					ps.setDate(47, new java.sql.Date(myPojo.getDiaEspejo().getTime()));
				} else {
					ps.setNull(47, Types.DATE);
				}

				ps.setString(48, myPojo.getHoraLimite());
				ps.setString(49, myPojo.getEstado());
				if (myPojo.getEstadoGrid() != null) {
					ps.setLong(50, myPojo.getEstadoGrid());
				} else {
					ps.setNull(50, Types.NUMERIC);
				}
				ps.setString(51, myPojo.getDescripcionError());
				ps.setString(52, myPojo.getFlgSIA()!=null?myPojo.getFlgSIA():"S");

				if (myPojo.getVentaEspejoTarifa() != null) {
					ps.setDouble(53, myPojo.getVentaEspejoTarifa());
				} else {
					ps.setNull(53, Types.NUMERIC);
				}

				if (myPojo.getVentaEspejoOferta() != null) {
					ps.setDouble(54, myPojo.getVentaEspejoOferta());
				} else {
					ps.setNull(54, Types.NUMERIC);
				}

				ps.setString(55, myPojo.getTipoAprov());
			}

			@Override
			public int getBatchSize() {
				return listToInsert.size();
			}
		});

		return null!= listToInsert ? Long.valueOf(listToInsert.size()) : 0L;

	}

	/** Obtiene la estructura para cargar las combos de Seccion, Categoria, Subcategoria.
	 * Combos necesarios para la pagina de Detallado Mostrador.
	 * <p> Estructura devuelta
	 * <code>
	 * <li>apr_t_r_estructura_dat IS TABLE OF apr_r_estructura_dat
	 * <li>APR_R_ESTRUCTURA_DAT AS OBJECT (
		    CODIGO    	                NUMBER(4),
		    DENOMINACION	            VARCHAR2(50 BYTE)
		)
	 * </code>
	 */
	@Override
	public EstrucComercialMostrador obtenerEstrCom(final Long codLoc, final Long codN2, final Long codN3, final Long codN4, final String tipoAprov){

		// Llamada al DAO que invoca al procedimiento "PK_APR_DET_MOSTRADOR_MISUMI.P_OBTENER_ESTR_COM()"
		EstrucComercialMostrador resultProc = this.detalladoMostradorSIADao.obtenerEstrCom(codLoc, codN2, codN3, codN4, tipoAprov);

		return resultProc;
	}
	
	@Override
	public void updateData(final List<DetalleMostradorModificados> listaDetalladoModif, final Long codCentro, final String sessionId) throws Exception {

		// Completamos los campos del registro
		// Preparamos la SQL
		String sql = "UPDATE t_detallado_mostrador "
				    + "SET propuesta_pedido 	= ?"
				   	  + ", propuesta_pedido_ant = ?"
					  + ", estado_grid 			= ?"
					  + ", estado				= ?"
					  + ", descripcion_error	= ?"
					  + ", cod_necesidad 		= ?"
					  + ", last_update_date 	= CURRENT_TIMESTAMP "
				   + "WHERE id_sesion		= ? "
				   + "AND centro 			= ? "
				   + "AND referencia		= ?";

		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				DetalleMostradorModificados myPojo = listaDetalladoModif.get(i);
				ps.setLong(1, myPojo.getPropuestaPedido());
				ps.setLong(2, myPojo.getPropuestaPedidoAnt());
				ps.setString(3, myPojo.getEstadoGrid());
				ps.setString(4, myPojo.getEstadoPedido());
				ps.setString(5, (myPojo.getDescError()!=null?myPojo.getDescError():""));
				if (myPojo.getCodNecesidad() != null) {
					ps.setLong(6, myPojo.getCodNecesidad());
				} else {
					ps.setNull(6, Types.NUMERIC);
				}
					
//					if (myPojo.getUnidadesCaja() != null) {
//						ps.setDouble(6, myPojo.getUnidadesCaja());
//					} else {
//						ps.setNull(6, Types.NUMERIC);
//					}

				ps.setString(7, sessionId);
				ps.setLong(8, codCentro);
				ps.setLong(9, myPojo.getCodArticulo());
			}

			@Override
			public int getBatchSize() {
				return listaDetalladoModif.size();
			}
		});

	}

	/**
	 * Pasa los datos de SIA(DetalladoMostradorSIA) al modelo de JAVA(DetalleMostrador)
	 * 
	 * @param resultProc
	 * @return
	 * @throws Exception
	 */
	private List<DetalleMostrador> tratarDatosObtenerProc(DetalladoMostradorSIALista resultProc) throws Exception {

		// Transformación de datos para estructura de DetalleMostrador.
		List<DetalleMostrador> resultado = new ArrayList<DetalleMostrador>();
		List<DetalladoMostradorSIA> detalladoMostradorSIAListaAux = new ArrayList<DetalladoMostradorSIA>();
		
		DetalleMostrador filaResultado = new DetalleMostrador();

		// Recuperar el campo DATOS de la estructura de SIA.
		if (null != resultProc.getDatos()) {
			detalladoMostradorSIAListaAux = resultProc.getDatos();

			// Nos recorremos la lista principal
			for (int i = 0; i < detalladoMostradorSIAListaAux.size(); i++) {
				filaResultado = new DetalleMostrador();
	
				// Centro
				filaResultado.setCodCentro((detalladoMostradorSIAListaAux.get(i).getCodLoc() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodLoc().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodLoc().toString()) : null);
				
				// Area
				filaResultado.setArea((detalladoMostradorSIAListaAux.get(i).getCodN1Ref() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodN1Ref().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodN1Ref().toString()) : null);
				filaResultado.setDenomArea((detalladoMostradorSIAListaAux.get(i).getDenomArea() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomArea().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomArea().toString() : null);
				
				// Seccion
				filaResultado.setSeccion((detalladoMostradorSIAListaAux.get(i).getCodN2Ref() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodN2Ref().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodN2Ref().toString()) : null);
				filaResultado.setDenomSeccion((detalladoMostradorSIAListaAux.get(i).getDenomSeccion() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomSeccion().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomSeccion().toString() : null);
	
				// Categoria
				filaResultado.setCategoria((detalladoMostradorSIAListaAux.get(i).getCodN3Ref() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodN3Ref().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodN3Ref().toString()) : null);
				filaResultado.setDenomCategoria((detalladoMostradorSIAListaAux.get(i).getDenomCategoria() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomCategoria().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomCategoria().toString() : null);
	
				// Subcategoria
				filaResultado.setSubcategoria((detalladoMostradorSIAListaAux.get(i).getCodN4Ref() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodN4Ref().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodN4Ref().toString()) : null);
				filaResultado.setDenomSubcategoria((detalladoMostradorSIAListaAux.get(i).getDenomSubcategoria() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomSubcategoria().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomSubcategoria().toString() : null);
	
				// Segmento
				filaResultado.setSegmento((detalladoMostradorSIAListaAux.get(i).getCodN5Ref() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodN5Ref().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodN5Ref().toString()) : null);
				filaResultado.setDenomSegmento((detalladoMostradorSIAListaAux.get(i).getDenomSegmento() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomSegmento().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomSegmento().toString() : null);
	
	//			filaResultado.setCajasPedidas((detalladoMostradorSIAListaAux.get(i).getUnidEncargoFl() != null
	//					&& !("".equals(detalladoMostradorSIAListaAux.get(i).getUnidEncargoFl().toString())))
	//							? new Long(detalladoMostradorSIAListaAux.get(i).getUnidEncargoFl().toString()) : null);
	
				// Referencia
				filaResultado.setReferencia((detalladoMostradorSIAListaAux.get(i).getCodArticulo() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodArticulo().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodArticulo().toString()) : null);
				
				// Descripcion
				filaResultado.setDescReferencia((detalladoMostradorSIAListaAux.get(i).getDenomRef() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDenomRef().toString())))
								? detalladoMostradorSIAListaAux.get(i).getDenomRef().toString() : null);
	
				// Tipo Gama
				filaResultado.setTipoGama((detalladoMostradorSIAListaAux.get(i).getTipoGama() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTipoGama().toString())))
								? detalladoMostradorSIAListaAux.get(i).getTipoGama().toString() : null);
				
				// Fecha Transmision
				filaResultado.setFechaTransmision((detalladoMostradorSIAListaAux.get(i).getFecTransmision() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getFecTransmision().toString())))
								? detalladoMostradorSIAListaAux.get(i).getFecTransmision() : null);
	
				// Fecha Venta
				filaResultado.setFechaVenta((detalladoMostradorSIAListaAux.get(i).getFecVentaDiaTrans() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getFecVentaDiaTrans().toString())))
								? detalladoMostradorSIAListaAux.get(i).getFecVentaDiaTrans() : null);
	
				// Fecha Sgte. Transmision
				filaResultado.setFechaSgteTransmision((detalladoMostradorSIAListaAux.get(i).getFecSigTransmision() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getFecSigTransmision().toString())))
								? detalladoMostradorSIAListaAux.get(i).getFecSigTransmision() : null);
	
				// Dias Cubre Pedido
				filaResultado.setDiasCubrePedido((detalladoMostradorSIAListaAux.get(i).getDiasCubrePed() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getDiasCubrePed().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getDiasCubrePed().toString()) : null);
	
				// Pdte Recibir Mañana
				filaResultado.setPendienteRecibirManana((detalladoMostradorSIAListaAux.get(i).getPdteRecManana() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPdteRecManana().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getPdteRecManana().toString()) : null);
				
				// Empuje Pdte Recibir
				filaResultado.setEmpujePdteRecibir((detalladoMostradorSIAListaAux.get(i).getEmpujePdteRecManana() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getEmpujePdteRecManana().toString())))
								? detalladoMostradorSIAListaAux.get(i).getEmpujePdteRecManana().toString() : null);
	
				// Pdte Recibir Venta
				filaResultado.setPdteRecibirVenta((detalladoMostradorSIAListaAux.get(i).getPdteRecFecVenta() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPdteRecFecVenta().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getPdteRecFecVenta().toString()) : null);
	
				// Unidades Caja
				filaResultado.setUnidadesCaja((detalladoMostradorSIAListaAux.get(i).getUnidadesCaja() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getUnidadesCaja().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getUnidadesCaja().toString()) : null);
				
				// Marca Compra
				filaResultado.setMarcaCompra((detalladoMostradorSIAListaAux.get(i).getMarcaCompra() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getMarcaCompra().toString())))
								? detalladoMostradorSIAListaAux.get(i).getMarcaCompra().toString() : null);
	
				// Marca Venta
				filaResultado.setMarcaVenta((detalladoMostradorSIAListaAux.get(i).getMarcaVenta() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getMarcaVenta().toString())))
								? detalladoMostradorSIAListaAux.get(i).getMarcaVenta().toString() : null);
	
				// Referencia Compra
				filaResultado.setReferenciaCompra((detalladoMostradorSIAListaAux.get(i).getReferenciaCompra() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getReferenciaCompra().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getReferenciaCompra().toString()) : null);
	
				// Multiplicador Ventas
				filaResultado.setMultiplicadorVentas((detalladoMostradorSIAListaAux.get(i).getMultCompraVenta() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getMultCompraVenta().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getMultCompraVenta().toString()) : null);
				
				// Pvp Tarifa
				filaResultado.setPvpTarifa((detalladoMostradorSIAListaAux.get(i).getPvpTarifa() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPvpTarifa().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getPvpTarifa().toString()) : null);
	
				// Costo
				filaResultado.setPrecioCostoArticulo((detalladoMostradorSIAListaAux.get(i).getCosto() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCosto().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getCosto().toString()) : null);
				
				// IVA
				filaResultado.setIva((detalladoMostradorSIAListaAux.get(i).getIva() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getIva().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getIva().toString()) : null);
	
				// Tirado
				filaResultado.setTirado((detalladoMostradorSIAListaAux.get(i).getTirado() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTirado().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getTirado().toString()) : null);
	
				// Tirado Parasitos
				filaResultado.setTiradoParasitos((detalladoMostradorSIAListaAux.get(i).getTiradoParasitos() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTiradoParasitos().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getTiradoParasitos().toString()) : null);
	
				// Prevision Venta
				filaResultado.setPrevisionVenta((detalladoMostradorSIAListaAux.get(i).getPrevisionVenta() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPrevisionVenta().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getPrevisionVenta().toString()) : null);
				
				// Propuesta Pedido
				filaResultado.setPropuestaPedido((detalladoMostradorSIAListaAux.get(i).getPropuestaPedido() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPropuestaPedido().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getPropuestaPedido().toString()) : null);
				
				// Propuesta Pedido Ant
				filaResultado.setPropuestaPedidoAnt((detalladoMostradorSIAListaAux.get(i).getPropuestaPedidoAnt() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPropuestaPedido().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getPropuestaPedido().toString()) : null);
				
				// Redondeo Propuesta Pedido
				filaResultado.setRedondeoPropuesta((detalladoMostradorSIAListaAux.get(i).getRedondeoPropPedido() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getRedondeoPropPedido().toString())))
								? detalladoMostradorSIAListaAux.get(i).getRedondeoPropPedido().toString() : null);
	
				// Total Ventas Espejo
				filaResultado.setTotalVentasEspejo((detalladoMostradorSIAListaAux.get(i).getTotVenFecEspejo() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTotVenFecEspejo().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getTotVenFecEspejo().toString()) : null);
				
				// Tot Importe Ventas Espejo
				filaResultado.setTotImporteVentasEspejo((detalladoMostradorSIAListaAux.get(i).getTotImpVenFecEspejo() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTotImpVenFecEspejo().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getTotImpVenFecEspejo().toString()) : null);
	
				// Num Orden
				filaResultado.setOrden((detalladoMostradorSIAListaAux.get(i).getNumOrden() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getNumOrden().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getNumOrden().toString()) : null);
				
				// Pvp
	//			filaResultado.setPvp((detalladoMostradorSIAListaAux.get(i).getPvp() != null
	//					&& !("".equals(detalladoMostradorSIAListaAux.get(i).getPvp().toString())))
	//							? new Double(detalladoMostradorSIAListaAux.get(i).getPvp().toString()) : null);
	
				
				// Cod Necesidad
				filaResultado.setCodNecesidad((detalladoMostradorSIAListaAux.get(i).getCodNecesidad() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getCodNecesidad().toString())))
								? new Long(detalladoMostradorSIAListaAux.get(i).getCodNecesidad().toString()) : null);
	
				// Estado
				filaResultado.setEstado((detalladoMostradorSIAListaAux.get(i).getEstado() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getEstado().toString())))
								? detalladoMostradorSIAListaAux.get(i).getEstado().toString() : null);
	
				// Hora Fin Bomba
				filaResultado.setHoraLimite((detalladoMostradorSIAListaAux.get(i).getHoraFinBomba() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getHoraFinBomba().toString())))
								? detalladoMostradorSIAListaAux.get(i).getHoraFinBomba().toString() : null);
	
				// Venta Espejo Tarifa
				filaResultado.setVentaEspejoTarifa((detalladoMostradorSIAListaAux.get(i).getVentaEspejoTarifa() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getVentaEspejoTarifa().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getVentaEspejoTarifa().toString()) : null);
				
				// Venta Espejo Oferta
				filaResultado.setVentaEspejoOferta((detalladoMostradorSIAListaAux.get(i).getVentaEspejoOferta() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getVentaEspejoOferta().toString())))
								? new Double(detalladoMostradorSIAListaAux.get(i).getVentaEspejoOferta().toString()) : null);

				// Tipo Aprov
				filaResultado.setTipoAprov((detalladoMostradorSIAListaAux.get(i).getTipoAprov() != null
						&& !("".equals(detalladoMostradorSIAListaAux.get(i).getTipoAprov().toString())))
								? detalladoMostradorSIAListaAux.get(i).getTipoAprov().toString() : null);
				
				resultado.add(filaResultado);
			}

		}
		
		return resultado;
	}

	private RowMapper<VMisDetalladoMostrador> rwVDetMostradorMap = new RowMapper<VMisDetalladoMostrador>() {
		public VMisDetalladoMostrador mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			VMisDetalladoMostrador det = new VMisDetalladoMostrador( resultSet.getLong("nivel")
																   , resultSet.getString("ident")
																   , resultSet.getString("parentid")
																   , (null == resultSet.getString("tiene_ventas") ? "N" : resultSet.getString("tiene_ventas"))
																   , resultSet.getString("id_sesion")
																   , resultSet.getLong("centro")
																   , resultSet.getLong("area")
																   , resultSet.getString("denom_area")
																   , resultSet.getLong("seccion")
																   , resultSet.getString("denom_seccion")
																   , resultSet.getLong("categoria")
																   , resultSet.getString("denom_categoria")
																   , resultSet.getLong("subcategoria")
																   , resultSet.getString("denom_subcategoria")
																   , resultSet.getLong("segmento")
																   , resultSet.getString("tipo_aprov")
//																   , resultSet.getString("denom_segmento")
																   , resultSet.getString("seccion_grid")
																   , resultSet.getLong("referencia")
																   , resultSet.getString("descripcion")
																   , resultSet.getLong("referencia_eroski")
																   , resultSet.getString("descripcion_eroski")
																   , resultSet.getDate("fecha_transmision")
																   , resultSet.getDate("fecha_venta")
																   , resultSet.getDouble("unidades_caja")
																   , resultSet.getDouble("precio_costo_articulo")
																   , resultSet.getDouble("precio_costo_inicial")
																   , resultSet.getDouble("precio_costo_final")
																   , resultSet.getLong("cod_necesidad")
																   , resultSet.getDate("fecha_sgte_transmision")
																   , resultSet.getLong("dias_cubre_pedido")
																   , resultSet.getDouble("margen")
																   , resultSet.getString("tipo_gama")
																   , resultSet.getDouble("pdte_recibir_manana")
																   , resultSet.getString("empuje_pdte_recibir")
																   , resultSet.getString("pdte_recibir_venta_grid")
																   , resultSet.getDouble("pdte_recibir_venta")
																   , resultSet.getString("marca_compra")
																   , resultSet.getString("marca_venta")
																   , resultSet.getLong("referencia_compra")
																   , resultSet.getDouble("pvp_tarifa")
																   , resultSet.getDouble("iva")
																   , resultSet.getDouble("tirado")
																   , resultSet.getString("tirado_parasitos")
																   , resultSet.getDouble("prevision_venta")
																   , resultSet.getLong("propuesta_pedido")
																   , resultSet.getLong("propuesta_pedido_ant")
																   , resultSet.getString("redondeo_propuesta")
																   , resultSet.getDouble("total_ventas_espejo")
																   , resultSet.getDouble("tot_importe_ventas_espejo")
																   , resultSet.getDouble("multiplicador_ventas")
																   , resultSet.getLong("orden")
																   , resultSet.getString("oferta_ab")
																   , resultSet.getString("oferta_cd")
																   , resultSet.getDate("oferta_ab_inicio")
																   , resultSet.getDate("oferta_ab_fin")
																   , resultSet.getDate("oferta_cd_inicio")
																   , resultSet.getDate("oferta_cd_fin")
																   , resultSet.getDouble("pvp")
																   , resultSet.getDate("dia_espejo")
																   , resultSet.getString("hora_limite")
																   , resultSet.getString("estado")
																   , resultSet.getLong("estado_grid")
																   , resultSet.getString("descripcion_error")
																   , resultSet.getDouble("total_ventas_espejo_tarifa")
																   , resultSet.getDouble("total_ventas_espejo_oferta")
																   );
			
			return det;
		}

	};

	private RowMapper<Cronometro> rwCronoMap= new RowMapper<Cronometro>() {
		public Cronometro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Cronometro(resultSet.getString("HORA_LIMITE"),resultSet.getString("NUM_HORAS_LIMITE"),resultSet.getString("CUENTA"));}};

	
	private RowMapper<DetalladoMostradorInfo> rwMostradorInfoMap = new RowMapper<DetalladoMostradorInfo>() {
		public DetalladoMostradorInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DetalladoMostradorInfo det =  new DetalladoMostradorInfo();
			
			Boolean avisoVisperaFestivo = Boolean.FALSE;
			
			if (null != resultSet.getString("vispera_festivo")){
				
				if ("S".equalsIgnoreCase(resultSet.getString("vispera_festivo"))){
					avisoVisperaFestivo = Boolean.TRUE;
				}
				
			}
			
			det.setAvisoVisperaFestivo(avisoVisperaFestivo);
			det.setFechaEntrega(resultSet.getString("fecha_venta"));
			det.setFechaPedido(resultSet.getString("fecha_sgte_transmision"));
			
			return det;
		}

	};

	@Override
	public List<String> getFestivosByCentro(Long codCentro) throws Exception {
		//Estado A = cerrado
		String sql = "SELECT TO_CHAR(FECHA_FESTIVO,'dd/mm/yyyy') AS diaFestivo "
				 + " FROM festivo_centro F "
				 + " WHERE F.COD_CENTRO = ? "
				 + " AND FECHA_FESTIVO BETWEEN TO_DATE(current_date - 395)  AND TO_DATE(current_date - 1) " 
				 + " AND ESTADO='A' "
				 + " AND F.FECHA_GEN IN "
				 + " (SELECT MAX(F2.FECHA_GEN) FROM FESTIVO_CENTRO F2 "
				 + " WHERE F2.COD_CENTRO = F.COD_CENTRO "
				 + " AND F2.FECHA_FESTIVO=F.FECHA_FESTIVO)";
		
		return this.jdbcTemplate.queryForList( sql, new Object[]{codCentro}, String.class);
	}


	@Override
	public List<OfertaDetalleMostrador> getOfertas(Long codCentro, Long referencia, String sesionId) throws Exception {
		
		String sql = "SELECT V.COD_CENTRO, V.ANO_OFERTA, "
						+ " V.NUM_OFERTA, V.FECHA_INI, V.FECHA_FIN, V.COD_ART, "
						+ " D.DESCRIPCION, V.MECANICA, D.PVP_TARIFA,  V.PVP PRECIO_OFERTA, "
						+ " (SELECT p.DESCRIPCION FROM PROMOCIONES p WHERE p.EJER_PROMOCION = v.ANO_OFERTA AND p.COD_PROMOCION = v.NUM_OFERTA AND rownum<2) AS NOMBRE_OFERTA "
					+ " FROM v_mis_ofer_mostrador v, v_mis_detallado_mostrador d "
					+ " WHERE d.centro= ? "
						+ " AND d.referencia_compra= ? "
						+ " AND d.id_sesion=? "
						+ " AND d.centro = v.cod_centro"
						+ " AND d.referencia = v.cod_art"
					+ " ORDER BY V.FECHA_INI, V.ANO_OFERTA, V.NUM_OFERTA, V.COD_ART ";
 
		
		return jdbcTemplate.query(sql, new Object[]{codCentro, referencia, sesionId}, rwOfertaDetalleMap);
		
	}
	
	private RowMapper<OfertaDetalleMostrador> rwOfertaDetalleMap = new RowMapper<OfertaDetalleMostrador>() {
		public OfertaDetalleMostrador mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			OfertaDetalleMostrador item =  new OfertaDetalleMostrador();

			item.setDenomOferta(rs.getString("NOMBRE_OFERTA"));
			item.setAnioOferta(rs.getString("ANO_OFERTA"));
			item.setNumOferta(rs.getString("NUM_OFERTA"));
			item.setFechaInicio(rs.getDate("FECHA_INI"));
			item.setFechaFin(rs.getDate("FECHA_FIN"));
			item.setReferencia(rs.getLong("COD_ART"));
			item.setDenominacion(rs.getString("DESCRIPCION"));
			item.setMecanica(rs.getString("MECANICA"));
			item.setPvpTarifa(rs.getString("PVP_TARIFA"));
			item.setPvpOferta(rs.getString("PRECIO_OFERTA"));
						
			return item;
		}

	};
	
	@Override
	public void deleteTempListadoReferenciasNoGama(String sesionId){
		// Borrar los registros de la sesion pasada como parámetro.
		if (!"".equals(sesionId) || null!=sesionId) {
			
			StringBuffer query = new StringBuffer("DELETE T_MIS_REF_NO_GAMA_MOSTRADOR WHERE ID_SESION = ?");
		
			try {
				this.jdbcTemplate.update(query.toString(),new Object[]{sesionId});
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), Arrays.asList(new Object[]{sesionId}), e);
			}

		}
	}
	
	
	
	@Override
	public void insertarTempListadoReferenciasNoGama(final List<ReferenciaNoGamaMostrador> lstReferencias, final String sesionId) throws Exception{

		String query = " INSERT INTO T_MIS_REF_NO_GAMA_MOSTRADOR (COD_ART_FORMLOG, DENOM_REF, COSTO, PVP_TARIFA, IVA, MARGEN, CC, NUM_ORDEN, FOTO, ID_SESION) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";

		if (lstReferencias != null && lstReferencias.size() > 0) {
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ReferenciaNoGamaMostrador referencia = lstReferencias.get(i);

					if (null != referencia.getCodArtFormLog()) {
						ps.setLong(1, referencia.getCodArtFormLog());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}

					ps.setString(2, referencia.getDenomRef());

					if (null != referencia.getCosto()) {
						ps.setDouble(3, referencia.getCosto());
					} else {
						ps.setNull(3, Types.NUMERIC);
					}

					if (null != referencia.getPvpTarifa()) {
						ps.setDouble(4, referencia.getPvpTarifa());
					} else {
						ps.setNull(4, Types.NUMERIC);
					}

					if (null != referencia.getIva()) {
						ps.setDouble(5, referencia.getIva());
					} else {
						ps.setNull(5, Types.NUMERIC);
					}
					if (null != referencia.getMargen()) {
						ps.setDouble(6, referencia.getMargen());
					} else {
						ps.setNull(6, Types.NUMERIC);
					}

					if (null != referencia.getCc()) {
						ps.setDouble(7, referencia.getCc());
					} else {
						ps.setNull(7, Types.NUMERIC);
					}

					if (null != referencia.getNumOrden()) {
						ps.setDouble(8, referencia.getNumOrden());
					} else {
						ps.setNull(8, Types.NUMERIC);
					}

					ps.setString(9, referencia.getFoto());
					ps.setString(10, sesionId);
				}

				@Override
				public int getBatchSize() {
					return lstReferencias.size();
				}
			});
		}

	}


	@Override
	public List<ReferenciaNoGamaMostrador> getReferenciasNoGamaMostrador(String sesionId, Long codCentro, Pagination pagination) {

    	List<Object> params = new ArrayList<Object>();
    	params.add(codCentro);
    	params.add(sesionId);	        		
    	
    	StringBuffer query = new StringBuffer("SELECT ");
    	query.append("(SELECT UNI_CAJA_SERV FROM V_SURTIDO_TIENDA WHERE COD_ART = COD_ART_FORMLOG AND COD_CENTRO=?) AS UNI_CAJA_SERV, ");
    	query.append("COD_ART_FORMLOG, DENOM_REF, COSTO, PVP_TARIFA, IVA, MARGEN, CC, NUM_ORDEN, FOTO FROM T_MIS_REF_NO_GAMA_MOSTRADOR ");
    	query.append(" WHERE ID_SESION = ? ");
        
        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        order.append(" ORDER BY num_orden");
		query.append(order);

        if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}
        
        List<ReferenciaNoGamaMostrador> lstReferencias = null;
 		try {
 			lstReferencias = this.jdbcTemplate.query(query.toString(), this.rwReferenciasNoGamaMap, params.toArray());
 		} catch (Exception e){
 			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
 		}
 		return lstReferencias;
		
	}

	
	private RowMapper<ReferenciaNoGamaMostrador> rwReferenciasNoGamaMap = new RowMapper<ReferenciaNoGamaMostrador>() {
		public ReferenciaNoGamaMostrador mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ReferenciaNoGamaMostrador item = new ReferenciaNoGamaMostrador();
			
			item.setCodArtFormLog(rs.getLong("COD_ART_FORMLOG"));
			item.setDenomRef(rs.getString("DENOM_REF"));
			
			Double costo = rs.getDouble("COSTO");
			item.setCosto(!rs.wasNull() ? costo : null);
			
			Long unidadesCaja = rs.getLong("UNI_CAJA_SERV") ;
			item.setUnidadesCaja(!rs.wasNull() ? unidadesCaja : null);
			
			Double pvp = rs.getDouble("PVP_TARIFA");
			item.setPvpTarifa(!rs.wasNull() ? pvp : null);
			
			Double iva = rs.getDouble("IVA");
			item.setIva(!rs.wasNull() ? iva : null);
			
			Double margen = rs.getDouble("MARGEN");
			item.setMargen(!rs.wasNull() ? margen : null);
			
			Double cc = rs.getDouble("CC");
			item.setCc(!rs.wasNull() ? cc : null);
			
			item.setNumOrden(rs.getInt("NUM_ORDEN"));
			item.setFoto(rs.getString("FOTO"));
			
			return item;
		}

	};

	@Override
	public Long getLstReferenciasNoGamaCount(String sesionId) {
		String query = " SELECT COUNT(*) FROM T_MIS_REF_NO_GAMA_MOSTRADOR WHERE ID_SESION = ? ";
		Long cont = null;

		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), new Object[] { sesionId });
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), Arrays.asList(new Object[] { sesionId }), e);
		}

		return cont;
	}
	
}
