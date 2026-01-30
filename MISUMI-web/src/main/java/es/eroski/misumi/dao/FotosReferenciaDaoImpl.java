package es.eroski.misumi.dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.FotosReferenciaDao;
import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.util.Utilidades;

@Repository
public class FotosReferenciaDaoImpl implements FotosReferenciaDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	}

	private RowMapper<FotosReferencia> rwFotosReferenciaMap = new RowMapper<FotosReferencia>() {
		public FotosReferencia mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			FotosReferencia fotosReferencia = new FotosReferencia();

			fotosReferencia.setCodReferencia(resultSet.getLong("COD_ARTICULO"));

			OracleLobHandler lobHandler = new OracleLobHandler();
			InputStream inputStream = lobHandler.getBlobAsBinaryStream(resultSet, "FOTO");

			fotosReferencia.setFoto(inputStream);
			return fotosReferencia;
		}

	};
	
	
	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.FotosReferenciaDao#findImage(es.eroski.misumi.model.FotosReferencia)
	 */
	@Override
	public FotosReferencia findImage(FotosReferencia fotosReferencia) throws Exception {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer query = new StringBuffer("SELECT cod_articulo, foto ");
		query.append("FROM cati_carga_fotos_ref "
				   + "WHERE cod_articulo = ? "
					);
		
		params.add(fotosReferencia.getCodReferencia());

		FotosReferencia fotosRef = null;
        try {
        	fotosRef = this.jdbcTemplate.queryForObject(query.toString(), this.rwFotosReferenciaMap, params.toArray());
		} catch (Exception e){			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
        return fotosRef;
	}
	
	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.FotosReferenciaDao#checkImage(es.eroski.misumi.model.FotosReferencia)
	 */
	@Override
	public Long checkImage(FotosReferencia fotosReferencia) throws Exception {
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer query = new StringBuffer(" SELECT COUNT(COD_ARTICULO) ");
		query.append(" FROM CATI_CARGA_FOTOS_REF WHERE COD_ARTICULO = ? ");
		
		params.add(fotosReferencia.getCodReferencia());
		
		Long cont = null;
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return cont;
	}
}
