package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CapraboMotivosNoPedibleDao;
import es.eroski.misumi.model.CapraboMotivoNoPedible;
import es.eroski.misumi.model.CapraboMotivoNoPedibleArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleCentroArt;
import es.eroski.misumi.model.CapraboMotivoNoPedibleMotivo;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
 	
@Repository
public class CapraboMotivosNoPedibleDaoImpl implements CapraboMotivosNoPedibleDao {
	
	 private static Logger logger = Logger.getLogger(CapraboMotivosNoPedibleDaoImpl.class);
	 private JdbcTemplate jdbcTemplate;

	 private static final int POSICION_PARAMETRO_SALIDA_BUSQUEDA = 1;

	 	@Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    }
	 
	    @Override
	    public CapraboMotivoNoPedible findCentroRefTipo(final CapraboMotivoNoPedible capraboMotivoNoPedible) throws Exception  {
	    	//Se crea una estructura de CapraboMotivoNoPedible a partir de los parámetros.
	    	//Únicamente en el parámetro capraboMotivoNoPedible se ha incluido el centro, referencia y tipo de movimiento
	    	CapraboMotivoNoPedible capraboMotivoNoPedibleBusqueda = new CapraboMotivoNoPedible();
	    	
	    	capraboMotivoNoPedibleBusqueda = crearEstructuraCapraboMotivosNoPedibleBusqueda(capraboMotivoNoPedible);
	    	
	    	return this.find(capraboMotivoNoPedibleBusqueda);
	    }
	    
		private CapraboMotivoNoPedible find(final CapraboMotivoNoPedible capraboMotivoNoPedible) throws Exception  {
	    	
			CapraboMotivoNoPedible salida = null;
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {
		                	
		                	//Crear estructura para actualización
		                	STRUCT itemConsulta = crearEstructuraBusquedaMotivos(capraboMotivoNoPedible, con);
		                	
		        	    	cs = con.prepareCall("{call PK_APR_MISUMI_CAPRABO.P_APR_MOTIVOS_NO_PEDIBLE(?) }");
		        	    	cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_REFPEDCAP_REG");
		        	    	cs.setObject(1, itemConsulta);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                CapraboMotivoNoPedible ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerEstructuraBusquedaMotivos(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        try {
		        	salida = (CapraboMotivoNoPedible) this.jdbcTemplate.execute(csCreator,csCallback);
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

		private CapraboMotivoNoPedible crearEstructuraCapraboMotivosNoPedibleBusqueda(CapraboMotivoNoPedible capraboMotivoNoPedible) throws SQLException {
	    	//Se crea una estructura de CapraboMotivoNoPedible a partir de los parámetros de entrada que contienen sólo los
			//campos de búsqueda

			CapraboMotivoNoPedible capraboMotivoNoPedibleBusqueda = new CapraboMotivoNoPedible();
			
			CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = new CapraboMotivoNoPedibleArt();
			capraboMotivoNoPedibleArt.setCodArticulo(capraboMotivoNoPedible.getCodArticuloBusqueda());
			capraboMotivoNoPedibleArt.setTipoMovimiento(capraboMotivoNoPedible.getTipoMovimientoBusqueda());

			List<CapraboMotivoNoPedibleArt> articulos = new ArrayList<CapraboMotivoNoPedibleArt>();
			articulos.add(capraboMotivoNoPedibleArt);
			
			CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = new CapraboMotivoNoPedibleCentroArt();
			capraboMotivoNoPedibleCentroArt.setCodLoc(capraboMotivoNoPedible.getCodLocBusqueda());
			capraboMotivoNoPedibleCentroArt.setArticulos(articulos);

			List<CapraboMotivoNoPedibleCentroArt> datos = new ArrayList<CapraboMotivoNoPedibleCentroArt>();
			datos.add(capraboMotivoNoPedibleCentroArt);
			
			capraboMotivoNoPedibleBusqueda.setDatos(datos);
	    	
	    	return capraboMotivoNoPedibleBusqueda;
	    }

		private STRUCT crearEstructuraBusquedaMotivos(CapraboMotivoNoPedible capraboMotivoNoPedible, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

			int numeroDatos = 0;
			List<CapraboMotivoNoPedibleCentroArt> listaCapraboMotivoNoPedibleCentroArt = null;
			Object[] objectMotivoNoPedible = new Object[3]; 
			if (capraboMotivoNoPedible != null){
				listaCapraboMotivoNoPedibleCentroArt = capraboMotivoNoPedible.getDatos();
				if (listaCapraboMotivoNoPedibleCentroArt != null){
					numeroDatos = listaCapraboMotivoNoPedibleCentroArt.size();
				}
				
				if (numeroDatos > 0){
					Object[] objectTablaDatos = new Object[numeroDatos];
					
					for (int contDatos=0; contDatos<numeroDatos; contDatos++){
						
						CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = listaCapraboMotivoNoPedibleCentroArt.get(contDatos);
	
						//STRUCT APR_R_REFPEDCAP_REG toda la estructura
						//TABLA APR_T_R_REFPEDCAP_DAT
				    	STRUCT motivoNoPedibleCentroArtObjectStruct = crearEstructuraBusquedaMotivosCentroArt(capraboMotivoNoPedibleCentroArt, con); 
				    	
				    	objectTablaDatos[contDatos] = motivoNoPedibleCentroArtObjectStruct;
					}
			    
					ArrayDescriptor datosDescriptor = new ArrayDescriptor("APR_T_R_REFPEDCAP_DAT", conexionOracle);
				    ARRAY arrayDatos = new ARRAY(datosDescriptor, conexionOracle, objectTablaDatos);
				    //Sólo debo informar el campo datos
				    objectMotivoNoPedible[0] = arrayDatos;
				}
			}
	    	StructDescriptor itemDescriptorMotivoNoPedible = StructDescriptor.createDescriptor("APR_R_REFPEDCAP_REG",conexionOracle);
	    	STRUCT itemMotivoNoPedible = new STRUCT(itemDescriptorMotivoNoPedible, conexionOracle, objectMotivoNoPedible);

	    	return itemMotivoNoPedible;
	    }

		private STRUCT crearEstructuraBusquedaMotivosCentroArt(CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

			int numeroArticulos = 0;
			List<CapraboMotivoNoPedibleArt> listaCapraboMotivoNoPedibleArt = null;
			Object[] objectMotivoNoPedibleCentroArt = new Object[2]; 
			if (capraboMotivoNoPedibleCentroArt != null){
				listaCapraboMotivoNoPedibleArt = capraboMotivoNoPedibleCentroArt.getArticulos();
				if (listaCapraboMotivoNoPedibleArt != null){
					numeroArticulos = listaCapraboMotivoNoPedibleArt.size();
				}
				
				if (numeroArticulos > 0){
					Object[] objectTablaArticulos = new Object[numeroArticulos];
					
					for (int contArticulos=0; contArticulos<numeroArticulos; contArticulos++){
						
						CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = listaCapraboMotivoNoPedibleArt.get(contArticulos);
	
						//STRUCT APR_R_REFPEDCAP_DAT toda la estructura
						//TABLA APR_T_R_REFCAP_DAT
				    	STRUCT motivoNoPedibleArtObjectStruct = crearEstructuraBusquedaMotivosArticulos(capraboMotivoNoPedibleArt, con); 
				    	
				    	objectTablaArticulos[contArticulos] = motivoNoPedibleArtObjectStruct;
					}
					ArrayDescriptor datosDescriptor = new ArrayDescriptor("APR_T_R_REFCAP_DAT", conexionOracle);
				    ARRAY arrayDatos = new ARRAY(datosDescriptor, conexionOracle, objectTablaArticulos);
				    //Se informa el codLoc y la lista de articulos
				    objectMotivoNoPedibleCentroArt[1] = arrayDatos;
				}
			    objectMotivoNoPedibleCentroArt[0] = capraboMotivoNoPedibleCentroArt.getCodLoc();
			}
	    	StructDescriptor itemDescriptorMotivoNoPedible = StructDescriptor.createDescriptor("APR_R_REFPEDCAP_DAT",conexionOracle);
	    	STRUCT itemMotivoNoPedibleCentroArt = new STRUCT(itemDescriptorMotivoNoPedible, conexionOracle, objectMotivoNoPedibleCentroArt);

	    	return itemMotivoNoPedibleCentroArt;
	    }

		private STRUCT crearEstructuraBusquedaMotivosArticulos(CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();
			
			int numeroMotivos = 0;
			List<CapraboMotivoNoPedibleMotivo> listaCapraboMotivoNoPedibleMotivo = null;
			Object[] objectMotivoNoPedibleMotivo = new Object[4]; 
			if (capraboMotivoNoPedibleArt != null){
				listaCapraboMotivoNoPedibleMotivo = capraboMotivoNoPedibleArt.getMotivos();
				if (listaCapraboMotivoNoPedibleMotivo != null){
					numeroMotivos = listaCapraboMotivoNoPedibleMotivo.size();
				}
				
				if (numeroMotivos > 0){
					Object[] objectTablaMotivos = new Object[numeroMotivos];
					
					for (int contMotivos=0; contMotivos<numeroMotivos; contMotivos++){
						
						CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo = listaCapraboMotivoNoPedibleMotivo.get(contMotivos);
	
						//STRUCT APR_R_REFCAP_DAT toda la estructura
						//TABLA APR_T_R_MOTCAP_DAT
				    	STRUCT motivoNoPedibleMotivoObjectStruct = crearEstructuraBusquedaMotivosArtMot(capraboMotivoNoPedibleMotivo, con); 
				    	
				    	objectTablaMotivos[contMotivos] = motivoNoPedibleMotivoObjectStruct;
					}
					ArrayDescriptor datosDescriptor = new ArrayDescriptor("APR_T_R_MOTCAP_DAT", conexionOracle);
				    ARRAY arrayMotivos = new ARRAY(datosDescriptor, conexionOracle, objectTablaMotivos);
				    //Se informa codArticulo, tipoMovimiento, pedible y lista de motivos
				    objectMotivoNoPedibleMotivo[3] = arrayMotivos;
				}
			    //Se informa codArticulo, tipoMovimiento, pedible y lista de motivos
				objectMotivoNoPedibleMotivo[0] = capraboMotivoNoPedibleArt.getCodArticulo();
			    objectMotivoNoPedibleMotivo[1] = capraboMotivoNoPedibleArt.getTipoMovimiento();
			    objectMotivoNoPedibleMotivo[2] = capraboMotivoNoPedibleArt.getPedible();
			}
	    	StructDescriptor itemDescriptorMotivoNoPedibleMotivo = StructDescriptor.createDescriptor("APR_R_REFCAP_DAT",conexionOracle);
	    	STRUCT itemMotivoNoPedibleMotivo = new STRUCT(itemDescriptorMotivoNoPedibleMotivo, conexionOracle, objectMotivoNoPedibleMotivo);

	    	return itemMotivoNoPedibleMotivo;
	    }
		

		private STRUCT crearEstructuraBusquedaMotivosArtMot(CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

			Object[] objectMotivoNoPedibleMotivo = new Object[3]; 
			if (capraboMotivoNoPedibleMotivo != null){
				//STRUCT APR_R_MOTCAP_DAT toda la estructura
	    
			    //Se debe informar el tipo, la descripcion y accion
			    objectMotivoNoPedibleMotivo[0] = capraboMotivoNoPedibleMotivo.getTipo();
			    objectMotivoNoPedibleMotivo[1] = capraboMotivoNoPedibleMotivo.getDescripcion();
			    objectMotivoNoPedibleMotivo[2] = capraboMotivoNoPedibleMotivo.getAccion();
			}
	    	StructDescriptor itemDescriptorMotivoNoPedibleMotivo = StructDescriptor.createDescriptor("APR_R_MOTCAP_DAT",conexionOracle);
	    	STRUCT itemMotivoNoPedibleMotivo = new STRUCT(itemDescriptorMotivoNoPedibleMotivo, conexionOracle, objectMotivoNoPedibleMotivo);

	    	return itemMotivoNoPedibleMotivo;
	    }

		private CapraboMotivoNoPedible obtenerEstructuraBusquedaMotivos(CallableStatement cs, ResultSet rs){
	    	
			CapraboMotivoNoPedible capraboMotivoNoPedible = new CapraboMotivoNoPedible();
			
			List<CapraboMotivoNoPedibleCentroArt> listaCapraboMotivoNOPedibleCentroArt = new ArrayList<CapraboMotivoNoPedibleCentroArt>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultadoBusquedaMotivos = (STRUCT)cs.getObject(POSICION_PARAMETRO_SALIDA_BUSQUEDA);
		    	
		    	//Obtención de los datos de la estructura
	            BigDecimal estado = (BigDecimal)estructuraResultadoBusquedaMotivos.getAttributes()[1];
	            String descEstado = (String)estructuraResultadoBusquedaMotivos.getAttributes()[2];
	            
	            //Control de error en la obtención de datos
	            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
	            	//Obtención de los datos de salida
            		ARRAY listaDatos = (ARRAY)estructuraResultadoBusquedaMotivos.getAttributes()[0];
            		if (listaDatos!=null){
	            		rs = listaDatos.getResultSet();
	            		//Recorrido del listado de datos
	                    while (rs.next()) {
	                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
	                    	CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = this.obtenerEstructuraBusquedaMotivosCentroArt(estructuraDatos);
	                    	listaCapraboMotivoNOPedibleCentroArt.add(capraboMotivoNoPedibleCentroArt);
	                    }
            		}            		
	            	
            		capraboMotivoNoPedible.setDatos(listaCapraboMotivoNOPedibleCentroArt);
	            }
	            
	            capraboMotivoNoPedible.setEstado(new Long(estado.toString()));
	            capraboMotivoNoPedible.setDescEstado(descEstado);
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return capraboMotivoNoPedible;
	    }

		private CapraboMotivoNoPedibleCentroArt obtenerEstructuraBusquedaMotivosCentroArt(STRUCT estructuraCentroArt){
	    	
			CapraboMotivoNoPedibleCentroArt capraboMotivoNoPedibleCentroArt = new CapraboMotivoNoPedibleCentroArt();
			
			List<CapraboMotivoNoPedibleArt> listaCapraboMotivoNoPedibleArt = new ArrayList<CapraboMotivoNoPedibleArt>();
	    	
	    	try{
	            
	            Object[] objectCentroArt = estructuraCentroArt.getAttributes();
	            
	            //Obtención de datos de la estructura de base de datos
	            BigDecimal codLoc_BD = ((BigDecimal)objectCentroArt[0]);
				ARRAY listaArticulos = (ARRAY)objectCentroArt[1];
				
	            //Transformación de datos para estructura de CapraboMotivoNoPedibleCentroArt
				Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
				
				capraboMotivoNoPedibleCentroArt.setCodLoc(codLoc);
				
				
        		if (listaArticulos!=null){
        			ResultSet rs = listaArticulos.getResultSet();
            		//Recorrido del listado de articulos
                    while (rs.next()) {
                    	STRUCT estructuraArticulos = (STRUCT) rs.getObject(2);
                    	CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = this.obtenerEstructuraBusquedaMotivosArt(estructuraArticulos);
                    	listaCapraboMotivoNoPedibleArt.add(capraboMotivoNoPedibleArt);
                    }
                    capraboMotivoNoPedibleCentroArt.setArticulos(listaCapraboMotivoNoPedibleArt);
        		}   
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }

			return capraboMotivoNoPedibleCentroArt;
		}
		
		private CapraboMotivoNoPedibleArt obtenerEstructuraBusquedaMotivosArt(STRUCT estructuraArticulo){
	    	
			CapraboMotivoNoPedibleArt capraboMotivoNoPedibleArt = new CapraboMotivoNoPedibleArt();
			
			List<CapraboMotivoNoPedibleMotivo> listaCapraboMotivoNOPedibleMotivo = new ArrayList<CapraboMotivoNoPedibleMotivo>();
	    	
	    	try{
	            
	            Object[] objectArticulo = estructuraArticulo.getAttributes();
	            
	            //Obtención de datos de la estructura de base de datos
	            BigDecimal codArticulo_BD = ((BigDecimal)objectArticulo[0]);
				String tipoMovimiento_BD = (String)objectArticulo[1];
				String pedible_BD = (String)objectArticulo[2];
				ARRAY listaMotivos = (ARRAY)objectArticulo[3];
				
	            //Transformación de datos para estructura de CapraboMotivoNoPedibleArt
				Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
				String tipoMovimiento = tipoMovimiento_BD;
				String pedible = pedible_BD;
				
				capraboMotivoNoPedibleArt.setCodArticulo(codArticulo);
				capraboMotivoNoPedibleArt.setTipoMovimiento(tipoMovimiento);
				capraboMotivoNoPedibleArt.setPedible(pedible);
				
				
        		if (listaMotivos!=null){
        			ResultSet rs = listaMotivos.getResultSet();
            		//Recorrido del listado de motivos
                    while (rs.next()) {
                    	STRUCT estructuraMotivos = (STRUCT) rs.getObject(2);
                    	CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo = this.obtenerEstructuraBusquedaMotivosArtMot(estructuraMotivos);
                    	listaCapraboMotivoNOPedibleMotivo.add(capraboMotivoNoPedibleMotivo);
                    }
                    capraboMotivoNoPedibleArt.setMotivos(listaCapraboMotivoNOPedibleMotivo);
        		}   
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }

			return capraboMotivoNoPedibleArt;
		}

		private CapraboMotivoNoPedibleMotivo obtenerEstructuraBusquedaMotivosArtMot(STRUCT estructuraMotivo){
	    	
			CapraboMotivoNoPedibleMotivo capraboMotivoNoPedibleMotivo = new CapraboMotivoNoPedibleMotivo();
			
	    	try{
	            
	            Object[] objectMotivo = estructuraMotivo.getAttributes();
	            
	            //Obtención de datos de la estructura de base de datos
				String tipo_BD = (String)objectMotivo[0];
				String descripcion_BD = (String)objectMotivo[1];
				String accion_BD = (String)objectMotivo[2];
				
	            //Transformación de datos para estructura de CapraboMotivoNoPedibleMotivo
				String tipo = tipo_BD;
				String descripcion = descripcion_BD;
				String accion = accion_BD;
				
				capraboMotivoNoPedibleMotivo.setTipo(tipo);
				capraboMotivoNoPedibleMotivo.setDescripcion(descripcion);
				capraboMotivoNoPedibleMotivo.setAccion(accion);
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }

			return capraboMotivoNoPedibleMotivo;
		}

}


