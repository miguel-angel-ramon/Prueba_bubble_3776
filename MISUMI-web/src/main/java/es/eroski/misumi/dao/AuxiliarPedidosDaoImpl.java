package es.eroski.misumi.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.AuxiliarPedidosDao;
 	
@Repository
public class AuxiliarPedidosDaoImpl implements AuxiliarPedidosDao{
	
	private JdbcTemplate jdbcTemplate;


	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean existsAuxiliarPedidos() {
		
		StringBuilder query = new StringBuilder();
		
		query.append("SELECT COUNT(1) "
				   + "FROM AUXILIAR_PEDIDOS "
				   + "WHERE NIVEL=0 AND TIPO='P' AND CODIGO=0"
				    );
		
		int count = this.jdbcTemplate.queryForObject(query.toString(), Integer.class);
		return (count > 0);
		
	} 

}
