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

import es.eroski.misumi.dao.iface.CentroRefAprovDao;
import es.eroski.misumi.model.CentroRefAprov;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class CentroRefAprovDaoImpl implements CentroRefAprovDao {
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = Logger.getLogger(VAgruComerRefDaoImpl.class);
	 private RowMapper<String> rwAgruMap = new RowMapper<String>() {
			public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return resultSet.getString("AMBITO");
			}

		    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    

	    @Override
		public List<String> findAll(CentroRefAprov centroRefAprov) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//params.add(lang);
	    	StringBuffer query = new StringBuffer(" SELECT  CASE WHEN decode(cod_n1_ec,null, '0', ");
            query.append("      decode(cod_n2_ec,null,'1', ");
            query.append("      decode(cod_n3_ec,null,'2', ");
            query.append("      decode(cod_n4_ec,null,'3', ");
            query.append("                 decode(cod_n5_ec,null,'4','5')  ");
            query.append("                     ) ");
            query.append("                    ) ");
            query.append("                )) > ? THEN 'AMBOS' ");
            params.add(centroRefAprov.getNivel());
            query.append(" ELSE 'SIA' END AS AMBITO ");
            query.append(" FROM CENTRO_REF_APROV_AUX ");
            if (null != centroRefAprov){
            	if (null != centroRefAprov.getCodCentro()){
            		where.append(" AND COD_LOC_DEST = ?");
    	            params.add(centroRefAprov.getCodCentro());
 	            }
	            
	            if (null != centroRefAprov.getGrupo1()){
	            	where.append(" AND ?  = nvl(COD_N1_EC, ?)");
	            	params.add(centroRefAprov.getGrupo1());
	            	params.add(centroRefAprov.getGrupo1());
	            	 if (null != centroRefAprov.getGrupo2()){
	 	            	where.append(" AND ?  = nvl(COD_N2_EC, ?)");
	 	            	params.add(centroRefAprov.getGrupo2());
	 	            	params.add(centroRefAprov.getGrupo2());
	 	            	if (null != centroRefAprov.getGrupo3()){
		 	            	where.append(" AND ?  = nvl(COD_N3_EC, ?)");
		 	            	params.add(centroRefAprov.getGrupo3());
		 	            	params.add(centroRefAprov.getGrupo3());
		 	            	if (null != centroRefAprov.getGrupo4()){
			 	            	where.append(" AND ?  = nvl(COD_N4_EC, ?)");
			 	            	params.add(centroRefAprov.getGrupo4());
			 	            	params.add(centroRefAprov.getGrupo4());
			 	            	if (null != centroRefAprov.getGrupo5()){
				 	            	where.append(" AND ?  = nvl(COD_N5_EC, ?)");
				 	            	params.add(centroRefAprov.getGrupo5());
				 	            	params.add(centroRefAprov.getGrupo5());
				 	            }
			 	            }
		 	            	
		 	            }
	 	            }
	            }
	            if (null != centroRefAprov.getFecha()){
            		where.append(" AND FEC_INI <= TRUNC(?)");
            		where.append(" AND FEC_FIN >= TRUNC(?)");
    	            params.add(centroRefAprov.getFecha());
    	            params.add(centroRefAprov.getFecha());
 	            }
            }
            
	        query.append(where);
	        
	        order.append(" ORDER BY NVL(COD_N1_EC, 0), NVL(COD_N2_EC, 0), NVL(COD_N3_EC, 0), NVL(COD_N4_EC, 0), NVL(COD_N5_EC,0) DESC");
	        query.append(order);
		   
	        
	        List<String> lista = null;
	        try { 
	        	lista = (List<String>) this.jdbcTemplate.query(query.toString(),this.rwAgruMap, params.toArray());
			 } catch (Exception e){
						
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
				
				return lista;
					    
	    }
	   
}
