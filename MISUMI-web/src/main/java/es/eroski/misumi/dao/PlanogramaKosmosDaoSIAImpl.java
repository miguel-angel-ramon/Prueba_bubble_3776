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

import es.eroski.misumi.dao.iface.PlanogramaKosmosDaoSIA;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PlanogramaKosmosDaoSIAImpl implements PlanogramaKosmosDaoSIA{
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSourceSIA) {
		this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	} 
	
	private RowMapper<PlanogramaVigente> rwPlanogramaKosmosMap = new RowMapper<PlanogramaVigente>() {
		public PlanogramaVigente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			PlanogramaVigente pVigenteKosmos =  new PlanogramaVigente();
			pVigenteKosmos.setCapacidadMaxLineal(resultSet.getLong("CAPACIDAD"));
			pVigenteKosmos.setStockMinComerLineal(resultSet.getFloat("FACING"));
			pVigenteKosmos.setTipoPlano(resultSet.getString("TIPO_PLANO"));
			pVigenteKosmos.setFacingAlto(resultSet.getLong("FACING_ALTO"));
			pVigenteKosmos.setFacingAncho(resultSet.getLong("FACING_ANCHO"));
			pVigenteKosmos.setEsCajaExp(resultSet.getString("ES_CAJA_EXP"));
			return pVigenteKosmos;
		}
	};
	
	
	@Override
	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT FACING, CAPACIDAD, FACING_ALTO, FACING_ANCHO, TIPO_PLANO, ES_CAJA_EXP " 
				+ " FROM V_KOSMOS_APR_PLANOGRAMAS ");


		if (planogramaVigente  != null){
			if(planogramaVigente.getCodCentro()!=null){
				where.append(" AND COD_LOC = ? ");
				params.add(planogramaVigente.getCodCentro());	        		
			}
			if(planogramaVigente.getCodArt()!=null){
				where.append(" AND COD_ART_FORMLOG = ? ");
				params.add(planogramaVigente.getCodArt());	        		
			}
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by COD_LOC, COD_ART_FORMLOG ");
		query.append(order);

		List<PlanogramaVigente> planogramaVigenteLista = null;		

		try {
			planogramaVigenteLista = (List<PlanogramaVigente>) this.jdbcTemplate.query(query.toString(),this.rwPlanogramaKosmosMap, params.toArray()); 
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return planogramaVigenteLista;
	}
}
