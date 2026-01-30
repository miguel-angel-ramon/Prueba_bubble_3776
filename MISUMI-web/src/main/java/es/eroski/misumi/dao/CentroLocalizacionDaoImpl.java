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

import es.eroski.misumi.dao.iface.CentroLocalizacionDao;
import es.eroski.misumi.model.DireccionCentro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CentroLocalizacionDaoImpl implements CentroLocalizacionDao{
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(QueHacerRefDaoImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
    private RowMapper<DireccionCentro> rwDireccionCentroMap = new RowMapper<DireccionCentro>() {
		public DireccionCentro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DireccionCentro(
					null,
					resultSet.getString("LATITUD"),
					resultSet.getString("LONGITUD")
				);
		}
	};
	
	@Override
	public DireccionCentro obtenerCentroLocalizacion(User user) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");

		//StringBuffer query = new StringBuffer(" SELECT * FROM DIRECCIONES ");
		StringBuffer query = new StringBuffer(" SELECT LONGITUD,LATITUD FROM LOC_DAT_NEG ");
		
		where.append(" AND COD_CENTRO = ? ");
		query.append(where);
		
		params.add(user.getCentro().getCodCentro());
		
		DireccionCentro direccionCentro = null;
		try {
			List<DireccionCentro> direccionCentroLst =  (List<DireccionCentro>) this.jdbcTemplate.query(query.toString(), this.rwDireccionCentroMap, params.toArray());
			if(direccionCentroLst != null && direccionCentroLst.size() > 0){
				direccionCentro = direccionCentroLst.get(0);
			}
		} catch (Exception e){			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return direccionCentro;
	}
}
