package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.EncargosClientePlataformaDao;
import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class EncargosClientePlataformaDaoImpl implements EncargosClientePlataformaDao {

	private JdbcTemplate jdbcTemplate;
	
	 private RowMapper<EncargosClientePlataforma> rwEncargoClienteMap = new RowMapper<EncargosClientePlataforma>() {
			public EncargosClientePlataforma mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				EncargosClientePlataforma encargosCliente = new EncargosClientePlataforma();
				encargosCliente.setCodCentro(resultSet.getLong("CENTRO"));
				encargosCliente.setArea(resultSet.getString("AREA"));
				encargosCliente.setSeccion(resultSet.getString("SECCION"));
				encargosCliente.setCategoria(resultSet.getString("CATEGORIA"));
				encargosCliente.setSubcategoria(resultSet.getString("SUBCATEGORIA"));
				encargosCliente.setSegmento(resultSet.getString("SEGMENTO"));
				encargosCliente.setCodReferencia(resultSet.getLong("REFERENCIA"));
				encargosCliente.setDescripcionArt(resultSet.getString("DESCRIPCION"));
				encargosCliente.setUnidadesCaja(resultSet.getDouble("UNIDADESCAJA"));
				encargosCliente.setIdSession(resultSet.getString("IDDSESION"));
				encargosCliente.setVitrina(resultSet.getString("VITRINA"));
				encargosCliente.setFlgEspec(resultSet.getString("FLG_PER_ESPEC"));
			    return encargosCliente;
			}

		    };

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<String> getSecciones(
			EncargosClientePlataforma encargosCliente) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DISTINCT SECCION FROM T_ENCARGOS_CLTE ");
    
    	
        if (encargosCliente  != null){
        	if(encargosCliente.getIdSession()!=null){
        		 where.append(" AND IDDSESION = ? ");
	        	 params.add(encargosCliente.getIdSession());	        		
        	}
        	
        	if (null != encargosCliente.getCodCentro()){
        		where.append(" AND CENTRO = ? ");
	        	 params.add(encargosCliente.getCodCentro());	
        	}
        	
        }
        query.append(where);
        
        
        List<String> lista = null;
		try {
			lista = this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<String> getCategorias(
			EncargosClientePlataforma encargosCliente) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT DISTINCT CATEGORIA FROM T_ENCARGOS_CLTE ");
    
    	
        if (encargosCliente  != null){
        	if(encargosCliente.getIdSession()!=null){
        		 where.append(" AND IDDSESION = ? ");
	        	 params.add(encargosCliente.getIdSession());	        		
        	}
        	
        	if (null != encargosCliente.getCodCentro()){
        		where.append(" AND CENTRO = ? ");
	        	 params.add(encargosCliente.getCodCentro());	
        	}
        	
        	if(null != encargosCliente.getSeccion()){
        		 where.append(" AND SECCION = ? ");
	        	 params.add(encargosCliente.getSeccion());	     
        	}
        	
        	
        	
        }
        query.append(where);

        List<String> lista = null;
        try {
	    	lista = this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
        
        return lista;
	}

	@Override
	public List<EncargosClientePlataforma> getReferencias(
			EncargosClientePlataforma encargosCliente, Pagination pagination) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT * FROM T_ENCARGOS_CLTE ");
    
    	
        if (encargosCliente  != null){
        	if(encargosCliente.getIdSession()!=null){
        		 where.append(" AND IDDSESION = ? ");
	        	 params.add(encargosCliente.getIdSession());	        		
        	}
        	
        	if (null != encargosCliente.getCodCentro()){
        		where.append(" AND CENTRO = ? ");
	        	 params.add(encargosCliente.getCodCentro());	
        	}
        	
        	if(encargosCliente.getSeccion()!=null){
          		 where.append(" AND SECCION = ? ");
   	        	 params.add(encargosCliente.getSeccion());	        		
           	}
        	
        	if(encargosCliente.getCategoria()!=null){
          		 where.append(" AND CATEGORIA = ? ");
   	        	 params.add(encargosCliente.getCategoria());	        		
           	}
        	
        }
        query.append(where);
        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}
		}
		else{
			String campoOrdenacion = "REFERENCIA";
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);
		}
        if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}
       
        
        List<EncargosClientePlataforma> lista = null;
 		try {
 			lista = this.jdbcTemplate.query(query.toString(), this.rwEncargoClienteMap, params.toArray());
 		} catch (Exception e){
 			
 			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
 		}
 		return lista;
	}
	
	@Override
	public Long getReferenciasCount(EncargosClientePlataforma encargosCliente) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(*) FROM T_ENCARGOS_CLTE ");
    
    	
        if (encargosCliente  != null){
        	if(encargosCliente.getIdSession()!=null){
        		 where.append(" AND IDDSESION = ? ");
	        	 params.add(encargosCliente.getIdSession());	        		
        	}
        	
        	if (null != encargosCliente.getCodCentro()){
        		where.append(" AND CENTRO = ? ");
	        	 params.add(encargosCliente.getCodCentro());	
        	}
        	
        	if(encargosCliente.getSeccion()!=null){
          		 where.append(" AND SECCION = ? ");
   	        	 params.add(encargosCliente.getSeccion());	        		
           	}
        	
        	if(encargosCliente.getCategoria()!=null){
          		 where.append(" AND CATEGORIA = ? ");
   	        	 params.add(encargosCliente.getCategoria());	        		
           	}
        	
        }
        query.append(where);
     
        
        Long cont = null;
 		try {
 			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
 		} catch (Exception e){
 			
 			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
 		}
 		return cont;
	}
	
	@Override
	public void insertReferencias(final List<EncargosClientePlataforma> listaReferencias) throws Exception{
		String sql = "INSERT INTO "
		        + " T_ENCARGOS_CLTE "
		        + "(centro,area,seccion,categoria,subCategoria,segmento,referencia,descripcion,unidadesCaja,idDsesion, vitrina,flg_per_espec) "
		        + "VALUES " + "(?,?,?,?,?,?,?,?,?,?,?,?)";

	        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

		        @Override
		        public void setValues(PreparedStatement ps, int i)
		            throws SQLException {
	        	
		        	EncargosClientePlataforma myPojo = listaReferencias.get(i);
		            ps.setLong(1, myPojo.getCodCentro());
		            ps.setString(2, myPojo.getArea());
		            ps.setString(3,  myPojo.getSeccion());
		            ps.setString(4, myPojo.getCategoria());
	            
		            ps.setString(5, myPojo.getSubcategoria());
		            ps.setString(6, myPojo.getSegmento());
		            ps.setLong(7, myPojo.getCodReferencia());
		            
		            
		            ps.setString(8, myPojo.getDescripcionArt());
		            ps.setDouble(9, myPojo.getUnidadesCaja());
		            ps.setString(10,  myPojo.getIdSession());
		            ps.setString(11,myPojo.getVitrina());
		            ps.setString(12, myPojo.getFlgEspec());


		        }

		        @Override
		        public int getBatchSize() {
		            return listaReferencias.size();
		        }
		    });

	}
	
	@Override
	public void deleteReferencias(EncargosClientePlataforma encargosCliente) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" DELETE FROM T_ENCARGOS_CLTE ");
    
    	
        if (encargosCliente  != null){
        	if(encargosCliente.getIdSession()!=null){
        		 where.append(" AND IDDSESION = ? ");
	        	 params.add(encargosCliente.getIdSession());	        		
        	}
        	if (null != encargosCliente.getCodCentro()){
        		where.append(" AND CENTRO = ? ");
	        	 params.add(encargosCliente.getCodCentro());	
        	}
        	
        }
        query.append(where);
		
		
		try {
			this.jdbcTemplate.update(query.toString(),params.toArray());
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	   
		
	}
	
	private String getMappedField (String fieldName) {
	      if (fieldName.toUpperCase().equals("CODREFERENCIA")){
	  	      return "REFERENCIA";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCIONART")){
	  	      return "DESCRIPCION";
	  	  }else {
	  	      return fieldName;
	  	  }
  }

}
