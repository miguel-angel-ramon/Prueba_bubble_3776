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

import es.eroski.misumi.dao.iface.VMisDetalladoEurosDao;
import es.eroski.misumi.model.DetalladoEuros;
import es.eroski.misumi.model.VMisDetalladoEuros;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VMisDetalladoEurosDaoImpl implements VMisDetalladoEurosDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<DetalladoEuros> rwDetalladoEurosMap = new RowMapper<DetalladoEuros>() {
		public DetalladoEuros mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new DetalladoEuros(
					resultSet.getLong("IDENT"),
					resultSet.getLong("NIVEL"),
					resultSet.getLong("IDENT"),
					resultSet.getLong("PARENTIDENT"),
					resultSet.getLong("COD_CENTRO"),
					resultSet.getString("IDSESION"),
					resultSet.getLong("GRUPO1"),
					resultSet.getLong("GRUPO2"),
					resultSet.getLong("GRUPO3"),
					resultSet.getLong("GRUPO4"),
					resultSet.getLong("GRUPO5"),
					resultSet.getString("DESCRIPCION"),
					resultSet.getLong("PRECIO_COSTO_INICIAL"),
					resultSet.getLong("CAJAS_INICIAL"),
					resultSet.getLong("PRECIO_COSTO_FINAL"),
					resultSet.getLong("CAJAS_FINALES"),
					resultSet.getLong("COD_ART"),
					resultSet.getString("TIPO"),
					resultSet.getInt("NIVEL"),
					resultSet.getString("PARENTIDENT"),
		    		(resultSet.getInt("NIVEL") > 4),
		    		false,
		    		false,
		    		resultSet.getString("FECHA"),
		    		resultSet.getString("AREA"),
		    		resultSet.getString("SECCION"),
		    		resultSet.getString("CATEGORIA"),
		    		resultSet.getString("SUBCATEGORIA"),
		    		resultSet.getString("SEGMENTO"),
		    		resultSet.getString("ESTADO")
					);
		}
	};
	

	@Override
	public List<DetalladoEuros> findAllReferenciasDetalladoEuros(VMisDetalladoEuros vMisDetalladoEuros, List<Long> listaReferencias)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT NIVEL, IDENT, PARENTIDENT, COD_CENTRO, IDSESION, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5,"
				+ " DESCRIPCION, PRECIO_COSTO_INICIAL, CAJAS_INICIAL, PRECIO_COSTO_FINAL, CAJAS_FINALES, "
				+ " COD_ART, TIPO, FECHA, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, ESTADO "
				+ " FROM V_MIS_DETALLADO_EUROS EUROS");

		if (vMisDetalladoEuros  != null){
			if(vMisDetalladoEuros.getNivel()!=null){
				where.append(" AND NIVEL = ? ");
				params.add(vMisDetalladoEuros.getNivel());	        		
			}
			if(vMisDetalladoEuros.getIdent()!=null){
				where.append(" AND IDENT = ? ");
				params.add(vMisDetalladoEuros.getIdent());	        		
			}
			if(vMisDetalladoEuros.getParentident()!=null){
				where.append(" AND PARENTIDENT = ? ");
				params.add(vMisDetalladoEuros.getParentident());	        		
			}
			if(vMisDetalladoEuros.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(vMisDetalladoEuros.getCodCentro());	        		
			}
			if(vMisDetalladoEuros.getIdSesion()!=null){
				where.append(" AND IDSESION = ? ");
				params.add(vMisDetalladoEuros.getIdSesion());	        		
			}
			if(vMisDetalladoEuros.getGrupo1()!=null){
				where.append(" AND GRUPO1 = ? ");
				params.add(vMisDetalladoEuros.getGrupo1());	        		
			}
			if(vMisDetalladoEuros.getGrupo2()!=null){
				where.append(" AND GRUPO2 = ? ");
				params.add(vMisDetalladoEuros.getGrupo2());	        		
			}
			if(vMisDetalladoEuros.getGrupo3()!=null){
				where.append(" AND GRUPO3 = ? ");
				params.add(vMisDetalladoEuros.getGrupo3());	        		
			}
			if(vMisDetalladoEuros.getDescripcion()!=null){
				where.append(" AND DESCRIPCION = upper(?) ");
				params.add(vMisDetalladoEuros.getDescripcion());	        		
			}
			
			if(vMisDetalladoEuros.getMapa()!=null && vMisDetalladoEuros.getMapa()!=0){
				where.append(" AND COD_MAPA = ? ");
				params.add(vMisDetalladoEuros.getMapa());	      
			}

			if(vMisDetalladoEuros.getCodArt()!=null){
				StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
				referencias.append(vMisDetalladoEuros.getCodArt());
				if (null != listaReferencias){
					for(Long referencia : listaReferencias){
						referencias.append(", ").append(referencia);
					}
				}
				where.append(" AND COD_ART IN ( ").append(referencias).append(" )");      		
			}
			if(vMisDetalladoEuros.getTipo()!=null){
				where.append(" AND TIPO = ? ");
				params.add(vMisDetalladoEuros.getTipo());	        		
			}
		}

		query.append(where);

		/*StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" group by nivel, ident, parentident, cod_centro ");
		query.append(groupBy);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by MAX(fecha_previs_ent), MAX(grupo1), MAX(grupo2), MAX(grupo3) ");
		query.append(order);*/

		List<DetalladoEuros> listaDetalladoEuros = null;			
		try {
			listaDetalladoEuros = (List<DetalladoEuros> ) this.jdbcTemplate.query(query.toString(),this.rwDetalladoEurosMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		return listaDetalladoEuros;
	}

}
