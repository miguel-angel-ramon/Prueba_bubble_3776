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

import es.eroski.misumi.dao.iface.CambioGamaDao;
import es.eroski.misumi.model.CambioGama;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CambioGamaDaoImpl implements CambioGamaDao{
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(AlarmasPLUDaoImpl.class);
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	private RowMapper<CambioGama> rowMapperCambioGama = new RowMapper<CambioGama>() {
		public CambioGama mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			final CambioGama out = new CambioGama();
			out.setCodCentro(resultSet.getLong("COD_CENTRO"));
			out.setCodArt(resultSet.getLong("COD_ART"));
			out.setTipo(resultSet.getString("TIPO").trim());
			out.setFechaGen(new java.util.Date(resultSet.getDate("FECHA_GEN").getTime()));
			out.setNumVeces(resultSet.getLong("NUM_VECES"));
			out.setFechaCreacion(new java.util.Date(resultSet.getDate("CREATION_DATE").getTime()));
			out.setFechaActualizacion(new java.util.Date(resultSet.getDate("LAST_UPDATE_DATE").getTime()));
			return out;
		}
	};
	
	@Override
	public CambioGama existeGama(Long codCentro, Long codArt)  throws Exception{ 
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		StringBuffer query = new StringBuffer("SELECT * ");
		query.append(" FROM T_MIS_CAMBIO_GAMA CG ");
		query.append(" WHERE CG.COD_CENTRO = ? ");
		query.append(" AND CG.COD_ART = ? ");
		query.append(" AND CG.FECHA_GEN = TRUNC(SYSDATE) ");
		
		logger.debug("CambioGamaDaoImpl - existeGama - SQL = "+query.toString());
		logger.debug("CambioGamaDaoImpl - existeGama - codCentro = "+codCentro);
		logger.debug("CambioGamaDaoImpl - existeGama - codArt = "+codArt);
		
		List<CambioGama> listaCambioGama =new ArrayList<CambioGama>();
		try {
			listaCambioGama =  this.jdbcTemplate.query(query.toString(),this.rowMapperCambioGama, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return null;
		}
		if (listaCambioGama!=null && listaCambioGama.size()>0){
			return listaCambioGama.get(0);
		}else{
			return null;
		}
		
	}
	
	@Override
	public void updateCambioGama(CambioGama cambioGama, String accion, String usuario)  throws Exception {
		String query ="UPDATE T_MIS_CAMBIO_GAMA SET TIPO = ?, NUM_VECES=?, LAST_UPDATE_DATE = TRUNC(SYSDATE), LAST_UPDATE_BY=? "
				+ " WHERE COD_CENTRO=?"
				+ " AND COD_ART=? "
				+ " AND FECHA_GEN=TRUNC(SYSDATE)";

		List<Object> params = new ArrayList<Object>();
		params.add(accion);
		params.add(cambioGama.getNumVeces()+1);
		params.add(usuario);
		params.add(cambioGama.getCodCentro());
		params.add(cambioGama.getCodArt());
		
		try {
			this.jdbcTemplate.update(query, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	}
	
	@Override
	public int insertarCambioGama(Long codCentro, Long codArt,String accion, String usuario) throws Exception{
		List<Object> params = new ArrayList<Object>();

		String query = "INSERT INTO T_MIS_CAMBIO_GAMA "
				+ " (COD_CENTRO, COD_ART, "
				+ " TIPO, FECHA_GEN, NUM_VECES, "
				+ " CREATION_DATE, LAST_UPDATE_DATE, CREATED_BY, LAST_UPDATE_BY )"
				+ " VALUES(?, ?, ?, TRUNC(SYSDATE), ?, TRUNC(SYSDATE), TRUNC(SYSDATE), ?, ?)";

		params.add(codCentro);
		params.add(codArt);
		params.add(accion);
		params.add(1L);
		params.add(usuario);
		params.add(usuario);
		int result= 0;
		
		try {
			result = this.jdbcTemplate.update(query, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		
		return result;
	}
	
}
