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

import es.eroski.misumi.dao.iface.FechaProximasEntregasDao;
import es.eroski.misumi.model.FechaProximaEntrega;
import es.eroski.misumi.model.FechaProximaEntregaRef;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class FechaProximasEntregasDaoImpl implements FechaProximasEntregasDao{
	private static Logger logger = Logger.getLogger(FechaProximasEntregasDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	//Posiciones PLSQL de salida de PK_APR_MISUMI.P_FEC_PROX_ENTREGAS
	private static final int POSICION_PARAMETRO_SALIDA_FECHA_PROXIMAS_ENTREGAS = 3;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	@Override
	public FechaProximaEntregaRef getFechaProximasEntregas(final Long codLoc, final Long codArt){
		/*FechaProximaEntrega prox = new FechaProximaEntrega(new Date(), new Date(), new Date());
		FechaProximaEntrega prox2 = new FechaProximaEntrega(new Date(), new Date(), new Date());
		FechaProximaEntrega prox3 = new FechaProximaEntrega(new Date(), new Date(), new Date());
		FechaProximaEntrega prox4 = new FechaProximaEntrega(new Date(), new Date(), new Date());
		FechaProximaEntrega prox5 = new FechaProximaEntrega(new Date(), new Date(), new Date());
		
		List<FechaProximaEntrega> proximaEntregaLst = new ArrayList<FechaProximaEntrega>();
		proximaEntregaLst.add(prox);
		proximaEntregaLst.add(prox2);
		proximaEntregaLst.add(prox3);
		proximaEntregaLst.add(prox4);
		proximaEntregaLst.add(prox5);
		
		FechaProximaEntregaRef proximaEntregaRef = new FechaProximaEntregaRef(proximaEntregaLst, new Long("0"), null);
		
		return proximaEntregaRef;*/
		// TODO Auto-generated method stub
		FechaProximaEntregaRef salida = null;

		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {	             	                	
						cs = con.prepareCall("{call PK_APR_MISUMI.P_FEC_PROX_ENTREGAS(?,?,?)}");

						//IN COD_CENTRO
						if(codLoc != null){
							cs.setInt(1, Integer.parseInt(codLoc.toString()));
						}

						//IN EJERCICIO
						if(codArt != null){
							cs.setLong(2, codArt);
						}

						//OUT APR_R_FEC_PROX_ENTREGAS_REG
						cs.registerOutParameter(3,OracleTypes.STRUCT,"APR_R_FEC_PROX_ENTREGAS_REG"); 
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					FechaProximaEntregaRef ret = null;
					try {
						cs.execute();
						ret = obtenerFechaProximasEntregas(cs,POSICION_PARAMETRO_SALIDA_FECHA_PROXIMAS_ENTREGAS);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return ret;
				}
			};
			try {
				salida = (FechaProximaEntregaRef) this.jdbcTemplate.execute(csCreator,csCallback);
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

	//Obtiene un objeto de ProximaEntregaRef. Este objeto contiene una lista proximaEntrega, un estado y un código de estado.
	private FechaProximaEntregaRef obtenerFechaProximasEntregas(CallableStatement cs,int idParametroResultado1) throws SQLException{
		STRUCT estructuraObjeto = (STRUCT)cs.getObject(idParametroResultado1);

		//Obtener datos de el objeto
		Object[] objectInfo = estructuraObjeto.getAttributes();

		BigDecimal estado_BD = (BigDecimal)objectInfo[1];
		String descEstado_BD = (String)objectInfo[2];

		List<FechaProximaEntrega> listaProximasEntregas = null;
		Long estado = ((estado_BD != null && !("".equals(estado_BD.toString())))?new Long(estado_BD.toString()):null);
		String descEstado = descEstado_BD;

		//Control de error en la obtencion de datos
		if (estado.intValue() == 0){
			//Obtenemos el array del objeto
			ARRAY proxEntregasLst = (ARRAY)objectInfo[0];
			if (proxEntregasLst != null){
				//Inicializamos la lista.
				listaProximasEntregas = new ArrayList<FechaProximaEntrega>();
				
				ResultSet rsProxEntrega = proxEntregasLst.getResultSet();			

				//Recorrido de la lista de datos
				while (rsProxEntrega.next()) {						
					STRUCT estructuraProximaEntrega = (STRUCT)rsProxEntrega.getObject(2);
					FechaProximaEntrega proximaEntrega = this.mapRowProximaEntrega(estructuraProximaEntrega);

					listaProximasEntregas.add(proximaEntrega);
				}
			}
		}
		FechaProximaEntregaRef proximaEntregaRef = new FechaProximaEntregaRef(listaProximasEntregas, estado, descEstado);
		return proximaEntregaRef;	
	}

	//Obtiene un objeto de EstructuraProximaEntrega. 
	private FechaProximaEntrega mapRowProximaEntrega(STRUCT estructuraProximaEntrega) throws SQLException {
		//Obtener datos de CalendarioDia en crudo
		Object[] objectInfo = estructuraProximaEntrega.getAttributes();

		Timestamp fechaTransmision_BD = (Timestamp)objectInfo[0];		
		Timestamp fechaTransporte_BD = (Timestamp)objectInfo[1];	
		Timestamp fechaVenta_BD = (Timestamp)objectInfo[2];	

		Date fechaTransmision = ((fechaTransmision_BD != null )?new Date(fechaTransmision_BD.getTime()):null);
		Date fechaTransporte = ((fechaTransporte_BD != null )?new Date(fechaTransporte_BD.getTime()):null);
		Date fechaVenta = ((fechaVenta_BD != null )?new Date(fechaVenta_BD.getTime()):null);

		//Creamos un día del proximaEntrega.
		FechaProximaEntrega proximaEntrega = new FechaProximaEntrega(fechaTransmision, fechaTransporte, fechaVenta);
		return proximaEntrega;
	}
}
