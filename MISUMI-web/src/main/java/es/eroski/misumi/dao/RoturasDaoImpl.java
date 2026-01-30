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

import es.eroski.misumi.dao.iface.RoturasDao;
import es.eroski.misumi.model.Roturas;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class RoturasDaoImpl implements RoturasDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	 private RowMapper<Roturas> rwRoturasMap = new RowMapper<Roturas>() {
			public Roturas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new Roturas(resultSet.getLong("COD_LOC"),resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getDate("FEC_ROTURA"), resultSet.getLong("COD_ROTURA"),
			    			resultSet.getLong("CREATED_BY"), resultSet.getDate("CREATION_DATE"),
			    			resultSet.getLong("LAST_UPDATED_BY"), resultSet.getDate("LAST_UPDATE_DATE"),
			    			resultSet.getLong("LAST_UPDATE_LOGIN"), resultSet.getLong("TECLE"),
			    			resultSet.getLong("TCN")
				    );
			}

		};

		 private RowMapper<Long> rwRoturasCountMap = new RowMapper<Long>() {
				public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    return resultSet.getLong(1);
				}

		 };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<Roturas> findAll(Roturas roturas) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_LOC, COD_ARTICULO, FEC_ROTURA, COD_ROTURA, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN " 
	    										+ " FROM ROTURAS ");
	    
	    	
	        if (roturas  != null){
	        	if(roturas.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(roturas.getCodLoc());	        		
	        	}
	        	if(roturas.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(roturas.getCodArticulo());	        		
	        	}
	        	if(roturas.getFechaRotura()!=null){
	        		 where.append(" AND TRUNC(FEC_ROTURA) = TRUNC(?) ");
		        	 params.add(roturas.getFechaRotura());	        		
	        	}
	        	if(roturas.getCodRotura()!=null){
	        		 where.append(" AND COD_ROTURA = ? ");
		        	 params.add(roturas.getCodRotura());	        		
	        	}
	        	if(roturas.getCreatedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(roturas.getCreatedBy());	        		
	        	}
	        	if(roturas.getCreationDate()!=null){
	        		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
		        	 params.add(roturas.getCreationDate());	        		
	        	}
	        	if(roturas.getLastUpdatedBy()!=null){
	        		 where.append(" AND LAST_UPDATED_BY = ? ");
		        	 params.add(roturas.getLastUpdatedBy());	        		
	        	}
	        	if(roturas.getLastUpdateDate()!=null){
	        		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
		        	 params.add(roturas.getLastUpdateDate());	        		
	        	}
	        	if(roturas.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(roturas.getLastUpdateLogin());	        		
	        	}
	        	if(roturas.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(roturas.getTecle());	        		
	        	}
	        	if(roturas.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(roturas.getTcn());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo ");
			query.append(order);

			List<Roturas> roturasLista = null;		
			
			try {

				roturasLista = (List<Roturas>) this.jdbcTemplate.query(query.toString(),this.rwRoturasMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return roturasLista;
	    }

	    @Override
	    public Long count(Roturas roturas) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
	    										+ " FROM ROTURAS ");
	    
	        if (roturas  != null){
	        	if(roturas.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(roturas.getCodLoc());	        		
	        	}
	        	if(roturas.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(roturas.getCodArticulo());	        		
	        	}
	        	if(roturas.getFechaRotura()!=null){
	        		 where.append(" AND TRUNC(FEC_ROTURA) = TRUNC(?) ");
		        	 params.add(roturas.getFechaRotura());	        		
	        	}
	        	if(roturas.getCodRotura()!=null){
	        		 where.append(" AND COD_ROTURA = ? ");
		        	 params.add(roturas.getCodRotura());	        		
	        	}
	        	if(roturas.getCreatedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(roturas.getCreatedBy());	        		
	        	}
	        	if(roturas.getCreationDate()!=null){
	        		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
		        	 params.add(roturas.getCreationDate());	        		
	        	}
	        	if(roturas.getLastUpdatedBy()!=null){
	        		 where.append(" AND LAST_UPDATED_BY = ? ");
		        	 params.add(roturas.getLastUpdatedBy());	        		
	        	}
	        	if(roturas.getLastUpdateDate()!=null){
	        		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
		        	 params.add(roturas.getLastUpdateDate());	        		
	        	}
	        	if(roturas.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(roturas.getLastUpdateLogin());	        		
	        	}
	        	if(roturas.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(roturas.getTecle());	        		
	        	}
	        	if(roturas.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(roturas.getTcn());	        		
	        	}
	        }
	        
	        query.append(where);

			List<Long> roturasCountLista = null;		
			
			try {

				roturasCountLista = (List<Long>) this.jdbcTemplate.query(query.toString(),this.rwRoturasCountMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return roturasCountLista.get(0);
	    }
}
