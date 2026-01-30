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

import es.eroski.misumi.dao.iface.VAgruComerOfertaPaDao;
import es.eroski.misumi.model.VAgruComerOfertaPa;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VAgruComerOfertaPaDaoImpl implements VAgruComerOfertaPaDao{
	 private JdbcTemplate jdbcTemplate;
	 private RowMapper<VAgruComerOfertaPa> rwAgruComerRefPedidosMap = new RowMapper<VAgruComerOfertaPa>() {
			public VAgruComerOfertaPa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VAgruComerOfertaPa(resultSet.getLong("COD_CENTRO"),resultSet.getLong("ANO_OFERTA"), 
			    		resultSet.getLong("NUM_OFERTA"),resultSet.getString("NIVEL"),resultSet.getLong("GRUPO1"),
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
	    public List<VAgruComerOfertaPa> findAll(VAgruComerOfertaPa vAgruComerOfertaPa) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//params.add(lang);
	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, ANO_OFERTA, NUM_OFERTA, NIVEL, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5 , DESCRIPCION, FECHA_GEN " 
	    										+ " FROM V_AGRU_COMER_OFERTA_PA ");
	    
	    	
	        if (vAgruComerOfertaPa  != null){
	        	if(vAgruComerOfertaPa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vAgruComerOfertaPa.getCodCentro());	        		
	        	}
	        	if(vAgruComerOfertaPa.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vAgruComerOfertaPa.getAnoOferta());	        		
	        	}
	        	if(vAgruComerOfertaPa.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vAgruComerOfertaPa.getNumOferta());	        		
	        	}
	        	if(vAgruComerOfertaPa.getNivel()!=null){
	        		 where.append(" AND NIVEL = upper(?) ");
		        	 params.add(vAgruComerOfertaPa.getNivel());	        		
	        	}
	        	if(vAgruComerOfertaPa.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vAgruComerOfertaPa.getGrupo1());	        		
	        	}
	        	if(vAgruComerOfertaPa.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vAgruComerOfertaPa.getGrupo2());	        		
	        	}
	        	if(vAgruComerOfertaPa.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vAgruComerOfertaPa.getGrupo3());	        		
	        	}
	        	if(vAgruComerOfertaPa.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vAgruComerOfertaPa.getGrupo4());	        		
	        	}
	        	if(vAgruComerOfertaPa.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vAgruComerOfertaPa.getGrupo5());	        		
	        	}
	        	if(vAgruComerOfertaPa.getDescripcion()!=null){
	        		 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
		        	 params.add(vAgruComerOfertaPa.getDescripcion());	        		
	        	}
	        	if(vAgruComerOfertaPa.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vAgruComerOfertaPa.getFechaGen());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			String campoOrdenacion = "DESCRIPCION";
			if(vAgruComerOfertaPa.getNivel()!=null){
				//Ordenaci�n seg�n el nivel
				campoOrdenacion = "GRUPO" +  vAgruComerOfertaPa.getNivel().substring(1).trim();
			}
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);

		    List<VAgruComerOfertaPa> lista = null;
		    
		    try {
	    		lista = this.jdbcTemplate.query(query.toString(),this.rwAgruComerRefPedidosMap, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    
		    return lista;
	    }
}