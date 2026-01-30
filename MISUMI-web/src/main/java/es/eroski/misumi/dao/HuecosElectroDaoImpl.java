/**
 * 
 */
package es.eroski.misumi.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.HuecosElectroDao;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

/**
 * P-50987
 * @author BICUGUAL
 */
@Repository
public class HuecosElectroDaoImpl implements HuecosElectroDao {
	 private JdbcTemplate jdbcTemplate;
    
	 @Autowired
	 public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }

	@Override
	public Integer getHuecosFinalSegByCodCentro(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3,
			Long codGrupo4, Long codGrupo5) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	StringBuffer query = 
    			new StringBuffer(" SELECT HUECOS_FINAL_SEG FROM HUECOS_PLANO_TIENDA "
    							+ " WHERE ROWNUM=1 AND COD_CENTRO = ? ");
    	params.add(codCentro);
    			
    	if (codGrupo1!=null)  {
    		where.append(" AND GRUPO1 = ? ");
    		params.add(codGrupo1);
    	}
    	
    	if (codGrupo2!=null)  {
    		where.append(" AND GRUPO2 = ? ");
    		params.add(codGrupo2);
    	}
    	
    	if (codGrupo3!=null)  {
    		where.append(" AND GRUPO3 = ? ");
    		params.add(codGrupo3);
    	}
    	
    	if (codGrupo4!=null)  {
    		where.append(" AND GRUPO4 = ? ");
    		params.add(codGrupo4);
    	}
    	
    	if (codGrupo5!=null)  {
    		where.append(" AND GRUPO5 = ? ");
    		params.add(codGrupo5);
    	}
    		
   		query.append(where);
    	
	    
	    Integer Huecos = null;
	    try {
	    	List<Integer> listaHuecos =  (List<Integer>)this.jdbcTemplate.queryForList(query.toString(), Integer.class, params.toArray()); 
	    	if(listaHuecos != null && listaHuecos.size() > 0){
				Huecos = listaHuecos.get(0);
			}
		} catch (Exception e){
			
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	    
	    return Huecos;
		
	} 
	
	
	
	@Override
	public Integer getHuecosFinalSubCatByCodCentro(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3,
			Long codGrupo4) throws Exception {
		
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
    	List<Object> params = new ArrayList<Object>();
    	StringBuffer query = 
    			new StringBuffer(" SELECT HUECOS_FINAL_SCAT FROM HUECOS_PLANO_TIENDA "
    							+ " WHERE ROWNUM=1 AND COD_CENTRO = ? ");
    	params.add(codCentro);
    			
    	if (codGrupo1!=null)  {
    		where.append(" AND GRUPO1 = ? ");
    		params.add(codGrupo1);
    	}
    	
    	if (codGrupo2!=null)  {
    		where.append(" AND GRUPO2 = ? ");
    		params.add(codGrupo2);
    	}
    	
    	if (codGrupo3!=null)  {
    		where.append(" AND GRUPO3 = ? ");
    		params.add(codGrupo3);
    	}
    	
    	if (codGrupo4!=null)  {
    		where.append(" AND GRUPO4 = ? ");
    		params.add(codGrupo4);
    	}
    	
    	
    		
   		query.append(where);
	    
	    Integer Huecos = null;
	    try {
	    	List<Integer> listaHuecos =  (List<Integer>)this.jdbcTemplate.queryForList(query.toString(), Integer.class, params.toArray()); 
	    	if(listaHuecos != null && listaHuecos.size() > 0){
				Huecos = listaHuecos.get(0);
			}
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			e.printStackTrace();
		}
	    
	    return Huecos;
		
	} 
	
	

}
