package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import es.eroski.misumi.dao.iface.PedidosAdCentralSPDao;
import es.eroski.misumi.model.PedidoAdSP;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@Repository
@Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NOT_SUPPORTED)
public class PedidosAdCentralSPDaoImpl implements PedidosAdCentralSPDao {

	private JdbcTemplate jdbcTemplate;
	
	private Logger logger = Logger.getLogger(PedidosAdCentralSPDaoImpl.class);

	private DataSourceTransactionManager transactionManager;

	@Autowired
	public void setDataSourceTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		this.jdbcTemplate = new JdbcTemplate(this.transactionManager.getDataSource());
	}

	private PedidosAdCentral mapRow(STRUCT estructuraDatos) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		// Obtenci�n de datos de la estructura de base de datos
		BigDecimal codLoc = ((BigDecimal) objectInfo[0]);
		BigDecimal codArticulo = (BigDecimal) objectInfo[1];
		BigDecimal identificador = (BigDecimal) objectInfo[2];
		String flgValidado = (String) objectInfo[3];
		String tipoPedido = (String) objectInfo[4];
		BigDecimal codError = (BigDecimal) objectInfo[5];
		String descError = (String) objectInfo[6];

		PedidosAdCentral pedidosAdCentral = new PedidosAdCentral();
		pedidosAdCentral.setCodCentro(codLoc.longValue());
		pedidosAdCentral.setCodArt(codArticulo.longValue());
		pedidosAdCentral.setIdentificador(identificador.longValue());
		pedidosAdCentral.setFlgValidado(flgValidado);
		pedidosAdCentral.setTipoPed(tipoPedido);
		pedidosAdCentral.setCodError(codError.longValue());
		pedidosAdCentral.setDescError(descError);

		return pedidosAdCentral;

	}

	@Override
	public void actualizacionPedidosAd(TPedidoAdicional tPedidoAdicional, HttpSession session)
			throws Exception {

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		PedidosAdCentral pedidosAdCentral = new PedidosAdCentral();
		pedidosAdCentral.setCodCentro(tPedidoAdicional.getCodCentro());
		pedidosAdCentral.setCodArt(tPedidoAdicional.getCodArticulo());
		pedidosAdCentral.setIdentificador(tPedidoAdicional.getIdentificador());
		pedidosAdCentral.setTipoPed(tPedidoAdicional.getClasePedido().toString());
		pedidosAdCentral.setFlgValidado(tPedidoAdicional.getFlgValidado());
		pedidosAdCentral.setCant1_centro(tPedidoAdicional.getCantidad1());
		pedidosAdCentral.setCant2_centro(tPedidoAdicional.getCantidad2());
		pedidosAdCentral.setCant3_centro(tPedidoAdicional.getCantidad3());
		pedidosAdCentral.setCant4_centro(tPedidoAdicional.getCantidad4());
		pedidosAdCentral.setCant5_centro(tPedidoAdicional.getCantidad5());
		
		if(tPedidoAdicional.getFechaInicio() != null) {
			pedidosAdCentral.setFechaInicio(Utilidades.convertirStringAFecha(tPedidoAdicional.getFechaInicio()));
		}
		if(tPedidoAdicional.getFecha2() != null) {
			pedidosAdCentral.setFecha2(Utilidades.convertirStringAFecha(tPedidoAdicional.getFecha2()));
		}
		if(tPedidoAdicional.getFecha3() != null) {
			pedidosAdCentral.setFecha3(Utilidades.convertirStringAFecha(tPedidoAdicional.getFecha3()));
		}
		if(tPedidoAdicional.getFecha4() != null) {
			pedidosAdCentral.setFecha4(Utilidades.convertirStringAFecha(tPedidoAdicional.getFecha4()));
		}
		if(tPedidoAdicional.getFecha5() != null) {
			pedidosAdCentral.setFecha5(Utilidades.convertirStringAFecha(tPedidoAdicional.getFecha5()));
		}

		List<PedidosAdCentral> listaActualizacion = new ArrayList<PedidosAdCentral>();
		listaActualizacion.add(pedidosAdCentral);
		PedidoAdSP pedidoAdSP = this.invokeSP(listaActualizacion);
		if (pedidoAdSP.getEstado() == 0) {
			List<PedidosAdCentral> listaPedidosAd = pedidoAdSP.getDatos();
			for (PedidosAdCentral pedidoAux : listaPedidosAd) {
				if (pedidoAux.getCodError() == 0) {					
					 transactionManager.commit(status);
					 tPedidoAdicional.setCodError(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO.toString()); 					 
				} else {
					transactionManager.commit(status);
					tPedidoAdicional.setCodError("1");
					// tPedidoAdicional.setDescError(descError);
				}
			}
		} else {
			transactionManager.commit(status);
			tPedidoAdicional.setCodError("1");
			// tPedidoAdicional.setDescError(descError);
		}
	}

	private PedidoAdSP invokeSP(final List<PedidosAdCentral> listaActualizacion)
			throws Exception {

		PedidoAdSP salida = new PedidoAdSP();
		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						// Crear estructura para actualizaci�n
						STRUCT itemConsulta = crearEstructuraPedidosAdUpdate(
								listaActualizacion, con);
						logger.debug(itemConsulta.dump());
						cs = con.prepareCall("{call PK_APR_MISUMI.P_UPDATE_PEDIDOS_AD_CENTRAL(?) }");
						cs.registerOutParameter(1, OracleTypes.STRUCT,
								"APR_R_PEDIDOS_AD_REG");
						cs.setObject(1, itemConsulta);

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback<PedidoAdSP> csCallback = new CallableStatementCallback<PedidoAdSP>() {

				public PedidoAdSP doInCallableStatement(CallableStatement cs) {
					PedidoAdSP ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerEstructuraPedidosAdUpdate(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			salida = (PedidoAdSP) this.jdbcTemplate.execute(csCreator,
					csCallback);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	private STRUCT crearEstructuraPedidosAdUpdate(
			List<PedidosAdCentral> listaActualizacion, Connection con)
			throws SQLException {

		// Transformaci�n de conexi�n a conexi�n de oracle. Necesari para
		// definici�n del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData()
				.getConnection();

		int numeroElementos = listaActualizacion.size();
		Object[] objectTabla = new Object[numeroElementos];

		for (int i = 0; i < numeroElementos; i++) {

			PedidosAdCentral pedidoAdCentral = (PedidosAdCentral) listaActualizacion
					.get(i);

			Object[] objectInfo = new Object[17];
			objectInfo[0] = pedidoAdCentral.getCodCentro();
			objectInfo[1] = pedidoAdCentral.getCodArt();
			objectInfo[2] = pedidoAdCentral.getIdentificador();
			objectInfo[3] = pedidoAdCentral.getFlgValidado();

			if(pedidoAdCentral.getTipoPed().equals(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL)){
				objectInfo[4] = Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL;
			}else if (pedidoAdCentral.getTipoPed().equals(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA) ){
				objectInfo[4] = Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA;
			}else if(pedidoAdCentral.getTipoPed().equals(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4)){
				objectInfo[4] = Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL;
			} else {
				objectInfo[4] = Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA;
			}
			
			//Actualización de cantidades de SIA
			objectInfo[7] = pedidoAdCentral.getCant1_centro();
			objectInfo[8] = pedidoAdCentral.getCant2_centro();
			objectInfo[9] = pedidoAdCentral.getCant3_centro();
			objectInfo[10] = pedidoAdCentral.getCant4_centro();
			objectInfo[11] = pedidoAdCentral.getCant5_centro();
			
			//Actualización de fechas de SIA
			objectInfo[12] = pedidoAdCentral.getFechaInicio() != null ? new java.sql.Date (pedidoAdCentral.getFechaInicio().getTime()):null;
			objectInfo[13] = pedidoAdCentral.getFecha2() != null ? new java.sql.Date (pedidoAdCentral.getFecha2().getTime()):null;
			objectInfo[14] = pedidoAdCentral.getFecha3() != null ? new java.sql.Date (pedidoAdCentral.getFecha3().getTime()):null;
			objectInfo[15] = pedidoAdCentral.getFecha4() != null ? new java.sql.Date (pedidoAdCentral.getFecha4().getTime()):null;
			objectInfo[16] = pedidoAdCentral.getFecha5() != null ? new java.sql.Date (pedidoAdCentral.getFecha5().getTime()):null;
			
			StructDescriptor itemDescriptor = StructDescriptor
					.createDescriptor("APR_R_PEDIDOS_AD_DAT", conexionOracle);
			STRUCT itemObjectStruct = new STRUCT(itemDescriptor,
					conexionOracle, objectInfo);

			objectTabla[i] = itemObjectStruct;

		}

		Object[] objectConsulta = new Object[3]; // Tiene 3 campos pero s�lo nos
													// interesa la lista
		ArrayDescriptor desc = new ArrayDescriptor("APR_T_R_PEDIDOS_AD_DAT",
				conexionOracle);
		ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);

		objectConsulta[0] = array;

		StructDescriptor itemDescriptorConsulta = StructDescriptor
				.createDescriptor("APR_R_PEDIDOS_AD_REG", conexionOracle);
		STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,
				conexionOracle, objectConsulta);

		return itemConsulta;
	}

	private PedidoAdSP obtenerEstructuraPedidosAdUpdate(CallableStatement cs,
			ResultSet rs) {

		PedidoAdSP pedidoAdSP = new PedidoAdSP();
		List<PedidosAdCentral> listaPedidosAd = new ArrayList<PedidosAdCentral>();

		try {
			// Obtenci�n del par�metro de salida
			STRUCT estructuraResultado = (STRUCT) cs.getObject(1);
			logger.debug(estructuraResultado.dump());
			// Obtenci�n de los datos de la estructura
			BigDecimal estado = (BigDecimal) estructuraResultado
					.getAttributes()[1];
			String descEstado = (String) estructuraResultado.getAttributes()[2];

			// Control de error en la obtenci�n de datos
			if (new BigDecimal("0").equals(estado)) { // El proceso se ha
														// ejecutado
														// correctamente
				// Obtenci�n de los datos de salida
				ARRAY listaDatos = (ARRAY) estructuraResultado.getAttributes()[0];
				if (listaDatos != null) {
					rs = listaDatos.getResultSet();
					// Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						PedidosAdCentral pedidoAd = this
								.mapRow(estructuraDatos);
						listaPedidosAd.add(pedidoAd);
					}
				}

				pedidoAdSP.setDatos(listaPedidosAd);
			}
			pedidoAdSP.setEstado(new Long(estado.toString()));
			pedidoAdSP.setDescEstado(descEstado);
		} catch (Exception e) {
			e.printStackTrace();
			// } catch (SQLException e) {
			// log.log(Level.ERROR, error.ERROR_RECUPERACION + "\n"+
			// error.getStackTrace(e) );
			// } catch (ClassNotFoundException e) {
			// log.log(Level.ERROR, error.ERROR_CONVERSION + "\n"+
			// error.getStackTrace(e) );
			// }
		}

		return pedidoAdSP;
	}
}
