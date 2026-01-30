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

import es.eroski.misumi.dao.iface.ParamCentrosVpDao;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.ParamCentrosVp;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ParamCentrosVpDaoImpl implements ParamCentrosVpDao{
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(ParamCentrosVpDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(ParamCentrosVpDaoImpl.class);

	 private RowMapper<ParamCentrosVp> rwParamCentrosVpMap = new RowMapper<ParamCentrosVp>() {
			public ParamCentrosVp mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new ParamCentrosVp(resultSet.getLong("COD_LOC"),
			    			resultSet.getString("FLG_STOCK"), 
			    			resultSet.getString("FLG_CAPACIDAD"),
			    			resultSet.getString("FLG_PORC_CONSUMIDOR"),
			    			resultSet.getString("FLG_FACING")
				);
			}
	 };
		    
	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<ParamCentrosVp> findAll(ParamCentrosVp paramCentrosVp) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COD_LOC, FLG_STOCK, FLG_CAPACIDAD, FLG_PORC_CONSUMIDOR, FLG_FACING " 
    										+ " FROM PARAM_CENTROS_VP ");
    
        if (paramCentrosVp  != null){
        	if(paramCentrosVp.getCodLoc()!=null){
        		 where.append(" AND COD_LOC = ? ");
	        	 params.add(paramCentrosVp.getCodLoc());	        		
        	}
        	if(paramCentrosVp.getFlgStock()!=null){
        		 where.append(" AND FLG_STOCK = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgStock());	        		
        	}
        	if(paramCentrosVp.getFlgCapacidad()!=null){
        		 where.append(" AND FLG_CAPACIDAD = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgCapacidad());	        		
        	}
        	if(paramCentrosVp.getFlgPorcConsumidor()!=null){
        		 where.append(" AND FLG_PORC_CONSUMIDOR = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgPorcConsumidor());	        		
        	}
        	if(paramCentrosVp.getFlgFacingCentro()!=null){
       		 where.append(" AND FLG_FACING = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgFacingCentro());	        		
       	    }
        }
        
        query.append(where);

	    List<ParamCentrosVp> lista = null;
	    try {

			lista = (List<ParamCentrosVp>) this.jdbcTemplate.query(query.toString(),this.rwParamCentrosVpMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    return lista;
    }
    
    @Override
    public Long findAllCont(ParamCentrosVp paramCentrosVp) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
    										+ " FROM PARAM_CENTROS_VP ");
    
        if (paramCentrosVp  != null){
        	if(paramCentrosVp.getCodLoc()!=null){
        		 where.append(" AND COD_LOC = ? ");
	        	 params.add(paramCentrosVp.getCodLoc());	        		
        	}
        	if(paramCentrosVp.getFlgStock()!=null){
        		 where.append(" AND FLG_STOCK = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgStock());	        		
        	}
        	if(paramCentrosVp.getFlgCapacidad()!=null){
        		 where.append(" AND FLG_CAPACIDAD = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgCapacidad());	        		
        	}
        	if(paramCentrosVp.getFlgPorcConsumidor()!=null){
        		 where.append(" AND FLG_PORC_CONSUMIDOR = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgPorcConsumidor());	        		
        	}
        	if(paramCentrosVp.getFlgFacingCentro()!=null){
       		 where.append(" AND FLG_FACING = upper(?) ");
	        	 params.add(paramCentrosVp.getFlgFacingCentro());	        		
       	    }
        }
        
        query.append(where);

	    Long cont = null;
	    try {

	    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    return cont;
	    
    }
}
