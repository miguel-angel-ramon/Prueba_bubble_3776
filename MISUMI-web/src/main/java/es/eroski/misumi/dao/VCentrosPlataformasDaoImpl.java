package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VCentrosPlataformasDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VCentrosPlataformasDaoImpl implements VCentrosPlataformasDao {
	private JdbcTemplate jdbcTemplate;
	//private static Logger logger = Logger.getLogger(VCentrosPlataformasDaoImpl.class);

	private RowMapper<Centro> rwCentroMap = new RowMapper<Centro>() {
		public Centro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new Centro(resultSet.getLong("cod_centro"),resultSet.getString("descrip_centro"),
		    		resultSet.getString("negocio"), resultSet.getLong("cod_ensena"),
		    		resultSet.getLong("cod_area"), resultSet.getLong("cod_region"), 
		    		resultSet.getLong("cod_zona"), resultSet.getString("descrip_zona"),
		    		resultSet.getString("provincia"), resultSet.getLong("cod_negocio"), resultSet.getLong("cod_soc"), resultSet.getLong("flg_cpb_especial"),resultSet.getLong("flg_pr_gama_cpb_kosmos")
			    );
		}

	};

	//Para rellenar con la lista de zonas
	private RowMapper<Centro> rwCentroZonaMap = new RowMapper<Centro>() {
		public Centro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new Centro(null, null,
		    		null, null,
		    		null, null, 
		    		resultSet.getLong("cod_zona"), resultSet.getString("descrip_zona"),
		    		null, null,null
			    );
		}

	};

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	
	@Override
	public List<Centro> findAll(Centro centro)
			throws Exception {
		   	
		StringBuffer where = null;
    	List<Object> params = null;
    	
    	StringBuffer query = new StringBuffer(" select cod_centro, descrip_centro, negocio, cod_ensena, cod_area, cod_region, cod_zona, descrip_zona, provincia, cod_negocio, cod_soc, flg_cpb_especial, flg_pr_gama_cpb_kosmos " +
    										  " from v_centros_plataformas ");
    	if (centro != null) {
    		where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	params = new ArrayList<Object>();
        	
    		where.append("WHERE 1=1 ");
    		if (centro.getCodCentro() != null &&  centro.getCodCentro().longValue()!=0){
    			where.append(" AND cod_centro = ? ");
	        	params.add(centro.getCodCentro().longValue());	
    		}
    		
    		if(centro.getCodRegion()!= null){
    			where.append(" AND cod_region = ? ");
	        	params.add(centro.getCodRegion());
    		}
    		
    		if(centro.getCodZona()!= null){
    			where.append(" AND cod_zona = ? ");
	        	params.add(centro.getCodZona());
    		}
    		
    		if(centro.getFlgCpbEspecial() != null){
    			where.append(" AND flg_cpb_especial = ? ");
	        	params.add(centro.getFlgCpbEspecial());
    		}
    		
    		if(centro.getFlgCpbNuevo() != null){
    			where.append(" AND flg_pr_gama_cpb_kosmos = ? ");
	        	params.add(centro.getFlgCpbNuevo());
    		}
	        	
    		query.append(where);
    	}    	
    	
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, descrip_centro ");
		query.append(order);
	
		List<Centro> centros = null;		

		try {
			if (params != null){
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap, params.toArray()); 
			} else {
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap);
			}
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
			
	    return centros;		
	}
	@Override
	public List<Centro> findByCodDesc(String matcher)
			throws Exception {
		   	
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");
    	List<Object> params = new ArrayList<Object>();
    	
    	StringBuffer query = new StringBuffer(" select cod_centro, descrip_centro, negocio, cod_ensena, cod_area, cod_region, cod_zona, descrip_zona, provincia, cod_negocio, cod_soc, flg_cpb_especial, flg_pr_gama_cpb_kosmos  " +
    										  " from v_centros_plataformas ");
    	if (matcher != null) {
    			where.append(" and upper(cod_centro||' - '|| descrip_centro) like upper(?) ");
	        	params.add("%" + matcher+ "%");	
    	
    	}    	
    	  query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, descrip_centro ");
		query.append(order);
	
	
		List<Centro> centros = null;		

		try {
			centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap, params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
			
	    return centros;		
	}

	@Override
	public List<Centro> listZonasByRegion(Centro centro) 
			throws Exception {
		StringBuffer where = null;
    	List<Object> params = null;
    	
    	StringBuffer query = new StringBuffer(" select cod_zona, trim(descrip_zona) descrip_zona" +
    										  " from v_centros_plataformas ");
    	if (centro != null) {
    		where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	params = new ArrayList<Object>();
        	
    		where.append("WHERE 1=1 ");
    		if (centro.getCodRegion() != null &&  centro.getCodRegion().longValue()!=0){
    			where.append(" AND cod_region = ? ");
	        	params.add(centro.getCodRegion().longValue());	
    		}
    		query.append(where);
    	}    	
    	
		StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		group.append(" group by cod_zona, descrip_zona ");
		query.append(group);
	
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by trim(descrip_zona) ");
		query.append(order);
	
		
	    
	    List<Centro> centros = null;		

		try {
			if (params != null){
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroZonaMap, params.toArray()); 
			} else {
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroZonaMap);
			}

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
			
	    return centros;		
	}
	
	@Override
	public Boolean isLotesCentroActivo(Long codCentro) throws Exception {
		
    	
		//Verifico que las cestas esten activas
    	StringBuffer queryCestas = new StringBuffer(" SELECT COUNT(*) FROM CESTAS_NAVIDAD CN WHERE TRUNC(SYSDATE) BETWEEN CN.FECHA_INICIO AND CN.FECHA_FIN");
    	
    	Long countCestasActivas= null;
    	
    	try {
    		countCestasActivas= this.jdbcTemplate.queryForObject(queryCestas.toString(), Long.class);

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(queryCestas.toString(), null, e);
		}
		
    	if(countCestasActivas >0 ){
    		return true;
    	} else {
    		return false;
    	}
	}

}
