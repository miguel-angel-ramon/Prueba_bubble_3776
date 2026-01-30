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

import es.eroski.misumi.dao.iface.VAlbaranesDao;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.VAgruComerRefPedidos;
import es.eroski.misumi.model.VAlbaran;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VAlbaranesDaoImpl implements VAlbaranesDao {
	private JdbcTemplate jdbcTemplate;

	private RowMapper<VAlbaran> rwVAlbaranMap = new RowMapper<VAlbaran>() {
		public VAlbaran mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VAlbaran(resultSet.getLong("cod_centro"),resultSet.getLong("num_albaran"),
		    		    resultSet.getLong("num_expedicion"),resultSet.getString("estado"),resultSet.getDate("fecha_previs_ent"),
		    		    resultSet.getDate("fecha_confirmado"),resultSet.getLong("grupo1"),resultSet.getLong("grupo2"),
		    		    resultSet.getLong("grupo3"),null,null
			    );
		}

	};
	
	private RowMapper<VAlbaran> rwVAlbaranPedidoMap = new RowMapper<VAlbaran>() {
		public VAlbaran mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VAlbaran(null,resultSet.getLong("num_albaran"),
		    		    resultSet.getLong("num_expedicion"),resultSet.getString("estado"),resultSet.getDate("fecha_previs_ent"),
		    		    null,null,null,null,resultSet.getString("fecha_confirmado_format"), resultSet.getDate("fecha_albaran")
			    );
		}

	};

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
	
	@Override
	public List<VAlbaran> findAll(VAlbaran vAlbaran, Pagination pagination) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");
    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO,NUM_ALBARAN, NUM_EXPEDICION, ESTADO, FECHA_PREVIS_ENT, FECHA_CONFIRMADO, " +
													"GRUPO1, GRUPO2, GRUPO3 " + 
											  " FROM V_ALBARAN ");
    
        if (vAlbaran  != null){
        	if(vAlbaran.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vAlbaran.getCodCentro());	        		
        	}
        	if(vAlbaran.getNumAlbaran()!=null){
       		 	 where.append(" AND NUM_ALBARAN = ? ");
	        	 params.add(vAlbaran.getNumAlbaran());	        		
        	}
        	if(vAlbaran.getNumExpedicion()!=null){
          		 where.append(" AND NUM_EXPEDICION = ? ");
   	        	 params.add(vAlbaran.getNumExpedicion());	        		
           	}
        	if(vAlbaran.getEstado()!=null){
         		 where.append(" AND ESTADO = ? ");
  	        	 params.add(vAlbaran.getEstado());	        		
          	}
        	if(vAlbaran.getFechaPrevisEnt()!=null){
	       		 where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
		         params.add(vAlbaran.getFechaPrevisEnt());	        		
	       	}
        	if(vAlbaran.getFechaConfirmado()!=null){
	       		 where.append(" AND TRUNC(FECHA_CONFIRMADO) = TRUNC(?) ");
		         params.add(vAlbaran.getFechaConfirmado());	        		
	       	}
        	if(vAlbaran.getGrupo1()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vAlbaran.getGrupo1());	        		
        	}
        	if(vAlbaran.getGrupo2()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vAlbaran.getGrupo2());	        		
	       	}
        	if(vAlbaran.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vAlbaran.getGrupo3());	        		
	       	}
        }
        
        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}
		}
		else{
			String campoOrdenacion = "NUM_ALBARAN";
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}
		

		List<VAlbaran> lista = null;
	    
	    try {
    		lista = this.jdbcTemplate.query(query.toString(),this.rwVAlbaranMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return lista;
	}

	@Override
	public Long findAllCont(VAlbaran vAlbaran) throws Exception {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + 
											  " FROM V_ALBARAN ");

    	if (vAlbaran  != null){
        	if(vAlbaran.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vAlbaran.getCodCentro());	        		
        	}
        	if(vAlbaran.getNumAlbaran()!=null){
       		 	 where.append(" AND NUM_ALBARAN = ? ");
	        	 params.add(vAlbaran.getNumAlbaran());	        		
        	}
        	if(vAlbaran.getNumExpedicion()!=null){
          		 where.append(" AND NUM_EXPEDICION = ? ");
   	        	 params.add(vAlbaran.getNumExpedicion());	        		
           	}
        	if(vAlbaran.getEstado()!=null){
         		 where.append(" AND ESTADO = ? ");
  	        	 params.add(vAlbaran.getEstado());	        		
          	}
        	if(vAlbaran.getFechaPrevisEnt()!=null){
	       		 where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
		         params.add(vAlbaran.getFechaPrevisEnt());	        		
	       	}
        	if(vAlbaran.getFechaConfirmado()!=null){
	       		 where.append(" AND TRUNC(FECHA_CONFIRMADO) = TRUNC(?) ");
		         params.add(vAlbaran.getFechaConfirmado());	        		
	       	}
        	if(vAlbaran.getGrupo1()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vAlbaran.getGrupo1());	        		
        	}
        	if(vAlbaran.getGrupo2()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vAlbaran.getGrupo2());	        		
	       	}
        	if(vAlbaran.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vAlbaran.getGrupo3());	        		
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
	public List<VAlbaran> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, List<Long> listaReferencias,  Pagination pagination) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DISTINCT NUM_ALBARAN, NUM_EXPEDICION, ESTADO, FECHA_ALBARAN, FECHA_PREVIS_ENT, " +
    											"TO_CHAR(FECHA_CONFIRMADO , 'DD/MM/YYYY HH24:MI:SS') AS FECHA_CONFIRMADO_FORMAT, FECHA_CONFIRMADO " +
    												" FROM V_ALBARAN ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if (seguimientoMiPedido.getCodArt() != null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(seguimientoMiPedido.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append(" AND COD_ART IN ( ").append(referencias).append(" )");	   
        	}
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND COD_MAPA = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
        	}
        }

        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}
		}
		else{
			String campoOrdenacion = "ESTADO,NUM_EXPEDICION";
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}


	    
	    List<VAlbaran> lista = null;
	    
	    try {
    		lista = this.jdbcTemplate.query(query.toString(),this.rwVAlbaranPedidoMap, params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return lista;
	}

	@Override
	public Long findSeguimientoMiPedidoCont(
			SeguimientoMiPedido seguimientoMiPedido) throws Exception {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + 
											  " FROM (SELECT DISTINCT NUM_ALBARAN, NUM_EXPEDICION, ESTADO, FECHA_PREVIS_ENT, FECHA_CONFIRMADO " +
    										  " FROM V_ALBARAN ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DDMMYYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if (seguimientoMiPedido.getCodArt() != null){
       		 where.append(" AND COD_ART = ? ");
		         params.add(seguimientoMiPedido.getCodArt());	   
        	}
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND COD_MAPA = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
        	}
        }

        query.append(where);
        query.append(" )");

	    Long cont = null;
	    
	    try {
    		cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return cont;
	}
	
    private String getMappedField (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("NUMEXPEDICION")){
	  	      return "NUM_EXPEDICION";
	  	  }else if(fieldName.toUpperCase().equals("NUMALBARAN")){
	  	      return "NUM_ALBARAN";
	  	  }else if(fieldName.toUpperCase().equals("FECHACONFIRMADO")){
	  	      return "FECHA_CONFIRMADO";
	  	  }else if(fieldName.toUpperCase().equals("FECHACONFIRMADOFORMAT")){
	  	      return "FECHA_CONFIRMADO";
	  	  }else if(fieldName.toUpperCase().equals("FECHAALBARAN")){
	  	      return "FECHA_ALBARAN";
	  	  } else {
	  	      return fieldName;
	  	  }
  }
}
