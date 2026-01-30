
package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.TPedidoAdicionalDao;
import es.eroski.misumi.model.CamposSeleccionadosVC;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.MontajeVegalsa;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.model.PedidoAdicionalNuevo;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class TPedidoAdicionalDaoImpl implements TPedidoAdicionalDao{
	
	private static Logger logger = Logger.getLogger(TPedidoAdicionalDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VDatosDiarioArtService vDatosDiarioArtService;
		
	 private RowMapper<TPedidoAdicional> rwTPedidoAdicionalMap = new RowMapper<TPedidoAdicional>() {
			public TPedidoAdicional mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new TPedidoAdicional(resultSet.getString("IDSESION"),resultSet.getLong("CLASEPEDIDO"),resultSet.getLong("CODCENTRO"),
			    			resultSet.getLong("CODARTICULO"),resultSet.getString("PANTALLA"),
			    			(resultSet.getObject("IDENTIFICADOR")!=null?resultSet.getLong("IDENTIFICADOR"):null),
			    			resultSet.getString("DESCRIPTIONART"), resultSet.getDouble("UNICAJASERV"), resultSet.getString("USUARIO"), 
			    			resultSet.getLong("PERFIL"), resultSet.getString("AGRUPACION"),resultSet.getString("OFERTA"),
			    			resultSet.getString("TIPOAPROVISIONAMIENTO"),resultSet.getString("BORRABLE"),resultSet.getString("MODIFICABLE"),
			    			resultSet.getString("MODIFICABLEINDIV"),resultSet.getDouble("CAJASPEDIDAS"), resultSet.getString("FECENTREGA"),
			    			resultSet.getString("FECHAINICIO"),resultSet.getString("FECHAFIN"),resultSet.getString("FECHA2"),
			    			resultSet.getString("FECHA3"),resultSet.getString("FECHA4"),resultSet.getDouble("CAPMAX"),
			    			resultSet.getDouble("CAPMIN"),resultSet.getDouble("CANTIDAD1"),resultSet.getDouble("CANTIDAD2"),
			    			resultSet.getDouble("CANTIDAD3"),resultSet.getString("TIPOPEDIDO"),resultSet.getString("CAJAS"),
			    			resultSet.getString("EXCLUIR"),resultSet.getDate("FECHACREACION"),resultSet.getString("CODERROR"),
			    			resultSet.getString("DESCERROR"),resultSet.getString("ESPLANOGRAMA"), resultSet.getString("DENOM_OFERTA"),
			    			resultSet.getDouble("CANT_MIN"),resultSet.getDouble("CANT_MAX"),resultSet.getString("FECHA5"),
			    			resultSet.getString("FECHAINPIL"),resultSet.getDouble("CANTIDAD4"),resultSet.getDouble("CANTIDAD5"),
			    			resultSet.getString("TRATAMIENTO"),resultSet.getString("FECHA_HASTA"), resultSet.getString("ESTADO"),
			    			resultSet.getString("NO_GESTIONA_PBL"),
			    			resultSet.getString("DESC_PERIODO"), resultSet.getString("ESPACIO_PROMO"),resultSet.getLong("COD_ART_GRID"), resultSet.getString("DESCRIP_ART_GRID"),
			    			(resultSet.getObject("IDENTIFICADOR_SIA")!=null?resultSet.getLong("IDENTIFICADOR_SIA"):null),
			    			(resultSet.getObject("IDENTIFICADOR_VEGALSA")!=null?resultSet.getLong("IDENTIFICADOR_VEGALSA"):null)
				    );
			}

		};
		
		 private RowMapper<MontajeVegalsa> rwTMontajeVegalsa = new RowMapper<MontajeVegalsa>() {
				public MontajeVegalsa mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				    MontajeVegalsa output = new MontajeVegalsa();
				    
				    final Long identificadorVegalsa = resultSet.getLong("IDENTIFICADOR_VEGALSA");
				    final Long codCentro = resultSet.getLong("COD_CENTRO");
				    final Long codArt = resultSet.getLong("COD_ART");
				    final String oferta = resultSet.getObject("OFERTA")!=null?resultSet.getString("OFERTA"):null;
				    final Date fechaInicio = resultSet.getObject("FECHA_INICIO")!=null? resultSet.getDate("FECHA_INICIO") :null;
				    final Date fechaFin = resultSet.getObject("FECHA_FIN")!=null? resultSet.getDate("FECHA_FIN") :null;
				    final Long cantidad = resultSet.getObject("CANTIDAD")!=null? resultSet.getLong("CANTIDAD") : null;
				    
				    output.setIdentificadorVegalsa(identificadorVegalsa);
				    output.setCodCentro(codCentro);
				    output.setCodArt(codArt);
				    output.setOferta(oferta);
				    if (fechaInicio!=null){
				    	output.setFechaInicio(fechaInicio);	
				    }

				    if (fechaFin!=null){
				    	output.setFechaFin(fechaFin);	
				    }
				    
				    output.setCantidad(cantidad);
				    
				    return output;
				}

			};
		
		private RowMapper<TPedidoAdicional> rwTPedidoAdicionalNuevoOfertaMap = new RowMapper<TPedidoAdicional>() {
			public TPedidoAdicional mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    TPedidoAdicional registro = new TPedidoAdicional(resultSet.getString("IDSESION"),resultSet.getLong("CLASEPEDIDO"),resultSet.getLong("CODCENTRO"),
			    			resultSet.getLong("CODARTICULO"),resultSet.getString("PANTALLA"),
			    			(resultSet.getObject("IDENTIFICADOR")!=null?resultSet.getLong("IDENTIFICADOR"):null),
			    			resultSet.getString("DESCRIPTIONART"), resultSet.getDouble("UNICAJASERV"), resultSet.getString("USUARIO"), 
			    			resultSet.getLong("PERFIL"), resultSet.getString("AGRUPACION"),resultSet.getString("OFERTA"),
			    			resultSet.getString("TIPOAPROVISIONAMIENTO"),resultSet.getString("BORRABLE"),resultSet.getString("MODIFICABLE"),
			    			resultSet.getString("MODIFICABLEINDIV"),resultSet.getDouble("CAJASPEDIDAS"), resultSet.getString("FECENTREGA"),
			    			resultSet.getString("FECHAINICIO"),resultSet.getString("FECHAFIN"),resultSet.getString("FECHA2"),
			    			resultSet.getString("FECHA3"),resultSet.getString("FECHA4"),resultSet.getDouble("CAPMAX"),
			    			resultSet.getDouble("CAPMIN"),resultSet.getDouble("CANTIDAD1"),resultSet.getDouble("CANTIDAD2"),
			    			resultSet.getDouble("CANTIDAD3"),resultSet.getString("TIPOPEDIDO"),resultSet.getString("CAJAS"),
			    			resultSet.getString("EXCLUIR"),resultSet.getDate("FECHACREACION"),resultSet.getString("CODERROR"),
			    			resultSet.getString("DESCERROR"),resultSet.getString("ESPLANOGRAMA"),resultSet.getString("DENOM_OFERTA"),
			    			resultSet.getDouble("CANT_MIN"),resultSet.getDouble("CANT_MAX"),resultSet.getString("FECHA5"),
			    			resultSet.getString("FECHAINPIL"),resultSet.getDouble("CANTIDAD4"),resultSet.getDouble("CANTIDAD5"),resultSet.getString("TRATAMIENTO"),
			    			resultSet.getString("FECHA_HASTA"), resultSet.getString("ESTADO"), resultSet.getString("NO_GESTIONA_PBL"),
			    			resultSet.getString("DESC_PERIODO"), resultSet.getString("ESPACIO_PROMO"), resultSet.getLong("COD_ART_GRID"), resultSet.getString("DESCRIP_ART_GRID"),
			    			(resultSet.getObject("IDENTIFICADOR_SIA")!=null?resultSet.getLong("IDENTIFICADOR_SIA"):null),
			    			(resultSet.getObject("IDENTIFICADOR_VEGALSA")!=null?resultSet.getLong("IDENTIFICADOR_VEGALSA"):null)
				    );
			    registro.setFechaMinima(resultSet.getString("FECHAMINIMA"));
			    return registro;
			}

		};
				
		 private RowMapper<GenericExcelVO> rwExcelPedidoAdicionalMap = new RowMapper<GenericExcelVO>() {
				public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {

					for (int i = 2; i < 43; i++) {
						logger.debug("VALOR EXCEL");
						logger.debug(Utilidades.obtenerValorExcel(resultSet,i));
					}
					
				    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet,3),
				    		Utilidades.obtenerValorExcel(resultSet,4),Utilidades.obtenerValorExcel(resultSet,5),Utilidades.obtenerValorExcel(resultSet,6),Utilidades.obtenerValorExcel(resultSet,7)
				    		,Utilidades.obtenerValorExcel(resultSet,8),Utilidades.obtenerValorExcel(resultSet,9),Utilidades.obtenerValorExcel(resultSet,10),Utilidades.obtenerValorExcel(resultSet,11)
				    		,Utilidades.obtenerValorExcel(resultSet,12),Utilidades.obtenerValorExcel(resultSet,13),Utilidades.obtenerValorExcel(resultSet,14),Utilidades.obtenerValorExcel(resultSet,15)
				    		,Utilidades.obtenerValorExcel(resultSet,16),Utilidades.obtenerValorExcel(resultSet,17),Utilidades.obtenerValorExcel(resultSet,18),Utilidades.obtenerValorExcel(resultSet,19)
				    		,Utilidades.obtenerValorExcel(resultSet,20),Utilidades.obtenerValorExcel(resultSet,21),Utilidades.obtenerValorExcel(resultSet,22),Utilidades.obtenerValorExcel(resultSet,23)
				    		,Utilidades.obtenerValorExcel(resultSet,24),Utilidades.obtenerValorExcel(resultSet,25),Utilidades.obtenerValorExcel(resultSet,26),Utilidades.obtenerValorExcel(resultSet,27)
				    		,Utilidades.obtenerValorExcel(resultSet,28),Utilidades.obtenerValorExcel(resultSet,29),Utilidades.obtenerValorExcel(resultSet,30),Utilidades.obtenerValorExcel(resultSet,31)
				    		,Utilidades.obtenerValorExcel(resultSet,32),Utilidades.obtenerValorExcel(resultSet,33),Utilidades.obtenerValorExcel(resultSet,34),Utilidades.obtenerValorExcel(resultSet,35)
				    		,Utilidades.obtenerValorExcel(resultSet,36),Utilidades.obtenerValorExcel(resultSet,37),Utilidades.obtenerValorExcel(resultSet,38),Utilidades.obtenerValorExcel(resultSet,39)
				    		,Utilidades.obtenerValorExcel(resultSet,40),Utilidades.obtenerValorExcel(resultSet,41),Utilidades.obtenerValorExcel(resultSet,42),Utilidades.obtenerValorExcel(resultSet,43)
				    		,Utilidades.obtenerValorExcel(resultSet,44),Utilidades.obtenerValorExcel(resultSet,45),Utilidades.obtenerValorExcel(resultSet,46),Utilidades.obtenerValorExcel(resultSet,47)
				    		,Utilidades.obtenerValorExcel(resultSet,48)
					    );
				}
		 };

	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
		public void deleteCalendario(TPedidoAdicional tPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" DELETE FROM T_PEDIDO_ADICIONAL WHERE IDSESION LIKE ? ");
			query.append("AND CODCENTRO = ? AND PANTALLA = ?");
			params.add(tPedidoAdicional.getIdSesion()+"_%");
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());

			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}

	    @Override
		public void delete(TPedidoAdicional tPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" DELETE FROM T_PEDIDO_ADICIONAL WHERE IDSESION = ? ");
			query.append(" AND CODCENTRO = ? AND PANTALLA = ? ");
			if (null != tPedidoAdicional.getClasePedido()){
				query.append(" AND CLASEPEDIDO = ? ");
			}
			params.add(tPedidoAdicional.getIdSesion());

			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			if (null != tPedidoAdicional.getClasePedido()){
				params.add(tPedidoAdicional.getClasePedido());
			}

			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}
	    
	    @Override
		public void deleteHistorico() throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" DELETE FROM T_PEDIDO_ADICIONAL WHERE FECHACREACION < (SYSDATE - ?) ");

			params.add(Constantes.DIAS_ELIMINAR);

			
			try {
				//this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}
	    
	    @Override
		public void deleteArticulo(TPedidoAdicional tPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" DELETE FROM T_PEDIDO_ADICIONAL WHERE IDSESION = ? ");
			query.append("AND CODCENTRO = ? AND PANTALLA = ? AND CODARTICULO = ? ");
			if (null != tPedidoAdicional.getIdentificador()){
				query.append(" AND IDENTIFICADOR = ?");
			}
			if (null != tPedidoAdicional.getClasePedido()){
				query.append(" AND CLASEPEDIDO = ?");
			}
			if (null != tPedidoAdicional.getUuid()){
				query.append(" AND UUID = ?");
			}
			if (null != tPedidoAdicional.getIdentificadorSIA()){
				query.append(" AND IDENTIFICADOR_SIA = ?");
			}
			
			if (null != tPedidoAdicional.getIdentificadorVegalsa()){
				query.append(" AND IDENTIFICADOR_VEGALSA = ?");
				params.add(tPedidoAdicional.getIdentificadorVegalsa());
			}

			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getCodArticulo());
			
			if (null != tPedidoAdicional.getIdentificador()){
				params.add(tPedidoAdicional.getIdentificador());
			}
			if (null != tPedidoAdicional.getClasePedido()){
				params.add(tPedidoAdicional.getClasePedido());
				
			}
			if (null != tPedidoAdicional.getUuid()){
				params.add(tPedidoAdicional.getUuid());
			}
			if (null != tPedidoAdicional.getIdentificadorSIA()){
				params.add(tPedidoAdicional.getIdentificadorSIA());
			}


			
			try {
				Integer total = this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}


		}
	    
	    @Override
		public void updateErrorArticulo(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ? ");
			query.append("WHERE IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODARTICULO = ?");
			if (null != tPedidoAdicional.getIdentificador()){
				query.append(" AND IDENTIFICADOR = ?");
			}
			if (null != tPedidoAdicional.getClasePedido()){
				query.append(" AND CLASEPEDIDO = ?");
			}
			if (null != tPedidoAdicional.getIdentificadorSIA()){
				query.append(" AND IDENTIFICADOR_SIA = ?");
			}

			params.add(tPedidoAdicional.getCodError());
			params.add(tPedidoAdicional.getDescError());
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getCodArticulo());
			
			if (null != tPedidoAdicional.getIdentificador()){
				params.add(tPedidoAdicional.getIdentificador());
			}
			if (null != tPedidoAdicional.getClasePedido()){
				params.add(tPedidoAdicional.getClasePedido());
			}
			if (null != tPedidoAdicional.getIdentificadorSIA()){
				params.add(tPedidoAdicional.getIdentificadorSIA());
			}

			
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}
	    
	    
	    @Override
		public void updateModifCantArticulo(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ?, ");
			query.append(" CANTIDAD1 = ?, CANTIDAD2 = ?, CANTIDAD3 = ?, BORRABLE = ?, MODIFICABLE = ? ");
			query.append(" WHERE IDSESION = ? AND CLASEPEDIDO = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODARTICULO = ? ");

			params.add(tPedidoAdicional.getCodError());
			params.add(tPedidoAdicional.getDescError());
			params.add(tPedidoAdicional.getCantidad1());
			params.add(tPedidoAdicional.getCantidad2());
			params.add(tPedidoAdicional.getCantidad3());
			params.add(tPedidoAdicional.getBorrable());
			params.add(tPedidoAdicional.getModificable());
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getClasePedido());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getCodArticulo());

			

			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}


		}
	    @Override
	    public void updateModifFechaFinArticulo(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ?, FECHAFIN = ? ");
			query.append(" WHERE CLASEPEDIDO = ? AND IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODARTICULO = ? ");

			if (tPedidoAdicional.getIdentificador()!=null){
				query.append(" AND IDENTIFICADOR = ? ");
			}else{
				query.append(" AND IDENTIFICADOR_SIA = ? ");
			}
			params.add(tPedidoAdicional.getCodError());
			params.add(tPedidoAdicional.getDescError());
			params.add(tPedidoAdicional.getFechaFin());
			params.add(tPedidoAdicional.getClasePedido());
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getCodArticulo());
			if (tPedidoAdicional.getIdentificador()!=null){
				params.add(tPedidoAdicional.getIdentificador());
			}else{
				params.add(tPedidoAdicional.getIdentificadorSIA());
			}

			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}


		}
	    
	    @Override
		public void updateErroresTesteo(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ? ");
			query.append(" WHERE IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODERROR IN (?,?,?) ");
			query.append(this.getFiltroClasePedido(tPedidoAdicional));

			params.add("");
			params.add("");
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			String[] codErrores = tPedidoAdicional.getCodError().split("-");
			params.add(codErrores[0]);
			params.add(codErrores[1]);
			params.add(codErrores[2]);
			
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		}
	    
	    @Override
		public void updateErrores(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ? ");
			query.append(" WHERE IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ?");
			query.append(this.getFiltroClasePedido(tPedidoAdicional));

			params.add("");
			params.add("");
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}


		}
	    
	    @Override
		public void resetearGuardados(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET CODERROR = ?, DESCERROR = ? ");
			query.append(" WHERE IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODERROR = ? ");
			query.append(this.getFiltroClasePedido(tPedidoAdicional));

			params.add("");
			params.add("");
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(Constantes.MODIFICADO_CORRECTO_PANTALLA); //Aquí introducimos el código de guardado

			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}


		}

	    @Override
		public void insertAll(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer("INSERT INTO t_pedido_adicional "
													+ "( idsesion, clasepedido, codcentro, codarticulo, pantalla, identificador, descriptionart"
													+ ", unicajaserv, usuario, perfil, agrupacion, tipoaprovisionamiento, borrable, modificable"
													+ ", modificableindiv, cajaspedidas, fecentrega, fechainicio, fechafin, fecha2, fecha3, fecha4"
													+ ", capmax, capmin, cantidad1, cantidad2, cantidad3, tipopedido, cajas, excluir, oferta"
													+ ", esplanograma, denom_oferta, cant_min, cant_max, fecha5, fechainpil, cantidad4, cantidad5"
													+ ", tratamiento, fecha_hasta, estado, no_gestiona_pbl, desc_periodo, espacio_promo, identificador_sia"
													+ ", cod_art_grid, descrip_art_grid"
													+ ") "
												+ "VALUES "
													+ "( ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?"
													+ ")"
												);
			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0){
				
				TPedidoAdicional campo = new TPedidoAdicional();

				for (int i =0;i<listaTPedidoAdicional.size();i++){
					campo = (TPedidoAdicional)listaTPedidoAdicional.get(i);
					
					params = new ArrayList<Object>();
					
					params.add(campo.getIdSesion());
					params.add(campo.getClasePedido());
					params.add(campo.getCodCentro());
					params.add(campo.getCodArticulo());
					params.add(campo.getPantalla());
					params.add(campo.getIdentificador());
					params.add(campo.getDescriptionArt());
					params.add(campo.getUniCajaServ());
					params.add(campo.getUsuario());
					params.add(campo.getPerfil());
					params.add(campo.getAgrupacion());
					params.add(campo.getTipoAprovisionamiento());
					params.add(campo.getBorrable());
					params.add(campo.getModificable());
					params.add(campo.getModificableIndiv());
					params.add(campo.getCajasPedidas());
					params.add(campo.getFecEntrega());
					params.add(campo.getFechaInicio());
					params.add(campo.getFechaFin());
					params.add(campo.getFecha2());
					params.add(campo.getFecha3());
					params.add(campo.getFecha4());
					params.add(campo.getCapMax());
					params.add(campo.getCapMin());
					params.add(campo.getCantidad1());
					params.add(campo.getCantidad2());
					params.add(campo.getCantidad3());
					params.add(campo.getTipoPedido());
					params.add(campo.getCajas());
					params.add(campo.getExcluir());
					params.add(campo.getOferta());
					params.add(campo.getEsPlanograma());
					params.add(campo.getDescOferta());
					params.add(campo.getCantMax());
					params.add(campo.getCantMin());
					params.add(campo.getFecha5());
					params.add(campo.getFechaPilada());
					params.add(campo.getCantidad4());
					params.add(campo.getCantidad5());
					params.add(campo.getTratamiento());
					params.add(campo.getFechaHasta());
					params.add(campo.getEstado());
					params.add(campo.getNoGestionaPbl());
					params.add(campo.getDescPeriodo());
					params.add(campo.getEspacioPromo());
					params.add(campo.getIdentificadorSIA());
					params.add(campo.getCodArticuloGrid());
					params.add(campo.getDescriptionArtGrid());
					
					try {
						this.jdbcTemplate.update(query.toString(), params.toArray());
						
					} catch (Exception e){
						
						Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
					}
					
				}
			}
		}
	    
	    @Override
	    public List<TPedidoAdicional> findAll(TPedidoAdicional tPedidoAdicional) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT IDSESION, CLASEPEDIDO, CODCENTRO, CODARTICULO, PANTALLA, IDENTIFICADOR, IDENTIFICADOR_SIA, IDENTIFICADOR_VEGALSA, DESCRIPTIONART," +
	    										  " UNICAJASERV, USUARIO, PERFIL, AGRUPACION, TIPOAPROVISIONAMIENTO, BORRABLE, " +
	    										  " MODIFICABLE, MODIFICABLEINDIV, CAJASPEDIDAS, FECENTREGA, FECHAINICIO, FECHAFIN," +
	    										  " FECHA2, FECHA3, FECHA4, CAPMAX, CAPMIN, CANTIDAD1, CANTIDAD2, CANTIDAD3, TIPOPEDIDO, " +
	    										  " CAJAS, EXCLUIR, FECHACREACION,CODERROR,DESCERROR,OFERTA,ESPLANOGRAMA,DENOM_OFERTA,CANT_MAX,CANT_MIN, " +
	    										  " FECHA5, FECHAINPIL, CANTIDAD4, CANTIDAD5, TRATAMIENTO, FECHA_HASTA, " +
												  "	CASE WHEN " +
												  "	ESTADO is null and perfil = 3 THEN  " + //el estado es nulo y el perfil es centro
												  "	 (select '"+Constantes.PEDIDO_ESTADO_NO_ACTIVA+"' " + // se buscan los pedidos adicionales de central
												  "	  from t_pedido_adicional t2 " +
												  "   where t2.codcentro     = t.codcentro " +
												  "	    and t2.codarticulo   = t.codarticulo " +
												  "	    and t2.idsesion      = t.idsesion " +
												  "	    and t2.perfil        in (1,4) " +
												  //Si es encargo no se tienen en cuenta las planogramadas
												  "	    and ((t.clasepedido <> " + Constantes.CLASE_PEDIDO_ENCARGO + ") OR (t.clasepedido = " + Constantes.CLASE_PEDIDO_ENCARGO + " AND (t2.esplanograma <> 'S' OR t2.esplanograma IS NULL))) " +
												  "	    and ( " +
 												  //	    --  1.- caso -- la fecha inicio está entre fechas de central
												  "	       TO_DATE(nvl(t.fecentrega, t.fechainicio),'ddmmyyyy') between " +  
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " +
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechafin),'ddmmyyyy') " + 
												  "     or " + 
												  //        -- 2.- caso -- la fecha fin está entre fechas de central
												  "	        TO_DATE(nvl(t.fecentrega, t.fechafin),'ddmmyyyy') between " +    
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " +
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechafin),'ddmmyyyy') " + 
												  "	    or " +
												  //	    -- 3 - caso -- las fechas que abarca central son mayores
												  "	       (TO_DATE(NVL(t.fecentrega, t.fechainicio),'ddmmyyyy') < TO_DATE(NVL(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " + 
												  "	        TO_DATE(NVL(t.fecentrega, t.fechafin),'ddmmyyyy') > TO_DATE(NVL(t2.fecentrega,t2.fechafin),'ddmmyyyy'))) " +
												  "	   group by '"+Constantes.PEDIDO_ESTADO_NO_ACTIVA+"') " +
												  "	ELSE " +
												  "	  estado " +
  												  "	END ESTADO, NO_GESTIONA_PBL, DESC_PERIODO, ESPACIO_PROMO, COD_ART_GRID, DESCRIP_ART_GRID "     
	    										  
	    										+ " FROM T_PEDIDO_ADICIONAL t ");
	    
	        if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getClasePedido()!=null){
	        		 where.append(" AND CLASEPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getClasePedido());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getCodArticulo()!=null){
	        		 where.append(" AND CODARTICULO = ? ");
		        	 params.add(tPedidoAdicional.getCodArticulo());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificador()!=null){
	        		 where.append(" AND IDENTIFICADOR = ? ");
		        	 params.add(tPedidoAdicional.getIdentificador());	        		
	        	}
	        	if(tPedidoAdicional.getDescriptionArt()!=null){
	        		 where.append(" AND UPPER(DESCRIPTIONART) = upper(?) ");
		        	 params.add(tPedidoAdicional.getDescriptionArt());	        		
	        	}
	        	if(tPedidoAdicional.getUniCajaServ()!=null){
	        		 where.append(" AND UNICAJASERV = ? ");
		        	 params.add(tPedidoAdicional.getUniCajaServ());	        		
	        	}
	        	if(tPedidoAdicional.getUsuario()!=null){
	        		 where.append(" AND USUARIO = ? ");
		        	 params.add(tPedidoAdicional.getUsuario());	        		
	        	}
	        	if(tPedidoAdicional.getPerfil()!=null){
	        		 where.append(" AND PERFIL = ? ");
		        	 params.add(tPedidoAdicional.getPerfil());	        		
	        	}
	        	if(tPedidoAdicional.getAgrupacion()!=null){
	        		 where.append(" AND AGRUPACION = ? ");
		        	 params.add(tPedidoAdicional.getAgrupacion());	        		
	        	}
	        	if(tPedidoAdicional.getTipoAprovisionamiento()!=null){
	        		 where.append(" AND TIPOAPROVISIONAMIENTO = ? ");
		        	 params.add(tPedidoAdicional.getTipoAprovisionamiento());	        		
	        	}
	        	if(tPedidoAdicional.getBorrable()!=null){
	        		 where.append(" AND BORRABLE = ? ");
		        	 params.add(tPedidoAdicional.getBorrable());	        		
	        	}
	        	if(tPedidoAdicional.getModificable()!=null){
	        		 where.append(" AND MODIFICABLE = ? ");
		        	 params.add(tPedidoAdicional.getModificable());	        		
	        	}
	        	if(tPedidoAdicional.getModificableIndiv()!=null){
	        		 where.append(" AND MODIFICABLEINDIV = ? ");
		        	 params.add(tPedidoAdicional.getModificableIndiv());	        		
	        	}
	        	if(tPedidoAdicional.getCajasPedidas()!=null){
	        		 where.append(" AND CAJASPEDIDAS = ? ");
		        	 params.add(tPedidoAdicional.getCajasPedidas());	        		
	        	}
	        	if(tPedidoAdicional.getFecEntrega()!=null){
	        		 where.append(" AND FECENTREGA = ? ");
		        	 params.add(tPedidoAdicional.getFecEntrega());	        		
	        	}
	        	if(tPedidoAdicional.getFechaInicio()!=null){
	        		 where.append(" AND FECHAINICIO = ? ");
		        	 params.add(tPedidoAdicional.getFechaInicio());	        		
	        	}
	        	if(tPedidoAdicional.getFechaFin()!=null){
	        		 where.append(" AND FECHAFIN = ? ");
		        	 params.add(tPedidoAdicional.getFechaFin());	        		
	        	}
	        	if(tPedidoAdicional.getFecha2()!=null){
	        		 where.append(" AND FECHA2 = ? ");
		        	 params.add(tPedidoAdicional.getFecha2());	        		
	        	}
	        	if(tPedidoAdicional.getFecha3()!=null){
	        		 where.append(" AND FECHA3 = ? ");
		        	 params.add(tPedidoAdicional.getFecha3());	        		
	        	}
	        	if(tPedidoAdicional.getFecha4()!=null){
	        		 where.append(" AND FECHA4 = ? ");
		        	 params.add(tPedidoAdicional.getFecha4());	        		
	        	}
	        	if(tPedidoAdicional.getCapMax()!=null){
	        		 where.append(" AND CAPMAX = ? ");
		        	 params.add(tPedidoAdicional.getCapMax());	        		
	        	}
	        	if(tPedidoAdicional.getCapMin()!=null){
	        		 where.append(" AND CAPMIN = ? ");
		        	 params.add(tPedidoAdicional.getCapMin());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad1()!=null){
	        		 where.append(" AND CANTIDAD1 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad1());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad2()!=null){
	        		 where.append(" AND CANTIDAD2 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad2());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad3()!=null){
	        		 where.append(" AND CANTIDAD3 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad3());	        		
	        	}
	        	if(tPedidoAdicional.getTipoPedido()!=null){
	        		 where.append(" AND TIPOPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getTipoPedido());	        		
	        	}
	        	if(tPedidoAdicional.getCajas()!=null){
	        		 where.append(" AND CAJAS = ? ");
		        	 params.add(tPedidoAdicional.getCajas());	        		
	        	}
	        	if(tPedidoAdicional.getExcluir()!=null){
	        		 where.append(" AND EXCLUIR = ? ");
		        	 params.add(tPedidoAdicional.getExcluir());	        		
	        	}
	        	if(tPedidoAdicional.getFechaCreacion()!=null){
	        		 where.append(" AND TRUNC(FECHACREACION) = TRUNC(?) ");
		        	 params.add(tPedidoAdicional.getFechaCreacion());	        		
	        	}
	        	if(tPedidoAdicional.getCodError()!=null){
	        		 where.append(" AND CODERROR = ? ");
		        	 params.add(tPedidoAdicional.getCodError());	        		
	        	}
	        	if(tPedidoAdicional.getDescError()!=null){
	        		 where.append(" AND DESCERROR = ? ");
		        	 params.add(tPedidoAdicional.getDescError());	        		
	        	}
	        	if(tPedidoAdicional.getOferta()!=null){
	        		 where.append(" AND OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getOferta());	        		
	        	}
	        	if(tPedidoAdicional.getEsPlanograma()!=null){
	        		 where.append(" AND ESPLANOGRAMA = ? ");
		        	 params.add(tPedidoAdicional.getEsPlanograma());	        		
	        	}
	        	if(tPedidoAdicional.getDescOferta()!=null){
	        		 where.append(" AND DENOM_OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getDescOferta());	        		
	        	}
	        	if(tPedidoAdicional.getNoGestionaPbl()!=null){
	        		 where.append(" AND NO_GESTIONA_PBL = ? ");
		        	 params.add(tPedidoAdicional.getNoGestionaPbl());	        		
	        	}
	        	if(tPedidoAdicional.getDescPeriodo()!=null){
	        		 where.append(" AND ( TRIM(DESC_PERIODO) = TRIM(?) OR TRIM(DENOM_OFERTA) = TRIM(?) OR TRIM(OFERTA) = TRIM(?) ) ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 
	        	}
	        	if(tPedidoAdicional.getEspacioPromo()!=null){
	        		 where.append(" AND TRIM(ESPACIO_PROMO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificadorSIA()!=null){
	        		 where.append(" AND IDENTIFICADOR_SIA = ? ");
		        	 params.add(tPedidoAdicional.getIdentificadorSIA());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificadorVegalsa()!=null){
	        		 where.append(" AND IDENTIFICADOR_VEGALSA = ? ");
		        	 params.add(tPedidoAdicional.getIdentificadorVegalsa());	        		
	        	}
	        	//Filtramos CLASEPEDIDO en el caso de que haya que filtrar una lista de clases pedidos
	        	if (tPedidoAdicional.getClasePedido()!=null){
		        	if (tPedidoAdicional.getListaFiltroClasePedido()== null){
		        		List<Long> listaFiltro = Arrays.asList(tPedidoAdicional.getClasePedido());
		        		tPedidoAdicional.setListaFiltroClasePedido(listaFiltro);
		        	}
		        	else if (!tPedidoAdicional.getListaFiltroClasePedido().contains(tPedidoAdicional.getClasePedido())){
		        		tPedidoAdicional.getListaFiltroClasePedido().add(tPedidoAdicional.getClasePedido());
		        	}
	        	}
	        	if (tPedidoAdicional.getListaFiltroClasePedido()!= null && tPedidoAdicional.getListaFiltroClasePedido().size()>0){
	        		int listaFiltroSize = tPedidoAdicional.getListaFiltroClasePedido().size();
	        		boolean esMac = tPedidoAdicional.getMAC() != null && Constantes.PEDIDO_ADICIONAL_MAC.equals(tPedidoAdicional.getMAC());
	        		boolean primera = true;	     
        			where.append(" AND ");
        			where.append(" ( ");
	        		for (int i = 0; i < listaFiltroSize; i++){
	        			Long clasePedido = tPedidoAdicional.getListaFiltroClasePedido().get(i);
	        			StringBuffer condicion = new StringBuffer();
	        			
	        			// Clase pedido 2
	        			if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 2 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");
	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 2 y perfil centro (igual a PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");
    		        			
	        				}
	        			}
	        			// Clase pedido 3
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 3 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 3 y perfil centro (igual a PERFIL_CENTRO)
    		        			condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        			}
	        			// Clase pedido 7
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			// Clase pedido 8
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			else{
	        				condicion.append("      ( CLASEPEDIDO = ").append(clasePedido.toString()).append(" ) ");
		        			
	        			}
	        			
	        			if (primera && !condicion.toString().isEmpty()){
	        				where.append(condicion.toString());
	        				primera = false;
	        			} else  if ( !condicion.toString().isEmpty()){
	        				where.append("        OR ").append(condicion.toString());
	        				
	        			}
	        		}
        			where.append(" ) ");
	        	}
	        }
	        
	        query.append(where);
	        
	        StringBuffer order = new StringBuffer(3000);
	        if (tPedidoAdicional.getOrderBy() != null && !tPedidoAdicional.getOrderBy().equals(""))
	        {
	        	order.append(" order by ");
	        	if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("FECHAINICIO"))||
	        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("FECHAFIN"))||
	        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("FECENTREGA")))
	        	{
	        		order.append("TO_DATE(");
	        		order.append(tPedidoAdicional.getOrderBy());
	        		order.append(",'DDMMYYYY')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("UNIDADESPEDIDAS")))
	        	{
	        		order.append("NVL(");
	        		order.append("CAJASPEDIDAS");
	        		order.append(",'0')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CAJASPEDIDAS")))
	        	{
	        		order.append("NVL(");
	        		order.append("ROUND(CAJASPEDIDAS / UNICAJASERV , 2)");
	        		order.append(",'0')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CAPMAX"))||
		        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CAPMIN"))||
		        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CANTIDAD1"))||
		        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CANTIDAD2"))||
		        		(tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CANTIDAD3")))
	        	{
	        		order.append("NVL(");
	        		order.append(tPedidoAdicional.getOrderBy());
	        		order.append(",'0')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("MENSAJE")))
	        	{
	        		order.append("DECODE(NVL(CODERROR,0),0,DECODE(NVL(ESTADO,''),'NOACT',3,4),9,2,1)");
	        		if (tPedidoAdicional.getSortOrder() != null && !tPedidoAdicional.getSortOrder().equals(""))
	    	        {
	        			order.append(" ");
	        			order.append(tPedidoAdicional.getSortOrder());
	    	        }
	        		order.append(", CODARTICULO");
	        		if (tPedidoAdicional.getSortOrder() != null && !tPedidoAdicional.getSortOrder().equals(""))
	    	        {
	        			order.append(" ");
	        			order.append(tPedidoAdicional.getSortOrder());
	    	        }
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CANTMIN")))
	        	{
	        		order.append("NVL(");
	        		order.append("CANT_MIN");
	        		order.append(",'0')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CANTMAX")))
	        	{
	        		order.append("NVL(");
	        		order.append("CANT_MAX");
	        		order.append(",'0')");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("DESCOFERTA")))
	        	{
	        		order.append("DENOM_OFERTA");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("CODARTICULOGRID")))
	        	{
	        		order.append("COD_ART_GRID");
	        	}
	        	else if ((tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("DESCRIPTIONARTGRID")))
	        	{
	        		order.append("DESCRIP_ART_GRID");
	        	}
	        	else
	        	{	
	        		order.append(tPedidoAdicional.getOrderBy());
	        	}
	        }
	        else
	        {
	        	order.append(" order by AGRUPACION,CODARTICULO ");
	        }
			query.append(order);
			
			if (tPedidoAdicional.getOrderBy() != null && !tPedidoAdicional.getOrderBy().equals("") && !tPedidoAdicional.getOrderBy().trim().toUpperCase().equals("MENSAJE"))
	        {
				query.append(" ");
				query.append(tPedidoAdicional.getSortOrder());
	        }

			logger.debug(query.toString());
			List<TPedidoAdicional> tPedidoAdicionalLista = null;		
			
			try {
				tPedidoAdicionalLista = (List<TPedidoAdicional>) this.jdbcTemplate.query(query.toString(),this.rwTPedidoAdicionalMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return tPedidoAdicionalLista;
	    }
	    
	    @Override
	    public Long findAllCount(TPedidoAdicional tPedidoAdicional) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(CODARTICULO)" 
	    										+ " FROM T_PEDIDO_ADICIONAL ");
	    
	    	
	        if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getClasePedido()!=null){
	        		 where.append(" AND CLASEPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getClasePedido());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getCodArticulo()!=null){
	        		 where.append(" AND CODARTICULO = ? ");
		        	 params.add(tPedidoAdicional.getCodArticulo());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificador()!=null){
	        		 where.append(" AND IDENTIFICADOR = ? ");
		        	 params.add(tPedidoAdicional.getIdentificador());	        		
	        	}
	        	if(tPedidoAdicional.getDescriptionArt()!=null){
	        		 where.append(" AND UPPER(DESCRIPTIONART) = upper(?) ");
		        	 params.add(tPedidoAdicional.getDescriptionArt());	        		
	        	}
	        	if(tPedidoAdicional.getUniCajaServ()!=null){
	        		 where.append(" AND UNICAJASERV = ? ");
		        	 params.add(tPedidoAdicional.getUniCajaServ());	        		
	        	}
	        	if(tPedidoAdicional.getUsuario()!=null){
	        		 where.append(" AND USUARIO = ? ");
		        	 params.add(tPedidoAdicional.getUsuario());	        		
	        	}
	        	if(tPedidoAdicional.getPerfil()!=null){
	        		 where.append(" AND PERFIL = ? ");
		        	 params.add(tPedidoAdicional.getPerfil());	        		
	        	}
	        	if(tPedidoAdicional.getAgrupacion()!=null){
	        		 where.append(" AND AGRUPACION = ? ");
		        	 params.add(tPedidoAdicional.getAgrupacion());	        		
	        	}
	        	if(tPedidoAdicional.getTipoAprovisionamiento()!=null){
	        		 where.append(" AND TIPOAPROVISIONAMIENTO = ? ");
		        	 params.add(tPedidoAdicional.getTipoAprovisionamiento());	        		
	        	}
	        	if(tPedidoAdicional.getBorrable()!=null){
	        		 where.append(" AND BORRABLE = ? ");
		        	 params.add(tPedidoAdicional.getBorrable());	        		
	        	}
	        	if(tPedidoAdicional.getModificable()!=null){
	        		 where.append(" AND MODIFICABLE = ? ");
		        	 params.add(tPedidoAdicional.getModificable());	        		
	        	}
	        	if(tPedidoAdicional.getModificableIndiv()!=null){
	        		 where.append(" AND MODIFICABLEINDIV = ? ");
		        	 params.add(tPedidoAdicional.getModificableIndiv());	        		
	        	}
	        	if(tPedidoAdicional.getCajasPedidas()!=null){
	        		 where.append(" AND CAJASPEDIDAS = ? ");
		        	 params.add(tPedidoAdicional.getCajasPedidas());	        		
	        	}
	        	if(tPedidoAdicional.getFecEntrega()!=null){
	        		 where.append(" AND FECENTREGA = ? ");
		        	 params.add(tPedidoAdicional.getFecEntrega());	        		
	        	}
	        	if(tPedidoAdicional.getFechaInicio()!=null){
	        		 where.append(" AND FECHAINICIO = ? ");
		        	 params.add(tPedidoAdicional.getFechaInicio());	        		
	        	}
	        	if(tPedidoAdicional.getFechaFin()!=null){
	        		 where.append(" AND FECHAFIN = ? ");
		        	 params.add(tPedidoAdicional.getFechaFin());	        		
	        	}
	        	if(tPedidoAdicional.getFecha2()!=null){
	        		 where.append(" AND FECHA2 = ? ");
		        	 params.add(tPedidoAdicional.getFecha2());	        		
	        	}
	        	if(tPedidoAdicional.getFecha3()!=null){
	        		 where.append(" AND FECHA3 = ? ");
		        	 params.add(tPedidoAdicional.getFecha3());	        		
	        	}
	        	if(tPedidoAdicional.getFecha4()!=null){
	        		 where.append(" AND FECHA4 = ? ");
		        	 params.add(tPedidoAdicional.getFecha4());	        		
	        	}
	        	if(tPedidoAdicional.getCapMax()!=null){
	        		 where.append(" AND CAPMAX = ? ");
		        	 params.add(tPedidoAdicional.getCapMax());	        		
	        	}
	        	if(tPedidoAdicional.getCapMin()!=null){
	        		 where.append(" AND CAPMIN = ? ");
		        	 params.add(tPedidoAdicional.getCapMin());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad1()!=null){
	        		 where.append(" AND CANTIDAD1 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad1());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad2()!=null){
	        		 where.append(" AND CANTIDAD2 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad2());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad3()!=null){
	        		 where.append(" AND CANTIDAD3 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad3());	        		
	        	}
	        	if(tPedidoAdicional.getTipoPedido()!=null){
	        		 where.append(" AND TIPOPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getTipoPedido());	        		
	        	}
	        	if(tPedidoAdicional.getCajas()!=null){
	        		 where.append(" AND CAJAS = ? ");
		        	 params.add(tPedidoAdicional.getCajas());	        		
	        	}
	        	if(tPedidoAdicional.getExcluir()!=null){
	        		 where.append(" AND EXCLUIR = ? ");
		        	 params.add(tPedidoAdicional.getExcluir());	        		
	        	}
	        	if(tPedidoAdicional.getFechaCreacion()!=null){
	        		 where.append(" AND TRUNC(FECHACREACION) = TRUNC(?) ");
		        	 params.add(tPedidoAdicional.getFechaCreacion());	        		
	        	}
	        	if(tPedidoAdicional.getCodError()!=null){
	        		 where.append(" AND CODERROR = ? ");
		        	 params.add(tPedidoAdicional.getCodError());	        		
	        	}
	        	if(tPedidoAdicional.getDescError()!=null){
	        		 where.append(" AND DESCERROR = ? ");
		        	 params.add(tPedidoAdicional.getDescError());	        		
	        	}
	        	if(tPedidoAdicional.getOferta()!=null){
	        		 where.append(" AND OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getOferta());	        		
	        	}
	        	if(tPedidoAdicional.getEsPlanograma()!=null){
	        		 where.append(" AND ESPLANOGRAMA = ? ");
		        	 params.add(tPedidoAdicional.getEsPlanograma());	        		
	        	}
	        	if(tPedidoAdicional.getDescOferta()!=null){
	        		 where.append(" AND DENOM_OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getDescOferta());	        		
	        	}
	        	if(tPedidoAdicional.getNoGestionaPbl()!=null){
	        		 where.append(" AND NO_GESTIONA_PBL = ? ");
		        	 params.add(tPedidoAdicional.getNoGestionaPbl());	        		
	        	}
	        	if(tPedidoAdicional.getDescPeriodo()!=null){
	        		 where.append(" AND ( TRIM(DESC_PERIODO) = TRIM(?) OR TRIM(DENOM_OFERTA) = TRIM(?) OR TRIM(OFERTA) = TRIM(?) ) ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 
	        	}
	        	if(tPedidoAdicional.getEspacioPromo()!=null){
	        		 where.append(" AND TRIM(ESPACIO_PROMO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificadorSIA()!=null){
	        		 where.append(" AND IDENTIFICADOR_SIA = ? ");
		        	 params.add(tPedidoAdicional.getIdentificadorSIA());	        		
	        	}
	        	
	        	//Filtramos CLASEPEDIDO en el caso de que haya que filtrar una lista de clases pedidos
	        	if (tPedidoAdicional.getClasePedido()!=null){
		        	if (tPedidoAdicional.getListaFiltroClasePedido()== null){
		        		List<Long> listaFiltro = Arrays.asList(tPedidoAdicional.getClasePedido());
		        		tPedidoAdicional.setListaFiltroClasePedido(listaFiltro);
		        	}
		        	else if (!tPedidoAdicional.getListaFiltroClasePedido().contains(tPedidoAdicional.getClasePedido())){
		        		tPedidoAdicional.getListaFiltroClasePedido().add(tPedidoAdicional.getClasePedido());
		        	}
	        	}
	        	if (tPedidoAdicional.getListaFiltroClasePedido()!= null && tPedidoAdicional.getListaFiltroClasePedido().size()>0){
	        		int listaFiltroSize = tPedidoAdicional.getListaFiltroClasePedido().size();
	        		boolean esMac = tPedidoAdicional.getMAC() != null && Constantes.PEDIDO_ADICIONAL_MAC.equals(tPedidoAdicional.getMAC());
	        		boolean primera = true;	     
        			where.append(" AND ");
        			where.append(" ( ");
	        		for (int i = 0; i < listaFiltroSize; i++){
	        			Long clasePedido = tPedidoAdicional.getListaFiltroClasePedido().get(i);
	        			StringBuffer condicion = new StringBuffer();
	        			
	        			// Clase pedido 2
	        			if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 2 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");
	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 2 y perfil centro (igual a PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");
    		        			
	        				}
	        			}
	        			// Clase pedido 3
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 3 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 3 y perfil centro (igual a PERFIL_CENTRO)
    		        			condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        			}
	        			// Clase pedido 7
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			// Clase pedido 8
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			else{
	        				condicion.append("      ( CLASEPEDIDO = ").append(clasePedido.toString()).append(" ) ");
		        			
	        			}
	        			
	        			if (primera && !condicion.toString().isEmpty()){
	        				where.append(condicion.toString());
	        				primera = false;
	        			} else  if ( !condicion.toString().isEmpty()){
	        				where.append("        OR ").append(condicion.toString());
	        				
	        			}
	        		}
        			where.append(" ) ");
	        	}
	        }
	        
	        query.append(where);
	        logger.debug(query);

		    Long cont = null;
		    try {
				cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return cont;
	    }
	    
	    @Override
		public void insertAllNuevoOferta(List<TPedidoAdicional> listaTPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer("INSERT INTO t_pedido_adicional "
													+ "( idsesion, usuario, clasepedido, codcentro, codarticulo"
													+ ", pantalla, descriptionart, unicajaserv,  tipoaprovisionamiento"
													+ ", modificable, cajaspedidas, fechaminima, fechainicio, fechafin"
													+ ", fecha2, fecha3, fecha4, capmax, capmin, cantidad1, cantidad2"
													+ ", cantidad3, tipopedido, excluir, oferta, desc_periodo, espacio_promo"
													+ ")"
												+ "VALUES"
													+ "( ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?, ?"
													+ ", ?, ?, ?, ?, ?, ?"
													+ ")"
													);

			if (listaTPedidoAdicional != null && listaTPedidoAdicional.size()>0){
				
				TPedidoAdicional campo = new TPedidoAdicional();

				for (int i =0;i<listaTPedidoAdicional.size();i++){
					
					campo = (TPedidoAdicional)listaTPedidoAdicional.get(i);
					
					params = new ArrayList<Object>();
					
					params.add(campo.getIdSesion());
					params.add(campo.getUsuario());
					params.add(campo.getClasePedido());
					params.add(campo.getCodCentro());
					params.add(campo.getCodArticulo());
					params.add(campo.getPantalla());
					params.add(campo.getDescriptionArt());
					params.add(campo.getUniCajaServ());
					params.add(campo.getTipoAprovisionamiento());
					params.add(campo.getModificable());
					params.add(campo.getCajasPedidas());
					params.add(campo.getFechaMinima());
					params.add(campo.getFechaInicio());
					params.add(campo.getFechaFin());
					params.add(campo.getFecha2());
					params.add(campo.getFecha3());
					params.add(campo.getFecha4());
					params.add(campo.getCapMax());
					params.add(campo.getCapMin());
					params.add(campo.getCantidad1());
					params.add(campo.getCantidad2());
					params.add(campo.getCantidad3());
					params.add(campo.getTipoPedido());
					params.add(campo.getExcluir());
					params.add(campo.getOferta());
					params.add(campo.getDescPeriodo());
					params.add(campo.getEspacioPromo());

					try {
						this.jdbcTemplate.update(query.toString(), params.toArray());
						
					} catch (Exception e){
						Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
					}
						
				}
				
			}
			
		}
	    
	    @Override
	    public List<TPedidoAdicional> findAllPaginate(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT IDSESION, CLASEPEDIDO, CODCENTRO, CODARTICULO, PANTALLA, IDENTIFICADOR, IDENTIFICADOR_SIA, IDENTIFICADOR_VEGALSA, DESCRIPTIONART," +
	    										  " UNICAJASERV, USUARIO, PERFIL, AGRUPACION, TIPOAPROVISIONAMIENTO, BORRABLE, " +
	    										  " MODIFICABLE, MODIFICABLEINDIV, CAJASPEDIDAS, FECENTREGA, FECHAINICIO, FECHAFIN," +
	    										  " FECHA2, FECHA3, FECHA4, CAPMAX, CAPMIN, CANTIDAD1, CANTIDAD2, CANTIDAD3, TIPOPEDIDO, " +
	    										  " CAJAS, EXCLUIR, FECHACREACION,CODERROR,DESCERROR,OFERTA,ESPLANOGRAMA, FECHAMINIMA, " +
	    										  " CANT_MAX, CANT_MIN, DENOM_OFERTA, FECHA5, FECHAINPIL, CANTIDAD4, CANTIDAD5, TRATAMIENTO, " + this.getMappedField("MENSAJE")+", FECHA_HASTA, " +
	    										  
												  "	CASE WHEN " +
												  "	ESTADO is null and perfil = 3 THEN " + //el estado es nulo y el perfil es centro
												  "	 (select '"+Constantes.PEDIDO_ESTADO_NO_ACTIVA+"' " + // se buscan los pedidos adicionales de central
												  "	  from t_pedido_adicional t2 " +
												  "   where t2.codcentro     = t.codcentro " +
												  "	    and t2.codarticulo   = t.codarticulo " +
												  "	    and t2.idsesion      = t.idsesion " +
												  "	    and t2.perfil        in (1,4) " +
												  //Si es encargo no se tienen en cuenta las planogramadas
												  "	    and ((t.clasepedido <> " + Constantes.CLASE_PEDIDO_ENCARGO + ") OR (t.clasepedido = " + Constantes.CLASE_PEDIDO_ENCARGO + " AND (t2.esplanograma <> 'S' OR t2.esplanograma IS NULL))) " +
												  "	    and ( " +
 												  //	    --  1.- caso -- la fecha inicio está entre fechas de central
												  "	       TO_DATE(nvl(t.fecentrega, t.fechainicio),'ddmmyyyy') between " +  
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " +
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechafin),'ddmmyyyy') " + 
												  "     or " + 
												  //        -- 2.- caso -- la fecha fin está entre fechas de central
												  "	        TO_DATE(nvl(t.fecentrega, t.fechafin),'ddmmyyyy') between " +    
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " +
												  "	                 TO_DATE(nvl(t2.fecentrega,t2.fechafin),'ddmmyyyy') " + 
												  "	    or " +
												  //	    -- 3 - caso -- las fechas que abarca central son mayores
												  "	       (TO_DATE(NVL(t.fecentrega,t.fechainicio),'ddmmyyyy') < TO_DATE(NVL(t2.fecentrega,t2.fechainicio),'ddmmyyyy') and " + 
												  "	        TO_DATE(NVL(t.fecentrega,t.fechafin),'ddmmyyyy') > TO_DATE(NVL(t2.fecentrega,t2.fechafin),'ddmmyyyy'))) " +
												  "	   group by '"+Constantes.PEDIDO_ESTADO_NO_ACTIVA+"') " +
												  "	ELSE " +
												  "	  estado " +
												  "	END ESTADO, NO_GESTIONA_PBL, DESC_PERIODO, ESPACIO_PROMO ,COD_ART_GRID, DESCRIP_ART_GRID "      
	    										  
	    										+ " FROM T_PEDIDO_ADICIONAL t ");
	    
	    	
	        if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getClasePedido()!=null){
	        		 where.append(" AND CLASEPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getClasePedido());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getCodArticulo()!=null){
	        		 where.append(" AND CODARTICULO = ? ");
		        	 params.add(tPedidoAdicional.getCodArticulo());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificador()!=null){
	        		 where.append(" AND IDENTIFICADOR = ? ");
		        	 params.add(tPedidoAdicional.getIdentificador());	        		
	        	}
	        	if(tPedidoAdicional.getDescriptionArt()!=null){
	        		 where.append(" AND UPPER(DESCRIPTIONART) = upper(?) ");
		        	 params.add(tPedidoAdicional.getDescriptionArt());	        		
	        	}
	        	if(tPedidoAdicional.getUniCajaServ()!=null){
	        		 where.append(" AND UNICAJASERV = ? ");
		        	 params.add(tPedidoAdicional.getUniCajaServ());	        		
	        	}
	        	if(tPedidoAdicional.getAgrupacion()!=null){
	        		 where.append(" AND AGRUPACION = ? ");
		        	 params.add(tPedidoAdicional.getAgrupacion());	        		
	        	}
	        	if(tPedidoAdicional.getTipoAprovisionamiento()!=null){
	        		 where.append(" AND TIPOAPROVISIONAMIENTO = ? ");
		        	 params.add(tPedidoAdicional.getTipoAprovisionamiento());	        		
	        	}
	        	if(tPedidoAdicional.getBorrable()!=null){
	        		 where.append(" AND BORRABLE = ? ");
		        	 params.add(tPedidoAdicional.getBorrable());	        		
	        	}
	        	if(tPedidoAdicional.getModificable()!=null){
	        		 where.append(" AND MODIFICABLE = ? ");
		        	 params.add(tPedidoAdicional.getModificable());	        		
	        	}
	        	if(tPedidoAdicional.getModificableIndiv()!=null){
	        		 where.append(" AND MODIFICABLEINDIV = ? ");
		        	 params.add(tPedidoAdicional.getModificableIndiv());	        		
	        	}
	        	if(tPedidoAdicional.getCajasPedidas()!=null){
	        		 where.append(" AND CAJASPEDIDAS = ? ");
		        	 params.add(tPedidoAdicional.getCajasPedidas());	        		
	        	}
	        	if(tPedidoAdicional.getFecEntrega()!=null){
	        		 where.append(" AND FECENTREGA = ? ");
		        	 params.add(tPedidoAdicional.getFecEntrega());	        		
	        	}
	        	if(tPedidoAdicional.getFechaInicio()!=null){
	        		 where.append(" AND FECHAINICIO = ? ");
		        	 params.add(tPedidoAdicional.getFechaInicio());	        		
	        	}
	        	if(tPedidoAdicional.getFechaFin()!=null){
	        		 where.append(" AND FECHAFIN = ? ");
		        	 params.add(tPedidoAdicional.getFechaFin());	        		
	        	}
	        	if(tPedidoAdicional.getFecha2()!=null){
	        		 where.append(" AND FECHA2 = ? ");
		        	 params.add(tPedidoAdicional.getFecha2());	        		
	        	}
	        	if(tPedidoAdicional.getFecha3()!=null){
	        		 where.append(" AND FECHA3 = ? ");
		        	 params.add(tPedidoAdicional.getFecha3());	        		
	        	}
	        	if(tPedidoAdicional.getFecha4()!=null){
	        		 where.append(" AND FECHA4 = ? ");
		        	 params.add(tPedidoAdicional.getFecha4());	        		
	        	}
	        	if(tPedidoAdicional.getCapMax()!=null){
	        		 where.append(" AND CAPMAX = ? ");
		        	 params.add(tPedidoAdicional.getCapMax());	        		
	        	}
	        	if(tPedidoAdicional.getCapMin()!=null){
	        		 where.append(" AND CAPMIN = ? ");
		        	 params.add(tPedidoAdicional.getCapMin());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad1()!=null){
	        		 where.append(" AND CANTIDAD1 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad1());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad2()!=null){
	        		 where.append(" AND CANTIDAD2 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad2());	        		
	        	}
	        	if(tPedidoAdicional.getCantidad3()!=null){
	        		 where.append(" AND CANTIDAD3 = ? ");
		        	 params.add(tPedidoAdicional.getCantidad3());	        		
	        	}
	        	if(tPedidoAdicional.getTipoPedido()!=null){
	        		 where.append(" AND TIPOPEDIDO = ? ");
		        	 params.add(tPedidoAdicional.getTipoPedido());	        		
	        	}
	        	if(tPedidoAdicional.getCajas()!=null){
	        		 where.append(" AND CAJAS = ? ");
		        	 params.add(tPedidoAdicional.getCajas());	        		
	        	}
	        	if(tPedidoAdicional.getExcluir()!=null){
	        		 where.append(" AND EXCLUIR = ? ");
		        	 params.add(tPedidoAdicional.getExcluir());	        		
	        	}
	        	if(tPedidoAdicional.getFechaCreacion()!=null){
	        		 where.append(" AND TRUNC(FECHACREACION) = TRUNC(?) ");
		        	 params.add(tPedidoAdicional.getFechaCreacion());	        		
	        	}
	        	if(tPedidoAdicional.getCodError()!=null){
	        		 where.append(" AND CODERROR = ? ");
		        	 params.add(tPedidoAdicional.getCodError());	        		
	        	}
	        	if(tPedidoAdicional.getDescError()!=null){
	        		 where.append(" AND DESCERROR = ? ");
		        	 params.add(tPedidoAdicional.getDescError());	        		
	        	}
	        	if(tPedidoAdicional.getOferta()!=null){
	        		 where.append(" AND OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getOferta());	        		
	        	}
	        	if(tPedidoAdicional.getEsPlanograma()!=null){
	        		 where.append(" AND ESPLANOGRAMA = ? ");
		        	 params.add(tPedidoAdicional.getEsPlanograma());	        		
	        	}
	        	if(tPedidoAdicional.getDescOferta()!=null){
	        		 where.append(" AND DENOM_OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getDescOferta());	        		
	        	}
	        	if(tPedidoAdicional.getNoGestionaPbl()!=null){
	        		 where.append(" AND NO_GESTIONA_PBL = ? ");
		        	 params.add(tPedidoAdicional.getNoGestionaPbl());	        		
	        	}
	        	if(tPedidoAdicional.getDescPeriodo()!=null){
	        		 where.append(" AND ( TRIM(DESC_PERIODO) = TRIM(?) OR TRIM(DENOM_OFERTA) = TRIM(?) OR TRIM(OFERTA) = TRIM(?) ) ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
		        	 params.add(tPedidoAdicional.getDescPeriodo());	     
	        	}
	        	if(tPedidoAdicional.getEspacioPromo()!=null){
	        		 where.append(" AND TRIM(ESPACIO_PROMO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}
	        	if(tPedidoAdicional.getIdentificadorSIA()!=null){
	        		 where.append(" AND IDENTIFICADOR_SIA = ? ");
		        	 params.add(tPedidoAdicional.getIdentificadorSIA());	        		
	        	}

	        	//Filtramos CLASEPEDIDO en el caso de que haya que filtrar una lista de clases pedidos
	        	if (tPedidoAdicional.getClasePedido()!=null){
		        	if (tPedidoAdicional.getListaFiltroClasePedido()== null){
		        		List<Long> listaFiltro = Arrays.asList(tPedidoAdicional.getClasePedido());
		        		tPedidoAdicional.setListaFiltroClasePedido(listaFiltro);
		        	}
		        	else if (!tPedidoAdicional.getListaFiltroClasePedido().contains(tPedidoAdicional.getClasePedido())){
		        		tPedidoAdicional.getListaFiltroClasePedido().add(tPedidoAdicional.getClasePedido());
		        	}
	        	}
	        	if (tPedidoAdicional.getListaFiltroClasePedido()!= null && tPedidoAdicional.getListaFiltroClasePedido().size()>0){
	        		int listaFiltroSize = tPedidoAdicional.getListaFiltroClasePedido().size();
	        		boolean esMac = tPedidoAdicional.getMAC() != null && Constantes.PEDIDO_ADICIONAL_MAC.equals(tPedidoAdicional.getMAC());
	        		boolean primera = true;	     
        			where.append(" AND ");
        			where.append(" ( ");
	        		for (int i = 0; i < listaFiltroSize; i++){
	        			Long clasePedido = tPedidoAdicional.getListaFiltroClasePedido().get(i);
	        			StringBuffer condicion = new StringBuffer();
	        			
	        			// Clase pedido 2
	        			if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 2 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");
	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 2 y perfil centro (igual a PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");
    		        			
	        				}
	        			}
	        			// Clase pedido 3
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 3 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 3 y perfil centro (igual a PERFIL_CENTRO)
    		        			condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        			}
	        			// Clase pedido 7
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			// Clase pedido 8
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			else{
	        				condicion.append("      ( CLASEPEDIDO = ").append(clasePedido.toString()).append(" ) ");
		        			
	        			}
	        			
	        			if (primera && !condicion.toString().isEmpty()){
	        				where.append(condicion.toString());
	        				primera = false;
	        			} else  if ( !condicion.toString().isEmpty()){
	        				where.append("        OR ").append(condicion.toString());
	        				
	        			}
	        		}
        			where.append(" ) ");
	        	}
	        }
	        
	        query.append(where);
	        
	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			if (pagination != null) {
				if (pagination.getSort() != null) {
					order.append(" order by " + this.getMappedFieldSort(pagination.getSort()) + " "+ pagination.getAscDsc());
					query.append(order);
				}
			} 
			
			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

			List<TPedidoAdicional> tPedidoAdicionalLista = null;		
			
			try {
				tPedidoAdicionalLista = (List<TPedidoAdicional>) this.jdbcTemplate.query(query.toString(),this.rwTPedidoAdicionalNuevoOfertaMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		    return tPedidoAdicionalLista;
	    }
	    
		@Override
		public void updatePedidosValidar(final  List<CamposSeleccionadosVC> listToUpdate, final String sesionID) throws Exception{
	
			List<Object> params = new ArrayList<Object>();
	    	
	    	for (CamposSeleccionadosVC camposSeleccionados : listToUpdate){
		    	StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET  CANTIDAD1 = ?, CANTIDAD2 = ?, CANTIDAD3 = ?, CANTIDAD4 = ?, CANTIDAD5 = ?, CODERROR = ?, DESCERROR = ? ");
		    	query.append(" WHERE IDSESION = ? AND CODCENTRO = ? AND PANTALLA = ? ");
		    	
		    	if (camposSeleccionados.getIdentificador() != null){
		    		query.append(" AND IDENTIFICADOR = ? ");
		    	}else{
		    		query.append(" AND IDENTIFICADOR_SIA = ?");
		    	}
	    		params = new ArrayList<Object>();
				
				params.add(camposSeleccionados.getCantidad1());
				params.add(camposSeleccionados.getCantidad2());
				params.add(camposSeleccionados.getCantidad3());
				params.add(camposSeleccionados.getCantidad4());
				params.add(camposSeleccionados.getCantidad5());
				params.add(camposSeleccionados.getCodError());
				params.add(camposSeleccionados.getDescError());
				params.add(sesionID);
				params.add(camposSeleccionados.getCodCentro());
				params.add(Constantes.PANTALLA_LISTADOS);
				if (camposSeleccionados.getIdentificador() != null){
					params.add(camposSeleccionados.getIdentificador());
				}else{
					params.add(camposSeleccionados.getIdentificadorSIA());
				}

				
				try {
					this.jdbcTemplate.update(query.toString(), params.toArray());
					
				} catch (Exception e){
					
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				}
	    	}
	    	
			       
	 	}
	    
	    @Override
		public void updatePedido(TPedidoAdicional tPedidoAdicional) throws Exception{
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET FECHAINICIO = ?, FECHAFIN = ?, FECHAMINIMA = ?, ");
			query.append(" CANTIDAD1 = ?, CANTIDAD2 = ?, CANTIDAD3 = ?, CANTIDAD4 = ?, CANTIDAD5 = ?, CAPMAX = ?, CAPMIN = ?, ");
			query.append(" FECHA2 = ?, FECHA3 = ?, FECHAINPIL = ?, FECENTREGA = ?, CAJASPEDIDAS = ?, CAJAS = ?, EXCLUIR = ?, CODERROR = ?, DESCERROR = ?, MODIFICABLE = ? ");
			query.append(" WHERE IDSESION = ? AND CLASEPEDIDO = ? AND CODCENTRO = ? AND PANTALLA = ? AND CODARTICULO = ? ");
			
			params.add(tPedidoAdicional.getFechaInicio());
			params.add(tPedidoAdicional.getFechaFin());
			params.add(tPedidoAdicional.getFechaMinima());
			params.add(tPedidoAdicional.getCantidad1());
			params.add(tPedidoAdicional.getCantidad2());
			params.add(tPedidoAdicional.getCantidad3());
			params.add(tPedidoAdicional.getCantidad4());
			params.add(tPedidoAdicional.getCantidad5());
			params.add(tPedidoAdicional.getCapMax());
			params.add(tPedidoAdicional.getCapMin());
			params.add(tPedidoAdicional.getFecha2());
			params.add(tPedidoAdicional.getFecha3());
			params.add(tPedidoAdicional.getFechaPilada());
			params.add(tPedidoAdicional.getFecEntrega());
			params.add(tPedidoAdicional.getCajasPedidas());
			params.add(tPedidoAdicional.getCajas());
			params.add(tPedidoAdicional.getExcluir());
			params.add(tPedidoAdicional.getCodError());
			params.add(tPedidoAdicional.getDescError());
			params.add(tPedidoAdicional.getModificable());
			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getClasePedido());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getCodArticulo());
			
			if (null != tPedidoAdicional.getIdentificadorVegalsa()){
				query.append(" AND IDENTIFICADOR_VEGALSA = ?");
				params.add(tPedidoAdicional.getIdentificadorVegalsa());
			}else{
				if (null != tPedidoAdicional.getIdentificador()){
					query.append(" AND IDENTIFICADOR = ?");
					params.add(tPedidoAdicional.getIdentificador());
				}
				if (null != tPedidoAdicional.getIdentificadorSIA()){
					query.append(" AND IDENTIFICADOR_SIA = ?");
					params.add(tPedidoAdicional.getIdentificadorSIA());
				}
			}

			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		}
	    
	    @Override
	    public void insertPlanogramadas(TPedidoAdicional tPedidoAdicional) throws Exception{
	    	List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" INSERT INTO t_pedido_adicional "
													+ "( idsesion, clasepedido, codcentro, codarticulo, pantalla"
													+ ", descriptionart, unicajaserv, perfil, agrupacion, tipoaprovisionamiento"
													+ ", borrable, modificable, fechainicio, fechafin, capmax"
													+ ", capmin, excluir, esplanograma, no_gestiona_pbl"
													+ ") "
													+ "SELECT ?, ? , cod_centro, cod_art, ?"
														 + ", descrip_art, uni_caja_serv, perfil, agrupacion, tipo_aprov"
														 + ", ?, ?, f_inicio, f_fin, imp_inicial"
														 + ", imp_final, exc, ?, ? "
													+ "FROM v_planogramas_pedido_adicional "
													+ "WHERE cod_centro = ? "
													+ "AND cod_art = ?");
			
			params.add(tPedidoAdicional.getIdSesion());
			params.add(Constantes.CLASE_PEDIDO_MONTAJE);
			params.add(Constantes.PANTALLA_CALENDARIO);
			params.add(Constantes.PEDIDO_NO_BORRABLE);
			params.add(Constantes.PEDIDO_MODIFICABLE_NO);
			params.add("S");
			params.add("N");
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getCodArticulo());
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

	    }
	    
	    @Override
	    public List<GenericExcelVO> findAllExcel(TPedidoAdicional tPedidoAdicional,String[] columnModel) throws Exception {
	    	
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	//columnModel
	    	int j=0;
	    	
	    	String fields="null";
	    	List<String> listColumns = Arrays.asList(columnModel);
	    	String mappedField;
	    	
	    	for(String column : listColumns){
	    		if (!column.toUpperCase().equals("MENSAJE")){
	    			
	    			mappedField = this.getMappedField2(column);
	    			if("null".equals(mappedField)){
	    				fields= fields+" ";
	    			}else{
	    				fields = fields + ", " + mappedField;
	    			}
		    		j++;	
	    		}
	    	}
	    	
	    	if(tPedidoAdicional.getSeccion()!=null)
	    	{
		    	//Si es TEXTIL mapeamos los nuevos campos para la JOIN
		    	if("3".equals(tPedidoAdicional.getSeccion())){
		    		
		        	for(String column : listColumns){
			    		if (!column.toUpperCase().equals("MENSAJE")){
			    			mappedField = this.getMappedFieldTextil(column);
			    			if("null".equals(mappedField)){
			    				fields= fields+" ";
			    			}else{
			    				fields = fields + ", " +mappedField;
			    			}
				    		j++;	
			    		}
			    	}
		    	}
	    	}
	    	
	    	while (j<=41){
	    		fields = fields + ", null";
	    		j++;
	    	}
		    	
	    	StringBuffer query = new StringBuffer(" SELECT ");
	    	query.append(fields);
	    	
	    	query.append( " FROM T_PEDIDO_ADICIONAL tpa ");
	    
	    	
	        if (tPedidoAdicional  != null){
	        	
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND tpa.IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND tpa.PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND tpa.CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getDescOferta()!=null){
	        		 where.append(" AND tpa.DENOM_OFERTA = ? ");
		        	 params.add(tPedidoAdicional.getDescOferta());	        		
	        	}
	        	if(tPedidoAdicional.getDescPeriodo()!=null){
	        		 where.append(" AND TRIM(tpa.DESC_PERIODO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	        		
	        	}
	        	if(tPedidoAdicional.getEspacioPromo()!=null){
	        		 where.append(" AND TRIM(tpa.ESPACIO_PROMO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}
	        	//Filtramos CLASEPEDIDO en el caso de que haya que filtrar una lista de clases pedidos
	        	if (tPedidoAdicional.getClasePedido()!=null){
		        	if (tPedidoAdicional.getListaFiltroClasePedido()== null){
		        		List<Long> listaFiltro = Arrays.asList(tPedidoAdicional.getClasePedido());
		        		tPedidoAdicional.setListaFiltroClasePedido(listaFiltro);
		        	}
		        	else if (!tPedidoAdicional.getListaFiltroClasePedido().contains(tPedidoAdicional.getClasePedido())){
		        		tPedidoAdicional.getListaFiltroClasePedido().add(tPedidoAdicional.getClasePedido());
		        	}
	        	}
	        	if (tPedidoAdicional.getListaFiltroClasePedido()!= null && tPedidoAdicional.getListaFiltroClasePedido().size()>0){
	        		int listaFiltroSize = tPedidoAdicional.getListaFiltroClasePedido().size();
	        		boolean esMac = tPedidoAdicional.getMAC() != null && Constantes.PEDIDO_ADICIONAL_MAC.equals(tPedidoAdicional.getMAC());
	        		boolean primera = true;	     
        			where.append(" AND ");
        			where.append(" ( ");
	        		for (int i = 0; i < listaFiltroSize; i++){
	        			Long clasePedido = tPedidoAdicional.getListaFiltroClasePedido().get(i);
	        			StringBuffer condicion = new StringBuffer();
	        			
	        			// Clase pedido 2
	        			if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 2 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");
	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 2 y perfil centro (igual a PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");
    		        			
	        				}
	        			}
	        			// Clase pedido 3
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
    		    	        	// Se filtra por clase pedido 3 y perfil central (distinto de PERFIL_CENTRO)
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        				else{
    		    	        	// Se filtra por clase pedido 3 y perfil centro (igual a PERFIL_CENTRO)
    		        			condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL).append(" AND PERFIL = ").append(Constantes.PERFIL_CENTRO).append(" ) ");

	        				}
	        			}
	        			// Clase pedido 7
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			// Clase pedido 8
	        			else if (clasePedido.longValue() == new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).longValue()){
	        				if (esMac){
	        					condicion.append("      ( CLASEPEDIDO = ").append(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA).append(" ) ");
    		        			
	        				}
	        				else{
    		        			
	        				}
	        			}
	        			else{
	        				condicion.append("      ( CLASEPEDIDO = ").append(clasePedido.toString()).append(" ) ");
		        			
	        			}
	        			
	        			if (primera && !condicion.toString().isEmpty()){
	        				where.append(condicion.toString());
	        				primera = false;
	        			} else  if ( !condicion.toString().isEmpty()){
	        				where.append("        OR ").append(condicion.toString());
	        				
	        			}
	        		}
        			where.append(" ) ");
	        	}
	        	
	        }

	        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			//order.append(" order by grupo1, grupo2, grupo3, grupo4, grupo5, cod_art");
			query.append(order);
			
			
			//Si es TEXTIL añadimos la JOIN y sus campos
			if(tPedidoAdicional.getSeccion()!=null)
	    	{
				if("3".equals(tPedidoAdicional.getSeccion())){
					query.append(" FULL OUTER JOIN V_DATOS_ESPECIFICOS_TEXTIL vdet " +
								"ON tpa.CODARTICULO = vdet.COD_ART ");
				}
	    	}
	    	
			query.append(where);
	   
			logger.debug(query.toString());
	    	
			List<GenericExcelVO> lista = null;
			try {
				lista = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(),this.rwExcelPedidoAdicionalMap, params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
			return lista;
						
	    }
	    
	    private String getMappedField (String fieldName) {
		      if (fieldName.toUpperCase().equals("UNIDADESPEDIDAS")){
		  	      return "CAJASPEDIDAS";
		  	  }else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")){
		  	      return "ROUND(CAJASPEDIDAS / UNICAJASERV , 2)";
		  	  }else if (fieldName.toUpperCase().equals("FECHAINICIO")){
		  	      return "TO_DATE(FECHAINICIO, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHAFIN")){
		  	      return "TO_DATE(FECHAFIN, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA2")){
		  	      return "TO_DATE(FECHA2, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA3")){
		  	      return "TO_DATE(FECHA3, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA4")){
		  	      return "TO_DATE(FECHA4, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA5")){
		  	      return "TO_DATE(FECHA5, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("DESCOFERTA")){
		  	      return "DENOM_OFERTA";
		  	  }else if (fieldName.toUpperCase().equals("CANTMIN")){
		  	      return "CANT_MIN";
		  	  }else if (fieldName.toUpperCase().equals("CANTMAX")){
		  	      return "CANT_MAX";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPCION")){
		  	      return "DENOM_OFERTA";
		  	  }else if (fieldName.toUpperCase().equals("MENSAJE")){
		  	      return "DECODE(NVL(CODERROR,0),0,DECODE(NVL(ESTADO,''),'tpa.NOACT',3,4),9,2,1) CODERROR_ICONOS"; //Orden primero erroneos, luego modificadas y por último el resto
		  	  }else if (fieldName.toUpperCase().equals("TIPOAPROV")){
		  	      return "TIPOAPROVISIONAMIENTO";
		  	  }else if (fieldName.toUpperCase().equals("CODARTICULOGRID")){
		  	      return "tpa.COD_ART_GRID";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPTIONARTGRID")){
		  	      return "tpa.DESCRIP_ART_GRID";
		  	  }else {
		  	      return fieldName;
		  	  }
		  	}
	    

	    private String getMappedField2 (String fieldName) {
		      if (fieldName.toUpperCase().equals("UNIDADESPEDIDAS")){
		  	      return "tpa.CAJASPEDIDAS";
		  	  }else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")){
		  	      return "ROUND(tpa.CAJASPEDIDAS / tpa.UNICAJASERV , 2)";
		  	  }else if (fieldName.toUpperCase().equals("FECHAINICIO")){
		  	      return "TO_DATE(tpa.FECHAINICIO, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHAFIN")){
		  	      return "TO_DATE(tpa.FECHAFIN, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA2")){
		  	      return "TO_DATE(tpa.FECHA2, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA3")){
		  	      return "TO_DATE(tpa.FECHA3, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA4")){
		  	      return "TO_DATE(tpa.FECHA4, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA5")){
		  	      return "TO_DATE(tpa.FECHA5, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("DESCOFERTA")){
		  		  //MISUMI-233. DESCOFERTA solo llega como campo del colmodel si está MAC seleccionada y además es de tipo M o MO. Por lo que aquí sacamos
		  		  //el dato.
		  	      return "NVL(tpa.DENOM_OFERTA, NVL(tpa.DESC_PERIODO, tpa.OFERTA))";
		  	  }else if (fieldName.toUpperCase().equals("CANTMIN")){
		  	      return "tpa.CANT_MIN";
		  	  }else if (fieldName.toUpperCase().equals("CANTMAX")){
		  	      return "tpa.CANT_MAX";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPCION")){
		  	      return "tpa.DENOM_OFERTA";
		  	  }else if (fieldName.toUpperCase().equals("MENSAJE")){
		  	      return "DECODE(NVL(tpa.CODERROR,0),0,DECODE(NVL(tpa.ESTADO,''),'tpa.NOACT',3,4),9,2,1) tpa.CODERROR_ICONOS"; //Orden primero erroneos, luego modificadas y por último el resto
		  	  }else if (fieldName.toUpperCase().equals("TIPOAPROV")){
		  	      return "tpa.TIPOAPROVISIONAMIENTO";
		  	  }else if (fieldName.toUpperCase().equals("COLOR")){
		  	      return "null";
		  	  }else if (fieldName.toUpperCase().equals("TALLA")){
		  		 return "null";
		  	  }else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
		  		 return "null";
		  	  }else if (fieldName.toUpperCase().equals("CODARTICULOGRID")){
		  	      return "tpa.COD_ART_GRID";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPTIONARTGRID")){
		  	      return "tpa.DESCRIP_ART_GRID";
		  	  }else {
		  	      return "tpa."+fieldName;
		  	  }
		  	}
	    
	    private String getMappedFieldTextil (String fieldName) {
		      if (fieldName.toUpperCase().equals("COLOR")){
		  	      return "vdet.DESCR_COLOR";
		  	  }else if (fieldName.toUpperCase().equals("TALLA")){
		  	      return "vdet.DESCR_TALLA";
		  	  }else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
		  	      return "vdet.MODELO_PROVEEDOR";
		  	  }else if (fieldName.toUpperCase().equals("CODARTICULOGRID")){
		  	      return "tpa.COD_ART_GRID";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPTIONARTGRID")){
		  	      return "tpa.DESCRIP_ART_GRID";
		  	  }else {
		  	      return "null";
		  	  }
		  	}
	    
	    private String getMappedFieldSort (String fieldName) {
		      if (fieldName.toUpperCase().equals("UNIDADESPEDIDAS")){
		  	      return "CAJASPEDIDAS";
		  	  }else if (fieldName.toUpperCase().equals("CAJASPEDIDAS")){
		  	      return "ROUND(CAJASPEDIDAS / UNICAJASERV , 2)";
		  	  }else if (fieldName.toUpperCase().equals("FECHAINICIO")){
		  	      return "TO_DATE(FECHAINICIO, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHAFIN")){
		  	      return "TO_DATE(FECHAFIN, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA2")){
		  	      return "TO_DATE(FECHA2, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA3")){
		  	      return "TO_DATE(FECHA3, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA4")){
		  	      return "TO_DATE(FECHA4, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("FECHA5")){
		  	      return "TO_DATE(FECHA5, 'DDMMYYYY')";
		  	  }else if (fieldName.toUpperCase().equals("DESCOFERTA")){
		  	      return "DENOM_OFERTA";
		  	  }else if (fieldName.toUpperCase().equals("CANTMIN")){
		  	      return "CANT_MIN";
		  	  }else if (fieldName.toUpperCase().equals("CANTMAX")){
		  	      return "CANT_MAX";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPCION")){
		  	      return "DENOM_OFERTA";
		  	  }else if (fieldName.toUpperCase().equals("MENSAJE")){
		  	      return "CODERROR_ICONOS, CODARTICULO";
		  	  }else if (fieldName.toUpperCase().equals("COLOR")){
		  	      return null;
		  	  }else if (fieldName.toUpperCase().equals("TALLA")){
		  	      return null;
		  	  }else if (fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
		  	      return null;
		  	  }else if (fieldName.toUpperCase().equals("TIPOAPROV")){
		  	      return "TIPOAPROVISIONAMIENTO";
		  	  }else if (fieldName.toUpperCase().equals("CODARTICULOGRID")){
		  	      return "COD_ART_GRID";
		  	  }else if (fieldName.toUpperCase().equals("DESCRIPTIONARTGRID")){
		  	      return "DESCRIP_ART_GRID";
		  	  }else {
		  	      return fieldName;
		  	  }
		}

	    private String getFiltroClasePedido(TPedidoAdicional tPedidoAdicional){
			StringBuffer query = new StringBuffer();
			
        	//Filtramos CLASEPEDIDO en el caso de que haya que filtrar una lista de clases pedidos
        	if (tPedidoAdicional.getClasePedido()!=null){
	        	if (tPedidoAdicional.getListaFiltroClasePedido()== null){
	        		List<Long> listaFiltro = Arrays.asList(tPedidoAdicional.getClasePedido());
	        		tPedidoAdicional.setListaFiltroClasePedido(listaFiltro);
	        	}
	        	else if (!tPedidoAdicional.getListaFiltroClasePedido().contains(tPedidoAdicional.getClasePedido())){
	        		tPedidoAdicional.getListaFiltroClasePedido().add(tPedidoAdicional.getClasePedido());
	        	}
        	}
        	if (tPedidoAdicional.getListaFiltroClasePedido()!= null && tPedidoAdicional.getListaFiltroClasePedido().size()>0){
        		int listaFiltroSize = tPedidoAdicional.getListaFiltroClasePedido().size();
    			query.append(" AND CLASEPEDIDO IN ( ");
        		for (int i = 0; i < listaFiltroSize; i++){
        			Long clasePedido = tPedidoAdicional.getListaFiltroClasePedido().get(i);
        			query.append(clasePedido.toString()).append((i+1 == listaFiltroSize) ? "" : " , ");
        		}
    			query.append(" ) ");
        	}
        	
        	return query.toString();
	    }
	    
	    @Override
	    public List<String> findComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception{
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT TRIM(DENOM_OFERTA) ||'**'|| DECODE(NVL(MIN(TO_DATE(FECHA_HASTA,'DDMMYYYY')),''), '', '',TO_CHAR(MIN(TO_DATE(FECHA_HASTA,'DDMMYYYY')),'DDMMYYYY')) FROM T_PEDIDO_ADICIONAL ");
	    	if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	 where.append(" AND DENOM_OFERTA IS NOT NULL ");
	    	}
	    	 where.append(" AND CLASEPEDIDO IN ( ?, ?) ");
        	 params.add(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4));	
        	 params.add(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5));	
        	 query.append(where);
        	 StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 group.append(" GROUP BY DENOM_OFERTA");
        	 query.append(group);
        	 StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 order.append(" ORDER BY DENOM_OFERTA");
        	 query.append(order);
        	 
        	 List<String> lista = null;
        	 try {
 				lista = this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
 				
 			} catch (Exception e){
 				
 				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
 			}
	    	return lista;
	    }
	    
	    @Override
	    public List<String> findComboOfertaPeriodoMO(TPedidoAdicional tPedidoAdicional) throws Exception{
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT NVL(DESC_PERIODO,NVL(DENOM_OFERTA, OFERTA)) FROM T_PEDIDO_ADICIONAL ");
	    	if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	//No se filtra por su propio valor porque nos interesa que salgan todos los valores
	        	/*if(tPedidoAdicional.getDescPeriodo()!=null && !(tPedidoAdicional.getDescPeriodo().equals("null"))){
	        		 where.append(" AND DESC_PERIODO = ? ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	        		
	        	}*/
	        	if(tPedidoAdicional.getEspacioPromo()!=null && !(tPedidoAdicional.getEspacioPromo().equals("null"))){
	        		 where.append(" AND TRIM(ESPACIO_PROMO) = (?) ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}

	        	 where.append(" AND NVL(DESC_PERIODO,NVL(DENOM_OFERTA,oferta)) IS NOT NULL ");
	    	
	    	
	        	 boolean esMac = tPedidoAdicional.getMAC() != null && Constantes.PEDIDO_ADICIONAL_MAC.equals(tPedidoAdicional.getMAC());
	        	 
	        	 if (esMac){
	    	        // Se filtra por clase pedido 3 y perfil central (distinto de PERFIL_CENTRO)
			    	 where.append(" AND ( CLASEPEDIDO IN ( ?, ?) AND PERFIL <> ").append(Constantes.PERFIL_CENTRO).append(" ) ");
		        	 /*params.add(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));	
		        	 params.add(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));*/	
			    	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(0));	
		        	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(1));
	        	 } else {
	        		 
	        		 where.append(" AND CLASEPEDIDO IN ( ?, ?) ");
		        	 /*params.add(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));	
		        	 params.add(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));*/	
			    	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(0));	
		        	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(1));
	        	 }
        	 
        	 
	    	}
	    	
	    	 query.append(where);
        	 
        	 StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 group.append(" GROUP BY NVL(DESC_PERIODO,NVL(DENOM_OFERTA, OFERTA)) ");
        	 query.append(group);
        	 StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 order.append(" ORDER BY NVL(DESC_PERIODO,NVL(DENOM_OFERTA, OFERTA)) "
        	 		+ "");
        	 query.append(order);
	    	
	    	
	    	List<String> lista = null;
	       	 try {
					lista = this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
					
				} catch (Exception e){
					
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				}
		    	return lista;
	    }
	    
	    @Override
	    public List<String> findComboEspacioPromoMO(TPedidoAdicional tPedidoAdicional) throws Exception{
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer(" SELECT ESPACIO_PROMO FROM T_PEDIDO_ADICIONAL ");
	    	if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getDescPeriodo()!=null && !(tPedidoAdicional.getDescPeriodo().equals("null"))){
	        		 where.append(" AND TRIM(DESC_PERIODO) = TRIM(?) ");
		        	 params.add(tPedidoAdicional.getDescPeriodo());	        		
	        	}
	        	//No se filtra por su propio valor porque nos interesa que salgan todos los valores
	        	/*if(tPedidoAdicional.getEspacioPromo()!=null && !(tPedidoAdicional.getEspacioPromo().equals("null"))){
	        		 where.append(" AND ESPACIO_PROMO = ? ");
		        	 params.add(tPedidoAdicional.getEspacioPromo());	        		
	        	}*/

	        	 where.append(" AND ESPACIO_PROMO IS NOT NULL ");
	    	}
	    	 where.append(" AND CLASEPEDIDO IN ( ?, ?) ");
        	 /*params.add(new Long(Constantes.CLASE_PEDIDO_MONTAJE_ADICIONAL));	
        	 params.add(new Long(Constantes.CLASE_PEDIDO_FRESCOS_MONTAJE_ADICIONAL_OFERTA));*/	
	    	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(0));	
        	 params.add(tPedidoAdicional.getListaFiltroClasePedido().get(1));
        	 query.append(where);
        	 StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 group.append(" GROUP BY ESPACIO_PROMO");
        	 query.append(group);
        	 StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        	 order.append(" ORDER BY ESPACIO_PROMO");
        	 query.append(order);
	    
	    	

	    	List<String> lista = null;
	       	 try {
					lista =   this.jdbcTemplate.queryForList(query.toString(), String.class, params.toArray());
					
				} catch (Exception e){
					
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
				}
		    	return lista;
	    }
	    
	    @Override 
	   public String findSelectedComboValidarVC(TPedidoAdicional tPedidoAdicional) throws Exception {
		   
		   try{
		   StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");
	    	StringBuffer query = new StringBuffer("SELECT DENOM_OFERTA ||'**'|| TO_CHAR(FECHA_HASTA,'DDMMYYYY') from ( ");
	    	query.append("select denom_oferta, min(to_date(nvl(fecha_hasta,''),'ddmmyyyy')) fecha_hasta , min(to_date(nvl(fechainicio,''),'ddmmyyyy')) fechainicio FROM T_PEDIDO_ADICIONAL ");
	    	if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	  
	        	}
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(new Long(Constantes.PANTALLA_LISTADOS));	        		
	    	}
	    	 where.append(" AND CLASEPEDIDO IN ( ?, ?) ");
       	 params.add(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_4));	
       	 params.add(new Long(Constantes.CLASE_PEDIDO_VALIDAR_CANT_EXTRA_5));	
       	 
       	 where.append(" AND DENOM_OFERTA IS NOT NULL ");
       	 query.append(where);
       	 StringBuffer group = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
       	 group.append(" GROUP BY DENOM_OFERTA, FECHA_HASTA");
       	 query.append(group);
       	 StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
       	 order.append(" ORDER BY FECHA_HASTA, FECHAINICIO");
       	 query.append(order);
       	 query.append(") where ROWNUM <= 1");
	    	
       	 
       	 
       	 return this.jdbcTemplate.queryForObject(query.toString(), String.class, params.toArray());
		   } catch (EmptyResultDataAccessException e) {
				return null;
			}
	   }
		
	    @Override
	    public List<TPedidoAdicional> findAllBloqueos(TPedidoAdicional tPedidoAdicional, Pagination pagination) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT IDSESION, CLASEPEDIDO, CODCENTRO, CODARTICULO, PANTALLA, IDENTIFICADOR, IDENTIFICADOR_SIA, IDENTIFICADOR_VEGALSA, DESCRIPTIONART," +
	    										  " UNICAJASERV, USUARIO, PERFIL, AGRUPACION, TIPOAPROVISIONAMIENTO, BORRABLE, " +
	    										  " MODIFICABLE, MODIFICABLEINDIV, CAJASPEDIDAS, FECENTREGA, FECHAINICIO, FECHAFIN," +
	    										  " FECHA2, FECHA3, FECHA4, CAPMAX, CAPMIN, CANTIDAD1, CANTIDAD2, CANTIDAD3, TIPOPEDIDO, " +
	    										  " CAJAS, EXCLUIR, FECHACREACION,CODERROR,DESCERROR,OFERTA,ESPLANOGRAMA,DENOM_OFERTA,CANT_MAX,CANT_MIN, " +
	    										  " FECHA5, FECHAINPIL, CANTIDAD4, CANTIDAD5, TRATAMIENTO, FECHA_HASTA, ESTADO, NO_GESTIONA_PBL, DESC_PERIODO, ESPACIO_PROMO, COD_ART_GRID, DESCRIP_ART_GRID " +
	    										  
	    										  " FROM T_PEDIDO_ADICIONAL ");
	    
	        if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getCodArticulo()!=null){
	        		 where.append(" AND CODARTICULO = ? ");
		        	 params.add(tPedidoAdicional.getCodArticulo());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getPerfil()!=null){
	        		 where.append(" AND PERFIL = ? ");
		        	 params.add(tPedidoAdicional.getPerfil());	        		
	        	}
	        	//Si es encargo no se tienen en cuenta las planogramadas
	        	if(tPedidoAdicional.getClasePedido()!=null && (new Long(Constantes.CLASE_PEDIDO_ENCARGO)).equals(tPedidoAdicional.getClasePedido())){
	        		 where.append(" AND (ESPLANOGRAMA <> 'S' OR ESPLANOGRAMA IS NULL) ");
	        	}
	        	if(tPedidoAdicional.getFechaInicio()!=null && tPedidoAdicional.getFechaFin()!=null){
	        		//Si es encargo no se tienen en cuenta las planogramadas
	        		where.append(" AND ( " +
	        				  //	    --  1.- caso -- la fecha inicio está entre fechas de central
	        				  "	       TO_DATE(?, 'DDMMYYYY') between " +  
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " +
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy') " + 
	        				  "     or " + 
	        				  //        -- 2.- caso -- la fecha fin está entre fechas de central
	        				  "	       TO_DATE(?, 'DDMMYYYY') between " +    
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " +
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy') " + 
	        				  "	    or " +
	        				  //	    -- 3 - caso -- las fechas que abarca central son mayores
	        				  "	       (TO_DATE(?, 'DDMMYYYY') < TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " + 
	        				  "	        TO_DATE(?, 'DDMMYYYY') > TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy'))) "
	        		);
					params.add(tPedidoAdicional.getFechaInicio());	        				  
	        		params.add(tPedidoAdicional.getFechaFin());
					params.add(tPedidoAdicional.getFechaInicio());	        				  
	        		params.add(tPedidoAdicional.getFechaFin());
	        	}
	        }
	        
	        query.append(where);
	        
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" ORDER BY NVL(FECENTREGA, FECHAINICIO) asc, NVL(FECENTREGA, FECHAFIN) asc");
			query.append(order);

			if (pagination != null) {
				query = new StringBuffer(Paginate.getQueryLimits(
						pagination, query.toString()));
			}

			List<TPedidoAdicional> tPedidoAdicionalLista = null;		
			
			try {
				tPedidoAdicionalLista = (List<TPedidoAdicional>) this.jdbcTemplate.query(query.toString(),this.rwTPedidoAdicionalMap, params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}

		    return tPedidoAdicionalLista;
	    }
	    
	    @Override
	    public Long findAllBloqueosCont(TPedidoAdicional tPedidoAdicional) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " +
	    										  
	    										  " FROM T_PEDIDO_ADICIONAL ");
	    
	        if (tPedidoAdicional  != null){
	        	if(tPedidoAdicional.getIdSesion()!=null){
	        		 where.append(" AND IDSESION = ? ");
		        	 params.add(tPedidoAdicional.getIdSesion());	        		
	        	}
	        	if(tPedidoAdicional.getCodCentro()!=null){
	        		 where.append(" AND CODCENTRO = ? ");
		        	 params.add(tPedidoAdicional.getCodCentro());	        		
	        	}
	        	if(tPedidoAdicional.getCodArticulo()!=null){
	        		 where.append(" AND CODARTICULO = ? ");
		        	 params.add(tPedidoAdicional.getCodArticulo());	        		
	        	}
	        	if(tPedidoAdicional.getPantalla()!=null){
	        		 where.append(" AND PANTALLA = ? ");
		        	 params.add(tPedidoAdicional.getPantalla());	        		
	        	}
	        	if(tPedidoAdicional.getPerfil()!=null){
	        		 where.append(" AND PERFIL = ? ");
		        	 params.add(tPedidoAdicional.getPerfil());	        		
	        	}
	        	//Si es encargo no se tienen en cuenta las planogramadas
	        	if(tPedidoAdicional.getClasePedido()!=null && (new Long(Constantes.CLASE_PEDIDO_ENCARGO)).equals(tPedidoAdicional.getClasePedido())){
	        		 where.append(" AND (ESPLANOGRAMA <> 'S' OR ESPLANOGRAMA IS NULL) ");
	        	}
	        	if(tPedidoAdicional.getFechaInicio()!=null && tPedidoAdicional.getFechaFin()!=null){
	        		where.append(" AND ( " +
	        				  //	    --  1.- caso -- la fecha inicio está entre fechas de central
	        				  "	       TO_DATE(?, 'DDMMYYYY') between " +  
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " +
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy') " + 
	        				  "     or " + 
	        				  //        -- 2.- caso -- la fecha fin está entre fechas de central
	        				  "	       TO_DATE(?, 'DDMMYYYY') between " +    
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " +
	        				  "	                 TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy') " + 
	        				  "	    or " +
	        				  //	    -- 3 - caso -- las fechas que abarca central son mayores
	        				  "	       (TO_DATE(?, 'DDMMYYYY') < TO_DATE(nvl(FECENTREGA,FECHAINICIO),'ddmmyyyy') and " + 
	        				  "	        TO_DATE(?, 'DDMMYYYY') > TO_DATE(nvl(FECENTREGA,FECHAFIN),'ddmmyyyy'))) "
	        		);
					params.add(tPedidoAdicional.getFechaInicio());	        				  
	        		params.add(tPedidoAdicional.getFechaFin());
					params.add(tPedidoAdicional.getFechaInicio());	        				  
	        		params.add(tPedidoAdicional.getFechaFin());
	        	}
	        }
	        
	        query.append(where);
	        
	    	StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			order.append(" ORDER BY NVL(FECENTREGA, FECHAINICIO) asc, NVL(FECENTREGA, FECHAFIN) asc");
			query.append(order);

			Long cont = null;
			
			try {
				cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
			return cont;
	    }
	    
	@Override    
	public void insertNuevoReferencia(TPedidoAdicional tPedidoAdicional) throws Exception{
	    	
	    	List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer("INSERT INTO t_pedido_adicional "
	    											+ "( idsesion, clasepedido, codcentro, codarticulo"
	    											+ ", pantalla, fecentrega, fechainicio, fechafin"
	    											+ ", fecha2, fecha3, fecha4, tipopedido, uuid"
	    											+ ", esplanograma, no_gestiona_pbl "
	    											+ ") "
	    										+ "VALUES ( ?, ?, ?, ?"
	    											   + ", ?, ?, ?, ?"
	    											   + ", ?, ?, ?, ?, ?"
	    											   + ", ?, ? "
	    											   + ")"
	    											   );

			params.add(tPedidoAdicional.getIdSesion());
			params.add(tPedidoAdicional.getClasePedido());
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getCodArticulo());
			params.add(tPedidoAdicional.getPantalla());
			params.add(tPedidoAdicional.getFecEntrega());
			params.add(tPedidoAdicional.getFechaInicio());
			params.add(tPedidoAdicional.getFechaFin());
			params.add(tPedidoAdicional.getFecha2());
			params.add(tPedidoAdicional.getFecha3());
			params.add(tPedidoAdicional.getFecha4());
			params.add(tPedidoAdicional.getTipoPedido());
			params.add(tPedidoAdicional.getUuid());
			params.add(tPedidoAdicional.getEsPlanograma());
			params.add(tPedidoAdicional.getNoGestionaPbl());
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
						
				
	}
	
	@Override    
	public void updateNuevoReferencia(TPedidoAdicional tPedidoAdicional) throws Exception{
	    
		List<Object> params = new ArrayList<Object>();
		StringBuffer query = new StringBuffer(" UPDATE T_PEDIDO_ADICIONAL SET FECHAINICIO = ?, FECHAFIN = ?, ");
		query.append(" FECHA2 = ?, FECHA3 = ?, FECHA4=?, FECENTREGA = ?, ");
		query.append(" IDSESION = ?, CLASEPEDIDO = ? ,CODCENTRO = ? AND TIPOPEDIDO = ? AND CODARTICULO = ? ");
		query.append(" WHERE UUID = ?");

		params.add(tPedidoAdicional.getFechaInicio());
		params.add(tPedidoAdicional.getFechaFin());
		params.add(tPedidoAdicional.getFecha2());
		params.add(tPedidoAdicional.getFecha3());
		params.add(tPedidoAdicional.getFecha4());
		params.add(tPedidoAdicional.getFecEntrega());
		params.add(tPedidoAdicional.getIdSesion());
		params.add(tPedidoAdicional.getClasePedido());
		params.add(tPedidoAdicional.getCodCentro());
		params.add(tPedidoAdicional.getTipoPedido());
		params.add(tPedidoAdicional.getCodArticulo());
		params.add(tPedidoAdicional.getUuid());
						
				
	}
	
	 @Override
		public void deleteAllNuevoReferencia(TPedidoAdicional tPedidoAdicional) throws Exception {
			List<Object> params = new ArrayList<Object>();

			StringBuffer query = new StringBuffer(" DELETE FROM T_PEDIDO_ADICIONAL WHERE IDSESION LIKE ? ");
			query.append("AND CODCENTRO = ? AND PANTALLA = ? ");
			

			params.add(tPedidoAdicional.getIdSesion()+"_%");
			params.add(tPedidoAdicional.getCodCentro());
			params.add(tPedidoAdicional.getPantalla());
			
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
			

		}

	@Override
	public Long findMontajesAdicionalesVegalsa(PedidoAdicionalE pedidoAdicionalE, Boolean oferta) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(pedidoAdicionalE.getCodCentro());
		
		String query = "SELECT COUNT (1) FROM T_MIS_MONTAJES_VEGALSA WHERE COD_CENTRO = ? AND TRUNC(FECHA_FIN) >= TRUNC(SYSDATE) ";
		if (oferta){
			query +=" AND OFERTA IS NOT NULL ";
		}else{
			query +=" AND OFERTA IS NULL ";
		}
		// Se esta buscando por referencia
		if (pedidoAdicionalE.getCodArticulo()!=null){
			query+=" AND COD_ART = ? ";
			params.add(pedidoAdicionalE.getCodArticulo());
		}else{
			// Se esta buscando por area/seccion/categoria
			if (pedidoAdicionalE.getGrupo3()!=null){
				query+=" AND COD_ART IN (SELECT DISTINCT COD_ART FROM V_DATOS_DIARIO_ART WHERE GRUPO1 = ? AND GRUPO2=? AND GRUPO3=?) ";
				params.add(pedidoAdicionalE.getGrupo1());
				params.add(pedidoAdicionalE.getGrupo2());
				params.add(pedidoAdicionalE.getGrupo3());
			// Se esta buscando por area/seccion
			}else if (pedidoAdicionalE.getGrupo2()!=null){
				query+=" AND COD_ART IN (SELECT DISTINCT COD_ART FROM V_DATOS_DIARIO_ART WHERE GRUPO1 = ? AND GRUPO2=?) ";
				params.add(pedidoAdicionalE.getGrupo1());
				params.add(pedidoAdicionalE.getGrupo2());
			}
		}
		
		return this.jdbcTemplate.queryForLong(query, params.toArray());
	}

	@Override
	public List<MontajeVegalsa> findPedidosVegalsa(PedidoAdicionalE pedidoAdicionalE) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(pedidoAdicionalE.getCodCentro());
		
		String query = "SELECT * FROM T_MIS_MONTAJES_VEGALSA WHERE COD_CENTRO = ? AND TRUNC(FECHA_FIN) >= TRUNC(SYSDATE) ";

		// Se esta buscando por referencia
		if (pedidoAdicionalE.getCodArticulo()!=null){
			query+=" AND COD_ART = ? ";
			params.add(pedidoAdicionalE.getCodArticulo());
		}else{
			// Se esta buscando por area/seccion/categoria
			if (pedidoAdicionalE.getGrupo3()!=null){
				query+=" AND COD_ART IN (SELECT DISTINCT COD_ART FROM V_DATOS_DIARIO_ART WHERE GRUPO1 = ? AND GRUPO2=? AND GRUPO3=?) ";
				params.add(pedidoAdicionalE.getGrupo1());
				params.add(pedidoAdicionalE.getGrupo2());
				params.add(pedidoAdicionalE.getGrupo3());
			// Se esta buscando por area/seccion
			}else if (pedidoAdicionalE.getGrupo2()!=null){
				query+=" AND COD_ART IN (SELECT DISTINCT COD_ART FROM V_DATOS_DIARIO_ART WHERE GRUPO1 = ? AND GRUPO2=?) ";
				params.add(pedidoAdicionalE.getGrupo1());
				params.add(pedidoAdicionalE.getGrupo2());
			}
		}
		
		return this.jdbcTemplate.query(query, params.toArray(), this.rwTMontajeVegalsa);
	}
	
	@Override
	public List<MontajeVegalsa> findMontajeAdicionalCentro(Long codCentro,Long codArt) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(codArt);
		String query = "SELECT * FROM T_MIS_MONTAJES_VEGALSA WHERE COD_CENTRO = ? AND COD_ART = ? AND TRUNC(SYSDATE) BETWEEN FECHA_INICIO AND FECHA_FIN ";
		return this.jdbcTemplate.query(query, params.toArray(), this.rwTMontajeVegalsa);
	}
	
	@Override
	public void deletePedidosVegalsa(final Long idVegalsa) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(idVegalsa);
		
		String query = "DELETE FROM T_MIS_MONTAJES_VEGALSA WHERE IDENTIFICADOR_VEGALSA = ? ";
		
		this.jdbcTemplate.update(query, params.toArray());
			
		query = "DELETE FROM T_PEDIDO_ADICIONAL WHERE IDENTIFICADOR_VEGALSA = ?";
		this.jdbcTemplate.update(query, params.toArray());
	}
	
	@Override
	public void updatePedidoVegalsa(final Long idVegalsa, final Double cantidad, final String fechaIni, final String fechaFin, final String user) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(cantidad);
		params.add(user);
		params.add(idVegalsa);
		
		String query = "UPDATE T_MIS_MONTAJES_VEGALSA SET CANTIDAD = ?, FECHA_INICIO = TO_DATE('"+fechaIni+"','DDMMYYYY'), FECHA_FIN = TO_DATE('"+fechaFin+"','DDMMYYYY'), USUARIO_MODIF = ?, LAST_UPDATE_DATE = SYSDATE WHERE IDENTIFICADOR_VEGALSA = ? ";
		
		this.jdbcTemplate.update(query, params.toArray());
			
	}
	
	@Override
	public void insertMontajesVegalsa(PedidoAdicionalNuevo pedidoAdicionalNuevo) throws Exception {
		
		List<Object> params = new ArrayList<Object>();

		params.add(pedidoAdicionalNuevo.getCodCentro());
		params.add(pedidoAdicionalNuevo.getCodArticulo());
		params.add(pedidoAdicionalNuevo.getOferta());
		params.add(pedidoAdicionalNuevo.getFechaInicioMontaje());
		params.add(pedidoAdicionalNuevo.getFechaFinMontaje());
		params.add(pedidoAdicionalNuevo.getCantidad());
		params.add(pedidoAdicionalNuevo.getUser());
		params.add(pedidoAdicionalNuevo.getUser());
		
    	String query ="INSERT INTO t_mis_montajes_vegalsa "
    					+ "( identificador_vegalsa, cod_centro, cod_art, oferta"
    					+ ", fecha_inicio, fecha_fin, cantidad"
    					+ ", usuario_creacion, usuario_modif, creation_date, last_update_date "
    					+ ") "
    				+ "VALUES "
    					+ "( MIS_SEQ_MONTAJES_VEGALSA.NEXTVAL, ?, ?, ?"
    					+ ", TO_DATE(?,'DD-MM-YYYY'), TO_DATE(?,'DD-MM-YYYY'), ?"
    					+ ", ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP"
    					+ ")";

    	try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
    	
	}
	
	private Long getCodMapaVegalsa(Long codArt) {
		String query = "SELECT COD_MAPA FROM T_MIS_MAPAS_VEGALSA WHERE COD_ART = "+codArt;

		Long codMapa = null;
		try {
			codMapa = this.jdbcTemplate.queryForLong(query);
		} catch (Exception e) {
			logger.warn("No se ha encontrado el codigo de mapa para el articulo "+codArt);
		}

		return codMapa;
	}
	
	@Override
	public void insertarPedidoAdicionalVegalsa(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {
		
		List<MontajeVegalsa> pedidosVegalsa = findPedidosVegalsa(pedidoAdicionalE);

		for (MontajeVegalsa montaje: pedidosVegalsa){
			List<Object> params = new ArrayList<Object>();
	    	String query ="INSERT INTO T_PEDIDO_ADICIONAL (IDSESION, CLASEPEDIDO, CODCENTRO, CODARTICULO, COD_ART_GRID, "
	    			+"PANTALLA, DESCRIPTIONART, DESCRIP_ART_GRID, UNICAJASERV, PERFIL, "
	    			+ "AGRUPACION, TIPOAPROVISIONAMIENTO, BORRABLE, MODIFICABLE, MODIFICABLEINDIV, "
	    			+ "FECHAINICIO, FECHAFIN, CAPMAX, CAPMIN, TIPOPEDIDO, "
	    			+ "EXCLUIR, FECHACREACION, IDENTIFICADOR_VEGALSA, OFERTA) "
					+" VALUES (?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, "
							+ "?, ?, ?, ?, ?, "
							+ "?, SYSDATE, ?, ?) ";

	    	
			final Long codCentro = montaje.getCodCentro();

	    	// IDSESION
	    	final String sessionID = session.getId();
			params.add(sessionID);
			// CLASEPEDIDO
			params.add(montaje.getOferta()!=null?"3":"2");
			// CODCENTRO 
			params.add(montaje.getCodCentro());
			// CODARTICULO
			final Long codArt = montaje.getCodArt();
			params.add(codArt);
			// COD_ART_GRID
			params.add(codArt);
			
			// PANTALLA: DATO FIJO = 1
			params.add(1);
			// DESCRIPTIONART
			
			VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
			vDatosDiarioArt.setCodArt(codArt);
			VDatosDiarioArt vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
			
			String descArt = "";
			if (vDatosDiarioArtRes != null && vDatosDiarioArtRes.getDescripArt() != null)
			{
				descArt =vDatosDiarioArtRes.getDescripArt();
			}
					
			params.add(descArt);
			// DESCRIP_ART_GRID
			params.add(descArt);
			// UNICAJASERV: Se extrae los datos de T_MIS_SURTIDO_VEGALSA, para mi centro-referencia y máxima fecha gen, campo UC
			final Double unicajaServ = getUnicajaServ(codCentro,codArt);
			params.add(unicajaServ);
			// PERFIL 
			params.add(3);
			
			// AGRUPACION
			final String agrupacion = getAgrupacion(codArt);
			params.add(agrupacion);
			
			// TIPOAPROVISIONAMIENTO
			final String tipoAprovisionamiento = getTipoAprovisionamiento(codCentro, codArt);
			params.add(tipoAprovisionamiento);
			// BORRABLE
			final String fechaFin = Utilidades.formatearFecha(montaje.getFechaFin());
					
			final String borrable = getIsBorrable(codCentro, codArt, fechaFin);
			
			params.add(borrable);
			// MODIFICABLE
			params.add(borrable);
			
			// MODIFICABLEINDIV
			String modificableIndiv = "N";
			final String fechaInicio = Utilidades.formatearFecha(montaje.getFechaInicio());
			if (!borrable.equalsIgnoreCase("N")){
				modificableIndiv = getIsModificableIndiv(codCentro, codArt, fechaInicio);
			}
			
			params.add(modificableIndiv);
			
			// FECHAINICIO: CAMPO TEXTO DDMMYYYY
			params.add(fechaInicio);
			// FECHAFIN: CAMPO TEXTO DDMMYYYY
			params.add(fechaFin);
			// CAPMAX: Campo CANTIDAD de T_MIS_MONTAJES_VEGALSA
			params.add(montaje.getCantidad());
			// CAPMIN: Campo CANTIDAD de T_MIS_MONTAJES_VEGALSA
			params.add(montaje.getCantidad());
			// TIPOPEDIDO: VALOR FIJO = P
			params.add("P");
			
			// EXCLUIR: VALOR FIJO = S
			params.add("S");
			// FECHACREACION (SYSDATE)
			
			// IDENTIFICADOR VEGALSA: CAMPO IDENTIFICADOR_VEGALSA DE LA TABLA T_MIS_MONTAJES_VEGALSA
			params.add(montaje.getIdentificadorVegalsa());
			
			// OFERTA
			params.add(montaje.getOferta()!=null?montaje.getOferta():null);
			
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
				
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
			}
		}
		
	}
	
	private String getTipoAprovisionamiento(Long codCentro, Long codArt) {
		String query="SELECT T1.TIPO_APROV FROM T_MIS_SURTIDO_VEGALSA T1 "
				+ " WHERE T1.COD_CENTRO = "+codCentro+" AND T1.COD_ART = "+codArt
				+ " AND NOT EXISTS (SELECT 1 FROM T_MIS_SURTIDO_VEGALSA T2 WHERE T2.COD_CENTRO = T1.COD_CENTRO AND T2.COD_ART = T1.COD_ART AND T2.FECHA_GEN > T1.FECHA_GEN) ";
		
		String output = null;
		try{
			output=jdbcTemplate.queryForObject(query.toString(), String.class);	
		}catch(Exception e){
			logger.warn("No se encuentra el valor del TIPO_APROV en la tabla T_MIS_SURTIDO_VEGALSA para el centro "+codCentro+" y la referencia "+codArt);
		}
		
		return output;
	}

	private String getAgrupacion(Long codArt) throws Exception {
		String output = "";
		
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		VDatosDiarioArt vDatosDiarioArtRes = this.vDatosDiarioArtService.findOne(vDatosDiarioArt);
		
		if (vDatosDiarioArtRes != null && vDatosDiarioArtRes.getGrupo1() != null && vDatosDiarioArtRes.getGrupo2() != null && vDatosDiarioArtRes.getGrupo3() != null && vDatosDiarioArtRes.getGrupo4() != null && vDatosDiarioArtRes.getGrupo5() != null)
		{
			final Long grupo1 =vDatosDiarioArtRes.getGrupo1();
			final Long grupo2 =vDatosDiarioArtRes.getGrupo2();
			final Long grupo3 =vDatosDiarioArtRes.getGrupo3();
			final Long grupo4 =vDatosDiarioArtRes.getGrupo4();
			final Long grupo5 =vDatosDiarioArtRes.getGrupo5();
			
			final String grupo1String = grupo1>=10?grupo1.toString():"0"+grupo1.toString();
			final String grupo2String = grupo2>=10?grupo2.toString():"0"+grupo2.toString();
			final String grupo3String = grupo3>=10?grupo3.toString():"0"+grupo3.toString();
			final String grupo4String = grupo4>=10?grupo4.toString():"0"+grupo4.toString();
			final String grupo5String = grupo5>=10?grupo5.toString():"0"+grupo5.toString();

			output = grupo1String+grupo2String+grupo3String+grupo4String+grupo5String;
		}
		return output;
	}

	private Double getUnicajaServ(Long codCentro, Long codArt) {
		
		String query="SELECT T1.UC FROM T_MIS_SURTIDO_VEGALSA T1 "
				+ " WHERE T1.COD_CENTRO = "+codCentro+" AND T1.COD_ART = "+codArt
				+ " AND NOT EXISTS (SELECT 1 FROM T_MIS_SURTIDO_VEGALSA T2 WHERE T2.COD_CENTRO = T1.COD_CENTRO AND T2.COD_ART = T1.COD_ART AND T2.FECHA_GEN > T1.FECHA_GEN) ";
		
		Double output = null;
		try{
			output=jdbcTemplate.queryForObject(query.toString(), Double.class);	
		}catch(Exception e){
			logger.warn("No se encuentra el valor del UC en la tabla T_MIS_SURTIDO_VEGALSA para el centro "+codCentro+" y la referencia "+codArt);
		}
		
		return output;
	}

	private String getIsBorrable(final Long codCentro, final Long codArt, final String fechaFin){
		String output = "N";
		
		// 1 - Obtener el codigo de mapa de la referencia
		final Long codMapa = getCodMapaVegalsa(codArt);
		// 2 - Obtener la fecha máxima de pedido en T_MIS_CALENDARIO_VEGALSA
		if (codMapa!=null){
			final Date maxFecha = getMaxFechaPedidoCalendarioMapaVegalsa(codCentro, codMapa, fechaFin);
			if (maxFecha!=null){
				final Date today = new Date();		
				
				if (maxFecha.compareTo(today)>0){
					output = "S";
				}
			}
		}
		return output;
	}
	
	private String getIsModificableIndiv(final Long codCentro, final Long codArt, final String fechaInicio) {
		String output = "P";
		// 1 - Obtener el codigo de mapa de la referencia
		final Long codMapa = getCodMapaVegalsa(codArt);
		// 2 - Obtener la fecha máxima de pedido en T_MIS_CALENDARIO_VEGALSA
		
		final Date maxFecha = getMaxFechaPedidoCalendarioMapaVegalsa(codCentro, codMapa, fechaInicio);
		if (maxFecha!=null){
			final Date today = new Date();		
			if (maxFecha.compareTo(today)>0){
				output = "S";
			}
		}
		
		return output;
		
	}	
	private Date getMaxFechaPedidoCalendarioMapaVegalsa(Long codCentro, Long codMapa, String fechaFin){
		
		String query="SELECT MAX(FECHA_PEDIDO) FROM T_MIS_CALENDARIO_MAPA_VEGALSA "
				+ " WHERE COD_CENTRO = "+codCentro+" AND COD_MAPA = "+codMapa
				+ " AND FECHA_REPO <= TO_DATE('"+fechaFin+"','DDMMYYYY') AND NVL(MARCADOR,'0') <> 'E' ";
		Date output=null;
		try{
			logger.debug(query);
			output=jdbcTemplate.queryForObject(query.toString(), Date.class);	
		}catch(Exception e){
			logger.warn("No se ha podido obtener la maxima FECHA_PEDIDO de la tabla T_MIS_CALENDARIO_MAPA_VEGALSA para el centro "+codCentro+" y el mapa "+codMapa+" cuya FECHA_REPO sea menor que "+fechaFin);
		}

		return output;
	}

	@Override
	public String getPrimeraFechaVentaDisponible(Long codCentro, Long codArticulo) throws Exception {
		String output = null;
		// 1 - Obtener el codigo de mapa de la referencia
		final Long codMapa = getCodMapaVegalsa(codArticulo);
		// 2 - Buscar la primera fecha de venta disponible. Se accede a T_MIS_CALENDARIO_MAPA_VEGALSA, para ese MAPA cuya fecha de pedido sea Mayor a hoy. Hay que recoger la fecha de venta menor (a partir de esa fecha todas son válidas).
		final Date fechaVentaMinimaDate = getFechaVentaMinimaVegalsa(codCentro, codMapa);
		if (fechaVentaMinimaDate!=null){
			output = Utilidades.formatearFecha(fechaVentaMinimaDate);
		}
		return output;
	}
	private Date getFechaVentaMinimaVegalsa(Long codCentro, Long codMapa){
		
		String query="SELECT MIN(FECHA_PEDIDO) FROM T_MIS_CALENDARIO_MAPA_VEGALSA "
				+ " WHERE COD_CENTRO = "+codCentro+" AND COD_MAPA = "+codMapa
				+ " AND TRUNC(FECHA_PEDIDO) > TRUNC(SYSDATE) ";
		Date output=null;
		try{
			logger.debug(query);
			output=jdbcTemplate.queryForObject(query.toString(), Date.class);	
		}catch(Exception e){
			logger.warn("No se ha podido obtener la minima FECHA_PEDIDO mayor que hoy de la tabla T_MIS_CALENDARIO_MAPA_VEGALSA para el centro "+codCentro+" y el mapa "+codMapa);
		}

		return output;
	}
	
	@Override
	public void deleteDatosSesionPedidoAdicional(Long codCentro, String idSesion) throws Exception {
		List<Object> params = new ArrayList<Object>();
		params.add(codCentro);
		params.add(idSesion);
		params.add(Constantes.PANTALLA_CALENDARIO);
		String query = "DELETE FROM T_PEDIDO_ADICIONAL WHERE CODCENTRO = ? AND IDSESION = ? AND PANTALLA=?";
		this.jdbcTemplate.update(query, params.toArray());
	}
}
