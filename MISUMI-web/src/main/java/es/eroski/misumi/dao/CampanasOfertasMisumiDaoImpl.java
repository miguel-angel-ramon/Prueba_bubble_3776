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

import es.eroski.misumi.dao.iface.CampanasOfertasMisumiDao;
import es.eroski.misumi.model.CampanasOfertasMisumi;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CampanasOfertasMisumiDaoImpl implements CampanasOfertasMisumiDao{
	 private JdbcTemplate jdbcTemplate;
	 private RowMapper<CampanasOfertasMisumi> rwCampanasOfertasMisumiMap = new RowMapper<CampanasOfertasMisumi>() {
			public CampanasOfertasMisumi mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new CampanasOfertasMisumi(resultSet.getString("IDENTIFICADOR"),resultSet.getLong("COD_LOC"), 
			    		resultSet.getLong("COD_ART"), resultSet.getLong("COD_ART_RL"), resultSet.getString("TIPO"),
			    		resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), resultSet.getDate("F_INICIO"),
			    		resultSet.getDate("F_FIN"), resultSet.getFloat("CANT_PREVISTA"), resultSet.getFloat("VENTA_MEDIA"),
			    		resultSet.getFloat("VENTA_TOTAL"), resultSet.getFloat("CANT_IMP"), resultSet.getDate("FECHA_IMP")
				    );
			}
		    };

	 private RowMapper<CampanasOfertasMisumi> rwCampanasMap = new RowMapper<CampanasOfertasMisumi>() {
			public CampanasOfertasMisumi mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new CampanasOfertasMisumi(resultSet.getString("IDENTIFICADOR"),null, 
			    		null, null, null, null, null, null,	null, null, null, null, null, null
				    );
			}
		    };

	 private RowMapper<CampanasOfertasMisumi> rwOfertasMap = new RowMapper<CampanasOfertasMisumi>() {
			public CampanasOfertasMisumi mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new CampanasOfertasMisumi(null, null, null, null, null,
			    		resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
			    		null, null, null, null, null, null, null
				    );
			}
		    };
		    
		@Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<CampanasOfertasMisumi> findAll(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT IDENTIFICADOR, COD_LOC, COD_ART, COD_ART_RL, TIPO, ANO_OFERTA, NUM_OFERTA, F_INICIO, F_FIN, CANT_PREVISTA, VENTA_MEDIA,	VENTA_TOTAL, CANT_IMP, FECHA_IMP " 
	    										+ " FROM CAMPANAS_OFERTAS_MISUMI ");
	    
	        if (campanasOfertasMisumi  != null){
	        	if(campanasOfertasMisumi.getIdentificador()!=null){
	        		 where.append(" AND UPPER(IDENTIFICADOR) = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getIdentificador());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(campanasOfertasMisumi.getCodLoc());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArt());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArtRl()!=null){
	        		 where.append(" AND COD_ART_RL = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArtRl());	        		
	        	}
	        	if(campanasOfertasMisumi.getTipo()!=null){
	        		 where.append(" AND TIPO = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getTipo());	        		
	        	}
	        	if(campanasOfertasMisumi.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getAnoOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getNumOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getfInicio()!=null){
	        		 where.append(" AND F_INICIO = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfInicio());	        		
	        	}
	        	if(campanasOfertasMisumi.getfFin()!=null){
	        		 where.append(" AND F_FIN = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfFin());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantPrevista()!=null){
	        		 where.append(" AND CANT_PREVISTA = ? ");
		        	 params.add(campanasOfertasMisumi.getCantPrevista());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaMedia()!=null){
	        		 where.append(" AND VENTA_MEDIA = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaMedia());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaTotal()!=null){
	        		 where.append(" AND VENTA_TOTAL = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaTotal());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantImp()!=null){
	        		 where.append(" AND CANT_IMP = ? ");
		        	 params.add(campanasOfertasMisumi.getCantImp());	        		
	        	}
	        	if(campanasOfertasMisumi.getFechaImp()!=null){
	        		 where.append(" AND FECHA_IMP = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getFechaImp());	        		
	        	}
	        }
	        
	        query.append(where);
			
			if(campanasOfertasMisumi.getTipo()!=null){
				StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
				if (Constantes.SEGUIMIENTO_CAMPANAS_OFERTAS_TIPO_OFERTA.equals(campanasOfertasMisumi.getTipo())){
				//Búsqueda por oferta
					order.append(" order by ANO_OFERTA asc, NUM_OFERTA asc ");
				}else{
					order.append(" order by IDENTIFICADOR asc ");
				}
				query.append(order);
			}

			
			List<CampanasOfertasMisumi> lista = null;
			try {
			    lista = (List<CampanasOfertasMisumi>) this.jdbcTemplate.query(query.toString(),this.rwCampanasOfertasMisumiMap, params.toArray());
			    
		    } catch (Exception e){
				
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
			
			return lista;
	    }
	    
	    @Override
	    public List<CampanasOfertasMisumi> findCampanas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT DISTINCT IDENTIFICADOR " 
	    										+ " FROM CAMPANAS_OFERTAS_MISUMI ");
	    
	        if (campanasOfertasMisumi  != null){
	        	if(campanasOfertasMisumi.getIdentificador()!=null){
	        		 where.append(" AND UPPER(IDENTIFICADOR) = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getIdentificador());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(campanasOfertasMisumi.getCodLoc());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArt());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArtRl()!=null){
	        		 where.append(" AND COD_ART_RL = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArtRl());	        		
	        	}
	        	if(campanasOfertasMisumi.getTipo()!=null){
	        		 where.append(" AND TIPO = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getTipo());	        		
	        	}
	        	if(campanasOfertasMisumi.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getAnoOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getNumOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getfInicio()!=null){
	        		 where.append(" AND F_INICIO = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfInicio());	        		
	        	}
	        	if(campanasOfertasMisumi.getfFin()!=null){
	        		 where.append(" AND F_FIN = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfFin());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantPrevista()!=null){
	        		 where.append(" AND CANT_PREVISTA = ? ");
		        	 params.add(campanasOfertasMisumi.getCantPrevista());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaMedia()!=null){
	        		 where.append(" AND VENTA_MEDIA = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaMedia());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaTotal()!=null){
	        		 where.append(" AND VENTA_TOTAL = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaTotal());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantImp()!=null){
	        		 where.append(" AND CANT_IMP = ? ");
		        	 params.add(campanasOfertasMisumi.getCantImp());	        		
	        	}
	        	if(campanasOfertasMisumi.getFechaImp()!=null){
	        		 where.append(" AND FECHA_IMP = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getFechaImp());	        		
	        	}
	        }
	        
	        query.append(where);
			
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by IDENTIFICADOR asc ");
			query.append(order);

			List<CampanasOfertasMisumi> lista = null;
			try {
				lista =  (List<CampanasOfertasMisumi>) this.jdbcTemplate.query(query.toString(),this.rwCampanasMap, params.toArray());
		    
		    } catch (Exception e){
				
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
			
				return lista;
		    }
	    
	    @Override
	    public List<CampanasOfertasMisumi> findOfertas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	
	    	StringBuffer query = new StringBuffer(" SELECT DISTINCT ANO_OFERTA, NUM_OFERTA " 
	    										+ " FROM CAMPANAS_OFERTAS_MISUMI ");
	    
	        if (campanasOfertasMisumi  != null){
	        	if(campanasOfertasMisumi.getIdentificador()!=null){
	        		 where.append(" AND UPPER(IDENTIFICADOR) = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getIdentificador());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodLoc()!=null){
	        		 where.append(" AND COD_LOC = ? ");
		        	 params.add(campanasOfertasMisumi.getCodLoc());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArt());	        		
	        	}
	        	if(campanasOfertasMisumi.getCodArtRl()!=null){
	        		 where.append(" AND COD_ART_RL = ? ");
		        	 params.add(campanasOfertasMisumi.getCodArtRl());	        		
	        	}
	        	if(campanasOfertasMisumi.getTipo()!=null){
	        		 where.append(" AND TIPO = UPPER(?) ");
		        	 params.add(campanasOfertasMisumi.getTipo());	        		
	        	}
	        	if(campanasOfertasMisumi.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getAnoOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(campanasOfertasMisumi.getNumOferta());	        		
	        	}
	        	if(campanasOfertasMisumi.getfInicio()!=null){
	        		 where.append(" AND F_INICIO = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfInicio());	        		
	        	}
	        	if(campanasOfertasMisumi.getfFin()!=null){
	        		 where.append(" AND F_FIN = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getfFin());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantPrevista()!=null){
	        		 where.append(" AND CANT_PREVISTA = ? ");
		        	 params.add(campanasOfertasMisumi.getCantPrevista());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaMedia()!=null){
	        		 where.append(" AND VENTA_MEDIA = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaMedia());	        		
	        	}
	        	if(campanasOfertasMisumi.getVentaTotal()!=null){
	        		 where.append(" AND VENTA_TOTAL = ? ");
		        	 params.add(campanasOfertasMisumi.getVentaTotal());	        		
	        	}
	        	if(campanasOfertasMisumi.getCantImp()!=null){
	        		 where.append(" AND CANT_IMP = ? ");
		        	 params.add(campanasOfertasMisumi.getCantImp());	        		
	        	}
	        	if(campanasOfertasMisumi.getFechaImp()!=null){
	        		 where.append(" AND FECHA_IMP = TRUNC(?) ");
		        	 params.add(campanasOfertasMisumi.getFechaImp());	        		
	        	}
	        }
	        
	        query.append(where);
			
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by ANO_OFERTA asc, NUM_OFERTA asc ");
			query.append(order);
			
			List<CampanasOfertasMisumi> lista = null;
			try {
			    lista = (List<CampanasOfertasMisumi>) this.jdbcTemplate.query(query.toString(),this.rwOfertasMap, params.toArray());
			    
		    } catch (Exception e){
				
	    		Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
	    	}
		
			return lista;
	    }	    
}