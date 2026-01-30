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

import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VDatosDiarioArtDaoImpl implements VDatosDiarioArtDao{
	
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(VDatosDiarioArtDaoImpl.class);
	// private static Logger logger = Logger.getLogger(VDatosDiarioArtDaoImpl.class);

	 private RowMapper<VDatosDiarioArt> rwDatosDiarioArtMap = new RowMapper<VDatosDiarioArt>() {
			public VDatosDiarioArt mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VDatosDiarioArt(resultSet.getLong("COD_ART"),resultSet.getString("DESCRIP_ART").trim(),
			    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"), 
			    			resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
			    			resultSet.getLong("GRUPO5"), resultSet.getLong("CODIFI_COMER_SUP"),
			    			resultSet.getLong("CODIFI_COMER_HIP"), resultSet.getLong("ESTLOG_N1"),
			    			resultSet.getLong("ESTLOG_N2"), resultSet.getLong("ESTLOG_N3"),
			    			resultSet.getString("ATRIB1"), resultSet.getString("ATRIB2"),
			    			resultSet.getLong("TIPO_MARCA"), resultSet.getLong("COD_MARCA"),
			    			resultSet.getLong("COD_ANT"), resultSet.getLong("COD_FP_MADRE"),
			    			resultSet.getLong("BALANZA"), resultSet.getString("TIPO_COMPRA_VENTA"),
			    			resultSet.getLong("DIAS_LIM_VTA"), resultSet.getString("FORMATO")
				    );
			}

		    };

		private RowMapper<VDatosDiarioArt> rwDatosRefCompraVenta = new RowMapper<VDatosDiarioArt>() {
			public VDatosDiarioArt mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new VDatosDiarioArt(resultSet.getLong("COD_ART"), resultSet.getString("DESCRIP_ART").trim());
			}
	
		};

		private String getMappedField(String fieldName) {
			if (fieldName.toUpperCase().equals("CODART")) {
				return "COD_ART";
			} else if (fieldName.toUpperCase().equals("DESCRIPART")) {
				return "DESCRIP_ART";
			} else {
				return fieldName;
			}
		}			
		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VDatosDiarioArt> findAll(VDatosDiarioArt vDatosDiarioArt) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_ART, DESCRIP_ART, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, CODIFI_COMER_SUP, CODIFI_COMER_HIP, " +
	    										  "   ESTLOG_N1, ESTLOG_N2, ESTLOG_N3, ATRIB1, ATRIB2, TIPO_MARCA, COD_MARCA, COD_ANT, COD_FP_MADRE,BALANZA, " +
	    										  "   TIPO_COMPRA_VENTA, DIAS_LIM_VTA, FORMATO"	
	    										+ " FROM V_DATOS_DIARIO_ART ");
	    
	    	
	        if (vDatosDiarioArt  != null){
	        	if(vDatosDiarioArt.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vDatosDiarioArt.getCodArt());	        		
	        	}
	        	if(vDatosDiarioArt.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getDescripArt());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo1());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo2());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo3());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo4());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo5());	        		
	        	}
	        	if(vDatosDiarioArt.getCodifiComerSup()!=null){
	        		 where.append(" AND CODIFI_COMER_SUP = ? ");
		        	 params.add(vDatosDiarioArt.getCodifiComerSup());	        		
	        	}
	        	if(vDatosDiarioArt.getCodifiComerHip()!=null){
	        		 where.append(" AND CODIFI_COMER_HIP = ? ");
		        	 params.add(vDatosDiarioArt.getCodifiComerHip());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN1()!=null){
	        		 where.append(" AND ESTLOG_N1 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN1());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN2()!=null){
	        		 where.append(" AND ESTLOG_N2 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN2());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN3()!=null){
	        		 where.append(" AND ESTLOG_N3 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN3());	        		
	        	}
	        	if(vDatosDiarioArt.getAtrib1()!=null){
	        		 where.append(" AND UPPER(ATRIB1) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getAtrib1());	        		
	        	}
	        	if(vDatosDiarioArt.getAtrib2()!=null){
	        		 where.append(" AND UPPER(ATRIB2) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getAtrib2());	        		
	        	}
	        	if(vDatosDiarioArt.getTipoMarca()!=null){
	        		 where.append(" AND TIPO_MARCA = ? ");
		        	 params.add(vDatosDiarioArt.getTipoMarca());	        		
	        	}
	        	if(vDatosDiarioArt.getCodMarca()!=null){
	        		 where.append(" AND COD_MARCA = ? ");
		        	 params.add(vDatosDiarioArt.getCodMarca());	        		
	        	}
	        	if(vDatosDiarioArt.getCodAnt()!=null){
	        		 where.append(" AND COD_ANT = ? ");
		        	 params.add(vDatosDiarioArt.getCodAnt());	        		
	        	}
	        	if(vDatosDiarioArt.getCodFpMadre()!=null){
	        		 where.append(" AND COD_FP_MADRE = ? ");
		        	 params.add(vDatosDiarioArt.getCodFpMadre());	        		
	        	}
	        	if(vDatosDiarioArt.getBalanza()!=null){
	        		 where.append(" AND BALANZA = ? ");
		        	 params.add(vDatosDiarioArt.getBalanza());
		        	 	//MISUMI-528 se añaden las siguientes lineas para evitar que 
		        	 	//si viene a 00000 se quede cargando indefinidamente
		        	 	if(vDatosDiarioArt.getBalanza()==0){
		        	 		where.append(" AND rownum < 2 ");
		        		 };
	        	}
	        	if(vDatosDiarioArt.getTipoCompraVenta()!=null){
	        		 where.append(" AND TIPO_COMPRA_VENTA = upper(?) ");
		        	 params.add(vDatosDiarioArt.getTipoCompraVenta());	        		
	        	}
	        	if(vDatosDiarioArt.getTipoCompraVenta()!=null){
	        		 where.append(" AND FORMATO = upper(?) ");
		        	 params.add(vDatosDiarioArt.getFormato());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append("ORDER BY cod_art ");
			query.append(order);

			List<VDatosDiarioArt> vDatosDiarioArtLista = null;		
			
			try {
				if(params.size()>0){
					vDatosDiarioArtLista = (List<VDatosDiarioArt>) this.jdbcTemplate.query(query.toString(),this.rwDatosDiarioArtMap, params.toArray()); 
				}

			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vDatosDiarioArtLista;
	    }
	   
	    @Override
	    public Long findVidaUtil(VDatosDiarioArt vDatosDiarioArt) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT DIAS_LIM_VTA "	
	    										+ " FROM V_DATOS_DIARIO_ART ");
	    
	    	
	        if (vDatosDiarioArt  != null){
	        	if(vDatosDiarioArt.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vDatosDiarioArt.getCodArt());	        		
	        	}
	        	if(vDatosDiarioArt.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getDescripArt());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo1());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo2());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo3());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo4());	        		
	        	}
	        	if(vDatosDiarioArt.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vDatosDiarioArt.getGrupo5());	        		
	        	}
	        	if(vDatosDiarioArt.getCodifiComerSup()!=null){
	        		 where.append(" AND CODIFI_COMER_SUP = ? ");
		        	 params.add(vDatosDiarioArt.getCodifiComerSup());	        		
	        	}
	        	if(vDatosDiarioArt.getCodifiComerHip()!=null){
	        		 where.append(" AND CODIFI_COMER_HIP = ? ");
		        	 params.add(vDatosDiarioArt.getCodifiComerHip());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN1()!=null){
	        		 where.append(" AND ESTLOG_N1 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN1());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN2()!=null){
	        		 where.append(" AND ESTLOG_N2 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN2());	        		
	        	}
	        	if(vDatosDiarioArt.getEstlogN3()!=null){
	        		 where.append(" AND ESTLOG_N3 = ? ");
		        	 params.add(vDatosDiarioArt.getEstlogN3());	        		
	        	}
	        	if(vDatosDiarioArt.getAtrib1()!=null){
	        		 where.append(" AND UPPER(ATRIB1) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getAtrib1());	        		
	        	}
	        	if(vDatosDiarioArt.getAtrib2()!=null){
	        		 where.append(" AND UPPER(ATRIB2) = upper(?) ");
		        	 params.add(vDatosDiarioArt.getAtrib2());	        		
	        	}
	        	if(vDatosDiarioArt.getTipoMarca()!=null){
	        		 where.append(" AND TIPO_MARCA = ? ");
		        	 params.add(vDatosDiarioArt.getTipoMarca());	        		
	        	}
	        	if(vDatosDiarioArt.getCodMarca()!=null){
	        		 where.append(" AND COD_MARCA = ? ");
		        	 params.add(vDatosDiarioArt.getCodMarca());	        		
	        	}
	        	if(vDatosDiarioArt.getCodAnt()!=null){
	        		 where.append(" AND COD_ANT = ? ");
		        	 params.add(vDatosDiarioArt.getCodAnt());	        		
	        	}
	        	if(vDatosDiarioArt.getCodFpMadre()!=null){
	        		 where.append(" AND COD_FP_MADRE = ? ");
		        	 params.add(vDatosDiarioArt.getCodFpMadre());	        		
	        	}
	        	if(vDatosDiarioArt.getBalanza()!=null){
	        		 where.append(" AND BALANZA = ? ");
		        	 params.add(vDatosDiarioArt.getBalanza());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_art ");
			query.append(order);
	
			Long vidaUtil = null;
			try {
				vidaUtil = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 

			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
			
		    return vidaUtil;
	    }

	    @Override
	    public List<VDatosDiarioArt> findAllVentaRef(VDatosDiarioArt vDatosDiarioArt, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append(" WHERE COD_ARTICULO_HIJO = COD_ART AND TIPO_COMPRA_VENTA IN ").
    			append(" (").
	    		append("'").append(Constantes.TIPO_COMPRA_VENTA_SOLO_VENTA).append("'").
	    		append(",").
    			append("'").append(Constantes.TIPO_COMPRA_VENTA_SOLO_COMPRAVENTA).append("'").
	    		append(") ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_ART, DESCRIP_ART "	
	    										+ " FROM V_DATOS_DIARIO_ART, REF_ASOCIADAS ");
	    
	    	
	        if (vDatosDiarioArt  != null){
				if (vDatosDiarioArt.getCodArt() != null) {
					where.append(" AND COD_ARTICULO = ? ");
					params.add(vDatosDiarioArt.getCodArt());
				}
			}
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	        if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" ORDER BY " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
				}
				query.append(order);
				query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
			}         
			else {
				order.append(" ORDER BY COD_ART asc");
				query.append(order);
			}

			List<VDatosDiarioArt> vDatosDiarioArtLista = null;		
			
			try {
				vDatosDiarioArtLista = (List<VDatosDiarioArt>) this.jdbcTemplate.query(query.toString(),this.rwDatosRefCompraVenta, params.toArray()); 

			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return vDatosDiarioArtLista;
	    }
	   
		@Override
		public Long findAllVentaRefCont(VDatosDiarioArt vDatosDiarioArt) throws Exception {

			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) "	
	    										+ " FROM V_DATOS_DIARIO_ART, REF_ASOCIADAS ");

	    	where.append(" WHERE COD_ARTICULO_HIJO = COD_ART AND TIPO_COMPRA_VENTA IN ").
				append(" (").
	    		append("'").append(Constantes.TIPO_COMPRA_VENTA_SOLO_VENTA).append("'").
	    		append(",").
				append("'").append(Constantes.TIPO_COMPRA_VENTA_SOLO_COMPRAVENTA).append("'").
	    		append(") ");

	        if (vDatosDiarioArt  != null){
				if (vDatosDiarioArt.getCodArt() != null) {
					where.append(" AND COD_ARTICULO = ? ");
					params.add(vDatosDiarioArt.getCodArt());
				}
			}
	        
	        query.append(where);
	        
	        Long cont = null;
			try {
				cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
			
		    return cont;
		}
		
		 @Override
		 public List<Long> findRefMismaRefMadre(Long codCentro,Long codArt) throws Exception  {
	    	List<Object> params = new ArrayList<Object>();
	    	params.add(codArt);
	    	params.add(codArt);
	    	params.add(codCentro);

	    	StringBuffer query = new StringBuffer(" SELECT COD_ART FROM V_DATOS_DIARIO_ART d "
	    			+ " WHERE d.COD_FP_MADRE=(SELECT COD_FP_MADRE FROM V_DATOS_DIARIO_ART D2 WHERE d2.COD_ART = ?) "
	    			+ " AND d.COD_ART <> ? AND EXISTS (SELECT 's' FROM V_SURTIDO_TIENDA s WHERE s.COD_CENTRO=? AND "
	    			+ " s.COD_ART=d.COD_ART AND s.MARCA_MAESTRO_CENTRO='S') ");
	    
	        List<Long> vRelacionArticuloLista = null;	

		    try {

		    	vRelacionArticuloLista = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), params.toArray(), Long.class); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return vRelacionArticuloLista;
	    }
}
