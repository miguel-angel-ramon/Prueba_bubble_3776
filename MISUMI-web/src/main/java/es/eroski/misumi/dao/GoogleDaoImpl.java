package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.GoogleDao;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class GoogleDaoImpl implements GoogleDao{
	private static Logger logger = Logger.getLogger(GoogleDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<String> rwDireccionMap = new RowMapper<String>() {
		public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return resultSet.getString("DIRECCION");   
		}
	};
	@Override
	public String obtenerDireccionGoogle(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DIRECCION " 
				+ " FROM T_MIS_CENTRO_DIRECCION ");

		where.append(" AND COD_CENTRO = ? ");
		params.add(codCentro);

		query.append(where);
		String direccionGoogle = null;
		try{
			List<String> direccionGoogleLst = (List<String>) this.jdbcTemplate.query(query.toString(),rwDireccionMap,params.toArray());
			if(direccionGoogleLst != null && direccionGoogleLst.size() > 0){
				direccionGoogle = direccionGoogleLst.get(0);
			}
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return direccionGoogle;
	}

	@Override
	public void guardarDireccionGoogle(Long codCentro, String direccionGoogle) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();		

		StringBuffer query = new StringBuffer(" INSERT INTO T_MIS_CENTRO_DIRECCION (COD_CENTRO,DIRECCION) VALUES(?,?) ");

		params.add(codCentro);
		params.add(direccionGoogle);

		try{
			this.jdbcTemplate.update(query.toString(), params.toArray());	

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
}
