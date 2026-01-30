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

import es.eroski.misumi.dao.iface.VCentrosUsuariosDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VCentrosUsuariosDaoImpl implements VCentrosUsuariosDao{
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<Centro> findByCodDesc(String matcher, String codUser) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" select cod_centro, descrip_centro, negocio, cod_ensena, cod_area, cod_region, cod_zona, descrip_zona, provincia, cod_negocio, cod_soc, flg_cpb_especial, flg_pr_gama_cpb_kosmos  " +
				" from v_mis_centros_usuarios ");
		if (matcher != null) {
			where.append(" and upper(cod_centro||' - '|| descrip_centro) like upper(?) ");
			params.add("%" + matcher+ "%");	

		}  
		if(codUser != null){
			where.append(" and UPPER(COD_USUARIO) = UPPER(?) ");
			params.add(codUser);
		}

		query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, descrip_centro ");
		query.append(order);


		List<Centro> centros = null;		

		try {
			centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return centros;		
	}

	@Override
	public List<Centro> findAll(Centro centro, String codUser) {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" select cod_centro, descrip_centro, negocio, cod_ensena, cod_area, cod_region, cod_zona, descrip_zona, provincia, cod_negocio, cod_soc, flg_cpb_especial, flg_pr_gama_cpb_kosmos " +
				" from v_mis_centros_usuarios ");
		if (centro != null) {
			where.append("WHERE 1=1 ");
			if (centro.getCodCentro() != null &&  centro.getCodCentro().longValue()!=0){
				where.append(" AND cod_centro = ? ");
				params.add(centro.getCodCentro().longValue());	
			}

			if(centro.getCodRegion()!= null){
				where.append(" AND cod_region = ? ");
				params.add(centro.getCodRegion());
			}

			if(centro.getCodZona()!= null){
				where.append(" AND cod_zona = ? ");
				params.add(centro.getCodZona());
			}

			if(centro.getFlgCpbEspecial() != null){
				where.append(" AND flg_cpb_especial = ? ");
				params.add(centro.getFlgCpbEspecial());
			}

			if(centro.getFlgCpbNuevo() != null){
				where.append(" AND flg_pr_gama_cpb_kosmos = ? ");
				params.add(centro.getFlgCpbNuevo());
			}						
		} 

		if(codUser != null){
			where.append(" and UPPER(COD_USUARIO) = UPPER(?) ");
			params.add(codUser);
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, descrip_centro ");
		query.append(order);

		List<Centro> centros = null;		

		try {
			if (params != null && params.size() > 0){
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap, params.toArray()); 
			} else {
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroMap);
			}

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return centros;		
	}

	@Override
	public List<Centro> listZonasByRegion(Centro centro, String codUser) {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" select cod_zona, trim(descrip_zona) descrip_zona" +
				" from v_mis_centros_usuarios ");
		if (centro != null) {    	
			where.append("WHERE 1=1 ");
			if (centro.getCodRegion() != null &&  centro.getCodRegion().longValue()!=0){
				where.append(" AND cod_region = ? ");
				params.add(centro.getCodRegion().longValue());	
			}
		}    

		if(codUser != null){
			where.append(" and UPPER(COD_USUARIO) = UPPER(?) ");
			params.add(codUser);
			
		}
		query.append(where);

		StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		group.append(" group by cod_zona, descrip_zona ");
		query.append(group);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by trim(descrip_zona) ");
		query.append(order);



		List<Centro> centros = null;		

		try {
			if (params != null && params.size() > 0){
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroZonaMap, params.toArray()); 
			} else {
				centros = (List<Centro>) this.jdbcTemplate.query(query.toString(),this.rwCentroZonaMap);
			}

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return centros;		
	}

	private RowMapper<Centro> rwCentroMap = new RowMapper<Centro>() {
		public Centro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Centro(resultSet.getLong("cod_centro"),resultSet.getString("descrip_centro"),
					resultSet.getString("negocio"), resultSet.getLong("cod_ensena"),
					resultSet.getLong("cod_area"), resultSet.getLong("cod_region"), 
					resultSet.getLong("cod_zona"), resultSet.getString("descrip_zona"),
					resultSet.getString("provincia"), resultSet.getLong("cod_negocio"), resultSet.getLong("cod_soc"), resultSet.getLong("flg_cpb_especial"),resultSet.getLong("flg_pr_gama_cpb_kosmos")
					);
		}
	};

	//Para rellenar con la lista de zonas
	private RowMapper<Centro> rwCentroZonaMap = new RowMapper<Centro>() {
		public Centro mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Centro(null, null,
					null, null,
					null, null, 
					resultSet.getLong("cod_zona"), resultSet.getString("descrip_zona"),
					null, null,null
					);
		}
	};
}
