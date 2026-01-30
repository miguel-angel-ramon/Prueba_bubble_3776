package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.AccionesCentroXReferenciaDao;
import es.eroski.misumi.util.Utilidades;

@Repository
public class AccionesCentroXReferenciaDaoImpl implements AccionesCentroXReferenciaDao {

	private static Logger logger = Logger.getLogger(AccionesCentroXReferenciaDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String getAccion(Long codCentro, Long codArticulo) throws Exception {

		String accion = null;
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();

		if (codCentro != null && codArticulo != null){
			query.append(" SELECT * ");
			query.append(" FROM   ( SELECT ACCION ");
			query.append("          FROM   acciones_centro_x_referencia ");
			query.append("          WHERE  cod_art = '").append(codArticulo.toString()).append("' ");
			query.append("          AND    ( cod_centro = '").append(codCentro.toString()).append("' OR cod_centro IS NULL ) ");
			query.append("          AND   SYSDATE BETWEEN fec_desde AND fec_hasta ");
			query.append("          ORDER BY DECODE (cod_centro, NULL, 2, 1) ");
			query.append("                 , cod_centro, cod_articulo, fec_desde DESC, fec_hasta DESC ");
			query.append("                 , DECODE (cod_art_formlog, cod_art, 1, 2) ");
			query.append("                 , creation_date DESC ");
			query.append("        ) ");
			query.append(" WHERE ROWNUM = 1 ");

			params.add(codArticulo.toString());
			params.add(codCentro.toString());

			List<String> listaAcciones = null;

			try {
				listaAcciones = this.jdbcTemplate.query(query.toString(), this.rwAccion);
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

			if (listaAcciones != null && listaAcciones.size() == 1) {
				accion = listaAcciones.get(0);
			}
		}
		
		return accion;
	}

	private RowMapper<String> rwAccion = new RowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("ACCION");
		}
	};
}
