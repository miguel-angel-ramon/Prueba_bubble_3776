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

import es.eroski.misumi.dao.iface.VSicFPromocionesDao;
import es.eroski.misumi.model.VSicFPromociones;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VSicFPromocionesDaoImpl implements VSicFPromocionesDao{
	
	 private JdbcTemplate jdbcTemplate;
	 // private static Logger logger = LoggerFactory.getLogger(VSicFPromocionesDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(VSicFPromocionesDaoImpl.class);
	 
	 private RowMapper<VSicFPromociones> rwVSicFPromocionesMap = new RowMapper<VSicFPromociones>() {
			public VSicFPromociones mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VSicFPromociones(resultSet.getLong("COD_PROMOCION"), resultSet.getLong("EJER_PROMOCION"),
			    		resultSet.getLong("COD_TP_ESTR_LOC"), resultSet.getString("FEC_INICIO"), resultSet.getString("FEC_FIN"), 
			    		resultSet.getLong("COD_LOC"), resultSet.getLong("NEL_COD_N1"), resultSet.getLong("NEL_COD_N2"), 
			    		resultSet.getLong("NEL_COD_N3"), resultSet.getLong("COD_ARTICULO"), resultSet.getString("NEA_COD_N1"),
			    		resultSet.getString("NEA_COD_N2"), resultSet.getString("NEA_COD_N3"), resultSet.getString("NEA_COD_N4"),
			    		resultSet.getString("NEA_COD_N5"), resultSet.getLong("COD_TP_PROMOCION"), resultSet.getString("FLG_FOLLETO"),
						resultSet.getString("FLG_NOVEDAD"), resultSet.getString("CAB_GONDOLA"), resultSet.getDouble("CANT_N"),
						resultSet.getDouble("CANT_M"), resultSet.getString("FLG_COMPRA_REGALO"), resultSet.getString("COD_TP_DESCUENTO"),
						resultSet.getDouble("CANT_DESCUENTO"), resultSet.getDouble("UMBRAL"), resultSet.getString("FLG_TV"),
						resultSet.getString("FINANCIACION"), resultSet.getLong("COD_BLOQUE"), resultSet.getString("FLG_PORTADA"),
						resultSet.getString("FLG_CONTRAPORTADA"), resultSet.getString("FLG_DESTACADO"), resultSet.getLong("COD_ZONA_CAMPANA"),
						resultSet.getDouble("PVP")
				    );
			}

		    };

	 private RowMapper<VSicFPromociones> rwVSicFPromocionesGondolaMap = new RowMapper<VSicFPromociones>() {
			public VSicFPromociones mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VSicFPromociones(resultSet.getString("CAB_GONDOLA")
				    );
			}

		    };
		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VSicFPromociones> findAll(VSicFPromociones vSicFPromociones) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_PROMOCION, EJER_PROMOCION, COD_TP_ESTR_LOC, FEC_INICIO, FEC_FIN, " +
	    			" COD_LOC, NEL_COD_N1, NEL_COD_N2, NEL_COD_N3, COD_ARTICULO, NEA_COD_N1, NEA_COD_N2, NEA_COD_N3, NEA_COD_N4, NEA_COD_N5," +
	    			" COD_TP_PROMOCION, FLG_FOLLETO, FLG_NOVEDAD, CAB_GONDOLA, CANT_N, CANT_M, FLG_COMPRA_REGALO, COD_TP_DESCUENTO," +
	    			" CANT_DESCUENTO, UMBRAL, FLG_TV, FINANCIACION, COD_BLOQUE, FLG_PORTADA, FLG_CONTRAPORTADA, FLG_DESTACADO, COD_ZONA_CAMPANA," +
	    			" PVP " + 
	    			" FROM V_SIC_F_PROMOCIONES ");

	        if (vSicFPromociones  != null){
				  if(vSicFPromociones.getCodPromocion()!=null){
		        		 where.append(" AND COD_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getCodPromocion());	        		
		          }
				  if(vSicFPromociones.getEjerPromocion()!=null){
		        		 where.append(" AND EJER_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getEjerPromocion());	        		
		          }
				  if(vSicFPromociones.getCodTpEstrLoc()!=null){
		        		 where.append(" AND COD_TP_ESTR_LOC = ? ");
			        	 params.add(vSicFPromociones.getCodTpEstrLoc());	        		
		          }
	        	  if(vSicFPromociones.getFecInicio()!=null){
		        		 where.append(" AND FEC_INICIO = upper(?) ");
			        	 params.add(vSicFPromociones.getFecInicio());	        		
		          }
		          if(vSicFPromociones.getFecFin()!=null){
		        		 where.append(" AND FEC_FIN = upper(?) ");
		        		 params.add(vSicFPromociones.getFecFin());	        		
		          }
				  if(vSicFPromociones.getCodLoc()!=null){
		        		 where.append(" AND COD_LOC = ? ");
			        	 params.add(vSicFPromociones.getCodLoc());	        		
		          }
				  if(vSicFPromociones.getNelCodN1()!=null){
		        		 where.append(" AND NEL_COD_N1 = ? ");
			        	 params.add(vSicFPromociones.getNelCodN1());	        		
		          }
				  if(vSicFPromociones.getNelCodN2()!=null){
		        		 where.append(" AND NEL_COD_N2 = ? ");
			        	 params.add(vSicFPromociones.getNelCodN2());	        		
		          }
				  if(vSicFPromociones.getNelCodN3()!=null){
		        		 where.append(" AND NEL_COD_N3 = ? ");
			        	 params.add(vSicFPromociones.getNelCodN3());	        		
		          }
				  if(vSicFPromociones.getCodArticulo()!=null){
		        		 where.append(" AND COD_ARTICULO = ? ");
			        	 params.add(vSicFPromociones.getCodArticulo());	        		
		          }
		          if(vSicFPromociones.getNeaCodN1()!=null){
		        		 where.append(" AND NEA_COD_N1 = upper(?) ");
			        	 params.add(vSicFPromociones.getNeaCodN1());	        		
		          }
		          if(vSicFPromociones.getNeaCodN2()!=null){
		        		 where.append(" AND NEA_COD_N2 = upper(?) ");
			        	 params.add(vSicFPromociones.getNeaCodN2());	        		
		          }
		          if(vSicFPromociones.getNeaCodN3()!=null){
		        		 where.append(" AND NEA_COD_N3 = upper(?) ");
			        	 params.add(vSicFPromociones.getNeaCodN3());	        		
		          }
		          if(vSicFPromociones.getNeaCodN4()!=null){
		        		 where.append(" AND NEA_COD_N4 = upper(?) ");
			        	 params.add(vSicFPromociones.getNeaCodN4());	        		
		          }
		          if(vSicFPromociones.getNeaCodN5()!=null){
		        		 where.append(" AND NEA_COD_N5 = upper(?) ");
			        	 params.add(vSicFPromociones.getNeaCodN5());	        		
		          }
				  if(vSicFPromociones.getCodTpPromocion()!=null){
		        		 where.append(" AND COD_TP_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getCodTpPromocion());	        		
		          }
				  if(vSicFPromociones.getFlgFolleto()!=null){
		        		 where.append(" AND FLG_FOLLETO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgFolleto());	        		
				  }
				  if(vSicFPromociones.getFlgNovedad()!=null){
		        		 where.append(" AND FLG_NOVEDAD = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgNovedad());	        		
				  }
				  if(vSicFPromociones.getCabGondola()!=null){
		        		 where.append(" AND CAB_GONDOLA = upper(?) ");
			        	 params.add(vSicFPromociones.getCabGondola());	        		
				  }
				  if(vSicFPromociones.getCantN()!=null){
		        		 where.append(" AND CANT_N = ? ");
			        	 params.add(vSicFPromociones.getCantN());	        		
		          }
				  if(vSicFPromociones.getCantM()!=null){
		        		 where.append(" AND CANT_M = ? ");
			        	 params.add(vSicFPromociones.getCantM());	        		
		          }
				  if(vSicFPromociones.getFlgCompraRegalo()!=null){
		        		 where.append(" AND FLG_COMPRA_REGALO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgCompraRegalo());	        		
				  }
				  if(vSicFPromociones.getCodTpDescuento()!=null){
		        		 where.append(" AND COD_TP_DESCUENTO = upper(?) ");
			        	 params.add(vSicFPromociones.getCodTpDescuento());	        		
				  }
				  if(vSicFPromociones.getCantDescuento()!=null){
		        		 where.append(" AND CANT_DESCUENTO = ? ");
			        	 params.add(vSicFPromociones.getCantDescuento());	        		
		          }
				  if(vSicFPromociones.getUmbral()!=null){
		        		 where.append(" AND UMBRAL = ? ");
			        	 params.add(vSicFPromociones.getUmbral());	        		
		          }
				  if(vSicFPromociones.getFlgTv()!=null){
		        		 where.append(" AND FLG_TV = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgTv());	        		
				  }
				  if(vSicFPromociones.getFinanciacion()!=null){
		        		 where.append(" AND FINANCIACION = upper(?) ");
			        	 params.add(vSicFPromociones.getFinanciacion());	        		
				  }
				  if(vSicFPromociones.getCodBloque()!=null){
		        		 where.append(" AND COD_BLOQUE = ? ");
			        	 params.add(vSicFPromociones.getCodBloque());	        		
		          }
				  if(vSicFPromociones.getFlgPortada()!=null){
		        		 where.append(" AND FLG_PORTADA = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgPortada());	        		
				  }
				  if(vSicFPromociones.getFlgContraportada()!=null){
		        		 where.append(" AND FLG_CONTRAPORTADA = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgContraportada());	        		
				  }
				  if(vSicFPromociones.getFlgDestacado()!=null){
		        		 where.append(" AND FLG_DESTACADO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgDestacado());	        		
				  }
				  if(vSicFPromociones.getCodZonaCampana()!=null){
		        		 where.append(" AND COD_ZONA_CAMPANA = ? ");
			        	 params.add(vSicFPromociones.getCodZonaCampana());	        		
		          }
				  if(vSicFPromociones.getPvp()!=null){
		        		 where.append(" AND PVP = ? ");
			        	 params.add(vSicFPromociones.getPvp());	        		
		          }
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cab_gondola ");
			query.append(order);

			List<VSicFPromociones> vSicFPromocionesLista = null;		
			
			try {
				vSicFPromocionesLista = (List<VSicFPromociones>) this.jdbcTemplate.query(query.toString(),this.rwVSicFPromocionesMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vSicFPromocionesLista;
	    }

	    @Override
	    public List<VSicFPromociones> findAllGondola(VSicFPromociones vSicFPromociones) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT DISTINCT CAB_GONDOLA " +
	    			" FROM V_SIC_F_PROMOCIONES ");

	        if (vSicFPromociones  != null){
				  if(vSicFPromociones.getCodPromocion()!=null){
		        		 where.append(" AND COD_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getCodPromocion());	        		
		          }
				  if(vSicFPromociones.getEjerPromocion()!=null){
		        		 where.append(" AND EJER_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getEjerPromocion());	        		
		          }
				  if(vSicFPromociones.getCodTpEstrLoc()!=null){
		        		 where.append(" AND COD_TP_ESTR_LOC = ? ");
			        	 params.add(vSicFPromociones.getCodTpEstrLoc());	        		
		          }
	        	  if(vSicFPromociones.getFecInicio()!=null){
		        		 where.append(" AND FEC_INICIO = upper(?) ");
			        	 params.add(vSicFPromociones.getFecInicio());	        		
		          }
		          if(vSicFPromociones.getFecFin()!=null){
		        		 where.append(" AND FEC_FIN = upper(?) ");
		        		 params.add(vSicFPromociones.getFecFin());	        		
		          }
        		  where.append(" AND ((COD_LOC IS NULL");
	        	 
				  if(vSicFPromociones.getNelCodN1()!=null){
		        		 where.append(" AND NEL_COD_N1 = ? ");
			        	 params.add(vSicFPromociones.getNelCodN1());	        		
		          }
				  if(vSicFPromociones.getNelCodN2()!=null){
		        		 where.append(" AND (NEL_COD_N2 = ? OR NEL_COD_N2 = 0) ");
			        	 params.add(vSicFPromociones.getNelCodN2());	        		
		          }else{
		        	  where.append(" AND NEL_COD_N2 = 0 ");
		          }
				  if(vSicFPromociones.getNelCodN3()!=null){
		        		 where.append(" AND (NEL_COD_N3 = ? OR NEL_COD_N3 = 0) ");
			        	 params.add(vSicFPromociones.getNelCodN3());	        		
		          }else{
		        	  where.append(" AND NEL_COD_N3 = 0 ");
		          }
				  where.append(" )");
				  if(vSicFPromociones.getCodLoc()!=null){
		        		 where.append(" OR COD_LOC = ? ");
			        	 params.add(vSicFPromociones.getCodLoc());
		          }

	        	  where.append(" )");

				  if(vSicFPromociones.getCodArticulo()!=null){
		        		 where.append(" AND COD_ARTICULO = ? ");
			        	 params.add(vSicFPromociones.getCodArticulo());	        		
		          }
		          if(vSicFPromociones.getNeaCodN1()!=null){
		        		 where.append(" AND NEA_COD_N1 = LPAD(upper(?), 4, '0') ");
			        	 params.add(vSicFPromociones.getNeaCodN1());	        		
		          }
		          if(vSicFPromociones.getNeaCodN2()!=null){
		        		 where.append(" AND NEA_COD_N2 = LPAD(upper(?), 4, '0') ");
			        	 params.add(vSicFPromociones.getNeaCodN2());	        		
		          }
		          if(vSicFPromociones.getNeaCodN3()!=null){
		        		 where.append(" AND NEA_COD_N3 = LPAD(upper(?), 4, '0') ");
			        	 params.add(vSicFPromociones.getNeaCodN3());	        		
		          }
		          if(vSicFPromociones.getNeaCodN4()!=null){
		        		 where.append(" AND NEA_COD_N4 = LPAD(upper(?), 4, '0') ");
			        	 params.add(vSicFPromociones.getNeaCodN4());	        		
		          }
		          if(vSicFPromociones.getNeaCodN5()!=null){
		        		 where.append(" AND NEA_COD_N5 = LPAD(upper(?), 4, '0') ");
			        	 params.add(vSicFPromociones.getNeaCodN5());	        		
		          }
				  if(vSicFPromociones.getCodTpPromocion()!=null){
		        		 where.append(" AND COD_TP_PROMOCION = ? ");
			        	 params.add(vSicFPromociones.getCodTpPromocion());	        		
		          }
				  if(vSicFPromociones.getFlgFolleto()!=null){
		        		 where.append(" AND FLG_FOLLETO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgFolleto());	        		
				  }
				  if(vSicFPromociones.getFlgNovedad()!=null){
		        		 where.append(" AND FLG_NOVEDAD = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgNovedad());	        		
				  }
				  if(vSicFPromociones.getCabGondola()!=null){
		        		 where.append(" AND CAB_GONDOLA = upper(?) ");
			        	 params.add(vSicFPromociones.getCabGondola());	        		
				  }
				  if(vSicFPromociones.getCantN()!=null){
		        		 where.append(" AND CANT_N = ? ");
			        	 params.add(vSicFPromociones.getCantN());	        		
		          }
				  if(vSicFPromociones.getCantM()!=null){
		        		 where.append(" AND CANT_M = ? ");
			        	 params.add(vSicFPromociones.getCantM());	        		
		          }
				  if(vSicFPromociones.getFlgCompraRegalo()!=null){
		        		 where.append(" AND FLG_COMPRA_REGALO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgCompraRegalo());	        		
				  }
				  if(vSicFPromociones.getCodTpDescuento()!=null){
		        		 where.append(" AND COD_TP_DESCUENTO = upper(?) ");
			        	 params.add(vSicFPromociones.getCodTpDescuento());	        		
				  }
				  if(vSicFPromociones.getCantDescuento()!=null){
		        		 where.append(" AND CANT_DESCUENTO = ? ");
			        	 params.add(vSicFPromociones.getCantDescuento());	        		
		          }
				  if(vSicFPromociones.getUmbral()!=null){
		        		 where.append(" AND UMBRAL = ? ");
			        	 params.add(vSicFPromociones.getUmbral());	        		
		          }
				  if(vSicFPromociones.getFlgTv()!=null){
		        		 where.append(" AND FLG_TV = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgTv());	        		
				  }
				  if(vSicFPromociones.getFinanciacion()!=null){
		        		 where.append(" AND FINANCIACION = upper(?) ");
			        	 params.add(vSicFPromociones.getFinanciacion());	        		
				  }
				  if(vSicFPromociones.getCodBloque()!=null){
		        		 where.append(" AND COD_BLOQUE = ? ");
			        	 params.add(vSicFPromociones.getCodBloque());	        		
		          }
				  if(vSicFPromociones.getFlgPortada()!=null){
		        		 where.append(" AND FLG_PORTADA = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgPortada());	        		
				  }
				  if(vSicFPromociones.getFlgContraportada()!=null){
		        		 where.append(" AND FLG_CONTRAPORTADA = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgContraportada());	        		
				  }
				  if(vSicFPromociones.getFlgDestacado()!=null){
		        		 where.append(" AND FLG_DESTACADO = upper(?) ");
			        	 params.add(vSicFPromociones.getFlgDestacado());	        		
				  }
				  if(vSicFPromociones.getCodZonaCampana()!=null){
		        		 where.append(" AND COD_ZONA_CAMPANA = ? ");
			        	 params.add(vSicFPromociones.getCodZonaCampana());	        		
		          }
				  if(vSicFPromociones.getPvp()!=null){
		        		 where.append(" AND PVP = ? ");
			        	 params.add(vSicFPromociones.getPvp());	        		
		          }
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cab_gondola NULLS FIRST");
			query.append(order);

			List<VSicFPromociones> vSicFPromocionesLista = null;		
			
			try {
				vSicFPromocionesLista = (List<VSicFPromociones>) this.jdbcTemplate.query(query.toString(),this.rwVSicFPromocionesGondolaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vSicFPromocionesLista;
	    }
}
