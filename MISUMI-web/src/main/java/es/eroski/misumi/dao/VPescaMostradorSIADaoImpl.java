package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VPescaMostradorSIADao;
import es.eroski.misumi.model.PescaPedirHoy;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class VPescaMostradorSIADaoImpl implements VPescaMostradorSIADao {

	private static Logger logger = Logger.getLogger(CapraboMotivosNoPedibleDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	private static final int POSICION_PARAMETRO_SALIDA_BUSQUEDA = 2;
	private static final int POSICION_PARAMETRO_SALIDA_COD_ERROR = 3;
	private static final int POSICION_PARAMETRO_SALIDA_DESC_ERROR = 4;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	@Override
	public PescaPedirHoy findPescaPedirHoy(PescaPedirHoy pescaPedirHoy) throws Exception {
		
    	return this.find(pescaPedirHoy.getCodCentro(), pescaPedirHoy.getListaPesca());
	}
	
	private PescaPedirHoy find(final Long codCentro, final List<VPescaMostrador> listaPesca) throws Exception  {
    	
		PescaPedirHoy salida = null;
		
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {
	                	
	        	    	cs = con.prepareCall("{call PK_APR_MISUMI_2.P_APR_REF_PESCA_PEDIR_HOY(?,?,?,?) }");
	        	    	
	        	    	//1: Long: Parámetro de entrada: código de centro
						cs.setLong(1, codCentro);
						
	        	    	//2: APR_R_REF_PESCA_REG: Parámetro de entrada y salida: array de códigos de artículo
	                	STRUCT itemConsulta = crearEstructuraRefPescaReg(listaPesca, con); //Crear estructura (array)
	        	    	cs.registerOutParameter(2, OracleTypes.STRUCT, "APR_R_REF_PESCA_REG");
	        	    	cs.setObject(2, itemConsulta);

	        	    	//3: String: Parámetro de salida: código de error
						cs.registerOutParameter(3,Types.INTEGER);
						
	        	    	//4: String: Parámetro de salida: descripción del error
						cs.registerOutParameter(4,Types.VARCHAR);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	PescaPedirHoy ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerEstructuraPescaPedirHoy(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	        try {
	        	salida = (PescaPedirHoy) this.jdbcTemplate.execute(csCreator,csCallback);
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

	private STRUCT crearEstructuraRefPescaReg(List<VPescaMostrador> listaPesca, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();
		
		int numeroPesca = 0;
		Object[] objectPescaPedirHoy = new Object[1]; 
		if (listaPesca != null){
			numeroPesca = listaPesca.size();
		}
		
		if (numeroPesca > 0){
			Object[] objectTablaPesca = new Object[numeroPesca];
			
			for (int contPesca=0; contPesca<numeroPesca; contPesca++){
				
				VPescaMostrador pesca = listaPesca.get(contPesca);

				//STRUCT APR_R_REF_PESCA_DAT toda la estructura
				//TABLA APR_T_R_REF_PESCA_DAT
		    	STRUCT pescaPedirHoyObjectStruct = crearEstructuraRefPescaDat(pesca, con); 
		    	
		    	objectTablaPesca[contPesca] = pescaPedirHoyObjectStruct;
			}
			ArrayDescriptor datosDescriptor = new ArrayDescriptor("APR_T_R_REF_PESCA_DAT", conexionOracle);
		    ARRAY arrayPesca = new ARRAY(datosDescriptor, conexionOracle, objectTablaPesca);
		    //Se informa codArticulo
		    objectPescaPedirHoy[0] = arrayPesca;
		}
    	StructDescriptor itemDescriptorPescaPedirHoy = StructDescriptor.createDescriptor("APR_R_REF_PESCA_REG",conexionOracle);
    	STRUCT itemPescaPedirHoy = new STRUCT(itemDescriptorPescaPedirHoy, conexionOracle, objectPescaPedirHoy);

    	return itemPescaPedirHoy;
    }
	
	private STRUCT crearEstructuraRefPescaDat(VPescaMostrador pesca, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectRefPescaDat = new Object[1]; 
		if (pesca != null){
			//STRUCT APR_R_REF_PESCA_DAT toda la estructura
    
		    //Se debe informar el código de artículo
		    objectRefPescaDat[0] = pesca.getCodArt();
		}
    	StructDescriptor itemDescriptorMotivoNoPedibleMotivo = StructDescriptor.createDescriptor("APR_R_REF_PESCA_DAT",conexionOracle);
    	STRUCT itemRefPescaDat = new STRUCT(itemDescriptorMotivoNoPedibleMotivo, conexionOracle, objectRefPescaDat);

    	return itemRefPescaDat;
	}

	private PescaPedirHoy obtenerEstructuraPescaPedirHoy(CallableStatement cs, ResultSet rs){
    	
		PescaPedirHoy pescaPedirHoyResultado = new PescaPedirHoy();
		
		List<VPescaMostrador> listaPesca = new ArrayList<VPescaMostrador>();
		
    	try{
	    	//Obtención del parámetro de salida
	    	STRUCT estructuraResultadoBusquedaPescaPedirHoy = (STRUCT)cs.getObject(POSICION_PARAMETRO_SALIDA_BUSQUEDA);
	    	
	    	//Obtención de los datos de la estructura
            Integer codError = (Integer)cs.getObject(POSICION_PARAMETRO_SALIDA_COD_ERROR);
            String descError = (String)cs.getObject(POSICION_PARAMETRO_SALIDA_DESC_ERROR);
            
            //Control de error en la obtención de datos
            if (new Integer("0").equals(codError)){ //El proceso se ha ejecutado correctamente
            	//Obtención de los datos de salida
        		ARRAY listaDatos = (ARRAY)estructuraResultadoBusquedaPescaPedirHoy.getAttributes()[0];
        		if (listaDatos!=null){
            		rs = listaDatos.getResultSet();
            		//Recorrido del listado de datos
                    while (rs.next()) {
                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
                    	VPescaMostrador pesca = this.obtenerEstructuraBusquedaPescaPedirHoy(estructuraDatos);
                    	listaPesca.add(pesca);
                    }
        		}            		
            }
            
            pescaPedirHoyResultado.setListaPesca(listaPesca);
            pescaPedirHoyResultado.setCodError(new Long(codError.toString()));
            pescaPedirHoyResultado.setDescError(descError);
        	
    	} catch (Exception e) {
        	e.printStackTrace();
        //} catch (SQLException e) {
        //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
        //} catch (ClassNotFoundException e) {
        //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
        //}	
        }
    
        return pescaPedirHoyResultado;
    }

	private VPescaMostrador obtenerEstructuraBusquedaPescaPedirHoy(STRUCT estructuraCentroArt){
    	
		VPescaMostrador pesca = new VPescaMostrador();
		
    	try{
            
            Object[] objectCentroArt = estructuraCentroArt.getAttributes();
            
            //Obtención de datos de la estructura de base de datos
            BigDecimal codArt_BD = ((BigDecimal)objectCentroArt[0]);
			
            //Transformación de datos para estructura de VPescaMostrador
			Long codArt = ((codArt_BD != null && !("".equals(codArt_BD.toString())))?new Long(codArt_BD.toString()):null);
			
			pesca.setCodArt(codArt);
			
    	} catch (Exception e) {
        	e.printStackTrace();
        //} catch (SQLException e) {
        //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
        //} catch (ClassNotFoundException e) {
        //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
        //}	
        }

		return pesca;
	}
	
}
