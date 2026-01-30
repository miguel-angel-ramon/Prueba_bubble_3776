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

import es.eroski.misumi.dao.iface.VOfertaPaAyudaDao;
import es.eroski.misumi.model.VOferta;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VOfertaPaAyudaDaoImpl implements VOfertaPaAyudaDao{
	
	private JdbcTemplate jdbcTemplate;
	//private static Logger logger = LoggerFactory.getLogger(VOfertaPaAyudaDaoImpl.class);
	//private static Logger logger = Logger.getLogger(VOfertaPaAyudaDaoImpl.class);

	private RowMapper<VOfertaPaAyuda> rwOfertaPaAyudaMap = new RowMapper<VOfertaPaAyuda>() {
		public VOfertaPaAyuda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VOfertaPaAyuda(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
		    			resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
		    			resultSet.getLong("TIPO_OFERTA"), resultSet.getDate("FECHA_INI"),
		    			resultSet.getDate("FECHA_FIN"), null, resultSet.getString("DOFTIP"),
		    			null, resultSet.getDouble("PRECIO"), null, null, null, null, null,
		    			resultSet.getLong("UNID_COBRO"), resultSet.getLong("UNID_VENTA")
		    			);
		}
	
	};
		  
	 private RowMapper<VOfertaPaAyuda> rwOfertaCentroMap = new RowMapper<VOfertaPaAyuda>() {
			public VOfertaPaAyuda mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VOfertaPaAyuda(resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"));
			}
	 };
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	    
    @Override
    public List<VOfertaPaAyuda> findCountNOVigentes(VOfertaPaAyuda vOfertaPaAyuda, int iRows) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
		
    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, ANO_OFERTA, NUM_OFERTA, TIPO_OFERTA, FECHA_INI, FECHA_FIN, " +
    										  "FECHA_GEN, DOFTIP, TIPO_MENSAJE, PRECIO, AREA, SECCION, CATEGORIA, SEGMENTO, TIPO_REG_OFE," +
    										  "UNID_COBRO, UNID_VENTA" +
    										  " FROM V_OFERTA_PA_AYUDA ");
    
        if (vOfertaPaAyuda  != null){
        	if(vOfertaPaAyuda.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vOfertaPaAyuda.getCodCentro());	        		
        	}
        	if(vOfertaPaAyuda.getCodArt()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(vOfertaPaAyuda.getCodArt());	        		
        	}
        	if(vOfertaPaAyuda.getAnoOferta()!=null){
        		 where.append(" AND ANO_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getAnoOferta());	        		
        	}
        	if(vOfertaPaAyuda.getNumOferta()!=null){
        		 where.append(" AND NUM_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getNumOferta());	        		
        	}
        	if(vOfertaPaAyuda.getTipoOferta()!=null){
        		 where.append(" AND TIPO_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getTipoOferta());	        		
        	}
        	if(vOfertaPaAyuda.getFechaIni()!=null){
        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
	        	 params.add(vOfertaPaAyuda.getFechaIni());	        		
        	}
        	//if(vOfertaPa.getFechaFin()!=null){
        		 where.append(" AND FECHA_FIN < TRUNC(SYSDATE) ");
            // 	 params.add(vOfertaPa.getFechaFin());	        		
            //}
        	if(vOfertaPaAyuda.getFechaGen()!=null){
        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
	        	 params.add(vOfertaPaAyuda.getFechaIni());	        		
        	}
       		if(vOfertaPaAyuda.getDoftip()!=null){
        		 where.append(" AND UPPER(DOFTIP) = upper(?) ");
	        	 params.add(vOfertaPaAyuda.getDoftip());	        		
        	}
        	if (Integer.toString(iRows)!= null && iRows>0){
        		 where.append(" AND ROWNUM < = " + iRows);        		
        	}
        }
        
        query.append(where);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, cod_art ");
		query.append(order);

		List<VOfertaPaAyuda> vOfertaPaAyudaLista = null;		
		try {
			vOfertaPaAyudaLista = (List<VOfertaPaAyuda>) this.jdbcTemplate.query(query.toString(),this.rwOfertaPaAyudaMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return vOfertaPaAyudaLista;
    }
    
    @Override
    public List<VOfertaPaAyuda> findAllNoVigentesCentro(VOfertaPaAyuda vOfertaPaAyuda) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DISTINCT FECHA_FIN, ANO_OFERTA, NUM_OFERTA " 
    										+ " FROM V_OFERTA_PA_AYUDA ");
        
        if (vOfertaPaAyuda  != null){
        	if(vOfertaPaAyuda.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vOfertaPaAyuda.getCodCentro());	        		
        	}
        	if(vOfertaPaAyuda.getCodArt()!=null){
        		 where.append(" AND COD_ART = ? ");
	        	 params.add(vOfertaPaAyuda.getCodArt());	        		
        	}
        	if(vOfertaPaAyuda.getAnoOferta()!=null){
        		 where.append(" AND ANO_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getAnoOferta());	        		
        	}
        	if(vOfertaPaAyuda.getNumOferta()!=null){
        		 where.append(" AND NUM_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getNumOferta());	        		
        	}
        	if(vOfertaPaAyuda.getTipoOferta()!=null){
        		 where.append(" AND TIPO_OFERTA = ? ");
	        	 params.add(vOfertaPaAyuda.getTipoOferta());	        		
        	}
        	if(vOfertaPaAyuda.getFechaIni()!=null){
        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
	        	 params.add(vOfertaPaAyuda.getFechaIni());	        		
        	}
        	//if(vOfertaPa.getFechaFin()!=null){
        		 where.append(" AND FECHA_FIN < TRUNC(SYSDATE) ");
            // 	 params.add(vOfertaPa.getFechaFin());	        		
            //}
        	if(vOfertaPaAyuda.getFechaGen()!=null){
        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
	        	 params.add(vOfertaPaAyuda.getFechaIni());	        		
        	}
       		if(vOfertaPaAyuda.getDoftip()!=null){
        		 where.append(" AND UPPER(DOFTIP) = upper(?) ");
	        	 params.add(vOfertaPaAyuda.getDoftip());	        		
        	}
        }

        query.append(where);

        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by FECHA_FIN DESC");
		query.append(order);

		List<VOfertaPaAyuda> vOfertaPaAyudaLista = null;		
		try {
			vOfertaPaAyudaLista = (List<VOfertaPaAyuda>) this.jdbcTemplate.query(query.toString(),this.rwOfertaCentroMap, params.toArray()); 
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
		}
	    return vOfertaPaAyudaLista;
    }   
}
