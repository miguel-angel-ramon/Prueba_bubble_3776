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

import es.eroski.misumi.dao.iface.EncargosReservasDao;
import es.eroski.misumi.model.EncargoReserva;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class EncargosReservasDaoImpl implements EncargosReservasDao{
	 private JdbcTemplate jdbcTemplate;
	 private static final int POSICION_PARAMETRO_SALIDA_OBTENER_ENC_RESERVAS= 7;
	 private static final int POSICION_PARAMETRO_SALIDA_INSERTAR_ENC_RESERVAS = 1;
	 private static final int POSICION_PARAMETRO_SALIDA_CONTAR_ENC_RESERVAS = 6;
	 private static final int POSICION_PARAMETRO_SALIDA_BORRAR_ENC_RESERVAS = 1;
	 private static final int POSICION_PARAMETRO_SALIDA_MODIF_ENC_RESERVAS = 1;
	 
	 private static Logger logger = Logger.getLogger(EncargosReservasDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
	    private EncargoReserva mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
            
            //Obtención de datos de la estructura de base de datos
			BigDecimal identificador_BD = ((BigDecimal)objectInfo[0]);
			String tipo_BD = (String)objectInfo[1];
			String origenEncargo_BD = (String)objectInfo[2];
			String oferta_BD = (String)objectInfo[3];
			BigDecimal codigoCentro_BD = (BigDecimal)objectInfo[4];
			BigDecimal articulo_BD = (BigDecimal)objectInfo[5];
			String descripcion_BD = (String)objectInfo[6];
			BigDecimal unidadesCaja_BD = (BigDecimal)objectInfo[7];
			String estructuraComercial_BD = (String)objectInfo[8];
			BigDecimal cantidad1_BD = (BigDecimal)objectInfo[9];
			BigDecimal cantidad2_BD = (BigDecimal)objectInfo[10];
			BigDecimal cantidad3_BD = (BigDecimal)objectInfo[11];
			BigDecimal implantacionInicial_BD = (BigDecimal)objectInfo[12];
			BigDecimal implantacionFinal_BD = (BigDecimal)objectInfo[13];
			Timestamp fechaInicio_BD = (Timestamp)objectInfo[14];
			Timestamp fechaFin_BD = (Timestamp)objectInfo[15];
			String tratamiento_BD = (String)objectInfo[16];
			BigDecimal tipoPedidoAdicional_BD = (BigDecimal)objectInfo[17];
			String flgForzarUnitaria_BD = (String)objectInfo[18];
			String flgExcluirVentas_BD = (String)objectInfo[19];
			String flgDetallado_BD = (String)objectInfo[20];
			String usuario_BD = (String)objectInfo[21];
			String tipoAprov_BD = (String)objectInfo[22];
			BigDecimal codError_BD = (BigDecimal)objectInfo[23];
			String descError_BD = (String)objectInfo[24];
			Timestamp fecha2_BD = (Timestamp)objectInfo[25];
			Timestamp fecha3_BD = (Timestamp)objectInfo[26];
			Timestamp fechaInicioPilada_BD = (Timestamp)objectInfo[27];	
			String flgModificableEnc1_BD = (String)objectInfo[28];
			String flgModificableEnc2_BD = (String)objectInfo[29];
			String flgModificableEnc3_BD = (String)objectInfo[30];
			String flgModificablePilada_BD = (String)objectInfo[31];
			String descPeriodo_BD = (String)objectInfo[32];
			String espacioPromo_BD = (String)objectInfo[33];
			
            //Transformación de datos para estructura de EncargoReserva
            Long identificador = ((identificador_BD != null && !("".equals(identificador_BD.toString())))?new Long(identificador_BD.toString()):null);
			String tipo = tipo_BD;
			String origenEncargo = origenEncargo_BD;
			String oferta = oferta_BD;
			Long codigoCentro = ((codigoCentro_BD != null && !("".equals(codigoCentro_BD.toString())))?new Long(codigoCentro_BD.toString()):null);
			Long articulo = ((articulo_BD != null && !("".equals(articulo_BD.toString())))?new Long(articulo_BD.toString()):null);
			String descripcion = descripcion_BD;
			Double unidadesCaja = ((unidadesCaja_BD != null && !("".equals(unidadesCaja_BD.toString())))?new Double(unidadesCaja_BD.toString()):null);
			String estructuraComercial = estructuraComercial_BD;
			Double cantidad1 = ((cantidad1_BD != null && !("".equals(cantidad1_BD.toString())))?new Double(cantidad1_BD.toString()):null);
			Double cantidad2 = ((cantidad2_BD != null && !("".equals(cantidad2_BD.toString())))?new Double(cantidad2_BD.toString()):null);
			Double cantidad3 = ((cantidad3_BD != null && !("".equals(cantidad3_BD.toString())))?new Double(cantidad3_BD.toString()):null);
			Double implantacionInicial = ((implantacionInicial_BD != null && !("".equals(implantacionInicial_BD.toString())))?new Double(implantacionInicial_BD.toString()):null);
			Double implantacionFinal = ((implantacionFinal_BD != null && !("".equals(implantacionFinal_BD.toString())))?new Double(implantacionFinal_BD.toString()):null);
			Date fechaInicio = ((fechaInicio_BD != null )?new Date(fechaInicio_BD.getTime()):null);
			Date fechaFin = ((fechaFin_BD != null )?new Date(fechaFin_BD.getTime()):null);
			String tratamiento = tratamiento_BD;
			Long tipoPedidoAdicional = ((tipoPedidoAdicional_BD != null && !("".equals(tipoPedidoAdicional_BD.toString())))?new Long(tipoPedidoAdicional_BD.toString()):null);
			String flgForzarUnitaria = flgForzarUnitaria_BD;
			String flgExcluirVentas = flgExcluirVentas_BD;
			
			String flgDetallado = flgDetallado_BD;
			String usuario = usuario_BD;
			String tipoAprov = tipoAprov_BD;
			Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
			String descError = descError_BD;
			Date fecha2 = ((fecha2_BD != null )?new Date(fecha2_BD.getTime()):null);
			Date fecha3 = ((fecha3_BD != null )?new Date(fecha3_BD.getTime()):null);
			Date fechaInicioPilada = ((fechaInicioPilada_BD != null )?new Date(fechaInicioPilada_BD.getTime()):null);
			String flgModificableEnc1 = flgModificableEnc1_BD;
			String flgModificableEnc2 = flgModificableEnc2_BD;
			String flgModificableEnc3 = flgModificableEnc3_BD;
			String flgModificablePilada = flgModificablePilada_BD;
			String descPeriodo = descPeriodo_BD;
			String espacioPromo = espacioPromo_BD;
			
			EncargoReserva encargoReserva =  new EncargoReserva(identificador, tipo, origenEncargo, oferta, codigoCentro,
					articulo, descripcion, unidadesCaja, estructuraComercial, cantidad1, cantidad2,cantidad3,
					implantacionInicial, implantacionFinal, fechaInicio, fechaFin, tratamiento,
					tipoPedidoAdicional, flgForzarUnitaria, flgExcluirVentas, 
					flgDetallado, usuario, tipoAprov, codError, descError,fecha2,fecha3, 
					fechaInicioPilada, flgModificableEnc1, flgModificableEnc2, flgModificableEnc3,flgModificablePilada,descPeriodo,espacioPromo);
			
			
			return encargoReserva;
			
			
		}
	    
	    private EncargoReserva mapRowCont(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
         
            //Obtención de datos de la estructura de base de datos
			BigDecimal tipoPedidoAdicional_BD = (BigDecimal)objectInfo[0];
			BigDecimal numeroOcurrencias_BD = (BigDecimal)objectInfo[1];
			BigDecimal codError_BD = (BigDecimal)objectInfo[2];
			String descError_BD = (String)objectInfo[3];
			
            //Transformación de datos para estructura de EncargoReserva
			Long tipoPedidoAdicional = ((tipoPedidoAdicional_BD != null && !("".equals(tipoPedidoAdicional_BD.toString())))?new Long(tipoPedidoAdicional_BD.toString()):null);
			Long numeroOcurrencias = ((numeroOcurrencias_BD != null && !("".equals(numeroOcurrencias_BD.toString())))?new Long(numeroOcurrencias_BD.toString()):null);
			Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
			String descError = descError_BD;
			
			EncargoReserva encargoReserva =  new EncargoReserva();
			encargoReserva.setTipoPedidoAdicional(tipoPedidoAdicional);
			encargoReserva.setNumeroOcurrencias(numeroOcurrencias);
			encargoReserva.setCodError(codError);
			encargoReserva.setDescError(descError);
			
			return encargoReserva;
		}

	    @Override
		public EncargosReservasLista obtenerEncReservas(EncargoReserva encargoReserva) throws Exception {
	    	
			EncargosReservasLista salida = null;
	    	//Obtención de parámetros de consulta
	    	final String p_cod_loc = encargoReserva.getCodLoc().toString();
	    	final String p_cod_n1 = ((encargoReserva.getCodN1() != null)? encargoReserva.getCodN1() : "");
	    	final String p_cod_n2 = ((encargoReserva.getCodN2() != null)? encargoReserva.getCodN2() : "");
	    	final String p_cod_n3 = ((encargoReserva.getCodN3() != null)? encargoReserva.getCodN3() : "");
	    	final String p_cod_art_formlog = ((encargoReserva.getCodArtFormlog() != null)? encargoReserva.getCodArtFormlog().toString() : "");
	    	final String p_tipo_pedido_adicional = ((encargoReserva.getTipoPedidoAdicional() != null)? encargoReserva.getTipoPedidoAdicional().toString() : "");
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_obtener_enc_reservas(?, ?, ?, ?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, Integer.parseInt(p_cod_loc));
		                    if (p_cod_n1 != null && !p_cod_n1.equals("")){
		                    	cs.setString(2, p_cod_n1);
		                    }else{
		                    	cs.setNull(2, OracleTypes.NULL);
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
		                    if (p_cod_art_formlog != null && !"".equals(p_cod_art_formlog)){
		                    	cs.setLong(5, Long.parseLong(p_cod_art_formlog));
		                    }else{
		                    	cs.setNull(5, OracleTypes.INTEGER);
		                    }
		                    if (p_tipo_pedido_adicional != null && !"".equals(p_tipo_pedido_adicional)){
		                    	cs.setLong(6, Long.parseLong(p_tipo_pedido_adicional));
		                    }else{
		                    	cs.setNull(6, OracleTypes.INTEGER);
		                    }
		                    cs.registerOutParameter(7, OracleTypes.STRUCT, "APR_R_ENCARGOS_RESERVAS_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                EncargosReservasLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoObtenerEncReservas(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		       
		        
		        try {
		        	 salida = (EncargosReservasLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	    
		@Override
		public EncargosReservasLista insertarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
	    	
			EncargosReservasLista salida = null;
	    	//Obtención de parámetros de actualización
	    	final List<EncargoReserva> listadoModificados = new ArrayList<EncargoReserva>(); //Datos que se enviarán al procedimiento
	    	//Carga de lista de modificados
	    	for (int i=0; i<listaEncargosReservas.size(); i++){
	    		EncargoReserva encargoReserva = listaEncargosReservas.get(i);
    			//Se guardan los modificados para pasarlos al procedimiento
	    		listadoModificados.add(encargoReserva);
	    	}
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {
		                	
		                	//Crear estructura para actualización
		                	STRUCT itemConsulta = crearEstructuraModificacionEncReservas(listadoModificados, con);

		        	    	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_insertar_enc_reservas(?) }");
		        	    	cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_ENCARGOS_RESERVAS_REG");
		        	    	cs.setObject(1, itemConsulta);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		            	EncargosReservasLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoInsertarEncReservas(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };

		        try {
		        	 salida = (EncargosReservasLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
		
		@Override
		public EncargosReservasLista contarEncReservas(EncargoReserva encargoReserva) throws Exception {
	    	
			EncargosReservasLista salida = null;
	    	//Obtención de parámetros de consulta
	    	final String p_cod_loc = encargoReserva.getCodLoc().toString();
	    	final String p_cod_n1 = ((encargoReserva.getCodN1() != null)? encargoReserva.getCodN1() : "");
	    	final String p_cod_n2 = ((encargoReserva.getCodN2() != null)? encargoReserva.getCodN2() : "");
	    	final String p_cod_n3 = ((encargoReserva.getCodN3() != null)? encargoReserva.getCodN3() : "");
	    	final String p_oferta = ((encargoReserva.getOferta() != null)? encargoReserva.getOferta() : "");
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_contar_enc_reservas(?, ?, ?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, Integer.parseInt(p_cod_loc));
		                    if (p_cod_n1 != null && !p_cod_n1.equals("")){
		                    	cs.setString(2, p_cod_n1);
		                    }else{
		                    	cs.setNull(2, OracleTypes.NULL);
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
		                    if (p_oferta != null && !"".equals(p_oferta)){
		                    	cs.setLong(5, Long.parseLong(p_oferta));
		                    }else{
		                    	cs.setNull(5, OracleTypes.INTEGER);
		                    }
		                    cs.registerOutParameter(6, OracleTypes.STRUCT, "APR_R_CONT_ENC_RESERVAS_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                EncargosReservasLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoContEncReservas(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		       
		        try {
		        	 salida = (EncargosReservasLista) this.jdbcTemplate.execute(csCreator,csCallback);
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

		@Override
		public EncargoReserva validarArticulo(EncargoReserva encargoReserva) throws Exception {
	    	
			EncargoReserva salida = null;
	    	//Obtención de parámetros de consulta
	    	final String p_cod_loc = encargoReserva.getCodLoc().toString();
	    	final String p_cod_art_formlog = ((encargoReserva.getCodArtFormlog() != null)? encargoReserva.getCodArtFormlog().toString() : "");
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_validar_articulo(?, ?, ?, ?, ?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, Integer.parseInt(p_cod_loc));
		                    if (p_cod_art_formlog != null && !"".equals(p_cod_art_formlog)){
		                    	cs.setLong(2, Long.parseLong(p_cod_art_formlog));
		                    }else{
		                    	cs.setNull(2, OracleTypes.INTEGER);
		                    }
		                    cs.registerOutParameter(3, OracleTypes.DATE);
		                    cs.registerOutParameter(4, OracleTypes.VARCHAR);
		                    cs.registerOutParameter(5, OracleTypes.VARCHAR);
		                    cs.registerOutParameter(6, OracleTypes.VARCHAR);
		                    cs.registerOutParameter(7, OracleTypes.INTEGER);
		                    cs.registerOutParameter(8, OracleTypes.VARCHAR);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                EncargoReserva ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoValidarArticulo(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		       
		        try {
		        	 salida = (EncargoReserva) this.jdbcTemplate.execute(csCreator,csCallback);
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
		
		@Override
		public EncargosReservasLista borrarEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
	    	
			EncargosReservasLista salida = null;
	    	//Obtención de parámetros de borrado
	    	final List<EncargoReserva> listadoBorrados = new ArrayList<EncargoReserva>(); //Datos que se enviarán al procedimiento
	    	//Carga de lista de borrados
	    	for (int i=0; i<listaEncargosReservas.size(); i++){
	    		EncargoReserva encargoReserva = listaEncargosReservas.get(i);
    			//Se guardan los borrados para pasarlos al procedimiento
	    		listadoBorrados.add(encargoReserva);
	    	}
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {
		                	
		                	//Crear estructura para actualización
		                	STRUCT itemConsulta = crearEstructuraModificacionEncReservas(listadoBorrados, con);
		                	
		        	    	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_borrar_enc_reservas(?) }");
		        	    	cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_ENCARGOS_RESERVAS_REG");
		        	    	cs.setObject(1, itemConsulta);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		            	EncargosReservasLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoBorrarEncReservas(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        try {
		        	 salida = (EncargosReservasLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
		
		@Override
		public EncargosReservasLista modifEncReservas(List<EncargoReserva> listaEncargosReservas) throws Exception {
	    	
			EncargosReservasLista salida = null;
	    	//Obtención de parámetros de actualización
	    	final List<EncargoReserva> listadoModificados = new ArrayList<EncargoReserva>(); //Datos que se enviarán al procedimiento
	    	//Carga de lista de modificados
	    	for (int i=0; i<listaEncargosReservas.size(); i++){
	    		EncargoReserva encargoReserva = listaEncargosReservas.get(i);
    			//Se guardan los modificados para pasarlos al procedimiento
	    		listadoModificados.add(encargoReserva);
	    	}
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {
		                	
		                	//Crear estructura para actualización
		                	STRUCT itemConsulta = crearEstructuraModificacionEncReservas(listadoModificados, con);
		                	
		        	    	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.p_apr_modif_enc_reservas(?) }");
		        	    	cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_ENCARGOS_RESERVAS_REG");
		        	    	cs.setObject(1, itemConsulta);
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		            	EncargosReservasLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoModifEncReservas(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        try {
		        	salida = (EncargosReservasLista) this.jdbcTemplate.execute(csCreator,csCallback);
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

		private EncargosReservasLista obtenerResultadoObtenerEncReservas(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraEncargosReservas(cs, rs, this.POSICION_PARAMETRO_SALIDA_OBTENER_ENC_RESERVAS);
	    }

		private EncargosReservasLista obtenerResultadoInsertarEncReservas(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraEncargosReservas(cs, rs, this.POSICION_PARAMETRO_SALIDA_INSERTAR_ENC_RESERVAS);
	    }

		private EncargosReservasLista obtenerResultadoContEncReservas(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraContEncargosReservas(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONTAR_ENC_RESERVAS);
	    }

		private EncargoReserva obtenerResultadoValidarArticulo(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraValidarArticulo(cs, rs);
	    }

		private EncargosReservasLista obtenerResultadoBorrarEncReservas(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraEncargosReservas(cs, rs, this.POSICION_PARAMETRO_SALIDA_BORRAR_ENC_RESERVAS);
	    }

		private EncargosReservasLista obtenerResultadoModifEncReservas(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraEncargosReservas(cs, rs, this.POSICION_PARAMETRO_SALIDA_MODIF_ENC_RESERVAS);
	    }

		private EncargosReservasLista obtenerEstructuraEncargosReservas(CallableStatement cs, ResultSet rs, int idParametroResultado){
	    	
			EncargosReservasLista encargosReservasLista = new EncargosReservasLista();
	    	List<EncargoReserva> listaEncargoReserva = new ArrayList<EncargoReserva>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	
		    	//Obtención de los datos de la estructura
	            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
	            String descEstado = (String)estructuraResultado.getAttributes()[2];
	            
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
	                        EncargoReserva encargoReserva = this.mapRow(estructuraDatos, rowNum);
	                        listaEncargoReserva.add(encargoReserva);
	                        rowNum++;
	                    }
            		}            		
	            	
	            	
	            	encargosReservasLista.setDatos(listaEncargoReserva);
	            }
	            
	            encargosReservasLista.setEstado(new Long(estado.toString()));
	            encargosReservasLista.setDescEstado(descEstado);
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return encargosReservasLista;
	    }

		private EncargosReservasLista obtenerEstructuraContEncargosReservas(CallableStatement cs, ResultSet rs, int idParametroResultado){
	    	
			EncargosReservasLista encargosReservasLista = new EncargosReservasLista();
	    	List<EncargoReserva> listaEncargoReserva = new ArrayList<EncargoReserva>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	
		    	//Obtención de los datos de la estructura
	            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
	            String descEstado = (String)estructuraResultado.getAttributes()[2];
	            
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
	                        EncargoReserva encargoReserva = this.mapRowCont(estructuraDatos, rowNum);
	                        listaEncargoReserva.add(encargoReserva);
	                        rowNum++;
	                    }
            		}            		
	            	
	            	
	            	encargosReservasLista.setDatos(listaEncargoReserva);
	            }
	            
	            encargosReservasLista.setEstado(new Long(estado.toString()));
	            encargosReservasLista.setDescEstado(descEstado);
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return encargosReservasLista;
	    }

		private EncargoReserva obtenerEstructuraValidarArticulo(CallableStatement cs, ResultSet rs){
	    	
			EncargoReserva encargoReserva = new EncargoReserva();
	    	
	    	try{
		    	//Obtención de los parámetros de salida
	    		Integer codError_BD = (Integer)cs.getObject(7);
	    		String descError_BD = (String)cs.getObject(8);
	    		
	            //Control de error en la obtención de datos
	            if (new Integer("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
	            	//Obtención de los datos de salida
	            	Date fechaVenta_BD = (Date)cs.getObject(3);
		    		String bloqueoEncargo_BD = (String)cs.getObject(4);
		    		String bloqueoPilada_BD = (String)cs.getObject(5);
		    		String bloqueoDetallado_BD = (String)cs.getObject(6);

		    		encargoReserva.setFechaVenta(((fechaVenta_BD != null )?fechaVenta_BD:null));
		    		encargoReserva.setBloqueoEncargo("S".equals(bloqueoEncargo_BD));
		    		encargoReserva.setBloqueoPilada("S".equals(bloqueoPilada_BD));
		    		encargoReserva.setBloqueoDetallado("S".equals(bloqueoDetallado_BD));
	            }
	            
	            encargoReserva.setCodError(((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null));
	            encargoReserva.setDescError(descError_BD);
            	
        	} catch (Exception e) {
            	e.printStackTrace();
            //} catch (SQLException e) {
            //	log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+ error.getStackTrace(e) );         
            //} catch (ClassNotFoundException e) {
            //	log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+ error.getStackTrace(e) );
            //}	
            }
	    
            return encargoReserva;
	    }

		private STRUCT crearEstructuraModificacionEncReservas(List<EncargoReserva> listaModificados, Connection con) throws SQLException {

			//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
			OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

			int numeroElementos = listaModificados.size();
			Object[] objectTabla = new Object[numeroElementos];
			
			for (int i=0; i<numeroElementos; i++){
				
				EncargoReserva encargoReserva = (EncargoReserva)listaModificados.get(i);
	        	
				Object[] objectInfo = new Object[34];
	        	
	        	//Sólo se informan los datos necesarios
	        	//objectInfo[0] = (encargoReserva.getIdentificador()!=null?new BigDecimal(encargoReserva.getIdentificador()):null);
				objectInfo[0] = (encargoReserva.getIdentificador()!=null?encargoReserva.getIdentificador():null);
				
				objectInfo[1] = encargoReserva.getTipo();
				objectInfo[2] = encargoReserva.getOrigenEncargo();
				objectInfo[3] = encargoReserva.getOferta();
				
				objectInfo[4] = (encargoReserva.getCodigoCentro()!=null?encargoReserva.getCodigoCentro():null);
				
				objectInfo[5] = (encargoReserva.getArticulo()!=null?encargoReserva.getArticulo():null);
				
				objectInfo[6] = encargoReserva.getDescripcion();
				
				objectInfo[7] = (encargoReserva.getUnidadesCaja()!=null?encargoReserva.getUnidadesCaja():null);
				
				objectInfo[8] = encargoReserva.getEstructuraComercial();
				
				objectInfo[9] = (encargoReserva.getCantidad1()!=null?encargoReserva.getCantidad1():null);
				objectInfo[10] = (encargoReserva.getCantidad2()!=null?encargoReserva.getCantidad2():null);
				objectInfo[11] = (encargoReserva.getCantidad3()!=null?encargoReserva.getCantidad3():null);

				objectInfo[12] = (encargoReserva.getImplantacionInicial()!=null?encargoReserva.getImplantacionInicial():null);

				objectInfo[13] = (encargoReserva.getImplantacionFinal()!=null?encargoReserva.getImplantacionFinal():null);
				
			
				objectInfo[14] = (encargoReserva.getFechaInicio()!=null?new Timestamp(encargoReserva.getFechaInicio().getTime()):null);
				objectInfo[15] = (encargoReserva.getFechaFin()!=null?new Timestamp(encargoReserva.getFechaFin().getTime()):null);
			
				objectInfo[16] = encargoReserva.getTratamiento();

				objectInfo[17] = (encargoReserva.getTipoPedidoAdicional()!=null?encargoReserva.getTipoPedidoAdicional():null);
				
				objectInfo[18] = encargoReserva.getFlgForzarUnitaria();
				objectInfo[19] = encargoReserva.getFlgExcluirVentas();
				objectInfo[20] = encargoReserva.getFlgDetallado();
				objectInfo[21] = encargoReserva.getUsuario();
				objectInfo[22] = encargoReserva.getTipoAprovisionamiento();

				objectInfo[23] = (encargoReserva.getCodError()!=null?encargoReserva.getCodError():null);
			    objectInfo[24] = (encargoReserva.getDescError()!=null?encargoReserva.getDescError():null);
			    
			    objectInfo[25] = (encargoReserva.getFecha2()!=null?new Timestamp(encargoReserva.getFecha2().getTime()):null);
			    objectInfo[26] = (encargoReserva.getFecha3()!=null?new Timestamp(encargoReserva.getFecha3().getTime()):null);
				
				objectInfo[27] = (encargoReserva.getFechaInicioPilada()!=null?new Timestamp(encargoReserva.getFechaInicioPilada().getTime()):null);
				objectInfo[28] = encargoReserva.getFlgModificableEnc1();
				objectInfo[29] = encargoReserva.getFlgModificableEnc2();
				objectInfo[30] = encargoReserva.getFlgModificableEnc3();
				objectInfo[31] = encargoReserva.getFlgModificablePilada();
				objectInfo[32] = encargoReserva.getDescPeriodo();
				objectInfo[33] = encargoReserva.getEspacioPromo();

				
				//TABLA apr_t_r_encargos_reservas_dat 
				//STRUCT apr_r_encargos_reservas_REG toda la estructura
				//STRUCT apr_r_encargos_reservas_dat registro 
				
				//StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_ENCARGOS_RESERVAS_REG",conexionOracle);
				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_ENCARGOS_RESERVAS_DAT",conexionOracle);
		    	STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
		    	
		    	objectTabla[i] = itemObjectStruct;
		    	
			}
		    
			Object[] objectConsulta = new Object[3]; //Tiene 3 campos pero sólo nos interesa la lista
			ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_ENCARGOS_RESERVAS_DAT", conexionOracle);
		    ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);
	    	
	    	objectConsulta[0] = array;
	    	
	    	StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_ENCARGOS_RESERVAS_REG",conexionOracle);
	    	STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);
	    	
	    	return itemConsulta;
	    }
}
