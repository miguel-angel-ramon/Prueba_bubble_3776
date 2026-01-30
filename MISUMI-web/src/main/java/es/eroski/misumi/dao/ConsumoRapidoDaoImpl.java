package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ConsumoRapidoDao;
import es.eroski.misumi.model.ConsumoRapido;
import es.eroski.misumi.model.ImagenComercial;
import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.service.iface.ImagenComercialService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ConsumoRapidoDaoImpl implements ConsumoRapidoDao{
	
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(ConsumoRapidoDaoImpl.class);

	@Autowired
	private ImagenComercialService imagenComercialService;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	 private RowMapper<ConsumoRapido> rwConsumoRapidoMap = new RowMapper<ConsumoRapido>() {
			public ConsumoRapido mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				ConsumoRapido output = new ConsumoRapido();
				final Long grupo1 = rs.getLong("GRUPO1");
				output.setGrupo1(grupo1);
				final Long grupo2 = rs.getLong("GRUPO2");
				output.setGrupo2(grupo2);
				final Long grupo3 = rs.getLong("GRUPO3");
				output.setGrupo3(grupo3);
				final Long grupo4 = rs.getLong("GRUPO4");
				output.setGrupo4(grupo4);
				final Long grupo5 = rs.getLong("GRUPO5");
				output.setGrupo5(grupo5);
				
				String descGrupo1 = rs.getString("DESC_GRUPO1");
				descGrupo1 = grupo1 + "-"+descGrupo1.trim();
				output.setDescripGrupo1(descGrupo1);
				String descGrupo2 = rs.getString("DESC_GRUPO2");
				descGrupo2 = grupo2 + "-"+descGrupo2.trim();
				output.setDescripGrupo2(descGrupo2);
				String descGrupo3 = rs.getString("DESC_GRUPO3");
				descGrupo3 = grupo3 + "-"+descGrupo3.trim();
				output.setDescripGrupo3(descGrupo3);
				String descGrupo4 = rs.getString("DESC_GRUPO4");
				descGrupo4 = grupo4 + "-"+descGrupo4.trim();
				output.setDescripGrupo4(descGrupo4);
				String descGrupo5 = rs.getString("DESC_GRUPO5");
				descGrupo5 = grupo5 + "-"+descGrupo5.trim();
				output.setDescripGrupo5(descGrupo5);

				final Long referencia = rs.getLong("REFERENCIA");
				output.setCodArt(referencia);
				final String denominacion = rs.getString("DENOMINACION");
				output.setDescripArt(denominacion);
				final Timestamp tsGrab = rs.getTimestamp("FECHA_GRAB");
				if (tsGrab != null){
					final Date d = new java.util.Date(tsGrab.getTime());
					final String ds = Utilidades.formatearFechaHora(d);
					output.setFechaGrab(ds);	
				}
				
				final Long cantidad = rs.getLong("CANTIDAD");
				output.setCantidad(cantidad);
				final Long vidaUtil = rs.getLong("VIDA_UTIL");
				output.setVidaUtil(vidaUtil);
				final Long codCentro = rs.getLong("COD_CENTRO");
				output.setCodCentro(codCentro);
				
				final Long imc = getImc(codCentro, referencia);

				output.setImc(imc);
				
				return output;
			}
	};
	
	@Override
	public List<ConsumoRapido> findAll(Long codCentro, Long area, Long seccion, String fechaIni, String fechaFin, String index, String sortOrder) throws Exception {
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer(" SELECT V.GRUPO1, V.GRUPO2, V.GRUPO3, V.GRUPO4, V.GRUPO5, ");
    	query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I1' AND O.GRUPO1 = V.GRUPO1) AS DESC_GRUPO1,");
    	query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I2' AND O.GRUPO1 = V.GRUPO1 AND O.GRUPO2 = V.GRUPO2) AS DESC_GRUPO2,");
    	query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I3' AND O.GRUPO1 = V.GRUPO1 AND O.GRUPO2 = V.GRUPO2 AND O.GRUPO3 = V.GRUPO3) AS DESC_GRUPO3,");
		query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I4' AND O.GRUPO1 = V.GRUPO1 AND O.GRUPO2 = V.GRUPO2 AND O.GRUPO3 = V.GRUPO3 AND O.GRUPO4 = V.GRUPO4) AS DESC_GRUPO4,");
		query.append(" (SELECT DISTINCT DESCRIPCION FROM V_AGRU_COMER_REF O WHERE NIVEL = 'I5' AND O.GRUPO1 = V.GRUPO1 AND O.GRUPO2 = V.GRUPO2 AND O.GRUPO3 = V.GRUPO3 AND O.GRUPO4 = V.GRUPO4 AND O.GRUPO5 = V.GRUPO5) AS DESC_GRUPO5,");
		query.append(" A.COD_CENTRO AS COD_CENTRO, A.COD_ARTICULO AS REFERENCIA,V.DESCRIP_ART AS DENOMINACION,A.LAST_UPDATE_DATE AS FECHA_GRAB,A.CANTIDAD AS CANTIDAD,S.VIDA_UTIL_APROV AS VIDA_UTIL ");
		query.append(" FROM VENTA_ANTICIPADA A, V_DATOS_DIARIO_ART V, STOCK_FINAL_MINIMO S");
		query.append(" WHERE A.COD_CENTRO = "+codCentro+" AND A.COD_ARTICULO = V.COD_ART ");
		query.append(" AND A.COD_ARTICULO = S.COD_ARTICULO and A.COD_CENTRO = S.COD_LOC ");
		query.append(" AND V.GRUPO1 = "+area+" AND V.GRUPO2 = "+seccion);
		query.append(" AND A.FECHA_GEN >= TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND A.FECHA_GEN <= TO_DATE('"+fechaFin+"','DD/MM/YYYY') ");
        
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (index != null) {
			order.append(" order by " + this.getMappedField(index) + " " + sortOrder);
			query.append(order);
		}else{
			query.append(" order by GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, REFERENCIA ");
		}
		
		List<ConsumoRapido> output = null;		
		logger.debug("ConsumoRapidoDaoImpl - findAll - QUERY = "+query.toString());
		try {
			output = (List<ConsumoRapido>) this.jdbcTemplate.query(query.toString(),this.rwConsumoRapidoMap); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	    return output;
    }

	 private String getMappedField (String fieldName) {
	  	  if (fieldName.toUpperCase().equals("CODART")){
	  	      return "REFERENCIA";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPART")){
	  	      return "DENOMINACION";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO1")){
	  	      return "GRUPO1";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO2")){
	  	      return "GRUPO2";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO3")){
	  	      return "GRUPO3";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO4")){
	  	      return "GRUPO4";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO5")){
	  	      return "GRUPO5";
	  	  }else if (fieldName.toUpperCase().equals("FECHAGRAB")){
	  	      return "FECHA_GRAB";
	  	  }else if (fieldName.toUpperCase().equals("CANTIDAD")){
	  	      return "CANTIDAD";
	  	  }else if (fieldName.toUpperCase().equals("VIDAUTIL")){
	  	      return "VIDA_UTIL";
	  	  }else {
	  	      return fieldName;
	  	  }
 	}

	protected Long getImc(Long codCentro, Long referencia) {
		ImagenComercial imagenComercial = imagenComercialService.consultaImc(codCentro, referencia);
		if(imagenComercial==null || imagenComercial.getMetodo()==2){
			return imagenComercial.getSfm();
		}else{
			return imagenComercial.getImc();
		}
		
	}	
	 
}
