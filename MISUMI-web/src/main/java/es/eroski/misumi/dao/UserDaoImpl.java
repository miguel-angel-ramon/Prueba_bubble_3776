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

import es.eroski.misumi.dao.iface.UserDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
@Repository
public class UserDaoImpl implements UserDao{
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	// private static Logger logger = Logger.getLogger(UserDaoImpl.class);
	 
	 private RowMapper<User> rwUserMap = new RowMapper<User>() {
		public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new User(
						resultSet.getString("COD_USUARIO"),
						null, 
						null, 
						resultSet.getLong("COD_ROL"),
						new Centro(resultSet.getLong("COD_CENTRO"), null, null, null, null, null, null, null, null, null,null),
						resultSet.getString("FLG_CESTAS"),
						resultSet.getString("FLG_AVISOS"));
		}

	 };
		    
		

	 @Autowired
	 public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }
	 
	 @Override
	 public User find(User user) throws Exception {
		 User usuario=null;
		 List<User> lista = null;
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	StringBuffer query = new StringBuffer(" SELECT cod_usuario, cod_rol, cod_centro, fecha_alta, fecha_baja, flg_cestas, flg_avisos " 
												+ " FROM usuario_rol ");

	    	where.append(" WHERE SYSDATE BETWEEN fecha_alta AND NVL(fecha_baja,TO_DATE('31/12/9999','DD/MM/YYYY')) ");

	    	if (user!=null){
	    		if (user.getCode()!=null){
	    			 where.append(" AND UPPER(cod_usuario) = UPPER(?) ");
		        	 params.add(user.getCode());
	    		}else if(user.getCentro().getCodCentro()!=null){
	    			 where.append(" AND cod_centro = ? "
	    			 		+ "AND cod_rol = 2 ");
		        	 params.add(user.getCentro().getCodCentro());
	    		}
	    	}
	    	query.append(where);
	    	
	    	try {
	    		lista=this.jdbcTemplate.query(query.toString(),this.rwUserMap, params.toArray());
	    		
	    		if(lista != null && lista.size() > 0){
	    			usuario = lista.get(0);
				}
	    		
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return usuario;
	 }

}
