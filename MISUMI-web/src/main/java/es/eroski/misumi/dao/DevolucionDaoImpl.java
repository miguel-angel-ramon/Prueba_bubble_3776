package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.DevolucionDao;
import es.eroski.misumi.model.Devolucion;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.DevolucionLineaModificada;
import es.eroski.misumi.model.OptionSelectBean;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class DevolucionDaoImpl implements DevolucionDao{

//	private static Logger logger = Logger.getLogger(DevolucionDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private RowMapper<OptionSelectBean> rwProveedorLineaDevolucion = new RowMapper<OptionSelectBean>() {
		public OptionSelectBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new OptionSelectBean(
					resultSet.getString("CODIGO"),
					resultSet.getString("DESCRIPCION"));
		}
	};
	
	private RowMapper<TDevolucionLinea> rwTDevolucionContadorPorBultoPDF = new RowMapper<TDevolucionLinea>() {
		public TDevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TDevolucionLinea tDevolucionLinea = new TDevolucionLinea();
			tDevolucionLinea.setContador(resultSet.getLong("CONTADOR"));
			tDevolucionLinea.setBulto(resultSet.getLong("BULTO"));

			return tDevolucionLinea;

		}
	};

	private RowMapper<TDevolucionLinea> rwTDevolucionLinea = new RowMapper<TDevolucionLinea>() {
		public TDevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TDevolucionLinea tdevolucionLinea = new TDevolucionLinea(resultSet.getString("IDSESION"),
					resultSet.getDate("CREATION_DATE"),
					resultSet.getLong("DEVOLUCION"),
					resultSet.getLong("COD_ARTICULO"),
					resultSet.getString("DENOMINACION"),
					resultSet.getString("MARCA"),
					resultSet.getString("SECCION"),
					resultSet.getLong("PROVR_GEN"),
					resultSet.getLong("PROVR_TRABAJO"),
					resultSet.getString("DENOM_PROVEEDOR"),
					resultSet.getString("FAMILIA"),
					resultSet.getString("FORMATO_DEVUELTO"),
					resultSet.getDouble("FORMATO"),
					resultSet.getString("TIPO_FORMATO"),
					resultSet.getString("PASILLO"),
					resultSet.getString("ESTRUCTURA_COMERCIAL"),
					resultSet.getDouble("UC"),
					resultSet.getDouble("STOCK_ACTUAL"),
					resultSet.getDouble("STOCK_EN_TIENDA"),
					resultSet.getDouble("STOCK_DEVOLVER"),		    									    							 	
					resultSet.getBigDecimal("STOCK_DEVUELTO") == null ? null:resultSet.getDouble("STOCK_DEVUELTO"),
					resultSet.getBigDecimal("STOCK_DEVUELTO_ORI") == null ? null:resultSet.getDouble("STOCK_DEVUELTO_ORI"),
					resultSet.getDouble("CANT_ABONADA"),
					resultSet.getString("FLG_CONTINUIDAD"),
					resultSet.getString("LOTE"),
					resultSet.getString("N_LOTE"),
					resultSet.getString("CADUCIDAD"),
					resultSet.getString("N_CADUCIDAD"),
					resultSet.getString("DESC_ABONO_ERROR"),
					resultSet.getBigDecimal("BULTO") == null ? null:resultSet.getLong("BULTO"),
					resultSet.getBigDecimal("BULTO_ORI") == null ? null:resultSet.getLong("BULTO_ORI"),
					resultSet.getString("UBICACION"),
					resultSet.getString("TIPO_REFERENCIA"),
					resultSet.getLong("ESTADO_LIN"),
					resultSet.getLong("COD_ERROR"),
					resultSet.getString("DESC_ERROR"),
					resultSet.getString("FLG_BANDEJAS"),
					resultSet.getString("FLG_PESO_VARIABLE"),
					resultSet.getLong("STOCK_DEVUELTO_BANDEJAS"),
					resultSet.getLong("STOCK_DEVUELTO_BANDEJAS_ORI"),
					resultSet.getLong("COD_TP_C_A"),
					resultSet.getLong("CANTIDAD_MAXIMA"));
			
			tdevolucionLinea.setDescrTalla(resultSet.getString("DESCR_TALLA"));
			tdevolucionLinea.setDescrColor(resultSet.getString("DESCR_COLOR"));
			tdevolucionLinea.setModeloProveedor(resultSet.getString("MODELO_PROVEEDOR"));
			tdevolucionLinea.setCosteUnitario(resultSet.getDouble("COSTE_UNITARIO"));
			tdevolucionLinea.setCosteFinal(resultSet.getDouble("COSTE_FINAL"));
			tdevolucionLinea.setArea(resultSet.getLong("AREA"));
			tdevolucionLinea.setModelo(resultSet.getString("MODELO"));
			tdevolucionLinea.setCantidadMaximaLin(resultSet.getBigDecimal("CANTIDAD_MAXIMA_LIN") == null ? null:resultSet.getDouble("CANTIDAD_MAXIMA_LIN"));
			
			return tdevolucionLinea;
		}
	};

	private RowMapper<DevolucionLinea> rwTDevolucionLineaEditadas = new RowMapper<DevolucionLinea>() {
		public DevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DevolucionLinea devolucionLinea = new DevolucionLinea();
			
			devolucionLinea.setCodArticulo(resultSet.getLong("COD_ARTICULO"));
			devolucionLinea.setDenominacion(resultSet.getString("DENOMINACION"));
			devolucionLinea.setMarca(resultSet.getString("MARCA"));
			devolucionLinea.setSeccion(resultSet.getString("SECCION"));
			devolucionLinea.setProvrGen(resultSet.getLong("PROVR_GEN"));
			devolucionLinea.setProvrTrabajo(resultSet.getLong("PROVR_TRABAJO"));
			devolucionLinea.setDenomProveedor(resultSet.getString("DENOM_PROVEEDOR"));
			devolucionLinea.setFamilia(resultSet.getString("FAMILIA"));
			devolucionLinea.setFormatoDevuelto(resultSet.getString("FORMATO_DEVUELTO"));
			devolucionLinea.setFormato(resultSet.getDouble("FORMATO"));
			devolucionLinea.setTipoFormato(resultSet.getString("TIPO_FORMATO"));
			devolucionLinea.setPasillo(resultSet.getString("PASILLO"));
			devolucionLinea.setEstructuraComercial(resultSet.getString("ESTRUCTURA_COMERCIAL"));
			devolucionLinea.setUc(resultSet.getDouble("UC"));
			devolucionLinea.setStockActual(resultSet.getDouble("STOCK_ACTUAL"));
			devolucionLinea.setStockTienda(resultSet.getDouble("STOCK_EN_TIENDA"));
			devolucionLinea.setStockDevolver(resultSet.getDouble("STOCK_DEVOLVER"));
			devolucionLinea.setStockDevuelto(resultSet.getBigDecimal("STOCK_DEVUELTO") == null ? null:resultSet.getDouble("STOCK_DEVUELTO"));
			devolucionLinea.setCantAbonada(resultSet.getDouble("CANT_ABONADA"));
			devolucionLinea.setFlgContinuidad(resultSet.getString("FLG_CONTINUIDAD"));
			devolucionLinea.setLote(resultSet.getString("LOTE"));
			devolucionLinea.setnLote(resultSet.getString("N_LOTE"));
			devolucionLinea.setCaducidad(resultSet.getString("CADUCIDAD"));
			devolucionLinea.setnCaducidad(resultSet.getString("N_CADUCIDAD"));
			devolucionLinea.setDescAbonoError(resultSet.getString("DESC_ABONO_ERROR"));
			devolucionLinea.setBulto(resultSet.getBigDecimal("BULTO") == null ? null:resultSet.getLong("BULTO"));
			devolucionLinea.setUbicacion(resultSet.getString("UBICACION"));
			devolucionLinea.setTipoReferencia(resultSet.getString("TIPO_REFERENCIA"));
			devolucionLinea.setEstadoLin(resultSet.getLong("ESTADO_LIN"));
			devolucionLinea.setCodError(new Long("0"));
			devolucionLinea.setDescError(resultSet.getString("DESC_ERROR"));
			devolucionLinea.setFlgBandejas(resultSet.getString("FLG_BANDEJAS"));
			devolucionLinea.setFlgPesoVariable(resultSet.getString("FLG_PESO_VARIABLE"));
			devolucionLinea.setStockDevueltoBandejas(resultSet.getLong("STOCK_DEVUELTO_BANDEJAS"));
			devolucionLinea.setStockDevueltoBandejasOrig(resultSet.getLong("STOCK_DEVUELTO_BANDEJAS_ORI"));
			devolucionLinea.setDescrTalla(resultSet.getString("DESCR_TALLA"));
			devolucionLinea.setDescrColor(resultSet.getString("DESCR_COLOR"));
			devolucionLinea.setModeloProveedor(resultSet.getString("MODELO_PROVEEDOR"));
			devolucionLinea.setModelo(resultSet.getString("MODELO"));
			devolucionLinea.setArea(resultSet.getLong("AREA"));
			devolucionLinea.setCosteUnitario(resultSet.getBigDecimal("COSTE_UNITARIO")==null?null:resultSet.getDouble("COSTE_UNITARIO"));
			return devolucionLinea;
		}
	};

	private RowMapper<TDevolucionLinea> rwTDevolucionLineaPDF = new RowMapper<TDevolucionLinea>() {
		public TDevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TDevolucionLinea tdevolucionLinea = new TDevolucionLinea(resultSet.getString("IDSESION"),
					resultSet.getDate("CREATION_DATE"),
					resultSet.getLong("DEVOLUCION"),
					resultSet.getLong("COD_ARTICULO"),
					resultSet.getString("DENOMINACION"),
					resultSet.getString("MARCA"),
					resultSet.getString("SECCION"),
					resultSet.getLong("PROVR_GEN"),
					resultSet.getLong("PROVR_TRABAJO"),
					resultSet.getString("DENOM_PROVEEDOR"),
					resultSet.getString("FAMILIA"),
					resultSet.getString("FORMATO_DEVUELTO"),
					resultSet.getDouble("FORMATO"),
					resultSet.getString("TIPO_FORMATO"),
					resultSet.getString("PASILLO"),
					resultSet.getString("ESTRUCTURA_COMERCIAL"),
					resultSet.getDouble("UC"),
					resultSet.getDouble("STOCK_ACTUAL"),
					resultSet.getDouble("STOCK_EN_TIENDA"),
					resultSet.getDouble("STOCK_DEVOLVER"),		    									    							 	
					resultSet.getDouble("STOCK_DEVUELTO"),
					resultSet.getDouble("CANT_ABONADA"),
					resultSet.getString("FLG_CONTINUIDAD"),
					resultSet.getString("LOTE"),
					resultSet.getString("N_LOTE"),
					resultSet.getString("CADUCIDAD"),
					resultSet.getString("N_CADUCIDAD"),
					resultSet.getString("DESC_ABONO_ERROR"),
					resultSet.getLong("BULTO"),
					resultSet.getString("UBICACION"),
					resultSet.getLong("ESTADO_LIN"),
					resultSet.getLong("COD_ERROR"),
					resultSet.getString("DESC_ERROR"),
					resultSet.getLong("NUM_REF_PROVEEDOR"),
					resultSet.getLong("FLG_REF_RETIRADAS")
					);
			tdevolucionLinea.setDescrTalla(resultSet.getString("DESCR_TALLA"));
			tdevolucionLinea.setDescrColor(resultSet.getString("DESCR_COLOR"));
			tdevolucionLinea.setModeloProveedor(resultSet.getString("MODELO_PROVEEDOR"));
			tdevolucionLinea.setModelo(resultSet.getString("MODELO"));
			tdevolucionLinea.setCantidadMaximaLin(resultSet.getBigDecimal("CANTIDAD_MAXIMA_LIN") == null ? null:resultSet.getDouble("CANTIDAD_MAXIMA_LIN"));
			tdevolucionLinea.setCodEAN(resultSet.getString("COD_EAN"));
			
			return tdevolucionLinea;
		}
	};

	private RowMapper<TDevolucionLinea> rwTDevolucionContadoresProveedorPDF = new RowMapper<TDevolucionLinea>() {
		public TDevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TDevolucionLinea tDevolucionLinea = new TDevolucionLinea();
			tDevolucionLinea.setContador(resultSet.getLong("CONTADOR"));
			tDevolucionLinea.setProvrTrabajo(resultSet.getLong("PROVR_GEN"));

			return tDevolucionLinea;
		}
	};

	private RowMapper<TDevolucionLinea> rwTDevolucionContadoresBultoPDF = new RowMapper<TDevolucionLinea>() {
		public TDevolucionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TDevolucionLinea tDevolucionLinea = new TDevolucionLinea();
			tDevolucionLinea.setContador(resultSet.getLong("CONTADOR"));
			tDevolucionLinea.setBulto(resultSet.getLong("BULTO"));
			tDevolucionLinea.setProvrGen(resultSet.getLong("PROVR_GEN"));

			return tDevolucionLinea;

		}
	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	@Override
	public void insertAll(final List<TDevolucionLinea> listaTDevolucionLinea) throws Exception {
		//Esto es infinitamente más rápido que hacerlo en blucle. ¡Viva el batchUpdate!
		String query = " INSERT INTO t_devoluciones "
						+ "(bulto, bulto_ori, caducidad, cant_abonada, cod_articulo, "
						+ " cod_error, creation_date, denominacion, denom_proveedor, desc_abono_error, desc_error, devolucion, "
						+ " estado_lin, estructura_comercial, familia, flg_continuidad, formato, formato_devuelto, idsesion, "		
						+ " lote, marca, n_caducidad, n_lote, pasillo, provr_gen, provr_trabajo, seccion, stock_actual, stock_devolver, "
						+ " stock_devuelto,stock_devuelto_ori, stock_en_tienda, tipo_formato, ubicacion, tipo_referencia, uc, flg_bandejas, "
						+ " flg_peso_variable, stock_devuelto_bandejas, stock_devuelto_bandejas_ori, cod_tp_c_a, cantidad_maxima, "
						+ " descr_talla, descr_color, modelo_proveedor, coste_unitario, coste_final, area, modelo, cantidad_maxima_lin"
						+ ")"
					 + " VALUES "
					 	+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		if (listaTDevolucionLinea != null && listaTDevolucionLinea.size()>0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					TDevolucionLinea myPojo = listaTDevolucionLinea.get(i);

					if (null != myPojo.getBulto()){
						ps.setLong(1, myPojo.getBulto());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}
					
					if (null != myPojo.getBulto()){
						ps.setLong(2, myPojo.getBulto());
					} else {
						ps.setNull(2, Types.NUMERIC);
					}
					
					ps.setString(3, myPojo.getCaducidad());
					
					if (null != myPojo.getCantAbonada()){
						ps.setDouble(4,myPojo.getCantAbonada());	
					} else {
						ps.setNull(4, Types.NUMERIC);
					}

					if (null != myPojo.getCodArticulo()){
						ps.setLong(5, myPojo.getCodArticulo());
					} else {
						ps.setNull(5, Types.NUMERIC);
					}
					
					if (null != myPojo.getCodError()){
						ps.setLong(6, myPojo.getCodError());
					} else {
						ps.setNull(6, Types.NUMERIC);
					}

					if (null != myPojo.getCreationDate()){
						ps.setDate(7, new java.sql.Date(myPojo.getCreationDate().getTime()));
					} else {
						ps.setNull(7, Types.DATE);
					}

					ps.setString(8, myPojo.getDenominacion());
					ps.setString(9, myPojo.getDenomProveedor());
					ps.setString(10, myPojo.getDescAbonoError());
					ps.setString(11, myPojo.getDescError());
					
					if (null != myPojo.getDevolucion()){
						ps.setLong(12, myPojo.getDevolucion());
					} else {
						ps.setNull(12, Types.NUMERIC);
					}
					
					if (null != myPojo.getEstadoLin()){
						ps.setLong(13, myPojo.getEstadoLin());
					} else {
						ps.setNull(13, Types.NUMERIC);
					}
					
					ps.setString(14, myPojo.getEstructuraComercial());
					ps.setString(15, myPojo.getFamilia());
					ps.setString(16, myPojo.getFlgContinuidad());
					
					if (null != myPojo.getFormato()){
						ps.setDouble(17,myPojo.getFormato());	
					} else {
						ps.setNull(17, Types.NUMERIC);
					}
															
					ps.setString(18,myPojo.getFormatoDevuelto());
					ps.setString(19,myPojo.getIdSesion());
					ps.setString(20,myPojo.getLote());
					ps.setString(21,myPojo.getMarca());
					ps.setString(22,myPojo.getnCaducidad());
					ps.setString(23,myPojo.getnLote());
					ps.setString(24,myPojo.getPasillo());
					
					if (null != myPojo.getProvrGen()){
						ps.setLong(25, myPojo.getProvrGen());
					} else {
						ps.setNull(25, Types.NUMERIC);
					}
					
					if (null != myPojo.getProvrTrabajo()){
						ps.setLong(26, myPojo.getProvrTrabajo());
					} else {
						ps.setNull(26, Types.NUMERIC);
					}
					
					ps.setString(27, myPojo.getSeccion());
					
					if (null != myPojo.getStockActual()){
						ps.setDouble(28,myPojo.getStockActual());	
					} else {
						ps.setNull(28, Types.NUMERIC);
					}
						
					if (null != myPojo.getStockDevolver()){
						ps.setDouble(29,myPojo.getStockDevolver());	
					} else {
						ps.setNull(29, Types.NUMERIC);
					}
					
					if (null != myPojo.getStockDevuelto()){
						ps.setDouble(30,myPojo.getStockDevuelto());
					} else {
						ps.setNull(30, Types.NUMERIC);
					}
					
					if (null != myPojo.getStockDevuelto()){
						ps.setDouble(31,myPojo.getStockDevuelto());
					} else {
						ps.setNull(31, Types.NUMERIC);
					}
					
					if (null != myPojo.getStockTienda()){
						ps.setDouble(32,myPojo.getStockTienda());
					} else {
						ps.setNull(32, Types.NUMERIC);
					}
					
					ps.setString(33,myPojo.getTipoFormato());
					ps.setString(34,myPojo.getUbicacion());
					ps.setString(35,myPojo.getTipoReferencia());
					
					if (null != myPojo.getUc()){
						ps.setDouble(36,myPojo.getUc());
					} else {
						ps.setNull(36, Types.NUMERIC);
					}
					
					ps.setString(37, myPojo.getFlgBandejas());
					ps.setString(38, myPojo.getFlgPesoVariable()); // MISUMI-259
					
					if (null != myPojo.getStockDevueltoBandejas()){
						ps.setDouble(39,myPojo.getStockDevueltoBandejas());
					} else {
						ps.setNull(39, Types.NUMERIC);
					}
					
					if (null != myPojo.getStockDevueltoBandejas()){
						ps.setDouble(40,myPojo.getStockDevueltoBandejas());
					} else {
						ps.setNull(40, Types.NUMERIC);
					}
					
					if (null != myPojo.getCodTpCa()){
						ps.setLong(41, myPojo.getCodTpCa());
					} else {
						ps.setNull(41, Types.NUMERIC);
					}
					
					if (null != myPojo.getCantidadMaximaPermitida()){
						ps.setDouble(42,myPojo.getCantidadMaximaPermitida());
					} else {
						ps.setNull(42, Types.NUMERIC);
					}
					
					ps.setString(43, myPojo.getDescrTalla());
					ps.setString(44, myPojo.getDescrColor());
					ps.setString(45, myPojo.getModeloProveedor());
					
					if (null != myPojo.getCosteUnitario()){
						ps.setDouble(46,myPojo.getCosteUnitario());
					} else {
						ps.setNull(46, Types.NUMERIC);
					}
					
					if (null != myPojo.getCosteFinal()){
						ps.setDouble(47,myPojo.getCosteFinal());
					} else {
						ps.setNull(47, Types.NUMERIC);
					}
					
					if (null != myPojo.getArea()){
						ps.setLong(48, myPojo.getArea());
					} else {
						ps.setNull(48, Types.NUMERIC);
					}
					ps.setString(49,  myPojo.getModelo());
					
					if (null != myPojo.getCantidadMaximaLin()){
						ps.setDouble(50, myPojo.getCantidadMaximaLin());
					} else {
						ps.setNull(50, Types.NUMERIC);
					}
				}

				@Override
				public int getBatchSize() {
					return listaTDevolucionLinea.size();
				}
			});
		}
	}

	@Override
	public void delete(TDevolucionLinea tDevolucionLinea) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM t_devoluciones WHERE idsesion = ? ");
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

		StringBuffer query = new StringBuffer(" DELETE FROM t_devoluciones WHERE creation_date < (SYSDATE - ?) ");
		params.add(Constantes.DIAS_ELIMINAR);

		try{
			//this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public List<OptionSelectBean> obtenerProveedoresLineasDevolucion(String session, Devolucion devolucion) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT DISTINCT NVL(provr_gen,'') codigo"
												 + ", (NVL(provr_gen,'') || '-' || NVL(denom_proveedor,'')) DESCRIPCION"
												 + ", provr_gen "
											+ "FROM t_devoluciones ");

		where.append("AND idsesion = ? ");
		where.append("AND devolucion = ? ");

		params.add(session);
		params.add(devolucion.getDevolucion());

		where.append("ORDER BY provr_gen ");

		query.append(where);
		
		List<OptionSelectBean> lista = null;
		try{
			lista = (List<OptionSelectBean>) this.jdbcTemplate.query(query.toString(),this.rwProveedorLineaDevolucion, params.toArray());

		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;
		
//		List<OptionSelectBean> listaAux = new ArrayList<OptionSelectBean>();
//		for (OptionSelectBean proveedor: lista){
//			if (this.esProveedorSinFinalizar(session, devolucion.getDevolucion(), proveedor.getCodigo())){
//				proveedor.setDescripcion(proveedor.getDescripcion());
//			}else{
//				proveedor.setDescripcion("OK "+proveedor.getDescripcion());
//			}
//
//			listaAux.add(proveedor);
//		}
//		
//		return listaAux;
	}

	@Override
	public List<TDevolucionLinea> findLineasDevolucion(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor,String flagRefPermanentes, String filtroReferencia) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("SELECT * " 
											+ "FROM t_devoluciones "
											+ "WHERE idsesion   = ? "
											+ "AND devolucion 	= ? "
							);

		params.add(session);
		params.add(devolucion.getDevolucion());  

		if (codArticulo != null){
			where.append("AND cod_articulo = ? ");
			params.add(codArticulo);
		}

		if (!(" ").equals(proveedor) && proveedor != null && !("").equals(proveedor)){
			/*String[] arrayProveedor = proveedor.split("-");
			if (arrayProveedor.length == 2){
				if (!"".equals(arrayProveedor[0])){
					where.append(" AND PROVR_GEN = ? ");
					params.add(arrayProveedor[0]);
				}
				if (!"".equals(arrayProveedor[1])){
					where.append(" AND PROVR_TRABAJO = ? ");
					params.add(arrayProveedor[1]);
				}
			}*/
			where.append("AND provr_gen = ? ");
			params.add(proveedor);

		}

		if(!(" ").equals(filtroReferencia) && filtroReferencia != null && !("").equals(filtroReferencia)){
			where.append("AND cod_articulo LIKE '" + filtroReferencia + "%' ");

		}
		if(flagRefPermanentes != null && !(" ").equals(flagRefPermanentes)){
			where.append("AND flg_continuidad = ? ");
			params.add(flagRefPermanentes);		
		}

		query.append(where);

		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por pasillo/denominacion proveedor/estructura comercial/stock_actual
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append("ORDER BY " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append("ORDER BY provr_gen, marca, estructura_comercial");
				query.append(order);
			}
		}else{
			//order.append(" order by DECODE(STOCK_ACTUAL,null,100,0,100,1), PASILLO, DENOMINACION, ESTRUCTURA_COMERCIAL");
			order.append("ORDER BY provr_gen, marca, estructura_comercial");
			query.append(order);
		}    	       	       

		List<TDevolucionLinea> lista = null;
		try {
//			logger.info(query.toString());
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionLinea, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TDevolucionLinea> findLineasDevBulto(String session, Devolucion devolucion, Pagination pagination, Long codArticulo, String proveedor, String flagRefPermanentes, String filtroReferencia) throws Exception{
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		int bultoSeleccionado = devolucion.getBultoSeleccionado();

		StringBuffer query = new StringBuffer("SELECT d.* " 
											+ "FROM t_devoluciones d, t_mis_devoluciones_bulto b "
											+ "WHERE d.idsesion 	= b.idsesion "
											+ "AND d.cod_articulo 	= b.cod_articulo "
											+ "AND d.devolucion 	= b.devolucion "
											+ "AND d.idsesion   	= ? "
											+ "AND d.devolucion 	= ? "
							);

		params.add(session);
		params.add(devolucion.getDevolucion());  

		if (codArticulo != null){
			where.append("AND d.cod_articulo = ? ");
			params.add(codArticulo);
		}

		if (bultoSeleccionado > 0){
			where.append("AND b.bulto = ? ");
			params.add(bultoSeleccionado);
		}
		
		if (!(" ").equals(proveedor) && proveedor != null && !("").equals(proveedor)){
			where.append("AND d.provr_gen = ? ");
			params.add(proveedor);
		}

		if(!(" ").equals(filtroReferencia) && filtroReferencia != null && !("").equals(filtroReferencia)){
			where.append("AND d.cod_articulo LIKE '" + filtroReferencia + "%' ");

		}
		if(flagRefPermanentes != null && !(" ").equals(flagRefPermanentes)){
			where.append("AND d.flg_continuidad = ? ");
			params.add(flagRefPermanentes);		
		}

		query.append(where);

		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por pasillo/denominacion proveedor/estructura comercial/stock_actual
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append("ORDER BY " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append("ORDER BY d.provr_gen, d.marca, d.estructura_comercial");
				query.append(order);
			}
		}else{
			//order.append(" order by DECODE(STOCK_ACTUAL,null,100,0,100,1), PASILLO, DENOMINACION, ESTRUCTURA_COMERCIAL");
			order.append("ORDER BY d.provr_gen, d.marca, d.estructura_comercial");
			query.append(order);
		}    	       	       

		List<TDevolucionLinea> lista = null;
		try {
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(), this.rwTDevolucionLinea, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<DevolucionLinea> findLineasDevolucionEditadas(String session, Long codError) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT * " 
											+ "FROM t_devoluciones ");

		where.append(" AND idsesion = ? ");
		where.append(" AND cod_error = ? ");

		params.add(session);
		params.add(codError);  

		query.append(where);

		List<DevolucionLinea> lista = null;	
		try{
			lista = (List<DevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionLineaEditadas, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TDevolucionLinea> findLineasDevolucionPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT t2.idsesion idsesion, t2.creation_date creation_date, t2.devolucion devolucion"
												 + ", t2.cod_articulo cod_articulo, t2.denominacion denominacion, t2.marca marca"
												 + ", t2.seccion seccion, t2.provr_gen provr_gen, t2.provr_trabajo provr_trabajo"
												 + ", t2.denom_proveedor denom_proveedor, t2.familia familia, t2.formato_devuelto formato_devuelto"
												 + ", t2.formato formato, t2.tipo_formato tipo_formato, t2.pasillo pasillo"
												 + ", t2.estructura_comercial estructura_comercial, t2.uc uc, t2.stock_actual stock_actual"
												 + ", t2.stock_en_tienda stock_en_tienda, t2.stock_devolver stock_devolver"
												 + ", bulto.stock_devuelto stock_devuelto, t2.cant_abonada, t2.flg_continuidad"
												 + ", t2.lote, t2.n_lote, t2.caducidad, t2.n_caducidad, t2.desc_abono_error"
												 + ", bulto.bulto, t2.ubicacion ubicacion, t2.estado_lin estado_lin, t2.cod_error cod_error"
												 + ", t2.desc_error desc_error"
												 + ", (SELECT COUNT(dev.provr_gen) "
												 	+ "FROM t_devoluciones dev "
												 	+ "WHERE dev.provr_gen 	= t2.provr_gen "
												 	+ "AND dev.idsesion 	= t2.idsesion "
												 	+ "AND dev.devolucion 	= t2.devolucion"
												 	+ ") num_ref_proveedor"
												 + ", DECODE(bulto.bulto, NULL, 0, 0, 0, 1) flg_ref_retiradas " //Se quiere que los bultos a 0 en orden de recogida salgan los primeros en el listado
												 + ", t2.descr_talla, t2.descr_color, t2.modelo_proveedor, t2.modelo, t2.cantidad_maxima_lin"
												 + ", (SELECT cod_ean "
												 	+ "FROM (SELECT cod_ean, 1 "
												 		  + "FROM eans ean "
												 		  + "WHERE ean.cod_articulo = t2.cod_articulo "
												 		  + "AND fec_desde_edi <= TRUNC(SYSDATE) "
												 		  + "AND fec_hasta_edi >= TRUNC(SYSDATE) "
												 		  + "UNION ALL "
												 		  + "SELECT cod_ean, 2 "
												 		  + "FROM eans ean "
												 		  + "WHERE ean.cod_articulo = t2.cod_articulo "
												 		  + "ORDER BY 2"
												 		  + ") "
												 	 + "FETCH FIRST 1 ROWS ONLY"
												 	 + ") cod_ean "
											+ "FROM t_devoluciones t2 ,t_mis_devoluciones_bulto bulto ");

		where.append(" AND t2.idsesion = ? ");
		params.add(session);

		where.append(" AND t2.devolucion = ? ");
		params.add(devolucion.getDevolucion()); 

		if (devolucion.getFiltroProveedor() != null && !(devolucion.getFiltroProveedor().equals(""))) {
			where.append(" AND t2.provr_gen = ? ");
			params.add(devolucion.getFiltroProveedor());
		}
		where.append(" AND T2.IDSESION =bulto.IDSESION(+) AND t2.DEVOLUCION =bulto.DEVOLUCION(+) AND t2.COD_ARTICULO =bulto.COD_ARTICULO(+) ");
		query.append(where);

		//Se aÃ±ade un order by segÃºn la columna clicada para la ordenaciÃ³n.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		if (devolucion.isEsOrdenRecogidaPDF()) {
			order.append(" ORDER BY DECODE(bulto.BULTO, NULL, 0, 0, 0, 1), T2.PROVR_GEN, bulto.BULTO, T2.MARCA, T2.ESTRUCTURA_COMERCIAL" );
		}else{
			order.append(" ORDER BY T2.PROVR_GEN,T2.FLG_CONTINUIDAD, T2.MARCA, T2.ESTRUCTURA_COMERCIAL" );
		}
		query.append(order);

		List<TDevolucionLinea> lista = null;
		try {
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionLineaPDF, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}
	
	@Override
	public List<TDevolucionLinea> findLineasDevolucionAgrupadasPDF(String session, Devolucion devolucion) throws Exception {

		//MISUMI-666.- agrupar proveedor-articulo
		StringBuilder sqlWrapper = new StringBuilder();
		
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT t2.idsesion idsesion, t2.creation_date creation_date, t2.devolucion devolucion"
												 + ", t2.cod_articulo cod_articulo, t2.denominacion denominacion, t2.marca marca"
												 + ", t2.seccion seccion, t2.provr_gen provr_gen, t2.provr_trabajo provr_trabajo"
												 + ", t2.denom_proveedor denom_proveedor, t2.familia familia, t2.formato_devuelto formato_devuelto"
												 + ", t2.formato formato, t2.tipo_formato tipo_formato, t2.pasillo pasillo"
												 + ", t2.estructura_comercial estructura_comercial, t2.uc uc, t2.stock_actual stock_actual"
												 + ", t2.stock_en_tienda stock_en_tienda, t2.stock_devolver stock_devolver"
												 + ", t2.stock_devuelto stock_devuelto, t2.cant_abonada, t2.flg_continuidad"
												 + ", t2.lote, t2.n_lote, t2.caducidad, t2.n_caducidad, t2.desc_abono_error"
												 + ", bulto.bulto, t2.ubicacion ubicacion, t2.estado_lin estado_lin, t2.cod_error cod_error"
												 + ", t2.desc_error desc_error"
												 + ", (SELECT COUNT(dev.provr_gen) "
												 	+ "FROM t_devoluciones dev "
												 	+ "WHERE dev.provr_gen 	= t2.provr_gen "
												 	+ "AND dev.idsesion 	= t2.idsesion "
												 	+ "AND dev.devolucion 	= t2.devolucion"
												 	+ ") num_ref_proveedor"
												 + ", DECODE(bulto.bulto, NULL, 0, 0, 0, 1) flg_ref_retiradas " //Se quiere que los bultos a 0 en orden de recogida salgan los primeros en el listado
												 + ", t2.descr_talla, t2.descr_color, t2.modelo_proveedor, t2.modelo, t2.cantidad_maxima_lin"
												 + ", (SELECT cod_ean "
												 	+ "FROM (SELECT cod_ean, 1 "
												 		  + "FROM eans ean "
												 		  + "WHERE ean.cod_articulo = t2.cod_articulo "
												 		  + "AND fec_desde_edi <= TRUNC(SYSDATE) "
												 		  + "AND fec_hasta_edi >= TRUNC(SYSDATE) "
												 		  + "UNION ALL "
												 		  + "SELECT cod_ean, 2 "
												 		  + "FROM eans ean "
												 		  + "WHERE ean.cod_articulo = t2.cod_articulo "
												 		  + "ORDER BY 2"
												 		  + ") "
												 	 + "FETCH FIRST 1 ROWS ONLY"
												 	 + ") cod_ean "
												 	 + ", ROW_NUMBER() OVER (PARTITION BY t2.provr_gen, t2.cod_articulo ORDER BY t2.creation_date DESC ) rn "
											+ "FROM t_devoluciones t2 ,t_mis_devoluciones_bulto bulto ");

		where.append(" AND t2.idsesion = ? ");
		params.add(session);

		where.append(" AND t2.devolucion = ? ");
		params.add(devolucion.getDevolucion()); 

		if (devolucion.getFiltroProveedor() != null && !(devolucion.getFiltroProveedor().equals(""))) {
			where.append(" AND t2.provr_gen = ? ");
			params.add(devolucion.getFiltroProveedor());
		}
		
		where.append(" AND T2.IDSESION =bulto.IDSESION(+) AND t2.DEVOLUCION =bulto.DEVOLUCION(+) AND t2.COD_ARTICULO =bulto.COD_ARTICULO(+) ");
		query.append(where);

		//Se añade un order by según la columna clicada para la ordenación.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		
		if (devolucion.isEsOrdenRecogidaPDF()) {
			order.append(" ORDER BY DECODE(BULTO, NULL, 0, 0, 0, 1), PROVR_GEN, BULTO, MARCA, ESTRUCTURA_COMERCIAL" );
		}else{
			order.append(" ORDER BY PROVR_GEN, FLG_CONTINUIDAD, MARCA, ESTRUCTURA_COMERCIAL" );
		}
		
		
		sqlWrapper.append("SELECT * FROM (").append(query).append(") WHERE rn=1 ").append(order);		
		
		List<TDevolucionLinea> lista = null;
		try {
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(sqlWrapper.toString(),this.rwTDevolucionLineaPDF, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion) throws Exception {
		return findContadoresPorProveedorPDF(session, devolucion, true);
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT COUNT(provr_gen) contador, provr_gen "
											+ "FROM t_devoluciones t2, t_mis_devoluciones_bulto bulto ");

		where.append(" AND t2.IDSESION = ? ");
		params.add(session);

		where.append(" AND t2.DEVOLUCION = ? ");
		params.add(devolucion.getDevolucion()); 

		if (devolucion.getFiltroProveedor() != null && !(devolucion.getFiltroProveedor().equals(""))) {

			where.append(" AND PROVR_GEN = ? ");
			params.add(devolucion.getFiltroProveedor());

		}

		if (!contarReferenciasSinRetirada){
			where.append(" AND bulto.BULTO IS NOT NULL AND bulto.BULTO <> 0 ");
		}
		where.append(" AND T2.IDSESION =bulto.IDSESION(+) AND t2.DEVOLUCION =bulto.DEVOLUCION(+) AND t2.COD_ARTICULO =bulto.COD_ARTICULO(+) ");
		query.append(where);

		//Se añade el group by 
		StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" GROUP BY PROVR_GEN " );
		query.append(groupBy);

		//Se añade un order by según la columna clicada para la ordenación.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY PROVR_GEN " );
		query.append(order);


		List<TDevolucionLinea> lista = null;
		try {
			lista =  (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionContadoresProveedorPDF, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}
	
	@Override
	public List<TDevolucionLinea> findContadoresPorBultoPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT count(BULTO) CONTADOR, BULTO  FROM  T_DEVOLUCIONES ");

		where.append(" AND IDSESION = ? ");
		params.add(session);

		where.append(" AND DEVOLUCION = ? ");
		params.add(devolucion.getDevolucion()); 
		
		where.append(" AND BULTO IS NOT NULL AND BULTO <> 0 ");
		
		query.append(where);

		//Se añade el group by 
		StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" GROUP BY BULTO " );
		query.append(groupBy);

		//Se añade un order by según la columna clicada para la ordenación.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY BULTO " );
		query.append(order);

		List<TDevolucionLinea> lista = null;
		try {
			lista =  (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionContadorPorBultoPDF, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion) throws Exception {
		return findContadoresBultoPorProveedorPDF(session, devolucion, true);
	}

	@Override
	public List<TDevolucionLinea> findContadoresBultoPorProveedorPDF(String session, Devolucion devolucion, boolean contarReferenciasSinRetirada) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT count(distinct bulto.BULTO) CONTADOR, PROVR_GEN  FROM  T_DEVOLUCIONES t2,	t_mis_devoluciones_bulto bulto ");

		where.append(" AND t2.IDSESION = ? ");
		params.add(session);

		where.append(" AND t2.DEVOLUCION = ? ");
		params.add(devolucion.getDevolucion()); 

		if (devolucion.getFiltroProveedor() != null && !(devolucion.getFiltroProveedor().equals(""))) {
			where.append(" AND PROVR_GEN = ? ");
			params.add(devolucion.getFiltroProveedor());
		}

		if (!contarReferenciasSinRetirada){
			where.append(" AND bulto.BULTO IS NOT NULL AND bulto.BULTO <> 0 ");
		}
		
		where.append(" AND T2.IDSESION =bulto.IDSESION(+) AND t2.DEVOLUCION =bulto.DEVOLUCION(+) AND t2.COD_ARTICULO =bulto.COD_ARTICULO(+) ");
		query.append(where);

		//Se añade el group by 
		StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" GROUP BY PROVR_GEN " );
		query.append(groupBy);

		//Se añade un order by según la columna clicada para la ordenación.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY PROVR_GEN " );
		query.append(order);


		List<TDevolucionLinea> lista = null;
		try {
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionContadoresProveedorPDF, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TDevolucionLinea> findContadoresPorProveedorBultoPDF(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT count(bulto.BULTO) CONTADOR, PROVR_GEN, bulto.BULTO  FROM  T_DEVOLUCIONES t2, t_mis_devoluciones_bulto bulto ");

		where.append(" AND t2.IDSESION = ? ");
		where.append(" AND t2.DEVOLUCION = ? ");
		where.append(" AND bulto.BULTO IS NOT NULL AND bulto.BULTO <> 0 ");
		where.append(" AND T2.IDSESION =bulto.IDSESION(+) AND t2.DEVOLUCION =bulto.DEVOLUCION(+) AND t2.COD_ARTICULO =bulto.COD_ARTICULO(+) ");
		query.append(where);

		//Se añade el group by 
		StringBuffer groupBy = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		groupBy.append(" GROUP BY PROVR_GEN, bulto.BULTO" );
		query.append(groupBy);

		//Se añade un order by según la columna clicada para la ordenación.
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY PROVR_GEN, bulto.BULTO" );
		query.append(order);

		params.add(session);
		params.add(devolucion.getDevolucion()); 

		List<TDevolucionLinea> lista = null;
		try {
			lista = (List<TDevolucionLinea>) this.jdbcTemplate.query(query.toString(),this.rwTDevolucionContadoresBultoPDF, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}


	@Override
	public Long findContadoresReferenciasSinRetiradaPDF(String session, Devolucion devolucion) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT count(1) FROM  T_DEVOLUCIONES ");

		where.append(" AND IDSESION = ? ");
		params.add(session);

		where.append(" AND DEVOLUCION = ? ");
		params.add(devolucion.getDevolucion()); 

		where.append(" AND (BULTO IS NULL OR BULTO = 0) ");

		query.append(where);

		Long cont = null;
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return cont;
	}

	//Función que sirve para realizar ordenaciones por columna, de esta forma, llega el código de columna con nombre
	//jqgrid y lo transforma a código SQL
	private String getMappedField (String fieldName) {
		if (fieldName.toUpperCase().equals("CODARTICULO")){
			return "COD_ARTICULO";
		}else if(fieldName.toUpperCase().equals("DENOMINACION")){
			return "DENOMINACION";
		}else if(fieldName.toUpperCase().equals("STOCKACTUAL")){
			return "STOCK_ACTUAL";
		}else if(fieldName.toUpperCase().equals("STOCKDEVOLVER")){
			return "STOCK_DEVOLVER";
		}else if(fieldName.toUpperCase().equals("STOCKDEVUELTO")){
			return "STOCK_DEVUELTO";
		}else if(fieldName.toUpperCase().equals("BULTO")){
			return "BULTO";
		}else if(fieldName.toUpperCase().equals("FORMATODEVUELTO")){
			return "FORMATO_DEVUELTO";
		}else if(fieldName.toUpperCase().equals("TIPOREFERENCIA")){
			return "TIPO_REFERENCIA";
		}else if(fieldName.toUpperCase().equals("MARCA")){
			return "MARCA";
		}
		else if(fieldName.toUpperCase().equals("FORMATO")){
			return "FORMATO";
		}
		else if(fieldName.toUpperCase().equals("NLOTE")){
			return "N_LOTE";
		}
		else if(fieldName.toUpperCase().equals("NCADUCIDAD")){
			return "N_CADUCIDAD";
		}
		else if(fieldName.toUpperCase().equals("CANTABONADA")){
			return "CANT_ABONADA";
		}
		else if(fieldName.toUpperCase().equals("DESCABONOERROR")){
			return "DESC_ABONO_ERROR";
		}
		else if(fieldName.toUpperCase().equals("MODELO")){
			return "MODELO";
		}
		else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
			return "MODELO_PROVEEDOR";
		}
		else if(fieldName.toUpperCase().equals("DESCRTALLA")){
			return "DESCR_TALLA";
		}
		else if(fieldName.toUpperCase().equals("DESCRCOLOR")){
			return "DESCR_COLOR";
		}
		else {
			return "DECODE(STOCK_ACTUAL,null,100,0,100,1), PASILLO, DENOMINACION, ESTRUCTURA_COMERCIAL";
		}
	}

	@Override
	public void updateTablaSesionLineaDevolucion(String session, Devolucion devolucion, boolean isSaveData) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		for (DevolucionLineaModificada devLineaModificada: devolucion.getDevLineasModificadas()){
			StringBuffer query = new StringBuffer("UPDATE t_devoluciones "
												+ "SET bulto                   = ?"
												  + ", stock_devuelto          = ?"
												  + ", stock_actual            = NVL(?, stock_actual)"
												  + ", stock_devolver          = NVL(?, stock_devolver)"
												  + ", cod_error               = NVL(?, cod_error)"
												  + ", stock_devuelto_bandejas = NVL(?, stock_devuelto_bandejas)"
												  + ", coste_final             = (NVL(coste_unitario,0) * ?) ");

			//Si la actualización de la tabla temporal es debido a que se ha realizado una actualización previa PLSQL de las
			//líneas de devolución modificadas, es necesario actualizar los datos originales de la tabla temporal también.
			if(isSaveData){
				query.append(", bulto_ori                   = NVL(?, bulto_ori)"
						   + ", stock_devuelto_ori          = NVL(?, stock_devuelto_ori)"
						   + ", stock_devuelto_bandejas_ori = NVL(?, stock_devuelto_bandejas_ori) "
						   );
			}
			query.append("WHERE idsesion = ? "
					   + "AND devolucion = ? "
					   + "AND cod_articulo = ?"
					   );

			params = new ArrayList<Object>();

			params.add(devLineaModificada.getBulto());
			params.add(devLineaModificada.getStockDevuelto());
			params.add(devLineaModificada.getStockActual());
			params.add(devLineaModificada.getStockDevolver());
			params.add(devLineaModificada.getCodError());
			params.add(devLineaModificada.getStockDevueltoBandejas());
			params.add(devLineaModificada.getStockDevuelto());

			//Si la actualización de la tabla temporal es debido a que se ha realizado una actualización previa PLSQL de las
			//líneas de devolución modificadas, es necesario actualizar los datos originales de la tabla temporal también.
			if(isSaveData){
				params.add(devLineaModificada.getBulto());
				params.add(devLineaModificada.getStockDevuelto());
				params.add(devLineaModificada.getStockDevueltoBandejas());
			}

			params.add(session);
			params.add(devolucion.getDevolucion());
			params.add(devLineaModificada.getCodArticulo());
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}

		}
	}

	@Override
	public void updateConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE T_DEVOLUCIONES SET STOCK_DEVUELTO = ?, STOCK_DEVUELTO_ORI = ?, BULTO = DECODE(BULTO, NULL, ?, BULTO) , BULTO_ORI = DECODE(BULTO, NULL, ?, BULTO) ");
		query.append(" WHERE IDSESION = ? ");
		query.append(" AND DEVOLUCION = ? ");
		query.append(" AND STOCK_DEVUELTO IS NULL ");

		params = new ArrayList<Object>();

		params.add(0);
		params.add(0);
		params.add(0);
		params.add(0);
		params.add(session);
		params.add(devolucion.getDevolucion());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void resetDevolEstados(String session) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("UPDATE t_devoluciones "
											+ "SET cod_error  = ? "
											+ "WHERE idsesion = ?"
											);
		params = new ArrayList<Object>();

		params.add(null);
		params.add(session);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public boolean existenRefPermanentes(String session, Devolucion devolucion) throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE FLG_CONTINUIDAD = 'S' ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
				+ " FROM  T_DEVOLUCIONES ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND DEVOLUCION = ? ");

		params.add(session);
		params.add(devolucion.getDevolucion());  

		query.append(where);

		boolean existen = false;
		try {
			existen = (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return existen;
	}

	@Override
	public boolean existeStockDevueltoConCeroTablaSesionLineaDevolucion(String session, Devolucion devolucion)
			throws Exception {
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE STOCK_DEVUELTO IS NULL ");

		StringBuffer query = new StringBuffer("SELECT COUNT(1) " 
											+ "FROM T_DEVOLUCIONES ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND DEVOLUCION = ? ");

		params.add(session);
		params.add(devolucion.getDevolucion());  

		query.append(where);

		return (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
	}

	@Override
	public boolean esProveedorSinFinalizar(String session, Long devolucion, String proveedor, String origen) throws Exception{

		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		
		//Si el origen de la llamada es Pistola.
		if (origen != null && Constantes.PDA.equalsIgnoreCase(origen)){
			query = new StringBuffer("SELECT COUNT(DISTINCT existe) "
									+ "FROM (SELECT 1 existe "
										  + "FROM t_devoluciones dev, t_mis_devoluciones_bulto deb "
										  + "WHERE dev.devolucion 	= deb.devolucion "
										  + "AND dev.idsesion 		= deb.idsesion "
										  + "AND deb.stock_devuelto	<> 0 "
										  + "AND dev.cod_articulo 	= deb.cod_articulo "
										  + "AND dev.idsesion 		= ? "
										  + "AND dev.devolucion 	= ? "
										  + "AND dev.provr_gen 		= NVL(?, dev.provr_gen) "
										  + "AND NVL(deb.estado(+),'N') <> 'S' "
										  + "UNION ALL "
										  + "SELECT 1 existe "
										  + "FROM t_devoluciones dev "
										  + "WHERE stock_actual 	<> 0 "
										  + "AND dev.idsesion 		= ? "
										  + "AND dev.devolucion 	= ? "
										  + "AND dev.provr_gen 		= NVL(?, dev.provr_gen) "
										  + "AND NOT EXISTS (SELECT 1 "
										  				  + "FROM t_mis_devoluciones_bulto deb "
										   				  + "WHERE dev.devolucion 	= deb.devolucion "
										   				  + "AND dev.idsesion 		= deb.idsesion "
														  + "AND dev.cod_articulo 	= deb.cod_articulo"
														  + ")"
										  + ")"
										 );

			params.add(session);
			params.add(devolucion);
			params.add(proveedor);
			params.add(session);
			params.add(devolucion);
			params.add(proveedor);
			
		// Para PC
		}else{
			query = new StringBuffer("SELECT COUNT(DISTINCT existe) "
								   + "FROM (SELECT 1 existe "
										 + "FROM t_devoluciones dev "
										 + "WHERE stock_actual 		<> 0 "
										 + "AND stock_devuelto		IS NULL "
										 + "AND dev.idsesion 		= ? "
										 + "AND dev.devolucion 		= ? "
										 + "AND dev.provr_gen 		= NVL(?, dev.provr_gen)"
										+ ")"
									);
//			  + "AND NOT EXISTS (SELECT 1 "
//			  + "FROM t_mis_devoluciones_bulto deb "
//				  + "WHERE dev.devolucion 	= deb.devolucion "
//				  + "AND dev.idsesion 		= deb.idsesion "
//			  + "AND dev.cod_articulo 	= deb.cod_articulo"
//			  + ")"
//+ ")"
//);
			params.add(session);
			params.add(devolucion);
			params.add(proveedor);
		}

		try{
			return (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean existeReferenciaEnDevolucion(String session, DevolucionLinea devLin) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1 =1");

		StringBuffer query = new StringBuffer("SELECT COUNT(1) " 
											+ "FROM T_DEVOLUCIONES ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND COD_ARTICULO = ? ");

		params.add(session);
		params.add(devLin.getCodArticulo());  

		query.append(where);

		return (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
	}

	@Override
	public void deleteLineasDevolucion(String session, Devolucion devolucionAEliminar) throws Exception {
		// TODO Auto-generated method stub
		for (TDevolucionLinea tDevLineaAEliminar: devolucionAEliminar.gettDevLineasLst()){
			List<Object> params = new ArrayList<Object>();
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append("WHERE 1 =1");

			StringBuffer query = new StringBuffer(" DELETE FROM T_DEVOLUCIONES ");

			where.append(" AND IDSESION = ? ");
			params.add(session);

			where.append(" AND COD_ARTICULO = ? ");
			params.add(tDevLineaAEliminar.getCodArticulo());
			query.append(where);

			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}									
	}

	@Override
	public List<Long> getRefByPattern(String term, String session, Devolucion devolucion) throws Exception {
		// TODO Auto-generated method stub		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1 =1");

		StringBuffer query = new StringBuffer("SELECT cod_articulo " 
											+ "FROM t_devoluciones "
											);

		where.append(" AND IDSESION = ? ");
		where.append(" AND DEVOLUCION = ? ");
		params.add(session);
		params.add(devolucion.getDevolucion());  

		if (term != null && !("".equals(term))) {
			where.append(" AND COD_ARTICULO LIKE (?) ");
			params.add(term+"%");
		}

		if(!(" ").equals(devolucion.getProveedor()) && devolucion.getProveedor() != null && !("").equals(devolucion.getProveedor())){
			where.append(" AND PROVR_GEN = ? ");
			params.add(devolucion.getProveedor());
		}

		query.append(where);

		List<Long> refList = null;
		try {
			refList = (List<Long>) this.jdbcTemplate.queryForList(query.toString(), Long.class ,params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return refList;
	}

	@Override
	public Double getSumaCosteFinal(final Devolucion devolucion, String sessionId) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append(" WHERE 1=1 ");
		//params.add(lang);
		StringBuffer query = new StringBuffer("SELECT SUM(NVL(coste_final, 0)) "
											+ "FROM t_devoluciones ");

		if (devolucion  != null){
			if(devolucion.getDevolucion()!=null){
				where.append(" AND DEVOLUCION = ? ");
				params.add(devolucion.getDevolucion());	        		
			}

			if(sessionId!=null){
				where.append(" AND IDSESION = ? ");
				params.add(sessionId);	        		
			}
		}

		query.append(where);			

		Double suma = null;

		try {			
			suma = this.jdbcTemplate.queryForObject(query.toString(), Double.class, params.toArray()); 
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return suma;
	}
	
	@Override
	public boolean hayRefsPdtes(String session) throws Exception{
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer("SELECT COUNT(1) "
											+ "FROM t_devoluciones dev "
											+ "WHERE dev.idsesion = ? "
											+ "AND stock_actual   <> 0 "
											+ "AND stock_devuelto IS NULL "
											);

		params.add(session);

		return (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
	}

}
