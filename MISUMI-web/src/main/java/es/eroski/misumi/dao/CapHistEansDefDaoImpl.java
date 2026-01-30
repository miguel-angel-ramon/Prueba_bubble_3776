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

import es.eroski.misumi.dao.iface.CapHistEansDefDao;
import es.eroski.misumi.model.CapHistEansDef;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;


 	
@Repository
public class CapHistEansDefDaoImpl implements CapHistEansDefDao{
	   private JdbcTemplate jdbcTemplate;

	   private RowMapper<CapHistEansDef> rwCapHistEansDefMap = new RowMapper<CapHistEansDef>() {
			public CapHistEansDef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new CapHistEansDef(resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getLong("COD_ARTICULO_CAP"), resultSet.getLong("COD_EAN"),
			    			resultSet.getDate("FEC_CREACION"), resultSet.getDate("FEC_FIN"),
			    			resultSet.getDate("FEC_INICIO"), resultSet.getString("HORA"),
			    			resultSet.getString("USUARIO")
				);
			}
		};
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<CapHistEansDef> findAll(CapHistEansDef capHistEansDef) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COD_ARTICULO, COD_ARTICULO_CAP, COD_EAN, " 
	    										+		"FEC_CREACION, FEC_FIN, FEC_INICIO, HORA,  USUARIO "
	    										+ " FROM CAP_HIST_EANS_DEF ");
		    
	        if (capHistEansDef  != null){
	        	if(capHistEansDef.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(capHistEansDef.getCodArticulo());	        		
	        	}
	        	if(capHistEansDef.getCodArticuloCap()!=null){
	        		 where.append(" AND COD_ARTICULO_CAP = ? ");
		        	 params.add(capHistEansDef.getCodArticuloCap());	        		
	        	}
	        	if(capHistEansDef.getCodEan()!=null){
	        		 where.append(" AND COD_EAN = ? ");
		        	 params.add(capHistEansDef.getCodEan());	        		
	        	}
	        	if(capHistEansDef.getFecCreacion()!=null){
	        		 where.append(" AND TRUNC(FEC_CREACION) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecCreacion());	        		
	        	}
	        	if(capHistEansDef.getFecFin()!=null){
	        		 where.append(" AND TRUNC(FEC_FIN) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecFin());	        		
	        	}
	        	if(capHistEansDef.getFecInicio()!=null){
	        		 where.append(" AND TRUNC(FEC_INICIO) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecInicio());	        		
	        	}
	        	if(capHistEansDef.getHora()!=null){
	        		 where.append(" AND UPPER(HORA) = upper(?) ");
		        	 params.add(capHistEansDef.getHora());	        		
	        	}
	        	if(capHistEansDef.getUsuario()!=null){
	        		 where.append(" AND UPPER(USUARIO) = upper(?) ");
		        	 params.add(capHistEansDef.getUsuario());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by COD_ARTICULO ");
			query.append(order);

			List<CapHistEansDef> lista = null;
			try {
				lista =  (List<CapHistEansDef>) this.jdbcTemplate.query(query.toString(),this.rwCapHistEansDefMap, params.toArray());
			    
		    } catch (Exception e){
				
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
		
			return lista;
	    }
	    
	    @Override
	    public Long findAllCont(CapHistEansDef capHistEansDef) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
	    										+ " FROM CAP_HIST_EANS_DEF ");
	    
	        if (capHistEansDef  != null){
	        	if(capHistEansDef.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(capHistEansDef.getCodArticulo());	        		
	        	}
	        	if(capHistEansDef.getCodArticuloCap()!=null){
	        		 where.append(" AND COD_ARTICULO_CAP = ? ");
		        	 params.add(capHistEansDef.getCodArticuloCap());	        		
	        	}
	        	if(capHistEansDef.getCodEan()!=null){
	        		 where.append(" AND COD_EAN = ? ");
		        	 params.add(capHistEansDef.getCodEan());	        		
	        	}
	        	if(capHistEansDef.getFecCreacion()!=null){
	        		 where.append(" AND TRUNC(FEC_CREACION) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecCreacion());	        		
	        	}
	        	if(capHistEansDef.getFecFin()!=null){
	        		 where.append(" AND TRUNC(FEC_FIN) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecFin());	        		
	        	}
	        	if(capHistEansDef.getFecInicio()!=null){
	        		 where.append(" AND TRUNC(FEC_INICIO) = TRUNC(?) ");
		        	 params.add(capHistEansDef.getFecInicio());	        		
	        	}
	        	if(capHistEansDef.getHora()!=null){
	        		 where.append(" AND UPPER(HORA) = upper(?) ");
		        	 params.add(capHistEansDef.getHora());	        		
	        	}
	        	if(capHistEansDef.getUsuario()!=null){
	        		 where.append(" AND UPPER(USUARIO) = upper(?) ");
		        	 params.add(capHistEansDef.getUsuario());	        		
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
}
