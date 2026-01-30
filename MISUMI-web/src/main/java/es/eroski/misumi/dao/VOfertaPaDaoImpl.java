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

import es.eroski.misumi.dao.iface.VOfertaPaDao;
import es.eroski.misumi.model.VOfertaPa;
import es.eroski.misumi.model.VOfertaPaAyuda;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VOfertaPaDaoImpl implements VOfertaPaDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VOfertaDaoImpl.class);
	 //private static Logger logger = Logger.getLogger(VOfertaDaoImpl.class);

	 private RowMapper<VOfertaPa> rwOfertaMap = new RowMapper<VOfertaPa>() {
			public VOfertaPa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VOfertaPa(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
			    			resultSet.getLong("TIPO_OFERTA"), resultSet.getDate("FECHA_INI"),
			    			resultSet.getDate("FECHA_FIN"), resultSet.getDate("FECHA_GEN"),
			    			resultSet.getString("DOFTIP"), resultSet.getString("TIPO_MENSAJE"),
			    			resultSet.getLong("PRECIO"),
			    			resultSet.getLong("AREA"),
			    			resultSet.getLong("SECCION"),
			    			resultSet.getLong("CATEGORIA"),
			    			resultSet.getLong("SUBCATEGORIA"),
			    			resultSet.getLong("SEGMENTO"),
			    			resultSet.getString("TIPO_REG_OFE")
				    );
			}

		    };
		    
		 private RowMapper<VOfertaPa> rwOfertaCentroMap = new RowMapper<VOfertaPa>() {
				public VOfertaPa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    return new VOfertaPa(resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA")
					    );
				}

			    };

		   
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
	    public List<VOfertaPa> findAllVigentes(VOfertaPa vOfertaPa) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, ANO_OFERTA, NUM_OFERTA, TIPO_OFERTA, FECHA_INI, FECHA_FIN, FECHA_GEN, " +
	    													" DOFTIP, TIPO_MENSAJE, PRECIO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, TIPO_REG_OFE " 
	    										+ " FROM V_OFERTA_PA ");
	    
	        if (vOfertaPa  != null){
	        	if(vOfertaPa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vOfertaPa.getCodCentro());	        		
	        	}
	        	if(vOfertaPa.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vOfertaPa.getCodArt());	        		
	        	}
	        	if(vOfertaPa.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getAnoOferta());	        		
	        	}
	        	if(vOfertaPa.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vOfertaPa.getNumOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoOferta()!=null){
	        		 where.append(" AND TIPO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getFechaIni()!=null){
	        		 where.append(" AND FECHA_INI = TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaIni());	        		
	        	}
	        	if(vOfertaPa.getFechaFin()!=null){
	        		 where.append(" AND FECHA_FIN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaFin());	        		
	        	}
	        	if(vOfertaPa.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaGen());	        		
	        	}
	        	if(vOfertaPa.getdTipoOferta()!=null){
	        		 where.append(" AND DOFTIP = upper(?) ");
		        	 params.add(vOfertaPa.getdTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoMensaje()!=null){
	        		 where.append(" AND TIPO_MENSAJE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoMensaje());	        		
	        	}
	        	if(vOfertaPa.getArea()!=null){
	        		 where.append(" AND AREA = ? ");
		        	 params.add(vOfertaPa.getArea());	        		
	        	}
	        	if(vOfertaPa.getSeccion()!=null){
	        		 where.append(" AND SECCION = ? ");
		        	 params.add(vOfertaPa.getSeccion());	        		
	        	}
	        	if(vOfertaPa.getCategoria()!=null){
	        		 where.append(" AND CATEGORIA = ? ");
		        	 params.add(vOfertaPa.getCategoria());	        		
	        	}
	        	if(vOfertaPa.getSubcategoria()!=null){
	        		 where.append(" AND SUBCATEGORIA = ? ");
		        	 params.add(vOfertaPa.getSubcategoria());	        		
	        	}
	        	if(vOfertaPa.getSegmento()!=null){
	        		 where.append(" AND SEGMENTO = ? ");
		        	 params.add(vOfertaPa.getSegmento());	        		
	        	}
	        	if(vOfertaPa.getTipoRegOfe()!=null){
	        		 where.append(" AND TIPO_REG_OFE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoRegOfe());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<VOfertaPa> vOfertaPaLista = null;		
			try {
				vOfertaPaLista = (List<VOfertaPa>) this.jdbcTemplate.query(query.toString(),this.rwOfertaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vOfertaPaLista;
	    }
	    
	    @Override
	    public List<VOfertaPa> findAllVigentesCentro(VOfertaPa vOfertaPa) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT DISTINCT ANO_OFERTA, NUM_OFERTA " 
	    										+ " FROM V_OFERTA_PA ");
	    
	        if (vOfertaPa  != null){
	        	if(vOfertaPa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vOfertaPa.getCodCentro());	        		
	        	}
	        	if(vOfertaPa.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vOfertaPa.getCodArt());	        		
	        	}
	        	if(vOfertaPa.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getAnoOferta());	        		
	        	}
	        	if(vOfertaPa.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vOfertaPa.getNumOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoOferta()!=null){
	        		 where.append(" AND TIPO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getFechaIni()!=null){
	        		 where.append(" AND FECHA_INI = TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaIni());	        		
	        	}
	        	if(vOfertaPa.getFechaFin()!=null){
	        		 where.append(" AND FECHA_FIN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaFin());	        		
	        	}
	        	if(vOfertaPa.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaGen());	        		
	        	}
	        	if(vOfertaPa.getdTipoOferta()!=null){
	        		 where.append(" AND DOFTIP = upper(?) ");
		        	 params.add(vOfertaPa.getdTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoMensaje()!=null){
	        		 where.append(" AND TIPO_MENSAJE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoMensaje());	        		
	        	}
	        	if(vOfertaPa.getArea()!=null){
	        		 where.append(" AND AREA = ? ");
		        	 params.add(vOfertaPa.getArea());	        		
	        	}
	        	if(vOfertaPa.getSeccion()!=null){
	        		 where.append(" AND SECCION = ? ");
		        	 params.add(vOfertaPa.getSeccion());	        		
	        	}
	        	if(vOfertaPa.getCategoria()!=null){
	        		 where.append(" AND CATEGORIA = ? ");
		        	 params.add(vOfertaPa.getCategoria());	        		
	        	}
	        	if(vOfertaPa.getSubcategoria()!=null){
	        		 where.append(" AND SUBCATEGORIA = ? ");
		        	 params.add(vOfertaPa.getSubcategoria());	        		
	        	}
	        	if(vOfertaPa.getSegmento()!=null){
	        		 where.append(" AND SEGMENTO = ? ");
		        	 params.add(vOfertaPa.getSegmento());	        		
	        	}
	        	if(vOfertaPa.getTipoRegOfe()!=null){
	        		 where.append(" AND TIPO_REG_OFE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoRegOfe());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by ano_oferta, num_oferta ");
			query.append(order);

			List<VOfertaPa> vOfertaPaLista = null;		
			
			try {
				vOfertaPaLista = (List<VOfertaPa>) this.jdbcTemplate.query(query.toString(),this.rwOfertaCentroMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vOfertaPaLista;
	    }
	    
	    @Override
	    public List<VOfertaPa> findCountNOVigentes(VOfertaPa vOfertaPa, int iRows) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, ANO_OFERTA, NUM_OFERTA, TIPO_OFERTA, FECHA_INI, FECHA_FIN, " +
	    												"FECHA_GEN, DOFTIP , TIPO_MENSAJE, PRECIO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, TIPO_REG_OFE " 
	    										+ " FROM V_OFERTA_PA ");
	    
	        if (vOfertaPa  != null){
	        	if(vOfertaPa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vOfertaPa.getCodCentro());	        		
	        	}
	        	if(vOfertaPa.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vOfertaPa.getCodArt());	        		
	        	}
	        	if(vOfertaPa.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getAnoOferta());	        		
	        	}
	        	if(vOfertaPa.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vOfertaPa.getNumOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoOferta()!=null){
	        		 where.append(" AND TIPO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getFechaIni()!=null){
	        		 where.append(" AND TRUNC(FECHA_INI) = TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaIni());	        		
	        	}
	        	//if(vOfertaPa.getFechaFin()!=null){
	        		 where.append(" AND FECHA_FIN < TRUNC(SYSDATE) ");
		       // 	 params.add(vOfertaPa.getFechaFin());	        		
	          //}
 	        	if(vOfertaPa.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaGen());	        		
	        	}
        		if(vOfertaPa.getdTipoOferta()!=null){
	        		 where.append(" AND UPPER(DOFTIP) = upper(?) ");
		        	 params.add(vOfertaPa.getdTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoMensaje()!=null){
	        		 where.append(" AND TIPO_MENSAJE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoMensaje());	        		
	        	}
	        	if(vOfertaPa.getArea()!=null){
	        		 where.append(" AND AREA = ? ");
		        	 params.add(vOfertaPa.getArea());	        		
	        	}
	        	if(vOfertaPa.getSeccion()!=null){
	        		 where.append(" AND SECCION = ? ");
		        	 params.add(vOfertaPa.getSeccion());	        		
	        	}
	        	if(vOfertaPa.getCategoria()!=null){
	        		 where.append(" AND CATEGORIA = ? ");
		        	 params.add(vOfertaPa.getCategoria());	        		
	        	}
	        	if(vOfertaPa.getSubcategoria()!=null){
	        		 where.append(" AND SUBCATEGORIA = ? ");
		        	 params.add(vOfertaPa.getSubcategoria());	        		
	        	}
	        	if(vOfertaPa.getSegmento()!=null){
	        		 where.append(" AND SEGMENTO = ? ");
		        	 params.add(vOfertaPa.getSegmento());	        		
	        	}
	        	if(vOfertaPa.getTipoRegOfe()!=null){
	        		 where.append(" AND TIPO_REG_OFE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoRegOfe());	        		
	        	}
	        	if (Integer.toString(iRows)!= null && iRows>0){
	        		 where.append(" AND ROWNUM < = " + iRows);        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);

			List<VOfertaPa> vOfertaPaLista = null;		
			
			try {
				vOfertaPaLista = (List<VOfertaPa>) this.jdbcTemplate.query(query.toString(),this.rwOfertaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
		    return vOfertaPaLista;
	    }
	    
		@Override
		public VOfertaPa findOneVigente(VOfertaPa vOfertaPa) throws Exception {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT * FROM ( " +
	    												"SELECT COD_CENTRO, COD_ART, ANO_OFERTA, NUM_OFERTA, TIPO_OFERTA, FECHA_INI, FECHA_FIN, FECHA_GEN, " +
	    												" DOFTIP, TIPO_MENSAJE, PRECIO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, SEGMENTO, TIPO_REG_OFE " + 
	    												" FROM V_OFERTA_PA ");
	    
	        if (vOfertaPa  != null){
	        	if(vOfertaPa.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(vOfertaPa.getCodCentro());	        		
	        	}
	        	if(vOfertaPa.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(vOfertaPa.getCodArt());	        		
	        	}
	        	if(vOfertaPa.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getAnoOferta());	        		
	        	}
	        	if(vOfertaPa.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(vOfertaPa.getNumOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoOferta()!=null){
	        		 where.append(" AND TIPO_OFERTA = ? ");
		        	 params.add(vOfertaPa.getTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getFechaIni()!=null){
	        		 where.append(" AND FECHA_INI = TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaIni());	        		
	        	}
	        	if(vOfertaPa.getFechaFin()!=null){
	        		 where.append(" AND FECHA_FIN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaFin());	        		
	        	}
	        	if(vOfertaPa.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN >= TRUNC(?) ");
		        	 params.add(vOfertaPa.getFechaGen());	        		
	        	}
	        	if(vOfertaPa.getdTipoOferta()!=null){
	        		 where.append(" AND DOFTIP = upper(?) ");
		        	 params.add(vOfertaPa.getdTipoOferta());	        		
	        	}
	        	if(vOfertaPa.getTipoMensaje()!=null){
	        		 where.append(" AND TIPO_MENSAJE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoMensaje());	        		
	        	}
	        	if(vOfertaPa.getArea()!=null){
	        		 where.append(" AND AREA = ? ");
		        	 params.add(vOfertaPa.getArea());	        		
	        	}
	        	if(vOfertaPa.getSeccion()!=null){
	        		 where.append(" AND SECCION = ? ");
		        	 params.add(vOfertaPa.getSeccion());	        		
	        	}
	        	if(vOfertaPa.getCategoria()!=null){
	        		 where.append(" AND CATEGORIA = ? ");
		        	 params.add(vOfertaPa.getCategoria());	        		
	        	}
	        	if(vOfertaPa.getSubcategoria()!=null){
	        		 where.append(" AND SUBCATEGORIA = ? ");
		        	 params.add(vOfertaPa.getSubcategoria());	        		
	        	}
	        	if(vOfertaPa.getSegmento()!=null){
	        		 where.append(" AND SEGMENTO = ? ");
		        	 params.add(vOfertaPa.getSegmento());	        		
	        	}
	        	if(vOfertaPa.getTipoRegOfe()!=null){
	        		 where.append(" AND TIPO_REG_OFE = upper(?) ");
		        	 params.add(vOfertaPa.getTipoRegOfe());	        		
	        	}
	        }
	        
	        query.append(where);

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" order by cod_centro, cod_art ");
			query.append(order);
			query.append(" ) WHERE ROWNUM = 1 ");

			List<VOfertaPa> vOfertaPaLista = null;		
			
			try {
				vOfertaPaLista = (List<VOfertaPa>) this.jdbcTemplate.query(query.toString(),this.rwOfertaMap, params.toArray()); 
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
			
			VOfertaPa vOfertaPaRes = null;
			if (!vOfertaPaLista.isEmpty()){
				vOfertaPaRes = vOfertaPaLista.get(0);
			}
		    return vOfertaPaRes;
		}
}
