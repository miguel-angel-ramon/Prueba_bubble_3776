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

import es.eroski.misumi.dao.iface.EstadoMostradorSIADao;
import es.eroski.misumi.model.EstadoMostradorSIALista;
import es.eroski.misumi.model.EstructuraArtMostrador;
import es.eroski.misumi.util.StackTraceManager;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

@Repository
public class EstadosMostradorSIADaoImpl implements EstadoMostradorSIADao {

	private JdbcTemplate jdbcTemplate;

	private static final int POSICION_PARAMETRO_SALIDA_CONSULTAR_ESTADOS = 2;

	private static Logger logger = Logger.getLogger(EstadosMostradorSIADaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	private EstructuraArtMostrador mapRow(STRUCT estructuraDatos, int rowNum) throws SQLException {
		Object[] objectInfo = estructuraDatos.getAttributes();

		EstructuraArtMostrador estadoMostradorSIA = new EstructuraArtMostrador();

		// Obtención de datos de la estructura de base de datos
		BigDecimal codLoc_BD = ((BigDecimal) objectInfo[0]); // centro
		// Transformación de datos para estructura de DetalladoMostradorSIA
		estadoMostradorSIA.setCodCentro(
				((codLoc_BD != null && !("".equals(codLoc_BD.toString()))) ? new Long(codLoc_BD.toString()) : null));

		estadoMostradorSIA.setNivel1(((BigDecimal) objectInfo[1]).intValueExact()); // area
		estadoMostradorSIA.setNivel2(((BigDecimal) objectInfo[2]).intValueExact()); // seccion
		estadoMostradorSIA.setNivel3(((BigDecimal) objectInfo[3]).intValueExact()); // categoria
		estadoMostradorSIA.setNivel4(((BigDecimal) objectInfo[4]).intValueExact()); // subcategoria
		estadoMostradorSIA.setNivel5(((BigDecimal) objectInfo[5]).intValueExact()); // segmento

		estadoMostradorSIA.setEstado((((BigDecimal) objectInfo[6]).intValueExact())); // estado

		return estadoMostradorSIA;
	}

	@Override
	public EstadoMostradorSIALista consultaEstados(Long codCentro) throws Exception {

		EstadoMostradorSIALista salida = null;

		// Obtención de parámetros de consulta
		final Long p_cod_loc = codCentro;

		try {

			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call PK_APR_DET_MOSTRADOR_MISUMI.P_AVISOS(?, ?) }");

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
					EstadoMostradorSIALista ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoEstados(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			try {
				salida = (EstadoMostradorSIALista) this.jdbcTemplate.execute(csCreator, csCallback);
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
	private EstadoMostradorSIALista obtenerResultadoEstados(CallableStatement cs, ResultSet rs) {
		return obtenerEstructuraEstadosSIA(cs, rs, EstadosMostradorSIADaoImpl.POSICION_PARAMETRO_SALIDA_CONSULTAR_ESTADOS);
	}

	private EstadoMostradorSIALista obtenerEstructuraEstadosSIA(CallableStatement cs, ResultSet rs,
			int idParametroResultado) {

		EstadoMostradorSIALista estadosMostradorSIALista = new EstadoMostradorSIALista();
		List<EstructuraArtMostrador> listaEstadosSIA = new ArrayList<EstructuraArtMostrador>();

		try {
			// Obtención del parámetro de salida
			STRUCT estructuraResultado = (STRUCT) cs.getObject(idParametroResultado);

			// Obtención de los datos de la estructura
			BigDecimal estado = (BigDecimal) estructuraResultado.getAttributes()[1];
			String descEstado = (String) estructuraResultado.getAttributes()[2];

			// Control de error en la obtención de datos
			if (new BigDecimal("0").equals(estado)) { // El proceso se ha
														// ejecutado
														// correctamente
				// Obtención de los datos de salida
				ARRAY listaDatos = (ARRAY) estructuraResultado.getAttributes()[0];
				if (listaDatos != null) {
					rs = listaDatos.getResultSet();
					int rowNum = 0;
					// Recorrido del listado de datos
					while (rs.next()) {
						STRUCT estructuraDatos = (STRUCT) rs.getObject(2);
						EstructuraArtMostrador estructuraEstadoSIA = this.mapRow(estructuraDatos, rowNum);
						listaEstadosSIA.add(estructuraEstadoSIA);
						rowNum++;
					}
				}

				estadosMostradorSIALista.setDatos(listaEstadosSIA);
			}

			estadosMostradorSIALista.setEstado(new Long(estado.toString()));
			estadosMostradorSIALista.setDescEstado(descEstado);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estadosMostradorSIALista;
	}

}
