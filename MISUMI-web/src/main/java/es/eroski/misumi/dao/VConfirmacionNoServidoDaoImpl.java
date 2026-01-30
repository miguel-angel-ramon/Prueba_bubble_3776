package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VConfirmacionNoServidoDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionNoServido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades; 

@Repository
public class VConfirmacionNoServidoDaoImpl implements VConfirmacionNoServidoDao {
	private JdbcTemplate jdbcTemplate;
	private static Logger logger = Logger.getLogger(VConfirmacionNoServidoDaoImpl.class);

	private RowMapper<SeguimientoMiPedidoDetalle> rwSeguimientoMiPedidoDetalleMap = new RowMapper<SeguimientoMiPedidoDetalle>() {
		public SeguimientoMiPedidoDetalle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new SeguimientoMiPedidoDetalle( new Centro(resultSet.getLong("cod_centro"),null,null, null, null, null, null, null, null, null,null)
		    									 , resultSet.getLong("cod_art")
		    									 , resultSet.getString("descrip_art")
		    									 , resultSet.getFloat("total")
		    									 , resultSet.getFloat("cajas_normales")
		    									 , resultSet.getFloat("cajas_empuje")
		    									 , resultSet.getFloat("cajas_cabecera")
		    									 , resultSet.getFloat("cajas_no_servidas")
		    									 , resultSet.getString("motivo")
		    									 , null
		    									 , resultSet.getString("color")
		    									 , resultSet.getString("talla")
		    									 , resultSet.getString("modeloproveedor")
		    									 , resultSet.getString("motivo_pedido")
												 , resultSet.getLong("cajas_cortadas")
												 , resultSet.getString("inc_prevision_venta")
												 , resultSet.getLong("sm_estatico")
												 , resultSet.getLong("facing")
												 , resultSet.getString("origen_pedido")
			    );
		}

	};
	
	private RowMapper<VConfirmacionNoServido> rwVConfirmacionNoServidoMap = new RowMapper<VConfirmacionNoServido>() {
		public VConfirmacionNoServido mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new VConfirmacionNoServido(resultSet.getLong("cod_centro"),resultSet.getLong("cod_plat"),
		    		    resultSet.getLong("cod_ped_plat"),resultSet.getLong("cod_ped_aprov_central"),resultSet.getDate("fecha_trans"),
		    		    resultSet.getDate("fecha_ped"),resultSet.getDate("fecha_previs_ent"),resultSet.getLong("cod_art"),
		    		    resultSet.getFloat("caja_nsr"),resultSet.getFloat("uni_no_serv"), resultSet.getString("flg_enviado_pbl"),
		    		    resultSet.getDate("fecha_nsr"),resultSet.getLong("grupo1"),resultSet.getLong("grupo2"),
		    		    resultSet.getLong("grupo3"),resultSet.getLong("grupo4"),resultSet.getLong("grupo5"),
		    		    resultSet.getString("descrip_art"),resultSet.getFloat("uni_caja_serv"),resultSet.getLong("motivo")
			    );
		}

	};

    private RowMapper<GenericExcelVO> rwExcelVConfirmacionNoServidoMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet, 3),
		    		Utilidades.obtenerValorExcel(resultSet, 4),Utilidades.obtenerValorExcel(resultSet, 5),Utilidades.obtenerValorExcel(resultSet, 6),Utilidades.obtenerValorExcel(resultSet, 7)
		    		,Utilidades.obtenerValorExcel(resultSet, 8),Utilidades.obtenerValorExcel(resultSet, 9),Utilidades.obtenerValorExcel(resultSet, 10),Utilidades.obtenerValorExcel(resultSet, 11)
		    		,Utilidades.obtenerValorExcel(resultSet, 12),Utilidades.obtenerValorExcel(resultSet, 13),Utilidades.obtenerValorExcel(resultSet, 14),Utilidades.obtenerValorExcel(resultSet, 15)
		    		,Utilidades.obtenerValorExcel(resultSet, 16),Utilidades.obtenerValorExcel(resultSet, 17),Utilidades.obtenerValorExcel(resultSet, 18),Utilidades.obtenerValorExcel(resultSet, 19)
		    		,Utilidades.obtenerValorExcel(resultSet, 20),Utilidades.obtenerValorExcel(resultSet, 21),Utilidades.obtenerValorExcel(resultSet, 22),resultSet.getString(23)
				    ,Utilidades.obtenerValorExcel(resultSet, 24),Utilidades.obtenerValorExcel(resultSet, 25),Utilidades.obtenerValorExcel(resultSet, 26),Utilidades.obtenerValorExcel(resultSet, 27)
				    ,Utilidades.obtenerValorExcel(resultSet, 28),Utilidades.obtenerValorExcel(resultSet, 29),Utilidades.obtenerValorExcel(resultSet, 30),Utilidades.obtenerValorExcel(resultSet, 31)
				    ,Utilidades.obtenerValorExcel(resultSet, 32),Utilidades.obtenerValorExcel(resultSet, 33),Utilidades.obtenerValorExcel(resultSet, 34),Utilidades.obtenerValorExcel(resultSet, 35)
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
	public List<VConfirmacionNoServido> findAll(VConfirmacionNoServido vConfirmacionNoServido) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer("SELECT cod_centro, cod_plat, cod_ped_plat, cod_ped_aprov_central"
    											 + ", fecha_trans, fecha_ped, fecha_previs_ent, cod_art, caja_nsr"
    											 + ", uni_no_serv, flg_enviado_pbl, fecha_nsr, grupo1, grupo2"
    											 + ", grupo3, grupo4, grupo5, descrip_art, uni_caja_serv, motivo "
    										 +"FROM v_confirmacion_no_servido ");

    	where.append("WHERE 1=1 ");
    
        if (vConfirmacionNoServido  != null){
        	if(vConfirmacionNoServido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vConfirmacionNoServido.getCodCentro());	        		
        	}
        	if(vConfirmacionNoServido.getCodPlat()!=null){
       		 	 where.append(" AND COD_PLAT = ? ");
	        	 params.add(vConfirmacionNoServido.getCodPlat());	        		
        	}
        	if(vConfirmacionNoServido.getCodPedPlat()!=null){
          		 where.append(" AND COD_PED_PLAT = ? ");
   	        	 params.add(vConfirmacionNoServido.getCodPedPlat());	        		
           	}
        	if(vConfirmacionNoServido.getCodPedAprovCentral()!=null){
         		 where.append(" AND COD_PED_APROV_CENTRAL = ? ");
  	        	 params.add(vConfirmacionNoServido.getCodPedAprovCentral());	        		
          	}
        	if(vConfirmacionNoServido.getFechaTrans()!=null){
	       		 where.append(" AND TRUNC(FECHA_TRANS) = TRUNC(?) ");
		         params.add(vConfirmacionNoServido.getFechaTrans());	        		
	       	}
        	if(vConfirmacionNoServido.getFechaPed()!=null){
	       		 where.append(" AND TRUNC(FECHA_PED) = TRUNC(?) ");
		         params.add(vConfirmacionNoServido.getFechaPed());	        		
	       	}
        	if(vConfirmacionNoServido.getFechaPrevisEnt()!=null){
	       		 where.append(" AND TRUNC(FECHA_PREVIS_ENT) = TRUNC(?) ");
		         params.add(vConfirmacionNoServido.getFechaPrevisEnt());	        		
	       	}
        	if(vConfirmacionNoServido.getCodArt()!=null){
         		 where.append(" AND COD_ART = ? ");
  	        	 params.add(vConfirmacionNoServido.getCodArt());	        		
          	}
        	if(vConfirmacionNoServido.getCajaNsr()!=null){
        		 where.append(" AND CAJA_NSR = ? ");
 	        	 params.add(vConfirmacionNoServido.getCajaNsr());	        		
         	}
        	if(vConfirmacionNoServido.getUniNoServ()!=null){
       		 	 where.append(" AND UNI_NO_SERV = ? ");
	        	 params.add(vConfirmacionNoServido.getUniNoServ());	        		
        	}
        	if(vConfirmacionNoServido.getFlgEnviadoPbl()!=null){
      		 	 where.append(" AND UPPER(FLG_ENVIADO_PBL) = upper(?) ");
	        	 params.add(vConfirmacionNoServido.getFlgEnviadoPbl());	        		
        	}
        	if(vConfirmacionNoServido.getFechaNsr()!=null){
	       		 where.append(" AND TRUNC(FECHA_NSR) = TRUNC(?) ");
		         params.add(vConfirmacionNoServido.getFechaNsr());	        		
	       	}
        	if(vConfirmacionNoServido.getGrupo1()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vConfirmacionNoServido.getGrupo1());	        		
        	}
        	if(vConfirmacionNoServido.getGrupo2()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vConfirmacionNoServido.getGrupo2());	        		
	       	}
        	if(vConfirmacionNoServido.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vConfirmacionNoServido.getGrupo3());	        		
	       	}
        	if(vConfirmacionNoServido.getGrupo4()!=null){
	     		 where.append(" AND GRUPO4 = ? ");
		         params.add(vConfirmacionNoServido.getGrupo4());	        		
	       	}
        	if(vConfirmacionNoServido.getGrupo5()!=null){
	     		 where.append(" AND GRUPO5 = ? ");
		         params.add(vConfirmacionNoServido.getGrupo5());	        		
	       	}
        	if(vConfirmacionNoServido.getDescripArt()!=null){
      		 	 where.append(" AND UPPER(TIPO_PED) = upper(?) ");
	        	 params.add(vConfirmacionNoServido.getDescripArt());	        		
        	}
        	if(vConfirmacionNoServido.getUniCajaServ()!=null){
	     		 where.append(" AND UNI_CAJA_SERV = ? ");
		         params.add(vConfirmacionNoServido.getUniCajaServ());	        		
	       	}
        	if(vConfirmacionNoServido.getMotivo()!=null){
	     		 where.append(" AND MOTIVO = ? ");
		         params.add(vConfirmacionNoServido.getMotivo());	        		
	       	}
        }
        
        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" order by cod_centro, grupo1, grupo2, grupo3, grupo4, grupo5, cod_art ");
		query.append(order);

		List<VConfirmacionNoServido> vConfirmacionNoServidoLista = null;		
		
		try {
			vConfirmacionNoServidoLista = (List<VConfirmacionNoServido>) this.jdbcTemplate.query(query.toString(),this.rwVConfirmacionNoServidoMap, params.toArray()); 

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return vConfirmacionNoServidoLista;
	}

	@Override
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, List<Long> listaReferencias, Pagination pagination) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	//where.append("WHERE 1=1 ");
    	
    	StringBuffer query = new StringBuffer("SELECT c.cod_centro"
    											 + ", c.cod_art"
    											 + ", c.descrip_art"
    											 + ", c.caja_nsr total"
    											 + ", c.motivo_pedido"
    											 + ", 0 cajas_normales"
    											 + ", 0 cajas_empuje"
    											 + ", 0 cajas_cabecera"
    											 + ", c.caja_nsr cajas_no_servidas"
    											 + ", (c.motivo || '-' || NVL(fm.descripcion,NVL(f.descripcion,''))) motivo"
												 + ", c.cajas_cortadas cajas_cortadas"
												 + ", c.inc_prevision_venta inc_prevision_venta"
												 + ", c.sm_estatico sm_estatico"
												 + ", c.facing facing"
												 + ", c.origen_pedido origen_pedido"
    											 + ", det.descr_talla talla"
    											 + ", det.descr_color color"
    											 + ", det.modelo_proveedor modeloProveedor "
    										+ "FROM v_confirmacion_no_servido c"
    										   + ", faltas f"
    										   + ", faltas_misumi fm"
    										   + ", v_datos_especificos_textil det "
    										+ "WHERE c.cod_art = det.cod_art(+) "
    										);
    	
        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append("AND c.cod_centro = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append("AND c.fecha_previs_ent = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append("AND c.grupo1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append("AND c.grupo2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append("AND c.grupo3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if(seguimientoMiPedido.getCodArt()!=null){
        		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		    	referencias.append(seguimientoMiPedido.getCodArt());
		    	if (null != listaReferencias){
		    		for(Long referencia : listaReferencias){
		    			referencias.append(", ").append(referencia);
		    		}
		    	}
	     		 where.append("AND c.cod_art IN ( ").append(referencias).append(" )");        		
	       	}
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append("AND c.cod_mapa = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append("AND NVL(c.cod_mapa,9999) = 9999 ");
        	}
        }

        //Condición de join
        where.append("AND f.cod_falta(+) = c.motivo AND fm.cod_falta(+) = c.motivo ");
        
		query.append(where);
        
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null) {
				order.append(" ORDER BY " + this.getMappedFieldOrderBy(pagination.getSort(), 1) + " "
						+ pagination.getAscDsc());
				query.append(order);
			}
		}else{
			String campoOrdenacion = "c.grupo1, c.grupo2, c.grupo3, c.grupo4, c.grupo5, c.cod_art";
			order.append(" ORDER BY " + campoOrdenacion + " ASC ");	
			query.append(order);
		}

		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(
					pagination, query.toString()));
		}

    	//StringBuffer query = new StringBuffer("SELECT c.cod_centro, c.cod_art, c.descrip_art, c.caja_nsr total,0 cajas_normales, 0 cajas_empuje, 0 cajas_cabecera, c.caja_nsr cajas_no_servidas,(c.motivo || '-' || NVL (fm.descripcion, NVL (f.descripcion, ''))) motivo,det.descr_talla talla, det.descr_color color,det.modelo_proveedor modeloproveedor FROM v_confirmacion_no_servido c,faltas f,faltas_misumi fm,v_datos_especificos_textil det WHERE c.cod_art = det.cod_art(+) AND c.cod_centro = 157 AND c.fecha_previs_ent = TO_DATE ('29/09/2015', 'DD/MM/YYYY') AND c.grupo1 = 3 AND f.cod_falta(+) = c.motivo AND fm.cod_falta(+) = c.motivo AND det.descr_talla IS NOT NULL");

		logger.debug(query);

		List<SeguimientoMiPedidoDetalle> lista = null;
		try {
			lista = (List<SeguimientoMiPedidoDetalle>) this.jdbcTemplate.query(query.toString(),this.rwSeguimientoMiPedidoDetalleMap, params.toArray());
		} catch (Exception e){			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;

	}

	@Override
	public Long findSeguimientoMiPedidoCont(
			SeguimientoMiPedido seguimientoMiPedido) throws Exception {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer(" SELECT COUNT(1) " + 
											  " FROM v_confirmacion_no_servido c ");
    	where.append("WHERE 1=1 ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND C.COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND C.FECHA_PREVIS_ENT = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append(" AND C.GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append(" AND C.GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND C.GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if(seguimientoMiPedido.getCodArt()!=null){
	     		 where.append(" AND C.COD_ART = ? ");
		         params.add(seguimientoMiPedido.getCodArt());	        		
	       	}     
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND C.COD_MAPA = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND NVL(C.COD_MAPA,9999) = 9999 ");
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

    private String getMappedFieldOrderBy (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("REFERENCIA")){
	    	  return "C.COD_ART";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCION")){
	  		  return "C.DESCRIP_ART";
	  	  }else if(fieldName.toUpperCase().equals("COLOR")){
	  	      return "det.DESCR_COLOR";
	  	  }else if(fieldName.toUpperCase().equals("TALLA")){
	  	      return "det.DESCR_TALLA";
	  	  }else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
	  	      return "det.MODELO_PROVEEDOR";
	  	  }else if(fieldName.toUpperCase().equals("CAJASNOSERVIDAS")){
	  	      return "C.CAJA_NSR";
	  	  }else if(fieldName.toUpperCase().equals("MOTIVO")){
	  	      return "C.MOTIVO || '-' || NVL(FM.DESCRIPCION,NVL(F.DESCRIPCION,''))";
	  	  }else if(fieldName.toUpperCase().equals("MOTIVOPEDIDO")){
	  	      return "C.MOTIVO_PEDIDO";
	  	  }else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
	  		  return "CAJAS_CORTADAS";
	  	  }else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
	  		  return "INC_PREVISION_VENTA";
	  	  }else if (fieldName.toUpperCase().equals("SMESTATICO")) {
	  		  return "SM_ESTATICO";
	  	  }else if (fieldName.toUpperCase().equals("FACING")) {
	  		  return "FACING";
	  	  }else if (fieldName.toUpperCase().equals("ORIGENPEDIDO")) {
	  		  return "ORIGEN_PEDIDO";
	  	  }else {
	  	      return fieldName;
	  	  }
    }
    
  	private String getMappedFieldSelect (String fieldName, int target) {
  		 if (fieldName.toUpperCase().equals("REFERENCIA")){
  			  return "C.COD_ART";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCION")){
	  		  return "C.DESCRIP_ART";
	  	  }else if(fieldName.toUpperCase().equals("COLOR")){
	  	      return "det.DESCR_COLOR";
	  	  }else if(fieldName.toUpperCase().equals("TALLA")){
	  	      return "det.DESCR_TALLA";
	  	  }else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
	  	      return "det.MODELO_PROVEEDOR";
	  	  }else if(fieldName.toUpperCase().equals("CAJASNOSERVIDAS")){
	  	      return "C.CAJA_NSR";
	  	  }else if(fieldName.toUpperCase().equals("MOTIVO")){
	  	      return "C.MOTIVO || '-' || NVL(FM.DESCRIPCION,NVL(F.DESCRIPCION,''))";
	  	  }else if(fieldName.toUpperCase().equals("MOTIVOPEDIDO")){
	  	      return "C.MOTIVO_PEDIDO";
	  	  }else if (fieldName.toUpperCase().equals("CAJASCORTADAS")) {
	  		  return "cajas_cortadas";
	  	  }else if (fieldName.toUpperCase().equals("INCPREVISIONVENTA")) {
	  		  return "NVL(inc_prevision_venta, 0)||'%'";
	  	  }else if (fieldName.toUpperCase().equals("SMESTATICO")) {
	  		  return "sm_estatico";
	  	  }else if (fieldName.toUpperCase().equals("FACING")) {
	  		  return "facing";
	  	  }else if (fieldName.toUpperCase().equals("ORIGENPEDIDO")) {
	  		  return "origen_pedido";
	  	  }else{
	  	      return fieldName;
	  	  }
  	}

	@Override
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(
			SeguimientoMiPedido seguimientoMiPedido, String[] columnModel)
			throws Exception {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	//where.append("WHERE 1=1 ");
    	//columnModel
    	int j=0;
    	
    	String fields="null";
    	List<String> listColumns = Arrays.asList(columnModel);
    	for(int i=0; i<listColumns.size();i++){
    		fields = fields + ", " +this.getMappedFieldSelect(listColumns.get(i),1);
    		j++;
    	}
    	while (j<=41){
    		fields = fields + ", null";
    		j++;
    	}
    	
    	StringBuffer query = new StringBuffer("SELECT ");
    	query.append(fields); 
    	query.append( " FROM v_confirmacion_no_servido c, faltas f, faltas_misumi fm, v_datos_especificos_textil det ");
    	query.append( " WHERE c.cod_art = det.cod_art(+) ");
    	
        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append("AND c.cod_centro = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append("AND c.fecha_previs_ent = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append("AND c.grupo1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append("AND c.grupo2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append("AND c.grupo3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if(seguimientoMiPedido.getCodArt()!=null){
	     		 where.append("AND c.cod_art = ? ");
		         params.add(seguimientoMiPedido.getCodArt());	        		
	       	}
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append("AND c.cod_mapa = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append("AND NVL(c.cod_mapa,9999) = 9999 ");
        	}
        }

        //Condición de join
        where.append(" AND F.COD_FALTA(+) = C.MOTIVO AND FM.COD_FALTA(+) = C.MOTIVO ");

        query.append(where);
        
        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        
        order.append(" ORDER BY c.cod_art ASC");
		query.append(order);
		logger.debug(query);

	    List<GenericExcelVO> lista = null; 

	    try {
			lista = (List<GenericExcelVO>) this.jdbcTemplate.query(query.toString(),this.rwExcelVConfirmacionNoServidoMap, params.toArray());

		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return lista;
	}

	@Override
	public Long findTotalReferenciasNoServidas(
			SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE CAJA_NSR IS NOT NULL AND CAJA_NSR <> 0 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + 
											  " FROM V_CONFIRMACION_NO_SERVIDO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if(seguimientoMiPedido.getCodArt()!=null){
	     		 where.append(" AND COD_ART = ? ");
		         params.add(seguimientoMiPedido.getCodArt());	        		
	       	} 
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND COD_MAPA = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
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
	public Float findTotalCajasNoServidas(
			SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT ROUND(SUM(NVL(CAJA_NSR,0)),1) " +
											  " FROM V_CONFIRMACION_NO_SERVIDO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_PREVIS_ENT = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
        	}
        	if(seguimientoMiPedido.getCodSeccion()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
        	if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
        	if(seguimientoMiPedido.getCodArt()!=null){
	     		 where.append(" AND COD_ART = ? ");
		         params.add(seguimientoMiPedido.getCodArt());	        		
	       	}      
        	if(seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().length()>0 && !seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND COD_MAPA = ? ");
        		params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append(" AND NVL(COD_MAPA,9999) = 9999 ");
        	}
        }

        query.append(where);
        
        Float total = null; 
	    
	    try {
	    	total = this.jdbcTemplate.queryForObject(query.toString(), Float.class,  params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    
	    return total;
	}
	
	@Override
	public Boolean checkNSR(SeguimientoMiPedido seguimientoPedido){
		StringBuffer query = new StringBuffer();
		query.append(" select count(c.cod_art) ")
		.append(" from v_confirmacion_pedido c ")
		.append(" where c.cod_art = ? ")
		.append(" and c.cod_centro = ? ")
		.append(" and exists (select 'x' from V_CONFIRMACION_NO_SERVIDO ns ")
		.append(" where c.cod_centro = ns.cod_centro ")
		.append(" AND to_char(c.cod_art) = ns.cod_art ")
		.append(" AND c.FECHA_PREVIS_ENT = ns.FECHA_PREVIS_ENT) ")
		.append(" and c.FECHA_PREVIS_ENT =  ")
		.append("    (select max(c2.fecha_previs_ent) ")
		.append("     from v_confirmacion_pedido c2 ")
		.append("     where c.COD_CENTRO = c2.cod_centro ")
		 .append("     and c.cod_art = c2.cod_art ")
		 .append("     and c2.FECHA_PREVIS_ENT < trunc(sysdate)) ");
		
		List<Object> params = new ArrayList<Object>();
		params.add(seguimientoPedido.getCodArt());
		params.add(seguimientoPedido.getCodCentro());
		
		Long count = null;
	    try {
	    	count = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		if (count > 0){
			return true;
		} else {
			return false;
		}
		
	}
	
	@Override
	public int countCentroVegalsa(Long codCentro) throws Exception {

		String query = "SELECT count(cod_centro) " + " FROM v_centros_plataformas " + " WHERE cod_centro=?"
				+ " AND cod_soc = 13";

		int cont = 0;

		try {

			cont = this.jdbcTemplate.queryForObject(query, new Object[] { codCentro }, Integer.class);

		} catch (Exception e) {

			Utilidades.mostrarMensajeErrorSQL(query.toString(),
					new ArrayList<Object>(Arrays.asList(new Object[] { codCentro })), e);
		}

		return cont;
	}
}
