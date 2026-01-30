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

import es.eroski.misumi.dao.iface.AreaFacingCeroSIADao;
import es.eroski.misumi.model.AreaFacingCeroSIALista;
import es.eroski.misumi.model.ECAreaFacingCero;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class AreaFacingCeroSIADaoImpl implements AreaFacingCeroSIADao {

	private static Logger logger = Logger.getLogger(AreaFacingCeroSIADaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	private static final int POSICION_PARAMETRO_SALIDA_CONSULTAR_AREAS = 2;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	@Override
	public AreaFacingCeroSIALista consultaAreas(Long codCentro) throws Exception {
		AreaFacingCeroSIALista salida = null;

		// Obtención de parámetros de consulta
		final Long p_cod_loc = codCentro;

		try {

			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						// TODO
						// PDTE. queda que se nos informe de cuál es el nombre del procedimiento.
						cs = con.prepareCall("{call PK_APR_MISUMI_IMC.P_APR_AVISO_FACING_0(?, ?) }");

						// Centro
						cs.setLong(1, p_cod_loc);

						cs.registerOutParameter(2, OracleTypes.STRUCT, "APR_R_DET_AVISO_MOSTRA_REG");

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};

			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					AreaFacingCeroSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoAreas(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			try {
				salida = (AreaFacingCeroSIALista) this.jdbcTemplate.execute(csCreator, csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error(StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return salida;
	}

	/**
	 * Obtener los datos de los estado de estructuras de un centor Mostrador con
	 * los datos obtenidos de la CONSULTA de SIA.
	 * 
	 * @param cs
	 * @param rs
	 * @return
	 */
	private AreaFacingCeroSIALista obtenerResultadoAreas(CallableStatement cs, ResultSet rs) {
		return obtenerEstructuraAreasSIA(cs, rs, AreaFacingCeroSIADaoImpl.POSICION_PARAMETRO_SALIDA_CONSULTAR_AREAS);
	}

	private AreaFacingCeroSIALista obtenerEstructuraAreasSIA(CallableStatement cs, ResultSet rs, int idParametroResultado) {

		AreaFacingCeroSIALista areasFacingCeroSIALista = new AreaFacingCeroSIALista();
		List<ECAreaFacingCero> listaAreasSIA = new ArrayList<ECAreaFacingCero>();

		try {
			// Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT) cs.getObject(idParametroResultado);

			// Obtención de los datos de la estructura
			BigDecimal codError = (BigDecimal) estructuraResultado.getAttributes()[1];
			String mensajeError = (String) estructuraResultado.getAttributes()[2];

			// Control de error en la obtención de datos
			if (new BigDecimal("0").equals(codError)) { // Si tiene Avisos.
				// Obtención de los datos de salida
				ARRAY listaDatos = (ARRAY) estructuraResultado.getAttributes()[0];
				if (listaDatos != null) {
					rs = listaDatos.getResultSet();
					int rowNum = 0;
					
					// Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						ECAreaFacingCero estructuraEstadoSIA = this.mapRow(estructuraDatos, rowNum);
						listaAreasSIA.add(estructuraEstadoSIA);
						rowNum++;
					}
				}

				areasFacingCeroSIALista.setAreas(listaAreasSIA);
			}

			areasFacingCeroSIALista.setCodError(new Long(codError.toString()));
			areasFacingCeroSIALista.setMensajeError(mensajeError);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return areasFacingCeroSIALista;
	}

	private ECAreaFacingCero mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		ECAreaFacingCero areasSIA = new ECAreaFacingCero();

		// Obtención de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal) objectInfo[0]); // centro
		// Transformación de datos para estructura de DetalladoMostradorSIA
		areasSIA.setCodCentro(
				((codLoc_BD != null && !("".equals(codLoc_BD.toString()))) ? new Long(codLoc_BD.toString()) : null));

		areasSIA.setNivel1(((BigDecimal) objectInfo[1]).intValueExact()); // area

		return areasSIA;
	}
	
}
