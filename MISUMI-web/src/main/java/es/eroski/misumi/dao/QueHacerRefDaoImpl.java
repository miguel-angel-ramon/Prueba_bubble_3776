package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import es.eroski.misumi.dao.iface.QueHacerRefDao;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class QueHacerRefDaoImpl implements QueHacerRefDao {
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(QueHacerRefDaoImpl.class);
	private static final int POSICION_PARAMETRO_SALIDA_OBTENCION_ACCION_REF = 3;
	//private static final int POSICION_PARAMETRO_SALIDA_OBTENCION_REF_TEXTIL = 1;

    @Autowired
    public void setDataSource(DataSource dataSourceSIA) {
    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
    } 
	
	@Override
	public QueHacerRef obtenerAccionRef(QueHacerRef pdaQueHacerRef) throws Exception  {
    	
		QueHacerRef salida = null;
    	//Obtención de parámetros de consulta
		final String p_cod_art_formlog = pdaQueHacerRef.getCodArtFormlog().toString();
		final String p_cod_loc = pdaQueHacerRef.getCodLoc().toString();
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {

	                	cs = con.prepareCall("{call PK_APR_SN_ACCION_REFERENCIA.P_APR_CONSULTA_ACCION_REF(?, ?, ?) }");
	                	//p_cod_art_formlog puede ser un número largo: se parsea a Long
	                    cs.setLong(1, Long.parseLong(p_cod_art_formlog));
	                    cs.setInt(2, Integer.parseInt(p_cod_loc));
	                    cs.registerOutParameter(3, OracleTypes.STRUCT, "MISUMI_R_TEXTIL_REG");
	              
	                } catch (SQLException e) {
	                	logger.error("Se ha producido un error: "+e);
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	QueHacerRef ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerResultadoObtenerAccionRef(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	       
	        try {
	        	 salida = (QueHacerRef) this.jdbcTemplate.execute(csCreator,csCallback);
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

/*	@Override
	public List<QueHacerRef> obtenerRefTextil(final List<QueHacerRef> listaQueHacerRef) throws Exception  {
    	
		List<QueHacerRef> salida = null;
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {
						// Crear estructura para consulta
						STRUCT itemConsulta = crearEstructuraConsultaRefTextil(
								listaQueHacerRef, con);

	                	cs = con.prepareCall("{call PK_SIC_TEXTIL_MISUMI.P_CONSULTA_REF_TEXTIL(?) }");
	                    
	                    cs.setObject(1, itemConsulta);
	                    cs.registerOutParameter(1, OracleTypes.STRUCT, "MISUMI_R_TEXTIL_REG");
	              
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	List<QueHacerRef> ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerResultadoObtenerRefTextil(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	        salida = (List<QueHacerRef>) this.jdbcTemplate.execute(csCreator,csCallback);

		}catch (Exception e) {
			e.printStackTrace();
		}
		
	    return salida;
    }*/

	private QueHacerRef obtenerResultadoObtenerAccionRef(CallableStatement cs, ResultSet rs){
    	
		QueHacerRef pdaQueHacerRef = new QueHacerRef();
    	try{
	    	//Obtención del parámetro de salida
	    	STRUCT estructuraResultado = (STRUCT)cs.getObject(POSICION_PARAMETRO_SALIDA_OBTENCION_ACCION_REF);
	    	
	    	//STRUCT estructuraDatos = (STRUCT) estructuraResultado.getAttributes()[0];
            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
            String descEstado = (String)estructuraResultado.getAttributes()[2];
            
            //Control de error en la obtención de datos
            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
    	    	//Obtención de los datos de la estructura
    	    	ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
        		if (listaDatos!=null){
            		rs = listaDatos.getResultSet();
            		//Recorrido del listado de datos
                    if (rs.next()) {
                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
                    	pdaQueHacerRef = this.mapRow(estructuraDatos);
                    }
        		}    
            }
        	
            pdaQueHacerRef.setEstado(new Long(estado.toString()));
            pdaQueHacerRef.setDescEstado(descEstado);

    	} catch (Exception e) {
        	e.printStackTrace();
        //} catch (SQLException e) {
        //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
        //} catch (ClassNotFoundException e) {
        //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
        //}	
        }
    
        return pdaQueHacerRef;
    }
	
    private QueHacerRef mapRow(STRUCT estructuraDatos) throws SQLException {
        Object[] objectInfo = estructuraDatos.getAttributes();
        
        //Obtención de datos de la estructura de base de datos
		BigDecimal codArticulo_BD = ((BigDecimal)objectInfo[0]);
		String denomInformeRef_BD = (String)objectInfo[1];
		String refProveedor_BD = (String)objectInfo[2];
		String talla_BD = (String)objectInfo[3];
		String color_BD = (String)objectInfo[4];
		String flgLote_BD = (String)objectInfo[5];
		BigDecimal codColeccion_BD = ((BigDecimal)objectInfo[6]);
		BigDecimal codTemporada_BD = ((BigDecimal)objectInfo[7]);
		String descrTemporadaAbr_BD = (String)objectInfo[8];
		String anioColeccion_BD = (String)objectInfo[9];
		Date fecInicio_BD = (Date)objectInfo[10];
		Date fecFin_BD = (Date)objectInfo[11];
		String converArt_BD = (String)objectInfo[12];
		String accion_BD = (String)objectInfo[13];
		
        //Transformación de datos para estructura de VartSfm
        Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
        String denomInformeRef = denomInformeRef_BD;
        String refProveedor = refProveedor_BD;
        String talla = talla_BD;
		String color = color_BD;
		String flgLote = flgLote_BD;
		Long codColeccion = ((codColeccion_BD != null && !("".equals(codColeccion_BD.toString())))?new Long(codColeccion_BD.toString()):null);
		Long codTemporada = ((codTemporada_BD != null && !("".equals(codTemporada_BD.toString())))?new Long(codTemporada_BD.toString()):null);
		String descrTemporadaAbr = descrTemporadaAbr_BD;
		String anioColeccion = anioColeccion_BD;
		Date fecInicio = fecInicio_BD;
		Date fecFin = fecFin_BD;
		String converArt = converArt_BD;
		String accion = accion_BD;
		
		Long estado = null;
		String descEstado = null;

		return new QueHacerRef(codArticulo, denomInformeRef,
				refProveedor, talla, color, flgLote,
				codColeccion, codTemporada, descrTemporadaAbr,
				anioColeccion, fecInicio, fecFin,
				converArt, accion, estado, descEstado);
	}
    
	private STRUCT crearEstructuraConsultaRefTextil(
			List<QueHacerRef> listaQueHacerRef, Connection con)
			throws SQLException {

		// Transformación de conexión a conexión de oracle. Necesario para
		// definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData()
				.getConnection();

		int numeroElementos = listaQueHacerRef.size();
		Object[] objectTabla = new Object[numeroElementos];

		for (int i = 0; i < numeroElementos; i++) {

			QueHacerRef queHacerRef = (QueHacerRef) listaQueHacerRef
					.get(i);

			Object[] objectInfo = new Object[14];
			objectInfo[0] = queHacerRef.getCodArtFormlog();
			
			StructDescriptor itemDescriptor = StructDescriptor
					.createDescriptor("MISUMI_R_TEXTIL_DAT", conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,
					conexionOracle, objectInfo);

			objectTabla[i] = itemObjectStruct;

		}

		Object[] objectConsulta = new Object[3]; // Tiene 3 campos pero sólo nos
													// interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("MISUMI_T_R_TEXTIL_DAT",
				conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor
				.createDescriptor("MISUMI_R_TEXTIL_REG", conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,
				conexionOracle, objectConsulta);

		return itemConsulta;
	}
	
	/*private List<QueHacerRef> obtenerResultadoObtenerRefTextil(CallableStatement cs, ResultSet rs){
    	
		QueHacerRef queHacerRef = new QueHacerRef();
		List<QueHacerRef> listaQueHacerRef = new ArrayList<QueHacerRef>();
    	try{
	    	//Obtención del parámetro de salida
	    	STRUCT estructuraResultado = (STRUCT)cs.getObject(POSICION_PARAMETRO_SALIDA_OBTENCION_REF_TEXTIL);
	    	
            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
            String descEstado = (String)estructuraResultado.getAttributes()[2];
            
            //Control de error en la obtención de datos
            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
    	    	//Obtención de los datos de la estructura
    	    	ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
        		if (listaDatos!=null){
            		rs = listaDatos.getResultSet();
            		//Recorrido del listado de datos
                    if (rs.next()) {
                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
                    	queHacerRef = this.mapRow(estructuraDatos);
                    	listaQueHacerRef.add(queHacerRef);
                    }
        		}    
            }
        	
            queHacerRef.setEstado(new Long(estado.toString()));
            queHacerRef.setDescEstado(descEstado);

    	} catch (Exception e) {
        	e.printStackTrace();
        //} catch (SQLException e) {
        //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
        //} catch (ClassNotFoundException e) {
        //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
        //}	
        }
    
        return listaQueHacerRef;
    }*/
}
