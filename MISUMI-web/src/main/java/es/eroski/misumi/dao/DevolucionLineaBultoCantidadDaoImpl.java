package es.eroski.misumi.dao;

import java.math.BigDecimal;	
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DevolucionLineaBultoCantidadDao;
import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
import oracle.jdbc.OracleTypes;

@Repository
public class DevolucionLineaBultoCantidadDaoImpl extends CentroParametrizadoDaoImpl implements DevolucionLineaBultoCantidadDao{

//	private static Logger logger = Logger.getLogger(DevolucionDaoImpl.class);

	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplate2;
	private static final int POSICION_PARAMETRO_SALIDA_CAMBIAR_ESTADO_BULTO_CODERR = 6;
	private static final int POSICION_PARAMETRO_SALIDA_CAMBIAR_ESTADO_BULTO_MSGERR = 7;

	private static Logger logger = Logger.getLogger(DevolucionLineaBultoCantidadDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 
	
	@Autowired
	public void setDataSource2(DataSource dataSourceSIA) {
		this.jdbcTemplate2 = new JdbcTemplate(dataSourceSIA);
	}
	
	private RowMapper<TDevolucionBulto> rwBultoCantidadLineaDevolucion = new RowMapper<TDevolucionBulto>() {
		public TDevolucionBulto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new TDevolucionBulto(
					resultSet.getString("IDSESION"),
					resultSet.getDate("FECHA_GEN"),
					resultSet.getLong("DEVOLUCION"),
					resultSet.getLong("BULTO"),
					resultSet.getLong("COD_ARTICULO"),
					resultSet.getLong("BULTO_ORI"),
					resultSet.getDouble("STOCK_DEVUELTO"),
					resultSet.getDouble("STOCK_DEVUELTO_ORI"),
					resultSet.getString("ESTADO"),
					resultSet.getLong("COD_ERROR"),
					resultSet.getString("DESC_ERROR"),
					resultSet.getDate("CREATION_DATE"));
		}
	};

	@Override
	public void insertAll(final List<TDevolucionBulto> listaTDevolucionLineaBultoCantidad) throws Exception {
		//Esto es infinitamente más rápido que hacerlo en bucle. ¡Viva el batchUpdate!
		String query = " INSERT INTO t_mis_devoluciones_bulto "
						+ "( idsesion, fecha_gen, devolucion, bulto, cod_articulo"
						+ ", bulto_ori, stock_devuelto, stock_devuelto_ori, estado"
						+ ", cod_error, desc_error, creation_date"
						+ ")"
					 + " VALUES "
					 	+ "(?, ?, ?, ?, ?"
					 	+ ", ?, ?, ?, ?"
					 	+ ", ?, ?, ?"
					 	+ ")";
		
		if (listaTDevolucionLineaBultoCantidad != null && listaTDevolucionLineaBultoCantidad.size()>0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					TDevolucionBulto myPojo = listaTDevolucionLineaBultoCantidad.get(i);
					ps.setString(1, myPojo.getIdSesion());
					
					if (null != myPojo.getFechaGen()){
						ps.setDate(2, new java.sql.Date(myPojo.getFechaGen().getTime()));
					} else {
						ps.setNull(2, Types.DATE);
					}
					
					if (null != myPojo.getDevolucion()){
						ps.setLong(3, myPojo.getDevolucion());
					} else {
						ps.setNull(3, Types.NUMERIC);
					}
					
					if (null != myPojo.getBulto()){
						ps.setLong(4, myPojo.getBulto());
					} else {
						ps.setNull(4, Types.NUMERIC);
					}
					
					if (null != myPojo.getCodArticulo()){
						ps.setLong(5, myPojo.getCodArticulo());
					} else {
						ps.setNull(5, Types.NUMERIC);
					}
					
					if (null != myPojo.getBultoOri()){
						ps.setLong(6, myPojo.getBultoOri());
					} else {
						ps.setNull(6, Types.NUMERIC);
					}
					
					if (null != myPojo.getStock()){
						ps.setDouble(7, myPojo.getStock());
					} else {
						ps.setNull(7, Types.NUMERIC);
					}
					
					if (null != myPojo.getStockOri()){
						ps.setDouble(8, myPojo.getStockOri());
					} else {
						ps.setNull(8, Types.NUMERIC);
					}
					ps.setString(9, myPojo.getEstadoCerrado());
					
					if (null != myPojo.getCodError()){
						ps.setLong(10, myPojo.getCodError());
					} else {
						ps.setNull(10, Types.NUMERIC);
					}
					
					ps.setString(11, myPojo.getDescError());
					
					if (null != myPojo.getCreationDate()){
						ps.setDate(12, new java.sql.Date(myPojo.getCreationDate().getTime()));
					} else {
						ps.setNull(12, Types.DATE);
					}
					
				}

				@Override
				public int getBatchSize() {
					return listaTDevolucionLineaBultoCantidad.size();
				}
			});
		}
	}

	@Override
	public void deleteLineasTablaBultoCantidad(String session, Long devolucion, Long codArticulo) throws Exception{
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE 1=1");

		StringBuffer query = new StringBuffer("DELETE FROM t_mis_devoluciones_bulto ");

		where.append(" AND idsesion = ? ");
		params.add(session);
		where.append(" AND devolucion = ? ");
		params.add(devolucion);
		where.append(" AND cod_articulo = ? ");
		params.add(codArticulo);
		query.append(where);
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){				
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
	
	@Override
	public List<TDevolucionLinea> cargarBultoCantidadLinea(String session, List<TDevolucionLinea> listTDevolucionLinea) throws Exception{

		List<TDevolucionLinea> listTDevolucionLineaReturn=new ArrayList<TDevolucionLinea>();
		
		for (TDevolucionLinea tDevolucionLinea:listTDevolucionLinea){
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer("SELECT * " 
												+ "FROM t_mis_devoluciones_bulto "
												);

			where.append("WHERE idsesion = ? ");
			params.add(session);
			where.append("AND devolucion = ? ");
			params.add(tDevolucionLinea.getDevolucion());
			where.append("AND cod_articulo = ? ");
			params.add(tDevolucionLinea.getCodArticulo());
			where.append("ORDER BY bulto");

			query.append(where);
			List<TDevolucionBulto> lista = null;
			try{
				lista = (List<TDevolucionBulto>) this.jdbcTemplate.query(query.toString(),this.rwBultoCantidadLineaDevolucion, params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
			if (lista.size()>1){
				tDevolucionLinea.setVariosBultos(true);
				Double resultadoCantidad=new Double("0"); 
				String variosBultos="";
				String bultoEstadoCerrado="";
				for(TDevolucionBulto devBulto:lista){
					resultadoCantidad=resultadoCantidad+devBulto.getStock();
					variosBultos=variosBultos+devBulto.getBulto().toString()+",";
					bultoEstadoCerrado=bultoEstadoCerrado+devBulto.getBulto().toString()+"-"+devBulto.getEstadoCerrado()+"*";
				}
				tDevolucionLinea.setStockDevuelto(resultadoCantidad);
				tDevolucionLinea.setBultoStr(variosBultos.substring(0, variosBultos.length()-1));
				tDevolucionLinea.setEstadoCerrado("N");
				tDevolucionLinea.setBultoEstadoCerrado(bultoEstadoCerrado.substring(0, bultoEstadoCerrado.length()-1));
			}else if(lista.size()==1){
				tDevolucionLinea.setBulto(lista.get(0).getBulto());
				tDevolucionLinea.setStockDevuelto(lista.get(0).getStock());
				tDevolucionLinea.setEstadoCerrado(lista.get(0).getEstadoCerrado());
				tDevolucionLinea.setBultoEstadoCerrado(lista.get(0).getBulto()+"-"+lista.get(0).getEstadoCerrado());
				tDevolucionLinea.setVariosBultos(false);
			}else{
				tDevolucionLinea.setBulto(null);
				tDevolucionLinea.setStockDevuelto(null);
				tDevolucionLinea.setEstadoCerrado("N");
				tDevolucionLinea.setBultoEstadoCerrado("N");
				tDevolucionLinea.setVariosBultos(false);
			}

			tDevolucionLinea.setListTDevolucionLinea(lista);
			listTDevolucionLineaReturn.add(tDevolucionLinea);
		}
		return listTDevolucionLineaReturn;
	}
	
	@Override
	public void delete(TDevolucionLinea tDevolucionLinea) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM t_mis_devoluciones_bulto WHERE idsesion = ? ");
		params.add(tDevolucionLinea.getIdSesion());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
	
	@Override
	public void deleteHistorico() throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM t_mis_devoluciones_bulto WHERE fecha_gen < (SYSDATE - ?) ");
		params.add(Constantes.DIAS_ELIMINAR);
		
		try{
			//this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
	
	public List<TDevolucionBulto> findDatosRef(String session, Long devolucion, Long codArticulo) throws Exception{
		List<TDevolucionBulto> lista = null;
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT * " 
											+ "FROM t_mis_devoluciones_bulto "
											 );
		where.append("WHERE 1=1 ");
		where.append("AND idsesion = ? ");
		params.add(session);
		where.append("AND devolucion = ? ");
		params.add(devolucion);
		where.append("AND cod_articulo = ? ");
		params.add(codArticulo);
		query.append(where);
		try {
			lista = (List<TDevolucionBulto>) this.jdbcTemplate.query(query.toString(),this.rwBultoCantidadLineaDevolucion, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}
	
	@Override
	public List<DevolucionLinea> cargarBultoCantidadLineaEditada(String session, List<DevolucionLinea> listTDevolucionLinea) throws Exception{
		List<DevolucionLinea> listDevolucionLineaReturn=new ArrayList<DevolucionLinea>();
		for(DevolucionLinea tDevolucionLinea:listTDevolucionLinea){
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			List<Object> params = new ArrayList<Object>();
			where.append("WHERE 1=1 ");

			StringBuffer query = new StringBuffer("SELECT * " 
												+ "FROM t_mis_devoluciones_bulto ");

			where.append("AND idsesion = ? ");
			params.add(session);
			where.append("AND devolucion = ? ");
			//params.add(tDevolucionLinea.getDevolucion());
			where.append("AND cod_articulo = ? ");
			params.add(tDevolucionLinea.getCodArticulo());
			where.append("ORDER BY bulto");

			query.append(where);
			List<TDevolucionBulto> lista = null;
			try{
				lista =  (List<TDevolucionBulto>) this.jdbcTemplate.query(query.toString(),this.rwBultoCantidadLineaDevolucion, params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			//tDevolucionLinea.setListTDevolucionLinea(lista);
			if(lista.size()>1){
				tDevolucionLinea.setVariosBultos(true);
				Double resultadoCantidad=new Double("0"); 
				String variosBultos="";
				for(TDevolucionBulto devBulto:lista){
					resultadoCantidad=resultadoCantidad+devBulto.getStock();
					variosBultos=variosBultos+devBulto.getBulto().toString()+",";
				}
				tDevolucionLinea.setStockDevuelto(resultadoCantidad.doubleValue());
				//tDevolucionLinea.setBultoStr(variosBultos.substring(0, variosBultos.length()-1));
			}else{
				tDevolucionLinea.setVariosBultos(false);
			}
			
			listDevolucionLineaReturn.add(tDevolucionLinea);
		}
		return listDevolucionLineaReturn;
	}
	
	@Override
	public String cargarListaBultos(String session, TDevolucionLinea tDevolucionLinea) throws Exception{
		List<Object> params = new ArrayList<Object>();
		String bultos="";
		StringBuffer query = new StringBuffer("SELECT * "
											+ "FROM (SELECT * "
												  + "FROM (SELECT ROWNUM x "
												  		+ "FROM DUAL CONNECT BY LEVEL <= 20"
												  		+ ") x "
												  + "WHERE x.x > 0 "
												  + "AND NOT EXISTS ( SELECT 'X' "
												  				   + "FROM t_mis_devoluciones_bulto b, t_devoluciones d "
												  				   + "WHERE	d.idsesion   = b.idsesion "
												  				   + "AND d.cod_articulo = b.cod_articulo "
												  				   + "AND d.devolucion   = b.devolucion "
												  				   + "AND b.estado 		 = 'S' "
												  				   + "AND b.idsesion	 = ? "
												  				   + "AND b.devolucion	 = ? "
												  				   + "AND d.provr_gen	 = ? "
												  				   + "AND b.bulto 		 = x.x"
												  				   + ") "
												  + "ORDER BY 1 "
												  + ") "
											+ "WHERE ROWNUM < 21"
											);
		
		params.add(session);
		params.add(tDevolucionLinea.getDevolucion());
		params.add(tDevolucionLinea.getProvrGen());
		
		List<Long> lista = null;
        try {
        	lista =  this.jdbcTemplate.queryForList(query.toString(), Long.class, params.toArray());
            for(int i=0;i<lista.size();i++){
            	Long bulto=lista.get(i);
            	bultos+=bulto.toString()+",";
            }
    		bultos=bultos.substring(0,bultos.length()-1);
	    } catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

        return bultos;
	}
	
	@Override
	public Long existenBultosPorbultoProveedor(String session,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COUNT(1) "
											+ "FROM t_mis_devoluciones_bulto t "
											+ "WHERE t.idsesion = ? "
											+ "AND t.bulto = ? "
											+ "AND t.devolucion = ? "
											+ "AND cod_articulo IN (SELECT cod_articulo "
																 + "FROM t_devoluciones t2 " 
																 + "WHERE t2.idsesion = t.idsesion "
																 + "AND t2.devolucion = t.devolucion "
																 + "AND t2.provr_gen = ?"
																 + ") "
											);
		
		params.add(session);
		params.add(bulto);
		params.add(devolucion);
		params.add(proveedor);
		
		Long bultosPorProveedor = 0L;
        try {
        	bultosPorProveedor = jdbcTemplate.queryForObject(query.toString(), params.toArray(),  Long.class);
	    } catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return bultosPorProveedor;
	}
	
	@Override
	public void actualizarEstadoBultoPorProveedor(String session,String estado,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("UPDATE t_mis_devoluciones_bulto t "
											+ "SET estado = ? "
											+ "WHERE t.idsesion = ? "
											+ "AND t.bulto = ? "
											+ "AND t.devolucion = ? "
											+ "AND cod_articulo IN (SELECT cod_articulo FROM t_devoluciones t2 " 
																 +" WHERE t2.idsesion = t.idsesion "
																 + "AND t2.devolucion = t.devolucion "
																 + "AND t2.provr_gen = ? "
																 + "AND t2.provr_trabajo = NVL(?, t2.provr_trabajo)"
																 + ") "
											+ ""
											);
		params.add(estado);
		params.add(session);
		params.add(bulto);
		params.add(devolucion);
		params.add(proveedor);
		params.add(proveedorTrabajo);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
	
	@Override
	public BultoCantidad procedimientoActualizarEstadoBultoPorProveedor(final String estado,final Long devolucion, final Long proveedor, final Long proveedorTrabajo, final Long bulto)throws Exception{
		BultoCantidad bultoCantidad = new BultoCantidad();
		
		try{
			CallableStatementCreator csCreator = new CallableStatementCreator() {

				@Override
				public CallableStatement createCallableStatement(Connection con) {
					CallableStatement cs = null;
					try {

						cs = con.prepareCall("{call PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_act_devol_prov_bulto(?, ?, ?, ?, ?, ?, ?) }");

						cs.setInt(1, devolucion.intValue());
						cs.setInt(2, proveedor.intValue());
						if (proveedorTrabajo != null){
							cs.setLong(3, proveedorTrabajo);
						}else{
							cs.setNull(3, OracleTypes.INTEGER);
						}
						cs.setInt(4, bulto.intValue());
						cs.setString(5, estado);
						
						cs.registerOutParameter(6, OracleTypes.INTEGER);
						cs.registerOutParameter(7, OracleTypes.VARCHAR);

					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return cs;
				}
			};
			CallableStatementCallback csCallback = new CallableStatementCallback() {

				public Object doInCallableStatement(CallableStatement cs) {
					BultoCantidad bulto = null;
					try {
						cs.execute();
						bulto = obtenerResultadoPendienteRecibir(cs,POSICION_PARAMETRO_SALIDA_CAMBIAR_ESTADO_BULTO_CODERR,POSICION_PARAMETRO_SALIDA_CAMBIAR_ESTADO_BULTO_MSGERR);
					} catch (SQLException e) {
						e.printStackTrace();                
					}
					return bulto;
				}
			};

			try {
				bultoCantidad = (BultoCantidad) this.jdbcTemplate2.execute(csCreator,csCallback);
			} catch (Exception e) {
				logger.error("#####################################################");
				logger.error( StackTraceManager.getStackTrace(e));
				logger.error("#####################################################");
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return bultoCantidad;
	}

	@Override
	public List<BultoCantidad> cargarListaBultos(String session, String devolucion, String provrGen, String provrTrabajo) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT DISTINCT b.bulto, b.estado "
											+ "FROM t_devoluciones d, t_mis_devoluciones_bulto b "
											+ "WHERE d.idsesion 	= b.idsesion "
											+ "AND d.cod_articulo 	= b.cod_articulo "
											+ "AND d.devolucion 	= b.devolucion "
					  						+ "AND b.idsesion		= ? "
					  						+ "AND b.devolucion		= ? "
					  						+ "AND d.provr_gen		= ? "
					  						+ "AND d.provr_trabajo	= NVL(?, d.provr_trabajo) "
					  						+ "AND b.bulto 			> 0 "
//					  						+ "AND d.provr_trabajo	= ? "
					  						+ "ORDER BY 1"
											);
		
		params.add(session);
		params.add(devolucion);
		params.add(provrGen);
		params.add(provrTrabajo);
		
		List<BultoCantidad> lista = null;
        
		try {
			lista = (List<BultoCantidad>) this.jdbcTemplate.query(query.toString(), this.rwDetBultos, params.toArray());
	    } catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
	}
	
	private BultoCantidad obtenerResultadoPendienteRecibir(CallableStatement cs, int idParametroResultado1, int idParametroResultado2) throws SQLException {
		
		//Obtención de los parámetros de salida en crudo
		BigDecimal codError_BD = (BigDecimal)cs.getBigDecimal(idParametroResultado1);
		String descError_BD = (String)cs.getString(idParametroResultado2);
		
		Long codError = ((codError_BD != null && !("".equals(codError_BD.toString())))?new Long(codError_BD.toString()):null);
		String descError = descError_BD;
		
//		DevolucionTipo res = new DevolucionTipo(codError,descError);
		BultoCantidad bulto = new BultoCantidad();
		bulto.setCodError(codError);
		bulto.setDescError(descError);
		
		return bulto;
	}

	private RowMapper<BultoCantidad> rwDetBultos = new RowMapper<BultoCantidad>() {
		public BultoCantidad mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			BultoCantidad bulto = new BultoCantidad();
			
			bulto.setBulto(resultSet.getLong("bulto"));
			bulto.setEstadoCerrado(resultSet.getString("estado"));
			
			return bulto;
		}

	};

	@Override
	public boolean deleteBultoPorProvDev(String session, String devolucion, String provrGen, String provrTrabajo, String bulto)
			throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		StringBuffer query = new StringBuffer("DELETE FROM t_mis_devoluciones_bulto deb ");

		where.append("WHERE EXISTS (SELECT 1 "
				   				 + "FROM t_devoluciones dev "
				   				 + "WHERE dev.idsesion    = deb.idsesion "
				   				 + "AND dev.devolucion    = deb.devolucion "
				   				 + "AND dev.cod_articulo  = deb.cod_articulo "
				   				 + "AND dev.provr_gen     = ? "
				   				 + "AND dev.provr_trabajo = NVL(?, provr_trabajo)"
				   				 + ") "
				    );
		params.add(provrGen);
		params.add(provrTrabajo);
		where.append("AND deb.idsesion = ? ");
		params.add(session);
		where.append("AND deb.devolucion = ? ");
		params.add(devolucion);
		where.append("AND deb.bulto = ?");
		params.add(bulto);
		query.append(where);
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
			return true;
		} catch (Exception e){				
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return false;
	}

	
}
