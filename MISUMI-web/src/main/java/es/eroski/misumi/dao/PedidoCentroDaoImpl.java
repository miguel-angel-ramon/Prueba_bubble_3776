package es.eroski.misumi.dao;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.PedidoCentroDao;
import es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWS;
import es.eroski.misumi.dao.pedidosCentroWS.PedidosCentroWSServiceLocator;
import es.eroski.misumi.model.ParamCentrosOpc;
import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.PedidoBasicInfo;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasRequestType;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.LogHandler;
import es.eroski.misumi.util.Utilidades;

 	
@Repository

public class PedidoCentroDaoImpl implements PedidoCentroDao{
	
	 @Value( "${ws.pedidosCentro}" )
	 private String pedidosCentroWsdl;

	 private JdbcTemplate jdbcTemplate;
	  
	 private RowMapper<PedidoBasicInfo> rwPedidoBasicMap = new RowMapper<PedidoBasicInfo>() {
			public PedidoBasicInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new PedidoBasicInfo(    	 
			    		resultSet.getDate("FECHA_PED"),resultSet.getString("CONFIRMADAS"),
			    			resultSet.getString("EMPUJE"), resultSet.getString("IMP_CAB"), 
			    			resultSet.getString("NSR"),resultSet.getString("INTERTIENDA"),
			    			resultSet.getDouble("CAJA_NORMAL"),resultSet.getDouble("CAJA_EMPUJE"),
			    			resultSet.getDouble("CAJA_IMPL"),resultSet.getDouble("CAJA_NSR"),
			    			resultSet.getDouble("CAJA_INTERTIENDA"),resultSet.getString("FORMATO")
				    );
			}

	};
		    
	 @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	 } 
	    

	@Override
	 public ValidarReferenciasResponseType findPedidosCentroWS(ReferenciasCentro vReferenciasCentro) throws Exception{
		// TODO Auto-generated method stub
		BigDecimal[] refArray= new BigDecimal[1];
		refArray[0]= new BigDecimal(vReferenciasCentro.getCodArt());
		PedidosCentroWSServiceLocator locator = new PedidosCentroWSServiceLocator();
		HandlerRegistry handlerRegistry = locator.getHandlerRegistry();
		
		 QName qname = new QName("http://webservice.gcnp.eroski.es/", "PedidosCentroWSPort");
		 List chain = handlerRegistry.getHandlerChain(qname);
		 HandlerInfo info = new HandlerInfo();
		 info.setHandlerClass(LogHandler.class);        
		 chain.add(info);
		 

		 try {
			 URL address = new URL(this.pedidosCentroWsdl);
		 	 ValidarReferenciasRequestType argument = new ValidarReferenciasRequestType(new BigDecimal(vReferenciasCentro.getCodCentro()),refArray);
			 PedidosCentroWS pedidosCentroWSProxy = locator.getPedidosCentroWSPort(address);
			 ValidarReferenciasResponseType resultado = pedidosCentroWSProxy.validarReferencias(argument);
			 return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			
			ValidarReferenciasResponseType resultado = new ValidarReferenciasResponseType();
			resultado.setCodigoRespuesta("1");
			return resultado;
		}
	}	  
	
	@Override
	public Pedido findAllUltimosEnvios(Pedido pedido, List<Long> listaReferencias) throws Exception {
		Pedido auxiliarPedido = new Pedido();
		StringBuffer referencias = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	referencias.append(pedido.getArticulo().getCodArt());
    	if (null != listaReferencias){
    		for(Long referencia : listaReferencias){
    			referencias.append(", ").append(referencia);
    		}
    	}
    	
    	StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	
    	query.append("SELECT c.*, NVL((SELECT 'FFPP' "
    								+ "FROM relacion_articulo r "
    								+ "WHERE r.cod_centro 	= c.cod_centro "
    								+ "AND r.tipo_rela 		= 2 "
    								+ "AND r.cod_art_rela 	= c.cod_art "
    								+ "AND r.fecha_gen 		= (SELECT MAX(r2.fecha_gen) "
    														+ "FROM relacion_articulo r2 "
    														+ "WHERE (r2.cod_centro = r.cod_centro "
    															   + "OR (r.cod_centro IS NULL AND r2.cod_centro IS NULL)"
    															  + ") "
    														+ "AND r2.cod_art 		= r.cod_art "
    														+ "AND r2.tipo_rela 	= r.tipo_rela "
    														+ "AND r2.cod_art_rela 	= r.cod_art_rela"
    														+ ")"
    							  + "), 'cajas') formato "
    				+ "FROM (SELECT MAX(b.vm_cod_centro) cod_centro, MAX(b.vm_fecha_ped) fecha_ped, MAX(b.vm_cod_art) cod_art"
    							+ ", MAX(b.vm_confirmadas) confirmadas"
    							+ ", MAX(b.vm_empuje) empuje"
    							+ ", MAX(b.vm_imp_cab) imp_cab"
    							+ ", MAX(b.vm_nsr) nsr"
    							+ ", MAX(b.vm_intertienda) intertienda"
    							+ ", SUM(caja_normal) caja_normal"
    							+ ", SUM(caja_empuje) caja_empuje"
    							+ ", SUM(caja_impl) caja_impl"
    							+ ", SUM(caja_nsr) caja_nsr"
    							+ ", SUM(caja_intertienda) caja_intertienda "
    					  + "FROM ( SELECT a.cod_centro vm_cod_centro, a.fecha_ped vm_fecha_ped, a.cod_art vm_cod_art"
    					  			  + ", NVL(a.confirmadas, 'N') vm_confirmadas"
    					  			  + ", NVL(a.empuje, 'N') vm_empuje"
    					  			  + ", NVL(a.imp_cab, 'N') vm_imp_cab"
    					  			  + ", NVL(a.nsr, 'N') vm_nsr"
    					  			  + ", NVL(a.intertienda, 'N') vm_intertienda"
    					  			  + ", caja_nsr"
    					  			  + ", caja_normal"
    					  			  + ", caja_empuje"
    					  			  + ", caja_impl"
    					  			  + ", caja_intertienda "
    					  		+ "FROM v_informacion_basica_pedido a "
    					  		+ "WHERE a.cod_centro = ? "
    					  		+ "AND a.cod_art IN (").append(referencias).append(") "
    					  	+ ") b "
    			  + "WHERE ROWNUM <= 5 "
    			  + "GROUP BY b.vm_fecha_ped "
    			  + "ORDER BY b.vm_fecha_ped DESC"
    		+ ") c "
    		+ "ORDER BY fecha_ped DESC"
    		);   

    	params.add(pedido.getArticulo().getCentro().getCodCentro());

        List<PedidoBasicInfo>  auxiliarPedidoBasic = null; 
        try {
        	auxiliarPedidoBasic = (List<PedidoBasicInfo>) this.jdbcTemplate.query(query.toString(),this.rwPedidoBasicMap, params.toArray()); 
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
        
        auxiliarPedido.setArticulo(pedido.getArticulo());
        auxiliarPedido.setBasicInfo(auxiliarPedidoBasic);

    	return auxiliarPedido;
    }
}


