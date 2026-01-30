package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VPlanogramasPiladasDao;
import es.eroski.misumi.model.VPescaMostrador;
import es.eroski.misumi.model.VPlanogramasPiladas;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VPlanogramasPiladasDaoImpl implements VPlanogramasPiladasDao {
	
	private JdbcTemplate jdbcTemplate;
	
    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	@Override
	public Long findDatosCabecera(VPlanogramasPiladas vPlanogramasPiladas) throws Exception {
    	List<Object> params = new ArrayList<Object>();
    	
    	
    	 
    	StringBuffer query = new StringBuffer(" select COUNT(*) from V_PLANOGRAMAS_PILADAS " +
    								"where cod_centro= ? and cod_art = ? " +
    								"and (? between f_inicio and f_fin " +
    								"or ? between f_inicio and f_fin " +
    								"or f_inicio between ? and ?)");
    
        
        params.add(vPlanogramasPiladas.getCodCentro());
        params.add(vPlanogramasPiladas.getCodArt());
        params.add(vPlanogramasPiladas.getfInicio());
        params.add(vPlanogramasPiladas.getfFin());
        params.add(vPlanogramasPiladas.getfInicio());
        params.add(vPlanogramasPiladas.getfFin());
		
        Long total = null;
		try {
			 total = this.jdbcTemplate.queryForLong(query.toString(),params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}

	    return total;
    }
}
