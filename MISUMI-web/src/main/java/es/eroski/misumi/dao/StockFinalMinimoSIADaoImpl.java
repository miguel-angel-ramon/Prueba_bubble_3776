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

import es.eroski.misumi.dao.iface.StockFinalMinimoSIADao;
import es.eroski.misumi.filter.SecurityFilter;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class StockFinalMinimoSIADaoImpl implements StockFinalMinimoSIADao{
	 
	 private static Logger logger = Logger.getLogger(StockFinalMinimoSIADaoImpl.class);
	 private JdbcTemplate jdbcTemplate;
	 private RowMapper<StockFinalMinimo> rwStockFinalMinimoMap = new RowMapper<StockFinalMinimo>() {
			public StockFinalMinimo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new StockFinalMinimo(resultSet.getLong("COD_LOC"),resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getFloat("STOCK_FIN_MIN_L"), resultSet.getFloat("STOCK_FIN_MIN_M"),
			    			resultSet.getFloat("STOCK_FIN_MIN_X"), resultSet.getFloat("STOCK_FIN_MIN_J"),
			    			resultSet.getFloat("STOCK_FIN_MIN_V"), resultSet.getFloat("STOCK_FIN_MIN_S"),
			    			resultSet.getFloat("STOCK_FIN_MIN_D"), resultSet.getFloat("CAPACIDAD"), 
			    			resultSet.getFloat("VENTA_MEDIA"), resultSet.getLong("FACING_CENTRO"), 
			    			resultSet.getLong("FACING_CENTRO_PREVIO"), null
				    );
			}
		};

	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    
	    @Override
	    public List<StockFinalMinimo> findAll(StockFinalMinimo stockFinalMinimo) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_LOC, COD_ARTICULO, STOCK_FIN_MIN_L, STOCK_FIN_MIN_M, STOCK_FIN_MIN_X, STOCK_FIN_MIN_J, " +
												" STOCK_FIN_MIN_V, STOCK_FIN_MIN_S, STOCK_FIN_MIN_D, CAPACIDAD, VENTA_MEDIA, FACING_CENTRO, FACING_CENTRO_PREVIO " 
												+ " FROM STOCK_FINAL_MINIMO ");
	    
	    	
	        if (stockFinalMinimo  != null){
	        	if(stockFinalMinimo.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(stockFinalMinimo.getCodLoc());	        		
	        	}
	        	if(stockFinalMinimo.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(stockFinalMinimo.getCodArticulo());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinL()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_L = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinL());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinM()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_M = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinM());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinX()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_X = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinX());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinJ()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_J = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinJ());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinV()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_V = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinV());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinS()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_S = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinS());	        		
	        	}
	        	if(stockFinalMinimo.getStockFinMinD()!=null){
	        		 where.append(" AND STOCK_FIN_MIN_D = ? ");
		        	 params.add(stockFinalMinimo.getStockFinMinD());	        		
	        	}
	        	if(stockFinalMinimo.getCapacidad()!=null){
	        		 where.append(" AND CAPACIDAD = ? ");
		        	 params.add(stockFinalMinimo.getCapacidad());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo ");
			query.append(order);

			List<StockFinalMinimo> stockFinalMinimoLista = null;	
			
			try {
				stockFinalMinimoLista = (List<StockFinalMinimo>) this.jdbcTemplate.query(query.toString(),this.rwStockFinalMinimoMap, params.toArray()); 
			} catch (Exception e) {
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
				
		    return stockFinalMinimoLista;
	    }
}
