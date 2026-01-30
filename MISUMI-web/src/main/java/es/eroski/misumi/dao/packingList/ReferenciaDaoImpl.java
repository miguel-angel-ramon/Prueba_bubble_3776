package es.eroski.misumi.dao.packingList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.packingList.iface.ReferenciaDao2;
import es.eroski.misumi.model.pda.packingList.FechaReferencia;

@Repository
public class ReferenciaDaoImpl implements ReferenciaDao2{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	
	@Override
	public String getUltimaFechaFestiva(FechaReferencia fechaReferencia) throws Exception {
		String query = "SELECT TO_CHAR(MAX(FECHA_FESTIVO), 'YYYY-MM-DD') " + "FROM FESTIVO_CENTRO F "
				+ "WHERE COD_CENTRO = ? " + "AND FECHA_FESTIVO < TRUNC(SYSDATE) " + "AND ESTADO = 'B' "
				+ "AND FECHA_GEN IN (SELECT MAX(FECHA_GEN) " + "FROM FESTIVO_CENTRO F2 "
				+ "WHERE F2.COD_CENTRO = F.COD_CENTRO " + "AND F2.FECHA_FESTIVO = F.FECHA_FESTIVO)";

		try {
			String fechaFestiva = jdbcTemplate.queryForObject(query, new Object[] { fechaReferencia.getCodCentro() },
					String.class);
			fechaReferencia.setFechaFestivo(fechaFestiva != null ? fechaFestiva : null);
			return fechaReferencia.getFechaFestivo();
		} catch (Exception e) {
			throw new Exception("Error al obtener la fecha festiva desde la base de datos", e);
		}
	}
	
}
