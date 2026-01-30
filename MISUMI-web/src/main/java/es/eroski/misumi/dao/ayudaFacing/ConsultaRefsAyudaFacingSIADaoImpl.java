package es.eroski.misumi.dao.ayudaFacing;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
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

import es.eroski.misumi.dao.ayudaFacing.iface.ConsultaRefsAyudaFacingSIADao;
import es.eroski.misumi.model.DetalladoMostradorSIA;
import es.eroski.misumi.model.DetalladoMostradorSIALista;
import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacing;
import es.eroski.misumi.model.pda.ayudaFacing.RefAyudaFacingLista;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class ConsultaRefsAyudaFacingSIADaoImpl implements ConsultaRefsAyudaFacingSIADao{

	private static Logger logger = Logger.getLogger(ConsultaRefsAyudaFacingSIADaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	 @Autowired
	 public void setDataSource(DataSource dataSourceSIA) {
		 this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	 } 

	public RefAyudaFacingLista getRefsAyudaFacing(Long centro, Long referencia){

		RefAyudaFacingLista salida = null;
		
		// Obtención de parámetros de consulta
		final Long pCentro = centro;
		final Long pReferencia = referencia;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {
	
				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {
						cs = con.prepareCall("{call PK_APR_MISUMI.P_QUITAR_FACING(?, ?, ?) }");
			                    
						cs.setLong(1, pCentro);
						cs.setLong(2, pReferencia);
			                    
						cs.registerOutParameter(3, OracleTypes.STRUCT, "APR_R_QUITAR_FACING_REG");

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			
			CallableStatementCallback<RefAyudaFacingLista> csCallback = new CallableStatementCallback <RefAyudaFacingLista>() {
	
				public RefAyudaFacingLista doInCallableStatement(CallableStatement cs) {
					RefAyudaFacingLista ret = null;
					try {
						cs.execute();
						ret = obtenerListaRefsAyudaFacing(cs);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}

			};

	        try {
	        	salida = (RefAyudaFacingLista) this.jdbcTemplate.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error("Error al ejecutar PK_APR_MISUMI.P_QUITAR_FACING para centro: " + centro + ", referencia: " + referencia, e);
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
			
		return salida;
	}
	
	// Carga el objeto "RefAyudaFacingLista" con los valores de los parámetros de salida de la llamada al método.
	private RefAyudaFacingLista obtenerListaRefsAyudaFacing(CallableStatement cs){
		RefAyudaFacingLista refAyudaFacingLista = new RefAyudaFacingLista();
		List<RefAyudaFacing> listaRefsAyudaFacingSIA = new ArrayList<RefAyudaFacing>();

		try{
			//Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT)cs.getObject(3);

			Object[] attrs = estructuraResultado.getAttributes();
			if (attrs.length >= 3) {
				
				//Obtención de los datos de la estructura
				BigDecimal estado = (BigDecimal)estructuraResultado.getAttributes()[1];
				String descEstado = (String)estructuraResultado.getAttributes()[2];

				//Control de error en la obtención de datos
				if (new BigDecimal("0").equals(estado)){ //El proceso se ha ejecutado correctamente
					//Obtención de los datos de salida
					ARRAY listaDatos = (ARRAY)estructuraResultado.getAttributes()[0];
					if (listaDatos!=null){
// Bloque de código que utiliza "try-with-resources" que precisa de la jdk 1.7 o superior.
/*
						try (ResultSet rs = listaDatos.getResultSet()) {
						    int rowNum = 0;
						    while (rs.next() && rowNum < 5) {
						        STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						        RefAyudaFacing refAyudaFacing = this.mapRow(estructuraDatos, rowNum);
						        listaRefsAyudaFacingSIA.add(refAyudaFacing);
						        rowNum++;
						    }
						} catch (SQLException e) {
							logger.error("Error procesando ResultSet de listaDatos", e);
						}
*/
		                ResultSet rs = null;
//		                try {
		                    rs = listaDatos.getResultSet();
		                    int rowNum = 0;
		                    while (rs.next() && rowNum < 5) {
		                        STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
		                        RefAyudaFacing refAyudaFacing = this.mapRow(estructuraDatos, rowNum);
		                        listaRefsAyudaFacingSIA.add(refAyudaFacing);
		                        rowNum++;
		                    }
//		                } catch (SQLException e) {
//		                    logger.error("Error procesando ResultSet de listaDatos", e);
//		                } finally {
//		                    if (rs != null) {
//		                        try {
//		                            rs.close();
//		                        } catch (SQLException e) {
//		                            logger.error("Error cerrando ResultSet", e);
//		                        }
//		                    }
//		                }

					}            		

					refAyudaFacingLista.setDatos(listaRefsAyudaFacingSIA);
				}

				refAyudaFacingLista.setEstado(new Long(estado.toString()));
				refAyudaFacingLista.setDescEstado(descEstado);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return refAyudaFacingLista;	    	
	 }

	private RefAyudaFacing mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		//Obtención de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal)objectInfo[0]);					// ref_tienda
		
		String denomRef_BD = (String)objectInfo[1];							// denominacion
		String rotacion_BD = (String)objectInfo[2]; 						// rotacion
		BigDecimal facingExcedente_BD = (BigDecimal)objectInfo[3];			// fac_max_quitar
		BigDecimal orden_BD = (BigDecimal)objectInfo[4];					// orden

		//Transformación de datos para estructura de DetalladoMostradorSIA
		Long codLoc = codLoc_BD != null ? codLoc_BD.longValue() : null;
		String denomRef = (denomRef_BD != null && denomRef_BD.length() >= 10) ? denomRef_BD.substring(0, 10) 
			    	    : (denomRef_BD != null ? denomRef_BD : "");
		String rotacion = rotacion_BD;
		Double facingExcedente = facingExcedente_BD != null ? facingExcedente_BD.doubleValue() : null;
		Long orden = new Long(orden_BD.toString());

		RefAyudaFacing refAyudaFacing = new RefAyudaFacing(codLoc, denomRef, rotacion, facingExcedente, orden);
		
		return refAyudaFacing;
	}

}
