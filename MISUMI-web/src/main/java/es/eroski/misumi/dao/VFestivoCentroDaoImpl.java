package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.model.VFacingX;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VFestivoCentroDaoImpl implements VFestivoCentroDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public String getNextDay(VFestivoCentro vFestivoCentro)  throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT TO_CHAR(MIN(A.FECHA_FESTIVO), 'DDMMYYYY') FROM FESTIVO_CENTRO A ");
		query.append("WHERE A.COD_CENTRO = ? AND A.FECHA_FESTIVO > ? AND A.ESTADO = 'B' ");
		
		query.append(" AND A.FECHA_GEN =  (SELECT MAX(B.FECHA_GEN) ");
		query.append(" FROM festivo_centro  B ");
		query.append(" WHERE B.COD_CENTRO = A.COD_CENTRO  AND B.fecha_festivo = A.fecha_festivo ");
		//Comentado por petición 59566 query.append(" and b.FLG_ENVIADO_PBL = 'S' ");
		query.append(" ) ");
		
		params.add(vFestivoCentro.getCodCentro());
		params.add(vFestivoCentro.getFechaFestivo());
		
		String nextDay = null;
		try {
	    	nextDay = this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		 return nextDay;

	}

	@Override
	public String getDiaLaborable(VFestivoCentro vFestivoCentro)  throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT TO_CHAR(MIN(A.FECHA_FESTIVO), 'DDMMYYYY') FROM FESTIVO_CENTRO A ");
		query.append("WHERE A.COD_CENTRO = ? AND A.FECHA_FESTIVO >= ? AND A.ESTADO = 'B' ");
		
		query.append(" AND A.FECHA_GEN =  (SELECT MAX(B.FECHA_GEN) ");
		query.append(" FROM festivo_centro  B ");
		query.append(" WHERE B.COD_CENTRO = A.COD_CENTRO  AND B.fecha_festivo = A.fecha_festivo ");
		//Comentado por petición 59566 query.append(" and b.FLG_ENVIADO_PBL = 'S' ");
		query.append(" ) ");
		
		params.add(vFestivoCentro.getCodCentro());
		params.add(vFestivoCentro.getFechaFestivo());
		
		
		String dia = null;
		try {
			dia = this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		 return dia;

	}
	
	@Override
	public Integer getNumDias(VFestivoCentro vFestivoCentro)  throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COUNT(A.FECHA_FESTIVO) FROM FESTIVO_CENTRO A ");
		query.append("WHERE A.COD_CENTRO = ? AND A.FECHA_FESTIVO BETWEEN ? AND ? AND A.ESTADO = 'B' ");
		
		query.append(" AND A.FECHA_GEN =  (SELECT MAX(B.FECHA_GEN) ");
		query.append(" FROM festivo_centro  B ");
		query.append(" WHERE B.COD_CENTRO = A.COD_CENTRO  AND B.fecha_festivo = A.fecha_festivo ");
		//Comentado por petición 59566 query.append(" and b.FLG_ENVIADO_PBL = 'S' ");
		query.append(" ) ");
		
		params.add(vFestivoCentro.getCodCentro());
		params.add(vFestivoCentro.getFechaInicio());
		params.add(vFestivoCentro.getFechaFin());
		
		Integer numDias =  null;
		try {
			numDias = this.jdbcTemplate.queryForObject(query.toString(), Integer.class, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
		if (numDias == 0){
			numDias = 1;
		}
		return numDias;

	}

	public List<Map<String,Object>> getNextDays(VFestivoCentro vFestivoCentro, Integer numMaximoDias) throws Exception{
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		
		//Si el número de días indicado es 0 no se hace control por número de días
		query = new StringBuffer("SELECT * FROM ( ");			

		query.append("SELECT TO_CHAR(A.FECHA_FESTIVO, 'DDMMYYYY') SIGUIENTE_DIA FROM FESTIVO_CENTRO A ");
		query.append("WHERE 1 = 1 ");
		if(vFestivoCentro.getCodCentro() != null){
			query.append(" AND A.COD_CENTRO = ? ");
			params.add(vFestivoCentro.getCodCentro());
		}
		if(vFestivoCentro.getFechaInicio() != null){
			query.append(" AND A.FECHA_FESTIVO > TRUNC (?) ");
			params.add(vFestivoCentro.getFechaInicio());
		}
		if(vFestivoCentro.getFechaFin() != null){
			query.append(" AND A.FECHA_FESTIVO <= TRUNC (?) ");
			params.add(vFestivoCentro.getFechaFin());
		}
		query.append(" AND A.ESTADO = 'B' ");
		query.append(" AND A.FECHA_GEN =  (SELECT MAX(B.FECHA_GEN) ");
		query.append(" FROM festivo_centro  B ");
		query.append(" WHERE B.COD_CENTRO = A.COD_CENTRO  AND B.fecha_festivo = A.fecha_festivo ");
		//Comentado por petición 59566 query.append(" and b.FLG_ENVIADO_PBL = 'S' ");
		query.append(" ) ");
		query.append(" ORDER BY TO_DATE(SIGUIENTE_DIA) ASC ");
		
		query.append(") ");
		if (numMaximoDias > 0){
			query.append(" WHERE ROWNUM <= ? ");
			params.add(numMaximoDias);
		}
	    

	    List<Map<String,Object>> listaFechasSiguientes = null;
	    
	    try {
	    	listaFechasSiguientes = this.jdbcTemplate.queryForList(query.toString(), params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return listaFechasSiguientes;

	}
}
