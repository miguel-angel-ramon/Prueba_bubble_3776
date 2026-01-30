package es.eroski.misumi.dao;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaPortType;
import es.eroski.misumi.dao.facingVegalsaWS.FacingVegalsaServiceLocator;
import es.eroski.misumi.dao.iface.FacingVegalsaDao;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ConsultaFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaRequestType;
import es.eroski.misumi.model.facingVegalsaWS.ModificarFacingVegalsaResponseType;
import es.eroski.misumi.model.facingVegalsaWS.ReferenciaModType;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.model.FacingVegalsaRequest;
import es.eroski.misumi.model.FacingVegalsaResponse;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Utilidades;

@Repository
public class FacingVegalsaDaoImpl implements FacingVegalsaDao{
	@Value( "${ws.facingVegalsa}" )
	private String facingVegalsa;

	private JdbcTemplate jdbcTemplate;
	
	private static Logger logger = Logger.getLogger(FacingVegalsaDaoImpl.class);
	
	private RowMapper<FacingVegalsaResponse> mapperFacingVegalsaResponse = new RowMapper<FacingVegalsaResponse>() {

		public FacingVegalsaResponse mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			final String area = resultSet.getString("AREA");
			final String seccion = resultSet.getString("SECCION");
			final String categoria = resultSet.getString("CATEGORIA");
			final String subcategoria = resultSet.getString("SUBCATEGORIA");
			final String segmento = resultSet.getString("SEGMENTO");
			
			final String descArea = resultSet.getString("DESC_AREA");
			final String descSeccion = resultSet.getString("DESC_SECCION");
			final String descCategoria = resultSet.getString("DESC_CATEGORIA");
			final String descSubcategoria = resultSet.getString("DESC_SUBCATEGORIA");
			final String descSegmento = resultSet.getString("DESC_SEGMENTO");
			
			final String referencia = resultSet.getString("REFERENCIA");
			final String denominacion = resultSet.getString("DENOMINACION");
			final String marca = resultSet.getString("MARCA");
			final String uc = resultSet.getString("UC");
			final Double stock = resultSet.getDouble("STOCK");
			final String mmc = resultSet.getString("MMC");
			final String catalogo = resultSet.getString("CATALOGO");
			final String capacidad = resultSet.getString("CAPACIDAD");
			final String capacidadPlano = resultSet.getString("CAPACIDAD_PLANO");
			final String facing = resultSet.getString("FACING");
			final String facingPlano = resultSet.getString("FACING_PLANO");
			final String fondo = resultSet.getString("FONDO");

			final String cc = resultSet.getString("cc");
			final String reapro = resultSet.getString("reapro");
			final String ufp = resultSet.getString("ufp");
			final String tipoAprov = resultSet.getString("tipoAprov");
			final String ffpp = resultSet.getString("ffpp");
			final String numOferta = resultSet.getString("numOferta");
			final String mapa = resultSet.getString("mapa");
			
			FacingVegalsaResponse output = new FacingVegalsaResponse();
			
			output.setArea(area+"-"+descArea);
			output.setSeccion(seccion+"-"+descSeccion);
			output.setCategoria(categoria+"-"+descCategoria);
			output.setSubcategoria(subcategoria+"-"+descSubcategoria);
			output.setSegmento(segmento+"-"+descSegmento);
			
			output.setReferencia(referencia);
			output.setDenominacion(denominacion);
			output.setMarca(marca);
			output.setUc(uc);
			output.setStock(stock);
			output.setMmc(mmc);
			output.setCatalogo(catalogo);
			if (capacidadPlano!=null){
				output.setCapacidad(capacidadPlano);
			}else{
				output.setCapacidad(capacidad);
			}
			if (facingPlano!=null){
				output.setFacing(facingPlano);
			}else{
				output.setFacing(facing);
			}
			output.setFondo(fondo);
			
			output.setCc(cc);
			output.setReapro(("S".toUpperCase().equalsIgnoreCase(reapro))?"N":"S");
			output.setUfp(ufp);
			output.setTipoAprov(tipoAprov);
			output.setFFPP(ffpp);
			output.setNumeroOferta(numOferta);

			if (mapa!=null){
				output.setMapa(mapa);
			}else{
				output.setMapa("");
			}


			return output;
		}

	};

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<FacingVegalsaResponse> findAll(FacingVegalsaRequest request, Pagination pagination) throws Exception {
		List<Object> params = new ArrayList<Object>();

		String query = "SELECT b.grupo1 AS area"
						  + ", b.grupo2 AS seccion"
						  + ", b.grupo3 AS categoria"
						  + ", b.grupo4 AS subcategoria"
						  + ", b.grupo5 AS segmento"
						  + ", a.cod_art AS referencia"
						  + ", b.descrip_art AS denominacion"
						  + ", (SELECT descripcion "
							 + "FROM marcas "
							 + "WHERE cod_marca = b.cod_marca"
							 + ") AS marca"
						  + ", a.uc"
						  + ", 0 as stock"
						  + ", A.MARCA_MAESTRO_CENTRO AS mmc"
						  + ", a.catalogo_cen AS catalogo"
						  + ", a.capacidad"
						  + ", a.facing"
						  + ", a.fondo"
						  + ", (SELECT p.stock_min_comer "
							 + "FROM v_planograma p "
							 + "WHERE p.cod_centro 	= a.cod_centro "
							 + "AND p.cod_art 	 	= a.cod_art "
							 + "AND EXISTS (SELECT 's' "
							 			 + "FROM v_referencias_pedir_sia r "
							   			 + "WHERE p.cod_centro  = r.cod_centro "
							   			 + "AND p.cod_art 		= r.cod_art"
							   			 + ") "
							 + "AND P.ANO_OFERTA_PILADA = '0000'"
							 + ") AS facing_plano"
						  + ", (SELECT p.capacidad_max "
							 + "FROM V_PLANOGRAMA P "
							 + "WHERE p.cod_centro 	= a.cod_centro "
							 + "AND p.cod_art 		= a.cod_art "
							 + "AND EXISTS (SELECT 's' "
							   			 + "FROM V_REFERENCIAS_PEDIR_SIA r "
							   			 + "WHERE p.cod_centro = r.cod_centro "
							   			 + "AND p.cod_art 	   = r.cod_art"
							   			 + ") "
							   + "AND P.ANO_OFERTA_PILADA = '0000'"
							  + ") AS capacidad_plano"
							+ ", (SELECT DESCRIPCION FROM v_agru_comer_ref WHERE UPPER(NIVEL) IN ('I1') AND GRUPO1 = B.GRUPO1) AS DESC_AREA"
							+ ", (SELECT DESCRIPCION FROM v_agru_comer_ref WHERE UPPER(NIVEL) IN ('I2') AND GRUPO1 = B.GRUPO1 AND GRUPO2 = B.GRUPO2) AS DESC_SECCION"
							+ ", (SELECT DESCRIPCION FROM v_agru_comer_ref WHERE UPPER(NIVEL) IN ('I3') AND GRUPO1 = B.GRUPO1 AND GRUPO2 = B.GRUPO2 AND GRUPO3 = B.GRUPO3) AS DESC_CATEGORIA"
							+ ", (SELECT DESCRIPCION FROM v_agru_comer_ref WHERE UPPER(NIVEL) IN ('I4') AND GRUPO1 = B.GRUPO1 AND GRUPO2 = B.GRUPO2 AND GRUPO3 = B.GRUPO3 AND GRUPO4 = B.GRUPO4) AS DESC_SUBCATEGORIA"
							+ ", (SELECT DESCRIPCION FROM v_agru_comer_ref WHERE UPPER(NIVEL) IN ('I5') AND GRUPO1 = B.GRUPO1 AND GRUPO2 = B.GRUPO2 AND GRUPO3 = B.GRUPO3 AND GRUPO4 = B.GRUPO4 AND GRUPO5 = B.GRUPO5) AS DESC_SEGMENTO "
							+ ", a.cc"
							+ ", a.reapro"
							+ ", a.ufp"
							+ ", a.tipo_aprov AS tipoAprov"
							+ ", (SELECT rea.cod_art||'-'||rea.descrip_art FROM v_relacion_articulo rea WHERE rea.cod_centro = a.cod_centro AND rea.cod_art = a.cod_art ) AS ffpp"
							+ ", (SELECT ofv.ano_oferta||'-'||ofv.num_oferta FROM oferta_vigente ofv WHERE ofv.cod_centro = a.cod_centro AND ofv.cod_art = a.cod_art) AS numOferta"
							+ ", (SELECT mav.cod_mapa||'-'||mav.desc_mapa FROM t_mis_mapas_vegalsa mav WHERE mav.cod_art = a.cod_art) AS mapa "
					+ "FROM t_mis_surtido_vegalsa a, v_datos_diario_art b "
					+ "WHERE a.cod_art = b.cod_art ";
		
		if (request != null) {
			final String centro = request.getCentro();
			final String area = request.getArea();
			final String seccion = request.getSeccion();
			final String categoria = request.getCategoria();
			final String subcategoria = request.getSubcategoria();
			final String segmento = request.getSegmento();
			final String mmc = request.getMmc();
			final String catalogo = request.getCatalogo();
			final String tipoAprov = request.getTipoAprov();
			final Boolean facing0 = request.getFacing0();
			final Boolean alimentado = request.getAlimentado();
			final Boolean conStock = request.getConStock();
			final Boolean gama = request.getGama();

			if (centro != null) {
				query+=" AND A.COD_CENTRO = ? ";
				params.add(centro);
			}
			if (area != null) {
				query+=" AND B.GRUPO1 = ? ";
				params.add(area);
			}
			if (seccion != null) {
				query+=" AND B.GRUPO2 = ? ";
				params.add(seccion);
			}
			if (categoria != null) {
				query+=" AND B.GRUPO3 = ? ";
				params.add(categoria);
			}
			if (subcategoria != null) {
				query+=" AND B.GRUPO4 = ? ";
				params.add(subcategoria);
			}
			if (segmento != null) {
				query+=" AND B.GRUPO5 = ? ";
				params.add(segmento);
			}
			if (mmc != null) {
				query+=" AND A.MARCA_MAESTRO_CENTRO = ? ";
				params.add(mmc);
			}
			if (catalogo != null) {
				query+=" AND A.CATALOGO_CEN = ? ";
				params.add(catalogo);
			}
			if (tipoAprov != null) {
				query+=" AND A.TIPO_APROV = ? ";
				params.add(tipoAprov);
			}
			if (facing0) {
				query+=" AND A.FACING = 0 ";
			}
			//AQUI ES DONDE HAY Q CAMBIAR LA QUERY DEPENDIENDO DE LOS CHECKS SELECCIONADOS EN LA PANTALLA
			if (alimentado) {
				query+=" AND A.FONDO > 0"; 
			}
			if (gama) {
				query+=" AND A.MARCA_MAESTRO_CENTRO = 'S' AND A.REAPRO =  'S' AND A.UFP = 0  AND "
						+ "NOT EXISTS (SELECT 'X' FROM V_RELACION_ARTICULO R WHERE R.COD_CENTRO=A.COD_CENTRO AND (R.COD_ART=A.COD_ART OR R.COD_ART_RELA = A.COD_ART))";
			}
			query+=" AND A.FECHA_GEN = (SELECT MAX(FECHA_GEN) FROM T_MIS_SURTIDO_VEGALSA WHERE COD_CENTRO = A.COD_CENTRO AND COD_ART = A.COD_ART) ";
		}
		if (pagination!=null){
			logger.info("FacingVegalsaDaoImpl - findAll - pagination = "+pagination.toString());
			if (pagination.getSort()!=null){
				query+=" order by "+pagination.getSort()+" "+pagination.getAscDsc();	
			}else{
				query+=" order by b.grupo1, b.grupo2, b.grupo3, b.grupo4, b.grupo5, a.cod_art "+pagination.getAscDsc();	
			}
		}

		List<FacingVegalsaResponse> output = null;
		logger.info("FacingVegalsaDaoImpl - findAll - SQL = "+query);
		try {
			output = (List<FacingVegalsaResponse>) this.jdbcTemplate.query(query.toString(),
					this.mapperFacingVegalsaResponse, params.toArray());
		} catch (Exception e) {
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return output;
	}

	@Override
	@Cacheable(value = "cacheConsultaFacingWS",key = "{#facingVegalsaRequest}")
	//Para que sea cacheable ConsultarFacingVegalsaRequestType tiene que tener los m�todos hashCode() y equals()
	public ConsultaFacingVegalsaResponseType consultarFacingVegalsa(
			ConsultaFacingVegalsaRequestType facingVegalsaRequest, HttpSession session) throws Exception {
		
		FacingVegalsaServiceLocator locator = new FacingVegalsaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/GCPV/wsdl/Facing", "FacingVegalsaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ConsultaFacingVegalsaResponseType facingVegalsaResponse =null;
		try {
			URL address = new URL(facingVegalsa);
			FacingVegalsaPortType proxy = locator.getFacingVegalsaPort(address);
			// Asignar el Tipo de operaci�n.
//			facingVegalsaRequest.setTipo(Constantes.CONSULTAR_FACING);
			facingVegalsaResponse = proxy.consultaFacing(facingVegalsaRequest);


		} catch (Exception e) {
			logger.error("######################## facingVegalsa WS CONSULTA ERROR ############################");
			User user = (User) session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro() );
			}
			logger.error("Tipo: " + facingVegalsaRequest.getTipo());
			if (null != facingVegalsaRequest){
				logger.error("Referencia: " + Arrays.toString(facingVegalsaRequest.getListaReferencias()));
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
			
			//Indicamos que ha ocurrido un error.
			facingVegalsaResponse.setCodigoRespuesta("ERR");
		}

		return facingVegalsaResponse;
	}

	@Override
	@CacheEvict(value="cacheConsultaFacingWS",allEntries=true)
	public ModificarFacingVegalsaResponseType modificarFacingVegalsa(ModificarFacingVegalsaRequestType facingVegalsaRequest, HttpSession session) throws Exception{

		FacingVegalsaServiceLocator locator = new FacingVegalsaServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();

		QName qname = new QName("http://www.eroski.es/GCPV/wsdl/Facing", "FacingVegalsaPort");
		List chain = handlerRegistry.getHandlerChain(qname);
		HandlerInfo info = new HandlerInfo();
		info.setHandlerClass(LogHandler.class);        
		chain.add(info);

		ModificarFacingVegalsaResponseType facingVegalsaResponse = null;
		try {
			URL address = new URL(facingVegalsa);
			FacingVegalsaPortType proxy = locator.getFacingVegalsaPort(address);
			facingVegalsaRequest.setTipo(Constantes.MODIFICAR_FACING);
			facingVegalsaResponse = proxy.modificarFacing(facingVegalsaRequest);

		} catch (Exception e) {
			logger.error("######################## facingVegalsa WS ERROR MODIFICACIÓN ############################");
			User user = (User)session.getAttribute("user");
			if (null != user){
				logger.error("Centro: " + user.getCentro().getCodCentro());
				logger.error("Usuario: " + user.getCode());
				logger.error("Dirección MAC: " + user.getMac());
				logger.error("Fecha-Hora: " + Utilidades.formatearFechaHora(new Date()));
			}
			if (null != facingVegalsaRequest){
				for (ReferenciaModType referencia : facingVegalsaRequest.getReferencias()) {
					logger.error("Referencia: " + referencia.getCodigoReferencia());
					logger.error("Facing: " + referencia.getFacing());
					logger.error("Capacidad: " + referencia.getCapacidad());
				}
			}
			logger.error( StackTraceManager.getStackTrace(e));
			logger.error("#####################################################");
		}

		return facingVegalsaResponse;
	}

	@Override
	public boolean facingModificable(Long codCentro) {
//		logger.info("Entra en FacingVegalsaDAOImpl.facingModificable()");

		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);

		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(1) "
				   + "FROM v_mis_param_centros_opc "
				   + "WHERE cod_loc = ? "
				   + "AND opc_habil LIKE '46%'");
		
		logger.info("SQL: "+query);
		int count = jdbcTemplate.queryForObject(query.toString(), params.toArray(), Integer.class);
		return (count > 0);
	}
	

}
