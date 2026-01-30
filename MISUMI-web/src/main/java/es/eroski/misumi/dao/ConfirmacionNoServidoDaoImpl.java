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

import es.eroski.misumi.dao.iface.ConfirmacionNoServidoDao;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class ConfirmacionNoServidoDaoImpl implements ConfirmacionNoServidoDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(ConfirmacionNoServidoDaoImpl.class);
	

	 	@Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	 
		private RowMapper<StockNoServido> rwNSRMap = new RowMapper<StockNoServido>() {
			public StockNoServido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new StockNoServido(
			    		resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"),
			    		resultSet.getDate("FECHA_NSR"), resultSet.getFloat("UNI_NO_SERV"),
			    		resultSet.getLong("MOTIVO"), resultSet.getString("MOTIVODES"),
			    		resultSet.getLong("COD_PLAT"), resultSet.getString("CODPLATDES")
				    );
			}
		};		
		
		
		private RowMapper<StockNoServido> rwNSRDDMMYYYYMap = new RowMapper<StockNoServido>() {
			public StockNoServido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new StockNoServido(
			    		resultSet.getFloat("UNI_NO_SERV"), resultSet.getLong("MOTIVO"), 
			    		resultSet.getString("MOTIVODES"), resultSet.getDate("FECHA_PREVIS_ENT"), 
			    		resultSet.getString("FECHA_PREVIS_ENT_DDMMYYYY")
				    );
			}
		};		
		
		
		private String getMappedField (String fieldName) {
		   	  if (fieldName.toUpperCase().equals("UNINOSERV")){
			      return "UNI_NO_SERV";
		  	  }else if(fieldName.toUpperCase().equals("FECHANSR")){
		  	      return "FECHA_NSR";
		  	  }else if(fieldName.toUpperCase().equals("CODPLAT")){
		  	      return "COD_PLAT";
		  	  }else if(fieldName.toUpperCase().equals("FECHAPREVISENT")){
		  	      return "FECHA_PREVIS_ENT";
		  	  }else {
		  	      return fieldName;
		  	  }
	  	}
		
	    
	    @Override
	    public List<StockNoServido> findAllLastDays(StockNoServido nsr, int lastDays, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
    	
	    	StringBuffer query = new StringBuffer(" SELECT C.COD_CENTRO, C.COD_ART, C.FECHA_NSR, C.UNI_NO_SERV/V.UNI_CAJA_SERV UNI_NO_SERV, " +
	    			                              "        C.MOTIVO, C.MOTIVO || ' - ' || NVL(FM.DESCRIPCION,NVL(F.DESCRIPCION,'')) MOTIVODES,  C.COD_PLAT, NULL CODPLATDES " +
	    			                              " FROM CONFIRMACION_NO_SERVIDO C, FALTAS F,FALTAS_MISUMI FM, V_SURTIDO_TIENDA V ");	
	    	
	    	where.append(" WHERE C.MOTIVO = F.COD_FALTA (+) " +
	    				 "	 AND C.MOTIVO = FM.COD_FALTA (+)" + 
	    				 "	 AND C.COD_CENTRO = ? AND C.COD_ART = ? " +
	    			     "   AND C.FECHA_NSR BETWEEN (SYSDATE - ?) AND (SYSDATE - 1) " +
	    			     "   AND C.COD_CENTRO = V.COD_CENTRO " +
	    			     "   AND C.COD_ART = V.COD_ART");
	    	
	        if (nsr  != null){
	        	params.add(nsr.getCodCentro());
	        	params.add(nsr.getCodArt());
	        	params.add(lastDays);
	        }
	        
	        query.append(where);
	               			
	        if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
					query.append(order);
				}
				query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
			}         
	        
			List<StockNoServido> nsrLista = null;	
			try {
				nsrLista = (List<StockNoServido>) this.jdbcTemplate.query(query.toString(),this.rwNSRMap, params.toArray()); 
	
		    } catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			    return nsrLista;
	    }
	    
		@Override
		public List<StockNoServido> findAllNoServidos(
				SeguimientoCampanas seguimientoCampanas, List<Long> listaReferencias, Pagination pagination)
				throws Exception {

			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();

	    	StringBuffer query = new StringBuffer(" SELECT C.FECHA_PREVIS_ENT, TO_CHAR(C.FECHA_PREVIS_ENT, 'DDMMYYYY') FECHA_PREVIS_ENT_DDMMYYYY, " +
	    										  "        C.MOTIVO, C.MOTIVO || ' - ' || NVL(FM.DESCRIPCION,NVL(F.DESCRIPCION,'')) MOTIVODES, sum(NVL(C.UNI_NO_SERV,0)) UNI_NO_SERV" +
                    							  " FROM CONFIRMACION_NO_SERVIDO C, FALTAS F, FALTAS_MISUMI FM ");	
	    
	    	where.append(" WHERE C.MOTIVO = F.COD_FALTA(+) AND  C.MOTIVO = FM.COD_FALTA(+) ");

	    	if (seguimientoCampanas  != null){
	        	if(seguimientoCampanas.getCodCentro()!=null){
	        		 where.append(" AND C.COD_CENTRO = ? ");
		        	 params.add(seguimientoCampanas.getCodCentro());	        		
	        	}
	        	if(seguimientoCampanas.getFechaInicioDDMMYYYY()!=null && seguimientoCampanas.getFechaFinDDMMYYYY()!=null){
		       		 where.append(" AND C.FECHA_PREVIS_ENT BETWEEN (TO_DATE(?, 'DDMMYYYY')) AND (TO_DATE(?, 'DDMMYYYY')) ");
			         params.add(seguimientoCampanas.getFechaInicioDDMMYYYY());
			         params.add(seguimientoCampanas.getFechaFinDDMMYYYY());	        		
		       	}
	        	if(seguimientoCampanas.getCodArt()!=null){
	        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			    	referencias.append(seguimientoCampanas.getCodArt());
			    	if (null != listaReferencias){
			    		for(Long referencia : listaReferencias){
			    			referencias.append(", ").append(referencia);
			    		}
			    	}
		     		 where.append(" AND C.COD_ART IN ( ").append(referencias).append(" )");        		
		       	}
	        }
	        
	        query.append(where);
	        
	    	StringBuffer groupby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	groupby.append(" GROUP BY C.FECHA_PREVIS_ENT, C.MOTIVO, F.DESCRIPCION, FM.DESCRIPCION ");
	        query.append(groupby);

	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	        if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
				}
				query.append(order);
				query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
			}         
			else {
				order.append(" ORDER BY C.FECHA_PREVIS_ENT asc");
				query.append(order);
			}

			List<StockNoServido> confirmacionNoServidoLista = null;
			try {
				confirmacionNoServidoLista = (List<StockNoServido>) this.jdbcTemplate.query(query.toString(),this.rwNSRDDMMYYYYMap, params.toArray()); 
	
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    	return confirmacionNoServidoLista;

		   
		}

		@Override
		public Long findAllNoServidosCont(SeguimientoCampanas seguimientoCampanas,
				List<Long> listaReferencias) throws Exception {

			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + 
												  " FROM ( " +
	    										  "     SELECT C.FECHA_PREVIS_ENT, C.MOTIVO, F.DESCRIPCION " + 
	    										  "     FROM CONFIRMACION_NO_SERVIDO C, FALTAS F, FALTAS_MISUMI FM ");

	    	where.append(" WHERE C.MOTIVO = F.COD_FALTA(+) AND C.MOTIVO = FM.COD_FALTA(+) ");

	        if (seguimientoCampanas  != null){
	        	if(seguimientoCampanas.getCodCentro()!=null){
	        		 where.append(" AND C.COD_CENTRO = ? ");
		        	 params.add(seguimientoCampanas.getCodCentro());	        		
	        	}
	        	if(seguimientoCampanas.getFechaInicioDDMMYYYY()!=null && seguimientoCampanas.getFechaFinDDMMYYYY()!=null){
		       		 where.append(" AND C.FECHA_PREVIS_ENT BETWEEN (TO_DATE(?, 'DDMMYYYY')) AND (TO_DATE(?, 'DDMMYYYY')) ");
			         params.add(seguimientoCampanas.getFechaInicioDDMMYYYY());
			         params.add(seguimientoCampanas.getFechaFinDDMMYYYY());	        		
		       	}
	        	if(seguimientoCampanas.getCodArt()!=null){
	        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			    	referencias.append(seguimientoCampanas.getCodArt());
			    	if (null != listaReferencias){
			    		for(Long referencia : listaReferencias){
			    			referencias.append(", ").append(referencia);
			    		}
			    	}
		     		 where.append(" AND C.COD_ART IN ( ").append(referencias).append(" )");        		
		       	}
	        }

	        query.append(where);
	        
	    	StringBuffer groupby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	groupby.append(" GROUP BY C.FECHA_PREVIS_ENT, C.MOTIVO, F.DESCRIPCION, FM.DESCRIPCION ");
	        query.append(groupby);
	        
	        query.append(" ) ");

	        Long cont = null;
		    try {
		    	cont =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		        
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    	return cont;
			}
}


