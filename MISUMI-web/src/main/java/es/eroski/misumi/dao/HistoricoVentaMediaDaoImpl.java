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

import es.eroski.misumi.dao.iface.HistoricoVentaMediaDao;
import es.eroski.misumi.model.HistoricoVentaMedia;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class HistoricoVentaMediaDaoImpl implements HistoricoVentaMediaDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	 private RowMapper<HistoricoVentaMedia> rwHistoricoVentaMediaMap = new RowMapper<HistoricoVentaMedia>() {
			public HistoricoVentaMedia mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new HistoricoVentaMedia(resultSet.getLong("COD_LOC"),resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getDate("FECHA_VENTA_MEDIA"), resultSet.getFloat("UNID_TOT_VENTA_TARIFA"),
			    			resultSet.getFloat("UNID_TOT_VENTA_COMPETENCIA"), resultSet.getFloat("UNID_TOT_VENTA_OFERTA"),
			    			resultSet.getFloat("UNID_TOT_VENTA_ANTICIPADA"), resultSet.getLong("DIAS"),
			    			resultSet.getString("TIPO_VENTA")
				    );
			}

		};

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    /*@Override
	    public List<HistoricoVentaMedia> findAll(HistoricoVentaMedia historicoVentaMedia) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_LOC, COD_ARTICULO, FECHA_VENTA_MEDIA, UNID_TOT_VENTA_TARIFA, UNID_TOT_VENTA_COMPETENCIA, UNID_TOT_VENTA_OFERTA, UNID_TOT_VENTA_ANTICIPADA, DIAS, TIPO_VENTA " 
	    										+ " FROM HISTORICO_VENTA_MEDIA ");
	    
	    	
	        if (historicoVentaMedia  != null){
	        	if(historicoVentaMedia.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(historicoVentaMedia.getCodLoc());	        		
	        	}
	        	if(historicoVentaMedia.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(historicoVentaMedia.getCodArticulo());	        		
	        	}
	        	if(historicoVentaMedia.getFechaVentaMedia()!=null){
	        		 where.append(" AND TRUNC(FECHA_VENTA_MEDIA) >= TRUNC(?) ");
		        	 params.add(historicoVentaMedia.getFechaVentaMedia());	        		
	        	}
	        	if(historicoVentaMedia.getUnidTotVentaTarifa()!=null){
	        		 where.append(" AND UNID_TOT_VENTA_TARIFA = ? ");
		        	 params.add(historicoVentaMedia.getUnidTotVentaTarifa());	        		
	        	}
	        	if(historicoVentaMedia.getUnidTotVentaCompetencia()!=null){
	        		 where.append(" AND UNID_TOT_VENTA_COMPETENCIA = ? ");
		        	 params.add(historicoVentaMedia.getUnidTotVentaCompetencia());	        		
	        	}
	        	if(historicoVentaMedia.getUnidTotVentaOferta()!=null){
	        		 where.append(" AND UNID_TOT_VENTA_OFERTA = ? ");
		        	 params.add(historicoVentaMedia.getUnidTotVentaOferta());	        		
	        	}
	        	if(historicoVentaMedia.getUnidTotVentaAnticipada()!=null){
	        		 where.append(" AND UNID_TOT_VENTA_ANTICIPADA = ? ");
		        	 params.add(historicoVentaMedia.getUnidTotVentaAnticipada());	        		
	        	}
	        	if(historicoVentaMedia.getDias()!=null){
	        		 where.append(" AND DIAS = ? ");
		        	 params.add(historicoVentaMedia.getDias());	        		
	        	}
	        	if(historicoVentaMedia.getTipoVenta()!=null){
	        		 where.append(" AND UPPER(TIPO_VENTA) = upper(?) ");
		        	 params.add(historicoVentaMedia.getTipoVenta());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_loc, cod_articulo ");
			query.append(order);

			List<HistoricoVentaMedia> historicoVentaMediaLista = null;		
			historicoVentaMediaLista = (List<HistoricoVentaMedia>) this.jdbcTemplate.query(query.toString(),this.rwHistoricoVentaMediaMap, params.toArray()); 

		    return historicoVentaMediaLista;
	    }*/

	    @Override
	    public List<HistoricoVentaMedia> findAllAcumuladoRef(HistoricoVentaMedia historicoVentaMedia) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append(" WHERE R.COD_ARTICULO_HIJO = H.COD_ARTICULO ");

	    	StringBuffer query = new StringBuffer(" SELECT H.COD_LOC COD_LOC, MAX(H.COD_ARTICULO) COD_ARTICULO, MAX(H.FECHA_VENTA_MEDIA) FECHA_VENTA_MEDIA,SUM(H.UNID_TOT_VENTA_TARIFA*R.CANTIDAD) UNID_TOT_VENTA_TARIFA, SUM(H.UNID_TOT_VENTA_COMPETENCIA*R.CANTIDAD) UNID_TOT_VENTA_COMPETENCIA, SUM(H.UNID_TOT_VENTA_OFERTA*R.CANTIDAD) UNID_TOT_VENTA_OFERTA, SUM(H.UNID_TOT_VENTA_ANTICIPADA*R.CANTIDAD) UNID_TOT_VENTA_ANTICIPADA, MAX(H.DIAS) DIAS, MAX(H.TIPO_VENTA) TIPO_VENTA "
	    										+ " FROM HISTORICO_VENTA_MEDIA H, REF_ASOCIADAS R ");

	        if (historicoVentaMedia  != null){
	        	if(historicoVentaMedia.getCodLoc()!=null){
	        		 where.append(" AND H.COD_LOC = ? ");
		        	 params.add(historicoVentaMedia.getCodLoc());	        		
	        	}
	        	if(historicoVentaMedia.getCodArticulo()!=null){
	        		 where.append(" AND R.COD_ARTICULO = ? ");
		        	 params.add(historicoVentaMedia.getCodArticulo());	        		
	        	}
	        }
	        where.append(" AND H.FECHA_VENTA_MEDIA >= TRUNC(SYSDATE-31) ");
	        query.append(where);

	        StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	        groupBy.append(" group by H.COD_LOC ");
			query.append(groupBy);

			List<HistoricoVentaMedia> historicoVentaMediaLista = null;		
			
			try {
				historicoVentaMediaLista = (List<HistoricoVentaMedia>) this.jdbcTemplate.query(query.toString(),this.rwHistoricoVentaMediaMap, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return historicoVentaMediaLista;
	    }
}
