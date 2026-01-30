package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.EstadoConvergenciaDao;
import es.eroski.misumi.model.DetalladoSIALista;
import es.eroski.misumi.model.EstadoConvergencia;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class EstadoConvergenciaDaoImpl implements EstadoConvergenciaDao{
	
	 private static Logger logger = Logger.getLogger(EstadoConvergenciaDaoImpl.class);
	 private JdbcTemplate jdbcTemplate;
	 
	 //private static Logger logger = LoggerFactory.getLogger(CcRefCentroDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(CcRefCentroDaoImpl.class);

	 @Autowired
	 public void setDataSource(DataSource dataSourceSIA) {
		 this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	 } 
	    
	 @Override
	 public String consultaEstadoConvergencia(EstadoConvergencia estadoConvergencia) throws Exception {
	    	
		String salida = null;
		
		// Obtención de parámetros de consulta
		final Long p_cod_art = estadoConvergencia.getCodArticulo();
		final Long p_cod_loc = estadoConvergencia.getCodLoc();

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {
	
				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_MISUMI.P_APR_OBT_CONVERGENCIA(?, ?, ?, ?, ?) }");
			                    
						cs.setLong(1, p_cod_loc);
						cs.setLong(2, p_cod_art);
			                    
						cs.registerOutParameter(3, Types.VARCHAR);
						cs.registerOutParameter(4, Types.INTEGER);
						cs.registerOutParameter(5, Types.VARCHAR);
			              
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			
			CallableStatementCallback csCallback = new CallableStatementCallback() {
	
				public Object doInCallableStatement(CallableStatement cs) {
					String ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerEstadoConvergencia(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

	        try {
	        	salida = (String) this.jdbcTemplate.execute(csCreator,csCallback);
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
		
	 private String obtenerEstadoConvergencia(CallableStatement cs, ResultSet rs){
	    	
		String resultado = null;
	   	Long resultadoError = null;
	    	
	   	try{
		    //Obtención del parámetro de error
	    	Integer resultadoCallError = (Integer)cs.getObject(4);
	    	resultadoError = new Long(resultadoCallError.toString());
	    	String descError = (String)cs.getObject(5);

	    	if (new Long("0").equals(resultadoError)){ //Si hay error se ignora el dato de Estado convergencia
		    	//Obtención del parámetro de salida.Obtención del estado de convergencia
	    		resultado = (String)cs.getObject(3);
    		}
       	} catch (Exception e) {
           	e.printStackTrace();
       	}
	   	return resultado;
	 }
}