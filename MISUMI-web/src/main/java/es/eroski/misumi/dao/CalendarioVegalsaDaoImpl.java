/**
 * 
 */
package es.eroski.misumi.dao;

import java.sql.ResultSet;
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

import es.eroski.misumi.dao.iface.CalendarioVegalsaDao;
import es.eroski.misumi.model.calendariovegalsa.DiaCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.DiaDetalleCalendarioVegalsa;
import es.eroski.misumi.model.calendariovegalsa.MapaVegalsa;
import es.eroski.misumi.model.ui.FilterBean;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.model.ui.RulesBean;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

/**
 * MISUMI-301 Implementacion de la capa de persistencia para el calendario de
 * Vegalsa
 * 
 * @author BICUGUAL
 *
 */
@Repository
public class CalendarioVegalsaDaoImpl implements CalendarioVegalsaDao {

	private static Logger logger = Logger.getLogger(CalendarioVegalsaDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<MapaVegalsa> getLstMapasVegalsa() {
		logger.info("getLstMapasVegalsa --> ");
		String query = "SELECT DISTINCT COD_MAPA , RTRIM(DESC_MAPA) DESC_MAPA FROM T_MIS_MAPAS_VEGALSA ORDER BY COD_MAPA";

		List<MapaVegalsa> lstMapas = null;
		try {
			lstMapas = (List<MapaVegalsa>) this.jdbcTemplate.query(query, MapaVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, null, e);
		}

		return lstMapas;
	}

	@Override
	public List<DiaCalendarioVegalsa> getLstDiasCalendarioVegalsa(Long codCentro, Integer codMapa, String mes) {
		logger.info("getLstDiasCalendarioVegalsa --> ");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codMapa);
		params.add(mes);
		params.add(mes);
		
		String query = " SELECT F.COD_CENTRO, F.FECHA_FESTIVO FECHA, DECODE(F.ESTADO,'A','CERRADO', 'B', 'ABIERTO') ESTADO, "
				+ " DECODE(TO_CHAR(FECHA_FESTIVO,'YYYY-MM-DD'),TO_CHAR(CURRENT_DATE,'YYYY-MM-DD'),'DIAACTUAL') DIA_ACTUAL,"
				+ " TO_CHAR(VEG.FECHA_PEDIDO, 'DD-Mon') FECHA_PEDIDO , "
				+ " DECODE(HORA_PED,NULL, NULL, SUBSTR(LPAD(HORA_PED,4,'0'),1,2)||':'||SUBSTR(LPAD(HORA_PED,4,'0'),3,2)) HORA_PEDIDO, "
				+ " TURNO_REPO, MARCADOR FROM FESTIVO_CENTRO F, T_MIS_CALENDARIO_MAPA_VEGALSA VEG "
				+ "	WHERE F.COD_CENTRO = ? " + " AND F.FECHA_GEN IN (SELECT MAX(F2.FECHA_GEN) "
				+ " FROM FESTIVO_CENTRO F2 " + " WHERE F2.COD_CENTRO = F.COD_CENTRO "
				+ " AND F2.FECHA_FESTIVO = F.FECHA_FESTIVO) " + "	AND F.COD_CENTRO = VEG.COD_CENTRO(+) "
				+ " AND F.FECHA_FESTIVO = VEG.FECHA_REPO(+) " + " AND VEG.COD_MAPA(+) = ? "
				+ " AND NVL(VEG.MARCADOR(+),'M') NOT IN ('E') "
				+ " AND FECHA_FESTIVO BETWEEN  TO_DATE('01'|| ? ,'DDYYYY-MM') "
				+ " AND LAST_DAY(TO_DATE('01'|| ? ,'DDYYYY-MM')) " + " AND FECHA_FESTIVO = FECHA_REPO(+) "
				+ " ORDER BY FECHA_FESTIVO ";

		List<DiaCalendarioVegalsa> lstCalendario = null;

		try {
			lstCalendario = (List<DiaCalendarioVegalsa>) this.jdbcTemplate.query(query, params.toArray(), DiaCalendarioVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query, null, e);
		}

		return lstCalendario;
	}
	
	@Override
	public List<DiaDetalleCalendarioVegalsa> getLstDiasDetalleCalendarioVegalsaForGrid(Long codCentro, Integer codMapa, FilterBean filtros, Pagination pagination) {
		logger.info("getLstDiasDetalleCalendarioVegalsa --> ");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT COD_CENTRO,"
				+ "	COD_MAPA, TO_CHAR(FECHA_PEDIDO, 'dd-MM-yyyy') AS FECHA_PEDIDO, TO_CHAR(FECHA_PEDIDO, 'DAY') DIA_SEM_PED, "
				+ " substr(LPAD(hora_ped,4,'0'),1,2) ||':'|| substr(LPAD(hora_ped,4,'0'),3,2) AS HORA_PED, TO_CHAR(FECHA_REPO, 'dd-MM-yyyy') AS FECHA_REPO, TO_CHAR(FECHA_REPO, 'DAY') DIA_SEM_REPO,"
				+ " TURNO_REPO, MARCADOR ");
		
		query.append(" FROM T_MIS_CALENDARIO_MAPA_VEGALSA WHERE COD_CENTRO = ? ");
		
		//Construyo el resto de la WHERE		
		query.append(this.getAndQuery(codMapa, filtros));
		
		//Construyo ORDENACION
		query.append(this.getOrder(pagination));

		//Incluyo la paginacion en la consulta
		if (null != pagination && null != pagination.getPage()) {
			query = new StringBuilder(Paginate.getQueryLimits(pagination, query.toString()));
		}
		
		List<DiaDetalleCalendarioVegalsa> lstCalendario = null;

		try {
			lstCalendario = (List<DiaDetalleCalendarioVegalsa>) this.jdbcTemplate.query(query.toString(), params.toArray(), DiaDetalleCalendarioVegalsaMapper);
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), null, e);
		}

		return lstCalendario;

	}
	
	@Override
	public Long getCountDiasDetalleCalendarioVegalsaForGrid(Long codCentro, Integer codMapa, FilterBean filtros){
		
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		
		StringBuffer query=new StringBuffer("SELECT COUNT(COD_CENTRO) ");
		query.append(" FROM T_MIS_CALENDARIO_MAPA_VEGALSA WHERE COD_CENTRO = ? ");
		
		//Construyo el resto de la WHERE		
		query.append(getAndQuery(codMapa, filtros));
		
		Long count=jdbcTemplate.queryForObject(query.toString(), params.toArray(),  Long.class);
		
		return count;
	}
	
	
	/**
	 * Construye la where de la consulta para el Detalle de Calendario Vegalsa en base a los parametros 
	 * del objeto filtros.
	 * 
	 * @param filtros
	 * @return
	 */
	private String getAndQuery (Integer codMapa, FilterBean filtros){
		
		StringBuffer where=new StringBuffer("");
		
		//Se desea obtener unicamente los registros que pertenezcan a un mapa existente en T_MIS_MAPAS_VEGALSA
		where.append(" AND COD_MAPA IN (SELECT DISTINCT tmmv.COD_MAPA FROM T_MIS_MAPAS_VEGALSA tmmv) ");
		
		if (null != codMapa ){
			where.append(" AND COD_MAPA =  " + codMapa);
		}
		
		if (filtros!=null && filtros.getRules()!=null && filtros.getRules().length>0){
			List<RulesBean> lstRules=Arrays.asList(filtros.getRules());
			
			for (RulesBean rule:lstRules){
				where.append(" AND ");
				where.append(translateConditions(rule));	 
			}
		}
		
		return where.toString();
	}
	
	private String translateConditions(RulesBean rule){
		
		String condition=new String();
		
		if ("FECHA_PEDIDO".equalsIgnoreCase(rule.getField())){
			condition="TO_CHAR(FECHA_PEDIDO, 'dd-MM-yyyy') = '"+rule.getData()+"'";
		}
		else if ("HORA_PED".equalsIgnoreCase(rule.getField())){
			condition="SUBSTR(HORA_PED, 1, 2) || ':' || SUBSTR(HORA_PED, 3, 2) = '"+rule.getData()+"'";
		}
		else if ("DIA_SEM_PED".equalsIgnoreCase(rule.getField())){
			condition = " TRIM(TRANSLATE (UPPER (TO_CHAR(FECHA_PEDIDO, 'DAY')), 'ÁÉÍÓÚ', 'AEIOU')) =  TRANSLATE(UPPER ('"+rule.getData()+"') , 'ÁÉÍÓÚ', 'AEIOU') ";
		}
		else if ("FECHA_REPO".equalsIgnoreCase(rule.getField())){
			condition="TO_CHAR(FECHA_REPO, 'dd-MM-yyyy') = '"+rule.getData()+"'";
		}
		else if("DIA_SEM_REPO".equalsIgnoreCase(rule.getField())){

			condition = " TRIM(TRANSLATE (UPPER (TO_CHAR(FECHA_REPO, 'DAY')), 'ÁÉÍÓÚ', 'AEIOU')) =  TRANSLATE(UPPER ('"+rule.getData()+"') , 'ÁÉÍÓÚ', 'AEIOU') ";
		}
		else{
			condition=" UPPER ("+rule.getField()+")= UPPER ('"+rule.getData()+"')";
		}
		
		return condition;
	}
	
	private String getOrder(Pagination pagination){
		StringBuilder order = new StringBuilder();
		
		if (pagination != null) {
			if (pagination.getSort() != null && !pagination.getSort().isEmpty()) {
				
				String columna = pagination.getSort();
				
				if ("FECHA_PEDIDO".equalsIgnoreCase(columna) || "FECHA_REPO".equalsIgnoreCase(columna)){
					order.append(" order by TO_DATE("+pagination.getSort()+", 'dd-MM-yyyy')");
				}
				else {
					order.append(" order by " + pagination.getSort());
				}
				order.append(" "+pagination.getAscDsc());
				
				return order.toString();
			}
		}

		//Ordenacion por defecto sin criterios de ordenacion
		return " ORDER BY TO_DATE(FECHA_REPO, 'dd-MM-yyyy'), COD_MAPA";
	}

	private RowMapper<MapaVegalsa> MapaVegalsaMapper = new RowMapper<MapaVegalsa>() {
		public MapaVegalsa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new MapaVegalsa(resultSet.getInt("COD_MAPA"), resultSet.getString("DESC_MAPA"));
		}
	};

	private RowMapper<DiaCalendarioVegalsa> DiaCalendarioVegalsaMapper = new RowMapper<DiaCalendarioVegalsa>() {
		public DiaCalendarioVegalsa mapRow(ResultSet rs, int rowNum) throws SQLException {

			DiaCalendarioVegalsa item = new DiaCalendarioVegalsa();

			item.setCodCentro(rs.getLong("COD_CENTRO"));
			item.setFechaCalendario(rs.getDate("FECHA"));
			item.setDiaActual(rs.getString("DIA_ACTUAL"));
			item.setEstado(rs.getString("ESTADO"));
			item.setMarcador(rs.getString("MARCADOR"));
			item.setFechaPedido(rs.getString("FECHA_PEDIDO"));
			item.setHoraPedido(rs.getString("HORA_PEDIDO"));
			item.setTurnoReposicion(rs.getString("TURNO_REPO"));

			return item;
		}
	};
	
	private RowMapper<DiaDetalleCalendarioVegalsa> DiaDetalleCalendarioVegalsaMapper = new RowMapper<DiaDetalleCalendarioVegalsa>() {
		public DiaDetalleCalendarioVegalsa mapRow(ResultSet rs, int rowNum) throws SQLException {

			DiaDetalleCalendarioVegalsa item = new DiaDetalleCalendarioVegalsa();

			item.setCodCentro(rs.getLong("COD_CENTRO"));
			item.setCodMapa(rs.getInt("COD_MAPA"));
			item.setFechaPedido(rs.getString("FECHA_PEDIDO"));
			item.setDiaSemPedido(rs.getString("DIA_SEM_PED"));
			item.setHoraPedido(rs.getString("HORA_PED"));
			item.setFechaReposicion(rs.getString("FECHA_REPO"));
			item.setDiaSemReposicion(rs.getString("DIA_SEM_REPO"));
			item.setTurnoReposicion(rs.getString("TURNO_REPO"));
			item.setMarcador(rs.getString("MARCADOR"));

			return item;
		}
	};

}
