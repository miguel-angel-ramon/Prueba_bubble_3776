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

import es.eroski.misumi.dao.iface.VReferenciasPedirSIADao;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VReferenciasPedirSIADaoImpl implements VReferenciasPedirSIADao{
	
	private JdbcTemplate jdbcTemplate;
		 
	private RowMapper<VReferenciasPedirSIA> rwReferenciasPedirSIAMap = new RowMapper<VReferenciasPedirSIA>() {
			public VReferenciasPedirSIA mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VReferenciasPedirSIA(resultSet.getLong("COD_CENTRO"),
			    			resultSet.getLong("COD_ART"),	
			    			resultSet.getLong("COD_N1_EC"), 
			    			resultSet.getLong("COD_N2_EC"), 
			    			resultSet.getLong("COD_N3_EC"), 
			    			resultSet.getLong("COD_N4_EC"),
			    			resultSet.getLong("COD_N5_EC"),
			    			resultSet.getString("FLG_MISUMI"),
			    			resultSet.getString("TIPO")
				    );
			}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	@Override
	public List<VReferenciasPedirSIA> findAll(VReferenciasPedirSIA vReferenciasPedirSIA) throws Exception  {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
	
		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_N1_EC, COD_N2_EC, COD_N3_EC, COD_N4_EC, COD_N5_EC, FLG_MISUMI, TIPO " +
											  " FROM V_REFERENCIAS_PEDIR_SIA ");
	
		
	    if (vReferenciasPedirSIA  != null){
	    	if(vReferenciasPedirSIA.getCodCentro()!=null){
	    		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodCentro());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodArt()!=null){
	    		 where.append(" AND COD_ART = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodArt());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodN1Ec()!=null){
	    		 where.append(" AND COD_N1_EC = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodN1Ec());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodN2Ec()!=null){
	    		 where.append(" AND COD_N2_EC = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodN2Ec());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodN3Ec()!=null){
	    		 where.append(" AND COD_N3_EC = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodN3Ec());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodN4Ec()!=null){
	    		 where.append(" AND COD_N4_EC = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodN4Ec());	        		
	    	}
	    	if(vReferenciasPedirSIA.getCodN5Ec()!=null){
	    		 where.append(" AND COD_N5_EC = ? ");
	        	 params.add(vReferenciasPedirSIA.getCodN5Ec());	        		
	    	}
	    	if(vReferenciasPedirSIA.getTipoPedido()!=null){
	    		if (vReferenciasPedirSIA.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_MONTAJE))) {
	    		   where.append(" AND (FLG_MISUMI = 'M' OR FLG_MISUMI = 'T') "); 
	    		} 
	    		if (vReferenciasPedirSIA.getTipoPedido().equals(new Integer(Constantes.CLASE_PEDIDO_ENCARGO))) {
		    		   where.append(" AND (FLG_MISUMI = 'E' OR FLG_MISUMI = 'T') "); 
		    		}
	    	}
			if(vReferenciasPedirSIA.getTipo()!=null){
				 where.append(" AND TIPO = ? ");
				 params.add(vReferenciasPedirSIA.getTipo());
			}
	    }
	    
	    query.append(where);
	
	    StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_art ");
		query.append(order);
	
		List<VReferenciasPedirSIA> vReferenciasPedirSIALista = null;		
		
		try {
			vReferenciasPedirSIALista = (List<VReferenciasPedirSIA>) this.jdbcTemplate.query(query.toString(),this.rwReferenciasPedirSIAMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return vReferenciasPedirSIALista;
	}
}
