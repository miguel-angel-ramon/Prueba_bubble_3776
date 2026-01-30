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

import es.eroski.misumi.dao.iface.VarAprpvDao;
import es.eroski.misumi.model.VarAprpv;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VarAprpvDaoImpl implements VarAprpvDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VarAprpvDaoImpl.class);
	 private RowMapper<VarAprpv> rwVarAprpvMap = new RowMapper<VarAprpv>() {
			public VarAprpv mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VarAprpv(resultSet.getLong("COD_LOC"),resultSet.getLong("COD_ARTICULO"),
			    			resultSet.getString("MARCA_REAPRO"), resultSet.getDate("FECHA_INICIO_REAPRO"),
			    			resultSet.getDate("FECHA_FIN_REAPRO"), resultSet.getString("GAMA_DISCONTINUA"),
			    			resultSet.getLong("GAMA_DISC_LUNES"), resultSet.getLong("GAMA_DISC_MARTES"),
			    			resultSet.getLong("GAMA_DISC_MIERCOLES"), resultSet.getLong("GAMA_DISC_JUEVES"),
			    			resultSet.getLong("GAMA_DISC_VIERNES"), resultSet.getLong("GAMA_DISC_SABADO"),
			    			resultSet.getLong("GAMA_DISC_DOMINGO"), resultSet.getFloat("UFP")
				    );
			}

		};

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VarAprpv> findAll(VarAprpv varAprpv) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_LOC, COD_ARTICULO, MARCA_REAPRO, FECHA_INICIO_REAPRO, FECHA_FIN_REAPRO, GAMA_DISCONTINUA, GAMA_DISC_LUNES, GAMA_DISC_MARTES, GAMA_DISC_MIERCOLES, GAMA_DISC_JUEVES, GAMA_DISC_VIERNES, GAMA_DISC_SABADO, GAMA_DISC_DOMINGO, UFP " 
	    										+ " FROM VAR_APRPV ");
	    
	    	
	        if (varAprpv  != null){
	        	if(varAprpv.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(varAprpv.getCodLoc());	        		
	        	}
	        	if(varAprpv.getCodArticulo()!=null){
	        		 where.append(" AND COD_ARTICULO = ? ");
		        	 params.add(varAprpv.getCodArticulo());	        		
	        	}
    			if(varAprpv.getMarcaReapro()!=null){
	        		 where.append(" AND UPPER(MARCA_REAPRO) = upper(?) ");
		        	 params.add(varAprpv.getMarcaReapro());	        		
	        	}
	        	if(varAprpv.getFechaInicioReapro()!=null){
	        		 where.append(" AND TRUNC(FECHA_INICIO_REAPRO) = TRUNC(?) ");
		        	 params.add(varAprpv.getFechaInicioReapro());	        		
	        	}
	        	if(varAprpv.getFechaFinReapro()!=null){
	        		 where.append(" AND TRUNC(FECHA_FIN_REAPRO) = TRUNC(?) ");
		        	 params.add(varAprpv.getFechaFinReapro());	        		
	        	}
	        	if(varAprpv.getGamaDiscontinua()!=null){
	        		 where.append(" AND UPPER(GAMA_DISCONTINUA) = upper(?) ");
		        	 params.add(varAprpv.getGamaDiscontinua());	        		
	        	}
	        	if(varAprpv.getGamaDiscLunes()!=null){
	        		 where.append(" AND GAMA_DISC_LUNES = ? ");
		        	 params.add(varAprpv.getGamaDiscLunes());	        		
	        	}
	        	if(varAprpv.getGamaDiscMartes()!=null){
	        		 where.append(" AND GAMA_DISC_MARTES = ? ");
		        	 params.add(varAprpv.getGamaDiscMartes());	        		
	        	}
	        	if(varAprpv.getGamaDiscMiercoles()!=null){
	        		 where.append(" AND GAMA_DISC_MIERCOLES = ? ");
		        	 params.add(varAprpv.getGamaDiscMiercoles());	        		
	        	}
	        	if(varAprpv.getGamaDiscJueves()!=null){
	        		 where.append(" AND GAMA_DISC_JUEVES = ? ");
		        	 params.add(varAprpv.getGamaDiscJueves());	        		
	        	}
	        	if(varAprpv.getGamaDiscViernes()!=null){
	        		 where.append(" AND GAMA_DISC_VIERNES = ? ");
		        	 params.add(varAprpv.getGamaDiscViernes());	        		
	        	}
	        	if(varAprpv.getGamaDiscSabado()!=null){
	        		 where.append(" AND GAMA_DISC_SABADO = ? ");
		        	 params.add(varAprpv.getGamaDiscSabado());	        		
	        	}
	        	if(varAprpv.getGamaDiscDomingo()!=null){
	        		 where.append(" AND GAMA_DISC_DOMINGO = ? ");
		        	 params.add(varAprpv.getGamaDiscDomingo());	        		
	        	}
	        	if(varAprpv.getUfp()!=null){
	        		 where.append(" AND UFP = ? ");
		        	 params.add(varAprpv.getUfp());	        		
	        	}
	        }
	        
	        query.append(where);

	        /*StringBuffer order = new StringBuffer(3000);
			order.append(" order by XXXXX ");
			query.append(order);*/

			List<VarAprpv> varAprpvLista = null;		

		    try {
		    	varAprpvLista = (List<VarAprpv>) this.jdbcTemplate.query(query.toString(),this.rwVarAprpvMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return varAprpvLista;
	    }
	   
}
