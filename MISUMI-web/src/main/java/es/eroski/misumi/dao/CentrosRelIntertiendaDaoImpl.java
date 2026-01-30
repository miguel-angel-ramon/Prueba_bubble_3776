package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CentrosRelIntertiendaDao;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CentrosRelIntertiendaDaoImpl implements CentrosRelIntertiendaDao{
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(CentrosRelIntertiendaDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public Long findOne(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO_REL "
    										+ " FROM T_MIS_CENTROS_INTERTIENDA ");
    
       
        where.append(" AND COD_CENTRO = ? ");
	    params.add(codCentro);	        		
	    query.append(where);
        
	    List<Long> listaCentro = null;
        Long codCentroRel = null;
        try {
        	listaCentro =  this.jdbcTemplate.queryForList(query.toString(), Long.class, params.toArray());
        	
        	if (listaCentro != null && listaCentro.size() > 0) {
        		codCentroRel = listaCentro.get(0);
        	}
        	
	    
	    } catch (Exception e){
			
    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
    	}
	
		return codCentroRel;
	}

}
