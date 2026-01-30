package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VConfirmacionPedidoDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionPedido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VConfirmacionPedidoDaoImpl implements VConfirmacionPedidoDao {
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(VConfirmacionPedidoDaoImpl.class);

	private RowMapper<SeguimientoMiPedidoDetalle> rwSeguimientoMiPedidoDetalleMap = new RowMapper<SeguimientoMiPedidoDetalle>() {
		public SeguimientoMiPedidoDetalle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new SeguimientoMiPedidoDetalle(
					new Centro(resultSet.getLong("cod_centro"), null, null, null, null, null, null, null, null, null,
							null),
					resultSet.getLong("cod_art"), resultSet.getString("descrip_art"), resultSet.getFloat("total"),
					resultSet.getFloat("cajas_normales"), resultSet.getFloat("cajas_empuje"),
					resultSet.getFloat("cajas_cabecera"), resultSet.getFloat("cajas_no_servidas"),
					resultSet.getString("motivo"), resultSet.getFloat("cajas_intertienda"),
					resultSet.getString("color"), resultSet.getString("talla"), resultSet.getString("modeloProveedor"),
					resultSet.getString("motivo_pedido"), resultSet.getLong("cajas_cortadas"),
					resultSet.getString("inc_prevision_venta"), resultSet.getLong("sm_estatico"),
					resultSet.getLong("facing"), resultSet.getString("origen_pedido"), resultSet.getLong("cajas_antes_ajuste"));
		}

	};

	private RowMapper<VConfirmacionPedido> rwVConfirmacionPedidoMap = new RowMapper<VConfirmacionPedido>() {
		public VConfirmacionPedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new VConfirmacionPedido(resultSet.getLong("cod_centro"), resultSet.getLong("cod_plat"),
					resultSet.getLong("cod_ped_plat"), resultSet.getLong("cod_ped_aprov_central"),
					resultSet.getDate("fecha_trans"), resultSet.getDate("fecha_ped"),
					resultSet.getDate("fecha_previs_ent"), resultSet.getLong("cod_art"), resultSet.getFloat("caj_ped"),
					resultSet.getFloat("uni_serv"), resultSet.getString("tipo_ped"),
					resultSet.getString("flg_enviado_pbl"), resultSet.getLong("grupo1"), resultSet.getLong("grupo2"),
					resultSet.getLong("grupo3"), resultSet.getLong("grupo4"), resultSet.getLong("grupo5"),
					resultSet.getString("descrip_art"), resultSet.getFloat("caja_normal"),
					resultSet.getFloat("caja_empuje"), resultSet.getFloat("caja_impl"),
					resultSet.getFloat("caja_intertienda"));
		}

	};

	private RowMapper<VConfirmacionPedido> rwVDetalleTipoPedidoMap = new RowMapper<VConfirmacionPedido>() {
		public VConfirmacionPedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new VConfirmacionPedido(resultSet.getLong("cod_centro"), resultSet.getLong("cod_art"),
					resultSet.getDate("fecha_previs_ent"), resultSet.getFloat("caja_normal"),
					resultSet.getFloat("caja_empuje"), resultSet.getFloat("caja_impl"),
					resultSet.getFloat("caja_intertienda"));
		}

	};

	private RowMapper<GenericExcelVO> rwExcelVConfirmacionPedidoMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),
					Utilidades.obtenerValorExcel(resultSet, 3), Utilidades.obtenerValorExcel(resultSet, 4),
					Utilidades.obtenerValorExcel(resultSet, 5), Utilidades.obtenerValorExcel(resultSet, 6),
					Utilidades.obtenerValorExcel(resultSet, 7), Utilidades.obtenerValorExcel(resultSet, 8),
					Utilidades.obtenerValorExcel(resultSet, 9), Utilidades.obtenerValorExcel(resultSet, 10),
					Utilidades.obtenerValorExcel(resultSet, 11), Utilidades.obtenerValorExcel(resultSet, 12),
					Utilidades.obtenerValorExcel(resultSet, 13), Utilidades.obtenerValorExcel(resultSet, 14),
					Utilidades.obtenerValorExcel(resultSet, 15), Utilidades.obtenerValorExcel(resultSet, 16),
					Utilidades.obtenerValorExcel(resultSet, 17), Utilidades.obtenerValorExcel(resultSet, 18),
					Utilidades.obtenerValorExcel(resultSet, 19), Utilidades.obtenerValorExcel(resultSet, 20),
					Utilidades.obtenerValorExcel(resultSet, 21), Utilidades.obtenerValorExcel(resultSet, 22),
					Utilidades.obtenerValorExcel(resultSet, 23), Utilidades.obtenerValorExcel(resultSet, 24),
					Utilidades.obtenerValorExcel(resultSet, 25), Utilidades.obtenerValorExcel(resultSet, 26),
					Utilidades.obtenerValorExcel(resultSet, 27), Utilidades.obtenerValorExcel(resultSet, 28),
					Utilidades.obtenerValorExcel(resultSet, 29), Utilidades.obtenerValorExcel(resultSet, 30),
					Utilidades.obtenerValorExcel(resultSet, 31), Utilidades.obtenerValorExcel(resultSet, 32),
					Utilidades.obtenerValorExcel(resultSet, 33), Utilidades.obtenerValorExcel(resultSet, 34),
					Utilidades.obtenerValorExcel(resultSet, 35), Utilidades.obtenerValorExcel(resultSet, 36),
					Utilidades.obtenerValorExcel(resultSet, 37), Utilidades.obtenerValorExcel(resultSet, 38),
					Utilidades.obtenerValorExcel(resultSet, 39), Utilidades.obtenerValorExcel(resultSet, 40),
					Utilidades.obtenerValorExcel(resultSet, 41), Utilidades.obtenerValorExcel(resultSet, 42),
					Utilidades.obtenerValorExcel(resultSet, 43), Utilidades.obtenerValorExcel(resultSet, 44),
					Utilidades.obtenerValorExcel(resultSet, 45), Utilidades.obtenerValorExcel(resultSet, 46),
					Utilidades.obtenerValorExcel(resultSet, 47), Utilidades.obtenerValorExcel(resultSet, 48));
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<VConfirmacionPedido> findAll(VConfirmacionPedido vConfirmacionPedido) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COD_CENTRO, COD_PLAT, COD_PED_PLAT"
				+ ", COD_PED_APROV_CENTRAL, FECHA_TRANS " + ", FECHA_PED, FECHA_PREVIS_ENT, COD_ART"
				+ ", CAJ_PED, UNI_SERV, TIPO_PED, FLG_ENVIADO_PBL" + ", GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5"
				+ ", DESCRIP_ART, CAJA_NORMAL, CAJA_EMPUJE, CAJA_IMPL" + ", CAJA_INTERTIENDA "
				+ "FROM V_CONFIRMACION_PEDIDO ");
		where.append("WHERE 1=1 ");

		if (vConfirmacionPedido != null) {
			if (vConfirmacionPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(vConfirmacionPedido.getCodCentro());
			}
			if (vConfirmacionPedido.getCodPlat() != null) {
				where.append(" AND COD_PLAT = ? ");
				params.add(vConfirmacionPedido.getCodPlat());
			}
			if (vConfirmacionPedido.getCodPedPlat() != null) {
				where.append(" AND COD_PED_PLAT = ? ");
				params.add(vConfirmacionPedido.getCodPedPlat());
			}
			if (vConfirmacionPedido.getCodPedAprovCentral() != null) {
				where.append(" AND COD_PED_APROV_CENTRAL = ? ");
				params.add(vConfirmacionPedido.getCodPedAprovCentral());
			}
			if (vConfirmacionPedido.getFechaTrans() != null) {
				where.append(" AND TRUNC(FECHA_TRANS) = TRUNC(?) ");
				params.add(vConfirmacionPedido.getFechaTrans());
			}
			if (vConfirmacionPedido.getFechaPed() != null) {
				where.append(" AND TRUNC(FECHA_PED) = TRUNC(?) ");
				params.add(vConfirmacionPedido.getFechaPed());
			}
			if (vConfirmacionPedido.getFechaPrevisEnt() != null) {
				where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
				params.add(vConfirmacionPedido.getFechaPrevisEnt());
			}
			if (vConfirmacionPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(vConfirmacionPedido.getCodArt());
			}
			if (vConfirmacionPedido.getCajPed() != null) {
				where.append(" AND CAJ_PED = ? ");
				params.add(vConfirmacionPedido.getCajPed());
			}
			if (vConfirmacionPedido.getUniServ() != null) {
				where.append(" AND UNI_SERV = ? ");
				params.add(vConfirmacionPedido.getUniServ());
			}
			if (vConfirmacionPedido.getTipoPed() != null) {
				where.append(" AND UPPER(TIPO_PED) = upper(?) ");
				params.add(vConfirmacionPedido.getTipoPed());
			}
			if (vConfirmacionPedido.getFlgEnviadoPbl() != null) {
				where.append(" AND UPPER(FLG_ENVIADO_PBL) = upper(?) ");
				params.add(vConfirmacionPedido.getFlgEnviadoPbl());
			}
			if (vConfirmacionPedido.getGrupo1() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(vConfirmacionPedido.getGrupo1());
			}
			if (vConfirmacionPedido.getGrupo2() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(vConfirmacionPedido.getGrupo2());
			}
			if (vConfirmacionPedido.getGrupo3() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(vConfirmacionPedido.getGrupo3());
			}
			if (vConfirmacionPedido.getGrupo4() != null) {
				where.append(" AND GRUPO4 = ? ");
				params.add(vConfirmacionPedido.getGrupo4());
			}
			if (vConfirmacionPedido.getGrupo5() != null) {
				where.append(" AND GRUPO5 = ? ");
				params.add(vConfirmacionPedido.getGrupo5());
			}
			if (vConfirmacionPedido.getDescripArt() != null) {
				where.append(" AND UPPER(TIPO_PED) = upper(?) ");
				params.add(vConfirmacionPedido.getDescripArt());
			}
			if (vConfirmacionPedido.getCajaNormal() != null) {
				where.append(" AND CAJA_NORMAL = ? ");
				params.add(vConfirmacionPedido.getCajaNormal());
			}
			if (vConfirmacionPedido.getCajaEmpuje() != null) {
				where.append(" AND CAJA_EMPUJE = ? ");
				params.add(vConfirmacionPedido.getCajaEmpuje());
			}
			if (vConfirmacionPedido.getCajaImpl() != null) {
				where.append(" AND CAJA_IMPL = ? ");
				params.add(vConfirmacionPedido.getCajaImpl());
			}
		}

		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, grupo1, grupo2, grupo3, grupo4, grupo5, cod_art ");
		query.append(order);

		List<VConfirmacionPedido> vConfirmacionPedidoLista = null;

		try {
			vConfirmacionPedidoLista = (List<VConfirmacionPedido>) this.jdbcTemplate.query(query.toString(),
					this.rwVConfirmacionPedidoMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return vConfirmacionPedidoLista;
	}

	// Recupera las líneas de los grid.
	// Sirve para buscar tanto las líneas de un centro CAPRABO, como de un centro
	// EROSKI
	// Sólo devuelve las lineas que quepan en 1 paginación, por lo que para saber
	// las
	// páginas que van a ser necesarias en el grid se utiliza el
	// método "findSeguimientoMiPedidoCont()", que devuelve el número total de
	// líneas
	// a enseñar en todo el grid.
	@Override
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido,
			List<Long> listaReferencias, Pagination pagination) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" AND 1=1 ");

		StringBuffer query = new StringBuffer("SELECT cp.cod_centro" + ", cp.cod_art" + ", cp.motivo_pedido "
				+ ", cp.descrip_art"
				+ ", (NVL(cp.caja_normal, 0) + NVL (cp.caja_empuje, 0) + NVL (cp.caja_impl, 0) + NVL (cp.caja_intertienda, 0)) total"
				+ ", cp.caja_normal cajas_normales" + ", cp.caja_empuje cajas_empuje" + ", cp.caja_impl cajas_cabecera"
				+ ", 0 cajas_no_servidas" + ", '' motivo" + ", cp.caja_intertienda cajas_intertienda"
				+ ", cp.cajas_cortadas cajas_cortadas" + ", cp.inc_prevision_venta inc_prevision_venta"
				+ ", cp.sm_estatico sm_estatico" + ", cp.facing facing" + ", cp.origen_pedido origen_pedido"
				+ ", det.descr_talla talla" + ", det.descr_color color" + ", det.modelo_proveedor modeloProveedor"
				+ ", DECODE(cp.cod_mapa,NULL,cp.cajas_cortadas,0) cajas_antes_ajuste " 
				+ "FROM v_confirmacion_pedido cp, v_datos_especificos_textil det "
				+ "WHERE cp.cod_art = det.cod_art(+) ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append("AND CP.COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append("AND CP.FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append("AND CP.GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append("AND CP.GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append("AND CP.GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
				referencias.append(seguimientoMiPedido.getCodArt());
				if (null != listaReferencias) {
					for (Long referencia : listaReferencias) {
						referencias.append(", ").append(referencia);
					}
				}
				where.append("AND CP.COD_ART IN ( ").append(referencias).append(" )");
			}
			logger.info("seguimientoMiPedido.getMapa() = " + seguimientoMiPedido.getMapa());
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append("AND CP.COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append("AND NVL(CP.COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);

		// A la hora de realizar la ordenación, como se quiere ordenar por
		// referencias/descripciones de centro caprabo o de centro eroski, es necesario
		// pasar la variable isCaprabo
		// y de esta forma saber sobre que campos hacer la ordenación
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedFieldOrderBy(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}
		} else {
			// Si no es centro caprabo ordenarlo con referencia eroski, si es caprabo
			// ordenarlo con referencia caprabo
			String campoOrdenacion = "cp.grupo1, cp.grupo2, cp.grupo3, cp.grupo4, cp.grupo5, cp.cod_art";
			order.append(" ORDER BY " + campoOrdenacion + " ASC ");
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}
		logger.info("sql = " + query.toString());
		List<SeguimientoMiPedidoDetalle> lista = null;
		try {
			lista = (List<SeguimientoMiPedidoDetalle>) this.jdbcTemplate.query(query.toString(),
					this.rwSeguimientoMiPedidoDetalleMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	// Mediante esta función se cuenta el número de líneas totales a enseñar en el
	// grid.
	// De esta forma, tratanto el número total se puede conseguir el número de
	// página a mostrar.
	@Override
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT COUNT(1) " + "FROM v_confirmacion_pedido cp ");
		where.append("WHERE 1=1 ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append("AND cp.cod_centro = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append("AND cp.fecha_previs_ent = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append("AND cp.grupo1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append("AND cp.grupo2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append("AND cp.grupo3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append("AND cp.cod_art = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append("AND cp.cod_mapa = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append("AND NVL(cp.cod_mapa,9999) = 9999 ");
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
	public long findLineasPedidoAjusteCont(Long codCentro) {

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		String query = "SELECT COUNT(1) FROM CONFIRMACION_PEDIDO WHERE cod_centro = ? "
				+ "AND FECHA_PREVIS_ENT >= TRUNC(SYSDATE) " + "AND COD_MAPA is NULL " + "AND CAJAS_CORTADAS <> 0 "
				+ "AND ROWNUM < 2";

		long cont = 0;

		try {
			cont = this.jdbcTemplate.queryForLong(query, params.toArray());
		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query, params, e);
		}

		return cont;

	}

	// Esta función se utiliza para realizar ordenaciones de los select. Esta
	// función
	// se llamaba originalmente getMappedField, pero al utilizarse tanto en un
	// select
	// de una función como en un order by de otra, se ha decidido separarla. Esto se
	// debe a
	// que en el caso de CAPRABO, al buscar la descripción se ha llamado
	// descrip_art_caprabo
	// en la función del ORDER BY, llamada findSeguimientoMiPedido, al haberla
	// nombrado así
	// en el select a mano, encuentra el campo de la columna. Sin embargo, en la
	// función
	// findSeguimientoMiPedidoExcel, realizaba un select sobre descrip_art_caprabo y
	// no encontraba
	// ese campo! Por eso hay que hacer el mismo decode que se encuentra en
	// findSeguimientoMiPedido
	private String getMappedFieldOrderBy(String fieldName, int target) {
		if (fieldName.toUpperCase().equals("REFERENCIA")) {
			return "COD_ART";
		} else if (fieldName.toUpperCase().equals("DESCRIPCION")) {
			return "DESCRIP_ART";
		} else if (fieldName.toUpperCase().equals("TOTAL")) {
			return "(NVL(CAJA_NORMAL,0) + NVL(CAJA_EMPUJE,0) + NVL(CAJA_IMPL,0) + NVL(CAJA_INTERTIENDA,0))";
		} else if (fieldName.toUpperCase().equals("CAJASNORMALES")) {
			return "CAJA_NORMAL";
		} else if (fieldName.toUpperCase().equals("CAJASANTESAJUSTE")) {
			return "cajas_antes_ajuste";
		} else if (fieldName.toUpperCase().equals("CAJASEMPUJE")) {
			return "CAJA_EMPUJE";
		} else if (fieldName.toUpperCase().equals("CAJASCABECERA")) {
			return "CAJA_IMPL";
		} else if (fieldName.toUpperCase().equals("CAJASINTERTIENDA")) {
			return "CAJA_INTERTIENDA";
		} else if (fieldName.toUpperCase().equals("TALLA")) {
			return "DESCR_TALLA";
		} else if (fieldName.toUpperCase().equals("COLOR")) {
			return "DESCR_COLOR";
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "MODELO_PROVEEDOR";
		} else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "MOTIVO_PEDIDO";
		} else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas";
		} else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "inc_prevision_venta";
		} else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico";
		} else if (fieldName.toUpperCase().equals("FACING")) {
			return "facing";
		} else if (fieldName.toUpperCase().equals("ORIGENPEDIDO")) {
			return "origen_pedido";
		} else {
			return fieldName;
		}
	}

	private String getMappedFieldSelect(String fieldName, int target) {
		if (fieldName.toUpperCase().equals("REFERENCIA")) {
			return "CP.COD_ART";
		} else if (fieldName.toUpperCase().equals("DESCRIPCION")) {
			return "CP.DESCRIP_ART";
		} else if (fieldName.toUpperCase().equals("TOTAL")) {
			return "(NVL(CP.CAJA_NORMAL,0) + NVL(CP.CAJA_EMPUJE,0) + NVL(CP.CAJA_IMPL,0) + NVL(CP.CAJA_INTERTIENDA,0))";
		} else if (fieldName.toUpperCase().equals("CAJASNORMALES")) {
			return "CP.CAJA_NORMAL";
		} else if (fieldName.toUpperCase().equals("CAJASANTESAJUSTE")) {
			return "DECODE(CP.COD_MAPA,NULL,CP.CAJAS_CORTADAS,0) cajas_antes_ajuste";
		} else if (fieldName.toUpperCase().equals("CAJASEMPUJE")) {
			return "CP.CAJA_EMPUJE";
		} else if (fieldName.toUpperCase().equals("CAJASCABECERA")) {
			return "CP.CAJA_IMPL";
		} else if (fieldName.toUpperCase().equals("CAJASINTERTIENDA")) {
			return "CP.CAJA_INTERTIENDA";
		} else if (fieldName.toUpperCase().equals("TALLA")) {
			return "CP.DESCR_TALLA";
		} else if (fieldName.toUpperCase().equals("COLOR")) {
			return "CP.DESCR_COLOR";
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "CP.MODELO_PROVEEDOR";
		} else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "CP.MOTIVO_PEDIDO";
		} else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas";
		} else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "NVL(inc_prevision_venta, 0)||'%'";
		} else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico";
		} else if (fieldName.toUpperCase().equals("FACING")) {
			return "facing";
		} else if (fieldName.toUpperCase().equals("ORIGENPEDIDO")) {
			return "origen_pedido";
		} else {
			return fieldName;
		}
	}

	// En el caso de textil, como se quieren seleccionar campos de textil, se
	// utiliza la siguiente función
	private String getJoinMappedFieldSelect(String fieldName, int target) {
		if (fieldName.toUpperCase().equals("REFERENCIA")) {
			return "CP.COD_ART";
		} else if (fieldName.toUpperCase().equals("DESCRIPCION")) {
			return "CP.DESCRIP_ART";
		} else if (fieldName.toUpperCase().equals("TOTAL")) {
			return "(NVL(CP.CAJA_NORMAL,0) + NVL(CP.CAJA_EMPUJE,0) + NVL(CP.CAJA_IMPL,0) + NVL(CP.CAJA_INTERTIENDA,0))";
		} else if (fieldName.toUpperCase().equals("CAJASNORMALES")) {
			return "CP.CAJA_NORMAL";
		} else if (fieldName.toUpperCase().equals("CAJASANTESAJUSTE")) {
			return "DECODE(CP.COD_MAPA,NULL,CP.CAJAS_CORTADAS,0) cajas_antes_ajuste";
		} else if (fieldName.toUpperCase().equals("CAJASEMPUJE")) {
			return "CP.CAJA_EMPUJE";
		} else if (fieldName.toUpperCase().equals("CAJASCABECERA")) {
			return "CP.CAJA_IMPL";
		} else if (fieldName.toUpperCase().equals("CAJASINTERTIENDA")) {
			return "CP.CAJA_INTERTIENDA";
		} else if (fieldName.toUpperCase().equals("TALLA")) {
			return "DET.DESCR_TALLA";
		} else if (fieldName.toUpperCase().equals("COLOR")) {
			return "DET.DESCR_COLOR";
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "DET.MODELO_PROVEEDOR";
		} else if (fieldName.toUpperCase().equals("MOTIVOPEDIDO")) {
			return "CP.MOTIVO_PEDIDO";
		} else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
			return "cajas_cortadas";
		} else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
			return "NVL(inc_prevision_venta, 0)||'%'";
		} else if (fieldName.toUpperCase().equals("SMESTATICO")) {
			return "sm_estatico";
		} else if (fieldName.toUpperCase().equals("FACING")) {
			return "facing";
		} else if (fieldName.toUpperCase().equals("ORIGENPEDIDO")) {
			return "origen_pedido";
		} else {
			return fieldName;
		}
	}

	@Override
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido,
			String[] columnModel) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer whereJoin = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
		whereJoin.append(" ");
		// columnModel
		int j = 0;

		String fields = "null";
		String fieldsJoin = "null";
		List<String> listColumns = Arrays.asList(columnModel);
		for (int i = 0; i < listColumns.size(); i++) {
			// Se han creado dos métodos para funcionar como selector, uno para textil y
			// otro para NO textil
			fields = fields + ", " + this.getMappedFieldSelect(listColumns.get(i), 1);
			fieldsJoin = fieldsJoin + ", " + this.getJoinMappedFieldSelect(listColumns.get(i), 1);
			j++;
		}
		while (j <= 41) {
			fields = fields + ", null";
			fieldsJoin = fieldsJoin + ", null";

			j++;
		}
		StringBuffer query = new StringBuffer("SELECT ");

		if ((seguimientoMiPedido.getCodArea() != null)
				&& (Constantes.AREA_TEXTIL.equals(seguimientoMiPedido.getCodArea().toString()))) {

			query.append(fieldsJoin);
			query.append(" FROM v_confirmacion_pedido cp, v_datos_especificos_textil det ");
			query.append("WHERE cp.cod_art = det.cod_art(+) ");
			whereJoin.append("AND cp.cod_centro = ? ");
			params.add(seguimientoMiPedido.getCodCentro());
			whereJoin.append("AND cp.fecha_previs_ent = TO_DATE(?, 'DD/MM/YYYY') ");
			params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			whereJoin.append("AND cp.grupo1 = ? ");

			query.append(whereJoin);
			params.add(seguimientoMiPedido.getCodArea());
		} else {
			query.append(fields);
			query.append(" FROM v_confirmacion_pedido cp ");

			if (seguimientoMiPedido != null) {
				if (seguimientoMiPedido.getCodCentro() != null) {
					where.append(" AND cp.cod_centro = ? ");
					params.add(seguimientoMiPedido.getCodCentro());
				}
				if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
					where.append("AND cp.fecha_previs_ent = TO_DATE(?, 'DDMMYYYY') ");
					params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
				}
				if (seguimientoMiPedido.getCodArea() != null) {
					where.append("AND CP.GRUPO1 = ? ");
					params.add(seguimientoMiPedido.getCodArea());
				}
				if (seguimientoMiPedido.getCodSeccion() != null) {
					where.append("AND CP.GRUPO2 = ? ");
					params.add(seguimientoMiPedido.getCodSeccion());
				}
				if (seguimientoMiPedido.getCodCategoria() != null) {
					where.append("AND CP.GRUPO3 = ? ");
					params.add(seguimientoMiPedido.getCodCategoria());
				}
				if (seguimientoMiPedido.getCodArt() != null) {
					where.append("AND CP.COD_ART = ? ");
					params.add(seguimientoMiPedido.getCodArt());
				}
				if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
						&& !seguimientoMiPedido.getMapa().equals("0")) {
					where.append("AND CP.COD_MAPA = ? ");
					params.add(seguimientoMiPedido.getMapa());
				} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
					where.append("AND NVL(CP.COD_MAPA,9999) = 9999 ");
				}
			}

			query.append(where);
		}

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		order.append(" ORDER BY cp.grupo1, cp.grupo2, cp.grupo3, cp.grupo4, cp.grupo5, cp.cod_art");

		query.append(order);

		List<GenericExcelVO> lista = null;

		try {
			lista = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(), this.rwExcelVConfirmacionPedidoMap,
					params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	@Override
	public Long findTotalReferenciasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT COUNT(DISTINCT(COD_ART)) " + "FROM v_confirmacion_pedido ");

		where.append("WHERE caja_normal IS NOT NULL " + "AND caja_normal <> 0 ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
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
	public Float findTotalCajasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT ROUND(SUM(NVL(CAJA_NORMAL,0)),1) " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);

		Float total = null;
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class, params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return total;
	}

	@Override
	public Long findTotalReferenciasEmpuje(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE CAJA_EMPUJE IS NOT NULL AND CAJA_EMPUJE <> 0 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
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
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT ROUND(SUM(NVL(CAJA_EMPUJE,0)),1) " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);

		Float total = null;
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class, params.toArray());

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return total;
	}

	@Override
	public Long findTotalReferenciasImplCab(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE CAJA_IMPL IS NOT NULL AND CAJA_IMPL <> 0 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
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
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT ROUND(SUM(NVL(CAJA_IMPL,0)),1) " + "FROM v_confirmacion_pedido ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);

		Float total = null;
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return total;
	}

	public Long findTotalReferenciasIntertienda(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE CAJA_INTERTIENDA IS NOT NULL AND CAJA_INTERTIENDA <> 0 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
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
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				"SELECT ROUND(SUM(NVL(CAJA_INTERTIENDA,0)),1) " + "FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);

		Float total = null;
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return total;
	}

	@Override
	public VConfirmacionPedido findDetalleTipoPedido(SeguimientoMiPedido seguimientoMiPedido) {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT COD_CENTRO, COD_ART, FECHA_PREVIS_ENT, SUM (caja_normal) caja_normal, "
						+ "sum(caja_empuje) caja_empuje, sum(caja_impl) caja_impl " + " FROM V_CONFIRMACION_PEDIDO ");

		if (seguimientoMiPedido != null) {
			if (seguimientoMiPedido.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(seguimientoMiPedido.getCodCentro());
			}
			if (seguimientoMiPedido.getFechaPedidoDDMMYYYY() != null) {
				where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
				params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());
			}
			if (seguimientoMiPedido.getCodArea() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(seguimientoMiPedido.getCodArea());
			}
			if (seguimientoMiPedido.getCodSeccion() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(seguimientoMiPedido.getCodSeccion());
			}
			if (seguimientoMiPedido.getCodCategoria() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(seguimientoMiPedido.getCodCategoria());
			}
			if (seguimientoMiPedido.getCodArt() != null) {
				where.append(" AND COD_ART = ? ");
				params.add(seguimientoMiPedido.getCodArt());
			}
			if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().length() > 0
					&& !seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND COD_MAPA = ? ");
				params.add(seguimientoMiPedido.getMapa());
			} else if (seguimientoMiPedido.getMapa() != null && seguimientoMiPedido.getMapa().equals("0")) {
				where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
			}
		}

		query.append(where);
		StringBuffer groupby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupby.append(" group by cod_centro, cod_art, fecha_previs_ent ");
		query.append(groupby);

		VConfirmacionPedido vConfirmacionPedido = null;
		try {
			vConfirmacionPedido = this.jdbcTemplate.queryForObject(query.toString(), this.rwVDetalleTipoPedidoMap,
					params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return vConfirmacionPedido;
	}

	@Override
	public int countCentroVegalsa(Long codCentro) throws Exception {

		String query = "SELECT count(cod_centro) " + "FROM v_centros_plataformas " + "WHERE cod_centro = ? "
				+ "AND cod_soc = 13";

		int cont = 0;

		try {
			cont = this.jdbcTemplate.queryForObject(query, new Object[] { codCentro }, Integer.class);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(),
					new ArrayList<Object>(Arrays.asList(new Object[] { codCentro })), e);
		}

		return cont;
	}
}
