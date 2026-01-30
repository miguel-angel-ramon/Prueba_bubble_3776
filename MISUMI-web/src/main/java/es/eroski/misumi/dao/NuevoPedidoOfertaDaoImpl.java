package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.NuevoPedidoOfertaDao;
import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.model.DiasServicio;
import es.eroski.misumi.model.NuevoPedidoOferta;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VBloqueoEncargosPiladas;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.service.iface.DiasServicioService;
import es.eroski.misumi.service.iface.TPedidoAdicionalService;
import es.eroski.misumi.service.iface.VBloqueoEncargosPiladasService;
import es.eroski.misumi.service.iface.VPlanPedidoAdicionalService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;


@Repository
public class NuevoPedidoOfertaDaoImpl implements NuevoPedidoOfertaDao{
	 private static Logger logger = Logger.getLogger(NuevoPedidoOfertaDaoImpl.class);
	 
	 @Resource 
	 private MessageSource messageSource;

	 @Autowired
	 private VPlanPedidoAdicionalService vPlanPedidoAdicionalService;

	 @Autowired
	 private TPedidoAdicionalService tPedidoAdicionalService;

	 @Autowired
	 private DiasServicioService diasServicioService;

	 @Autowired
	 private VBloqueoEncargosPiladasService vBloqueoEncargosPiladasService;

	 private JdbcTemplate jdbcTemplate;
	 // private static Logger logger = LoggerFactory.getLogger(NuevoPedidoOfertaDaoImpl.class);
	 // private static Logger logger = Logger.getLogger(NuevoPedidoOfertaDaoImpl.class);

	 private RowMapper<NuevoPedidoOferta> rwNuevoPedidoOfertaMap = new RowMapper<NuevoPedidoOferta>() {
		public NuevoPedidoOferta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new NuevoPedidoOferta(resultSet.getString("TIPO_APROV"), resultSet.getLong("COD_CENTRO"),
		 			resultSet.getLong("COD_ART"), resultSet.getString("DESCRIP_ART"), resultSet.getString("FECHA_INI"),
		 			null, null, null, resultSet.getString("FECHA_FIN"), null, null, null, null, null, null, null,
		 			resultSet.getDouble("UNI_CAJA_SERV"), resultSet.getString("FLG_TIPO_LISTADO"), null,
		 			null, null, null, String.valueOf(resultSet.getLong("COD_ART")), rowNum, null, resultSet.getLong("ANO_OFERTA"), resultSet.getLong("NUM_OFERTA"), 
		 			null, null, null, null, null, null, null, null, null, null);
		}
     };

     @Autowired
     public void setDataSource(DataSource dataSource) {
     	this.jdbcTemplate = new JdbcTemplate(dataSource);
     } 
	 
	 @Autowired
	 private VFestivoCentroDao vFestivoCentroDao;
	 
	 @Override
	 public List<NuevoPedidoOferta> findAll(NuevoPedidoOferta nuevoPedidoOferta, String idSession, String usuario, HttpSession session) throws Exception {
		 List<NuevoPedidoOferta> listaNuevosPedidos = null;
		 List<NuevoPedidoOferta> listaPedidosPorOferta = null;
		 List<NuevoPedidoOferta> listaBD = findAllBD(nuevoPedidoOferta);
		 if (listaBD != null){
			 //Se obtiene la lista del servicio para eliminar las referencias ya pedidas
			 listaNuevosPedidos = obtenerListaNuevosPedidosSinWS(listaBD, listaPedidosPorOferta);
		 }
		 
		 /*----Inserción en tabla temporal de lista de pedidos por centro y planogramadas con y sin oferta para cálculo de días de servicio*/ 
		 
		 //Borramos previamente los posibles registros almacenados.
		 this.eliminarTablaSesion(idSession,nuevoPedidoOferta.getCodCentro());

		 /*------- Fin de Inserción de lista de pedidos por centro ------------------------------------------------------------------------*/ 
		 
		 /*------- Inserción de lista de planogramadas sin oferta -------------------------------------------------------------------------*/
		 VPlanPedidoAdicional vPlanPedidoAdicional = new VPlanPedidoAdicional();
		 vPlanPedidoAdicional.setCodCentro(nuevoPedidoOferta.getCodCentro());
		 vPlanPedidoAdicional.setGrupo1(nuevoPedidoOferta.getGrupo1());
		 vPlanPedidoAdicional.setGrupo2(nuevoPedidoOferta.getGrupo2());
		 vPlanPedidoAdicional.setGrupo3(nuevoPedidoOferta.getGrupo3());
		 vPlanPedidoAdicional.setEsOferta(Constantes.NO_OFERTA);
		 List<VPlanPedidoAdicional> listPlanogramas =this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
		 if (listPlanogramas != null && listPlanogramas.size()>0) {	
			 this.insertarTablaSesionPlanogramas(listPlanogramas, idSession, new Long(Constantes.CLASE_PEDIDO_MONTAJE));
		 }
		 /*------- Fin Inserción de lista de planogramadas sin oferta ---------------------------------------------------------------------*/

		 /*------- Inserción de lista de planogramadas con oferta -------------------------------------------------------------------------*/
		 vPlanPedidoAdicional.setEsOferta(Constantes.SI_OFERTA);
		 listPlanogramas =this.vPlanPedidoAdicionalService.findAll(vPlanPedidoAdicional);
		 if (listPlanogramas != null && listPlanogramas.size()>0) {	
			 this.insertarTablaSesionPlanogramas(listPlanogramas, idSession, new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
		 }
		 /*------- Fin Inserción de lista de planogramadas con oferta ---------------------------------------------------------------------*/ 
		 
		 /*------- Eliminado de la lista de planogramadas con oferta de la lista de nuevos pedidos-------------------------------------------------------------------------*/
		 List<NuevoPedidoOferta> listaPlanogramadasOferta = obtenerListaPlanogramadasOferta(listPlanogramas, nuevoPedidoOferta);
		 listaNuevosPedidos = obtenerListaNuevosPedidosSinPlanogramadas(listaNuevosPedidos, listaPlanogramadasOferta);
		 /*------- Fin Eliminado de la lista de planogramadas con oferta de la lista de nuevos pedidos ---------------------------------------------------------------------*/ 
		 
		 return listaNuevosPedidos;
	 }
	 
	 private List<NuevoPedidoOferta> findAllBD(NuevoPedidoOferta nuevoPedidoOferta) throws Exception  {
		 StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		 List<Object> params = new ArrayList<Object>();
	
		 StringBuffer query = new StringBuffer(" SELECT S.TIPO_APROV, O.COD_CENTRO, O.COD_ART, D.DESCRIP_ART, TO_CHAR(O.FECHA_INI,'DDMMYYYY') FECHA_INI, " +
				 							   " TO_CHAR(O.FECHA_FIN,'DDMMYYYY') FECHA_FIN, S.UNI_CAJA_SERV, " +
				 							   "DECODE(GRUPO1, 1 ,DECODE(GRUPO2,1,'FP',2,'FP',7,'FP','AFI'),'AFI') FLG_TIPO_LISTADO, O.ANO_OFERTA, O.NUM_OFERTA " +
											   " FROM V_OFERTA_PA O, V_SURTIDO_TIENDA S, V_DATOS_DIARIO_ART D ");

		 //Filtrado por góndola
		 if (nuevoPedidoOferta.getCabGondola() != null){
			 //Subquery de artículos de góndola
			 StringBuffer queryGondolaArticulos = new StringBuffer(" SELECT DISTINCT COD_ARTICULO, '' NEA_COD_N1, '' NEA_COD_N2, '' NEA_COD_N3 " +
	    			" FROM V_SIC_F_PROMOCIONES WHERE 1=1 ");

			  if(nuevoPedidoOferta.getNumOferta()!=null){
				  queryGondolaArticulos.append(" AND COD_PROMOCION = ? ");
		        	 params.add(nuevoPedidoOferta.getNumOferta());	        		
	          }
			  if(nuevoPedidoOferta.getAnoOferta()!=null){
				  queryGondolaArticulos.append(" AND EJER_PROMOCION = ? ");
		        	 params.add(nuevoPedidoOferta.getAnoOferta());	        		
	          }
    		  queryGondolaArticulos.append(" AND ((COD_LOC IS NULL");
        	 
			  if(nuevoPedidoOferta.getNelCodN1()!=null){
	        		 queryGondolaArticulos.append(" AND NEL_COD_N1 = ? ");
		        	 params.add(nuevoPedidoOferta.getNelCodN1());	        		
	          }
			  if(nuevoPedidoOferta.getNelCodN2()!=null){
	        		 queryGondolaArticulos.append(" AND (NEL_COD_N2 = ? OR NEL_COD_N2 = 0) ");
		        	 params.add(nuevoPedidoOferta.getNelCodN2());	        		
	          }else{
	        	  queryGondolaArticulos.append(" AND NEL_COD_N2 = 0 ");
	          }
			  if(nuevoPedidoOferta.getNelCodN3()!=null){
	        		 queryGondolaArticulos.append(" AND (NEL_COD_N3 = ? OR NEL_COD_N3 = 0) ");
		        	 params.add(nuevoPedidoOferta.getNelCodN3());	        		
	          }else{
	        		 queryGondolaArticulos.append(" AND NEL_COD_N3 = 0 ");
	          }
			  queryGondolaArticulos.append(" )");
			  if(nuevoPedidoOferta.getCodCentro()!=null){
	        		 queryGondolaArticulos.append(" OR COD_LOC = ? ");
		        	 params.add(nuevoPedidoOferta.getCodCentro());
	          }

        	  queryGondolaArticulos.append(" )");

	          if(nuevoPedidoOferta.getGrupo1()!=null){
	        		 queryGondolaArticulos.append(" AND NEA_COD_N1 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo1());	        		
	          }
	          if(nuevoPedidoOferta.getGrupo2()!=null){
	        		 queryGondolaArticulos.append(" AND NEA_COD_N2 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo2());	        		
	          }
	          if(nuevoPedidoOferta.getGrupo3()!=null){
	        		 queryGondolaArticulos.append(" AND NEA_COD_N3 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo3());	        		
	          }
			  if(nuevoPedidoOferta.getCabGondola()!=null){
				  if (nuevoPedidoOferta.getCabGondola().equals("-1")){
					  queryGondolaArticulos.append(" AND CAB_GONDOLA IS NULL "); 
				  }else{
	        		 queryGondolaArticulos.append(" AND CAB_GONDOLA = upper(?) ");
        			 params.add(nuevoPedidoOferta.getCabGondola());
				  }
			  }

			 //Subquery de estructuras de góndola
			 StringBuffer queryGondolaEstructuras = new StringBuffer(" SELECT DISTINCT '' COD_ARTICULO, NEA_COD_N1, NEA_COD_N2, NEA_COD_N3 " +
		    			" FROM V_SIC_F_PROMOCIONES WHERE 1=1 ");
	
			  if(nuevoPedidoOferta.getNumOferta()!=null){
				  queryGondolaEstructuras.append(" AND COD_PROMOCION = ? ");
		        	 params.add(nuevoPedidoOferta.getNumOferta());	        		
	          }
			  if(nuevoPedidoOferta.getAnoOferta()!=null){
				  queryGondolaEstructuras.append(" AND EJER_PROMOCION = ? ");
		        	 params.add(nuevoPedidoOferta.getAnoOferta());	        		
	          }
    		  queryGondolaEstructuras.append(" AND ((COD_LOC IS NULL");
        	 
			  if(nuevoPedidoOferta.getNelCodN1()!=null){
	        		 queryGondolaEstructuras.append(" AND NEL_COD_N1 = ? ");
		        	 params.add(nuevoPedidoOferta.getNelCodN1());	        		
	          }
			  if(nuevoPedidoOferta.getNelCodN2()!=null){
	        		 queryGondolaEstructuras.append(" AND (NEL_COD_N2 = ? OR NEL_COD_N2 = 0) ");
		        	 params.add(nuevoPedidoOferta.getNelCodN2());	        		
	          }else{
	        	  queryGondolaEstructuras.append(" AND NEL_COD_N2 = 0 ");
	          }
			  if(nuevoPedidoOferta.getNelCodN3()!=null){
	        		 queryGondolaEstructuras.append(" AND (NEL_COD_N3 = ? OR NEL_COD_N3 = 0) ");
		        	 params.add(nuevoPedidoOferta.getNelCodN3());	        		
	          }else{
	        	  queryGondolaEstructuras.append(" AND NEL_COD_N3 = 0 ");
	          }
			  queryGondolaEstructuras.append(" )");
			  if(nuevoPedidoOferta.getCodCentro()!=null){
	        		 queryGondolaEstructuras.append(" OR COD_LOC = ? ");
		        	 params.add(nuevoPedidoOferta.getCodCentro());
	          }

        	  queryGondolaEstructuras.append(" )");

	          if(nuevoPedidoOferta.getGrupo1()!=null){
	        		 queryGondolaEstructuras.append(" AND NEA_COD_N1 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo1());	        		
	          }
	          if(nuevoPedidoOferta.getGrupo2()!=null){
	        		 queryGondolaEstructuras.append(" AND NEA_COD_N2 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo2());	        		
	          }
	          if(nuevoPedidoOferta.getGrupo3()!=null){
	        		 queryGondolaEstructuras.append(" AND NEA_COD_N3 = LPAD(upper(?), 4, '0') ");
		        	 params.add(nuevoPedidoOferta.getGrupo3());	        		
	          }
			  if(nuevoPedidoOferta.getCabGondola()!=null){
				  if (nuevoPedidoOferta.getCabGondola().equals("-1")){
					  queryGondolaEstructuras.append(" AND CAB_GONDOLA IS NULL "); 
				  }else{
					  queryGondolaEstructuras.append(" AND CAB_GONDOLA = upper(?) ");
        			 params.add(nuevoPedidoOferta.getCabGondola());
				  }
			  }
			  
			  query.append(", ("+queryGondolaArticulos+" UNION "+queryGondolaArticulos+") GONDOLAS ");
		 }
		 
		 //Condiciones para la JOIN
		 where.append("WHERE O.COD_CENTRO = S.COD_CENTRO AND O.COD_ART = S.COD_ART AND O.COD_ART = D.COD_ART ");
		 
		 if (nuevoPedidoOferta  != null){
			 if(nuevoPedidoOferta.getCodCentro()!=null){
	    		 where.append(" AND O.COD_CENTRO = ? ");
	        	 params.add(nuevoPedidoOferta.getCodCentro());	        		
			 }
			 if(nuevoPedidoOferta.getCodArticulo()!=null){
	    		 where.append(" AND O.COD_ART = ? ");
	        	 params.add(nuevoPedidoOferta.getCodArticulo());	        		
			 }
			 if(nuevoPedidoOferta.getAnoOferta()!=null){
	    		 where.append(" AND O.ANO_OFERTA = ? ");
	        	 params.add(nuevoPedidoOferta.getAnoOferta());	        		
			 }
			 if(nuevoPedidoOferta.getNumOferta()!=null){
	    		 where.append(" AND O.NUM_OFERTA = ? ");
	        	 params.add(nuevoPedidoOferta.getNumOferta());	        		
			 }
			 if(nuevoPedidoOferta.getGrupo1()!=null){
	    		 where.append(" AND D.GRUPO1 = ? ");
	        	 params.add(nuevoPedidoOferta.getGrupo1());	        		
			 }
			 if(nuevoPedidoOferta.getGrupo2()!=null){
	    		 where.append(" AND D.GRUPO2 = ? ");
	        	 params.add(nuevoPedidoOferta.getGrupo2());	        		
			 }
			 if(nuevoPedidoOferta.getGrupo3()!=null){
	    		 where.append(" AND D.GRUPO3 = ? ");
	        	 params.add(nuevoPedidoOferta.getGrupo3());	        		
			 }
			 //Filtro por góndola
			 if (nuevoPedidoOferta.getCabGondola() != null){
				 where.append(" AND (O.COD_ART = GONDOLAS.COD_ARTICULO OR " +
				 		"(LPAD(upper(D.GRUPO1), 4, '0') = GONDOLAS.NEA_COD_N1 AND " +
				 		" LPAD(upper(D.GRUPO2), 4, '0') = GONDOLAS.NEA_COD_N2 AND " +
				 		" LPAD(upper(D.GRUPO3), 4, '0') = GONDOLAS.NEA_COD_N3)) ");
			 }
		 }
	    
		 query.append(where);
	
		 StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		 order.append(" order by O.COD_CENTRO, O.COD_ART ");
		 query.append(order);
	
		 List<NuevoPedidoOferta> nuevoPedidoOfertaLista = null;		
		 try {
			 nuevoPedidoOfertaLista = (List<NuevoPedidoOferta>) this.jdbcTemplate.query(query.toString(),this.rwNuevoPedidoOfertaMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	
		 return nuevoPedidoOfertaLista;
	 }
	 	
	 private List<NuevoPedidoOferta> obtenerListaNuevosPedidosSinWS(List<NuevoPedidoOferta> listaBD, List<NuevoPedidoOferta> listaWS){
		 List<NuevoPedidoOferta> listaBDFiltrada = listaBD;
		 List<NuevoPedidoOferta> listaBDFiltradaElemAEliminar = new ArrayList<NuevoPedidoOferta>(listaBD);
		 
		 //Obtiene los registros que están en la listaBD y listaWS
		 listaBDFiltradaElemAEliminar.retainAll(listaWS);
		 //Eliminar de la lista de BD los marcados para eliminar
		 listaBDFiltrada.removeAll(listaBDFiltradaElemAEliminar);

		 return listaBDFiltrada;
	 }
	 
	 private List<NuevoPedidoOferta> obtenerListaPlanogramadasOferta(List<VPlanPedidoAdicional> listaPlanogramadasConOferta, NuevoPedidoOferta nuevoPedidoOfertaBusqueda){
		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<NuevoPedidoOferta> listaPlanogramasOferta = new ArrayList<NuevoPedidoOferta>();

		if (nuevoPedidoOfertaBusqueda.getAnoOferta() != null && nuevoPedidoOfertaBusqueda.getNumOferta() != null){

			String anoOfertaBusqueda = nuevoPedidoOfertaBusqueda.getAnoOferta().toString();
			Long numOfertaBusqueda = nuevoPedidoOfertaBusqueda.getNumOferta();

			//Nos recorremos la lista.
			VPlanPedidoAdicional registro = new VPlanPedidoAdicional();
			NuevoPedidoOferta nuevoRegistro = new NuevoPedidoOferta();
			for (int i =0;i<listaPlanogramadasConOferta.size();i++)
			{
				nuevoRegistro = new NuevoPedidoOferta();
				
				registro = (VPlanPedidoAdicional)listaPlanogramadasConOferta.get(i);
				//Sólo hace falta obtener la lista de planogramadas de la oferta buscada y
				//mantener una lista con los referencias obtenidas para eliminarlas de la lista de pantalla
				if (anoOfertaBusqueda.equals(registro.getAnoOferta()) && numOfertaBusqueda.equals(registro.getCodOferta())){
					nuevoRegistro.setCodArticulo((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null);					
				}
	
				listaPlanogramasOferta.add(nuevoRegistro);
			}
		}
		return listaPlanogramasOferta;
	 }
	 
	 private List<NuevoPedidoOferta> obtenerListaNuevosPedidosSinPlanogramadas(List<NuevoPedidoOferta> listaBD, List<NuevoPedidoOferta> listaPlanogramadasOferta){
		 List<NuevoPedidoOferta> listaBDFiltrada = listaBD;
		 List<NuevoPedidoOferta> listaBDFiltradaElemAEliminar = new ArrayList<NuevoPedidoOferta>(listaBD);
		 
		 //Obtiene los registros que están en la listaBD y listaWS
		 listaBDFiltradaElemAEliminar.retainAll(listaPlanogramadasOferta);
		 //Eliminar de la lista de BD los marcados para eliminar
		 listaBDFiltrada.removeAll(listaBDFiltradaElemAEliminar);

		 return listaBDFiltrada;
	 }

	 private List<Long> obtenerListaArticulosRecarga(List<NuevoPedidoOferta> listaNuevosPedidos){
		 List<Long> listaArticulosRecarga = new ArrayList<Long>();
		 for (NuevoPedidoOferta nuevoPedido : listaNuevosPedidos){
			 listaArticulosRecarga.add(nuevoPedido.getCodArticulo());
		 }

		 return listaArticulosRecarga;
	 }
 	
 	
 	public void insertarTablaSesionNuevoOferta(List<NuevoPedidoOferta> list, String idSesion, String usuario, Long clasePedido) throws Exception{
 		insertarTablaSesionNuevoOferta(list, idSesion, usuario, clasePedido, false, Constantes.PANTALLA_OFERTAS);
 	}

 	private void insertarTablaSesionNuevoOferta(List<NuevoPedidoOferta> list, String idSesion, String usuario, Long clasePedido, boolean sesionPorArticulo, String pantalla) throws Exception{

		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		for (NuevoPedidoOferta registro: list)
		{
			nuevoRegistro = new TPedidoAdicional();
			if (sesionPorArticulo){ //El identificador de sesión se calcula por cada artículo
				nuevoRegistro.setIdSesion(idSesion + "_" +((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null));
			}else{
				nuevoRegistro.setIdSesion(idSesion);
			}
			nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
			nuevoRegistro.setCodArticulo((registro.getCodArticulo() != null && !("".equals(registro.getCodArticulo().toString())))?new Long(registro.getCodArticulo().toString()):null);
			nuevoRegistro.setDescriptionArt((registro.getDescriptionArt() != null && !("".equals(registro.getDescriptionArt())))?registro.getDescriptionArt():null);
			nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
			if (usuario != null){
				nuevoRegistro.setUsuario((usuario != null && !("".equals(usuario)))?usuario:null);
			}else{
				nuevoRegistro.setUsuario((registro.getUsuario() != null && !("".equals(registro.getUsuario())))?registro.getUsuario():null);
			}
			nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
			String oferta = registro.getAnoOferta()+"-"+registro.getNumOferta();
			nuevoRegistro.setOferta(oferta);
			nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprov() != null && !("".equals(registro.getTipoAprov())))?registro.getTipoAprov():null);
			nuevoRegistro.setFechaMinima(registro.getFechaMinima());
			nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null && !("".equals(registro.getFechaInicio())))?registro.getFechaInicio():null);
			nuevoRegistro.setFechaFin((registro.getFechaFin() != null && !("".equals(registro.getFechaFin())))?registro.getFechaFin():null);
			nuevoRegistro.setClasePedido((clasePedido != null && !("".equals(clasePedido)))?clasePedido:registro.getClasePedido());
			nuevoRegistro.setFecha2((registro.getFecha2() != null && !("".equals(registro.getFecha2())))?registro.getFecha2():null);
			nuevoRegistro.setFecha3((registro.getFecha3() != null && !("".equals(registro.getFecha3())))?registro.getFecha3():null);
			nuevoRegistro.setFechaPilada((registro.getFechaPilada() != null && !("".equals(registro.getFechaPilada())))?registro.getFechaPilada():null);
			nuevoRegistro.setCapMax((registro.getImplInicial() != null && !("".equals(registro.getImplInicial().toString())))?new Double(registro.getImplInicial().toString()):null);
			nuevoRegistro.setCapMin((registro.getImplFinal() != null && !("".equals(registro.getImplFinal().toString())))?new Double(registro.getImplFinal().toString()):null);
			nuevoRegistro.setCantidad1((registro.getCantidad1() != null && !("".equals(registro.getCantidad1().toString())))?new Double(registro.getCantidad1().toString()):null);
			nuevoRegistro.setCantidad2((registro.getCantidad2() != null && !("".equals(registro.getCantidad2().toString())))?new Double(registro.getCantidad2().toString()):null);
			nuevoRegistro.setCantidad3((registro.getCantidad3() != null && !("".equals(registro.getCantidad3().toString())))?new Double(registro.getCantidad3().toString()):null);
			nuevoRegistro.setTipoPedido((registro.getTipoPedido() != null && !("".equals(registro.getTipoPedido())))?registro.getTipoPedido():null);
			nuevoRegistro.setPantalla(pantalla);
			nuevoRegistro.setModificable(registro.getBloqueado());
			listaTPedidoAdicional.add(nuevoRegistro);
		}

			this.tPedidoAdicionalService.insertAllNuevoOferta(listaTPedidoAdicional);

			
	}
 	
	private void insertarTablaSesionPlanogramas(List<VPlanPedidoAdicional> list, String idSesion, Long clasePedido){
	
		//Nos recorremos la lista para generar la lista que debemos enviar al servicio para que inserte en la tabla temporal.
		List<TPedidoAdicional> listaTPedidoAdicional = new ArrayList<TPedidoAdicional>();
		
		//Nos recorremos la lista.
		VPlanPedidoAdicional registro = new VPlanPedidoAdicional();
		TPedidoAdicional nuevoRegistro = new TPedidoAdicional();
		for (int i =0;i<list.size();i++)
		{
			nuevoRegistro = new TPedidoAdicional();
			
			registro = (VPlanPedidoAdicional)list.get(i);
			
			nuevoRegistro.setIdSesion(idSesion + "_" +((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null));
			nuevoRegistro.setCodCentro((registro.getCodCentro() != null && !("".equals(registro.getCodCentro().toString())))?new Long(registro.getCodCentro().toString()):null);
			nuevoRegistro.setCodArticulo((registro.getCodArt() != null && !("".equals(registro.getCodArt().toString())))?new Long(registro.getCodArt().toString()):null);
			nuevoRegistro.setDescriptionArt((registro.getDescripArt() != null && !("".equals(registro.getDescripArt())))?registro.getDescripArt():null);
			nuevoRegistro.setUniCajaServ((registro.getUniCajaServ() != null && !("".equals(registro.getUniCajaServ().toString())))?new Double(registro.getUniCajaServ().toString()):null);
			nuevoRegistro.setPerfil((registro.getPerfil() != null && !("".equals(registro.getPerfil())))?registro.getPerfil():null);
			nuevoRegistro.setAgrupacion((registro.getAgrupacion() != null && !("".equals(registro.getAgrupacion())))?registro.getAgrupacion():null);
			nuevoRegistro.setTipoAprovisionamiento((registro.getTipoAprovisionamiento() != null && !("".equals(registro.getTipoAprovisionamiento())))?registro.getTipoAprovisionamiento():null);
			nuevoRegistro.setFechaInicio((registro.getFechaInicio() != null)?Utilidades.formatearFecha(registro.getFechaInicio()):null);
			nuevoRegistro.setFechaFin((registro.getFechaFin() != null)?Utilidades.formatearFecha(registro.getFechaFin()):null);
			nuevoRegistro.setClasePedido((clasePedido != null && !("".equals(clasePedido)))?clasePedido:null);
			nuevoRegistro.setCapMax((registro.getImpInicial() != null && !("".equals(registro.getImpInicial().toString())))?new Double(registro.getImpInicial().toString()):null);
			nuevoRegistro.setCapMin((registro.getImpFinal() != null && !("".equals(registro.getImpFinal().toString())))?new Double(registro.getImpFinal().toString()):null);
			nuevoRegistro.setPantalla(Constantes.PANTALLA_CALENDARIO);
			nuevoRegistro.setEsPlanograma("S");

			if (clasePedido!=null){
				if (Constantes.CLASE_PEDIDO_MONTAJE.equals(clasePedido)){ //Sin oferta
					nuevoRegistro.setExcluir(registro.getExcluir());
				}else{ //Con oferta
					nuevoRegistro.setOferta(registro.getAnoOferta()+"-"+((registro.getCodOferta())));
				}
			}
			nuevoRegistro.setBorrable(Constantes.PEDIDO_NO_BORRABLE);
			nuevoRegistro.setModificable(Constantes.PEDIDO_MODIFICABLE_NO);

			listaTPedidoAdicional.add(nuevoRegistro);
		}
		
		try {
			this.tPedidoAdicionalService.insertAll(listaTPedidoAdicional);
		} catch (Exception e) {
			logger.error("insertarTablaSesionMO="+e.toString());
			e.printStackTrace();
		}
	}
	
	private void eliminarTablaSesion(String idSesion, Long codCentro){
		
		TPedidoAdicional registro = new TPedidoAdicional();
		
		registro.setIdSesion(idSesion);
		registro.setCodCentro(codCentro);
		registro.setPantalla(Constantes.PANTALLA_OFERTAS);
		
		try {
			this.tPedidoAdicionalService.delete(registro);
		} catch (Exception e) {
			logger.error("eliminarTablaSesion="+e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public void recargaDiasServicioArticulosPagina (NuevoPedidoOferta nuevoPedidoOferta, List<NuevoPedidoOferta> listaNuevosPedidos, String idSession) throws Exception {
		DiasServicio diasServicioRecarga = new DiasServicio();
		diasServicioRecarga.setCodCentro(nuevoPedidoOferta.getCodCentro());
		diasServicioRecarga.setCodArt(nuevoPedidoOferta.getCodArticulo());
		diasServicioRecarga.setIdsesion(idSession);
		//Obtención de la lista de artículos para la recarga
		List<Long> listaArticulosRecarga = obtenerListaArticulosRecarga(listaNuevosPedidos);
		if (listaArticulosRecarga != null && listaArticulosRecarga.size() > 0){
			diasServicioRecarga.setListaArticulosRecargaDiasServicio(listaArticulosRecarga);
			this.diasServicioService.recargaDiasServicioGrupo(diasServicioRecarga);	 
		}
	}
	
	private void actualizarPedidoValidado(NuevoPedidoOferta pedidoValidado, User user, String idSesion) throws Exception{
		TPedidoAdicional tPedidoAdicional = new TPedidoAdicional();
		tPedidoAdicional.setIdSesion(idSesion);
		//tPedidoAdicional.setClasePedido(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));
		
		tPedidoAdicional.setCodCentro(user.getCentro().getCodCentro());
		tPedidoAdicional.setPantalla(Constantes.PANTALLA_OFERTAS);
		tPedidoAdicional.setCodArticulo(pedidoValidado.getCodArticulo());
		//Obtenemos el registro de la lista guardada en sesión.
		tPedidoAdicional = this.tPedidoAdicionalService.findOne(tPedidoAdicional);
		//Se sobreescribe el valor del identificador con null porque todavía no debe tener ningún identificador asignado
		tPedidoAdicional.setIdentificador(null);
		
		if (tPedidoAdicional != null){
			//Actualizamos los valores que me llegan.
			tPedidoAdicional.setModificable(pedidoValidado.getBloqueado());
			tPedidoAdicional.setFechaInicio(pedidoValidado.getFechaInicio());
			tPedidoAdicional.setFechaMinima(pedidoValidado.getFechaMinima());
			tPedidoAdicional.setFechaFin(pedidoValidado.getFechaFin());
			if (Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO.equals(pedidoValidado.getFlgTipoListado())){
				tPedidoAdicional.setFecha2(pedidoValidado.getFecha2());
				tPedidoAdicional.setFecha3(pedidoValidado.getFecha3());
				tPedidoAdicional.setFechaPilada(pedidoValidado.getFechaPilada());
				tPedidoAdicional.setCantidad1(pedidoValidado.getCantidad1());
				tPedidoAdicional.setCantidad2(pedidoValidado.getCantidad2());
				tPedidoAdicional.setCantidad3(pedidoValidado.getCantidad3());
			}else{
				tPedidoAdicional.setCapMax(pedidoValidado.getImplInicial());
				if (pedidoValidado.getImplFinal()==null){
					tPedidoAdicional.setCapMin(pedidoValidado.getImplInicial());
				}else{
					tPedidoAdicional.setCapMin(pedidoValidado.getImplFinal());
				}
			}
			tPedidoAdicional.setCodError(pedidoValidado.getCodError().toString());
			tPedidoAdicional.setDescError(pedidoValidado.getDescError());
			//Actualizamos la lista con el registro.
			this.tPedidoAdicionalService.updatePedido(tPedidoAdicional);
		}
	}

	@Override
	public void comprobacionBloqueos(NuevoPedidoOferta nuevoPedidoOferta, User user, String tipoListado, boolean validandoPedidos) throws Exception{
		int numeroRegistrosBloqueo = 0;
		Locale locale = LocaleContextHolder.getLocale();
		
		VBloqueoEncargosPiladas vBloqueoEncargosPiladas = new VBloqueoEncargosPiladas();
		vBloqueoEncargosPiladas.setCodCentro(user.getCentro().getCodCentro());
		vBloqueoEncargosPiladas.setCodArticulo(nuevoPedidoOferta.getCodArticulo());
		vBloqueoEncargosPiladas.setFecIniDDMMYYYY((nuevoPedidoOferta.getFechaInicio()!=null?nuevoPedidoOferta.getFechaInicio():""));
		vBloqueoEncargosPiladas.setFecha2DDMMYYYY((nuevoPedidoOferta.getFecha2()!=null?nuevoPedidoOferta.getFecha2():""));
		vBloqueoEncargosPiladas.setFecha3DDMMYYYY((nuevoPedidoOferta.getFecha3()!=null?nuevoPedidoOferta.getFecha3():""));
		vBloqueoEncargosPiladas.setFechaInPilDDMMYYYY((nuevoPedidoOferta.getFechaPilada()!=null?nuevoPedidoOferta.getFechaPilada():""));
		vBloqueoEncargosPiladas.setFecFinDDMMYYYY((nuevoPedidoOferta.getFechaFin()!=null?nuevoPedidoOferta.getFechaFin():""));
		
		//Se guarda la fecha de fin para restaurarla tras la búsqueda
		String fechaFinMontaje = vBloqueoEncargosPiladas.getFecFinDDMMYYYY(); 
	 	//Obtener la última fecha de encargo
	    String fechaFinEncargoDateDDMMYYYY = null;
	    if(vBloqueoEncargosPiladas.getFecha3DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha3DDMMYYYY())){
	    	fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha3DDMMYYYY();
	    }else if(vBloqueoEncargosPiladas.getFecha2DDMMYYYY()!=null && !"".equals(vBloqueoEncargosPiladas.getFecha2DDMMYYYY())){
	    	fechaFinEncargoDateDDMMYYYY = vBloqueoEncargosPiladas.getFecha2DDMMYYYY();
	    }
	    //Bloqueos de encargo
	    if (Constantes.PED_ADI_TIPO_LISTADO_FRESCO_PURO.equals(tipoListado)){//Sólo se comprueba bloqueo de encargo para frescos
		    vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinEncargoDateDDMMYYYY);
		    vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_ENCARGO);
			numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
			if (numeroRegistrosBloqueo > 0){
				nuevoPedidoOferta.setFechaBloqueoEncargo("S");
				if (validandoPedidos){
					nuevoPedidoOferta.setBloqueado("S");
				}
			}else{
				nuevoPedidoOferta.setFechaBloqueoEncargo("N");
			}
	    }else{
	    	nuevoPedidoOferta.setFechaBloqueoEncargo("N");
	    }
		//Bloqueos de encargo y pilada
		vBloqueoEncargosPiladas.setFecFinDDMMYYYY(fechaFinMontaje);
		vBloqueoEncargosPiladas.setCodTpBloqueo(Constantes.COD_TP_BLOQUEO_MONTAJE);
		numeroRegistrosBloqueo = this.vBloqueoEncargosPiladasService.findMotivosRefBloqueadaCont(vBloqueoEncargosPiladas).intValue();
		if (numeroRegistrosBloqueo > 0){
			nuevoPedidoOferta.setFechaBloqueoEncargoPilada("S");
			if (validandoPedidos){
				nuevoPedidoOferta.setBloqueado("S");
			}
		}else{
			nuevoPedidoOferta.setFechaBloqueoEncargoPilada("N");
		}
		
		if("S".equals(nuevoPedidoOferta.getFechaBloqueoEncargo()) || "S".equals(nuevoPedidoOferta.getFechaBloqueoEncargoPilada())){
			nuevoPedidoOferta.setCodError(new Long(5));
			if("S".equals(nuevoPedidoOferta.getFechaBloqueoEncargo()) && "S".equals(nuevoPedidoOferta.getFechaBloqueoEncargoPilada())){
				nuevoPedidoOferta.setDescError(this.messageSource.getMessage(
						"p51_nuevoPedidoAdicionalOF.fechaSelBloqueosEncargosMontajes", null, locale));

			}else if("S".equals(nuevoPedidoOferta.getFechaBloqueoEncargo()) ){
				nuevoPedidoOferta.setDescError(this.messageSource.getMessage(
						"p51_nuevoPedidoAdicionalOF.fechaSelBloqueosEncargos", null, locale));
	
			}else{//"S".equals(nuevoPedidoOferta.getFechaBloqueoEncargoPilada()
				nuevoPedidoOferta.setDescError(this.messageSource.getMessage(
						"p51_nuevoPedidoAdicionalOF.fechaSelBloqueosMontajes", null, locale));
	
			}
		}
	}
}
