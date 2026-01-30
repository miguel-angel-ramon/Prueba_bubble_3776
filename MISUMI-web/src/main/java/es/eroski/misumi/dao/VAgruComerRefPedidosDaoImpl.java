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

import es.eroski.misumi.dao.iface.VAgruComerRefPedidosDao;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.VAgruComerRefPedidos;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VAgruComerRefPedidosDaoImpl implements VAgruComerRefPedidosDao{
	 private JdbcTemplate jdbcTemplate;
	 private RowMapper<VAgruComerRefPedidos> rwAgruComerRefPedidosMap = new RowMapper<VAgruComerRefPedidos>() {
			public VAgruComerRefPedidos mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VAgruComerRefPedidos(resultSet.getLong("COD_CENTRO"), resultSet.getString("NIVEL"),resultSet.getLong("GRUPO1"),
			    		    resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
			    		    resultSet.getLong("GRUPO4"),resultSet.getLong("GRUPO5"),
			    		    resultSet.getString("DESCRIPCION"), resultSet.getDate("FECHA_GEN")
				    );
			}
		    };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VAgruComerRefPedidos> findAll(VAgruComerRefPedidos vAgruComerRefPedidos, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//params.add(lang);
	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, NIVEL, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5 , DESCRIPCION, FECHA_GEN " 
	    										+ " FROM V_AGRU_COMER_REF_PEDIDOS ");
	    
	    	
	        if (vAgruComerRefPedidos  != null){
	        	if(vAgruComerRefPedidos.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vAgruComerRefPedidos.getCodCentro());	        		
	        	}
	        	if(vAgruComerRefPedidos.getNivel()!=null){
	        		 where.append(" AND NIVEL = upper(?) ");
		        	 params.add(vAgruComerRefPedidos.getNivel());	        		
	        	}
	        	if(vAgruComerRefPedidos.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vAgruComerRefPedidos.getGrupo1());	        		
	        	}
	        	if(vAgruComerRefPedidos.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vAgruComerRefPedidos.getGrupo2());	        		
	        	}
	        	if(vAgruComerRefPedidos.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vAgruComerRefPedidos.getGrupo3());	        		
	        	}
	        	if(vAgruComerRefPedidos.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vAgruComerRefPedidos.getGrupo4());	        		
	        	}
	        	if(vAgruComerRefPedidos.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vAgruComerRefPedidos.getGrupo5());	        		
	        	}
	        	if(vAgruComerRefPedidos.getDescripcion()!=null){
	        		 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
		        	 params.add(vAgruComerRefPedidos.getDescripcion());	        		
	        	}
	        	if(vAgruComerRefPedidos.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vAgruComerRefPedidos.getFechaGen());	        		
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
					if(vAgruComerRefPedidos.getNivel()!=null){
						//Ordenación según el nivel
						campoOrdenacion = "GRUPO" +  vAgruComerRefPedidos.getNivel().substring(1).trim();
					}
					order.append(" order by " + campoOrdenacion + " asc ");	
					query.append(order);
			}

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

		  
		    
		    List<VAgruComerRefPedidos> lista = null;
		    
		    try {
	    		lista = this.jdbcTemplate.query(query.toString(),this.rwAgruComerRefPedidosMap, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    
		    return lista;
	    }
}