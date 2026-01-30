package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.VUltimoPedidoNsrDao;
import es.eroski.misumi.model.VArtSfm;
import es.eroski.misumi.util.Utilidades;
 	
@Repository
public class VUltimoPedidoNsrDaoImpl implements VUltimoPedidoNsrDao{
	
	 private JdbcTemplate jdbcTemplate;
	
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
		public List<VArtSfm> completarCampoNSR(List<VArtSfm> listaVArtSfm) throws Exception {
			
			   for (int i =0;i<listaVArtSfm.size();i++)
			   { 
				  if (this.countUltimoPedidoNSR(listaVArtSfm.get(i)).equals(new Long(1))) { //Existe el registros en la vista V_ULTIMO_PEDIDO_NSR
					  listaVArtSfm.get(i).setFlgNsr("S");
				  }
			   }

			return listaVArtSfm;
			
		}
		
		@Override
		public String obtenerUltimoPedidoNSR(VArtSfm vArtSfm) throws Exception {
			
			StringBuffer query = new StringBuffer(" SELECT v.CAJA_NSR FROM V_ULTIMO_PEDIDO_NSR v");
			StringBuffer where =  new StringBuffer(" WHERE 1=1 ");
			List<Object> params = new ArrayList<Object>();
			
			if (vArtSfm.getCodArticulo()!=null){
				where.append(" AND COD_ART = ? ");
				params.add(vArtSfm.getCodArticulo());	
			}
			
			if (vArtSfm.getCodLoc()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(vArtSfm.getCodLoc());	
			}
			
			query.append(where);
			
			String NSR= null;
			try {
				 NSR= this.jdbcTemplate.queryForObject(query.toString(),String.class,params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
			return NSR;	

		}
		
		@Override
		public Long countUltimoPedidoNSR(VArtSfm vArtSfm) throws Exception {
			
			StringBuffer query = new StringBuffer(" SELECT count(1) FROM V_ULTIMO_PEDIDO_NSR2 v");
			StringBuffer where =  new StringBuffer(" WHERE 1=1 ");
			List<Object> params = new ArrayList<Object>();
			
			if (vArtSfm.getCodArticulo()!=null){
				where.append(" AND COD_ART = TO_CHAR(?) ");
				params.add(vArtSfm.getCodArticulo());	
			}
			
			if (vArtSfm.getCodLoc()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(vArtSfm.getCodLoc());	
			}
			
			query.append(where);
			
			
			Long cont= null;
			try {
				cont= this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
			} catch (Exception e) {
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params, e);	
			}
			return cont;	
			

		}
		
		
}
