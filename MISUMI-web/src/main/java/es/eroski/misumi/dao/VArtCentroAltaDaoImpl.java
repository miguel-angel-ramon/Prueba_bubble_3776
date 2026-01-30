package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.control.p12AltasCatalogoController;
import es.eroski.misumi.dao.iface.VArtCentroAltaDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;


 	
@Repository
public class VArtCentroAltaDaoImpl implements VArtCentroAltaDao{
	 private JdbcTemplate jdbcTemplate;
	// private static Logger logger = LoggerFactory.getLogger(VArtCentroAltaDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(VArtCentroAltaDaoImpl.class);
	 private static Logger logger = Logger.getLogger(VArtCentroAltaDaoImpl.class);
	 private RowMapper<VArtCentroAlta> rwVartCentroAltaMap = new RowMapper<VArtCentroAlta>() {
			public VArtCentroAlta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				
				String multipli  = resultSet.getString("MULTIPLI")  ;
				Double stockMinComer = resultSet.getDouble("stock_min_comer");

				Double imc = new Double((multipli!=null && !"".equals(multipli.trim())?multipli:"0")) * stockMinComer;

				VArtCentroAlta centroAlta = new VArtCentroAlta(new Centro(resultSet.getLong("COD_CENTRO"),null,null, null, null, null, null, null, null, null,null),resultSet.getLong("COD_ART"),
			    			resultSet.getString("DESCRIP_ART").trim(), resultSet.getString("NIVEL"),
			    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
			    		    resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
			    		    resultSet.getLong("GRUPO5"),resultSet.getString("DESCRIPCION").trim(),
			    		    resultSet.getString("PEDIR"),resultSet.getString("MARCA_MAESTRO_CENTRO"),
			    		    resultSet.getString("catalogo"),
			    		    stockMinComer==0 ? "S":"N", // Facing Cero
			    		    resultSet.getString("TP_GAMA"),resultSet.getDouble("STOCK"),
			    		    resultSet.getString("TIPO_APROV"),resultSet.getDouble("UNI_CAJA_SERV"),
			    		    resultSet.getLong("CAPACIDAD_MAX"),resultSet.getDouble("STOCK_MIN_COMER"),
			    		    resultSet.getLong("CAPACIDAD_CAB"),resultSet.getDouble("STOCK_CAB"),
			    		    resultSet.getDouble("STOCK_FIN_MIN_S"),resultSet.getLong("COD_ART_RELA"),
			    		    resultSet.getString("DESCRIP_RELA"), resultSet.getString("MULTIPLI"),
			    		    resultSet.getLong("TIPO_OFERTA"),resultSet.getString("NUMERO_OFERTA"),
			    		    resultSet.getLong("CC"),resultSet.getString("AREA"),
			    		    resultSet.getString("SECC"),resultSet.getString("CAT"),
			    		    resultSet.getString("SUBCAT"),resultSet.getDouble("UFP"),
			    		    resultSet.getString("GAMA_DISCONTINUA"),resultSet.getString("MARCA"),
			    		    resultSet.getDouble("LMIN"),resultSet.getDouble("LSF"),
			    		    resultSet.getDouble("COBERTURA_SFM"),resultSet.getDouble("VENTA_MEDIA"),
			    		    resultSet.getDouble("VENTA_ANTICIPADA"),resultSet.getString("DIAS_STOCK"),
			    		    resultSet.getString("VIDA_UTIL"),
			    		    resultSet.getLong("CAPACI_MA1"), resultSet.getDouble("FACING_MA1"),
			    		    resultSet.getLong("CAPACI_MA2"), resultSet.getDouble("FACING_MA2"),imc,
			    		    resultSet.getString("PEDIBLE") != null && resultSet.getString("PEDIBLE").toUpperCase().equals(Constantes.REF_TEXTIL_PEDIBLE_SI)? Constantes.REF_TEXTIL_PEDIBLE_SI : Constantes.REF_TEXTIL_PEDIBLE_NO,
			    		    resultSet.getString("ACCION"),
			    		    resultSet.getString("tipo_implantacion")
				    );
				//Peticion Misumi-137
				centroAlta.setTipoRotacion(resultSet.getString("TIPO_ROT"));
				centroAlta.setAlto(resultSet.getLong("ALTO"));
				centroAlta.setAncho(resultSet.getLong("ANCHO"));
				
				return centroAlta;
			}
	 	};
		    
		 private RowMapper<VArtCentroAlta> rwVartCentroAltaTexN1Map = new RowMapper<VArtCentroAlta>() {
				public VArtCentroAlta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    
					String multipli  = resultSet.getString("MULTIPLI")  ;
					Double stockMinComer = resultSet.getDouble("STOCK_MIN_COMER");

					Double imc = new Double((multipli!=null && !"".equals(multipli.trim())?multipli:"0")) * stockMinComer;

					VArtCentroAlta centroAlta = new VArtCentroAlta(new Centro(resultSet.getLong("COD_CENTRO"),null,null, null, null, null, null, null, null, null,null),resultSet.getLong("COD_ART"),
				    			resultSet.getString("DESCRIP_ART").trim(), resultSet.getString("NIVEL"),
				    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
				    		    resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
				    		    resultSet.getLong("GRUPO5"),resultSet.getString("DESCRIPCION").trim(),
				    		    resultSet.getString("PEDIR"),resultSet.getString("MARCA_MAESTRO_CENTRO"),
				    		    resultSet.getString("catalogo"),
				    		    resultSet.getString("TP_GAMA"),resultSet.getDouble("STOCK"),
				    		    resultSet.getString("TIPO_APROV"),resultSet.getDouble("UNI_CAJA_SERV"),
				    		    resultSet.getLong("CAPACIDAD_MAX"),resultSet.getDouble("STOCK_MIN_COMER"),
				    		    resultSet.getLong("CAPACIDAD_CAB"),resultSet.getDouble("STOCK_CAB"),
				    		    resultSet.getDouble("STOCK_FIN_MIN_S"),resultSet.getLong("COD_ART_RELA"),
				    		    resultSet.getString("DESCRIP_RELA"), resultSet.getString("MULTIPLI"),
				    		    resultSet.getLong("TIPO_OFERTA"),resultSet.getString("NUMERO_OFERTA"),
				    		    resultSet.getLong("CC"),resultSet.getString("AREA"),
				    		    resultSet.getString("SECC"),resultSet.getString("CAT"),
				    		    resultSet.getString("SUBCAT"),resultSet.getDouble("UFP"),
				    		    resultSet.getString("GAMA_DISCONTINUA"),resultSet.getString("MARCA"),
				    		    resultSet.getDouble("LMIN"),resultSet.getDouble("LSF"),
				    		    resultSet.getDouble("COBERTURA_SFM"),resultSet.getDouble("VENTA_MEDIA"),
				    		    resultSet.getDouble("VENTA_ANTICIPADA"),resultSet.getString("DIAS_STOCK"),
				    		    resultSet.getString("VIDA_UTIL"),
				    		    resultSet.getLong("CAPACI_MA1"), resultSet.getDouble("FACING_MA1"),
				    		    resultSet.getLong("CAPACI_MA2"), resultSet.getDouble("FACING_MA2"),
				    		    resultSet.getString("PEDIBLE") != null && resultSet.getString("PEDIBLE").toUpperCase().equals(Constantes.REF_TEXTIL_PEDIBLE_SI)? Constantes.REF_TEXTIL_PEDIBLE_SI : Constantes.REF_TEXTIL_PEDIBLE_NO, 
				    		    resultSet.getString("NIVEL_LOTE"),resultSet.getString("COD_COLOR"),
				    		    resultSet.getString("DESCR_COLOR"),resultSet.getString("COD_TALLA"),
				    		    resultSet.getString("TEMPORADA"), resultSet.getString("NUM_ORDEN"), imc,
				    		    resultSet.getString("MODELO_PROVEEDOR"), resultSet.getString("ANIO_COLECCION"),
				    		    resultSet.getString("ACCION"),
				    		    resultSet.getString("tipo_implantacion")
				    		);
					//Peticion Misumi-137
					centroAlta.setTipoRotacion(resultSet.getString("TIPO_ROT"));
					centroAlta.setAlto(resultSet.getLong("ALTO"));
					centroAlta.setAncho(resultSet.getLong("ANCHO"));
					return centroAlta;
				}
		 	};
			    
		    private RowMapper<VArtCentroAlta> rwVartCentroAltaTexN2Map = new RowMapper<VArtCentroAlta>() {
				public VArtCentroAlta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					
					String multipli  = resultSet.getString("MULTIPLI")  ;
					Double stockMinComer = resultSet.getDouble("STOCK_MIN_COMER");
					
					
					Double imc = new Double((multipli!=null && !"".equals(multipli.trim())?multipli:"0")) * stockMinComer;
					
					VArtCentroAlta centroAlta = new VArtCentroAlta(resultSet.getLong("COD_ARTICULO_LOTE"),new Centro(resultSet.getLong("COD_CENTRO"),null,null, null, null, null, null, null, null, null,null),resultSet.getLong("COD_ART"), 
				    			resultSet.getString("DESCRIP_ART").trim(), resultSet.getString("NIVEL"),
				    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
				    		    resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
				    		    resultSet.getLong("GRUPO5"),resultSet.getString("DESCRIPCION").trim(),
				    		    resultSet.getString("PEDIR"),resultSet.getString("MARCA_MAESTRO_CENTRO"),
				    		    resultSet.getString("catalogo"),
				    		    resultSet.getString("TP_GAMA"),resultSet.getDouble("STOCK"),
				    		    resultSet.getString("TIPO_APROV"),resultSet.getDouble("UNI_CAJA_SERV"),
				    		    resultSet.getLong("CAPACIDAD_MAX"),resultSet.getDouble("STOCK_MIN_COMER"),
				    		    resultSet.getLong("CAPACIDAD_CAB"),resultSet.getDouble("STOCK_CAB"),
				    		    resultSet.getDouble("STOCK_FIN_MIN_S"),resultSet.getLong("COD_ART_RELA"),
				    		    resultSet.getString("DESCRIP_RELA"), resultSet.getString("MULTIPLI"),
				    		    resultSet.getLong("TIPO_OFERTA"),resultSet.getString("NUMERO_OFERTA"),
				    		    resultSet.getLong("CC"),resultSet.getString("AREA"),
				    		    resultSet.getString("SECC"),resultSet.getString("CAT"),
				    		    resultSet.getString("SUBCAT"),resultSet.getDouble("UFP"),
				    		    resultSet.getString("GAMA_DISCONTINUA"),resultSet.getString("MARCA"),
				    		    resultSet.getDouble("LMIN"),resultSet.getDouble("LSF"),
				    		    resultSet.getDouble("COBERTURA_SFM"),resultSet.getDouble("VENTA_MEDIA"),
				    		    resultSet.getDouble("VENTA_ANTICIPADA"),resultSet.getString("DIAS_STOCK"),
				    		    resultSet.getString("VIDA_UTIL"),
				    		    resultSet.getLong("CAPACI_MA1"), resultSet.getDouble("FACING_MA1"),
				    		    resultSet.getLong("CAPACI_MA2"), resultSet.getDouble("FACING_MA2"),
				    		    resultSet.getString("PEDIBLE") != null && resultSet.getString("PEDIBLE").toUpperCase().equals(Constantes.REF_TEXTIL_PEDIBLE_SI)? Constantes.REF_TEXTIL_PEDIBLE_SI : Constantes.REF_TEXTIL_PEDIBLE_NO, 
				    		    resultSet.getString("NIVEL_LOTE"),resultSet.getString("COD_COLOR"),
				    		    resultSet.getString("DESCR_COLOR"),resultSet.getString("COD_TALLA"),
				    		    resultSet.getString("TEMPORADA"), resultSet.getString("NUM_ORDEN"),
				    		    resultSet.getString("MODELO_PROVEEDOR"),imc, resultSet.getString("ANIO_COLECCION"),
				    		    resultSet.getString("tipo_implantacion")
				    		);
					//Peticion Misumi-137
					centroAlta.setTipoRotacion(resultSet.getString("TIPO_ROT"));
					centroAlta.setAlto(resultSet.getLong("ALTO"));
					centroAlta.setAncho(resultSet.getLong("ANCHO"));
					return centroAlta;
				}
		    };
		    
		    private RowMapper<GenericExcelVO> rwExcelVArtCEntroAltaMap = new RowMapper<GenericExcelVO>() {
				public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					ResultSetMetaData rsmd = resultSet.getMetaData();
				    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet,3),
				    		Utilidades.obtenerValorExcel(resultSet,4),Utilidades.obtenerValorExcel(resultSet,5),Utilidades.obtenerValorExcel(resultSet,6),Utilidades.obtenerValorExcel(resultSet,7)
				    		,Utilidades.obtenerValorExcel(resultSet,8),Utilidades.obtenerValorExcel(resultSet,9),Utilidades.obtenerValorExcel(resultSet,10),Utilidades.obtenerValorExcel(resultSet,11)
				    		,Utilidades.obtenerValorExcel(resultSet,12),Utilidades.obtenerValorExcel(resultSet,13),Utilidades.obtenerValorExcel(resultSet,14),Utilidades.obtenerValorExcel(resultSet,15)
				    		,Utilidades.obtenerValorExcel(resultSet,16),Utilidades.obtenerValorExcel(resultSet,17),Utilidades.obtenerValorExcel(resultSet,18),Utilidades.obtenerValorExcel(resultSet,19)
				    		,Utilidades.obtenerValorExcel(resultSet,20),Utilidades.obtenerValorExcel(resultSet,21),Utilidades.obtenerValorExcel(resultSet,22),Utilidades.obtenerValorExcel(resultSet,23)
				    		,Utilidades.obtenerValorExcel(resultSet,24),Utilidades.obtenerValorExcel(resultSet,25),Utilidades.obtenerValorExcel(resultSet,26),Utilidades.obtenerValorExcel(resultSet,27)
				    		,Utilidades.obtenerValorExcel(resultSet,28),Utilidades.obtenerValorExcel(resultSet,29),Utilidades.obtenerValorExcel(resultSet,30),Utilidades.obtenerValorExcel(resultSet,31)
				    		,Utilidades.obtenerValorExcel(resultSet,32),Utilidades.obtenerValorExcel(resultSet,33),Utilidades.obtenerValorExcel(resultSet,34),Utilidades.obtenerValorExcel(resultSet,35)
				    		,Utilidades.obtenerValorExcel(resultSet,36),Utilidades.obtenerValorExcel(resultSet,37),Utilidades.obtenerValorExcel(resultSet,38),Utilidades.obtenerValorExcel(resultSet,39)
				    		,Utilidades.obtenerValorExcel(resultSet,40),Utilidades.obtenerValorExcel(resultSet,41),Utilidades.obtenerValorExcel(resultSet,42),Utilidades.obtenerValorExcel(resultSet,43)
				    		,Utilidades.obtenerValorExcel(resultSet,44),Utilidades.obtenerValorExcel(resultSet,45),Utilidades.obtenerValorExcel(resultSet,46),Utilidades.obtenerValorExcel(resultSet,47)
				    		,Utilidades.obtenerValorExcel(resultSet,48)//,Utilidades.obtenerValorExcel(resultSet,49),Utilidades.obtenerValorExcel(resultSet,50)
					    );
				}

		    };
			/*    
		    private RowMapper<VArtCentroAlta> rwExcelVArtCEntroAltaTextilMap = new RowMapper<VArtCentroAlta>() {
		    	public VArtCentroAlta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    return new VArtCentroAlta(new Centro(resultSet.getLong("COD_CENTRO"),null,null, null, null, null, null, null, null, null),resultSet.getLong("COD_ART"),
				    			resultSet.getString("DESCRIP_ART").trim(), resultSet.getString("NIVEL"),
				    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
				    		    resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
				    		    resultSet.getLong("GRUPO5"),resultSet.getString("DESCRIPCION").trim(),
				    		    resultSet.getString("PEDIR"),resultSet.getString("MARCA_MAESTRO_CENTRO"),
				    		    resultSet.getString("TP_GAMA"),resultSet.getDouble("STOCK"),
				    		    resultSet.getString("TIPO_APROV"),resultSet.getDouble("UNI_CAJA_SERV"),
				    		    resultSet.getLong("CAPACIDAD_MAX"),resultSet.getDouble("STOCK_MIN_COMER"),
				    		    resultSet.getLong("CAPACIDAD_CAB"),resultSet.getDouble("STOCK_CAB"),
				    		    resultSet.getDouble("STOCK_FIN_MIN_S"),resultSet.getLong("COD_ART_RELA"),
				    		    resultSet.getString("DESCRIP_RELA"), resultSet.getString("MULTIPLI"),
				    		    resultSet.getLong("TIPO_OFERTA"),resultSet.getString("NUMERO_OFERTA"),
				    		    resultSet.getLong("CC"),resultSet.getString("AREA"),
				    		    resultSet.getString("SECC"),resultSet.getString("CAT"),
				    		    resultSet.getString("SUBCAT"),resultSet.getDouble("UFP"),
				    		    resultSet.getString("GAMA_DISCONTINUA"),resultSet.getString("MARCA"),
				    		    resultSet.getDouble("LMIN"),resultSet.getDouble("LSF"),
				    		    resultSet.getDouble("COBERTURA_SFM"),resultSet.getDouble("VENTA_MEDIA"),
				    		    resultSet.getDouble("VENTA_ANTICIPADA"),resultSet.getString("DIAS_STOCK"),
				    		    resultSet.getString("VIDA_UTIL"),
				    		    resultSet.getLong("CAPACI_MA1"), resultSet.getDouble("FACING_MA1"),
				    		    resultSet.getLong("CAPACI_MA2"), resultSet.getDouble("FACING_MA2"),
				    		    resultSet.getString("PEDIBLE") != null && resultSet.getString("PEDIBLE").toUpperCase().equals(Constantes.REF_TEXTIL_PEDIBLE_SI)? Constantes.REF_TEXTIL_PEDIBLE_SI : Constantes.REF_TEXTIL_PEDIBLE_NO, 
				    		    resultSet.getString("NIVEL_LOTE"),resultSet.getString("COD_COLOR"),
				    		    resultSet.getString("DESCR_COLOR"),resultSet.getString("COD_TALLA"),
				    		    resultSet.getString("TEMPORADA"), resultSet.getString("NUM_ORDEN")
				    		);
				}

			    };
*/
		    
		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VArtCentroAlta> findAll(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT cod_centro, cod_art, descrip_art, nivel, grupo1, " 
			    										  + "grupo2, grupo3, grupo4, grupo5, descripcion, pedir, "
			    										  + "marca_maestro_centro, catalogo, tp_gama, stock, tipo_aprov, "
			    										  + "uni_caja_serv, capacidad_max, stock_min_comer, "
			    										  + "capacidad_cab, stock_cab, "
			    										  + "stock_fin_min_s, cod_art_rela,descrip_rela, "
			    										  + "multipli,tipo_oferta,numero_oferta, cc, "
			    										  + "area, secc, cat, subcat, ufp, gama_discontinua, "
			    										  + "marca, lmin, lsf, cobertura_sfm, venta_media, venta_anticipada, "
			    										  + "dias_stock, vida_util, capaci_ma1, facing_ma1, capaci_ma2, facing_ma2, pedible, "
			    										  + "pk_mis_accion_referencia.p_consulta_accion_ref(cod_art, cod_centro) accion,  "
			    										  + "tipo_rot, "
			    										  + "tipo_implantacion, "
			    										  + "alto, "
			    										  + "ancho "
	    										 // + " STOCK_MIN_COMER * MULTIPLI AS IMC  "
	    										+ " FROM v_art_centro_alta ");

//	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, DESCRIP_ART, NIVEL, GRUPO1, " 
//					  + " GRUPO2, GRUPO3, GRUPO4, GRUPO5, DESCRIPCION, PEDIR, "
//					  + " MARCA_MAESTRO_CENTRO, TP_GAMA, STOCK, TIPO_APROV, "
//					  + " UNI_CAJA_SERV, CAPACIDAD_MAX, STOCK_MIN_COMER, "
//					  + " CAPACIDAD_CAB, STOCK_CAB, "
//					  + " STOCK_FIN_MIN_S, COD_ART_RELA,DESCRIP_RELA, "
//					  + " MULTIPLI,TIPO_OFERTA,NUMERO_OFERTA, CC, "
//					  + " null  AREA, null SECC, null CAT, null SUBCAT "
//					+ " FROM V_ART_CENTRO_ALTA ");

	    	
	        if (vArtCentroAlta != null){
	        	if(vArtCentroAlta.getCentro()!=null && vArtCentroAlta.getCentro().getCodCentro()!=null){
	        		 where.append("AND cod_centro = ? ");
		        	 params.add(vArtCentroAlta.getCentro().getCodCentro());	        		
	        	}
	        	if(vArtCentroAlta.getNivel()!=null){
	        		 where.append("AND nivel = UPPER(?) ");
		        	 params.add(vArtCentroAlta.getNivel());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo1()!=null){
	        		 where.append("AND grupo1 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo1());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo2()!=null){
	        		 where.append("AND grupo2 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo2());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo3()!=null){
	        		 where.append("AND grupo3 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo3());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo4()!=null){
	        		 where.append("AND grupo4 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo4());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo5()!=null){
	        		 where.append("AND grupo5 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo5());	        		
	        	}
	        	if(vArtCentroAlta.getMarcaMaestroCentro()!=null){
	        		 where.append("AND marca_maestro_centro = ? ");
		        	 params.add(vArtCentroAlta.getMarcaMaestroCentro());
	        	}
	        	if(vArtCentroAlta.getCatalogo()!=null){
	        		 where.append("AND catalogo = ? ");
		        	 params.add(vArtCentroAlta.getCatalogo());
	        	}
	        	if(vArtCentroAlta.getPedir()!=null){
	        		 where.append("AND pedir = ? ");
		        	 params.add(vArtCentroAlta.getPedir());
	        	}
	        	if(vArtCentroAlta.getCodArticulo()!=null){
	        		 where.append("AND cod_art = ? ");
		        	 params.add(vArtCentroAlta.getCodArticulo());
	        	}
	        	if(vArtCentroAlta.getFacingCero()!=null && vArtCentroAlta.getFacingCero().equalsIgnoreCase("S")){
	        		 where.append("AND stock_min_comer = 0 ");
	        	}
	        	if(vArtCentroAlta.getPedible()!=null){
	        		if ( Constantes.REF_TEXTIL_PEDIBLE_NO.equals(vArtCentroAlta.getPedible()) ) {
	        			where.append("AND (pedible = ? OR pedible IS NULL) ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		} else {
	        			where.append("AND pedible = ? ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append("ORDER BY " + getMappedField(pagination.getSort(),0) + " "
							+ pagination.getAscDsc());
					query.append(order);
				}
			}

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

		    List<VArtCentroAlta> varAprpvLista = null;		

		    try {
		    	varAprpvLista = (List<VArtCentroAlta>) this.jdbcTemplate.query(query.toString(),this.rwVartCentroAltaMap, params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return varAprpvLista;
	    }

	    @Override
	    public List<VArtCentroAlta> findAllTextilN1(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer("SELECT cod_centro, cod_art, descrip_art, nivel, grupo1, " 
	    										+ "grupo2, grupo3, grupo4, grupo5, descripcion, pedir, "
	    										+ "marca_maestro_centro, catalogo, tp_gama, stock, tipo_aprov, "
	    										+ "uni_caja_serv, capacidad_max, stock_min_comer, "
	    										+ "capacidad_cab, stock_cab, "
	    										+ "stock_fin_min_s, cod_art_rela,descrip_rela, "
	    										+ "multipli, tipo_oferta,numero_oferta, cc, "
	    										+ "area, secc, cat, subcat, ufp, gama_discontinua, "
	    										+ "marca, lmin, lsf, cobertura_sfm, venta_media, venta_anticipada, "
	    										+ "dias_stock, vida_util, capaci_ma1, facing_ma1, capaci_ma2, facing_ma2, pedible, "
	    										+ "nivel_lote, cod_color, descr_color, cod_talla, temporada, num_orden, modelo_proveedor, anio_coleccion, "
	    										+ "pk_mis_accion_referencia.p_consulta_accion_ref(cod_art, cod_centro) accion,  "
	    										//  + " stock_min_comer * multipli as imc  "
	    										+ "tipo_rot, "
	    										+ "tipo_implantacion, "
	    										+ "alto, "
	    										+ "ancho "
	    										+ "FROM v_art_centro_alta_tex_n1 ");

	        if (vArtCentroAlta  != null){
	        	if(vArtCentroAlta.getCentro()!=null && vArtCentroAlta.getCentro().getCodCentro()!=null){
	        		 where.append("AND cod_centro = ? ");
		        	 params.add(vArtCentroAlta.getCentro().getCodCentro());	        		
	        	}
	        	if(vArtCentroAlta.getNivel()!=null){
	        		 where.append("AND nivel = upper(?) ");
		        	 params.add(vArtCentroAlta.getNivel());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo1()!=null){
	        		 where.append("AND grupo1 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo1());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo2()!=null){
	        		 where.append("AND grupo2 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo2());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo3()!=null){
	        		 where.append("AND grupo3 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo3());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo4()!=null){
	        		 where.append("AND grupo4 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo4());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo5()!=null){
	        		 where.append("AND grupo5 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo5());	        		
	        	}
	        	if(vArtCentroAlta.getMarcaMaestroCentro()!=null){
	        		 where.append("AND marca_maestro_centro = ? ");
		        	 params.add(vArtCentroAlta.getMarcaMaestroCentro());	        		
	        	}
	        	if(vArtCentroAlta.getCatalogo()!=null){
	        		 where.append("AND catalogo = ? ");
		        	 params.add(vArtCentroAlta.getCatalogo());
	        	}
	        	if(vArtCentroAlta.getPedir()!=null){
	        		 where.append("AND pedir = ? ");
		        	 params.add(vArtCentroAlta.getPedir());	        		
	        	}
	        	if(vArtCentroAlta.getCodArticulo()!=null){
	        		 where.append("AND cod_art = ? ");
		        	 params.add(vArtCentroAlta.getCodArticulo());	        		
	        	}
	        	if(vArtCentroAlta.getFacingCero()!=null && vArtCentroAlta.getFacingCero().equalsIgnoreCase("S")){
	        		 where.append("AND stock_min_comer = 0 ");
	        	}
	        	if(vArtCentroAlta.getPedible()!=null){
	        		if ( Constantes.REF_TEXTIL_PEDIBLE_NO.equals(vArtCentroAlta.getPedible()) ) {
	        			where.append("AND (pedible = ? OR pedible IS NULL) ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}else{
	        			where.append("AND pedible = ? ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        	}
	        	
	        	if(vArtCentroAlta.getLoteSN()!=null){
	        		if ( Constantes.REF_TEXTIL_LOTE_NO.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append("AND nivel_lote = 0 ");        		
	        		}else if ( Constantes.REF_TEXTIL_LOTE_SI.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append("AND nivel_lote > 0 ");        		
	        		}
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append("ORDER BY " + getMappedField(pagination.getSort(),0) + " "
							+ pagination.getAscDsc());
					query.append(order);
				}
			} else {
				query.append("ORDER BY grupo1, grupo2, grupo3, grupo4, grupo5, cod_art");
			}

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

		    List<VArtCentroAlta> varAprpvLista = null;		

		    try {
		    	varAprpvLista = (List<VArtCentroAlta>) this.jdbcTemplate.query(query.toString(),this.rwVartCentroAltaTexN1Map, params.toArray());
				
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				logger.error(e);
			}

		    return varAprpvLista;
	    }

	    @Override
	    public List<VArtCentroAlta> findAllTextilN2ByLote(VArtCentroAlta vArtCentroAlta) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT cod_articulo_lote, cod_centro, cod_art, descrip_art, nivel, grupo1, " 
	    										  		+ "grupo2, grupo3, grupo4, grupo5, descripcion, pedir, "
			    										+ "marca_maestro_centro, catalogo, tp_gama, stock, tipo_aprov, "
			    										+ "uni_caja_serv, capacidad_max, stock_min_comer, "
			    										+ "capacidad_cab, stock_cab, "
			    										+ "stock_fin_min_s, cod_art_rela,descrip_rela, "
			    										+ "multipli,tipo_oferta,numero_oferta, cc, "
			    										+ "area, secc, cat, subcat, ufp, gama_discontinua, "
			    										+ "marca, lmin, lsf, cobertura_sfm, venta_media, venta_anticipada, "
			    										+ "dias_stock, vida_util, capaci_ma1, facing_ma1, capaci_ma2, facing_ma2, pedible, "
			    										+ "nivel_lote, cod_color, descr_color, cod_talla, temporada, num_orden, modelo_proveedor, anio_coleccion,"
			    										+ "tipo_rot, "
			    										+ "alto, "
			    										+ "ancho, "
			    										+ "tipo_implantacion "
			    										//  + " STOCK_MIN_COMER * MULTIPLI AS IMC  "
			    										+ "FROM v_art_centro_alta_tex_n2 "
			    										+ "WHERE cod_articulo_lote = " + new Long(vArtCentroAlta.getId()) 
			    										+ "AND cod_centro = " + vArtCentroAlta.getCentro().getCodCentro() 
			    										+ " ORDER BY cod_art");
	        
	        List<VArtCentroAlta> lista = null;		

		    try {
		    	lista = (List<VArtCentroAlta>) this.jdbcTemplate.query(query.toString(),this.rwVartCentroAltaTexN2Map, params.toArray()); 
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return lista;
	    }
	    
	    @Override
	    public List<VArtCentroAlta> isReferenciaTextilN2(VArtCentroAlta vArtCentroAlta) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT cod_articulo_lote, cod_centro, cod_art, descrip_art, nivel, grupo1, " 
		    										  + "grupo2, grupo3, grupo4, grupo5, descripcion, pedir, "
		    										  + "marca_maestro_centro, catalogo, tp_gama, stock, tipo_aprov, "
		    										  + "uni_caja_serv, capacidad_max, stock_min_comer, "
		    										  + "capacidad_cab, stock_cab, "
		    										  + "stock_fin_min_s, cod_art_rela,descrip_rela, "
		    										  + "multipli,tipo_oferta,numero_oferta, cc, "
		    										  + "area, secc, cat, subcat, ufp, gama_discontinua, "
		    										  + "marca, lmin, lsf, cobertura_sfm, venta_media, venta_anticipada, "
		    										  + "dias_stock, vida_util, capaci_ma1, facing_ma1, capaci_ma2, facing_ma2, pedible, "
		    										  + "nivel_lote, cod_color, descr_color, cod_talla, temporada, num_orden, modelo_proveedor, anio_coleccion, "
		    										  + "stock_min_comer * multipli as imc,"
		    										  + "tipo_rot, "
		    										  + "tipo_implantacion "
	    										  + "FROM v_art_centro_alta_tex_n2 "
	    										  +	"WHERE ROWNUM 		= 1 "
	    										  + "AND cod_art 		= " + vArtCentroAlta.getCodArticulo()
	    										  +	" AND cod_centro 	= " + vArtCentroAlta.getCentro().getCodCentro() 
	    										  );
	        
	        List<VArtCentroAlta> lista = null;		

		    try {
		    	 lista = (List<VArtCentroAlta>) this.jdbcTemplate.query(query.toString(),this.rwVartCentroAltaTexN2Map, params.toArray()); 
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return lista;
	    }
	    
	    @Override
	    public Long findAllCont(VArtCentroAlta vArtCentroAlta) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer("SELECT COUNT(1) "
	    										+ "FROM v_art_centro_alta "
	    										);
	    	
	        if (vArtCentroAlta  != null){
	        	if(vArtCentroAlta.getCentro()!=null && vArtCentroAlta.getCentro().getCodCentro()!=null){
	        		 where.append("AND cod_centro = ? ");
		        	 params.add(vArtCentroAlta.getCentro().getCodCentro());	        		
	        	}
	        	if(vArtCentroAlta.getNivel()!=null){
	        		 where.append("AND nivel = UPPER(?) ");
		        	 params.add(vArtCentroAlta.getNivel());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo1()!=null){
	        		 where.append("AND grupo1 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo1());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo2()!=null){
	        		 where.append("AND grupo2 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo2());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo3()!=null){
	        		 where.append("AND grupo3 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo3());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo4()!=null){
	        		 where.append("AND grupo4 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo4());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo5()!=null){
	        		 where.append("AND grupo5 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo5());	        		
	        	}
	        	if(vArtCentroAlta.getMarcaMaestroCentro()!=null){
	        		 where.append("AND marca_maestro_centro = ? ");
		        	 params.add(vArtCentroAlta.getMarcaMaestroCentro());	        		
	        	}
	        	if(vArtCentroAlta.getCatalogo()!=null){
	        		 where.append("AND catalogo = ? ");
		        	 params.add(vArtCentroAlta.getCatalogo());
	        	}
	        	if(vArtCentroAlta.getPedir()!=null){
	        		 where.append("AND pedir = ? ");
		        	 params.add(vArtCentroAlta.getPedir());	        		
	        	}
	        	if(vArtCentroAlta.getCodArticulo()!=null){
	        		 where.append("AND cod_art = ? ");
		        	 params.add(vArtCentroAlta.getCodArticulo());	        		
	        	}
	        	if(vArtCentroAlta.getFacingCero()!=null && vArtCentroAlta.getFacingCero().equalsIgnoreCase("S")){
	        		 where.append("AND stock_min_comer = 0 ");
	        	}
	        	if(vArtCentroAlta.getPedible()!=null){
	        		if ( Constantes.REF_TEXTIL_PEDIBLE_NO.equals(vArtCentroAlta.getPedible()) ) {
	        			where.append("AND (pedible = ? OR pedible IS NULL) ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        		else {
	        			where.append("AND pedible = ?");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
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
	    public Long findAllTextilN1Cont(VArtCentroAlta vArtCentroAlta) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer("SELECT COUNT(1) "
	    										+ "FROM v_art_centro_alta_tex_n1 "
	    										);
	    
	    	
	        if (vArtCentroAlta  != null){
	        	if(vArtCentroAlta.getCentro()!=null && vArtCentroAlta.getCentro().getCodCentro()!=null){
	        		 where.append("AND cod_centro = ? ");
		        	 params.add(vArtCentroAlta.getCentro().getCodCentro());	        		
	        	}
	        	if(vArtCentroAlta.getNivel()!=null){
	        		 where.append("AND nivel = UPPER(?) ");
		        	 params.add(vArtCentroAlta.getNivel());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo1()!=null){
	        		 where.append("AND grupo1 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo1());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo2()!=null){
	        		 where.append("AND grupo2 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo2());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo3()!=null){
	        		 where.append("AND grupo3 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo3());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo4()!=null){
	        		 where.append("AND grupo4 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo4());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo5()!=null){
	        		 where.append("AND grupo5 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo5());	        		
	        	}
	        	if(vArtCentroAlta.getMarcaMaestroCentro()!=null){
	        		 where.append("AND marca_maestro_centro = ? ");
		        	 params.add(vArtCentroAlta.getMarcaMaestroCentro());	        		
	        	}
	        	if(vArtCentroAlta.getCatalogo()!=null){
	        		 where.append("AND catalogo = ? ");
		        	 params.add(vArtCentroAlta.getCatalogo());
	        	}
	        	if(vArtCentroAlta.getPedir()!=null){
	        		 where.append("AND pedir = ? ");
		        	 params.add(vArtCentroAlta.getPedir());	        		
	        	}
	        	if(vArtCentroAlta.getCodArticulo()!=null){
	        		 where.append("AND cod_art = ? ");
		        	 params.add(vArtCentroAlta.getCodArticulo());	        		
	        	}
	        	if(vArtCentroAlta.getFacingCero()!=null && vArtCentroAlta.getFacingCero().equalsIgnoreCase("S")){
	        		 where.append("AND stock_min_comer = 0 ");
	        	}
	        	if(vArtCentroAlta.getPedible()!=null){
	        		if ( Constantes.REF_TEXTIL_PEDIBLE_NO.equals(vArtCentroAlta.getPedible()) ) {
	        			where.append("AND (pedible = ? OR pedible IS NULL)");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}else{
	        			where.append("AND pedible = ? ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        	}
	        	
	        	if(vArtCentroAlta.getLoteSN()!=null){
	        		if ( Constantes.REF_TEXTIL_LOTE_NO.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append(" AND nivel_lote = 0");        		
	        		}else if ( Constantes.REF_TEXTIL_LOTE_SI.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append(" AND nivel_lote > 0");        		
	        		}
	        	}
	        }
	        
	        query.append(where);

		    Long cont = null;		

		    try {
		    	cont =  this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return cont;
	    }
	    
	    private String getMappedField (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("CODARTICULO")){
	  	      return "COD_ART";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPTIONART")){
	  	      return "DESCRIP_ART";
	  	  }else if(fieldName.toUpperCase().equals("MARCAMAESTROCENTRO")){
	  	      return "MARCA_MAESTRO_CENTRO";
	  	  }else if(fieldName.toUpperCase().equals("TPGAMA")){
	  	      return "TP_GAMA";
	  	  }else if(fieldName.toUpperCase().equals("TIPOAPROV")){
	  	      return "TIPO_APROV";
	  	  }else if(fieldName.toUpperCase().equals("UNICAJASERV")){
	  	      return "NVL(UNI_CAJA_SERV,0)";
	  	  }else if(fieldName.toUpperCase().equals("CAPACIDADMAX")){
	  	      return "NVL(CAPACIDAD_MAX,0) ";
	  	  } else if(fieldName.toUpperCase().equals("STOCKMINCOMER")){
	  	      return "NVL(STOCK_MIN_COMER,0) ";
	  	  } else if(fieldName.toUpperCase().equals("STOCKFINMINS")){
	  	      return "NVL(STOCK_FIN_MIN_S,0) ";
	  	  } else if(fieldName.toUpperCase().equals("STOCK")){
	  	      return "NVL(STOCK,0)";
	  	  } else if(fieldName.toUpperCase().equals("CODARTRELA")){
	  	      return "COD_ART_RELA || DECODE(NVL(COD_ART_RELA, '-1'),'-1','','-') || DESCRIP_RELA";
	  	  } else if(fieldName.toUpperCase().equals("DESCRIPRELA")){
	  	      return "DESCRIP_RELA";
	  	  } else if(fieldName.toUpperCase().equals("NUMEROOFERTA")){
	  	      return "NUMERO_OFERTA";
	  	  } else if(fieldName.toUpperCase().equals("CAPACIDADCAB")){
	  	      return "NVL(CAPACIDAD_CAB,0)";
	  	  } else if(fieldName.toUpperCase().equals("STOCKCAB")){
	  	      return "NVL(STOCK_CAB,0)";
	  	  } else if(fieldName.toUpperCase().equals("AREA")){
	  	      return "GRUPO1 ||'-'|| AREA";
	  	  } else if(fieldName.toUpperCase().equals("SECCION")){
	  	      return "GRUPO2 ||'-'||SECC";
	  	  }else if(fieldName.toUpperCase().equals("CATEGORIA")){
	  	      return "GRUPO3 ||'-'||CAT";
	  	  }else if(fieldName.toUpperCase().equals("SUBCATEGORIA")){
	  	      return "GRUPO4 ||'-'||SUBCAT";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCION")){
	  		  return "GRUPO5 ||'-'||DESCRIPCION";
	  	  }else if(fieldName.toUpperCase().equals("VENTAMEDIA")){
	  	      return "NVL(VENTA_MEDIA,0)";
	  	  }else if(fieldName.toUpperCase().equals("VENTAANTICIPADA")){
	  	      return "NVL(VENTA_ANTICIPADA,0)";
	  	  }else if(fieldName.toUpperCase().equals("GAMADISCONTINUA")){
	  	      return "GAMA_DISCONTINUA";
	  	  }else if(fieldName.toUpperCase().equals("COBERTURASFM")){
	  	      return "COBERTURA_SFM";
	  	  }else if(fieldName.toUpperCase().equals("DIASSTOCK")){
	  	      return "DIAS_STOCK";
	  	  }else if(fieldName.toUpperCase().equals("VIDAUTIL")){
	  	      return "VIDA_UTIL";
	  	  }else if(fieldName.toUpperCase().equals("LIMITEINFERIOR")){
	  	      return "NVL(LMIN,0)";
	  	  }else if(fieldName.toUpperCase().equals("LIMITESUPERIOR")){
	  	      return "NVL(LSF,0)";
	  	  } else if(fieldName.toUpperCase().equals("CAPACIMA1")){
	  	      return "NVL(CAPACI_MA1,0)";
	  	  } else if(fieldName.toUpperCase().equals("FACINGMA1")){
	  	      return "NVL(FACING_MA1,0)";
	  	  } else if(fieldName.toUpperCase().equals("CAPACIMA2")){
	  	      return "NVL(CAPACI_MA2,0)";
	  	  } else if(fieldName.toUpperCase().equals("FACINGMA2")){
	  	      return "NVL(FACING_MA2,0)";
	  	  } else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
	  	      return "MODELO_PROVEEDOR";
	  	  } else if(fieldName.toUpperCase().equals("TALLA")){
	  	      return "COD_TALLA";
	  	  }else if(fieldName.toUpperCase().equals("COLOR")){
	  	      return "DESCR_COLOR";
	  	  }else if(fieldName.toUpperCase().equals("ANIOCOLECCION")){
	  	      return "ANIO_COLECCION";
	  	  }else if(fieldName.toUpperCase().equals("ORDEN")){
	  	      return "ORDEN";
	  	  }else if(fieldName.toUpperCase().equals("PEDIBLE")){
	  	      return "nvl(PEDIBLE,'" + Constantes.REF_TEXTIL_PEDIBLE_NO + "')";
	  	  }else if(fieldName.toUpperCase().equals("IMC")){
	  		return "STOCK_MIN_COMER * MULTIPLI";
	  	  }else if(fieldName.toUpperCase().equals("ACCION")){
	  	      return "ACCION";
	  	  }else if(fieldName.toUpperCase().equals("TIPOROTACION")){
	  	      return "TIPO_ROT";
	  	  }else if(fieldName.toUpperCase().equals("ALTO")){
	  	      return "nvl(ALTO,0)";
	  	  }else if(fieldName.toUpperCase().equals("ANCHO")){
	  	      return "nvl(ANCHO,0)";
	  	  }else if(fieldName.toUpperCase().equals("TIPOIMPLANTACION")){
	  	      return "tipo_implantacion";
	  	  }else {
	  	      return fieldName;
	  	  }
	  	}
	    
	    @Override
	    public List<GenericExcelVO> findAllExcel(VArtCentroAlta vArtCentroAlta,String[] columnModel) throws Exception {
	    	
	    	boolean codArtIncluido = false; //Control para saber si viene el campo de código de artículo
	    	String nombreCampoColmodel = "";
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//columnModel
	    	int j=1;
	    	
	    	String fields="null";
	    	List<String> listColumns = Arrays.asList(columnModel);
	    	for(int i=0; i<listColumns.size();i++){
	    		nombreCampoColmodel = this.getMappedField(listColumns.get(i),1).toUpperCase();
	    		if ("COD_ART".equals(nombreCampoColmodel)){
	    			codArtIncluido = true;
	    		}
	    		//Ignorar campos de textil, se calculan a a partir del plsql
	    		if (!"TEMPORADA".equals(nombreCampoColmodel) && !"ANIO_COLECCION".equals(nombreCampoColmodel) && !"MODELOPROVEEDOR".equals(nombreCampoColmodel)
	    				 && !"COD_TALLA".equals(nombreCampoColmodel) && !"DESCR_COLOR".equals(nombreCampoColmodel) && !"LOTE".equals(nombreCampoColmodel)
	    				 && !"MODELO_PROVEEDOR".equals(nombreCampoColmodel) && !"ORDEN".equals(nombreCampoColmodel)){
		    		//Ignorar el campo de acción, se calcula a partir de una función PLSQL
		    		if (!"ACCION".equals(nombreCampoColmodel)){
			    		fields = fields + ", " + nombreCampoColmodel;	    			
		    		}else{
		    			fields = fields + ", pk_mis_accion_referencia.P_CONSULTA_ACCION_REF(COD_ART, COD_CENTRO) ACCION ";
		    		}
	    		}else{
	    			fields = fields + ", null";
	    		}
	    		j++;
	    	}
	    	while (j<=55){//TODO elena era 41
	    		//Si el campo cod_art no viene en el colmodel se incluye para poder buscar el CC. Se pone el último para poder eliminarlo
	    		//de forma fácil tras el tratamiento del CC tratamiento posterior
	    		if (j==55 && !codArtIncluido){
	    			fields = fields + ", COD_ART ";
	    		}else{
	    			fields = fields + ", null ";
	    		}
	    		j++;
	    	}
	    	
	    	StringBuffer query = new StringBuffer("SELECT ");
	    	query.append(fields); 
	    	
	    	if ((vArtCentroAlta.getGrupo1()!=null) && (vArtCentroAlta.getGrupo1().toString().equals("3"))){
	    		query.append( "FROM v_art_centro_alta_tex_n1 "); 
	    	} else {
	    		query.append( "FROM v_art_centro_alta "); 
	    	}
	    	
	        if (vArtCentroAlta  != null){
	        	if(vArtCentroAlta.getCentro()!=null && vArtCentroAlta.getCentro().getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vArtCentroAlta.getCentro().getCodCentro());	        		
	        	}
	        	if(vArtCentroAlta.getNivel()!=null){
	        		 where.append(" AND nivel = UPPER(?) ");
		        	 params.add(vArtCentroAlta.getNivel());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo1());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo2());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo3());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo4());	        		
	        	}
	        	if(vArtCentroAlta.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vArtCentroAlta.getGrupo5());	        		
	        	}
	        	if(vArtCentroAlta.getMarcaMaestroCentro()!=null){
	        		 where.append(" AND marca_maestro_centro = ? ");
		        	 params.add(vArtCentroAlta.getMarcaMaestroCentro());	        		
	        	}
	        	if(vArtCentroAlta.getCatalogo()!=null){
	        		 where.append("AND catalogo = ? ");
		        	 params.add(vArtCentroAlta.getCatalogo());
	        	}	        	
	        	if(vArtCentroAlta.getPedir()!=null){
	        		 where.append(" AND pedir = ? ");
		        	 params.add(vArtCentroAlta.getPedir());	        		
	        	}
	        	if(vArtCentroAlta.getCodArticulo()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vArtCentroAlta.getCodArticulo());	        		
	        	}
	        	if(vArtCentroAlta.getFacingCero()!=null && vArtCentroAlta.getFacingCero().equalsIgnoreCase("S")){
	        		 where.append("AND stock_min_comer = 0 ");
	        	}
	        	
	        	if(vArtCentroAlta.getPedible()!=null){
	        		if ( Constantes.REF_TEXTIL_PEDIBLE_NO.equals(vArtCentroAlta.getPedible()) ) {
	        			where.append(" AND (pedible = ? OR pedible IS NULL) ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        		else {
	        			where.append(" AND pedible = ? ");
	        			params.add(vArtCentroAlta.getPedible());	        		
	        		}
	        	}
	        	
	        	if(vArtCentroAlta.getLoteSN()!=null){
	        		if ( Constantes.REF_TEXTIL_LOTE_NO.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append("AND nivel_lote = 0 ");        		
	        		}else if ( Constantes.REF_TEXTIL_LOTE_SI.equals(vArtCentroAlta.getLoteSN()) ) {
	        			where.append("AND nivel_lote > 0 ");        		
	        		}
	        	}
	        }
	        
	        query.append(where);
	        
	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append("ORDER BY grupo1, grupo2, grupo3, grupo4, grupo5, cod_art");
			query.append(order);

			List<GenericExcelVO> lista = null;
		    
		    try {
		    	lista =  this.jdbcTemplate.query(query.toString(),this.rwExcelVArtCEntroAltaMap, params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return lista;
	    }
	    
}
