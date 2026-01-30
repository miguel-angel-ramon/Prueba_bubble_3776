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

import es.eroski.misumi.dao.iface.VMapaAprovDao;
import es.eroski.misumi.model.VMapaAprov;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VMapaAprovDaoImpl implements VMapaAprovDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VSurtidoTiendaDaoImpl.class);
	 private RowMapper<VMapaAprov> rwVMapaAprovMap = new RowMapper<VMapaAprov>() {
			public VMapaAprov mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VMapaAprov(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("COD_PLAT"), resultSet.getLong("PED_LUN"),
			    			resultSet.getLong("PED_MAR"), resultSet.getLong("PED_MIE"),
			    			resultSet.getLong("PED_JUE"), resultSet.getLong("PED_VIE"),
			    			resultSet.getLong("PED_SAB"), resultSet.getLong("PED_DOM"),
			    			resultSet.getString("TIPO_DIA_L_N"), resultSet.getLong("CICLO_SEMANAL"),
			    			resultSet.getString("ESTADO"), resultSet.getDate("FECHA_GEN"),
			    			resultSet.getString("COD_N1"), resultSet.getString("COD_N2"),
			    			resultSet.getString("COD_N3"), resultSet.getString("HORA_TRANS")
				    );
			}

		};

		 private RowMapper<Long> rwMapaCountAprovMap = new RowMapper<Long>() {
				public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    return resultSet.getLong(1);
				}

		 };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VMapaAprov> findAll(VMapaAprov vMapaAprov) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_PLAT, PED_LUN, PED_MAR, PED_MIE, PED_JUE, PED_VIE, PED_SAB, PED_DOM, TIPO_DIA_L_N, CICLO_SEMANAL, ESTADO, FECHA_GEN, COD_N1, COD_N2, COD_N3, HORA_TRANS " 
	    										+ " FROM V_MAPA_APROV ");
	    
	    	
	        if (vMapaAprov  != null){
	        	if(vMapaAprov.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vMapaAprov.getCodCentro());	        		
	        	}
	        	if(vMapaAprov.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vMapaAprov.getCodArt());	        		
	        	}
	        	if(vMapaAprov.getCodPlat()!=null){
	        		 where.append(" AND COD_PLAT = ? ");
		        	 params.add(vMapaAprov.getCodPlat());	        		
	        	}
	        	if(vMapaAprov.getPedLun()!=null){
	        		 where.append(" AND PED_LUN = ? ");
		        	 params.add(vMapaAprov.getPedLun());	        		
	        	}
	        	if(vMapaAprov.getPedMar()!=null){
	        		 where.append(" AND PED_MAR = ? ");
		        	 params.add(vMapaAprov.getPedMar());	        		
	        	}
	        	if(vMapaAprov.getPedMie()!=null){
	        		 where.append(" AND PED_MIE = ? ");
		        	 params.add(vMapaAprov.getPedMie());	        		
	        	}
	        	if(vMapaAprov.getPedJue()!=null){
	        		 where.append(" AND PED_JUE = ? ");
		        	 params.add(vMapaAprov.getPedJue());	        		
	        	}
	        	if(vMapaAprov.getPedVie()!=null){
	        		 where.append(" AND PED_VIE = ? ");
		        	 params.add(vMapaAprov.getPedVie());	        		
	        	}
	        	if(vMapaAprov.getPedSab()!=null){
	        		 where.append(" AND PED_SAB = ? ");
		        	 params.add(vMapaAprov.getPedSab());	        		
	        	}
	        	if(vMapaAprov.getPedDom()!=null){
	        		 where.append(" AND PED_DOM = ? ");
		        	 params.add(vMapaAprov.getPedDom());	        		
	        	}
    			if(vMapaAprov.getTipoDiaLN()!=null){
	        		 where.append(" AND TIPO_DIA_L_N = upper(?) ");
		        	 params.add(vMapaAprov.getTipoDiaLN());	        		
	        	}
	        	if(vMapaAprov.getCicloSemanal()!=null){
	        		 where.append(" AND CICLO_SEMANAL = ? ");
		        	 params.add(vMapaAprov.getCicloSemanal());	        		
	        	}
	        	if(vMapaAprov.getEstado()!=null){
	        		 where.append(" AND ESTADO = upper(?) ");
		        	 params.add(vMapaAprov.getEstado());	        		
	        	}
	        	if(vMapaAprov.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(vMapaAprov.getFechaGen());	        		
	        	}
	        	if(vMapaAprov.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN1());	        		
	        	}
	        	if(vMapaAprov.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN2());	        		
	        	}
	        	if(vMapaAprov.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN3());	        		
	        	}
	        	if(vMapaAprov.getHoraTrans()!=null){
	        		 where.append(" AND HORA_TRANS = upper(?) ");
		        	 params.add(vMapaAprov.getHoraTrans());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<VMapaAprov> vMapaAprovLista = null;		
			
			try {
				vMapaAprovLista = (List<VMapaAprov>) this.jdbcTemplate.query(query.toString(),this.rwVMapaAprovMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}

		    return vMapaAprovLista;
	    }

	    @Override
	    public Long count(VMapaAprov vMapaAprov) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
	    										+ " FROM V_MAPA_APROV ");
	    
	    	
	        if (vMapaAprov  != null){
	        	if(vMapaAprov.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vMapaAprov.getCodCentro());	        		
	        	}
	        	if(vMapaAprov.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vMapaAprov.getCodArt());	        		
	        	}
	        	if(vMapaAprov.getCodPlat()!=null){
	        		 where.append(" AND COD_PLAT = ? ");
		        	 params.add(vMapaAprov.getCodPlat());	        		
	        	}
	        	if(vMapaAprov.getPedLun()!=null){
	        		 where.append(" AND PED_LUN = ? ");
		        	 params.add(vMapaAprov.getPedLun());	        		
	        	}
	        	if(vMapaAprov.getPedMar()!=null){
	        		 where.append(" AND PED_MAR = ? ");
		        	 params.add(vMapaAprov.getPedMar());	        		
	        	}
	        	if(vMapaAprov.getPedMie()!=null){
	        		 where.append(" AND PED_MIE = ? ");
		        	 params.add(vMapaAprov.getPedMie());	        		
	        	}
	        	if(vMapaAprov.getPedJue()!=null){
	        		 where.append(" AND PED_JUE = ? ");
		        	 params.add(vMapaAprov.getPedJue());	        		
	        	}
	        	if(vMapaAprov.getPedVie()!=null){
	        		 where.append(" AND PED_VIE = ? ");
		        	 params.add(vMapaAprov.getPedVie());	        		
	        	}
	        	if(vMapaAprov.getPedSab()!=null){
	        		 where.append(" AND PED_SAB = ? ");
		        	 params.add(vMapaAprov.getPedSab());	        		
	        	}
	        	if(vMapaAprov.getPedDom()!=null){
	        		 where.append(" AND PED_DOM = ? ");
		        	 params.add(vMapaAprov.getPedDom());	        		
	        	}
    			if(vMapaAprov.getTipoDiaLN()!=null){
	        		 where.append(" AND TIPO_DIA_L_N = upper(?) ");
		        	 params.add(vMapaAprov.getTipoDiaLN());	        		
	        	}
	        	if(vMapaAprov.getCicloSemanal()!=null){
	        		 where.append(" AND CICLO_SEMANAL = ? ");
		        	 params.add(vMapaAprov.getCicloSemanal());	        		
	        	}
	        	if(vMapaAprov.getEstado()!=null){
	        		 where.append(" AND ESTADO = upper(?) ");
		        	 params.add(vMapaAprov.getEstado());	        		
	        	}
	        	if(vMapaAprov.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
	        		 params.add(vMapaAprov.getFechaGen());
	        	}
	        	if(vMapaAprov.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN1());	        		
	        	}
	        	if(vMapaAprov.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN2());	        		
	        	}
	        	if(vMapaAprov.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = upper(?) ");
		        	 params.add(vMapaAprov.getCodN3());	        		
	        	}
	        	if(vMapaAprov.getHoraTrans()!=null){
	        		 where.append(" AND HORA_TRANS = upper(?) ");
		        	 params.add(vMapaAprov.getHoraTrans());	        		
	        	}
	        }
	        
	        query.append(where);

			List<Long> vMapaCountAprovLista = null;		
			
			try {
				vMapaCountAprovLista = (List<Long>) this.jdbcTemplate.query(query.toString(),this.rwMapaCountAprovMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}


		    return vMapaCountAprovLista.get(0);
	    }
}
