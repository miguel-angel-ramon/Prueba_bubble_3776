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

import es.eroski.misumi.dao.iface.VOfertaDao;
import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.VOferta;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VOfertaDaoImpl implements VOfertaDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VOfertaDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(VOfertaDaoImpl.class);

	 private RowMapper<VOferta> rwOfertaMap = new RowMapper<VOferta>() {
			public VOferta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VOferta(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
			    			resultSet.getLong("TIPO_OFERTA"), resultSet.getDate("FECHA_INI"),
			    			resultSet.getDate("FECHA_FIN"), 
			    			resultSet.getString("DOFTIP")
				    );
			}

		    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VOferta> findAll(VOferta vOferta) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, ANO_OFERTA, NUM_OFERTA, TIPO_OFERTA, FECHA_INI, FECHA_FIN, DOFTIP " 
	    										+ " FROM V_OFERTA ");
	    
	        if (vOferta  != null){
	        	if(vOferta.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vOferta.getCodCentro());	        		
	        	}
	        	if(vOferta.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vOferta.getCodArt());	        		
	        	}
	        	if(vOferta.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vOferta.getAnoOferta());	        		
	        	}
	        	if(vOferta.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vOferta.getNumOferta());	        		
	        	}
	        	if(vOferta.getTipoOferta()!=null){
	        		 where.append(" AND TIPO_OFERTA = ? ");
		        	 params.add(vOferta.getTipoOferta());	        		
	        	}
	        	if(vOferta.getFechaIni()!=null){
	        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
		        	 params.add(vOferta.getFechaIni());	        		
	        	}
	        	if(vOferta.getFechaFin()!=null){
	        		 where.append(" AND TRUNC(FECHA_FIN) = TRUNC(?) ");
		        	 params.add(vOferta.getFechaFin());	        		
	        	}
	        	if(vOferta.getdTipoOferta()!=null){
	        		 where.append(" AND UPPER(D_TIPO_OFERTA) = upper(?) ");
		        	 params.add(vOferta.getdTipoOferta());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<VOferta> vOfertaLista = null;		
			
			try {
				vOfertaLista = (List<VOferta>) this.jdbcTemplate.query(query.toString(),this.rwOfertaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}

		    return vOfertaLista;
	    }
	   
}
