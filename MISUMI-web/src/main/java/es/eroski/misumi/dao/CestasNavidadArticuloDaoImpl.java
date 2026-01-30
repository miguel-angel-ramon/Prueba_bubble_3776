package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CestasNavidadArticuloDao;
import es.eroski.misumi.model.CestasNavidadArticulo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CestasNavidadArticuloDaoImpl implements CestasNavidadArticuloDao{
	private static Logger logger = Logger.getLogger(CestasNavidadArticuloDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<CestasNavidadArticulo> findAll(Long codArtLote) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		//Preparamos la SQL
		StringBuffer query = new StringBuffer(" SELECT * FROM  ");
		query.append(" CESTAS_NAVIDAD_ARTICULOS ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		where.append(" AND COD_ART_LOTE = ? ");
		params.add(codArtLote);
		query.append(where);

		order.append(" ORDER BY ID_CESTAS_NAVIDAD_ARTICULO ");
		query.append(order);

		List<CestasNavidadArticulo> cestasNavidadArticuloLista = null;		
		try {	
			cestasNavidadArticuloLista = (List<CestasNavidadArticulo>) this.jdbcTemplate.query(query.toString(),this.rwCestasNavidadArticulo, params.toArray()); 
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return cestasNavidadArticuloLista;
	}

	@Override
	public void deleteCestasNavidadArticulo(Long codArtLote, Long idCestasNavidadArticulo) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		StringBuffer query = new StringBuffer(" DELETE FROM CESTAS_NAVIDAD_ARTICULOS WHERE COD_ART_LOTE = ? ");
		params.add(codArtLote);

		if(idCestasNavidadArticulo != null){
			where.append(" AND ID_CESTAS_NAVIDAD_ARTICULO = ? ");
			params.add(idCestasNavidadArticulo);
		}
		query.append(where);
		try{
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	private RowMapper<CestasNavidadArticulo> rwCestasNavidadArticulo= new RowMapper<CestasNavidadArticulo>() {
		public CestasNavidadArticulo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			CestasNavidadArticulo cestaNavidadArticulo = new CestasNavidadArticulo(
					resultSet.getLong("COD_ART_LOTE"),
					resultSet.getLong("ID_CESTAS_NAVIDAD_ARTICULO"),
					resultSet.getString("TITULO_ARTICULO_LOTE"),
					resultSet.getString("DESCR_ARTICULO_LOTE"));

			return cestaNavidadArticulo;
		}
	};

	@Override
	public int deleteArticuloLote(final List<CestasNavidadArticulo> lstBorrados) {
		// TODO Auto-generated method stub
		try{
			StringBuffer query = new StringBuffer(
					" DELETE FROM CESTAS_NAVIDAD_ARTICULOS WHERE ID_CESTAS_NAVIDAD_ARTICULO = ? ");

			if (lstBorrados != null && lstBorrados.size() > 0) {
				jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						CestasNavidadArticulo articuloLote = lstBorrados.get(i);
						ps.setLong(1, articuloLote.getIdCestasNavidadArticulo());
					}

					@Override
					public int getBatchSize() {
						return lstBorrados.size();
					}
				});
			}
			
			return Constantes.COD_ERROR_OK;
		}catch(Exception e){
			logger.error("deleteArticuloLote="+e.toString());
			e.printStackTrace();
			
			return Constantes.COD_ERROR_DELETE_ARTICULO_LOTE;
		}
	}

	@Override
	public int updateArticuloLote(final List<CestasNavidadArticulo> lstModificados) {
		// TODO Auto-generated method stub
		try{
			StringBuffer query = new StringBuffer(
					" UPDATE CESTAS_NAVIDAD_ARTICULOS SET TITULO_ARTICULO_LOTE = ?, DESCR_ARTICULO_LOTE = ? ");
			query.append(" WHERE ID_CESTAS_NAVIDAD_ARTICULO = ? ");

			if (lstModificados != null && lstModificados.size() > 0) {
				jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						CestasNavidadArticulo articuloLote = lstModificados.get(i);

						ps.setString(1, articuloLote.getTituloArticuloLote());
						ps.setString(2, articuloLote.getDescrArticuloLote());
						ps.setLong(3, articuloLote.getIdCestasNavidadArticulo());
					}

					@Override
					public int getBatchSize() {
						return lstModificados.size();
					}
				});
			}
			
			return Constantes.COD_ERROR_OK;
		}catch(Exception e){
			logger.error("updateArticuloLote="+e.toString());
			e.printStackTrace();
			
			return Constantes.COD_ERROR_UPDATE_ARTICULO_LOTE;
		}
	}

	@Override
	public int newArticuloLote(final List<CestasNavidadArticulo> lstNuevos) {
		// TODO Auto-generated method stub
		try{
			StringBuffer query = new StringBuffer(
					"INSERT INTO CESTAS_NAVIDAD_ARTICULOS (COD_ART_LOTE, TITULO_ARTICULO_LOTE, DESCR_ARTICULO_LOTE) ");
			query.append(" VALUES (?,?,?)");

			if (lstNuevos != null && lstNuevos.size() > 0) {
				jdbcTemplate.batchUpdate(query.toString(), new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						CestasNavidadArticulo articuloLote = lstNuevos.get(i);


						if (null != articuloLote.getCodArtLote()) {
							ps.setLong(1, articuloLote.getCodArtLote());
						} else {
							ps.setNull(1, Types.NUMERIC);
						}

						ps.setString(2, articuloLote.getTituloArticuloLote());
						ps.setString(3, articuloLote.getDescrArticuloLote());
					}

					@Override
					public int getBatchSize() {
						return lstNuevos.size();
					}
				});
			}
			
			return Constantes.COD_ERROR_OK;
		}catch(Exception e){
			logger.error("newArticuloLote="+e.toString());
			e.printStackTrace();
			
			return Constantes.COD_ERROR_INSERT_ARTICULO_LOTE;
		}
	}
}
