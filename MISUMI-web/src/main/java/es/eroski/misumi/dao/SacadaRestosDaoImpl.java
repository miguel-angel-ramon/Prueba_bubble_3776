package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.SacadaRestosDao;
import es.eroski.misumi.util.Utilidades;

@Repository
public class SacadaRestosDaoImpl implements SacadaRestosDao {

	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(SacadaRestosDaoImpl.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public Long countGondolaG(Long codCentro, Long codArt, Long anoOferta, Long numeroOferta){
		
		String query ="SELECT COUNT(1) FROM ("
						+ "SELECT * "
						+ "FROM OFERTA "
						+ "WHERE COD_CENTRO = ? "
						+ " AND COD_ART = ? "
						+ " AND ANO_OFERTA = ? "
						+ " AND NUM_OFERTA = ? "
						+ " AND GONDOLA ='G' "
						+ "ORDER BY FECHA_GEN DESC )"
				+ " WHERE ROWNUM=1 ";
 

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		params.add(anoOferta);
		params.add(numeroOferta);

		
		Long cont = null;

		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return Long.valueOf(cont);

	}
}

