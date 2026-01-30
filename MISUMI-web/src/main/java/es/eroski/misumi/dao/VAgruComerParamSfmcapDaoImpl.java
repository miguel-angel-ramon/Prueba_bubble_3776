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

import es.eroski.misumi.dao.iface.VAgruComerParamSfmcapDao;
import es.eroski.misumi.model.VAgruComerParamSfmcap;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VAgruComerParamSfmcapDaoImpl implements VAgruComerParamSfmcapDao{
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = Logger.getLogger(VAgruComerParamSfmcapDaoImpl.class);
	 private RowMapper<VAgruComerParamSfmcap> rwAgruComerParamSfmcapMap = new RowMapper<VAgruComerParamSfmcap>() {
			public VAgruComerParamSfmcap mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VAgruComerParamSfmcap(resultSet.getLong("COD_CENTRO"), resultSet.getString("NIVEL"),resultSet.getLong("GRUPO1"),
			    		    resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
			    		    resultSet.getLong("GRUPO4"),resultSet.getLong("GRUPO5"),
			    		    resultSet.getString("DESCRIPCION"), resultSet.getDate("FECHA_GEN"),
			    		    resultSet.getString("FLG_STOCK_FINAL"),resultSet.getString("FLG_CAPACIDAD"),
			    		    resultSet.getString("FLG_FACING_CENTRO"),resultSet.getString("FLG_FAC_CAPACIDAD")
				    );
			}
		    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VAgruComerParamSfmcap> findAll(VAgruComerParamSfmcap vAgruComerParamSfmcap, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//params.add(lang);
	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, NIVEL, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5 , DESCRIPCION, FECHA_GEN, FLG_STOCK_FINAL, FLG_CAPACIDAD, FLG_FACING_CENTRO, FLG_FAC_CAPACIDAD " 
	    										+ " FROM V_AGRU_COMER_PARAM_SFMCAP ");
	    
	    	
	        if (vAgruComerParamSfmcap  != null){
	        	if(vAgruComerParamSfmcap.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vAgruComerParamSfmcap.getCodCentro());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getNivel()!=null){
	        		 where.append(" AND NIVEL = upper(?) ");
		        	 params.add(vAgruComerParamSfmcap.getNivel());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vAgruComerParamSfmcap.getGrupo1());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vAgruComerParamSfmcap.getGrupo2());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vAgruComerParamSfmcap.getGrupo3());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vAgruComerParamSfmcap.getGrupo4());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vAgruComerParamSfmcap.getGrupo5());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getDescripcion()!=null){
	        		 where.append(" AND DESCRIPCION = upper(?) ");
		        	 params.add(vAgruComerParamSfmcap.getDescripcion());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vAgruComerParamSfmcap.getFechaGen());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getFlgStockFinal()!=null){
	        		 where.append(" AND FLG_STOCK_FINAL = upper(?) ");
		        	 params.add(vAgruComerParamSfmcap.getFlgStockFinal());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getFlgCapacidad()!=null){
	        		 where.append(" AND FLG_CAPACIDAD = upper(?) ");
		        	 params.add(vAgruComerParamSfmcap.getFlgCapacidad());	        		
	        	}
	        	if(vAgruComerParamSfmcap.getFlgFacing()!=null){
	        		 where.append(" AND FLG_FACING_CENTRO = upper(?) ");
		        	 params.add(vAgruComerParamSfmcap.getFlgFacing());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" order by " + pagination.getSort() + " "
							+ pagination.getAscDsc());
					query.append(order);
				}
			}
				else{
					String campoOrdenacion = "DESCRIPCION";
					if(vAgruComerParamSfmcap.getNivel()!=null){
						//Ordenaci�n seg�n el nivel
						campoOrdenacion = "GRUPO" +  vAgruComerParamSfmcap.getNivel().substring(1).trim();
					}
					order.append(" order by " + campoOrdenacion + " asc ");	
					query.append(order);
			}

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

		   
		    
		    List<VAgruComerParamSfmcap> lista = null;
		    
		    try {
	    		lista = this.jdbcTemplate.query(query.toString(),this.rwAgruComerParamSfmcapMap, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    
		    return lista;
	    }
	   
}
