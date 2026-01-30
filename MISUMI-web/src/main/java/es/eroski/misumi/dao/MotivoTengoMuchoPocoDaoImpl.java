package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import es.eroski.misumi.dao.iface.MotivoTengoMuchoPocoDao;
import es.eroski.misumi.filter.SecurityFilter;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.util.Constantes;

@Repository
public class MotivoTengoMuchoPocoDaoImpl implements MotivoTengoMuchoPocoDao{
	 private JdbcTemplate jdbcTemplate;
	 private static final int POSICION_PARAMETRO_SALIDA_CONSULTA_MOTIVOS = 8;
	 private static Logger logger = Logger.getLogger(SecurityFilter.class);


	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    private MotivoTengoMuchoPoco mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
            
            //Obtención de datos de la estructura de base de datos
			BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);
			BigDecimal codArticulo_BD = (BigDecimal)objectInfo[1];
			String tipo_BD = (String)objectInfo[2];
			String descripcion_BD = (String)objectInfo[3];
			
            //Transformación de datos para estructura de PedidoHtNoPbl
            Long codCentro = ((codLoc_BD != null && !("".equals(codLoc_BD.toString())))?new Long(codLoc_BD.toString()):null);
			Long codArticulo = ((codArticulo_BD != null && !("".equals(codArticulo_BD.toString())))?new Long(codArticulo_BD.toString()):null);
			String tipo = tipo_BD;
			String descripcion = descripcion_BD;
			
			return new MotivoTengoMuchoPoco(codCentro, tipo, null, codArticulo, null, null, descripcion, null);
		}
	    
		@Override
		public MotivoTengoMuchoPocoLista consultaMotivosTengoMuchoPoco(MotivoTengoMuchoPoco motivoTengoMuchoPoco) throws Exception {
	    	
			MotivoTengoMuchoPocoLista salida = null;
	    	//Obtención de parámetros de consulta
	    	final String p_cod_centro = motivoTengoMuchoPoco.getCodCentro().toString();
	    	final String p_tipo = ((motivoTengoMuchoPoco.getTipo() != null)? motivoTengoMuchoPoco.getTipo().toString() : "");
	    	final String p_idesesion = ((motivoTengoMuchoPoco.getIdSesion() != null)? motivoTengoMuchoPoco.getIdSesion().toString() : "");
	    	final String p_cod_art = ((motivoTengoMuchoPoco.getCodArticulo() != null)? motivoTengoMuchoPoco.getCodArticulo().toString() : "");
	    	final String p_stockbajo = ((motivoTengoMuchoPoco.getStockBajo() != null)? motivoTengoMuchoPoco.getStockBajo().toString() : "");
	    	final String p_stockalto = ((motivoTengoMuchoPoco.getStockAlto() != null)? motivoTengoMuchoPoco.getStockAlto().toString() : "");
	    	final String p_stockact = ((motivoTengoMuchoPoco.getStock() != null)? motivoTengoMuchoPoco.getStock().toString() : "");
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_MIS_PROCESOS.P_MOTIVO_TENGO_MUCHOPOCO(?, ?, ?, ?, ?, ?, ?, ?) }");
		                    
		                    cs.setInt(1, Integer.parseInt(p_cod_centro));
		                    if (p_tipo != null && !p_tipo.equals("")){
		                    	cs.setString(2, p_tipo);
		                    }else{
		                    	cs.setNull(2, OracleTypes.NULL);
		                    }
		                    if (p_idesesion != null && !p_idesesion.equals("")){
		                    	cs.setString(3, p_idesesion);
		                    }else{
		                    	cs.setNull(3, OracleTypes.NULL);
		                    }
		                    if (p_cod_art != null && !"".equals(p_cod_art)){
		                    	cs.setLong(4, Long.parseLong(p_cod_art));
		                    }else{
		                    	cs.setNull(4, OracleTypes.INTEGER);
		                    }
		                    if (p_stockbajo != null && !p_stockbajo.equals("")){
		                    	cs.setDouble(5, Double.parseDouble(p_stockbajo));
		                    }else{
		                    	cs.setNull(5, OracleTypes.NULL);
		                    }
		                    if (p_stockalto != null && !p_stockalto.equals("")){
		                    	cs.setDouble(6, Double.parseDouble(p_stockalto));
		                    }else{
		                    	cs.setNull(6, OracleTypes.NULL);
		                    }
		                    if (p_stockact != null && !p_stockact.equals("")){
		                    	cs.setDouble(7, Double.parseDouble(p_stockact));
		                    }else{
		                    	cs.setNull(7, OracleTypes.NULL);
		                    }
		                    cs.registerOutParameter(POSICION_PARAMETRO_SALIDA_CONSULTA_MOTIVOS, OracleTypes.STRUCT, "MIS_R_MOTIVOS_MUCHOPOCO_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		                MotivoTengoMuchoPocoLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoConsultaMotivosTengoMuchoPoco(cs, rs, p_tipo);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        salida = (MotivoTengoMuchoPocoLista) this.jdbcTemplate.execute(csCreator,csCallback);

			}catch (Exception e) {
				e.printStackTrace();
			}
			
		    return salida;
	    }
	    
		
		private MotivoTengoMuchoPocoLista obtenerResultadoConsultaMotivosTengoMuchoPoco(CallableStatement cs, ResultSet rs, String p_tipo){
	    	return obtenerResultadoConsultaMotivosTengoMuchoPoco(cs, rs, this.POSICION_PARAMETRO_SALIDA_CONSULTA_MOTIVOS, p_tipo);
	    }

		private MotivoTengoMuchoPocoLista obtenerResultadoConsultaMotivosTengoMuchoPoco(CallableStatement cs, ResultSet rs, int idParametroResultado, String p_tipo){
	    	
			MotivoTengoMuchoPocoLista motivoTengoMuchoPocoLista = new MotivoTengoMuchoPocoLista();
	    	List<MotivoTengoMuchoPoco> listaMotivosTengoMuchoPoco = new ArrayList<MotivoTengoMuchoPoco>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	//Obtención de los datos de la estructura
		    	String mapaActivo = (String)estructuraResultado.getAttributes()[1];
		    	String flgFGen = (String)estructuraResultado.getAttributes()[5];
		    	
	            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[6];
	            String descEstado = (String)estructuraResultado.getAttributes()[7];
	            
	            //Control de error en la obtención de datos
	            if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
	            	//Obtención de los datos de salida
            		
            		if (p_tipo != null && !Constantes.TENGO_MUCHO_POCO_MOTIVO_LINK.equals(p_tipo)){
            			ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
	            		if (listaDatos!=null){
		            		rs = listaDatos.getResultSet();
		            		
		                    int rowNum = 0;
		            		//Recorrido del listado de datos
		                    while (rs.next()) {
		                    	STRUCT estructuraDatos = (STRUCT) listaDatos.getOracleArray()[rowNum];
		                    	MotivoTengoMuchoPoco motivoTengoMuchoPoco = this.mapRow(estructuraDatos, rowNum);
		                    	listaMotivosTengoMuchoPoco.add(motivoTengoMuchoPoco);
		                        rowNum++;
		                    }
	            		}
            		}
            		motivoTengoMuchoPocoLista.setMapaActivo(mapaActivo);
            		motivoTengoMuchoPocoLista.setFlgFGen(flgFGen);
            		
            		motivoTengoMuchoPocoLista.setEstado(new Long(estado.toString()));
            		motivoTengoMuchoPocoLista.setDescEstado(descEstado);
            		motivoTengoMuchoPocoLista.setDatos(listaMotivosTengoMuchoPoco);
	            }
        	} catch (Exception e) {
            	e.printStackTrace();
            }
	    
            return motivoTengoMuchoPocoLista;
	    }
}
