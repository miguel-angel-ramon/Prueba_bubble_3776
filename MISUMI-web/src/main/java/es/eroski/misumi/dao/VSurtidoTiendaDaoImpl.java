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

import es.eroski.misumi.dao.iface.VSurtidoTiendaDao;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VSurtidoTiendaDaoImpl implements VSurtidoTiendaDao{
	
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(VSurtidoTiendaDaoImpl.class);

	 private RowMapper<VSurtidoTienda> rwSurtidoTiendaMap = new RowMapper<VSurtidoTienda>() {
			public VSurtidoTienda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VSurtidoTienda(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),resultSet.getLong("COD_ART_CAPRABO"),
			    			resultSet.getDouble("UNI_CAJA_SERV"), resultSet.getString("TIPO_APROV"), 
			    			resultSet.getString("MARCA_MAESTRO_CENTRO"), resultSet.getString("CATALOGO"),
			    			resultSet.getString("PEDIR"), resultSet.getLong("TIPO_GAMA"), resultSet.getString("DESC_GAMA"),
			    			resultSet.getString("TIPO_APROV"),resultSet.getDate("FECHA_GEN"),resultSet.getFloat("UNI_FORMA_PED")
				    );
			}
	};
		    
    private RowMapper<VSurtidoTienda> rwSurtidoTiendaGamaMap = new RowMapper<VSurtidoTienda>() {
		public VSurtidoTienda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VSurtidoTienda(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
		    			resultSet.getString("GAMA"), 
		    			resultSet.getLong("STK_FINAL_MIN_LUN"), resultSet.getLong("STK_FINAL_MIN_MAR"),
		    			resultSet.getLong("STK_FINAL_MIN_MIE"), resultSet.getLong("STK_FINAL_MIN_JUE"), 
		    			resultSet.getLong("STK_FINAL_MIN_VIE"), resultSet.getLong("STK_FINAL_MIN_SAB"),
		    			resultSet.getLong("STK_FINAL_MIN_DOM"), resultSet.getFloat("UNI_FORMA_PED")
			    );
		}
	};

    private RowMapper<VSurtidoTienda> rwSurtidoTiendaImplantacion = new RowMapper<VSurtidoTienda>() {
		public VSurtidoTienda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VSurtidoTienda(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
		    			resultSet.getDate("FECHA_GEN"), resultSet.getString("PEDIR")
			    );
		}
	};
	
	private RowMapper<VSurtidoTienda> rwSurtidoTiendaVegalsaMap = new RowMapper<VSurtidoTienda>() {
		public VSurtidoTienda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VSurtidoTienda( resultSet.getLong("cod_centro")
		    						 , resultSet.getLong("cod_art")
		    						 , resultSet.getDouble("uc")
		    						 , resultSet.getString("tipo_aprov")
		    						 , resultSet.getString("marca_maestro_centro")
		    						 , resultSet.getString("catalogo_cen")
		    						 , resultSet.getString("reapro")
		    						 , resultSet.getDate("fecha_mmc")
		    						 , resultSet.getLong("cc")
		    						 , resultSet.getFloat("ufp")
			    );
		}
	};
		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VSurtidoTienda> findAll(VSurtidoTienda vSurtidoTienda) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_ART_CAPRABO, UNI_CAJA_SERV, TIPO_APROV, MARCA_MAESTRO_CENTRO"
	    												+ ", CATALOGO, PEDIR, TIPO_GAMA, DESC_GAMA, TIPO_APROV, FECHA_GEN, UNI_FORMA_PED " 
	    										+ " FROM V_SURTIDO_TIENDA ");
	    
	    	
	        if (vSurtidoTienda  != null){
	        	if(vSurtidoTienda.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vSurtidoTienda.getCodCentro());	        		
	        	}
	        	if(vSurtidoTienda.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vSurtidoTienda.getCodArt());	        		
	        	}
	        	if(vSurtidoTienda.getCodArtCaprabo()!=null){
	        		 where.append(" AND COD_ART_CAPRABO = ? ");
		        	 params.add(vSurtidoTienda.getCodArtCaprabo());	        		
	        	}
	        	if(vSurtidoTienda.getUniCajaServ()!=null){
	        		 where.append(" AND UNI_CAJA_SERV = upper(?) ");
		        	 params.add(vSurtidoTienda.getUniCajaServ());	        		
	        	}
	        	if(vSurtidoTienda.getTipoAprov()!=null){
	        		 where.append(" AND TIPO_APROV = upper(?) ");
		        	 params.add(vSurtidoTienda.getTipoAprov());	        		
	        	}
	        	if(vSurtidoTienda.getMarcaMaestroCentro()!=null){
	        		 where.append(" AND MARCA_MAESTRO_CENTRO = upper(?) ");
		        	 params.add(vSurtidoTienda.getMarcaMaestroCentro());	        		
	        	}
	        	if(vSurtidoTienda.getCatalogo()!=null){
	        		 where.append(" AND CATALOGO = upper(?) ");
		        	 params.add(vSurtidoTienda.getCatalogo());	        		
	        	}
	        	if(vSurtidoTienda.getPedir()!=null){
	        		 where.append(" AND PEDIR = upper(?) ");
		        	 params.add(vSurtidoTienda.getPedir());	        		
	        	}
	        	if(vSurtidoTienda.getTipoGama()!=null){
	        		 where.append(" AND TIPO_GAMA = ? ");
		        	 params.add(vSurtidoTienda.getTipoGama());	        		
	        	}
	        	if(vSurtidoTienda.getDescGama()!=null){
	        		 where.append(" AND DESC_GAMA = upper(?) ");
		        	 params.add(vSurtidoTienda.getDescGama());	        		
	        	}
	        	if(vSurtidoTienda.getDescGama()!=null){
	        		 where.append(" AND TIPO_APROV = upper(?) ");
		        	 params.add(vSurtidoTienda.getTipoAprov());	        		
	        	}

	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art, fecha_gen ");
			query.append(order);

			List<VSurtidoTienda> vSurtidoTiendaLista = null;		
			
			try {
				vSurtidoTiendaLista = (List<VSurtidoTienda>) this.jdbcTemplate.query(query.toString(),this.rwSurtidoTiendaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vSurtidoTiendaLista;
	    }
	    
	    @Override
	    public List<VSurtidoTienda> findAllGama(VSurtidoTienda vSurtidoTienda) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, GAMA, STK_FINAL_MIN_LUN, STK_FINAL_MIN_MAR,  STK_FINAL_MIN_MIE, STK_FINAL_MIN_JUE, STK_FINAL_MIN_VIE, STK_FINAL_MIN_SAB, STK_FINAL_MIN_DOM, UNI_FORMA_PED  " 
	    										+ " FROM V_SURTIDO_TIENDA ");
	    
	    	
	    	if (vSurtidoTienda  != null){
	        	if(vSurtidoTienda.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vSurtidoTienda.getCodCentro());	        		
	        	}
	        	if(vSurtidoTienda.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vSurtidoTienda.getCodArt());	        		
	        	}
	        	if(vSurtidoTienda.getUniCajaServ()!=null){
	        		 where.append(" AND UNI_CAJA_SERV = upper(?) ");
		        	 params.add(vSurtidoTienda.getUniCajaServ());	        		
	        	}
	        	if(vSurtidoTienda.getTipoAprov()!=null){
	        		 where.append(" AND TIPO_APROV = upper(?) ");
		        	 params.add(vSurtidoTienda.getTipoAprov());	        		
	        	}
	        	if(vSurtidoTienda.getMarcaMaestroCentro()!=null){
	        		 where.append(" AND MARCA_MAESTRO_CENTRO = upper(?) ");
		        	 params.add(vSurtidoTienda.getMarcaMaestroCentro());	        		
	        	}
	        	if(vSurtidoTienda.getCatalogo()!=null){
	        		 where.append(" AND CATALOGO = upper(?) ");
		        	 params.add(vSurtidoTienda.getCatalogo());	        		
	        	}
	        	if(vSurtidoTienda.getPedir()!=null){
	        		 where.append(" AND PEDIR = upper(?) ");
		        	 params.add(vSurtidoTienda.getPedir());	        		
	        	}
	        	if(vSurtidoTienda.getTipoGama()!=null){
	        		 where.append(" AND TIPO_GAMA = ? ");
		        	 params.add(vSurtidoTienda.getTipoGama());	        		
	        	}
	        	if(vSurtidoTienda.getDescGama()!=null){
	        		 where.append(" AND DESC_GAMA = upper(?) ");
		        	 params.add(vSurtidoTienda.getDescGama());	        		
	        	}

	        }
	        
	        query.append(where);

	        /*StringBuffer order = new StringBuffer(3000);
			order.append(" order by XXXXX ");
			query.append(order);*/

			List<VSurtidoTienda> varAprpvLista = null;		
			
			try {
				varAprpvLista = (List<VSurtidoTienda>) this.jdbcTemplate.query(query.toString(),this.rwSurtidoTiendaGamaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return varAprpvLista;
	    }
	    
	    @Override
	    public List<VSurtidoTienda> findAllVegalsa(VSurtidoTienda vSurtidoTienda) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer("SELECT cod_centro, cod_art, uc, tipo_aprov, marca_maestro_centro, catalogo_cen, reapro, fecha_mmc, cc, ufp " 
	    										+ "FROM t_mis_surtido_vegalsa ");
	    
	        if (vSurtidoTienda  != null){
	        	if(vSurtidoTienda.getCodCentro()!=null){
	        		 where.append(" AND cod_centro = ? ");
		        	 params.add(vSurtidoTienda.getCodCentro());	        		
	        	}
	        	if(vSurtidoTienda.getCodArt()!=null){
	        		 where.append(" AND cod_art = ? ");
		        	 params.add(vSurtidoTienda.getCodArt());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append("ORDER BY fecha_gen DESC");
			query.append(order);

			List<VSurtidoTienda> vSurtidoTiendaLista = null;		
			
			try {
				vSurtidoTiendaLista = (List<VSurtidoTienda>) this.jdbcTemplate.query(query.toString(),this.rwSurtidoTiendaVegalsaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vSurtidoTiendaLista;
	    }
	    
       public List<VSurtidoTienda> obtenerFechaGeneracionSurtidoTienda(VSurtidoTienda vSurtidoTienda) throws Exception  {
    	   
    	   List<Object> params = new ArrayList<Object>();
	    	
	    	StringBuffer query = new StringBuffer("SELECT COD_ART, COD_CENTRO, FECHA_GEN, PEDIR FROM SURTIDO_TIENDA " + 
												  "WHERE COD_CENTRO = " + vSurtidoTienda.getCodCentro() +
												  "	AND COD_ART =" + vSurtidoTienda.getCodArt() +
												  " AND ?  < SYSDATE " + 
												  " ORDER BY FECHA_GEN DESC");
	    	 params.add(vSurtidoTienda.getFechaGen());	

	    	List<VSurtidoTienda> varAprpvLista = null;		
			
			try {
				varAprpvLista = (List<VSurtidoTienda>) this.jdbcTemplate.query(query.toString(),this.rwSurtidoTiendaImplantacion, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return varAprpvLista;

	    }
       
       public Long comprobarStockMayorACero(VSurtidoTienda vSurtidoTienda) throws Exception  {
    	   
    	   List<Object> params = new ArrayList<Object>();
	    	
	    	StringBuffer query = new StringBuffer("SELECT COUNT(1) " +  
												  "FROM STOCK_ACTUAL_CENTRO " + 
												  "WHERE COD_LOC = ? " +
												  "	AND  COD_ARTICULO = ? " +
												  " AND " +
												  "     ( " +
												  "         (STOCK <> 0) " +
												  "          OR " +
												  "         (STOCK = 0 AND FECHA_GEN  >= TRUNC(SYSDATE-30)) " +
	    										  "     ) ");
	    	
	    	params.add(vSurtidoTienda.getCodCentro());	
	    	params.add(vSurtidoTienda.getCodArt());

			Long stock = null;
			try {
				stock = (Long) this.jdbcTemplate.queryForLong(query.toString(),params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return stock;

	    }
       
       	public Long comprobarStockMayorACeroCaprabo(VSurtidoTienda vSurtidoTienda) throws Exception  {
    	   
    	   List<Object> params = new ArrayList<Object>();
	    	
	    	StringBuffer query = new StringBuffer("SELECT COUNT(1) " +  
												  "FROM STOCK_ACTUAL_CENTRO " + 
												  "WHERE COD_LOC = ? " +
												  "	AND  COD_ARTICULO = ? " +
												  " AND " +
												  "     ( " +
												  "         (STOCK <> 0) " +
												  "          OR " +
												  "         (STOCK = 0 AND FECHA_GEN  >= TRUNC(SYSDATE-30)) " +
	    										  "     ) ");
	    	
	    	params.add(vSurtidoTienda.getCodCentro());	
	    	params.add(vSurtidoTienda.getCodArtCaprabo());

			Long stock = null;
			try {
				stock = (Long) this.jdbcTemplate.queryForLong(query.toString(),params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return stock;

	    }
	   
}
