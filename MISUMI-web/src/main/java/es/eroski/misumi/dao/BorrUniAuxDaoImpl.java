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

import es.eroski.misumi.dao.iface.BorrUniAuxDao;
import es.eroski.misumi.model.BorrUniAux;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class BorrUniAuxDaoImpl implements BorrUniAuxDao{
	 private JdbcTemplate jdbcTemplate;
	 
	 //private static Logger logger = Logger.getLogger(BorrUniAuxDaoImpl.class);
	 private RowMapper<BorrUniAux> rwBorrUniAuxMap = new RowMapper<BorrUniAux>() {
			public BorrUniAux mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new BorrUniAux(resultSet.getLong("SEQ_BORR_UNI_AUX"),resultSet.getLong("FAMILIA"),
			    		    resultSet.getLong("ORDEN_FAM"), resultSet.getString("NOM_TABLA"),
			    		    resultSet.getString("FECHA_REF"),resultSet.getLong("NUM_DIAS"),
			    		    resultSet.getString("WHERE_PLUS"), resultSet.getString("FLG_ACTIVO"), resultSet.getString("OBSERVACIONES"),
			    		    resultSet.getLong("CREATED_BY"), resultSet.getDate("CREATION_DATE"), resultSet.getLong("LAST_UPDATED_BY"), 
			    		    resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getLong("LAST_UPDATE_LOGIN"), resultSet.getLong("TECLE"), 
			    		    resultSet.getLong("TCN")
				    );
			}

	 };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<BorrUniAux> findAll(BorrUniAux borrUniAux, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT SEQ_BORR_UNI_AUX, FAMILIA, ORDEN_FAM, NOM_TABLA, " +
	    										  "		   FECHA_REF, NUM_DIAS, WHERE_PLUS, FLG_ACTIVO, " +
	    										  "	  	   OBSERVACIONES, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, " +
	    										  "        LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN " + 
	    										  " FROM BORR_UNI_AUX ");
	    
	        if (borrUniAux  != null){
	        	if(borrUniAux.getSeqBorrUniAux()!=null){
	        		 where.append(" AND SEQ_BORR_UNI_AUX = ? ");
		        	 params.add(borrUniAux.getSeqBorrUniAux());	        		
	        	}
	        	if(borrUniAux.getFamilia()!=null){
	        		 where.append(" AND FAMILIA = ? ");
		        	 params.add(borrUniAux.getFamilia());	        		
	        	}
	        	if(borrUniAux.getOrdenFam()!=null){
	        		 where.append(" AND ORDEN_FAM = ? ");
		        	 params.add(borrUniAux.getOrdenFam());	        		
	        	}
	        	if(borrUniAux.getNomTabla()!=null){
	        		 where.append(" AND UPPER(NOM_TABLA) = upper(?) ");
		        	 params.add(borrUniAux.getNomTabla());	        		
	        	}
	        	if(borrUniAux.getFechaRef()!=null){
	        		 where.append(" AND UPPER(FECHA_REF) = upper(?) ");
		        	 params.add(borrUniAux.getFechaRef());	        		
	        	}
	        	if(borrUniAux.getNumDias()!=null){
	        		 where.append(" AND NUM_DIAS = ? ");
		        	 params.add(borrUniAux.getNumDias());	        		
	        	}
	        	if(borrUniAux.getWherePlus()!=null){
	        		 where.append(" AND UPPER(WHERE_PLUS) = upper(?) ");
		        	 params.add(borrUniAux.getWherePlus());	        		
	        	}
	        	if(borrUniAux.getFlgActivo()!=null){
	        		 where.append(" AND UPPER(FLG_ACTIVO) = upper(?) ");
		        	 params.add(borrUniAux.getFlgActivo());	        		
	        	}
	        	if(borrUniAux.getObservaciones()!=null){
	        		 where.append(" AND UPPER(OBSERVACIONES) = upper(?) ");
		        	 params.add(borrUniAux.getObservaciones());	        		
	        	}
	        	if(borrUniAux.getCreatedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(borrUniAux.getCreatedBy());	        		
	        	}
	        	if(borrUniAux.getCreationDate()!=null){
	        		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
		        	 params.add(borrUniAux.getCreationDate());	        		
	        	}
	        	if(borrUniAux.getLastUpdatedBy()!=null){
	        		 where.append(" AND LAST_UPDATED_BY = ? ");
		        	 params.add(borrUniAux.getLastUpdatedBy());	        		
	        	}
	        	if(borrUniAux.getLastUpdateDate()!=null){
	        		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
		        	 params.add(borrUniAux.getLastUpdateDate());	        		
	        	}
	        	if(borrUniAux.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(borrUniAux.getLastUpdateLogin());	        		
	        	}
	        	if(borrUniAux.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(borrUniAux.getTecle());	        		
	        	}
	        	if(borrUniAux.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(borrUniAux.getTcn());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" order by " + pagination.getSort() + " "
							+ pagination.getAscDsc());
					query.append(order);
				}
			} else{
					String campoOrdenacion = "SEQ_BORR_UNI_AUX";
					order.append(" order by " + campoOrdenacion + " asc ");	
					query.append(order);
			}

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

			
			List<BorrUniAux> lista = null;
			try {
			    lista =  (List<BorrUniAux>) this.jdbcTemplate.query(query.toString(),this.rwBorrUniAuxMap, params.toArray());
			    
		    } catch (Exception e){
				
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
			
			return lista;
	    
	    }
	   
}
