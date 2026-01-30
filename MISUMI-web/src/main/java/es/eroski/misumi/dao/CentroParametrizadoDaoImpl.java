/**
 * 
 */
package es.eroski.misumi.dao;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import es.eroski.misumi.dao.iface.CentroParametrizadoDao;

/**
 * @author BICAGAAN
 *
 */
public abstract class CentroParametrizadoDaoImpl implements CentroParametrizadoDao{

	private NamedParameterJdbcTemplate nameParamJdbcTemplate;
	private static Logger logger = Logger.getLogger(CentroParametrizadoDaoImpl.class);

	@Autowired
	public void setDataSourceNamed(DataSource dataSource) {
		this.nameParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public boolean centroParametrizado(Long codCentro, String permiso) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(1) "
				   + "FROM v_mis_param_centros_opc "
				   + "WHERE cod_loc = :centro "
				   + "AND opc_habil LIKE :permiso");

		String permisoParam= "%" + permiso.trim() + "%";

		MapSqlParameterSource namedParams= new MapSqlParameterSource();
		namedParams.addValue("centro", codCentro);
		namedParams.addValue("permiso", permisoParam);
		
		logger.info("SQL: "+query);
		int count = this.nameParamJdbcTemplate.queryForInt(query.toString(), namedParams);
		return (count > 0);
	}

}
