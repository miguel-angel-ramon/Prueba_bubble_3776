package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DiasServicioDao;
import es.eroski.misumi.model.CalendarioDescripcionServicio;
import es.eroski.misumi.model.CalendarioDescripcionServicios;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.FechaEntregaDiasServicio;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
 	
@Repository
public class DiasServicioDaoImpl implements DiasServicioDao{
	
	private static Logger logger = Logger.getLogger(DiasServicioDaoImpl.class);
	
	 private JdbcTemplate jdbcTemplate;
	 
	 private JdbcTemplate jdbcTemplate2;

	 private RowMapper<DiasServicio> rwDiasServicioMap = new RowMapper<DiasServicio>() {
			public DiasServicio mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new DiasServicio(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
			    			resultSet.getLong("COD_N1"), resultSet.getLong("COD_N2"), resultSet.getLong("COD_N3"),
			    			resultSet.getDate("FECHA"), resultSet.getString("FECHA_PANTALLA"), resultSet.getString("D_FESTIVO"),
			    			resultSet.getLong("D_PEDIDO"), resultSet.getString("D_SERVICIO"),
			    			resultSet.getDate("CREATION_DATE"), resultSet.getDate("LAST_UPDATE_DATE"),
			    			resultSet.getString("IDSESION"), 
			    			(resultSet.getObject("IDENTIFICADOR")!=null?resultSet.getLong("IDENTIFICADOR"):null),
			    			(resultSet.getObject("IDENTIFICADOR_SIA")!=null?resultSet.getLong("IDENTIFICADOR_SIA"):null),
			    			resultSet.getString("D_ENCARGO"), resultSet.getString("D_MONTAJE"),
			    			 resultSet.getString("TIPO_DIA_L_N"), resultSet.getString("B_ENCARGO"), 
			    			 resultSet.getString("B_ENCARGO_PILADA"),  resultSet.getString("D_ENCARGO_CLIENTE")
				    );
			}

	 };

	 @Autowired
	 public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplate = new JdbcTemplate(dataSource);
	 } 
	 @Autowired
	 public void setDataSource2(DataSource dataSourceSIA) {
		 this.jdbcTemplate2 = new JdbcTemplate(dataSourceSIA);
	 } 
	 
	 @Autowired
	 private VDatosDiarioArtService vDatosDiarioArtService;

	 @Override
	 public List<DiasServicio> findAll(DiasServicio diasServicio) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, COD_ART, COD_N1, COD_N2, COD_N3, FECHA, TO_CHAR(FECHA,'DDMMYYYY') FECHA_PANTALLA, D_FESTIVO, D_PEDIDO, D_SERVICIO, CREATION_DATE, " +
	    													" LAST_UPDATE_DATE, IDSESION, IDENTIFICADOR, IDENTIFICADOR_SIA, D_ENCARGO, D_MONTAJE, TIPO_DIA_L_N, B_ENCARGO, B_ENCARGO_PILADA, D_ENCARGO_CLIENTE " 
	    										+ " FROM DIAS_SERVICIO ");
	    
	        if (diasServicio  != null){
	        	if(diasServicio.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(diasServicio.getCodCentro());	        		
	        	}
	        	if(diasServicio.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(diasServicio.getCodArt());	        		
	        	}
	        	if(diasServicio.getCodN1()!=null){
	        		 where.append(" AND COD_N1 = ? ");
		        	 params.add(diasServicio.getCodN1());	        		
	        	}
	        	if(diasServicio.getCodN2()!=null){
	        		 where.append(" AND COD_N2 = ? ");
		        	 params.add(diasServicio.getCodN2());	        		
	        	}
	        	if(diasServicio.getCodN3()!=null){
	        		 where.append(" AND COD_N3 = ? ");
		        	 params.add(diasServicio.getCodN3());	        		
	        	}
	        	if(diasServicio.getFecha()!=null){
	        		 //Si se proporciona fecha hay que devolver los registros de ese mes. Se suma 7 para asegurarnos que está dentro del mes a tratar.
	        		 //En el datepicker se tienen en cuenta días del mes anterior por lo que no empieza el día 1.
	        		 where.append(" AND FECHA BETWEEN TRUNC(?+7, 'mm')AND TRUNC(LAST_DAY(?+7)) ");
		        	 params.add(diasServicio.getFecha());	        		
		        	 params.add(diasServicio.getFecha());
	        	}
	        	if(diasServicio.getdFestivo()!=null){
	        		 where.append(" AND D_FESTIVO = ? ");
		        	 params.add(diasServicio.getdFestivo());	        		
	        	}
	        	if(diasServicio.getdPedido()!=null){
	        		 where.append(" AND D_PEDIDO = upper(?) ");
		        	 params.add(diasServicio.getdPedido());	        		
	        	}
	        	if(diasServicio.getdServicio()!=null){
	        		 where.append(" AND D_SERVICIO = ? ");
		        	 params.add(diasServicio.getdServicio());	        		
	        	}
	        	if(diasServicio.getCreationDate()!=null){
	        		 where.append(" AND CREATION_DATE = TRUNC(?) ");
		        	 params.add(diasServicio.getCreationDate());	        		
	        	}
	        	if(diasServicio.getLastUpdateDate()!=null){
	        		 where.append(" AND LAST_UPDATE_DATE = TRUNC(?) ");
		        	 params.add(diasServicio.getLastUpdateDate());	        		
	        	}
	        	if(diasServicio.getIdsesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(diasServicio.getIdsesion());	        		
	        	}
	        	if(diasServicio.getIdentificador()!=null){
	        		 where.append(" AND IDENTIFICADOR = ? ");
		        	 params.add(diasServicio.getIdentificador());	        		
	        	}
	        	if(diasServicio.getIdentificadorSIA()!=null){
	        		 where.append(" AND IDENTIFICADOR_SIA = ? ");
		        	 params.add(diasServicio.getIdentificadorSIA());	        		
	        	}
	        	if(diasServicio.getdEncargo()!=null){
	        		 where.append(" AND D_ENCARGO = upper(?) ");
		        	 params.add(diasServicio.getdEncargo());	        		
	        	}
	        	if(diasServicio.getdMontaje()!=null){
	        		 where.append(" AND D_MONTAJE = upper(?) ");
		        	 params.add(diasServicio.getdMontaje());	        		
	        	}
	        	if(diasServicio.getTipoDiaLN()!=null){
	        		 where.append(" AND TIPO_DIA_L_N = upper(?) ");
		        	 params.add(diasServicio.getTipoDiaLN());	        		
	        	}
	        	if(diasServicio.getbEncargo()!=null){
	        		 where.append(" AND B_ENCARGO = upper(?) ");
		        	 params.add(diasServicio.getbEncargo());	        		
	        	}
	        	if(diasServicio.getbEncargoPilada()!=null){
	        		 where.append(" AND B_ENCARGO_PILADA = upper(?) ");
		        	 params.add(diasServicio.getbEncargoPilada());	        		
	        	}
	        	if(null != diasServicio.getUuid()){
	        		 where.append(" AND UUID <> ? ");
		        	 params.add(diasServicio.getIdentificador());	        		
	        	}
	        	if(null!= diasServicio.getFechaDesde()){
	        		where.append(" AND FECHA >= TRUNC(?) ");
	        		params.add(diasServicio.getFechaDesde());	
	        	}
	        }
	        
	        query.append(where);

	        List<DiasServicio> diasServicioLista = null;		
	        
	        try {
	        	 diasServicioLista = (List<DiasServicio>) this.jdbcTemplate.query(query.toString(),this.rwDiasServicioMap, params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

			return diasServicioLista;
	 }
	   
    @Override
    public DiasServicio recargaDiasServicio(final DiasServicio diasServicio) throws Exception  {
    	
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		DiasServicio diasServicioSalida = new DiasServicio();
		Long codError = new Long (0);
		
		declaredParameters.add(new SqlParameter("p_centro", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_cod_art", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_idsesion", Types.VARCHAR));
		declaredParameters.add(new SqlOutParameter("p_cod_error", Types.INTEGER));
		
		try{
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {
	
			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_MIS_PROCESOS.p_mis_cal_dias_servicio(?, ?, ?, ?)}");
	
			        stmnt.setInt(1, diasServicio.getCodCentro().intValue());
			        if (diasServicio.getCodArt() != null && !diasServicio.getCodArt().equals(new Long(0))){
			        	stmnt.setInt(2, diasServicio.getCodArt().intValue());	
			        }else{
			        	stmnt.setNull(2, OracleTypes.NULL);
			        }
			        if (diasServicio.getIdsesion() != null){
			        	stmnt.setString(3, diasServicio.getIdsesion());	
			        }else{
			        	stmnt.setNull(3, OracleTypes.NULL);
			        }
			        stmnt.registerOutParameter(4, Types.INTEGER);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			

			
			diasServicioSalida.setCodCentro(diasServicio.getCodCentro());
			diasServicioSalida.setCodArt(diasServicio.getCodArt());
			diasServicioSalida.setIdsesion(diasServicio.getIdsesion());
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error")!=null && "0".equals(mapaResultados.get("p_cod_error").toString())){
					if (mapaResultados.get("p_cod_error") != null){
						codError = Long.parseLong(mapaResultados.get("p_cod_error").toString());
					}
				}
			}
			diasServicioSalida.setCodError(codError);
		}catch (Exception e) {
			//Si surge algún error hay que informarlo para mostrar en pantalla que no se han podido actualizar los datos de la tienda
			diasServicioSalida.setCodError(new Long(1));
		}		
	    return diasServicioSalida;
    }
    
    @Override
    public DiasServicio recargaDiasServicioOfer(final DiasServicio diasServicio, final String oferta) throws Exception  {
    	
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		DiasServicio diasServicioSalida = new DiasServicio();
		Long codError = new Long (0);
		
		declaredParameters.add(new SqlParameter("p_centro", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_ano_ofer", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_num_ofer", Types.INTEGER));
		declaredParameters.add(new SqlParameter("p_idsesion", Types.VARCHAR));
		declaredParameters.add(new SqlOutParameter("p_cod_error", Types.INTEGER));
		
		try{
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {
	
			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_MIS_PROCESOS.p_mis_cal_dias_servicio_ofer(?, ?, ?, ?, ?, ?, ?, ?)}");
	
			        stmnt.setInt(1, diasServicio.getCodCentro().intValue());
			        
			        String[] ofertaArray = oferta.split("-"); 
			        stmnt.setInt(2, Integer.parseInt(ofertaArray[0], 10)); //año oferta
			        stmnt.setInt(3, Integer.parseInt(ofertaArray[1], 10)); //num oferta
			        if (diasServicio.getIdsesion() != null){
			        	stmnt.setString(4, diasServicio.getIdsesion());	
			        }else{
			        	stmnt.setNull(4, OracleTypes.NULL);
			        }
			        if (diasServicio.getArea() != null){
			        	stmnt.setLong(5, diasServicio.getArea());	
			        }else{
			        	stmnt.setNull(5, OracleTypes.NULL);
			        }
			        if (diasServicio.getSeccion() != null){
			        	stmnt.setLong(6, diasServicio.getSeccion());	
			        }else{
			        	stmnt.setNull(6, OracleTypes.NULL);
			        }
			        if (diasServicio.getCategoria() != null){
			        	stmnt.setLong(7, diasServicio.getCategoria());	
			        }else{
			        	stmnt.setNull(7, OracleTypes.NULL);
			        }
			        stmnt.registerOutParameter(8, Types.INTEGER);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			

			
			diasServicioSalida.setCodCentro(diasServicio.getCodCentro());
			diasServicioSalida.setIdsesion(diasServicio.getIdsesion());
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error")!=null && "0".equals(mapaResultados.get("p_cod_error").toString())){
					if (mapaResultados.get("p_cod_error") != null){
						codError = Long.parseLong(mapaResultados.get("p_cod_error").toString());
					}
				}
			}
			diasServicioSalida.setCodError(codError);
		}catch (Exception e) {
			//Si surge algún error hay que informarlo para mostrar en pantalla que no se han podido actualizar los datos de la tienda
			diasServicioSalida.setCodError(new Long(1));
		}		
	    return diasServicioSalida;
    }

    @Override
    public DiasServicio recargaDiasServicioGrupo(final DiasServicio diasServicio) throws Exception  {
    	
		List<SqlParameter> declaredParameters = new ArrayList<SqlParameter>();
		DiasServicio diasServicioSalida = new DiasServicio();
		Long codError = new Long (0);

		declaredParameters.add(new SqlParameter("p_cod_centro_grupo", Types.INTEGER));
		declaredParameters.add(new SqlParameter("v_grupo_art", OracleTypes.STRUCT, "MIS_R_ARTICULO_REG"));
		declaredParameters.add(new SqlParameter("p_idsesion_grupo", Types.VARCHAR));
		declaredParameters.add(new SqlOutParameter("p_cod_error_grupo", Types.INTEGER));
		
		try{
			Map<String, Object> mapaResultados = this.jdbcTemplate.call(new CallableStatementCreator() {
	
			    @Override
				public
			    CallableStatement createCallableStatement(Connection con) throws SQLException {
			        CallableStatement stmnt = con.prepareCall("{call PK_MIS_PROCESOS.p_mis_cal_dias_servicio_grupo(?, ?, ?, ?)}");
	
			        stmnt.setInt(1, diasServicio.getCodCentro().intValue());
			        
			        //Crear estructura para recarga
                	STRUCT itemConsulta = crearEstructuraRecargaDiasServicioGrupo(diasServicio.getListaArticulosRecargaDiasServicio(), con);
                	stmnt.setObject(2, itemConsulta);
        	    	
			        if (diasServicio.getIdsesion() != null){
			        	stmnt.setString(3, diasServicio.getIdsesion());	
			        }else{
			        	stmnt.setNull(3, OracleTypes.NULL);
			        }
			        stmnt.registerOutParameter(4, Types.INTEGER);
			        
			        return stmnt;
			    }
			}, declaredParameters);
			
			diasServicioSalida.setCodCentro(diasServicio.getCodCentro());
			diasServicioSalida.setIdsesion(diasServicio.getIdsesion());
			if (mapaResultados != null){
				if (mapaResultados.get("p_cod_error_grupo")!=null && "0".equals(mapaResultados.get("p_cod_error_grupo").toString())){
					if (mapaResultados.get("p_cod_error_grupo") != null){
						codError = Long.parseLong(mapaResultados.get("p_cod_error_grupo").toString());
					}
				}
			}
			diasServicioSalida.setCodError(codError);
		}catch (Exception e) {
			//Si surge algún error hay que informarlo 
			diasServicioSalida.setCodError(new Long(1));
		}		
	    return diasServicioSalida;
    }

    @Override
	public String getPrimerDiaHabilitado(DiasServicio diasServicio, String idSession)  throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(" SELECT TO_CHAR(MIN(FECHA), 'DDMMYYYY') FROM dias_servicio ");
		//Se filtra por no festivos
		query.append(" WHERE D_FESTIVO = 'N' ");
		
		//Se filtra por la sesión
		query.append(" AND IDSESION = ? ");
		params.add(idSession);
		
    	if(diasServicio.getCodCentro()!=null){
    		query.append(" AND COD_CENTRO = ? ");
   		 	params.add(diasServicio.getCodCentro());	        		
	   	}
	   	if(diasServicio.getFecha()!=null){
	   		 //Si se proporciona fecha hay que devolver los registros de ese mes. Se suma 7 para asegurarnos que está dentro del mes a tratar.
	   		 //En el datepicker se tienen en cuenta días del mes anterior por lo que no empieza el día 1.
	   		 query.append(" AND FECHA >= TRUNC(?) ");
	       	 params.add(diasServicio.getFecha());
	   	}

		if (diasServicio  != null && diasServicio.getClasePedido() != null){
    		//En caso de proporcionar clase de pedido hay que comprobar si tiene también identificador para  no tener en cuenta los que tienen pedido
    		//En caso de modificación (identificador relleno) hay que poder seleccionar los que correspondan con el identificador
			
			if (diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_ENCARGO)) ||
					diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE)) ||
					diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL))){
				if (diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_ENCARGO))){
	        		query.append(" AND (D_ENCARGO IS NULL OR NOT (D_ENCARGO = 'S'");
	        	}
	        	if (diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE)) || 
	        			diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL))){
	        		query.append(" AND (D_MONTAJE IS NULL OR NOT (D_MONTAJE = 'S' ");
	        	}
	        	if (diasServicio.getIdentificador() == null || diasServicio.getIdentificador().equals("")){
	        		query.append(" OR (IDENTIFICADOR IS NOT NULL OR IDENTIFICADOR <> '') ");
	    		}else{
	    			query.append(" OR IDENTIFICADOR <> ? ");
	    			params.add(diasServicio.getIdentificador());	
	    		}
	        	if (diasServicio.getIdentificadorSIA() == null || diasServicio.getIdentificadorSIA().equals("")){
	        		query.append(" OR (IDENTIFICADOR_SIA IS NOT NULL OR IDENTIFICADOR_SIA <> '') ");
	    		}else{
	    			query.append(" OR IDENTIFICADOR_SIA <> ? ");
	    			params.add(diasServicio.getIdentificadorSIA());	
	    		}
	        	
	        	query.append(") ");
	        	if(null != diasServicio.getUuid()){
	        		 query.append(" OR UUID = ? ");
		        	 params.add(diasServicio.getUuid());	        		
	        	} 
	        	query.append(")");
			}

			//En caso de encargos no se puede seleccionar ningún día con bloqueo de encargos ni bloqueo de encargo y pilada
			if (diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_ENCARGO))){
				query.append(" AND (B_ENCARGO IS NULL OR B_ENCARGO = 'N') ");
				query.append(" AND (B_ENCARGO_PILADA IS NULL OR B_ENCARGO_PILADA = 'N') ");
				query.append(" AND (D_SERVICIO = 'X') ");
			}
			
			//En caso de montajes adicionales y siendo de alimentación no se pueden seleccionar con bloqueo de pilada
			if (diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE)) ||
			diasServicio.getClasePedido().equals(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL))){

				//Comprobación de si el artículo es fresco o de alimentación
				VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
				vDatosDiarioArt.setCodArt(diasServicio.getCodArt());
				VDatosDiarioArt vDatosAux = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
				if (null != vDatosAux && !(new Long(1)).equals(vDatosAux.getGrupo1())){
					query.append(" AND (B_ENCARGO_PILADA IS NULL OR B_ENCARGO_PILADA = 'N') ");
				}else{
					query.append(" AND (B_ENCARGO IS NULL OR B_ENCARGO = 'N') AND (B_ENCARGO_PILADA IS NULL OR B_ENCARGO_PILADA = 'N') ");
				}
			}
        }

		String primerDiaHabilitado = null;
		try {
			primerDiaHabilitado =  this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		} catch (Exception e){	
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return primerDiaHabilitado;
	}
    
	private STRUCT crearEstructuraRecargaDiasServicioGrupo(List<Long> listaArticulosRecarga, Connection con) throws SQLException {

		//Transformación de conexión a conexión de oracle. Necesari para definición del descriptor
		OracleConnection conexionOracle = (OracleConnection) con.getMetaData().getConnection();

		int numeroElementos = listaArticulosRecarga.size();
		Object[] objectTabla = new Object[numeroElementos];
		
		for (int i=0; i<numeroElementos; i++){
			
			Long codigoArticulo = (Long)listaArticulosRecarga.get(i);
        	
        	Object[] objectInfo = new Object[1];
        	
        	//Sólo se informan los datos necesarios
        	objectInfo[0] = (codigoArticulo!=null?new BigDecimal(codigoArticulo):null);
			//TABLA mis_t_articulo_dat 
			//STRUCT mis_r_articulo_reg toda la estructura
			//STRUCT mis_r_articulo_dat registro 
			
			StructDescriptor itemDescriptor = StructDescriptor.createDescriptor("MIS_R_ARTICULO_DAT",conexionOracle);
	    	STRUCT itemObjectStruct = new STRUCT(itemDescriptor,conexionOracle,objectInfo);
	    	
	    	objectTabla[i] = itemObjectStruct;
	    	
		}
	    
		Object[] objectConsulta = new Object[1]; //Tiene 1 campo con la lista de códigos de artículos
		ArrayDescriptor desc = new ArrayDescriptor("MIS_T_ARTICULO_DAT", conexionOracle);
	    ARRAY array = new ARRAY(desc, conexionOracle, objectTabla);
    	
    	objectConsulta[0] = array;
    	
    	StructDescriptor itemDescriptorConsulta = StructDescriptor.createDescriptor("MIS_R_ARTICULO_REG",conexionOracle);
    	STRUCT itemConsulta = new STRUCT(itemDescriptorConsulta,conexionOracle,objectConsulta);
    	
    	return itemConsulta;
    }
	
	@Override
	public void updateDiasEncargoCliente(DiasServicio diasServicio, List<Date> fechasVenta) throws Exception{
		
		StringBuffer sql = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		sql.append("UPDATE  DIAS_SERVICIO  SET D_ENCARGO_CLIENTE = ? ")
		.append(" WHERE COD_CENTRO = ? AND IDSESION = ? AND COD_ART = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(diasServicio.getdEncargoCliente());
		params.add(diasServicio.getCodCentro());
		params.add(diasServicio.getIdsesion()+"_"+diasServicio.getCodArt());
		params.add(diasServicio.getCodArt());
		sql.append(" AND FECHA IN ( ");
		boolean primero = true;
		for(Date fecha : fechasVenta){
			if (primero){
				primero = false;
			} else {
				sql.append(", ");
			}
			sql.append("TRUNC(?)");
			params.add(fecha);
		}
		sql.append(" )");
	        
		jdbcTemplate.update(sql.toString(), params.toArray());
		
		 try {
			 jdbcTemplate.update(sql.toString(), params.toArray());
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params ,e);
		}

		
	}
	
	@Override
	public FechaEntregaDiasServicio cargarDiasServicioDServicio( Long centro,  Long referencia) throws Exception{
		
		FechaEntregaDiasServicio salida = null;
    	//Obtención de parámetros de consulta
    	final Long p_cod_art_formlog = referencia;
    	final Long p_cod_loc = centro;
    	
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

	            @Override
	            public CallableStatement createCallableStatement(Connection con) {
	                CallableStatement cs = null;
	                try {

	                	cs = con.prepareCall("{call PK_APR_ENC_RESERVAS_MISUMI.P_APR_OBT_FEC_ENTREGA(?, ?, ?, ?, ?, ?) }");
	                    
	                    cs.setLong(1, p_cod_loc);
	                    cs.setLong(2, p_cod_art_formlog);
	                    cs.setString(3, null);
	                    cs.registerOutParameter(3, Types.VARCHAR);
	                    //cs.registerOutParameter(4, OracleTypes.STRUCT, "APR_R_FECHA_VENTA_DAT");
	                    cs.registerOutParameter(4, OracleTypes.STRUCT, "APR_R_FECHA_VENTA_REG");
	                    //cs.registerOutParameter(4, OracleTypes.STRUCT, "apr_r_fecha_venta_reg");
						cs.registerOutParameter(5,Types.INTEGER);
						cs.registerOutParameter(6,Types.VARCHAR);

	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return cs;
	            }
	        };
	        CallableStatementCallback csCallback = new CallableStatementCallback() {

	            public Object doInCallableStatement(CallableStatement cs) {
	            	FechaEntregaDiasServicio ret = null;
	                ResultSet rs = null;
	                try {
		                cs.execute();
	                    ret = obtenerFechasServicio(cs, rs);
	                } catch (SQLException e) {
	                	e.printStackTrace();                
	                }
	                return ret;
	            }
	        };
	        
	        try {
	        	salida = (FechaEntregaDiasServicio) this.jdbcTemplate2.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}
	        // CON LAS FECHAS DEVUELTAS Y CON EL CENTRO Y REFERENCIA HAY Q  LLAMAR AL MÉTODO DE ACTUALIZACIÓN DE LA TABLA DIAS_SERVICIO
		}catch (Exception e) {
			e.printStackTrace();
		}
		return salida;
    }
	
	private FechaEntregaDiasServicio obtenerFechasServicio(CallableStatement cs, ResultSet rs) throws SQLException {
		FechaEntregaDiasServicio fechasEntregaList = null;
		List<Date> listaFechasEntregaDiasServicio = new ArrayList<Date>();
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(5);
		String descError_BD = (String)cs.getString(6);

		//Transformación de datos para estructura de CalendarioDescripcionServicios
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		//Control de error en la obtención de datos
		if (new BigDecimal("0").equals(codError_BD)){ //El proceso se ha ejecutado correctamente
			STRUCT estructuraResultado = (STRUCT)cs.getObject(4);
			Object[] objectInfo = estructuraResultado.getAttributes();

			//Obtenemos el array del objeto
			ARRAY calendarioServiciosLst = (ARRAY)objectInfo[0];
			//Obtención de los datos de salida
			if (calendarioServiciosLst!=null){
				rs = calendarioServiciosLst.getResultSet();
				//Recorrido del listado de datos
				while (rs.next()) {
					STRUCT estructuraCalendarioServicios = (STRUCT)rs.getObject(2);
					//Obtener datos de CalendarioDescripcionServicio en crudo
					Object[] objectInfo2 = estructuraCalendarioServicios.getAttributes();
					Date fechaVenta = (Date)objectInfo2[0];
					listaFechasEntregaDiasServicio.add(fechaVenta);
				}
			}            		
			fechasEntregaList=new FechaEntregaDiasServicio(codError, descError, listaFechasEntregaDiasServicio);

		}
		return fechasEntregaList;
	}
	
	
	public void actualizarDiasServicio(Long centro, Long referencia, FechaEntregaDiasServicio fechasServicio, Long codFpMadre, String idSession) throws Exception{
		StringBuffer sql = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		sql.append("UPDATE dias_servicio "
				 + "SET d_servicio = ? "
				 + "WHERE idsesion = ? "
				 + "AND cod_centro = ? "
				 + "AND cod_art = ? "
				 );
		List<Object> params = new ArrayList<Object>();
		try {
			params.add("X");
			params.add(idSession+"_"+codFpMadre);
			params.add(centro);
			params.add(referencia);
			
			if(fechasServicio!=null && fechasServicio.getpCodError() == 0){
				List<Date> fechasServicioList=fechasServicio.getListFechasServicio();
				sql.append(" AND fecha IN (");
				boolean primero = true;
				for(Date fecha : fechasServicioList){
					if (primero){
						primero = false;
					} else {
						sql.append(", ");
					}
					sql.append("TRUNC(?)");
					params.add(fecha);
				}
				sql.append(")");
				jdbcTemplate.update(sql.toString(), params.toArray());	
			}
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params ,e);
		}
	}
}
