package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.ListadoReposicionDao;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Paginate;
import es.eroski.misumi.util.Utilidades;


@Repository
public class ListadoReposicionDaoImpl implements ListadoReposicionDao {

	private JdbcTemplate jdbcTemplate;

	

	private static Logger logger = Logger.getLogger(DevolucionDaoImpl.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	
	
	
	private RowMapper<ReposicionLinea> rwDetPedidoMap= new RowMapper<ReposicionLinea>() {
		public ReposicionLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ReposicionLinea reposicionLinea = new ReposicionLinea();
			DecimalFormat df = new DecimalFormat("#0.00");
			reposicionLinea.setCodLoc(resultSet.getLong("COD_CENTRO"));
			reposicionLinea.setCodMac(resultSet.getString("MAC"));
			reposicionLinea.setModeloProveedor(resultSet.getString("MODELO_PROVEEDOR"));
			reposicionLinea.setDescrColor(resultSet.getString("COLOR"));
			reposicionLinea.setCodArticulo(resultSet.getLong("COD_ART"));
			double  numRepo = resultSet.getDouble("REPO");
			int p_entRepo= (int)numRepo;
			double p_decRepo= numRepo - p_entRepo;
			if(p_decRepo>0){
				String repo= df.format(numRepo).replace(",", ".");
				reposicionLinea.setCantRepo(repo);	
			}else{
				reposicionLinea.setCantRepo(String.valueOf(p_entRepo));
			}
			double  numStock = resultSet.getDouble("STOCK");
			int p_entStock= (int)numStock;
			double p_decStock= numStock - p_entStock;
			if(p_decStock>0){
				String stock= df.format(numStock).replace(",", ".");
				reposicionLinea.setStock(stock);	
			}else{
				reposicionLinea.setStock(String.valueOf(p_entStock));
			}
			reposicionLinea.setFlgPantCorrStock(resultSet.getString("FLG_PANT_CORR_STOCK"));
			reposicionLinea.setDescrTalla(resultSet.getString("TALLA"));
			reposicionLinea.setSubPosicion(resultSet.getLong("SUB_POSICION"));
			return reposicionLinea;
		}

	};
	

	
	@Override
	public void eliminarTempListadoRepo( String codMac) throws Exception{
	
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_MIS_LISTADO_REPO_TEMP WHERE MAC = ? ");
		params.add(codMac);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

	}
	

	
	
	@Override
	public void insertarTempListadoRepo(final Reposicion reposicion) throws Exception{

	
			String query = " INSERT INTO T_MIS_LISTADO_REPO_TEMP (COD_CENTRO, MAC, MODELO_PROVEEDOR, COLOR, COD_ART, REPO, STOCK, FLG_PANT_CORR_STOCK, TALLA, SUB_POSICION, AREA) "
							                          + " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
		
				
				final List <ReposicionLinea> reposicionLineas  = reposicion.getReposicionLineas();
				
				if (reposicionLineas != null && reposicionLineas.size()>0){
					jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
							throws SQLException {	        	
								ReposicionLinea myPojo = reposicionLineas.get(i);
						
								if (null != reposicion.getCodLoc()){
									ps.setLong(1,reposicion.getCodLoc());
								} else {
									ps.setNull(1, Types.NUMERIC);
								}
								
								ps.setString(2, reposicion.getCodMac());		
								ps.setString(3, reposicion.getModeloProveedor());	
								ps.setString(4, reposicion.getDescrColor());
								
								if (null != myPojo.getCodArticulo()){
									ps.setLong(5, myPojo.getCodArticulo());
								} else {
									ps.setNull(5, Types.NUMERIC);
								}
								
								if (null != myPojo.getCantRepo()){
									ps.setDouble(6,new Double(myPojo.getCantRepo()));
								} else {
									ps.setNull(6, Types.NUMERIC);
								}
								
								if (null != myPojo.getStock()){
									ps.setDouble(7,new Double(myPojo.getStock()));
								} else {
									ps.setNull(7, Types.NUMERIC);
								}
								
								ps.setString(8, myPojo.getFlgPantCorrStock());
								ps.setString(9, myPojo.getDescrTalla());
						
								if (null != myPojo.getSubPosicion()){
									ps.setLong(10, myPojo.getSubPosicion());
								} else {
									ps.setNull(10, Types.NUMERIC);
								}							
								ps.setString(11, reposicion.getArea());										
							}
	
							@Override
							public int getBatchSize() {
								return reposicionLineas.size();
							}
						});
					}

	}
	
	
	
	
	@Override
	public List<ReposicionLinea> findTempListadoRepo(Reposicion reposicion, Pagination pagination) throws Exception{

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");
	
		StringBuffer query = new StringBuffer(" SELECT COD_CENTRO, MAC, MODELO_PROVEEDOR, COLOR, COD_ART, REPO, STOCK,FLG_PANT_CORR_STOCK, TALLA, SUB_POSICION " +
				" FROM T_MIS_LISTADO_REPO_TEMP ");


		if (reposicion  != null){
			if(reposicion.getCodLoc()!=null){
				where.append(" AND COD_CENTRO = ? ");
				params.add(reposicion.getCodLoc());	        		
			}
			if(reposicion.getCodMac()!=null){
				where.append(" AND MAC = ? ");
				params.add(reposicion.getCodMac());	        		
			}
			if(reposicion.getModeloProveedor()!=null){
				where.append(" AND MODELO_PROVEEDOR = ? ");
				params.add(reposicion.getModeloProveedor());	        		
			}
			if(reposicion.getDescrColor()!=null){
				where.append(" AND COLOR = ? ");
				params.add(reposicion.getDescrColor());	        		
			}
			if(reposicion.getCodArt()!=null){
				where.append(" AND COD_ART = ? ");
				params.add(reposicion.getCodArt());	        		
			}
			if(reposicion.getArea()!=null){
				where.append(" AND AREA = ? ");
				params.add(reposicion.getArea());	   
			}			
		}

		query.append(where);
		
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		order.append(" ORDER BY SUB_POSICION");
		query.append(order);
		
		
		if (pagination != null) {
			query = new StringBuffer(Paginate.getQueryLimits(pagination, query.toString()));
		}  

		List<ReposicionLinea> lista = null;		
		try {

			lista =  (List<ReposicionLinea>) this.jdbcTemplate.query(query.toString(),this.rwDetPedidoMap, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return lista;

	}
	
	
	
	@Override
	public Long countTempListadoRepo(String codMac) throws Exception{

		StringBuffer query = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();


		query.append("SELECT COUNT(*) ");

		query.append(" FROM T_MIS_LISTADO_REPO_TEMP R ");
		query.append(" WHERE R.MAC = ? ");
	
		params.add(codMac);

		Long cont = null;

		try {

			cont = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return cont;


	}
	
	


}
