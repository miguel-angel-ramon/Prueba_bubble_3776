package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.MapaAprovFestivoDao;
import es.eroski.misumi.model.MapaAprovFestivo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class MapaAprovFestivoDaoImpl implements MapaAprovFestivoDao {
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VMapaAprovFestivoDaoImpl.class);

	 
	 private RowMapper<MapaAprovFestivo> rwMapaAprovFestivoPedidosMap = new RowMapper<MapaAprovFestivo>() {
			public MapaAprovFestivo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				MapaAprovFestivo mapaAprovFestivo = new MapaAprovFestivo();
				Date date = resultSet.getDate("FEC_CAMBIO");
				Calendar cal = new GregorianCalendar();
				cal.setTime(date);
				mapaAprovFestivo.setFechaCambio(cal.getTime());
				mapaAprovFestivo.setPlazo(resultSet.getInt("PLAZO"));
				return mapaAprovFestivo;
			}

	 };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	   
	    
	  /* (non-Javadoc)
	 * @see es.eroski.misumi.dao.MapaAprovFestivoDao#getFechasPedido(es.eroski.misumi.model.MapaAprovFestivo)
	 */
	@Override
	public List<MapaAprovFestivo> getFechasPedido(MapaAprovFestivo mapaAprovFestivo) throws Exception {
			StringBuffer query = new StringBuffer(" select fec_cambio, cod_art_mapa, plazo ");
			List<Object> params = new ArrayList<Object>();
		  query.append(" from ( ");
		query.append(" SELECT cod_centro, cod_art_mapa, cod_art, fec_cambio, plazo, estado ");
			query.append(" FROM (SELECT ");
			query.append(" m.cod_centro, m.cod_art cod_art_mapa, m.cod_art, m.fec_cambio, m.plazo, m.estado ");
			query.append(" FROM mapa_aprov_festivo m ");
			query.append(" where m.fecha_gen = (select max(m3.fecha_gen) from mapa_aprov_festivo m3 ");
			query.append(" where m.cod_centro = m3.cod_centro and m.cod_art = m3.cod_art and m.fec_cambio = m3.fec_cambio) ");
			query.append(" UNION ");
			query.append(" SELECT m2.cod_centro, m2.cod_art, d.cod_art, m2.fec_cambio, m2.plazo, m2.estado ");
			query.append(" FROM mapa_aprov_festivo m2, v_datos_diario_art d ");
			query.append(" WHERE TO_CHAR (d.estlog_n1) = m2.cod_n1 ");
			query.append(" AND TO_CHAR (d.estlog_n2) = m2.cod_n2 ");
			query.append(" AND TO_CHAR (d.estlog_n3) = m2.cod_n3 ");
			query.append(" AND m2.cod_art is null ");
			query.append(" and m2.fecha_gen = (select max(m4.fecha_gen) from mapa_aprov_festivo m4 ");
			query.append(" where m2.cod_centro = m4.cod_centro and m2.fec_cambio = m4.fec_cambio ");
			query.append(" and m2.cod_n1 = m4.cod_n1 and m2.cod_n2 = m4.cod_n2 and m2.cod_n3 = m4.cod_n3 ");
			query.append(" )) mapa ");
			query.append(" ) ");
			
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append("WHERE 1=1 ");
	        if (mapaAprovFestivo  != null){
	        	if(mapaAprovFestivo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(mapaAprovFestivo.getCodCentro());	        		
	        	}
	        	if(mapaAprovFestivo.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(mapaAprovFestivo.getCodArt());	        		
	        	}
	        	
	        	if(mapaAprovFestivo.getEstado()!=null){
	        		 where.append(" AND ESTADO = upper(?) ");
		        	 params.add(mapaAprovFestivo.getEstado());	        		
	        	}
	        	where.append(" AND FEC_CAMBIO BETWEEN (SYSDATE + 1) AND (SYSDATE + 8)");
	        	
	        }
	        
	        query.append(where);
	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, fec_cambio, cod_art_mapa ");
			query.append(order);


			List<MapaAprovFestivo> listFechasAux =  null;
			try {
				listFechasAux =  (List<MapaAprovFestivo>) this.jdbcTemplate.query(query.toString(),this.rwMapaAprovFestivoPedidosMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			List<MapaAprovFestivo> listFechas = new ArrayList<MapaAprovFestivo>();
			for(MapaAprovFestivo mapaAprov : listFechasAux){
				if (!listFechas.contains(mapaAprov)){
					listFechas.add(mapaAprov);
				}
			}
			
			return listFechas;
	  }
}
