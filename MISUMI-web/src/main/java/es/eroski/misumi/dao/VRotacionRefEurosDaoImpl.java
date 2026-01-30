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

import es.eroski.misumi.dao.iface.VRotacionRefEurosDao;
import es.eroski.misumi.model.VRotacionRefEuros;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VRotacionRefEurosDaoImpl implements VRotacionRefEurosDao{
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(VRotacionRefDaoImpl.class);
	// private static Logger logger = Logger.getLogger(VRotacionRefDaoImpl.class);

	 private RowMapper<VRotacionRefEuros> rwVRotacionRefEurosMap = new RowMapper<VRotacionRefEuros>() {
			public VRotacionRefEuros mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new VRotacionRefEuros(resultSet.getLong("COD_CENTRO"), 
						resultSet.getLong("COD_ART"), 
						resultSet.getLong("GRUPO1"),
						resultSet.getLong("GRUPO2"),
						resultSet.getLong("GRUPO3"),
						resultSet.getLong("GRUPO4"),
						resultSet.getLong("GRUPO5"),
						resultSet.getDouble("TOT_VENTA_REF"), 
						resultSet.getDouble("TOT_VENTA_GRUPO"), 
						resultSet.getDouble("PORCENTAJE_REF"), 
						resultSet.getDouble("PORCENTAJE_TOT"), 
						resultSet.getString("TIPO_ROT_REF"),
						resultSet.getString("TIPO_ROT_TOTAL")
				    );
			}
	    };
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VRotacionRefEuros> findAll(VRotacionRefEuros vRotacionRefEuros) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, GRUPO1, GRUPO2, GRUPO3, " 
	    										  + " GRUPO4, GRUPO5, TOT_VENTA_REF, TOT_VENTA_GRUPO, "
	    										  + " PORCENTAJE_REF, TIPO_ROT_REF, PORCENTAJE_TOT, TIPO_ROT_TOTAL "
	    										  + " FROM V_ROTACION_REF_EUROS ");

	        if (vRotacionRefEuros  != null){
	        	if(vRotacionRefEuros.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vRotacionRefEuros.getCodCentro());	        		
	        	}
	        	if(vRotacionRefEuros.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vRotacionRefEuros.getCodArt());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo1());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo2());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo3());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo4());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo5());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaRef()!=null){
	        		 where.append(" AND TOT_VENTA_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaGrupo()!=null){
	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaGrupo());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeRef()!=null){
	        		 where.append(" AND PORCENTAJE_REF = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotRef()!=null){
	        		 where.append(" AND TIPO_ROT_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotRef());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeTot()!=null){
	        		 where.append(" AND PORCENTAJE_TOT = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeTot());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT_TOTAL = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotTotal());	        		
	        	}
	        }
	        
	        query.append(where);

	        List<VRotacionRefEuros> lista = null; 
		    try {
				lista = (List<VRotacionRefEuros>) this.jdbcTemplate.query(query.toString(),this.rwVRotacionRefEurosMap, params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return lista;
	    }
	    
	    @Override
	    public Long findAllCont(VRotacionRefEuros vRotacionRefEuros) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "
	    										+ " FROM V_ROTACION_REF_EUROS ");
	    
	    	
	        if (vRotacionRefEuros  != null){
	        	if(vRotacionRefEuros.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vRotacionRefEuros.getCodCentro());	        		
	        	}
	        	if(vRotacionRefEuros.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vRotacionRefEuros.getCodArt());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo1());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo2());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo3());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo4());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo5());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaRef()!=null){
	        		 where.append(" AND TOT_VENTA_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaGrupo()!=null){
	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaGrupo());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeRef()!=null){
	        		 where.append(" AND PORCENTAJE_REF = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotRef()!=null){
	        		 where.append(" AND TIPO_ROT_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotRef());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeTot()!=null){
	        		 where.append(" AND PORCENTAJE_TOT = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeTot());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT_TOTAL = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotTotal());	        		
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
		public VRotacionRefEuros findOne(VRotacionRefEuros vRotacionRefEuros) throws Exception {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT * FROM ( " 
	    			+ "SELECT COD_CENTRO, COD_ART, GRUPO1, GRUPO2, GRUPO3, " 
				    	+ " GRUPO4, GRUPO5, TOT_VENTA_REF, TOT_VENTA_GRUPO, "
				    	+ " PORCENTAJE_REF, TIPO_ROT_REF, PORCENTAJE_TOT, TIPO_ROT_TOTAL "
				    	+ " FROM V_ROTACION_REF_EUROS ");

	        if (vRotacionRefEuros  != null){
	        	if(vRotacionRefEuros.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vRotacionRefEuros.getCodCentro());	        		
	        	}
	        	if(vRotacionRefEuros.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vRotacionRefEuros.getCodArt());	        		
	        	}
	        	/*
	        	if(vRotacionRefEuros.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo1());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo2());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo3());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo4());	        		
	        	}
	        	if(vRotacionRefEuros.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vRotacionRefEuros.getGrupo5());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaRef()!=null){
	        		 where.append(" AND TOT_VENTA_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTotVentaGrupo()!=null){
	        		 where.append(" AND TOT_VENTA_GRUPO = ? ");
		        	 params.add(vRotacionRefEuros.getTotVentaGrupo());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeRef()!=null){
	        		 where.append(" AND PORCENTAJE_REF = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeRef());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotRef()!=null){
	        		 where.append(" AND TIPO_ROT_REF = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotRef());	        		
	        	}
	        	if(vRotacionRefEuros.getPorcentajeTot()!=null){
	        		 where.append(" AND PORCENTAJE_TOT = ? ");
		        	 params.add(vRotacionRefEuros.getPorcentajeTot());	        		
	        	}
	        	if(vRotacionRefEuros.getTipoRotTotal()!=null){
	        		 where.append(" AND TIPO_ROT_TOTAL = ? ");
		        	 params.add(vRotacionRefEuros.getTipoRotTotal());	        		
	        	}
	        	*/
	        }
	        
	        query.append(where);

			query.append(" ) WHERE ROWNUM = 1 ");

			List<VRotacionRefEuros> vRotacionRefEurosLista = null;		
		
		    try {
		    	vRotacionRefEurosLista = (List<VRotacionRefEuros>) this.jdbcTemplate.query(query.toString(),this.rwVRotacionRefEurosMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		   
			VRotacionRefEuros vRotacionRefEurosRes = null;
			if (!vRotacionRefEurosLista.isEmpty()){
				vRotacionRefEurosRes = vRotacionRefEurosLista.get(0);
			}
		    return vRotacionRefEurosRes;
		}
}
