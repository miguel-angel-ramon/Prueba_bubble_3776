package es.eroski.misumi.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VReferenciaActiva2Dao;
import es.eroski.misumi.model.VReferenciaActiva2;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VReferenciaActiva2DaoImpl implements VReferenciaActiva2Dao{
	

	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VRelacionArticuloDaoImpl.class);
	 private RowMapper<VReferenciaActiva2> rwDateMap = new RowMapper<VReferenciaActiva2>() {
	        public VReferenciaActiva2 mapRow(ResultSet resultSet, int i) throws SQLException {
	        	VReferenciaActiva2 vRefAct = new VReferenciaActiva2();
	        	vRefAct.setActiva(resultSet.getString("ACTIVA"));
	        	vRefAct.setMon(resultSet.getInt("PED_LUN"));
	        	vRefAct.setTue(resultSet.getInt("PED_MAR"));
	        	vRefAct.setWed(resultSet.getInt("PED_MIE"));
	        	vRefAct.setThu(resultSet.getInt("PED_JUE"));
	        	vRefAct.setFri(resultSet.getInt("PED_VIE"));
	        	vRefAct.setSat(resultSet.getInt("PED_SAB"));
	        	vRefAct.setSun(resultSet.getInt("PED_DOM"));
	          return vRefAct;
	        }
	      };
		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    /* (non-Javadoc)
		 * @see es.eroski.misumi.dao.VReferenciaActiva2Dao#getNextDiaPedido(java.lang.Long, java.lang.Long)
		 */
	    @Override
		public List<VReferenciaActiva2> getNextDiaPedido(Long codCentro, Long codArt) throws Exception  {
			
			String nextDayStr = null;
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT ACTIVA, PED_LUN, PED_MAR, PED_MIE, PED_JUE, PED_VIE, PED_SAB, PED_DOM " 
	    										+ " FROM V_REFERENCIA_ACTIVA2 ");
	    

	        where.append(" AND COD_CENTRO = ? ");
		    params.add(codCentro);	        		
	        	
	        where.append(" AND COD_ART = ? ");
		    params.add(codArt);	        		

	        	

	        
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(3000);
			order.append(" ORDER BY COD_ART_MAPA DESC ");
			query.append(order);
		
			List<VReferenciaActiva2> lista = null; 
			try {
				lista = (List<VReferenciaActiva2>) this.jdbcTemplate.query(query.toString(),this.rwDateMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
			
			return lista;

	    }
	   
}
