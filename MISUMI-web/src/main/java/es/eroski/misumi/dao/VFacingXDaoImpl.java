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

import es.eroski.misumi.dao.iface.VFacingXDao;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VFacingXDaoImpl implements VFacingXDao{
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(vFacingXDaoImpl.class);
	// private static Logger logger = Logger.getLogger(vFacingXDaoImpl.class);

	 private RowMapper<VFacingX> rwVFacingXMap = new RowMapper<VFacingX>() {
			public VFacingX mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new VFacingX(resultSet.getLong("COD_N1"), 
						resultSet.getLong("COD_N2"),
						resultSet.getLong("COD_N3"),
						resultSet.getLong("COD_CENTRO"),
						resultSet.getLong("GRUPO1"),
						resultSet.getLong("GRUPO2"),
						resultSet.getLong("GRUPO3"), 
						resultSet.getLong("GRUPO4"), 
						resultSet.getLong("GRUPO5"),
						resultSet.getLong("COD_ARTICULO"),
						resultSet.getLong("FACING_X"),
						resultSet.getDate("FEC_INICIO") 
				    );
			}
	    };
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VFacingX> findAll(VFacingX vFacingX) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT COD_N1, COD_N2, COD_N3, "
	    											+ " COD_CENTRO, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, "
	    											+ " COD_ARTICULO, FACING_X, FEC_INICIO "
	    											+ " FROM V_FACING_X ");
	    										//	+ " FROM VM_HZT_FACING_X "); //Peticion 54867

	        if (vFacingX  != null){
	        	if(vFacingX.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = ? ");
		        	 params.add(vFacingX.getCodN1());	        		
	        	}
	        	if(vFacingX.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = ? ");
		        	 params.add(vFacingX.getCodN2());	        		
	        	}
	        	if(vFacingX.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = ? ");
		        	 params.add(vFacingX.getCodN3());	        		
	        	}
	        	if(vFacingX.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vFacingX.getCodCentro());	        		
	        	}
	        	if(vFacingX.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vFacingX.getGrupo1());	        		
	        	}
	        	if(vFacingX.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vFacingX.getGrupo2());	        		
	        	}
	        	if(vFacingX.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vFacingX.getGrupo3());	        		
	        	}
	        	if(vFacingX.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vFacingX.getGrupo4());	        		
	        	}
	        	if(vFacingX.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vFacingX.getGrupo5());	        		
	        	}
	        	if(vFacingX.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vFacingX.getCodArticulo());	        		
	        	}
	        	if(vFacingX.getFacingX()!=null){
	        		 where.append(" AND FACING_X = ? ");
		        	 params.add(vFacingX.getFacingX());	        		
	        	}
	        	if(vFacingX.getFecInicio()!=null){
	        		 where.append(" AND FEC_INICIO = ? ");
		        	 params.add(vFacingX.getFecInicio());	        		
	        	}
	        }
	        
	        query.append(where);

		    List<VFacingX> lista = null;
		    try {
		    	lista = this.jdbcTemplate.query(query.toString(),this.rwVFacingXMap, params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return lista;

	    }
	    
	    @Override
	    public Long findAllCont(VFacingX vFacingX) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
	    						    			+ " FROM V_FACING_X "); 
							//					+ " FROM VM_HZT_FACING_X "); //Peticion 54867
	    
	        if (vFacingX  != null){
	        	if(vFacingX.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = ? ");
		        	 params.add(vFacingX.getCodN1());	        		
	        	}
	        	if(vFacingX.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = ? ");
		        	 params.add(vFacingX.getCodN2());	        		
	        	}
	        	if(vFacingX.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = ? ");
		        	 params.add(vFacingX.getCodN3());	        		
	        	}
	        	if(vFacingX.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vFacingX.getCodCentro());	        		
	        	}
	        	if(vFacingX.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vFacingX.getGrupo1());	        		
	        	}
	        	if(vFacingX.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vFacingX.getGrupo2());	        		
	        	}
	        	if(vFacingX.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vFacingX.getGrupo3());	        		
	        	}
	        	if(vFacingX.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vFacingX.getGrupo4());	        		
	        	}
	        	if(vFacingX.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vFacingX.getGrupo5());	        		
	        	}
	        	if(vFacingX.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vFacingX.getCodArticulo());	        		
	        	}
	        	if(vFacingX.getFacingX()!=null){
	        		 where.append(" AND FACING_X = ? ");
		        	 params.add(vFacingX.getFacingX());	        		
	        	}
	        	if(vFacingX.getFecInicio()!=null){
	        		 where.append(" AND FEC_INICIO = ? ");
		        	 params.add(vFacingX.getFecInicio());	        		
	        	}
	        }
	        
	        query.append(where);
	       
		    
		    Long cont = null;
		    try {
		    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return cont;
	    }
	    
		@Override
		public VFacingX findOne(VFacingX vFacingX) throws Exception {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT * FROM ( "
	    			+ " SELECT COD_N1, COD_N2, COD_N3, "
					+ " COD_CENTRO, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, "
					+ " COD_ARTICULO, FACING_X, FEC_INICIO "
					 + " FROM V_FACING_X ");
					//+ " FROM VM_HZT_FACING_X ");  //Peticion 54867

	        if (vFacingX  != null){
	        	if(vFacingX.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = ? ");
		        	 params.add(vFacingX.getCodN1());	        		
	        	}
	        	if(vFacingX.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = ? ");
		        	 params.add(vFacingX.getCodN2());	        		
	        	}
	        	if(vFacingX.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = ? ");
		        	 params.add(vFacingX.getCodN3());	        		
	        	}
	        	if(vFacingX.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vFacingX.getCodCentro());	        		
	        	}
	        	if(vFacingX.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vFacingX.getGrupo1());	        		
	        	}
	        	if(vFacingX.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vFacingX.getGrupo2());	        		
	        	}
	        	if(vFacingX.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vFacingX.getGrupo3());	        		
	        	}
	        	if(vFacingX.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vFacingX.getGrupo4());	        		
	        	}
	        	if(vFacingX.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vFacingX.getGrupo5());	        		
	        	}
	        	if(vFacingX.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vFacingX.getCodArticulo());	        		
	        	}
	        	if(vFacingX.getFacingX()!=null){
	        		 where.append(" AND FACING_X = ? ");
		        	 params.add(vFacingX.getFacingX());	        		
	        	}
	        	if(vFacingX.getFecInicio()!=null){
	        		 where.append(" AND FEC_INICIO = ? ");
		        	 params.add(vFacingX.getFecInicio());	        		
	        	}
	        }
	        
	        query.append(where);

			query.append(" ) WHERE ROWNUM = 1 ");

			List<VFacingX> vFacingXLista = null;	
			
		    try {
		    	vFacingXLista = (List<VFacingX>) this.jdbcTemplate.query(query.toString(),this.rwVFacingXMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}

			VFacingX vFacingXRes = null;
			if (!vFacingXLista.isEmpty()){
				vFacingXRes = vFacingXLista.get(0);
			}
		    return vFacingXRes;
		}
}
