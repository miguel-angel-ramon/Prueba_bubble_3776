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

import es.eroski.misumi.dao.iface.VReferenciasPedirSIADetallDao;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.model.VReferenciasPedirSIADetall;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VReferenciasPedirSIADetallDaoImpl implements VReferenciasPedirSIADetallDao{
	
	private JdbcTemplate jdbcTemplate;
		 
	private RowMapper<VReferenciasPedirSIADetall> rwReferenciasPedirSIAMap = new RowMapper<VReferenciasPedirSIADetall>() {
			public VReferenciasPedirSIADetall mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VReferenciasPedirSIADetall(resultSet.getLong("COD_CENTRO"),
			    			resultSet.getLong("COD_ART"),	
			    			resultSet.getLong("COD_N1_EC"), 
			    			resultSet.getLong("COD_N2_EC"), 
			    			resultSet.getLong("COD_N3_EC"), 
			    			resultSet.getLong("COD_N4_EC"),
			    			resultSet.getLong("COD_N5_EC")
				    );
			}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	@Override
	public List<VReferenciasPedirSIADetall> findAll(VReferenciasPedirSIADetall vReferenciasPedirSIADetall) throws Exception  {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
	
		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_N1_EC, COD_N2_EC, COD_N3_EC, COD_N4_EC, COD_N5_EC " +
											  " FROM V_REFERENCIAS_PEDIR_SIA_DETALL ");
	
		
	    if (vReferenciasPedirSIADetall  != null){
	    	if(vReferenciasPedirSIADetall.getCodCentro()!=null){
	    		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodCentro());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodArt()!=null){
	    		 where.append(" AND COD_ART = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodArt());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodN1Ec()!=null){
	    		 where.append(" AND COD_N1_EC = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodN1Ec());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodN2Ec()!=null){
	    		 where.append(" AND COD_N2_EC = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodN2Ec());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodN3Ec()!=null){
	    		 where.append(" AND COD_N3_EC = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodN3Ec());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodN4Ec()!=null){
	    		 where.append(" AND COD_N4_EC = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodN4Ec());	        		
	    	}
	    	if(vReferenciasPedirSIADetall.getCodN5Ec()!=null){
	    		 where.append(" AND COD_N5_EC = ? ");
	        	 params.add(vReferenciasPedirSIADetall.getCodN5Ec());	        		
	    	}
	    }
	    
	    query.append(where);
	
	    StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_art ");
		query.append(order);
	
		List<VReferenciasPedirSIADetall> vReferenciasPedirSIALista = null;		
		try {
			vReferenciasPedirSIALista = (List<VReferenciasPedirSIADetall>) this.jdbcTemplate.query(query.toString(),this.rwReferenciasPedirSIAMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return vReferenciasPedirSIALista;
	}
}
