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

import es.eroski.misumi.dao.iface.TEncargosClteDao;
import es.eroski.misumi.model.Roturas;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class TEncargosClteDaoImpl implements TEncargosClteDao{
	
	private static Logger logger = Logger.getLogger(TEncargosClteDaoImpl.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<TEncargosClte> rwTEncargosClteMap = new RowMapper<TEncargosClte>() {
		 
		public TEncargosClte mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			 
			 return new TEncargosClte(resultSet.getLong("LOCALIZADOR"),
					resultSet.getLong("CENTRO"),
					resultSet.getString("AREA"),
					resultSet.getString("SECCION"),
					resultSet.getString("CATEGORIA"),
					resultSet.getString("SUBCATEGORIA"),
					resultSet.getString("SEGMENTO"),
					resultSet.getLong("REFERENCIA"),
					resultSet.getLong("REFERENCIA_MADRE"),
					resultSet.getString("DESCRIPCION"),
					resultSet.getDouble("UNIDADESCAJA"),
					resultSet.getString("IDDSESION"),
					resultSet.getString("CONTACTO_CENTRO"),
					resultSet.getString("TELEFONO_CENTRO"),
					resultSet.getString("NOMBRE_CLIENTE"),
					resultSet.getString("APELLIDO_CLIENTE"),
					resultSet.getString("TELEFONO_CLIENTE"),
					resultSet.getDate("FECHA_HORA_ENCARGO"),
					resultSet.getString("TIPO_ENCARGO"),
					resultSet.getDate("FECHA_VENTA"),
					resultSet.getDate("FECHA_VENTA_MODIFICADA"),
					resultSet.getString("FECHA_INFERIOR"),
					resultSet.getString("ESPECIFICACION"),
					(resultSet.getBigDecimal("PESO_DESDE")!=null?resultSet.getDouble("PESO_DESDE"):null),
					(resultSet.getBigDecimal("PESO_HASTA")!=null?resultSet.getDouble("PESO_HASTA"):null),
					resultSet.getString("CONFIRMAR_ESPECIFICACIONES"),
					resultSet.getString("FALTA_REF"),
					resultSet.getString("CAMBIO_REF"),
					resultSet.getString("CONFIRMAR_PRECIO"),
					(resultSet.getBigDecimal("CANT_ENCARGO")!=null?resultSet.getDouble("CANT_ENCARGO"):null),
					(resultSet.getBigDecimal("CANT_FINAL_COMPRA")!=null?resultSet.getDouble("CANT_FINAL_COMPRA"):null),
					(resultSet.getBigDecimal("CANT_SERVIDO")!=null?resultSet.getDouble("CANT_SERVIDO"):null),
					(resultSet.getBigDecimal("CANT_NO_SERVIDO")!=null?resultSet.getDouble("CANT_NO_SERVIDO"):null),
					resultSet.getString("ESTADO"),
					resultSet.getString("OBSERVACIONES_MISUMI"),
					resultSet.getBigDecimal("CODIGO_PEDIDO_INTERNO")!=null?resultSet.getBigDecimal("CODIGO_PEDIDO_INTERNO"):null,
					resultSet.getString("FLG_MODIFICABLE"),
					resultSet.getString("COD_TP_APROV"),
					resultSet.getString("VITRINA"),
					resultSet.getString("REL_COMPRA_VENTA"),
					resultSet.getLong("CODIGO_ERROR"),
					resultSet.getString("DESCRIPCION_ERROR"),
					resultSet.getLong("NIVEL"),
					resultSet.getDate("FECHACREACION")   ,
					resultSet.getString("DESCRIPCION_GESTADIC"),
					resultSet.getString("ESTADO_GESTADIC"),
					resultSet.getString("TXT_DETALLE_GESTADIC"),
					resultSet.getString("TXT_SITUACION_GESTADIC"),
					null,
					resultSet.getLong("COD_ART_GRID"),
					resultSet.getString("DESCRIP_ART_GRID")
			);
		}
	};

    @Autowired
    public void setDataSource(DataSource dataSource) {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    } 
    
    @Override
	public void deleteHistorico() throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_ENCARGOS_CLTE WHERE FECHACREACION < (SYSDATE - ?) ");

		params.add(Constantes.DIAS_ELIMINAR);

		//this.jdbcTemplate.update(query.toString(), params.toArray());

	}

    @Override
	public void delete(TEncargosClte tEncargosClte) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_ENCARGOS_CLTE WHERE IDDSESION = ? ");
		params.add(tEncargosClte.getIddsesion());

		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}


	}
    
    
    @Override
	public void deleteEncargo(TEncargosClte tEncargosClte) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_ENCARGOS_CLTE WHERE IDDSESION = ? ");
		query.append("AND CENTRO = ? AND LOCALIZADOR = ? ");

		params.add(tEncargosClte.getIddsesion());
		params.add(tEncargosClte.getCentro());
		params.add(tEncargosClte.getLocalizador());

		try {

			Integer total = this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}
    
    @Override
	public void updateErrorEncargo(TEncargosClte tEncargosClte) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE T_ENCARGOS_CLTE SET CODIGO_ERROR = ?, DESCRIPCION_ERROR = ? ");
		query.append("WHERE IDDSESION = ? AND CENTRO = ?  AND REFERENCIA = ?");
		if (null != tEncargosClte.getLocalizador()){
			query.append(" AND LOCALIZADOR = ?");
		}
		
		params.add(tEncargosClte.getCodigoError());
		params.add(tEncargosClte.getDescripcionError());
		params.add(tEncargosClte.getIddsesion());
		params.add(tEncargosClte.getCentro());
		params.add(tEncargosClte.getReferencia());
		
		if (null != tEncargosClte.getLocalizador()){
			params.add(tEncargosClte.getLocalizador());
		}

		
		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	}
    
    @Override
	public void updateEncargo(TEncargosClte tEncargosClte) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("UPDATE T_ENCARGOS_CLTE SET CANT_ENCARGO = ?, CONTACTO_CENTRO = ?, NOMBRE_CLIENTE = ?, ");
		query.append(" APELLIDO_CLIENTE = ?, TELEFONO_CLIENTE = ?, TIPO_ENCARGO = ?, FECHA_VENTA = ?,FECHA_VENTA_MODIFICADA = ?, FECHA_INFERIOR = ?, ");
		query.append(" ESPECIFICACION = ?, PESO_DESDE = ?, PESO_HASTA = ? ");
		query.append(" WHERE IDDSESION = ? AND CENTRO = ?  AND REFERENCIA = ? AND LOCALIZADOR = ?"); 
			
		params = new ArrayList<Object>();
		
		params.add(tEncargosClte.getCantEncargo());
		params.add(tEncargosClte.getContactoCentro());
		params.add(tEncargosClte.getNombreCliente());
		params.add(tEncargosClte.getApellidoCliente());
		params.add(tEncargosClte.getTelefonoCliente());
		params.add(tEncargosClte.getTipoEncargo());
		params.add(tEncargosClte.getFechaVenta());
		/***
		 * Añado fechaVentaModificada
		 * @author BICUGUAL
		 */
		params.add(tEncargosClte.getFechaVentaModificada());
		
		params.add(tEncargosClte.getFechaInferior());
		params.add(tEncargosClte.getEspecificacion());
		params.add(tEncargosClte.getPesoDesde());
		params.add(tEncargosClte.getPesoHasta());
		params.add(tEncargosClte.getIddsesion());
		params.add(tEncargosClte.getCentro());
		params.add(tEncargosClte.getReferencia());
		params.add(tEncargosClte.getLocalizador());
		
		
		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	}

    @Override
	public void updateErrores(TEncargosClte tEncargosClte) throws Exception{
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE T_ENCARGOS_CLTE SET CODIGO_ERROR = ?, DESCRIPCION_ERROR = ? ");
		query.append(" WHERE IDDSESION = ? AND CENTRO = ?");

		params.add(null);
		params.add(null);
		params.add(tEncargosClte.getIddsesion());
		params.add(tEncargosClte.getCentro());

		
	
		try {

			this.jdbcTemplate.update(query.toString(), params.toArray());
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	}

    @Override
	public void insertAll(List<TEncargosClte> listaTEncargosClte) throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("INSERT INTO T_ENCARGOS_CLTE (LOCALIZADOR, CENTRO, AREA, SECCION, CATEGORIA, SUBCATEGORIA, ");
		query.append(" SEGMENTO, REFERENCIA, REFERENCIA_MADRE, DESCRIPCION, UNIDADESCAJA, IDDSESION, CONTACTO_CENTRO, TELEFONO_CENTRO, NOMBRE_CLIENTE, ");
		query.append(" APELLIDO_CLIENTE, TELEFONO_CLIENTE, FECHA_HORA_ENCARGO, TIPO_ENCARGO, FECHA_VENTA, FECHA_VENTA_MODIFICADA, FECHA_INFERIOR, ");
		query.append(" ESPECIFICACION, PESO_DESDE, PESO_HASTA, CONFIRMAR_ESPECIFICACIONES, FALTA_REF, CAMBIO_REF, CONFIRMAR_PRECIO, CANT_ENCARGO, ");
		query.append(" CANT_FINAL_COMPRA, CANT_SERVIDO, CANT_NO_SERVIDO, ESTADO, OBSERVACIONES_MISUMI, CODIGO_PEDIDO_INTERNO, FLG_MODIFICABLE, ");
		query.append(" COD_TP_APROV, VITRINA, REL_COMPRA_VENTA, CODIGO_ERROR, DESCRIPCION_ERROR, FECHACREACION, ");
		query.append(" DESCRIPCION_GESTADIC, ESTADO_GESTADIC, TXT_DETALLE_GESTADIC, TXT_SITUACION_GESTADIC, FLG_PER_ESPEC, COD_ART_GRID, DESCRIP_ART_GRID ) ");
		query.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		if (listaTEncargosClte != null && listaTEncargosClte.size()>0)
		{
			TEncargosClte campo = new TEncargosClte();
			for (int i =0;i<listaTEncargosClte.size();i++)
			{
				campo = (TEncargosClte)listaTEncargosClte.get(i);
				
				params = new ArrayList<Object>();
				
				params.add(campo.getLocalizador());
				params.add(campo.getCentro());
				params.add(campo.getArea());
				params.add(campo.getSeccion());
				params.add(campo.getCategoria());
				params.add(campo.getSubcategoria());
				params.add(campo.getSegmento());
				params.add(campo.getReferencia());
				params.add(campo.getReferenciaMadre());
				params.add(campo.getDescripcion());
				params.add(campo.getUnidadescaja());
				params.add(campo.getIddsesion());
				params.add(campo.getContactoCentro());
				params.add(campo.getTelefonoCentro());
				params.add(campo.getNombreCliente());
				params.add(campo.getApellidoCliente());
				params.add(campo.getTelefonoCliente());
				params.add(campo.getFechaHoraEncargo());
				params.add(campo.getTipoEncargo());
				params.add(campo.getFechaVenta());
				params.add(campo.getFechaVentaModificada());
				params.add(campo.getFechaInferior());
				params.add(campo.getEspecificacion());
				params.add(campo.getPesoDesde());
				params.add(campo.getPesoHasta());
				params.add(campo.getConfirmarEspecificaciones());
				params.add(campo.getFaltaRef());
				params.add(campo.getCambioRef());
				params.add(campo.getConfirmarPrecio());
				params.add(campo.getCantEncargo());
				params.add(campo.getCantFinalCompra());
				params.add(campo.getCantServido());
				params.add(campo.getCantNoServido());
				params.add(campo.getEstado());
				params.add(campo.getObservacionesMisumi());
				params.add(campo.getCodigoPedidoInterno());
				params.add(campo.getFlgModificable());
				params.add(campo.getCodTpAprov());
				params.add(campo.getVitrina());
				params.add(campo.getRelCompraVenta());
				params.add(campo.getCodigoError());
				params.add(campo.getDescripcionError());
				params.add(campo.getFechacreacion());
				params.add(campo.getDescripcionGestadic());
				params.add(campo.getEstadoGestadic());
				params.add(campo.getTxtDetalleGestadic());
				params.add(campo.getTxtSituacionGestadic());
				params.add(campo.getFlgEspec());
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
    public List<TEncargosClte> findAll(TEncargosClte tEncargosClte) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer(
    			"   SELECT localizador, centro, area, seccion, categoria, subcategoria, segmento, " +
    		    "       referencia, referencia_madre, descripcion, unidadescaja, iddsesion, " + 
    		    "       contacto_centro, telefono_centro, nombre_cliente, apellido_cliente, " +
    		    "       telefono_cliente, fecha_hora_encargo, tipo_encargo, fecha_venta, " +
    		    "       fecha_venta_modificada, fecha_inferior, especificacion, peso_desde, " +
    		    "       peso_hasta, confirmar_especificaciones, falta_ref, cambio_ref, " +
    		    "       confirmar_precio, cant_encargo, cant_final_compra, cant_servido, " +
    		    "       cant_no_servido, estado, observaciones_misumi, " +
    		    "       codigo_pedido_interno, flg_modificable, cod_tp_aprov, " +
    		    "       vitrina, rel_compra_venta, codigo_error, descripcion_error, " +
    		    "       fechacreacion, nivel, descripcion_gestadic, estado_gestadic, " +
    		    "       txt_detalle_gestadic, txt_situacion_gestadic, COD_ART_GRID, DESCRIP_ART_GRID "  +   
    		    "   FROM V_CONSULTA_ENCARGOS_CLTE_N1 " +
    	        " 	WHERE IDDSESION = '" + tEncargosClte.getIddsesion() + "' ");
        
        StringBuffer order = new StringBuffer(3000);
        if (tEncargosClte.getOrderBy() != null && !tEncargosClte.getOrderBy().equals(""))
        {
        	order.append(" order by ");
        	if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("ESTADO")))
        	{
        		order.append("ESTADO");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CODARTFORMLOG")))
        	{
        		order.append("NVL(");
        		order.append("REFERENCIA");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("DENOMARTICULO")))
        	{
        		order.append("DESCRIPCION");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("FECHAVENTA")))
        	{
        		order.append("FECHA_VENTA_MODIFICADA");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CANTENCARGO")))
        	{
        		order.append("NVL(");
        		order.append("CANT_ENCARGO");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CANTFINALCOMPRA")))
        	{
        		order.append("NVL(");
        		order.append("CANT_FINAL_COMPRA");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CANTSERVIDO")))
        	{
        		order.append("NVL(");
        		order.append("CANT_SERVIDO");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CANTNOSERVIDO")))
        	{
        		order.append("NVL(");
        		order.append("CANT_NO_SERVIDO");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("PESODESDE")))
        	{
        		order.append("NVL(");
        		order.append("PESO_DESDE");
        		order.append(",'0')");
        		
        		if (tEncargosClte.getOrderBy() != null && !tEncargosClte.getOrderBy().equals("") && !tEncargosClte.getOrderBy().trim().toUpperCase().equals("MENSAJE"))
                {
        			order.append(" ");
        			order.append(tEncargosClte.getSortOrder());
                }

        		order.append(",");
        		order.append("NVL(");
        		order.append("PESO_HASTA");
        		order.append(",'0')");
        		
        		if (tEncargosClte.getOrderBy() != null && !tEncargosClte.getOrderBy().equals("") && !tEncargosClte.getOrderBy().trim().toUpperCase().equals("MENSAJE"))
                {
        			order.append(" ");
        			order.append(tEncargosClte.getSortOrder());
                }

        		order.append(",");
        		order.append("ESPECIFICACION");
        		
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("NOMBRECLIENTE")))
        	{
        		order.append("NOMBRE_CLIENTE");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("CONFIRMARPRECIO")))
        	{
        		order.append("CONFIRMAR_PRECIO");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("LOCALIZADOR")))
        	{
        		order.append("NVL(");
        		order.append("LOCALIZADOR");
        		order.append(",'0')");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("ESTADOGESTADIC")))
        	{
        		//Se establece la ordenación del campo ESTADO_GESTADIC teniendo en cuenta el siguiente orden: 'PDTE', OK o KO, nulos
        		order.append("DECODE(NVL(ESTADO_GESTADIC,''),'PDTE',0,'OK',1,'KO',1,'FIN',1,2)");
        		order.append(" ");
    			order.append(tEncargosClte.getSortOrder());
    			order.append(", ");
        		order.append("REFERENCIA");
        	}
        	else if ((tEncargosClte.getOrderBy().trim().toUpperCase().equals("DESCRIPTIONARTGRID")))
        	{
        		order.append("DESCRIP_ART_GRID");
        	}
        	else
        	{	
        		order.append(tEncargosClte.getOrderBy());
        	}

        	if (tEncargosClte.getOrderBy() != null && !tEncargosClte.getOrderBy().equals("") && !tEncargosClte.getOrderBy().trim().toUpperCase().equals("MENSAJE")
        			&& !tEncargosClte.getOrderBy().trim().toUpperCase().equals("ESTADOGESTADIC"))
            {
        		order.append(" ");
        		order.append(tEncargosClte.getSortOrder());
            }
        }
        else
        {
        	order.append(" ORDER BY DECODE(ESTADO,'PEND',1,'PENDIENTE',2,3), FECHA_VENTA_MODIFICADA, REFERENCIA");
        }
		query.append(order);
		
		List<TEncargosClte> tEncargosClteLista = null;		
		
		try {

			tEncargosClteLista = (List<TEncargosClte>) this.jdbcTemplate.query(query.toString(),this.rwTEncargosClteMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}

	    return tEncargosClteLista;
    }
    
    @Override
    public List<TEncargosClte> findAllDetalle(TEncargosClte tEncargosClte) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer("SELECT " +
    			" 	LOCALIZADOR , " + 
    			" 	CENTRO           ,  AREA             ,  SECCION          , " +
    			" 	CATEGORIA        ,  SUBCATEGORIA     ,  SEGMENTO         , " +
    			" 	REFERENCIA       ,  REFERENCIA_MADRE , " +
    			" 	DESCRIPCION      ,  UNIDADESCAJA     ,  IDDSESION        , " +
    			" 	CONTACTO_CENTRO  ,  TELEFONO_CENTRO  , " +
    			" 	NOMBRE_CLIENTE   ,  APELLIDO_CLIENTE ,  TELEFONO_CLIENTE , " +
    			" 	FECHA_HORA_ENCARGO,  TIPO_ENCARGO      , " +
    			" 	FECHA_VENTA       ,  FECHA_VENTA_MODIFICADA,  FECHA_INFERIOR        , " +
    			" 	ESPECIFICACION        , " + //  -- NO SE MUESTRA
    			" 	PESO_DESDE            ,  PESO_HASTA            , " +
    			" 	CONFIRMAR_ESPECIFICACIONES, " +
    			" 	FALTA_REF                 ,  CAMBIO_REF                , " +
    			" 	CONFIRMAR_PRECIO          , " +
    			" 	CANT_ENCARGO,  CANT_FINAL_COMPRA         ,  CANT_SERVIDO              ,  CANT_NO_SERVIDO           , " +
    			" 	ESTADO                    , " +
    			" 	OBSERVACIONES_MISUMI      , " +
    			" 	CODIGO_PEDIDO_INTERNO     , " +
    			" 	FLG_MODIFICABLE           , " +
    			" 	COD_TP_APROV              ,  VITRINA                   ,  REL_COMPRA_VENTA            , " +
    			" 	CODIGO_ERROR, DESCRIPCION_ERROR, 2 NIVEL, FECHACREACION, DESCRIPCION_GESTADIC, ESTADO_GESTADIC, TXT_DETALLE_GESTADIC, TXT_SITUACION_GESTADIC, COD_ART_GRID, DESCRIP_ART_GRID " +
    			" FROM T_ENCARGOS_CLTE " +
    			" WHERE IDDSESION = '" + tEncargosClte.getIddsesion() + "' " +
    			" 	AND CODIGO_PEDIDO_INTERNO =  " + tEncargosClte.getCodigoPedidoInterno() +
    			" ORDER BY FECHA_VENTA_MODIFICADA, REFERENCIA, LOCALIZADOR ");

		List<TEncargosClte> tEncargosClteLista = null;		
	
		try {
			tEncargosClteLista = (List<TEncargosClte>) this.jdbcTemplate.query(query.toString(),this.rwTEncargosClteMap, params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    return tEncargosClteLista;
    }

    @Override
    public TEncargosClte findOne(TEncargosClte tEncargosClte) throws Exception  {
    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	where.append("WHERE 1=1 ");

    	StringBuffer query = new StringBuffer("SELECT * FROM ( SELECT " +
    			" 	LOCALIZADOR , " + 
    			" 	CENTRO           ,  AREA             ,  SECCION          , " +
    			" 	CATEGORIA        ,  SUBCATEGORIA     ,  SEGMENTO         , " +
    			" 	REFERENCIA       ,  REFERENCIA_MADRE , " +
    			" 	DESCRIPCION      ,  UNIDADESCAJA     ,  IDDSESION        , " +
    			" 	CONTACTO_CENTRO  ,  TELEFONO_CENTRO  , " +
    			" 	NOMBRE_CLIENTE   ,  APELLIDO_CLIENTE ,  TELEFONO_CLIENTE , " +
    			" 	FECHA_HORA_ENCARGO,  TIPO_ENCARGO      , " +
    			" 	FECHA_VENTA       ,  FECHA_VENTA_MODIFICADA,  FECHA_INFERIOR        , " +
    			" 	ESPECIFICACION        , " + 
    			" 	PESO_DESDE            ,  PESO_HASTA            , " +
    			" 	CONFIRMAR_ESPECIFICACIONES, " +
    			" 	FALTA_REF                 ,  CAMBIO_REF                , " +
    			" 	CONFIRMAR_PRECIO          , " +
    			" 	CANT_ENCARGO,  CANT_FINAL_COMPRA         ,  CANT_SERVIDO              ,  CANT_NO_SERVIDO           , " +
    			" 	ESTADO                    , " +
    			" 	OBSERVACIONES_MISUMI      , " +
    			" 	CODIGO_PEDIDO_INTERNO     , " +
    			" 	FLG_MODIFICABLE           , " +
    			" 	COD_TP_APROV              ,  VITRINA                   ,  REL_COMPRA_VENTA            , " +
    			" 	CODIGO_ERROR, DESCRIPCION_ERROR, 2 NIVEL, FECHACREACION, DESCRIPCION_GESTADIC, ESTADO_GESTADIC, TXT_DETALLE_GESTADIC, TXT_SITUACION_GESTADIC, COD_ART_GRID, DESCRIP_ART_GRID " +
    			" FROM T_ENCARGOS_CLTE " +
    			" WHERE IDDSESION = '" + tEncargosClte.getIddsesion() + "' " +
    			" 	AND LOCALIZADOR =  " + tEncargosClte.getLocalizador() +
    			" ORDER BY FECHA_VENTA_MODIFICADA, REFERENCIA, LOCALIZADOR ");

    	query.append(" ) WHERE ROWNUM = 1 ");
    	
    	List<TEncargosClte> tEncargosClteLista = null;	

    	try {
    		tEncargosClteLista = (List<TEncargosClte>) this.jdbcTemplate.query(query.toString(),this.rwTEncargosClteMap, params.toArray());  
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
    	TEncargosClte tEncargosClteRes = null;
		if (!tEncargosClteLista.isEmpty()){
			tEncargosClteRes = tEncargosClteLista.get(0);
		}

	    return tEncargosClteRes;
    }

    @Override
    public Long findAllCount(TEncargosClte tEncargosClte) throws Exception  {
    	List<Object> params = new ArrayList<Object>();
    	
    	StringBuffer query = new StringBuffer();
    	query.append("SELECT COUNT (*) ")
    	.append("FROM T_ENCARGOS_CLTE t ")
    	 .append("where t.iddsesion = ? ")
    	 .append("and t.CENTRO = ? ");
    	params.add(tEncargosClte.getIddsesion());
    	params.add(tEncargosClte.getCentro());

    	
        logger.debug(query);

	    Long cont = null;
	    try {
    		cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray()); 
			
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);
		}
	    return cont;
    }
}
