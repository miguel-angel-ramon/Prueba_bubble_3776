package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VMisSeguimientosDao;
import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.VMisSeguimientos;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VMisSeguimientosDaoImpl implements VMisSeguimientosDao {
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(VMisSeguimientosDaoImpl.class);

	private RowMapper<VMisSeguimientos> rwVMisSeguimientosMap = new RowMapper<VMisSeguimientos>() {
		public VMisSeguimientos mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VMisSeguimientos(resultSet.getLong("NIVEL"), resultSet.getLong("IDENT"), resultSet.getLong("PARENTIDENT"),
		    		resultSet.getLong("COD_CENTRO"), resultSet.getDate("FECHA_PREVIS_ENT"), resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
		    		resultSet.getLong("GRUPO3"), resultSet.getString("DESCRIPCION"), resultSet.getLong("ART_PED"), resultSet.getFloat("CAJ_PED"),
		    		resultSet.getLong("ART_NSR"), resultSet.getFloat("CAJ_NO_SERV"), resultSet.getLong("ART_ENT"), resultSet.getFloat("CAJ_ENT"),
		    		resultSet.getLong("ALB_TOT"), resultSet.getLong("ALB_CONF"), resultSet.getLong("COD_ART"), resultSet.getString("TIPO")
			    );
		}
	};

	private RowMapper<ReferenciasPedido> rwReferenciasPedidoMap = new RowMapper<ReferenciasPedido>() {
		public ReferenciasPedido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new ReferenciasPedido(resultSet.getString("IDENT_GRUPO"), resultSet.getString("DESCRIPCIONNIVEL"), resultSet.getString("HOY"),
		    		resultSet.getString("NSR"), resultSet.getString("CONFIRM"), resultSet.getString("GISAE"), resultSet.getString("FECHA_FORMATEADA"),
		    		resultSet.getLong("GRUPO1_GRUPO"), resultSet.getString("AREA"), resultSet.getLong("GRUPO2_GRUPO"), resultSet.getString("SECCION"),
		    		resultSet.getLong("GRUPO3_GRUPO"), resultSet.getString("CATEGORIA"), resultSet.getInt("NIVEL_GRUPO"), resultSet.getString("PARENTIDENT_GRUPO"),
		    		(resultSet.getInt("NIVEL_GRUPO") > 2), false, false, resultSet.getString("MAPA_GRUPO"), resultSet.getString("MAPA"), resultSet.getInt("CAJAS_CORTADAS"));
		}
	};

	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	@Override
	public List<VMisSeguimientos> findAll(VMisSeguimientos vMisSeguimientos, boolean controlFechaActual) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT NIVEL, IDENT, PARENTIDENT, COD_CENTRO, FECHA_PREVIS_ENT, " + 
    												"GRUPO1, GRUPO2, GRUPO3, DESCRIPCION, ART_PED, " + 
    												"CAJ_PED, ART_NSR, CAJ_NO_SERV, ART_ENT, CAJ_ENT, " + 
    												"ALB_TOT, ALB_CONF, COD_ART, TIPO " + 
											  " FROM V_MIS_SEGUIMIENTOS ");
    
        if (vMisSeguimientos  != null){
        	if (controlFechaActual){
        		where.append(" AND FECHA_PREVIS_ENT <= SYSDATE ");
        	}
        	if(vMisSeguimientos.getNivel()!=null){
        		 where.append(" AND NIVEL = ? ");
	        	 params.add(vMisSeguimientos.getNivel());	        		
        	}
        	if(vMisSeguimientos.getIdent()!=null){
       		 	 where.append(" AND IDENT = ? ");
	        	 params.add(vMisSeguimientos.getIdent());	        		
        	}
        	if(vMisSeguimientos.getParentident()!=null){
          		 where.append(" AND PARENTIDENT = ? ");
   	        	 params.add(vMisSeguimientos.getParentident());	        		
           	}
        	if(vMisSeguimientos.getCodCentro()!=null){
         		 where.append(" AND COD_CENTRO = ? ");
  	        	 params.add(vMisSeguimientos.getCodCentro());	        		
          	}
        	if(vMisSeguimientos.getFechaPrevisEnt()!=null){
	       		 where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
		         params.add(vMisSeguimientos.getFechaPrevisEnt());	        		
	       	}
        	if(vMisSeguimientos.getGrupo1()!=null){
     		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vMisSeguimientos.getGrupo1());	        		
	       	}
	       	if(vMisSeguimientos.getGrupo2()!=null){
    		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vMisSeguimientos.getGrupo2());	        		
	       	}
	       	if(vMisSeguimientos.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vMisSeguimientos.getGrupo3());	        		
	       	}
        	if(vMisSeguimientos.getDescripcion()!=null){
       		 	 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
	        	 params.add(vMisSeguimientos.getDescripcion());	        		
        	}
        	if(vMisSeguimientos.getArtPed()!=null){
	     		 where.append(" AND ART_PED = ? ");
		         params.add(vMisSeguimientos.getArtPed());	        		
	       	}
        	if(vMisSeguimientos.getCajPed()!=null){
	     		 where.append(" AND CAJ_PED = ? ");
		         params.add(vMisSeguimientos.getCajPed());	        		
	       	}
        	if(vMisSeguimientos.getArtNsr()!=null){
	     		 where.append(" AND ART_NSR = ? ");
		         params.add(vMisSeguimientos.getArtNsr());	        		
	       	}
        	if(vMisSeguimientos.getCajNoServ()!=null){
	     		 where.append(" AND CAJ_NO_SERV = ? ");
		         params.add(vMisSeguimientos.getCajNoServ());	        		
	       	}
        	if(vMisSeguimientos.getArtEnt()!=null){
	     		 where.append(" AND ART_ENT = ? ");
		         params.add(vMisSeguimientos.getArtEnt());	        		
	       	}
        	if(vMisSeguimientos.getCajEnt()!=null){
	     		 where.append(" AND CAJ_ENT = ? ");
		         params.add(vMisSeguimientos.getCajEnt());	        		
	       	}
        	if(vMisSeguimientos.getAlbTot()!=null){
	     		 where.append(" AND ALB_TOT = ? ");
		         params.add(vMisSeguimientos.getAlbTot());	        		
	       	}
        	if(vMisSeguimientos.getAlbConf()!=null){
	     		 where.append(" AND ALB_CONF = ? ");
		         params.add(vMisSeguimientos.getAlbConf());	        		
	       	}
        	if(vMisSeguimientos.getCodArt()!=null){
	     		 where.append(" AND COD_ART = ? ");
		         params.add(vMisSeguimientos.getCodArt());	        		
	       	}
        	if(vMisSeguimientos.getTipo()!=null){
	     		 where.append(" AND TIPO = ? ");
		         params.add(vMisSeguimientos.getTipo());	        		
	       	}
        }
        
        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by fecha_previs_ent,grupo1,grupo2,grupo3 ");
		query.append(order);

		List<VMisSeguimientos> vMisSeguimientosLista = null;		
		try {
			vMisSeguimientosLista = (List<VMisSeguimientos>) this.jdbcTemplate.query(query.toString(),this.rwVMisSeguimientosMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return vMisSeguimientosLista;
	}
	
	@Override
	public List<ReferenciasPedido> findAllReferenciasPedido(VMisSeguimientos vMisSeguimientos, List<Long> listaReferencias) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	
    	StringBuffer query = new StringBuffer(" SELECT MAX(NVL(COD_MAPA,0)) mapa_grupo, MAX(NIVEL) nivel_grupo, MAX(IDENT) ident_grupo, MAX(PARENTIDENT) parentident_grupo, MAX(COD_CENTRO) cod_centro_grupo, TO_CHAR(MAX(FECHA_PREVIS_ENT),'DDMMYYYY') FECHA_FORMATEADA, " + 
    												"MAX(GRUPO1) grupo1_grupo, MAX(GRUPO2) grupo2_grupo, MAX(GRUPO3) grupo3_grupo, MAX(DESCRIPCION)||'**'||DECODE(TO_CHAR(SYSDATE,'DDMMYYYY'),TO_CHAR(MAX(FECHA_PREVIS_ENT),'DDMMYYYY'),'1','0') descripcionNivel, " +
    												"SUM(ART_PED)||'**'||ltrim(to_char(SUM(CAJ_PED),'9G999G990D0','NLS_NUMERIC_CHARACTERS='',.''')) HOY, " + 
    												"SUM(ART_NSR)||'**'||ltrim(to_char(SUM(CAJ_NO_SERV),'9G999G990D0','NLS_NUMERIC_CHARACTERS='',.''')) NSR, " +
    												"SUM(ART_ENT)||'**'||ltrim(to_char(SUM(CAJ_ENT),'999G990','NLS_NUMERIC_CHARACTERS='',.''')) CONFIRM, " + 
    												"SUM(ALB_TOT)||'**'||SUM(ALB_CONF) GISAE,  " +
    												//MISUMI-299
    												" MAX(NVL((SELECT DISTINCT m1.desc_mapa FROM t_mis_mapas_vegalsa m1 " +
					                    			" WHERE seg.COD_MAPA = m1.cod_mapa ),'')) mapa, " + //AREA
    									        	//Obtención de descripciones de area, grupo y sección
    									        	" MAX(NVL((SELECT a1.descripcion  FROM v_agru_comer_ref a1 " +
							                    			" WHERE a1.nivel = 'I1' AND seg.grupo1 = a1.grupo1 ),'')) area, " + //AREA
							                    	" MAX(NVL((SELECT a2.descripcion FROM v_agru_comer_ref a2 " +
							                    	 		" WHERE a2.nivel = 'I2' AND seg.grupo1 = a2.grupo1 " +
							                    	 		" AND seg.grupo2 = a2.grupo2),'')) seccion, " + //SECCION
							                    	" MAX(NVL((SELECT a3.descripcion FROM v_agru_comer_ref a3 " +
							                    	 		" WHERE a3.nivel = 'I3' AND seg.grupo1 = a3.grupo1 " +
							                    	 		" AND seg.grupo2 = a3.grupo2 AND seg.grupo3 = a3.grupo3 ),'')) categoria, " +
							                    	 " SUM(seg.CAJAS_CORTADAS) cajas_cortadas " + 
											  " FROM V_MIS_SEGUIMIENTOS seg ");
    
        if (vMisSeguimientos  != null){
        	if(vMisSeguimientos.getNivel()!=null){
        		 where.append(" AND NIVEL = ? ");
	        	 params.add(vMisSeguimientos.getNivel());	        		
        	}
        	if(vMisSeguimientos.getIdent()!=null){
       		 	 where.append(" AND IDENT = ? ");
	        	 params.add(vMisSeguimientos.getIdent());	        		
        	}
        	if(vMisSeguimientos.getParentident()!=null){
          		 where.append(" AND PARENTIDENT = ? ");
   	        	 params.add(vMisSeguimientos.getParentident());	        		
           	}
        	if(vMisSeguimientos.getCodCentro()!=null){
         		 where.append(" AND COD_CENTRO = ? ");
  	        	 params.add(vMisSeguimientos.getCodCentro());	        		
          	}
        	if(vMisSeguimientos.getFechaPrevisEnt()!=null){
	       		 where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
		         params.add(vMisSeguimientos.getFechaPrevisEnt());	        		
	       	}
        	if(vMisSeguimientos.getGrupo1()!=null){
     		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vMisSeguimientos.getGrupo1());	        		
	       	}
	       	if(vMisSeguimientos.getGrupo2()!=null){
    		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vMisSeguimientos.getGrupo2());	        		
	       	}
	       	if(vMisSeguimientos.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vMisSeguimientos.getGrupo3());	        		
	       	}
        	if(vMisSeguimientos.getDescripcion()!=null){
       		 	 where.append(" AND UPPER(DESCRIPCION) = upper(?) ");
	        	 params.add(vMisSeguimientos.getDescripcion());	        		
        	}
        	if(vMisSeguimientos.getArtPed()!=null){
	     		 where.append(" AND ART_PED = ? ");
		         params.add(vMisSeguimientos.getArtPed());	        		
	       	}
        	if(vMisSeguimientos.getCajPed()!=null){
	     		 where.append(" AND CAJ_PED = ? ");
		         params.add(vMisSeguimientos.getCajPed());	        		
	       	}
        	if(vMisSeguimientos.getArtNsr()!=null){
	     		 where.append(" AND ART_NSR = ? ");
		         params.add(vMisSeguimientos.getArtNsr());	        		
	       	}
        	if(vMisSeguimientos.getCajNoServ()!=null){
	     		 where.append(" AND CAJ_NO_SERV = ? ");
		         params.add(vMisSeguimientos.getCajNoServ());	        		
	       	}
        	if(vMisSeguimientos.getArtEnt()!=null){
	     		 where.append(" AND ART_ENT = ? ");
		         params.add(vMisSeguimientos.getArtEnt());	        		
	       	}
        	if(vMisSeguimientos.getCajEnt()!=null){
	     		 where.append(" AND CAJ_ENT = ? ");
		         params.add(vMisSeguimientos.getCajEnt());	        		
	       	}
        	if(vMisSeguimientos.getAlbTot()!=null){
	     		 where.append(" AND ALB_TOT = ? ");
		         params.add(vMisSeguimientos.getAlbTot());	        		
	       	}
        	if(vMisSeguimientos.getAlbConf()!=null){
	     		 where.append(" AND ALB_CONF = ? ");
		         params.add(vMisSeguimientos.getAlbConf());	        		
	       	}
        	if(vMisSeguimientos.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(vMisSeguimientos.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND COD_ART IN ( ").append(referencias).append(" )");
		         //params.add(vMisSeguimientos.getCodArt());	        		
	       	}
        	if(vMisSeguimientos.getMapa()!=null && vMisSeguimientos.getMapa().length()>0){
	     		 where.append(" AND COD_MAPA = ? ");
	     		 params.add(vMisSeguimientos.getMapa());
	       	}
        	if(vMisSeguimientos.getTipo()!=null){
	     		 where.append(" AND TIPO = ? ");
		         params.add(vMisSeguimientos.getTipo());	        		
	       	}
        }
        
        query.append(where);

        StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" group by nivel, ident, parentident, cod_centro, fecha_previs_ent ");
		query.append(groupBy);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by MAX(fecha_previs_ent), MAX(NVL(COD_MAPA,0)), MAX(grupo1), MAX(grupo2), MAX(grupo3) ");
		query.append(order);
		logger.info("findAllReferenciasPedido - SQL = "+query.toString());
		logger.info("findAllReferenciasPedido - PARAMS = "+vMisSeguimientos.toString());
		List<ReferenciasPedido> referenciasPedidoLista = null;			
		try {
			referenciasPedidoLista = (List<ReferenciasPedido>) this.jdbcTemplate.query(query.toString(),this.rwReferenciasPedidoMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		return referenciasPedidoLista;
	}
	
    @Override
    public VMisSeguimientos recargaDatosTienda(final VMisSeguimientos vMisSeguimientos) throws Exception  {
    	
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		VMisSeguimientos misSeguimientosSalida = new VMisSeguimientos();
		Long codError = new Long (0);
		String descError = "";
		
		declaredParameters.add(new SqlParameter("p_centro", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_referencia", Types.INTEGER));
		declaredParameters.add(new SqlOutParameter("p_cod_error", Types.INTEGER));
		declaredParameters.add(new SqlOutParameter("p_msg_error", Types.VARCHAR));
		
		try{
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {
	
			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_MIS_LLAMADA_WS_ALBARAN.p_consulta_albaran(?, ?, ?, ?)}");
	
			        stmnt.setInt(1, vMisSeguimientos.getCodCentro().intValue());
			        if (vMisSeguimientos.getCodArt() != null){
			        	stmnt.setInt(2, vMisSeguimientos.getCodArt().intValue());	
			        }else{
			        	stmnt.setNull(2, OracleTypes.NULL);
			        }
			        stmnt.registerOutParameter(3, Types.INTEGER);
			        stmnt.registerOutParameter(4, Types.VARCHAR);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			

			
			misSeguimientosSalida.setCodCentro(vMisSeguimientos.getCodCentro());
			misSeguimientosSalida.setCodArt(vMisSeguimientos.getCodArt());
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error")!=null && "0".equals(mapaResultados.get("p_cod_error").toString())){
					if (mapaResultados.get("p_cod_error") != null){
						codError = Long.parseLong(mapaResultados.get("p_cod_error").toString());
					}
					if (mapaResultados.get("p_msg_error") != null){
						descError = mapaResultados.get("p_msg_error").toString();
					}
				}
			}
			misSeguimientosSalida.setCodError(codError);
			misSeguimientosSalida.setDescError(descError);
		}catch (Exception e) {
			//Si surge algún error hay que informarlo para mostrar en pantalla que no se han podido actualizar los datos de la tienda
			misSeguimientosSalida.setCodError(new Long(1));
			misSeguimientosSalida.setDescError("");
		}		
	    return misSeguimientosSalida;
    }
}
