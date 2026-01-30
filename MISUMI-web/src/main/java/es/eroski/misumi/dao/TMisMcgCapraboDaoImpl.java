package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.TMisMcgCapraboDao;
import es.eroski.misumi.model.TMisMcgCaprabo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class TMisMcgCapraboDaoImpl implements TMisMcgCapraboDao{
	
	 private JdbcTemplate jdbcTemplate;



	 private RowMapper<TMisMcgCaprabo> rwMisMcgCapraboMap = new RowMapper<TMisMcgCaprabo>() {
			public TMisMcgCaprabo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new TMisMcgCaprabo(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART_CAPRABO"),resultSet.getLong("EAN"),
			    			resultSet.getString("TIPO_MOV"), resultSet.getString("DESCRIP_ART"),resultSet.getString("DESCRIP_ART_FIND"),
			    			resultSet.getLong("SUSTITUIDAPOR"), resultSet.getLong("SUSTITUTADE"),resultSet.getString("IMPLANTACION"),
			    			resultSet.getDate("FECHA_ACTIVACION"), resultSet.getString("MOTIVO"),resultSet.getDouble("STOCK"),resultSet.getDouble("STOCK_DIAS"),
			    			resultSet.getDate("FECHA_GEN"), resultSet.getString("NOMBRE_FICHERO"),resultSet.getDate("CREATION_DATE"),
			    			resultSet.getDate("LAST_UPDATE_DATE"),resultSet.getString("MSGID")
				    );
			}

		    };
		    

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<TMisMcgCaprabo> findAll(TMisMcgCaprabo tMisMcgCaprabo) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");


	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART_CAPRABO, EAN, TIPO_MOV, DESCRIP_ART, DESCRIP_ART_FIND, "
	    										+ "	SUSTITUIDAPOR, SUSTITUTADE, IMPLANTACION, FECHA_ACTIVACION, MOTIVO, STOCK, STOCK_DIAS, " 
	    										+ "	FECHA_GEN, NOMBRE_FICHERO, CREATION_DATE, LAST_UPDATE_DATE, MSGID " 
	    										+ " FROM T_MIS_MCG_CAPRABO ");
	    
	    	
	        if (tMisMcgCaprabo  != null){
	        	
	        	if(tMisMcgCaprabo.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(tMisMcgCaprabo.getCodCentro());	        		
	        	}
	        	if(tMisMcgCaprabo.getCodArtCaprabo()!=null){
	        		 where.append(" AND COD_ART_CAPRABO = ? ");
		        	 params.add(tMisMcgCaprabo.getCodArtCaprabo());	        		
	        	}
	        	if(tMisMcgCaprabo.getEan()!=null){
	        		 where.append(" AND EAN = ? ");
		        	 params.add(tMisMcgCaprabo.getEan());	        		
	        	}
	        	if(tMisMcgCaprabo.getTipoMov()!=null){
	        		 where.append(" AND TIPO_MOV = ? ");
		        	 params.add(tMisMcgCaprabo.getTipoMov());	        		
	        	}
	        	if(tMisMcgCaprabo.getDescripArt()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
		        	 params.add(tMisMcgCaprabo.getDescripArt());	        		
	        	}
	        	if(tMisMcgCaprabo.getDescripArtFind()!=null){
	        		 where.append(" AND UPPER(DESCRIP_ART_FIND) = upper(?) ");
		        	 params.add(tMisMcgCaprabo.getDescripArtFind());	        		
	        	}
	        	
	        	if(tMisMcgCaprabo.getSustituidaPor()!=null){
	        		 where.append(" AND SUSTITUIDAPOR = ? ");
		        	 params.add(tMisMcgCaprabo.getSustituidaPor());	        		
	        	}
	        	if(tMisMcgCaprabo.getSustitutaDe()!=null){
	        		 where.append(" AND SUSTITUTADE = ? ");
		        	 params.add(tMisMcgCaprabo.getSustitutaDe());	        		
	        	}
	        	if(tMisMcgCaprabo.getImplantacion()!=null){
	        		 where.append(" AND IMPLANTACION = ? ");
		        	 params.add(tMisMcgCaprabo.getImplantacion());	        		
	        	}
	        	if(tMisMcgCaprabo.getFechaActivacion()!=null){
		       		 where.append(" AND TRUNC(FECHA_ACTIVACION) = TRUNC(?) ");
			         params.add(tMisMcgCaprabo.getFechaActivacion());	        		
		       	}
	        	if(tMisMcgCaprabo.getMotivo()!=null){
	        		 where.append(" AND MOTIVO = ? ");
		        	 params.add(tMisMcgCaprabo.getMotivo());	        		
	        	}
	        	if(tMisMcgCaprabo.getStock()!=null){
	        		 where.append(" AND STOCK = ? ");
		        	 params.add(tMisMcgCaprabo.getStock());	        		
	        	}
	        	if(tMisMcgCaprabo.getStockDias()!=null){
	        		 where.append(" AND STOCK_DIAS = ? ");
		        	 params.add(tMisMcgCaprabo.getStockDias());	        		
	        	}
	        	if(tMisMcgCaprabo.getFechaGen()!=null){
		       		 where.append(" AND FECHA_GEN = TRUNC(?) ");
			         params.add(tMisMcgCaprabo.getFechaGen());	        		
		       	}
	        	if(tMisMcgCaprabo.getNombreFichero()!=null){
	        		 where.append(" AND UPPER(NOMBRE_FICHERO) = upper(?) ");
		        	 params.add(tMisMcgCaprabo.getNombreFichero());	        		
	        	}
	        	if(tMisMcgCaprabo.getCreationDate()!=null){
		       		 where.append(" AND TRUNC(CREATION_DATE) = TRUNC(?) ");
			         params.add(tMisMcgCaprabo.getCreationDate());	        		
		       	}
	        	if(tMisMcgCaprabo.getLastUpdateDate()!=null){
		       		 where.append(" AND TRUNC(LAST_UPDATE_DATE) = TRUNC(?) ");
			         params.add(tMisMcgCaprabo.getLastUpdateDate());	        		
		       	}
	        	if(tMisMcgCaprabo.getMsgid()!=null){
	        		 where.append(" AND MSGID = ? ");
		        	 params.add(tMisMcgCaprabo.getMsgid());	        		
	        	}
	        	

	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by COD_CENTRO, COD_ART_CAPRABO ");
			query.append(order);

			List<TMisMcgCaprabo> tMisMcgCapraboLista = null;		
			
			try {
				tMisMcgCapraboLista = (List<TMisMcgCaprabo>) this.jdbcTemplate.query(query.toString(),this.rwMisMcgCapraboMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return tMisMcgCapraboLista;
	    }
	    
	    
}
