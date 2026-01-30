package es.eroski.misumi.dao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.Region;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class RelacionArticuloDaoImpl implements RelacionArticuloDao {
	
	 private JdbcTemplate jdbcTemplate;
	
	 private RowMapper<PdaArticulo> rwDatEspecificosTextil = new RowMapper<PdaArticulo>() {
			public PdaArticulo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new PdaArticulo(resultSet.getLong("COD_ART"), resultSet.getString("TEMPORADA"),
			    		resultSet.getString("ESTRUCTURA"), resultSet.getLong("NUM_ORDEN"),
		    			    resultSet.getString("MODELO_PROVEEDOR"),resultSet.getString("DESCR_TALLA"),
		    			    resultSet.getString("DESCR_COLOR"), resultSet.getString("DENOMINACION")	    
				    );
			}

	 };
	 
	 private RowMapper<DetallePedido> rwDatEspecificosTextilPC = new RowMapper<DetallePedido>() {
			public DetallePedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new DetallePedido(resultSet.getLong("COD_ART"), resultSet.getString("TEMPORADA"),
			    		    resultSet.getLong("NUM_ORDEN"),
		    			    resultSet.getString("MODELO_PROVEEDOR"),resultSet.getString("DESCR_TALLA"),
		    			    resultSet.getString("DESCR_COLOR")    
				    );
			}

	 };
	 
	 private RowMapper<RelacionArticulo> rwRelacionArticuloMap = new RowMapper<RelacionArticulo>() {
			public RelacionArticulo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new RelacionArticulo(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_ART"), resultSet.getLong("COD_ART_RELA"),
			    		resultSet.getLong("PROPOR_REF_COMPRA_TRATADA"),resultSet.getLong("PROPOR_REF_VENTA_TRATADA")
				    );
			}
	 };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<Long> findAll(RelacionArticulo relacionArticulo) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	if (relacionArticulo  != null){
	        	if(relacionArticulo.getCodArt()!=null){
	        		query.append("SELECT DISTINCT r.cod_art_rela ");
	        	}
	        	if(relacionArticulo.getCodArtRela()!=null){
	        		 query.append("SELECT DISTINCT r.cod_art ");
	        	}
	        }
	    	
	    	query.append(" FROM relacion_articulo r ");
	    	query.append(" WHERE (r.tipo_rela = 1 or r.tipo_rela = 2 or r.tipo_rela = 3) AND r.a_b_m <> 'B' and ");
			
        	if(relacionArticulo.getCodCentro()!=null){
       		 	query.append("(r.cod_centro is null or r.cod_centro = ?) ");
	        	params.add(relacionArticulo.getCodCentro());	        		
        	}else{
       		 	query.append("r.cod_centro is null ");
        	}

			query.append(" AND r.flg_enviado_pbl = 'S' ");
	    	if (relacionArticulo  != null){
	        	if(relacionArticulo.getCodArt()!=null){
	        		query.append(" AND COD_ART = ? ");
		        	 params.add(relacionArticulo.getCodArt());	        		
	        	}
	        	if(relacionArticulo.getCodArtRela()!=null){
	        		query.append(" AND COD_ART_RELA = ? ");
		        	 params.add(relacionArticulo.getCodArtRela());	        		
	        	}
	        }

			query.append(" AND NOT EXISTS (SELECT 'x' FROM relacion_articulo r2 ");
			query.append(" WHERE r2.cod_art = r.cod_art AND r2.cod_art_rela = r.cod_art_rela and ");

			if(relacionArticulo.getCodCentro()!=null){
       		 	query.append("(r2.cod_centro is null or r2.cod_centro = ?) ");
	        	params.add(relacionArticulo.getCodCentro());	        		
        	}else{
       		 	query.append("r2.cod_centro is null ");
        	}

			query.append(" AND r2.tipo_rela = r.tipo_rela ");
			query.append(" AND r2.flg_enviado_pbl = 'S' ");
			query.append(" AND r2.fecha_gen > r.fecha_gen) ");
	    

			List<Long> vRelacionArticuloLista = null;	

		    try {

		    	vRelacionArticuloLista = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vRelacionArticuloLista;
	    }

	    @Override
	    public List<Long> findRefMismoModeloProveedor(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ART ");
	    	
	    	query.append(" FROM V_DATOS_ESPECIFICOS_TEXTIL E");
	    	query.append(" WHERE (MODELO_PROVEEDOR, NUM_ORDEN) IN ( SELECT MODELO_PROVEEDOR, NUM_ORDEN FROM V_DATOS_ESPECIFICOS_TEXTIL T WHERE T.COD_ART ='"+ codArt + "') AND COD_TALLA IS NOT NULL ");
			

			List<Long> vListaMismoMoldeloProveedor = null;	

		    try {

		    	vListaMismoMoldeloProveedor = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class);
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaMismoMoldeloProveedor;
	    }

	    @Override
	    public List<PdaArticulo> findDatosEspecificosTextil(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT COD_ART, TEMPORADA, SECCION||'/'||CATEGORIA||'/'||SUBCATEGORIA||'/'||SEGMENTO AS ESTRUCTURA, NUM_ORDEN, MODELO_PROVEEDOR, DESCR_TALLA,DESCR_COLOR, DENOMINACION");
	    	
	    	query.append(" FROM V_DATOS_ESPECIFICOS_TEXTIL ");
	    	query.append(" WHERE COD_ART = '"+ codArt + "'");

			
			
			List<PdaArticulo> lista = null;	

		    try {

		    	lista = (List<PdaArticulo>) this.jdbcTemplate.query(query.toString(),this.rwDatEspecificosTextil, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return lista;

		    
	    }
	    
	    @Override
	    public List<DetallePedido> findDatosEspecificosTextilPC(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT COD_ART, TEMPORADA, SECCION||'/'||CATEGORIA||'/'||SUBCATEGORIA||'/'||SEGMENTO AS ESTRUCTURA, NUM_ORDEN, MODELO_PROVEEDOR, DESCR_TALLA,DESCR_COLOR, DENOMINACION");
	    	
	    	query.append(" FROM V_DATOS_ESPECIFICOS_TEXTIL ");
	    	query.append(" WHERE COD_ART = '"+ codArt + "'");

	
			
			List<DetallePedido> lista = null;	

		    try {

		    	lista = (List<DetallePedido>) this.jdbcTemplate.query(query.toString(),this.rwDatEspecificosTextilPC, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return lista;

		    
	    }
	    
	    @Override
	    public List<Long> findLoteReferenciaHija(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ARTICULO_LOTE ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E");
	    	query.append(" WHERE E.COD_ARTICULO_ASOCIADO = ? ");
	    	
	    	params.add(codArt);
			

			List<Long> vListaLoteReferenciaHija = null;	

		    try {

		    	vListaLoteReferenciaHija = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaLoteReferenciaHija;
	    }
	    
	    @Override
	    public List<Long> obtenerHijasLote (Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ARTICULO_ASOCIADO ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E");
	    	query.append(" WHERE E.COD_ARTICULO_LOTE = ? ");
	    	
	    	params.add(codArt);
			

			List<Long> vListaHijasLote = null;	

		    try {

		    	vListaHijasLote = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaHijasLote;
	    }
	    
	    @Override
	    public List<BigInteger> obtenerHijasLoteBI (Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ARTICULO_ASOCIADO ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E");
	    	query.append(" WHERE E.COD_ARTICULO_LOTE = ? ");
	    	
	    	params.add(codArt);
			

			List<BigInteger> vListaHijasLote = null;	
			
			
			try {

				vListaHijasLote = (List<BigInteger>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), BigInteger.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaHijasLote;
	    }
	    
	    
	    @Override
	    public List<Long> esReferenciaLote(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ARTICULO_LOTE  ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E");
	    	query.append(" WHERE E.COD_ARTICULO_LOTE = ? ");
	    	
	    	params.add(codArt);
			

			List<Long> vListaReferenciaLote = null;	

			try {

				vListaReferenciaLote = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaReferenciaLote;
	    }
	    
	   
	    
	    @Override
	    public List<Long> esReferenciaHijaDeLote(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT E.COD_ARTICULO_ASOCIADO  ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL E");
	    	query.append(" WHERE E.COD_ARTICULO_ASOCIADO = ? ");
	    	
	    	params.add(codArt);
			

			List<Long> vListaReferenciaHijaDeLote = null;	
			
			try {

				vListaReferenciaHijaDeLote = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaReferenciaHijaDeLote;
	    }


	    @Override
	    public RelacionArticulo findOneProporciones(RelacionArticulo relacionArticulo) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();

       		query.append("SELECT r.COD_CENTRO, r.COD_ART, r.COD_ART_RELA, DECODE(r.TIPO_RELA, 1, NVL(r.PROPOR_REF_COMPRA,1), 1) PROPOR_REF_COMPRA_TRATADA, DECODE(r.TIPO_RELA, 1, NVL(r.PROPOR_REF_VENTA,1), 1) PROPOR_REF_VENTA_TRATADA ");
	    	
	    	query.append(" FROM relacion_articulo r ");
	    	
	    	query.append(" WHERE 1=1 ");
			
			if (relacionArticulo.getCodCentro() != null) {
				query.append(" AND r.cod_centro = ? ");
				params.add(relacionArticulo.getCodCentro());
			} 
			else {
				query.append("AND r.cod_centro is null ");
			}
			if (relacionArticulo.getCodArt() != null) {
				query.append(" AND r.cod_art = ? ");
				params.add(relacionArticulo.getCodArt());
			}
			if (relacionArticulo.getCodArtRela() != null) {
				query.append(" AND r.cod_art_rela = ? ");
				params.add(relacionArticulo.getCodArtRela());
			}
	    	query.append(" AND r.fecha_gen IN  (SELECT MAX(r2.fecha_gen) FROM relacion_articulo r2 ");
	    	query.append(" 		WHERE r2.cod_centro = r.cod_centro ");
	    	query.append(" 		AND r2.cod_art      = r.cod_art ");
	    	query.append(" 		AND r2.cod_art_rela = r.cod_art_rela ");
	    	query.append(" 		AND r2.tipo_rela    = r.tipo_rela) ");
			
			

			List<RelacionArticulo> vRelacionArticuloLista = null;	
			
			try {

				vRelacionArticuloLista = (List<RelacionArticulo>) this.jdbcTemplate.query(query.toString(),this.rwRelacionArticuloMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

			RelacionArticulo relacionArticuloRes = null;
			if (!vRelacionArticuloLista.isEmpty()){
				relacionArticuloRes = vRelacionArticuloLista.get(0);
			}
		    return relacionArticuloRes;
	    }
	    
	    @Override
	    public List<RelacionArticulo> findAllProporciones(RelacionArticulo relacionArticulo) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();

       		query.append("SELECT COD_CENTRO, COD_ART, COD_ART_RELA, DECODE(TIPO_RELA, 1, NVL(PROPOR_REF_COMPRA,1), 1) PROPOR_REF_COMPRA_TRATADA, DECODE(TIPO_RELA, 1, NVL(PROPOR_REF_VENTA,1), 1) PROPOR_REF_VENTA_TRATADA ");
	    	
	    	query.append(" FROM relacion_articulo ");
	    	
	    	query.append(" WHERE 1=1 ");
			
        	if(relacionArticulo.getCodCentro()!=null){
       		 	query.append(" AND (cod_centro is null or cod_centro = ?) ");
	        	params.add(relacionArticulo.getCodCentro());	        		
        	}else{
       		 	query.append("AND cod_centro is null ");
        	}

	    	if (relacionArticulo  != null){
	        	if(relacionArticulo.getCodArt()!=null){
	        		query.append(" AND COD_ART = ? ");
		        	 params.add(relacionArticulo.getCodArt());	        		
	        	}
	        	if(relacionArticulo.getCodArtRela()!=null){
	        		query.append(" AND COD_ART_RELA = ? ");
		        	 params.add(relacionArticulo.getCodArtRela());	        		
	        	}
	        }

			List<RelacionArticulo> vRelacionArticuloLista = null;	
				
			try {

				vRelacionArticuloLista = (List<RelacionArticulo>) this.jdbcTemplate.query(query.toString(),this.rwRelacionArticuloMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vRelacionArticuloLista;
	    }
	    
	    @Override
	    public List<Long> findRefMismoLote(Long codArt) throws Exception  {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	
	    	
	    	query.append("SELECT v2.cod_articulo_asociado ");
	    	
	    	query.append(" FROM V_REFERENCIAS_LOTE_TEXTIL v1, V_REFERENCIAS_LOTE_TEXTIL v2");
	    	query.append(" WHERE(v1.cod_articulo_lote = ? or v1.cod_articulo_asociado= ?) and v1.cod_articulo_lote = v2.cod_articulo_lote");
			
	    	params.add(codArt);
	    	params.add(codArt);
			List<Long> vListaMismoMoldeloProveedor = null;	

			try {

				vListaMismoMoldeloProveedor = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vListaMismoMoldeloProveedor;
	    }
	    
	    @Override
	    public Long findRefMadrePromocional(Long codArtRela) throws Exception {
	    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    		    	
	    	query.append(" SELECT COD_ART ");	    	
	    	query.append(" FROM RELACION_ARTICULO R ");
	    	query.append(" WHERE COD_ART_RELA = ? ");
	    	query.append(" AND COD_CENTRO IS NULL ");
	    	query.append(" AND R.TIPO_RELA = 3 ");
	    	query.append(" AND R.A_B_M <> 'B' ");
	    	query.append(" AND FECHA_GEN IN (SELECT MAX(FECHA_GEN) FROM RELACION_ARTICULO R2 ");
	    	query.append(" 	WHERE R2.COD_CENTRO IS NULL ");
	    	query.append(" 	AND R.COD_ART_RELA = R2.COD_ART_RELA ");
	    	query.append(" 	AND R.TIPO_RELA = R2.TIPO_RELA ");
	    	query.append(" 	AND R.COD_ART = R2.COD_ART)");
	    	
	    	params.add(codArtRela);
	    	
	    	Long codArtMadre = null;
			try {
				codArtMadre =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());		
			}  catch (EmptyResultDataAccessException e) {
				//Si devuelve 0 filas, devolvemos el null y no pintamos el error.
		        return null;
		    }catch (Exception e){			
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return codArtMadre;
	    }
}
