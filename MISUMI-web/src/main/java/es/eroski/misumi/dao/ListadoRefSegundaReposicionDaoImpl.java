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

import es.eroski.misumi.dao.iface.ListadoRefSegundaReposicionDao;
import es.eroski.misumi.model.ListadoRefSegundaReposicion;
import es.eroski.misumi.model.ListadoRefSegundaReposicionSalida;
import es.eroski.misumi.model.ReferenciasSegundaReposicion;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;


 	
@Repository
public class ListadoRefSegundaReposicionDaoImpl implements ListadoRefSegundaReposicionDao{
	private static Logger logger = Logger.getLogger(ListadoRefSegundaReposicionDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	 
	private static final int POSICION_PARAMETRO_SALIDA_LISTADO = 6;
	
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}
	    
    @Override
    public ReferenciasSegundaReposicion findAll(ListadoRefSegundaReposicion filtroListadoRefSegundaReposicion) throws Exception {
    	ReferenciasSegundaReposicion salida = null;
    	//Obtención de parámetros de consulta
    	final Long p_cod_loc = filtroListadoRefSegundaReposicion.getCodCentro();
    	final int mes=filtroListadoRefSegundaReposicion.getMes();
    	final String grupo1 = filtroListadoRefSegundaReposicion.getGrupo1().toString();
    	final String grupo2 = filtroListadoRefSegundaReposicion.getGrupo2().toString();
    	final List<Long> categorias=filtroListadoRefSegundaReposicion.getListadoSeleccionados();
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {
	                	//PK_APR_MISUMI.p_roturas_capacidad   (  p_cod_loc_dest, p_mes, p_cod_n1, p_cod_n2, p_cod_n3, p_tabla);
	                	cs = con.prepareCall("{call PK_APR_MISUMI.P_ROTURAS_CAPACIDAD(?, ?, ?, ?, ?, ?) }");
	                    cs.setLong(1, p_cod_loc);//CENTRO
	                    cs.setInt(2, mes);//MES A BUSCAR
	                    cs.setString(3, grupo1);//AREA
	                    cs.setString(4, grupo2);//SECCION
	                    //Crear estructura para recarga
	                	STRUCT itemConsulta = crearEstructuraCategorias(categorias, con);
	                	cs.setObject(5, itemConsulta);//LISTA DE CATEGORIAS
	                    cs.registerOutParameter(6, OracleTypes.STRUCT, "APR_R_ROTURAS_CAP_REG");

	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	ReferenciasSegundaReposicion ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerListado(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	        try {
	        	salida = (ReferenciasSegundaReposicion) this.jdbcTemplate.execute(csCreator,csCallback);
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
    
    private STRUCT crearEstructuraCategorias(List<Long> listaCategorias, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaCategorias.size();
		Object[] objectTabla = new Object[numeroElementos];
		
		for (int i=0; i<numeroElementos; i++){
			
			Long categoria = (Long)listaCategorias.get(i);
        	
        	Object[] objectInfo = new Object[1];
        	
        	//Sólo se informan la categoria
        	objectInfo[0] = categoria;
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("APR_R_COD_N3_DAT",conexionOracle);
	    	STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
	    	
	    	objectTabla[i] = itemObjectStruct;
	    	
		}
	    
		Object[] objectConsulta = new Object[1]; //Tiene 1 campo con la lista de códigos de categorias
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_COD_N3_DAT", conexionOracle);
	    ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);
    	
    	objectConsulta[0] = array;
    	
    	StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("APR_R_COD_N3_REG",conexionOracle);
    	STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);
    	
    	return itemConsulta;
    }
    
    private ReferenciasSegundaReposicion obtenerListado(CallableStatement cs, ResultSet rs) throws SQLException {
    	ReferenciasSegundaReposicion referenciasSegundaReposicion= null;
		List<ListadoRefSegundaReposicionSalida> listadoRefSegundaReposicionSalidaList = new ArrayList<ListadoRefSegundaReposicionSalida>();

		//Control de error en la obtención de datos
		STRUCT estructuraResultado = (STRUCT)cs.getObject(POSICION_PARAMETRO_SALIDA_LISTADO);
		
		BigDecimal codError_BD = (BigDecimal) estructuraResultado.getAttributes()[1];
		String descError_BD = (String) estructuraResultado.getAttributes()[2];
		//Transformación de datos para estructura de CalendarioDescripcionServicios
		
		if (new BigDecimal("0").equals(codError_BD)){ 
			//Obtenemos el array del objeto
			ARRAY listadoProcesoSalida = (ARRAY) estructuraResultado.getAttributes()[0];
			//Obtención de los datos de salida
			if (listadoProcesoSalida!=null){
				rs = listadoProcesoSalida.getResultSet();
				//Recorrido del listado de datos
				while (rs.next()) {
					STRUCT estructuraListadoSalida = (STRUCT)rs.getObject(2);
					//Obtener datos de estructuraListadoSalida en crudo
					
					ListadoRefSegundaReposicionSalida listadoRefSegundaReposicionSalida=this.mapRow(estructuraListadoSalida);
					
					listadoRefSegundaReposicionSalidaList.add(listadoRefSegundaReposicionSalida);
				}
			} 
		}
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		referenciasSegundaReposicion=new ReferenciasSegundaReposicion(codError, descError_BD, listadoRefSegundaReposicionSalidaList);
		return referenciasSegundaReposicion;
	}
    
    private ListadoRefSegundaReposicionSalida mapRow(STRUCT estructuraDatos) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		ListadoRefSegundaReposicionSalida fila = new ListadoRefSegundaReposicionSalida();

		// Obtención de datos de la estructura de base de datos
		BigDecimal codArea = ((BigDecimal) objectInfo[0]);
		fila.setCodN1(codArea.toString());
		String descArea = (String)objectInfo[1];
		fila.setDescCodN1(descArea);
		BigDecimal codSeccion = ((BigDecimal) objectInfo[2]);
		fila.setCodN2(codSeccion.toString());
		String descSeccion = (String)objectInfo[3];
		fila.setDescCodN2(descSeccion);
		BigDecimal codCategoria = ((BigDecimal) objectInfo[4]);
		fila.setCodN3(codCategoria.toString());
		String descCategoria = (String)objectInfo[5];
		fila.setDescCodN3(descCategoria);
		BigDecimal codSubcategoria = ((BigDecimal) objectInfo[6]);
		fila.setCodN4(codSubcategoria.toString());
		String descSubcategoria = (String)objectInfo[7];
		fila.setDescCodN4(descSubcategoria);
		BigDecimal codSegmento = ((BigDecimal) objectInfo[8]);
		fila.setCodN5(codSegmento.toString());
		String descSegmento = (String)objectInfo[9];
		fila.setDescCodN5(descSegmento);
		BigDecimal referencia=((BigDecimal) objectInfo[10]);
		fila.setReferencia(referencia.toString());
		String descripcion=(String)objectInfo[11];
		fila.setReferenciaDesc(descripcion.toString());
		BigDecimal facing=((BigDecimal) objectInfo[12]);
		fila.setFacing(facing.toString());
		BigDecimal capacidad=((BigDecimal) objectInfo[13]);
		fila.setCapacidad(capacidad.toString());
		String cajaExpositora=(String)objectInfo[14];
		fila.setCajaExpositora(cajaExpositora.toString());
		BigDecimal tendencia_BBDD=((BigDecimal) objectInfo[15]);
		tendencia_BBDD=tendencia_BBDD.setScale(2, BigDecimal.ROUND_HALF_UP);
		Double tendencia = ((tendencia_BBDD != null && !("".equals(tendencia_BBDD.toString())))?new Double(tendencia_BBDD.toString()):null);
		fila.setTendencia(tendencia);
		BigDecimal ventaPrevista_BBDD=((BigDecimal) objectInfo[16]);
		Double ventaPrevista = ((ventaPrevista_BBDD != null && !("".equals(ventaPrevista_BBDD.toString())))?new Double(ventaPrevista_BBDD.toString()):null);
		fila.setVentaPrevista(ventaPrevista);
		return fila;
	}
}
