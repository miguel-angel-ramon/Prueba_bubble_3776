package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoHTNoPblDao;
import es.eroski.misumi.model.PedidoAdicionalEC;
import es.eroski.misumi.model.PedidoHTNoPbl;
import es.eroski.misumi.model.PedidoHTNoPblLista;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PedidoHTNoPblDaoImpl implements PedidoHTNoPblDao{
	
	 private static Logger logger = Logger.getLogger(PedidoHTNoPblDaoImpl.class);
	 private JdbcTemplate jdbcTemplate;
	 private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_PEDIDOS_HT_NO_PBL = 11;

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
	    private PedidoHTNoPbl mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
            
            //Obtención de datos de la estructura de base de datos
			BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);
			BigDecimal codArticulo_BD = (BigDecimal)objectInfo[1];
			BigDecimal identificador_BD = (BigDecimal)objectInfo[2];
			BigDecimal anoOferta_BD = (BigDecimal)objectInfo[3];
			BigDecimal numOferta_BD = (BigDecimal)objectInfo[4];
			String denomInforme_BD = (String)objectInfo[5];
			BigDecimal uniCajaServ_BD = (BigDecimal)objectInfo[6];
			String tipoAprov_BD = (String)objectInfo[7];
			String agrupacion_BD = (String)objectInfo[8];
			Date fechaInicio_BD = (Date)objectInfo[9];
			BigDecimal cant1_BD = (BigDecimal)objectInfo[10];
			Date fecha2_BD = (Date)objectInfo[11];
			BigDecimal cant2_BD = (BigDecimal)objectInfo[12];
			Date fecha3_BD = (Date)objectInfo[13];
			BigDecimal cant3_BD = (BigDecimal)objectInfo[14];
			Date fecha4_BD = (Date)objectInfo[15];
			BigDecimal cant4_BD = (BigDecimal)objectInfo[16];
			Date fecha5_BD = (Date)objectInfo[17];
			BigDecimal cant5_BD = (BigDecimal)objectInfo[18];
			Date fechaFin_BD = (Date)objectInfo[19];
			String denomOferta_BD = (String)objectInfo[20];
			BigDecimal cantMin_BD = (BigDecimal)objectInfo[21];
			BigDecimal cantMax_BD = (BigDecimal)objectInfo[22];
			Date fechaHasta_BD = (Date)objectInfo[23];
			String tipoPed_BD = (String)objectInfo[24];
			
            //Transformación de datos para estructura de PedidoHtNoPbl
            Long codCentro = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
			Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
			Long identificador = ((identificador_BD != null && !("".equals(identificador_BD.toString())))?new Long(identificador_BD.toString()):null);
			String anoOferta = ((anoOferta_BD != null && !("".equals(anoOferta_BD.toString())))?anoOferta_BD.toString():null);
			String numOferta = ((numOferta_BD != null && !("".equals(numOferta_BD.toString())))?numOferta_BD.toString():null);
			String descriptionArt = denomInforme_BD;
			Double uniCajaServ = ((uniCajaServ_BD != null && !("".equals(uniCajaServ_BD.toString())))?new Double(uniCajaServ_BD.toString()):null);
			String tipoAprovisionamiento = tipoAprov_BD;
			String agrupacion = agrupacion_BD;
			String fechaInicio =  ((fechaInicio_BD != null)?Utilidades.formatearFecha(fechaInicio_BD):null);
			Double cantidad1 = ((cant1_BD != null && !("".equals(cant1_BD.toString())))?new Double(cant1_BD.toString()):null);
			String fecha2 =  ((fecha2_BD != null)?Utilidades.formatearFecha(fecha2_BD):null);
			Double cantidad2 = ((cant2_BD != null && !("".equals(cant2_BD.toString())))?new Double(cant2_BD.toString()):null);
			String fecha3 =  ((fecha3_BD != null)?Utilidades.formatearFecha(fecha3_BD):null);
			Double cantidad3 = ((cant3_BD != null && !("".equals(cant3_BD.toString())))?new Double(cant3_BD.toString()):null);
			String fecha4 =  ((fecha4_BD != null)?Utilidades.formatearFecha(fecha4_BD):null);
			Double cantidad4 = ((cant4_BD != null && !("".equals(cant4_BD.toString())))?new Double(cant4_BD.toString()):null);
			String fecha5 =  ((fecha5_BD != null)?Utilidades.formatearFecha(fecha5_BD):null);
			Double cantidad5 = ((cant5_BD != null && !("".equals(cant5_BD.toString())))?new Double(cant5_BD.toString()):null);
			String fechaFin =  ((fechaFin_BD != null)?Utilidades.formatearFecha(fechaFin_BD):null);
			String descOferta = denomOferta_BD;		
			Double cantMin = ((cantMin_BD != null && !("".equals(cantMin_BD.toString())))?new Double(cantMin_BD.toString()):null);
			Double cantMax = ((cantMax_BD != null && !("".equals(cantMax_BD.toString())))?new Double(cantMax_BD.toString()):null);
			String fechaHasta =  ((fechaHasta_BD != null)?Utilidades.formatearFecha(fechaHasta_BD):null);
			Long clasePedido = new Long(tipoPed_BD);	
			
			//Los pedidos que no gestiona PBL serán considerados siempre 'E'
			String tipoPedido = Constantes.TIPO_PEDIDO_ENCARGO;
			
			return new PedidoHTNoPbl(codCentro, identificador, codArticulo,
					anoOferta, numOferta, descriptionArt,
					uniCajaServ, tipoAprovisionamiento,
					agrupacion, fechaInicio, fecha2,
					fecha3, fecha4, fecha5, fechaFin,
					cantidad1, cantidad2, cantidad3,
					cantidad4, cantidad5, descOferta,
					cantMin, cantMax, fechaHasta,
					clasePedido, tipoPedido, null, null,
					null, null);
		}
	    
		@Override
		public PedidoHTNoPblLista consultaPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception {
	    	
	    	PedidoHTNoPblLista salida = null;

	    	//Obtención de parámetros de consulta
	    	final String p_cod_loc = pedidoHTNoPbl.getCodCentro().toString();
	    	final String p_cod_articulo = ((pedidoHTNoPbl.getCodArticulo() != null)? pedidoHTNoPbl.getCodArticulo().toString() : "");
	    	final String p_cod_n1 = ((pedidoHTNoPbl.getGrupo1() != null)? pedidoHTNoPbl.getGrupo1().toString() : "");
	    	final String p_cod_n2 = ((pedidoHTNoPbl.getGrupo2() != null)? pedidoHTNoPbl.getGrupo2().toString() : "");
	    	final String p_cod_n3 = ((pedidoHTNoPbl.getGrupo3() != null)? pedidoHTNoPbl.getGrupo3().toString() : "");
	    	final String p_ano_oferta = ((pedidoHTNoPbl.getAnoOferta() != null)? pedidoHTNoPbl.getAnoOferta() : "");
	    	final String p_num_oferta = ((pedidoHTNoPbl.getNumOferta() != null)? pedidoHTNoPbl.getNumOferta() : "");
	    	final String p_validados = ((pedidoHTNoPbl.getValidados() != null)? pedidoHTNoPbl.getValidados() : "");
	    	final String p_tipo_pedido = ((pedidoHTNoPbl.getClasePedido() != null)? pedidoHTNoPbl.getClasePedido().toString() : "");
	    	final String p_identificador = ((pedidoHTNoPbl.getIdentificador() != null)? pedidoHTNoPbl.getIdentificador().toString() : "");
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_MISUMI.P_PEDIDOS_HT_NO_PBL(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, Integer.parseInt(p_cod_loc));
		                    if (p_cod_articulo != null && !"".equals(p_cod_articulo)){
		                    	cs.setLong(2, Long.parseLong(p_cod_articulo));
		                    }else{
		                    	cs.setNull(2, OracleTypes.INTEGER);
		                    }
		                    if (p_cod_n1 != null && !p_cod_n1.equals("")){
		                    	cs.setString(3, p_cod_n1);
		                    }else{
		                    	cs.setNull(3, OracleTypes.NULL);
		                    }
		                    if (p_cod_n2 != null && !p_cod_n2.equals("")){
		                    	cs.setString(4, p_cod_n2);
		                    }else{
		                    	cs.setNull(4, OracleTypes.NULL);
		                    }
		                    if (p_cod_n3 != null && !p_cod_n3.equals("")){
		                    	cs.setString(5, p_cod_n3);
		                    }else{
		                    	cs.setNull(5, OracleTypes.NULL);
		                    }
		                    if (p_ano_oferta != null && !"".equals(p_ano_oferta)){
		                    	cs.setLong(6, Long.parseLong(p_ano_oferta));
		                    }else{
		                    	cs.setNull(6, OracleTypes.INTEGER);
		                    }
		                    if (p_num_oferta != null && !"".equals(p_num_oferta)){
		                    	cs.setLong(7, Long.parseLong(p_num_oferta));
		                    }else{
		                    	cs.setNull(7, OracleTypes.INTEGER);
		                    }
		                    if (p_validados != null && !p_validados.equals("")){
		                    	cs.setString(8, p_validados);
		                    }else{
		                    	cs.setNull(8, OracleTypes.NULL);
		                    }
		                    if (p_tipo_pedido != null && !p_tipo_pedido.equals("")){
		                    	cs.setString(9, p_tipo_pedido);
		                    }else{
		                    	cs.setNull(9, OracleTypes.NULL);
		                    }
		                    if (p_identificador != null && !"".equals(p_identificador)){
		                    	cs.setLong(10, Long.parseLong(p_identificador));
		                    }else{
		                    	cs.setNull(10, OracleTypes.INTEGER);
		                    }

		                    cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_PEDIDOS_HT_NO_PBL, OracleTypes.STRUCT, "APR_R_PEDIDOS_AD_CENTRAL_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                PedidoHTNoPblLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoConsultaPedidosHTNoPbl(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        

		        try {
		        	salida = (PedidoHTNoPblLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	    
		
		private PedidoHTNoPblLista obtenerResultadoConsultaPedidosHTNoPbl(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraPedidoHTNoPbl(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONSULTA_PEDIDOS_HT_NO_PBL);
	    }

		private PedidoHTNoPblLista obtenerEstructuraPedidoHTNoPbl(CallableStatement cs, ResultSet rs, int idParametroResultado){
	    	
			PedidoHTNoPblLista pedoHtNoPblLista = new PedidoHTNoPblLista();
	    	List<PedidoHTNoPbl> listaPedidoHTNoPbl = new ArrayList<PedidoHTNoPbl>();
	    	
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
	                        PedidoHTNoPbl pedidoHTNoPbl = this.mapRow(estructuraDatos, rowNum);
	                        listaPedidoHTNoPbl.add(pedidoHTNoPbl);
	                        rowNum++;
	                    }
            		}            		
	            	
            		pedoHtNoPblLista.setEstado(new Long(estado.toString()));
            		pedoHtNoPblLista.setDescEstado(descEstado);
            		pedoHtNoPblLista.setDatos(listaPedidoHTNoPbl);
	            }
        	} catch (Exception e) {
            	e.printStackTrace();
            }
	    
            return pedoHtNoPblLista;
	    }
		
		@Override
		public int countPedidosHTNoPbl(PedidoHTNoPbl pedidoHTNoPbl) throws Exception {
	    	
			int cuenta = 0;
			
	    	PedidoHTNoPblLista listaHTNoPbl = this.consultaPedidosHTNoPbl(pedidoHTNoPbl);
	    	if (listaHTNoPbl != null && listaHTNoPbl.getDatos() != null && listaHTNoPbl.getDatos().size() > 0){
	    		cuenta = listaHTNoPbl.getDatos().size();
	    	}

		    return cuenta;
	    }
}
