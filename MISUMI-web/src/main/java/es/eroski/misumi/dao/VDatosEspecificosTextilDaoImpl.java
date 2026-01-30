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

import es.eroski.misumi.dao.iface.VDatosEspecificosTextilDao;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VDatosEspecificosTextilDaoImpl implements VDatosEspecificosTextilDao{
	
	 private JdbcTemplate jdbcTemplate;
	   
	 private static Logger logger = Logger.getLogger(VOfertaDaoImpl.class);
	 
	 private RowMapper<PedidoAdicionalE> rwPedidoAdicionaTextillMap = new RowMapper<PedidoAdicionalE>() {
			public PedidoAdicionalE mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new PedidoAdicionalE(resultSet.getString("DESCR_COLOR"),resultSet.getString("DESCR_TALLA"),resultSet.getString("MODELO_PROVEEDOR"));
			}
		};
  
    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


	@Override
	public PedidoAdicionalE findTextil(PedidoAdicionalE pedidoAdicionalE)
			throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DESCR_COLOR, DESCR_TALLA, MODELO_PROVEEDOR" 
    										+ " FROM V_DATOS_ESPECIFICOS_TEXTIL ");
        	
        if (pedidoAdicionalE  != null){
        	if(pedidoAdicionalE.getCodArticulo()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(pedidoAdicionalE.getCodArticulo());
        	}
        }
        
        query.append(where);
        logger.debug(query);
        
        PedidoAdicionalE pedidoAdicionalEOut  = null;
	    
	    try {
	    	pedidoAdicionalEOut = this.jdbcTemplate.queryForObject(query.toString(),this.rwPedidoAdicionaTextillMap, params.toArray());	

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return pedidoAdicionalEOut;
	} 
	    
	   
}

