package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaPortType;
import es.eroski.misumi.dao.alarmasPLUSWS.PLUSTiendaServiceLocator;
import es.eroski.misumi.dao.iface.AlarmasPLUDao;
import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.PLU;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.PLUSType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaType;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;

@Repository
public class AlarmasPLUDaoImpl implements AlarmasPLUDao{

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(AlarmasPLUDaoImpl.class);
	
	@Value("${ws.alarmasPLU}")
	private String alarmasPLUWsdl;
	
	private RowMapper<AlarmaPLU> rowMapperAlarmasPLU = new RowMapper<AlarmaPLU>() {
		public AlarmaPLU mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			final AlarmaPLU out = new AlarmaPLU();
			
			out.setFechaGen(rs.getDate("FECHA_GEN"));
			out.setCodCentro(rs.getLong("COD_CENTRO"));
			out.setCodArticulo(rs.getLong("COD_ARTICULO"));
			out.setGrupo1(rs.getLong("GRUPO1"));
			out.setDescGrupo1(rs.getString("DESC_GRUPO1"));
			out.setGrupo2(rs.getLong("GRUPO2"));
			out.setDescGrupo2(rs.getString("DESC_GRUPO2"));
			out.setGrupo3(rs.getLong("GRUPO3"));
			out.setGrupo4(rs.getLong("GRUPO4"));
			out.setGrupo5(rs.getLong("GRUPO5"));
			out.setDenominacion(rs.getString("DENOMINACION"));
			out.setDenomSegmento(rs.getString("DENOM_SEGMENTO"));
			out.setFormato(rs.getString("FORMATO"));
			out.setTipoFormato(rs.getDouble("TIPO_FORMATO"));
			out.setMarca(rs.getString("MARCA"));
			out.setBalanza(rs.getLong("BALANZA"));
			out.setGrupoBalanza(rs.getLong("GRUPO_BALANZA"));
			out.setPlu(rs.getLong("PLU"));
			// rs.getLong devuelve 0 si el dato es NULL. Aceptamos esto, ya que los NULL para este caso contaran como 0
			out.setPlu_1(rs.getLong("PLU_1"));
			//out.setPlu_2(rs.getLong("PLU_2"));
			//out.setPlu_3(rs.getLong("PLU_3"));
			
			out.setPluAnt(rs.getLong("PLU_ANT"));
			out.setDiasCaducidad(rs.getLong("DIAS_CADUCIDAD"));
			out.setMmc(rs.getString("MMC"));
			out.setEb(rs.getString("EB"));
			out.setFechaUltimaVenta(rs.getDate("ULTIMA_VENTA"));
			out.setStock(rs.getDouble("STOCK"));
			out.setStockBandejas(rs.getDouble("STOCK_BANDEJAS"));
			out.setAlbaranes(rs.getString("ALBARANES"));
			out.setCompraVenta(rs.getString("COMPRA_VENTA"));
			out.setFechaCreacion(new java.util.Date(rs.getTimestamp("CREATION_DATE").getTime()));
			out.setFechaActualizacion(new java.util.Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()));
			out.setFlgEnviadoGISAE(rs.getString("FLG_ENVIADO_GISAE"));
			out.setEstadoGrid(rs.getLong("ESTADO_GRID"));
			out.setImprimirEtiquetas(rs.getLong("IMPRIMIR_ETIQUETAS"));
			out.setCodError(rs.getLong("COD_ERROR"));
			out.setMsgError(rs.getString("MSG"));
			out.setDenominacionBalanza(rs.getString("DENOMINACION_BALANZA"));
			out.setFlgVariable(rs.getString("FLG_VARIABLE"));
			
			out.setPluOriginal(rs.getLong("PLU_ORIGINAL"));
			// rs.getLong devuelve 0 si el dato es NULL. Aceptamos esto, ya que los NULL para este caso contaran como 0
			out.setPluOriginal_1(rs.getLong("PLU_ORIGINAL_1"));
			//out.setPluOriginal_2(rs.getLong("PLU_ORIGINAL_2"));
			//out.setPluOriginal_3(rs.getLong("PLU_ORIGINAL_3"));
			
			out.setLstOtrasAgrupacionesBalanza(rs.getString("OTRAS_AGRUPACIONES_BALANZA"));
			return out;
		}
	};

	private RowMapper<AlarmaPLU> rowMapperAlarmasPLUDatosAdicionales = new RowMapper<AlarmaPLU>() {
		public AlarmaPLU mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			final AlarmaPLU out = new AlarmaPLU();
			
			out.setFechaGen(new java.util.Date(rs.getDate("FECHA_GEN").getTime()));
			out.setCodArticulo(rs.getLong("COD_ARTICULO"));
			out.setGrupo1(rs.getLong("GRUPO1"));
			out.setGrupo2(rs.getLong("GRUPO2"));
			out.setGrupo3(rs.getLong("GRUPO3"));
			out.setGrupo4(rs.getLong("GRUPO4"));
			out.setGrupo5(rs.getLong("GRUPO5"));
			out.setDenominacion(rs.getString("DENOMINACION"));
			out.setFormato(rs.getString("FORMATO"));
			out.setTipoFormato(rs.getDouble("TIPO_FORMATO"));
			out.setMarca(rs.getString("MARCA"));
			out.setBalanza(rs.getLong("BALANZA"));
			out.setMmc(rs.getString("MMC"));
			out.setAlbaranes(rs.getString("ALBARANES"));
			out.setCompraVenta(rs.getString("COMPRA_VENTA"));
			out.setFechaCreacion(new java.util.Date(rs.getTimestamp("CREATION_DATE").getTime()));
			out.setFechaActualizacion(new java.util.Date(rs.getTimestamp("LAST_UPDATE_DATE").getTime()));
			out.setFlgEnviadoGISAE(rs.getString("FLG_ENVIADO_GISAE"));
			out.setImprimirEtiquetas(rs.getLong("IMPRIMIR_ETIQUETAS"));
			out.setFlgVariable(rs.getString("FLG_VARIABLE"));
			return out;
		}
	};
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<AlarmaPLU> findAll(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception {
//		reestablecerEstadosGrid(codCentro, agrupacion);
		
		List<Object> params = new ArrayList<Object>();
		logger.debug("AlarmasPLUDaoImpl - findAll");
		logger.debug("AlarmasPLUDaoImpl - codCentro = "+codCentro);
		logger.debug("AlarmasPLUDaoImpl - codArea = "+codArea);
		logger.debug("AlarmasPLUDaoImpl - codSeccion = "+codSeccion);
		logger.debug("AlarmasPLUDaoImpl - agrupacion = "+agrupacion);
		StringBuffer query = new StringBuffer("SELECT FECHA_GEN, COD_CENTRO, COD_ARTICULO, GRUPO1, "
				+ " (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I1' AND O.GRUPO1 = A.GRUPO1) AS DESC_GRUPO1, "
				+ " GRUPO2, "
				+ " (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I2' AND O.GRUPO1 = A.GRUPO1 AND O.GRUPO2 = A.GRUPO2) AS DESC_GRUPO2, "
				+ " GRUPO3, GRUPO4, GRUPO5, DENOMINACION, ");
		query.append(" (SELECT MAX(PLU) FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN BETWEEN SYSDATE AND SYSDATE-10 AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_1, ");
		//query.append(" (SELECT PLU FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-2) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_2, ");
		//query.append(" (SELECT PLU FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-3) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_3, ");
		query.append(" (SELECT MAX(PLU_ORIGINAL) FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN BETWEEN SYSDATE AND SYSDATE-10 AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_1, ");
		//query.append(" (SELECT PLU_ORIGINAL FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-2) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_2, ");
		//query.append(" (SELECT PLU_ORIGINAL FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-3) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_3, ");

		query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I5' AND O.GRUPO1 = A.GRUPO1 AND O.GRUPO2 = A.GRUPO2 AND O.GRUPO3 = A.GRUPO3 AND O.GRUPO4 = A.GRUPO4 AND O.GRUPO5 = A.GRUPO5) AS DENOM_SEGMENTO, ");
		query.append(" FORMATO, TIPO_FORMATO, MARCA, BALANZA, GRUPO_BALANZA, PLU, PLU_ANT, PLU_ORIGINAL, DIAS_CADUCIDAD, MMC, EB, ULTIMA_VENTA, STOCK, STOCK_BANDEJAS, ALBARANES, ");
		query.append(" DECODE(COMPRA_VENTA,'T','COMPRA-VENTA','C','COMPRA','V','VENTA') AS COMPRA_VENTA, ");
		query.append(" CREATION_DATE, LAST_UPDATE_DATE, FLG_ENVIADO_GISAE, ESTADO_GRID, IMPRIMIR_ETIQUETAS, COD_ERROR, MSG, DENOMINACION_BALANZA, FLG_VARIABLE, ");
		query.append(" (SELECT listagg(grupo_balanza,',') FROM T_MIS_ALARMAS_PLU T2 WHERE A.COD_CENTRO=T2.COD_CENTRO and T2.COD_ARTICULO=A.COD_ARTICULO and T2.FECHA_GEN=A.FECHA_GEN and T2.PLU <> 0 and T2.GRUPO_BALANZA <> A.GRUPO_BALANZA) OTRAS_AGRUPACIONES_BALANZA");
		query.append(" FROM T_MIS_ALARMAS_PLU A WHERE COD_CENTRO = ? AND FECHA_GEN = TRUNC(SYSDATE)  ");
		params.add(codCentro);
		
		if (codArea!=null && codArea!=0){
			query.append(" AND GRUPO1 = ? ");
			params.add(codArea);
		}
		
		if (codSeccion!=null && codSeccion!=0){
			query.append(" AND GRUPO2 = ? ");
			params.add(codSeccion);
		}
		
		if (agrupacion!=null){
			query.append(" AND GRUPO_BALANZA = ? ");
			params.add(agrupacion);
		}

		query.append(" order by ESTADO_GRID ASC ");
		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por COD_ART
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append(" , " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append(" , STOCK DESC");
				query.append(order);
			}
		}else{
			order.append(" , STOCK DESC");
			query.append(order);
		}    
		logger.debug("AlarmasPLUDaoImpl - findAll - SQL = "+query.toString());
		
		
		List<AlarmaPLU> lista = null;
		try {
			lista =  this.jdbcTemplate.query(query.toString(),this.rowMapperAlarmasPLU, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
	}
	
	public AlarmaPLU findOne(Long codCentro, Long codArt, Long agrupacion) throws Exception{
		List<Object> params = new ArrayList<Object>();
		logger.debug("AlarmasPLUDaoImpl - findAll");
		logger.debug("AlarmasPLUDaoImpl - codCentro = "+codCentro);
		logger.debug("AlarmasPLUDaoImpl - agrupacion = "+agrupacion);
		StringBuffer query = new StringBuffer("SELECT FECHA_GEN, COD_CENTRO, COD_ARTICULO, GRUPO1, "
				+ " (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I1' AND O.GRUPO1 = A.GRUPO1) AS DESC_GRUPO1, "
				+ " GRUPO2, "
				+ " (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I2' AND O.GRUPO1 = A.GRUPO1 AND O.GRUPO2 = A.GRUPO2) AS DESC_GRUPO2, "
				+ " GRUPO3, GRUPO4, GRUPO5, DENOMINACION, ");
		query.append(" (SELECT MAX(PLU) FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN BETWEEN SYSDATE AND SYSDATE-10 AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_1, ");
		//query.append(" (SELECT PLU FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-1) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_1, ");
		//query.append(" (SELECT PLU FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-2) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_2, ");
		//query.append(" (SELECT PLU FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-3) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_3, ");
		query.append(" (SELECT MAX(PLU_ORIGINAL) FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN BETWEEN SYSDATE AND SYSDATE-10 AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_1, ");
		//query.append(" (SELECT PLU_ORIGINAL FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-1) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_1, ");
		//query.append(" (SELECT PLU_ORIGINAL FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-2) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_2, ");
		//query.append(" (SELECT PLU_ORIGINAL FROM T_MIS_ALARMAS_PLU A2 WHERE A2.COD_CENTRO = A.COD_CENTRO AND A2.COD_ARTICULO = A.COD_ARTICULO AND A2.FECHA_GEN = TRUNC(SYSDATE-3) AND A2.GRUPO_BALANZA = A.GRUPO_BALANZA) AS PLU_ORIGINAL_3, ");

		query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I5' AND O.GRUPO1 = A.GRUPO1 AND O.GRUPO2 = A.GRUPO2 AND O.GRUPO3 = A.GRUPO3 AND O.GRUPO4 = A.GRUPO4 AND O.GRUPO5 = A.GRUPO5) AS DENOM_SEGMENTO, ");
		query.append(" FORMATO, TIPO_FORMATO, MARCA, BALANZA, GRUPO_BALANZA, PLU, PLU_ANT, PLU_ORIGINAL, DIAS_CADUCIDAD, MMC, EB, ULTIMA_VENTA, STOCK, STOCK_BANDEJAS, ALBARANES, ");
		query.append(" DECODE(COMPRA_VENTA,'T','COMPRA-VENTA','C','COMPRA','V','VENTA') AS COMPRA_VENTA, ");
		query.append(" CREATION_DATE, LAST_UPDATE_DATE, FLG_ENVIADO_GISAE, ESTADO_GRID, IMPRIMIR_ETIQUETAS, COD_ERROR, MSG, DENOMINACION_BALANZA, FLG_VARIABLE, ");
		query.append(" (SELECT listagg(grupo_balanza,',') FROM T_MIS_ALARMAS_PLU T2 WHERE A.COD_CENTRO=T2.COD_CENTRO and T2.COD_ARTICULO=A.COD_ARTICULO and T2.FECHA_GEN=A.FECHA_GEN and T2.PLU <> 0 and T2.GRUPO_BALANZA <> A.GRUPO_BALANZA) OTRAS_AGRUPACIONES_BALANZA");
		query.append(" FROM T_MIS_ALARMAS_PLU A WHERE COD_CENTRO = ? AND FECHA_GEN = TRUNC(SYSDATE) AND COD_ARTICULO=? AND GRUPO_BALANZA = ?  ");
		params.add(codCentro);
		params.add(codArt);
		params.add(agrupacion);
		logger.debug("AlarmasPLUDaoImpl - findOne - SQL = "+query.toString());
		
		AlarmaPLU alarmaPLU = null;
		try {
			alarmaPLU =  (AlarmaPLU)this.jdbcTemplate.queryForObject(query.toString(),this.rowMapperAlarmasPLU, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return alarmaPLU;
	}
	
	//Función que sirve para realizar ordenaciones por columna, de esta forma, llega el código de columna con nombre
		//jqgrid y lo transforma a código SQL
		private String getMappedField (String fieldName) {
			if(fieldName.equals("codArticulo")){
				return "COD_ARTICULO";
			}else if(fieldName.equals("denominacion")){
				return "DENOMINACION";
			}else if(fieldName.equals("grupo3")){
				return "GRUPO3";
			}else if(fieldName.equals("grupo4")){
				return "GRUPO4";
			}else if(fieldName.equals("grupo5")){
				return "GRUPO5";
			}else if(fieldName.equals("denomSegmento")){
				return "DENOM_SEGMENTO";
			}else if(fieldName.equals("marca")){
				return "MARCA";
			}else if(fieldName.equals("grupoBalanza")){
				return "GRUPO_BALANZA";
			}else if(fieldName.equals("plu")){
				return "PLU";
			}else if(fieldName.equals("diasCaducidad")){
				return "DIAS_CADUCIDAD";
			}else if(fieldName.equals("mmc")){
				return "MMC";
			}else if(fieldName.equals("eb")){
				return "EB";				
			}else if(fieldName.equals("fechaUltimaVenta")){
				return "ULTIMA_VENTA";
			}else if(fieldName.equals("stock")){
				return "STOCK";
			}else if(fieldName.equals("compraVenta")){
				return "COMPRA_VENTA";
			}else{
				return "STOCK";
			}
			
		}

	@Override
	public boolean hayDatosDeHoyEnTablaIntermedia(Long codCentro) throws Exception{
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		StringBuffer query = new StringBuffer("SELECT COUNT(1) ");
		query.append(" FROM T_MIS_ALARMAS_PLU A ");
		query.append(" WHERE A.COD_CENTRO = ? ");
		query.append(" AND A.FECHA_GEN = TRUNC(SYSDATE) ");
		
		logger.debug("AlarmasPLUDaoImpl - hayDatosDeHoyEnTablaIntermedia - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - hayDatosDeHoyEnTablaIntermedia - codCentro = "+codCentro);
		
		Integer output = null;
		try {
			output =  this.jdbcTemplate.queryForInt(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return false;
		}
		logger.debug("AlarmasPLUDaoImpl - hayDatosDeHoyEnTablaIntermedia - NUM_DATOS = "+output);
		
		return output>0;
	}
	
	@Override
	public boolean existeReferenciaDiferenteGrupoBalanza(Long codCentro,Long codArt, Long grupoBalanza) throws Exception{
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		params.add(grupoBalanza);
		StringBuffer query = new StringBuffer("SELECT COUNT(1) ");
		query.append(" FROM T_MIS_ALARMAS_PLU A ");
		query.append(" WHERE A.COD_CENTRO = ? ");
		query.append(" AND A.FECHA_GEN = TRUNC(SYSDATE) ");
		query.append(" AND A.COD_ARTICULO = ? ");
		query.append(" AND A.GRUPO_BALANZA <> ? ");
		query.append(" AND A.PLU <> 0 ");
		logger.debug("AlarmasPLUDaoImpl - existsReferenciaDiferenteGrupoBalanza - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - existsReferenciaDiferenteGrupoBalanza - codCentro = "+codCentro);
		logger.debug("AlarmasPLUDaoImpl - existsReferenciaDiferenteGrupoBalanza - codArt = "+codArt);
		logger.debug("AlarmasPLUDaoImpl - existsReferenciaDiferenteGrupoBalanza - grupoBalanza = "+grupoBalanza);
		Integer output = null;
		try {
			output =  this.jdbcTemplate.queryForInt(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return false;
		}
		logger.debug("AlarmasPLUDaoImpl - hayDatosDeHoyEnTablaIntermedia - NUM_DATOS = "+output);
		
		return output>0;
	}
	
	@Override
	public List<String> cargaExtraStockAlarmasPLU(Long codCentro){
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		StringBuffer query = new StringBuffer(" SELECT DISTINCT D2.COD_ART ");
		query.append(" FROM STOCK_ACTUAL_CENTRO S, V_DATOS_DIARIO_ART D, DATOS_DIARIO_ART D2 ");
		query.append(" WHERE S.COD_LOC = ? ");
		query.append(" AND S.COD_ARTICULO = D.COD_ART AND S.STOCK > 0 ");
		query.append(" AND (D.GRUPO1, D.GRUPO2, D.GRUPO3) IN ( SELECT COD_AREA, COD_SECCION, COD_CATEGORIA FROM T_MIS_PARAM_AGRU_PLU) ");
		query.append(" AND D.COD_ART = D2.COD_ART AND D.FECHA_GEN = D2.FECHA_GEN ");
		query.append(" AND D2.KILOS_UNI = 1 AND D2.PESO_VARI = 'S' ");
		query.append(" AND D.BALANZA>0 AND D.TIPO_COMPRA_VENTA IN ('T','V') AND D.FORMATO = 'KG'");
		query.append(" AND NOT EXISTS (SELECT 'x' FROM T_MIS_ALARMAS_PLU A WHERE S.COD_LOC = A.COD_CENTRO AND S.COD_ARTICULO = A.COD_ARTICULO AND A.FECHA_GEN = TRUNC(SYSDATE) ) ");

		logger.debug("AlarmasPLUDaoImpl - cargaExtraStockAlarmasPLU - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - cargaExtraStockAlarmasPLU - codCentro = "+codCentro);
		
		List<String> output = new ArrayList<String>();
		try {
			output =  this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		logger.debug("AlarmasPLUDaoImpl - cargaExtraStockAlarmasPLU - Num articulos a cargar: "+output.size());
		
		return output;

	}
	
	@Override
	public List<String> cargaExtraAlbaranesAlarmasPLU(Long codCentro){
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		StringBuffer query = new StringBuffer(" SELECT DISTINCT D2.COD_ART ");
		query.append(" FROM V_MIS_ALBARANES S, V_DATOS_DIARIO_ART D, DATOS_DIARIO_ART D2 ");
		query.append(" WHERE S.COD_CENTRO = ? AND S.TIPO='A' AND S.NIVEL=0 AND S.FECHA_PREVIS_ENT>=TRUNC(SYSDATE)");
		query.append(" AND S.COD_ART = D.COD_ART AND D.BALANZA>0 AND D.TIPO_COMPRA_VENTA IN ('T','V') AND D.FORMATO = 'KG' ");
		query.append(" AND D.GRUPO1 IN (SELECT DISTINCT COD_AREA FROM T_MIS_PARAM_AGRU_PLU) ");
		query.append(" AND D.GRUPO2 IN (SELECT DISTINCT COD_SECCION FROM T_MIS_PARAM_AGRU_PLU) ");
		query.append(" AND D.GRUPO3 IN (SELECT DISTINCT COD_CATEGORIA FROM T_MIS_PARAM_AGRU_PLU) ");
		query.append(" AND D.COD_ART = D2.COD_ART AND D.FECHA_GEN = D2.FECHA_GEN ");
		query.append(" AND D2.KILOS_UNI = 1 AND D2.PESO_VARI = 'S' ");
		query.append(" AND NOT EXISTS (SELECT 'x' FROM T_MIS_ALARMAS_PLU A WHERE S.COD_CENTRO = A.COD_CENTRO AND S.COD_ART = A.COD_ARTICULO AND A.FECHA_GEN = TRUNC(SYSDATE)) ");

		logger.debug("AlarmasPLUDaoImpl - cargaExtraAlbaranesAlarmasPLU - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - cargaExtraAlbaranesAlarmasPLU - codCentro = "+codCentro);
		
		List<String> output = new ArrayList<String>();
		try {
			output =  this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		logger.debug("AlarmasPLUDaoImpl - cargaExtraAlbaranesAlarmasPLU - Num articulos a cargar: "+output.size());
		
		return output;

	}

	@Override
	public ConsultarPLUSResponseType getPLUsGISAE(ConsultarPLUSRequestType request) throws Exception{
		PLUSTiendaServiceLocator locator = new PLUSTiendaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/gcpv/wsdl/PLUSTienda", "PLUSTiendaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
		
		PLUSTiendaPortType port = null;
		URL address = new URL(alarmasPLUWsdl);
		port = locator.getPLUSTiendaPort(address);

		return port.consultarPLUS(request);
	}
	@Override
	public ModificarPLUSResponseType setPLUsGISAE(ModificarPLUSRequestType request) throws Exception{
		PLUSTiendaServiceLocator locator = new PLUSTiendaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		QName qname = new QName("http://www.eroski.es/gcpv/wsdl/PLUSTienda", "PLUSTiendaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);
		
		PLUSTiendaPortType port = null;
		ModificarPLUSResponseType response = null;
		try {
			URL address = new URL(alarmasPLUWsdl);
			port = locator.getPLUSTiendaPort(address);
			response = port.modificarPLUS(request);
		} catch (Exception e) {
			response = new ModificarPLUSResponseType();
			response.setCodigoRespuesta("KO");
			response.setDescripcionRespuesta("ERROR desconocido en el servidor de GISAE al enviar los datos - "+e.getMessage());
			logger.error(StackTraceManager.getStackTrace(e));
		}
		
		return response;
	}
	@Override
	public void insertarDatosEnTablaIntermedia(final List<AlarmaPLU> list){
		
		try{
			StringBuffer query = new StringBuffer(
					"INSERT INTO T_MIS_ALARMAS_PLU (FECHA_GEN, COD_CENTRO, COD_ARTICULO, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, DENOMINACION, FORMATO, "
					+ "TIPO_FORMATO, MARCA, BALANZA, GRUPO_BALANZA, PLU, PLU_ANT, PLU_ORIGINAL, DIAS_CADUCIDAD, MMC, EB, ULTIMA_VENTA, "
					+ "STOCK, STOCK_BANDEJAS, ALBARANES, COMPRA_VENTA, CREATION_DATE, LAST_UPDATE_DATE, FLG_ENVIADO_GISAE, ESTADO_GRID, IMPRIMIR_ETIQUETAS, COD_ERROR, "
					+ "MSG, DENOMINACION_BALANZA, FLG_VARIABLE) VALUES ");
			query.append("(?,?,?,?,?,?,?,?,?,?,");
			query.append("?,?,?,?,?,?,?,?,?,?,");
			query.append("?,?,?,?,?,?,?,?,?,?,");
			query.append("?,?,?,?)");
	
			jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {
	
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AlarmaPLU alarma = list.get(i);
					if (alarma!=null){
						int index = 1;
						logger.debug("insertarDatosEnTablaIntermedia - setValues - fechaGen = "+alarma.getFechaGen()+" - codCentro = "+alarma.getCodCentro()+" - codArticulo = "+alarma.getCodArticulo()+ " - grupoBalanza = "+alarma.getGrupoBalanza());
						ps.setDate(index++, new java.sql.Date(alarma.getFechaGen().getTime()));	
						
						ps.setLong(index++, alarma.getCodCentro());
						ps.setLong(index++, alarma.getCodArticulo());
						
						if (alarma.getGrupo1()!=null){
							ps.setLong(index++, alarma.getGrupo1());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getGrupo2()!=null){
							ps.setLong(index++, alarma.getGrupo2());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getGrupo3()!=null){
							ps.setLong(index++, alarma.getGrupo3());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getGrupo4()!=null){
							ps.setLong(index++, alarma.getGrupo4());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getGrupo5()!=null){
							ps.setLong(index++, alarma.getGrupo5());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getDenominacion()!=null){
							ps.setString(index++, alarma.getDenominacion());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFormato()!=null){
							ps.setString(index++, alarma.getFormato());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getTipoFormato()!=null){
							ps.setDouble(index++, alarma.getTipoFormato());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getMarca()!=null){
							ps.setString(index++, alarma.getMarca());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getBalanza()!=null){
							ps.setLong(index++, alarma.getBalanza());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getGrupoBalanza()!=null){
							ps.setLong(index++, alarma.getGrupoBalanza());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getPlu()!=null){
							ps.setLong(index++, alarma.getPlu());
							ps.setLong(index++, alarma.getPlu());
							ps.setLong(index++, alarma.getPlu());
						}else{
							ps.setNull(index++, Types.NULL);
							ps.setNull(index++, Types.NULL);
							ps.setNull(index++, Types.NULL);
						}
		
						if (alarma.getDiasCaducidad()!=null){
							ps.setLong(index++, alarma.getDiasCaducidad());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getMmc()!=null){
							ps.setString(index++, alarma.getMmc());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getEb()!=null){
							ps.setString(index++, alarma.getEb());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFechaUltimaVenta()!=null){
							final Long ts = alarma.getFechaUltimaVenta().getTime();
							if (ts>0){
								ps.setTimestamp(index++, new java.sql.Timestamp(ts));	
							}else{
								ps.setNull(index++, Types.NULL);
							}
							
						}else{
							ps.setNull(index++, Types.NULL);	
						}
						
						if (alarma.getStock()!=null){
							ps.setDouble(index++, alarma.getStock());
						}else{
							ps.setNull(index++, Types.NULL);	
						}
						
						ps.setNull(index++, Types.NULL);
		
						if (alarma.getAlbaranes()!=null){
							ps.setString(index++, alarma.getAlbaranes());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getCompraVenta()!=null){
							ps.setString(index++, alarma.getCompraVenta());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFechaCreacion()!=null){
							ps.setTimestamp(index++, new java.sql.Timestamp(alarma.getFechaCreacion().getTime()));
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFechaActualizacion()!=null){
							ps.setTimestamp(index++, new java.sql.Timestamp(alarma.getFechaActualizacion().getTime()));
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFlgEnviadoGISAE()!=null){
							ps.setString(index++, alarma.getFlgEnviadoGISAE());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						ps.setNull(index++, Types.NULL);
						
						if (alarma.getImprimirEtiquetas()!=null){
							ps.setLong(index++, alarma.getImprimirEtiquetas());
						}else{
							ps.setNull(index++, Types.NULL);
						}
										
						if (alarma.getCodError()!=null){
							ps.setLong(index++, alarma.getCodError());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getMsgError()!=null){
							ps.setString(index++, alarma.getMsgError());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getDenominacionBalanza()!=null){
							ps.setString(index++, alarma.getDenominacionBalanza());
						}else{
							ps.setNull(index++, Types.NULL);
						}
						
						if (alarma.getFlgVariable()!=null){
							ps.setString(index++, alarma.getFlgVariable());	
						}else{
							ps.setNull(index++, Types.NULL);
						}
					}
				
				}
	
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
		}catch (Exception e){
			logger.error("***********************************************************************");
			logger.error("ERROR AL INSERTAR - SE INTENTARON INSERTAR LOS SIGUIENTES REGISTROS: ");
			for (AlarmaPLU alarma: list){
				logger.error(alarma.toString());
				logger.error("--------------------------");
			}
			logger.error("ERROR AL INSERTAR: "+e.getMessage()+" - "+e.getCause());
			e.printStackTrace();
			logger.error("***********************************************************************");
		}
	}
	
	@Override
	public List<AlarmaPLU> fromGisaeResponseToAlarmasPLU(ReferenciaType[] referencias, Long codCentro){
		List<AlarmaPLU> output = new ArrayList<AlarmaPLU>();
		List<AlarmaPLU> alarmasTmp = new ArrayList<AlarmaPLU>();
		List<Long> codArticulos = new ArrayList<Long>();
		
		for (ReferenciaType ref : referencias){
			if (!ref.getCodigoError().equals(BigInteger.ZERO)){
				continue;
			}
			Long codArticulo = ref.getCodigoReferencia().longValue();
			codArticulos.add(codArticulo);
			if (ref.getListadoPLUS()!=null){
				for (PLUSType plu : ref.getListadoPLUS()){
					AlarmaPLU alarma = new AlarmaPLU();
					alarma.setCodArticulo(codArticulo);
					alarma.setCodCentro(codCentro);
					alarma.setCodError(ref.getCodigoError()!=null?ref.getCodigoError().longValue():null);
					alarma.setMsgError(ref.getMensajeError());
					alarma.setStock(ref.getStockTeorico()!=null?ref.getStockTeorico().doubleValue():null);
					alarma.setEb(ref.getEnvasadoTienda());
					alarma.setDiasCaducidad(ref.getDiasCaducidad()!=null?ref.getDiasCaducidad().longValue():null);
					alarma.setFechaUltimaVenta(ref.getFechaUltimaVenta());
					// DATOS PLU
					alarma.setPlu(plu.getPLU().longValue());
					alarma.setGrupoBalanza(plu.getGrupoBalanza().longValue());
					alarma.setDenominacionBalanza(plu.getDenominacionBalanza());
					
					alarmasTmp.add(alarma);
				}
			}
		}
		List<AlarmaPLU> listaDatosAdicionales = null;
		if (codArticulos.size()>0){
			final String codArticulosString = StringUtils.join(codArticulos,",");
			
			StringBuffer query = new StringBuffer("SELECT DISTINCT SYSDATE AS FECHA_GEN, VD.COD_ART AS COD_ARTICULO, VD.GRUPO1, VD.GRUPO2, VD.GRUPO3, VD.GRUPO4, VD.GRUPO5, "
					+ "VD.DESCRIP_ART AS DENOMINACION, VD.FORMATO, D.KILOS_UNI AS TIPO_FORMATO, M.DESCRIPCION AS MARCA, VD.BALANZA, (SELECT MARCA_MAESTRO_CENTRO FROM V_SURTIDO_TIENDA VS WHERE VS.COD_ART = VD.COD_ART AND VS.COD_CENTRO = ?) AS MMC, "
					+ "(SELECT CASE WHEN (EXISTS (SELECT 1 FROM V_ALBARAN VAL WHERE VAL.COD_ART = VD.COD_ART AND VAL.COD_CENTRO = ? AND VAL.FECHA_PREVIS_ENT = TRUNC(SYSDATE) AND NVL(VAL.ESTADO ,'C') <> 'C')) THEN 'S' ELSE 'N' END FROM DUAL) AS ALBARANES, "
					+ "VD.TIPO_COMPRA_VENTA AS COMPRA_VENTA, "
					+ "SYSDATE AS CREATION_DATE, SYSDATE AS LAST_UPDATE_DATE, 0 AS FLG_ENVIADO_GISAE, "
					+ "0 AS IMPRIMIR_ETIQUETAS, D.PESO_VARI AS FLG_VARIABLE FROM V_DATOS_DIARIO_ART VD, DATOS_DIARIO_ART D, MARCAS M "
					+ "WHERE VD.COD_ART IN ("+codArticulosString+") AND VD.COD_ART = D.COD_ART AND VD.GRUPO1 = D.GRUPO1 AND VD.GRUPO2 = D.GRUPO2 AND VD.GRUPO3 = D.GRUPO3 AND VD.GRUPO4 = D.GRUPO4 AND VD.GRUPO5 = D.GRUPO5 AND VD.FECHA_GEN = D.FECHA_GEN AND VD.COD_MARCA = M.COD_MARCA(+) ");
			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(codCentro);
			logger.debug("AlarmasPLUDaoImpl - fromGisaeResponseToAlarmasPLU - SQL = "+query.toString());

			
			try {
				listaDatosAdicionales =  this.jdbcTemplate.query(query.toString(),this.rowMapperAlarmasPLUDatosAdicionales, params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}
		if (listaDatosAdicionales!=null){
			logger.debug("fromGisaeResponseToAlarmasPLU - Procesando datos adicionales... ("+listaDatosAdicionales.size()+")");

			for (int i=0; i<alarmasTmp.size(); i++){
				final AlarmaPLU alarma = alarmasTmp.get(i);
				final Long codArt1 = alarma.getCodArticulo();
				for (int j=0; j< listaDatosAdicionales.size(); j++){
					final AlarmaPLU alarmaDatosAdicionales = listaDatosAdicionales.get(j);
					final Long codArt2 = alarmaDatosAdicionales.getCodArticulo();
					if (codArt1.equals(codArt2)){
						// Establecemos los parametros adicionales
						final Date fechaGen = alarmaDatosAdicionales.getFechaGen();
						final Long grupo1 = alarmaDatosAdicionales.getGrupo1();
						final Long grupo2 = alarmaDatosAdicionales.getGrupo2();
						final Long grupo3 = alarmaDatosAdicionales.getGrupo3();
						final Long grupo4 = alarmaDatosAdicionales.getGrupo4();
						final Long grupo5 = alarmaDatosAdicionales.getGrupo5();
						final String denominacion = alarmaDatosAdicionales.getDenominacion();
						final String formato = alarmaDatosAdicionales.getFormato();
						final Double tipoFormato = alarmaDatosAdicionales.getTipoFormato();
						final String marca = alarmaDatosAdicionales.getMarca();
						final Long balanza = alarmaDatosAdicionales.getBalanza();
						final String mmc = alarmaDatosAdicionales.getMmc();
						final String albaranes = alarmaDatosAdicionales.getAlbaranes();
						final String compraVenta = alarmaDatosAdicionales.getCompraVenta();
						final Date creationDate = alarmaDatosAdicionales.getFechaCreacion();
						final Date lastUpdateDate = alarmaDatosAdicionales.getFechaActualizacion();
						final String flgEnviadoGisae = alarmaDatosAdicionales.getFlgEnviadoGISAE();
						final Long imprimirEtiquetas = alarmaDatosAdicionales.getImprimirEtiquetas();
						final String pesoVariable = alarmaDatosAdicionales.getFlgVariable();

						alarma.setFechaGen(fechaGen);
						alarma.setGrupo1(grupo1);
						alarma.setGrupo2(grupo2);
						alarma.setGrupo3(grupo3);
						alarma.setGrupo4(grupo4);
						alarma.setGrupo5(grupo5);
						alarma.setDenominacion(denominacion);
						alarma.setFormato(formato);
						alarma.setTipoFormato(tipoFormato);
						alarma.setMarca(marca);
						alarma.setBalanza(balanza);
						alarma.setMmc(mmc);
						alarma.setAlbaranes(albaranes);
						alarma.setCompraVenta(compraVenta);
						alarma.setFechaCreacion(creationDate);
						alarma.setFechaActualizacion(lastUpdateDate);
						alarma.setFlgEnviadoGISAE(flgEnviadoGisae);
						alarma.setImprimirEtiquetas(imprimirEtiquetas);
						alarma.setFlgVariable(pesoVariable);
						alarmasTmp.set(i, alarma);
						break;
					}
				}
			}

		}
		logger.debug("fromGisaeResponseToAlarmasPLU - Registros de salida antes de limpiar: "+alarmasTmp.size());
		// Limpiamos los articulos obtenidos de Gisae de los cuales no tengamos datos (esto solo deberia pasar en desarrollo)
		for (int k=0; k<alarmasTmp.size(); k++){
			final AlarmaPLU alarma = alarmasTmp.get(k);
			final Date fechaGen = alarma.getFechaGen();
			if (fechaGen!=null){
				output.add(alarma);
			}else{
				logger.warn("fromGisaeResponseToAlarmasPLU - No se encuentran datos en la base de datos para el articulo "+alarma.getCodArticulo());
			}
		}
		logger.debug("fromGisaeResponseToAlarmasPLU - Registros de salida despues de limpiar: "+output.size());
		return output;

	}
		
	@Override
	public BigDecimal calculaPendienteRecibir(final Long codArticulo, final Long codCentro){
		// Llamada a procedimiento para calcular si el centro-referencia tiene cajas pendientes de recibir al dia siguiente
		BigDecimal output = new BigDecimal(0);
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call PK_UNI_PEDIDOS.p_uni_obt_cant_pdte_recibir(?, ?, ?, ?, ?) }");

						cs.setInt(1, codCentro.intValue());
						cs.setInt(2, codArticulo.intValue());
						
						cs.registerOutParameter(3, OracleTypes.NUMBER);
						cs.registerOutParameter(4, OracleTypes.NUMBER);
						cs.registerOutParameter(5, OracleTypes.NUMBER);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					BigDecimal ret = null;
					try {
						cs.execute();
						ret = obtenerResultadoPendienteRecibir(cs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				output = (BigDecimal) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	private BigDecimal obtenerResultadoPendienteRecibir(CallableStatement cs) {
		BigDecimal res = new BigDecimal(0);
		try{
			//Obtención de los datos de la estructura
			res = (BigDecimal)cs.getObject(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void asignarPluAnt(Long codCentro, Long plu, Long codArticulo, Long grupoBalanza) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = null;
		if (plu!=null){
			query = new StringBuffer("UPDATE T_MIS_ALARMAS_PLU SET PLU = ?, PLU_ANT = ? WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND FECHA_GEN = TRUNC(SYSDATE) AND GRUPO_BALANZA = "+grupoBalanza);
			params.add(plu);
			params.add(plu);
		}else{
			query = new StringBuffer("UPDATE T_MIS_ALARMAS_PLU SET PLU = 0, PLU_ANT = 0 WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND FECHA_GEN = TRUNC(SYSDATE) AND GRUPO_BALANZA = "+grupoBalanza);
		}
		
		params.add(codCentro);
		params.add(codArticulo);
				
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
	}

	@Override
	public void anotarMsgError(Long codCentro, Long codigoReferencia, Long grupoBalanza, String msg) {
		String updateDate = "";
		if (msg.contains("OK#")){
			updateDate=" LAST_UPDATE_DATE = SYSDATE, "; 
		}
		String query = "UPDATE T_MIS_ALARMAS_PLU SET "+updateDate+"MSG = '"+msg+"' WHERE COD_CENTRO = "+codCentro+" AND FECHA_GEN = TRUNC(SYSDATE) AND COD_ARTICULO = "+codigoReferencia+" AND GRUPO_BALANZA = "+grupoBalanza;
		logger.debug("anotarMsgError - query = "+query);
		try {
			this.jdbcTemplate.update(query);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean insertMaxPLUS(PLU plu){
		logger.debug("---> insertMaxPLUS ");
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO t_mis_param_balanzas_plu "
						+ "( cod_centro, grupo_balanza, numero_plu"
						+ ", usuario_creacion, usuario_modificacion"
						+ ", creation_date, last_update_date"
						+ ") "
				   + "VALUES "
				   		+ "( ?, ?, ?"
				   		+ ", ?, ?"
				   		+ ", SYSDATE, SYSDATE"
				   		+ ")"
				   );
		List<Object> params = new ArrayList<Object>();
		params.add(plu.getCodCentro());
		params.add(plu.getGrupoBalanza());
		params.add(plu.getNumMaxPLU());
		params.add(plu.getUsuarioCreacion());
		params.add(plu.getUsuarioModificacion());
		
		logger.debug("SQL: "+query);
		
		try{
			int rs = this.jdbcTemplate.update(query.toString(), params.toArray());

			logger.debug("Resultado alta parametrización PLU: " + rs);
			logger.debug("<--- insertMaxPLUS ");
			return (rs == 1);

		} catch (DataAccessException dae){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, dae);
			throw new DataAccessException(Constantes.ERROR_MAX_PLU) {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
			};
			
		}
		
	}

	@Override
	public void updateMaxPLUS(PLU plu) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = null;
		if (plu!=null){
			query = new StringBuffer("UPDATE t_mis_param_balanzas_plu "
								   + "SET numero_plu = ? "
								     + ", usuario_modificacion = ? "
								     + ", last_update_date = SYSDATE "
								   + "WHERE cod_centro = ? "
								   + "AND grupo_balanza = ?"
								   );
			params.add(plu.getNumMaxPLU());
			params.add(plu.getUsuarioModificacion());
			params.add(plu.getCodCentro());
			params.add(plu.getGrupoBalanza());

			try {
				int rdoUpdate = this.jdbcTemplate.update(query.toString(), params.toArray());
				
				if (rdoUpdate==0){
					throw new DataAccessException(Constantes.UPDATE_CERO) {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
					};
				}
				
			} catch (DataAccessException e){
				boolean altaOK = this.insertMaxPLUS(plu);
				
				if (!altaOK){
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
					throw new DataAccessException(Constantes.ERROR_MAX_PLU) {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
					};
				}
			} catch(Exception e){
				throw new Exception(e);
			}

		}
	}
	
	@Override
	public int getMaxPLUS(Long codCentro, Long grupoBalanza){
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(grupoBalanza);

		StringBuffer query = new StringBuffer("SELECT numero_plu ");
								 query.append("FROM t_mis_param_balanzas_plu ");
								 query.append("WHERE cod_centro = ? ");
								 query.append("AND grupo_balanza = ?");

		logger.debug("AlarmasPLUDaoImpl - getMaxPLUS - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - getMaxPLUS - codCentro = "+codCentro + " grupoBalanza = "+grupoBalanza);
		
		List<String> maxPLU = new ArrayList<String>();
		int numMaxPLU = Constantes.NUM_TECLAS_BALANZA;
		try {
			maxPLU =  this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());

			numMaxPLU = Integer.valueOf(maxPLU.get(0));
		} catch (Exception e){
			return (-1)*numMaxPLU;
		}

		logger.debug("AlarmasPLUDaoImpl - getMaxPLUS - Nº MAX PLUs por centro/balanza: "+numMaxPLU);
		
		return numMaxPLU;
	}

	@Override
	public void updateStock(Long codCentro, final List<AlarmaPLU> alarmas) {
		logger.info("updateStock - codCentro  = "+codCentro);

		try{
			StringBuffer query = new StringBuffer(
					"UPDATE T_MIS_ALARMAS_PLU SET STOCK = ? WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND FECHA_GEN = TRUNC(SYSDATE) ");
	
			jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {
	
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AlarmaPLU alarma = alarmas.get(i);
					if (alarma!=null){
						logger.debug("updateStock - setValues - codCentro = "+alarma.getCodCentro()+" - codArticulo = "+alarma.getCodArticulo()+ " - stock = "+alarma.getStock());
						ps.setDouble(1, alarma.getStock());	
						ps.setLong(2, alarma.getCodCentro());	
						ps.setLong(3, alarma.getCodArticulo());	
					}
				}
	
				@Override
				public int getBatchSize() {
					return alarmas.size();
				}
			});
		}catch (Exception e){
			logger.error("***********************************************************************");
			logger.error("ERROR AL ACTUALIZAR EL STOCK DEL CENTRO "+codCentro+" : "+e.getMessage()+" - "+e.getCause());
			e.printStackTrace();
			logger.error("***********************************************************************");
		}
		
	}
	
	@Override
	public Boolean existeAlarma(Long codCentro, Long referencia, Long grupoBalanza)  throws Exception{ 
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(referencia);
		params.add(grupoBalanza);
		StringBuffer query = new StringBuffer("SELECT COUNT(1) ");
		query.append(" FROM T_MIS_ALARMAS_PLU A ");
		query.append(" WHERE A.COD_CENTRO = ? ");
		query.append(" AND A.COD_ARTICULO = ? ");
		query.append(" AND A.GRUPO_BALANZA = ? ");
		query.append(" AND A.FECHA_GEN = TRUNC(SYSDATE) ");
		
		logger.debug("AlarmasPLUDaoImpl - existeAlarma - SQL = "+query.toString());
		logger.debug("AlarmasPLUDaoImpl - existeAlarma - codCentro = "+codCentro);
		
		Integer output = null;
		try {
			output =  this.jdbcTemplate.queryForInt(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return false;
		}
		logger.debug("AlarmasPLUDaoImpl - existeAlarma - NUM_DATOS = "+output);
		
		return output>0;
	}
	
	@Override
	public void updatePLU(final List<AlarmaPLU> alarmas) {

		try{
			StringBuffer query = new StringBuffer(
					"UPDATE T_MIS_ALARMAS_PLU SET PLU = ?,PLU_ANT = ? WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND GRUPO_BALANZA = ? AND FECHA_GEN = TRUNC(SYSDATE) ");
	
			jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {
	
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AlarmaPLU alarma = alarmas.get(i);
					if (alarma!=null){
						logger.debug("updatePLU - setValues - codCentro = "+alarma.getCodCentro()+" - codArticulo = "+alarma.getCodArticulo()+ " - plu = "+alarma.getPlu());
						ps.setLong(1, alarma.getPlu());
						ps.setLong(2, alarma.getPlu());
						ps.setLong(3, alarma.getCodCentro());	
						ps.setLong(4, alarma.getCodArticulo());	
						ps.setLong(5, alarma.getGrupoBalanza());	
					}
				}
	
				@Override
				public int getBatchSize() {
					return alarmas.size();
				}
			});
		}catch (Exception e){
			logger.error("***********************************************************************");
			logger.error("ERROR AL ACTUALIZAR EL PLU: "+e.getMessage()+" - "+e.getCause());
			e.printStackTrace();
			logger.error("***********************************************************************");
		}
		
	}
	
}
