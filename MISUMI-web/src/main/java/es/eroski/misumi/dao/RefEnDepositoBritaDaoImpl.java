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

import es.eroski.misumi.dao.iface.RefEnDepositoBritaDao;
import es.eroski.misumi.model.RefAsociadas;
import es.eroski.misumi.model.RefEnDepositoBrita;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class RefEnDepositoBritaDaoImpl implements RefEnDepositoBritaDao{
	
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(RefEnDepositoBritaDaoImpl.class);
	// private static Logger logger = Logger.getLogger(RefEnDepositoBritaDaoImpl.class);

	private RowMapper<RefEnDepositoBrita> rwRefEndepositoBritaMap = new RowMapper<RefEnDepositoBrita>() {
		public RefEnDepositoBrita mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new RefEnDepositoBrita(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"),
    			resultSet.getLong("COD_PROVR_GEN"), resultSet.getLong("COD_PROVR_TRABAJO"), 
    			resultSet.getString("NOMBRE")
		    );
		}
	};

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<RefEnDepositoBrita> findAll(RefEnDepositoBrita refEnDepositoBrita) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_PROVR_GEN, COD_PROVR_TRABAJO, NOMBRE "	
    										+ " FROM REF_EN_DEPOSITO_BRITA ");
    	
        if (refEnDepositoBrita  != null){
        	if(refEnDepositoBrita.getCodCentro()!=null){
	       		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(refEnDepositoBrita.getCodCentro());	        		
	       	}
        	if(refEnDepositoBrita.getCodArt()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(refEnDepositoBrita.getCodArt());	        		
        	}
        	if(refEnDepositoBrita.getCodProvrGen()!=null){
       		 	 where.append(" AND COD_PROVR_GEN = ? ");
       		 	params.add(refEnDepositoBrita.getCodProvrGen());	        		
        	}
        	if(refEnDepositoBrita.getCodProvrTrabajo()!=null){
       		 	 where.append(" AND COD_PROVR_TRABAJO = ? ");
	        	 params.add(refEnDepositoBrita.getCodProvrTrabajo());	        		
        	}
        	if(refEnDepositoBrita.getNombre()!=null){
        		 where.append(" AND UPPER(NOMBRE) = upper(?) ");
	        	 params.add(refEnDepositoBrita.getNombre());	        		
        	}
        }
        
        query.append(where);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_art ");
		query.append(order);

		List<RefEnDepositoBrita> refEnDepositoBritaLista = null;		
	    try {

	    	refEnDepositoBritaLista = (List<RefEnDepositoBrita>) this.jdbcTemplate.query(query.toString(),this.rwRefEndepositoBritaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return refEnDepositoBritaLista;
    }
    
    @Override
    public Long findAllCont(RefEnDepositoBrita refEnDepositoBrita) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
    										+ " FROM REF_EN_DEPOSITO_BRITA ");
    
        if (refEnDepositoBrita  != null){
        	if(refEnDepositoBrita.getCodCentro()!=null){
	       		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(refEnDepositoBrita.getCodCentro());	        		
	       	}
        	if(refEnDepositoBrita.getCodArt()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(refEnDepositoBrita.getCodArt());	        		
        	}
        	if(refEnDepositoBrita.getCodProvrGen()!=null){
       		 	 where.append(" AND COD_PROVR_GEN = ? ");
       		 	params.add(refEnDepositoBrita.getCodProvrGen());	        		
        	}
        	if(refEnDepositoBrita.getCodProvrTrabajo()!=null){
       		 	 where.append(" AND COD_PROVR_TRABAJO = ? ");
	        	 params.add(refEnDepositoBrita.getCodProvrTrabajo());	        		
        	}
        	if(refEnDepositoBrita.getNombre()!=null){
        		 where.append(" AND UPPER(NOMBRE) = upper(?) ");
	        	 params.add(refEnDepositoBrita.getNombre());	        		
        	}
        }
        
        query.append(where);
        
	   
	    Long cont = null;
	    try {

	    	cont =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return  cont;
    }
}
