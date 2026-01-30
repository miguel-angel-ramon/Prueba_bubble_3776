package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CapturaRestoDao;
import es.eroski.misumi.model.pda.PdaCapturaResto;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CapturaRestoDaoImpl implements CapturaRestoDao {

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(CapturaRestoDaoImpl.class);
	
	private static String SELECT_CAPTURA_RESTOS = 
//			"SELECT "
//			+ " COD_CENTRO, COD_ART, FLG_MODIF_STOCK, STOCK_ANTES,"
//			+ " STOCK_DESPUES, FLG_MODIF_ALTO, FLG_MODIF_ANCHO, FLG_MODIF_FACING,"
//			+ " FACING_ANTES, FACING_DESPUES, CAPACIDAD_ANTES, CAPACIDAD_DESPUES, FLG_CAPACIDAD_INCORRECTA,"
//			+ " FLG_FONDO_BALDA, FLG_MUY_ALTA_ROTACION, ROTACION, UNIDADES_CAJA, TIPO_APROV, EMPUJE, COD_PLAT, "
//			+ " COEFICIENTE_REPOSICION, FACING_EXCEDENTE_MAXIMO, METODO_CALCULO_IMC, TIPO_REFERENCIA, FECHA_GEN,"
//			+ " CREATION_DATE, LAST_UPDATE_DATE" 
//			+ " FROM T_MIS_CAPTURA_RESTOS" 
//			+ " WHERE COD_CENTRO = ? "
//			+ " AND FECHA_GEN = TRUNC(SYSDATE)" 
//			+ "	ORDER BY CREATION_DATE ";
	"SELECT "
		+ "	CR.COD_CENTRO, "
		+ "	CR.COD_ART, "
		+ "	(SELECT RTRIM(ART.DESCRIP_ART)  FROM V_DATOS_DIARIO_ART ART WHERE ART.COD_ART = CR.COD_ART) AS DESCRIP_ART, "
		+ "	CR.FLG_MODIF_STOCK, "
		+ "	CR.STOCK_ANTES, "
		+ "	CR.STOCK_DESPUES, "
		+ "	CR.FLG_MODIF_ALTO, "
		+ "	CR.FLG_MODIF_ANCHO, "
		+ "	CR.FLG_MODIF_FACING, "
		+ "	CR.FACING_ANTES, "
		+ "	CR.FACING_DESPUES, "
		+ "	CR.CAPACIDAD_ANTES, "
		+ "	CR.CAPACIDAD_DESPUES, "
		+ "	CR.ALTO_ANTES, "
		+ "	CR.ALTO_DESPUES, "
		+ "	CR.ANCHO_ANTES, "
		+ "	CR.ANCHO_DESPUES, "
		+ "	CR.FLG_CAPACIDAD_INCORRECTA, "
		+ "	CR.FLG_FONDO_BALDA, "
		+ "	CR.FLG_MUY_ALTA_ROTACION, "
		+ "	CR.ROTACION, "
		+ "	CR.UNIDADES_CAJA, "
		+ "	CR.TIPO_APROV, "
		+ "	CR.EMPUJE, "
		+ "	CR.COD_PLAT, "
		+ "	CR.COEFICIENTE_REPOSICION, "
		+ "	CR.FACING_EXCEDENTE_MAXIMO, "
		+ "	CR.METODO_CALCULO_IMC, "
		+ "	CR.TIPO_REFERENCIA, "
		+ "	CR.FECHA_GEN, "
		+ "	CR.CREATION_DATE, "
		+ "	CR.LAST_UPDATE_DATE "
	+ " FROM "
		+ "	T_MIS_CAPTURA_RESTOS CR "
	+ " WHERE "
		+ "	CR.COD_CENTRO = ? "
		+ "	AND CR.FECHA_GEN = TRUNC(SYSDATE) "
	+ " ORDER BY "
		+ "	CR.CREATION_DATE ";
	
	private static String COUNT_CAPTURA_RESTOS = "SELECT COUNT(1)"
			+ " FROM T_MIS_CAPTURA_RESTOS" 
			+ " WHERE COD_CENTRO = ? "
			+ " AND FECHA_GEN = TRUNC(SYSDATE)";

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public PdaCapturaResto getCapturaRestosByCodArt(Long codCentro, Long codArt) {

		String query = this.buildSelectCapturaRestos(codCentro, codArt, null);
		
		PdaCapturaResto capturaResto = null;

		try {
			capturaResto = (PdaCapturaResto) this.jdbcTemplate.queryForObject(query, this.rwCapturaRestoMap,
					new Object[] { codCentro, codArt });
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), Arrays.asList(new Object[] { codCentro.toString(), codArt }), e);
		}

		return capturaResto;
	}

	@Override
	public PdaCapturaResto getCapturaRestosByPage(Long codCentro, Long page) {
		
		String query = this.buildSelectCapturaRestos(codCentro, null, page);

		PdaCapturaResto capturaResto = null;

		try {
			capturaResto = (PdaCapturaResto) this.jdbcTemplate.queryForObject(query, this.rwCapturaRestoMap,
					new Object[] { codCentro, page });
		} catch (EmptyResultDataAccessException emptyExcp){
			
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), Arrays.asList(new Object[] { codCentro, page }), e);
		}

		return capturaResto;
	}

	@Override
	public Long countCapturasRestos(Long codCentro, Long codArt) {
		
		StringBuilder query = new StringBuilder( COUNT_CAPTURA_RESTOS );
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		if (null != codArt) {
			query.append(" AND COD_ART = ?");
			params.add(codArt);
		}
		
		Long cont = null;

		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return Long.valueOf(cont);
	}

	private String buildSelectCapturaRestos(Long codCentro, Long codArt, Long page){
		StringBuilder query = new StringBuilder("SELECT rnSql.* FROM ( " 
				+ " SELECT ROWNUM AS page, main.* FROM	( ");
		query.append(SELECT_CAPTURA_RESTOS);
		query.append(") main ");
		query.append(") rnSql");
		
		if (null!=codArt)
			query.append(" WHERE rnSql.cod_art=?"); 
		else
			query.append(" WHERE rnSql.page=?"); 
			
		query.append(" ORDER BY page");
		
		return query.toString();
	}
	


	
	@Override
	public String getEmpuje(Long codCentro, Long codArt,List<Long> vRelacionArticuloLista){
		List<Object> params = new ArrayList<Object>();
		String empuje="N";
		params.add(codCentro);
		String query="SELECT EMPUJE FROM ( SELECT * FROM V_INFORMACION_BASICA_PEDIDO WHERE COD_CENTRO=? AND COD_ART IN (?";
		params.add(codArt);
		for(Long codArtRel : vRelacionArticuloLista){
			query=query+",?";
			params.add(codArtRel);
		}
		query=query+") "
				+ "ORDER BY FECHA_PED DESC, EMPUJE DESC) "
				+ "WHERE ROWNUM=1";
		try {
			empuje= (String) this.jdbcTemplate.queryForObject(query, params.toArray(), String.class);
		}catch (EmptyResultDataAccessException e) {
			logger.info("<--- EmptyResultDataAccessException CapturaRestosDaoImpl.getEmpuje");
			return empuje;
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		//Por defecto, si existe algun error se devuelve
		return empuje;
	}

	
	@Override
	public Long getCodPlataforma(Long codCentro, Long codArt,List<Long> vRelacionArticuloLista){
		Long codPlataforma=null;
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		String query = "SELECT COD_PLAT FROM ( "
					+ " SELECT * FROM CONFIRMACION_ENVIADO WHERE COD_CENTRO=? AND COD_ART IN(?";
		params.add(codArt);
		for(Long codArtRel : vRelacionArticuloLista){
			query=query+",?";
			params.add(codArtRel);
		}
		query=query+") "
				+ " ORDER BY FECHA_EXP DESC)"
				+ "WHERE ROWNUM=1";
		
		
		
		try {
			codPlataforma= this.jdbcTemplate.queryForLong(query, params.toArray());
		} catch (EmptyResultDataAccessException e) {
			logger.info("<--- EmptyResultDataAccessException CapturaRestosDaoImpl.getEmpuje");
			return codPlataforma;
		}  catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return codPlataforma;
	}
	
	private RowMapper<PdaCapturaResto> rwCapturaRestoMap = new RowMapper<PdaCapturaResto>() {
		public PdaCapturaResto mapRow(ResultSet rs, int rowNum) throws SQLException {

			PdaCapturaResto item = new PdaCapturaResto();

			item.setRowNum(rs.getLong("PAGE"));
			item.setCodCentro(rs.getLong("COD_CENTRO"));
			item.setCodArt(rs.getLong("COD_ART"));
			
			item.setDescripArt(rs.getString("DESCRIP_ART"));
			
			item.setFlgModifStock(rs.getString("FLG_MODIF_STOCK"));
			
			Double stockAntes = rs.getDouble("STOCK_ANTES");
			item.setStockAntes(!rs.wasNull() ? stockAntes : null);
			
			Double stockDespues = rs.getDouble("STOCK_DESPUES");
			item.setStockDespues(!rs.wasNull() ? stockDespues : null);

			item.setFlgModifAlto(rs.getString("FLG_MODIF_ALTO"));
			item.setFlgModifAncho(rs.getString("FLG_MODIF_ANCHO"));
			item.setFlgModifFacing(rs.getString("FLG_MODIF_FACING"));

			Long facingAntes = rs.getLong("FACING_ANTES");
			item.setFacingAntes(!rs.wasNull() ? facingAntes : null);
			
			Long facingDespues = rs.getLong("FACING_DESPUES");
			item.setFacingDespues(!rs.wasNull() ? facingDespues : null);
			
			Long capacidadAntes = rs.getLong("CAPACIDAD_ANTES");
			item.setCapacidadAntes(!rs.wasNull() ? capacidadAntes : null);
			
			Long capacidadDespues = rs.getLong("CAPACIDAD_DESPUES");
			item.setCapacidadDespues(!rs.wasNull() ? capacidadDespues : null);
			
			Long altoAntes = rs.getLong("ALTO_ANTES");
			item.setFacingAltoAntes(!rs.wasNull() ? altoAntes : null);
			
			Long altoDespues = rs.getLong("ALTO_DESPUES");
			item.setFacingAltoDespues(!rs.wasNull() ? altoDespues : null);
			
			Long anchoAntes = rs.getLong("ANCHO_ANTES");
			item.setFacingAnchoAntes(!rs.wasNull() ? anchoAntes : null);
			
			Long anchoDespues = rs.getLong("ANCHO_DESPUES");
			item.setFacingAnchoDespues(!rs.wasNull() ? anchoDespues : null);
			
			item.setFlgCapacidadIncorreta(rs.getString("FLG_CAPACIDAD_INCORRECTA"));
			item.setFlgFondoBalda(rs.getString("FLG_FONDO_BALDA"));
			item.setFlgMuyAltaRotacion(rs.getString("FLG_MUY_ALTA_ROTACION"));
			item.setRotacion(rs.getString("ROTACION"));
			
			Double unidadesCaja = rs.getDouble("UNIDADES_CAJA");
			item.setUnidadesCaja(!rs.wasNull() ? unidadesCaja : null);
			
			item.setTipoAprov(rs.getString("TIPO_APROV"));
			item.setEmpuje(rs.getString("EMPUJE"));
			
			Long codPlat = rs.getLong("COD_PLAT");
			item.setCodPlat(!rs.wasNull() ? codPlat : null);
			
			Double coeficienteReposicion = rs.getDouble("COEFICIENTE_REPOSICION");
			item.setCoeficienteReposicion(!rs.wasNull() ? coeficienteReposicion : null);
			
			Integer facingExcedenteMaximo = rs.getInt("FACING_EXCEDENTE_MAXIMO");
			item.setFacingExcedenteMaximo(!rs.wasNull() ? facingExcedenteMaximo : null);
			
			Integer metodoCalculoIMC = rs.getInt("METODO_CALCULO_IMC");
			item.setMetodoCalculoIMC(!rs.wasNull() ? metodoCalculoIMC : null);
			
			Long tipoReferencia = rs.getLong("TIPO_REFERENCIA");
			item.setTipoReferencia(!rs.wasNull() ? tipoReferencia : null);
			
			return item;
		}

	};

	@Override
	public int insertCapturaResto(PdaCapturaResto capturaResto) {
		
		List<Object> params = new ArrayList<Object>();

		String query = " INSERT INTO T_MIS_CAPTURA_RESTOS "
				+ " (COD_CENTRO, COD_ART, "
				+ " FLG_MODIF_STOCK, STOCK_ANTES, STOCK_DESPUES, "
				+ " FLG_MODIF_ALTO, FLG_MODIF_ANCHO, FLG_MODIF_FACING, FACING_ANTES, FACING_DESPUES, "
				+ " CAPACIDAD_ANTES, CAPACIDAD_DESPUES, FLG_CAPACIDAD_INCORRECTA, FLG_FONDO_BALDA, FLG_MUY_ALTA_ROTACION, "
				+ " ROTACION, UNIDADES_CAJA, TIPO_APROV, EMPUJE, COD_PLAT, "
				+ " COEFICIENTE_REPOSICION, FACING_EXCEDENTE_MAXIMO, METODO_CALCULO_IMC, TIPO_REFERENCIA, "
				+ " FECHA_GEN ) "
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, TRUNC(SYSDATE))";

		params.add(capturaResto.getCodCentro());
		params.add(capturaResto.getCodArt());
		
		params.add(capturaResto.getFlgModifStock());
		params.add(capturaResto.getStockAntes());
		params.add(capturaResto.getStockDespues());
		
		params.add(capturaResto.getFlgModifAlto());
		params.add(capturaResto.getFlgModifAncho());
		params.add(capturaResto.getFlgModifFacing());
		params.add(capturaResto.getFacingAntes());
		params.add(capturaResto.getFacingDespues());
		
		params.add(capturaResto.getCapacidadAntes());
		params.add(capturaResto.getCapacidadDespues());
		params.add(capturaResto.getFlgCapacidadIncorreta());
		params.add(capturaResto.getFlgFondoBalda());
		params.add(capturaResto.getFlgMuyAltaRotacion());
		
		params.add(capturaResto.getRotacion());
		params.add(capturaResto.getUnidadesCaja());
		params.add(capturaResto.getTipoAprov());
		params.add(capturaResto.getEmpuje());
		params.add(capturaResto.getCodPlat());
		
		params.add(capturaResto.getCoeficienteReposicion());
		params.add(capturaResto.getFacingExcedenteMaximo());
		params.add(capturaResto.getMetodoCalculoIMC());
		params.add(capturaResto.getTipoReferencia());
		
		

		int result= 0;
		
		try {
			result = this.jdbcTemplate.update(query, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		
		return result;
	}

	@Override
	public int updateCapturaResto(PdaCapturaResto capturaResto){
		List<Object> params = new ArrayList<Object>();

		StringBuilder query = new StringBuilder(" UPDATE T_MIS_CAPTURA_RESTOS ");
		query.append(" SET STOCK_ANTES = ?, ");
		query.append(" STOCK_DESPUES = ?, ");
		query.append(" FLG_MODIF_STOCK = ?, ");
		
		
		query.append(" FACING_ANTES = ?, ");
		query.append(" FACING_DESPUES = ?, ");
		query.append(" CAPACIDAD_ANTES = ?, ");
		query.append(" CAPACIDAD_DESPUES = ?, ");
		query.append(" ALTO_ANTES = ?, ");
		query.append(" ALTO_DESPUES = ?, ");
		query.append(" ANCHO_ANTES = ?, ");
		query.append(" ANCHO_DESPUES = ?, ");
		query.append(" FLG_MODIF_FACING = ? ");

		if (Boolean.TRUE.equals(capturaResto.getModificarLastUpdateDate())){
			query.append(", LAST_UPDATE_DATE = SYSDATE ");
		}
		
		query.append(" WHERE COD_CENTRO=? ");
		query.append(" AND COD_ART=? ");
		query.append(" AND FECHA_GEN=TRUNC(SYSDATE)");
		
		
		params.add(capturaResto.getStockAntes());
		params.add(capturaResto.getStockDespues());
		params.add(capturaResto.getFlgModifStock());
		
		params.add(capturaResto.getFacingAntes());
		params.add(capturaResto.getFacingDespues());
		params.add(capturaResto.getCapacidadAntes());
		params.add(capturaResto.getCapacidadDespues());
		params.add(capturaResto.getFacingAltoAntes());
		params.add(capturaResto.getFacingAltoDespues());
		params.add(capturaResto.getFacingAnchoAntes());
		params.add(capturaResto.getFacingAnchoDespues());
		params.add(capturaResto.getFlgModifFacing());
		
		params.add(capturaResto.getCodCentro());
		params.add(capturaResto.getCodArt());
		
/*		params.add(capturaResto.getFlgModifAlto());
		params.add(capturaResto.getFlgModifAncho());
		params.add(capturaResto.getFlgModifFacing());
		params.add(capturaResto.getFacingAntes());
		params.add(capturaResto.getFacingDespues());
		
		params.add(capturaResto.getCapacidadAntes());
		params.add(capturaResto.getCapacidadDespues());
		params.add(capturaResto.getFlgCapacidadIncorreta());
		params.add(capturaResto.getFlgFondoBalda());
		params.add(capturaResto.getFlgMuyAltaRotacion());
		
		params.add(capturaResto.getRotacion());
		params.add(capturaResto.getUnidadesCaja());
		params.add(capturaResto.getTipoAprov());
		params.add(capturaResto.getEmpuje());
		params.add(capturaResto.getCodPlat());
		
		params.add(capturaResto.getCoeficienteReposicion());
		params.add(capturaResto.getFacingExcedenteMaximo());
		params.add(capturaResto.getMetodoCalculoIMC());
		params.add(capturaResto.getTipoReferencia());
*/		
		

		int result= 0;
		
		try {
			result = this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		
		return result;

	}
	
	@Override
	public Long countGondolaG(Long codCentro, Long codArt, Long anoOferta, Long numeroOferta){
		
		String query ="SELECT COUNT(1) FROM ("
						+ "SELECT * "
						+ "FROM OFERTA "
						+ "WHERE COD_CENTRO = ? "
						+ " AND COD_ART = ? "
						+ " AND ANO_OFERTA = ? "
						+ " AND NUM_OFERTA = ? "
						+ " AND GONDOLA ='G' "
						+ "ORDER BY FECHA_GEN DESC )"
				+ " WHERE ROWNUM=1 ";
 

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		params.add(anoOferta);
		params.add(numeroOferta);

		
		Long cont = null;

		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return Long.valueOf(cont);

	}

	@Override
	public void updateFlagCapacidadIncorrecta(Long codCentro, Long codArt, String flgCapacidadIncorrecta) {
		
		String query ="UPDATE T_MIS_CAPTURA_RESTOS SET FLG_CAPACIDAD_INCORRECTA = ?, LAST_UPDATE_DATE = SYSDATE "
				+ " WHERE COD_CENTRO=?"
				+ " AND COD_ART=? "
				+ " AND FECHA_GEN=TRUNC(SYSDATE)";

		List<Object> params = new ArrayList<Object>();
		params.add(flgCapacidadIncorrecta);
		params.add(codCentro);
		params.add(codArt);
		
		try {
			this.jdbcTemplate.update(query, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		
	}
}

