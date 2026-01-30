package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VBloqueoEncargosPiladasDao;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VBloqueoEncargosPiladasDaoImpl implements VBloqueoEncargosPiladasDao{
	
	 private JdbcTemplate jdbcTemplate;
	 //private static Logger logger = LoggerFactory.getLogger(VBloqueoEncargosPiladasDaoImpl.class);
	 private RowMapper<VBloqueoEncargosPiladas> rwVBloqueoEncargosPiladasMap = new RowMapper<VBloqueoEncargosPiladas>() {
			public VBloqueoEncargosPiladas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new VBloqueoEncargosPiladas(resultSet.getLong("COD_BLOQUEO"),
			    		resultSet.getLong("COD_N1"), resultSet.getLong("COD_N2"), resultSet.getLong("COD_N3"),
			    		resultSet.getLong("COD_CENTRO"), resultSet.getLong("GRUPO1"), resultSet.getLong("GRUPO2"),
			    		resultSet.getLong("GRUPO3"), resultSet.getLong("GRUPO4"), resultSet.getLong("GRUPO5"),
			    		resultSet.getLong("COD_ARTICULO"), resultSet.getString("COD_TP_BLOQUEO"), resultSet.getString("COD_TP_ORIGEN_EP"),
						resultSet.getDate("FEC_INI"), resultSet.getDate("FEC_FIN"), resultSet.getDate("FECHA_GEN"),
						resultSet.getString("FEC_INI_DDMMYYYY"), resultSet.getString("FEC_FIN_DDMMYYYY")
				    );
			}

	 };

	 private RowMapper<VBloqueoEncargosPiladas> rwVBloqueoEncargosPiladasPopupMap = new RowMapper<VBloqueoEncargosPiladas>() {
			public VBloqueoEncargosPiladas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				Date fecIni = resultSet.getDate("FEC_INI");
				Date fecFin = resultSet.getDate("FEC_FIN");
				if (null != fecIni && null != fecFin){
			    return new VBloqueoEncargosPiladas(null,
			    		null, null, null,
			    		null, null, null,
			    		null, null, null,
			    		null, null, null,
						fecIni, fecFin, null,
						Utilidades.formatearFecha(fecIni),Utilidades.formatearFecha(fecFin)
				    );
				} else {
					return null;
				}
			}

	 };

	@Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
    public List<VBloqueoEncargosPiladas> findAll(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COD_BLOQUEO, COD_N1, COD_N2, COD_N3, COD_CENTRO, "
    										+ " GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, COD_ARTICULO, "
    										+ " COD_TP_BLOQUEO, COD_TP_ORIGEN_EP, FEC_INI, FEC_FIN, FECHA_GEN, " 
    										+ " TO_CHAR(FEC_INI, 'DDMMYYYY') FEC_INI_DDMMYYYY, TO_CHAR(FEC_FIN, 'DDMMYYYY') FEC_FIN_DDMMYYYY "	
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
    	
        if (vBloqueoEncargosPiladas  != null){
        	if(vBloqueoEncargosPiladas.getCodBloqueo()!=null){
        		 where.append(" AND COD_BLOQUEO = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
			if(vBloqueoEncargosPiladas.getCodN1()!=null){
	       		 where.append(" AND COD_N1 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN1());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodN2()!=null){
	       		 where.append(" AND COD_N2 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN2());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodN3()!=null){
	       		 where.append(" AND COD_N3 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN3());	        		
	       	}
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo1()!=null){
       		 	where.append(" AND GRUPO1 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo1());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo2()!=null){
       		 	where.append(" AND GRUPO2 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo2());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo3()!=null){
       		 	where.append(" AND GRUPO3 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo3());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo4()!=null){
       		 	where.append(" AND GRUPO4 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo4());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo5()!=null){
       		 	where.append(" AND GRUPO5 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo5());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
			if(vBloqueoEncargosPiladas.getCodTpBloqueo()!=null){
	       		where.append(" AND COD_TP_BLOQUEO = upper(?) ");
	        	params.add(vBloqueoEncargosPiladas.getCodTpBloqueo());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodTpOrigenEp()!=null){
	       		where.append(" AND COD_TP_ORIGEN_EP = upper(?) ");
	        	params.add(vBloqueoEncargosPiladas.getCodTpOrigenEp());	        		
	       	}
        	if(vBloqueoEncargosPiladas.getFecIni()!=null){
        		where.append(" AND FEC_INI = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFecIni());	        		
        	}
        	if(vBloqueoEncargosPiladas.getFecFin()!=null){
       		 	where.append(" AND FEC_FIN = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFecFin());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getFechaGen()!=null){
      		 	where.append(" AND FECHA_GEN = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFechaGen());	        		
        	}
        }
        
        query.append(where);

		List<VBloqueoEncargosPiladas> vBloqueoEncargosPiladasLista = null;		

	    try {
	    	vBloqueoEncargosPiladasLista = (List<VBloqueoEncargosPiladas>) this.jdbcTemplate.query(query.toString(),this.rwVBloqueoEncargosPiladasMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return vBloqueoEncargosPiladasLista;
    }
    
    @Override
    public List<VBloqueoEncargosPiladas> findAllPaginate(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COD_BLOQUEO, COD_N1, COD_N2, COD_N3, COD_CENTRO, "
    										+ " GRUPO1, GRUPO2, GRUPO3, GRUPO4, GRUPO5, COD_ARTICULO, "
    										+ " COD_TP_BLOQUEO, COD_TP_ORIGEN_EP, FEC_INI, FEC_FIN, FECHA_GEN, "
    										+ " TO_CHAR(FEC_INI, 'DDMMYYYY') FEC_INI_DDMMYYYY, TO_CHAR(FEC_FIN, 'DDMMYYYY') FEC_FIN_DDMMYYYY "	
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
    	
        if (vBloqueoEncargosPiladas  != null){
        	if(vBloqueoEncargosPiladas.getCodBloqueo()!=null){
        		 where.append(" AND COD_BLOQUEO = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
			if(vBloqueoEncargosPiladas.getCodN1()!=null){
	       		 where.append(" AND COD_N1 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN1());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodN2()!=null){
	       		 where.append(" AND COD_N2 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN2());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodN3()!=null){
	       		 where.append(" AND COD_N3 = ? ");
	        	 params.add(vBloqueoEncargosPiladas.getCodN3());	        		
	       	}
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo1()!=null){
       		 	where.append(" AND GRUPO1 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo1());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo2()!=null){
       		 	where.append(" AND GRUPO2 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo2());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo3()!=null){
       		 	where.append(" AND GRUPO3 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo3());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo4()!=null){
       		 	where.append(" AND GRUPO4 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo4());	        		
        	}
        	if(vBloqueoEncargosPiladas.getGrupo5()!=null){
       		 	where.append(" AND GRUPO5 = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getGrupo5());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
			if(vBloqueoEncargosPiladas.getCodTpBloqueo()!=null){
	       		where.append(" AND COD_TP_BLOQUEO = upper(?) ");
	        	params.add(vBloqueoEncargosPiladas.getCodTpBloqueo());	        		
	       	}
			if(vBloqueoEncargosPiladas.getCodTpOrigenEp()!=null){
	       		where.append(" AND COD_TP_ORIGEN_EP = upper(?) ");
	        	params.add(vBloqueoEncargosPiladas.getCodTpOrigenEp());	        		
	       	}
        	if(vBloqueoEncargosPiladas.getFecIni()!=null){
        		where.append(" AND FEC_INI = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFecIni());	        		
        	}
        	if(vBloqueoEncargosPiladas.getFecFin()!=null){
       		 	where.append(" AND FEC_FIN = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFecFin());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getFechaGen()!=null){
      		 	where.append(" AND FECHA_GEN = TRUNC(?) ");
	        	params.add(vBloqueoEncargosPiladas.getFechaGen());	        		
        	}
        }
        
        query.append(where);
		
		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}

		List<VBloqueoEncargosPiladas> vBloqueoEncargosPiladasLista = null;		

	    try {
	    	vBloqueoEncargosPiladasLista = (List<VBloqueoEncargosPiladas>) this.jdbcTemplate.query(query.toString(),this.rwVBloqueoEncargosPiladasMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return vBloqueoEncargosPiladasLista;
    }
    
    @Override
    public List<VBloqueoEncargosPiladas> findMotivosRefBloqueada(VBloqueoEncargosPiladas vBloqueoEncargosPiladas, Pagination pagination) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MIN(FEC_INI) AS FEC_INI, MAX(FEC_FIN) AS FEC_FIN "
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
        if (vBloqueoEncargosPiladas  != null){
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(Constantes.COD_TP_BLOQUEO_ENCARGO.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND (( ");
        		//Control de bloqueo por encargo
        		where.append(" ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? )");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	 
       		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" ) OR ( ");
        		//Control de bloqueo por encargo y pilada
        		where.append(" ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? )");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);	
       		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" )) ");
        	}
        	else if(Constantes.COD_TP_BLOQUEO_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		//Control de bloqueo por encargo y pilada
        		where.append(" AND ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);
       		 params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
       		
       		 
       		 
       		 	if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        	}
        	else if(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND (( ");
        		//Control de bloqueo por encargo
        		where.append(" ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	     
       		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		 	//Obtener la última fecha de encargo
       		    Date fechaFinEncargoDate = null;
       		    if(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha5DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()); 
       		    }else if(vBloqueoEncargosPiladas.getFecha4DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha4DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha4DDMMYYYY());
       		    }else if(vBloqueoEncargosPiladas.getFecha3DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha3DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha3DDMMYYYY());
       		    }else if(vBloqueoEncargosPiladas.getFecha2DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha2DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha2DDMMYYYY());
       		    }
       		    
        		if(fechaInicioDate!=null && fechaFinEncargoDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinEncargoDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinEncargoDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		
        		where.append(" ) OR ( ");
        		//Control de bloqueo por encargo y pilada
        		where.append(" ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);
       		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" )) ");
        	}else{
        		//Si no hay codTpBloqueo no se devuelven registros
        		where.append(" AND 1 = 2 ");
        	}
        }
        
        query.append(where);

    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY FEC_INI asc, FEC_FIN asc");
		query.append(order);

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}

		List<VBloqueoEncargosPiladas> vBloqueoEncargosPiladasLista = null;		
		
		try {
			vBloqueoEncargosPiladasLista = (List<VBloqueoEncargosPiladas>) this.jdbcTemplate.query(query.toString(),this.rwVBloqueoEncargosPiladasPopupMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return vBloqueoEncargosPiladasLista;
    }
    
    @Override
    public Long findMotivosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT FEC_INI||FEC_FIN) "	
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
        if (vBloqueoEncargosPiladas  != null){
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(Constantes.COD_TP_BLOQUEO_ENCARGO.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND (( ");
        		//Control de bloqueo por encargo
       		 	where.append(" ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
    		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" ) OR ( ");
        		//Control de bloqueo por encargo y pilada
       		 	where.append(" ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);	
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" )) ");
        	}
        	else if(Constantes.COD_TP_BLOQUEO_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		//Control de bloqueo por encargo y pilada      
       		 	where.append(" AND ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
    		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);	
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        	}
        	else if(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND (( ");
        		//Control de bloqueo por encargo  
       		 	where.append(" ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
    		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		 	Date fechaInicioDate = ((vBloqueoEncargosPiladas.getFecIniDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecIniDDMMYYYY()):null);
       		 	//Obtener la última fecha de encargo
       		    Date fechaFinEncargoDate = null;
       		    if(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha5DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha5DDMMYYYY()); 
       		    }else if(vBloqueoEncargosPiladas.getFecha4DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha4DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha4DDMMYYYY());
       		    }else if(vBloqueoEncargosPiladas.getFecha3DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha3DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha3DDMMYYYY());
       		    }else if(vBloqueoEncargosPiladas.getFecha2DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha2DDMMYYYY())){
       		    	fechaFinEncargoDate = Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecha2DDMMYYYY());
       		    }
       		    
        		if(fechaInicioDate!=null && fechaFinEncargoDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinEncargoDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinEncargoDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		
        		where.append(" ) OR ( ");
        		//Control de bloqueo por encargo y pilada
       		 	where.append(" ( COD_TP_BLOQUEO = ?  OR COD_TP_BLOQUEO = ? )");
    		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);	
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);

       		    Date fechaFinDate = ((vBloqueoEncargosPiladas.getFecFinDDMMYYYY() != null && !"".equals(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()))? Utilidades.convertirStringAFecha(vBloqueoEncargosPiladas.getFecFinDDMMYYYY()):null);
        		if(fechaInicioDate!=null && fechaFinDate!=null){
					where.append(" AND ((? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaInicioDate);
					 
					where.append(" OR  (? BETWEEN FEC_INI AND FEC_FIN) ");
					params.add(fechaFinDate);	        		
					
					where.append(" OR  (? < FEC_INI AND ? > FEC_FIN)) ");
					params.add(fechaInicioDate);
					params.add(fechaFinDate);	        		
            	}else{
            		where.append(" AND 1 = 2 ");
            	}
        		where.append(" )) ");
        	}else{
        		//Si no hay codTpBloqueo no se devuelven registros
        		where.append(" AND 1 = 2 ");
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
    public Long registrosRefBloqueadaCont(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(COD_ARTICULO) "	
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
        if (vBloqueoEncargosPiladas  != null){
        	where.append(" AND FEC_FIN >= SYSDATE");
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(Constantes.COD_TP_BLOQUEO_ENCARGO.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND (( ");
        		//Control de bloqueo por encargo
        		where.append(" COD_TP_BLOQUEO = ? ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	 

       		 	
        		where.append(" ) OR ( ");
        		//Control de bloqueo por encargo y pilada
        		where.append(" COD_TP_BLOQUEO = ? ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);	      
       		 	
       		 	where.append(" ) OR ( ");
       		 	//Control de bloqueo por encargo y pilada
       		 	where.append(" COD_TP_BLOQUEO = ? ");
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);	        		


        		where.append(" )) ");


        	}
        	else if(Constantes.COD_TP_BLOQUEO_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		//Control de bloqueo por encargo y pilada
        		where.append(" AND ( ( COD_TP_BLOQUEO = ? ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);

       		 	where.append(" ) OR ( ");
       		 	//Control de bloqueo por encargo y pilada
       		 	where.append(" COD_TP_BLOQUEO = ? ");
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);	        		


        		where.append(" )) ");

        	}
        	else if(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE.equals(vBloqueoEncargosPiladas.getCodTpBloqueo())){
        		where.append(" AND ( (");
        		//Control de bloqueo por encargo
        		where.append(" COD_TP_BLOQUEO = ? ");
       		 	params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	       

       		 	where.append(" ) OR ( ");
       		 	//Control de bloqueo por encargo y pilada
       		 	where.append(" COD_TP_BLOQUEO = ? ");
    		 	params.add(Constantes.COD_TP_BLOQUEO_TODOS);	        		


        		where.append(" )) ");

        	}else{
        		//Si no hay codTpBloqueo no se devuelven registros
        		where.append(" AND 1 = 2 ");
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
    public VBloqueoEncargosPiladas getBloqueoFecha(VBloqueoEncargosPiladas vBloqueoEncargosPiladas) throws Exception {
    	
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT MIN(FEC_INI) AS FEC_INI, MAX(FEC_FIN) AS FEC_FIN "
    										+ " FROM V_BLOQUEO_ENCARGOS_PILADAS ");
    
        if (vBloqueoEncargosPiladas  != null){
        	if(vBloqueoEncargosPiladas.getCodCentro()!=null){
       		 	where.append(" AND COD_CENTRO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodCentro());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodArticulo()!=null){
       		 	where.append(" AND COD_ARTICULO = ? ");
       		 	params.add(vBloqueoEncargosPiladas.getCodArticulo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getModo()!=null){
       		 	where.append(" AND MODO = ? ");
	        	params.add(vBloqueoEncargosPiladas.getModo());	        		
        	}
        	if(vBloqueoEncargosPiladas.getCodTpBloqueo()!=null){
        		if (vBloqueoEncargosPiladas.getCodTpBloqueo().equals(Constantes.COD_TP_BLOQUEO_DETALLADO)){
       		 		where.append(" AND ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
       		 		params.add(vBloqueoEncargosPiladas.getCodTpBloqueo());	        
       		 		params.add(Constantes.COD_TP_BLOQUEO_TODOS);
        		} else if (vBloqueoEncargosPiladas.getCodTpBloqueo().equals(Constantes.COD_TP_BLOQUEO_MONTAJE)){
        			if (vBloqueoEncargosPiladas.getEsFresco().booleanValue()){
        				where.append(" AND ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
        				params.add(Constantes.COD_TP_BLOQUEO_ENCARGO);	 
           		 		params.add(Constantes.COD_TP_BLOQUEO_MONTAJE);	 
           		 		params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);
           		 		params.add(Constantes.COD_TP_BLOQUEO_TODOS);
        			} else {
        				where.append(" AND ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
           		 		params.add(Constantes.COD_TP_BLOQUEO_MONTAJE);	 
           		 		params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);
           		 		params.add(Constantes.COD_TP_BLOQUEO_TODOS);
        			}
        			
        		} else {
        			where.append(" AND ( COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? OR COD_TP_BLOQUEO = ? ) ");
       		 		params.add(vBloqueoEncargosPiladas.getCodTpBloqueo());	 
       		 		params.add(Constantes.COD_TP_BLOQUEO_ENCARGO_Y_MONTAJE);
       		 		params.add(Constantes.COD_TP_BLOQUEO_TODOS);
        		}
        	}
        	if (null != vBloqueoEncargosPiladas.getFechaControl()){
        		where.append(" AND ? BETWEEN FEC_INI AND FEC_FIN ");
        		params.add(vBloqueoEncargosPiladas.getFechaControl());
        	}
        	


        }

        query.append(where);

        try {
			return this.jdbcTemplate.queryForObject(query.toString(),this.rwVBloqueoEncargosPiladasPopupMap, params.toArray());
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			return null;
		} 
    }
    
    
}
