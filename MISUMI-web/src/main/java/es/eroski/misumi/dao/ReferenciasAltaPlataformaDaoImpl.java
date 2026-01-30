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
import org.springframework.web.context.request.RequestContextHolder;

import es.eroski.misumi.dao.iface.ReferenciasAltaPlataformaDao;
import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.EncargosClientePlataformaLista;
import es.eroski.misumi.model.QueHacerRef;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class ReferenciasAltaPlataformaDaoImpl implements ReferenciasAltaPlataformaDao {
	 private JdbcTemplate jdbcTemplate;
	 
	 private static Logger logger = Logger.getLogger(ReferenciasAltaPlataformaDaoImpl.class);

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
	    private EncargosClientePlataforma mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
            Object[] objectInfo = estructuraDatos.getAttributes();
            EncargosClientePlataforma encargosCliente = new EncargosClientePlataforma();
			encargosCliente.setIdSession( RequestContextHolder.currentRequestAttributes().getSessionId());
			//Los pedidos que no gestiona PBL serán considerados siempre 'E'
			encargosCliente.setCodCentro(((BigDecimal) objectInfo[1]).longValue());
			encargosCliente.setArea((String) objectInfo[12]);
			encargosCliente.setSeccion((String) objectInfo[13]);
			encargosCliente.setCategoria((String) objectInfo[14]);
			encargosCliente.setSubcategoria((String) objectInfo[15]);
			encargosCliente.setSegmento((String) objectInfo[16]);
			encargosCliente.setCodReferencia(((BigDecimal) objectInfo[17]).longValue());
			encargosCliente.setDescripcionArt((String) objectInfo[19]);
			encargosCliente.setFlgEspec((String) objectInfo[26]);
			BigDecimal unidadesCaja = (BigDecimal) objectInfo[32];
			if (null != unidadesCaja){
				encargosCliente.setUnidadesCaja(unidadesCaja.doubleValue());
			}
			encargosCliente.setVitrina((String) objectInfo[38]);

			
			return encargosCliente;
		}
	    

		@Override
		public EncargosClientePlataformaLista consultaReferenciasAltaCatalogo(EncargosClientePlataforma encargosCliente) throws Exception {
	    	
			EncargosClientePlataformaLista salida = new EncargosClientePlataformaLista();

	    	//Obtención de parámetros de consulta
	    	final Long p_cod_loc = encargosCliente.getCodCentro();
	    	final String p_desc_articulo = encargosCliente.getDescripcionArt();
	    	
			try{
				CallableStatementCreator csCreator = new CallableStatementCreator() {
	
		            @Override
		            public CallableStatement createCallableStatement(Connection con) {
		                CallableStatement cs = null;
		                try {

		                	cs = con.prepareCall("{call PK_APR_ENCARGOS_MISUMI.P_APR_OBT_REF_ALTA_PLATAF(?, ?, ?) }");
		                    
		                    cs.setInt(1, p_cod_loc.intValue());
		                    if (!p_desc_articulo.isEmpty()){
		                    	cs.setString(2, p_desc_articulo);
		                    }else{
		                    	cs.setNull(2, OracleTypes.VARCHAR);
		                    }

		                    cs.registerOutParameter(3, OracleTypes.STRUCT, "APR_R_ENCARGO_REG");
		              
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return cs;
		            }
		        };
		        CallableStatementCallback csCallback = new CallableStatementCallback() {
	
		            public Object doInCallableStatement(CallableStatement cs) {
		            	EncargosClientePlataformaLista ret = null;
		                ResultSet rs = null;
		                try {
			                cs.execute();
		                    ret = obtenerResultadoConsultaReferenciasAltaCatalogo(cs, rs);
		                } catch (SQLException e) {
		                	e.printStackTrace();                
		                }
		                return ret;
		            }
		        };
		        
		        
		        try {
		        	salida = (EncargosClientePlataformaLista) this.jdbcTemplate.execute(csCreator,csCallback);
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
	    
		
		private EncargosClientePlataformaLista obtenerResultadoConsultaReferenciasAltaCatalogo(CallableStatement cs, ResultSet rs){
	    	return obtenerEstructuraReferenciasAltaCatalogo(cs, rs, 3);
	    }

		private EncargosClientePlataformaLista obtenerEstructuraReferenciasAltaCatalogo(CallableStatement cs, ResultSet rs, int idParametroResultado){
	    	
			EncargosClientePlataformaLista pedoHtNoPblLista = new EncargosClientePlataformaLista();
	    	List<EncargosClientePlataforma> listaPedidoHTNoPbl = new ArrayList<EncargosClientePlataforma>();
	    	
	    	try{
		    	//Obtención del parámetro de salida
		    	STRUCT estructuraResultado = (STRUCT)cs.getObject(idParametroResultado);
		    	logger.info(estructuraResultado.dump());
		    	//Obtención de los datos de la estructura
		    	String nota = (String) estructuraResultado.getAttributes()[1];
	            BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[2];
	            String descEstado = (String)estructuraResultado.getAttributes()[3];
	            pedoHtNoPblLista.setEstado(new Long(estado.toString()));
        		pedoHtNoPblLista.setDescEstado(descEstado);
        		pedoHtNoPblLista.setNotas(nota);
        		pedoHtNoPblLista.setDatos(listaPedidoHTNoPbl);
	            //Control de error en la obtención de datos
	           // if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
	            	//Obtención de los datos de salida
            		ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
            		if (listaDatos!=null){
	            		rs = listaDatos.getResultSet();
	                    int rowNum = 0;
	            		//Recorrido del listado de datos
	                    while (rs.next()) {
	                    	STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
	                    	EncargosClientePlataforma pedidoHTNoPbl = this.mapRow(estructuraDatos, rowNum);
	                        listaPedidoHTNoPbl.add(pedidoHTNoPbl);
	                        rowNum++;
	                    }
            		}            		
	            	
            		
	            //}
        	} catch (Exception e) {
            	e.printStackTrace();
            }
	    
            return pedoHtNoPblLista;
	    }
		
		
}
