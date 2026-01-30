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

import es.eroski.misumi.dao.iface.OfertaVigenteDao;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.model.OfertaVigente;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class OfertaVigenteDaoImpl implements OfertaVigenteDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(OfertaVigenteDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(OfertaVigenteDaoImpl.class);

	 private RowMapper<OfertaVigente> rwOfertaMap = new RowMapper<OfertaVigente>() {
		 public OfertaVigente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			 return new OfertaVigente(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
			    			resultSet.getLong("TIPO_OFERTA"), resultSet.getDate("FECHA_INI"),
			    			resultSet.getDate("FECHA_FIN"), 
			    			resultSet.getString("DOFTIP")
				    );
		}

	};

	// MISUMI-391 MISUMIS-JAVA VEGALSA-FAMILIA sacar las ofertas de oferta vigente
	private RowMapper<OfertaVigente> rwOfertaAuxMap = new RowMapper<OfertaVigente>() {
		public OfertaVigente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new OfertaVigente(resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA") );
		}
	};		    
		    

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<OfertaVigente> findAll(OfertaVigente ofertaVigente) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT O.COD_CENTRO, O.COD_ART, O.ANO_OFERTA, O.NUM_OFERTA, O.TIPO_OFERTA, O.FECHA_INI, O.FECHA_FIN, SOT.DOFTIP " 
	    										+ " FROM OFERTA_VIGENTE O, T_SUP_OFTIP SOT ");
	    
	        if (ofertaVigente  != null){
	        	if(ofertaVigente.getCodCentro()!=null){
	        		 where.append(" AND O.COD_CENTRO = ? ");
		        	 params.add(ofertaVigente.getCodCentro());	        		
	        	}
	        	if(ofertaVigente.getCodArt()!=null){
	        		 where.append(" AND O.COD_ART = ? ");
		        	 params.add(ofertaVigente.getCodArt());	        		
	        	}
	        	if(ofertaVigente.getAnoOferta()!=null){
	        		 where.append(" AND O.ANO_OFERTA = ? ");
		        	 params.add(ofertaVigente.getAnoOferta());	        		
	        	}
	        	if(ofertaVigente.getNumOferta()!=null){
	        		 where.append(" AND O.NUM_OFERTA = ? ");
		        	 params.add(ofertaVigente.getNumOferta());	        		
	        	}
	        	if(ofertaVigente.getTipoOferta()!=null){
	        		 where.append(" AND O.TIPO_OFERTA = ? ");
		        	 params.add(ofertaVigente.getTipoOferta());	        		
	        	}
	        	if(ofertaVigente.getFechaIni()!=null){
	        		 where.append(" AND TRUNC(O.FECHA_INI) = TRUNC(?) ");
		        	 params.add(ofertaVigente.getFechaIni());	        		
	        	}
	        	if(ofertaVigente.getFechaFin()!=null){
	        		 where.append(" AND TRUNC(O.FECHA_FIN) = TRUNC(?) ");
		        	 params.add(ofertaVigente.getFechaFin());	        		
	        	}
	        }
	        
	        //Condici�n de join
	        where.append(" AND SOT.COFTIP(+) = O.TIPO_OFERTA ");
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<OfertaVigente> ofertaLista = null;		
			
			try {
				
				ofertaLista = (List<OfertaVigente>) this.jdbcTemplate.query(query.toString(),this.rwOfertaMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		    return ofertaLista;
	    }
	    
		// MISUMI-391 MISUMIS-JAVA VEGALSA-FAMILIA sacar las ofertas de oferta vigente
	    @Override
		public OfertaPVP recuperarAnnoOferta(OfertaPVP ofertaPVP) throws Exception {
			List<Object> params = new ArrayList<Object>();
	    	StringBuffer query = new StringBuffer(" SELECT ANO_OFERTA,NUM_OFERTA FROM OFERTA_VIGENTE WHERE COD_CENTRO= ? AND COD_ART= ?");
	    	params.add(ofertaPVP.getCentro());
	    	params.add(ofertaPVP.getCodArticulo());
	    	try {
	    		List<OfertaVigente> ofertaLista = null;
	    		ofertaLista = (List<OfertaVigente>) this.jdbcTemplate.query(query.toString(),this.rwOfertaAuxMap, params.toArray()); 
	    		if(ofertaLista.size()>0){
	    			OfertaVigente ofertaVigente=ofertaLista.get(0);
	    			ofertaPVP.setAnnoOferta(ofertaVigente.getAnoOferta());
	    			ofertaPVP.setCodOferta(ofertaVigente.getNumOferta());
	    		}
	 		} catch (Exception e) {
	 			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
	 		}
		    return ofertaPVP;
		}
	   
}
