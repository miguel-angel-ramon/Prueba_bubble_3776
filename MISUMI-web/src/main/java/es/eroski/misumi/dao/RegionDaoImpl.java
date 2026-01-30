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

import es.eroski.misumi.dao.iface.RegionDao;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.model.Region;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class RegionDaoImpl implements RegionDao{
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = Logger.getLogger(VAgruComerRefDaoImpl.class);
	 private RowMapper<Region> rwRegionMap = new RowMapper<Region>() {
			public Region mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new Region(resultSet.getLong("COD_REGION"),null,
			    		    null, null,
			    		    resultSet.getString("DESCRIPCION")
				    );
			}

		    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<Region> findAll(Region region) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	StringBuffer query = new StringBuffer(" SELECT COD_REGION, trim(DESCRIPCION) DESCRIPCION  " 
	    										+ " FROM  region ");
	    	
	    	where.append(" WHERE 1=1 ");
	        if (region  != null){
	        	if(region.getCodRegion()!=null){
	        		 where.append(" AND cod_region = ? ");
		        	 params.add(region.getCodRegion());	        		
	        	}
	        }
	        query.append(where);
	        
			StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			group.append(" group by cod_region, descripcion ");
			query.append(group);
		
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by trim(descripcion) ");
			query.append(order);
			
	
		    
		    List<Region> lista = null;
		    try {

		    	lista = (List<Region>) this.jdbcTemplate.query(query.toString(),this.rwRegionMap, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    
		    return lista;
	    }
	   
}
