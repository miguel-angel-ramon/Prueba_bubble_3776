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

import es.eroski.misumi.dao.iface.TOptimaDao;
import es.eroski.misumi.model.TOptima;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class TOptimaDaoImpl implements TOptimaDao {

	private static Logger logger = Logger.getLogger(TOptimaDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private RowMapper<TOptima> rwTOptimaMap = new RowMapper<TOptima>() {
		public TOptima mapRow(ResultSet resultSet, int rowNum)
				throws SQLException {
			TOptima tOptima = new TOptima();
			tOptima.setCodMac(resultSet.getString("COD_MAC"));
			tOptima.setCodCentro(resultSet.getLong("COD_CENTRO"));
			tOptima.setArea(resultSet.getLong("AREA"));
			tOptima.setCodArt(resultSet.getLong("COD_ART"));
			tOptima.setDescripcion(resultSet.getString("DESCRIPCION"));
			tOptima.setFechaGen(resultSet.getDate("FECHA_GEN"));
			tOptima.setCreationDate(resultSet.getDate("CREATION_DATE"));
			tOptima.setNsr((null != resultSet.getString("FLG_NSR") && resultSet
					.getString("FLG_NSR").equals("S")) ? true : false);
			tOptima.setEnviado((null != resultSet.getString("FLG_ENVIADA") && resultSet
					.getString("FLG_ENVIADA").equals("S")) ? true : false);
			tOptima.setCodError(resultSet.getLong("COD_ERROR"));
			tOptima.setDescError(resultSet.getString("DESC_ERROR"));
			return tOptima;
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<TOptima> getHuecos(TOptima tOptima) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(
				" SELECT COD_MAC, COD_CENTRO, AREA, COD_ART, DESCRIPCION, FLG_NSR, FLG_ENVIADA, ");
		query.append(" FECHA_GEN, CREATION_DATE, COD_ERROR, DESC_ERROR FROM T_OPTIMA");

		where.append(" WHERE 1=1 ");
		if (null != tOptima) {
			if (null != tOptima.getCodMac()) {
				where.append(" AND COD_MAC = ? ");
				params.add(tOptima.getCodMac());
			}
			if (null != tOptima.getCodCentro()) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(tOptima.getCodCentro());
			}
			if (null != tOptima.getFechaGen()) {
				where.append(" AND FECHA_GEN = TRUNC(?) ");
				params.add(tOptima.getFechaGen());
			}
			if (null != tOptima.getCreationDate()) {
				where.append(" AND CREATION_DATE = TRUNC(?) ");
				params.add(tOptima.getCreationDate());
			}
			if (null != tOptima.isEnviado()) {
				where.append(" AND FLG_ENVIADA = ? ");
				params.add((tOptima.isEnviado().booleanValue()) ? "S" : "N");
			}
		}
		query.append(where);

	
		List<TOptima> lista = null;
		try {
			lista = (List<TOptima>) this.jdbcTemplate.query(query.toString(),this.rwTOptimaMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;
	}

	@Override
	public void borrarHuecos(TOptima tOptima) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(" DELETE FROM T_OPTIMA");

		where.append(" WHERE 1=1 ");
		if (null != tOptima) {
			if (null != tOptima.getCodArt()) {
				where.append(" AND COD_ART = ? ");
				params.add(tOptima.getCodArt());
			}
			if (null != tOptima.getCodCentro()) {
				where.append(" AND COD_CENTRO = ? ");
				params.add(tOptima.getCodCentro());
			}
			if (null != tOptima.getFechaGen()) {
				where.append(" AND FECHA_GEN = TRUNC(?) ");
				params.add(tOptima.getFechaGen());
			}
			if (tOptima.isEnviado()) {
				where.append(" AND FLG_ENVIADA = ? ");
				params.add((tOptima.isEnviado().booleanValue()) ? "S" : "N");
			}
		}
		query.append(where);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	
	}

	@Override
	public void insertHueco(TOptima tOptima) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(
				"INSERT INTO T_OPTIMA (COD_MAC, COD_CENTRO, AREA, COD_ART, DESCRIPCION, FLG_NSR, FLG_ENVIADA, ");
		query.append(" FECHA_GEN, CREATION_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, TRUNC(?), TRUNC(?)) ");

		params.add(tOptima.getCodMac());
		params.add(tOptima.getCodCentro());
		params.add(tOptima.getArea());
		params.add(tOptima.getCodArt());
		params.add(tOptima.getDescripcion());
		params.add((tOptima.isNsr().booleanValue()) ? "S" : "N");
		params.add((tOptima.isEnviado().booleanValue()) ? "S" : "N");
		params.add(tOptima.getFechaGen());
		params.add(tOptima.getCreationDate());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	}
	
	@Override
	public void updateHueco(TOptima tOptima) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(
				"UPDATE T_OPTIMA SET FLG_ENVIADA = ?, COD_ERROR = ?, DESC_ERROR = ?  ");
		query.append("WHERE COD_MAC = ? AND COD_CENTRO = ? AND COD_ART = ? AND FECHA_GEN = TRUNC(?) ");

		params.add((tOptima.isEnviado().booleanValue()) ? "S" : "N");
		params.add(tOptima.getCodError());
		params.add(tOptima.getDescError());
		params.add(tOptima.getCodMac());
		params.add(tOptima.getCodCentro());
		params.add(tOptima.getCodArt());
		params.add(tOptima.getFechaGen());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	}

}
