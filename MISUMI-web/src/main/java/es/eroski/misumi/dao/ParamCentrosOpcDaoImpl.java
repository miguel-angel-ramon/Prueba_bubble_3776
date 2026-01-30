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

import es.eroski.misumi.dao.iface.ParamCentrosOpcDao;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class ParamCentrosOpcDaoImpl implements ParamCentrosOpcDao{
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(ParamCentrosVpDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(ParamCentrosVpDaoImpl.class);

	 private RowMapper<ParamCentrosOpc> rwParamCentrosOpcMap = new RowMapper<ParamCentrosOpc>() {
			public ParamCentrosOpc mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new ParamCentrosOpc(resultSet.getLong("COD_LOC"),
			    			resultSet.getString("OPC_HABIL").replaceAll("\\s+","")
				);
			}
	 };
		    
	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<ParamCentrosOpc> findAll(ParamCentrosOpc paramCentrosOpc) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	StringBuffer query = new StringBuffer("SELECT cod_loc, UPPER(opc_habil) AS opc_habil " 
    										+ "FROM v_mis_param_centros_opc ");
    	where.append("WHERE 1=1 ");
    
        if (paramCentrosOpc != null){
        	if (paramCentrosOpc.getCodLoc()!=null){
        		 where.append(" AND cod_loc = ?");
	        	 params.add(paramCentrosOpc.getCodLoc());	        		
        	}
        	if (paramCentrosOpc.getOpcHabil()!=null){
        		 where.append(" AND UPPER(opc_habil) = UPPER(?) ");
	        	 params.add(paramCentrosOpc.getOpcHabil());	        		
        	}
        }
        
        query.append(where);
	 
	    List<ParamCentrosOpc> lista = null;
	    try {
			lista = (List<ParamCentrosOpc>) this.jdbcTemplate.query(query.toString(),this.rwParamCentrosOpcMap, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return lista;
    }
    
    @Override
    public Long findAllCont(ParamCentrosOpc paramCentrosOpc) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	StringBuffer query = new StringBuffer("SELECT COUNT(1) "
    										+ "FROM v_mis_param_centros_opc ");
    	where.append("WHERE 1=1 ");
    
        if (paramCentrosOpc  != null){
        	if (paramCentrosOpc.getCodLoc()!=null){
        		 where.append("AND cod_loc = ? ");
	        	 params.add(paramCentrosOpc.getCodLoc());	        		
        	}
        	if (paramCentrosOpc.getOpcHabil()!=null){
        		 where.append("AND UPPER(opc_habil) = UPPER(?) ");
	        	 params.add(paramCentrosOpc.getOpcHabil());	        		
        	}
        }
        
        query.append(where);
        
	    Long cont = null;
	    try {
	    	cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    return cont;
    }

	@Override
	public List<ParamCentrosOpc> findAllConcat(ParamCentrosOpc paramCentrosOpc) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer("SELECT cod_loc, TO_CHAR(RTRIM(xmlagg(xmlelement(e, opc_habil,',').extract('//text()')).GetClobVal(),',')) AS opc_habil "
    										+ "FROM v_mis_param_centros_opc "
    										);
    	where.append("WHERE 1=1 ");
    	
        if (paramCentrosOpc  != null){
        	if (paramCentrosOpc.getCodLoc()!=null){
        		 where.append("AND cod_loc = ? ");
	        	 params.add(paramCentrosOpc.getCodLoc());	        		
        	}
        	if (paramCentrosOpc.getOpcHabil()!=null){
        		 where.append("AND UPPER(opc_habil) = UPPER(?) ");
	        	 params.add(paramCentrosOpc.getOpcHabil());	        		
        	}
        }
        
        query.append(where);
        query.append("GROUP BY cod_loc");
	 
	    List<ParamCentrosOpc> lista = null;
	    try {
			lista = (List<ParamCentrosOpc>) this.jdbcTemplate.query(query.toString(),this.rwParamCentrosOpcMap, params.toArray());			
		} catch (Exception e){			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return lista;
	}
}
