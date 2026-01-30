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

import es.eroski.misumi.dao.iface.StockFinalMinimoDao;
import es.eroski.misumi.model.ParamStockFinalMinimo;
import es.eroski.misumi.model.Roturas;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class StockFinalMinimoDaoImpl implements StockFinalMinimoDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<StockFinalMinimo> rwStockFinalMinimoMap = new RowMapper<StockFinalMinimo>() {
		public StockFinalMinimo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new StockFinalMinimo(resultSet.getLong("COD_LOC"), resultSet.getLong("COD_ARTICULO"),
					resultSet.getFloat("STOCK_FIN_MIN_L"), resultSet.getFloat("STOCK_FIN_MIN_M"),
					resultSet.getFloat("STOCK_FIN_MIN_X"), resultSet.getFloat("STOCK_FIN_MIN_J"),
					resultSet.getFloat("STOCK_FIN_MIN_V"), resultSet.getFloat("STOCK_FIN_MIN_S"),
					resultSet.getFloat("STOCK_FIN_MIN_D"), resultSet.getFloat("CAPACIDAD"),
					resultSet.getFloat("VENTA_MEDIA"), resultSet.getLong("FACING_CENTRO"),
					resultSet.getLong("FACING_CENTRO_PREVIO"), resultSet.getLong("VIDA_UTIL_APROV"));
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(
				" SELECT S.COD_LOC, S.COD_ARTICULO, S.STOCK_FIN_MIN_L, S.STOCK_FIN_MIN_M, S.STOCK_FIN_MIN_X, S.STOCK_FIN_MIN_J, "
						+ " S.STOCK_FIN_MIN_V, S.STOCK_FIN_MIN_S, S.STOCK_FIN_MIN_D, S.CAPACIDAD, S.VENTA_MEDIA, S.FACING_CENTRO, S.FACING_CENTRO_PREVIO, S.VIDA_UTIL_APROV "
						+ " FROM STOCK_FINAL_MINIMO S ");
		
		if (stockFinalMinimo != null) {
			if (stockFinalMinimo.getCodLoc() != null) {
				where.append(" AND S.COD_LOC = ? ");
				params.add(stockFinalMinimo.getCodLoc());
			}
			if (stockFinalMinimo.getCodArticulo() != null) {
				where.append(" AND S.COD_ARTICULO = ? ");
				params.add(stockFinalMinimo.getCodArticulo());
			}
			if (stockFinalMinimo.getStockFinMinL() != null) {
				where.append(" AND S.STOCK_FIN_MIN_L = ? ");
				params.add(stockFinalMinimo.getStockFinMinL());
			}
			if (stockFinalMinimo.getStockFinMinM() != null) {
				where.append(" AND S.STOCK_FIN_MIN_M = ? ");
				params.add(stockFinalMinimo.getStockFinMinM());
			}
			if (stockFinalMinimo.getStockFinMinX() != null) {
				where.append(" AND S.STOCK_FIN_MIN_X = ? ");
				params.add(stockFinalMinimo.getStockFinMinX());
			}
			if (stockFinalMinimo.getStockFinMinJ() != null) {
				where.append(" AND S.STOCK_FIN_MIN_J = ? ");
				params.add(stockFinalMinimo.getStockFinMinJ());
			}
			if (stockFinalMinimo.getStockFinMinV() != null) {
				where.append(" AND S.STOCK_FIN_MIN_V = ? ");
				params.add(stockFinalMinimo.getStockFinMinV());
			}
			if (stockFinalMinimo.getStockFinMinS() != null) {
				where.append(" AND S.STOCK_FIN_MIN_S = ? ");
				params.add(stockFinalMinimo.getStockFinMinS());
			}
			if (stockFinalMinimo.getStockFinMinD() != null) {
				where.append(" AND S.STOCK_FIN_MIN_D = ? ");
				params.add(stockFinalMinimo.getStockFinMinD());
			}
			if (stockFinalMinimo.getCapacidad() != null) {
				where.append(" AND S.CAPACIDAD = ? ");
				params.add(stockFinalMinimo.getCapacidad());
			}
			if (stockFinalMinimo.getVentaMedia() != null) {
				where.append(" AND S.VENTA_MEDIA = ? ");
				params.add(stockFinalMinimo.getVentaMedia());
			}

		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by S.cod_loc, S.cod_articulo ");
		query.append(order);

		List<StockFinalMinimo> stockFinalMinimoLista = null;

		try {

			stockFinalMinimoLista = (List<StockFinalMinimo>) this.jdbcTemplate.query(query.toString(),
					this.rwStockFinalMinimoMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return stockFinalMinimoLista;
	}

	@Override
	public Long findFinalStockParam(ParamStockFinalMinimo paramStockFinalMinimo) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + " FROM PARAM_STOCK_FINAL_MIN ");

		where.append(" AND FLG_STOCK_FINAL = ? ");
		params.add("S");

		if (paramStockFinalMinimo != null) {
			if (paramStockFinalMinimo.getCodN1() != null) {
				where.append(" AND COD_N1 = ? ");
				params.add(paramStockFinalMinimo.getCodN1());
			}
			if (paramStockFinalMinimo.getCodN2() != null) {
				where.append(" AND COD_N2 = ? ");
				params.add(paramStockFinalMinimo.getCodN2());
			}
			if (paramStockFinalMinimo.getCodN3() != null) {
				where.append(" AND COD_N3 = ? ");
				params.add(paramStockFinalMinimo.getCodN3());
			}
			if (paramStockFinalMinimo.getCodN4() != null) {
				where.append(" AND COD_N4 = ? ");
				params.add(paramStockFinalMinimo.getCodN4());
			}
			if (paramStockFinalMinimo.getCodN5() != null) {
				where.append(" AND COD_N5 = ? ");
				params.add(paramStockFinalMinimo.getCodN5());
			}
			if (paramStockFinalMinimo.getCodLoc() != null) {
				where.append(" AND COD_LOC = ? ");
				params.add(paramStockFinalMinimo.getCodLoc());
			}

		}

		query.append(where);

		Long cont = null; 
		
		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;

	}

	@Override
	public Long findVidaUtil(StockFinalMinimo stockFinalMinimo) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT VIDA_UTIL_APROV " + " FROM STOCK_FINAL_MINIMO ");

		if (stockFinalMinimo != null) {
			if (stockFinalMinimo.getCodLoc() != null) {
				where.append(" AND COD_LOC = ? ");
				params.add(stockFinalMinimo.getCodLoc());
			}
			if (stockFinalMinimo.getCodArticulo() != null) {
				where.append(" AND COD_ARTICULO = ? ");
				params.add(stockFinalMinimo.getCodArticulo());
			}
			if (stockFinalMinimo.getStockFinMinL() != null) {
				where.append(" AND STOCK_FIN_MIN_L = ? ");
				params.add(stockFinalMinimo.getStockFinMinL());
			}
			if (stockFinalMinimo.getStockFinMinM() != null) {
				where.append(" AND STOCK_FIN_MIN_M = ? ");
				params.add(stockFinalMinimo.getStockFinMinM());
			}
			if (stockFinalMinimo.getStockFinMinX() != null) {
				where.append(" AND STOCK_FIN_MIN_X = ? ");
				params.add(stockFinalMinimo.getStockFinMinX());
			}
			if (stockFinalMinimo.getStockFinMinJ() != null) {
				where.append(" AND STOCK_FIN_MIN_J = ? ");
				params.add(stockFinalMinimo.getStockFinMinJ());
			}
			if (stockFinalMinimo.getStockFinMinV() != null) {
				where.append(" AND STOCK_FIN_MIN_V = ? ");
				params.add(stockFinalMinimo.getStockFinMinV());
			}
			if (stockFinalMinimo.getStockFinMinS() != null) {
				where.append(" AND STOCK_FIN_MIN_S = ? ");
				params.add(stockFinalMinimo.getStockFinMinS());
			}
			if (stockFinalMinimo.getStockFinMinD() != null) {
				where.append(" AND STOCK_FIN_MIN_D = ? ");
				params.add(stockFinalMinimo.getStockFinMinD());
			}
			if (stockFinalMinimo.getCapacidad() != null) {
				where.append(" AND CAPACIDAD = ? ");
				params.add(stockFinalMinimo.getCapacidad());
			}
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_loc, cod_articulo ");
		query.append(order);

	
		
		Long vidaUtil = null; 
		
		try {

			vidaUtil = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		


		return vidaUtil;
	}

}
