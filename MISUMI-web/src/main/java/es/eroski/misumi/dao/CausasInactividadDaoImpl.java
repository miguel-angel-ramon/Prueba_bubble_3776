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

import es.eroski.misumi.dao.iface.CausasInactividadDao;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CausasInactividadDaoImpl implements CausasInactividadDao {
	private JdbcTemplate jdbcTemplate;
	// private static Logger logger =
	// LoggerFactory.getLogger(CentroAutoservicioDaoImpl.class);
	// private static Logger logger =
	// Logger.getLogger(CentroAutoservicioDaoImpl.class);

	private RowMapper<CausaInactividad> rwCausaInactividadMap = new RowMapper<CausaInactividad>() {
		public CausaInactividad mapRow(ResultSet resultSet, int rowNum)
				throws SQLException {
			CausaInactividad causaInactividad = new CausaInactividad();
			causaInactividad.setCodCausa(resultSet.getInt("CODIGO_INACT"));
			causaInactividad.setDescCausa(resultSet.getString("DESCRIPCION"));
			return causaInactividad;
		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.CausasInactividadDao#findAll()
	 */
	@Override
	public List<CausaInactividad> findAll() throws Exception {

		StringBuffer query = new StringBuffer(
				" SELECT CODIGO_INACT, DESCRIPCION ");
		query.append(" FROM CAUSAS_INACTIVIDAD ORDER BY CODIGO_INACT ");

		List<CausaInactividad> lista = null;
		try {
			lista = (List<CausaInactividad>) this.jdbcTemplate.query(
				query.toString(), this.rwCausaInactividadMap);
		
	 	} catch (Exception e){
			
 			Utilidades.mostrarMensajeErrorSQL(query.toString(), null ,e);
 		}
		
		return lista;
	}

	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.CausasInactividadDao#findOne(java.lang.String)
	 */
	@Override
	public CausaInactividad findOne(String descripcion) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
		StringBuffer query = new StringBuffer(
				" SELECT CODIGO_INACT, DESCRIPCION FROM CAUSAS_INACTIVIDAD ");

		if (null != descripcion) {
			where.append(" AND DESCRIPCION = ? ");
			params.add(descripcion);

		}
		query.append(where);

		CausaInactividad causaInactividad = null;
		try {
			causaInactividad = this.jdbcTemplate.queryForObject(query.toString(),
					this.rwCausaInactividadMap, params.toArray());
	    } catch (Exception e){
				
	 		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	 		throw e;
	 	}
		
			return causaInactividad;
	}

	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.CausasInactividadDao#getCausaSequence()
	 */
	@Override
	public Integer getCausaSequence() throws Exception {

		StringBuffer query = new StringBuffer(" SELECT NVL(MAX(CODIGO_INACT),0) FROM CAUSAS_INACTIVIDAD ");

		Integer causas = null;
		try {
			causas = this.jdbcTemplate.queryForInt(query.toString());
		
		} catch (Exception e){
			
	 		Utilidades.mostrarMensajeErrorSQL(query.toString(), null ,e);
	 	}
	
		return causas;
		
	}
	
	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.CausasInactividadDao#insertCausaInactividad(es.eroski.misumi.model.CausaInactividad)
	 */
	@Override
	public void insertCausaInactividad(CausaInactividad causaInactividad){
		
		StringBuffer query = new StringBuffer(" INSERT INTO CAUSAS_INACTIVIDAD(CODIGO_INACT, DESCRIPCION) VALUES(?, ?) ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(causaInactividad.getCodCausa());
		params.add(causaInactividad.getDescCausa());
		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
				
		 		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		 }
		
			
	}
}
