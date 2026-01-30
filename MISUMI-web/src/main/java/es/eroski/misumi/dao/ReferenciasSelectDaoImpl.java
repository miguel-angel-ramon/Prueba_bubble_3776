/**
 * 
 */
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

import es.eroski.misumi.dao.iface.ReferenciasSelectDao;
import es.eroski.misumi.model.CategoriaBean;
import es.eroski.misumi.model.NumLoteMpGrid;
import es.eroski.misumi.model.QueryReferenciasByDescr;
import es.eroski.misumi.model.ReferenciasByDescr;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

/**
 * Implementacion de consultas para busquedas de referencias por descripcion.
 * P49634
 * @author BICUGUAL
 *
 */
@Repository
public class ReferenciasSelectDaoImpl implements ReferenciasSelectDao {

	private static Logger logger = Logger.getLogger(ReferenciasSelectDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	private RowMapper<ReferenciasByDescr> rwReferenciasByDescrMap = new RowMapper<ReferenciasByDescr>() {
		public ReferenciasByDescr mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReferenciasByDescr ref= new ReferenciasByDescr();
			ref.setCodArticulo(resultSet.getLong("COD_ART"));
			ref.setGrupo1(resultSet.getLong("GRUPO1"));
			ref.setDescripcion(resultSet.getString("DESCRIP_ART"));
			ref.setCatalogo(resultSet.getString("CATALOGO"));
			ref.setActiva(resultSet.getString("ACTIVA"));
			ref.setUnidadesCaja(resultSet.getDouble("UNI_CAJA_SERV"));
			ref.setNivel_lote (resultSet.getLong("NIVEL_LOTE"));
			ref.setNivel_mod_prov(resultSet.getLong("NIVEL_MOD_PROV"));
			ref.setModelo_proveedor(resultSet.getString("MODELO_PROVEEDOR"));
			ref.setNumOrden(resultSet.getString("NUM_ORDEN"));
			return ref;
		}
	};

	private RowMapper<ReferenciasByDescr> rwReferenciasByDescrTextilMap = new RowMapper<ReferenciasByDescr>() {
		public ReferenciasByDescr mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReferenciasByDescr ref= new ReferenciasByDescr();
			ref.setCodArticulo(resultSet.getLong("COD_ART"));
			ref.setGrupo1(resultSet.getLong("GRUPO1"));
			ref.setDescripcion(resultSet.getString("DESCRIP_ART"));
			ref.setCatalogo(resultSet.getString("CATALOGO"));
			ref.setActiva(resultSet.getString("ACTIVA"));
			ref.setUnidadesCaja(resultSet.getDouble("UNI_CAJA_SERV"));
			ref.setNivel_lote (resultSet.getLong("NIVEL_LOTE"));
			ref.setColor(resultSet.getString("DESCR_COLOR"));
			ref.setTalla(resultSet.getString("DESCR_TALLA"));
			ref.setModelo_proveedor(resultSet.getString("MODELO_PROVEEDOR"));
			ref.setNivel_mod_prov(resultSet.getLong("NIVEL_MOD_PROV"));
			ref.setNumOrden(resultSet.getString("NUM_ORDEN"));
			
			return ref;
		}
	};

	private RowMapper<ReferenciasByDescr> rwReferenciasByDescrTextilMap2 = new RowMapper<ReferenciasByDescr>() {
		public ReferenciasByDescr mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReferenciasByDescr ref= new ReferenciasByDescr();
			ref.setCodArticulo(resultSet.getLong("COD_ART"));
			ref.setGrupo1(resultSet.getLong("GRUPO1"));
			ref.setDescripcion(resultSet.getString("DESCRIP_ART"));
			ref.setCatalogo(resultSet.getString("CATALOGO"));
			ref.setActiva(resultSet.getString("ACTIVA"));
			ref.setUnidadesCaja(resultSet.getDouble("UNI_CAJA_SERV"));
			ref.setNivel_lote (resultSet.getLong("NIVEL_LOTE"));
			ref.setColor(resultSet.getString("DESCR_COLOR"));
			ref.setTalla(resultSet.getString("DESCR_TALLA"));
			ref.setModelo_proveedor(resultSet.getString("MODELO_PROVEEDOR"));


			return ref;
		}
	};

	private RowMapper<ReferenciasByDescr> rwTest= new RowMapper<ReferenciasByDescr>() {
		public ReferenciasByDescr mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReferenciasByDescr ref= new ReferenciasByDescr();
			ref.setCodArticulo(resultSet.getLong("COD_ARTICULO"));
			return ref;
		}
	};
	
	private RowMapper<NumLoteMpGrid> rwLotMPGrid= new RowMapper<NumLoteMpGrid>() {
		public NumLoteMpGrid mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			NumLoteMpGrid numLotMpGrid= new NumLoteMpGrid();
			numLotMpGrid.setFilasGrid(resultSet.getLong("FILAS_GRID"));
			numLotMpGrid.setNivelLote(resultSet.getLong("NIVEL_LOTE"));
			numLotMpGrid.setNivelModProv(resultSet.getLong("NIVEL_MOD_PROV"));
			return numLotMpGrid;
		}
	};


	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	/**
	 * P-52083
	 * Refactorizacion de codigo para reutilizar la query principal de busqueda de referencias.
	 * Count de referencias encontradas
	 * @BICUGUAL
	 */
	@Override
	public NumLoteMpGrid findAllCount(QueryReferenciasByDescr filtros) throws Exception {
		List<Object> params = new ArrayList<Object>();    	
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FILAS_GRID, COUNT (*) + NVL(SUM(NIVEL_LOTE),0) NIVEL_LOTE, COUNT (*) + NVL(SUM(NIVEL_MOD_PROV),0) NIVEL_MOD_PROV FROM (");
		
		//Mostramos para las búsquedas de pedido adicional en montajes únicamente las referencias activas. Además, para encargos, solo se mostrarán
		//las de catalogo = A.
		if(filtros != null){
			if(("nuevoPedidoAdicional-montaje").equals(filtros.getPaginaConsulta()) || ("intertienda").equals(filtros.getPaginaConsulta())){
				sql.append( "SELECT * FROM ( ");
				getQueryReferenciasByDescr(filtros, sql, params);
				sql.append( " ) WHERE ACTIVA = 'S' ");
			}else if(("nuevoPedidoAdicional-encargos").equals(filtros.getPaginaConsulta())){
				sql.append( "SELECT * FROM ( ");
				getQueryReferenciasByDescr(filtros, sql, params);
				sql.append( " ) WHERE CATALOGO = 'A' ");
								
				// MISUMI-324: AL BUSCAR REFERENCIAS EN NUEVO PEDIDO ADICIONAL, NO MOSTRAR LAS REFERENCIAS DESCENTRALIZADAS
				final Long codCentro = filtros.getCodCentro();
				sql.append(" AND cod_art not in (SELECT cod_art FROM V_SURTIDO_TIENDA WHERE COD_CENTRO = "+codCentro+" AND tipo_aprov = 'D') ");
				
				//Pet Misumi-70 Mostrar las referencias que tienen mapa de pedido. Contra la vista V_REFERENCIA_ACTIVA2 con COD_CENTRO y COD_ART. Si tiene un mapa con algún día distinto a 0.
				sql.append(" AND   cod_art IN ( "
						+ " SELECT COD_ART "
						+ " FROM "
                        + "    v_referencia_activa2 "
                        + " WHERE "
                        + "    cod_centro = ? "
                        + "    AND   ( "
                        + "       ped_lun != 0 AND ped_lun IS NOT NULL "
                        + "        OR    ped_mar != 0 AND ped_mar IS NOT NULL "
                        + "        OR    ped_mie != 0 AND ped_mie IS NOT NULL "
                        + "        OR    ped_jue != 0 AND ped_jue IS NOT NULL "
                        + "        OR    ped_vie != 0 AND ped_vie IS NOT NULL "
                        + "        OR    ped_sab != 0 AND ped_sab IS NOT NULL "
                        + "        OR    ped_dom != 0 AND ped_dom IS NOT NULL "
                        + "    ) "
                        + " ) " );
				
				params.add(filtros.getCodCentro());
			}else{
				getQueryReferenciasByDescr(filtros, sql, params);
			}
		}
		sql.append(" ) ");

		logger.debug(sql.toString());
		NumLoteMpGrid numLotGrid=null;
		try { 
			List<NumLoteMpGrid> lstNumLotGrid = this.jdbcTemplate.query(sql.toString(), this.rwLotMPGrid, params.toArray());	
			if(lstNumLotGrid != null && lstNumLotGrid.size() > 0){
				numLotGrid = lstNumLotGrid.get(0);
			}
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}
		return numLotGrid; 
	}

	@Override
	public Long findAllCountNivelModProv(QueryReferenciasByDescr filtros) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();    	
		StringBuffer sql = new StringBuffer("SELECT COUNT (*) + NVL(SUM(NIVEL_LOTE),0) FROM (");
		getQueryTextil(sql);
		sql.append(" ) ");

		params.add(filtros.getCodCentro());
		params.add(filtros.getCodArticulo());

		params.add(filtros.getCodCentro());
		params.add(filtros.getCodArticulo());
		params.add(filtros.getCodCentro());

		logger.debug(sql.toString());
		Long count=null;
		try {

			count = this.jdbcTemplate.queryForLong(sql.toString(), params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}
		return count; 
	}


	/**
	 * P-52083
	 * Refactorizacion de codigo para reutilizar la query principal de busqueda de referencias.
	 * Busqueda de referencias
	 * @BICUGUAL
	 */
	@Override
	public List<ReferenciasByDescr> findAll(QueryReferenciasByDescr filtros, Pagination pagination) throws Exception {


		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");

		//Capturo los parametros para sustituir las incongitas
		// Preparo la query Buca registros por modeloProveedor y codigo de ariticulo
		sql = new StringBuffer("SELECT * FROM ( ");        

		//construyo la select desde el metodo
		getQueryReferenciasByDescr(filtros, sql, params);

		sql.append(" ) ");

		//Hay que darle una vuelta a lo de activa con MARÍA Y A LO DE CATALOGO. ESTO ES PROVISIONAL!
		//Mostramos para las búsquedas de pedido adicional en montajes o intertienda y encargos únicamente las referencias activas. Además, para encargos, solo se mostrarán
		//las de catalogo = A.
		if(filtros != null){
			if(("nuevoPedidoAdicional-montaje").equals(filtros.getPaginaConsulta()) || ("intertienda").equals(filtros.getPaginaConsulta())){
				sql.append(" WHERE ACTIVA = 'S' " );
			}else if(("nuevoPedidoAdicional-encargos").equals(filtros.getPaginaConsulta())){
				sql.append(" WHERE CATALOGO = 'A' " );
				
				// MISUMI-324: AL BUSCAR REFERENCIAS EN NUEVO PEDIDO ADICIONAL, NO MOSTRAR LAS REFERENCIAS DESCENTRALIZADAS
				final Long codCentro = filtros.getCodCentro();
				sql.append(" AND cod_art not in (SELECT cod_art FROM V_SURTIDO_TIENDA WHERE COD_CENTRO = "+codCentro+" AND tipo_aprov = 'D') ");
				
				//Pet Misumi-70 Mostrar las referencias que tienen mapa de pedido. Contra la vista V_REFERENCIA_ACTIVA2 con COD_CENTRO y COD_ART. Si tiene un mapa con algún día distinto a 0.
				sql.append(" AND   cod_art IN ( "
						+ " SELECT COD_ART "
						+ " FROM "
                        + "    v_referencia_activa2 "
                        + " WHERE "
                        + "    cod_centro = ? "
                        + "    AND   ( "
                        + "       ped_lun != 0 AND ped_lun IS NOT NULL "
                        + "        OR    ped_mar != 0 AND ped_mar IS NOT NULL "
                        + "        OR    ped_mie != 0 AND ped_mie IS NOT NULL "
                        + "        OR    ped_jue != 0 AND ped_jue IS NOT NULL "
                        + "        OR    ped_vie != 0 AND ped_vie IS NOT NULL "
                        + "        OR    ped_sab != 0 AND ped_sab IS NOT NULL "
                        + "        OR    ped_dom != 0 AND ped_dom IS NOT NULL "
                        + "    ) "
                        + " ) " );
				params.add(filtros.getCodCentro());
                
			}
		}
		//ORDENAMIENTOS
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				sql.append(order);
			}else {
				order.append(" order by DECODE(ACTIVA, 'S', 'A', 'B') asc,  COD_ART asc ");	
				sql.append(order);
			}
		}
		else{
			order.append(" order by DECODE(ACTIVA, 'S', 'A', 'B') asc,  COD_ART asc ");	
			sql.append(order);
		}
		if (pagination != null) {
			sql = new StringBuffer(Paginate.getQueryLimits(pagination, sql.toString()));
		}

		logger.debug(sql.toString());

		//PARA TEXTIL
		List<ReferenciasByDescr> lstReferencias = null;

		try {

			if(filtros.getCodArea()!= null && filtros.getCodArea()== 3){
				lstReferencias=this.jdbcTemplate.query(sql.toString(), this.rwReferenciasByDescrTextilMap, params.toArray());
			}else{
				lstReferencias=this.jdbcTemplate.query(sql.toString(), this.rwReferenciasByDescrMap, params.toArray());	
			}

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}

		return lstReferencias;


	}

	/**
	 * P-52083
	 * Refactorizacion de codigo para reutilizar la query principal de busqueda de referencias.
	 * Diferentes secciones de exitentes en la busqueda de referencias. Utilizada para cargar el combo de secciones 
	 * para filtrar las referencias por seccion. 
	 * @BICUGUAL
	 */
	@Override
	public List<SeccionBean> findLstSecciones(QueryReferenciasByDescr filtros)	throws Exception {

		List<Object> params = new ArrayList<Object>();

		//Construyo la query		

		StringBuffer sql = new StringBuffer("SELECT DISTINCT (GRUPO2) AS codigo, DESCRIPCION, GRUPO1 FROM ");
		sql.append(" (");

		getQueryReferenciasByDescr(filtros, sql, params);

	
		if(("nuevoPedidoAdicional-montaje").equals(filtros.getPaginaConsulta()) || ("intertienda").equals(filtros.getPaginaConsulta())){
			sql.append( " ) WHERE ACTIVA = 'S' ");
		} else {
			sql.append(" )");
		}
		

		sql.append("ORDER BY GRUPO2");


		logger.debug(sql.toString());
		//Ejecuto la sql

		List<SeccionBean> lstSeccionBean = null;
		try {

			lstSeccionBean = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<SeccionBean>() {

				//Mapeo los resultados de la query
				@Override
				public SeccionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
					SeccionBean seccion = new SeccionBean();

					seccion.setCodigo(rs.getLong("CODIGO"));
					seccion.setDescripcion(rs.getString("DESCRIPCION"));
					seccion.setCodArea(rs.getLong("GRUPO1"));

					return seccion;
				}
			});

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}



		return lstSeccionBean;
	}

	/**
	 * P-52083
	 * Refactorizacion de codigo para reutilizar la query principal de busqueda de referencias.
	 * Diferentes secciones de exitentes en la busqueda de referencias. Utilizada para cargar el combo de secciones 
	 * para filtrar las referencias por seccion. 
	 * @BICUGUAL
	 */
	@Override
	public List<CategoriaBean> findLstCategoria(QueryReferenciasByDescr filtros)	throws Exception {

		List<Object> params = new ArrayList<Object>();

		//Construyo la query		

		StringBuffer sql = new StringBuffer("SELECT DISTINCT (GRUPO3) AS codigo,"
				+ " GRUPO1, GRUPO2, "
				+ " (SELECT VAG.DESCRIPCION "
				+ " FROM V_AGRU_COMER_REF VAG "
				+ " WHERE D.GRUPO1 = VAG.GRUPO1 "
				+ " AND D.GRUPO2   = VAG.GRUPO2 "
				+ " AND D.GRUPO3   = VAG.GRUPO3 "
				+ " AND VAG.NIVEL    = 'I3' "
				+ " ) DESCRIPCION_CAT FROM ");
		sql.append(" (");

		getQueryReferenciasByDescr(filtros, sql, params);

		//sql.append(" )");
		//sql.append( " ) D WHERE ACTIVA = 'S' ");
		
		if(("nuevoPedidoAdicional-montaje").equals(filtros.getPaginaConsulta()) || ("intertienda").equals(filtros.getPaginaConsulta())){
			sql.append( " ) D WHERE ACTIVA = 'S' ");
		} else {
			sql.append( " ) D  ");
		}


		sql.append("ORDER BY GRUPO3");


		logger.debug(sql.toString());
		//Ejecuto la sql

		List<CategoriaBean> lstCategoriaBean = null;
		try {

			lstCategoriaBean = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<CategoriaBean>() {

				//Mapeo los resultados de la query
				@Override
				public CategoriaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
					CategoriaBean categoria = new CategoriaBean();

					categoria.setCodCategoria(rs.getLong("CODIGO"));
					categoria.setDescripcionCat(rs.getString("DESCRIPCION_CAT"));
					categoria.setCodArea(rs.getLong("GRUPO1"));
					categoria.setCodSeccion(rs.getLong("GRUPO2"));
					return categoria;
				}
			});

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}
		return lstCategoriaBean;
	}
	
	/**
	 * Metodo que devuleve el nombre de la columna en bd en base al recibido.
	 * 
	 * @param fieldName. Nombre de columna a mapear
	 * @return
	 */
	private String getMappedField(String fieldName) {
		if (fieldName.toUpperCase().equals("CODARTICULO")) {
			return "COD_ART";
		} else if (fieldName.toUpperCase().equals("DESCRIPCION")) {
			return "DESCRIP_ART";
		} else if (fieldName.toUpperCase().equals("ACTIVA")) {
			return "DECODE(ACTIVA, 'S', 'A', 'B') ";
		} else {
			return fieldName;
		}
	}

	@Override
	public List<ReferenciasByDescr> findAllTextilN2ByLote(QueryReferenciasByDescr referenciaByDescr) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT *  FROM ( ");
		getQueryTextil(query);
		query.append(")");

		params.add(referenciaByDescr.getCodCentro());
		params.add(referenciaByDescr.getCodArticulo());
		
		params.add(referenciaByDescr.getCodCentro());
		params.add(referenciaByDescr.getCodArticulo());
		params.add(referenciaByDescr.getCodCentro());
		
		List<ReferenciasByDescr> lista = null;

		try {

			lista = (List<ReferenciasByDescr>) this.jdbcTemplate.query(query.toString(), this.rwReferenciasByDescrTextilMap2,
					params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params,e);
		}

		return lista;
	}

	@Override
	public List<ReferenciasByDescr> findEAN(QueryReferenciasByDescr referenciaByDescr) throws Exception {

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer("SELECT COD_ARTICULO FROM EANS" + " WHERE COD_EAN = ? ");

		params.add(referenciaByDescr.getDescripcion());

		List<ReferenciasByDescr> lista = null;

		try {

			lista = (List<ReferenciasByDescr>) this.jdbcTemplate.query(query.toString(), this.rwTest,
					params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params,e);
		}

		return lista ;

	}  

	@Override
	public List<ReferenciasByDescr> findAllRef(QueryReferenciasByDescr filtros, Pagination pagination) throws Exception {


		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");

		/* SELECT PARA LAS REFERENCIAS */
		sql = new StringBuffer("SELECT * FROM (SELECT "
				+ "   /*+ ordered index (VDA i_v_datos_diario_art_4)  INDEX (VST I_V_REFERENCIA_ACTIVA_1)"
				+ "  index(VST i_v_surtido_tienda_1)*/ "
				+ "  VDA.COD_ART, VDA.GRUPO1, VDA.DESCRIP_ART, VST.CATALOGO, VST.ACTIVA, VST.UNI_CAJA_SERV, "
				+ " (SELECT COUNT(*) FROM V_REFERENCIAS_LOTE_TEXTIL  LT, V_REFERENCIA_ACTIVA VST2 "
				+ " WHERE LT.COD_ARTICULO_LOTE = VDA.COD_ART " + " AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ " AND VST2.COD_CENTRO = VST.COD_CENTRO) NIVEL_LOTE "
				+ " FROM V_DATOS_DIARIO_ART VDA, V_REFERENCIA_ACTIVA VST, V_DATOS_ESPECIFICOS_TEXTIL VDET "
				+ " WHERE VDA.COD_ART = ? " + " AND VST.COD_CENTRO = ? " + " AND VDA.COD_ART = VST.COD_ART "
				+ " AND VDA.COD_ART = VDET.COD_ART (+) " + " AND VDA.COD_ART NOT IN " + "(SELECT cod_articulo_asociado "
				+ "FROM v_referencias_lote_textil VL," + " V_REFERENCIA_ACTIVA VST2"
				+ " WHERE VL.COD_ARTICULO_ASOCIADO = VST2.COD_ART" + " AND VST2.COD_CENTRO = VST.COD_CENTRO ) ");

		// Capturo los parametros para sustituir las incongitas
		params.add(filtros.getDescripcion());
		params.add(filtros.getCodCentro());

		if (filtros.getCodSeccion() != null && filtros.getCodSeccion() != 0) {
			sql.append(" AND VDA.GRUPO2= ? ");
			params.add(filtros.getCodSeccion());
		}

		if (null != filtros.getAltaCatalogo() && filtros.getAltaCatalogo().booleanValue()) {
			sql.append(" AND VST.CATALOGO= 'A' ");
		}

		sql.append(" ) ");
		if (null != filtros.getActivo() && filtros.getActivo().booleanValue()) {
			sql.append(" WHERE ACTIVA= 'S' ");
		}

		// ORDENAMIENTOS
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " " + pagination.getAscDsc());
				sql.append(order);
			} else {
				order.append(" order by DECODE(ACTIVA, 'S', 'A', 'B') asc,  COD_ART asc ");
				sql.append(order);
			}
		} else {
			order.append(" order by DECODE(ACTIVA, 'S', 'A', 'B') asc,  COD_ART asc ");
			sql.append(order);
		}
		if (pagination != null) {
			sql = new StringBuffer(Paginate.getQueryLimits(pagination, sql.toString()));
		}

		logger.debug(sql.toString());


		List<ReferenciasByDescr> lstReferencias = null;

		try {

			lstReferencias = this.jdbcTemplate.query(sql.toString(), this.rwReferenciasByDescrMap,params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}

		return lstReferencias;

	}

	@Override
	public List<Long> findArea(QueryReferenciasByDescr referenciaByDescr) throws Exception {

		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT GRUPO1 FROM V_AGRU_COMER_REF WHERE NIVEL= 'I2' AND GRUPO2 = (?)");

		params.add(referenciaByDescr.getCodSeccion());

		List<Long> area = null;

		try {

			area = this.jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<Long>() {

				//Mapeo los resultados de la query
				@Override
				public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long area;
					area = rs.getLong("GRUPO1");

					return area;
				}
			});

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(sql.toString(), params,e);
		}


		return area;
	}


	/***
	 * P-52083
	 * Constructor de la query que obtiene los registros en base a los filtros
	 * @param filtros
	 * @param sql
	 * @param params
	 * @author BICUGUAL
	 */
	private void getQueryReferenciasByDescr(QueryReferenciasByDescr filtros, StringBuffer sql, List<Object> params ){
		sql.append(" (SELECT /*+ ordered INDEX_COMBINE (VDA I_V_DATOS_DIARIO_ART_4 I_V_DATOS_DIARIO_ART_1) INDEX (VST I_V_REFERENCIA_ACTIVA_1)  */ "                       
				+ "VDA.COD_ART, "
				+ "NULL DESCR_COLOR, "
				+ "NULL DESCR_TALLA, "
				+ "NULL MODELO_PROVEEDOR, "
				+ "NULL NUM_ORDEN, "
				+ "VDA.GRUPO1, "
				+ "VDA.GRUPO2, "
				+ "VDA.GRUPO3, "
				+ "VDA.DESCRIP_ART, "
				+ "VST.CATALOGO, "
				+ "VST.ACTIVA, "
				+ "VST.UNI_CAJA_SERV, "
				+ "0 NIVEL_LOTE, "
				+ "0 NIVEL_MOD_PROV, "       
				+ "(SELECT VAG.DESCRIPCION "
				+ "FROM V_AGRU_COMER_REF VAG "
				+ "WHERE VDA.GRUPO1 = VAG.GRUPO1 "
				+ "AND VDA.GRUPO2 = VAG.GRUPO2 "
				+ "AND VAG.NIVEL = 'I2') DESCRIPCION "
				+ "FROM V_DATOS_DIARIO_ART VDA, ");
				//Para pantalla SFM/FAC
				if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta())){
					sql.append(" V_AGRU_COMER_PARAM_SFMCAP ap, ");// nuevo proceso
				}
				sql.append( "V_REFERENCIA_ACTIVA VST "
				+ "WHERE 1 = 1 ");
				// Compruebo si es alfanumerico o numerico
				if (filtros.getAlfaNumerico()) {

					// Divido la descripcion introducida por si hubiera varias palabras
					String[] arrayDenominacion = filtros.getDescripcion().split(" ");
					for (int i = 0; i < arrayDenominacion.length; i++) {
						if(!arrayDenominacion[i].equals("")){
							sql.append(" AND VDA.DESCRIP_ART_FIND LIKE UPPER (?) ");
							params.add("%" + arrayDenominacion[i] + "%");
						}
					}

				} else {
					sql.append(" AND VDA.COD_ART = (?) ");
					//params.add(filtros.getCodArticulo());
					params.add(filtros.getDescripcion());
				}
				sql.append( "AND VST.COD_CENTRO = (?) "
				+ "AND VDA.COD_ART = VST.COD_ART "
				+ "AND NOT EXISTS "
				+ "(SELECT /*+  INDEX (DT I_V_DATOS_ESPECIFICOS_TEXTIL_1)*/ COD_ART "
				+ "FROM V_DATOS_ESPECIFICOS_TEXTIL DT " 
				+ "WHERE VDA.COD_ART = DT.COD_ART) ");
				//Para nuevo pedido adicional. Pet Misumi-130
				if (null != filtros.getActivo() && filtros.getActivo().booleanValue()) {
					sql.append(" AND ACTIVA= 'S' ");
				}
				//Para pantalla SFM/FAC
				if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta())) {

					sql.append(" AND ( " // para el grupo 1 y 2 sea pedible o para cualquier grupo
							+ " (VST.PEDIR = 'S') " 
							+ " OR " // sino es pedible tiene que ser flg_txl_pedible
							+ " (VDA.grupo1 NOT IN (1,2)" 
							+ " AND VST.flg_txl_pedible = 'S'))"
							+ " AND VST.marca_maestro_centro = 'S' " // -- que sea mmc='S' "
							+ " AND VST.cod_centro = ap.cod_centro " 
							+ " AND VDA.grupo1 = ap.grupo1 "
							+ " AND VDA.grupo2 = ap.grupo2 " + " AND VDA.grupo3 = ap.grupo3 " + " AND VDA.grupo4 = ap.grupo4 "
							+ " AND VDA.grupo5 = ap.grupo5 " + " AND ap.nivel = 'I5' " + " AND (ap.flg_stock_final = 'S' "
							+ " OR ap.flg_capacidad   = 'S' " + " OR ap.flg_facing_centro = 'S' "
							+ " OR ap.flg_fac_capacidad = 'S') ");
				}
				if (null != filtros.getAltaCatalogo() && filtros.getAltaCatalogo().booleanValue()) {
					sql.append(" AND VST.CATALOGO= 'A' ");
				}
		
				
				//Si tiene centro relacionado, se busca por ese centro.
				if(filtros.getCodCentroRelacionado() != null){
					params.add(filtros.getCodCentroRelacionado());
				}else{
					params.add(filtros.getCodCentro());					
				}

		
		if (filtros.getCodSeccion() != null && filtros.getCodSeccion() != 0) {
			sql.append(" AND VDA.GRUPO2= ? ");
			params.add(filtros.getCodSeccion());
		}
		if(filtros.getFlgCategoria() != null && ("S").equals(filtros.getFlgCategoria())){
			sql.append(" AND VDA.GRUPO1 = ? ");
			params.add(filtros.getCodArea());
		}
		if(filtros.getCodCategoria() != null && filtros.getCodCategoria() != 0){
			sql.append(" AND VDA.GRUPO3 = ? ");
			params.add(filtros.getCodCategoria());
		}
		
		sql.append(" ) UNION "); 
		//Segunda union
		sql.append("(SELECT "
				+ " /*+ ordered INDEX_COMBINE (VDA I_V_DATOS_DIARIO_ART_4 I_V_DATOS_DIARIO_ART_1) INDEX (VST I_V_REFERENCIA_ACTIVA_1) index(VDET I_V_DATOS_ESPECIFICOS_TEXTIL_1) index(VST i_v_surtido_tienda_1) */ "
				+ " VDA.COD_ART,"
				+ " VDET.DESCR_COLOR,"
				+ " VDET.DESCR_TALLA,"
				+ " VDET.MODELO_PROVEEDOR,"
				+ " VDET.NUM_ORDEN,"
				+ " VDA.GRUPO1,"
				+ " VDA.GRUPO2,"
				+ " VDA.GRUPO3,"
				+ " VDA.DESCRIP_ART, "
				+ " VST.CATALOGO,"
				+ "  VST.ACTIVA,"
				+ "  VST.UNI_CAJA_SERV, "
				+ " (SELECT COUNT(*)"
				+ "     FROM V_REFERENCIAS_LOTE_TEXTIL LT,"
				+ "          V_REFERENCIA_ACTIVA VST2 "
				+ "     WHERE LT.COD_ARTICULO_LOTE   = VDA.COD_ART " 
				+ "     AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ "     AND VST2.COD_CENTRO = VST.COD_CENTRO )  NIVEL_LOTE, "
				+ " (SELECT /*+ index (vdet2 I_V_DATOS_ESPECIFICOS_TEXTIL_2)*/ COUNT(*)  "
				+ "     FROM V_DATOS_ESPECIFICOS_TEXTIL VDET2, "
				+ "          V_REFERENCIA_ACTIVA VST2"
				+ "     WHERE VDET2.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR "
				+ "		AND VDET2.NUM_ORDEN = VDET.NUM_ORDEN "
				+ "     AND VST2.COD_CENTRO = VST.COD_CENTRO"
				+ "     AND VDET2.TIPO_REFERENCIA <> 'Lote' "
				+ "     AND VDET2.COD_ART = VST2.COD_ART "
				+ "     AND VDET2.LOTE = 'N' "
				+ "     ) NIVEL_MOD_PROV, "
				+ " (SELECT vag.DESCRIPCION  "
				+ "      FROM V_AGRU_COMER_REF vag "
				+ "      WHERE VDA.GRUPO1 = vag.GRUPO1"
				+ "      AND VDA.GRUPO2   = vag.GRUPO2 "
				+ "      AND vag.NIVEL    = 'I2' ) DESCRIPCION "
				+ "      FROM V_DATOS_DIARIO_ART         VDA, "
				+ "           V_REFERENCIA_ACTIVA        VST, ");

		//Para pantalla SFM/FAC
		if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta()))
			sql.append(" V_AGRU_COMER_PARAM_SFMCAP ap, ");// nuevo proceso

		sql.append(" V_DATOS_ESPECIFICOS_TEXTIL VDET " 
			   + " WHERE 1=1 "); 

		// Compruebo si es alfanumerico o numerico
		if (filtros.getAlfaNumerico()) {

			// Divido la descripcion introducida por si hubiera varias palabras
			String[] arrayDenominacion = filtros.getDescripcion().split(" ");
			for (int i = 0; i < arrayDenominacion.length; i++) {
				if(!arrayDenominacion[i].equals("")){
					sql.append(" AND VDA.DESCRIP_ART_FIND LIKE UPPER (?) ");
					params.add("%" + arrayDenominacion[i] + "%");
				}
			}

		} else {
			sql.append(" AND VDA.COD_ART = (?) ");
			//params.add(filtros.getCodArticulo());
			params.add(filtros.getDescripcion());
		}

		sql.append(" AND VST.COD_CENTRO = (?) ");
		// Añado parametro para la incognita
		
		//Si tiene centro relacionado, se busca por ese centro.
		if(filtros.getCodCentroRelacionado() != null){
			params.add(filtros.getCodCentroRelacionado());
		}else{
			params.add(filtros.getCodCentro());					
		}

		//Para pantalla SFM/FAC
		if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta())) {

			sql.append(" AND ( " // para el grupo 1 y 2 sea pedible o para cualquier grupo
					+ " (VST.PEDIR = 'S') " 
					+ " OR " // sino es pedible tiene que ser flg_txl_pedible
					+ " (VDA.grupo1 NOT IN (1,2)" 
					+ " AND VST.flg_txl_pedible = 'S'))"
					+ " AND VST.marca_maestro_centro = 'S' " // -- que sea mmc='S' "
					+ " AND VST.cod_centro = ap.cod_centro " 
					+ " AND VDA.grupo1 = ap.grupo1 "
					+ " AND VDA.grupo2 = ap.grupo2 " + " AND VDA.grupo3 = ap.grupo3 " + " AND VDA.grupo4 = ap.grupo4 "
					+ " AND VDA.grupo5 = ap.grupo5 " + " AND ap.nivel = 'I5' " + " AND (ap.flg_stock_final = 'S' "
					+ " OR ap.flg_capacidad   = 'S' " + " OR ap.flg_facing_centro = 'S' "
					+ " OR ap.flg_fac_capacidad = 'S') ");
		}

		sql.append(" AND VDA.COD_ART = VST.COD_ART " 
				+ " AND VDA.COD_ART = VDET.COD_ART " 
				+ " AND VDET.LOTE = 'N' ");

		//params.add(filtros.getCodCentro());

		if (filtros.getAlfaNumerico()) {
		sql.append(" AND (VDET.TIPO_REFERENCIA = 'Lote' "
				+ " OR "
				+ " VDA.COD_ART IN "
				+ " (SELECT /*+  INDEX (VDET2 I_V_DATOS_ESPECIFICOS_TEXTIL_2)*/ MIN(VDET2.COD_ART) "
				+ " FROM V_DATOS_ESPECIFICOS_TEXTIL VDET2, "
				+ "      V_REFERENCIA_ACTIVA VST2 "
				+ " WHERE VDET2.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR "
				+ "	AND VDET2.NUM_ORDEN = VDET.NUM_ORDEN "
				+ " AND TIPO_REFERENCIA <> 'Lote' "
				+ " AND VST2.COD_CENTRO = VST.COD_CENTRO "
				+ " AND VDET2.COD_ART = VST2.COD_ART"
				+ " AND vdet2.lote = 'N')) ");
		}

		if(filtros.getFlgCategoria() != null && ("S").equals(filtros.getFlgCategoria())){
			sql.append(" AND VDA.GRUPO1 = ? ");
			params.add(filtros.getCodArea());
		}
		
		if (null != filtros.getAltaCatalogo() && filtros.getAltaCatalogo().booleanValue()) {
			sql.append(" AND VST.CATALOGO= 'A' ");
		}

		if (null != filtros.getActivo() && filtros.getActivo().booleanValue()) {
			sql.append(" AND ACTIVA= 'S' ");
		}
		// añado mas filtros
		if (filtros.getCodSeccion() != null && filtros.getCodSeccion() != 0) {

			sql.append(" AND VDA.GRUPO2= ? ");
			params.add(filtros.getCodSeccion());
		}
		if(filtros.getCodCategoria() != null && filtros.getCodCategoria() != 0){
			sql.append(" AND VDA.GRUPO3 = ? ");
			params.add(filtros.getCodCategoria());
		}

		if (!filtros.getAlfaNumerico()) {
			sql.append(" UNION " 
					+ " (SELECT /*+ ordered INDEX(LT V_REFERENCIAS_LOTE_TEXTIL_I2)"
					+ " index (VDA i_v_datos_diario_art_1)  INDEX (VST I_V_REFERENCIA_ACTIVA_1)"
					+ " index(VST i_v_surtido_tienda_1)*/"
					+ " VDA.COD_ART, VDET.DESCR_COLOR, VDET.DESCR_TALLA, VDET.MODELO_PROVEEDOR,  VDET.NUM_ORDEN, "
					+ " VDA.GRUPO1, VDA.GRUPO2, VDA.GRUPO3, VDA.DESCRIP_ART, VST.CATALOGO, " + " VST.ACTIVA, VST.UNI_CAJA_SERV, "
					+ " (SELECT /*+ index (lt V_REFERENCIAS_LOTE_TEXTIL_I1) */  COUNT(*) " + " FROM V_REFERENCIAS_LOTE_TEXTIL LT, V_REFERENCIA_ACTIVA VST2 "
					+ " WHERE LT.COD_ARTICULO_LOTE   = VDA.COD_ART " + " AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
					+ " AND VST2.COD_CENTRO = VST.COD_CENTRO " + " ) NIVEL_LOTE, "
			        + " (SELECT "
			        + "       /*+ index (lt V_REFERENCIAS_LOTE_TEXTIL_I1) */ "
			        + "        COUNT(*) "
			        + "      FROM V_DATOS_ESPECIFICOS_TEXTIL VDET2, "
			        + "        V_REFERENCIA_ACTIVA VST2 "
			        + "      WHERE VDET2.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR "
			        + "		 AND VDET2.NUM_ORDEN = VDET.NUM_ORDEN "
			        + "      AND VST2.COD_CENTRO          = VST.COD_CENTRO "
			        + "  AND VDET2.TIPO_REFERENCIA   <> 'Lote' "
			        + "      AND VDET2.COD_ART            = VST2.COD_ART "
			        + "      AND VDET2.LOTE = 'N' "
			        + "       ) NIVEL_MOD_PROV, "
					+ " (SELECT vag.DESCRIPCION  FROM V_AGRU_COMER_REF vag "
					+ " WHERE VDA.GRUPO1 = vag.GRUPO1 AND VDA.GRUPO2   = vag.GRUPO2 "
					+ " AND vag.NIVEL    = 'I2' ) DESCRIPCION " + " FROM V_REFERENCIAS_LOTE_TEXTIL LT, "
					+ " V_DATOS_DIARIO_ART VDA, V_REFERENCIA_ACTIVA VST, ");

			//Para pantalla SFM/FAC
			if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta()))
				sql.append(" V_AGRU_COMER_PARAM_SFMCAP ap, ");//nuevo proceso 

			sql.append(" V_DATOS_ESPECIFICOS_TEXTIL VDET "
					+ " WHERE 1 = 1 " + " AND LT.COD_ARTICULO_ASOCIADO=(?) ");

			// Añado parametro a la incognita
			//params.add(filtros.getCodArticulo());
			params.add(filtros.getDescripcion());

			sql.append(" AND LT.COD_ARTICULO_LOTE = VDA.COD_ART" + " AND VST.COD_CENTRO = (?)");

			// Añado parametro para la incognita
			
			//Si tiene centro relacionado, se busca por ese centro.
			if(filtros.getCodCentroRelacionado() != null){
				params.add(filtros.getCodCentroRelacionado());
			}else{
				params.add(filtros.getCodCentro());					
			};

			//Para pantalla SFM/FAC
			if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta())){

				sql.append(" AND ( " + //para el grupo 1 y 2 sea pedible o para cualquier grupo
						" (VST.PEDIR = 'S') " +                               
						" OR "+//sino es pedible tiene que ser flg_txl_pedible
						" (VDA.grupo1 NOT IN (1,2)" +
						" AND VST.flg_txl_pedible = 'S'))" +
						" AND VST.marca_maestro_centro = 'S' "+ // -- que sea mmc='S' "                        
						" AND VST.cod_centro = ap.cod_centro "+
						" AND VDA.grupo1 = ap.grupo1 " +
						" AND VDA.grupo2 = ap.grupo2 " +
						" AND VDA.grupo3 = ap.grupo3 " +
						" AND VDA.grupo4 = ap.grupo4 " +
						" AND VDA.grupo5 = ap.grupo5 " +
						" AND ap.nivel = 'I5' " +
						" AND (ap.flg_stock_final = 'S' " +
						" OR ap.flg_capacidad   = 'S' " +
						" OR ap.flg_facing_centro = 'S' " +
						" OR ap.flg_fac_capacidad = 'S') ");
			}

			sql.append(" AND VDA.COD_ART    = VST.COD_ART" + " AND VDA.COD_ART    = VDET.COD_ART(+))");
		}

		sql.append(" ) UNION  "
				+ " (SELECT  "
				+ " /*+ ordered INDEX (VDET I_V_DATOS_ESPECIFICOS_TEXTIL_2) index (VDA i_v_datos_diario_art_1)  INDEX (VST I_V_REFERENCIA_ACTIVA_1) "
				+ " index(VST i_v_surtido_tienda_1)*/ "
				+ " VDA.COD_ART,"
				+ " VDET.DESCR_COLOR,"
				+ " VDET.DESCR_TALLA,"
				+ " VDET.MODELO_PROVEEDOR,"
				+ " VDET.NUM_ORDEN,"
				+ " VDA.GRUPO1,"
				+ " VDA.GRUPO2,"
				+ " VDA.GRUPO3,"
				+ " VDA.DESCRIP_ART,"
				+ " VST.CATALOGO,"
				+ " VST.ACTIVA,"
				+ " VST.UNI_CAJA_SERV, "
				+ " (SELECT /*+ index (lt V_REFERENCIAS_LOTE_TEXTIL_I1) */  COUNT(*)"
				+ "    FROM V_REFERENCIAS_LOTE_TEXTIL LT,"
				+ "         V_REFERENCIA_ACTIVA VST2 "
				+ "    WHERE LT.COD_ARTICULO_LOTE = VDET.COD_ART " 
				+ "    AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ "    AND VST2.COD_CENTRO = VST.COD_CENTRO) NIVEL_LOTE, "
				+ " (SELECT /*+ INDEX (VDET2 I_V_DATOS_ESPECIFICOS_TEXTIL_2) */ COUNT(*)"
				+ "   FROM V_DATOS_ESPECIFICOS_TEXTIL VDET2,"
				+ "        V_REFERENCIA_ACTIVA VST2"
				+ "   WHERE VDET2.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR"
				+ "	  AND VDET2.NUM_ORDEN = VDET.NUM_ORDEN "
				+ "   AND VST2.COD_CENTRO = VST.COD_CENTRO"
				+ "   AND VDET2.TIPO_REFERENCIA <> 'Lote'"
				+ " AND VDET2.LOTE = 'N' "
				+ "       AND VDET2.COD_ART = VST2.COD_ART) NIVEL_MOD_PROV,"
				+ "   (SELECT vag.DESCRIPCION  "
				+ "      FROM V_AGRU_COMER_REF vag "
				+ "      WHERE VDA.GRUPO1 = vag.GRUPO1 "
				+ "      AND VDA.GRUPO2   = vag.GRUPO2 "
				+ "      AND vag.NIVEL    = 'I2' ) DESCRIPCION " 
				+ "     FROM V_DATOS_ESPECIFICOS_TEXTIL VDET, "
				+ "          V_DATOS_DIARIO_ART         VDA, "); 

		//				//Para pantalla SFM/FAC
		if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta()))
			sql.append(" V_AGRU_COMER_PARAM_SFMCAP ap, ");//nuevo proceso 

		sql.append(" V_REFERENCIA_ACTIVA        VST "
				+ " WHERE VST.COD_CENTRO = (?) ");

		// Añado parametro para la incognita
		//Si tiene centro relacionado, se busca por ese centro.
		if(filtros.getCodCentroRelacionado() != null){
			params.add(filtros.getCodCentroRelacionado());
		}else{
			params.add(filtros.getCodCentro());					
		}
		
		sql.append(" AND VDET.COD_ART = VST.COD_ART " + " AND VDET.COD_ART = VDA.COD_ART "
				+ " AND VDET.MODELO_PROVEEDOR = UPPER (?) ");

		// Añado parametro a la incognita
		params.add(filtros.getDescripcion());

		//Para pantalla SFM/FAC
		if ("informacionSfm".equalsIgnoreCase(filtros.getPaginaConsulta())){

			sql.append(" AND ( " + //para el grupo 1 y 2 sea pedible o para cualquier grupo
					" (VST.PEDIR = 'S') " +                               
					" OR "+//sino es pedible tiene que ser flg_txl_pedible
					" (VDA.grupo1 NOT IN (1,2)" +
					" AND VST.flg_txl_pedible = 'S'))" +
					" AND VST.marca_maestro_centro = 'S' "+ // -- que sea mmc='S' "                        
					" AND VST.cod_centro = ap.cod_centro "+
					" AND VDA.grupo1 = ap.grupo1 " +
					" AND VDA.grupo2 = ap.grupo2 " +
					" AND VDA.grupo3 = ap.grupo3 " +
					" AND VDA.grupo4 = ap.grupo4 " +
					" AND VDA.grupo5 = ap.grupo5 " +
					" AND ap.nivel = 'I5' " +
					" AND (ap.flg_stock_final = 'S' " +
					" OR ap.flg_capacidad   = 'S' " +
					" OR ap.flg_facing_centro = 'S' " +
					" OR ap.flg_fac_capacidad = 'S') ");
		}

		sql.append(" AND VDET.LOTE = 'N' "
				+ " AND (VDET.TIPO_REFERENCIA = 'Lote' OR"
				+ " VDA.COD_ART IN "
				+ " (SELECT MIN(VDET2.COD_ART) "
				+ " FROM V_DATOS_ESPECIFICOS_TEXTIL VDET2,"
				+ "      V_REFERENCIA_ACTIVA VST2"
				+ " WHERE VDET2.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR"
				+ "	AND VDET2.NUM_ORDEN = VDET.NUM_ORDEN "
				+ " AND VST2.COD_CENTRO = VST.COD_CENTRO"
				+ " AND VDET2.COD_ART = VST2.COD_ART"
				+ " AND VDET2.TIPO_REFERENCIA <> 'Lote'"
				+ " AND VDET2.LOTE = 'N' ");

		// Añado parametro para la incognita
		//params.add(filtros.getCodCentro());

		// Añado mas filtros
		if (filtros.getCodSeccion() != null && filtros.getCodSeccion() != 0) {
			sql.append(" AND VDA.GRUPO2= ? ");
			params.add(filtros.getCodSeccion());
		}

		if (null != filtros.getAltaCatalogo() && filtros.getAltaCatalogo().booleanValue()) {
			sql.append(" AND VST.CATALOGO= 'A' ");
		}

		if (null != filtros.getActivo() && filtros.getActivo().booleanValue()) {
			sql.append(" AND ACTIVA= 'S' ");
		}
		if(filtros.getFlgCategoria() != null && ("S").equals(filtros.getFlgCategoria())){
			sql.append(" AND VDA.GRUPO1 = ? ");
			params.add(filtros.getCodArea());
		}
		if(filtros.getCodCategoria() != null && filtros.getCodCategoria() != 0){
			sql.append(" AND VDA.GRUPO3 = ? ");
			params.add(filtros.getCodCategoria());
		}
		sql.append(" ))) ");


	}

	private void getQueryTextil(StringBuffer sql){
		sql.append( "(SELECT "
				+ "   VDA.COD_ART, "
				+ "   VDA.GRUPO1, "
				+ "   VDA.DESCRIP_ART, "
				+ "   VST.CATALOGO, "
				+ "   VST.ACTIVA, "
				+ "   VST.UNI_CAJA_SERV, "
				+ "   VDET.DESCR_COLOR, "
				+ "   VDET.DESCR_TALLA, "
				+ "   VDET.MODELO_PROVEEDOR, "
				+ "   (SELECT COUNT(*) "
				+ "    FROM V_REFERENCIAS_LOTE_TEXTIL LT, "
				+ "         V_REFERENCIA_ACTIVA VST2 "
				+ "   WHERE LT.COD_ARTICULO_LOTE   = VDA.COD_ART "
				+ "   AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ "   AND VST2.COD_CENTRO          = VST.COD_CENTRO "
				+ "   ) NIVEL_LOTE, "
				+ "   VL.COD_ARTICULO_LOTE, "
				+ "   'L' TIPO "
				+ " FROM V_REFERENCIAS_LOTE_TEXTIL VL, "
				+ "   V_DATOS_DIARIO_ART VDA, "
				+ "   V_REFERENCIA_ACTIVA VST, "
				+ "   V_DATOS_ESPECIFICOS_TEXTIL VDET "
				+ " WHERE VST.COD_CENTRO         = ? "
				+ " AND VL.COD_ARTICULO_LOTE     = ? "
				+ " AND VL.COD_ARTICULO_ASOCIADO = VDA.COD_ART "
				+ " AND VDA.COD_ART              = VDET.COD_ART(+) "
				+ " AND VDA.COD_ART              = VST.COD_ART "
				+ " ) "
				+ "UNION "
				+ " (SELECT VDA.COD_ART, "
				+ "   VDA.GRUPO1, "
				+ "   VDA.DESCRIP_ART, "
				+ "   VST.CATALOGO, "
				+ "   VST.ACTIVA, "
				+ "   VST.UNI_CAJA_SERV, "
				+ "   VDET.DESCR_COLOR, "
				+ "   VDET.DESCR_TALLA, "
				+ "   VDET.MODELO_PROVEEDOR, "
				+ "   (SELECT COUNT(*) "
				+ "   FROM V_REFERENCIAS_LOTE_TEXTIL LT, "
				+ "        V_REFERENCIA_ACTIVA VST2 "
				+ "   WHERE LT.COD_ARTICULO_LOTE   = VDA.COD_ART "
				+ "   AND LT.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ "   AND VST2.COD_CENTRO          = VST.COD_CENTRO "
				+ "   ) NIVEL_LOTE, "
				+ "   0 COD_ARTICULO_LOTE, "
				+ "   'M' TIPO "
				+ "  FROM V_DATOS_DIARIO_ART VDA, "
				+ "    V_REFERENCIA_ACTIVA VST, "
				+ "    V_DATOS_ESPECIFICOS_TEXTIL VDET, "
				+ "    V_DATOS_ESPECIFICOS_TEXTIL VDETO "
				+ "  WHERE VST.COD_CENTRO       = ? "
				+ "  AND VDETO.COD_ART          = ? "
				+ "  AND VDETO.MODELO_PROVEEDOR = VDET.MODELO_PROVEEDOR "
				+ "  AND VDETO.NUM_ORDEN = VDET.NUM_ORDEN "
				+ "  AND VDET.COD_ART           = VDA.COD_ART "
				+ "  AND VDET.TIPO_REFERENCIA  <> 'Lote' "
				+ "  AND VDET.COD_ART           = VST.COD_ART "
				+ "  AND NOT EXISTS "
				+ "    (SELECT 'X' "
				+ "    FROM V_REFERENCIAS_LOTE_TEXTIL VL, "
				+ "      V_REFERENCIA_ACTIVA VST2 "
				+ "     WHERE VL.COD_ARTICULO_ASOCIADO = VST2.COD_ART "
				+ "     AND VST2.COD_CENTRO            = VST.COD_CENTRO "
				+ "     AND VST2.COD_CENTRO            = ? "
				+ "     AND VL.COD_ARTICULO_ASOCIADO   = VDA.COD_ART "
				+ "   ) "
				+ "  )");
	}
}
