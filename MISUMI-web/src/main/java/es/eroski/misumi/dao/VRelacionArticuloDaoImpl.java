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

import es.eroski.misumi.dao.iface.VRelacionArticuloDao;
import es.eroski.misumi.model.VReferenciasPedirSIADetall;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VRelacionArticuloDaoImpl implements VRelacionArticuloDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VRelacionArticuloDaoImpl.class);
	 private RowMapper<VRelacionArticulo> rwVRelacionArticuloMap = new RowMapper<VRelacionArticulo>() {
			public VRelacionArticulo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VRelacionArticulo(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("COD_ART_RELA"), resultSet.getString("DESCRIP_ART"),
			    			resultSet.getFloat("UNI_CAJA_SERV"), resultSet.getString("FORMA_PROD_ACTI")
				    );
			}

		};

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VRelacionArticulo> findAll(VRelacionArticulo vRelacionArticulo) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	//StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_ART_RELA, DESCRIP_ART, UNI_CAJA_SERV " 
	    	//									+ " FROM V_RELACION_ARTICULO ");
	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_ART_RELA, DESCRIP_ART, UNI_CAJA_SERV, FORMA_PROD_ACTI " 
					+ " FROM V_RELACION_ARTICULO ");
	    	
	        if (vRelacionArticulo  != null){
	        	if(vRelacionArticulo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vRelacionArticulo.getCodCentro());	        		
	        	}
	        	if(vRelacionArticulo.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vRelacionArticulo.getCodArt());	        		
	        	}
	        	if(vRelacionArticulo.getCodArtRela()!=null){
	        		 where.append(" AND COD_ART_RELA = ? ");
		        	 params.add(vRelacionArticulo.getCodArtRela());	        		
	        	}
    			if(vRelacionArticulo.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(vRelacionArticulo.getDescripArt());	        		
	        	}
	        	if(vRelacionArticulo.getUniCajaServ()!=null){
	        		 where.append(" AND UNI_CAJA_SERV = ? ");
		        	 params.add(vRelacionArticulo.getUniCajaServ());	        		
	        	}
	        }
	        
	        query.append(where);

	        /*StringBuffer order = new StringBuffer(3000);
			order.append(" order by xxxxx ");
			query.append(order);*/

			List<VRelacionArticulo> vRelacionArticuloLista = null;		
			
			try {
				vRelacionArticuloLista = (List<VRelacionArticulo>) this.jdbcTemplate.query(query.toString(),this.rwVRelacionArticuloMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vRelacionArticuloLista;
	    }
	   
}
