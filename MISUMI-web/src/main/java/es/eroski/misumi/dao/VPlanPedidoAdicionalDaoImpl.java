package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VPlanPedidoAdicionalDao;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VPlanPedidoAdicionalDaoImpl implements VPlanPedidoAdicionalDao{
	
	 private JdbcTemplate jdbcTemplate;
	 private RowMapper<VPlanPedidoAdicional> rwVPlanPedidoAdicionalMap = new RowMapper<VPlanPedidoAdicional>() {
			public VPlanPedidoAdicional mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VPlanPedidoAdicional(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getString("DESCRIP_ART"), resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"), 
			    			resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),resultSet.getLong("GRUPO5"),
			    			resultSet.getString("AGRUPACION"),resultSet.getDouble("UNI_CAJA_SERV"),resultSet.getDate("F_INICIO"),
			    			resultSet.getDate("F_FIN"), resultSet.getDouble("IMP_INICIAL"),resultSet.getDouble("IMP_FINAL"),
			    			resultSet.getLong("PERFIL"),resultSet.getString("EXC"), resultSet.getString("TIPO_APROV"),
			    			resultSet.getString("ANO_OFERTA"), resultSet.getLong("COD_OFERTA"), resultSet.getString("DESC_PERIODO"), resultSet.getString("ESPACIO_PROMO"), resultSet.getDate("FECHA_GEN")
				    );
			}

		};
		   
		private RowMapper<String> rwVPlanUltimosPedidosAdicionalesMap = new RowMapper<String>() {
				public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    return resultSet.getString("DESC_PERIODO_CODE");
				}

		};
		
		
		
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VPlanPedidoAdicional> findAll(VPlanPedidoAdicional vPlanPedidoAdicional) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, DESCRIP_ART, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, AGRUPACION, " +
	    										  "UNI_CAJA_SERV, F_INICIO, F_FIN, IMP_INICIAL, IMP_FINAL, PERFIL, EXC, TIPO_APROV, ANO_OFERTA, COD_OFERTA, DESC_PERIODO, ESPACIO_PROMO, FECHA_GEN" 
	    										+ " FROM V_PLANOGRAMAS_PEDIDO_ADICIONAL ");
	    
	    	
	        if (vPlanPedidoAdicional  != null){
	        	if(vPlanPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vPlanPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(vPlanPedidoAdicional.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vPlanPedidoAdicional.getCodArt());	        		
	        	}
	        	if(vPlanPedidoAdicional.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(vPlanPedidoAdicional.getDescripArt());	        		
	        	}
	        	if(vPlanPedidoAdicional.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vPlanPedidoAdicional.getGrupo1());	        		
	        	}
	        	if(vPlanPedidoAdicional.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vPlanPedidoAdicional.getGrupo2());	        		
	        	}
	        	if(vPlanPedidoAdicional.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vPlanPedidoAdicional.getGrupo3());	        		
	        	}
	        	if(vPlanPedidoAdicional.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vPlanPedidoAdicional.getGrupo4());	        		
	        	}
	        	if(vPlanPedidoAdicional.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vPlanPedidoAdicional.getGrupo5());	        		
	        	}
	        	if(vPlanPedidoAdicional.getAgrupacion()!=null){
	        		 where.append(" AND AGRUPACION = ? ");
		        	 params.add(vPlanPedidoAdicional.getAgrupacion());	        		
	        	}
	        	if(vPlanPedidoAdicional.getUniCajaServ()!=null){
	        		 where.append(" AND UNI_CAJA_SERV = ? ");
		        	 params.add(vPlanPedidoAdicional.getUniCajaServ());	        		
	        	}
	        	if(vPlanPedidoAdicional.getFechaInicio()!=null){
	        		 where.append(" AND TRUNC(F_INICIO) = TRUNC(?) ");
		        	 params.add(vPlanPedidoAdicional.getFechaInicio());	        		
	        	}
	        	if(vPlanPedidoAdicional.getFechaFin()!=null){
	        		 where.append(" AND TRUNC(F_FIN) = TRUNC(?) ");
		        	 params.add(vPlanPedidoAdicional.getFechaFin());	        		
	        	}
	        	if(vPlanPedidoAdicional.getImpInicial()!=null){
	        		 where.append(" AND IMP_INICIAL = ? ");
		        	 params.add(vPlanPedidoAdicional.getImpInicial());	        		
	        	}
	        	if(vPlanPedidoAdicional.getImpFinal()!=null){
	        		 where.append(" AND IMP_FINAL = ? ");
		        	 params.add(vPlanPedidoAdicional.getImpFinal());	        		
	        	}
	        	if(vPlanPedidoAdicional.getPerfil()!=null){
	        		 where.append(" AND PERFIL = ? ");
		        	 params.add(vPlanPedidoAdicional.getPerfil());	        		
	        	}
	        	if(vPlanPedidoAdicional.getExcluir()!=null){
	        		 where.append(" AND EXC = ? ");
		        	 params.add(vPlanPedidoAdicional.getExcluir());	        		
	        	}
	        	if(vPlanPedidoAdicional.getTipoAprovisionamiento()!=null){
	        		 where.append(" AND TIPO_APROV = ? ");
		        	 params.add(vPlanPedidoAdicional.getTipoAprovisionamiento());	        		
	        	}
	        	if(vPlanPedidoAdicional.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vPlanPedidoAdicional.getAnoOferta());	        		
	        	}
	        	if(vPlanPedidoAdicional.getCodOferta()!=null){
	        		 where.append(" AND COD_OFERTA = ? ");
		        	 params.add(vPlanPedidoAdicional.getCodOferta());	        		
	        	}
	        	if(vPlanPedidoAdicional.getDescPeriodo()!=null){
	        		 where.append(" AND DESC_PERIODO = ? ");
		        	 params.add(vPlanPedidoAdicional.getDescPeriodo());	        		
	        	}
	        	if(vPlanPedidoAdicional.getEspacioPromo()!=null){
	        		 where.append(" AND ESPACIO_PROMO = ? ");
		        	 params.add(vPlanPedidoAdicional.getEspacioPromo());	        		
	        	}
	        	if(vPlanPedidoAdicional.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vPlanPedidoAdicional.getFechaGen());	        		
	        	}
	        	if(vPlanPedidoAdicional.getEsOferta()!=null){
	        		if(vPlanPedidoAdicional.getEsOferta().equals(Constantes.NO_OFERTA)){
	        			where.append(" AND ANO_OFERTA IS NULL AND COD_OFERTA IS NULL ");	
	        		}
	        		else if(vPlanPedidoAdicional.getEsOferta().equals(Constantes.SI_OFERTA)){
	        			where.append(" AND ANO_OFERTA IS NOT NULL AND COD_OFERTA IS NOT NULL ");	
	        		}
	        	}
	        	if (null != vPlanPedidoAdicional.getMAC()){
	        		;
	        		if (vPlanPedidoAdicional.getMAC().equals("S")){
	        			where.append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO);	
	        		} else {
	        			where.append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO);
	        		}
	        	}
	        }
	        
	        query.append(where);

			List<VPlanPedidoAdicional> vPlanPedidoAdicionalLista = null;		
			
			try {
				vPlanPedidoAdicionalLista = (List<VPlanPedidoAdicional>) this.jdbcTemplate.query(query.toString(),this.rwVPlanPedidoAdicionalMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vPlanPedidoAdicionalLista;
	    }
	    
	    @Override
	    public List<String> findUltimosPedidos(VPlanPedidoAdicional vPlanPedidoAdicional) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT DISTINCT CONCAT(DESC_PERIODO,(DECODE(COD_OFERTA,NULL,'**2','**3'))) AS DESC_PERIODO_CODE " 
	    										+ " FROM V_PLANOGRAMAS_PEDIDO_ADICIONAL ");
	    
	    	
	        if (vPlanPedidoAdicional  != null){
	        	if(vPlanPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? AND FECHA_GEN >=trunc(SYSDATE-5) AND DESC_PERIODO IS NOT NULL");
		        	 params.add(vPlanPedidoAdicional.getCodCentro());
		        	 if(this.isCentroVegalsa(vPlanPedidoAdicional.getCodCentro())){
		        		 where.append(" AND F_INICIO >=trunc(SYSDATE-3) ");
		 	        }
	        	}
	
	        }
	        
	        
	        
	        query.append(where);

			List<String> vPlanPedidoAdicionalLista = null;		
			
			try {
				vPlanPedidoAdicionalLista = (List<String>) this.jdbcTemplate.query(query.toString(),this.rwVPlanUltimosPedidosAdicionalesMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vPlanPedidoAdicionalLista;
	    }
	    
	    @Override
		public Boolean isCentroVegalsa(Long codCentro) throws Exception {

			String query = "SELECT count(cod_centro) " + " FROM v_centros_plataformas " + " WHERE cod_centro=?"
					+ " AND cod_soc = 13";

			int cont = 0;

			try {

				cont = this.jdbcTemplate.queryForObject(query, new Object[] { codCentro }, Integer.class);

			} catch (Exception e) {

				Utilidades.mostrarMensajeErrorSQL(query.toString(),
						new ArrayList<Object>(Arrays.asList(new Object[] { codCentro })), e);
			}

			return cont> 0 ? true : false;
		}
	   
}
