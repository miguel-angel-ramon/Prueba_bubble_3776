package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.USSDaoSIA;

@Repository
public class USSDaoSIAImpl implements USSDaoSIA{
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}
	
	
	@Override
	public String esUSS(final Long codArt, final Long codAgrup){
		StringBuffer query = new StringBuffer(" SELECT PK_APR_MISUMI.IS_USS(?,?) FROM DUAL ");

		List<Object> params = new ArrayList<Object>();
		params.add(codArt);
		params.add(codAgrup);

		return (String)this.jdbcTemplate.queryForObject(query.toString(), params.toArray(),String.class);
	}
}
