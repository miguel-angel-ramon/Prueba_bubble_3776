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

import es.eroski.misumi.dao.iface.AgrupacionDao;
import es.eroski.misumi.model.Agrupacion;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class AgrupacionDaoImpl implements AgrupacionDao{
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = Logger.getLogger(VAgruComerRefDaoImpl.class);
	 private RowMapper<Agrupacion> rwAgruMap = new RowMapper<Agrupacion>() {
			public Agrupacion mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new Agrupacion(resultSet.getLong("COD_ART"),resultSet.getLong("GRUPO1"),
			    		    resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
			    		    resultSet.getLong("GRUPO4"),resultSet.getLong("GRUPO5")
				    );
			}

		    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<Agrupacion> findAll(Agrupacion agrupacion) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//params.add(lang);
	    	StringBuffer query = new StringBuffer(" SELECT COD_ART , GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5  " 
	    										+ " FROM  v_datos_diario_art ");
	    	
	    	
//	    	select grupo1, grupo2, grupo3, grupo4, grupo5
//	    	from v_datos_diario_art
//	    	where cod_art=
//	    
	    	
	        if (agrupacion  != null){
	        	if(agrupacion.getCodArt()!=null){
	        		 where.append(" AND cod_art = ? ");
		        	 params.add(agrupacion.getCodArt());	        		
	        	}
	        	

	        }
	        
	        query.append(where);
		    
	        List<Agrupacion> lista = null;
	        try {
	        
	        	lista =  (List<Agrupacion>) this.jdbcTemplate.query(query.toString(),this.rwAgruMap, params.toArray());
	        
	    	} catch (Exception e){
			
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
		    
		    return lista;
	    }
	   
}
