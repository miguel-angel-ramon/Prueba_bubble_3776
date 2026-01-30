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

import es.eroski.misumi.dao.iface.VentaAnticipadaDao;
import es.eroski.misumi.model.VentaAnticipada;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VentaAnticipadaDaoImpl implements VentaAnticipadaDao {

	private JdbcTemplate jdbcTemplate;

	private RowMapper<VentaAnticipada> rwVentaAnticipadaMap = new RowMapper<VentaAnticipada>() {
		public VentaAnticipada mapRow(ResultSet resultSet, int rowNum)
				throws SQLException {
			return new VentaAnticipada(resultSet.getLong("COD_CENTRO"),
					resultSet.getLong("COD_ARTICULO"),
					resultSet.getString("FECHA_GEN"),
					resultSet.getDouble("CANTIDAD"),
					resultSet.getDate("CREATION_DATE"),
					resultSet.getDate("LAST_UPDATE_DATE"),
					resultSet.getString("FLG_ENVIO_AC"));
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public VentaAnticipada find(VentaAnticipada ventaAnticipada) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COD_CENTRO, COD_ARTICULO, TO_CHAR(FECHA_GEN ,'DDMMYYYY') AS FECHA_GEN, CANTIDAD, ");
		query.append(" CREATION_DATE, LAST_UPDATE_DATE, FLG_ENVIO_AC ");
		query.append(" FROM VENTA_ANTICIPADA ");
		query.append("WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND FECHA_GEN = TO_DATE(? ,'DDMMYYYY')");

		params.add(ventaAnticipada.getCodCentro());
		params.add(ventaAnticipada.getCodArt());
		params.add(ventaAnticipada.getFechaGen());

		VentaAnticipada ventaAnticipadaOut = null;
		try {
			List<VentaAnticipada> lista =  (List<VentaAnticipada>)this.jdbcTemplate.query(query.toString(), this.rwVentaAnticipadaMap, params.toArray());
			if(lista != null && lista.size() > 0){
				ventaAnticipadaOut = lista.get(0);
			}
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			return null;
		}

		return ventaAnticipadaOut;
	}

	@Override
	public void insert(VentaAnticipada ventaAnticipada) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("INSERT INTO VENTA_ANTICIPADA (COD_CENTRO, COD_ARTICULO, FECHA_GEN, ");
		query.append(" CANTIDAD, CREATION_DATE, LAST_UPDATE_DATE, FLG_ENVIO_AC) ");
		query.append(" VALUES (?, ?, ?, ?, ?, ?, ?) ");


		params.add(ventaAnticipada.getCodCentro());
		params.add(ventaAnticipada.getCodArt());
		params.add(ventaAnticipada.getFechaGen());
		params.add(ventaAnticipada.getCantidad());
		params.add(ventaAnticipada.getCreationDate());
		params.add(ventaAnticipada.getLastUpdate());
		params.add(ventaAnticipada.getFlgEnvioAC());


		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}

	}

	@Override
	public void update(VentaAnticipada ventaAnticipada) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE VENTA_ANTICIPADA SET CANTIDAD = ?, FLG_ENVIO_AC = ?, LAST_UPDATE_DATE = ? ");
		query.append("WHERE COD_CENTRO = ? AND COD_ARTICULO = ? AND FECHA_GEN = ? ");

		params.add(ventaAnticipada.getCantidad());
		params.add(ventaAnticipada.getFlgEnvioAC());
		params.add(ventaAnticipada.getLastUpdate());
		params.add(ventaAnticipada.getCodCentro());
		params.add(ventaAnticipada.getCodArt());
		params.add(ventaAnticipada.getFechaGen());


		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}

	}

	@Override
	public void delete(VentaAnticipada ventaAnticipada) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM VENTA_ANTICIPADA WHERE COD_CENTRO = ? ");
		query.append("AND COD_ARTICULO = ? AND FECHA_GEN = ?");
		params.add(ventaAnticipada.getCodCentro());
		params.add(ventaAnticipada.getCodArt());
		params.add(ventaAnticipada.getFechaGen());


		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}


	}

}
