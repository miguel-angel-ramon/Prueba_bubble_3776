package es.eroski.misumi.dao.prehuecos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosAlmacenDao;
import es.eroski.misumi.model.pda.prehuecos.AlmacenPrehuecos;
import es.eroski.misumi.model.pda.prehuecos.StockAlmacen;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PrehuecosAlmacenDaoImpl implements PrehuecosAlmacenDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacen(Long centro, String mac, String codArt) {
	    List<Object> params = new ArrayList<Object>();

	    StringBuffer query = new StringBuffer("SELECT t.*, a.descrip_art FROM T_MIS_PREHUECOS_LINEAL t LEFT JOIN v_datos_diario_art a ON a.cod_art = t.cod_art"
	    		+ " WHERE a.cod_art = t.cod_art AND t.COD_ART = ? AND t.mac = ? AND t.COD_CENTRO = ? AND (t.estado < 2 OR trunc(t.fec_validado) = trunc(sysdate))"
	    		+ " ORDER BY t.COD_ART");

	    params.add(codArt);
	    params.add(mac);
	    params.add(centro);

	    List<AlmacenPrehuecos> lista = new ArrayList<AlmacenPrehuecos>();
	    try {
	        lista = (List<AlmacenPrehuecos>) this.jdbcTemplate.query(query.toString(), 
	                this.rwAlmacenMapper, params.toArray());

	        if (!lista.isEmpty()) {
	            lista = lista.subList(0, 1);  
	        }
	    } catch (Exception e) {

	        Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
	    }

	    return lista;
	}

	@Override
	public List<AlmacenPrehuecos> getReferenciasPrehuecosAlmacenActualizada(Long centro, String mac, String codArt) {
	    List<Object> params = new ArrayList<Object>();

	    StringBuffer query = new StringBuffer("WITH cte AS ( SELECT t.*, a.descrip_art, trunc(last_update_date) fecha,"
	    		+ " ROW_NUMBER() OVER( PARTITION BY mac ORDER BY trunc(last_update_date) DESC, estado, last_update_date"
	    		+ " ) fila, count(*) OVER( PARTITION BY mac ORDER BY mac ) total FROM t_mis_prehuecos_lineal t LEFT JOIN v_datos_diario_art a ON"
	    		+ " a.cod_art = t.cod_art WHERE t.cod_centro = ? AND t.mac = ? AND estado < 2 "
	    		+ " ORDER BY trunc(last_update_date) DESC, estado, last_update_date) SELECT c.* FROM cte c WHERE fila > nvl((SELECT fila"
	    		+ "  FROM cte a WHERE a.cod_art = ? AND a.fila <> a.total AND a.total <> 1 ), 0) and ? <> c.cod_art and rownum <2");
	    
	    params.add(centro);
	    params.add(mac);
	    params.add(codArt);
	    params.add(codArt);

	    List<AlmacenPrehuecos> lista = new ArrayList<AlmacenPrehuecos>();
	    try {
	        lista = (List<AlmacenPrehuecos>) this.jdbcTemplate.query(query.toString(), 
	                this.rwAlmacenMapper, params.toArray());

	        if (!lista.isEmpty()) {
	            lista = lista.subList(0, 1);  
	        }
	    } catch (Exception e) {

	        Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
	    }

	    return lista;
	}
	
	private RowMapper<AlmacenPrehuecos> rwAlmacenMapper = new RowMapper<AlmacenPrehuecos>() {
	    public AlmacenPrehuecos mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	        
	        AlmacenPrehuecos almacenPrehuecos = new AlmacenPrehuecos();
	        
	        almacenPrehuecos.setCodCentro(resultSet.getInt("cod_centro"));
	        almacenPrehuecos.setMac(resultSet.getString("mac"));
	        almacenPrehuecos.setCodArt(resultSet.getInt("cod_art"));
	        almacenPrehuecos.setStockLineal(resultSet.getInt("stock_lineal"));
	        almacenPrehuecos.setEstado(resultSet.getInt("estado"));
	        almacenPrehuecos.setDescArt(resultSet.getString("descrip_art").trim());
	        almacenPrehuecos.setCreationDate(resultSet.getDate("creation_date"));
	        almacenPrehuecos.setLastUpdateDate(resultSet.getDate("last_update_date"));
	        almacenPrehuecos.setFecValidado(resultSet.getDate("fec_validado"));
	        
	        return almacenPrehuecos;
	    }
	};
	
	@Override
	public List<StockAlmacen> getReferenciasPrehuecosStockAlmacen(Long centro, String mac, String codArt) {
	    List<Object> params = new ArrayList<Object>();

	    StringBuffer query = new StringBuffer(
	    	      "WITH cte AS ( "
	    	    		    + "  SELECT t.*, a.descrip_art, TRUNC(t.last_update_date) AS fecha, "
	    	    		    + "         ROW_NUMBER() OVER (PARTITION BY TRUNC(t.last_update_date) "
	    	    		    + "                            ORDER BY TRUNC(t.last_update_date) DESC, t.estado, t.last_update_date) AS fila_por_fecha, "
	    	    		    + "         CEIL( ROW_NUMBER() OVER (PARTITION BY TRUNC(t.last_update_date) "
	    	    		    + "                                   ORDER BY TRUNC(t.last_update_date) DESC, t.estado, t.last_update_date) "
	    	    		    + "               / 4.0 ) AS pagina_por_fecha "
	    	    		    + "    FROM t_mis_prehuecos_lineal t "
	    	    		    + "    LEFT JOIN v_datos_diario_art a "
	    	    		    + "           ON a.cod_art = t.cod_art "
	    	    		    + "   WHERE t.cod_centro = ? "
	    	    		    + "     AND t.mac       = ? "
	    	    		    + "     AND t.cod_art   = ? "
	    	    		    + "     AND ( t.estado < 2 "
	    	    		    + "       OR TRUNC(t.fec_validado) = TRUNC(SYSDATE) ) "
	    	    		    + "   ORDER BY TRUNC(t.last_update_date) DESC, t.estado, t.last_update_date "
	    	    		    + ") "
	    	    		    + "SELECT * "
	    	    		    + "  FROM ( "
	    	    		    + "    SELECT t.*, MAX(pagina_global) OVER (PARTITION BY cod_centro) AS pagina_total "
	    	    		    + "      FROM ( "
	    	    		    + "        SELECT c.*, DENSE_RANK() OVER (ORDER BY TRUNC(c.last_update_date) DESC, c.pagina_por_fecha) AS pagina_global "
	    	    		    + "          FROM cte c "
	    	    		    + "         ORDER BY TRUNC(c.last_update_date) DESC, c.fila_por_fecha "
	    	    		    + "      ) t "
	    	    		    + "     ORDER BY fecha DESC, fila_por_fecha "
	    	    		    + "  )"
	    	    		    );


	    params.add(centro);  
	    params.add(mac);     
	    params.add(codArt);  
	    List<StockAlmacen> listaStok = new ArrayList<StockAlmacen>();
	    try {
	    	listaStok = (List<StockAlmacen>) this.jdbcTemplate.query(query.toString(), 
	                this.rwStockAlmacenMapper, params.toArray());
	    } catch (Exception e) {
	        Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
	    }

	    return listaStok;
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


}
