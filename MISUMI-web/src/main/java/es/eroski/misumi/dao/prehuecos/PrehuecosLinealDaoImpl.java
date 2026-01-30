package es.eroski.misumi.dao.prehuecos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosLinealDao;
import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class PrehuecosLinealDaoImpl implements PrehuecosLinealDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

    @Override
    public Long getPrehuecosSinValidar(PrehuecosLineal prehuecosLineal, Boolean esHoy){
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer("SELECT COUNT(1) numPrehuecos " 
											+ "FROM t_mis_prehuecos_lineal "
											+ "WHERE cod_centro = ? "
											+ "AND mac 			= ? "
    										);

    	params.add(prehuecosLineal.getCodCentro());
    	params.add(prehuecosLineal.getMac());	        		

    	if (esHoy!=null && esHoy.equals(Boolean.TRUE)){
    		query.append("AND cod_art 	   = ?");
    		query.append("AND (estado 	   < 2 ");
    		query.append("OR fec_validado >= TRUNC(SYSDATE))");

    		params.add(prehuecosLineal.getCodArticulo());	        		
    	} else {
    		query.append("AND estado 	   < 2");
    	}

		Long numPrehuecos = 0L;
		try {
			numPrehuecos = this.jdbcTemplate.queryForObject(query.toString(), params.toArray(), Long.class);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return numPrehuecos;
    }

	@Override
	public PrehuecosLineal getStockLinealEstadoRef(PrehuecosLineal prehuecosLineal) throws Exception {
    	List<Object> params = new ArrayList<Object>();
    	PrehuecosLineal prehuecosLinealCompletado = prehuecosLineal;
    	
    	StringBuffer query = new StringBuffer("SELECT stock_lineal, estado " 
											+ "FROM t_mis_prehuecos_lineal "
											+ "WHERE cod_centro = ? "
											+ "AND mac 			= ? "
											+ "AND cod_art      = ? "
											+ "AND (estado      < 2 "
												 + "OR fec_validado >= TRUNC(SYSDATE)"
												 + ")"
    										);

    	params.add(prehuecosLineal.getCodCentro());
    	params.add(prehuecosLineal.getMac());
   		params.add(prehuecosLineal.getCodArticulo());

		try {
			prehuecosLinealCompletado = this.jdbcTemplate.queryForObject(query.toString(), this.rwPrehuecosLinealMapper, params.toArray());
	    } catch (EmptyResultDataAccessException e) {
			//Utilidades.mostrarMensajeSinRegistrosSQL(query.toString(), params, "*** No se recuperado ningún registro. "+e.getStackTrace(), e);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return prehuecosLinealCompletado;
	}
    
	@Override
	public int deletePrehuecos(PrehuecosLineal prehuecosLineal){
		List<Object> params = new ArrayList<Object>();
		int numBorrados = 0;
		
		StringBuffer query = new StringBuffer("DELETE FROM t_mis_prehuecos_lineal "
											+ "WHERE cod_centro = ? "
											+ "AND mac 			= ? "
											+ "AND estado 	   != 2 "
											);
		
		params.add(prehuecosLineal.getCodCentro());
		params.add(prehuecosLineal.getMac());

		if (prehuecosLineal.getCodArticulo()!=null){
			query.append("AND cod_art = ?");
			params.add(prehuecosLineal.getCodArticulo());
		}

		try {
			 numBorrados = this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return numBorrados;
	}

	@Override
	public int insertPrehuecos(PrehuecosLineal prehuecosLineal) {
		List<Object> params = new ArrayList<Object>();
		int numInsertados = 0;		
		
		StringBuffer query = new StringBuffer("INSERT INTO t_mis_prehuecos_lineal "
													+ "( cod_centro"
													+ ", mac"
													+ ", cod_art"
													+ ", stock_lineal"
													+ ", estado"
													+ ", creation_date"
													+ ", last_update_date"
													+ ", fec_validado"
													+ ")"
											+ "VALUES "
													+ "( ?"
													+ ", ?"
													+ ", ?"
													+ ", ?"
													+ ", 0"
													+ ", SYSDATE"
													+ ", SYSDATE"
													+ ", NULL"
													+ ")"
											);

		params.add(prehuecosLineal.getCodCentro());
		params.add(prehuecosLineal.getMac());
		params.add(prehuecosLineal.getCodArticulo());
		params.add(prehuecosLineal.getStockLineal());
	
		try {
			numInsertados = this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return numInsertados;
	}

	@Override
	public int updatePrehuecos(PrehuecosLineal prehuecosLineal) {
		List<Object> params = new ArrayList<Object>();
		int numModificados = 0;		
		
		StringBuffer query = new StringBuffer("UPDATE t_mis_prehuecos_lineal "
											+ "SET stock_lineal 	= ?"
											  + ", estado 			= 0"
											  + ", fec_validado 	= NULL"
											  + ", last_update_date = SYSDATE "
											+ "WHERE cod_centro = ? "
											+ "AND mac 			= ? "
											+ "AND cod_art		= ? "
											+ "AND (estado       < 2 "
											+ "OR fec_validado >= TRUNC(SYSDATE))"
											);
		params.add(prehuecosLineal.getStockLineal());
		params.add(prehuecosLineal.getCodCentro());
		params.add(prehuecosLineal.getMac());
		params.add(prehuecosLineal.getCodArticulo());
	
		try {
			 numModificados = this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return numModificados;
	}

	private RowMapper<PrehuecosLineal> rwPrehuecosLinealMapper = new RowMapper<PrehuecosLineal>() {
		public PrehuecosLineal mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			PrehuecosLineal prehuecosLineal = new PrehuecosLineal();
			
			prehuecosLineal.setStockLineal(resultSet.getLong("stock_lineal"));
			prehuecosLineal.setEstadoRef(resultSet.getLong("estado"));
			
			return prehuecosLineal;
		}
	};

}
