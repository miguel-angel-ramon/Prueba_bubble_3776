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

import es.eroski.misumi.dao.iface.VMapaReferenciaDao;
import es.eroski.misumi.model.VMapaReferencia;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VMapaReferenciaDaoImpl implements VMapaReferenciaDao{
	
	private JdbcTemplate jdbcTemplate;

	private RowMapper<VMapaReferencia> rwVMapaReferenciaMap = new RowMapper<VMapaReferencia>() {
			public VMapaReferencia mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VMapaReferencia( resultSet.getLong("cod_mapa")
			    						  , resultSet.getString("desc_mapa")
			    						  , resultSet.getString("bloqueo")
			    						  );
			}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public VMapaReferencia findMapa(Long codArt) throws Exception {

		List<Object> params = new ArrayList<Object>();
		params.add(codArt);
		String query = "SELECT cod_mapa, TRIM(desc_mapa) desc_mapa, NVL(bloqueo,'') bloqueo "
					 + "FROM t_mis_mapas_vegalsa "
					 + "WHERE cod_art = ?";
		
		List<VMapaReferencia> mapa = null;
		try {
			mapa = (List<VMapaReferencia>) this.jdbcTemplate.query(query, this.rwVMapaReferenciaMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, null, e);
		}

		return mapa.get(0);
	} 
	    
	@Override
	public boolean existsMapa(Long codArt) {

		List<Object> params = new ArrayList<Object>();
		params.add(codArt);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(1) "
				   + "FROM t_mis_mapas_vegalsa "
				   + "WHERE cod_art = ?"
				    );
		
		int count = jdbcTemplate.queryForObject(query.toString(), params.toArray(), Integer.class);
		return (count > 0);
		
	} 

}
