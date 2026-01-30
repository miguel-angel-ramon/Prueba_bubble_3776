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

import es.eroski.misumi.dao.iface.RefAsociadasDao;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;


 	
@Repository
public class RefAsociadasDaoImpl implements RefAsociadasDao{
	
	private JdbcTemplate jdbcTemplate;

	private RowMapper<RefAsociadas> rwRefAsociadasMap = new RowMapper<RefAsociadas>() {
		public RefAsociadas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new RefAsociadas(resultSet.getLong("COD_ARTICULO"),resultSet.getLong("COD_ARTICULO_HIJO"), resultSet.getLong("CANTIDAD"),
					resultSet.getLong("COD_NIVEL_ESTR_ART_EC1"), resultSet.getLong("COD_NIVEL_ESTR_ART_EC2"),
					resultSet.getLong("COD_NIVEL_ESTR_ART_EC3"), resultSet.getLong("COD_NIVEL_ESTR_ART_EC4"),
					resultSet.getLong("COD_NIVEL_ESTR_ART_EC5"));
		}
    };
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<RefAsociadas> findAll(RefAsociadas refAsociadas) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COD_ARTICULO, COD_ARTICULO_HIJO, CANTIDAD, " 
    			+ " COD_NIVEL_ESTR_ART_EC1, COD_NIVEL_ESTR_ART_EC2, COD_NIVEL_ESTR_ART_EC3," 
    			+ " COD_NIVEL_ESTR_ART_EC4, COD_NIVEL_ESTR_ART_EC5" 
    			+ " FROM REF_ASOCIADAS ");

        if (refAsociadas  != null){
        	if(refAsociadas.getCodArticulo()!=null && refAsociadas.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(refAsociadas.getCodArticulo());	        		
        	}
        	if(refAsociadas.getCodArticuloHijo()!=null && refAsociadas.getCodArticuloHijo()!=null){
       		 where.append(" AND COD_ARTICULO_HIJO = ? ");
	        	 params.add(refAsociadas.getCodArticuloHijo());	        		
        	}
        	if(refAsociadas.getCantidad()!=null && refAsociadas.getCantidad()!=null){
       		 where.append(" AND CANTIDAD = ? ");
	        	 params.add(refAsociadas.getCantidad());	        		
        	}
        	if(refAsociadas.getCodNivelEstrArtEc1()!=null && refAsociadas.getCodNivelEstrArtEc1()!=null){
       		 where.append(" AND COD_NIVEL_ESTR_ART_EC1 = ? ");
	        	 params.add(refAsociadas.getCodNivelEstrArtEc1());	        		
        	}
        	if(refAsociadas.getCodNivelEstrArtEc2()!=null && refAsociadas.getCodNivelEstrArtEc2()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC2 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc2());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc3()!=null && refAsociadas.getCodNivelEstrArtEc3()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC3 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc3());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc4()!=null && refAsociadas.getCodNivelEstrArtEc4()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC4 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc4());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc5()!=null && refAsociadas.getCodNivelEstrArtEc5()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC5 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc5());	        		
           	}
        }
        
        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		String campoOrdenacion = "COD_ARTICULO_HIJO";
		order.append(" order by " + campoOrdenacion + " asc ");	
		query.append(order);

	   
	    List<RefAsociadas> lista = null;
	    try {

	    	lista = (List<RefAsociadas>) this.jdbcTemplate.query(query.toString(),this.rwRefAsociadasMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return  lista;
	    
    }
    
    @Override
    public Long findAllCont(RefAsociadas refAsociadas) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
    										+ " FROM REF_ASOCIADAS ");
    
    	
        if (refAsociadas  != null){
        	if(refAsociadas.getCodArticulo()!=null && refAsociadas.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(refAsociadas.getCodArticulo());	        		
        	}
        	if(refAsociadas.getCodArticuloHijo()!=null && refAsociadas.getCodArticuloHijo()!=null){
       		 where.append(" AND COD_ARTICULO_HIJO = ? ");
	        	 params.add(refAsociadas.getCodArticuloHijo());	        		
        	}
        	if(refAsociadas.getCantidad()!=null && refAsociadas.getCantidad()!=null){
       		 where.append(" AND CANTIDAD = ? ");
	        	 params.add(refAsociadas.getCantidad());	        		
        	}
        	if(refAsociadas.getCodNivelEstrArtEc1()!=null && refAsociadas.getCodNivelEstrArtEc1()!=null){
       		 where.append(" AND COD_NIVEL_ESTR_ART_EC1 = ? ");
	        	 params.add(refAsociadas.getCodNivelEstrArtEc1());	        		
        	}
        	if(refAsociadas.getCodNivelEstrArtEc2()!=null && refAsociadas.getCodNivelEstrArtEc2()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC2 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc2());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc3()!=null && refAsociadas.getCodNivelEstrArtEc3()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC3 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc3());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc4()!=null && refAsociadas.getCodNivelEstrArtEc4()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC4 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc4());	        		
           	}
        	if(refAsociadas.getCodNivelEstrArtEc5()!=null && refAsociadas.getCodNivelEstrArtEc5()!=null){
          		 where.append(" AND COD_NIVEL_ESTR_ART_EC5 = ? ");
   	        	 params.add(refAsociadas.getCodNivelEstrArtEc5());	        		
           	}
        }
        
        query.append(where);
        
	  
	    
	    Long cont = null;
	    try {

	    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return  cont;
    }
}
