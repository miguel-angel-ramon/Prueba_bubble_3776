package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PkAprMisumiImcDao;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PkAprMisumiImcDaoImpl implements PkAprMisumiImcDao {

	 private JdbcTemplate jdbcTemplate;
	
	 @Autowired
	 public void setDataSource(DataSource dataSourceSIA) {
		 this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	 }

	 /**
	  * <code>
	  * f_apr_pc_peso_variable  ( p_cod_articulo      IN  articulos.cod_articulo%type
                                     ) RETURN VARCHAR2
        </code>
	  */
	public String consultarPcPesoVariable(Long codArticulo){
		String query = " SELECT PK_APR_MISUMI_IMC.f_apr_pc_peso_variable(?) FROM DUAL";

		List<Object> params = new ArrayList<Object>();
		params.add(codArticulo);

		String salida = null;
		try{

			salida = this.jdbcTemplate.queryForObject(query, params.toArray(), String.class);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL("PK_APR_MISUMI_IMC.f_apr_pc_peso_variable(?)", Arrays.asList((Object) codArticulo),e);
		}
		return salida;
	}
	

	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public String obtenerMetodosBoton(Long codCentro) {
		 SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		 .withCatalogName("PK_APR_MISUMI_IMC")
		 .withFunctionName("f_obtener_metodos_boton");
		 SqlParameterSource paramMap = new MapSqlParameterSource()
				 .addValue("p_cod_loc", codCentro);
		 return jdbcCall.executeFunction(String.class, paramMap);
	}

}
