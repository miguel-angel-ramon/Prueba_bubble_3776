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

import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.model.EncargosReservasLista;
import es.eroski.misumi.model.PedidoBasicInfo;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.model.StockFinalMinimo;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.StackTraceManager;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class PedidosAdCentralDaoImpl implements PedidosAdCentralDao{
	 private JdbcTemplate jdbcTemplate;
	 
	 private static Logger logger = Logger.getLogger(PedidosAdCentralDaoImpl.class);
	 
	 //private static Logger logger = Logger.getLogger(VAgruComerParamSfmcapDaoImpl.class);
	 private RowMapper<PedidosAdCentral> rwPedidosAdCentralMap = new RowMapper<PedidosAdCentral>() {
			public PedidosAdCentral mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			    return new PedidosAdCentral(resultSet.getBigDecimal("COD_SECUENCIA").longValue(), resultSet.getLong("COD_CENTRO"), resultSet.getBigDecimal("COD_ART").longValue(),
			    		    resultSet.getString("DESCRIPCION"), resultSet.getLong("ANO_OFERTA"), resultSet.getBigDecimal("NUM_OFERTA")!=null?resultSet.getBigDecimal("NUM_OFERTA").longValue():null,
			    		    resultSet.getDate("FECHA_INICIO"), resultSet.getDate("FECHA_FIN"),
			    		    resultSet.getDouble("CANT1"), resultSet.getDouble("CANT2"), resultSet.getDouble("CANT3"),
			    		    resultSet.getDouble("CANT_MIN"), resultSet.getDouble("CANT_MAX"),
			    		    resultSet.getString("FLG_MODIFICAR"), resultSet.getString("FLG_ENVIO_PBL"),
			    		    resultSet.getString("FLG_ERRONEO"), resultSet.getString("FINALIZADO"),
			    		    resultSet.getDate("FECHA_GEN"), resultSet.getBigDecimal("IDENTIFICADOR").longValue(),
			    		    resultSet.getString("FLG_VALIDADO"), resultSet.getString("TIPO_PED"),
			    		    resultSet.getString("A_B_M"), resultSet.getBigDecimal("CREATED_BY").longValue(),
			    		    resultSet.getDate("CREATION_DATE"), resultSet.getBigDecimal("LAST_UPDATED_BY").longValue(),
			    		    resultSet.getDate("LAST_UPDATE_DATE"), resultSet.getBigDecimal("LAST_UPDATE_LOGIN").longValue(),
			    		    resultSet.getLong("TECLE"), resultSet.getBigDecimal("TCN").longValue(), resultSet.getDate("FECHA_HASTA"),
			    		    resultSet.getDouble("CANT1_CENTRO"), resultSet.getDouble("CANT2_CENTRO"), resultSet.getDouble("CANT3_CENTRO"),
			    		    resultSet.getDouble("CANT4_CENTRO"), resultSet.getDouble("CANT5_CENTRO"), resultSet.getString("NO_GESTIONA_PBL"),
			    		    resultSet.getDate("FECHA2"),resultSet.getDate("FECHA3"),resultSet.getDate("FECHA4"),resultSet.getDate("FECHA5")
				    );
			}
		    };
		    
	
		   
	    @Autowired
	    public void setDataSource(DataSource dataSourceSIA) {
			this.jdbcTemplate = new JdbcTemplate(dataSourceSIA);
	    } 
	    

	    
	    
	    @Override
	    public List<PedidosAdCentral> findAll(PedidosAdCentral pedidosAdCentral) throws Exception  {
	    	StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
	    	List<Object> params = new ArrayList<Object>();
	    	where.append("WHERE 1=1 ");

	    	StringBuffer query = new StringBuffer(" SELECT COD_SECUENCIA, COD_CENTRO, COD_ART, DESCRIPCION, ANO_OFERTA, NUM_OFERTA, FECHA_INICIO, FECHA_FIN, CANT1, CANT2, CANT3, CANT_MIN, CANT_MAX, FLG_MODIFICAR, FLG_ENVIO_PBL, FLG_ERRONEO, FINALIZADO, FECHA_GEN, IDENTIFICADOR, FLG_VALIDADO, TIPO_PED, A_B_M, CREATED_BY, CREATION_DATE,LAST_UPDATED_BY,LAST_UPDATE_DATE, LAST_UPDATE_LOGIN, TECLE, TCN, FECHA_HASTA, CANT1_CENTRO, CANT2_CENTRO, CANT3_CENTRO, CANT4_CENTRO, CANT5_CENTRO, NULL NO_GESTIONA_PBL, NULL FECHA2, NULL FECHA3, NULL FECHA4, NULL FECHA5 " 
	    										+ " FROM PEDIDOS_AD_CENTRAL ");
	    
	        if (pedidosAdCentral  != null){
	        	if(pedidosAdCentral.getCodSecuencia()!=null){
	        		 where.append(" AND COD_SECUENCIA = ? ");
		        	 params.add(pedidosAdCentral.getCodSecuencia());	        		
	        	}
	        	if(pedidosAdCentral.getCodCentro()!=null){
	        		 where.append(" AND COD_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCodCentro());	        		
	        	}
	        	if(pedidosAdCentral.getCodArt()!=null){
	        		 where.append(" AND COD_ART = ? ");
		        	 params.add(pedidosAdCentral.getCodArt());	        		
	        	}
	        	if(pedidosAdCentral.getDescripcion()!=null){
	        		 where.append(" AND DESCRIPCION = upper(?) ");
		        	 params.add(pedidosAdCentral.getDescripcion());	        		
	        	}
	        	if(pedidosAdCentral.getAnoOferta()!=null){
	        		 where.append(" AND ANO_OFERTA = ? ");
		        	 params.add(pedidosAdCentral.getAnoOferta());	        		
	        	}
	        	if(pedidosAdCentral.getNumOferta()!=null){
	        		 where.append(" AND NUM_OFERTA = ? ");
		        	 params.add(pedidosAdCentral.getNumOferta());	        		
	        	}
	        	if(pedidosAdCentral.getFechaInicio()!=null){
	        		 where.append(" AND FECHA_INICIO = TRUNC(?) ");
		        	 params.add(pedidosAdCentral.getFechaInicio());	        		
	        	}
	        	if(pedidosAdCentral.getFechaFin()!=null){
	        		 where.append(" AND FECHA_FIN = TRUNC(?) ");
		        	 params.add(pedidosAdCentral.getFechaFin());	        		
	        	}
	        	if(pedidosAdCentral.getCant1()!=null){
	        		 where.append(" AND CANT1 = ? ");
		        	 params.add(pedidosAdCentral.getCant1());	        		
	        	}
	        	if(pedidosAdCentral.getCant2()!=null){
	        		 where.append(" AND CANT2 = ? ");
		        	 params.add(pedidosAdCentral.getCant2());	        		
	        	}
	        	if(pedidosAdCentral.getCant3()!=null){
	        		 where.append(" AND CANT3 = ? ");
		        	 params.add(pedidosAdCentral.getCant3());	        		
	        	}
	        	if(pedidosAdCentral.getCantMin()!=null){
	        		 where.append(" AND CANT_MIN = ? ");
		        	 params.add(pedidosAdCentral.getCantMin());	        		
	        	}
	        	if(pedidosAdCentral.getCantMax()!=null){
	        		 where.append(" AND CANT_MAX = ? ");
		        	 params.add(pedidosAdCentral.getCantMax());	        		
	        	}
	        	if(pedidosAdCentral.getFlgModificar()!=null){
	        		 where.append(" AND FLG_MODIFICAR = upper(?) ");
		        	 params.add(pedidosAdCentral.getFlgModificar());	        		
	        	}
	        	if(pedidosAdCentral.getFlgEnvioPbl()!=null){
	        		 where.append(" AND FLG_ENVIO_PBL = upper(?) ");
		        	 params.add(pedidosAdCentral.getFlgEnvioPbl());	        		
	        	}
	        	if(pedidosAdCentral.getFlgError()!=null){
	        		 where.append(" AND FLG_ERRONEO = upper(?) ");
		        	 params.add(pedidosAdCentral.getFlgError());	        		
	        	}
	        	if(pedidosAdCentral.getFinalizado()!=null){
	        		 where.append(" AND FINALIZADO = upper(?) ");
		        	 params.add(pedidosAdCentral.getFinalizado());	        		
	        	}
	        	if(pedidosAdCentral.getFechaGen()!=null){
	        		 where.append(" AND FECHA_GEN = TRUNC(?) ");
		        	 params.add(pedidosAdCentral.getFechaGen());	        		
	        	}
	        	if(pedidosAdCentral.getIdentificador()!=null){
	        		 where.append(" AND IDENTIFICADOR = ? ");
		        	 params.add(pedidosAdCentral.getIdentificador());	        		
	        	}
	        	if(pedidosAdCentral.getFlgValidado()!=null){
	        		 where.append(" AND FLG_VALIDADO = upper(?) ");
		        	 params.add(pedidosAdCentral.getFlgValidado());	        		
	        	}
	        	if(pedidosAdCentral.getTipoPed()!=null){
	        		 where.append(" AND TIPO_PED = upper(?) ");
		        	 params.add(pedidosAdCentral.getTipoPed());	        		
	        	}
	        	if(pedidosAdCentral.getaBM()!=null){
	        		 where.append(" AND A_B_M = upper(?) ");
		        	 params.add(pedidosAdCentral.getaBM());	        		
	        	}
	        	if(pedidosAdCentral.getCreatedBy()!=null){
	        		 where.append(" AND CREATED_BY = ? ");
		        	 params.add(pedidosAdCentral.getCreatedBy());	        		
	        	}
	        	if(pedidosAdCentral.getCreationDate()!=null){
	        		 where.append(" AND CREATION_DATE = TRUNC(?) ");
		        	 params.add(pedidosAdCentral.getCreationDate());	        		
	        	}
	        	if(pedidosAdCentral.getLastUpdateLogin()!=null){
	        		 where.append(" AND LAST_UPDATE_LOGIN = ? ");
		        	 params.add(pedidosAdCentral.getLastUpdateLogin());	        		
	        	}
	        	if(pedidosAdCentral.getTecle()!=null){
	        		 where.append(" AND TECLE = ? ");
		        	 params.add(pedidosAdCentral.getTecle());	        		
	        	}
	        	if(pedidosAdCentral.getTcn()!=null){
	        		 where.append(" AND TCN = ? ");
		        	 params.add(pedidosAdCentral.getTcn());	        		
	        	}
	        	if(pedidosAdCentral.getFechaHasta()!=null){
	        		 where.append(" AND FECHA_HASTA = TRUNC(?) ");
		        	 params.add(pedidosAdCentral.getFechaHasta());	        		
	        	}
	        	if(pedidosAdCentral.getCant1_centro()!=null){
	        		 where.append(" AND CANT1_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCant1_centro());	        		
	        	}
	        	if(pedidosAdCentral.getCant2_centro()!=null){
	        		 where.append(" AND CANT2_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCant2_centro());	        		
	        	}
	        	if(pedidosAdCentral.getCant3_centro()!=null){
	        		 where.append(" AND CANT3_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCant3_centro());	        		
	        	}
	        	if(pedidosAdCentral.getCant4_centro()!=null){
	        		 where.append(" AND CANT4_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCant4_centro());	        		
	        	}
	        	if(pedidosAdCentral.getCant5_centro()!=null){
	        		 where.append(" AND CANT5_CENTRO = ? ");
		        	 params.add(pedidosAdCentral.getCant5_centro());	        		
	        	}
	        	if(pedidosAdCentral.getNoGestionaPbl()!=null){
	        		 where.append(" AND NO_GESTIONA_PBL = upper(?) ");
		        	 params.add(pedidosAdCentral.getNoGestionaPbl());	        		
	        	}
	        }
	        
	        query.append(where);
			StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			String campoOrdenacion = "IDENTIFICADOR";
			order.append(" order by " + campoOrdenacion + " asc ");	
			query.append(order);

			List<PedidosAdCentral> pedidosAdCentralLista = null;	
			try {
				pedidosAdCentralLista = (List<PedidosAdCentral>) this.jdbcTemplate.query(query.toString(),this.rwPedidosAdCentralMap, params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
		    return pedidosAdCentralLista;
	    }
	    
	   
	   
}
