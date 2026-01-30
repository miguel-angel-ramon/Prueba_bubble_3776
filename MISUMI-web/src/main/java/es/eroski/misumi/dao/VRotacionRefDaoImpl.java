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

import es.eroski.misumi.dao.iface.VRotacionRefDao;
import es.eroski.misumi.model.VRotacionRef;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VRotacionRefDaoImpl implements VRotacionRefDao{
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(VRotacionRefDaoImpl.class);
	// private static Logger logger = Logger.getLogger(VRotacionRefDaoImpl.class);

	 private RowMapper<VRotacionRef> rwVRotacionRefMap = new RowMapper<VRotacionRef>() {
			public VRotacionRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new VRotacionRef(resultSet.getLong("COD_LOC"), 
						resultSet.getLong("COD_ARTICULO"),
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
//						resultSet.getLong("GRUPO1"),
//						resultSet.getLong("GRUPO2"),
//						resultSet.getLong("GRUPO3"),
//						resultSet.getLong("GRUPO4"),
//						resultSet.getLong("GRUPO5"),
//						resultSet.getDouble("TOT_VENTA_REF"), 
//						resultSet.getDouble("TOT_VENTA_GRUPO"), 
//						resultSet.getDouble("PORCENTAJE_REF"), 
//						resultSet.getDouble("PORCENTAJE_TOT"), 
//						resultSet.getString("TIPO_ROT_REF"),
						resultSet.getString("TIPO_ROT")
				    );
			}
	    };
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VRotacionRef> findAll(VRotacionRef vRotacionRef) throws Exception {
	    	System.out.println("findAll - T_UNI_ROTACION_REF");
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 AND FLG_BAJA='N' ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT * "
	    										  + " FROM T_UNI_ROTACION_REF ");

	        if (vRotacionRef  != null){
	        	if(vRotacionRef.getCodCentro()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(vRotacionRef.getCodCentro());	        		
	        	}
	        	if(vRotacionRef.getCodArt()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vRotacionRef.getCodArt());	        		
	        	}
//	        	if(vRotacionRef.getGrupo1()!=null){
//	        		 where.append(" AND GRUPO1 = ? ");
//		        	 params.add(vRotacionRef.getGrupo1());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo2()!=null){
//	        		 where.append(" AND GRUPO2 = ? ");
//		        	 params.add(vRotacionRef.getGrupo2());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo3()!=null){
//	        		 where.append(" AND GRUPO3 = ? ");
//		        	 params.add(vRotacionRef.getGrupo3());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo4()!=null){
//	        		 where.append(" AND GRUPO4 = ? ");
//		        	 params.add(vRotacionRef.getGrupo4());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo5()!=null){
//	        		 where.append(" AND GRUPO5 = ? ");
//		        	 params.add(vRotacionRef.getGrupo5());	        		
//	        	}
//	        	if(vRotacionRef.getTotVentaRef()!=null){
//	        		 where.append(" AND TOT_VENTA_REF = ? ");
//		        	 params.add(vRotacionRef.getTotVentaRef());	        		
//	        	}
//	        	if(vRotacionRef.getTotVentaGrupo()!=null){
//	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
//		        	 params.add(vRotacionRef.getTotVentaGrupo());	        		
//	        	}
//	        	if(vRotacionRef.getPorcentajeRef()!=null){
//	        		 where.append(" AND PORCENTAJE_REF = ? ");
//		        	 params.add(vRotacionRef.getPorcentajeRef());	        		
//	        	}
//	        	if(vRotacionRef.getTipoRotRef()!=null){
//	        		 where.append(" AND TIPO_ROT_REF = ? ");
//		        	 params.add(vRotacionRef.getTipoRotRef());	        		
//	        	}
//	        	if(vRotacionRef.getPorcentajeTot()!=null){
//	        		 where.append(" AND PORCENTAJE_TOT = ? ");
//		        	 params.add(vRotacionRef.getPorcentajeTot());	        		
//	        	}
	        	if(vRotacionRef.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT = ? ");
		        	 params.add(vRotacionRef.getTipoRotTotal());	        		
	        	}
	        }
	        
	        query.append(where);

	        List<VRotacionRef> lista = null; 
		    try {
				lista = (List<VRotacionRef>) this.jdbcTemplate.query(query.toString(),this.rwVRotacionRefMap, params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return lista;
	    }
	    
	    @Override
	    public Long findAllCont(VRotacionRef vRotacionRef) throws Exception {
	    	System.out.println("findAllCont - T_UNI_ROTACION_REF");
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 AND FLG_BAJA='N' ");
	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
	    										+ " FROM T_UNI_ROTACION_REF ");
	    
	    	
	        if (vRotacionRef  != null){
	        	if(vRotacionRef.getCodCentro()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(vRotacionRef.getCodCentro());	        		
	        	}
	        	if(vRotacionRef.getCodArt()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vRotacionRef.getCodArt());	        		
	        	}
//	        	if(vRotacionRef.getGrupo1()!=null){
//	        		 where.append(" AND GRUPO1 = ? ");
//		        	 params.add(vRotacionRef.getGrupo1());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo2()!=null){
//	        		 where.append(" AND GRUPO2 = ? ");
//		        	 params.add(vRotacionRef.getGrupo2());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo3()!=null){
//	        		 where.append(" AND GRUPO3 = ? ");
//		        	 params.add(vRotacionRef.getGrupo3());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo4()!=null){
//	        		 where.append(" AND GRUPO4 = ? ");
//		        	 params.add(vRotacionRef.getGrupo4());	        		
//	        	}
//	        	if(vRotacionRef.getGrupo5()!=null){
//	        		 where.append(" AND GRUPO5 = ? ");
//		        	 params.add(vRotacionRef.getGrupo5());	        		
//	        	}
//	        	if(vRotacionRef.getTotVentaRef()!=null){
//	        		 where.append(" AND TOT_VENTA_REF = ? ");
//		        	 params.add(vRotacionRef.getTotVentaRef());	        		
//	        	}
//	        	if(vRotacionRef.getTotVentaGrupo()!=null){
//	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
//		        	 params.add(vRotacionRef.getTotVentaGrupo());	        		
//	        	}
//	        	if(vRotacionRef.getPorcentajeRef()!=null){
//	        		 where.append(" AND PORCENTAJE_REF = ? ");
//		        	 params.add(vRotacionRef.getPorcentajeRef());	        		
//	        	}
//	        	if(vRotacionRef.getTipoRotRef()!=null){
//	        		 where.append(" AND TIPO_ROT_REF = ? ");
//		        	 params.add(vRotacionRef.getTipoRotRef());	        		
//	        	}
//	        	if(vRotacionRef.getPorcentajeTot()!=null){
//	        		 where.append(" AND PORCENTAJE_TOT = ? ");
//		        	 params.add(vRotacionRef.getPorcentajeTot());	        		
//	        	}
	        	if(vRotacionRef.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT = ? ");
		        	 params.add(vRotacionRef.getTipoRotTotal());	        		
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
		public VRotacionRef findOne(VRotacionRef vRotacionRef) throws Exception {
	    	System.out.println("findOne - T_UNI_ROTACION_REF");

	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 AND FLG_BAJA='N' ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT * FROM ( " 
	    			+ "SELECT * "
				    	+ " FROM T_UNI_ROTACION_REF ");

	        if (vRotacionRef  != null){
	        	if(vRotacionRef.getCodCentro()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(vRotacionRef.getCodCentro());	        		
	        	}
	        	if(vRotacionRef.getCodArt()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(vRotacionRef.getCodArt());	        		
	        	}
	        	/*
	        	if(vRotacionRef.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vRotacionRef.getGrupo1());	        		
	        	}
	        	if(vRotacionRef.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vRotacionRef.getGrupo2());	        		
	        	}
	        	if(vRotacionRef.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vRotacionRef.getGrupo3());	        		
	        	}
	        	if(vRotacionRef.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vRotacionRef.getGrupo4());	        		
	        	}
	        	if(vRotacionRef.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vRotacionRef.getGrupo5());	        		
	        	}
	        	if(vRotacionRef.getTotVentaRef()!=null){
	        		 where.append(" AND TOT_VENTA_REF = ? ");
		        	 params.add(vRotacionRef.getTotVentaRef());	        		
	        	}
	        	if(vRotacionRef.getTotVentaGrupo()!=null){
	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
		        	 params.add(vRotacionRef.getTotVentaGrupo());	        		
	        	}
	        	if(vRotacionRef.getPorcentajeRef()!=null){
	        		 where.append(" AND PORCENTAJE_REF = ? ");
		        	 params.add(vRotacionRef.getPorcentajeRef());	        		
	        	}
	        	if(vRotacionRef.getTipoRotRef()!=null){
	        		 where.append(" AND TIPO_ROT_REF = ? ");
		        	 params.add(vRotacionRef.getTipoRotRef());	        		
	        	}
	        	if(vRotacionRef.getPorcentajeTot()!=null){
	        		 where.append(" AND PORCENTAJE_TOT = ? ");
		        	 params.add(vRotacionRef.getPorcentajeTot());	        		
	        	}
	        	if(vRotacionRef.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT = ? ");
		        	 params.add(vRotacionRef.getTipoRotTotal());	        		
	        	}
	        	*/
	        }
	        
	        query.append(where);

			query.append(" ) WHERE ROWNUM = 1 ");

			List<VRotacionRef> vRotacionRefLista = null;		
		
		    try {
		    	vRotacionRefLista = (List<VRotacionRef>) this.jdbcTemplate.query(query.toString(),this.rwVRotacionRefMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		   
			VRotacionRef vRotacionRefRes = null;
			if (vRotacionRefLista!=null && !vRotacionRefLista.isEmpty()){
				vRotacionRefRes = vRotacionRefLista.get(0);
			}
		    return vRotacionRefRes;
		}
}
