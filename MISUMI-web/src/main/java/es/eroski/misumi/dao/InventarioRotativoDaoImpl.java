package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.InventarioRotativoDao;
import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.VAgruComerRef;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class InventarioRotativoDaoImpl implements InventarioRotativoDao{
	
	private static Logger logger = Logger.getLogger(InventarioRotativoDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	 
	private RowMapper<InventarioRotativo> rwInventarioRotativoMap = new RowMapper<InventarioRotativo>() {
			public InventarioRotativo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				return new InventarioRotativo(resultSet.getLong("COD_CENTRO"), resultSet.getString("COD_MAC"), resultSet.getLong("COD_AREA"),
						resultSet.getLong("COD_SECCION"), resultSet.getLong("COD_ARTICULO"), resultSet.getString("FLG_UNICA"),
						resultSet.getLong("COD_ARTICULO_RELA"), resultSet.getString("FLG_STOCKPRINCIPAL"),
						resultSet.getString("FLG_NO_GUARDAR"), resultSet.getDouble("CAMARA_STOCK"), resultSet.getLong("CAMARA_BANDEJA"),
						resultSet.getDouble("SALA_STOCK"), resultSet.getLong("SALA_BANDEJA"), resultSet.getLong("PROPOR_REF_COMPRA"),
						resultSet.getLong("PROPOR_REF_VENTA"), resultSet.getString("FLG_VARIAS_UNITARIAS"), resultSet.getString("AVISO"),
						resultSet.getString("ERROR"), resultSet.getString("CREATED_BY"), resultSet.getDate("CREATION_DATE"),
						resultSet.getLong("TECLE_CREATION"), resultSet.getString("LAST_UPDATED_BY"), resultSet.getDate("LAST_UPDATE_DATE"),
						resultSet.getLong("LAST_UPDATE_LOGIN"), resultSet.getLong("TECLE"), resultSet.getLong("TCN"));
			}
	};
	
	 private RowMapper<PdaDatosInventarioLibre> rwInventarioRotativoPdaMap = new RowMapper<PdaDatosInventarioLibre>() {
			public PdaDatosInventarioLibre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre(resultSet.getLong("COD_CENTRO"), resultSet.getString("COD_MAC"), resultSet.getLong("COD_AREA"),
						resultSet.getLong("COD_SECCION"), resultSet.getLong("COD_ARTICULO"), resultSet.getString("FLG_UNICA"),
						resultSet.getLong("COD_ARTICULO_RELA"), resultSet.getString("FLG_STOCKPRINCIPAL"),
						resultSet.getString("FLG_NO_GUARDAR"), resultSet.getString("CAMARA_STOCK"), resultSet.getString("CAMARA_BANDEJA"),
						resultSet.getString("SALA_STOCK"), resultSet.getString("SALA_BANDEJA"), resultSet.getLong("PROPOR_REF_COMPRA"),
						resultSet.getLong("PROPOR_REF_VENTA"), resultSet.getString("FLG_VARIAS_UNITARIAS"), resultSet.getString("AVISO"),
						resultSet.getString("ERROR"), resultSet.getString("CREATED_BY"), resultSet.getDate("CREATION_DATE"),
						resultSet.getLong("TECLE_CREATION"), resultSet.getString("LAST_UPDATED_BY"), resultSet.getDate("LAST_UPDATE_DATE"),
						resultSet.getLong("LAST_UPDATE_LOGIN"), resultSet.getLong("TECLE"), resultSet.getLong("TCN"));
				pdaDatosInventarioLibre.setDescArt(resultSet.getString("DESCRIP_ART"));
				
				return pdaDatosInventarioLibre;
			}
	};
	 private RowMapper<PdaDatosInventarioLibre> rwInventarioRotativoPdaGISAEMap = new RowMapper<PdaDatosInventarioLibre>() {
			public PdaDatosInventarioLibre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				PdaDatosInventarioLibre pdaDatosInventarioLibre = new PdaDatosInventarioLibre(resultSet.getLong("COD_CENTRO"), "GISAE", resultSet.getLong("COD_AREA"),
						resultSet.getLong("COD_SECCION"), resultSet.getLong("COD_ART"), resultSet.getString("FLG_UNICA"),
						resultSet.getLong("COD_ART"), resultSet.getString("FLG_STOCKPRINCIPAL"),
						resultSet.getString("FLG_NO_GUARDAR"), resultSet.getString("CAMARA_STOCK"), resultSet.getString("CAMARA_BANDEJA"),
						resultSet.getString("SALA_STOCK"), resultSet.getString("SALA_BANDEJA"), resultSet.getLong("PROPOR_REF_COMPRA"),
						resultSet.getLong("PROPOR_REF_VENTA"), resultSet.getString("FLG_VARIAS_UNITARIAS"), resultSet.getString("AVISO"),
						resultSet.getString("ERROR"), resultSet.getString("CREATED_BY"), resultSet.getDate("CREATION_DATE"),
						resultSet.getLong("TECLE_CREATION"), resultSet.getString("LAST_UPDATED_BY"), resultSet.getDate("LAST_UPDATE_DATE"),
						resultSet.getLong("LAST_UPDATE_LOGIN"), resultSet.getLong("TECLE"), resultSet.getLong("TCN"));
				pdaDatosInventarioLibre.setDescArt(resultSet.getString("DESCRIP_ART"));
				pdaDatosInventarioLibre.setOrigenGISAE("SI");
				return pdaDatosInventarioLibre;
			}
	};
	
	 private RowMapper<VAgruComerRef> rwAgruComerRefMap = new RowMapper<VAgruComerRef>() {
			public VAgruComerRef mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VAgruComerRef(resultSet.getString("NIVEL"),resultSet.getLong("GRUPO1"),
			    		    resultSet.getLong("GRUPO2"), resultSet.getLong("GRUPO3"),
			    		    resultSet.getLong("GRUPO4"),resultSet.getLong("GRUPO5"),
			    		    resultSet.getString("DESCRIPCION").trim()
				    );
			}

     };

	 private RowMapper<Long> rwInventarioRotativoPdaCountMap = new RowMapper<Long>() {
			public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return resultSet.getLong(1);
			}

	 };

	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public void insert(InventarioRotativo inventarioRotativo) throws Exception{
    	List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" INSERT INTO INVENTARIO_ROTATIVO (COD_CENTRO, COD_MAC, COD_AREA, COD_SECCION, ");
		query.append(" COD_ARTICULO, FLG_UNICA, COD_ARTICULO_RELA, FLG_STOCKPRINCIPAL, FLG_NO_GUARDAR, CAMARA_STOCK, CAMARA_BANDEJA,  ");
		query.append(" SALA_STOCK, SALA_BANDEJA, PROPOR_REF_COMPRA,  PROPOR_REF_VENTA, FLG_VARIAS_UNITARIAS,  AVISO, ERROR) ");
		//query.append(" CREATED_BY, CREATION_DATE, TECLE_CREATION, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN)");
		query.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		
		
		params = new ArrayList<Object>();
		
		params.add(inventarioRotativo.getCodCentro());
		params.add(inventarioRotativo.getCodMac());
		params.add(inventarioRotativo.getCodArea());
		params.add(inventarioRotativo.getCodSeccion());
		params.add(inventarioRotativo.getCodArticulo());
		params.add(inventarioRotativo.getFlgUnica());
		params.add(inventarioRotativo.getCodArticuloRela());
		params.add(inventarioRotativo.getFlgStockPrincipal());
		params.add(inventarioRotativo.getFlgNoGuardar());
		params.add(inventarioRotativo.getCamaraStock());
		params.add(inventarioRotativo.getCamaraBandeja());
		params.add(inventarioRotativo.getSalaStock());
		params.add(inventarioRotativo.getSalaBandeja());
		params.add(inventarioRotativo.getProporRefCompra());
		params.add(inventarioRotativo.getProporRefVenta());
		params.add(inventarioRotativo.getFlgVariasUnitarias());
		params.add(inventarioRotativo.getAviso());
		params.add(inventarioRotativo.getError());
		/*params.add(inventarioRotativo.getCreatedBy());
		params.add(inventarioRotativo.getCreationDate());
		params.add(inventarioRotativo.getTecleCreation());
		params.add(inventarioRotativo.getLastUpdatedBy());
		params.add(inventarioRotativo.getLastUpdateDate());
		params.add(inventarioRotativo.getLastUpdateLogin());
		params.add(inventarioRotativo.getTecle());
		params.add(inventarioRotativo.getTcn());*/
		
		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
    }

    @Override
	public void delete(InventarioRotativo inventarioRotativo) throws Exception {
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer query = new StringBuffer(" DELETE FROM INVENTARIO_ROTATIVO ");
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE 1=1 ");
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
        	}
        	if(inventarioRotativo.getCodSeccion()!=null && !(new Long(-1).equals(inventarioRotativo.getCodSeccion()))){
      		 	 where.append(" AND COD_SECCION = ? ");
	        	 params.add(inventarioRotativo.getCodSeccion());	        		
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
	public void deleteGuardados(InventarioRotativo inventarioRotativo) throws Exception {
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer query = new StringBuffer(" DELETE FROM INVENTARIO_ROTATIVO ");
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE AVISO IS NOT NULL ");
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
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
	public void deleteRefSinRelaciones(InventarioRotativo inventarioRotativo) throws Exception {
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer query = new StringBuffer(" DELETE FROM INVENTARIO_ROTATIVO a ");
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE 1=1 ");
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		where.append(" AND a.COD_CENTRO = ? ");
	        	params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodMac()!=null){
        		where.append(" AND a.COD_MAC = ? ");
	        	params.add(inventarioRotativo.getCodMac());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		where.append(" AND ");
        		where.append(" ( ");
        		where.append("   a.COD_ARTICULO = ? ");
        		where.append("   OR ");
        		where.append("   a.COD_ARTICULO_RELA = ? ");
        		where.append(" ) ");
        		params.add(inventarioRotativo.getCodArticulo());	        		
        		params.add(inventarioRotativo.getCodArticulo());	        		
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
	public void deleteUnitario(InventarioRotativo inventarioRotativo) throws Exception {
		List<Object> params = new ArrayList<Object>();
	
		StringBuffer query = new StringBuffer(" DELETE FROM INVENTARIO_ROTATIVO a ");
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE 1=1 ");
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		where.append(" AND a.COD_CENTRO = ? ");
	        	params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodMac()!=null){
        		where.append(" AND a.COD_MAC = ? ");
	        	params.add(inventarioRotativo.getCodMac());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		where.append(" AND ");
        		where.append(" ( ");
        		where.append("   (a.COD_ARTICULO = ?) ");
        		where.append("   OR ");
        		where.append("   (a.COD_ARTICULO_RELA = ? AND NOT EXISTS (SELECT 'X' FROM INVENTARIO_ROTATIVO b WHERE b.COD_ARTICULO = b.COD_ARTICULO_RELA AND b.COD_ARTICULO = a.COD_ARTICULO)) ");
        		where.append(" ) ");
        		params.add(inventarioRotativo.getCodArticulo());	        		
        		params.add(inventarioRotativo.getCodArticulo());	        		
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
	public void update(InventarioRotativo inventarioRotativo) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE INVENTARIO_ROTATIVO SET COD_CENTRO = ?, COD_MAC = ?, COD_AREA = ?, COD_SECCION = ?, ");
		query.append(" COD_ARTICULO = ?, FLG_UNICA = ?, COD_ARTICULO_RELA = ?, FLG_STOCKPRINCIPAL = ?, FLG_NO_GUARDAR = ?, CAMARA_STOCK = ?, CAMARA_BANDEJA = ?,  ");
		query.append(" SALA_STOCK = ?, SALA_BANDEJA = ?, PROPOR_REF_COMPRA = ?,  PROPOR_REF_VENTA = ?, FLG_VARIAS_UNITARIAS = ?,  AVISO = ?, ERROR = ? ");

		query.append("WHERE 1=1 ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		

		params.add(inventarioRotativo.getCodCentro());
		params.add(inventarioRotativo.getCodMac());
		params.add(inventarioRotativo.getCodArea());
		params.add(inventarioRotativo.getCodSeccion());
		params.add(inventarioRotativo.getCodArticulo());
		params.add(inventarioRotativo.getFlgUnica());
		params.add(inventarioRotativo.getCodArticuloRela());
		params.add(inventarioRotativo.getFlgStockPrincipal());
		params.add(inventarioRotativo.getFlgNoGuardar());
		params.add(inventarioRotativo.getCamaraStock());
		params.add(inventarioRotativo.getCamaraBandeja());
		params.add(inventarioRotativo.getSalaStock());
		params.add(inventarioRotativo.getSalaBandeja());
		params.add(inventarioRotativo.getProporRefCompra());
		params.add(inventarioRotativo.getProporRefVenta());
		params.add(inventarioRotativo.getFlgVariasUnitarias());
		params.add(inventarioRotativo.getAviso());
		params.add(inventarioRotativo.getError());
		/*params.add(inventarioRotativo.getCreatedBy());
		params.add(inventarioRotativo.getCreationDate());
		params.add(inventarioRotativo.getTecleCreation());
		params.add(inventarioRotativo.getLastUpdatedBy());
		params.add(inventarioRotativo.getLastUpdateDate());
		params.add(inventarioRotativo.getLastUpdateLogin());
		params.add(inventarioRotativo.getTecle());
		params.add(inventarioRotativo.getTcn());*/
		
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
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
	public void updateErrorAvisoArticulo(InventarioRotativo inventarioRotativo) throws Exception{
		List<Object> params = new ArrayList<Object>();

		if (inventarioRotativo != null && 
			((inventarioRotativo.getAviso()!=null && "".equals(inventarioRotativo.getAviso())) || (inventarioRotativo.getError()!=null && "".equals(inventarioRotativo.getError())))){
		
			StringBuffer query = new StringBuffer(" UPDATE INVENTARIO_ROTATIVO SET AVISO = ?, ERROR = ? ");
			query.append("WHERE 1=1 ");

			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			
			params.add(inventarioRotativo.getAviso());
			params.add(inventarioRotativo.getError());
			
			if (inventarioRotativo  != null){
	        	if(inventarioRotativo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(inventarioRotativo.getCodCentro());	        		
	        	}
	        	if(inventarioRotativo.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(inventarioRotativo.getCodArticulo());	        		
	        	}
				if(inventarioRotativo.getCodArticuloRela()!=null){
					where.append(" AND COD_ARTICULO_RELA = ? ");
					params.add(inventarioRotativo.getCodArticuloRela());	        		
				}
	        	if(inventarioRotativo.getCodMac()!=null){
	        		 where.append(" AND COD_MAC = ? ");
		        	 params.add(inventarioRotativo.getCodMac());	        		
	        	}
			}
				
			query.append(where);
			
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}
	}

    @Override
    public List<InventarioRotativo> findAll(InventarioRotativo inventarioRotativo) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_MAC, COD_AREA, COD_SECCION, COD_ARTICULO, FLG_UNICA, COD_ARTICULO_RELA," +
    										  " FLG_STOCKPRINCIPAL, FLG_NO_GUARDAR, CAMARA_STOCK, CAMARA_BANDEJA, SALA_STOCK, SALA_BANDEJA, " +
    										  " PROPOR_REF_COMPRA, PROPOR_REF_VENTA, FLG_VARIAS_UNITARIAS, AVISO, ERROR, CREATED_BY," +
    										  " CREATION_DATE, TECLE_CREATION, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN " +
    										  
    										  " FROM INVENTARIO_ROTATIVO ");
    
	   	if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
        	}
		}
        
        query.append(where);
        
        //StringBuffer order = new StringBuffer(3000);

		List<InventarioRotativo> inventarioRotativoLista = null;		
		
		try {
			inventarioRotativoLista = (List<InventarioRotativo>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return inventarioRotativoLista;
    }
    
    @Override
    public List<InventarioRotativo> findAllPaginate(InventarioRotativo inventarioRotativo, Pagination pagination) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_MAC, COD_AREA, COD_SECCION, COD_ARTICULO, FLG_UNICA, COD_ARTICULO_RELA," +
				  " FLG_STOCKPRINCIPAL, FLG_NO_GUARDAR, CAMARA_STOCK, CAMARA_BANDEJA, SALA_STOCK, SALA_BANDEJA, " +
				  " PROPOR_REF_COMPRA, PROPOR_REF_VENTA, FLG_VARIAS_UNITARIAS, AVISO, ERROR, CREATED_BY," +
				  " CREATION_DATE, TECLE_CREATION, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN " +
				  
				  " FROM INVENTARIO_ROTATIVO ");

		if (inventarioRotativo  != null){
			if(inventarioRotativo.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(inventarioRotativo.getCodCentro());	        		
			}
			if(inventarioRotativo.getCodArticulo()!=null){
				where.append(" AND COD_ARTICULO = ? ");
				params.add(inventarioRotativo.getCodArticulo());	        		
			}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
			if(inventarioRotativo.getCodMac()!=null){
				where.append(" AND COD_MAC = ? ");
				params.add(inventarioRotativo.getCodMac());	  
			}
		}
        
        query.append(where);
        
        /*StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedFieldSort(pagination.getSort()) + " "+ pagination.getAscDsc());
				query.append(order);
			}
		}*/ 
		
		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}

		List<InventarioRotativo> inventarioRotativoLista = null;		
		
		try {
			inventarioRotativoLista = (List<InventarioRotativo>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return inventarioRotativoLista;
    }

	@Override
	public InventarioRotativo findOne(InventarioRotativo inventarioRotativo) throws Exception {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT * FROM ( " +
    			  " SELECT COD_CENTRO, COD_MAC, COD_AREA, COD_SECCION, COD_ARTICULO, FLG_UNICA, COD_ARTICULO_RELA," +
				  " FLG_STOCKPRINCIPAL, FLG_NO_GUARDAR, CAMARA_STOCK, CAMARA_BANDEJA, SALA_STOCK, SALA_BANDEJA, " +
				  " PROPOR_REF_COMPRA, PROPOR_REF_VENTA, FLG_VARIAS_UNITARIAS, AVISO, ERROR, CREATED_BY," +
				  " CREATION_DATE, TECLE_CREATION, LAST_UPDATED_BY, LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN " +
				  
				  " FROM INVENTARIO_ROTATIVO ");

		if (inventarioRotativo  != null){
			if(inventarioRotativo.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(inventarioRotativo.getCodCentro());	        		
			}
			if(inventarioRotativo.getCodArticulo()!=null){
				where.append(" AND COD_ARTICULO = ? ");
				params.add(inventarioRotativo.getCodArticulo());	        		
			}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
			if(inventarioRotativo.getCodMac()!=null){
				where.append(" AND COD_MAC = ? ");
				params.add(inventarioRotativo.getCodMac());	        		
			}
		}        
        query.append(where);

		query.append(" ) WHERE ROWNUM = 1 ");

		List<InventarioRotativo> inventarioRotativoLista = null;		

		try {
			inventarioRotativoLista = (List<InventarioRotativo>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoMap, params.toArray());  
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
		InventarioRotativo inventarioRotativoRes = null;
		if (!inventarioRotativoLista.isEmpty()){
			inventarioRotativoRes = inventarioRotativoLista.get(0);
		}
	    return inventarioRotativoRes;
	}

    @Override
    public Long findAllCount(InventarioRotativo inventarioRotativo) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1)" 
    										+ " FROM INVENTARIO_ROTATIVO ");
    
		if (inventarioRotativo  != null){
			if(inventarioRotativo.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(inventarioRotativo.getCodCentro());	        		
			}
			if(inventarioRotativo.getCodArticulo()!=null){
				where.append(" AND COD_ARTICULO = ? ");
				params.add(inventarioRotativo.getCodArticulo());	        		
			}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
			if(inventarioRotativo.getCodMac()!=null){
				where.append(" AND COD_MAC = ? ");
				params.add(inventarioRotativo.getCodMac());	   
			}
		}

        query.append(where);
        logger.debug(query);

	 
	    Long cont = null;
	    try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return cont;
    }
    
    @Override
    public List<PdaDatosInventarioLibre> findAllPda(PdaDatosInventarioLibre pdaDatosInventarioLibre, boolean bolPrincipal) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE I.COD_ARTICULO=D.COD_ART ");

    	if (bolPrincipal){
    		where.append(" AND I.COD_ARTICULO = I.COD_ARTICULO_RELA ");
    	}
    	
    	StringBuffer query = new StringBuffer(" SELECT I.COD_CENTRO COD_CENTRO, I.COD_MAC COD_MAC, I.COD_AREA COD_AREA, I.COD_SECCION COD_SECCION, I.COD_ARTICULO COD_ARTICULO, I.FLG_UNICA FLG_UNICA, I.COD_ARTICULO_RELA COD_ARTICULO_RELA," +
    										  " I.FLG_STOCKPRINCIPAL FLG_STOCKPRINCIPAL, I.FLG_NO_GUARDAR  FLG_NO_GUARDAR, I.CAMARA_STOCK CAMARA_STOCK, I.CAMARA_BANDEJA CAMARA_BANDEJA, I.SALA_STOCK SALA_STOCK, I.SALA_BANDEJA SALA_BANDEJA, " +
    										  " I.PROPOR_REF_COMPRA PROPOR_REF_COMPRA, I.PROPOR_REF_VENTA PROPOR_REF_VENTA, I.FLG_VARIAS_UNITARIAS FLG_VARIAS_UNITARIAS, I.AVISO AVISO, I.ERROR ERROR, I.CREATED_BY CREATED_BY," +
    										  " I.CREATION_DATE CREATION_DATE, I.TECLE_CREATION TECLE_CREATION, I.LAST_UPDATED_BY LAST_UPDATED_BY, I.LAST_UPDATE_DATE LAST_UPDATE_DATE, I.LAST_UPDATE_LOGIN LAST_UPDATE_LOGIN, I.TECLE TECLE, I.TCN TCN, D.DESCRIP_ART DESCRIP_ART " +
    										  
    										  " FROM INVENTARIO_ROTATIVO I, V_DATOS_DIARIO_ART D ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND I.COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodArticulo()!=null){
        		 where.append(" AND I.COD_ARTICULO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodArticulo());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND I.COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodSeccion()!=null && !(new Long(-1).equals(pdaDatosInventarioLibre.getCodSeccion()))){
       		 	 where.append(" AND I.COD_SECCION = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodSeccion());	        		
        	}
        	
        	if(pdaDatosInventarioLibre.getCreationDate() !=null){
       		 where.append(" AND TRUNC(I.CREATION_DATE) = TRUNC(?) ");
	        	 params.add(pdaDatosInventarioLibre.getCreationDate());	        		
       	}
		}
	   	
        query.append(where);
        
        //StringBuffer order = new StringBuffer(3000);

		List<PdaDatosInventarioLibre> pdaDatosInventarioLibreLista = null;		
		
		 try {
			 pdaDatosInventarioLibreLista = (List<PdaDatosInventarioLibre>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoPdaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return pdaDatosInventarioLibreLista;
    }
    
    @Override
    public LinkedHashMap<Long, String> findSeccionesPda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE V.NIVEL = 'I2' AND I.COD_AREA = V.GRUPO1 AND I.COD_SECCION = V.GRUPO2 AND I.COD_ARTICULO = I.COD_ARTICULO_RELA ");
    	
    	StringBuffer query = new StringBuffer(" SELECT V.NIVEL, V.GRUPO1, V.GRUPO2, V.GRUPO3, V.GRUPO4, V.GRUPO5, V.DESCRIPCION " 
    										+ " FROM INVENTARIO_ROTATIVO I, V_AGRU_COMER_REF V ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND I.COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND I.COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
		}

        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		String campoOrdenacion = "GRUPO2";
		order.append(" order by " + campoOrdenacion + " asc ");	
		query.append(order);

    	
		List<VAgruComerRef> listaAgrupaciones = null;
    	try {
    		listaAgrupaciones = (List<VAgruComerRef>) this.jdbcTemplate.query(query.toString(),this.rwAgruComerRefMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
    	
    	
    	//Tratamiento para pasar la lista de secciones a pantalla
    	LinkedHashMap<Long, String> secciones = new LinkedHashMap<Long, String>();
    	//Opción para mostrado de todas las secciones
    	secciones.put(new Long(-1), "");
    	if (listaAgrupaciones != null && listaAgrupaciones.size() > 0){
    		for (VAgruComerRef vAgruComerRef : listaAgrupaciones) {
    			secciones.put(vAgruComerRef.getGrupo2(), vAgruComerRef.getGrupo2()+"-"+vAgruComerRef.getDescripcion());
			}
    	}

    	return secciones;
    }
    

    @Override
    public boolean existenDatosInventarioLibrePda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception{
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE COD_ARTICULO = COD_ARTICULO_RELA ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
				+ " FROM INVENTARIO_ROTATIVO ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodArticulo());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
		}
        
        query.append(where);
        
		
		Boolean existenDatos = false;
		try {
			existenDatos = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()) > 0;
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
		return existenDatos;
    }
    
    @Override
    public PdaDatosInventarioLibre findSumaCantidades(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	
    	//Solo se suman los articulos hijos.
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MAX(COD_CENTRO) COD_CENTRO, MAX(COD_MAC) COD_MAC, MAX(COD_AREA) COD_AREA, MAX(COD_SECCION) COD_SECCION, MAX(COD_ARTICULO) COD_ARTICULO, MAX(FLG_UNICA) FLG_UNICA, MAX(COD_ARTICULO_RELA) COD_ARTICULO_RELA," +
    										  " MAX(FLG_STOCKPRINCIPAL) FLG_STOCKPRINCIPAL, MAX(FLG_NO_GUARDAR) FLG_NO_GUARDAR, SUM(NVL(CAMARA_STOCK,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) CAMARA_STOCK, SUM(NVL(CAMARA_BANDEJA,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) CAMARA_BANDEJA, SUM(NVL(SALA_STOCK,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) SALA_STOCK, SUM(NVL(SALA_BANDEJA,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) SALA_BANDEJA, " +
    										  " MAX(PROPOR_REF_COMPRA) PROPOR_REF_COMPRA, MAX(PROPOR_REF_VENTA) PROPOR_REF_VENTA, MAX(FLG_VARIAS_UNITARIAS) FLG_VARIAS_UNITARIAS, MAX(AVISO) AVISO, MAX(ERROR) ERROR, MAX(CREATED_BY) CREATED_BY," +
    										  " MAX(CREATION_DATE) CREATION_DATE, MAX(TECLE_CREATION) TECLE_CREATION, MAX(LAST_UPDATED_BY) LAST_UPDATED_BY, MAX(LAST_UPDATE_DATE) LAST_UPDATE_DATE, MAX(LAST_UPDATE_LOGIN) LAST_UPDATE_LOGIN, MAX(TECLE) TECLE, MAX(TCN) TCN, '' DESCRIP_ART " +
    										  
    										  " FROM INVENTARIO_ROTATIVO ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodArticulo());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
		}
	   	
        query.append(where);
        
        if(pdaDatosInventarioLibre.getCodArticulo()!=null){
        	query.append(" GROUP BY COD_ARTICULO ");
        }else{
        	query.append(" GROUP BY COD_ARTICULO_RELA ");
        }
        
        //StringBuffer order = new StringBuffer(3000);

		List<PdaDatosInventarioLibre> pdaDatosInventarioLibreLista = null;		
		
		try {
			pdaDatosInventarioLibreLista = (List<PdaDatosInventarioLibre>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoPdaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		

		PdaDatosInventarioLibre pdaDatosInventarioLibreSuma = new PdaDatosInventarioLibre();
		if(pdaDatosInventarioLibreLista != null && pdaDatosInventarioLibreLista.size() > 0){
			pdaDatosInventarioLibreSuma = pdaDatosInventarioLibreLista.get(0);
		}
	    return pdaDatosInventarioLibreSuma;
    }
    
    @Override
    public PdaDatosInventarioLibre findSumaCantidadesRefConRelaciones(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	
    	//Solo se suman los articulos hijos.
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MAX(COD_CENTRO) COD_CENTRO, MAX(COD_MAC) COD_MAC, MAX(COD_AREA) COD_AREA, MAX(COD_SECCION) COD_SECCION, MAX(COD_ARTICULO) COD_ARTICULO, MAX(FLG_UNICA) FLG_UNICA, MAX(COD_ARTICULO_RELA) COD_ARTICULO_RELA," +
    										  " MAX(FLG_STOCKPRINCIPAL) FLG_STOCKPRINCIPAL, MAX(FLG_NO_GUARDAR) FLG_NO_GUARDAR, SUM(NVL(CAMARA_STOCK,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) CAMARA_STOCK, SUM(NVL(CAMARA_BANDEJA,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) CAMARA_BANDEJA, SUM(NVL(SALA_STOCK,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) SALA_STOCK, SUM(NVL(SALA_BANDEJA,0)*DECODE(NVL(propor_ref_compra,1),0,1,propor_ref_compra)/DECODE(NVL(propor_ref_venta,1),0,1,propor_ref_venta)) SALA_BANDEJA, " +
    										  " MAX(PROPOR_REF_COMPRA) PROPOR_REF_COMPRA, MAX(PROPOR_REF_VENTA) PROPOR_REF_VENTA, MAX(FLG_VARIAS_UNITARIAS) FLG_VARIAS_UNITARIAS, MAX(AVISO) AVISO, MAX(ERROR) ERROR, MAX(CREATED_BY) CREATED_BY," +
    										  " MAX(CREATION_DATE) CREATION_DATE, MAX(TECLE_CREATION) TECLE_CREATION, MAX(LAST_UPDATED_BY) LAST_UPDATED_BY, MAX(LAST_UPDATE_DATE) LAST_UPDATE_DATE, MAX(LAST_UPDATE_LOGIN) LAST_UPDATE_LOGIN, MAX(TECLE) TECLE, MAX(TCN) TCN, '' DESCRIP_ART " +
    										  
    										  " FROM INVENTARIO_ROTATIVO ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodArticuloRela()!=null){
       		 	 where.append(" AND COD_ARTICULO <> COD_ARTICULO_RELA ");
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodArticulo());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
		}
	   	
        query.append(where);
        
        if(pdaDatosInventarioLibre.getCodArticuloRela()!=null){
        	//query.append(" GROUP BY COD_ARTICULO_RELA ");
        }
        
        //StringBuffer order = new StringBuffer(3000);

		List<PdaDatosInventarioLibre> pdaDatosInventarioLibreLista = null;		
		
		try {
			pdaDatosInventarioLibreLista = (List<PdaDatosInventarioLibre>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoPdaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
		PdaDatosInventarioLibre pdaDatosInventarioLibreSuma = new PdaDatosInventarioLibre();
		if(pdaDatosInventarioLibreLista != null && pdaDatosInventarioLibreLista.size() > 0){
			pdaDatosInventarioLibreSuma = pdaDatosInventarioLibreLista.get(0);
		}
	    return pdaDatosInventarioLibreSuma;
    }
    
    @Override
    public PdaDatosInventarioLibre findCantidadesRef(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	
    	//Solo se suman los articulos hijos.
    	where.append("WHERE 1=1 AND ROWNUM <= 1 ");

    	StringBuffer query = new StringBuffer(" SELECT MAX(COD_CENTRO) COD_CENTRO, MAX(COD_MAC) COD_MAC, MAX(COD_AREA) COD_AREA, MAX(COD_SECCION) COD_SECCION, MAX(COD_ARTICULO) COD_ARTICULO, MAX(FLG_UNICA) FLG_UNICA, MAX(COD_ARTICULO_RELA) COD_ARTICULO_RELA," +
    										  " MAX(FLG_STOCKPRINCIPAL) FLG_STOCKPRINCIPAL, MAX(FLG_NO_GUARDAR) FLG_NO_GUARDAR, SUM(NVL(CAMARA_STOCK,0)) CAMARA_STOCK, SUM(NVL(CAMARA_BANDEJA,0)) CAMARA_BANDEJA, SUM(NVL(SALA_STOCK,0)) SALA_STOCK, SUM(NVL(SALA_BANDEJA,0)) SALA_BANDEJA, " +
    										  " MAX(PROPOR_REF_COMPRA) PROPOR_REF_COMPRA, MAX(PROPOR_REF_VENTA) PROPOR_REF_VENTA, MAX(FLG_VARIAS_UNITARIAS) FLG_VARIAS_UNITARIAS, MAX(AVISO) AVISO, MAX(ERROR) ERROR, MAX(CREATED_BY) CREATED_BY," +
    										  " MAX(CREATION_DATE) CREATION_DATE, MAX(TECLE_CREATION) TECLE_CREATION, MAX(LAST_UPDATED_BY) LAST_UPDATED_BY, MAX(LAST_UPDATE_DATE) LAST_UPDATE_DATE, MAX(LAST_UPDATE_LOGIN) LAST_UPDATE_LOGIN, MAX(TECLE) TECLE, MAX(TCN) TCN, '' DESCRIP_ART " +
    										  
    										  " FROM INVENTARIO_ROTATIVO ");
    
	   	if (pdaDatosInventarioLibre  != null){
        	if(pdaDatosInventarioLibre.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodCentro());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodArticuloRela()!=null){
       		 	 where.append(" AND COD_ARTICULO <> COD_ARTICULO_RELA ");
        		 where.append(" AND COD_ARTICULO_RELA = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodArticulo());	        		
        	}
        	if(pdaDatosInventarioLibre.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(pdaDatosInventarioLibre.getCodMac());	        		
        	}
		}
	   	
        query.append(where);
        
		List<PdaDatosInventarioLibre> pdaDatosInventarioLibreLista = null;		
		
		try {
			pdaDatosInventarioLibreLista = (List<PdaDatosInventarioLibre>) this.jdbcTemplate.query(query.toString(),this.rwInventarioRotativoPdaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		
		PdaDatosInventarioLibre pdaDatosInventarioLibreSuma = null;
		if(pdaDatosInventarioLibreLista != null && pdaDatosInventarioLibreLista.size() == 1){
			pdaDatosInventarioLibreSuma = pdaDatosInventarioLibreLista.get(0);
		}
	    return pdaDatosInventarioLibreSuma;
    }
    
    @Override
	public List<PdaDatosInventarioLibre> findAllPdaGISAE(
			PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE G.COD_ART=D.COD_ART  ");

		StringBuffer query = new StringBuffer(" SELECT G.COD_CENTRO, G.COD_AREA, G.COD_SECCION, G.COD_ART, ");
		query.append(" I.FLG_UNICA FLG_UNICA, I.COD_ARTICULO_RELA COD_ARTICULO_RELA, ");
		query.append("I.FLG_STOCKPRINCIPAL FLG_STOCKPRINCIPAL, I.FLG_NO_GUARDAR  FLG_NO_GUARDAR, I.CAMARA_STOCK CAMARA_STOCK, I.CAMARA_BANDEJA CAMARA_BANDEJA, I.SALA_STOCK SALA_STOCK, I.SALA_BANDEJA SALA_BANDEJA, ");
		query.append("NVL(I.PROPOR_REF_COMPRA, 0) PROPOR_REF_COMPRA, NVL(I.PROPOR_REF_VENTA, 0) PROPOR_REF_VENTA, I.FLG_VARIAS_UNITARIAS FLG_VARIAS_UNITARIAS, I.AVISO AVISO, I.ERROR ERROR, I.CREATED_BY CREATED_BY, ");
		query.append(" NVL(I.CREATION_DATE, SYSDATE) CREATION_DATE, I.TECLE_CREATION TECLE_CREATION, I.LAST_UPDATED_BY LAST_UPDATED_BY, NVL(I.LAST_UPDATE_DATE, SYSDATE) LAST_UPDATE_DATE, NVL(I.LAST_UPDATE_LOGIN, 0) LAST_UPDATE_LOGIN,  ");
		query.append(" NVL(I.TECLE, 0) TECLE, NVL(I.TCN, 0) TCN, D.DESCRIP_ART DESCRIP_ART ");
		query.append("FROM V_DATOS_DIARIO_ART D, INVENTARIO_ROTATIVO_GISAE G ");
		query.append("LEFT OUTER JOIN INVENTARIO_ROTATIVO I ON I.COD_CENTRO = G.COD_CENTRO AND I.COD_ARTICULO = G.COD_ART AND I.COD_MAC = 'GISAE' AND I.COD_ARTICULO = I.COD_ARTICULO_RELA AND TRUNC(I.CREATION_DATE) >= TRUNC(SYSDATE)");

		if (pdaDatosInventarioLibre != null) {
			if (pdaDatosInventarioLibre.getCodCentro() != null) {
				where.append(" AND G.COD_CENTRO = ? ");
				params.add(pdaDatosInventarioLibre.getCodCentro());
			}
			if (pdaDatosInventarioLibre.getCodSeccion() != null
					&& !(new Long(-1).equals(pdaDatosInventarioLibre
							.getCodSeccion()))) {
				where.append(" AND G.COD_SECCION = ? ");
				params.add(pdaDatosInventarioLibre.getCodSeccion());
			}
			where.append("AND G.FECHA_GEN = TRUNC(SYSDATE) AND G.FLG_TRATADO = 'N'");
		}

		query.append(where);

		// StringBuffer order = new StringBuffer(3000);

		List<PdaDatosInventarioLibre> pdaDatosInventarioLibreLista = null;
		
		try {
			pdaDatosInventarioLibreLista = (List<PdaDatosInventarioLibre>) this.jdbcTemplate
					.query(query.toString(), this.rwInventarioRotativoPdaGISAEMap,
							params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return pdaDatosInventarioLibreLista;
	}

    @Override
	public void updateFlgNoGuardarPda(String flgNoGuardar, InventarioRotativo inventarioRotativo) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE INVENTARIO_ROTATIVO SET FLG_NO_GUARDAR = ? ");

		query.append("WHERE 1=1 ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		

		params.add(flgNoGuardar);
		
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
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
	public void updateAvisoPda(String aviso, InventarioRotativo inventarioRotativo) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE INVENTARIO_ROTATIVO SET AVISO = ? ");

		query.append("WHERE 1=1 ");

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		

		params.add(aviso);
		
		if (inventarioRotativo  != null){
        	if(inventarioRotativo.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(inventarioRotativo.getCodCentro());	        		
        	}
        	if(inventarioRotativo.getCodArticulo()!=null){
        		 where.append(" AND COD_ARTICULO = ? ");
	        	 params.add(inventarioRotativo.getCodArticulo());	        		
        	}
			if(inventarioRotativo.getCodArticuloRela()!=null){
				where.append(" AND COD_ARTICULO_RELA = ? ");
				params.add(inventarioRotativo.getCodArticuloRela());	        		
			}
        	if(inventarioRotativo.getCodMac()!=null){
        		 where.append(" AND COD_MAC = ? ");
	        	 params.add(inventarioRotativo.getCodMac());	        		
        	}
		}
			
		query.append(where);
		
		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

}
