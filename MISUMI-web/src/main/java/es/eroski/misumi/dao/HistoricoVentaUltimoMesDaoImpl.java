package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.HistoricoVentaUltimoMesDao;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.model.HistoricoVentaUltimoMes;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class HistoricoVentaUltimoMesDaoImpl implements HistoricoVentaUltimoMesDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	 private RowMapper<HistoricoVentaUltimoMes> rwHistoricoVentaUltimoMesMap = new RowMapper<HistoricoVentaUltimoMes>() {
			public HistoricoVentaUltimoMes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new HistoricoVentaUltimoMes(resultSet.getLong("COD_LOC"),resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getDate("FECHA_VENTA"), resultSet.getFloat("UNID_VENTA_TARIFA"),
			    			resultSet.getFloat("UNID_VENTA_COMPETENCIA"), resultSet.getFloat("UNID_VENTA_OFERTA"),
			    			resultSet.getFloat("UNID_VENTA_ANTICIPADA"), resultSet.getString("TIPO_VENTA"),
			    			resultSet.getString("FECHA"), resultSet.getString("ESFESTIVO")
				    );
			}

		};

		private RowMapper<HistoricoVentaUltimoMes> rwVentasUltimoMesMap = new RowMapper<HistoricoVentaUltimoMes>() {
			public HistoricoVentaUltimoMes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				//C�lculo del campo fecha venta
				Date fechaVenta = resultSet.getDate("FECHA_VENTA");
				String fechaVentaDDMMYYYY = "";
				if (fechaVenta != null){
					final SimpleDateFormat sdfFormateador = new SimpleDateFormat();
			        sdfFormateador.applyPattern("ddMMyyyy");

			        fechaVentaDDMMYYYY = sdfFormateador.format(fechaVenta);
				}
			    return new HistoricoVentaUltimoMes(
			    		resultSet.getLong("COD_LOC"), resultSet.getLong("COD_ARTICULO"),
			    		resultSet.getFloat("UNIDADES"), fechaVenta,
			    		resultSet.getString("ANTICIPADA"), fechaVentaDDMMYYYY,
			    		resultSet.getString("FECHA"), resultSet.getString("ESFESTIVO"), resultSet.getString("ESOFERTA")
				    );
			}
		};	
		
		private RowMapper<HistoricoVentaUltimoMes> rwTotalVentasLastDays = new RowMapper<HistoricoVentaUltimoMes>() {
			public HistoricoVentaUltimoMes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
			    return new HistoricoVentaUltimoMes(resultSet.getFloat("UNID_VENTA_TARIFA"),
		    			resultSet.getFloat("UNID_VENTA_COMPETENCIA"), resultSet.getFloat("UNID_VENTA_OFERTA"),
		    			resultSet.getFloat("UNID_VENTA_ANTICIPADA")
				    );
			}
		};	
		
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<HistoricoVentaUltimoMes> findAll(HistoricoVentaUltimoMes historicoVentaUltimoMes) throws Exception  {
	    	StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	referencias.append(historicoVentaUltimoMes.getCodArticulo());
	    	if (null != historicoVentaUltimoMes.getReferencias()){
	    		for(Long referencia : historicoVentaUltimoMes.getReferencias()){
	    			referencias.append(", ").append(referencia);
	    		}
	    	}
	    	List<Object> params = new ArrayList<Object>();

	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	query.append("SELECT null FECHA, null ESFESTIVO, COD_LOC, FECHA_VENTA, ? COD_ARTICULO, null TIPO_VENTA, ");
	    	query.append("SUM(UNID_VENTA_TARIFA) UNID_VENTA_TARIFA, SUM(UNID_VENTA_COMPETENCIA) UNID_VENTA_COMPETENCIA, ");
	    	query.append("SUM(UNID_VENTA_OFERTA) UNID_VENTA_OFERTA, SUM(UNID_VENTA_ANTICIPADA) UNID_VENTA_ANTICIPADA ");
	    	query.append("FROM HISTORICO_VENTA_ULTIMO_MES ");
	    	query.append("WHERE COD_LOC = ? ");
	    	query.append("AND COD_ARTICULO IN (").append(referencias.toString()).append(") ");
	    	query.append("AND FECHA_VENTA = TRUNC(?) ");
	    	query.append("GROUP BY COD_LOC, FECHA_VENTA");
	    	
	       params.add(historicoVentaUltimoMes.getCodArticulo());	 
	       params.add(historicoVentaUltimoMes.getCodLoc());
	       params.add(historicoVentaUltimoMes.getFechaVenta());

			List<HistoricoVentaUltimoMes> historicoVentaUltimoMesLista = null;		

			try {
				historicoVentaUltimoMesLista = (List<HistoricoVentaUltimoMes>) this.jdbcTemplate.query(query.toString(),this.rwHistoricoVentaUltimoMesMap, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		    return historicoVentaUltimoMesLista;
	    }

	    @Override
	    public List<HistoricoVentaUltimoMes> findAllLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays) throws Exception  {
	    	StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	referencias.append(historicoVentaUltimoMes.getCodArticulo());
	    	if (null != historicoVentaUltimoMes.getReferencias()){
	    		for(Long referencia : historicoVentaUltimoMes.getReferencias()){
	    			referencias.append(", ").append(referencia);
	    		}
	    	}
	    	List<Object> params = new ArrayList<Object>();

	    	StringBuffer query = new StringBuffer(" SELECT  DECODE(TO_CHAR((SYSDATE - dias.l), 'DD'), '01', TO_CHAR((SYSDATE - dias.l), 'DD MON'), TO_CHAR((SYSDATE - dias.l), 'DD')) FECHA, " +
	    										  " 	   DECODE(FECHA_FESTIVO, NULL, 'F', 'T') ESFESTIVO, " +
	    										  "		   cod_loc, ? as cod_articulo, " +
												  "       CASE WHEN (dias.l < 1) THEN -99 " +
												  "            WHEN (dias.l > ?) THEN -99 " +
												  "            ELSE NVL (historico.unidades, 0) END AS unidades, " +
												  "       fecha_venta, DECODE (NVL(unid_venta_anticipada, 0), " +
												  "                                      0, 'N0', " +
												  "                                       'SI' " +
												  "                                      ) anticipada, NVL(ESOFERTA, 'F') ESOFERTA " +
												  "  FROM (SELECT h.cod_loc, (TO_CHAR (SYSDATE, 'J') - TO_CHAR (h.fecha_venta, 'J')) diaant, " +
												  "				 h.fecha_venta, (sum(h.unid_venta_tarifa) + sum(h.unid_venta_competencia) + sum(h.unid_venta_oferta) + sum(h.unid_venta_anticipada)) unidades, " +
												  "         	sum(h.unid_venta_anticipada) unid_venta_anticipada   " +
												  "          FROM HISTORICO_VENTA_ULTIMO_MES h " +
												  "         WHERE h.cod_loc = ? " +
												  "           AND h.cod_articulo in ( " + referencias.toString() + " ) " +
												  "           AND h.fecha_venta BETWEEN ((SYSDATE -1) - ? ) AND (SYSDATE - 1) " + 
												  "          group by h.cod_loc, (TO_CHAR (SYSDATE, 'J') - TO_CHAR (h.fecha_venta, 'J')) , h.fecha_venta) historico, " +
												  "       (SELECT ? + LEVEL l " +
												  "              FROM DUAL " +
												  "        CONNECT BY LEVEL < TO_CHAR (SYSDATE - ?, 'D') " +
												  "        UNION " +
												  "        SELECT     LEVEL l " +
												  "              FROM DUAL " +
												  "        CONNECT BY LEVEL <= ? " +
												  "        UNION " +
												  "        SELECT     -1 * ((7 - TO_CHAR (SYSDATE - 1, 'D')) - LEVEL) l " +
												  "              FROM DUAL " +
												  "        CONNECT BY LEVEL <= (7 - TO_CHAR (SYSDATE - 1, 'D'))) dias," +
												  "       ( SELECT DIAS.L, COD_CENTRO, FECHA_FESTIVO " +
	    										  "        FROM ( SELECT COD_CENTRO, FECHA_FESTIVO, (TO_CHAR(SYSDATE, 'J') - TO_CHAR(FECHA_FESTIVO, 'J')) DIAANT " +
	    										  "               FROM  festivo_centro A " +
	    										  "               where  A.FECHA_GEN = (SELECT MAX(B.FECHA_GEN) FROM festivo_centro  B" +
	    										  "                                     WHERE B.COD_CENTRO = A.COD_CENTRO" +
	    										  "                                      AND B.fecha_festivo = A.fecha_festivo" +
	    										  //Comentado por petición 59566 "                                      and b.FLG_ENVIADO_PBL = 'S' " +
	    										  "                                      ) " +
	    										  "                      and A.estado <> 'B' and A.FECHA_FESTIVO  BETWEEN (SYSDATE - (:LASTDAYS + 7)) AND (SYSDATE + (7 - TO_CHAR(SYSDATE, 'D'))) and A.cod_centro = :CENTRO) FC," +
	    										  "              ( SELECT :LASTDAYS + LEVEL L FROM DUAL CONNECT BY LEVEL < TO_CHAR(SYSDATE - :LASTDAYS, 'D')" +
	    										  "                UNION " +
	    										  "                SELECT LEVEL L FROM DUAL CONNECT BY LEVEL <= :LASTDAYS" +
	    										  "                UNION " +
	    									      "					 SELECT -1 * ((7 - TO_CHAR (SYSDATE -1, 'D')) - LEVEL) L " + 
	    									      "			        FROM DUAL  " +
	    									      "				  CONNECT BY LEVEL <= (7 - TO_CHAR (SYSDATE -1, 'D'))) DIAS " +
	    										  "        WHERE FC.DIAANT (+) = DIAS.L" +
	    										  "        ORDER BY DIAS.L) FESTIVOS ,   " +
	    										  "       (SELECT DIAS.L, DECODE(COUNT(OFERTAS.NUM_OFERTA),0,'F','T') ESOFERTA " +
	    										  "        FROM (SELECT TRUNC(SYSDATE) - LEVEL FECHA, (TO_CHAR(SYSDATE, 'J') - TO_CHAR(TRUNC(SYSDATE) - LEVEL, 'J')) L " +
	    										  "				FROM DUAL CONNECT BY LEVEL <= :LASTDAYS " +
	    										  "				UNION " +
	    										  "				SELECT TRUNC(SYSDATE) + LEVEL FECHA, :LASTDAYS + LEVEL L FROM DUAL CONNECT BY LEVEL < TO_CHAR(SYSDATE - :LASTDAYS, 'D') " +
	    										  "     		UNION " +		
	    										  "					 SELECT  TRUNC (SYSDATE ) - (-1 * ((7 - TO_CHAR (SYSDATE - 1 , 'D')) - LEVEL)) FECHA,     -1 * ((7 - TO_CHAR (SYSDATE -1, 'D')) - LEVEL) L " + 
	    									      "			        FROM DUAL  " +
	    									      "				  CONNECT BY LEVEL <= (7 - TO_CHAR (SYSDATE -1, 'D')) " +
	    										  "				ORDER BY L ) DIAS, " +
	    										  "       (SELECT NUM_OFERTA, FECHA_INI, FECHA_FIN FROM V_OFERTA_PA_AYUDA " +
	    										  "      	WHERE COD_CENTRO = :COD_CENTRO " +
	    										  "			AND COD_ART = :COD_ART " +
	    										  "			UNION " +
	    										  "			SELECT NUM_OFERTA, FECHA_INI, FECHA_FIN FROM V_OFERTA_PA " + 
	    										  "			WHERE COD_CENTRO = :COD_CENTRO  " +
	    										  "			AND COD_ART = :COD_ART) OFERTAS " + 
	    										  "         WHERE DIAS.FECHA >= OFERTAS.FECHA_INI (+) " + 
	    										  "         AND DIAS.FECHA <= OFERTAS.FECHA_FIN (+) " +
	    										  "      GROUP BY DIAS.L " +  
	    										  "      ORDER BY DIAS.L) OFERTAS " +
												  " WHERE historico.diaant(+) = dias.l and dias.l = FESTIVOS.L and ofertas.l (+) = dias.l" +
												  " ORDER BY dias.l ");
	    	
	        if (historicoVentaUltimoMes  != null){
	        	params.add(historicoVentaUltimoMes.getCodArticulo());
	        	params.add(lastDays);
	        	params.add(historicoVentaUltimoMes.getCodLoc());
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	
	        	params.add(lastDays);
	        	params.add(historicoVentaUltimoMes.getCodLoc());
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(lastDays);
	        	params.add(historicoVentaUltimoMes.getCodLoc());
	        	params.add(historicoVentaUltimoMes.getCodArticulo());
	        	params.add(historicoVentaUltimoMes.getCodLoc());
	        	params.add(historicoVentaUltimoMes.getCodArticulo());
	        }
	        
			List<HistoricoVentaUltimoMes> historicoVentaUltimoMesLista = null;		
			
			try {
				historicoVentaUltimoMesLista = (List<HistoricoVentaUltimoMes>) this.jdbcTemplate.query(query.toString(),this.rwVentasUltimoMesMap, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return historicoVentaUltimoMesLista;
	    }
	    
	    @Override
	    public List<HistoricoVentaUltimoMes> findTotalLastDays(HistoricoVentaUltimoMes historicoVentaUltimoMes, int lastDays) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer("SELECT SUM(unid_venta_tarifa) AS UNID_VENTA_TARIFA, SUM(unid_venta_competencia) AS UNID_VENTA_COMPETENCIA, " +
	    			"SUM(unid_venta_oferta) AS UNID_VENTA_OFERTA, SUM(unid_venta_anticipada) AS UNID_VENTA_ANTICIPADA " +
	    			"FROM historico_venta_ultimo_mes ");
	    
	    	
	        if (historicoVentaUltimoMes  != null){
	        	if(historicoVentaUltimoMes.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(historicoVentaUltimoMes.getCodLoc());	        		
	        	}
	        	if(historicoVentaUltimoMes.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(historicoVentaUltimoMes.getCodArticulo());	        		
	        	}
	        	if(historicoVentaUltimoMes.getFechaVenta()!=null){
	        		 where.append(" AND TRUNC(FECHA_VENTA) = TRUNC(?) ");
		        	 params.add(historicoVentaUltimoMes.getFechaVenta());	        		
	        	}
	        	if(historicoVentaUltimoMes.getUnidVentaTarifa()!=null){
	        		 where.append(" AND UNID_VENTA_TARIFA = ? ");
		        	 params.add(historicoVentaUltimoMes.getUnidVentaTarifa());	        		
	        	}
	        	if(historicoVentaUltimoMes.getUnidVentaCompetencia()!=null){
	        		 where.append(" AND UNID_VENTA_COMPETENCIA = ? ");
		        	 params.add(historicoVentaUltimoMes.getUnidVentaCompetencia());	        		
	        	}
	        	if(historicoVentaUltimoMes.getUnidVentaOferta()!=null){
	        		 where.append(" AND UNID_VENTA_OFERTA = ? ");
		        	 params.add(historicoVentaUltimoMes.getUnidVentaOferta());	        		
	        	}
	        	if(historicoVentaUltimoMes.getUnidVentaAnticipada()!=null){
	        		 where.append(" AND UNID_VENTA_ANTICIPADA = ? ");
		        	 params.add(historicoVentaUltimoMes.getUnidVentaAnticipada());	        		
	        	}
	        	if(historicoVentaUltimoMes.getTipoVenta()!=null){
	        		 where.append(" AND UPPER(TIPO_VENTA) = upper(?) ");
		        	 params.add(historicoVentaUltimoMes.getTipoVenta());	        		
	        	}
	        }
	        where.append(" AND ROWNUM <= ? ");
       	 	params.add(lastDays);	
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo ");
			query.append(order);

			List<HistoricoVentaUltimoMes> historicoVentaUltimoMesLista = null;		
			
			try {
				historicoVentaUltimoMesLista = (List<HistoricoVentaUltimoMes>) this.jdbcTemplate.query(query.toString(),this.rwTotalVentasLastDays, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
		    return historicoVentaUltimoMesLista;
	    }
	    
}
