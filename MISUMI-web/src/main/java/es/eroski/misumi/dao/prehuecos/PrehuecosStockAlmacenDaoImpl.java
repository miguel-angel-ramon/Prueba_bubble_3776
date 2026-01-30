package es.eroski.misumi.dao.prehuecos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosStockAlmacenDao;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PrehuecosStockAlmacenDaoImpl implements PrehuecosStockAlmacenDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, int pagActual) {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("WITH cte AS ( SELECT t.*, a.descrip_art, trunc(last_update_date) fecha, ROW_NUMBER() OVER( "
				+ "PARTITION BY trunc(last_update_date) ORDER BY trunc(last_update_date) DESC, estado, last_update_date ) fila_por_fecha,"
				+ "ceil(ROW_NUMBER() OVER( PARTITION BY trunc(last_update_date) ORDER BY trunc(last_update_date) DESC, estado, last_update_date"
				+ " ) / 4.0) AS pagina_por_fecha FROM t_mis_prehuecos_lineal t LEFT JOIN v_datos_diario_art a ON a.cod_art = t.cod_art WHERE"
				+ " t.cod_centro = ? AND t.mac = ? AND estado < 2 ORDER BY trunc(last_update_date) DESC, estado, last_update_date ) SELECT * FROM (select t.* ,"
				+ " max(pagina_global) OVER( PARTITION BY cod_centro) pagina_total from ( SELECT c.*, DENSE_RANK() OVER( ORDER BY "
				+ " trunc(last_update_date) DESC, pagina_por_fecha ) AS pagina_global FROM cte c ORDER BY trunc(last_update_date) DESC, fila_por_fecha) t"
				+ " order by fecha desc, fila_por_fecha) where pagina_global = ?");

		params.add(centro);
		params.add(mac);
		params.add(pagActual);

		List<StockAlmacen> lista = new ArrayList<StockAlmacen>();
		try {
			lista = (List<StockAlmacen>) this.jdbcTemplate.query(query.toString(), this.rwStockAlmacenMapper, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		return lista;
	}

	private RowMapper<StockAlmacen> rwStockAlmacenMapper = new RowMapper<StockAlmacen>() {
		public StockAlmacen mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			StockAlmacen stockAlmacen = new StockAlmacen();
			
			stockAlmacen.setCodArt(resultSet.getLong("cod_art"));
			stockAlmacen.setStockLineal(resultSet.getString("stock_lineal"));
			stockAlmacen.setEstado(resultSet.getLong("estado"));
			stockAlmacen.setDescArt(resultSet.getString("descrip_art").trim());
			stockAlmacen.setFecha(Utilidades.formatearFecha_ddMMyyyyBarra(resultSet.getDate("fecha")));
			stockAlmacen.setPagTotal(resultSet.getLong("pagina_total"));
			return stockAlmacen;
		}
	};
	
	
	@Override
	public int updateEstadoPrehueco(final String codCentro, final String mac, final String codArt, final int nuevoEstado) {
        final Timestamp now = new Timestamp(new Date().getTime());
        int rows;

        if (nuevoEstado == 2) {
            String sql = "UPDATE T_MIS_PREHUECOS_LINEAL " +
                         "SET ESTADO = ?, LAST_UPDATE_DATE = ?, FEC_VALIDADO = ? " +
                         "WHERE COD_CENTRO = ? AND MAC = ? AND COD_ART = ? AND TRUNC(NVL(FEC_VALIDADO,SYSDATE)) = TRUNC(SYSDATE)";
            rows = jdbcTemplate.update(sql, nuevoEstado, now, now, codCentro, mac, codArt);
        } else {
            String sql = "UPDATE T_MIS_PREHUECOS_LINEAL " +
                         "SET ESTADO = ?, LAST_UPDATE_DATE = ?, FEC_VALIDADO = null " + 
                         "WHERE COD_CENTRO = ? AND MAC = ? AND COD_ART = ? AND TRUNC(NVL(FEC_VALIDADO,SYSDATE)) = TRUNC(SYSDATE)";
            rows = jdbcTemplate.update(sql, nuevoEstado, now, codCentro, mac, codArt);
        }

        return rows;
    }



}
