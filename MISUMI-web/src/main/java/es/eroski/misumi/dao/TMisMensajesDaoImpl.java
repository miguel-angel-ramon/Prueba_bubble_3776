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

import es.eroski.misumi.dao.iface.TMisMensajesDao;
import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.util.Utilidades;

@Repository
public class TMisMensajesDaoImpl implements TMisMensajesDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Aviso> rwTMisMensajesMap = new RowMapper<Aviso>() {
		public Aviso mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Aviso aviso = new Aviso();

			aviso.setClaseMensaje(resultSet.getString("CLASE_MENSAJE"));

			aviso.setMensaje(resultSet.getString("MENSAJE"));
			return aviso;
		}

	};

	private RowMapper<Aviso> rwTMisMensajesMap2 = new RowMapper<Aviso>() {
		public Aviso mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Aviso aviso = new Aviso();

			aviso.setClaseMensaje(resultSet.getString("CLASE_MENSAJE"));

			aviso.setMensaje(resultSet.getString("MENSAJE_PDA"));
			return aviso;
		}

	};


	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.TMisMensajesDao#findOne(es.eroski.misumi.model.Aviso)
	 */
	@Override
	public Aviso findOne(Aviso aviso) throws Exception {

		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" SELECT CLASE_MENSAJE,");
		if(aviso.isPda()){
			query.append(" MENSAJE_PDA");
		}else{
			query.append(" MENSAJE ");
		}
		query.append(" FROM T_MIS_MENSAJES WHERE ROWNUM=1 AND COD_CENTRO = ? ");
		query.append(" AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FIN");

		params.add(aviso.getCodCentro());

		Aviso avisoOut = null;
		try {
			if(aviso.isPda()){
				avisoOut = this.jdbcTemplate.queryForObject(query.toString(), this.rwTMisMensajesMap2, params.toArray());
			}else{
				avisoOut =  this.jdbcTemplate.queryForObject(query.toString(), this.rwTMisMensajesMap, params.toArray());	
			}
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return avisoOut;
		
	}

	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.TMisMensajesDao#findCont(es.eroski.misumi.model.Aviso)
	 */
	@Override
	public Long findCont(Aviso aviso) throws Exception {

		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" SELECT COUNT(1) ");
		query.append(" FROM T_MIS_MENSAJES WHERE COD_CENTRO = ? ");
		query.append(" AND SYSDATE BETWEEN FECHA_INICIO AND FECHA_FIN");
		if(aviso.isPda()){
			query.append(" AND MENSAJE_PDA IS NOT NULL");
		}else{
			query.append(" AND MENSAJE IS NOT NULL");
		}

		params.add(aviso.getCodCentro());

		Long cont = null;
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return  cont;
	}

}
