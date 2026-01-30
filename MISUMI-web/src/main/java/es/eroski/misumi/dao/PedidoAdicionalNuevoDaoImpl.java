package es.eroski.misumi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoAdicionalNuevoDao;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.VReferenciasNuevasVegalsa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

@Repository
public class PedidoAdicionalNuevoDaoImpl implements PedidoAdicionalNuevoDao {

	
	private static Logger logger = Logger.getLogger(PedidoAdicionalNuevoDaoImpl.class);

	JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 

	@Override
	public VReferenciasNuevasVegalsa getDatosValidacionVegalsa(PedidoAdicionalNuevo pedidoAdicionalNuevo) {

		logger.debug("---> PedidoAdicionalNuevoDaoImpl.getDatosValidacionVegalsa");

		StringBuilder query = new StringBuilder();
		
		query.append("WITH calendario AS (SELECT m.cod_mapa"
									   + ", (SELECT MIN(fecha_repo) "
									      + "FROM t_mis_calendario_mapa_vegalsa cal "
									      + "WHERE cal.cod_centro = suv.cod_centro "
									      + "AND cal.cod_mapa = m.cod_mapa "
									      + "AND fecha_pedido > TRUNC(SYSDATE) "
									      + "AND NVL(marcador,'0') <> 'E' "
									     + ") primera_fecha_reposicion "
									   + ", ( SELECT MAX(mov.fecha_fin) + 1 "
									   	   + "FROM t_mis_montajes_vegalsa mov "
									   	   + "WHERE mov.cod_centro = suv.cod_centro "
									   	   + "AND mov.cod_art = suv.cod_art "
									   	 + ") max_fecha_montajes "
									   + ", ( SELECT MAX(plp.f_fin) + 1 "
									   	   + "FROM v_planogramas_piladas plp "
									   	   + "WHERE plp.cod_centro = suv.cod_centro "
									   	   + "AND plp.cod_art = suv.cod_art "
									   	 + ") max_fecha_piladas "
									   + ", suv.catalogo_cen"
									   + ", suv.tipo_aprov"
									   + ", suv.uc "
									   + ", param.dias_maximos "
									   + ", param.cantidad_maxima "
								  + "FROM t_mis_surtido_vegalsa suv, v_datos_diario_art dda, t_mis_mapas_vegalsa m "
								  	 + ", t_mis_param_montajes_vegalsa param, v_centros_plataformas c "
								  + "WHERE suv.cod_art = dda.cod_art "
								  + "AND suv.marca_maestro_centro = 'S' "
								  + "AND suv.reapro = 'S' "
								  + "AND suv.tipo_aprov != 'D' "
								  + "AND suv.cod_centro = ? "
								  + "AND suv.cod_art = m.cod_art "
								  + "AND c.cod_centro = suv.cod_centro "
								  + "AND c.negocio = param.negocio "
								  + "AND ((param.flg_solo_compra = 'S' "
										  + "AND dda.tipo_compra_venta = 'C' "
										+") "
									   + "OR "
									   + "( dda.tipo_compra_venta = 'T' ) "
									 + ") "
								  + "AND c.cod_ensena = DECODE(param.ensena, 0, c.cod_ensena, param.ensena) "
								  + "AND dda.grupo1 = param.area "
								  + "AND dda.grupo2 = DECODE(param.seccion, 0, dda.grupo2, param.seccion) "
								  + "AND dda.grupo3 = DECODE(param.categoria, 0, dda.grupo3, param.categoria) "
								  + "AND NOT EXISTS (SELECT 1 "
								  				  + "FROM relacion_articulo rea "
								  				  + "WHERE rea.cod_centro = suv.cod_centro "
								  				  + "AND rea.cod_art_rela = suv.cod_art "
								  				  + "AND rea.tipo_rela = 3 "
								  				  + "AND rea.a_b_m != 'B' "
								  				  + "AND rea.fecha_gen = ( SELECT MAX(rea2.fecha_gen) "
								  				  						+ "FROM relacion_articulo rea2 "
								  				  						+ "WHERE rea2.cod_centro = rea.cod_centro "
								  				  						+ "AND rea2.cod_art = rea.cod_art "
								  				  						+ "AND rea2.cod_art_rela = rea.cod_art_rela "
								  				  						+ "AND rea2.tipo_rela = rea.tipo_rela "
								  				  					  + ") "
								  				  + ") "
								  + "AND suv.cod_art = ? "
								  + "AND suv.fecha_gen IN (SELECT MAX(suv2.fecha_gen) "
								  						+ "FROM t_mis_surtido_vegalsa suv2 "
								  						+ "WHERE suv2.cod_centro = suv.cod_centro "
								  						+ "AND suv2.cod_art = suv.cod_art "
								  					   + ") "
								  + "AND EXISTS (SELECT 1 "
								  			  + "FROM t_mis_calendario_mapa_vegalsa c "
								  			  + "WHERE c.cod_centro = suv.cod_centro "
								  			  + "AND c.cod_mapa = m.cod_mapa "
								  			  + "AND fecha_pedido > TRUNC(SYSDATE)"
								  			 + ")"
							+ ") "
			+ "SELECT cod_mapa, TO_DATE(TO_CHAR(GREATEST(NVL(primera_fecha_reposicion, '01-01-1900'), NVL(max_fecha_montajes, '01-01-1900')"
				 + ", NVL(max_fecha_piladas, '01-01-1900')), 'DD-MM-YYYY'), 'DD-MM-YYYY') fecha_inicio, catalogo_cen, tipo_aprov, uc, dias_maximos, cantidad_maxima "
			+ "FROM calendario"
			);

		List<Object> params = new ArrayList<Object>();
		params.add(pedidoAdicionalNuevo.getCodCentro());
		params.add(pedidoAdicionalNuevo.getCodArticulo());
		
		logger.debug(query);
		List<VReferenciasNuevasVegalsa> lstReferenciaNuevaVegalsa = jdbcTemplate.query(query.toString(), params.toArray(), new VReferenciasNuevasVegalsaMapper());
		return lstReferenciaNuevaVegalsa.get(0);

	}

	private class VReferenciasNuevasVegalsaMapper implements RowMapper<VReferenciasNuevasVegalsa> {

		@Override
		public VReferenciasNuevasVegalsa mapRow(ResultSet rs, int rowNum) throws SQLException {

			VReferenciasNuevasVegalsa referenciaNuevaVegalsa = new VReferenciasNuevasVegalsa();
			referenciaNuevaVegalsa.setCodMapa(rs.getObject("cod_mapa")!=null?rs.getLong("cod_mapa"):null);
			referenciaNuevaVegalsa.setPrimeraFechaReposicion(rs.getDate("fecha_inicio")!=null?rs.getDate("fecha_inicio"):null);
			referenciaNuevaVegalsa.setCatalogoCen(rs.getString("catalogo_cen")!=null?rs.getString("catalogo_cen"):null);
			referenciaNuevaVegalsa.setTipoAprov(rs.getString("catalogo_cen")!=null?rs.getString("catalogo_cen"):null);
			referenciaNuevaVegalsa.setUc(rs.getObject("uc")!=null?rs.getDouble("uc"):null);
			referenciaNuevaVegalsa.setDiasMaximos(rs.getObject("dias_maximos")!=null?rs.getLong("dias_maximos"):null);
			referenciaNuevaVegalsa.setCantidadMaxima(rs.getObject("cantidad_maxima")!=null?rs.getLong("cantidad_maxima"):null);

			return referenciaNuevaVegalsa;
		}
	}
	
}
