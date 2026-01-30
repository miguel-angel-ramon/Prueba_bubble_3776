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

import es.eroski.misumi.dao.iface.EansDao;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.Eans;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;


 	
@Repository
public class EansDaoImpl implements EansDao{
	   private JdbcTemplate jdbcTemplate;

	   private RowMapper<Eans> rwEansMap = new RowMapper<Eans>() {
			public Eans mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new Eans(resultSet.getLong("COD_EAN"),
			    			resultSet.getLong("COD_ARTICULO"), resultSet.getString("FLG_NLU"),
			    			resultSet.getDate("FEC_DESDE_EDI"), resultSet.getDate("FEC_HASTA_EDI"),
			    		    resultSet.getLong("COD_ARTICULO_ANT"), resultSet.getLong("CREATED_BY"),
			    		    resultSet.getDate("CREATION_DATE"),resultSet.getLong("LAST_UPDATED_BY"),
			    		    resultSet.getDate("LAST_UPDATE_DATE"),resultSet.getLong("LAST_UPDATE_LOGIN"),
			    		    resultSet.getLong("TECLE"),resultSet.getLong("TCN")
				);
			}
		};
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<Eans> findAll(Eans eans) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COD_EAN, COD_ARTICULO, FLG_NLU, FEC_DESDE_EDI, "
	    										+ "	FEC_HASTA_EDI, COD_ARTICULO_ANT, CREATED_BY, CREATION_DATE, "
	    										+ " LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, "
	    										+ " TECLE, TCN "
	    										+ " FROM EANS ");
		    
	        if (eans  != null){
	        	if(eans.getCodEan()!=null){
	        		 where.append(" AND COD_EAN = ? ");
		        	 params.add(eans.getCodEan());	        		
	        	}
	        	if(eans.getCodArticulo()!=null ){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(eans.getCodArticulo());	        		
	        	}
	        	if(eans.getFlgNlu()!=null){
	        		 where.append(" AND UPPER(FLG_NLU) = upper(?) ");
		        	 params.add(eans.getFlgNlu());	        		
	        	}
	        	if(eans.getFecDesdeEdi()!=null){
	        		 where.append(" AND TRUNC(FEC_DESDE_EDI) = TRUNC(?) ");
		        	 params.add(eans.getFecDesdeEdi());	        		
	        	}
	        	if(eans.getFecHastaEdi()!=null){
	        		 where.append(" AND TRUNC(FEC_HASTA_EDI) = TRUNC(?) ");
		        	 params.add(eans.getFecHastaEdi());	        		
	        	}
	        	if(eans.getCodArticuloAnt()!=null){
	        		 where.append(" AND COD_ARTICULO_ANT = ? ");
		        	 params.add(eans.getCodArticuloAnt());	        		
	        	}
	        	if(eans.getCretedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(eans.getCretedBy());	        		
	        	}
	        	if(eans.getCreationDate()!=null){
	        		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
		        	 params.add(eans.getCreationDate());	        		
	        	}
	        	if(eans.getLastUpdatedBy()!=null){
	        		 where.append(" AND LAST_UPDATED_BY = ? ");
		        	 params.add(eans.getLastUpdatedBy());	        		
	        	}
	        	if(eans.getLastUpdateDate()!=null){
	        		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
		        	 params.add(eans.getLastUpdateDate());	        		
	        	}
	        	if(eans.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(eans.getLastUpdateLogin());	        		
	        	}
	        	if(eans.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(eans.getTecle());	        		
	        	}
	        	if(eans.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(eans.getTcn());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by COD_ARTICULO ");
			query.append(order);

			List<Eans> lista = null;
		    try {
		    	lista = (List<Eans>) this.jdbcTemplate.query(query.toString(),this.rwEansMap, params.toArray());
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    return lista;
		    
	    }
	    
	    @Override
	    public Long findAllCont(Eans eans) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
	    										+ " FROM EANS ");
	    
	        if (eans  != null){
	        	if(eans.getCodEan()!=null){
	        		 where.append(" AND COD_EAN = ? ");
		        	 params.add(eans.getCodEan());	        		
	        	}
	        	if(eans.getCodArticulo()!=null ){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(eans.getCodArticulo());	        		
	        	}
	        	if(eans.getFlgNlu()!=null){
	        		 where.append(" AND UPPER(FLG_NLU) = upper(?) ");
		        	 params.add(eans.getFlgNlu());	        		
	        	}
	        	if(eans.getFecDesdeEdi()!=null){
	        		 where.append(" AND TRUNC(FEC_DESDE_EDI) = TRUNC(?) ");
		        	 params.add(eans.getFecDesdeEdi());	        		
	        	}
	        	if(eans.getFecHastaEdi()!=null){
	        		 where.append(" AND TRUNC(FEC_HASTA_EDI) = TRUNC(?) ");
		        	 params.add(eans.getFecHastaEdi());	        		
	        	}
	        	if(eans.getCodArticuloAnt()!=null){
	        		 where.append(" AND COD_ARTICULO_ANT = ? ");
		        	 params.add(eans.getCodArticuloAnt());	        		
	        	}
	        	if(eans.getCretedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(eans.getCretedBy());	        		
	        	}
	        	if(eans.getCreationDate()!=null){
	        		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
		        	 params.add(eans.getCreationDate());	        		
	        	}
	        	if(eans.getLastUpdatedBy()!=null){
	        		 where.append(" AND LAST_UPDATED_BY = ? ");
		        	 params.add(eans.getLastUpdatedBy());	        		
	        	}
	        	if(eans.getLastUpdateDate()!=null){
	        		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
		        	 params.add(eans.getLastUpdateDate());	        		
	        	}
	        	if(eans.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(eans.getLastUpdateLogin());	        		
	        	}
	        	if(eans.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(eans.getTecle());	        		
	        	}
	        	if(eans.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(eans.getTcn());	        		
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
	    
	    @Override
		public Long obtenerReferenciaEan14(final Long codigoEan){
			StringBuffer query = new StringBuffer("SELECT PK_MIS_PROCESOS.f_mis_convertir_dun14_ean13(?) FROM DUAL");
			List<Object> params = new ArrayList<Object>();
			params.add(codigoEan);
			return (Long)this.jdbcTemplate.queryForObject(query.toString(), params.toArray(),Long.class);
		}
}
