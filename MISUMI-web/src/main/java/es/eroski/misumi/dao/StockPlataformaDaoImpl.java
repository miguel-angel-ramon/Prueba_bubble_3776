package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.StockPlataformaDao;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.util.StackTraceManager;
 	
@Repository
public class StockPlataformaDaoImpl implements StockPlataformaDao {
	
	 private static Logger logger = Logger.getLogger(StockPlataformaDaoImpl.class);
	 private JdbcTemplate jdbcTemplate;
	

	 	@Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    }
	 
    
	    @Override
	    public StockPlataforma find(final StockPlataforma sp) throws Exception  {
	    	
			List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();

			declaredParameters.add(new SqlParameter("p_cod_loc", Types.INTEGER));
			declaredParameters.add(new SqlParameter("p_cod_art_formlog", Types.INTEGER));
			declaredParameters.add(new SqlOutParameter("p_stock", Types.INTEGER));
			declaredParameters.add(new SqlOutParameter("p_r_error", Types.INTEGER));
			
			
			Map<String, Object> mapaResultados = null;	
			
			try {
				   mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {

				    @Override
					public
				    CallableStatement createCallableStatement(Connection con) throws SQLException {
				        CallableStatement stmnt = con.prepareCall("{call PK_APR_MISUMI.P_OBT_STOCK_PLATAFORMA(?, ?, ?, ?)}");

				        stmnt.setInt(1, sp.getCodCentro().intValue());
				        stmnt.setInt(2, sp.getCodArt().intValue());
				        stmnt.registerOutParameter(3, Types.INTEGER);
				        stmnt.registerOutParameter(4, Types.INTEGER);
				        
				        return stmnt;
				    }
				}, declaredParameters);
				
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
			
			
			StockPlataforma spSalida = new StockPlataforma();
			
			spSalida.setCodCentro(sp.getCodCentro());
			spSalida.setCodArt(sp.getCodArt());
			Long valorStock = new Long(0);

			if (mapaResultados != null){
				if (mapaResultados.get("p_r_error")!=null && "0".equals(mapaResultados.get("p_r_error").toString())){
					valorStock = Long.parseLong(mapaResultados.get("p_stock").toString());
				}
			}
			
			spSalida.setStock(valorStock);
			
		    return spSalida;
	    }
}


