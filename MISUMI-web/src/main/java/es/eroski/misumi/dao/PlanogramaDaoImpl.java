package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PlanogramaDao;
import es.eroski.misumi.model.Planograma;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.TExclusionVentas;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PlanogramaDaoImpl implements PlanogramaDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<Planograma> rwPlanogramaMap = new RowMapper<Planograma>() {
		public Planograma mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Planograma(
					resultSet.getString("NOMBRE_FICHERO"),
					resultSet.getString("FLG_ENVIADO"),
					resultSet.getDate("CREATION_DATE"),
					resultSet.getDate("LAST_UPDATE_DATE"),
					resultSet.getString("MSGID"),
					resultSet.getLong("COD_CENTRO"),
					resultSet.getLong("COD_ART"),
					resultSet.getDouble("STOCK_MIN_COMER"),
					resultSet.getLong("CAPACIDAD_MAX"),
					resultSet.getString("ANO_OFERTA_PILADA"),
					resultSet.getLong("NUM_OFERTA"),
					resultSet.getDate("FECHA_GEN"),
					resultSet.getString("FLG_ENVIADO_PBL"),
					resultSet.getLong("COD_OFERTA"),
					resultSet.getString("TIPO_PLANO"),
					resultSet.getLong("FACING_ALTO"),
					resultSet.getLong("FACING_ANCHO"));
		}
	};

	@Override
	public List<Planograma> findAll(PlanogramaVigente planogramaVigente) throws Exception  {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT * " 
				+ " FROM PLANOGRAMA ");

		if (planogramaVigente  != null){
			if(planogramaVigente.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(planogramaVigente.getCodCentro());	        		
			}
			if(planogramaVigente.getCodArt()!=null){
				where.append(" AND COD_ART = ? ");
				params.add(planogramaVigente.getCodArt());	        		
			}
		}
		where.append(" AND FECHA_GEN = TO_DATE(TO_CHAR(TRUNC(SYSDATE),'DD/MM/YYYY'),'DD/MM/YYYY') ");
		where.append(" AND NUM_OFERTA = 0000 ");

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, cod_art ");
		query.append(order);

		List<Planograma> planogramaLista = null;		

		try {
			planogramaLista = (List<Planograma>) this.jdbcTemplate.query(query.toString(),this.rwPlanogramaMap, params.toArray()); 
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return planogramaLista;
	}

	@Override
	public String updatePlanograma(PlanogramaVigente planogramaVigenteReq) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();


		StringBuffer query = new StringBuffer(" UPDATE PLANOGRAMA SET CAPACIDAD_MAX = ?, STOCK_MIN_COMER = ?, LAST_UPDATE_DATE = TRUNC(SYSDATE), FLG_ENVIADO = 'N' ");

		query.append(" WHERE COD_CENTRO = ? "
				+ "AND COD_ART = ? "
				+ "AND FECHA_GEN = TO_DATE(TO_CHAR(TRUNC(SYSDATE),'DD/MM/YYYY'),'DD/MM/YYYY') "
				+ "AND NUM_OFERTA = 0");

		params = new ArrayList<Object>();

		params.add(planogramaVigenteReq.getCapacidadMaxLineal());
		params.add(planogramaVigenteReq.getStockMinComerLineal());
		params.add(planogramaVigenteReq.getCodCentro());
		params.add(planogramaVigenteReq.getCodArt());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return "0";
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			return "1";
		}
	}

	public String insertPlanograma(PlanogramaVigente planogramaVigenteReq,VPlanograma vPlanograma) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("INSERT INTO PLANOGRAMA (NOMBRE_FICHERO, FLG_ENVIADO, ");
		query.append(" COD_CENTRO, COD_ART, STOCK_MIN_COMER, CAPACIDAD_MAX, ANO_OFERTA_PILADA, NUM_OFERTA, ");
		query.append(" FLG_ENVIADO_PBL, TIPO_PLANO, FACING_ALTO, FACING_ANCHO, FECHA_GEN) ");
		query.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE(TO_CHAR(TRUNC(SYSDATE),'DD/MM/YYYY'),'DD/MM/YYYY')) ");

		params = new ArrayList<Object>();

		params.add(Constantes.PLANOGRAMA_FORZADO_MISUMI);
		params.add("N");
		params.add(planogramaVigenteReq.getCodCentro());
		params.add(planogramaVigenteReq.getCodArt());
		params.add(planogramaVigenteReq.getStockMinComerLineal());
		params.add(planogramaVigenteReq.getCapacidadMaxLineal());
		params.add("0000");
		params.add("0");
		params.add("N");
		params.add("P");
		params.add(vPlanograma.getFacingAlto());
		params.add(vPlanograma.getFacingAncho());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return "0";
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			return "2";
		}
	}
}
