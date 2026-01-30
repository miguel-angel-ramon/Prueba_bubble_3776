package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VMisCampanaOferDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.SeguimientoCampanasDetalle;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VMisCampanaOferDaoImpl implements VMisCampanaOferDao {
	private JdbcTemplate jdbcTemplate;
	//private static Logger logger = Logger.getLogger(VMisCampanaOferDaoImpl.class);

	private RowMapper<SeguimientoCampanas> rwSeguimientoCampanaOferMap = new RowMapper<SeguimientoCampanas>() {
		public SeguimientoCampanas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new 	SeguimientoCampanas(resultSet.getString("IDENT_GRUPO"), resultSet.getString("DESCRIPCION_GRUPO"),
					resultSet.getString("SERVIDO_PENDIENTE"), resultSet.getString("NO_SERVIDO"), resultSet.getString("VENTAS_PREVISION"),
					resultSet.getString("IDENTIFICADOR_GRUPO"), resultSet.getString("ANO_OFERTA_GRUPO")+"-"+resultSet.getString("NUM_OFERTA_GRUPO"), 
					resultSet.getString("TIPO_O_C_GRUPO"), resultSet.getString("F_INICIO_FORMATEADA"), null,
					resultSet.getString("F_FIN_FORMATEADA"), null, resultSet.getLong("GRUPO1_GRUPO"), resultSet.getString("AREA"),
					resultSet.getLong("GRUPO2_GRUPO"), resultSet.getString("SECCION"), resultSet.getLong("GRUPO3_GRUPO"),
					resultSet.getString("CATEGORIA"), resultSet.getLong("GRUPO4_GRUPO"),
					resultSet.getString("SUBCATEGORIA"), null, null,
					resultSet.getLong("COD_ART_GRUPO"), resultSet.getString("DESCRIPCION_ART"), resultSet.getInt("NIVEL_GRUPO"), resultSet.getString("PARENTIDENT_GRUPO"), (resultSet.getInt("NIVEL_GRUPO") > 2), false,
					false, null);
 		}
	};

	private RowMapper<SeguimientoCampanasDetalle> rwSeguimientoCampanaOferPopupMap = new RowMapper<SeguimientoCampanasDetalle>() {

		public SeguimientoCampanasDetalle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			Centro centro = new Centro();
			centro.setCodCentro(resultSet.getLong("COD_CENTRO_GRUPO"));
		    return new 	SeguimientoCampanasDetalle(centro, resultSet.getLong("COD_ART_GRUPO"), resultSet.getString("DESCRIPCION_GRUPO"),
		    		resultSet.getString("SERVIDO_PENDIENTE"), resultSet.getString("NO_SERVIDO"), resultSet.getString("VENTAS_PREVISION"),
		    		resultSet.getString("STOCK"), resultSet.getString("COLOR"),resultSet.getString("TALLA"),resultSet.getString("MODELOPROVEEDOR"));
 		}
	};

	private RowMapper<GenericExcelVO> rwExcelVMisCampanaOferMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet, 3),
		    		Utilidades.obtenerValorExcel(resultSet, 4),Utilidades.obtenerValorExcel(resultSet, 5),Utilidades.obtenerValorExcel(resultSet, 6),Utilidades.obtenerValorExcel(resultSet, 7)
		    		,Utilidades.obtenerValorExcel(resultSet, 8),Utilidades.obtenerValorExcel(resultSet, 9),Utilidades.obtenerValorExcel(resultSet, 10),Utilidades.obtenerValorExcel(resultSet, 11)
		    		,Utilidades.obtenerValorExcel(resultSet, 12),Utilidades.obtenerValorExcel(resultSet, 13),Utilidades.obtenerValorExcel(resultSet, 14),Utilidades.obtenerValorExcel(resultSet, 15)
		    		,Utilidades.obtenerValorExcel(resultSet, 16),Utilidades.obtenerValorExcel(resultSet, 17),Utilidades.obtenerValorExcel(resultSet, 18),Utilidades.obtenerValorExcel(resultSet, 19)
		    		,Utilidades.obtenerValorExcel(resultSet, 20),Utilidades.obtenerValorExcel(resultSet, 21),Utilidades.obtenerValorExcel(resultSet, 22),Utilidades.obtenerValorExcel(resultSet, 23)
				    ,Utilidades.obtenerValorExcel(resultSet, 24),Utilidades.obtenerValorExcel(resultSet, 25),Utilidades.obtenerValorExcel(resultSet, 26),Utilidades.obtenerValorExcel(resultSet, 27)
				    ,Utilidades.obtenerValorExcel(resultSet, 28),Utilidades.obtenerValorExcel(resultSet, 29),Utilidades.obtenerValorExcel(resultSet, 30),Utilidades.obtenerValorExcel(resultSet, 31)
				    ,Utilidades.obtenerValorExcel(resultSet, 32),Utilidades.obtenerValorExcel(resultSet, 33),Utilidades.obtenerValorExcel(resultSet, 34),Utilidades.obtenerValorExcel(resultSet, 35)
		    		,Utilidades.obtenerValorExcel(resultSet,36),Utilidades.obtenerValorExcel(resultSet,37),Utilidades.obtenerValorExcel(resultSet,38),Utilidades.obtenerValorExcel(resultSet,39)
		    		,Utilidades.obtenerValorExcel(resultSet,40),Utilidades.obtenerValorExcel(resultSet,41),Utilidades.obtenerValorExcel(resultSet,42),Utilidades.obtenerValorExcel(resultSet,43)
		    		,Utilidades.obtenerValorExcel(resultSet,44),Utilidades.obtenerValorExcel(resultSet,45),Utilidades.obtenerValorExcel(resultSet,46),Utilidades.obtenerValorExcel(resultSet,47)
		    		,Utilidades.obtenerValorExcel(resultSet,48)//,Utilidades.obtenerValorExcel(resultSet,49),Utilidades.obtenerValorExcel(resultSet,50)
			    );
		}

    };
    
	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	@Override
	public List<SeguimientoCampanas> findAllReferenciasCampanaOfer(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MAX(COD_ART) cod_art_grupo, MAX(NIVEL) nivel_grupo, MAX(REPLACE(IDENT,' ','_')) ident_grupo, MAX(REPLACE(PARENTIDENT, ' ','_')) parentident_grupo, MAX(COD_CENTRO) cod_centro_grupo, TO_CHAR(MAX(F_INICIO),'DDMMYYYY') F_INICIO_FORMATEADA,  TO_CHAR(MAX(F_FIN),'DDMMYYYY') F_FIN_FORMATEADA," + 
    												"MAX(GRUPO1) grupo1_grupo, MAX(GRUPO2) grupo2_grupo, MAX(GRUPO3) grupo3_grupo, MAX(GRUPO4) grupo4_grupo, MAX(DESCRIPCION) descripcion_grupo, MAX(TIPO_O_C) tipo_o_c_grupo, MAX(IDENTIFICADOR) identificador_grupo," +
    												"MAX(ANO_OFERTA) ano_oferta_grupo, MAX(NUM_OFERTA) num_oferta_grupo, " +
    												"ltrim(to_char(SUM(NVL(CANT_IMP,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_SERVIDA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_PREVISTA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) servido_pendiente, " +
    												"SUM(NVL(NSR_NUMERO,0))||'**'||ltrim(to_char(SUM(NVL(NSR_UNIDADES,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) no_servido, " +
    												"ltrim(to_char(SUM(NVL(VENTA_TOTAL,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_PREVISTA_VENTA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) ventas_prevision, " +
    									        	//Obtención de descripción del artículo
    									        	" MAX(NVL((SELECT ar.descrip_art  FROM v_datos_diario_art ar " +
					                    			" WHERE ar.cod_art = camp_ofe.cod_art),'')) descripcion_art, " + //Artículo
    									        	//Obtención de descripciones de area, sección, categoría y subcategoría
    									        	" MAX(NVL((SELECT a1.descripcion  FROM v_agru_comer_ref a1 " +
							                    			" WHERE a1.nivel = 'I1' AND camp_ofe.grupo1 = a1.grupo1 ),'')) area, " + //AREA
							                    	" MAX(NVL((SELECT a2.descripcion FROM v_agru_comer_ref a2 " +
							                    	 		" WHERE a2.nivel = 'I2' AND camp_ofe.grupo1 = a2.grupo1 " +
							                    	 		" AND camp_ofe.grupo2 = a2.grupo2),'')) seccion, " + //SECCION
							                    	" MAX(NVL((SELECT a3.descripcion FROM v_agru_comer_ref a3 " +
							                    	 		" WHERE a3.nivel = 'I3' AND camp_ofe.grupo1 = a3.grupo1 " +
							                    	 		" AND camp_ofe.grupo2 = a3.grupo2 AND camp_ofe.grupo3 = a3.grupo3 ),'')) categoria, " + //CATEGORIA
							                    	" MAX(NVL((SELECT a4.descripcion FROM v_agru_comer_ref a4 " +
							                    	 		" WHERE a4.nivel = 'I4' AND camp_ofe.grupo1 = a4.grupo1 " +
							                    	 		" AND camp_ofe.grupo2 = a4.grupo2 AND camp_ofe.grupo3 = a4.grupo3 AND camp_ofe.grupo4 = a4.grupo4),'')) subcategoria " + //SUBCATEGORIA
    										" FROM V_MIS_CAMPANA_OFER camp_ofe ");
    
        if (vMisCampanaOfer  != null){
        	if(vMisCampanaOfer.getIdSesion()!=null){
        		 where.append(" AND IDSESION = ? ");
	        	 params.add(vMisCampanaOfer.getIdSesion());	        		
        	}
        	if(vMisCampanaOfer.getNivel()!=null){
       		 	 where.append(" AND NIVEL = ? ");
	        	 params.add(vMisCampanaOfer.getNivel());	        		
        	}
        	if(vMisCampanaOfer.getIdent()!=null){
       		 	 where.append(" AND REPLACE(IDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
	        	 params.add(vMisCampanaOfer.getIdent());	        		
        	}
        	if(vMisCampanaOfer.getParentident()!=null){
          		 where.append(" AND REPLACE(PARENTIDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
   	        	 params.add(vMisCampanaOfer.getParentident());	        		
           	}
        	if(vMisCampanaOfer.getIdentificador()!=null){
         		 where.append(" AND IDENTIFICADOR = ? ");
  	        	 params.add(vMisCampanaOfer.getIdentificador());	        		
          	}
        	if(vMisCampanaOfer.getCodCentro()!=null){
         		 where.append(" AND COD_CENTRO = ? ");
  	        	 params.add(vMisCampanaOfer.getCodCentro());	        		
          	}
        	if(vMisCampanaOfer.getGrupo1()!=null){
    		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo1());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo2()!=null){
   		 	     where.append(" AND GRUPO2 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo2());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vMisCampanaOfer.getGrupo3());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo4()!=null){
	     		 where.append(" AND GRUPO4 = ? ");
		         params.add(vMisCampanaOfer.getGrupo4());	        		
	       	}
        	if(vMisCampanaOfer.getDescripcion()!=null){
      		 	 where.append(" AND DESCRIPCION = upper(?) ");
	        	 params.add(vMisCampanaOfer.getDescripcion());	        		
        	}
        	if(vMisCampanaOfer.getTipoOC()!=null){
     		 	 where.append(" AND TIPO_O_C = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipoOC());	        		
        	}
        	if(vMisCampanaOfer.getTipo()!=null){
    		 	 where.append(" AND TIPO = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipo());	        		
        	}
	       	if(vMisCampanaOfer.getAnoOferta()!=null){
	     		 where.append(" AND ANO_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getAnoOferta());	        		
	       	}
	       	if(vMisCampanaOfer.getNumOferta()!=null){
	     		 where.append(" AND NUM_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getNumOferta());	        		
	       	}
        	if(vMisCampanaOfer.getFInicio()!=null){
	       		 where.append(" AND TRUNC(F_INICIO) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFInicio());	        		
	       	}
        	if(vMisCampanaOfer.getFFin()!=null){
	       		 where.append(" AND TRUNC(F_FIN) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFFin());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevista()!=null){
	     		 where.append(" AND CANT_PREVISTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevista());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevistaVenta()!=null){
	     		 where.append(" AND CANT_PREVISTA_VENTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevistaVenta());	        		
	       	}
        	if(vMisCampanaOfer.getVentaMedia()!=null){
	     		 where.append(" AND VENTA_MEDIA = ? ");
		         params.add(vMisCampanaOfer.getVentaMedia());	        		
	       	}
        	if(vMisCampanaOfer.getVentaTotal()!=null){
	     		 where.append(" AND VENTA_TOTAL = ? ");
		         params.add(vMisCampanaOfer.getVentaTotal());	        		
	       	}
        	if(vMisCampanaOfer.getNsrNumero()!=null){
	     		 where.append(" AND NSR_NUMERO = ? ");
		         params.add(vMisCampanaOfer.getNsrNumero());	        		
	       	}
        	if(vMisCampanaOfer.getNsrUnidades()!=null){
	     		 where.append(" AND NSR_UNIDADES = ? ");
		         params.add(vMisCampanaOfer.getNsrUnidades());	        		
	       	}
        	if(vMisCampanaOfer.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(vMisCampanaOfer.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND COD_ART IN ( ").append(referencias).append(" )");
	       	}
        }
        
        query.append(where);

        StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        if (vMisCampanaOfer.getTipoOC()!=null && Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(vMisCampanaOfer.getTipoOC())){
        	groupBy.append(" group by nivel, ident, parentident, cod_centro, identificador ");
        }else{
        	groupBy.append(" group by nivel, ident, parentident, cod_centro, ano_oferta, num_oferta ");
        }

		query.append(groupBy);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        if (vMisCampanaOfer.getTipoOC()!=null && Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(vMisCampanaOfer.getTipoOC())){
            order.append(" order by MAX(f_inicio), MAX(f_fin), MAX(identificador), MAX(grupo1), MAX(grupo2), MAX(grupo3), MAX(grupo4) ");
        }else{
            order.append(" order by MAX(f_inicio), MAX(f_fin), MAX(ano_oferta), MAX(num_oferta),MAX(grupo1), MAX(grupo2), MAX(grupo3), MAX(grupo4) ");
        }
		query.append(order);

		List<SeguimientoCampanas> seguimientoCampanasLista = null;		
		
		try {
			seguimientoCampanasLista = (List<SeguimientoCampanas>) this.jdbcTemplate.query(query.toString(),this.rwSeguimientoCampanaOferMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}

	    return seguimientoCampanasLista;
	}
	
    @Override
    public VMisCampanaOfer recargaDatosCampanasOfer(final VMisCampanaOfer vMisCampanaOfer, final List<Long> listaReferencias) throws Exception  {
    	
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		VMisCampanaOfer misCampanaOferSalida = new VMisCampanaOfer();
		Long codError = new Long (0);

		declaredParameters.add(new SqlParameter("p_cod_centro", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_tipo", Types.VARCHAR));
		declaredParameters.add(new SqlParameter("p_idsesion", Types.VARCHAR));
		declaredParameters.add(new SqlParameter("p_grupo_art", OracleTypes.STRUCT, "MIS_R_ARTICULO_REG"));
		declaredParameters.add(new SqlParameter("p_identificador", Types.VARCHAR));
		declaredParameters.add(new SqlOutParameter("p_cod_error_grupo", Types.INTEGER));
		
		try{
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {
	
			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_MIS_PROCESOS.P_MIS_CONSULTA_CAMPANA_OFERTA(?, ?, ?, ?, ?, ?)}");
	
			        stmnt.setInt(1, vMisCampanaOfer.getCodCentro().intValue());
		        	stmnt.setString(2, vMisCampanaOfer.getTipoOC());
			        stmnt.setString(3, vMisCampanaOfer.getIdSesion());
			        
			        //Crear estructura para recarga
                	STRUCT itemConsulta = crearEstructuraRecargaDatosCampanasOfer(listaReferencias, con);
                	stmnt.setObject(4, itemConsulta);

                	String identificador = null;
                	if (Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(vMisCampanaOfer.getTipoOC())){
				        if (vMisCampanaOfer.getIdentificador() != null && !"".equals(vMisCampanaOfer.getIdentificador())){
				        	identificador =  vMisCampanaOfer.getIdentificador();
				        }
					}else{
						if(vMisCampanaOfer.getAnoOferta() != null && vMisCampanaOfer.getNumOferta() != null){
							identificador =  vMisCampanaOfer.getAnoOferta()+"-"+vMisCampanaOfer.getNumOferta();
						}
					}
			        if (identificador != null){
			        	stmnt.setString(5, identificador);
			        }else{
			        	stmnt.setNull(5, OracleTypes.NULL);
			        }
			        stmnt.registerOutParameter(6, Types.INTEGER);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			
			misCampanaOferSalida.setCodCentro(vMisCampanaOfer.getCodCentro());
			misCampanaOferSalida.setIdSesion(vMisCampanaOfer.getIdSesion());
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error_grupo")!=null && !"0".equals(mapaResultados.get("p_cod_error_grupo").toString())){
					if (mapaResultados.get("p_cod_error_grupo") != null){
						codError = Long.parseLong(mapaResultados.get("p_cod_error_grupo").toString());
					}
				}
			}
			misCampanaOferSalida.setCodError(codError);
		}catch (Exception e) {
			//Si surge algún error hay que informarlo 
			misCampanaOferSalida.setCodError(new Long(1));
		}		
	    return misCampanaOferSalida;
    }

    private String getMappedField (String fieldName, int target, String empujeLabel) {
    	/*P-56402
    	 * BICUGUAL
    	 * Separo los datos de la seleccion de REFERENCIA para obtener ahora REFERENCIA Y DENOMINACION 
    	 */  
		if (fieldName.toUpperCase().equals("REFERENCIA")) {
			return "MAX(NVL((SELECT ar.cod_art FROM v_datos_diario_art ar WHERE ar.cod_art = camp_ofe.cod_art),''))";
		} else if (fieldName.toUpperCase().equals("DENOMINACION")) {
			return "MAX(NVL((SELECT ar.descrip_art  FROM v_datos_diario_art ar WHERE ar.cod_art = camp_ofe.cod_art),''))";
		} else if (fieldName.toUpperCase().equals("SERVIDOPENDIENTE")) {
			return "ROUND(SUM(NVL(CANT_IMP,0)),1), ROUND(SUM(NVL(CANT_SERVIDA,0)),1), ROUND(SUM(NVL(CANT_PREVISTA,0)),1)";
		} else if (fieldName.toUpperCase().equals("NOSERVIDO")) {
			return "ROUND(SUM(NVL(NSR_NUMERO,0)),1), ROUND(SUM(NVL(NSR_UNIDADES,0)),1)";
		} else if (fieldName.toUpperCase().equals("VENTASPREVISION")) {
			return "ROUND(SUM(NVL(VENTA_TOTAL,0)),1), ROUND(SUM(NVL(CANT_PREVISTA_VENTA,0)),1)";
		} else if (fieldName.toUpperCase().equals("STOCK")) {
			return "ROUND(SUM(NVL(camp_ofe.stock,0)),1), ROUND(SUM(NVL(camp_ofe.stock,0)) / decode( NVL( max(venta_media),0), 0, 1, NVL( max(venta_media),0)),1)";
		} else if (fieldName.toUpperCase().equals("TALLA")) {
			return "MAX(DESCR_TALLA)";
		} else if (fieldName.toUpperCase().equals("COLOR")) {
			return "MAX(DESCR_COLOR)";
		} else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")) {
			return "MAX(MODELO_PROVEEDOR)";
		} else {
			return fieldName;
		}
    }

    private String getMappedFieldSort (String fieldName, String sortAscDesc) {
    	  if(fieldName.toUpperCase().equals("REFERENCIA")){
    		  return "camp_ofe.COD_ART " + sortAscDesc;
          }else if(fieldName.toUpperCase().equals("SERVIDOPENDIENTE")){
	  	      return "SUM(NVL(CANT_SERVIDA,0)) / DECODE(SUM(NVL(CANT_PREVISTA,0)), 0, 1, SUM(NVL(CANT_PREVISTA,0))) " + sortAscDesc + ", SUM(NVL(CANT_SERVIDA,0)) " + sortAscDesc + ", SUM(NVL(CANT_PREVISTA,0)) " + sortAscDesc ;
	  	  }else if(fieldName.toUpperCase().equals("NOSERVIDO")){
	  		  return "SUM(NVL(NSR_NUMERO,0)) " + sortAscDesc;
	  	  }else if(fieldName.toUpperCase().equals("VENTASPREVISION")){
	  		  return "SUM(NVL(VENTA_TOTAL,0)) / DECODE(SUM(NVL(CANT_PREVISTA_VENTA,0)), 0, 1, SUM(NVL(CANT_PREVISTA_VENTA,0))) " + sortAscDesc + ", SUM(NVL(VENTA_TOTAL,0)) " + sortAscDesc + ", SUM(NVL(CANT_PREVISTA_VENTA,0)) " + sortAscDesc;
	  	  }else if(fieldName.toUpperCase().equals("STOCK")){
	  	      return "SUM(NVL(camp_ofe.stock,0)) / decode( NVL( max(venta_media),0), 0, 1, NVL( max(venta_media),0)) " + sortAscDesc;
	  	  }else {
	  	      return fieldName;
	  	  }
    }

	@Override
	public List<GenericExcelVO> findAllReferenciasCampanaOferExcel(
			VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias, String[] columnModel, String empujeLabel, String textil, boolean mostrarColumnas)
			throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	//columnModel
    	String fields="null";
    	List<String> listColumns = Arrays.asList(columnModel);
    	for(int i=0; i<listColumns.size();i++){
    		fields = fields + ", " +this.getMappedField(listColumns.get(i),1, empujeLabel);
    	}
    	if(textil != null){
    		//numero de columnas y orden fijo
    		fields = "null, " +
		        	//Obtención de descripciones de area, sección, categoría y subcategoría
//		        	" camp_ofe.grupo2, " +
		        	" MAX(NVL((SELECT a2.grupo2||'-'||a2.descripcion FROM v_agru_comer_ref a2 " +
                	 		" WHERE a2.nivel = 'I2' AND camp_ofe.grupo1 = a2.grupo1 " +
                	 		" AND camp_ofe.grupo2 = a2.grupo2),'')) seccion, " + //SECCION
//					" camp_ofe.grupo3, " +
                	" MAX(NVL((SELECT a3.grupo3||'-'||a3.descripcion FROM v_agru_comer_ref a3 " +
                	 		" WHERE a3.nivel = 'I3' AND camp_ofe.grupo1 = a3.grupo1 " +
                	 		" AND camp_ofe.grupo2 = a3.grupo2 AND camp_ofe.grupo3 = a3.grupo3 ),'')) categoria, " + //CATEGORIA
//					" camp_ofe.grupo4, " +
                	" MAX(NVL((SELECT a4.grupo4||'-'||a4.descripcion FROM v_agru_comer_ref a4 " +
                	 		" WHERE a4.nivel = 'I4' AND camp_ofe.grupo1 = a4.grupo1 " +
                	 		" AND camp_ofe.grupo2 = a4.grupo2 AND camp_ofe.grupo3 = a4.grupo3 AND camp_ofe.grupo4 = a4.grupo4),'')) subcategoria, " + //SUBCATEGORIA
//					" camp_ofe.grupo5 " +
                	" MAX(NVL((SELECT a5.grupo5||'-'||a5.descripcion FROM v_agru_comer_ref a5 " +
		        	 		" WHERE a5.nivel = 'I5' AND camp_ofe.grupo1 = a5.grupo1 " +
		        	 		" AND camp_ofe.grupo2 = a5.grupo2 AND camp_ofe.grupo3 = a5.grupo3 AND camp_ofe.grupo4 = a5.grupo4 AND camp_ofe.grupo5 = a5.grupo5),'')) segmento " + //SEGMENTO
    				", MAX(MODELO_PROVEEDOR) " +
    				", MAX(DESCR_COLOR) " +
    				", MAX(DESCR_TALLA) " +
    				", NVL(camp_ofe.cod_art, ''), NVL((SELECT ar.descrip_art  FROM v_datos_diario_art ar WHERE ar.cod_art = camp_ofe.cod_art), '')" +
    				", ROUND(SUM(NVL(CANT_IMP,0)),1) ";
    				
    				if (mostrarColumnas){
    					fields += ", ROUND(SUM(NVL(CANT_IMP_CAJAS,0)),1) ";
    				}else{
    					fields += ", 0 ";
    				}
    				
					fields += ", ROUND(SUM(NVL(CANT_SERVIDA,0)),1) ";
    				
							
					if (mostrarColumnas){
						fields += ", ROUND(SUM(NVL(CANT_SERVIDA_CAJAS,0)),1) ";
    				}else{
    					fields += ", 0 ";
					}
    					
    				fields += ", ROUND(SUM(NVL(CANT_PREVISTA,0)),1)";
    				
					if (mostrarColumnas){
						fields += ", ROUND(SUM(NVL(CANT_PREVISTA_CAJAS,0)),1) ";
    				}else{
    					fields += ", 0 ";
					}
    					
    				fields += ", ROUND(SUM(NVL(NSR_NUMERO,0)),1), ROUND(SUM(NVL(NSR_UNIDADES,0)),1)";
    				
					if (mostrarColumnas){
						fields += ", ROUND(SUM(NVL(NSR_CAJAS,0)),1) ";
    				}else{
    					fields += ", 0 ";
					}
    					
    				fields += ", ROUND(SUM(NVL(VENTA_TOTAL,0)),1), ROUND(SUM(NVL(CANT_PREVISTA_VENTA,0)),1) " +
    				", ROUND(SUM(NVL((SELECT SUM(STOCK) " +
										 " FROM V_STOCK_CAMPANAS SC " +
										 " WHERE SC.COD_LOC      = camp_ofe.COD_CENTRO " +
										 "   AND SC.COD_ARTICULO = camp_ofe.COD_ART),0)),1)" +
    				", ROUND(SUM(NVL((SELECT SUM(STOCK) " +
										 " FROM V_STOCK_CAMPANAS SC " +
										 " WHERE SC.COD_LOC      = camp_ofe.COD_CENTRO " +
										 "   AND SC.COD_ARTICULO = camp_ofe.COD_ART),0)) / decode( NVL( max(venta_media),0), 0, 1, NVL( max(venta_media),0)),1) " +
					", MAX(SUBSTR(det.temporada, 1, 2)||' '||substr(det.temporada, 3, 4) " +
					"        ||' '|| lpad(det.seccion, 2, '0')||lpad(det.categoria, 2, '0')||lpad(det.subcategoria, 2, '0')||lpad(det.segmento, 2, '0') " +
					"        ||' '|| lpad(num_orden, 3, '0') " +
					"     ) modelo";
    	}
    	
    	StringBuffer query = new StringBuffer(" SELECT ");
    	query.append(fields); 
    	if(textil == null){
    		query.append( " FROM V_MIS_CAMPANA_OFER_STOCK camp_ofe, v_datos_especificos_textil det  ");
    	}else{
    		//En textil se saca la estructura, para ello accedemos directamente a T_CAMPANA_OFERTA_MISUMI
    		query.append( " FROM t_campana_oferta_misumi camp_ofe, v_datos_especificos_textil det  ");
    	}

    	
    	where.append(" AND camp_ofe.COD_ART = det.COD_ART(+) ");
        if (vMisCampanaOfer  != null){
        	if(vMisCampanaOfer.getIdSesion()!=null){
        		 where.append(" AND camp_ofe.IDSESION = ? ");
	        	 params.add(vMisCampanaOfer.getIdSesion());	        		
        	}
        	if(textil == null){
            	if(vMisCampanaOfer.getNivel()!=null){
          		 	 where.append(" AND camp_ofe.NIVEL = ? ");
	   	        	 params.add(vMisCampanaOfer.getNivel());	        		
	           	}
            	if(vMisCampanaOfer.getIdent()!=null){
          		 	 where.append(" AND camp_ofe.IDENT = ? ");
	   	        	 params.add(vMisCampanaOfer.getIdent());	        		
	           	}
            	if(vMisCampanaOfer.getParentident()!=null){
             		 where.append(" AND camp_ofe.PARENTIDENT = ? ");
      	        	 params.add(vMisCampanaOfer.getParentident());	        		
              	}
            	if(vMisCampanaOfer.getTipoOC()!=null){
        		 	 where.append(" AND camp_ofe.TIPO_O_C = upper(?) ");
	   	        	 params.add(vMisCampanaOfer.getTipoOC());	        		
	           	}
            	if(vMisCampanaOfer.getTipo()!=null){
	       		 	 where.append(" AND camp_ofe.TIPO = upper(?) ");
	   	        	 params.add(vMisCampanaOfer.getTipo());	        		
	           	}
        	}
        	if(vMisCampanaOfer.getIdentificador()!=null){
         		 where.append(" AND camp_ofe.IDENTIFICADOR = ? ");
  	        	 params.add(vMisCampanaOfer.getIdentificador());	        		
          	}
        	if(vMisCampanaOfer.getCodCentro()!=null){
         		 where.append(" AND camp_ofe.COD_CENTRO = ? ");
  	        	 params.add(vMisCampanaOfer.getCodCentro());	        		
          	}
        	if(vMisCampanaOfer.getGrupo1()!=null){
    		 	 where.append(" AND camp_ofe.GRUPO1 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo1());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo2()!=null){
   		 	     where.append(" AND camp_ofe.GRUPO2 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo2());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo3()!=null){
	     		 where.append(" AND camp_ofe.GRUPO3 = ? ");
		         params.add(vMisCampanaOfer.getGrupo3());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo4()!=null){
	     		 where.append(" AND camp_ofe.GRUPO4 = ? ");
		         params.add(vMisCampanaOfer.getGrupo4());	        		
	       	}
        	if(vMisCampanaOfer.getDescripcion()!=null){
      		 	 where.append(" AND TRIM(camp_ofe.DESCRIPCION) = upper(?) ");
	        	 params.add(vMisCampanaOfer.getDescripcion());	        		
        	}
	       	if(vMisCampanaOfer.getAnoOferta()!=null){
	     		 where.append(" AND camp_ofe.ANO_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getAnoOferta());	        		
	       	}
	       	if(vMisCampanaOfer.getNumOferta()!=null){
	     		 where.append(" AND camp_ofe.NUM_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getNumOferta());	        		
	       	}
        	if(vMisCampanaOfer.getFInicio()!=null){
	       		 where.append(" AND TRUNC(camp_ofe.F_INICIO) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFInicio());	        		
	       	}
        	if(vMisCampanaOfer.getFFin()!=null){
	       		 where.append(" AND TRUNC(camp_ofe.F_FIN) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFFin());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevista()!=null){
	     		 where.append(" AND camp_ofe.CANT_PREVISTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevista());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevistaVenta()!=null){
	     		 where.append(" AND camp_ofe.CANT_PREVISTA_VENTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevistaVenta());	        		
	       	}
        	if(vMisCampanaOfer.getVentaMedia()!=null){
	     		 where.append(" AND camp_ofe.VENTA_MEDIA = ? ");
		         params.add(vMisCampanaOfer.getVentaMedia());	        		
	       	}
        	if(vMisCampanaOfer.getVentaTotal()!=null){
	     		 where.append(" AND camp_ofe.VENTA_TOTAL = ? ");
		         params.add(vMisCampanaOfer.getVentaTotal());	        		
	       	}
        	if(vMisCampanaOfer.getNsrNumero()!=null){
	     		 where.append(" AND camp_ofe.NSR_NUMERO = ? ");
		         params.add(vMisCampanaOfer.getNsrNumero());	        		
	       	}
        	if(vMisCampanaOfer.getNsrUnidades()!=null){
	     		 where.append(" AND camp_ofe.NSR_UNIDADES = ? ");
		         params.add(vMisCampanaOfer.getNsrUnidades());	        		
	       	}
        	if(vMisCampanaOfer.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(vMisCampanaOfer.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND camp_ofe.COD_ART IN ( ").append(referencias).append(" )");
	       	}
        }
        
        query.append(where);

        StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        if(textil == null){
	        if (vMisCampanaOfer.getTipoOC()!=null && Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_CAMPANA.equals(vMisCampanaOfer.getTipoOC())){
	        	groupBy.append(" group by camp_ofe.nivel, camp_ofe.ident, camp_ofe.parentident, camp_ofe.cod_centro, camp_ofe.identificador, camp_ofe.cod_art");
	        }else{
	        	groupBy.append(" group by camp_ofe.nivel, camp_ofe.ident, camp_ofe.parentident, camp_ofe.cod_centro, camp_ofe.ano_oferta, camp_ofe.num_oferta, camp_ofe.cod_art ");
	        }
        }else{
        	groupBy.append(" group by camp_ofe.grupo2, camp_ofe.grupo3, camp_ofe.grupo4, camp_ofe.grupo5, camp_ofe.cod_centro, camp_ofe.identificador, camp_ofe.cod_art, det.temporada");
        }

		query.append(groupBy);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        if(textil != null){
        	order.append(" order by 2,3,4,5,6,7 ");
        }else{
        	order.append(" order by MAX(camp_ofe.COD_ART) ");
        }
		query.append(order);

	   List<GenericExcelVO> lista = null;
	    
	    try {
	    	lista = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(),this.rwExcelVMisCampanaOferMap, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    
	    return lista;
	}
	
	private STRUCT crearEstructuraRecargaDatosCampanasOfer(List<Long> listaRecarga, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesario para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaRecarga.size();
		Object[] objectTabla = new Object[numeroElementos];
		
		for (int i=0; i<numeroElementos; i++){
			
			Long codigoArticulo = (Long)listaRecarga.get(i);
        	
        	Object[] objectInfo = new Object[1];
        	
        	//Sólo se informan los datos necesarios
        	objectInfo[0] = (codigoArticulo!=null?new BigDecimal(codigoArticulo):null);
			//TABLA mis_t_articulo_dat 
			//STRUCT mis_r_articulo_reg toda la estructura
			//STRUCT mis_r_articulo_dat registro 
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("MIS_R_ARTICULO_DAT",conexionOracle);
	    	STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
	    	
	    	objectTabla[i] = itemObjectStruct;
	    	
		}
	    
		Object[] objectConsulta = new Object[1]; //Tiene 1 campo con la lista de códigos de artículos
		ArrayDescriptor desc = new ArrayDescriptor("MIS_T_ARTICULO_DAT", conexionOracle);
	    ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);
    	
    	objectConsulta[0] = array;
    	
    	StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("MIS_R_ARTICULO_REG",conexionOracle);
    	STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);
    	
    	return itemConsulta;
    }
	
	@Override
	public List<SeguimientoCampanasDetalle> findAllReferenciasCampanaOferPopup(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias, Pagination pagination) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MAX(camp_ofe.COD_CENTRO) cod_centro_grupo, " +
					"MAX(camp_ofe.COD_ART) cod_art_grupo, " +
					"MAX(NVL((SELECT ar.descrip_art FROM v_datos_diario_art ar WHERE ar.cod_art = camp_ofe.cod_art),'')) descripcion_grupo, " +
					"ltrim(to_char(SUM(NVL(CANT_IMP,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_SERVIDA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_PREVISTA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) servido_pendiente, " +
					"SUM(NVL(NSR_NUMERO,0))||'**'||ltrim(to_char(SUM(NVL(NSR_UNIDADES,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) no_servido, " +
					"ltrim(to_char(SUM(NVL(VENTA_TOTAL,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,'''))||'**'||ltrim(to_char(SUM(NVL(CANT_PREVISTA_VENTA,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')) ventas_prevision, " +
                	"replace(ltrim(to_char(SUM(NVL(camp_ofe.stock,0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')),'.0') || '**' || replace(ltrim(to_char( SUM(NVL(camp_ofe.stock,0)) / decode( NVL( max(venta_media),0), 0, 1, NVL( max(venta_media),0)),'9999990D0','NLS_NUMERIC_CHARACTERS=''.,''')),'.0') stock, " +
					" MAX (det.DESCR_TALLA) TALLA, MAX (det.DESCR_COLOR) COLOR, MAX (det.MODELO_PROVEEDOR) MODELOPROVEEDOR " +
                	" FROM V_MIS_CAMPANA_OFER_STOCK camp_ofe,  v_datos_especificos_textil det ");
    	
    	
    	where.append(" AND camp_ofe.COD_ART = det.COD_ART(+) ");


      if (vMisCampanaOfer  != null){
        	if(vMisCampanaOfer.getIdSesion()!=null){
        		 where.append(" AND camp_ofe.IDSESION = ? ");
	        	 params.add(vMisCampanaOfer.getIdSesion());	        		
        	}
        	if(vMisCampanaOfer.getNivel()!=null){
       		 	 where.append(" AND camp_ofe.NIVEL = ? ");
	        	 params.add(vMisCampanaOfer.getNivel());	        		
        	}
        	if(vMisCampanaOfer.getIdent()!=null){
       		 	 where.append(" AND REPLACE(IDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
	        	 params.add(vMisCampanaOfer.getIdent());	        		
        	}
        	if(vMisCampanaOfer.getParentident()!=null){
          		 where.append(" AND REPLACE(PARENTIDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
   	        	 params.add(vMisCampanaOfer.getParentident());	        		
           	}
        	if(vMisCampanaOfer.getIdentificador()!=null){
         		 where.append(" AND camp_ofe.IDENTIFICADOR = ? ");
  	        	 params.add(vMisCampanaOfer.getIdentificador());	        		
          	}
        	if(vMisCampanaOfer.getCodCentro()!=null){
         		 where.append(" AND camp_ofe.COD_CENTRO = ? ");
  	        	 params.add(vMisCampanaOfer.getCodCentro());	        		
          	}
        	if(vMisCampanaOfer.getGrupo1()!=null){
    		 	 where.append(" AND camp_ofe.GRUPO1 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo1());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo2()!=null){
   		 	     where.append(" AND camp_ofe.GRUPO2 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo2());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo3()!=null){
	     		 where.append(" AND camp_ofe.GRUPO3 = ? ");
		         params.add(vMisCampanaOfer.getGrupo3());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo4()!=null){
	     		 where.append(" AND camp_ofe.GRUPO4 = ? ");
		         params.add(vMisCampanaOfer.getGrupo4());	        		
	       	}
        	if(vMisCampanaOfer.getDescripcion()!=null){
      		 	 where.append(" AND camp_ofe.DESCRIPCION = upper(?) ");
	        	 params.add(vMisCampanaOfer.getDescripcion());	        		
        	}
        	if(vMisCampanaOfer.getTipoOC()!=null){
     		 	 where.append(" AND camp_ofe.TIPO_O_C = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipoOC());	        		
        	}
        	if(vMisCampanaOfer.getTipo()!=null){
    		 	 where.append(" AND camp_ofe.TIPO = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipo());	        		
        	}
	       	if(vMisCampanaOfer.getAnoOferta()!=null){
	     		 where.append(" AND camp_ofe.ANO_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getAnoOferta());	        		
	       	}
	       	if(vMisCampanaOfer.getNumOferta()!=null){
	     		 where.append(" AND camp_ofe.NUM_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getNumOferta());	        		
	       	}
        	if(vMisCampanaOfer.getFInicio()!=null){
	       		 where.append(" AND TRUNC(camp_ofe.F_INICIO) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFInicio());	        		
	       	}
        	if(vMisCampanaOfer.getFFin()!=null){
	       		 where.append(" AND TRUNC(camp_ofe.F_FIN) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFFin());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevista()!=null){
	     		 where.append(" AND camp_ofe.CANT_PREVISTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevista());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevistaVenta()!=null){
	     		 where.append(" AND camp_ofe.CANT_PREVISTA_VENTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevistaVenta());	        		
	       	}
        	if(vMisCampanaOfer.getVentaMedia()!=null){
	     		 where.append(" AND camp_ofe.VENTA_MEDIA = ? ");
		         params.add(vMisCampanaOfer.getVentaMedia());	        		
	       	}
        	if(vMisCampanaOfer.getVentaTotal()!=null){
	     		 where.append(" AND camp_ofe.VENTA_TOTAL = ? ");
		         params.add(vMisCampanaOfer.getVentaTotal());	        		
	       	}
        	if(vMisCampanaOfer.getNsrNumero()!=null){
	     		 where.append(" AND camp_ofe.NSR_NUMERO = ? ");
		         params.add(vMisCampanaOfer.getNsrNumero());	        		
	       	}
        	if(vMisCampanaOfer.getNsrUnidades()!=null){
	     		 where.append(" AND camp_ofe.NSR_UNIDADES = ? ");
		         params.add(vMisCampanaOfer.getNsrUnidades());	        		
	       	}
        	if(vMisCampanaOfer.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(vMisCampanaOfer.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND camp_ofe.COD_ART IN ( ").append(referencias).append(" )");
	       	}
        }
        
        query.append(where);

        StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
       	groupBy.append(" group by camp_ofe.COD_ART ");
		query.append(groupBy);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + getMappedFieldSort(pagination.getSort(), pagination.getAscDsc()));
				query.append(order);
			}
		}
		else{
			String campoOrdenacion = "camp_ofe.COD_ART";
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}

		List<SeguimientoCampanasDetalle> seguimientoCampanasLista = null;		

	    try {
	    	seguimientoCampanasLista = (List<SeguimientoCampanasDetalle>) this.jdbcTemplate.query(query.toString(),this.rwSeguimientoCampanaOferPopupMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	     
	    return seguimientoCampanasLista;
	}
	
	@Override
	public Long findAllReferenciasCampanaOferPopupCont(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias) throws Exception {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + 
											  " FROM V_MIS_CAMPANA_OFER camp_ofe ");
    	
        if (vMisCampanaOfer  != null){
        	if(vMisCampanaOfer.getIdSesion()!=null){
        		 where.append(" AND IDSESION = ? ");
	        	 params.add(vMisCampanaOfer.getIdSesion());	        		
        	}
        	if(vMisCampanaOfer.getNivel()!=null){
       		 	 where.append(" AND NIVEL = ? ");
	        	 params.add(vMisCampanaOfer.getNivel());	        		
        	}
        	if(vMisCampanaOfer.getIdent()!=null){
       		 	 where.append(" AND REPLACE(IDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
	        	 params.add(vMisCampanaOfer.getIdent());	        		
        	}
        	if(vMisCampanaOfer.getParentident()!=null){
          		 where.append(" AND REPLACE(PARENTIDENT, ' ', '_') = REPLACE(?, ' ', '_') ");
   	        	 params.add(vMisCampanaOfer.getParentident());	        		
           	}
        	if(vMisCampanaOfer.getIdentificador()!=null){
         		 where.append(" AND IDENTIFICADOR = ? ");
  	        	 params.add(vMisCampanaOfer.getIdentificador());	        		
          	}
        	if(vMisCampanaOfer.getCodCentro()!=null){
         		 where.append(" AND COD_CENTRO = ? ");
  	        	 params.add(vMisCampanaOfer.getCodCentro());	        		
          	}
        	if(vMisCampanaOfer.getGrupo1()!=null){
    		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo1());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo2()!=null){
   		 	     where.append(" AND GRUPO2 = ? ");
	        	 params.add(vMisCampanaOfer.getGrupo2());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vMisCampanaOfer.getGrupo3());	        		
	       	}
	       	if(vMisCampanaOfer.getGrupo4()!=null){
	     		 where.append(" AND GRUPO4 = ? ");
		         params.add(vMisCampanaOfer.getGrupo4());	        		
	       	}
        	if(vMisCampanaOfer.getDescripcion()!=null){
      		 	 where.append(" AND DESCRIPCION = upper(?) ");
	        	 params.add(vMisCampanaOfer.getDescripcion());	        		
        	}
        	if(vMisCampanaOfer.getTipoOC()!=null){
     		 	 where.append(" AND TIPO_O_C = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipoOC());	        		
        	}
        	if(vMisCampanaOfer.getTipo()!=null){
    		 	 where.append(" AND TIPO = upper(?) ");
	        	 params.add(vMisCampanaOfer.getTipo());	        		
        	}
	       	if(vMisCampanaOfer.getAnoOferta()!=null){
	     		 where.append(" AND ANO_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getAnoOferta());	        		
	       	}
	       	if(vMisCampanaOfer.getNumOferta()!=null){
	     		 where.append(" AND NUM_OFERTA = ? ");
		         params.add(vMisCampanaOfer.getNumOferta());	        		
	       	}
        	if(vMisCampanaOfer.getFInicio()!=null){
	       		 where.append(" AND TRUNC(F_INICIO) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFInicio());	        		
	       	}
        	if(vMisCampanaOfer.getFFin()!=null){
	       		 where.append(" AND TRUNC(F_FIN) = TRUNC(?) ");
		         params.add(vMisCampanaOfer.getFFin());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevista()!=null){
	     		 where.append(" AND CANT_PREVISTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevista());	        		
	       	}
        	if(vMisCampanaOfer.getCantPrevistaVenta()!=null){
	     		 where.append(" AND CANT_PREVISTA_VENTA = ? ");
		         params.add(vMisCampanaOfer.getCantPrevistaVenta());	        		
	       	}
        	if(vMisCampanaOfer.getVentaMedia()!=null){
	     		 where.append(" AND VENTA_MEDIA = ? ");
		         params.add(vMisCampanaOfer.getVentaMedia());	        		
	       	}
        	if(vMisCampanaOfer.getVentaTotal()!=null){
	     		 where.append(" AND VENTA_TOTAL = ? ");
		         params.add(vMisCampanaOfer.getVentaTotal());	        		
	       	}
        	if(vMisCampanaOfer.getNsrNumero()!=null){
	     		 where.append(" AND NSR_NUMERO = ? ");
		         params.add(vMisCampanaOfer.getNsrNumero());	        		
	       	}
        	if(vMisCampanaOfer.getNsrUnidades()!=null){
	     		 where.append(" AND NSR_UNIDADES = ? ");
		         params.add(vMisCampanaOfer.getNsrUnidades());	        		
	       	}
        	if(vMisCampanaOfer.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(vMisCampanaOfer.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND COD_ART IN ( ").append(referencias).append(" )");
	       	}
        }

        query.append(where);
	    
	    Long cont = null;		

	    try {
	    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	     
	    return cont;
	}
}
