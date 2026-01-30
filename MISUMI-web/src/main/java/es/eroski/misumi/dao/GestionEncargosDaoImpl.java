package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.GestionEncargosDao;
import es.eroski.misumi.model.EncargoCliente;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.ExclusionVentasSIA;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class GestionEncargosDaoImpl implements GestionEncargosDao{
	 private JdbcTemplate jdbcTemplate;
	 
	 //private static Logger logger = LoggerFactory.getLogger(GestionEncargosDaoImpl.class);
	 private static Logger logger = Logger.getLogger(GestionEncargosDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
	    private EncargoCliente mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
            
            EncargoCliente encargoCliente = new EncargoCliente();
            encargoCliente.setLocalizador(objectInfo[0]!=null?((BigDecimal)objectInfo[0]).longValue():null);
            encargoCliente.setCodLoc(objectInfo[1]!=null?((BigDecimal)objectInfo[1]).longValue():null);
            encargoCliente.setCodArticulo(objectInfo[18]!=null?((BigDecimal)objectInfo[18]).longValue():null);
            encargoCliente.setCodError(((BigDecimal)objectInfo[40]).longValue());
            encargoCliente.setDescError((String) objectInfo[41]);

            encargoCliente.setContactoCentro((String) objectInfo[2]);
            encargoCliente.setTelefonoCentro((String) objectInfo[3]);
            encargoCliente.setNombreCliente((String) objectInfo[4]);
            encargoCliente.setApellidoCliente((String) objectInfo[5]);
            encargoCliente.setTelefonoCliente((String) objectInfo[6]);
            encargoCliente.setTipoEncargo((String) objectInfo[8]);
            encargoCliente.setFechaVenta(objectInfo[9]!=null?((Date)objectInfo[9]):null);
            encargoCliente.setFechaVentaModificada(objectInfo[10]!=null?((Date)objectInfo[10]):null);
            encargoCliente.setEspecificacion((String)objectInfo[20]);
            encargoCliente.setPesoDesde(objectInfo[21]!=null?((BigDecimal)objectInfo[21]).doubleValue():null);
            encargoCliente.setPesoHasta(objectInfo[22]!=null?((BigDecimal)objectInfo[22]).doubleValue():null);
            encargoCliente.setCantidadEncargo(objectInfo[28]!=null?((BigDecimal)objectInfo[28]).doubleValue():null);
            
			return encargoCliente; 
		}
	    
		@Override
		public EncargosClienteLista gestionEncargos(final List<EncargoCliente> listaActualizacion) throws Exception {
	    	
			EncargosClienteLista salida = null;
	    	
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {
		                	
		                	//Crear estructura para actualización
		                	STRUCT itemConsulta = crearEstructuraGestionEncargos(listaActualizacion, con);
		                	
		        	    	cs = con.prepareCall("{call PK_APR_ENCARGOS_MISUMI.P_APR_ABM_ENCARGOS(?) }");
		        	    	cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_ENCARGO_REG");
		        	    	cs.setObject(1, itemConsulta);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		            	EncargosClienteLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoActualizacionEncargos(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        
		        try {
		        	salida = (EncargosClienteLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	
		private EncargosClienteLista obtenerResultadoActualizacionEncargos(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraEncargos(cs, rs, 1);
	    }

		private EncargosClienteLista obtenerEstructuraEncargos(CallableStatement cs, ResultSet rs, int idParametroResultado){
	    	
	    	EncargosClienteLista encargosLista = new EncargosClienteLista();
	    	List<EncargoCliente> listaEncargos = new ArrayList<EncargoCliente>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	logger.info(estructuraResultado.dump());
		    	//Obtención de los datos de la estructura
	            String descEstado = (String)estructuraResultado.getAttributes()[3];
	            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[2];
	            
	            //Control de error en la obtenci�n de datos
	            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
	            	//Obtención de los datos de salida
            		ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
            		if (listaDatos!=null){
	            		rs = listaDatos.getResultSet();
	                    int rowNum = 0;
	            		//Recorrido del listado de datos
	                    while (rs.next()) {
	                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
	                        EncargoCliente encargoCliente = this.mapRow(estructuraDatos, rowNum);
	                        listaEncargos.add(encargoCliente);
	                        rowNum++;
	                    }
            		}            		
	            	if(null != estado){
	            		encargosLista.setEstado(new Long(estado.toString()));
	            	}
            		encargosLista.setDescEstado(descEstado);
            		encargosLista.setDatos(listaEncargos);
	            }
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return encargosLista;
	    }

		private STRUCT crearEstructuraGestionEncargos(List<EncargoCliente> listEncargos, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

			Object[] objectTabla = new Object[listEncargos.size()];
			for (int i=0; i<listEncargos.size(); i++){
				
				EncargoCliente encargoCliente = listEncargos.get(i);
	        	
	        	Object[] objectInfo = new Object[46];
	        	
	        	//Sólo se informan los datos necesarios
	        	objectInfo[0] = (encargoCliente.getLocalizador()!=null?new BigDecimal(encargoCliente.getLocalizador()):null);
				objectInfo[1] = (encargoCliente.getCodLoc()!=null?new BigDecimal(encargoCliente.getCodLoc()):null);
				objectInfo[2] = encargoCliente.getContactoCentro();
				objectInfo[3] = encargoCliente.getTelefonoCentro();
				objectInfo[4] = encargoCliente.getNombreCliente();
				objectInfo[5] = encargoCliente.getApellidoCliente();
				objectInfo[6] = encargoCliente.getTelefonoCliente();
				objectInfo[7] = (encargoCliente.getFechaHoraEncargo()!=null?new Timestamp(encargoCliente.getFechaHoraEncargo().getTime()):null);
				objectInfo[8] = encargoCliente.getTipoEncargo();
				objectInfo[9] = (encargoCliente.getFechaVenta()!=null?new Timestamp(encargoCliente.getFechaVenta().getTime()):null);
				objectInfo[10] = (encargoCliente.getFechaVentaModificada()!=null?new Timestamp(encargoCliente.getFechaVentaModificada().getTime()):null);
				objectInfo[11] = (encargoCliente.getFechaInferior()!=null && encargoCliente.getFechaInferior().booleanValue())?"S":"N";
				objectInfo[17] = (encargoCliente.getCodArticulo()!=null?new BigDecimal(encargoCliente.getCodArticulo()):null);
				objectInfo[18] = (encargoCliente.getCodArticulo()!=null?new BigDecimal(encargoCliente.getCodArticulo()):null);
				objectInfo[20] = encargoCliente.getEspecificacion();
				objectInfo[21] = (encargoCliente.getPesoDesde()!=null?new BigDecimal(encargoCliente.getPesoDesde()):null);
				objectInfo[22] = (encargoCliente.getPesoHasta()!=null?new BigDecimal(encargoCliente.getPesoHasta()):null);
				objectInfo[23] = (encargoCliente.getConfirmarEspecificacion()!=null && encargoCliente.getConfirmarEspecificacion().booleanValue())?"S":"N";
				objectInfo[24] = (encargoCliente.getFaltaReferencia()!=null && encargoCliente.getFaltaReferencia().booleanValue())?"S":"N";
				objectInfo[26] = encargoCliente.getFlgEspec();
				objectInfo[27] = encargoCliente.getConfirmarPrecio();
				objectInfo[28] = (encargoCliente.getCantidadEncargo()!=null?new BigDecimal(encargoCliente.getCantidadEncargo()):null);
				objectInfo[33] = encargoCliente.getEstado();
			
				//TABLA apr_t_r_sfmcap_dat 
				//STRUCT apr_r_sfmcap_REG toda la estructura
				//STRUCT apr_r_sfmcap_dat registro 
				
				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_ENCARGO_DAT",conexionOracle);
		    	STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
		    	
		    	objectTabla[i] = itemObjectStruct;
			}	
		    
			Object[] objectConsulta = new Object[4]; //Tiene 3 campos pero sólo nos interesa la lista
			ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_ENCARGO_DAT", conexionOracle);
		    ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);
	    	
	    	objectConsulta[0] = array;
	    	
	    	StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_ENCARGO_REG",conexionOracle);
	    	STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);
	    	
	    	return itemConsulta;
	    }


}
