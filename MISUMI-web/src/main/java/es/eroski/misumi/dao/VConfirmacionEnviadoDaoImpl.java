package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VConfirmacionEnviadoDao;
import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionEnviado;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;

@Repository
public class VConfirmacionEnviadoDaoImpl implements VConfirmacionEnviadoDao {
	private JdbcTemplate jdbcTemplate;
	//private static Logger logger = Logger.getLogger(VConfirmacionEnviadoDaoImpl.class);

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
		    									 , resultSet.getFloat("cajas_intertienda")
		    									 , resultSet.getString("color")
		    									 , resultSet.getString("talla")
		    									 , resultSet.getString("modeloProveedor")
		    									 , resultSet.getString("motivo_pedido")
												 , resultSet.getLong("cajas_cortadas")
												 , resultSet.getString("inc_prevision_venta")
												 , resultSet.getLong("sm_estatico")
												 , resultSet.getLong("facing")
												 , resultSet.getString("origen_pedido")
			    );
		}

	};
	
	private RowMapper<VConfirmacionEnviado> rwVConfirmacionEnviadoMap = new RowMapper<VConfirmacionEnviado>() {
		public VConfirmacionEnviado mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
		    return new VConfirmacionEnviado(resultSet.getLong("cod_centro"),resultSet.getLong("cod_plat"),
		    		    resultSet.getLong("cod_ped_plat"),resultSet.getLong("cod_ped_aprov_central"),resultSet.getDate("fecha_trans"),
		    		    resultSet.getDate("fecha_ped"),resultSet.getDate("fecha_exp"),resultSet.getLong("cod_art"),
		    		    resultSet.getFloat("caj_env"),resultSet.getFloat("uni_serv"),resultSet.getString("tipo_ped"),
		    		    resultSet.getString("flg_enviado_pbl"),resultSet.getLong("grupo1"),resultSet.getLong("grupo2"),
		    		    resultSet.getLong("grupo3"),resultSet.getLong("grupo4"),resultSet.getLong("grupo5"),
		    		    resultSet.getString("descrip_art"),resultSet.getFloat("caja_normal"),resultSet.getFloat("caja_empuje"),
		    		    resultSet.getFloat("caja_impl"),resultSet.getFloat("caja_intertienda")
			    );
		}

	};

    private RowMapper<GenericExcelVO> rwExcelVConfirmacionEnviadoMap = new RowMapper<GenericExcelVO>() {
		public GenericExcelVO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		    return new GenericExcelVO(Utilidades.obtenerValorExcel(resultSet, 2),Utilidades.obtenerValorExcel(resultSet, 3),
		    		Utilidades.obtenerValorExcel(resultSet, 4),Utilidades.obtenerValorExcel(resultSet, 5),Utilidades.obtenerValorExcel(resultSet, 6),Utilidades.obtenerValorExcel(resultSet, 7)
		    		,Utilidades.obtenerValorExcel(resultSet, 8),Utilidades.obtenerValorExcel(resultSet, 9),Utilidades.obtenerValorExcel(resultSet, 10),Utilidades.obtenerValorExcel(resultSet, 11)
		    		,Utilidades.obtenerValorExcel(resultSet, 12),Utilidades.obtenerValorExcel(resultSet, 13),Utilidades.obtenerValorExcel(resultSet, 14),Utilidades.obtenerValorExcel(resultSet, 15)
		    		,Utilidades.obtenerValorExcel(resultSet, 16),Utilidades.obtenerValorExcel(resultSet, 17),Utilidades.obtenerValorExcel(resultSet, 18),Utilidades.obtenerValorExcel(resultSet, 19)
		    		,Utilidades.obtenerValorExcel(resultSet, 20),Utilidades.obtenerValorExcel(resultSet, 21),Utilidades.obtenerValorExcel(resultSet, 22),Utilidades.obtenerValorExcel(resultSet, 23)
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
	public List<VConfirmacionEnviado> findAll(VConfirmacionEnviado vConfirmacionEnviado) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	 
    	StringBuffer query = new StringBuffer("SELECT cod_centro, cod_plat, cod_ped_plat, cod_ped_aprov_central"
    											  + ", fecha_trans, fecha_ped, fecha_exp, cod_art, caj_env"
    											  + ", uni_serv, tipo_ped, flg_enviado_pbl "
    											  + ", grupo1, grupo2, grupo3, grupo4, grupo5"
    											  + ", descrip_art, caja_normal, caja_empuje"
    											  + ", caja_impl, caja_intertienda "
    										 + "FROM v_confirmacion_enviado ");

    	where.append("WHERE 1=1 ");

        if (vConfirmacionEnviado  != null){
        	if(vConfirmacionEnviado.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(vConfirmacionEnviado.getCodCentro());	        		
        	}
        	if(vConfirmacionEnviado.getCodPlat()!=null){
       		 	 where.append(" AND COD_PLAT = ? ");
	        	 params.add(vConfirmacionEnviado.getCodPlat());	        		
        	}
        	if(vConfirmacionEnviado.getCodPedPlat()!=null){
          		 where.append(" AND COD_PED_PLAT = ? ");
   	        	 params.add(vConfirmacionEnviado.getCodPedPlat());	        		
           	}
        	if(vConfirmacionEnviado.getCodPedAprovCentral()!=null){
         		 where.append(" AND COD_PED_APROV_CENTRAL = ? ");
  	        	 params.add(vConfirmacionEnviado.getCodPedAprovCentral());	        		
          	}
        	if(vConfirmacionEnviado.getFechaTrans()!=null){
	       		 where.append(" AND TRUNC(FECHA_TRANS) = TRUNC(?) ");
		         params.add(vConfirmacionEnviado.getFechaTrans());	        		
	       	}
        	if(vConfirmacionEnviado.getFechaPed()!=null){
	       		 where.append(" AND TRUNC(FECHA_PED) = TRUNC(?) ");
		         params.add(vConfirmacionEnviado.getFechaPed());	        		
	       	}
        	if(vConfirmacionEnviado.getFechaExp()!=null){
	       		 where.append(" AND TRUNC(FECHA_EXP) = TRUNC(?) ");
		         params.add(vConfirmacionEnviado.getFechaExp());	        		
	       	}
        	if(vConfirmacionEnviado.getCodArt()!=null){
         		 where.append(" AND COD_ART = ? ");
  	        	 params.add(vConfirmacionEnviado.getCodArt());	        		
          	}
        	if(vConfirmacionEnviado.getCajEnv()!=null){
        		 where.append(" AND CAJ_ENV = ? ");
 	        	 params.add(vConfirmacionEnviado.getCajEnv());	        		
         	}
        	if(vConfirmacionEnviado.getUniServ()!=null){
       		 	 where.append(" AND UNI_SERV = ? ");
	        	 params.add(vConfirmacionEnviado.getUniServ());	        		
        	}
        	if(vConfirmacionEnviado.getTipoPed()!=null){
       		 	 where.append(" AND UPPER(TIPO_PED) = upper(?) ");
	        	 params.add(vConfirmacionEnviado.getTipoPed());	        		
        	}
        	if(vConfirmacionEnviado.getFlgEnviadoPbl()!=null){
      		 	 where.append(" AND UPPER(FLG_ENVIADO_PBL) = upper(?) ");
	        	 params.add(vConfirmacionEnviado.getFlgEnviadoPbl());	        		
        	}
        	if(vConfirmacionEnviado.getGrupo1()!=null){
      		 	 where.append(" AND GRUPO1 = ? ");
	        	 params.add(vConfirmacionEnviado.getGrupo1());	        		
        	}
        	if(vConfirmacionEnviado.getGrupo2()!=null){
     		 	 where.append(" AND GRUPO2 = ? ");
	        	 params.add(vConfirmacionEnviado.getGrupo2());	        		
	       	}
        	if(vConfirmacionEnviado.getGrupo3()!=null){
	     		 where.append(" AND GRUPO3 = ? ");
		         params.add(vConfirmacionEnviado.getGrupo3());	        		
	       	}
        	if(vConfirmacionEnviado.getGrupo4()!=null){
	     		 where.append(" AND GRUPO4 = ? ");
		         params.add(vConfirmacionEnviado.getGrupo4());	        		
	       	}
        	if(vConfirmacionEnviado.getGrupo5()!=null){
	     		 where.append(" AND GRUPO5 = ? ");
		         params.add(vConfirmacionEnviado.getGrupo5());	        		
	       	}
        	if(vConfirmacionEnviado.getDescripArt()!=null){
      		 	 where.append(" AND UPPER(TIPO_PED) = upper(?) ");
	        	 params.add(vConfirmacionEnviado.getDescripArt());	        		
        	}
        	if(vConfirmacionEnviado.getCajaNormal()!=null){
	     		 where.append(" AND CAJA_NORMAL = ? ");
		         params.add(vConfirmacionEnviado.getCajaNormal());	        		
	       	}
        	if(vConfirmacionEnviado.getCajaEmpuje()!=null){
	     		 where.append(" AND CAJA_EMPUJE = ? ");
		         params.add(vConfirmacionEnviado.getCajaEmpuje());	        		
	       	}
        	if(vConfirmacionEnviado.getCajaImpl()!=null){
	     		 where.append(" AND CAJA_IMPL = ? ");
		         params.add(vConfirmacionEnviado.getCajaImpl());	        		
	       	}
        }
        
        query.append(where);
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY cod_centro, grupo1, grupo2, grupo3, grupo4, grupo5, cod_art ");
		query.append(order);

		List<VConfirmacionEnviado> vConfirmacionEnviadoLista = null;		
		
		try {
			vConfirmacionEnviadoLista = (List<VConfirmacionEnviado>) this.jdbcTemplate.query(query.toString(),this.rwVConfirmacionEnviadoMap, params.toArray()); 

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return vConfirmacionEnviadoLista;
	}

	@Override
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, List<Long> listaReferencias, Pagination pagination) throws Exception {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer("SELECT c.cod_centro"
    											 + ", c.cod_art"
    											 + ", c.motivo_pedido"
    											 + ", c.descrip_art"
    											 + ", (NVL(c.caja_normal,0) + NVL(c.caja_empuje,0)"
    											 + "	+ NVL(c.caja_impl,0)+ NVL(c.caja_intertienda,0)) total "
    											 + ", c.caja_normal cajas_normales"
    											 + ", c.caja_empuje cajas_empuje"
    											 + ", c.caja_impl cajas_cabecera"
    											 + ", 0 cajas_no_servidas"
    											 + ", '' motivo"
    											 + ", c.caja_intertienda cajas_intertienda"
												 + ", c.cajas_cortadas cajas_cortadas"
												 + ", c.inc_prevision_venta inc_prevision_venta"
												 + ", c.sm_estatico sm_estatico"
												 + ", c.facing facing"
												 + ", c.origen_pedido origen_pedido"
    											 + ", det.descr_talla talla"
    											 + ", det.descr_color color"
    											 + ", det.modelo_proveedor modeloProveedor "
    										+ "FROM v_confirmacion_enviado c"
    										   + ", v_datos_especificos_textil det "
    										+ "WHERE c.cod_art = det.cod_art(+) "
    										);
//    	where.append(" AND 1=1 ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append("AND c.cod_centro = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append("AND C.FECHA_EXP = TO_DATE(?, 'DD/MM/YYYY') ");
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
        		 where.append("AND C.COD_MAPA = ? ");
		         params.add(seguimientoMiPedido.getMapa());	        	
        	}else if (seguimientoMiPedido.getMapa()!=null && seguimientoMiPedido.getMapa().equals("0")){
        		where.append("AND NVL(C.COD_MAPA,9999) = 9999 ");
        	}
        }
        
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

		List<SeguimientoMiPedidoDetalle> lista = null;
		
		try {
		    lista =  (List<SeguimientoMiPedidoDetalle>) this.jdbcTemplate.query(query.toString(),this.rwSeguimientoMiPedidoDetalleMap, params.toArray());	    	
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

    	StringBuffer query = new StringBuffer("SELECT COUNT(1) "
    										+ "FROM v_confirmacion_enviado c "
    										 );

    	where.append("WHERE 1=1 ");
    	
        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND C.COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND C.FECHA_EXP = TO_DATE(?, 'DD/MM/YYYY') ");
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
			cont = this.jdbcTemplate.queryForLong(query.toString(),params.toArray());;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;
	}

    private String getMappedFieldOrderBy (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("REFERENCIA")){
	    	  return "c.COD_ART";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCION")){
	  		  return "c.DESCRIP_ART";
	  	  }else if(fieldName.toUpperCase().equals("COLOR")){
	  	      return "det.DESCR_COLOR";
	  	  }else if(fieldName.toUpperCase().equals("TALLA")){
	  	      return "det.DESCR_TALLA";
	  	  }else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
	  	      return "det.MODELO_PROVEEDOR";
	  	  }else if(fieldName.toUpperCase().equals("TOTAL")){
	  	      return "(NVL(c.CAJA_NORMAL,0) + NVL(c.CAJA_EMPUJE,0) + NVL(c.CAJA_IMPL,0) + NVL(c.CAJA_INTERTIENDA,0))";
	  	  }else if(fieldName.toUpperCase().equals("CAJASNORMALES")){
	  	      return "c.CAJA_NORMAL";
	  	  }else if(fieldName.toUpperCase().equals("CAJASEMPUJE")){
	  	      return "c.CAJA_EMPUJE";
	  	  }else if(fieldName.toUpperCase().equals("CAJASCABECERA")){
	  	      return "c.CAJA_IMPL";
	  	  }else if(fieldName.toUpperCase().equals("CAJASINTERTIENDA")){
	  	      return "c.CAJA_INTERTIENDA";
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
	  	  }else{
	  	      return fieldName;
	  	  }
    }
    
    private String getMappedFieldSelect (String fieldName, int target) {
	      if (fieldName.toUpperCase().equals("REFERENCIA")){
	    	  return "c.COD_ART";
	  	  }else if(fieldName.toUpperCase().equals("DESCRIPCION")){
	  		  return "c.DESCRIP_ART";
	  	  }else if(fieldName.toUpperCase().equals("COLOR")){
	  	      return "det.DESCR_COLOR";
	  	  }else if(fieldName.toUpperCase().equals("TALLA")){
	  	      return "det.DESCR_TALLA";
	  	  }else if(fieldName.toUpperCase().equals("MODELOPROVEEDOR")){
	  	      return "det.MODELO_PROVEEDOR";
	  	  }else if(fieldName.toUpperCase().equals("TOTAL")){
	  	      return "(NVL(c.CAJA_NORMAL,0) + NVL(c.CAJA_EMPUJE,0) + NVL(c.CAJA_IMPL,0) + NVL(c.CAJA_INTERTIENDA,0))";
	  	  }else if(fieldName.toUpperCase().equals("CAJASNORMALES")){
	  	      return "c.CAJA_NORMAL";
	  	  }else if(fieldName.toUpperCase().equals("CAJASEMPUJE")){
	  	      return "c.CAJA_EMPUJE";
	  	  }else if(fieldName.toUpperCase().equals("CAJASCABECERA")){
	  	      return "c.CAJA_IMPL";
	  	  }else if(fieldName.toUpperCase().equals("CAJASINTERTIENDA")){
	  	      return "c.CAJA_INTERTIENDA";
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
    	where.append(" AND 1=1 ");
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
    	
    	StringBuffer query = new StringBuffer(" SELECT ");
    	query.append(fields); 
    	query.append( " FROM v_confirmacion_enviado c, v_datos_especificos_textil det ");
    	query.append( " WHERE c.cod_art = det.cod_art(+) ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND c.COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND c.FECHA_EXP = TO_DATE(?, 'DD/MM/YYYY') ");
		         params.add(seguimientoMiPedido.getFechaPedidoDDMMYYYY());	        		
	       	}
        	if(seguimientoMiPedido.getCodArea()!=null){
     		 	 where.append(" AND c.GRUPO1 = ? ");
	        	 params.add(seguimientoMiPedido.getCodArea());	        		
       		}
       		if(seguimientoMiPedido.getCodSeccion()!=null){
    		 	 where.append(" AND c.GRUPO2 = ? ");
	        	 params.add(seguimientoMiPedido.getCodSeccion());	        		
	       	}
       		if(seguimientoMiPedido.getCodCategoria()!=null){
	     		 where.append(" AND c.GRUPO3 = ? ");
		         params.add(seguimientoMiPedido.getCodCategoria());	        		
	       	}
       		if(seguimientoMiPedido.getCodArt()!=null){
	     		 where.append(" AND c.COD_ART = ? ");
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
        
        StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
        
        order.append(" ORDER BY c.grupo1, c.grupo2, c.grupo3, c.grupo4, c.grupo5, c.cod_art");
		query.append(order);
	    
	    List<GenericExcelVO> lista = null;
		
		try {
			lista = this.jdbcTemplate.query(query.toString(),this.rwExcelVConfirmacionEnviadoMap, params.toArray());

		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

		return lista;
    }

	@Override
	public Long findTotalReferenciasBajoPedido(
			SeguimientoMiPedido seguimientoMiPedido) {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) "
    										+ " FROM v_confirmacion_enviado "
    										);

    	where.append("WHERE CAJA_NORMAL IS NOT NULL AND CAJA_NORMAL <> 0 ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

	    Long  cont = null;
		
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;
	}

	@Override
	public Float findTotalCajasBajoPedido(
			SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT ROUND(SUM(NVL(CAJA_NORMAL,0))) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

	    
	    Float  total = null;
		
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class,  params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return total;
	}

	@Override
	public Long findTotalReferenciasEmpuje(
			SeguimientoMiPedido seguimientoMiPedido) {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE CAJA_EMPUJE IS NOT NULL AND CAJA_EMPUJE <> 0 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

	    
	    Long  cont = null;
		
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;
	}

	@Override
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT ROUND(SUM(NVL(CAJA_EMPUJE,0))) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

        
        Float  total = null;
		
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class,  params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return total;
	}

	@Override
	public Long findTotalReferenciasImplCab(
			SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append(" WHERE CAJA_IMPL IS NOT NULL AND CAJA_IMPL <> 0 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

	   
	    Long  cont = null;
		
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;
	}

	@Override
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT ROUND(SUM(NVL(CAJA_IMPL,0))) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

        
        
        Float  total = null;
		
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class,  params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return total;
	}

	
	@Override
	public Long findTotalReferenciasIntertienda(
			SeguimientoMiPedido seguimientoMiPedido) {
		
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE CAJA_INTERTIENDA IS NOT NULL AND CAJA_INTERTIENDA <> 0 ");

    	StringBuffer query = new StringBuffer(" SELECT COUNT(DISTINCT(COD_ART)) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

	    Long  cont = null;
		
		try {
			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return cont;
	}

	@Override
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido) {

    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(" SELECT ROUND(SUM(NVL(CAJA_INTERTIENDA,0))) " + 
											  " FROM V_CONFIRMACION_ENVIADO ");

        if (seguimientoMiPedido  != null){
        	if(seguimientoMiPedido.getCodCentro()!=null){
        		 where.append(" AND COD_CENTRO = ? ");
	        	 params.add(seguimientoMiPedido.getCodCentro());	        		
        	}
        	if(seguimientoMiPedido.getFechaPedidoDDMMYYYY()!=null){
	       		 where.append(" AND FECHA_EXP = TO_DATE(?, 'DDMMYYYY') ");
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

        
        Float  total = null;
		
		try {
			total = this.jdbcTemplate.queryForObject(query.toString(), Float.class,  params.toArray());

		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
		
		return total;
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
