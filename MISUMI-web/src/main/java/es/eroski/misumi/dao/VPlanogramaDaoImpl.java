package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VPlanogramaDao;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VPlanogramaDaoImpl implements VPlanogramaDao{
	private static Logger logger = Logger.getLogger(VPlanogramaDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	
	 @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	 
	 private RowMapper<VPlanograma> rwVPlanograma = new RowMapper<VPlanograma>() {
			public VPlanograma mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VPlanograma(resultSet.getLong("COD_CENTRO"),
			    								resultSet.getLong("COD_ART"),
			    								resultSet.getLong("CAPACIDAD_MAX"),
			    								resultSet.getDouble("STOCK_MIN_COMER"),
			    								resultSet.getString("ANO_OFERTA_PILADA"),
			    								resultSet.getLong("NUM_OFERTA"),
			    								resultSet.getString("SIMULADO"),
			    								resultSet.getDate("FECHA_GEN"),
			    								resultSet.getDate("LAST_UPDATE_DATE"),
			    								resultSet.getString("TIPO_PLANO"),
			    								resultSet.getLong("FACING_ALTO"),
			    								resultSet.getLong("FACING_ANCHO"),
			    								resultSet.getString("ES_CAJA_EXP"));	
			}
		};
	 
		private RowMapper<DatosAdicionalesVegalsa> rwVPlanogramaDatosAdicionalesVegalsa = new RowMapper<DatosAdicionalesVegalsa>() {
			public DatosAdicionalesVegalsa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				DatosAdicionalesVegalsa output = new DatosAdicionalesVegalsa();
				final String ofertaPromo = resultSet.getString("DESC_PERIODO");
				final String espacioPromo = resultSet.getString("ESPACIO_PROMO");
				output.setOfertaPromo(ofertaPromo);
				output.setEspacioPromo(espacioPromo);
				return output;
			}
		};
	 @Override
	    public List<VPlanograma> findAll(VPlanograma vPlanoframa) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT * " 
	    										+ " FROM V_PLANOGRAMA ");
	    	  
	    	 where.append(" AND NUM_OFERTA = 0 ");
	        if (vPlanoframa  != null){
	        	if(vPlanoframa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vPlanoframa.getCodCentro());	        		
	        	}
	        	if(vPlanoframa.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vPlanoframa.getCodArt());	        		
	        	}	        	
	        }	        
	        query.append(where);

			List<VPlanograma> vPlanogramalLista = null;		
			
			try {
				vPlanogramalLista = (List<VPlanograma>) this.jdbcTemplate.query(query.toString(),this.rwVPlanograma, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vPlanogramalLista;
	    }


	@Override
	public PlanogramaVigente findDatosVegalsa(PlanogramaVigente planogramaVigente) {
		if (planogramaVigente!=null && planogramaVigente.getCodCentro()!=null && planogramaVigente.getCodArt()!=null && planogramaVigente.getFechaGenMontaje1()!=null){
			PlanogramaVigente output = planogramaVigente;
			logger.info("findDatosVegalsa - codCentro = "+planogramaVigente.getCodCentro()+" - codArt = "+planogramaVigente.getCodArt()+" - fechaGen = "+planogramaVigente.getFechaGenMontaje1());
			StringBuffer query = new StringBuffer("SELECT desc_periodo, espacio_promo "
												+ "FROM v_planogramas_piladas p "
												+ "WHERE cod_centro = ? "
												+ "AND cod_art = ? "
												+ "AND capacidad_max = ? "
												+ "AND SYSDATE BETWEEN f_inicio AND f_fin "
												+ "AND fecha_gen = TRUNC(?) "
												+ "AND f_inicio = (SELECT MAX(f_inicio) "
																+ "FROM v_planogramas_piladas p "
																+ "WHERE cod_centro = ? "
																+ "AND cod_art = ? "
																+ "AND capacidad_max = ? "
																+ "AND SYSDATE BETWEEN f_inicio AND f_fin "
																+ "AND fecha_gen = TRUNC(?) "
																+ ")"
												);

			List<Object> params = new ArrayList<Object>();
			logger.info("findDatosVegalsa - codCentro = "+planogramaVigente.getCodCentro());
			logger.info("findDatosVegalsa - codArt = "+planogramaVigente.getCodArt());
			logger.info("findDatosVegalsa - fechaGen = "+planogramaVigente.getFechaGenMontaje1());
			params.add(planogramaVigente.getCodCentro());
			params.add(planogramaVigente.getCodArt());
			params.add(planogramaVigente.getCapacidadMontaje1().intValue());
			params.add(planogramaVigente.getFechaGenMontaje1());
			params.add(planogramaVigente.getCodCentro());
			params.add(planogramaVigente.getCodArt());
			params.add(planogramaVigente.getCapacidadMontaje1().intValue());
			params.add(planogramaVigente.getFechaGenMontaje1());

			try{
				List<DatosAdicionalesVegalsa> res = (List<DatosAdicionalesVegalsa>) this.jdbcTemplate.query(query.toString(),this.rwVPlanogramaDatosAdicionalesVegalsa, params.toArray());

				if (res!=null && res.size()>0){
					DatosAdicionalesVegalsa datosAdicionales = res.get(0);
					logger.info("findDatosVegalsa - res.ofertaProm = "+datosAdicionales.getOfertaPromo()+" - res.espacioPromo = "+datosAdicionales.getEspacioPromo());
					output.setOfertaProm(datosAdicionales.getOfertaPromo());
					output.setEspacioProm(datosAdicionales.getEspacioPromo());
				}
				
				return output;

			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public PlanogramaVigente findDatosIMCVegalsa(PlanogramaVigente planogramaVigente){
		
		if (planogramaVigente == null){
			planogramaVigente = new PlanogramaVigente();
		}

		final PlanogramaVigente planogramaFinal = planogramaVigente;

		if (planogramaVigente.getCodCentro() != null && planogramaVigente.getCodArt() != null){
			
	    	List<Object> params = new ArrayList<Object>();
			StringBuffer query = new StringBuffer("SELECT inc_prevision_venta, sm_estatico "
												+ "FROM (SELECT t.*, "
															+ "MAX(fecha_pedido) KEEP (DENSE_RANK FIRST "
															+ "ORDER BY fecha_pedido DESC) "
															+ "OVER(PARTITION BY cod_centro) max_fecha_pedido "
														+ "FROM t_mis_detallado_vegalsa t "
														+ "WHERE cod_centro = ? "
														+ "AND cod_art      = ? "
														+ ") "
														+ "WHERE fecha_pedido = max_fecha_pedido"
												);
	    	params.add(planogramaVigente.getCodCentro());
	    	params.add(planogramaVigente.getCodArt());

			// RowMapper que reutiliza el planogramaVigente
			RowMapper<PlanogramaVigente> mapper = new RowMapper<PlanogramaVigente>() {
				@Override
				public PlanogramaVigente mapRow(ResultSet rs, int rowNum) throws SQLException {
					long incPrevisionVenta = rs.getLong("inc_prevision_venta");
					if (!rs.wasNull()) {
						planogramaFinal.setIncPrevisionVenta(incPrevisionVenta);
					}

					long smEstatico = rs.getLong("sm_estatico");
					if (!rs.wasNull()) {
						planogramaFinal.setSmEstatico(smEstatico);
					}
					
					return planogramaFinal;
				}
			};

			try {
				planogramaVigente = this.jdbcTemplate.queryForObject(query.toString(), mapper, params.toArray());
	//		    } catch (EmptyResultDataAccessException e) {
	//				Utilidades.mostrarMensajeSinRegistrosSQL(query.toString(), params, "*** No se recuperado ningún registro. "+e.getStackTrace(), e);
			} catch (EmptyResultDataAccessException e){
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		}
		
		return planogramaVigente;
	}
	
	@Override
	public Boolean vieneDeSIA(Long codCentro, Long codArt){
		logger.info("vieneDeSIA - codCentro = "+codCentro+" - codArt = "+codArt);
		StringBuffer query = new StringBuffer("SELECT COUNT(1) "
											+ "FROM v_referencias_pedir_sia "
											+ "WHERE cod_centro = ? "
											+ "AND cod_art = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		try{
			int res = this.jdbcTemplate.queryForInt(query.toString(), params.toArray());
			logger.info("vieneDeSIA - res = "+res);

			if (res>0){
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}

	}
	private class DatosAdicionalesVegalsa{
		private String ofertaPromo;
		private String espacioPromo;
		
		public String getOfertaPromo() {
			return ofertaPromo;
		}
		public void setOfertaPromo(String ofertaPromo) {
			this.ofertaPromo = ofertaPromo;
		}
		public String getEspacioPromo() {
			return espacioPromo;
		}
		public void setEspacioPromo(String espacioPromo) {
			this.espacioPromo = espacioPromo;
		}
	}

}

