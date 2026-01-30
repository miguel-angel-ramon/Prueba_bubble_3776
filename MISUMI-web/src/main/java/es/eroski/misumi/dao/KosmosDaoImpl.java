package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.KosmosDao;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.util.StackTraceManager;

@Repository
public class KosmosDaoImpl implements KosmosDao {
	private static Logger logger = Logger.getLogger(KosmosDaoImpl.class);
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceKOSMOS) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceKOSMOS);
	}

	@Override
	public OfertaPVP obtenerDatosPVP(OfertaPVP ofertaPVP) throws Exception {

		OfertaPVP salida = null;
		// Obtención de parámetros de consulta
		final Long p_centro = ofertaPVP.getCentro();
		final Long p_referencia = ofertaPVP.getCodArticulo();
		final Date p_fecha = ofertaPVP.getFecha();

		try {
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall(
								"{call KOSMOSCOADM.PK_KOSMOS_WEBSERVICE.P_OBTENER_DATOS_PVP(?, ?, ?, ?, ?, ?, ?) }");

						cs.setLong(1, p_centro);
						cs.setLong(2, p_referencia);
						cs.setDate(3, new java.sql.Date(p_fecha.getTime()));
						cs.registerOutParameter(4, Types.DOUBLE);
						cs.registerOutParameter(5, Types.INTEGER);
						cs.registerOutParameter(6, Types.INTEGER);
						cs.registerOutParameter(7, Types.DOUBLE);

					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					OfertaPVP ret = null;
					ResultSet rs = null;
					try {
						cs.execute();
						ret = obtenerResultadoDatosPVP(cs, rs);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ret;
				}
			};

			try {
				salida = (OfertaPVP) this.jdbcTemplate.execute(csCreator, csCallback);
				if (null != salida){
					salida.setCentro(p_centro);
					salida.setCodArticulo(p_referencia);
					salida.setFecha(p_fecha);
				}
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

	private OfertaPVP obtenerResultadoDatosPVP(CallableStatement cs, ResultSet rs) {

		OfertaPVP resultado = new OfertaPVP();

		try {

			// Obtención de los parámetros de salida

			Double tarifa_BD = (Double) cs.getObject(4);
			Integer codOferta_BD = (Integer) cs.getObject(5);
			Integer annoOferta_BD = (Integer) cs.getObject(6);
			Double pvpOfer_BD = (Double) cs.getObject(7);

			resultado.setTarifa(((tarifa_BD != null && !("".equals(tarifa_BD.toString())))
					? new Double(tarifa_BD.toString()) : null));
			resultado.setCodOferta(((codOferta_BD != null && !("".equals(codOferta_BD.toString())))
					? new Long(codOferta_BD.toString()) : null));
			resultado.setAnnoOferta(((annoOferta_BD != null && !("".equals(annoOferta_BD.toString())))
					? new Long(annoOferta_BD.toString()) : null));
			resultado.setPvpOfer(((pvpOfer_BD != null && !("".equals(pvpOfer_BD.toString())))
					? new Double(pvpOfer_BD.toString()) : null));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	@Override
	public Double obtenerCosto(OfertaPVP ofertaPVP) throws Exception {
		logger.info("obtenerCosto");

		String query = "select KOSMOSCOADM.PK_KOSMOS_WEBSERVICE.FOBTENER_COSTO(" + ofertaPVP.getCentro() + ", "
				+ ofertaPVP.getCodArticulo() + ") FROM dual";

		logger.info("SQL: " + query);

		return jdbcTemplate.queryForObject(query, Double.class);
	}

}
