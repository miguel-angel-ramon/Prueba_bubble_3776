package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.TExclusionVentasDao;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.model.TExclusionVentas;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class TExclusionVentasDaoImpl implements TExclusionVentasDao{
	
	private static Logger logger = Logger.getLogger(TExclusionVentasDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	 
	private RowMapper<TExclusionVentas> rwTExclusionVentasMap = new RowMapper<TExclusionVentas>() {
		public TExclusionVentas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    TExclusionVentas tExclusionVentas = new TExclusionVentas(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),resultSet.getString("DESCRIP_ART"),
		    			resultSet.getLong("GRUPO1"),resultSet.getLong("GRUPO2"),
		    			resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"), resultSet.getLong("GRUPO5"), 
		    			resultSet.getString("DESCRIP_GRUPO1"), resultSet.getString("DESCRIP_GRUPO2"),resultSet.getString("DESCRIP_GRUPO3"),
		    			resultSet.getString("DESCRIP_GRUPO4"),resultSet.getString("DESCRIP_GRUPO5"),resultSet.getLong("IDENTIFICADOR"),
		    			resultSet.getDate("FECHA"),resultSet.getString("ID_SESION"), resultSet.getDate("FECHA_GEN"),
		    			resultSet.getLong("COD_ERROR"),resultSet.getString("DESCRIP_ERROR")
			    );
		    tExclusionVentas.setIdentificadorSIA(resultSet.getLong("IDENTIFICADOR_SIA"));
		    
		    return tExclusionVentas;
		}
	};
		
    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
	public void delete(TExclusionVentas tExclusionVentas) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_EXCLUSION_VENTAS WHERE ID_SESION = ? ");
		query.append(" AND COD_CENTRO = ? ");
		params.add(tExclusionVentas.getIdSesion());
		params.add(tExclusionVentas.getCodCentro());

		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}
    
    @Override
	public void deleteHistorico() throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_EXCLUSION_VENTAS WHERE FECHA_GEN < (SYSDATE - ?) ");

		params.add(Constantes.DIAS_ELIMINAR);

		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}
    
    @Override
	public void updateErrores(TExclusionVentas tExclusionVentas) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE T_EXCLUSION_VENTAS SET COD_ERROR = ?, DESCRIP_ERROR = ? ");
		query.append("WHERE ID_SESION = ? AND COD_CENTRO = ? ");
		params.add(tExclusionVentas.getCodError());
		params.add(tExclusionVentas.getDescripError());
		params.add(tExclusionVentas.getIdSesion());
		params.add(tExclusionVentas.getCodCentro());
		if (null != tExclusionVentas.getIdentificador()){
			query.append(" AND IDENTIFICADOR = ?");
			params.add(tExclusionVentas.getIdentificador());
		}
		if (null != tExclusionVentas.getIdentificadorSIA()){
			query.append(" AND IDENTIFICADOR_SIA = ?");
			params.add(tExclusionVentas.getIdentificadorSIA());
		}
    	if(tExclusionVentas.getCodArt()!=null){
    		query.append(" AND COD_ART = ? ");
        	params.add(tExclusionVentas.getCodArt());	        		
    	}
    	if(tExclusionVentas.getGrupo1()!=null){
    		query.append(" AND GRUPO1 = ? ");
        	params.add(tExclusionVentas.getGrupo1());	        		
    	}
    	if(tExclusionVentas.getGrupo2()!=null){
    		query.append(" AND GRUPO2 = ? ");
        	params.add(tExclusionVentas.getGrupo2());	        		
    	}
    	if(tExclusionVentas.getGrupo3()!=null){
    		query.append(" AND GRUPO3 = ? ");
        	params.add(tExclusionVentas.getGrupo3());	        		
    	}
    	if(tExclusionVentas.getGrupo4()!=null){
    		query.append(" AND GRUPO4 = ? ");
        	params.add(tExclusionVentas.getGrupo4());	        		
    	}
    	if(tExclusionVentas.getGrupo5()!=null){
    		query.append(" AND GRUPO5 = ? ");
        	params.add(tExclusionVentas.getGrupo5());	        		
    	}
    	if(tExclusionVentas.getFecha()!=null){
   		 	query.append(" AND TRUNC(FECHA) = TRUNC(?) ");
        	params.add(tExclusionVentas.getFecha());	        		
    	}

		
    	try {
    		this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		

	}
    
    @Override
	public void insertAll(List<TExclusionVentas> listaTExclusionVentas) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("INSERT INTO T_EXCLUSION_VENTAS (COD_CENTRO, COD_ART, DESCRIP_ART, ");
		query.append(" GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, DESCRIP_GRUPO1, DESCRIP_GRUPO2, DESCRIP_GRUPO3, DESCRIP_GRUPO4, ");
		query.append(" DESCRIP_GRUPO5, IDENTIFICADOR, FECHA, ID_SESION, FECHA_GEN, COD_ERROR, DESCRIP_ERROR, IDENTIFICADOR_SIA) ");
		query.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ");

		if (listaTExclusionVentas != null && listaTExclusionVentas.size()>0)
		{
			TExclusionVentas campo = new TExclusionVentas();
			for (int i =0;i<listaTExclusionVentas.size();i++)
			{
				campo = (TExclusionVentas)listaTExclusionVentas.get(i);
				
				params = new ArrayList<Object>();
				
				params.add(campo.getCodCentro());
				params.add(campo.getCodArt());
				params.add(campo.getDescripArt());
				params.add(campo.getGrupo1());
				params.add(campo.getGrupo2());
				params.add(campo.getGrupo3());
				params.add(campo.getGrupo4());
				params.add(campo.getGrupo5());
				params.add(campo.getDescripGrupo1());
				params.add(campo.getDescripGrupo2());
				params.add(campo.getDescripGrupo3());
				params.add(campo.getDescripGrupo4());
				params.add(campo.getDescripGrupo5());
				params.add(campo.getIdentificador());
				params.add(campo.getFecha());
				params.add(campo.getIdSesion());
				params.add(campo.getFechaGen());
				params.add(campo.getCodError());
				params.add(campo.getDescripError());
				params.add(campo.getIdentificadorSIA());

				
				try {
					this.jdbcTemplate.update(query.toString(), params.toArray());
					
				} catch (Exception e){
					
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				}
			}
		}
	}
	    
    @Override
    public List<TExclusionVentas> findAllPaginate(TExclusionVentas tExclusionVentas, Pagination pagination) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	
    	//La lista en pantalla vendrá ordenada de la siguiente forma (guardados, erroneos, resto) 
    	StringBuffer query = new StringBuffer(" SELECT DECODE(NVL(COD_ERROR,0),-1,1,0,3,2) COD_ERROR_ORDEN, "
    			+  " COD_CENTRO, COD_ART, DESCRIP_ART, "
				+  " GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, DESCRIP_GRUPO1, DESCRIP_GRUPO2, DESCRIP_GRUPO3, DESCRIP_GRUPO4, "
				+  " DESCRIP_GRUPO5, IDENTIFICADOR, FECHA, ID_SESION, FECHA_GEN, COD_ERROR, DESCRIP_ERROR, IDENTIFICADOR_SIA " 
				+ " FROM T_EXCLUSION_VENTAS ");

		if (tExclusionVentas  != null){
		
        	if(tExclusionVentas.getCodCentro()!=null){
        		where.append(" AND COD_CENTRO = ? ");
	        	params.add(tExclusionVentas.getCodCentro());	        		
        	}
        	if(tExclusionVentas.getCodArt()!=null){
        		where.append(" AND COD_ART = ? ");
	        	params.add(tExclusionVentas.getCodArt());	        		
        	}
        	if(tExclusionVentas.getDescripArt()!=null){
        		where.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripArt());	        		
        	}
        	if(tExclusionVentas.getGrupo1()!=null){
        		where.append(" AND GRUPO1 = ? ");
	        	params.add(tExclusionVentas.getGrupo1());	        		
        	}
        	if(tExclusionVentas.getGrupo2()!=null){
        		where.append(" AND GRUPO2 = ? ");
	        	params.add(tExclusionVentas.getGrupo2());	        		
        	}
        	if(tExclusionVentas.getGrupo3()!=null){
       		 	where.append(" AND GRUPO3 = ? ");
	        	params.add(tExclusionVentas.getGrupo3());	        		
        	}
        	if(tExclusionVentas.getGrupo4()!=null){
       		    where.append(" AND GRUPO4 = ? ");
	        	params.add(tExclusionVentas.getGrupo4());	        		
        	}
        	if(tExclusionVentas.getGrupo5()!=null){
       		 	where.append(" AND GRUPO5 = ? ");
	        	params.add(tExclusionVentas.getGrupo5());	        		
        	}
        	if(tExclusionVentas.getDescripGrupo1()!=null){
        		where.append(" AND UPPER(DESCRIP_GRUPO1) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo1());	        		
        	}
        	if(tExclusionVentas.getDescripGrupo2()!=null){
        		where.append(" AND UPPER(DESCRIP_GRUPO2) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo2());	        		
        	}
        	if(tExclusionVentas.getDescripGrupo3()!=null){
       		 	where.append(" AND UPPER(DESCRIP_GRUPO3) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo3());	        		
        	}
        	if(tExclusionVentas.getDescripGrupo4()!=null){
       		 	where.append(" AND UPPER(DESCRIP_GRUPO4) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo4());	        		
        	}
        	if(tExclusionVentas.getDescripGrupo5()!=null){
       		 	where.append(" AND UPPER(DESCRIP_GRUPO5) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo5());	        		
        	}
        	if(tExclusionVentas.getIdentificador()!=null){
        		where.append(" AND IDENTIFICADOR = ? ");
	        	params.add(tExclusionVentas.getIdentificador());	        		
        	}
        	if(tExclusionVentas.getFecha()!=null){
       		 	where.append(" AND TRUNC(FECHA) = TRUNC(?) ");
	        	params.add(tExclusionVentas.getFecha());	        		
        	}
        	if(tExclusionVentas.getIdSesion()!=null){
       		 	where.append(" AND UPPER(ID_SESION) = upper(?) ");
	        	params.add(tExclusionVentas.getIdSesion());	        		
        	}
        	if(tExclusionVentas.getFechaGen()!=null){
          		where.append(" AND TRUNC(FECHA_GEN) = TRUNC(?) ");
   	        	params.add(tExclusionVentas.getFechaGen());	        		
           	}
        	if(tExclusionVentas.getCodError()!=null){
       		 	where.append(" AND COD_ERROR = ? ");
	        	params.add(tExclusionVentas.getCodError());	        		
        	}
        	if(tExclusionVentas.getDescripError()!=null){
        		where.append(" AND UPPER(DESCRIP_ERROR) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripError());	        		
        	}
		}

        query.append(where);
        
        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
				query.append(order);
			}
		}else{
			query.append(" order by COD_ERROR_ORDEN, GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, COD_ART, FECHA ");
		}
		
		/*if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}*/

		List<TExclusionVentas> tExclusionVentasLista = null;		
		
		try {
			tExclusionVentasLista = (List<TExclusionVentas>) this.jdbcTemplate.query(query.toString(),this.rwTExclusionVentasMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return tExclusionVentasLista;
    }
	    
    @Override
	public void update(TExclusionVentas tExclusionVentas) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(" UPDATE T_EXCLUSION_VENTAS SET COD_CENTRO = ?, COD_ART = ?, DESCRIP_ART = ?, ");
		query.append(" GRUPO1 = ?, GRUPO2 = ?, GRUPO3 = ?, GRUPO4 = ?, GRUPO5 = ?, ");
		query.append(" DESCRIP_GRUPO1 = ?, DESCRIP_GRUPO2 = ?, DESCRIP_GRUPO3 = ?, DESCRIP_GRUPO4 = ?, DESCRIP_GRUPO5 = ?, ");
		query.append(" IDENTIFICADOR = ?, FECHA = ?, ID_SESION = ?, FECHA_GEN = ?, COD_ERROR = ?, DESCRIP_ERROR = ? ");
		query.append(" WHERE 1 = 1 ");

		if (tExclusionVentas != null){
			if(tExclusionVentas.getCodCentro()!=null){
				query.append(" AND COD_CENTRO = ? ");
	        	params.add(tExclusionVentas.getCodCentro());	        		
	    	}
	    	if(tExclusionVentas.getCodArt()!=null){
	    		query.append(" AND COD_ART = ? ");
	        	params.add(tExclusionVentas.getCodArt());	        		
	    	}
	    	if(tExclusionVentas.getDescripArt()!=null){
	    		query.append(" AND UPPER(DESCRIP_ART) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripArt());	        		
	    	}
	    	if(tExclusionVentas.getGrupo1()!=null){
	    		query.append(" AND GRUPO1 = ? ");
	        	params.add(tExclusionVentas.getGrupo1());	        		
	    	}
	    	if(tExclusionVentas.getGrupo2()!=null){
	    		query.append(" AND GRUPO2 = ? ");
	        	params.add(tExclusionVentas.getGrupo2());	        		
	    	}
	    	if(tExclusionVentas.getGrupo3()!=null){
	   		 	query.append(" AND GRUPO3 = ? ");
	        	params.add(tExclusionVentas.getGrupo3());	        		
	    	}
	    	if(tExclusionVentas.getGrupo4()!=null){
	   		    query.append(" AND GRUPO4 = ? ");
	        	params.add(tExclusionVentas.getGrupo4());	        		
	    	}
	    	if(tExclusionVentas.getGrupo5()!=null){
	   		 	query.append(" AND GRUPO5 = ? ");
	        	params.add(tExclusionVentas.getGrupo5());	        		
	    	}
	    	if(tExclusionVentas.getDescripGrupo1()!=null){
	    		query.append(" AND UPPER(DESCRIP_GRUPO1) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo1());	        		
	    	}
	    	if(tExclusionVentas.getDescripGrupo2()!=null){
	    		query.append(" AND UPPER(DESCRIP_GRUPO2) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo2());	        		
	    	}
	    	if(tExclusionVentas.getDescripGrupo3()!=null){
	   		 	query.append(" AND UPPER(DESCRIP_GRUPO3) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo3());	        		
	    	}
	    	if(tExclusionVentas.getDescripGrupo4()!=null){
	   		 	query.append(" AND UPPER(DESCRIP_GRUPO4) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo4());	        		
	    	}
	    	if(tExclusionVentas.getDescripGrupo5()!=null){
	   		 	query.append(" AND UPPER(DESCRIP_GRUPO5) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripGrupo5());	        		
	    	}
	    	if(tExclusionVentas.getIdentificador()!=null){
	    		 query.append(" AND IDENTIFICADOR = ? ");
	        	 params.add(tExclusionVentas.getIdentificador());	        		
	    	}
	    	if(tExclusionVentas.getIdentificadorSIA()!=null){
	    		 query.append(" AND IDENTIFICADOR_SIA = ? ");
	        	 params.add(tExclusionVentas.getIdentificadorSIA());	        		
	    	}
	    	if(tExclusionVentas.getFecha()!=null){
	   		 	query.append(" AND TRUNC(FECHA) = TRUNC(?) ");
	        	 params.add(tExclusionVentas.getFecha());	        		
	    	}
	    	if(tExclusionVentas.getIdSesion()!=null){
	   		 	query.append(" AND UPPER(ID_SESION) = upper(?) ");
	        	params.add(tExclusionVentas.getIdSesion());	        		
	    	}
	    	if(tExclusionVentas.getFechaGen()!=null){
	      		 query.append(" AND TRUNC(FECHA_GEN) = TRUNC(?) ");
		         params.add(tExclusionVentas.getFechaGen());	        		
	       	}
	    	if(tExclusionVentas.getCodError()!=null){
	   		 	query.append(" AND COD_ERROR = ? ");
	        	params.add(tExclusionVentas.getCodError());	        		
	    	}
	    	if(tExclusionVentas.getDescripError()!=null){
	    		query.append(" AND UPPER(DESCRIP_ERROR) = upper(?) ");
	        	params.add(tExclusionVentas.getDescripError());	        		
	    	}
		}
		
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		
		
	}
	    
    private String getMappedField (String fieldName) {
	  	  if (fieldName.toUpperCase().equals("CODART")){
	  	      return "COD_ART";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPART")){
	  	      return "DESCRIP_ART";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO1")){
	  	      return "GRUPO1";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO2")){
	  	      return "GRUPO2";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO3")){
	  	      return "GRUPO3";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO4")){
	  	      return "GRUPO4";
	  	  }else if (fieldName.toUpperCase().equals("DESCRIPGRUPO5")){
	  	      return "GRUPO5";
	  	  }else if (fieldName.toUpperCase().equals("CODERROR")){
	  	      return "COD_ERROR_ORDEN"; //Orden primero erroneos y por último el resto
	  	  }else {
	  	      return fieldName;
	  	  }
  	}
}
