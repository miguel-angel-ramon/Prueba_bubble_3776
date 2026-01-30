package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ImpresionEtiquetasDao;
import es.eroski.misumi.model.ImpresionEtiquetas;
import es.eroski.misumi.util.Utilidades;


 	
@Repository
public class ImpresionEtiquetasDaoImpl implements ImpresionEtiquetasDao{
	   private JdbcTemplate jdbcTemplate;

	   private RowMapper<ImpresionEtiquetas> rwImpresionEtiquetasMap = new RowMapper<ImpresionEtiquetas>() {
			 public ImpresionEtiquetas mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				 return new ImpresionEtiquetas(resultSet.getLong("COD_CENTRO"),resultSet.getLong("COD_ART"),
				    			resultSet.getString("MAC"), resultSet.getLong("N_VECES"), 
				    			 resultSet.getDate("CREATION_DATE"),resultSet.getDate("LAST_UPDATE_DATE")
					    );
			}

		};
		    
	    @Autowired
	    public void setDataSource(DataSource dataSource) {
	    	this.jdbcTemplate = new JdbcTemplate(dataSource);
	    } 
	    
	    @Override
		public boolean existsReferencias(Long codCentro, String mac) {

			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(mac);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(1) "
					   + "FROM T_MIS_IMPRESION_ETIQUETAS "
					   + "WHERE COD_CENTRO = ? AND MAC = ?"
					    );
			
			int count = jdbcTemplate.queryForObject(query.toString(), params.toArray(), Integer.class);
			return (count > 0);
			
		}
	    
	    @Override
		public boolean existReferenciaInBBDD(Long codCentro, String mac,String codArt) {

			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(mac);
			params.add(codArt);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(1) "
					   + "FROM T_MIS_IMPRESION_ETIQUETAS "
					   + "WHERE COD_CENTRO = ? AND MAC = ? AND COD_ART=? "
					    );
			
			int count = jdbcTemplate.queryForObject(query.toString(), params.toArray(), Integer.class);
			return (count > 0);
			
		} 
	    
	    @Override
		public HashMap<String, Integer> getNumEtiquetasReferencias(Long codCentro, String mac) {

			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(mac);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT COD_CENTRO, COD_ART, MAC, N_VECES, CREATION_DATE, LAST_UPDATE_DATE "
					   + "FROM T_MIS_IMPRESION_ETIQUETAS "
					   + "WHERE COD_CENTRO = ? AND MAC = ?"
					    );
			List<ImpresionEtiquetas> numEtiquetasReferenciaLista = null;
			HashMap<String, Integer> mapNumEti = new HashMap<String, Integer>();
			try {
				
				numEtiquetasReferenciaLista = (List<ImpresionEtiquetas>) this.jdbcTemplate.query(query.toString(),this.rwImpresionEtiquetasMap, params.toArray()); 
				for(ImpresionEtiquetas impresionEtiquetas:numEtiquetasReferenciaLista){
					mapNumEti.put(impresionEtiquetas.getCodArt().toString(), impresionEtiquetas.getnVeces().intValue());
				}
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			return mapNumEti;
			
		}
	    
	    @Override
		public int getNumEtiquetaInBBDD(String codArt,Long codCentro, String mac) {

			List<Object> params = new ArrayList<Object>();
			params.add(codArt);
			params.add(codCentro);
			params.add(mac);
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT  N_VECES "
					   + "FROM T_MIS_IMPRESION_ETIQUETAS "
					   + "WHERE COD_ART=? AND COD_CENTRO = ? AND MAC = ?"
					    );
			int numEti=0;
			try {
				numEti = (int) this.jdbcTemplate.queryForInt(query.toString(),params.toArray()); 
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			return numEti;
		}
	    
	    @Override
		public void updateNumEtiquetasReferencias(Long codCentro,String codArt, String mac,int numVeces) {

			List<Object> params = new ArrayList<Object>();
			params.add(numVeces);
			params.add(codCentro);
			params.add(codArt);
			params.add(mac);
			
			StringBuilder query = new StringBuilder();
			query.append("UPDATE T_MIS_IMPRESION_ETIQUETAS SET N_VECES = ?, LAST_UPDATE_DATE = SYSDATE WHERE COD_CENTRO = ? AND COD_ART = ? AND MAC = ? ");
			try {
				this.jdbcTemplate.update(query.toString(),params.toArray()); 
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
		}
	    
	    @Override
		public void insertNumEtiquetasReferencias(Long codCentro,String codArt, String mac,int numVeces) {

			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(codArt);
			params.add(mac);
			params.add(numVeces);
			
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO T_MIS_IMPRESION_ETIQUETAS (COD_CENTRO,COD_ART,MAC,N_VECES) VALUES (?,?,?,?) ");
			try {
				this.jdbcTemplate.update(query.toString(),params.toArray()); 
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}
	    
	    @Override
		public void deleteNumEtiquetasReferencias(Long codCentro, String mac) {

			List<Object> params = new ArrayList<Object>();
			params.add(codCentro);
			params.add(mac);
			
			StringBuilder query = new StringBuilder();
			query.append("DELETE T_MIS_IMPRESION_ETIQUETAS WHERE COD_CENTRO = ? AND MAC = ? ");
			try {
				
				this.jdbcTemplate.update(query.toString(),params.toArray()); 
			} catch (Exception e){
				
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
			
		}
	    
}
