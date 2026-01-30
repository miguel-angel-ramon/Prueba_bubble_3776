package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CcRefCentroDao;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class CcRefCentroDaoImpl implements CcRefCentroDao{
	 private static Logger logger = Logger.getLogger(CcRefCentroDaoImpl.class);
	 private JdbcTemplate jdbcTemplate;
	 private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CC = 4;
	 private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_CC_ERROR = 5;
	 
	 //private static Logger logger = LoggerFactory.getLogger(CcRefCentroDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(CcRefCentroDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
		@Override
		public Long consultaCc(CcRefCentro ccRefCentro) throws Exception {
	    	
	    	Long salida = null;
	    	//Obtención de parámetros de consulta
	    	final Long p_cod_art_formlog = ccRefCentro.getCodArticulo();
	    	final Long p_cod_loc = ccRefCentro.getCodLoc();
	    	final Date p_fecha = ccRefCentro.getFecha();
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_MISUMI.P_APR_OBT_CC_REF_CENTRO(?, ?, ?, ?, ?) }");
		                    
		                    cs.setLong(1, p_cod_art_formlog);
		                    cs.setLong(2, p_cod_loc);
		                    cs.setDate(3, new java.sql.Date(p_fecha.getTime()));

		                    cs.registerOutParameter(4, Types.INTEGER);
		                    cs.registerOutParameter(5, Types.INTEGER);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                Long ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoConsultaCc(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        
		        try {
		        	salida = (Long) this.jdbcTemplate.execute(csCreator,csCallback);
				} catch (Exception e) {
					logger.error("#####################################################");
					logger.error( StackTraceManager.getStackTrace(e));
					logger.error("#####################################################");
				}
		        

			}catch (Exception e) {
				e.printStackTrace();
			}
			
		    return salida;
	    }
		
		private Long obtenerResultadoConsultaCc(CallableStatement cs, ResultSet rs){
	    	
	    	Long resultado = null;
	    	Long resultadoError = null;
	    	
	    	try{
		    	//Obtención del parámetro de error
	    		Integer resultadoCallError = (Integer)cs.getObject(POSICION_PARAMETRO_SALIDA_CONSULTA_CC_ERROR);
	    		resultadoError = new Long(resultadoCallError.toString());
	    		
	    		if (new Long("0").equals(resultadoError)){ //Si hay error se ignora el dato de CC
		    		//Obtención del parámetro de salida
	    			Integer resultadoCall = (Integer)cs.getObject(POSICION_PARAMETRO_SALIDA_CONSULTA_CC);
		    		resultado = new Long(resultadoCall.toString());
	    		}

        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return resultado;
	    }
}
