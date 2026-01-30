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

import es.eroski.misumi.dao.iface.VMapaAprovFestivoDao;
import es.eroski.misumi.model.VMapaAprovFestivo;
import es.eroski.misumi.util.Constantes;
 	
@Repository
public class VMapaAprovFestivoDaoImpl implements VMapaAprovFestivoDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VMapaAprovFestivoDaoImpl.class);
	 private RowMapper<VMapaAprovFestivo> rwMapaAprovFestivoMap = new RowMapper<VMapaAprovFestivo>() {
			public VMapaAprovFestivo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VMapaAprovFestivo(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("COD_PLAT"), resultSet.getString("TIPO_DIA_L_N"), 
			    			resultSet.getString("ESTADO"), resultSet.getDate("FECHA_GEN"),
			    			resultSet.getString("COD_N1"), resultSet.getString("COD_N2"), 
			    			resultSet.getString("COD_N3"),resultSet.getString("HORA_TRANS"), 
			    			resultSet.getDate("FECHA_CAMBIO")
				    );
			}

	 };
	
	 private RowMapper<Long> rwMapaCountAprovFestivoMap = new RowMapper<Long>() {
			public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return resultSet.getLong(1);
			}

	 };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VMapaAprovFestivo> findAll(VMapaAprovFestivo vMapaAprovFestivo) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_PLAT, TIPO_DIA_L_N, ESTADO, FECHA_GEN, COD_N1, COD_N2, COD_N3, HORA_TRANS, FEC_CAMBIO " 
	    										+ " FROM V_MAPA_APROV_FESTIVO ");
	    
	    	
	        if (vMapaAprovFestivo  != null){
	        	if(vMapaAprovFestivo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vMapaAprovFestivo.getCodCentro());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vMapaAprovFestivo.getCodArt());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodPlat()!=null){
	        		 where.append(" AND COD_PLAT = ? ");
		        	 params.add(vMapaAprovFestivo.getCodPlat());	        		
	        	}
	        	if(vMapaAprovFestivo.getTipoDiaLN()!=null){
	        		 where.append(" AND TIPO_DIA_L_N = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getTipoDiaLN());	        		
	        	}
	        	if(vMapaAprovFestivo.getEstado()!=null){
	        		 where.append(" AND ESTADO = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getEstado());	        		
	        	}
	        	if(vMapaAprovFestivo.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vMapaAprovFestivo.getFechaGen());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN1());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN2());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN3());	        		
	        	}
	        	if(vMapaAprovFestivo.getHoraTrans()!=null){
	        		 where.append(" AND HORA_TRANS = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getHoraTrans());	        		
	        	}
	        	if(vMapaAprovFestivo.getFechaCambio()!=null){
	        		 where.append(" AND TRUNC(FEC_CAMBIO) = TRUNC(?) ");
		        	 params.add(vMapaAprovFestivo.getFechaCambio());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<VMapaAprovFestivo> vMapaAprovFestivoLista = null;		
			vMapaAprovFestivoLista = (List<VMapaAprovFestivo>) this.jdbcTemplate.query(query.toString(),this.rwMapaAprovFestivoMap, params.toArray()); 

		    return vMapaAprovFestivoLista;
	    }

	    @Override
	    public Long count(VMapaAprovFestivo vMapaAprovFestivo) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
	    										+ " FROM V_MAPA_APROV_FESTIVO ");
	    
	        if (vMapaAprovFestivo  != null){
	        	if(vMapaAprovFestivo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vMapaAprovFestivo.getCodCentro());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vMapaAprovFestivo.getCodArt());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodPlat()!=null){
	        		 where.append(" AND COD_PLAT = ? ");
		        	 params.add(vMapaAprovFestivo.getCodPlat());	        		
	        	}
	        	if(vMapaAprovFestivo.getTipoDiaLN()!=null){
	        		 where.append(" AND TIPO_DIA_L_N = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getTipoDiaLN());	        		
	        	}
	        	if(vMapaAprovFestivo.getEstado()!=null){
	        		 where.append(" AND ESTADO = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getEstado());	        		
	        	}
	        	if(vMapaAprovFestivo.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vMapaAprovFestivo.getFechaGen());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN1());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN2());	        		
	        	}
	        	if(vMapaAprovFestivo.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getCodN3());	        		
	        	}
	        	if(vMapaAprovFestivo.getHoraTrans()!=null){
	        		 where.append(" AND HORA_TRANS = upper(?) ");
		        	 params.add(vMapaAprovFestivo.getHoraTrans());	        		
	        	}
	        	if(vMapaAprovFestivo.getFechaCambio()!=null){
	        		 where.append(" AND TRUNC(FEC_CAMBIO) = TRUNC(?) ");
		        	 params.add(vMapaAprovFestivo.getFechaCambio());	        		
	        	}
	        }
	        
	        query.append(where);

			List<Long> vMapaCountAprovFestivoLista = null;		
			vMapaCountAprovFestivoLista = (List<Long>) this.jdbcTemplate.query(query.toString(),this.rwMapaCountAprovFestivoMap, params.toArray()); 

		    return vMapaCountAprovFestivoLista.get(0);
	    }
}
