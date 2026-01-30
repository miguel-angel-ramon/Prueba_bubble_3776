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

import es.eroski.misumi.dao.iface.IntertiendaDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class IntertiendaDaoImpl implements IntertiendaDao{
	 private JdbcTemplate jdbcTemplate;
	 private static Logger logger = Logger.getLogger(IntertiendaDaoImpl.class);
	 private RowMapper<Intertienda> rwIntertiendaMap = new RowMapper<Intertienda>() {
			public Intertienda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new Intertienda(null, 
			    			resultSet.getLong("COD_CENTRO"), resultSet.getString("DESCRIP_CENTRO"),
			    			resultSet.getLong("COD_REGION"), resultSet.getString("DESCRIP_REGION"),
			    			resultSet.getLong("COD_ZONA"), resultSet.getString("DESCRIP_ZONA"),
			    			null, resultSet.getString("DESCRIP_PROVINCIA"),
			    			resultSet.getLong("COD_ENSENA"), resultSet.getLong("COD_AREA"),
			    			null, resultSet.getFloat("VENTA_MEDIA"),
			    			resultSet.getBoolean("EXISTE_ARTICULO"), null
				    );
			}
	 };
	 
	    private RowMapper<GenericExcelVO> rwExcelIntertiendaMap = new RowMapper<GenericExcelVO>() {
			public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
						 
			    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 1),Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet, 3),
			    		Utilidades.obtenerValorExcel(resultSet, 4),Utilidades.obtenerValorExcel(resultSet, 5),
			    		Utilidades.obtenerValorExcel(resultSet, 6),
			    		Utilidades.obtenerValorExcel(resultSet, 7)
			    		,Utilidades.obtenerValorExcel(resultSet, 8),Utilidades.obtenerValorExcel(resultSet, 9),Utilidades.obtenerValorExcel(resultSet, 10),Utilidades.obtenerValorExcel(resultSet, 11)
			    		,Utilidades.obtenerValorExcel(resultSet, 12),Utilidades.obtenerValorExcel(resultSet, 13),Utilidades.obtenerValorExcel(resultSet, 14),Utilidades.obtenerValorExcel(resultSet, 15)
			    		,Utilidades.obtenerValorExcel(resultSet, 16),Utilidades.obtenerValorExcel(resultSet, 17),Utilidades.obtenerValorExcel(resultSet, 18),Utilidades.obtenerValorExcel(resultSet, 19)
			    		,Utilidades.obtenerValorExcel(resultSet, 20),Utilidades.obtenerValorExcel(resultSet, 21),Utilidades.obtenerValorExcel(resultSet, 22),Utilidades.obtenerValorExcel(resultSet, 23)
					    ,Utilidades.obtenerValorExcel(resultSet, 24),Utilidades.obtenerValorExcel(resultSet, 25),Utilidades.obtenerValorExcel(resultSet, 26),Utilidades.obtenerValorExcel(resultSet, 27)
					    ,Utilidades.obtenerValorExcel(resultSet, 28),Utilidades.obtenerValorExcel(resultSet, 29),Utilidades.obtenerValorExcel(resultSet, 30),Utilidades.obtenerValorExcel(resultSet, 31)
					    ,Utilidades.obtenerValorExcel(resultSet, 32),Utilidades.obtenerValorExcel(resultSet, 33),Utilidades.obtenerValorExcel(resultSet, 34),Utilidades.obtenerValorExcel(resultSet, 35)
			    		,Utilidades.obtenerValorExcel(resultSet,36),Utilidades.obtenerValorExcel(resultSet,37),Utilidades.obtenerValorExcel(resultSet,38),Utilidades.obtenerValorExcel(resultSet,39)
			    		,Utilidades.obtenerValorExcel(resultSet,40),Utilidades.obtenerValorExcel(resultSet,42),Utilidades.obtenerValorExcel(resultSet,41),Utilidades.obtenerValorExcel(resultSet,43)
			    		,Utilidades.obtenerValorExcel(resultSet,44),Utilidades.obtenerValorExcel(resultSet,45),Utilidades.obtenerValorExcel(resultSet,46),Utilidades.obtenerValorExcel(resultSet,47)
				    );
			}

	    };

		   
    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
	@Override
	public List<Intertienda> listCentroIntertienda(Intertienda intertienda, Pagination pagination) throws Exception {

		StringBuffer where = null;
    	List<Object> params = null;
		int decimales = Constantes.POSICIONES_DECIMALES;
    	
    	StringBuffer query = new StringBuffer(" SELECT DISTINCT a.COD_CENTRO, trim(a.DESCRIP_CENTRO) DESCRIP_CENTRO, a.COD_REGION, trim(b.DESCRIPCION) DESCRIP_REGION, a.COD_ZONA, trim(a.DESCRIP_ZONA) DESCRIP_ZONA, a.PROVINCIA DESCRIP_PROVINCIA, a.COD_ENSENA, a.COD_AREA, nvl2(c.COD_LOC,1,0) EXISTE_ARTICULO, " +
    										  " nvl2(d.UNID_TOT_VENTA_TARIFA,      nvl2(d.DIAS, decode(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_TARIFA      / d.DIAS," + decimales + ")),0),0) + " + 
    										  " nvl2(d.UNID_TOT_VENTA_COMPETENCIA, nvl2(d.DIAS, decode(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_COMPETENCIA / d.DIAS," + decimales + ")),0),0) + " +
    										  " nvl2(d.UNID_TOT_VENTA_OFERTA,      nvl2(d.DIAS, decode(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_OFERTA      / d.DIAS," + decimales + ")),0),0) + " +
    										  " nvl2(d.UNID_TOT_VENTA_ANTICIPADA,  nvl2(d.DIAS, decode(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_ANTICIPADA  / d.DIAS," + decimales + ")),0),0) VENTA_MEDIA " +
    										  " FROM v_centros_plataformas a, region b, stock_actual_centro c, historico_venta_media d ");
    	
    	if (intertienda != null) {
    		where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	params = new ArrayList<Object>();
        	
    		where.append(" WHERE 1=1 ");
    		where.append(" AND a.COD_ENSENA = b.COD_ENSENA ");
    		where.append(" AND a.COD_AREA = b.COD_AREA ");
    		where.append(" AND a.COD_REGION = b.COD_REGION ");
    		where.append(" AND c.COD_LOC(+) = a.COD_CENTRO ");
    		where.append(" AND d.COD_LOC(+) = a.COD_CENTRO ");
    		where.append(" AND d.FECHA_VENTA_MEDIA(+) >= trunc(SYSDATE-1) ");
    		if (intertienda.getCodCentro() != null &&  intertienda.getCodCentro().longValue()!=0){
    			where.append(" AND a.COD_CENTRO = ? ");
	        	params.add(intertienda.getCodCentro().longValue());	
    		}
    		if (intertienda.getCodEnsena() != null &&  intertienda.getCodEnsena().longValue()!=0){
    			where.append(" AND a.COD_ENSENA = ? ");
	        	params.add(intertienda.getCodEnsena().longValue());	
    		}
    		if (intertienda.getCodArea() != null &&  intertienda.getCodArea().longValue()!=0){
    			where.append(" AND a.COD_AREA = ? ");
	        	params.add(intertienda.getCodArea().longValue());	
    		}
    		if (intertienda.getCodRegion() != null &&  intertienda.getCodRegion().longValue()!=0){
    			where.append(" AND a.COD_REGION = ? ");
	        	params.add(intertienda.getCodRegion().longValue());	
    		}
    		if (intertienda.getCodZona() != null &&  intertienda.getCodZona().longValue()!=0){
    			where.append(" AND a.COD_ZONA = ? ");
	        	params.add(intertienda.getCodZona().longValue());	
    		}
    		if (intertienda.getDescProvincia() != null &&  intertienda.getDescProvincia()!=""){
    			where.append(" AND trim(a.PROVINCIA) = trim(?) ");
	        	params.add(intertienda.getDescProvincia());
    		}
    		if (null != intertienda.getNegocio() && !intertienda.getNegocio().isEmpty()){
    			where.append(" AND a.NEGOCIO = ? ");
	        	params.add(intertienda.getNegocio());
    		}
    		if (intertienda.getCodReferencia() != null &&  intertienda.getCodReferencia()!=0){
    			where.append(" AND c.COD_ARTICULO(+) = ? ");
	        	params.add(intertienda.getCodReferencia());
    			where.append(" AND d.COD_ARTICULO(+) = ? ");
	        	params.add(intertienda.getCodReferencia());
    		}
    		query.append(where);
    	}    	
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}else{
				order.append("  order by ");
				if (null != intertienda.getDescProvinciaSession()){
					order.append(" DECODE (a.PROVINCIA, ? ,'AA',a.PROVINCIA) asc ,");
					params.add(intertienda.getDescProvinciaSession());
				}
				order.append(" DECODE (a.COD_REGION, ? ,0,a.COD_REGION) asc, DECODE (a.COD_ZONA, ? ,0,a.COD_ZONA) asc , a.COD_CENTRO asc ");
				params.add(intertienda.getCodRegionSession());
				params.add(intertienda.getCodZonaSession());
				query.append(order);
			}
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}
	    logger.info(query.toString());
		List<Intertienda> centros = null;
		
		try {
			if (params != null){
				centros = (List<Intertienda>) this.jdbcTemplate.query(query.toString(),this.rwIntertiendaMap, params.toArray()); 
			} else {
				centros = (List<Intertienda>) this.jdbcTemplate.query(query.toString(),this.rwIntertiendaMap);
			}
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
	    return centros;		
	}
	

	public List<GenericExcelVO> listCentroIntertiendaExcel(Intertienda intertienda, String[] model, Pagination pagination) throws Exception {

		StringBuffer where = null;
    	List<Object> params = null;
    	String fields="";
    	List<String> listColumns = Arrays.asList(model);
    	String mappedField;
    	boolean descCentroIncluido = false;
    	int j = 1;
    	for(String column : listColumns){
    			if(column.equals("descCentro")){
    				descCentroIncluido = true;
    			}
    			mappedField = this.getMappedField(column);
    			
    			if(fields.isEmpty()){
    				fields= mappedField;
    			}else{
    				fields = fields + ", " + mappedField;
    			}	
    			j++;
    	}
    	while (j<=41){
    		//Si el campo cod_art no viene en el colmodel se incluye para poder buscar el CC. Se pone el último para poder eliminarlo
    		//de forma fácil tras el tratamiento del CC tratamiento posterior
    		if (j==41 && !descCentroIncluido){
    			fields = fields + ", " + this.getMappedField("descCentro");
    		}else{
    			fields = fields + ", null";
    		}
    		j++;
    	}
    	StringBuffer query = new StringBuffer(" SELECT ");
    	query.append(fields);
    	query.append(" FROM v_centros_plataformas a, region b, stock_actual_centro c, historico_venta_media d ");
    	
    	if (intertienda != null) {
    		where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	params = new ArrayList<Object>();
        	
    		where.append(" WHERE 1=1 ");
    		where.append(" AND a.COD_ENSENA = b.COD_ENSENA ");
    		where.append(" AND a.COD_AREA = b.COD_AREA ");
    		where.append(" AND a.COD_REGION = b.COD_REGION ");
    		where.append(" AND c.COD_LOC(+) = a.COD_CENTRO ");
    		where.append(" AND d.COD_LOC(+) = a.COD_CENTRO ");
    		where.append(" AND d.FECHA_VENTA_MEDIA(+) = TRUNC(sysdate) ");
    		if (intertienda.getCodCentro() != null &&  intertienda.getCodCentro().longValue()!=0){
    			where.append(" AND a.COD_CENTRO = ? ");
	        	params.add(intertienda.getCodCentro().longValue());	
    		}
    		if (intertienda.getCodEnsena() != null &&  intertienda.getCodEnsena().longValue()!=0){
    			where.append(" AND a.COD_ENSENA = ? ");
	        	params.add(intertienda.getCodEnsena().longValue());	
    		}
    		if (intertienda.getCodArea() != null &&  intertienda.getCodArea().longValue()!=0){
    			where.append(" AND a.COD_AREA = ? ");
	        	params.add(intertienda.getCodArea().longValue());	
    		}
    		if (intertienda.getCodRegion() != null &&  intertienda.getCodRegion().longValue()!=0){
    			where.append(" AND a.COD_REGION = ? ");
	        	params.add(intertienda.getCodRegion().longValue());	
    		}
    		if (intertienda.getCodZona() != null &&  intertienda.getCodZona().longValue()!=0){
    			where.append(" AND a.COD_ZONA = ? ");
	        	params.add(intertienda.getCodZona().longValue());	
    		}
    		if (intertienda.getDescProvincia() != null &&  intertienda.getDescProvincia()!=""){
    			where.append(" AND trim(a.PROVINCIA) = trim(?) ");
	        	params.add(intertienda.getDescProvincia());
    		}
    		if (null != intertienda.getNegocio() && !intertienda.getNegocio().isEmpty()){
    			where.append(" AND a.NEGOCIO = ? ");
	        	params.add(intertienda.getNegocio());
    		}
    		if (intertienda.getCodReferencia() != null &&  intertienda.getCodReferencia()!=0){
    			where.append(" AND c.COD_ARTICULO(+) = ? ");
	        	params.add(intertienda.getCodReferencia());
    			where.append(" AND d.COD_ARTICULO(+) = ? ");
	        	params.add(intertienda.getCodReferencia());
    		}
    		query.append(where);
    	}    	
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}else{
				order.append("  order by ");
				if (null != intertienda.getDescProvinciaSession()){
					order.append(" DECODE (a.PROVINCIA, ? ,'AA',a.PROVINCIA) asc ,");
					params.add(intertienda.getDescProvinciaSession());
				}
				order.append(" DECODE (a.COD_REGION, ? ,0,a.COD_REGION) asc, DECODE (a.COD_ZONA, ? ,0,a.COD_ZONA) asc , a.COD_CENTRO asc ");
				params.add(intertienda.getCodRegionSession());
				params.add(intertienda.getCodZonaSession());
				query.append(order);
			}
			//query = new StringBuffer(Paginate.getQueryLimits(
				//	pagination, query.toString()));
		}
		logger.debug(query.toString());
	
		List<GenericExcelVO> centros = null;
			
		try {
			if (params != null){
				centros = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(),this.rwExcelIntertiendaMap, params.toArray()); 
			} else {
				centros = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(),this.rwExcelIntertiendaMap);
			}
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    return centros;		
	}

    private String getMappedField (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("DESCREGION")){
	  	      return "a.COD_REGION";
	  	  }else if(fieldName.toUpperCase().equals("DESCZONA")){
	  	      return "a.COD_ZONA";
	  	  }else if(fieldName.toUpperCase().equals("DESCCENTRO")){
	  	      return "a.COD_CENTRO";
	  	  }else if(fieldName.toUpperCase().equals("VENTAMEDIA")){
	  	      return "VENTA_MEDIA";
	  	  }else {
	  	      return "a.COD_CENTRO";
	  	  }
    }
    


	@Override
	public Long listCentroIntertiendaCount(Intertienda intertienda) throws Exception {
		
		StringBuffer where = null;
    	List<Object> params = null;
    	StringBuffer query = new StringBuffer(" SELECT COUNT(*) FROM (" +
    										  " SELECT DISTINCT a.COD_CENTRO, a.DESCRIP_CENTRO, a.COD_REGION, b.DESCRIPCION, a.COD_ZONA, a.DESCRIP_ZONA, a.PROVINCIA, a.COD_ENSENA, a.COD_AREA, nvl2(c.COD_LOC,1,0) EXISTE_ARTICULO " + 
				  							  " FROM v_centros_plataformas a, region b, stock_actual_centro c ");

    	if (intertienda != null) {
    		where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	params = new ArrayList<Object>();
        	
    		where.append(" WHERE 1=1 ");
    		where.append(" AND a.COD_ENSENA = b.COD_ENSENA ");
    		where.append(" AND a.COD_AREA = b.COD_AREA ");
    		where.append(" AND a.COD_REGION = b.COD_REGION ");
    		where.append(" AND C.COD_LOC(+) = a.COD_CENTRO ");
    		if (intertienda.getCodCentro() != null &&  intertienda.getCodCentro().longValue()!=0){
    			where.append(" AND a.COD_CENTRO = ? ");
	        	params.add(intertienda.getCodCentro().longValue());	
    		}
    		if (intertienda.getCodEnsena() != null &&  intertienda.getCodEnsena().longValue()!=0){
    			where.append(" AND a.COD_ENSENA = ? ");
	        	params.add(intertienda.getCodEnsena().longValue());	
    		}
    		if (intertienda.getCodArea() != null &&  intertienda.getCodArea().longValue()!=0){
    			where.append(" AND a.COD_AREA = ? ");
	        	params.add(intertienda.getCodArea().longValue());	
    		}
    		if (intertienda.getCodRegion() != null &&  intertienda.getCodRegion().longValue()!=0){
    			where.append(" AND a.COD_REGION = ? ");
	        	params.add(intertienda.getCodRegion().longValue());	
    		}
    		if (intertienda.getCodZona() != null &&  intertienda.getCodZona().longValue()!=0){
    			where.append(" AND a.COD_ZONA = ? ");
	        	params.add(intertienda.getCodZona().longValue());	
    		}
    		if (intertienda.getDescProvincia() != null &&  intertienda.getDescProvincia()!=""){
    			where.append(" AND trim(a.PROVINCIA) = trim(?) ");
	        	params.add(intertienda.getDescProvincia());
    		}
    		if (intertienda.getCodReferencia() != null &&  intertienda.getCodReferencia()!=0){
    			where.append(" AND c.COD_ARTICULO(+) = ? ");
	        	params.add(intertienda.getCodReferencia());
    		}
    		
    		query.append(where);
    	}
		query.append(" ) ");
    	
	    
	    Long cont = null;
	    try {
			cont =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return cont;
	}
	
    private String getMappedField (String fieldName) {
	      if (fieldName.toUpperCase().equals("DESCREGION")){
	  	      return "a.COD_REGION||' - '||b.DESCRIPCION ";
	  	  }else if(fieldName.toUpperCase().equals("DESCZONA")){
	  	      return "a.COD_ZONA||' - '||a.DESCRIP_ZONA";
	  	  }else if(fieldName.toUpperCase().equals("DESCCENTRO")){
	  	      return "a.COD_CENTRO||' - '||a.DESCRIP_CENTRO";
	  	  }else if(fieldName.toUpperCase().equals("VENTAMEDIA")){
	  	      return "nvl2(d.UNID_TOT_VENTA_TARIFA, nvl2(d.DIAS, DECODE(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_TARIFA / d.DIAS,"+Constantes.POSICIONES_DECIMALES+")),0),0) + nvl2(d.UNID_TOT_VENTA_COMPETENCIA, nvl2(d.DIAS, DECODE(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_COMPETENCIA / d.DIAS,"+Constantes.POSICIONES_DECIMALES+")),0),0) + nvl2(d.UNID_TOT_VENTA_OFERTA, nvl2(d.DIAS, DECODE(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_OFERTA / d.DIAS,"+Constantes.POSICIONES_DECIMALES+")),0),0) + nvl2(d.UNID_TOT_VENTA_ANTICIPADA, nvl2(d.DIAS, DECODE(d.DIAS,0,0,ROUND(d.UNID_TOT_VENTA_ANTICIPADA / d.DIAS,"+Constantes.POSICIONES_DECIMALES+")),0),0) VENTA_MEDIA";
	  	  }else if(fieldName.toUpperCase().equals("STOCK")){
	  	      return "NULL AS STOCK";
	  	  } else {
	  		  return fieldName;
	  	  }
    }
}
