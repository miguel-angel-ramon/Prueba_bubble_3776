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

import es.eroski.misumi.dao.iface.VDatosDiarioCapDao;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.VDatosDiarioCap;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VDatosDiarioCapDaoImpl implements VDatosDiarioCapDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VDatosDiarioCapDaoImpl.class);

	    private RowMapper<VDatosDiarioCap> rwDatosDiarioCapMap = new RowMapper<VDatosDiarioCap>() {
			public VDatosDiarioCap mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VDatosDiarioCap(resultSet.getLong("COD_ART"),resultSet.getString("DESCRIP_ART").trim(),
			    			resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"), 
			    			resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"),
			    			resultSet.getLong("GRUPO5"), resultSet.getLong("ESTLOG_N1"),
			    			resultSet.getLong("ESTLOG_N2"), resultSet.getLong("ESTLOG_N3"),
			    			resultSet.getString("BLOQ_COM"), resultSet.getString("ESTADO"),
			    			resultSet.getDouble("KILOS_UNI"), resultSet.getString("PESO_VARI"),
			    			resultSet.getString("FORMATO"), resultSet.getDate("FECHA_GEN")
				    );
			 }
	    };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VDatosDiarioCap> findAll(VDatosDiarioCap vDatosDiarioCap) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_ART, DESCRIP_ART, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, " +
	    			 									" ESTLOG_N1, ESTLOG_N2, ESTLOG_N3, BLOQ_COM, ESTADO, KILOS_UNI, " +
	    			 									" PESO_VARI, FORMATO, FECHA_GEN " +	
	    										  " FROM V_DAT_DIARIO_CAP ");
	    
	    	
	        if (vDatosDiarioCap  != null){
	        	if(vDatosDiarioCap.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vDatosDiarioCap.getCodArt());	        		
	        	}
	        	if(vDatosDiarioCap.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(vDatosDiarioCap.getDescripArt());	        		
	        	}
	        	if(vDatosDiarioCap.getGrupo1()!=null){
	        		 where.append(" AND GRUPO1 = ? ");
		        	 params.add(vDatosDiarioCap.getGrupo1());	        		
	        	}
	        	if(vDatosDiarioCap.getGrupo2()!=null){
	        		 where.append(" AND GRUPO2 = ? ");
		        	 params.add(vDatosDiarioCap.getGrupo2());	        		
	        	}
	        	if(vDatosDiarioCap.getGrupo3()!=null){
	        		 where.append(" AND GRUPO3 = ? ");
		        	 params.add(vDatosDiarioCap.getGrupo3());	        		
	        	}
	        	if(vDatosDiarioCap.getGrupo4()!=null){
	        		 where.append(" AND GRUPO4 = ? ");
		        	 params.add(vDatosDiarioCap.getGrupo4());	        		
	        	}
	        	if(vDatosDiarioCap.getGrupo5()!=null){
	        		 where.append(" AND GRUPO5 = ? ");
		        	 params.add(vDatosDiarioCap.getGrupo5());	        		
	        	}
	        	if(vDatosDiarioCap.getEstlogN1()!=null){
	        		 where.append(" AND ESTLOG_N1 = ? ");
		        	 params.add(vDatosDiarioCap.getEstlogN1());	        		
	        	}
	        	if(vDatosDiarioCap.getEstlogN2()!=null){
	        		 where.append(" AND ESTLOG_N2 = ? ");
		        	 params.add(vDatosDiarioCap.getEstlogN2());	        		
	        	}
	        	if(vDatosDiarioCap.getEstlogN3()!=null){
	        		 where.append(" AND ESTLOG_N3 = ? ");
		        	 params.add(vDatosDiarioCap.getEstlogN3());	        		
	        	}
	        	if(vDatosDiarioCap.getBloqCom()!=null){
	        		 where.append(" AND UPPER(BLOQ_COM) = upper(?) ");
		        	 params.add(vDatosDiarioCap.getBloqCom());	        		
	        	}
	        	if(vDatosDiarioCap.getEstado()!=null){
	        		 where.append(" AND UPPER(ESTADO) = upper(?) ");
		        	 params.add(vDatosDiarioCap.getEstado());	        		
	        	}
	        	if(vDatosDiarioCap.getKilosUni()!=null){
	        		 where.append(" AND KILOS_UNI = ? ");
		        	 params.add(vDatosDiarioCap.getKilosUni());	        		
	        	}
	        	if(vDatosDiarioCap.getPesoVari()!=null){
	        		where.append(" AND UPPER(PESO_VARI) = upper(?) ");
		        	 params.add(vDatosDiarioCap.getPesoVari());	        		
	        	}
	        	if(vDatosDiarioCap.getFormato()!=null){
	        		where.append(" AND UPPER(FORMATO) = upper(?) ");
		        	 params.add(vDatosDiarioCap.getFormato());	        		
	        	}
	        	if(vDatosDiarioCap.getFechaGen()!=null){
	        		where.append(" AND FECHA_GEN = TRUNC(?) ");
	        		params.add(vDatosDiarioCap.getFechaGen());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_art ");
			query.append(order);

			List<VDatosDiarioCap> vDatosDiarioCapLista = null;		
			
			try {
				vDatosDiarioCapLista = (List<VDatosDiarioCap>) this.jdbcTemplate.query(query.toString(),this.rwDatosDiarioCapMap, params.toArray()); 

			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return vDatosDiarioCapLista;
	    }
}
