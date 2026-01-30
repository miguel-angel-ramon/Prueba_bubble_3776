package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PlataformaDaoSIA;

@Repository
public class PlataformaDaoSIAImpl implements PlataformaDaoSIA{
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}
	
	
	@Override
	public String estaEnModoEmpuje(final Long codCentro, final Long codArt){
		StringBuffer query = new StringBuffer(" SELECT PK_APR_PLAT_ART_FL.F_EST_EMPUJE(?,?) FROM DUAL ");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);

		return (String)this.jdbcTemplate.queryForObject(query.toString(), params.toArray(),String.class);
	}
}
