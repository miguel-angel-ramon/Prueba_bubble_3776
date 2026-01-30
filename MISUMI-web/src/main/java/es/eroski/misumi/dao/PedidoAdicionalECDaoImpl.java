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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoAdicionalECDao;
import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class PedidoAdicionalECDaoImpl implements PedidoAdicionalECDao{
	
	private static Logger logger = Logger.getLogger(PedidoAdicionalECDaoImpl.class);
	 
	 private JdbcTemplate jdbcTemplate;
	 
	 //private static Logger logger = LoggerFactory.getLogger(PedidoAdicionalECDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(PedidoAdicionalECDaoImpl.class);

	 @Autowired
	 public void setDataSource(DataSource dataSourceSIA) {
		 this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	 }
	 
	 private PedidoAdicionalEC mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		 Object[] objectInfo = estructuraDatos.getAttributes();
         
		 //Obtención de datos de la estructura de base de datos
		 BigDecimal localizador_BD = ((BigDecimal)objectInfo[0]);
		 BigDecimal codLoc_BD = ((BigDecimal)objectInfo[1]);
		 String contactoCentro_BD = (String)objectInfo[2];
		 String telefonoCentro_BD = (String)objectInfo[3];
		 String nombreCliente_BD = (String)objectInfo[4];
		 String apellidoCliente_BD = (String)objectInfo[5];
		 String telefonoCliente_BD = (String)objectInfo[6];
		 Timestamp fechaHoraEncargo_BD = (Timestamp)objectInfo[7];
		 String tipoEncargo_BD = (String)objectInfo[8];
		 Timestamp fechaVenta_BD = (Timestamp)objectInfo[9];
		 Timestamp fechaVentaModificada_BD = (Timestamp)objectInfo[10];
		 String fechaInferior_BD = (String)objectInfo[11];
		 String area_BD = (String)objectInfo[12];
		 String seccion_BD = (String)objectInfo[13];
		 String categoria_BD = (String)objectInfo[14];
		 String subcategoria_BD = (String)objectInfo[15];
		 String segmento_BD = (String)objectInfo[16];
		 BigDecimal codArtFormlogMisumi_BD = ((BigDecimal)objectInfo[17]);
		 BigDecimal codArtFormlog_BD = ((BigDecimal)objectInfo[18]);
		 String denomArticulo_BD = (String)objectInfo[19];
		 String especificacion_BD = (String)objectInfo[20];
		 BigDecimal pesoDesde_BD = ((BigDecimal)objectInfo[21]);
		 BigDecimal pesoHasta_BD = ((BigDecimal)objectInfo[22]);
		 String confirmarEspecificaciones_BD = (String)objectInfo[23];
		 String faltaRef_BD = (String)objectInfo[24];
		 String cambioRef_BD = (String)objectInfo[25];
		 String flgEspec_BD = (String)objectInfo[26];
		 String confirmarPrecio_BD = (String)objectInfo[27];
		 BigDecimal cantEncargo_BD = ((BigDecimal)objectInfo[28]);
		 BigDecimal cantFinalCompra_BD = ((BigDecimal)objectInfo[29]);
		 BigDecimal cantServido_BD = ((BigDecimal)objectInfo[30]);
		 BigDecimal cantNoServido_BD = ((BigDecimal)objectInfo[31]);
		 BigDecimal unidServ_BD = ((BigDecimal)objectInfo[32]);
		 String estado_BD = (String)objectInfo[33];
		 String observacionesMisumi_BD = (String)objectInfo[34];
		 BigDecimal codigoPedidoInterno_BD = ((BigDecimal)objectInfo[35]);
		 String flgModificable_BD = (String)objectInfo[36];
		 String tipoAprov_BD = (String)objectInfo[37];
		 String vitrina_BD = (String)objectInfo[38];
		 String relCompraVenta_BD = (String)objectInfo[39];
		 BigDecimal codigoError_BD = ((BigDecimal)objectInfo[40]);
		 String descripcionError_BD = (String)objectInfo[41];
		 String descripcionGestadic_BD = (String)objectInfo[42];
		 String estadoGestadic_BD = (String)objectInfo[43];
		 String txtDetalleGestadic_BD = (String)objectInfo[44];
		 String txtSituacionGestadic_BD = (String)objectInfo[45];
		 
         //Transformación de datos para estructura de PedidoAdicionalEC
		 Long localizador = ((localizador_BD != null && !("".equals(localizador_BD.toString())))?new Long(localizador_BD.toString()):null);
		 Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
		 String contactoCentro = contactoCentro_BD;
		 String telefonoCentro = telefonoCentro_BD;
		 String nombreCliente = nombreCliente_BD;
		 String apellidoCliente = apellidoCliente_BD;
		 String telefonoCliente = telefonoCliente_BD;
		 Date fechaHoraEncargo = ((fechaHoraEncargo_BD != null )?new Date(fechaHoraEncargo_BD.getTime()):null);
		 String tipoEncargo = tipoEncargo_BD;
		 Date fechaVenta = ((fechaVenta_BD != null )?new Date(fechaVenta_BD.getTime()):null);
		 Date fechaVentaModificada = ((fechaVentaModificada_BD != null )?new Date(fechaVentaModificada_BD.getTime()):null);
		 String fechaInferior = fechaInferior_BD;
		 String area = area_BD;
		 String seccion = seccion_BD;
		 String categoria = categoria_BD;
		 String subcategoria = subcategoria_BD;
		 String segmento = segmento_BD;
		 Long codArtFormlogMisumi = ((codArtFormlogMisumi_BD != null && !("".equals(codArtFormlogMisumi_BD.toString())))?new Long(codArtFormlogMisumi_BD.toString()):null);
		 Long codArtFormlog = ((codArtFormlog_BD != null && !("".equals(codArtFormlog_BD.toString())))?new Long(codArtFormlog_BD.toString()):null);
		 String denomArticulo = denomArticulo_BD;
		 String especificacion = especificacion_BD;
		 Double pesoDesde = ((pesoDesde_BD != null && !("".equals(pesoDesde_BD.toString())))?new Double(pesoDesde_BD.toString()):null);
		 Double pesoHasta = ((pesoHasta_BD != null && !("".equals(pesoHasta_BD.toString())))?new Double(pesoHasta_BD.toString()):null);
		 String confirmarEspecificaciones = confirmarEspecificaciones_BD;
		 String faltaRef = faltaRef_BD;
		 String cambioRef = cambioRef_BD;
		 String confirmarPrecio = confirmarPrecio_BD;
		 Double cantEncargo = ((cantEncargo_BD != null && !("".equals(cantEncargo_BD.toString())))?new Double(cantEncargo_BD.toString()):null);
		 Double cantFinalCompra = ((cantFinalCompra_BD != null && !("".equals(cantFinalCompra_BD.toString())))?new Double(cantFinalCompra_BD.toString()):null);
		 Double cantServido = ((cantServido_BD != null && !("".equals(cantServido_BD.toString())))?new Double(cantServido_BD.toString()):null);
		 Double cantNoServido = ((cantNoServido_BD != null && !("".equals(cantNoServido_BD.toString())))?new Double(cantNoServido_BD.toString()):null);
		 Double unidServ = ((unidServ_BD != null && !("".equals(unidServ_BD.toString())))?new Double(unidServ_BD.toString()):null);
		 String estado = estado_BD;
		 String observacionesMisumi = observacionesMisumi_BD;
		 BigDecimal codigoPedidoInterno = ((codigoPedidoInterno_BD != null && !("".equals(codigoPedidoInterno_BD.toString())))?new BigDecimal(codigoPedidoInterno_BD.toString()):null);
		 String flgModificable = flgModificable_BD;
		 String tipoAprov = tipoAprov_BD;
		 String vitrina = vitrina_BD;
		 String relCompraVenta = relCompraVenta_BD;
		 Long codigoError = ((codigoError_BD != null && !("".equals(codigoError_BD.toString())))?new Long(codigoError_BD.toString()):null);
		 String descripcionError = descripcionError_BD;
		 String descripcionGestadic = descripcionGestadic_BD;
		 String estadoGestadic = estadoGestadic_BD;
		 String txtDetalleGestadic = txtDetalleGestadic_BD;
		 String txtSituacionGestadic = txtSituacionGestadic_BD;
	 
		 return new PedidoAdicionalEC(localizador, codLoc,
					contactoCentro, telefonoCentro, nombreCliente,
					apellidoCliente, telefonoCliente,
					fechaHoraEncargo, tipoEncargo, fechaVenta,
					fechaVentaModificada, fechaInferior, area,
					seccion, categoria, subcategoria,
					segmento, codArtFormlogMisumi, codArtFormlog,
					denomArticulo, especificacion, pesoDesde,
					pesoHasta, confirmarEspecificaciones, faltaRef,
					cambioRef, confirmarPrecio, cantEncargo,
					cantFinalCompra, cantServido, cantNoServido,
					unidServ, estado, observacionesMisumi,
					codigoPedidoInterno, flgModificable, tipoAprov,
					vitrina, relCompraVenta, codigoError,
					descripcionError, descripcionGestadic, estadoGestadic,
					txtDetalleGestadic, txtSituacionGestadic,
					null, flgEspec_BD);
		 
	 }
	 
	 @Override
	 public List<PedidoAdicionalEC> findAll(PedidoAdicionalEC pedidoAdicionalEC) throws Exception {
		 
		 List<PedidoAdicionalEC> salida = null;
		 
    	//Obtención de parámetros de consulta
    	final String p_cod_loc_dest = pedidoAdicionalEC.getCodLoc().toString();
    	final String p_cod_n1 = ((pedidoAdicionalEC.getArea() != null)? pedidoAdicionalEC.getArea() : "");
    	final String p_cod_n2 = ((pedidoAdicionalEC.getSeccion() != null)? pedidoAdicionalEC.getSeccion() : "");
    	final String p_cod_n3 = ((pedidoAdicionalEC.getCategoria() != null)? pedidoAdicionalEC.getCategoria() : "");
    	final String p_cod_articulo = ((pedidoAdicionalEC.getCodArtFormlog() != null)? pedidoAdicionalEC.getCodArtFormlog().toString() : "");
    	final String p_denominacion = ((pedidoAdicionalEC.getDenomArticulo() != null)? pedidoAdicionalEC.getDenomArticulo() : "");
    	final String p_tfno_cli = ((pedidoAdicionalEC.getTelefonoCliente() != null)? pedidoAdicionalEC.getTelefonoCliente() : "");
    	final String p_nom_cli = ((pedidoAdicionalEC.getNombreCliente() != null)? pedidoAdicionalEC.getNombreCliente() : "");
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {

	                	cs = con.prepareCall("{call PK_APR_ENCARGOS_MISUMI.P_APR_OBT_ENCARGOS(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
	                    
	                	cs.setInt(1, Integer.parseInt(p_cod_loc_dest));
	                    if (p_cod_n1 != null && !"".equals(p_cod_n1)){
	                    	cs.setLong(2, Long.parseLong(p_cod_n1));
	                    }else{
	                    	cs.setNull(2, OracleTypes.INTEGER);
	                    }
	                    if (p_cod_n2 != null && !p_cod_n2.equals("")){
	                    	cs.setString(3, p_cod_n2);
	                    }else{
	                    	cs.setNull(3, OracleTypes.NULL);
	                    }
	                    if (p_cod_n3 != null && !p_cod_n3.equals("")){
	                    	cs.setString(4, p_cod_n3);
	                    }else{
	                    	cs.setNull(4, OracleTypes.NULL);
	                    }
	                    if (p_cod_articulo != null && !p_cod_articulo.equals("")){
	                    	cs.setString(5, p_cod_articulo);
	                    }else{
	                    	cs.setNull(5, OracleTypes.NULL);
	                    }
	                    if (p_denominacion != null && !p_denominacion.equals("")){
	                    	cs.setString(6, p_denominacion);
	                    }else{
	                    	cs.setNull(6, OracleTypes.NULL);
	                    }
	                    if (p_tfno_cli != null && !p_tfno_cli.equals("")){
	                    	cs.setString(7, p_tfno_cli);
	                    }else{
	                    	cs.setNull(7, OracleTypes.NULL);
	                    }
	                    if (p_nom_cli != null && !p_nom_cli.equals("")){
	                    	cs.setString(8, p_nom_cli);
	                    }else{
	                    	cs.setNull(8, OracleTypes.NULL);
	                    }

	                    cs.registerOutParameter(9, OracleTypes.STRUCT, "APR_R_ENCARGO_REG");
	              
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	List<PedidoAdicionalEC> ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerResultadoConsultaEncargos(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	       
	        try {
	        	 salida = (List<PedidoAdicionalEC>) this.jdbcTemplate.execute(csCreator,csCallback);
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
	 
	private List<PedidoAdicionalEC> obtenerResultadoConsultaEncargos(CallableStatement cs, ResultSet rs){
    	
		List<PedidoAdicionalEC> listaEncargos = new ArrayList<PedidoAdicionalEC>();
    	
    	try{
	    	//Obtención del parámetro de salida
	    	STRUCT estructuraResultado = (STRUCT)cs.getObject(9);
	    	//logger.info("###############################");
	    	//logger.info(estructuraResultado.dump());
	    	//logger.info("###############################");
	    	//Obtención de los datos de la estructura
            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[2];
            String descEstado = (String)estructuraResultado.getAttributes()[3];
            
            //Control de error en la obtención de datos
            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
            	//Obtención de los datos de salida
        		ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
        		if (listaDatos!=null){
            		rs = listaDatos.getResultSet();
                    int rowNum = 0;
            		//Recorrido del listado de datos
                    while (rs.next()) {
                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
                    	PedidoAdicionalEC pedidoAdicionalEC = this.mapRow(estructuraDatos, rowNum);
                    	listaEncargos.add(pedidoAdicionalEC);
                        rowNum++;
                    }
        		}            		
            }
    	} catch (Exception e) {
        	e.printStackTrace();
        //} catch (SQLException e) {
        //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
        //} catch (ClassNotFoundException e) {
        //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
        //}	
        }
    
        return listaEncargos;
    }
	 
	 
	 @Override
	 public List<PedidoAdicionalEC> removeAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception {
	    	
		List<PedidoAdicionalEC> resultado = null;

		return resultado;
     }
	 
	 	
 	 @Override
	 public List<PedidoAdicionalEC> modifyAll(List<PedidoAdicionalEC> listaPedidoAdicionalEC) throws Exception {
	    	
 		List<PedidoAdicionalEC> resultado = null;

		return resultado;
    }
}
