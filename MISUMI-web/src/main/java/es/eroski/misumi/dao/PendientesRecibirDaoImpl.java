package es.eroski.misumi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PendientesRecibirDao;
import es.eroski.misumi.model.PendientesRecibir;
 	
@Repository
public class PendientesRecibirDaoImpl implements PendientesRecibirDao{
	
	 private JdbcTemplate jdbcTemplate;
	

	 	@Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	 
    
	    @Override
	    public PendientesRecibir find(final PendientesRecibir pr) throws Exception  {
	    	
			List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();

			declaredParameters.add(new SqlParameter("p_cod_centro", Types.INTEGER));
			declaredParameters.add(new SqlParameter("p_cod_art", Types.INTEGER));
			declaredParameters.add(new SqlOutParameter("p_cant_hoy", Types.INTEGER));
			declaredParameters.add(new SqlOutParameter("p_cant_futura", Types.INTEGER));
			declaredParameters.add(new SqlOutParameter("p_cod_error", Types.INTEGER));
			
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {

			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_UNI_PEDIDOS.p_uni_obt_cant_pdte_recibir(?, ?, ?, ?, ?)}");

			        stmnt.setInt(1, pr.getCodCentro().intValue());
			        stmnt.setInt(2, pr.getCodArt().intValue());
			        stmnt.registerOutParameter(3, Types.INTEGER);
			        stmnt.registerOutParameter(4, Types.INTEGER);
			        stmnt.registerOutParameter(5, Types.INTEGER);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			
			PendientesRecibir pendientesRecibirSalida = new PendientesRecibir();
			
			pendientesRecibirSalida.setCodCentro(pr.getCodCentro());
			pendientesRecibirSalida.setCodArt(pr.getCodArt());
			Long valorPCantHoy = new Long(0);
			Long valorPCantFutura = new Long(0);
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error")!=null && "0".equals(mapaResultados.get("p_cod_error").toString())){
					valorPCantHoy = Long.parseLong(mapaResultados.get("p_cant_hoy").toString());
					valorPCantFutura = Long.parseLong(mapaResultados.get("p_cant_futura").toString());
				}
			}
			pendientesRecibirSalida.setCantHoy(valorPCantHoy);
			pendientesRecibirSalida.setCantFutura(valorPCantFutura);
			
		    return pendientesRecibirSalida;
	    }
}


