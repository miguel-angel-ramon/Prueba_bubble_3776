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

import es.eroski.misumi.dao.iface.MovimientoStockDao;
import es.eroski.misumi.model.MapaAprovFestivo;
import es.eroski.misumi.model.MovimientoStock;
import es.eroski.misumi.model.pda.PdaUltimosMovStocks;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class MovimientoStockDaoImpl implements MovimientoStockDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(MovimientoStockDaoImpl.class);
	

	 	@Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	 
		private RowMapper<MovimientoStock> rwMovimientoStockMap = new RowMapper<MovimientoStock>() {
			public MovimientoStock mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new MovimientoStock( resultSet.getString("FECHA"), resultSet.getString("ESFESTIVO")
			    						  , resultSet.getString("ESHOY"), resultSet.getLong("COD_CENTRO")
			    						  , resultSet.getLong("COD_ART"), resultSet.getFloat("STOCK_INICIAL")
			    						  , resultSet.getFloat("STOCK_KG_VAL"), resultSet.getFloat("STOCK_PEN_RECI")
			    						  , resultSet.getFloat("ENTRADAS"), resultSet.getFloat("SALIDAS")
			    						  , resultSet.getFloat("SALIDAS_PROMO"), resultSet.getFloat("SALIDAS_FORZ")
			    						  , resultSet.getFloat("AJUSTE_CONTEO"), resultSet.getFloat("REGULAR")
			    						  , resultSet.getDate("FECHA_GEN"), resultSet.getString("ROTURA"), resultSet.getFloat("STOCK_FINAL")
				    );
			}
		};		
		
		private RowMapper<PdaUltimosMovStocks> rwMovimientoStockPdaMap = new RowMapper<PdaUltimosMovStocks>() {
			public PdaUltimosMovStocks mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new PdaUltimosMovStocks(resultSet.getString("FECHA"), 
			    		resultSet.getFloat("VENTAS"), 
			    		resultSet.getFloat("CORR1"),
			    		resultSet.getFloat("CORR2"),
			    		resultSet.getString("FILA")
				    );
			}
		};
		
		private String getMappedField (String fieldName) {
		   	  if (fieldName.toUpperCase().equals("STOCKKGVAL")){
		   		  return " STOCK_KG_VAL ";
			  }else if (fieldName.toUpperCase().equals("STOCKPENRECI")){
		  	      return "STOCK_PEN_RECI";
		  	  }else if(fieldName.toUpperCase().equals("SALIDASPROMO")){
		  	      return "SALIDAS_PROMO";
		  	  }else if(fieldName.toUpperCase().equals("SALIDASFORZ")){
		  	      return "SALIDAS_FORZ";
		  	  }else if(fieldName.toUpperCase().equals("AJUSTECONTEO")){
		  	      return "AJUSTE_CONTEO";
		  	  }else if(fieldName.toUpperCase().equals("FECHAGEN")){
		  	      return "FECHA_GEN";
		  	  }else if(fieldName.toUpperCase().equals("STOCKINICIAL")){
		  	      return "STOCK_INICIAL";
		  	  }else if(fieldName.toUpperCase().equals("STOCKFINAL")){
		  	      return "STOCK_FINAL";
		  	  }else {
		  	      return fieldName;
		  	  }
	  	}
		
	    
	    
	    @Override
	    public List<MovimientoStock> findAllLastDays(MovimientoStock mc, int lastDays) throws Exception  {
	    	//StringBuffer where = new StringBuffer(3000);
	    	List<Object> params = new ArrayList<Object>();
	    	//where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT DECODE(TO_CHAR(((SYSDATE + 1) - MOV.L), 'DD'), '01', TO_CHAR(((SYSDATE + 1) - MOV.L), 'DD MON'), TO_CHAR(((SYSDATE + 1) - MOV.L), 'DD')) FECHA, " +
	    										  " 	   DECODE(FECHA_FESTIVO, NULL, 'F', 'T') ESFESTIVO, " +
	    										  "		   DECODE(TO_CHAR ((SYSDATE + 1 - mov.l), 'DD'),TO_CHAR(SYSDATE,'DD'),'S','N') ESHOY, " +
	    										  "		   MOV.COD_CENTRO, MOV.COD_ART, MOV.STOCK STOCK_FINAL, MOV.STOCK_KG_VAL, MOV.STOCK_PEN_RECI, MOV.ENTRADAS, " +
	    										  "        MOV.SALIDAS, MOV.SALIDAS_PROMO, MOV.SALIDAS_FORZ, MOV.AJUSTE_CONTEO, MOV.REGULAR, MOV.FECHA_GEN, " +
	    										  "        DECODE(ROTURAS.CONT_ROTURAS, NULL, 'N0', 'SI') ROTURA, NULL STOCK_INICIAL " +
	    										  " FROM ( SELECT DIAS.L, COD_CENTRO, COD_ART," +
	    										  "               CASE WHEN (DIAS.L < 1) THEN -999999 " +
	    										  "                    WHEN (DIAS.L > :LASTDAYS) THEN -999999 " +
	    										  "               ELSE STOCK.STOCK END AS STOCK,  " +
	    										  "				  NULL STOCK_KG_VAL, NULL STOCK_PEN_RECI, NULL ENTRADAS, NULL SALIDAS, NULL SALIDAS_PROMO, " +
	    										  "               NULL SALIDAS_FORZ, NULL AJUSTE_CONTEO, NULL REGULAR, FECHA_GEN " +
	    										  "        FROM  ( SELECT COD_CENTRO, COD_ART, STOCK, STOCK_KG_VAL, STOCK_PEN_RECI, ENTRADAS," +
	    										  "                       SALIDAS, SALIDAS_PROMO, SALIDAS_FORZ, AJUSTE_CONTEO, REGULAR, FECHA_GEN, " +
	    										  "                       (TO_CHAR(SYSDATE + 1, 'J') - TO_CHAR(FECHA_GEN, 'J')) DIAANT " +
	    										  "                FROM MOVIMIENTO_STOCK M " +
	    										  "                WHERE M.COD_CENTRO = :CENTRO AND M.COD_ART = :ART AND " +
	    										  "                      M.FECHA_GEN BETWEEN (SYSDATE - :LASTDAYS) AND (SYSDATE)) STOCK, " +
	    										  "              ( SELECT :LASTDAYS + LEVEL L FROM DUAL CONNECT BY LEVEL < TO_CHAR((SYSDATE - :LASTDAYS)+1, 'D') " +
	    										  "                UNION " +
	    										  "                SELECT LEVEL L FROM DUAL CONNECT BY LEVEL <= :LASTDAYS " +
	    										  "                UNION " +
	    										  "                SELECT -1 * ((7 - TO_CHAR(SYSDATE, 'D')) - LEVEL) L FROM DUAL CONNECT BY LEVEL <= (7 - TO_CHAR(SYSDATE, 'D')) ) DIAS " +
	    										  "        WHERE STOCK.DIAANT (+) = DIAS.L " +
	    										  "        ORDER BY DIAS.L) MOV, " +
	    										  "      ( SELECT DIAS.L, COD_LOC, COD_ARTICULO, CONT_ROTURAS, FEC_ROTURA " +
	    										  "        FROM  ( SELECT COD_LOC, COD_ARTICULO, FEC_ROTURA, COUNT(*) CONT_ROTURAS, " +
	    										  "                       (TO_CHAR(SYSDATE + 1, 'J') - TO_CHAR(FEC_ROTURA, 'J')) DIAANT" +
	    										  "                FROM ROTURAS WHERE COD_LOC = :CENTRO AND COD_ARTICULO = :ART " +
	    										  "                GROUP BY COD_LOC, COD_ARTICULO, FEC_ROTURA)  ROT, " +
	    										  "              ( SELECT :LASTDAYS + LEVEL L FROM DUAL CONNECT BY LEVEL < TO_CHAR((SYSDATE - :LASTDAYS) +1, 'D') " +
	    										  "                UNION " +
	    										  "                SELECT LEVEL L FROM DUAL CONNECT BY LEVEL <= :LASTDAYS " +
	    										  "                UNION " +
	    										  "                SELECT -1 * ((7 - TO_CHAR(SYSDATE, 'D')) - LEVEL) L FROM DUAL CONNECT BY LEVEL <= (7 - TO_CHAR(SYSDATE, 'D')) ) DIAS " +
	    										  "        WHERE ROT.DIAANT (+) = DIAS.L " +
	    										  "        ORDER BY DIAS.L) ROTURAS," +
	    										  "      ( SELECT DIAS.L, COD_CENTRO, FECHA_FESTIVO " +
	    										  "        FROM ( SELECT COD_CENTRO, FECHA_FESTIVO, (TO_CHAR(SYSDATE+1, 'J') - TO_CHAR(FECHA_FESTIVO, 'J')) DIAANT " +
	    										  "               FROM  festivo_centro A " +
	    										  "               where  A.FECHA_GEN = (SELECT MAX(B.FECHA_GEN) FROM festivo_centro  B" +
	    										  "                                     WHERE B.COD_CENTRO = A.COD_CENTRO" +
	    										  "                                      AND B.fecha_festivo = A.fecha_festivo" +
	    										  //Comentado por petición 59566 "                                      and b.FLG_ENVIADO_PBL = 'S' " +
	    										  "                                      ) " +
	    										  "                      and A.estado <> 'B' and TO_DATE(A.FECHA_FESTIVO,'DD/MM/YYYY')  BETWEEN (TO_DATE(((SYSDATE - :LASTDAYS) + 1) - (TO_CHAR ((SYSDATE - :LASTDAYS),'D')),'DD/MM/YYYY')) AND (TO_DATE(SYSDATE + (7 - TO_CHAR(SYSDATE, 'D')),'DD/MM/YYYY')) and A.cod_centro = :CENTRO) FC," +
	    										  "              ( SELECT :LASTDAYS + LEVEL L FROM DUAL CONNECT BY LEVEL < TO_CHAR((SYSDATE - :LASTDAYS)+1, 'D')" +
	    										  "                UNION " +
	    										  "                SELECT LEVEL L FROM DUAL CONNECT BY LEVEL <= :LASTDAYS" +
	    										  "                UNION" +
	    										  "                SELECT -1 * ((7 - TO_CHAR(SYSDATE, 'D')) - LEVEL) L FROM DUAL CONNECT BY LEVEL <= (7 - TO_CHAR(SYSDATE, 'D')) ) DIAS " +
	    										  "        WHERE FC.DIAANT (+) = DIAS.L" +
	    										  "        ORDER BY DIAS.L) FESTIVOS      " +
	    										  " WHERE MOV.L  = ROTURAS.L and MOV.L = FESTIVOS.L");
	    	
	        if (mc  != null){
	        	params.add(lastDays);
	        	params.add(mc.getCodCentro());
	        	params.add(mc.getCodArt());
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(mc.getCodCentro());
	        	params.add(mc.getCodArt());
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(mc.getCodCentro());
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	
	        }
	        
	        //query.append(where);

	        //StringBuffer order = new StringBuffer(3000);
			//order.append(" order by cod_loc, cod_articulo ");
			//query.append(order);

			List<MovimientoStock> MovimientoStockLista = null;		
			
			try {
				MovimientoStockLista = (List<MovimientoStock>) this.jdbcTemplate.query(query.toString(),this.rwMovimientoStockMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
			/* ... Correlación de stock*/
			
			
		    return MovimientoStockLista;
	    }

	    @Override
	    public List<MovimientoStock> findAllDetailsLastDays(MovimientoStock mc, int lastDays, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
    	
	    	StringBuffer query = new StringBuffer(" SELECT null FECHA, null ESFESTIVO, null ESHOY, COD_CENTRO, COD_ART, STOCK STOCK_FINAL, STOCK_KG_VAL, STOCK_PEN_RECI, ENTRADAS," +
	    										  "        SALIDAS, SALIDAS_PROMO, SALIDAS_FORZ, AJUSTE_CONTEO, REGULAR, FECHA_GEN, null ROTURA, " +
	    										  "        (NVL(STOCK, 0) - NVL(ENTRADAS, 0) + NVL(SALIDAS, 0) + NVL(SALIDAS_PROMO, 0) + NVL(SALIDAS_FORZ, 0) - NVL(AJUSTE_CONTEO, 0) - NVL(REGULAR, 0)) STOCK_INICIAL " +
	    										  " FROM MOVIMIENTO_STOCK");			  
	    	
	    	where.append(" WHERE COD_CENTRO= ? AND COD_ART = ?" +
					     " AND FECHA_GEN BETWEEN (SYSDATE - ?) AND (SYSDATE - 1)");  	
	        if (mc  != null){
	        	params.add(mc.getCodCentro());
	        	params.add(mc.getCodArt());
	        	params.add(lastDays);
	        }
	        
	        query.append(where);
	               			
	        if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
					query.append(order);
				}
				query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
			}         
	        
			List<MovimientoStock> movimientoStockLista = null;		
			
			try {
				movimientoStockLista = (List<MovimientoStock>) this.jdbcTemplate.query(query.toString(),this.rwMovimientoStockMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return movimientoStockLista;
	    }
	    

	    private String findSysdate() throws Exception  {
	    	
	    	StringBuffer query = new StringBuffer(" select to_char(sysdate,'dd') from dual ");			  
			String today = this.jdbcTemplate.queryForObject(query.toString(),String.class); 
		    return today;
	    }

	    @Override
	    public List<PdaUltimosMovStocks> findAllDetailsLastDaysPda(MovimientoStock mc) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
    	
	    	StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	referencias.append(mc.getCodArt());
	    	if (null != mc.getReferencias()){
	    		for(Long referencia : mc.getReferencias()){
	    			referencias.append(", ").append(referencia);
	    		}
	    	}

	    	/*StringBuffer query = new StringBuffer("SELECT MAX(TO_CHAR(FECHA_GEN,'DD/MM/YYYY')) FECHA_GEN_M, " +
	    										  "        SUM((NVL(SALIDAS, 0) + NVL(SALIDAS_PROMO, 0) + NVL(SALIDAS_FORZ, 0))) VENTAS_M," +
	    										  " 	   SUM(NVL(AJUSTE_CONTEO, 0)) CORR1_M," +
	    										  "        SUM(NVL(REGULAR, 0)) CORR2_M, FECHA_GEN  " +
	    										  " FROM MOVIMIENTO_STOCK");		*/	  
	    	StringBuffer query = new StringBuffer("SELECT null FILA, FECHA_GEN FECHA, null VENTAS, null CORR1, null CORR2  " +
					  " FROM MOVIMIENTO_STOCK");
	    	
	    	where.append(" WHERE COD_CENTRO= ? AND COD_ART IN ( " + referencias.toString() + " ) ");  	
        	params.add(mc.getCodCentro());
        	
        	where.append(" AND ((NVL(SALIDAS, 0) + NVL(SALIDAS_PROMO, 0) + NVL(SALIDAS_FORZ, 0)) <> 0 OR " +
        				 " 	   NVL(AJUSTE_CONTEO, 0)<> 0 OR " +
        				 "     NVL(REGULAR, 0) <> 0) ");
        	
        	where.append(" AND TRUNC(ADD_MONTHS(SYSDATE,-1),'MM') <= FECHA_GEN AND FECHA_GEN <= TRUNC(SYSDATE) ");
	        
	        query.append(where);

	        query.append(" GROUP BY FECHA_GEN");
	        query.append(" ORDER BY FECHA_GEN DESC");
	        
       	 	
       	 	
			List<PdaUltimosMovStocks> movimientoStockLista = null;		
			
			try {
				movimientoStockLista = (List<PdaUltimosMovStocks>) this.jdbcTemplate.query(query.toString(),this.rwMovimientoStockPdaMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return movimientoStockLista;
	    }
	    
	    
	    @Override
	    public List<PdaUltimosMovStocks> findAllDetailsLastDaysPdaPaginada(MovimientoStock mc, int inicioPaginacion, int finPaginacion) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
    	
	    	StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	referencias.append(mc.getCodArt());
	    	if (null != mc.getReferencias()){
	    		for(Long referencia : mc.getReferencias()){
	    			referencias.append(", ").append(referencia);
	    		}
	    	}

	    	StringBuffer query = new StringBuffer("SELECT ROWNUM FILA, FECHA, VENTAS,  CORR1, CORR2, FECHA_GEN  FROM (" +
												  "	SELECT ROWNUM FILA, FECHA, VENTAS,  CORR1, CORR2, FECHA_GEN  FROM (" +
	    										  "		SELECT FECHA_GEN_M FECHA, VENTAS_M VENTAS, CORR1_M CORR1, CORR2_M CORR2, FECHA_GEN  FROM " +
	    										  " 		(SELECT MAX(TO_CHAR(FECHA_GEN,'DD/MM/YYYY')) FECHA_GEN_M, " +
	    										  "        		SUM((NVL(SALIDAS, 0) + NVL(SALIDAS_PROMO, 0) + NVL(SALIDAS_FORZ, 0))) VENTAS_M," +
	    										  " 	   		SUM(NVL(AJUSTE_CONTEO, 0)) CORR1_M," +
	    										  "        		SUM(NVL(REGULAR, 0)) CORR2_M, FECHA_GEN  " +
	    										  " 		FROM MOVIMIENTO_STOCK");			  
	    	
	    	where.append(" WHERE COD_CENTRO= ? AND COD_ART IN ( " + referencias.toString() + " ) ");  	
        	params.add(mc.getCodCentro());
        	
        	where.append(" AND ((NVL(SALIDAS, 0) + NVL(SALIDAS_PROMO, 0) + NVL(SALIDAS_FORZ, 0)) <> 0 OR " +
        				 " 	   NVL(AJUSTE_CONTEO, 0)<> 0 OR " +
        				 "     NVL(REGULAR, 0) <> 0) ");
        	
        	where.append(" AND TRUNC(ADD_MONTHS(SYSDATE,-1),'MM') <= FECHA_GEN AND FECHA_GEN <= TRUNC(SYSDATE) ");
	        
	        query.append(where);

	        query.append("     GROUP BY FECHA_GEN");
	        query.append("     ORDER BY FECHA_GEN DESC)");
	        query.append(" ORDER BY FECHA_GEN DESC))");
	        
	        query.append(" WHERE FILA > ? AND FILA <= ? ");
	        params.add(inicioPaginacion);	
	        params.add(finPaginacion);	
	        	        
	        
       	 	
			List<PdaUltimosMovStocks> movimientoStockLista = null;		
			
			try {
				movimientoStockLista = (List<PdaUltimosMovStocks>) this.jdbcTemplate.query(query.toString(),this.rwMovimientoStockPdaMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return movimientoStockLista;
	    }
	    
	    
	    

}


