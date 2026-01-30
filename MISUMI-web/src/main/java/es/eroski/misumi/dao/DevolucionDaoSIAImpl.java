package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DevolucionDaoSIA;
import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.DevolucionCatalogoDescripcion;
import es.eroski.misumi.model.DevolucionCatalogoEstado;
import es.eroski.misumi.model.DevolucionDescripcion;
import es.eroski.misumi.model.DevolucionEmail;
import es.eroski.misumi.model.DevolucionEstado;
import es.eroski.misumi.model.DevolucionFinCampana;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionOrdenRecogida;
import es.eroski.misumi.model.DevolucionOrdenRetirada;
import es.eroski.misumi.model.DevolucionPlataforma;
import es.eroski.misumi.model.DevolucionTipo;
import es.eroski.misumi.model.DevolucionTipos;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class DevolucionDaoSIAImpl implements DevolucionDaoSIA {

	private JdbcTemplate jdbcTemplate;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DENOM_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_MSGERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_LISTA= 5;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_ESTADO_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_CODERR = 7;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_MSGERR = 8;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_LISTA= 9;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_CAB_DEVOL 
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERA_CODERR = 7;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERA_MSGERR = 8;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERAS_LISTA= 9;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_APR_OBT_DEVOL_TOTAL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_CODERR = 7;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_MSGERR = 8;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_LISTA= 9;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ACTUALIZAR_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_CODERR = 1;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_MSGERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_OBJ= 3;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_FINALIZAR_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_EMAIL = 3;
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_CODERR = 4;
	private static final int POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_MSGERR = 5;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_DUPLICAR_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_DUPLICAR_DEVOLUCION_CODERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_DUPLICAR_DEVOLUCION_MSGERR = 3;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ELIMINAR_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_ELIMINAR_DEVOLUCION_CODERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_ELIMINAR_DEVOLUCION_MSGERR = 3;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_FINALIZAR_DEVOL
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_DEVOLUCIONES = 2;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_FRESCOS = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_ALI = 4;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_NO_ALI = 5;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_FRESCOS = 6;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_ALI = 7;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_NO_ALI = 8;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_CODERR = 9;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_MSGERR = 10;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_ACT_MISUMI.P_APR_ALTA_DEVOLUCION
	private static final int POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_OBJ = 1;
	private static final int POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_CODERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_MSGERR = 3;

	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.p_obtener_plataformas
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODLOCRECOGIDA = 5;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_DESCRIPRECOGIDA = 6;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODLOCABONO = 7;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_DESCRIPABONO = 8;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_MARCA = 9;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODERR = 10;
	private static final int POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_MSGERR = 11;
	
	//Posiciones PLSQL de salida de PK_APR_DEVOLUCIONES_MISUMI.P_OBTENER_DATOS_COMBO
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_LISTA = 2;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_CODERR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_MSGERR = 4;
	
	//Posiciones PLSQL de salida de  PK_APR_DEVOLUCIONES_MISUMI.P_OBT_CANT_DEVOLVER
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONLINEA_CANTIDADMAXIMA = 2;

	private static Logger logger = Logger.getLogger(DevolucionDaoSIAImpl.class);

	private RowMapper<SeccionBean> rwSeccionesMap = new RowMapper<SeccionBean>() {
		public SeccionBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new SeccionBean(
					resultSet.getLong("SECCION"), // código sección 
					"",                           // descripcion sección
					resultSet.getLong("AREA"));   // código del area de sección
		}

	};

	private RowMapper<DevolucionFinCampana> rwDevolucionFinCampanaMap = new RowMapper<DevolucionFinCampana>() {
		public DevolucionFinCampana mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DevolucionFinCampana(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"), 
					resultSet.getString("DENOMINACION"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null);
		}

	};

	private RowMapper<DevolucionOrdenRetirada> rwDevolucionOrdenRetiradaMap = new RowMapper<DevolucionOrdenRetirada>() {
		public DevolucionOrdenRetirada mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DevolucionOrdenRetirada(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"), 
					resultSet.getString("DENOMINACION"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), 
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null,
					null, null);
		}

	};

	private RowMapper<DevolucionOrdenRecogida> rwDevolucionOrdenRecogidaMap = new RowMapper<DevolucionOrdenRecogida>() {
		public DevolucionOrdenRecogida mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DevolucionOrdenRecogida(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"), 
					resultSet.getString("DENOMINACION"), resultSet.getLong("AREA"),
					resultSet.getLong("SECCION"), resultSet.getLong("CATEGORIA"), resultSet.getLong("SUBCATEGORIA"),
					resultSet.getLong("SEGMENTO"), 
					null, null,
					null, null,
					null, null,
					null, null,
					null, null);
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	@Resource 
	private MessageSource messageSource;

	@Override
	public List<SeccionBean> findAllDevolucionFinCampanaSecciones(DevolucionFinCampana devFinCampana) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DISTINCT AREA, SECCION FROM V_PESCA_MOSTRADOR ");

		if (devFinCampana != null) {
			if (devFinCampana.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devFinCampana.getCodCentro());
			}
			if (devFinCampana.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devFinCampana.getGrupo1());
			}
			if (devFinCampana.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devFinCampana.getGrupo2());
			}
			if (devFinCampana.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devFinCampana.getGrupo3());
			}
			if (devFinCampana.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devFinCampana.getGrupo4());
			}
			if (devFinCampana.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devFinCampana.getGrupo5());
			}
		}

		query.append(where);

		List<SeccionBean> listaDevFinCampana = null;

		try {
			listaDevFinCampana = (List<SeccionBean>) this.jdbcTemplate.query(query.toString(),
					this.rwSeccionesMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return listaDevFinCampana;
	}

	@Override
	public List<DevolucionFinCampana> findAllDevolucionFinCampana(DevolucionFinCampana devFinCampana) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, "
				+ " DESC_SUBCATEGORIA, ORDEN_LISTADO, IDENTIFICA_SUBCAT, COD_ART, "
				+ " DENOMINACION, UNIDADES_CAJA, EAN, IMPORTE_TIRADO, POR_IMP_TIRADO, "
				+ " PROP_PEDIR, PED_MANANA_CAJAS, OFERTA_VIGOR_INI, OFERTA_VIGOR_FIN, OFERTA_FUTURA_INI, OFERTA_FUTURA_FIN " + " FROM V_PESCA_MOSTRADOR ");

		if (devFinCampana != null) {
			if (devFinCampana.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devFinCampana.getCodCentro());
			}
			if (devFinCampana.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devFinCampana.getGrupo1());
			}
			if (devFinCampana.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devFinCampana.getGrupo2());
			}
			if (devFinCampana.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devFinCampana.getGrupo3());
			}
			if (devFinCampana.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devFinCampana.getGrupo4());
			}
			if (devFinCampana.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devFinCampana.getGrupo5());
			}
		}

		query.append(where);

		List<DevolucionFinCampana> listaDevFinCampana = null;

		try {
			listaDevFinCampana = (List<DevolucionFinCampana>) this.jdbcTemplate.query(query.toString(),
					this.rwDevolucionFinCampanaMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return listaDevFinCampana;
	}

	@Override
	public Long findAllDevolucionFinCampanaCount(DevolucionFinCampana devFinCampana) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT (*) " + " FROM V_PESCA_MOSTRADOR ");

		if (devFinCampana != null) {
			if (devFinCampana.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devFinCampana.getCodCentro());
			}
			if (devFinCampana.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devFinCampana.getGrupo1());
			}
			if (devFinCampana.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devFinCampana.getGrupo2());
			}
			if (devFinCampana.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devFinCampana.getGrupo3());
			}
			if (devFinCampana.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devFinCampana.getGrupo4());
			}
			if (devFinCampana.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devFinCampana.getGrupo5());
			}
		}

		query.append(where);

		Long countDevFinCampana = null;

		try {
			countDevFinCampana = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return countDevFinCampana;
	}

	@Override
	public List<SeccionBean> findAllDevolucionOrdenRetiradaSecciones(DevolucionOrdenRetirada devOrdenRetirada) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DISTINCT AREA, SECCION FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRetirada != null) {
			if (devOrdenRetirada.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRetirada.getCodCentro());
			}
			if (devOrdenRetirada.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRetirada.getGrupo1());
			}
			if (devOrdenRetirada.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRetirada.getGrupo2());
			}
			if (devOrdenRetirada.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo3());
			}
			if (devOrdenRetirada.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo4());
			}
			if (devOrdenRetirada.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRetirada.getGrupo5());
			}
		}

		query.append(where);

		List<SeccionBean> listaDevOrdenRetirada = null;

		try {
			listaDevOrdenRetirada = (List<SeccionBean>) this.jdbcTemplate.query(query.toString(),
					this.rwSeccionesMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return listaDevOrdenRetirada;
	}

	@Override
	public List<DevolucionOrdenRetirada> findAllDevolucionOrdenRetirada(DevolucionOrdenRetirada devOrdenRetirada) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, "
				+ " DESC_SUBCATEGORIA, ORDEN_LISTADO, IDENTIFICA_SUBCAT, COD_ART, "
				+ " DENOMINACION, UNIDADES_CAJA, EAN, IMPORTE_TIRADO, POR_IMP_TIRADO, "
				+ " PROP_PEDIR, PED_MANANA_CAJAS, OFERTA_VIGOR_INI, OFERTA_VIGOR_FIN, OFERTA_FUTURA_INI, OFERTA_FUTURA_FIN " + " FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRetirada != null) {
			if (devOrdenRetirada.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRetirada.getCodCentro());
			}
			if (devOrdenRetirada.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRetirada.getGrupo1());
			}
			if (devOrdenRetirada.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRetirada.getGrupo2());
			}
			if (devOrdenRetirada.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo3());
			}
			if (devOrdenRetirada.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo4());
			}
			if (devOrdenRetirada.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRetirada.getGrupo5());
			}
		}

		query.append(where);

		List<DevolucionOrdenRetirada> listaDevOrdenRetirada = null;


		try {
			listaDevOrdenRetirada = (List<DevolucionOrdenRetirada>) this.jdbcTemplate.query(query.toString(),
					this.rwDevolucionOrdenRetiradaMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return listaDevOrdenRetirada;
	}

	@Override
	public Long findAllDevolucionOrdenRetiradaCount(DevolucionOrdenRetirada devOrdenRetirada) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT (*) " + " FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRetirada != null) {
			if (devOrdenRetirada.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRetirada.getCodCentro());
			}
			if (devOrdenRetirada.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRetirada.getGrupo1());
			}
			if (devOrdenRetirada.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRetirada.getGrupo2());
			}
			if (devOrdenRetirada.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo3());
			}
			if (devOrdenRetirada.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRetirada.getGrupo4());
			}
			if (devOrdenRetirada.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRetirada.getGrupo5());
			}
		}

		query.append(where);

		Long countDevOrdenRetirada = null;


		try {
			countDevOrdenRetirada = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return countDevOrdenRetirada;
	}

	@Override
	public List<SeccionBean> findAllDevolucionOrdenRecogidaSecciones(DevolucionOrdenRecogida devOrdenRecogida) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DISTINCT AREA, SECCION FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRecogida != null) {
			if (devOrdenRecogida.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRecogida.getCodCentro());
			}
			if (devOrdenRecogida.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRecogida.getGrupo1());
			}
			if (devOrdenRecogida.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRecogida.getGrupo2());
			}
			if (devOrdenRecogida.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo3());
			}
			if (devOrdenRecogida.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo4());
			}
			if (devOrdenRecogida.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRecogida.getGrupo5());
			}
		}

		query.append(where);

		List<SeccionBean> listaDevOrdenRecogida = null;

		try {
			listaDevOrdenRecogida = (List<SeccionBean>) this.jdbcTemplate.query(query.toString(),
					this.rwSeccionesMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return listaDevOrdenRecogida;
	}

	@Override
	public List<DevolucionOrdenRecogida> findAllDevolucionOrdenRecogida(DevolucionOrdenRecogida devOrdenRecogida) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, "
				+ " DESC_SUBCATEGORIA, ORDEN_LISTADO, IDENTIFICA_SUBCAT, COD_ART, "
				+ " DENOMINACION, UNIDADES_CAJA, EAN, IMPORTE_TIRADO, POR_IMP_TIRADO, "
				+ " PROP_PEDIR, PED_MANANA_CAJAS, OFERTA_VIGOR_INI, OFERTA_VIGOR_FIN, OFERTA_FUTURA_INI, OFERTA_FUTURA_FIN " + " FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRecogida != null) {
			if (devOrdenRecogida.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRecogida.getCodCentro());
			}
			if (devOrdenRecogida.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRecogida.getGrupo1());
			}
			if (devOrdenRecogida.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRecogida.getGrupo2());
			}
			if (devOrdenRecogida.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo3());
			}
			if (devOrdenRecogida.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo4());
			}
			if (devOrdenRecogida.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRecogida.getGrupo5());
			}
		}

		query.append(where);

		List<DevolucionOrdenRecogida> listaDevOrdenRecogida = null;

		try {
			listaDevOrdenRecogida = (List<DevolucionOrdenRecogida>) this.jdbcTemplate.query(query.toString(),
					this.rwDevolucionOrdenRecogidaMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}


		return listaDevOrdenRecogida;
	}

	@Override
	public Long findAllDevolucionOrdenRecogidaCount(DevolucionOrdenRecogida devOrdenRecogida) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT (*) " + " FROM V_PESCA_MOSTRADOR ");

		if (devOrdenRecogida != null) {
			if (devOrdenRecogida.getCodCentro() != null) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(devOrdenRecogida.getCodCentro());
			}
			if (devOrdenRecogida.getGrupo1() != null) {
				where.append(" AND AREA = ? ");
				params.add(devOrdenRecogida.getGrupo1());
			}
			if (devOrdenRecogida.getGrupo2() != null) {
				where.append(" AND SECCION = ? ");
				params.add(devOrdenRecogida.getGrupo2());
			}
			if (devOrdenRecogida.getGrupo3() != null) {
				where.append(" AND CATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo3());
			}
			if (devOrdenRecogida.getGrupo4() != null) {
				where.append(" AND SUBCATEGORIA = ? ");
				params.add(devOrdenRecogida.getGrupo4());
			}
			if (devOrdenRecogida.getGrupo5() != null) {
				where.append(" AND SEGMENTO = ? ");
				params.add(devOrdenRecogida.getGrupo5());
			}
		}

		query.append(where);

		Long countDevOrdenRecogida = null;

		try {
			countDevOrdenRecogida = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return countDevOrdenRecogida;
	}


	/**************************************************************************************************************/
	/**************************************************************************************************************/
	/************************************************ LLAMADAS PLSQL **********************************************/
	/**************************************************************************************************************/
	/**************************************************************************************************************/

	/***********************************          PK_APR_DEVOLUCIONES_MISUMI          ***********************************/
	@Override
	public DevolucionCatalogoEstado cargarCabeceraDevoluciones(final Devolucion devolucion) throws Exception {

		DevolucionCatalogoEstado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_apr_obt_cab_devol(?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(devolucion.getCentro().toString()));
						cs.setString(2, devolucion.getFlagHistorico());

						if (devolucion.getTitulo1() != null && !" ".equals(devolucion.getTitulo1())){
							cs.setString(3, devolucion.getTitulo1());
						}else{
							cs.setNull(3,OracleTypes.NULL);
						}

						if (devolucion.getCodArticulo() != null){
							cs.setLong(4, devolucion.getCodArticulo());
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						if(devolucion.getLocalizador() != null && !"".equals(devolucion.getLocalizador())){
							cs.setString(5, devolucion.getLocalizador());
						}else{							
							cs.setNull(5, OracleTypes.NULL);
						}

						if(devolucion.getEstadoCab() != null){
							cs.setInt(6,Integer.parseInt(devolucion.getEstadoCab().toString()));
						}else{							
							cs.setNull(6,OracleTypes.INTEGER);
						}

						cs.registerOutParameter(7,Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.registerOutParameter(9, OracleTypes.ARRAY, "APR_T_R_DEVOLUCION_ESTADOS");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionCatalogoEstado(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERA_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERA_MSGERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_CABECERAS_LISTA);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoEstado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public DevolucionCatalogoEstado cargarEstadoDevoluciones(final Devolucion devolucion) throws Exception {

		DevolucionCatalogoEstado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_apr_obt_estado_devol(?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(devolucion.getCentro().toString()));
						cs.setString(2, devolucion.getFlagHistorico());

						if (devolucion.getTitulo1() != null && !" ".equals(devolucion.getTitulo1())){
							cs.setString(3, devolucion.getTitulo1());
						}else{
							cs.setNull(3,OracleTypes.NULL);
						}

						if (devolucion.getCodArticulo() != null){
							cs.setLong(4, devolucion.getCodArticulo());
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						if(devolucion.getLocalizador() != null && !"".equals(devolucion.getLocalizador())){
							cs.setString(5, devolucion.getLocalizador());
						}else{							
							cs.setNull(5, OracleTypes.NULL);
						}

						if(devolucion.getEstadoCab() != null){
							cs.setInt(6,Integer.parseInt(devolucion.getEstadoCab().toString()));
						}else{							
							cs.setNull(6,OracleTypes.INTEGER);
						}

						cs.registerOutParameter(7,Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.registerOutParameter(9, OracleTypes.ARRAY, "APR_T_R_DEVOLUCION_ESTADOS");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionCatalogoEstado(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_MSGERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_ESTADO_LISTA);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoEstado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public DevolucionCatalogoEstado cargarAllDevoluciones(final Devolucion devolucion) throws Exception {

		DevolucionCatalogoEstado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_apr_obt_devol_total(?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(devolucion.getCentro().toString()));
						cs.setString(2, devolucion.getFlagHistorico());

						if (devolucion.getTitulo1() != null && !" ".equals(devolucion.getTitulo1())){
							cs.setString(3, devolucion.getTitulo1());
						}else{
							cs.setNull(3,OracleTypes.NULL);
						}

						if (devolucion.getCodArticulo() != null){
							cs.setLong(4, devolucion.getCodArticulo());
						}else{							
							cs.setNull(4, OracleTypes.INTEGER);
						}

						if(devolucion.getLocalizador() != null && !"".equals(devolucion.getLocalizador())){
							cs.setString(5, devolucion.getLocalizador());
						}else{							
							cs.setNull(5, OracleTypes.NULL);
						}

						if(devolucion.getEstadoCab() != null){
							cs.setInt(6,Integer.parseInt(devolucion.getEstadoCab().toString()));
						}else{							
							cs.setNull(6,OracleTypes.INTEGER);
						}

						cs.registerOutParameter(7,Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.registerOutParameter(9, OracleTypes.ARRAY, "APR_T_R_DEVOLUCION_ESTADOS");


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionCatalogoEstado(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_MSGERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TODOS_LISTA);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoEstado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}


	@Override
	public DevolucionCatalogoDescripcion cargarDenominacionesDevoluciones(final Devolucion devolucion) throws Exception {

		DevolucionCatalogoDescripcion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_apr_obt_denom_devol(?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(devolucion.getCentro().toString()));
						cs.setString(2, devolucion.getFlagHistorico());
						cs.registerOutParameter(3,Types.INTEGER);
						cs.registerOutParameter(4,Types.VARCHAR);
						cs.registerOutParameter(5, OracleTypes.ARRAY, "APR_T_R_CATALOGO_DEVOLUCIONES");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoDescripcion ret = null;
					try {
						cs.execute();
						ret = obtenerCatalogoDevoluciones(cs,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_CODERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_MSGERR,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_DENOMINACIONES_LISTA);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoDescripcion) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}			

	//Obtiene un objeto de DevolucionCatalogoEstado. Este objeto contiene una lista con los 4 estados distintos y sus elementos, un código de error
	// y otro de descripción.
	private DevolucionCatalogoEstado obtenerDevolucionCatalogoEstado(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsDevolEstado = null;
		List<DevolucionEstado> listaDevolucionEstado = new ArrayList<DevolucionEstado>();
		DevolucionCatalogoEstado devolucionCatalogoEstado = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las DevolucionEstado de la lista 
			ARRAY devolucionEstadosLst = (ARRAY)cs.getObject(idParametroResultado3);
			if (devolucionEstadosLst != null){
				rsDevolEstado = devolucionEstadosLst.getResultSet();

				//Recorrido de la lista de DevolucionEstado
				while (rsDevolEstado.next()) {						
					STRUCT estructuraDevolucionEstado = (STRUCT)rsDevolEstado.getObject(2);
					DevolucionEstado DevolucionEstado = this.mapRowDevolucionEstado(estructuraDevolucionEstado);

					listaDevolucionEstado.add(DevolucionEstado);
				}
			}
		}
		devolucionCatalogoEstado = new DevolucionCatalogoEstado(codError, descError, listaDevolucionEstado);
		return devolucionCatalogoEstado;
	}

	//Obtiene un objeto de . Este objeto contiene una lista con las descripciones de las devoluciones, un código de error y una descripción del error.
	private DevolucionCatalogoDescripcion obtenerCatalogoDevoluciones(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		ResultSet rsDevolDescripcion = null;
		List<DevolucionDescripcion> listaDescripcion = new ArrayList<DevolucionDescripcion>();
		DevolucionCatalogoDescripcion devolucionCatalogoDescripcion = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			//Obtención de las DevolucionEstado de la lista 
			ARRAY descripcionDevolucionLst = (ARRAY)cs.getObject(idParametroResultado3);
			if (descripcionDevolucionLst != null){
				rsDevolDescripcion  = descripcionDevolucionLst.getResultSet();
				int rowNumDevolDescripcion = 0;

				//Recorrido de la lista de DevolucionEstado
				while (rsDevolDescripcion.next()) {						
					STRUCT estructuraDevolucionDescripcion= (STRUCT)rsDevolDescripcion.getObject(2);
					DevolucionDescripcion devDesc = this.mapRowDescripcionDevolucion(estructuraDevolucionDescripcion, rowNumDevolDescripcion);

					listaDescripcion.add(devDesc);
					rowNumDevolDescripcion ++;
				}
			}
		}
		devolucionCatalogoDescripcion = new DevolucionCatalogoDescripcion(codError, descError, listaDescripcion);
		return devolucionCatalogoDescripcion;
	}

	private DevolucionTipos obtenerTipoDevoluciones(CallableStatement cs, int idParametroResultado1, int idParametroResultado2, int idParametroResultado3) throws SQLException {
		ResultSet rsDevolTipo = null;
		List listaTipos = new ArrayList();
		DevolucionTipos devolucionTipos = null;

		BigDecimal codError_BD = cs.getBigDecimal(idParametroResultado2);
		String descError_BD = cs.getString(idParametroResultado3);

		Long codError = (codError_BD != null) && (!"".equals(codError_BD.toString())) ? new Long(codError_BD.toString()) : null;
		String descError = descError_BD;

		if (new BigDecimal("0").equals(codError_BD))
		{
			ARRAY tipoDevolucionLst = (ARRAY)cs.getObject(idParametroResultado1);
			if (tipoDevolucionLst != null) {
				rsDevolTipo = tipoDevolucionLst.getResultSet();
				int rowNumDevolTipo = 0;

				while (rsDevolTipo.next()) {
					STRUCT estructuraDevolucionTipo = (STRUCT)rsDevolTipo.getObject(2);
					DevolucionTipo devTipo = mapRowTiposDevolucion(estructuraDevolucionTipo, rowNumDevolTipo);

					listaTipos.add(devTipo);
					rowNumDevolTipo++;
				}
				devolucionTipos = new DevolucionTipos(codError, descError, listaTipos);
			}
		}
		return devolucionTipos;
	}

	//Obtiene un objeto de DevolucionEstado. Existen 5 estados, 0,1,2,3,4. Nosotros trataremos los estados 1,2,3,4 por lo que en teoría
	//esta función se utilizará para devolver 4 estados distintos.
	private DevolucionEstado mapRowDevolucionEstado(STRUCT estructuraDevolucionEstado) throws SQLException {
		//Iniciar variables
		ResultSet rsDevol = null;
		List<Devolucion> listaDevolucion = new ArrayList<Devolucion>();
		DevolucionEstado devolucionEstado = null;

		//Obtener datos de DevolucionEstado en crudo
		Object[] objectInfo = estructuraDevolucionEstado.getAttributes();

		BigDecimal estadoDevolucionEstado_BD = (BigDecimal)objectInfo[0];
		BigDecimal numDevolucionEstado_BD = (BigDecimal)objectInfo[1];

		//Transformación de datos para estructura de DevolucionEstado
		Long estadoDevolucionEstado = ((estadoDevolucionEstado_BD != null && !("".equals(estadoDevolucionEstado_BD.toString())))?new Long(estadoDevolucionEstado_BD.toString()):null);;
		Long numDevolucionEstado = ((numDevolucionEstado_BD != null && !("".equals(numDevolucionEstado_BD.toString())))?new Long(numDevolucionEstado_BD.toString()):null);;

		//STRUCT estructuraListaDevolucion = (STRUCT) 
		ARRAY devolucionLst = (ARRAY)objectInfo[2];

		if (devolucionLst != null){
			rsDevol = devolucionLst.getResultSet();

			//Recorrido de la lista de Devolucion
			while (rsDevol.next()) {						
				STRUCT estructuraDevolucion = (STRUCT)rsDevol.getObject(2);
				Devolucion devolucion = this.mapRowDevolucion(estructuraDevolucion);

				listaDevolucion.add(devolucion);
			}
			devolucionEstado = new DevolucionEstado(estadoDevolucionEstado, numDevolucionEstado, listaDevolucion);
		}
		return devolucionEstado;
	}

	//Obtiene un objeto de Devolucion. Cada Devolucion equivale a un formulario y tendrá una tabla relacionada.
	private Devolucion mapRowDevolucion(STRUCT estructuraDevolucion) throws SQLException {
		//Iniciar variables
		ResultSet rsLineaDevol = null;
		List<DevolucionLinea> listaDevolucionLinea = new ArrayList<DevolucionLinea>();
		Devolucion devolucionObj = null;

		//Obtener datos de Devolucion en crudo
		Object[] objectInfo =  estructuraDevolucion.getAttributes();

		BigDecimal devolucion_BD = (BigDecimal)objectInfo[0];
		String localizador_BD = (String)objectInfo[1];
		BigDecimal centro_BD = (BigDecimal)objectInfo[2];
		Timestamp fechaDesde_BD = (Timestamp)objectInfo[3];
		Timestamp fechaHasta_BD = (Timestamp)objectInfo[4];
		String flgRecogida_BD = (String)objectInfo[5];
		String abono_BD = (String)objectInfo[6];
		String recogida_BD = (String)objectInfo[7];
		BigDecimal codPlataforma_BD = (BigDecimal)objectInfo[8];
		String titulo1_BD = (String)objectInfo[9];
		String descripcion_BD =(String)objectInfo[10];
		String motivo_BD = (String)objectInfo[11];
		String titulo2_BD = (String)objectInfo[12];
		Timestamp fechaPrecio_BD =(Timestamp)objectInfo[13];
		BigDecimal estadoCab_BD = (BigDecimal)objectInfo[14];
		BigDecimal flgFueraFechas_BD = (BigDecimal)objectInfo[15];
		//BigDecimal codTpCA_BD = (BigDecimal)objectInfo[16]; 
		BigDecimal codError_BD = (BigDecimal)objectInfo[16]; 
		String descError_BD = (String)objectInfo[17];
		//Pet. MISUMI - 114
		BigDecimal costeMaximo_BD = (BigDecimal)objectInfo[18]; 
		String codRMA_BD = (String)objectInfo[19];
		String tipoRMA_BD = (String)objectInfo[20];
	    
		
		//Transformación de datos para estructura de Devolucion
		Long devolucion = ((devolucion_BD != null && !("".equals(devolucion_BD.toString())))?new Long(devolucion_BD.toString()):null);
		String localizador = localizador_BD;
		Long centro = ((centro_BD != null && !("".equals(centro_BD.toString())))?new Long(centro_BD.toString()):null);
		Date fechaDesde = ((fechaDesde_BD != null )?new Date(fechaDesde_BD.getTime()):null);
		Date fechaHasta = ((fechaHasta_BD != null )?new Date(fechaHasta_BD.getTime()):null);
		String flgRecogida = flgRecogida_BD;
		String abono = abono_BD;
		String recogida = recogida_BD;
		Long codPlataforma = ((codPlataforma_BD != null && !("".equals(codPlataforma_BD.toString())))?new Long(codPlataforma_BD.toString()):null);
		String titulo1 = titulo1_BD;
		String descripcion = descripcion_BD;
		String motivo = motivo_BD;
		String titulo2 = titulo2_BD;
		Date fechaPrecio = ((fechaPrecio_BD != null )?new Date(fechaPrecio_BD.getTime()):null);
		Long estadoCab = ((estadoCab_BD != null && !("".equals(estadoCab_BD.toString())))?new Long(estadoCab_BD.toString()):null);
		Long flgFueraFechas = ((flgFueraFechas_BD != null && !("".equals(flgFueraFechas_BD.toString())))?new Long(flgFueraFechas_BD.toString()):null);
		//Long codTpCA = ((codTpCA_BD != null && !("".equals(codTpCA_BD.toString())))?new Long(codTpCA_BD.toString()):null); 
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null); 
		String descError = descError_BD;
		//Pet. MISUMI - 114
		Double costeMaximo = ((costeMaximo_BD != null && !("".equals(costeMaximo_BD.toString())))?new Double(costeMaximo_BD.toString()):null);
		String codRMA = codRMA_BD;
		String tipoRMA = tipoRMA_BD;
	   
		ARRAY DevolucionLineaLst = (ARRAY)objectInfo[21];


		String fechaDesdeStrDDMMYYYY = fechaDesde != null ? Utilidades.obtenerFechaFormateadaEEEddMMMyyyy(fechaDesde):null;
		String fechaHastaStrDDMMYYYY = fechaHasta != null ? Utilidades.obtenerFechaFormateadaEEEddMMMyyyy(fechaHasta):null;
		String fechaPrecioStrDDMMYYYY =  fechaPrecio != null ? Utilidades.obtenerFechaFormateadaEEEddMMMyyyy(fechaPrecio):null;

		//Obtenemos la fecha de hoy y ponemos la hora en 00:00:00:00 para compararla con la fechaHasta que tiene la hora 00:00:00:00. Si la fechaHasta es anterior que la fecha de hoy, 
		//la fecha de devolución se habrá pasado y fechaDeDevolucionPasada será true.
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date today = c.getTime();

		boolean fechaDeDevolucionPasada = false;
		if(fechaHasta != null){
			fechaDeDevolucionPasada = fechaHasta.before(today);
		}

		if (DevolucionLineaLst != null){
			rsLineaDevol = DevolucionLineaLst.getResultSet();
			int rowNumLineaDevol = 0;

			//Recorrido de la lista de DevolucionLinea
			while (rsLineaDevol.next()) {						
				STRUCT estructuraDevolucionLinea = (STRUCT)rsLineaDevol.getObject(2);
				DevolucionLinea devolucionLinea = this.mapRowDevolucionLinea(estructuraDevolucionLinea, rowNumLineaDevol);

				listaDevolucionLinea.add(devolucionLinea);
				rowNumLineaDevol ++;
			}
			
			//Pet. MISUMI - 114
		
			devolucionObj = new Devolucion(devolucion, localizador, centro, fechaDesde, fechaHasta, flgRecogida, abono, recogida, codPlataforma, titulo1, descripcion, motivo, titulo2, fechaPrecio, estadoCab, flgFueraFechas, codError, descError, costeMaximo, codRMA, tipoRMA, listaDevolucionLinea,fechaDesdeStrDDMMYYYY,fechaHastaStrDDMMYYYY,fechaPrecioStrDDMMYYYY,fechaDeDevolucionPasada);
			
		}
		return devolucionObj;
	}

	//Obtiene un objeto de DevolucionLinea. Cada línea equivale a una línea de la tabla de devoluciones
	private DevolucionLinea mapRowDevolucionLinea(STRUCT estructuraDevolucionLinea, int rowNumLineaDevol) throws SQLException {

		DevolucionLinea devolucionLinea = null;
		ResultSet rsLineaDevolBultoCantidad = null;
		List<BultoCantidad> listaBultoCantidad = new ArrayList<BultoCantidad>();
		//Obtener datos de DevolucionLinea en crudo
		Object[] objectInfo = estructuraDevolucionLinea.getAttributes();

		BigDecimal codArticulo_BD =  ((BigDecimal)objectInfo[0]);
		String denominacion_BD = ((String)objectInfo[1]);
		String marca_BD = ((String)objectInfo[2]);
		String seccion_BD = ((String)objectInfo[3]);
		BigDecimal provrGen_BD = ((BigDecimal)objectInfo[4]);
		BigDecimal provrTrabajo_BD = ((BigDecimal)objectInfo[5]);
		String denomProveedor_BD = ((String)objectInfo[6]);
		String familia_BD = ((String)objectInfo[7]);
		String formatoDevuelto_BD = ((String)objectInfo[8]);
		BigDecimal formato_BD = ((BigDecimal)objectInfo[9]);
		String tipoFormato_BD = ((String)objectInfo[10]);
		String pasillo_BD = ((String)objectInfo[11]);
		String estructuraComercial_BD = ((String)objectInfo[12]);
		BigDecimal uc_BD = ((BigDecimal)objectInfo[13]);
		BigDecimal stockActual_BD = ((BigDecimal)objectInfo[14]);
		BigDecimal stockTienda_BD = ((BigDecimal)objectInfo[15]);
		BigDecimal stockDevolver_BD = ((BigDecimal)objectInfo[16]);
		BigDecimal stockDevuelto_BD =((BigDecimal)objectInfo[17]);
		BigDecimal cantAbonada_BD = ((BigDecimal)objectInfo[18]);
		String flgContinuidad_BD = ((String)objectInfo[19]);
		String lote_BD = ((String)objectInfo[20]);
		String nLote_BD = ((String)objectInfo[21]);
		String caducidad_BD = ((String)objectInfo[22]);
		String nCaducidad_BD = ((String)objectInfo[23]);
		String descAbonoError_BD = ((String)objectInfo[24]);
		BigDecimal bulto_BD = ((BigDecimal)objectInfo[25]);
		String ubicacion_BD = ((String)objectInfo[26]);
		String tipoReferencia_BD = ((String)objectInfo[27]);
		BigDecimal estadoLin_BD = ((BigDecimal)objectInfo[28]);
		BigDecimal codTpCA_BD = (BigDecimal)objectInfo[29]; 
		BigDecimal codErrorDevol_BD = ((BigDecimal)objectInfo[30]);
		String descErrorDevol_BD =((String)objectInfo[31]);
		String flgBandejas_BD = ((String)objectInfo[32]);
		BigDecimal stockDevueltoBandejas_BD =((BigDecimal)objectInfo[33]);
		//Pet. MISUMI - 114	
		//Textil
	    String descrTalla_BD = ((String)objectInfo[34]);
	    String descrColor_BD = ((String)objectInfo[35]);
	    String modelo_BD = ((String)objectInfo[36]);
	    String modeloProveedor_BD = ((String)objectInfo[37]);
	    //Bazar
	    BigDecimal costeUnitario_BD = ((BigDecimal)objectInfo[38]);
	    BigDecimal area_BD = ((BigDecimal)objectInfo[39]);
	    BigDecimal cantidadMaxima_BD = ((BigDecimal)objectInfo[40]);
		String flgPesoVariable_BD = ((String)objectInfo[41]); // MISUMI-259
		// En el parametro 42 me va a devolver una lista q contendrá Bulto, cantidad, codError,descError
		ARRAY LineaBultoCantidadLst = (ARRAY)objectInfo[42];

		//Transformación de datos para estructura de DevolucionLinea
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		String denominacion = denominacion_BD;
		String marca = marca_BD;
		String seccion = seccion_BD;
		Long provrGen = ((provrGen_BD != null && !("".equals(provrGen_BD.toString())))?new Long(provrGen_BD.toString()):null);
		Long provrTrabajo = ((provrTrabajo_BD != null && !("".equals(provrTrabajo_BD.toString())))?new Long(provrTrabajo_BD.toString()):null);
		String denomProveedor = denomProveedor_BD;
		String familia = familia_BD;
		String formatoDevuelto = formatoDevuelto_BD;
		Double formato = ((formato_BD != null && !("".equals(formato_BD.toString())))?new Double(formato_BD.toString()):null);
		String tipoFormato = tipoFormato_BD;
		String pasillo = pasillo_BD;
		String estructuraComercial = estructuraComercial_BD;
		Double uc = ((uc_BD != null && !("".equals(uc_BD.toString())))?new Double(uc_BD.toString()):null);
		Double stockActual = ((stockActual_BD != null && !("".equals(stockActual_BD.toString())))?new Double(stockActual_BD.toString()):null);
		Double stockTienda = ((stockTienda_BD != null && !("".equals(stockTienda_BD.toString())))?new Double(stockTienda_BD.toString()):null);
		Double stockDevolver = ((stockDevolver_BD != null && !("".equals(stockDevolver_BD.toString())))?new Double(stockDevolver_BD.toString()):null);
		Double stockDevuelto = ((stockDevuelto_BD != null && !("".equals(stockDevuelto_BD.toString())))?new Double(stockDevuelto_BD.toString()):null);
		Double cantAbonada = ((cantAbonada_BD != null && !("".equals(cantAbonada_BD.toString())))?new Double(cantAbonada_BD.toString()):null);
		String flgContinuidad = flgContinuidad_BD;
		String lote = lote_BD;
		String nLote = nLote_BD;
		String caducidad = caducidad_BD;
		String nCaducidad = nCaducidad_BD;
		String descAbonoError = descAbonoError_BD;
		Long bulto = ((bulto_BD != null && !("".equals(bulto_BD.toString())))?new Long(bulto_BD.toString()):null); 
		String ubicacion = ubicacion_BD;
		String tipoReferencia = tipoReferencia_BD;
		Long estadoLin = ((estadoLin_BD != null && !("".equals(estadoLin_BD.toString())))?new Long(estadoLin_BD.toString()):null);
		Long codTpCA = ((codTpCA_BD != null && !("".equals(codTpCA_BD .toString())))?new Long(codTpCA_BD .toString()):null);
		Long codErrorDevol = ((codErrorDevol_BD != null && !("".equals(codErrorDevol_BD.toString())))?new Long(codErrorDevol_BD.toString()):null);
		String descErrorDevol = descErrorDevol_BD;
		String flgBandejas = flgBandejas_BD;
		String flgPesoVariable = flgPesoVariable_BD;
		Long stockDevueltoBandejas = ((stockDevueltoBandejas_BD != null && !("".equals(stockDevueltoBandejas_BD.toString())))?new Long(stockDevueltoBandejas_BD.toString()):null);
		//Pet. MISUMI - 114

		//Textil
	    String descrTalla = descrTalla_BD;
	    String descrColor = descrColor_BD;
	    String modeloProveedor = modeloProveedor_BD;
	    String modelo = modelo_BD;
	    //Bazar
	    Double costeUnitario = ((costeUnitario_BD != null && !("".equals(costeUnitario_BD.toString())))?new Double(costeUnitario_BD.toString()):null); // modelo
	    Long area = ((area_BD != null && !("".equals(area_BD.toString())))?new Long(area_BD.toString()):null); // modelo
	    Double cantidadMaxima = ((cantidadMaxima_BD != null && !("".equals(cantidadMaxima_BD.toString())))?new Double(cantidadMaxima_BD.toString()):null); // MISUMI-269
	    if (LineaBultoCantidadLst != null){
	    	rsLineaDevolBultoCantidad = LineaBultoCantidadLst.getResultSet();
			//Recorrido de la lista de DevolucionLineaBultoCantidad
			while (rsLineaDevolBultoCantidad.next()) {
				STRUCT estructuraDevolucionLineaBultoCantidad = (STRUCT)rsLineaDevolBultoCantidad.getObject(2);
				BultoCantidad bultoCantidad = this.mapRowBultoCantidad(estructuraDevolucionLineaBultoCantidad);
				listaBultoCantidad.add(bultoCantidad);
			}
	    }
		devolucionLinea = new DevolucionLinea();
		
		devolucionLinea.setCodArticulo(codArticulo);
		devolucionLinea.setDenominacion(denominacion);
		devolucionLinea.setMarca(marca);
		devolucionLinea.setSeccion(seccion);
		devolucionLinea.setProvrGen(provrGen);
		devolucionLinea.setProvrTrabajo(provrTrabajo);
		devolucionLinea.setDenomProveedor(denomProveedor);
		devolucionLinea.setFamilia(familia);
		devolucionLinea.setFormatoDevuelto(formatoDevuelto);
		devolucionLinea.setFormato(formato);
		devolucionLinea.setTipoFormato(tipoFormato);
		devolucionLinea.setPasillo(pasillo);
		devolucionLinea.setEstructuraComercial(estructuraComercial);
		devolucionLinea.setUc(uc);
		devolucionLinea.setStockActual(stockActual);
		devolucionLinea.setStockTienda(stockTienda);
		devolucionLinea.setStockDevolver(stockDevolver);
		devolucionLinea.setStockDevuelto(stockDevuelto);
		devolucionLinea.setCantAbonada(cantAbonada);
		devolucionLinea.setFlgContinuidad(flgContinuidad);
		devolucionLinea.setLote(nLote);
		devolucionLinea.setnLote(nLote);
		devolucionLinea.setCaducidad(caducidad);
		devolucionLinea.setnCaducidad(nCaducidad);
		devolucionLinea.setDescAbonoError(descAbonoError);
		devolucionLinea.setBulto(bulto);
		devolucionLinea.setUbicacion(ubicacion);
		devolucionLinea.setTipoReferencia(tipoReferencia);
		devolucionLinea.setEstadoLin(estadoLin);
		devolucionLinea.setCodError(codErrorDevol);
		devolucionLinea.setDescError(descErrorDevol);
		devolucionLinea.setFlgBandejas(flgBandejas);
		devolucionLinea.setFlgPesoVariable(flgPesoVariable);
		devolucionLinea.setStockDevueltoBandejas(stockDevueltoBandejas);
		devolucionLinea.setCodTpCa(codTpCA);
		devolucionLinea.setDescrTalla(descrTalla);
		devolucionLinea.setDescrColor(descrColor);
		devolucionLinea.setModeloProveedor(modeloProveedor);
		devolucionLinea.setCosteUnitario(costeUnitario);
		devolucionLinea.setArea(area);
		devolucionLinea.setCantidadMaximaLin(cantidadMaxima);
		devolucionLinea.setModelo(modelo);
		devolucionLinea.setListaBultoCantidad(listaBultoCantidad);
		
		return devolucionLinea;
	}
	
	//Obtiene un objeto de BultoCantidad. Cada línea contiene una lista de cantidad-bulto
	private BultoCantidad mapRowBultoCantidad(STRUCT estructuraDevolucionLineaBultoCantidad) throws SQLException {
		BultoCantidad bultoCantidad=null;
		Object[] objectInfo = estructuraDevolucionLineaBultoCantidad.getAttributes();
		BigDecimal bulto_BD = ((BigDecimal)objectInfo[0]);
		BigDecimal stock_BD = ((BigDecimal)objectInfo[1]);
		String estado_BD = ((String)objectInfo[2]);
		BigDecimal stockBandejas_BD =  ((BigDecimal)objectInfo[3]);
		BigDecimal codError_BD = ((BigDecimal)objectInfo[4]);
		String descError_BD = ((String)objectInfo[5]);
		
		Long bulto = ((bulto_BD != null && !("".equals(bulto_BD.toString())))?new Long(bulto_BD.toString()):null);
		Double stock = ((stock_BD != null && !("".equals(stock_BD.toString())))?new Double(stock_BD.toString()):null);
		String estado = estado_BD;
		Long stockBandejas = ((stockBandejas_BD != null && !("".equals(stockBandejas_BD.toString())))?new Long(stockBandejas_BD.toString()):null);
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		bultoCantidad = new BultoCantidad(stock, bulto, estado, stockBandejas, codError, descError);
		
		return bultoCantidad;
	}

	//Obtiene un String con la descripcion de la Devolucion
	private DevolucionDescripcion mapRowDescripcionDevolucion(STRUCT estructuraDevolucionDescripcion, int rowNumDevolDescripcion) throws SQLException {

		//Obtener datos de DevolucionLinea en crudo
		Object[] objectInfo = estructuraDevolucionDescripcion.getAttributes();

		String descripcion = (String) objectInfo[0];
		String localizador = (String) objectInfo[1];

		DevolucionDescripcion desc = new DevolucionDescripcion(descripcion, localizador);

		return desc;
	}

	private DevolucionTipo mapRowTiposDevolucion(STRUCT estructuraDevolucionTipo, int rowNumDevolTipos) throws SQLException {
		Object[] objectInfo = estructuraDevolucionTipo.getAttributes();

		BigDecimal codTpCa_BD = (BigDecimal)objectInfo[0];
		String denominacion = (String)objectInfo[1];

		Long codTpCa = (codTpCa_BD != null) && (!"".equals(codTpCa_BD.toString())) ? new Long(codTpCa_BD.toString()) : null;

		DevolucionTipo tipo = new DevolucionTipo(codTpCa, denominacion);

		return tipo;
	}

	@Override
	public DevolucionAvisos cargarAvisosDevoluciones(final Long codCentro) throws Exception {

		DevolucionAvisos salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_avisos_prep_mercancia(?,?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(codCentro.toString()));

						cs.registerOutParameter(2,Types.VARCHAR);
						cs.registerOutParameter(3,Types.VARCHAR);
						cs.registerOutParameter(4,Types.VARCHAR);
						cs.registerOutParameter(5,Types.VARCHAR);
						
						cs.registerOutParameter(6,Types.VARCHAR);
						cs.registerOutParameter(7,Types.VARCHAR);
						cs.registerOutParameter(8,Types.VARCHAR);
						
						cs.registerOutParameter(9,Types.INTEGER);
						cs.registerOutParameter(10,Types.VARCHAR);


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionAvisos ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionAvisos(cs, codCentro, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_DEVOLUCIONES,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_FRESCOS,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_ALI, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_NO_ALI, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_FRESCOS,POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_ALI, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_URGENTE_NO_ALI, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCION_AVISO_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionAvisos) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public String devolucionConStockVacio(final Devolucion devolucion){
		StringBuffer query = new StringBuffer(" SELECT PK_APR_DEVOLUCIONES_MISUMI.f_cant_stock_dev_vacia(?,?) FROM DUAL");

		List<Object> params = new ArrayList<Object>();
		params.add(devolucion.getCentro());
		params.add(devolucion.getLocalizador());

		return (String)this.jdbcTemplate.queryForObject(query.toString(), params.toArray(),String.class);
	}


	//Obtiene un objeto de DevolucionCatalogoEstado. Este objeto contiene una lista con los 4 estados distintos y sus elementos, un código de error
	// y otro de descripción.
	private DevolucionAvisos obtenerDevolucionAvisos(CallableStatement cs,Long codCentro, int idParametroResultado1,int idParametroResultado2,int idParametroResultado3,int idParametroResultado4,int idParametroResultado5,int idParametroResultado6, int idParametroResultado7, int idParametroResultado8, int idParametroResultado9) throws SQLException{
		//Iniciar variables
		DevolucionAvisos devolucionAvisos = null;

		//Obtención de los parámetros de salida en crudo
		String flgDevoluciones_BD = (String)cs.getString(idParametroResultado1);
		String flgFrescos_BD = (String)cs.getString(idParametroResultado2);
		String flgAli_BD = (String)cs.getString(idParametroResultado3);
		String flgNoAli_BD = (String)cs.getString(idParametroResultado4);
		
		String flgUrgenteFrescos_BD = (String)cs.getString(idParametroResultado5);
		String flgUrgenteAli_BD = (String)cs.getString(idParametroResultado6);
		String flgUrgenteNoAli_BD = (String)cs.getString(idParametroResultado7);
		
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado8);
		String descError_BD = (String)cs.getString(idParametroResultado9);

		//Transformación de datos para estructura de DevolucionAvisos
		String flgDevoluciones = flgDevoluciones_BD;
		String flgFrescos = flgFrescos_BD;
		String flgAli = flgAli_BD;
		String flgNoAli = flgNoAli_BD;
		
		String flgUrgenteFrescos = flgUrgenteFrescos_BD;
		String flgUrgenteAli = flgUrgenteAli_BD;
		String flgUrgenteNoAli = flgUrgenteNoAli_BD;
		
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		devolucionAvisos = new DevolucionAvisos(codCentro, flgDevoluciones, flgFrescos, flgAli, flgNoAli, flgUrgenteFrescos, flgUrgenteAli, flgUrgenteNoAli, codError, descError);

		return devolucionAvisos;
	}

	public List<DevolucionLinea> obtenerCantidadADevolver(final Devolucion devolucion)
			throws Exception
	{
		List salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try
					{
						ARRAY itemConsulta = DevolucionDaoSIAImpl.this.crearEstructuraCantidadADevolver(devolucion, con);

						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.P_OBT_CANT_DEVOLVER(?,?,?,?) }");
						cs.setString(1, devolucion.getCentro().toString());
						cs.setArray(2, itemConsulta);

						cs.registerOutParameter(2, OracleTypes.ARRAY, "APR_T_R_CANT_DEVOLVER");

						cs.registerOutParameter(3, Types.VARCHAR);
						cs.registerOutParameter(4, Types.INTEGER);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					List ret = null;
					try {
						cs.execute();
						ret = DevolucionDaoSIAImpl.this.obtenerCantidadesADevolver(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONLINEA_CANTIDADMAXIMA);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (List)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	private ARRAY crearEstructuraCantidadADevolver(Devolucion devolucion, Connection con) throws SQLException {
		OracleConnection conexionOracle = (OracleConnection)con.getMetaData().getConnection();

		Object[] objectTablaDevolucionLineas = new Object[devolucion.getDevLineas().size()];

		int i = 0;
		for (DevolucionLinea devolucionLinea : devolucion.getDevLineas())
		{
			Object[] objectCantidadMaxima = new Object[3];

			objectCantidadMaxima[0] = devolucionLinea.getCodArticulo();

			objectCantidadMaxima[1] = devolucionLinea.getDenominacion();

			objectCantidadMaxima[2] = devolucionLinea.getStockDevuelto();

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_CANT_DEVOLVER", conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor, conexionOracle, objectCantidadMaxima);

			objectTablaDevolucionLineas[i] = itemObjectStruct;

			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_CANT_DEVOLVER", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionLineas);

		return array;
	}

	private List<DevolucionLinea> obtenerCantidadesADevolver(CallableStatement cs, int idParametroResultado1) throws SQLException {
		ResultSet rsCantidadesMaximas = null;

		List listaDevolucionLinea = new ArrayList();

		ARRAY cantidadesMaximasLst = (ARRAY)cs.getObject(idParametroResultado1);
		if (cantidadesMaximasLst != null) {
			rsCantidadesMaximas = cantidadesMaximasLst.getResultSet();

			while (rsCantidadesMaximas.next()) {
				STRUCT estructuraCantidadMaxima = (STRUCT)rsCantidadesMaximas.getObject(2);
				DevolucionLinea devolucionLinea = mapRowDevolucionLineaCantidadMaxima(estructuraCantidadMaxima);

				listaDevolucionLinea.add(devolucionLinea);
			}
		}
		return listaDevolucionLinea;
	}

	private DevolucionLinea mapRowDevolucionLineaCantidadMaxima(STRUCT estructuraCantidadMaxima) throws SQLException {
		Object[] objectInfo = estructuraCantidadMaxima.getAttributes();

		DevolucionLinea devLin = new DevolucionLinea();

		BigDecimal codArticulo_BD = (BigDecimal)objectInfo[0];
		String denominacion_BD = (String)objectInfo[1];
		BigDecimal cantidad_BD = (BigDecimal)objectInfo[2];

		Long codArticulo = (codArticulo_BD != null) && (!"".equals(codArticulo_BD.toString())) ? new Long(codArticulo_BD.toString()) : null;
		Long cantidad = (cantidad_BD != null) && (!"".equals(cantidad_BD.toString())) ? new Long(cantidad_BD.toString()) : null;

		devLin.setCodArticulo(codArticulo);
		devLin.setCantidadMaximaPermitida(cantidad);
		devLin.setDenominacion(denominacion_BD);

		return devLin;
	}
	/**************************************      PK_APR_DEVOLUCIONES_ACT_MISUMI         ***********************************/

	@Override
	public DevolucionCatalogoEstado actualizarDevolucion(final Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		DevolucionCatalogoEstado salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	
						//Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraActualizacionDevolucionEstado(devolucion, con);

						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_actualizar_devol(?,?,?)}");
						cs.setObject(1,itemConsulta);

						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_T_DEVOLUCION_ESTADOS");
						cs.registerOutParameter(2,Types.INTEGER);
						cs.registerOutParameter(3,Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionEstado(cs,POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_CODERR,POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_MSGERR,POSICION_PARAMETRO_SALIDA_ACTUALIZACION_DEVOLUCION_OBJ);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoEstado) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public DevolucionEmail finalizarDevolucion(final Devolucion devolucion, final String flgRellenarHuecos, String dispositivo) throws Exception {
		// TODO Auto-generated method stub
		DevolucionEmail salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_finalizar_devol(?,?,?,?,?,?)}");
						cs.setInt(1, Integer.parseInt(devolucion.getCentro().toString()));
						cs.setString(2, devolucion.getLocalizador());

						cs.registerOutParameter(3,Types.VARCHAR);
						cs.registerOutParameter(4,Types.INTEGER);
						cs.registerOutParameter(5,Types.VARCHAR);

						cs.setString(6, flgRellenarHuecos);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionEmail ret = null;
					try {
						cs.execute();
						ret = obtenerRespuestaFinalizarDevolucion(cs,POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_EMAIL,POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_CODERR,POSICION_PARAMETRO_SALIDA_FINALIZAR_DEVOLUCION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionEmail) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.error("--------------------------------------------- FINALIZAR DEVOLUCION ---------------------------------------------------");
		logger.error("------------------------ DEVOLUCION FINALIZADA CON LOCALIZADOR: "+devolucion.getLocalizador()+"-----------------------");
		logger.error("---------------------------------------------------- EN CENTRO: "+devolucion.getCentro()+"----------------------------");
		logger.error("--------------------------------------- CON MENSAJE DE ERROR: "+salida.getMsgError()+"--------------------------------");
		logger.error("--------------------------------------------------- DISPOSITIVO:"+dispositivo+"---------------------------------------");
		logger.error("----------------------------------------------------------------------------------------------------------------------");
		return salida;
	}

	@Override
	public Devolucion duplicarDevolucion(final Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		Devolucion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_duplicar_devolucion(?,?,?)}");
						cs.setString(1, devolucion.getLocalizador());

						cs.registerOutParameter(2,Types.INTEGER);
						cs.registerOutParameter(3,Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Devolucion ret = null;
					try {
						cs.execute();
						ret = obtenerRespuestaDuplicarDevolucion(cs, devolucion, POSICION_PARAMETRO_SALIDA_DUPLICAR_DEVOLUCION_CODERR,POSICION_PARAMETRO_SALIDA_DUPLICAR_DEVOLUCION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (Devolucion) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public Devolucion eliminarDevolucion(final Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		Devolucion salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_eliminar_devolucion(?,?,?)}");
						cs.setString(1, devolucion.getLocalizador());

						cs.registerOutParameter(2,Types.INTEGER);
						cs.registerOutParameter(3,Types.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					Devolucion ret = null;
					try {
						cs.execute();
						ret = obtenerRespuestaEliminarDevolucion(cs, devolucion, POSICION_PARAMETRO_SALIDA_ELIMINAR_DEVOLUCION_CODERR,POSICION_PARAMETRO_SALIDA_ELIMINAR_DEVOLUCION_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (Devolucion) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public DevolucionPlataforma obtenerPlataformasDevolucionCreadaPorCentro(final DevolucionPlataforma devolucionPlataforma) throws Exception {

		DevolucionPlataforma salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.p_obtener_plataformas(?,?,?,?,?,?,?,?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(devolucionPlataforma.getCentro().toString()));
						cs.setInt(2, Integer.parseInt(devolucionPlataforma.getCodArticulo().toString()));
						cs.setInt(3, Integer.parseInt(devolucionPlataforma.getCodAbono().toString()));
						cs.setInt(4, Integer.parseInt(devolucionPlataforma.getCodRecogida().toString()));
						cs.registerOutParameter(5, Types.VARCHAR);
						cs.registerOutParameter(6, Types.VARCHAR);
						cs.registerOutParameter(7, Types.VARCHAR);
						cs.registerOutParameter(8, Types.VARCHAR);
						cs.registerOutParameter(9, Types.VARCHAR);
						cs.registerOutParameter(10, Types.INTEGER);
						cs.registerOutParameter(11, Types.VARCHAR);
						
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionPlataforma ret = null;
					try {
						cs.execute();

						ret = obtenerDevolucionPlataforma(cs, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODLOCRECOGIDA, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_DESCRIPRECOGIDA, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODLOCABONO, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_DESCRIPABONO, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_MARCA, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_CODERR, POSICION_PARAMETRO_SALIDA_OBTENER_PLATAFORMA_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionPlataforma)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	@Override
	public DevolucionCatalogoEstado altaDevolucionCreadaPorCentro(final Devolucion devolucion) throws Exception {

		DevolucionCatalogoEstado salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try
					{
						ARRAY itemConsulta = crearEstructuraAltaDevolucionEstado(devolucion, con);

						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_alta_devolucion(?,?,?)}");
						cs.setArray(1, itemConsulta);

						cs.registerOutParameter(1, OracleTypes.ARRAY, "APR_T_R_DEVOLUCION_ESTADOS");
						cs.registerOutParameter(2, Types.INTEGER);
						cs.registerOutParameter(3, Types.VARCHAR);
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionCatalogoEstado ret = null;
					try {
						cs.execute();
						ret = obtenerDevolucionCatalogoEstado(cs, POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_CODERR, POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_MSGERR, POSICION_PARAMETRO_SALIDA_ALTA_DEVOLUCION_OBJ);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionCatalogoEstado)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	//Devuelve un objeto de devolución catalogo estado
	private STRUCT crearEstructuraActualizacionDevolucionEstado(Devolucion devolucion, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		// APR_T_DEVOLUCION_ESTADOS 
		// ESTADO                  NUMBER,
		// NUMERO_REGISTROS        NUMBER,
		// CAB_DEV                 APR_T_R_DEVOLUCION_CAB_DAT 

		//Creamos el objeto que contendrá APR_T_DEVOLUCION_ESTADOS 
		Object[] objectDevolucionEstado = new Object[3];

		//Guardamos el estado de la devolución
		objectDevolucionEstado[0] = devolucion.getEstadoCab();		
		//Será 1 al estar editando las líneas de una cabecera de devolución
		objectDevolucionEstado[1] = 1;
		//Insertamos la lista de devoluciones. En este caso será de 1 elemento.
		objectDevolucionEstado[2] = crearEstructuraActualizacionDevolucion(devolucion, conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_T_DEVOLUCION_ESTADOS",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectDevolucionEstado);

		return itemConsulta;
	}

	private ARRAY crearEstructuraActualizacionDevolucion(Devolucion devolucion, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de devoluciones, en este caso solo habrá 1
		Object[] objectTablaDevoluciones = new Object[1];

		//Objeto de devolución que contiene 18 elementos
		Object[] objectInfo = new Object[22];
		objectInfo[0] = devolucion.getDevolucion();
		objectInfo[1] = devolucion.getLocalizador();
		objectInfo[2] = devolucion.getCentro();
		objectInfo[3] = (devolucion.getFechaDesde()!=null?new Timestamp(devolucion.getFechaDesde().getTime()):null);
		objectInfo[4] = (devolucion.getFechaHasta()!=null?new Timestamp(devolucion.getFechaHasta().getTime()):null);
		objectInfo[5] = devolucion.getFlgRecogida();
		objectInfo[6] = devolucion.getAbono();
		objectInfo[7] = devolucion.getRecogida();
		objectInfo[8] = devolucion.getCodPlataforma();
		objectInfo[9] = devolucion.getTitulo1();
		objectInfo[10] = devolucion.getDescripcion();
		objectInfo[11] = devolucion.getMotivo();
		objectInfo[12] = devolucion.getTitulo2();
		objectInfo[13] = (devolucion.getFechaPrecio()!=null?new Timestamp(devolucion.getFechaPrecio().getTime()):null);
		objectInfo[14] = devolucion.getEstadoCab();
		objectInfo[15] = devolucion.getFlgFueraFechas();
		//objectInfo[16] = devolucion.getCodTpCA();
		objectInfo[16] = devolucion.getCodError();
		objectInfo[17] = devolucion.getDescError();
		//Pet. MISUMI - 114
		objectInfo[18] = devolucion.getCosteMaximo();
		objectInfo[19] = devolucion.getCodRMA();
		objectInfo[20] = devolucion.getTipoRMA();
		objectInfo[21] = crearEstructuraActualizacionDevolucionLinea(devolucion, conexionOracle);

		StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DEVOLUCION_CAB_DAT",conexionOracle);
		STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

		objectTablaDevoluciones[0] = itemObjectStruct;

		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_CAB_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevoluciones);

		return array;
	}

	private ARRAY crearEstructuraActualizacionDevolucionLinea(Devolucion devolucion, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lineas de devolución. Su tamaño será el de la lista de devoluciones
		Object[] objectTablaDevolucionLineas = new Object[devolucion.getDevLineas().size()];

		int i= 0;
		for(DevolucionLinea devolucionLinea:devolucion.getDevLineas()){
			//Objeto de DevolucionLinea que contiene 43 elementos
			Object[] objectInfo = new Object[43];

			//Rellenamos el objeto con la línea de devolución
			objectInfo[0] = devolucionLinea.getCodArticulo();
			objectInfo[1] = devolucionLinea.getDenominacion();
			objectInfo[2] = devolucionLinea.getMarca();
			objectInfo[3] = devolucionLinea.getSeccion();
			objectInfo[4] = devolucionLinea.getProvrGen();
			objectInfo[5] = devolucionLinea.getProvrTrabajo();
			objectInfo[6] = devolucionLinea.getDenomProveedor();
			objectInfo[7] = devolucionLinea.getFamilia();
			objectInfo[8] = devolucionLinea.getFormatoDevuelto();
			objectInfo[9] = devolucionLinea.getFormato();
			objectInfo[10] = devolucionLinea.getTipoFormato();
			objectInfo[11] = devolucionLinea.getPasillo();
			objectInfo[12] = devolucionLinea.getEstructuraComercial();
			objectInfo[13] = devolucionLinea.getUc();
			objectInfo[14] = devolucionLinea.getStockActual();
			objectInfo[15] = devolucionLinea.getStockTienda();
			objectInfo[16] = devolucionLinea.getStockDevolver();
			objectInfo[17] = devolucionLinea.getStockDevuelto();
			objectInfo[18] = devolucionLinea.getCantAbonada();
			objectInfo[19] = devolucionLinea.getFlgContinuidad();
//			objectInfo[20] = devolucionLinea.getLote();
			objectInfo[20] = "";
			objectInfo[21] = devolucionLinea.getnLote();
			objectInfo[22] = devolucionLinea.getCaducidad();
			objectInfo[23] = devolucionLinea.getnCaducidad();
			objectInfo[24] = devolucionLinea.getDescAbonoError();
			objectInfo[25] = devolucionLinea.getBulto();
			objectInfo[26] = devolucionLinea.getUbicacion();
			objectInfo[27] = devolucionLinea.getTipoReferencia();
			objectInfo[28] = devolucionLinea.getEstadoLin();
			objectInfo[29] = devolucionLinea.getCodTpCa();
			objectInfo[30] = devolucionLinea.getCodError();
			objectInfo[31] = devolucionLinea.getDescError();	
			objectInfo[32] = devolucionLinea.getFlgBandejas();
			objectInfo[33] = devolucionLinea.getStockDevueltoBandejas();
			objectInfo[34] = devolucionLinea.getDescrTalla();
			objectInfo[35] = devolucionLinea.getDescrColor();
			objectInfo[36] = devolucionLinea.getModelo();
			objectInfo[37] = devolucionLinea.getModeloProveedor();
			objectInfo[38] = devolucionLinea.getCosteUnitario();
			objectInfo[39] = devolucionLinea.getArea();
			objectInfo[40] = devolucionLinea.getCantidadMaximaLin();
			objectInfo[41] = devolucionLinea.getFlgPesoVariable(); // MISUMI-259
			objectInfo[42] = crearEstructuraActualizacionDevolucionLineaBultoCantidad(devolucionLinea, conexionOracle);

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DEVOLUCION_LINEAS_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaDevolucionLineas[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_LINEAS_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionLineas);

		return array;
	}
	
	private ARRAY crearEstructuraActualizacionDevolucionLineaBultoCantidad(DevolucionLinea devolucionLinea, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lineas de devolución. Su tamaño será el de la lista de bulto/cantidad asociado a la linea
		Object[] objectTablaDevolucionLineaBultoCantidad = new Object[devolucionLinea.getListaBultoCantidad().size()];

		int i= 0;
		for(BultoCantidad bultoCantidad:devolucionLinea.getListaBultoCantidad()){
			Object[] objectInfo = new Object[6];
			//Rellenamos el objeto con la cantidad y los bultos asociados a la liea
			objectInfo[0] = bultoCantidad.getBulto();
			objectInfo[1] = bultoCantidad.getStock();
			objectInfo[2] = bultoCantidad.getEstadoCerrado();
			objectInfo[3] = bultoCantidad.getStockBandejas();
			objectInfo[4] = bultoCantidad.getCodError();
			objectInfo[5] = bultoCantidad.getDescError();
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DEVOLUCION_LIN_BUL_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaDevolucionLineaBultoCantidad[i] = itemObjectStruct;
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_LIN_BUL_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionLineaBultoCantidad);
		return array;
		
	}

	private ARRAY crearEstructuraAltaDevolucionEstado(Devolucion devolucion, Connection con) throws SQLException {
		Object[] objectTablaDevolucionesEstado = new Object[1];

		OracleConnection conexionOracle = (OracleConnection)con.getMetaData().getConnection();

		Object[] objectDevolucionEstado = new Object[3];

		objectDevolucionEstado[0] = devolucion.getEstadoCab();

		objectDevolucionEstado[1] = Integer.valueOf(1);

		objectDevolucionEstado[2] = crearEstructuraAltaDevolucion(devolucion, conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_T_DEVOLUCION_ESTADOS", conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta, conexionOracle, objectDevolucionEstado);

		objectTablaDevolucionesEstado[0] = itemConsulta;

		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_ESTADOS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionesEstado);

		return array;
	}

	private ARRAY crearEstructuraAltaDevolucion(Devolucion devolucion, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de devoluciones, en este caso solo habrá 1
		Object[] objectTablaDevoluciones = new Object[1];

		//Objeto de devolución que contiene 18 elementos
		Object[] objectInfo = new Object[22];
		objectInfo[0] = devolucion.getDevolucion();
		objectInfo[1] = devolucion.getLocalizador();
		objectInfo[2] = devolucion.getCentro();
		objectInfo[3] = (devolucion.getFechaDesde()!=null?new Timestamp(devolucion.getFechaDesde().getTime()):null);
		objectInfo[4] = (devolucion.getFechaHasta()!=null?new Timestamp(devolucion.getFechaHasta().getTime()):null);
		objectInfo[5] = devolucion.getFlgRecogida();
		objectInfo[6] = devolucion.getAbono();
		objectInfo[7] = devolucion.getRecogida();
		objectInfo[8] = devolucion.getCodPlataforma();
		objectInfo[9] = devolucion.getTitulo1();
		objectInfo[10] = devolucion.getDescripcion();
		objectInfo[11] = devolucion.getMotivo();
		objectInfo[12] = devolucion.getTitulo2();
		objectInfo[13] = (devolucion.getFechaPrecio()!=null?new Timestamp(devolucion.getFechaPrecio().getTime()):null);
		objectInfo[14] = devolucion.getEstadoCab();
		objectInfo[15] = devolucion.getFlgFueraFechas();
		//objectInfo[16] = devolucion.getCodTpCA();
		objectInfo[16] = devolucion.getCodError();
		objectInfo[17] = devolucion.getDescError();
		//Pet. MISUMI - 114
		objectInfo[18] = devolucion.getCosteMaximo();
		objectInfo[19] = devolucion.getCodRMA();
		objectInfo[20] = devolucion.getTipoRMA();
		objectInfo[21] = crearEstructuraAltaDevolucionLinea(devolucion, conexionOracle);

		StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DEVOLUCION_CAB_DAT",conexionOracle);
		STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

		objectTablaDevoluciones[0] = itemObjectStruct;

		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_CAB_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevoluciones);

		return array;
	}
	
	private ARRAY crearEstructuraAltaDevolucionLinea(Devolucion devolucion, OracleConnection conexionOracle) throws SQLException {
		//ARRAY de lineas de devolución. Su tamaño será el de la lista de devoluciones
		Object[] objectTablaDevolucionLineas = new Object[devolucion.gettDevLineasLst().size()];

		int i= 0;
		for(TDevolucionLinea tDevolucionLinea:devolucion.gettDevLineasLst()){
			//Objeto de DevolucionLinea que contiene 41 elementos
			Object[] objectInfo = new Object[41];

			//Rellenamos el objeto con la línea de devolución
			objectInfo[0] = tDevolucionLinea.getCodArticulo();
			objectInfo[1] = tDevolucionLinea.getDenominacion();
			objectInfo[2] = tDevolucionLinea.getMarca();
			objectInfo[3] = tDevolucionLinea.getSeccion();
			objectInfo[4] = tDevolucionLinea.getProvrGen();
			objectInfo[5] = tDevolucionLinea.getProvrTrabajo();
			objectInfo[6] = tDevolucionLinea.getDenomProveedor();
			objectInfo[7] = tDevolucionLinea.getFamilia();
			objectInfo[8] = tDevolucionLinea.getFormatoDevuelto();
			objectInfo[9] = tDevolucionLinea.getFormato();
			objectInfo[10] = tDevolucionLinea.getTipoFormato();
			objectInfo[11] = tDevolucionLinea.getPasillo();
			objectInfo[12] = tDevolucionLinea.getEstructuraComercial();
			objectInfo[13] = tDevolucionLinea.getUc();
			objectInfo[14] = tDevolucionLinea.getStockActual();
			objectInfo[15] = tDevolucionLinea.getStockTienda();
			objectInfo[16] = tDevolucionLinea.getStockDevolver();
			objectInfo[17] = tDevolucionLinea.getStockDevuelto();
			objectInfo[18] = tDevolucionLinea.getCantAbonada();
			objectInfo[19] = tDevolucionLinea.getFlgContinuidad();
			objectInfo[20] = tDevolucionLinea.getLote();
			objectInfo[21] = tDevolucionLinea.getnLote();
			objectInfo[22] = tDevolucionLinea.getCaducidad();
			objectInfo[23] = tDevolucionLinea.getnCaducidad();
			objectInfo[24] = tDevolucionLinea.getDescAbonoError();
			objectInfo[25] = tDevolucionLinea.getBulto();
			objectInfo[26] = tDevolucionLinea.getUbicacion();
			objectInfo[27] = tDevolucionLinea.getTipoReferencia();
			objectInfo[28] = tDevolucionLinea.getEstadoLin();
			objectInfo[29] = tDevolucionLinea.getCodTpCa();
			objectInfo[30] = tDevolucionLinea.getCodError();
			objectInfo[31] = tDevolucionLinea.getDescError();	
			objectInfo[32] = tDevolucionLinea.getFlgBandejas();
			objectInfo[33] = tDevolucionLinea.getStockDevueltoBandejas();
			objectInfo[34] = tDevolucionLinea.getDescrTalla();
			objectInfo[35] = tDevolucionLinea.getDescrColor();
			objectInfo[36] = tDevolucionLinea.getModelo();
			objectInfo[37] = tDevolucionLinea.getModeloProveedor();
			objectInfo[38] = tDevolucionLinea.getCosteUnitario();
			objectInfo[39] = tDevolucionLinea.getArea();
			objectInfo[40] = tDevolucionLinea.getFlgPesoVariable(); // MISUMI-259

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DEVOLUCION_LINEAS_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTablaDevolucionLineas[i] = itemObjectStruct;

			//Actualizamos el índice del objeto
			i++;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DEVOLUCION_LINEAS_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTablaDevolucionLineas);

		return array;
	}
	
	//Obtiene un objeto de DevolucionEstado. Este objeto contiene una lista con los 4 estados distintos y sus elementos, un código de error
	// y otro de descripción.
	private DevolucionCatalogoEstado obtenerDevolucionEstado(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Iniciar variables
		List<DevolucionEstado> listaDevolucionEstado = new ArrayList<DevolucionEstado>();
		DevolucionCatalogoEstado devolucionCatalogoEstado = null;

		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		STRUCT estructuraDevolucionEstado = (STRUCT)cs.getObject(idParametroResultado1);

		DevolucionEstado devolucionEstado = this.mapRowDevolucionEstado(estructuraDevolucionEstado);
		listaDevolucionEstado.add(devolucionEstado);

		devolucionCatalogoEstado = new DevolucionCatalogoEstado(codError,descError,listaDevolucionEstado);

		return devolucionCatalogoEstado;
	}

	private DevolucionEmail obtenerRespuestaFinalizarDevolucion(CallableStatement cs,int idParametroResultado1,int idParametroResultado2,int idParametroResultado3) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		String email_BD = (String)cs.getString(idParametroResultado1);
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
		String descError_BD = (String)cs.getString(idParametroResultado3);

		//Transformación de datos para estructura de DevolucionCatalogoEstado
		String email = email_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		DevolucionEmail devolEmail = new DevolucionEmail(email, codError, descError);

		return devolEmail;
	}

	private Devolucion obtenerRespuestaDuplicarDevolucion(CallableStatement cs, Devolucion devolucion, int idParametroResultado1,int idParametroResultado2) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		String email_BD = "";
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		//Transformación de datos para estructura de Devolucion
		String email = email_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;

		//devolucion.setLocalizador("");
		devolucion.setCodError(codError);
		devolucion.setDescError(descError);

		return devolucion;
	}

	private Devolucion obtenerRespuestaEliminarDevolucion(CallableStatement cs, Devolucion devolucion, int idParametroResultado1,int idParametroResultado2) throws SQLException{
		//Obtención de los parámetros de salida en crudo
		String email_BD = "";
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);

		Locale locale = LocaleContextHolder.getLocale();

		//Transformación de datos para estructura de Devolucion
		String email = email_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = (( (codError == null || codError != 0) && (descError_BD == null || "".equals(descError_BD)) )?this.messageSource.getMessage("p85_devoluciones.eliminar.error.desc.vacio", null, locale):descError_BD);

		//devolucion.setLocalizador("");
		devolucion.setCodError(codError);
		devolucion.setDescError(descError);

		return devolucion;
	}

	private DevolucionPlataforma obtenerDevolucionPlataforma(CallableStatement cs, int idParametroResultado1, int idParametroResultado2, int idParametroResultado3, int idParametroResultado4, int idParametroResultado5, int idParametroResultado6, int idParametroResultado7) throws SQLException{	
		//Obtención de los parámetros de salida en crudo
		String codLocAbono_BD = cs.getString(idParametroResultado1);
		String descripPlatAbono_BD = cs.getString(idParametroResultado2);
		String codLocRecogida_BD = cs.getString(idParametroResultado3);
		String descripPlatRecogida_BD = cs.getString(idParametroResultado4);
		String marca_BD = cs.getString(idParametroResultado5);
		BigDecimal codError_BD = cs.getBigDecimal(idParametroResultado6);
		String descError_BD = cs.getString(idParametroResultado7);

		String codLocAbono = codLocAbono_BD;
		String descripPlatAbono = descripPlatAbono_BD;
		String codLocRecogida = codLocRecogida_BD;
		String descripPlatRecogida = descripPlatRecogida_BD;
		String marca = marca_BD;
		Long codError = (codError_BD != null) && (!"".equals(codError_BD.toString())) ? new Long(codError_BD.toString()) : null;
		String descError = descError_BD;

		DevolucionPlataforma devolucionPlataforma = new DevolucionPlataforma();
		devolucionPlataforma.setCodAbono(new Long(codLocAbono));
		devolucionPlataforma.setDescripPlatAbono(descripPlatAbono);
		devolucionPlataforma.setCodRecogida(new Long(codLocRecogida));
		devolucionPlataforma.setDescripPlatRecogida(descripPlatRecogida);
		devolucionPlataforma.setMarca(marca);
		devolucionPlataforma.setCodError(codError);
		devolucionPlataforma.setMsgError(descError);

		return devolucionPlataforma;	
	}

	public DevolucionTipos obtenerDatosCombo(final Centro centro) throws Exception {
		DevolucionTipos salida = null;
		try
		{
			CallableStatementCreator csCreator = new CallableStatementCreator()
			{
				public CallableStatement createCallableStatement(Connection con)
				{
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_MISUMI.P_OBTENER_DATOS_COMBO(?,?,?,?) }");
						cs.setInt(1, Integer.parseInt(centro.getCodCentro().toString()));
						cs.registerOutParameter(2, OracleTypes.ARRAY, "APR_T_R_TIPOS_C_A");
						cs.registerOutParameter(3, Types.INTEGER);
						cs.registerOutParameter(4, Types.VARCHAR);
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback()
			{
				public Object doInCallableStatement(CallableStatement cs) {
					DevolucionTipos ret = null;
					try {
						cs.execute();
						ret = obtenerTipoDevoluciones(cs, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_LISTA, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_CODERR, POSICION_PARAMETRO_SALIDA_CONSULTA_DEVOLUCIONES_TIPOS_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};
			try {
				salida = (DevolucionTipos)this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}
	
	@Override
	public String puedeFinalizar(final Long centro, String localizador){
		StringBuffer query = new StringBuffer("SELECT pk_apr_devoluciones_misumi.f_puede_finalizar_dev(?,?) FROM dual");

		List<Object> params = new ArrayList<Object>();
		params.add(centro);
		params.add(localizador);

		return (String)this.jdbcTemplate.queryForObject(query.toString(), params.toArray(),String.class);
	}

}
