package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.InventarioRotativoGisaeDao;
import es.eroski.misumi.model.InformeHuecos;
import es.eroski.misumi.model.InventarioRotativoGisae;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class InventarioRotativoGisaeDaoImpl implements InventarioRotativoGisaeDao {
	
	private static Logger logger = Logger.getLogger(InventarioRotativoGisaeDaoImpl.class);
	
	 private JdbcTemplate jdbcTemplate;
	 
	 private RowMapper<InventarioRotativoGisae> rwInventarioRotativoGisaeMap = new RowMapper<InventarioRotativoGisae>() {
			public InventarioRotativoGisae mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new InventarioRotativoGisae(resultSet.getLong("COD_CENTRO"), resultSet.getLong("COD_AREA"), resultSet.getString("DENOM_AREA"),
						resultSet.getLong("COD_SECCION"), resultSet.getString("DENOM_SECCION"), resultSet.getLong("COD_ART"), 
						resultSet.getString("FLG_NSR").equals("S") ? true : false,resultSet.getString("TIPO_LISTADO"), resultSet.getDate("FECHA_GEN")
						);
			}
	};
	
	 private RowMapper<InventarioRotativoGisae> rwInventarioRotativoGisaeSeccionMap = new RowMapper<InventarioRotativoGisae>() {
			public InventarioRotativoGisae mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				InventarioRotativoGisae inventarioRotativo = new InventarioRotativoGisae();
				inventarioRotativo.setCodSeccion(resultSet.getLong("COD_SECCION"));
				inventarioRotativo.setDescSeccion(resultSet.getString("DENOM_SECCION"));
				return inventarioRotativo;
			}
	};
	

	 private RowMapper<InformeHuecos> rwInformeHuecosMap = new RowMapper<InformeHuecos>() {
			public InformeHuecos mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			   InformeHuecos informeHuecos = new InformeHuecos();
			   informeHuecos.setCodCentro(resultSet.getLong("COD_CENTRO"));
			   informeHuecos.setCodSeccion(resultSet.getLong("COD_SEC_TRABAJO"));
			   informeHuecos.setDescSeccion(resultSet.getString("DENOM_SEC_TRABAJO"));
			   informeHuecos.setCodPasillo(resultSet.getLong("COD_PASILLO"));
			   informeHuecos.setDescPasillo(resultSet.getString("DENOM_PASILLO"));
			   informeHuecos.setCodArticulo(resultSet.getLong("COD_ART"));
			   informeHuecos.setDescArticulo(resultSet.getString("DESCRIP_ART"));
			   informeHuecos.setMmc(resultSet.getString("MARCA_MAESTRO_CENTRO"));
			   informeHuecos.setCatalogo(resultSet.getString("CATALOGO"));
			   informeHuecos.setTipoIncidencia(resultSet.getInt("TIPO_INCIDENCIA"));
			   informeHuecos.setFechaUltimaVenta(resultSet.getDate("FECHA_ULT_VENTA"));
			   informeHuecos.setOferta(resultSet.getString("OFERTA_VIGENTE"));
			   informeHuecos.setTipoAprov(resultSet.getString("TIPO_APROV"));
			   return informeHuecos;
			}

	 };

	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    

	@Override
    public void insert(InventarioRotativoGisae inventarioRotativoGisae) throws Exception{
    	List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" INSERT INTO INVENTARIO_ROTATIVO_GISAE (COD_CENTRO, COD_AREA, DENOM_AREA, COD_SECCION, ");
		query.append(" DENOM_SECCION, COD_ART, FLG_NSR, TIPO_LISTADO, FECHA_GEN, FLG_TRATADO )  ");
		//query.append(" CREATED_BY, CREATION_DATE, TECLE_CREATION, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN)");
		query.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUNC(?),?) ");
		
		
		params = new ArrayList<Object>();
		
		params.add(inventarioRotativoGisae.getCodCentro());

		params.add(inventarioRotativoGisae.getCodArea());
		params.add(inventarioRotativoGisae.getDescArea());
		params.add(inventarioRotativoGisae.getCodSeccion());
		params.add(inventarioRotativoGisae.getDescSeccion());
		params.add(inventarioRotativoGisae.getCodArticulo());
		params.add((inventarioRotativoGisae.getNsr().booleanValue()) ? "S" : "N");
		params.add(inventarioRotativoGisae.getTipoListado());
		params.add(inventarioRotativoGisae.getFechaGen());
		params.add((inventarioRotativoGisae.getTratado().booleanValue()) ? "S" : "N");
		
		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
    }

	@Override
    public List<InventarioRotativoGisae> findAll(InventarioRotativoGisae inventarioRotativoGisae) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");


    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_AREA, DENOM_AREA, COD_SECCION,  DENOM_SECCION, COD_ART, FLG_NSR, TIPO_LISTADO, FECHA_GEN " +
				  
				  " FROM INVENTARIO_ROTATIVO_GISAE ");
	   	if (inventarioRotativoGisae  != null){
        	if(inventarioRotativoGisae.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativoGisae.getCodCentro());	        		
        	}
        	if(inventarioRotativoGisae.getCodArticulo()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(inventarioRotativoGisae.getCodArticulo());	        		
        	}
        	if(inventarioRotativoGisae.getFechaGen()!=null){
        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
	        	 params.add(inventarioRotativoGisae.getFechaGen());	        		
        	}
		}
        
        query.append(where);
        
        //StringBuffer order = new StringBuffer(3000);

		List<InventarioRotativoGisae> inventarioRotativoLista = null;		
		
		try {
			inventarioRotativoLista = (List<InventarioRotativoGisae>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoGisaeMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return inventarioRotativoLista;
    }
	
	@Override
    public LinkedHashMap<Long, String> findAllSeccion(InventarioRotativoGisae inventarioRotativoGisae) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DISTINCT COD_SECCION,  DENOM_SECCION " +
				  
				  " FROM INVENTARIO_ROTATIVO_GISAE ");

    
	   	if (inventarioRotativoGisae  != null){
        	if(inventarioRotativoGisae.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativoGisae.getCodCentro());	        		
        	}
        	if(inventarioRotativoGisae.getCodArticulo()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(inventarioRotativoGisae.getCodArticulo());	        		
        	}
        	if(inventarioRotativoGisae.getFechaGen()!=null){
        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
	        	 params.add(inventarioRotativoGisae.getFechaGen());	        		
        	}
        	
        	where.append(" AND FLG_TRATADO = 'N' ");
		}
        
        query.append(where);
        
        StringBuffer order = new StringBuffer(3000);
        order.append("ORDER BY COD_SECCION");
        query.append(order);

		List<InventarioRotativoGisae> inventarioRotativoLista = null;
		try {
			inventarioRotativoLista = (List<InventarioRotativoGisae>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoGisaeSeccionMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    LinkedHashMap<Long, String> secciones = new LinkedHashMap<Long, String>();
    	//Opción para mostrado de todas las secciones
    	secciones.put(new Long(-1), "");
    	if (inventarioRotativoLista != null && inventarioRotativoLista.size() > 0){
    		for (InventarioRotativoGisae inventarioRotativo : inventarioRotativoLista) {
    			secciones.put(inventarioRotativo.getCodSeccion(), inventarioRotativo.getCodSeccion()+"-"+inventarioRotativo.getDescSeccion());
			}
    	}

    	return secciones;
    }
	
	@Override
	public void updateInventarioATratado(InventarioRotativoGisae inventarioRotativoGisae) throws Exception{
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer query = new StringBuffer(" UPDATE INVENTARIO_ROTATIVO_GISAE SET FLG_TRATADO = ? ");

		query.append("WHERE 1=1 ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		

		
		params.add((inventarioRotativoGisae.getTratado().booleanValue()) ? "S" : "N");
		
	   	if (inventarioRotativoGisae  != null){
        	if(inventarioRotativoGisae.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativoGisae.getCodCentro());	        		
        	}
        	if(inventarioRotativoGisae.getCodArticulo()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(inventarioRotativoGisae.getCodArticulo());	        		
        	}
        	if(inventarioRotativoGisae.getFechaGen()!=null){
        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
	        	 params.add(inventarioRotativoGisae.getFechaGen());	        		
        	}
		}
			
		query.append(where);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
	}
	
	@Override
	public Long getInformeHuecosCount(Long codCentro) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COUNT(G.COD_ART) FROM INVENTARIO_ROTATIVO_GISAE G, V_SURTIDO_TIENDA S, V_INFORME_HUECOS I ");
		query.append("WHERE G.COD_CENTRO = ? AND G.FECHA_GEN = TRUNC(?) ");
		query.append("AND G.COD_CENTRO = S.COD_CENTRO AND G.COD_ART = S.COD_ART ");
		query.append("AND G.FLG_TRATADO = 'S' AND S.PEDIR = 'N' ");
		query.append("AND I.COD_CENTRO = G.COD_CENTRO AND G.COD_ART = I.COD_ART");
		params.add(codCentro);
		params.add(new Date());
		
	
		Long cont = null;
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return cont;
		
	}
	
	@Override
	public List<InformeHuecos> getInformeHuecos(Long codCentro) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT I.COD_CENTRO, I.COD_SEC_TRABAJO, I.DENOM_SEC_TRABAJO, I.COD_PASILLO, I.DENOM_PASILLO, ");
		query.append("I.COD_ART, I.DESCRIP_ART, I.CATALOGO, I.MARCA_MAESTRO_CENTRO, I.TIPO_INCIDENCIA,  I.TIPO_APROV, I.OFERTA_VIGENTE, I.FECHA_ULT_VENTA ");
		query.append("FROM INVENTARIO_ROTATIVO_GISAE G, V_SURTIDO_TIENDA S, V_INFORME_HUECOS I ");
		query.append("WHERE G.COD_CENTRO = ? AND G.FECHA_GEN = TRUNC(?) ");
		query.append("AND G.COD_CENTRO = S.COD_CENTRO AND G.COD_ART = S.COD_ART ");
		query.append("AND G.FLG_TRATADO = 'S' AND S.PEDIR = 'N' ");
		query.append("AND I.COD_CENTRO = G.COD_CENTRO AND G.COD_ART = I.COD_ART");
		params.add(codCentro);
		params.add(new Date());
		
	
		
		List<InformeHuecos> lista = null;
		try {
			lista = this.jdbcTemplate.query(query.toString(), this.rwInformeHuecosMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}
    
    
}
