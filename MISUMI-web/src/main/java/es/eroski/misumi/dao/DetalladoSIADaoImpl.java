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

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DetalladoSIADao;
import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetalladoSIALista;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosRefs;
import es.eroski.misumi.model.GestionEurosSIA;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
public class DetalladoSIADaoImpl implements DetalladoSIADao{

	private JdbcTemplate jdbcTemplate;
	private static final int POSICION_PARAMETRO_SALIDA_CONSULTAR_DETALLADO= 7;
	private static final int POSICION_PARAMETRO_SALIDA_ACTUALIZAR_DETALLADO = 1;
	private static final int POSICION_PARAMETRO_SALIDA_NUEVA_REFERENCIA_DETALLADO= 3;

	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_OBJ = 1;
	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_CODERR = 2;
	private static final int POSICION_PARAMETRO_SALIDA_GESTION_EUROS_DESCERR = 3;

	private static Logger logger = Logger.getLogger(DetalladoSIADaoImpl.class);
	
	private String pkDetalladoByContext;
	private ServletContext context;
	
	@Autowired
	public DetalladoSIADaoImpl(ServletContext context, @Value("${pk.apr.detallado.misumi}") String pkDetalladoMisumi,
			@Value("${pk.apr.detallado.misumi.web2}") String pkDetalladoMisumiWeb2) {
		
		this.context = context;
		this.pkDetalladoByContext = context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2) ? pkDetalladoMisumiWeb2 : pkDetalladoMisumi;
		
	}

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	} 

	private DetalladoSIA mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtención de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);
		String codN1Ref_BD = (String)objectInfo[1];
		String codN2Ref_BD = (String)objectInfo[2];
		String codN3Ref_BD = (String)objectInfo[3];
		String codN4Ref_BD = (String)objectInfo[4];
		String codN5Ref_BD = (String)objectInfo[5];
		BigDecimal codArticulo_BD = ((BigDecimal)objectInfo[6]);
		String denomRef_BD = (String)objectInfo[7];
		BigDecimal stockTienda_BD = ((BigDecimal)objectInfo[8]);
		BigDecimal pendienteTienda_BD = ((BigDecimal)objectInfo[9]);
		BigDecimal pendienteTiendaManana_BD = ((BigDecimal)objectInfo[10]);
		BigDecimal unidCaja_BD = ((BigDecimal)objectInfo[11]);
		BigDecimal unidEncargoFl_BD = ((BigDecimal)objectInfo[12]);
		BigDecimal unidPropuestasFlOrigen_BD = ((BigDecimal)objectInfo[13]);
		BigDecimal unidPropuestasFlAnt_BD = ((BigDecimal)objectInfo[14]);
		BigDecimal unidPropuestasFlModif_BD = ((BigDecimal)objectInfo[15]);
		String tipoDetalado_BD = (String)objectInfo[16];
		String  horaLimite_BD = (String)objectInfo[17];
		String estado_BD = (String)objectInfo[18];   
		Timestamp fechaEntrega_BD = (Timestamp)objectInfo[19];
		Timestamp fechaPedidoProx_BD = (Timestamp)objectInfo[20];
		BigDecimal unidFlEmpuje_BD = ((BigDecimal)objectInfo[21]);
		BigDecimal codNecesidad_BD = ((BigDecimal)objectInfo[22]);
		BigDecimal ufp_BD = (BigDecimal)objectInfo[23];   
		String flgOferta_BD = (String)objectInfo[24];   
		String oferta_BD = (String)objectInfo[25];   
		BigDecimal tipoOferta_BD = ((BigDecimal)objectInfo[26]);
		String tipoAprov_BD = (String)objectInfo[27];   
		String tipo_BD = (String)objectInfo[28];
		String temporada_BD = (String)objectInfo[29];  	
		BigDecimal numOrden_BD =  ((BigDecimal)objectInfo[30]);
		String modeloProveedor_BD = (String)objectInfo[31];  	
		String descrTalla_BD = (String)objectInfo[32];  	
		String descrColor_BD = (String)objectInfo[33]; 
		String converArt_BD = (String)objectInfo[34]; 
		BigDecimal nivelLote_BD = ((BigDecimal)objectInfo[35]);
		BigDecimal facing_BD = ((BigDecimal)objectInfo[36]);
		String flgSIA_BD = (String)objectInfo[37];  
		BigDecimal codError_BD = ((BigDecimal)objectInfo[38]);
		String descError_BD = (String)objectInfo[39];
		String flgPropuesta_BD = (String)objectInfo[40];  

		//GESTION DE EUROS
		String denomArea_BD = (String)objectInfo[41];  
		String denomSeccion_BD = (String)objectInfo[42];  
		String denomCategoria_BD = (String)objectInfo[43];  
		String denomSubcategoria_BD = (String)objectInfo[44];  
		String denomSegmento_BD = (String)objectInfo[45];  
		BigDecimal precioCostoArticulo_BD = (BigDecimal)objectInfo[46];   
		BigDecimal precioCostoArtLineaInicial_BD = (BigDecimal)objectInfo[47];   
		BigDecimal precioCostoArtLineaFinal_BD = (BigDecimal)objectInfo[48];   


		//Transformación de datos para estructura de DetalladoSIA
		Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
		String codN1Ref = codN1Ref_BD;
		String codN2Ref = codN2Ref_BD;
		String codN3Ref = codN3Ref_BD;
		String codN4Ref = codN4Ref_BD;
		String codN5Ref = codN5Ref_BD;
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		String denomRef = denomRef_BD;
		Double stockTienda =((stockTienda_BD != null && !("".equals(stockTienda_BD.toString())))?new Double(stockTienda_BD.toString()):null);
		Double pendienteTienda = ((pendienteTienda_BD != null && !("".equals(pendienteTienda_BD.toString())))?new Double(pendienteTienda_BD.toString()):null);
		Double pendienteTiendaManana = ((pendienteTiendaManana_BD != null && !("".equals(pendienteTiendaManana_BD.toString())))?new Double(pendienteTiendaManana_BD.toString()):null);
		Double unidCaja = ((unidCaja_BD != null && !("".equals(unidCaja_BD.toString())))?new Double(unidCaja_BD.toString()):null);
		Long unidEncargoFl = ((unidEncargoFl_BD != null && !("".equals(unidEncargoFl_BD.toString())))?new Long(unidEncargoFl_BD.toString()):null);
		Long unidPropuestasFlOrigen = ((unidPropuestasFlOrigen_BD != null && !("".equals(unidPropuestasFlOrigen_BD.toString())))?new Long(unidPropuestasFlOrigen_BD.toString()):null);
		Long unidPropuestasFlAnt = ((unidPropuestasFlAnt_BD != null && !("".equals(unidPropuestasFlAnt_BD.toString())))?new Long(unidPropuestasFlAnt_BD.toString()):null);
		Double unidPropuestasFlModif = ((unidPropuestasFlModif_BD != null && !("".equals(unidPropuestasFlModif_BD.toString())))?new Double(unidPropuestasFlModif_BD.toString()):null);
		String tipoDetalado = tipoDetalado_BD;
		String  horaLimite = horaLimite_BD;
		String estado = estado_BD;   
		Date fechaEntrega = ((fechaEntrega_BD != null )?new Date(fechaEntrega_BD.getTime()):null);
		Date fechaPedidoProx = ((fechaPedidoProx_BD != null )?new Date(fechaPedidoProx_BD.getTime()):null);
		Long unidFlEmpuje = ((unidFlEmpuje_BD != null && !("".equals(unidFlEmpuje_BD.toString())))?new Long(unidFlEmpuje_BD.toString()):null);
		Long codNecesidad = ((codNecesidad_BD != null && !("".equals(codNecesidad_BD.toString())))?new Long(codNecesidad_BD.toString()):null);
		Double ufp = ((ufp_BD != null && !("".equals(ufp_BD.toString())))?new Double(ufp_BD.toString()):null);
		String flgOferta = flgOferta_BD;
		String oferta = oferta_BD;
		Long tipoOferta = ((tipoOferta_BD != null && !("".equals(tipoOferta_BD.toString())))?new Long(tipoOferta_BD.toString()):null);
		String tipoAprov = tipoAprov_BD;
		String  tipo = tipo_BD;
		String temporada = temporada_BD;
		Long numOrden = ((numOrden_BD != null && !("".equals(numOrden_BD.toString())))?new Long(numOrden_BD.toString()):null);
		String modeloProveedor = modeloProveedor_BD;
		String descrTalla = descrTalla_BD;
		String descrColor = descrColor_BD;
		String converArt = converArt_BD;
		Long nivelLote = ((nivelLote_BD != null && !("".equals(nivelLote_BD.toString())))?new Long(nivelLote_BD.toString()):null);
		Long facing = ((facing_BD != null && !("".equals(facing_BD.toString())))?new Long(facing_BD.toString()):null);
		String flgSIA = flgSIA_BD;
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		String flgPropuesta = flgPropuesta_BD;
		String denomArea = denomArea_BD;  
		String denomSeccion = denomSeccion_BD;  
		String denomCategoria = denomCategoria_BD;  
		String denomSubcategoria = denomSubcategoria_BD;  
		String denomSegmento = denomSegmento_BD;  
		Double precioCostoArticulo = ((precioCostoArticulo_BD != null && !("".equals(precioCostoArticulo_BD.toString())))?new Double(precioCostoArticulo_BD.toString()):null);
		Double precioCostoArtLineaInicial = ((precioCostoArtLineaInicial_BD != null && !("".equals(precioCostoArtLineaInicial_BD.toString())))?new Double(precioCostoArtLineaInicial_BD.toString()):null);
		Double precioCostoArtLineafinal = ((precioCostoArtLineaFinal_BD != null && !("".equals(precioCostoArtLineaFinal_BD.toString())))?new Double(precioCostoArtLineaFinal_BD.toString()):null);



		DetalladoSIA detalladoSIA =  new DetalladoSIA(codLoc, codN1Ref, codN2Ref, codN3Ref, codN4Ref, codN5Ref, 
				codArticulo,  denomRef,  stockTienda,  pendienteTienda, pendienteTiendaManana,  unidCaja,  unidEncargoFl,
				unidPropuestasFlOrigen, unidPropuestasFlAnt, unidPropuestasFlModif,
				tipoDetalado,  horaLimite,  estado, fechaEntrega, fechaPedidoProx, unidFlEmpuje, codNecesidad, codError, descError, ufp,  flgOferta,  oferta,  tipoOferta, flgSIA,
				tipoAprov, temporada, numOrden, modeloProveedor, descrTalla, descrColor, converArt, tipo, nivelLote, facing, flgPropuesta, 
				denomArea, denomSeccion, denomCategoria, denomSubcategoria, denomSegmento, 
				precioCostoArticulo, precioCostoArtLineaInicial, precioCostoArtLineafinal);


		return detalladoSIA;


	}

	private GestionEurosRefs mapRowGestionEurosRefs(STRUCT estructuraDatos) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtención de datos de la estructura de base de datos
		BigDecimal codArticulo_BD = ((BigDecimal)objectInfo[0]);
		BigDecimal codNecesidad_BD = ((BigDecimal)objectInfo[1]);
		BigDecimal unidsCaja_BD = ((BigDecimal)objectInfo[2]);
		BigDecimal precioCostoArticulo_BD = ((BigDecimal)objectInfo[3]);
		BigDecimal precioCostoArtLinealFinal_BD = ((BigDecimal)objectInfo[4]);
		BigDecimal unidPropuestasFlModif_BD = ((BigDecimal)objectInfo[5]);
		BigDecimal diferencia_BD = ((BigDecimal)objectInfo[6]);
		String refCumple_BD = (String)objectInfo[7];
		String aviso_BD = (String)objectInfo[8];
		
		//Transformación de datos para estructura de DetalladoSIA
		Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
		Long codNecesidad = ((codNecesidad_BD != null && !("".equals(codNecesidad_BD.toString())))?new Long(codNecesidad_BD.toString()):null);
		Double unidsCaja = ((unidsCaja_BD != null && !("".equals(unidsCaja_BD.toString())))?new Double(unidsCaja_BD.toString()):null);
		Double precioCostoArticulo = ((precioCostoArticulo_BD != null && !("".equals(precioCostoArticulo_BD.toString())))?new Double(precioCostoArticulo_BD.toString()):null);
		Double precioCostoArtLinealFinal = ((precioCostoArtLinealFinal_BD != null && !("".equals(precioCostoArtLinealFinal_BD.toString())))?new Double(precioCostoArtLinealFinal_BD.toString()):null);
		Long unidPropuestasFlModif = ((unidPropuestasFlModif_BD != null && !("".equals(unidPropuestasFlModif_BD.toString())))?new Long(unidPropuestasFlModif_BD.toString()):null);
		Long diferencia = ((diferencia_BD != null && !("".equals(diferencia_BD.toString())))?new Long(diferencia_BD.toString()):null);
		String refCumple = refCumple_BD;
		String aviso = aviso_BD;
		/*ARRAY listaDatos = (ARRAY)objectInfo[6];

		List<String> avisosLst = new ArrayList<String>();

		if (listaDatos!=null){
			ResultSet rs = listaDatos.getResultSet();			

			//Recorrido del listado de datos
			while (rs.next()) {
				STRUCT estructuraDatosAviso = (STRUCT) rs.getObject(2);
				String aviso = (String)estructuraDatosAviso.getAttributes()[0];

				avisosLst.add(aviso);
			}
		}*/  
		GestionEurosRefs gestionEurosRef =  new GestionEurosRefs(codArticulo, codNecesidad, unidsCaja, precioCostoArticulo, precioCostoArtLinealFinal, unidPropuestasFlModif, diferencia, refCumple, aviso);
		return gestionEurosRef;
	}

	@Override
	public DetalladoSIALista consultaDetallado(DetalladoSIA detalladoSIA) throws Exception {

		DetalladoSIALista salida = null;
		//Obtención de parámetros de consulta
		final String p_cod_loc = detalladoSIA.getCodLoc().toString();
		final String p_cod_n1 = ((detalladoSIA.getCodN1Ref() != null)? detalladoSIA.getCodN1Ref() : "");
		final String p_cod_n2 = ((detalladoSIA.getCodN2Ref() != null)? detalladoSIA.getCodN2Ref() : "");
		final String p_cod_n3 = ((detalladoSIA.getCodN3Ref() != null)? detalladoSIA.getCodN3Ref() : "");
		final String p_cod_n4 = ((detalladoSIA.getCodN4Ref() != null)? detalladoSIA.getCodN4Ref() : "");
		final String p_cod_n5 = ((detalladoSIA.getCodN5Ref() != null)? detalladoSIA.getCodN5Ref() : "");

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {				
				
				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						if (context.getContextPath().equalsIgnoreCase(Constantes.CONTEXTO_WEB2)){
							logger.error("##########################################");
							logger.error("El paquete de DETALLADO utilizado para este contexto (" + context.getContextPath() + ") es: "
									+ pkDetalladoByContext);
							logger.error("##########################################");
						}
						
  						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_consulta(?, ?, ?, ?, ?, ?,?) }");

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
						if (p_cod_n4 != null && !p_cod_n4.equals("")){
							cs.setString(5, p_cod_n4);
						}else{
							cs.setNull(5, OracleTypes.NULL);
						}
						if (p_cod_n5 != null && !p_cod_n5.equals("")){
							cs.setString(6, p_cod_n5);
						}else{
							cs.setNull(6, OracleTypes.NULL);
						}
						cs.registerOutParameter(7, OracleTypes.STRUCT, "APR_R_DETALLADO_REG");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoConsultaDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public DetalladoSIALista actualizarDetallado(List<DetalladoSIA> detalladoSIALista) throws Exception {

		DetalladoSIALista salida = null;
		//Obtención de parámetros de actualización
		final List<DetalladoSIA> listadoModificados = new ArrayList<DetalladoSIA>(); //Datos que se enviarán al procedimiento
		//Carga de lista de modificados
		for (int i=0; i<detalladoSIALista.size(); i++){
			DetalladoSIA detalladoSIA = detalladoSIALista.get(i);
			//Se guardan los modificados para pasarlos al procedimiento
			listadoModificados.add(detalladoSIA);
		}

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						//Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraActualizarDetallado(listadoModificados, con);

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_actualizar(?) }");
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_DETALLADO_REG");
						cs.setObject(1, itemConsulta);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = crearEstructuraActualizarDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public DetalladoRedondeo redondeoDetallado(DetalladoSIA detalladoSIA) throws Exception {

		DetalladoRedondeo salida = null;
		//Obtención de parámetros de consulta
		final String p_cod_loc = detalladoSIA.getCodLoc().toString();
		final String p_cod_art_formlog_ini = detalladoSIA.getCodArticulo().toString();
		final String p_cant_art_formlog_ini = ((detalladoSIA.getUnidPropuestasFlModif() != null)? detalladoSIA.getUnidPropuestasFlModif().toString() : "");



		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_redondeo(?, ?, ?, ?, ?, ?, ?, ?) }");

						//Parametros de entrada
						cs.setInt(1, Integer.parseInt(p_cod_loc));
						cs.setInt(2, Integer.parseInt(p_cod_art_formlog_ini));
						cs.setDouble(3, Double.parseDouble(p_cant_art_formlog_ini));

						//Parametros de salida
						cs.registerOutParameter(4, OracleTypes.NUMBER);
						cs.registerOutParameter(5, OracleTypes.NUMBER);
						cs.registerOutParameter(6, OracleTypes.NUMBER);
						cs.registerOutParameter(7, OracleTypes.NUMBER);
						cs.registerOutParameter(8, OracleTypes.VARCHAR);


					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoRedondeo ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoRedondeoDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoRedondeo) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public DetalladoSIALista referenciaNueva(DetalladoSIA detalladoSIA) throws Exception {

		DetalladoSIALista salida = null;
		//Obtención de parámetros de consulta
		final String p_cod_loc = detalladoSIA.getCodLoc().toString();
		final String p_cod_art_formlog = detalladoSIA.getCodArticulo().toString();

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {		                	

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_ref_nueva(?, ?, ?) }");

						cs.setInt(1, Integer.parseInt(p_cod_loc));
						cs.setInt(2, Integer.parseInt(p_cod_art_formlog));
						cs.registerOutParameter(3, OracleTypes.STRUCT, "APR_R_DETALLADO_REG");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					DetalladoSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoNuevaReferenciaDetallado(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (DetalladoSIALista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	public GestionEurosSIA gestionEuros(final GestionEuros gestionEuros) throws Exception {			    	
		GestionEurosSIA salida = null;		

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						//Crear estructura para actualización
						STRUCT itemConsulta = crearEstructuraGestionEurosDetallado(gestionEuros, con);

						cs = con.prepareCall("{call " + pkDetalladoByContext + ".p_apr_mis_gestion_euros(?,?,?) }");

						//Parametros de entrada
						cs.setObject(1, itemConsulta);					

						//Parametros de salida
						cs.registerOutParameter(1, OracleTypes.STRUCT, "APR_R_GESTION_EUROS");
						cs.registerOutParameter(2, OracleTypes.NUMBER);
						cs.registerOutParameter(3, OracleTypes.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					GestionEurosSIA ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoGestionEuros(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};

			try {
				salida = (GestionEurosSIA) this.jdbcTemplate.execute(csCreator,csCallback);
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

	private STRUCT crearEstructuraGestionEurosDetallado(GestionEuros gestionEuros, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		//COD_LOC    	                NUMBER,
		//RESPETAR_IMC                VARCHAR2(1 BYTE),
		//PRECIO_COSTO_FINAL	        NUMBER,
		//PRECIO_COSTO_FINAL_FINAL    NUMBER,
		//REFERENCIAS                 APR_T_R_GESTION_EUROS_REFS

		//Creamos el objeto que contendrá APR_R_GESTION_EUROS 
		Object[] objectGestionEuros = new Object[5];

		//Guardamos el codloc.
		objectGestionEuros[0] = gestionEuros.getCodLoc();		

		//Guardamos el IMC
		objectGestionEuros[1] = gestionEuros.getRespetarIMC();

		//Guardamos precio costo final
		objectGestionEuros[2] = gestionEuros.getPrecioCostoFinal();

		//Guardamos precio costo final final
		objectGestionEuros[3] = gestionEuros.getPrecioCostoFinalFinal();

		//Insertamos la lista de referencias de gestion de euros.
		objectGestionEuros[4] = crearEstructuraGestionEurosRefs(gestionEuros.getGestionEurosRefsLst(), conexionOracle);

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectGestionEuros);

		return itemConsulta;
	}

	private ARRAY crearEstructuraGestionEurosRefs(List<GestionEurosRefs> listaGestionEurosRefs, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectTabla;
		if(listaGestionEurosRefs != null){
			int numeroElementos = listaGestionEurosRefs.size();
			objectTabla = new Object[numeroElementos];

			for (int i=0; i<numeroElementos; i++){
				GestionEurosRefs gestionEurosRefs = (GestionEurosRefs)listaGestionEurosRefs.get(i);

				Object[] objectInfo = new Object[9];

				//COD_ARTICULO   	                NUMBER,
				//COD_NECESIDAD   	                NUMBER,
				//UNIDS_CAJA                       NUMBER,
				//PRECIO_COSTO_ARTICULO            NUMBER,
				//PRECIO_COSTO_ART_LINEAL_FINAL     NUMBER,
				//UNID_PROPUESTAS_FL_MODIF         NUMBER,
				//DIFERENCIA                       NUMBER,
				//REF_CUMPLE                       VARCHAR(1),
				//AVISOS                           APR_T_R_GESTION_EUROS_REFS_AV		     
				objectInfo[0] = gestionEurosRefs.getCodArticulo() != null ? gestionEurosRefs.getCodArticulo() : null;
				objectInfo[1] = gestionEurosRefs.getCodNecesidad() != null ? gestionEurosRefs.getCodNecesidad() : null;
				objectInfo[2] = gestionEurosRefs.getUnidsCaja() != null ? gestionEurosRefs.getUnidsCaja() : null;
				objectInfo[3] = gestionEurosRefs.getPrecioCostoArticulo() != null ? gestionEurosRefs.getPrecioCostoArticulo() : null;
				objectInfo[4] = gestionEurosRefs.getPrecioCostoArtLinealFinal() != null ? gestionEurosRefs.getPrecioCostoArtLinealFinal() : null;
				objectInfo[5] = gestionEurosRefs.getUnidPropuestasFlModif() != null ? gestionEurosRefs.getUnidPropuestasFlModif() : null;
				objectInfo[6] = gestionEurosRefs.getDiferencia() != null ? gestionEurosRefs.getDiferencia() : null;	
				objectInfo[7] = gestionEurosRefs.getRefCumple();
				objectInfo[8] = gestionEurosRefs.getAvisos();

				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS",conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

				objectTabla[i] = itemObjectStruct;
			}
		}else{
			objectTabla = new Object[1];

			Object[] objectInfo = new Object[7];

			//COD_ARTICULO   	                NUMBER,
			//COD_NECESIDAD   	                NUMBER,
			//UNIDS_CAJA                       NUMBER,
			//PRECIO_COSTO_ARTICULO            NUMBER,
			//PRECIO_COSTO_ART_LINEAL_FINAL     NUMBER,
			//UNID_PROPUESTAS_FL_MODIF         NUMBER,
			//DIFERENCIA                       NUMBER,
			//REF_CUMPLE                       VARCHAR(1),
			//AVISOS                           APR_T_R_GESTION_EUROS_REFS_AV		     
			objectInfo[0] = null;
			objectInfo[1] = null;
			objectInfo[2] = null;
			objectInfo[3] = null;
			objectInfo[4] = null;
			objectInfo[5] = null;		
			objectInfo[6] = null;
			objectInfo[7] = null;
			objectInfo[8] = null;//crearEstructuraAvisos(null, conexionOracle);

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
			
			objectTabla[0] = itemObjectStruct;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_GESTION_EUROS_REFS", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		return array;
	}

	private ARRAY crearEstructuraAvisos(List<String> listaAvisos, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		Object[] objectTabla;
		if(listaAvisos != null){
			int numeroElementos = listaAvisos.size();
			objectTabla = new Object[numeroElementos];
		
			for (int i=0; i<numeroElementos; i++){
				String aviso = (String)listaAvisos.get(i);
	
				Object[] objectInfo = new Object[1];			
				objectInfo[0] = aviso;
	
				StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS_AV",conexionOracle);
				STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
	
				objectTabla[i] = itemObjectStruct;
			}
		}else{
			objectTabla = new Object[1];
			
			Object[] objectInfo = new Object[1];			
			objectInfo[0] = null;

			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_GESTION_EUROS_REFS_AV",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTabla[0] = itemObjectStruct;
		}
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_GESTION_EUROS_REFS_AV", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		return array;
	}

	private GestionEurosSIA obtenerResultadoGestionEuros(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraGestionEuros(cs, rs, this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_OBJ, this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_CODERR,this.POSICION_PARAMETRO_SALIDA_GESTION_EUROS_DESCERR);
	}

	private DetalladoSIALista crearEstructuraActualizarDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_ACTUALIZAR_DETALLADO);
	}

	private DetalladoSIALista obtenerResultadoNuevaReferenciaDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_NUEVA_REFERENCIA_DETALLADO);
	}

	private DetalladoSIALista obtenerResultadoConsultaDetallado(CallableStatement cs, ResultSet rs){
		return obtenerEstructuraDetalladoSIA(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONSULTAR_DETALLADO);
	}

	private GestionEurosSIA obtenerEstructuraGestionEuros(CallableStatement cs, ResultSet rs, int idParametroResultado1,int idParametroResultado2,int idParametroResultado3){
		GestionEurosSIA gestionEurosSIA = null;
		GestionEuros gestionEuros = null;
		List<GestionEurosRefs> listaGestionEurosRefs = new ArrayList<GestionEurosRefs>();

		try{
			//Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado1);

			//Obtención de los parámetros de salida en crudo
			BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado2);
			String descError_BD = (String)cs.getString(idParametroResultado3);

			//Transformación de datos para estructura de GestionEuros
			Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
			String descError = descError_BD;

			//Control de error en la obtención de datos
			if (new Long("0").equals(codError)){ //El proceso se ha ejecutado correctamente				
				Object[] objectInfo = estructuraResultado.getAttributes();

				BigDecimal codLoc_BD = (BigDecimal) objectInfo[0];
				String respetarIMC_BD = (String) objectInfo[1];
				BigDecimal precioCostoFinal_BD = (BigDecimal) objectInfo[2];
				BigDecimal precioCostoFinalFinal_BD = (BigDecimal) objectInfo[3];
				ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[4];

				Long codLoc = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
				String respetarIMC = respetarIMC_BD;
				Double precioCostoFinal = ((precioCostoFinal_BD != null && !("".equals(precioCostoFinal_BD.toString())))?new Double(precioCostoFinal_BD.toString()):null);
				Double precioCostoFinalFinal = ((precioCostoFinalFinal_BD != null && !("".equals(precioCostoFinalFinal_BD.toString())))?new Double(precioCostoFinalFinal_BD.toString()):null);				

				if (listaDatos!=null){
					rs = listaDatos.getResultSet();

					//Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						GestionEurosRefs gestionEurosRef = this.mapRowGestionEurosRefs(estructuraDatos);
						listaGestionEurosRefs.add(gestionEurosRef);
					}
				}   
				gestionEuros = new GestionEuros(codLoc, respetarIMC, precioCostoFinal, precioCostoFinalFinal, listaGestionEurosRefs);
			}		
			gestionEurosSIA = new GestionEurosSIA(codError, descError, gestionEuros);
		} catch (Exception e) {
			e.printStackTrace();
		}						
		return gestionEurosSIA;
	}

	private DetalladoSIALista obtenerEstructuraDetalladoSIA(CallableStatement cs, ResultSet rs, int idParametroResultado){

		DetalladoSIALista detalladoSIALista = new DetalladoSIALista();
		List<DetalladoSIA> listaDetalladoSIA = new ArrayList<DetalladoSIA>();

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
						DetalladoSIA detalladoSIA = this.mapRow(estructuraDatos, rowNum);
						listaDetalladoSIA.add(detalladoSIA);
						rowNum++;
					}
				}            		


				detalladoSIALista.setDatos(listaDetalladoSIA);
			}

			detalladoSIALista.setEstado(new Long(estado.toString()));
			detalladoSIALista.setDescEstado(descEstado);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return detalladoSIALista;
	}


	private DetalladoRedondeo obtenerResultadoRedondeoDetallado(CallableStatement cs, ResultSet rs){

		DetalladoRedondeo detalladoRedondeo = new DetalladoRedondeo();
		List<DetalladoSIA> listaDetalladoSIA = new ArrayList<DetalladoSIA>();

		try{
			//Obtención del parámetro de salida
			detalladoRedondeo.setCodArticulo((Long)cs.getLong(4));

			Double unidades = cs.getDouble(5);
			Double unidadesCaja = cs.getDouble(6);

			Double cajas = Math.round(Math.ceil(new Double(unidades.toString())/new Double(unidadesCaja.toString()))*100.0)/100.0;

			detalladoRedondeo.setUnidCaja(unidadesCaja);	
			detalladoRedondeo.setUnidPropuestasFlModif(cajas);

			detalladoRedondeo.setCodError((Long)cs.getLong(7));
			detalladoRedondeo.setDescError((String)cs.getString(8));


		} catch (Exception e) {
			e.printStackTrace();

		}

		return detalladoRedondeo;
	}

	private STRUCT crearEstructuraActualizarDetallado(List<DetalladoSIA> listaModificados, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesaria para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaModificados.size();
		Object[] objectTabla = new Object[numeroElementos];

		for (int i=0; i<numeroElementos; i++){

			DetalladoSIA detalladoSIA = (DetalladoSIA)listaModificados.get(i);

			Object[] objectInfo = new Object[49];

			//Sólo se informan los datos necesarios

			objectInfo[0] = (detalladoSIA.getCodLoc()!=null?detalladoSIA.getCodLoc():null);
			objectInfo[1] = detalladoSIA.getCodN1Ref();
			objectInfo[2] = detalladoSIA.getCodN2Ref();
			objectInfo[3] = detalladoSIA.getCodN3Ref();
			objectInfo[4] = detalladoSIA.getCodN4Ref();
			objectInfo[5] = detalladoSIA.getCodN5Ref();		
			objectInfo[6] = (detalladoSIA.getCodArticulo()!=null?detalladoSIA.getCodArticulo():null);	
			objectInfo[7] = detalladoSIA.getDenomRef();
			objectInfo[8] = (detalladoSIA.getStockTienda()!=null?detalladoSIA.getStockTienda():null);
			objectInfo[9] = (detalladoSIA.getPendienteTienda()!=null?detalladoSIA.getPendienteTienda():null);
			objectInfo[10] = (detalladoSIA.getPendienteTiendaManana()!=null?detalladoSIA.getPendienteTiendaManana():null);
			objectInfo[11] = (detalladoSIA.getUnidCaja()!=null?detalladoSIA.getUnidCaja():null);
			objectInfo[12] = (detalladoSIA.getUnidEncargoFl()!=null?detalladoSIA.getUnidEncargoFl():null);
			objectInfo[13] = (detalladoSIA.getUnidPropuestasFlOrigen()!=null?detalladoSIA.getUnidPropuestasFlOrigen():null);
			objectInfo[14] = (detalladoSIA.getUnidPropuestasFlAnt()!=null?detalladoSIA.getUnidPropuestasFlAnt():null);
			objectInfo[15] = (detalladoSIA.getUnidPropuestasFlModif()!=null?detalladoSIA.getUnidPropuestasFlModif():null);
			objectInfo[16] = detalladoSIA.getTipoDetallado();
			objectInfo[17] = detalladoSIA.getHoraLimite();
			objectInfo[18] = detalladoSIA.getEstado();
			objectInfo[19] = (detalladoSIA.getFechaEntrega()!=null?new Timestamp(detalladoSIA.getFechaEntrega().getTime()):null);
			objectInfo[20] = (detalladoSIA.getFechaPedidoProx()!=null?new Timestamp(detalladoSIA.getFechaPedidoProx().getTime()):null);
			objectInfo[21] = detalladoSIA.getUnidFlEmpuje();
			objectInfo[22] = detalladoSIA.getCodNecesidad();
			objectInfo[23] = (detalladoSIA.getUfp()!=null?detalladoSIA.getUfp():null);
			objectInfo[24] = detalladoSIA.getFlgOferta();
			objectInfo[25] = detalladoSIA.getOferta();
			objectInfo[26] = (detalladoSIA.getTipoOferta()!=null?detalladoSIA.getTipoOferta():null);
			objectInfo[27] = detalladoSIA.getTipoAprov();
			objectInfo[28] = detalladoSIA.getTipo();
			objectInfo[29] = detalladoSIA.getTemporada();
			objectInfo[30] = (detalladoSIA.getNumOrden()!=null?detalladoSIA.getNumOrden():null);	
			objectInfo[31] = detalladoSIA.getModeloProveedor();
			objectInfo[32] = detalladoSIA.getDescrTalla();
			objectInfo[33] = detalladoSIA.getDescrColor();
			objectInfo[34] = detalladoSIA.getConverArt();
			objectInfo[35] = detalladoSIA.getNivelLote();
			objectInfo[36] = detalladoSIA.getFacing();
			objectInfo[37] = detalladoSIA.getFlgSIA();
			objectInfo[38] = (detalladoSIA.getCodError()!=null?detalladoSIA.getCodError():null);	
			objectInfo[39] = detalladoSIA.getDescError();
			objectInfo[40] = detalladoSIA.getFlgPropuesta();
			//GESTION DE EUROS
			objectInfo[41] = detalladoSIA.getDenomCategoria();
			objectInfo[42] = detalladoSIA.getDenomSeccion();
			objectInfo[43] = detalladoSIA.getDenomCategoria();
			objectInfo[44] = detalladoSIA.getDenomSubcategoria();
			objectInfo[45] = detalladoSIA.getDenomSeccion();
			objectInfo[46] = (detalladoSIA.getUfp()!=null?detalladoSIA.getUfp():null);
			objectInfo[47] = (detalladoSIA.getUfp()!=null?detalladoSIA.getUfp():null);
			objectInfo[48] = (detalladoSIA.getUfp()!=null?detalladoSIA.getUfp():null);


			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_DETALLADO_DAT",conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);

			objectTabla[i] = itemObjectStruct;

		}

		Object[] objectConsulta = new Object[3]; //Tiene 3 campos pero sólo nos interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_DETALLADO_DAT", conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_DETALLADO_REG",conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);

		return itemConsulta;
	}



	@Override
	public List<DetallePedidoModificados> modifyProcedureSIAData(List<DetallePedido> lista, Long codCentro) throws Exception {		


		List<DetalladoSIA> detalladoSIALista = new ArrayList<DetalladoSIA>();

		DetalladoSIALista resultadoProc= new DetalladoSIALista();

		DetalladoSIA campo = new DetalladoSIA();

		List<DetallePedidoModificados> resultado  = new ArrayList<DetallePedidoModificados>();


		for (int i=0;i<lista.size();i++){
			campo = new DetalladoSIA();
			campo.setCodLoc((codCentro != null && !("".equals(codCentro.toString())))?codCentro:null);
			campo.setCodN1Ref((lista.get(i).getGrupo1() != null && !("".equals(lista.get(i).getGrupo1().toString())))?lista.get(i).getGrupo1().toString() :null);
			campo.setCodN2Ref((lista.get(i).getGrupo2() != null && !("".equals(lista.get(i).getGrupo2().toString())))?lista.get(i).getGrupo2().toString() :null);
			campo.setCodN3Ref((lista.get(i).getGrupo3() != null && !("".equals(lista.get(i).getGrupo3().toString())))?lista.get(i).getGrupo3().toString() :null);
			campo.setCodN4Ref((lista.get(i).getGrupo4() != null && !("".equals(lista.get(i).getGrupo4().toString())))?lista.get(i).getGrupo4().toString() :null);
			campo.setCodN5Ref((lista.get(i).getGrupo5() != null && !("".equals(lista.get(i).getGrupo5().toString())))?lista.get(i).getGrupo5().toString() :null);
			campo.setCodArticulo((lista.get(i).getCodArticuloEroski() != null && !("".equals(lista.get(i).getCodArticuloEroski().toString())))?lista.get(i).getCodArticuloEroski():null);	
			campo.setDenomRef((lista.get(i).getDescriptionArtEroski() != null && !("".equals(lista.get(i).getDescriptionArtEroski().toString())))?lista.get(i).getDescriptionArtEroski() :null);
			campo.setStockTienda((lista.get(i).getStock() != null && !("".equals(lista.get(i).getStock().toString())))?lista.get(i).getStock() :null);
			campo.setUnidEncargoFl((lista.get(i).getCajasPedidas() != null && !("".equals(lista.get(i).getCajasPedidas().toString())))?lista.get(i).getCajasPedidas() :null);
			campo.setUnidPropuestasFlModif((double)((lista.get(i).getCantidad() != null && !("".equals(lista.get(i).getCantidad().toString())))?lista.get(i).getCantidad() :null));
			campo.setUnidPropuestasFlAnt((lista.get(i).getCantidadAnt() != null && !("".equals(lista.get(i).getCantidadAnt().toString())))?lista.get(i).getCantidadAnt() :null);
			campo.setUnidEncargoFl((lista.get(i).getCajasPedidas() != null && !("".equals(lista.get(i).getCajasPedidas().toString())))?lista.get(i).getCajasPedidas() :null);
			campo.setEstado((lista.get(i).getEstadoPedido() != null && !("".equals(lista.get(i).getEstadoPedido().toString())))?lista.get(i).getEstadoPedido() :null);
			campo.setUnidPropuestasFlOrigen((lista.get(i).getPropuesta() != null && !("".equals(lista.get(i).getPropuesta().toString())))?lista.get(i).getPropuesta() :null);
			campo.setTipoDetallado((lista.get(i).getTipoDetallado() != null && !("".equals(lista.get(i).getTipoDetallado().toString())))?lista.get(i).getTipoDetallado().toString() :null);
			campo.setUnidCaja((lista.get(i).getUnidadesCaja() != null && !("".equals(lista.get(i).getUnidadesCaja().toString())))?lista.get(i).getUnidadesCaja():null);

			campo.setPendienteTienda(lista.get(i).getEnCurso1() != null && !("".equals(lista.get(i).getEnCurso1().toString()))?new Double(lista.get(i).getEnCurso1().toString()):new Double(0)); 
			campo.setPendienteTiendaManana(lista.get(i).getEnCurso2() != null && !("".equals(lista.get(i).getEnCurso2().toString()))?new Double(lista.get(i).getEnCurso2().toString()):new Double(0));
			campo.setHoraLimite((lista.get(i).getHoraLimite() != null && !("".equals(lista.get(i).getHoraLimite().toString())))?lista.get(i).getHoraLimite() :null);


			campo.setFechaEntrega((lista.get(i).getFechaEntrega() != null && !("".equals(lista.get(i).getFechaEntrega().toString())))?lista.get(i).getFechaEntrega():null);
//			campo.setFechaEntrega((lista.get(i).getNextDayPedido() != null && !("".equals(lista.get(i).getNextDayPedido().toString())))?Utilidades.convertirStringAFecha(lista.get(i).getNextDayPedido()):null);

			campo.setUnidFlEmpuje((lista.get(i).getUnidFlEmpuje() != null && !("".equals(lista.get(i).getUnidFlEmpuje().toString())))?new Long(lista.get(i).getUnidFlEmpuje().toString()):null);
			campo.setCodNecesidad((lista.get(i).getCodNecesidad() != null && !("".equals(lista.get(i).getCodNecesidad().toString())))?new Long(lista.get(i).getCodNecesidad().toString()):null);

			campo.setUfp(lista.get(i).getUfp() != null && !("".equals(lista.get(i).getUfp().toString()))?new Double(lista.get(i).getUfp().toString()):new Double(0));


			campo.setFlgOferta((lista.get(i).getFlgOferta() != null && !("".equals(lista.get(i).getFlgOferta().toString())))?lista.get(i).getFlgOferta() :null);
			campo.setOferta((lista.get(i).getOferta() != null && !("".equals(lista.get(i).getOferta().toString())))?lista.get(i).getOferta() :null);
			campo.setTipoOferta((lista.get(i).getTipoOferta() != null && !("".equals(lista.get(i).getUfp().toString())))?new Long(lista.get(i).getUfp().toString()):null);

			campo.setFlgSIA((lista.get(i).getFlgSIA() != null && !("".equals(lista.get(i).getFlgSIA().toString())))?lista.get(i).getFlgSIA() :null);

			campo.setPrecioCostoArticulo(lista.get(i).getPrecioCostoArticulo() != null && !("".equals(lista.get(i).getPrecioCostoArticulo().toString()))?new Double(lista.get(i).getPrecioCostoArticulo().toString()):new Double(0));
			campo.setPrecioCostoInicial(lista.get(i).getPrecioCostoInicial() != null && !("".equals(lista.get(i).getPrecioCostoInicial().toString()))?new Double(lista.get(i).getPrecioCostoInicial().toString()):new Double(0));
			campo.setPrecioCostoFinal(lista.get(i).getPrecioCostoFinal() != null && !("".equals(lista.get(i).getPrecioCostoFinal().toString()))?new Double(lista.get(i).getPrecioCostoFinal().toString()):new Double(0));

			detalladoSIALista.add(campo);
		}


		//Llamamos al procedimiento para obtener el resultado.
		resultadoProc = this.actualizarDetallado(detalladoSIALista);


		if (null !=resultadoProc && new Long(0).equals(resultadoProc.getEstado())) {
			resultado = tratarDatosObtenerProc(resultadoProc);	
		}else{
			//throw new RuntimeException(resultadoProc.getDescEstado()!=null?resultadoProc.getDescEstado():null);
		}


		return resultado;
	}



	private List<DetallePedidoModificados> tratarDatosObtenerProc(DetalladoSIALista resultProc){

		//Transformaci�n de datos para estructura de PedidoAdicionalM
		List<DetallePedidoModificados> resultado = new ArrayList<DetallePedidoModificados>();
		List<DetalladoSIA> detalladoSIAListaAux = new ArrayList<DetalladoSIA>();

		DetallePedidoModificados filaResultado = new DetallePedidoModificados();

		if (null != resultProc.getDatos()){
			detalladoSIAListaAux = resultProc.getDatos();
		}

		//Nos recorremos la lista 
		for (int i=0;i<detalladoSIAListaAux.size();i++){
			filaResultado = new DetallePedidoModificados();
			filaResultado.setCodCentro((detalladoSIAListaAux.get(i).getCodLoc() != null && !("".equals(detalladoSIAListaAux.get(i).getCodLoc().toString())))?new Long(detalladoSIAListaAux.get(i).getCodLoc().toString()):null);
			filaResultado.setCodArticulo((detalladoSIAListaAux.get(i).getCodArticulo() != null && !("".equals(detalladoSIAListaAux.get(i).getCodArticulo().toString())))?new Long(detalladoSIAListaAux.get(i).getCodArticulo().toString()):null);
			filaResultado.setCantidad((detalladoSIAListaAux.get(i).getUnidPropuestasFlModif() != null && !("".equals(detalladoSIAListaAux.get(i).getUnidPropuestasFlModif().toString())))?detalladoSIAListaAux.get(i).getUnidPropuestasFlModif().longValue():null);
			filaResultado.setEstadoPedido((detalladoSIAListaAux.get(i).getEstado() != null && !("".equals(detalladoSIAListaAux.get(i).getEstado().toString())))?detalladoSIAListaAux.get(i).getEstado().toString():null);
			filaResultado.setTipoDetallado((detalladoSIAListaAux.get(i).getTipoDetallado() != null && !("".equals(detalladoSIAListaAux.get(i).getTipoDetallado().toString())))?detalladoSIAListaAux.get(i).getTipoDetallado().toString():null);
			filaResultado.setCantidadAnt((detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt() != null && !("".equals(detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt().toString())))?detalladoSIAListaAux.get(i).getUnidPropuestasFlAnt():null);
			filaResultado.setCodNecesidad((detalladoSIAListaAux.get(i).getCodNecesidad() != null && !("".equals(detalladoSIAListaAux.get(i).getCodNecesidad().toString())))?detalladoSIAListaAux.get(i).getCodNecesidad():null);
			filaResultado.setUnidadesCaja((detalladoSIAListaAux.get(i).getUnidCaja() != null && !("".equals(detalladoSIAListaAux.get(i).getUnidCaja())))?detalladoSIAListaAux.get(i).getUnidCaja():null);

			if (!(new Long(0).equals(detalladoSIAListaAux.get(i).getCodError()))) {
				filaResultado.setEstadoGrid("-1");
				filaResultado.setResultadoWS(detalladoSIAListaAux.get(i).getCodError()+ "-"+detalladoSIAListaAux.get(i).getDescError());	
			}else{
				filaResultado.setEstadoGrid("3");
			}


			resultado.add(filaResultado);
		}
		return resultado;
	}
}
