package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ValidarReferenciaEncargosDao;
import es.eroski.misumi.model.EncargosClientePlataformaLista;
import es.eroski.misumi.model.ValidarReferenciaEncargo;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class ValidarReferenciaEncargosDaoImpl implements ValidarReferenciaEncargosDao {
	 private JdbcTemplate jdbcTemplate;
	 
	 private static Logger logger = Logger.getLogger(ValidarReferenciaEncargosDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    

		@Override
		public ValidarReferenciaEncargo validarReferenciaEncargos(final ValidarReferenciaEncargo validarReferenciaEncargo) throws Exception {
	    	
			ValidarReferenciaEncargo salida = null;
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_ENCARGOS_MISUMI.P_APR_VALIDAR_REFERENCIA(?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, validarReferenciaEncargo.getCodCentro().intValue());
		                    cs.setLong(2, validarReferenciaEncargo.getCodReferencia());
		                    cs.setString(3, validarReferenciaEncargo.getGenerico());

		                    cs.registerOutParameter(3, Types.VARCHAR);
		                    cs.registerOutParameter(4, OracleTypes.STRUCT, "APR_R_VALIDAR_REF_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {

		            	ValidarReferenciaEncargo ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoValidarReferenciaEncargo(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        
		        try {
		        	salida = (ValidarReferenciaEncargo) this.jdbcTemplate.execute(csCreator,csCallback);
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
	    
		
		private ValidarReferenciaEncargo obtenerResultadoValidarReferenciaEncargo(CallableStatement cs, ResultSet rs){
	    	return obtenerResultadoValidarReferenciaEncargo(cs, rs, 3, 4);
	    }

		private ValidarReferenciaEncargo obtenerResultadoValidarReferenciaEncargo(CallableStatement cs, ResultSet rs, int idParametroGenerica, int idParametroResultado){
	    	
			ValidarReferenciaEncargo validarReferencia = new ValidarReferenciaEncargo();
	    	
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	String esGenerica = (String)cs.getString(idParametroGenerica);
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	logger.info(estructuraResultado.dump());
		    	//Obtención de los datos de la estructura
	            validarReferencia.setCodCentro(((BigDecimal) estructuraResultado.getAttributes()[0]).longValue()); 
	            validarReferencia.setCodReferencia(((BigDecimal) estructuraResultado.getAttributes()[1]).longValue());
	            validarReferencia.setCodError(((BigDecimal) estructuraResultado.getAttributes()[9]).intValue());
				validarReferencia.setDescError((String) estructuraResultado.getAttributes()[10]);
				validarReferencia.setGenerico(esGenerica);
	           // MISUMI-323
				validarReferencia.setTipoReferencia((String) estructuraResultado.getAttributes()[11]);	
	            
	            //Control de error en la obtención de datos
	            if (validarReferencia.getCodError().equals(0)){ //El proceso se ha ejecutado correctamente
	            	if (null != estructuraResultado.getAttributes()[3]){
	            		validarReferencia.setUnidadesCaja(((BigDecimal) estructuraResultado.getAttributes()[3]).doubleValue());
	            	}
		            validarReferencia.setPlataforma(((BigDecimal) estructuraResultado.getAttributes()[4]).longValue());
		            validarReferencia.setFechaEntrega((Date) estructuraResultado.getAttributes()[5]);
		            validarReferencia.setFlgEspec((String) estructuraResultado.getAttributes()[7]);
	            	//Obtención de los datos de salida
	            	List<Date> fechasVenta = new ArrayList<Date>();
            		ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[8];
            		if (listaDatos!=null && listaDatos.length() > 0){
	            		rs = listaDatos.getResultSet();
	            		//Recorrido del listado de datos
	            		
	                    while (rs.next()) {
	                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
	                    	//bd.toString();
	                    	fechasVenta.add((Date) estructuraDatos.getAttributes()[0]);
	                    	//System.out.println(bd.toString());
	                    }
	                    validarReferencia.setFechasVenta(fechasVenta);
            		}            		
	            	
	            }
        	} catch (Exception e) {
            	e.printStackTrace();
            }
	    
            return validarReferencia;
	    }
		
		
}
