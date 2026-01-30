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

import es.eroski.misumi.dao.iface.CentroAutoservicioDao;
import es.eroski.misumi.model.CentroAutoservicio;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CentroAutoservicioDaoImpl implements CentroAutoservicioDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(CentroAutoservicioDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(CentroAutoservicioDaoImpl.class);

	 private RowMapper<CentroAutoservicio> rwCentroAutoservicioMap = new RowMapper<CentroAutoservicio>() {
			public CentroAutoservicio mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new CentroAutoservicio(resultSet.getDate("CREATION_DATE"),
			    			resultSet.getLong("COD_CENTRO"), 
			    			resultSet.getFloat("PORCENTAJE_CAPACIDAD"),
			    			resultSet.getLong("TIPO"),
			    			resultSet.getString("DESCRIPCION")
				);
			}
	 };
		    
	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<CentroAutoservicio> findAll(CentroAutoservicio centroAutoservicio) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT CREATION_DATE, COD_CENTRO, PORCENTAJE_CAPACIDAD, TIPO, DESCRIPCION " 
    										+ " FROM CENTRO_AUTOSERVICIO ");

    	if (centroAutoservicio  != null){
        	if(centroAutoservicio.getCreationDate()!=null){
        		where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
	        	 params.add(centroAutoservicio.getCreationDate());	        		
        	}
        	if(centroAutoservicio.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(centroAutoservicio.getCodCentro());	        		
        	}
        	if(centroAutoservicio.getPorcentajeCapacidad()!=null){
        		 where.append(" AND PORCENTAJE_CAPACIDAD = ? ");
	        	 params.add(centroAutoservicio.getPorcentajeCapacidad());	        		
        	}
        	if(centroAutoservicio.getTipo()!=null){
        		 where.append(" AND TIPO = ? ");
	        	 params.add(centroAutoservicio.getTipo());	        		
        	}
        	if(centroAutoservicio.getDescripcion()!=null){
       		 	 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
	        	 params.add(centroAutoservicio.getDescripcion());
        	}
       	}
        
        query.append(where);
    
        List<CentroAutoservicio> lista = null;
        try {
		    lista =  (List<CentroAutoservicio>) this.jdbcTemplate.query(query.toString(),this.rwCentroAutoservicioMap, params.toArray());
		    
	    } catch (Exception e){
			
	 		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	 	}
	
		return lista;
    }
    
    @Override
    public Long findAllCont(CentroAutoservicio centroAutoservicio) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
    										+ " FROM CENTRO_AUTOSERVICIO ");
    
    	if (centroAutoservicio  != null){
        	if(centroAutoservicio.getCreationDate()!=null){
        		where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
	        	 params.add(centroAutoservicio.getCreationDate());	        		
        	}
        	if(centroAutoservicio.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(centroAutoservicio.getCodCentro());	        		
        	}
        	if(centroAutoservicio.getPorcentajeCapacidad()!=null){
        		 where.append(" AND PORCENTAJE_CAPACIDAD = ? ");
	        	 params.add(centroAutoservicio.getPorcentajeCapacidad());	        		
        	}
        	if(centroAutoservicio.getTipo()!=null){
        		 where.append(" AND TIPO = ? ");
	        	 params.add(centroAutoservicio.getTipo());	        		
        	}
        	if(centroAutoservicio.getDescripcion()!=null){
       		 	 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
	        	 params.add(centroAutoservicio.getDescripcion());
        	}
       	}
        
        query.append(where);
        
        Long cont = null;
        try { 
        	cont =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		    
	    } catch (Exception e){
			
	 		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	 	}
	
		return cont;
    }

    @Override
    public Boolean esAutoservicio(CentroAutoservicio centroAutoservicio) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE PORCENTAJE_CAPACIDAD IS NOT NULL ");
    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
    										+ " FROM CENTRO_AUTOSERVICIO ");
    
    	if (centroAutoservicio  != null){
        	if(centroAutoservicio.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(centroAutoservicio.getCodCentro());	        		
        	}
       	}
        
        query.append(where);
        
	    Boolean esAutoservicio = false;
	    try {
	    	esAutoservicio =  (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
		    
	    } catch (Exception e){
			
	 		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	 	}
	    
	    return esAutoservicio;
	}
}
