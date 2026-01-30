package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.HistoricoUnidadesVentaDao;
import es.eroski.misumi.model.DiaVentasUltimasOfertas;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.HistoricoUnidadesVenta;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class HistoricoUnidadesVentaDaoImpl implements HistoricoUnidadesVentaDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(HistoricoUnidadesVentaDaoImpl.class);
	 private RowMapper<HistoricoUnidadesVenta> rwHistoricoUnidadesVentaMap = new RowMapper<HistoricoUnidadesVenta>() {
			public HistoricoUnidadesVenta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
			    return new HistoricoUnidadesVenta(resultSet.getLong("COD_LOC"),resultSet.getDate("FECHA_VENTA"),
			    			resultSet.getLong("COD_ARTICULO"), 
			    			resultSet.getFloat("UNID_VENTA_TARIFA"),
			    			resultSet.getFloat("UNID_VENTA_COMPETENCIA"), resultSet.getFloat("UNID_VENTA_OFERTA"),
			    			resultSet.getFloat("UNID_VENTA_ANTICIPADA"), resultSet.getFloat("UNID_VENTA_TOTAL"),
			    			resultSet.getLong("UNID_VENTA_TARIFA_BD"),
			    			resultSet.getLong("UNID_VENTA_COMPETENCIA_BD"), resultSet.getLong("UNID_VENTA_OFERTA_BD"),
			    			resultSet.getLong("UNID_VENTA_ANTICIPADA_BD"), resultSet.getLong("UNID_VENTA_TOTAL_BD"),
			    			resultSet.getString("PORCION_CEN"), resultSet.getLong("CREATED_BY"), 
			    			resultSet.getDate("CREATION_DATE"), resultSet.getLong("LAST_UPDATED_BY"),
			    			resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getLong("LAST_UPDATE_LOGIN"),
			    			resultSet.getLong("TECLE"), resultSet.getLong("TCN"), 
			    			resultSet.getFloat("TOTAL_VENTAS"), null, null);
			}

		};

		//Mapear las filas de la tabla en objetos.
		private RowMapper<HistoricoUnidadesVenta> rwVentaUltimaOfertaMap = new RowMapper<HistoricoUnidadesVenta>() {
			public HistoricoUnidadesVenta mapRow(ResultSet rs, int rowNum) throws SQLException {
				HistoricoUnidadesVenta historicoUnidadesVenta= new HistoricoUnidadesVenta();

				historicoUnidadesVenta.setFechaVenta(rs.getDate("FECHA_VENTA"));
				historicoUnidadesVenta.setUnidVentaTarifa(rs.getFloat("TARIFA"));
				historicoUnidadesVenta.setUnidVentaCompetencia(rs.getFloat("COMPETENCIA"));
				historicoUnidadesVenta.setUnidVentaOferta(rs.getFloat("OFERTA"));
				historicoUnidadesVenta.setUnidVentaAnticipada(rs.getFloat("ANTICIPADA"));
				historicoUnidadesVenta.setTotalVentas(rs.getFloat("TOTAL_VENTAS"));

				return historicoUnidadesVenta;
			}
		};
		
		//Mapear las filas de la tabla en objetos.
		private RowMapper<DiaVentasUltimasOfertas> rwDiaVentasMap = new RowMapper<DiaVentasUltimasOfertas>() {
			public DiaVentasUltimasOfertas mapRow(ResultSet rs, int rowNum) throws SQLException {
				DiaVentasUltimasOfertas diaVentasUltimasOfertas= new DiaVentasUltimasOfertas();

				diaVentasUltimasOfertas.setcVD(rs.getFloat("TOTAL_VENTAS"));
				diaVentasUltimasOfertas.setD(rs.getString("FECHA_FESTIVO"));
				diaVentasUltimasOfertas.setDFormat(rs.getString("FECHA_FESTIVO_FORMAT"));
				diaVentasUltimasOfertas.setLaboral(rs.getString("MAX_TIPO_DIA"));
				diaVentasUltimasOfertas.setAnticipada(rs.getString("ANTICIPADA"));
				diaVentasUltimasOfertas.setOferta(rs.getString("OFERTA"));

				return diaVentasUltimasOfertas;
			}
		};
		
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<HistoricoUnidadesVenta> findAll(HistoricoUnidadesVenta historicoUnidadesVenta) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT  COD_LOC, FECHA_VENTA, COD_ARTICULO, UNID_VENTA_TARIFA, " +
	    			"UNID_VENTA_COMPETENCIA, UNID_VENTA_OFERTA, UNID_VENTA_ANTICIPADA, UNID_VENTA_TOTAL, " +
	    			"UNID_VENTA_TARIFA_BD, UNID_VENTA_COMPETENCIA_BD, UNID_VENTA_OFERTA_BD, UNID_VENTA_ANTICIPADA_BD, UNID_VENTA_TOTAL_BD, " +
	    			"PORCION_CEN, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, " +
	    			"TCN, " +
	    			"DECODE(PORCION_CEN,'N',(UNID_VENTA_TARIFA + UNID_VENTA_COMPETENCIA + UNID_VENTA_OFERTA), " +
	    				" (UNID_VENTA_TARIFA_BD + UNID_VENTA_COMPETENCIA_BD + UNID_VENTA_OFERTA_BD)) TOTAL_VENTAS" +
	    			" FROM HISTORICO_UNIDADES_VENTA ");
	    
	    	
	        if (historicoUnidadesVenta  != null){
	        	if(historicoUnidadesVenta.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(historicoUnidadesVenta.getCodLoc());	        		
	        	}
	        	if(historicoUnidadesVenta.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(historicoUnidadesVenta.getCodArticulo());	        		
	        	}
	        	if(historicoUnidadesVenta.getFechaVenta()!=null){
	        		 where.append(" AND FECHA_VENTA = TRUNC(?) ");
		        	 params.add(historicoUnidadesVenta.getFechaVenta());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo ");
			query.append(order);

			List<HistoricoUnidadesVenta> historicoUnidadesVentaLista = null;		

	        try {
	        	historicoUnidadesVentaLista = (List<HistoricoUnidadesVenta>) this.jdbcTemplate.query(query.toString(),this.rwHistoricoUnidadesVentaMap, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
	      

		    return historicoUnidadesVentaLista;
	    }
	    
		private RowMapper<HistoricoUnidadesVenta> rwHistoricoUnidadesVentaMapLastDays = new RowMapper<HistoricoUnidadesVenta>() {
			public HistoricoUnidadesVenta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
			    return new HistoricoUnidadesVenta(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
			    		null,null,null,null,null,null,null, resultSet.getFloat("TOTAL"), resultSet.getLong("RECORDS"));
			}

		};
	    
	    @Override
	    public List<HistoricoUnidadesVenta> findTotalLastDays(HistoricoUnidadesVenta historicoUnidadesVenta, int lastDays) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer("SELECT SUM(TOTAL_VENTA) TOTAL,MAX(ROWNUM) RECORDS FROM ( " +
	    			" SELECT DECODE(PORCION_CEN,'N',(H.UNID_VENTA_TARIFA + H.UNID_VENTA_ANTICIPADA + H.UNID_VENTA_COMPETENCIA + " +
	    			" H.UNID_VENTA_OFERTA),(H.UNID_VENTA_TARIFA_BD + H.UNID_VENTA_ANTICIPADA + H.UNID_VENTA_COMPETENCIA_BD + " +
	    			" H.UNID_VENTA_OFERTA_BD)) TOTAL_VENTA " +
	    			" FROM HISTORICO_UNIDADES_VENTA H ");
	    
	    	
	    	if (historicoUnidadesVenta  != null){
	        	if(historicoUnidadesVenta.getCodLoc()!=null){
	        		 where.append(" AND H.COD_LOC = ? ");
		        	 params.add(historicoUnidadesVenta.getCodLoc());	        		
	        	}
	        	if(historicoUnidadesVenta.getCodArticulo()!=null){
	        		 where.append(" AND H.COD_ARTICULO = ? ");
		        	 params.add(historicoUnidadesVenta.getCodArticulo());	        		
	        	}
	        	if(historicoUnidadesVenta.getFechaVenta()!=null){
	        		 where.append(" AND H.FECHA_VENTA < TRUNC(?) ");
		        	 params.add(historicoUnidadesVenta.getFechaVenta());	        		
	        	}
	        }
	    	
	    	query.append(where);
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo, fecha_venta desc ");
			query.append(order);
	    	
			query.append(") WHERE ROWNUM <= ? ");
       	 	params.add(lastDays);	

			List<HistoricoUnidadesVenta> historicoUnidadesVentaLastDays = null;		
			
			try {
				historicoUnidadesVentaLastDays = (List<HistoricoUnidadesVenta>) this.jdbcTemplate.query(query.toString(),this.rwHistoricoUnidadesVentaMapLastDays, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
		    return historicoUnidadesVentaLastDays;
	    }
	    
	    @Override
	    public Double findDayMostSales(HistoricoUnidadesVenta historicoUnidadesVenta, int lastDays) throws Exception  {
	    	
	    	StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	referencias.append(historicoUnidadesVenta.getCodArticulo());

	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	query.append("SELECT MAX(UNID_VENTA_TOTAL) DIA_MAYOR_VENTA FROM");  
	    	
	    	query.append(" ( SELECT H.COD_LOC, H.FECHA_VENTA, ");
	    	query.append(" SUM(H.UNID_VENTA_TOTAL*R.CANTIDAD) UNID_VENTA_TOTAL,"); 
	    	query.append(" SUM(H.UNID_VENTA_OFERTA*R.CANTIDAD) UNID_VENTA_OFERTA"); 
	    	
	    	query.append(" FROM HISTORICO_VENTA_PERIODO H, REF_ASOCIADAS R");
	    	query.append(" WHERE H.COD_LOC = ? ");
	    	query.append(" AND R.COD_ARTICULO = ? ");
	    	query.append(" AND H.FECHA_VENTA BETWEEN (SYSDATE - ?) AND SYSDATE");
	    	query.append(" AND R.COD_ARTICULO_HIJO = H.COD_ARTICULO");
	    	query.append(" GROUP BY H.COD_LOC, H.FECHA_VENTA)");
	    	
	    	params.add(historicoUnidadesVenta.getCodLoc());
	    	params.add(historicoUnidadesVenta.getCodArticulo());
	    	params.add(lastDays);
			
	    	Double ventas = null; 
			try {
				ventas = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    return ventas;
	    }
	    
		//Consigue entre dos fechas los d�as laborales, los festivos y el n�mero de ventas totales por d�a
		@Override
		public List<DiaVentasUltimasOfertas> findDateListMediaSales(String codCentro, String codArticulo, String fecIni, String fecFin,String sumVentaAnticipada) throws Exception {

			StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			List<Object> params = new ArrayList<Object>();

			query.append("SELECT TO_CHAR(CALENDARIO.FECHA_FESTIVO, 'dd/mm/yyyy') FECHA_FESTIVO_FORMAT,");
			query.append("       TO_CHAR(CALENDARIO.FECHA_FESTIVO, 'ddmmyyyy') FECHA_FESTIVO,");
			query.append("       MAX(TIPO_DIA) MAX_TIPO_DIA,");
			if(sumVentaAnticipada.equals("0")){
				query.append("	  SUM ( DECODE ( UV.PORCION_CEN, 'N' , NVL( UV.UNID_VENTA_TARIFA,0 ) + NVL( UV.UNID_VENTA_COMPETENCIA,0 ) + NVL( UV.UNID_VENTA_OFERTA, 0 ), NVL( UV.UNID_VENTA_TARIFA_BD,0 ) + NVL( UV.UNID_VENTA_COMPETENCIA_BD,0 ) + NVL( UV.UNID_VENTA_OFERTA_BD, 0 ) )* NVL(UV.CANTIDAD, 0) ) TOTAL_VENTAS,"); 
			}else{
				query.append("    SUM(DECODE(UV.PORCION_CEN,'N', NVL(UV.UNID_VENTA_TOTAL, 0), NVL(UV.UNID_VENTA_TOTAL_BD, 0)* NVL(UV.CANTIDAD, 0))) TOTAL_VENTAS,");
			}
			query.append("    CASE  WHEN SUM(NVL(UV.UNID_VENTA_ANTICIPADA,0)) + SUM(NVL(UV.UNID_VENTA_ANTICIPADA_BD,0)) > '0' THEN 'SI'");
			query.append("    ELSE 'NO' END ANTICIPADA,");
			query.append("    NVL(");
			query.append("     (SELECT 'SI' FROM V_OFERTA_PA_AYUDA OA");
			query.append("      WHERE OA.COD_CENTRO = ?");
			query.append("        AND OA.cod_art    = ?");
			query.append("        AND FECHA_FESTIVO BETWEEN OA.fecha_ini AND OA.fecha_fin");
			query.append("     UNION");
			query.append("      SELECT 'SI' FROM V_OFERTA_PA OP");
			query.append("      WHERE OP.COD_CENTRO = ?");
			query.append("        AND OP.cod_art    = ?");
			query.append("        AND FECHA_FESTIVO BETWEEN OP.fecha_ini AND OP.fecha_fin");
			query.append("     UNION");
			query.append("      SELECT 'SI' FROM V_OFERTA OFE");
			query.append("      WHERE OFE.COD_CENTRO = ?");
			query.append("        AND OFE.cod_art    = ?");
			query.append("        AND FECHA_FESTIVO BETWEEN OFE.fecha_ini AND OFE.fecha_fin), 'NO') OFERTA");
			query.append("  FROM (SELECT * ");
			query.append("          FROM HISTORICO_UNIDADES_VENTA C, REF_ASOCIADAS R");
			query.append("         WHERE C.COD_LOC = ?");
			query.append("           AND R.COD_ARTICULO = ?");
			query.append("           AND R.COD_ARTICULO_HIJO = C.COD_ARTICULO");
			query.append("           AND C.FECHA_VENTA >= TRUNC(TO_DATE(?, 'ddmmyyyy'))");
			query.append("           AND C.FECHA_VENTA <= TRUNC(TO_DATE(?, 'ddmmyyyy'))) UV,");
			query.append("       (SELECT (A.FECHA_FESTIVO) FECHA_FESTIVO,");
			query.append("               DECODE(A.ESTADO, 'B', 'LABORAL', 'FESTIVO') TIPO_DIA");
			query.append("          FROM FESTIVO_CENTRO A");
			query.append("         WHERE A.COD_CENTRO = ?");
			query.append("           AND A.FECHA_FESTIVO >= TRUNC(TO_DATE(?, 'ddmmyyyy'))");
			query.append("           AND A.FECHA_FESTIVO <= TRUNC(TO_DATE(?, 'ddmmyyyy'))");
			query.append("           AND A.FECHA_GEN = (SELECT MAX(B.FECHA_GEN)");
			query.append("                                FROM FESTIVO_CENTRO B");
			query.append("                               WHERE B.COD_CENTRO = A.COD_CENTRO");
			query.append("                                 AND B.FECHA_FESTIVO = A.FECHA_FESTIVO)) CALENDARIO ");
			query.append(" WHERE CALENDARIO.FECHA_FESTIVO = UV.FECHA_VENTA(+)");
			query.append(" GROUP BY FECHA_FESTIVO");
			query.append(" ORDER BY TO_DATE(FECHA_FESTIVO) ASC");

			params.add(new Long(codCentro));
			params.add(new Long(codArticulo));
			params.add(new Long(codCentro));
			params.add(new Long(codArticulo));
			params.add(new Long(codCentro));
			params.add(new Long(codArticulo));
			params.add(new Long(codCentro));
			params.add(new Long(codArticulo));
			params.add(fecIni);
			params.add(fecFin);
			params.add(new Long(codCentro));
			params.add(fecIni);
			params.add(fecFin);
			List<DiaVentasUltimasOfertas> listaDiasUltimasOfertas = (List<DiaVentasUltimasOfertas>) this.jdbcTemplate.query(query.toString(),this.rwDiaVentasMap,params.toArray()); 
			return listaDiasUltimasOfertas;
		}
	    
	    public HistoricoUnidadesVenta findVentaUltimaOferta(HistoricoUnidadesVenta historicoUnidadesVenta,String sumVentaAnticipada) throws Exception{
			StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			List<Object> params = new ArrayList<Object>();

			query.append("SELECT FECHA_VENTA,SUM(DECODE(PORCION_CEN,'N',UNID_VENTA_TARIFA,UNID_VENTA_TARIFA_BD*NVL(CANTIDAD, 0))) TARIFA,");  
			query.append("SUM(DECODE(PORCION_CEN,'N',UNID_VENTA_COMPETENCIA,UNID_VENTA_COMPETENCIA_BD*NVL(CANTIDAD, 0))) COMPETENCIA,");  
			query.append("SUM(DECODE(PORCION_CEN,'N',UNID_VENTA_OFERTA,UNID_VENTA_OFERTA_BD*NVL(CANTIDAD, 0))) OFERTA,");  
			query.append("SUM(DECODE(PORCION_CEN,'N',UNID_VENTA_ANTICIPADA,UNID_VENTA_ANTICIPADA_BD*NVL(CANTIDAD, 0))) ANTICIPADA,");  

			//Si no se quiere sumar la venta anticipada, p53,p56
			if(sumVentaAnticipada.equals("0")){
				query.append("SUM(DECODE(PORCION_CEN,'N',(UNID_VENTA_TARIFA + UNID_VENTA_COMPETENCIA + UNID_VENTA_OFERTA), (UNID_VENTA_TARIFA_BD + UNID_VENTA_COMPETENCIA_BD + UNID_VENTA_OFERTA_BD)* NVL(CANTIDAD, 0))) TOTAL_VENTAS");  
			}//Si se quiere sumar la venta anticipada p15
			else{
				query.append("SUM(DECODE(PORCION_CEN,'N',UNID_VENTA_TOTAL,UNID_VENTA_TOTAL_BD*NVL(CANTIDAD, 0))) TOTAL_VENTAS");
			}
			query.append(" FROM (SELECT * ");  
			query.append("  FROM HISTORICO_UNIDADES_VENTA a, REF_ASOCIADAS b");  
			query.append("  WHERE a.COD_LOC    = ?");  
			query.append("	AND   b.COD_ARTICULO = ?");  
			query.append("  AND   b.COD_ARTICULO_HIJO =a.COD_ARTICULO");  
			query.append("  AND   a.FECHA_VENTA = TRUNC(?))");  
			query.append("GROUP BY FECHA_VENTA");  

			params.add(historicoUnidadesVenta.getCodLoc());
			params.add(historicoUnidadesVenta.getCodArticulo());
			params.add(historicoUnidadesVenta.getFechaVenta());

			HistoricoUnidadesVenta historicoUnidadesVentaRet = this.jdbcTemplate.queryForObject(query.toString(), this.rwVentaUltimaOfertaMap, params.toArray()); 
			return historicoUnidadesVentaRet;
		}
	    
		@Override
		public List<Map<String,Object>> findDateListMediaSalesViejo(String codCentro, String codArticulo, String fecIni, String fecFin) throws Exception {

	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	query.append("SELECT TO_CHAR(C.FECHA_VENTA,'ddmmyyyy') FECHA_VENTA, ");  
	    	query.append("     SUM ( ");  
	    	query.append("         DECODE ( "); 
	    	query.append("             C.PORCION_CEN, "); 
	    	query.append("             'N' , "); 
	    	query.append("             NVL( C.UNID_VENTA_TARIFA,0 ) + NVL( C.UNID_VENTA_COMPETENCIA,0 ) + NVL( C.UNID_VENTA_OFERTA, 0 ), "); 
	    	query.append("             NVL( C.UNID_VENTA_TARIFA_BD,0 ) + NVL( C.UNID_VENTA_COMPETENCIA_BD,0 ) + NVL( C.UNID_VENTA_OFERTA_BD, 0 ) "); 
	    	query.append("         ) "); 
	    	query.append("     ) TOTAL_VENTAS "); 
	    	query.append(" FROM HISTORICO_UNIDADES_VENTA C");
	    	query.append(" WHERE C.COD_LOC = ? ");
	    	query.append(" AND C.COD_ARTICULO = ? ");
	    	query.append(" AND FECHA_VENTA IN ( ");
	    	query.append("     SELECT (A.FECHA_FESTIVO) ");
	    	query.append("     FROM FESTIVO_CENTRO A ");
	    	query.append("     WHERE A.COD_CENTRO = C.COD_LOC ");
   	    	query.append("     AND A.FECHA_FESTIVO >= TRUNC (TO_DATE (?, 'ddmmyyyy')) ");
   	    	query.append("     AND A.FECHA_FESTIVO <= TRUNC (TO_DATE (?, 'ddmmyyyy')) "); 
   	    	query.append("     AND A.ESTADO = 'B'  ");
   	    	query.append("     AND A.FECHA_GEN =  ( ");
   	    	query.append("         SELECT MAX(B.FECHA_GEN) ");
   	    	query.append("         FROM festivo_centro  B  ");
   	    	query.append("         WHERE B.COD_CENTRO = A.COD_CENTRO  AND B.fecha_festivo = A.fecha_festivo ");
   	    	//Comentado por petición 59566 query.append("         and b.FLG_ENVIADO_PBL = 'S' ");
   	    	query.append("         ) ");
   	    	query.append("     ) ");
   	    	query.append(" GROUP BY FECHA_VENTA ");
   	    	query.append(" ORDER BY TO_DATE(FECHA_VENTA) ASC ");
	    	
	    	params.add(codCentro);
	    	params.add(codArticulo);
	    	params.add(fecIni);
	    	params.add(fecFin);
	    	
			List<Map<String,Object>> listaFechasVentasMedias = null;
			
			try {
				listaFechasVentasMedias = this.jdbcTemplate.queryForList(query.toString(), params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    return listaFechasVentasMedias;
		}
}
