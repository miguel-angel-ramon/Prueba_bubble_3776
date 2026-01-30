package es.eroski.misumi.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CestasNavidadDao;
import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CestasNavidadDaoImpl implements CestasNavidadDao{

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(CestasNavidadDaoImpl.class);

	private RowMapper<CestasNavidad> rwVCestasNavidadMap = new RowMapper<CestasNavidad>() {
		public CestasNavidad mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new CestasNavidad(resultSet.getLong("COD_ART_LOTE"),
					resultSet.getString("DESCRIPCION_LOTE_MISUMI"), 
					resultSet.getString("DESCRIPCION_PRODUCTO"),
					parseBlobToB64(resultSet.getBlob("IMAGEN1")),
					parseBlobToB64(resultSet.getBlob("IMAGEN2")),
					parseBlobToB64(resultSet.getBlob("IMAGEN3")),
					resultSet.getLong("COD_ART_LOTE_CPB"),
					resultSet.getString("DESCRIPCION_LOTE_CPB"),		    			
					resultSet.getLong("COD_ART_LOTE_PANTALLA"),
					resultSet.getString("DESCRIPCION_LOTE_PANTALLA")
					);
		}
	};

	private RowMapper<CestasNavidad> rwCestasNavidadMap = new RowMapper<CestasNavidad>() {
		public CestasNavidad mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new CestasNavidad(resultSet.getLong("COD_ART_LOTE"),
					resultSet.getString("DESCRIPCION_LOTE_MISUMI").trim(), 
					parseBlobToB64(resultSet.getBlob("IMAGEN1")),
					parseBlobToB64(resultSet.getBlob("IMAGEN2")),
					parseBlobToB64(resultSet.getBlob("IMAGEN3")),
					resultSet.getLong("COD_ART_LOTE_CPB"),
					resultSet.getString("DESCRIPCION_LOTE_CPB"),		    			
					resultSet.getDate("FECHA_INICIO"),
					resultSet.getDate("FECHA_FIN"),
					resultSet.getString("FLG_EROSKI"),	
					resultSet.getString("FLG_CAPRABO"),
					resultSet.getLong("ORDEN"),
					resultSet.getLong("ESTADO"),
					resultSet.getString("BORRAR"),
					resultSet.getInt("TIENE_TEXTO")>0?Boolean.TRUE:Boolean.FALSE
					);
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<CestasNavidad> findAll(Long codCentro, boolean esCentroCaprabo) throws Exception {

		StringBuffer query = new StringBuffer("SELECT COD_ART_LOTE,DESCRIPCION_LOTE_MISUMI,DESCRIPCION_PRODUCTO,IMAGEN1,IMAGEN2,IMAGEN3,COD_ART_LOTE_CPB,DESCRIPCION_LOTE_CPB, ");
		if (esCentroCaprabo){
			query.append("COD_ART_LOTE_CPB COD_ART_LOTE_PANTALLA,DESCRIPCION_LOTE_CPB DESCRIPCION_LOTE_PANTALLA");
		}else{
			query.append("COD_ART_LOTE COD_ART_LOTE_PANTALLA,DESCRIPCION_LOTE_MISUMI DESCRIPCION_LOTE_PANTALLA");
		}
		query.append(" FROM V_CESTAS_NAVIDAD WHERE COD_CENTRO = ? AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FIN");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		List<CestasNavidad> lista = null;
		try {
			lista =  this.jdbcTemplate.query(query.toString(),this.rwVCestasNavidadMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
	}

	@Override
	public CestasNavidad findOne (Long codCentro, Long codArticuloLote, boolean esCentroCaprabo) throws Exception {

		StringBuffer query = new StringBuffer("SELECT COD_ART_LOTE,DESCRIPCION_LOTE_MISUMI,DESCRIPCION_PRODUCTO,IMAGEN1,IMAGEN2,IMAGEN3,COD_ART_LOTE_CPB,DESCRIPCION_LOTE_CPB,");
		if (esCentroCaprabo){
			query.append("COD_ART_LOTE_CPB COD_ART_LOTE_PANTALLA,DESCRIPCION_LOTE_CPB DESCRIPCION_LOTE_PANTALLA");
		}else{
			query.append("COD_ART_LOTE COD_ART_LOTE_PANTALLA,DESCRIPCION_LOTE_MISUMI DESCRIPCION_LOTE_PANTALLA");
		}

		query.append(" FROM V_CESTAS_NAVIDAD");
		StringBuffer where =  new StringBuffer(" WHERE COD_CENTRO = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		if (codArticuloLote!=null){
			where.append(" AND COD_ART_LOTE = ? ");
			params.add(codArticuloLote);
		}
		query.append(where);


		CestasNavidad cestaNavidad = null;
		try {
			cestaNavidad =  this.jdbcTemplate.queryForObject(query.toString(),rwVCestasNavidadMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return cestaNavidad;

	}

	@Override
	public String findDescription(Long codArtLote) throws Exception{
		StringBuffer query = new StringBuffer(" SELECT vdd.DESCRIP_ART FROM V_DATOS_DIARIO_ART vdd");
		StringBuffer where =  new StringBuffer(" WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();

		if (codArtLote!=null){
			where.append(" AND COD_ART = ? ");
			params.add(codArtLote);
			query.append(where);
		}

		String descr= null;
		try {
			descr= this.jdbcTemplate.queryForObject(query.toString(),String.class,params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return descr;	
	}

	/**
	 * Convierte una imagen a base 64 para ser tratada posteriormente en el front-end
	 * @param imagen
	 * @return
	 */
	private String parseBlobToB64(Blob imagen){
		String imagenStr=null;
		try{
			byte[] ba;
			ba = imagen.getBytes(1, (int) imagen.length()); 
			byte[] img64 = Base64.encode(ba);
			imagenStr = new String(img64);
		}catch (Exception e) {

		}
		return imagenStr; // The original encoding before Base64

	}

	/**
	 * Convierte una imagen a base 64 para ser tratada posteriormente en el front-end
	 * @param imagen
	 * @return
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	private byte[] parseB64ToBlob(String b64Img) throws SerialException, SQLException{
		byte[] decodedByte = Base64.decode(b64Img.getBytes());  
		//Blob blob = new SerialBlob(decodedByte);

		return decodedByte;
	}


	@Override
	public List<CestasNavidad> findAll(Pagination pagination) throws Exception {
		//MISUMI-284: Se añade un campo mas que consulta en la tabla CESTAS_NAVIDAD_ARTICULOS para ver si la cesta tiene texto introducido
		StringBuffer query = new StringBuffer("SELECT c.*, (select count(1) from CESTAS_NAVIDAD_ARTICULOS c2 where c.COD_ART_LOTE = c2.COD_ART_LOTE) as TIENE_TEXTO  ");
		query.append(" FROM CESTAS_NAVIDAD c");

		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por pasillo/denominacion proveedor/estructura comercial/stock_actual
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append(" order by ORDEN ");
				query.append(order);
			}
		}else{
			//order.append(" order by DECODE(STOCK_ACTUAL,null,100,0,100,1), PASILLO, DENOMINACION, ESTRUCTURA_COMERCIAL");
			order.append(" order by PROVR_GEN, MARCA, ESTRUCTURA_COMERCIAL");
			query.append(order);
		}
		logger.info("CestasNavidadDaoImpl - findAll - SQL = "+query.toString());
		List<Object> params = new ArrayList<Object>();

		List<CestasNavidad> lista = null;
		try {
			lista =  this.jdbcTemplate.query(query.toString(),this.rwCestasNavidadMap, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
	}

	@Override
	public int updateBorradoLinea(CestasNavidad cestasNavidad) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE CESTAS_NAVIDAD SET BORRAR = ? ");
		params.add(cestasNavidad.getBorrar());

		if(cestasNavidad.getCodArtLote() != null){
			query.append(" WHERE COD_ART_LOTE = ? ");
			params.add(cestasNavidad.getCodArtLote());
		}

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return 0;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return 1;
		}
	}

	@Override
	public int updateFechas(CestasNavidad cestasNavidad) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE CESTAS_NAVIDAD SET FECHA_INICIO = ?, FECHA_FIN = ?, ESTADO = ?, BORRAR = 'N', LAST_UPDATE_DATE = SYSDATE ");
		params.add(cestasNavidad.getFecIni());
		params.add(cestasNavidad.getFecFin());
		params.add(cestasNavidad.getEstado());
		
		query.append(" WHERE BORRAR = 'S' ");
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return 0;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return 1;
		}
	}
	
	//Función que sirve para realizar ordenaciones por columna, de esta forma, llega el código de columna con nombre
	//jqgrid y lo transforma a código SQL
	private String getMappedField (String fieldName) {
		if (fieldName.toUpperCase().equals("CODARTLOTE")){
			return "COD_ART_LOTE";
		}else if(fieldName.toUpperCase().equals("DESCRLOTEMISUMI")){
			return "DESCRIPCION_LOTE_MISUMI";
		}else if(fieldName.toUpperCase().equals("FECINI")){
			return "FECHA_INICIO";
		}else if(fieldName.toUpperCase().equals("FECFIN")){
			return "FECHA_FIN";
		}else if(fieldName.toUpperCase().equals("CODARTLOTECPB")){
			return "COD_ART_LOTE_CPB";
		}else if(fieldName.toUpperCase().equals("DESCRIPCIONLOTECPB")){
			return "DESCRIPCION_LOTE_CPN";
		}else if(fieldName.toUpperCase().equals("FLGEROSKI")){
			return "FLG_EROSKI";
		}else if(fieldName.toUpperCase().equals("FLGCAPRABO")){
			return "FLG_CAPRABO";
		}else if(fieldName.toUpperCase().equals("ORDEN")){
			return "ORDEN";
		}else if(fieldName.toUpperCase().equals("MENSAJE")){
			return "ESTADO";
		}
		return "COD_ART_LOTE";
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM CESTAS_NAVIDAD WHERE BORRAR = 'S' ");
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return "0";
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return "1";
		}
	}

	@Override
	public int updateLinea(CestasNavidad cestasNavidad){
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer where =  new StringBuffer(" WHERE 1=1 ");
		StringBuffer query = new StringBuffer(" UPDATE CESTAS_NAVIDAD SET ");

		try {
			//Si estamos actualizando la linea el estado se actualiza siempre
			query.append(" ESTADO = ? ");
			params.add(cestasNavidad.getEstado());
			
			if (cestasNavidad.getLastUpdate() != null){
				query.append(" , LAST_UPDATE_DATE = ? ");
				params.add(cestasNavidad.getLastUpdate());
			}
			
			if (cestasNavidad.getDescrLoteMisumi() != null){
				query.append(" , DESCRIPCION_LOTE_MISUMI = ? ");
				params.add(cestasNavidad.getDescrLoteMisumi());
			}

			if (cestasNavidad.getFecIni() != null){
				query.append(" , FECHA_INICIO = ? ");
				params.add(cestasNavidad.getFecIni());
			}

			if (cestasNavidad.getFecFin() != null){
				query.append(" , FECHA_FIN = ? ");
				params.add(cestasNavidad.getFecFin());
			}

			if (cestasNavidad.getImagen1() != null){
				query.append(" , IMAGEN1 = ? ");
				params.add(parseB64ToBlob(cestasNavidad.getImagen1()));
			}

			if (cestasNavidad.getImagen2() != null){
				query.append(" , IMAGEN2 = ? ");
				params.add(parseB64ToBlob(cestasNavidad.getImagen2()));
			}

			if (cestasNavidad.getFlgEroski() != null){
				query.append(" , FLG_EROSKI = ? ");
				params.add(cestasNavidad.getFlgEroski());
			}

			if (cestasNavidad.getFlgCaprabo() != null){
				query.append(" , FLG_CAPRABO = ? ");
				params.add(cestasNavidad.getFlgCaprabo());
			}

			if (cestasNavidad.getOrden() != null){
				query.append(" , ORDEN = ? ");
				params.add(cestasNavidad.getOrden());
			}		
			
			if(cestasNavidad.getCodArtLote() != null){
				where.append(" AND COD_ART_LOTE = ? ");
				params.add(cestasNavidad.getCodArtLote());
			}
			
			query.append(where);
			
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return Constantes.COD_ERROR_OK;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return Constantes.COD_ERROR_UPDATE_LOTE;
		}
	}

	
	@Override
	public int insertLinea(CestasNavidad cestasNavidad) {
		// TODO Auto-generated method stub
		try{
			StringBuffer insert = new StringBuffer(
					"INSERT INTO CESTAS_NAVIDAD (COD_ART_LOTE, DESCRIPCION_LOTE_MISUMI, FECHA_INICIO, FECHA_FIN,");
			insert.append(" IMAGEN1, IMAGEN2, CREATION_DATE, LAST_UPDATE_DATE, FLG_EROSKI, FLG_CAPRABO, ORDEN, ESTADO) ");
			insert.append(" VALUES (?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?,?) ");

			List<Object> params = new ArrayList<Object>();
			params.add(cestasNavidad.getCodArtLote());
			params.add(cestasNavidad.getDescrLoteMisumi());
			params.add(cestasNavidad.getFecIni());
			params.add(cestasNavidad.getFecFin());
			params.add(cestasNavidad.getImagen1() != null ? parseB64ToBlob(cestasNavidad.getImagen1()):null);
			params.add(cestasNavidad.getImagen2() != null ? parseB64ToBlob(cestasNavidad.getImagen2()):null);
			params.add(cestasNavidad.getFlgEroski());
			params.add(cestasNavidad.getFlgCaprabo());
			params.add(cestasNavidad.getOrden());
			params.add(cestasNavidad.getEstado());
			
			this.jdbcTemplate.update(insert.toString(), params.toArray());
			
			return Constantes.COD_ERROR_OK;
		}catch (DataIntegrityViolationException ex) {
			logger.error("insertLinea clave="+ex.toString());
			
			return Constantes.COD_ERROR_INSERT_DUP_LOTE;
		}catch(Exception e){
			logger.error("insertLinea="+e.toString());
			e.printStackTrace();
			
			return Constantes.COD_ERROR_INSERT_LOTE;
		} 		
	}

	@Override
	public int countSeleccionados() {
		// TODO Auto-generated method stub
		try{
			StringBuffer query = new StringBuffer(" SELECT COUNT(*) ");
			query.append(" FROM CESTAS_NAVIDAD ");
			query.append(" WHERE BORRAR = 'S' ");

			return this.jdbcTemplate.queryForInt(query.toString());
		}catch(Exception e){
			logger.error("insertLinea="+e.toString());
			e.printStackTrace();
			
			return -1;
		} 		
	}
}
