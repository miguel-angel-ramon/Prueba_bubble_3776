package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VAgruComerRefDao;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VAgruComerRefDaoImpl implements VAgruComerRefDao {
	private JdbcTemplate jdbcTemplate;
	// private static Logger logger =
	// Logger.getLogger(VAgruComerRefDaoImpl.class);
	private RowMapper<VAgruComerRef> rwAgruComerRefMap = new RowMapper<VAgruComerRef>() {
		public VAgruComerRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new VAgruComerRef(resultSet.getString("NIVEL"), resultSet.getLong("GRUPO1"),
					resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
					resultSet.getLong("GRUPO5"), resultSet.getString("DESCRIPCION").trim());
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<VAgruComerRef> findAll(VAgruComerRef vAgruComerRef, Pagination pagination) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
		// params.add(lang);
		StringBuffer query = new StringBuffer("SELECT nivel, grupo1, grupo2, grupo3, grupo4, grupo5, descripcion "
											+ "FROM v_agru_comer_ref "
											);

		if (vAgruComerRef != null) {
			if (vAgruComerRef.getNivel() != null) {
				where.append(" AND NIVEL = upper(?) ");
				params.add(vAgruComerRef.getNivel());
			}
			if (vAgruComerRef.getGrupo1() != null) {
				where.append(" AND GRUPO1 = ? ");
				params.add(vAgruComerRef.getGrupo1());
			}
			if (vAgruComerRef.getGrupo2() != null) {
				where.append(" AND GRUPO2 = ? ");
				params.add(vAgruComerRef.getGrupo2());
			}
			if (vAgruComerRef.getGrupo3() != null) {
				where.append(" AND GRUPO3 = ? ");
				params.add(vAgruComerRef.getGrupo3());
			}
			if (vAgruComerRef.getGrupo4() != null) {
				where.append(" AND GRUPO4 = ? ");
				params.add(vAgruComerRef.getGrupo4());
			}
			if (vAgruComerRef.getGrupo5() != null) {
				where.append(" AND GRUPO5 = ? ");
				params.add(vAgruComerRef.getGrupo5());
			}

		}

		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + pagination.getSort() + " " + pagination.getAscDsc());
				query.append(order);
			}
		} else {
			String campoOrdenacion = "DESCRIPCION";
			if (vAgruComerRef.getNivel() != null) {
				// Ordenaci�n seg�n el nivel
				campoOrdenacion = "GRUPO" + vAgruComerRef.getNivel().substring(1).trim();
			}
			order.append(" order by " + campoOrdenacion + " asc ");
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}

		List<VAgruComerRef> lista = null;

		try {
			lista = this.jdbcTemplate.query(query.toString(), this.rwAgruComerRefMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
	}

	@Override
	public String getDescripcionSeccion(Integer codArea, Integer codSeccion) throws Exception {

		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(
				"SELECT descripcion FROM v_agru_comer_ref WHERE grupo1 = ? AND grupo2 = ? AND nivel = 'I2'");

		params.add(codArea);
		params.add(codSeccion);

		String descripcion = null;

		try {
			descripcion = this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return descripcion;
	}

	@Override
	public String getDescripcionArea(Integer codArea) throws Exception {

		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT descripcion "
											+ "FROM v_agru_comer_ref "
											+ "WHERE grupo1 = ? "
											+ "AND nivel = 'I1'"
											);

		params.add(codArea);
			
		String descripcion = null;
		try {
			descripcion = this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return descripcion;
	}
		
}
