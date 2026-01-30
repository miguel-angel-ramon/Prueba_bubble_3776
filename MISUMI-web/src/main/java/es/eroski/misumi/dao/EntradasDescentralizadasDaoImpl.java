package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.EntradasDescentralizadasDao;
import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.EntradaLineaModificada;
import es.eroski.misumi.model.TEntradaLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class EntradasDescentralizadasDaoImpl implements EntradasDescentralizadasDao{
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<TEntradaLinea> rwTEntradaLinea = new RowMapper<TEntradaLinea>() {
		public TEntradaLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TEntradaLinea tEntradaLinea = new TEntradaLinea(
					resultSet.getString("IDSESION"),
					resultSet.getDate("CREATION_DATE"),
					resultSet.getLong("COD_CAB_PEDIDO"),
					resultSet.getLong("COD_ART"),
					resultSet.getString("DENOM_COD_ART"),
					resultSet.getLong("NUMERO_CAJAS_PEDIDAS"),
					resultSet.getLong("NUMERO_CAJAS_RECEP"),
					resultSet.getLong("UC"),
					resultSet.getLong("TOTAL_BANDEJAS_PEDIDAS"),
					resultSet.getLong("TOTAL_BANDEJAS_RECEP"),
					resultSet.getDouble("TOTAL_UNIDADES_PEDIDAS"),
					resultSet.getDouble("TOTAL_UNIDADES_RECEP"),
					resultSet.getString("FLG_FOTO"),
					resultSet.getLong("COD_ERROR"),
					resultSet.getString("DESC_ERROR"),
					resultSet.getLong("NUMERO_CAJAS_RECEP_ORI"),
					resultSet.getLong("TOTAL_BANDEJAS_RECEP_ORI"),
					resultSet.getDouble("TOTAL_UNIDADES_RECEP_ORI"));	
			return tEntradaLinea;
		}
	};
	
	private RowMapper<EntradaLinea> rwEntradaLinea = new RowMapper<EntradaLinea>() {
		public EntradaLinea mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			EntradaLinea entradaLinea = new EntradaLinea(
					resultSet.getLong("COD_ART"),
					resultSet.getString("DENOM_COD_ART"),
					resultSet.getLong("NUMERO_CAJAS_PEDIDAS"),
					resultSet.getLong("NUMERO_CAJAS_RECEP"),
					resultSet.getLong("UC"),
					resultSet.getLong("TOTAL_BANDEJAS_PEDIDAS"),
					resultSet.getLong("TOTAL_BANDEJAS_RECEP"),
					resultSet.getDouble("TOTAL_UNIDADES_PEDIDAS"),
					resultSet.getDouble("TOTAL_UNIDADES_RECEP"),
					resultSet.getLong("COD_ERROR"),
					resultSet.getString("DESC_ERROR"));	
			return entradaLinea;
		}
	};

	@Override
	public List<TEntradaLinea> findLineasEntrada(String idSesion, Entrada entrada, Pagination pagination) {
		// TODO Auto-generated method stub

		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT * " 
				+ " FROM  T_MIS_ENTRADA_LINEAS ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND COD_CAB_PEDIDO = ? ");

		params.add(idSesion);
		params.add(entrada.getCodCabPedido());  

		query.append(where);

		//Se añade un order by según la columna clicada para la ordenación. Si no hay ordenación por columna (como cuando cargas la 1 vez el grid)
		//se ordena por COD_ART
		StringBuffer order = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		if (pagination != null) {
			if (pagination.getSort() != null && !(pagination.getSort().equals(""))) {
				order.append(" order by " + this.getMappedField(pagination.getSort()) + " "
						+ pagination.getAscDsc());
				query.append(order);
			} else {
				order.append(" order by COD_ART");
				query.append(order);
			}
		}else{
			order.append(" order by COD_ART");
			query.append(order);
		}    	       	       
		List<TEntradaLinea> lista = null;
		try {
			lista = (List<TEntradaLinea>) this.jdbcTemplate.query(query.toString(),this.rwTEntradaLinea, params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public void deleteHistorico() throws Exception {
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_MIS_ENTRADA_LINEAS WHERE CREATION_DATE < (SYSDATE - ?) ");

		params.add(Constantes.DIAS_ELIMINAR);
		try{
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void delete(TEntradaLinea registro) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_MIS_ENTRADA_LINEAS WHERE IDSESION = ? ");
		params.add(registro.getIdSesion());

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void insertAll(final List<TEntradaLinea> lstTEntradaLinea) {
		// TODO Auto-generated method stub

		//Esto es infinitamente más rápido que hacerlo en blucle. ¡Viva el batchUpdate!
		String query = " INSERT INTO T_MIS_ENTRADA_LINEAS (COD_CAB_PEDIDO, CREATION_DATE, COD_CENTRO, DENOM_COD_ART, DESC_ERROR, COD_ERROR, FLG_FOTO, "
				+ " IDSESION, NUMERO_CAJAS_PEDIDAS, NUMERO_CAJAS_RECEP, NUMERO_CAJAS_RECEP_ORI, COD_ART, TOTAL_BANDEJAS_PEDIDAS, TOTAL_BANDEJAS_RECEP, "
				+ " TOTAL_BANDEJAS_RECEP_ORI, TOTAL_UNIDADES_PEDIDAS, TOTAL_UNIDADES_RECEP, TOTAL_UNIDADES_RECEP_ORI, UC ) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		if (lstTEntradaLinea != null && lstTEntradaLinea.size()>0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					TEntradaLinea myPojo = lstTEntradaLinea.get(i);

					if (null != myPojo.getCodCabPedido()){
						ps.setLong(1, myPojo.getCodCabPedido());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}

					if (null != myPojo.getCreationDate()){
						ps.setDate(2, new java.sql.Date(myPojo.getCreationDate().getTime()));
					} else {
						ps.setNull(2, Types.DATE);
					}

					if (null != myPojo.getCodLoc()){
						ps.setLong(3, myPojo.getCodLoc());
					} else {
						ps.setNull(3, Types.NUMERIC);
					}
					
					ps.setString(4, myPojo.getDenomCodArticulo());
					
					ps.setString(5, myPojo.getDescError());
					if (null != myPojo.getCodError()){
						ps.setLong(6, myPojo.getCodError());
					} else {
						ps.setNull(6, Types.NUMERIC);
					}

					ps.setString(7, myPojo.getFlgFoto());
					ps.setString(8,myPojo.getIdSesion());

					if (null != myPojo.getNumeroCajasPedidas()){
						ps.setLong(9, myPojo.getNumeroCajasPedidas());
					} else {
						ps.setNull(9, Types.NUMERIC);
					}

					if (null != myPojo.getNumeroCajasRecepcionadas()){
						ps.setLong(10, myPojo.getNumeroCajasRecepcionadas());
					} else {
						ps.setNull(10, Types.NUMERIC);
					}

					if (null != myPojo.getNumeroCajasRecepcionadas()){
						ps.setLong(11, myPojo.getNumeroCajasRecepcionadas());
					} else {
						ps.setNull(11, Types.NUMERIC);
					}

					if (null != myPojo.getCodArticulo()){
						ps.setLong(12, myPojo.getCodArticulo());
					} else {
						ps.setNull(12, Types.NUMERIC);
					}

					if (null != myPojo.getTotalBandejasPedidas()){
						ps.setLong(13, myPojo.getTotalBandejasPedidas());
					} else {
						ps.setNull(13, Types.NUMERIC);
					}

					if (null != myPojo.getTotalBandejasRecepcionadas()){
						ps.setLong(14, myPojo.getTotalBandejasRecepcionadas());
					} else {
						ps.setNull(14, Types.NUMERIC);
					}

					if (null != myPojo.getTotalBandejasRecepcionadas()){
						ps.setLong(15, myPojo.getTotalBandejasRecepcionadas());
					} else {
						ps.setNull(15, Types.NUMERIC);
					}

					if (null != myPojo.getTotalUnidadesPedidas()){
						ps.setDouble(16, myPojo.getTotalUnidadesPedidas());
					} else {
						ps.setNull(16,Types.NUMERIC);
					}

					if (null != myPojo.getTotalUnidadesRecepcionadas()){
						ps.setDouble(17, myPojo.getTotalUnidadesRecepcionadas());
					} else {
						ps.setNull(17, Types.NUMERIC);
					}

					if (null != myPojo.getTotalUnidadesRecepcionadas()){
						ps.setDouble(18, myPojo.getTotalUnidadesRecepcionadas());
					} else {
						ps.setNull(18, Types.NUMERIC);
					}

					if (null != myPojo.getUc()){
						ps.setLong(19, myPojo.getUc());
					} else {
						ps.setNull(19, Types.NUMERIC);
					}
				}

				@Override
				public int getBatchSize() {
					return lstTEntradaLinea.size();
				}
			});
		}
	}

	@Override
	public void updateTablaSesionLineasEntrada(final String id, final Entrada entrada, final boolean isSaveData) {
		// TODO Auto-generated method stub
		//Esto es infinitamente más rápido que hacerlo en blucle. ¡Viva el batchUpdate!
		String query = " UPDATE T_MIS_ENTRADA_LINEAS SET NUMERO_CAJAS_RECEP = ?, TOTAL_BANDEJAS_RECEP = ?, "
				+ " TOTAL_UNIDADES_RECEP = ?, COD_ERROR = ?, DESC_ERROR = ? ";

		//Si la actualización de la tabla temporal es debido a que se ha realizado una actualización previa PLSQL de las
		//líneas de entradas modificadas, es necesario actualizar los datos originales de la tabla temporal también.
		if(isSaveData){
			query = query + "  , NUMERO_CAJAS_RECEP_ORI = ?, TOTAL_BANDEJAS_RECEP_ORI = ?, "
					+ " TOTAL_UNIDADES_RECEP_ORI = ? ";
		}
		String where = "WHERE 1=1 AND IDSESION = ? AND COD_CAB_PEDIDO = ? AND COD_ART = ? ";
		query = query + where;

		final List<EntradaLineaModificada> lstEntradaLineaModificada = entrada.getLstModificados();
		if (lstEntradaLineaModificada != null && lstEntradaLineaModificada.size() >0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					EntradaLineaModificada myPojo = lstEntradaLineaModificada.get(i);

					if (null != myPojo.getNumeroCajasRecepcionadas()){
						ps.setLong(1, myPojo.getNumeroCajasRecepcionadas());
					} else {
						ps.setNull(1, Types.NUMERIC);
					}

					if (null != myPojo.getTotalBandejasRecepcionadas()){
						ps.setLong(2, myPojo.getTotalBandejasRecepcionadas());
					} else {
						ps.setNull(2, Types.NUMERIC);
					}

					if (null != myPojo.getTotalUnidadesRecepcionadas()){
						ps.setDouble(3, myPojo.getTotalUnidadesRecepcionadas());
					} else {
						ps.setNull(3, Types.NUMERIC);
					}

					if (null != myPojo.getCodError()){
						ps.setDouble(4, myPojo.getCodError());
					} else {
						ps.setNull(4, Types.NUMERIC);
					}
					
					ps.setString(5, myPojo.getDescError());
					
					int posId = 6;
					int posCodCab = 7;
					int posRef = 8;

					//Si es un guardado, actualizamos los campos originales.
					if(isSaveData){
						if (null != myPojo.getNumeroCajasRecepcionadas()){
							ps.setLong(posId, myPojo.getNumeroCajasRecepcionadas());
						} else {
							ps.setNull(posId, Types.NUMERIC);
						}

						if (null != myPojo.getTotalBandejasRecepcionadas()){
							ps.setLong(posCodCab, myPojo.getTotalBandejasRecepcionadas());
						} else {
							ps.setNull(posCodCab, Types.NUMERIC);
						}

						if (null != myPojo.getTotalUnidadesRecepcionadas()){
							ps.setDouble(posRef, myPojo.getTotalUnidadesRecepcionadas());
						} else {
							ps.setNull(posRef, Types.NUMERIC);
						}

						//Actualizamos posiciones del where
						posId +=3;
						posCodCab +=3;
						posRef +=3;
					}

					//Idsesion
					ps.setString(posId, id);

					//Cod cab pedido
					if (null != entrada.getCodCabPedido()){
						ps.setLong(posCodCab, entrada.getCodCabPedido());
					} else {
						ps.setNull(posCodCab, Types.NUMERIC);
					}

					//Cod referencia
					if (null != myPojo.getCodArticulo()){
						ps.setLong(posRef, myPojo.getCodArticulo());
					} else {
						ps.setNull(posRef, Types.NUMERIC);
					}
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return lstEntradaLineaModificada.size();
				}
			});
		}
	}

	//Función que sirve para realizar ordenaciones por columna, de esta forma, llega el código de columna con nombre
	//jqgrid y lo transforma a código SQL
	private String getMappedField (String fieldName) {
		if(fieldName.toUpperCase().equals("DENOMCODARTICULO")){
			return "DENOM_COD_ART";
		}else if(fieldName.toUpperCase().equals("MENSAJE")){
			return "COD_ERROR";
		}else if(fieldName.toUpperCase().equals("NUMEROCAJASPEDIDAS")){
			return "NUMERO_CAJAS_PEDIDAS";
		}else if(fieldName.toUpperCase().equals("NUMEROCAJASRECEPCIONADAS")){
			return "NUMERO_CAJAS_RECEP";
		}else if(fieldName.toUpperCase().equals("CODARTICULO")){
			return "COD_ART";
		}else if(fieldName.toUpperCase().equals("TOTALBANDEJASPEDIDAS")){
			return "TOTAL_BANDEJAS_PEDIDAS";
		}else if(fieldName.toUpperCase().equals("TOTALBANDEJASRECEPCIONADAS")){
			return "TOTAL_BANDEJAS_RECEP";
		}else if(fieldName.toUpperCase().equals("TOTALUNIDADESPEDIDAS")){
			return "TOTAL_UNIDADES_PEDIDAS";
		}else if(fieldName.toUpperCase().equals("TOTALUNIDADESRECEPCIONADAS")){
			return "TOTAL_UNIDADES_RECEP";
		}else if(fieldName.toUpperCase().equals("UC")){
			return "UC";
		}else{
			return "COD_ART";
		}
	}

	@Override
	public List<EntradaLinea> findLineasEntradaEditadas(String idSesion, Long codError) {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT * " 
				+ " FROM T_MIS_ENTRADA_LINEAS ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND COD_ERROR = ? ");

		params.add(idSesion);
		params.add(codError);  

		query.append(where);

		List<EntradaLinea> lista = null;	
		try{
			lista = (List<EntradaLinea>) this.jdbcTemplate.query(query.toString(),this.rwEntradaLinea, params.toArray());

		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public void resetEntradaEstados(String session) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" UPDATE T_MIS_ENTRADA_LINEAS SET COD_ERROR = ?");
		query.append(" WHERE IDSESION = ?");
		params = new ArrayList<Object>();

		params.add(null);
		params.add(session);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
}
