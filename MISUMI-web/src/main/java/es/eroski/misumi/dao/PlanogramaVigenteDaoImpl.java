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

import es.eroski.misumi.dao.iface.PlanogramaVigenteDao;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PlanogramaVigenteDaoImpl implements PlanogramaVigenteDao{

	private JdbcTemplate jdbcTemplate;
	//private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	private RowMapper<PlanogramaVigente> rwPlanogramaVigenteMap = new RowMapper<PlanogramaVigente>() {
		public PlanogramaVigente mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new PlanogramaVigente(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
					resultSet.getFloat("STOCK_MIN_COMER_LINEAL"), resultSet.getLong("CAPACIDAD_MAX_LINEAL"),
					resultSet.getFloat("STOCK_MIN_COMER_CABECERA"),resultSet.getLong("CAPACIDAD_MAX_CABECERA"),
					resultSet.getDate("FECHA_GEN_LINEAL"), resultSet.getString("ANO_OFERTA_PILADA"),
					resultSet.getLong("NUM_OFERTA"),resultSet.getDate("FECHA_INI"),
					resultSet.getDate("FECHA_FIN"),resultSet.getDate("FECHA_GEN_CABECERA"),
					resultSet.getString("SIMULADO_LINEAL"),resultSet.getFloat("CAPACI_MA1"),
					resultSet.getFloat("FACING_MA1"), resultSet.getString("FLG_CABECERA_MA1"),
					resultSet.getString("FLG_OFERTA_MA1"), resultSet.getString("FLG_CAMPANA_MA1"),
					resultSet.getDate("FECHA_GEN_MA1"), resultSet.getFloat("CAPACI_MA2"),
					resultSet.getFloat("FACING_MA2"), resultSet.getString("FLG_CABECERA_MA2"),
					resultSet.getString("FLG_OFERTA_MA2"), resultSet.getString("FLG_CAMPANA_MA2"),
					resultSet.getDate("FECHA_GEN_MA2"), resultSet.getDate("CREATION_DATE"),
					resultSet.getDate("UPDATE_DATE")
					);
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public List<PlanogramaVigente> findAll(PlanogramaVigente planogramaVigente) throws Exception  {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT COD_CENTRO, COD_ART, STOCK_MIN_COMER_LINEAL, CAPACIDAD_MAX_LINEAL"
												 + ", STOCK_MIN_COMER_CABECERA, CAPACIDAD_MAX_CABECERA, FECHA_GEN_LINEAL"
												 + ", ANO_OFERTA_PILADA, NUM_OFERTA, FECHA_INI, FECHA_FIN, FECHA_GEN_CABECERA"
												 + ", SIMULADO_LINEAL, CAPACI_MA1, FACING_MA1, FLG_CABECERA_MA1, FLG_OFERTA_MA1"
												 + ", FLG_CAMPANA_MA1, FECHA_GEN_MA1, CAPACI_MA2, FACING_MA2, FLG_CABECERA_MA2"
												 + ", FLG_OFERTA_MA2, FLG_CAMPANA_MA2, FECHA_GEN_MA2, CREATION_DATE, UPDATE_DATE " 
											+ "FROM planograma_vigente ");

		where.append("WHERE 1=1 ");

		if (planogramaVigente  != null){
			if (planogramaVigente.getCodCentro()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(planogramaVigente.getCodCentro());	        		
			}
			if (planogramaVigente.getCodArt()!=null){
				where.append(" AND COD_ART = ? ");
				params.add(planogramaVigente.getCodArt());	        		
			}
			if (planogramaVigente.getStockMinComerLineal()!=null){
				where.append(" AND STOCK_MIN_COMER_LINEAL = ? ");
				params.add(planogramaVigente.getStockMinComerLineal());	        		
			}
			if (planogramaVigente.getCapacidadMaxLineal()!=null){
				where.append(" AND CAPACIDAD_MAX_LINEAL = ? ");
				params.add(planogramaVigente.getCapacidadMaxLineal());	        		
			}
			if (planogramaVigente.getFechaGenLineal()!=null){
				where.append(" AND FECHA_GEN_LINEAL = TRUNC(?) ");
				params.add(planogramaVigente.getFechaGenLineal());	        		
			}
			if (planogramaVigente.getCapacidadMontaje1()!=null){
				where.append(" AND CAPACI_MA1 = ? ");
				params.add(planogramaVigente.getCapacidadMontaje1());	        		
			}
			if (planogramaVigente.getFacingMontaje1()!=null){
				where.append(" AND FACING_MA1 = ? ");
				params.add(planogramaVigente.getFacingMontaje1());	        		
			}
			if (planogramaVigente.getFlgCabeceraMontaje1()!=null){
				where.append(" AND FLG_CABECERA_MA1 = upper(?) ");
				params.add(planogramaVigente.getFlgCabeceraMontaje1());	        		
			}
			if (planogramaVigente.getFlgOfertaMontaje1()!=null){
				where.append(" AND FLG_OFERTA_MA1 = upper(?) ");
				params.add(planogramaVigente.getFlgOfertaMontaje1());	        		
			}
			if (planogramaVigente.getFlgCampanaMontaje1()!=null){
				where.append(" AND FLG_CAMPANA_MA1 = upper(?) ");
				params.add(planogramaVigente.getFlgCampanaMontaje1());	        		
			}
			if (planogramaVigente.getFechaGenMontaje1()!=null){
				where.append(" AND FECHA_GEN_MA1 = TRUNC(?) ");
				params.add(planogramaVigente.getFechaGenMontaje1());	        		
			}
			if(planogramaVigente.getCapacidadMontaje2()!=null){
				where.append(" AND CAPACI_MA2 = ? ");
				params.add(planogramaVigente.getCapacidadMontaje2());	        		
			}
			if(planogramaVigente.getFacingMontaje2()!=null){
				where.append(" AND FACING_MA2 = ? ");
				params.add(planogramaVigente.getFacingMontaje2());	        		
			}
			if(planogramaVigente.getFlgCabeceraMontaje2()!=null){
				where.append(" AND FLG_CABECERA_MA2 = upper(?) ");
				params.add(planogramaVigente.getFlgCabeceraMontaje2());	        		
			}
			if(planogramaVigente.getFlgOfertaMontaje2()!=null){
				where.append(" AND FLG_OFERTA_MA2 = upper(?) ");
				params.add(planogramaVigente.getFlgOfertaMontaje2());	        		
			}
			if(planogramaVigente.getFlgCampanaMontaje2()!=null){
				where.append(" AND FLG_CAMPANA_MA2 = upper(?) ");
				params.add(planogramaVigente.getFlgCampanaMontaje2());	        		
			}
			if(planogramaVigente.getFechaGenMontaje2()!=null){
				where.append(" AND FECHA_GEN_MA2 = TRUNC(?) ");
				params.add(planogramaVigente.getFechaGenMontaje2());	        		
			}
			if(planogramaVigente.getCreationDate()!=null){
				where.append(" AND CREATION_DATE = TRUNC(?) ");
				params.add(planogramaVigente.getCreationDate());	        		
			}
			if(planogramaVigente.getUpdateDate()!=null){
				where.append(" AND UPDATE_DATE = TRUNC(?) ");
				params.add(planogramaVigente.getUpdateDate());	        		
			}
		}

		query.append(where);

		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, cod_art ");
		query.append(order);

		List<PlanogramaVigente> planogramaVigenteLista = null;		

		try {

			planogramaVigenteLista = (List<PlanogramaVigente>) this.jdbcTemplate.query(query.toString(),this.rwPlanogramaVigenteMap, params.toArray()); 

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return planogramaVigenteLista;
	}

	@Override
	public String updatePlanogramaVigente(PlanogramaVigente planogramaVigenteReq) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();


		StringBuffer query = new StringBuffer(" UPDATE PLANOGRAMA_VIGENTE SET CAPACIDAD_MAX_LINEAL = ?, STOCK_MIN_COMER_LINEAL = ?");

		query.append(" WHERE COD_CENTRO = ? AND COD_ART = ? ");

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
}
