package es.eroski.misumi.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CalendarioServiciosDao;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CalendarioServiciosDaoImpl implements CalendarioServiciosDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<TCalendarioDiaCambioServicio> rwTCalendarioDiaCambioServicio = new RowMapper<TCalendarioDiaCambioServicio>() {
		public TCalendarioDiaCambioServicio mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio = new TCalendarioDiaCambioServicio();
			tCalendarioDiaCambioServicio.setCodCentro(resultSet.getLong("CENTRO"));
			tCalendarioDiaCambioServicio.setIdSesion(resultSet.getString("IDSESION"));
			tCalendarioDiaCambioServicio.setCreationDate(resultSet.getDate("CREATION_DATE"));
			tCalendarioDiaCambioServicio.setFechaCalendario(resultSet.getDate("FECHA"));

			tCalendarioDiaCambioServicio.setServicioHabitual(resultSet.getString("SERVICIO_HABITUAL"));
			tCalendarioDiaCambioServicio.setCambioEstacional(resultSet.getString("CAMBIO_ESTACIONAL"));
			tCalendarioDiaCambioServicio.setCambioManual(resultSet.getString("CAMBIO_MANUAL"));
			tCalendarioDiaCambioServicio.setDenominacionServicio(resultSet.getString("DENOMINACION_SERVICIO"));
			tCalendarioDiaCambioServicio.setCodigoServicio(resultSet.getLong("CODIGO_SERVICIO"));
			
			tCalendarioDiaCambioServicio.seteCambioPlataforma(resultSet.getString("E_CAMBIO_PLATAFORMA"));
			tCalendarioDiaCambioServicio.seteObservaConfirmaPlataforma(resultSet.getString("E_OBSERVA_CONFIRMA_PLATAFORMA"));
			tCalendarioDiaCambioServicio.setTipoEjercicio(resultSet.getString("TIPO_EJERCICIO"));

			tCalendarioDiaCambioServicio.setCodError(resultSet.getLong("COD_ERROR"));
			tCalendarioDiaCambioServicio.setEstadoDia(resultSet.getLong("ESTADO_DIA"));
			
			tCalendarioDiaCambioServicio.setEjercicio(resultSet.getLong("EJERCICIO"));
			tCalendarioDiaCambioServicio.setPuedeSolicitarServicio(resultSet.getString("PUEDE_SOLICITAR_SERVICIO"));
			return tCalendarioDiaCambioServicio;
		}
	};

	@Override
	public List<TCalendarioDia> findServiciosCalendario(List<TCalendarioDia> listTCalendarioDia, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:listTCalendarioDia){
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			List<Object> params = new ArrayList<Object>();
			where.append("WHERE 1=1 ");

			StringBuffer query = new StringBuffer(" SELECT * " 
					+ " FROM T_SERVICIOS_DIAS_CALENDARIO ");

			where.append(" AND IDSESION = ? ");
			params.add(tCalendarioDia.getIdSesion());

			where.append(" AND TRUNC(FECHA) = ? ");

			DateFormat formatter = new SimpleDateFormat("ddMMyy");
			String today = formatter.format(tCalendarioDia.getFechaCalendario());

			params.add(today);

			where.append(" AND CENTRO = ? ");
			params.add(tCalendarioDia.getCodCentro());   

			//Miramos si hay servicios editados, guardados, etc.
			if(estadoDia != null){
				where.append(" AND ESTADO_DIA = ? ");
				params.add(estadoDia);
			}
			
			if(tCalendarioDia.getEjercicio() != null){
				where.append(" AND EJERCICIO = ? ");
				params.add(tCalendarioDia.getEjercicio());
			}
			
			if(tCalendarioDia.getTipoEjercicio() != null){
				where.append(" AND TIPO_EJERCICIO = ? ");
				params.add(tCalendarioDia.getTipoEjercicio());
			}

			//Si el estado dia es igual a 10, significa que estamos buscando los servicios
			//modificados. Por lo que miramos si se han modificado.
			/*if(new Long(10).equals(estadoDia)){

			}*/

			where.append(" ORDER BY CODIGO_SERVICIO ASC ");	 
			query.append(where);

			List<TCalendarioDiaCambioServicio> lista = null;
			try {
				lista = (List<TCalendarioDiaCambioServicio>) this.jdbcTemplate.query(query.toString(),this.rwTCalendarioDiaCambioServicio, params.toArray());
				if(lista != null && lista.size() > 0){
					tCalendarioDia.settCalendarioDiaCambioServicioLst(lista);
				}			
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}		
		return listTCalendarioDia;
	}

	@Override
	public void updateDiaCalendarioServicios(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Si existen servicios en ese día, se actualzian.
			if(tCalendarioDia.gettCalendarioDiaCambioServicioLst()!= null && tCalendarioDia.gettCalendarioDiaCambioServicioLst().size() > 0){
				for(TCalendarioDiaCambioServicio tCalendarioDiaServicio:tCalendarioDia.gettCalendarioDiaCambioServicioLst()){
					//Parámetros.
					//Query de update.
					StringBuffer query = new StringBuffer(" UPDATE T_SERVICIOS_DIAS_CALENDARIO SET ");


					List<Object> params = new ArrayList<Object>();

					if(tCalendarioDiaServicio.getCambioEstacional() != null){
						query.append(" CAMBIO_ESTACIONAL = ?,");
						params.add(tCalendarioDiaServicio.getCambioEstacional());
					}
					if(tCalendarioDiaServicio.getCambioManual() != null){
						query.append(" CAMBIO_MANUAL = ?,");
						params.add(tCalendarioDiaServicio.getCambioManual());
					}
					if(tCalendarioDiaServicio.getServicioHabitual() != null){
						query.append(" SERVICIO_HABITUAL = ?,");
						params.add(tCalendarioDiaServicio.getServicioHabitual());
					}		
					if(tCalendarioDiaServicio.geteCambioPlataforma() != null){
						query.append(" E_CAMBIO_PLATAFORMA = ?,");
						params.add(tCalendarioDiaServicio.geteCambioPlataforma());
					}
					if(estado != null){
						query.append(" ESTADO_DIA = ?,");
						params.add(estado);		
					}

					//Si es un guardado, actualizamos los datos originales y los ponemos como los actuales.
					if(new Long("8").equals(estado)){
						if(tCalendarioDiaServicio.getCambioEstacional() != null){
							query.append(" CAMBIO_ESTACIONAL_ORI = ?,");
							params.add(tCalendarioDiaServicio.getCambioEstacional());
						}
						if(tCalendarioDiaServicio.getCambioManual() != null){
							query.append(" CAMBIO_MANUAL_ORI = ?,");
							params.add(tCalendarioDiaServicio.getCambioManual());
						}
						if(tCalendarioDiaServicio.getServicioHabitual() != null){
							query.append(" SERVICIO_HABITUAL_ORI = ?,");
							params.add(tCalendarioDiaServicio.getServicioHabitual());
						}		
					}

					//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
					query.deleteCharAt(query.length()-1);

					//Añadimos where.
					StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
					where.append(" WHERE 1=1 ");
					where.append(" AND IDSESION = ? "
							+ " AND CENTRO = ? "
							+ " AND TRUNC(FECHA) = TRUNC(?)"
							+ " AND EJERCICIO = ? "
							+ " AND TIPO_EJERCICIO = ?"
							+ " AND E_CAMBIO_PLATAFORMA IS NULL ");
					
					params.add(tCalendarioDiaServicio.getIdSesion());
					params.add(tCalendarioDiaServicio.getCodCentro());
					params.add(tCalendarioDiaServicio.getFechaCalendario());	
					params.add(tCalendarioDiaServicio.getEjercicio());
					params.add(tCalendarioDiaServicio.getTipoEjercicio());
					
					if(tCalendarioDiaServicio.getCodigoServicio() != null){
						where.append(" AND CODIGO_SERVICIO = ?");
						params.add(tCalendarioDiaServicio.getCodigoServicio());
					}

					query.append(where);
					//Ejecutamos update.
					try {
						this.jdbcTemplate.update(query.toString(), params.toArray());
					} catch (Exception e){
						Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
					}
				}			
			}
		}
	}

	@Override
	public void deleteHistoricoDiasCalendarioServicios() throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_SERVICIOS_DIAS_CALENDARIO WHERE CREATION_DATE < (SYSDATE - ?) ");

		params.add(Constantes.DIAS_ELIMINAR);
		try{
			//this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void deleteDiasCalendarioServicios(String idSesion) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_SERVICIOS_DIAS_CALENDARIO WHERE IDSESION = ? ");
		params.add(idSesion);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void insertAllServiciosDiasCalendario(List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio) throws Exception {
		// TODO Auto-generated method stub

		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer("INSERT INTO T_SERVICIOS_DIAS_CALENDARIO (CREATION_DATE, IDSESION, FECHA, ");
		query.append(" CODIGO_SERVICIO, DENOMINACION_SERVICIO, SERVICIO_HABITUAL, CAMBIO_MANUAL, CAMBIO_ESTACIONAL, CENTRO, SERVICIO_HABITUAL_ORI, CAMBIO_MANUAL_ORI, CAMBIO_ESTACIONAL_ORI, E_CAMBIO_PLATAFORMA, E_OBSERVA_CONFIRMA_PLATAFORMA, TIPO_EJERCICIO, EJERCICIO, PUEDE_SOLICITAR_SERVICIO) ");		
		query.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		if (listTCalendarioDiaCambioServicio != null && listTCalendarioDiaCambioServicio.size()>0)
		{
			for (TCalendarioDiaCambioServicio tCalendarioDiaCambioServicio:listTCalendarioDiaCambioServicio)
			{
				params = new ArrayList<Object>();
				params.add(tCalendarioDiaCambioServicio.getCreationDate());
				params.add(tCalendarioDiaCambioServicio.getIdSesion());
				params.add(tCalendarioDiaCambioServicio.getFechaCalendario());

				params.add(tCalendarioDiaCambioServicio.getCodigoServicio());
				params.add(tCalendarioDiaCambioServicio.getDenominacionServicio());
				params.add(tCalendarioDiaCambioServicio.getServicioHabitual());
				params.add(tCalendarioDiaCambioServicio.getCambioManual());
				params.add(tCalendarioDiaCambioServicio.getCambioEstacional());

				params.add(tCalendarioDiaCambioServicio.getCodCentro());

				params.add(tCalendarioDiaCambioServicio.getServicioHabitual());
				params.add(tCalendarioDiaCambioServicio.getCambioManualOriginal());
				params.add(tCalendarioDiaCambioServicio.getCambioEstacional());
				
				params.add(tCalendarioDiaCambioServicio.geteCambioPlataforma());
				params.add(tCalendarioDiaCambioServicio.geteObservaConfirmaPlataforma());
				params.add(tCalendarioDiaCambioServicio.getTipoEjercicio());
				params.add(tCalendarioDiaCambioServicio.getEjercicio());
				
				params.add(tCalendarioDiaCambioServicio.getPuedeSolicitarServicio());
				try{
					this.jdbcTemplate.update(query.toString(), params.toArray());	
				} catch (Exception e){
					Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
				}
			}
		}
	}

	@Override
	public void quitFakeModifiedServices(String idSesion, Long codCentro, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub
		//Query de update.
		StringBuffer query = new StringBuffer(" UPDATE T_SERVICIOS_DIAS_CALENDARIO SET ESTADO_DIA = NULL ");
		List<Object> params = new ArrayList<Object>();

		//Añadimos where.
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");
		where.append(" AND IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND ESTADO_DIA = ?"
				+ " AND   NVL(SERVICIO_HABITUAL_ORI,'?') = nvl(SERVICIO_HABITUAL,'?') "
				+ " AND   NVL(CAMBIO_ESTACIONAL_ORI,'?') = NVL(CAMBIO_ESTACIONAL,'?') "
				+ " AND   NVL(CAMBIO_MANUAL_ORI,'?') = NVL(CAMBIO_MANUAL,'?')"
				+ " AND EXISTS (SELECT 'X' FROM T_DIAS_CALENDARIO "
				+ " WHERE T_DIAS_CALENDARIO.CENTRO = T_SERVICIOS_DIAS_CALENDARIO.CENTRO "
				+ " AND T_DIAS_CALENDARIO.FECHA  = T_SERVICIOS_DIAS_CALENDARIO.FECHA "
				+ " AND T_DIAS_CALENDARIO.IDSESION = T_SERVICIOS_DIAS_CALENDARIO.IDSESION "
				+ " AND (T_DIAS_CALENDARIO.FESTIVO IS  NULL OR T_DIAS_CALENDARIO.FESTIVO = 'X')) ");					

		params.add(idSesion);
		params.add(codCentro);
		params.add(estadoDia);

		query.append(where);
		//Ejecutamos update.
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public String checkValueOfCambioManualTodos(TCalendarioDia tCalDiaServiciosSN) throws Exception {
		String cambioManualTotal = "";

		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer(" "
				+ " SELECT "
				+ " 	DECODE(a.difference,0,'N','S') "
				+ " FROM( "
				+ "      SELECT abs( ( "
				+ "          SELECT COUNT(DENOMINACION_SERVICIO) "
				+ "		     FROM T_SERVICIOS_DIAS_CALENDARIO "
				+ "          WHERE IDSESION = ? "
				+ "          AND CENTRO = ? "
				+ "          AND TRUNC(FECHA) = TRUNC(?) "
				+ "          AND EJERCICIO = ? "
				+ "          AND TIPO_EJERCICIO = ? "
				+ "          ) - ( "
				+ "          SELECT COUNT(DENOMINACION_SERVICIO) "
				+ "		     FROM T_SERVICIOS_DIAS_CALENDARIO "
				+ "          WHERE IDSESION = ? "
				+ "          AND CENTRO = ? "
				+ "			 AND NVL(E_CAMBIO_PLATAFORMA,CAMBIO_MANUAL) = 'N' "
				+ "          AND TRUNC(FECHA) = TRUNC(?) "
				+ "          AND EJERCICIO = ? "
				+ "          AND TIPO_EJERCICIO = ? "
				+ "          )) DIFFERENCE "
				+ " FROM DUAL) A ");

		List<Object> params = new ArrayList<Object>();

		params.add(tCalDiaServiciosSN.getIdSesion());
		params.add(tCalDiaServiciosSN.getCodCentro());
		params.add(tCalDiaServiciosSN.getFechaCalendario());
		params.add(tCalDiaServiciosSN.getEjercicio());
		params.add(tCalDiaServiciosSN.getTipoEjercicio());
		
		params.add(tCalDiaServiciosSN.getIdSesion());
		params.add(tCalDiaServiciosSN.getCodCentro());
		params.add(tCalDiaServiciosSN.getFechaCalendario());
		params.add(tCalDiaServiciosSN.getEjercicio());
		params.add(tCalDiaServiciosSN.getTipoEjercicio());
		try {
			cambioManualTotal =  (String) this.jdbcTemplate.queryForObject(query.toString(), params.toArray(), String.class);
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}		
		return cambioManualTotal;
	}

	/*@Override
	public void updateDiaCalendarioServiciosGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Si existen servicios en ese día, se actualzian.
			if(tCalendarioDia.gettCalendarioDiaCambioServicioLst()!= null && tCalendarioDia.gettCalendarioDiaCambioServicioLst().size() > 0){
				for(TCalendarioDiaCambioServicio tCalendarioDiaServicio:tCalendarioDia.gettCalendarioDiaCambioServicioLst()){
					//Parámetros.
					//Query de update.
					StringBuffer query = new StringBuffer(" UPDATE T_SERVICIOS_DIAS_CALENDARIO SET ");

					List<Object> params = new ArrayList<Object>();
		
					if(tCalendarioDiaServicio.getEstadoDia() != null){
						query.append(" ESTADO_DIA = ?,");
						params.add(8L);		
					}

					//Actualizamos los datos originales y los ponemos como los actuales.
					if(tCalendarioDiaServicio.getCambioEstacional() != null){
						query.append(" CAMBIO_ESTACIONAL_ORI = ?,");
						params.add(tCalendarioDiaServicio.getCambioEstacional());
					}
					//if(tCalendarioDiaServicio.getCambioManual() != null){
					query.append(" CAMBIO_MANUAL_ORI = CAMBIO_MANUAL,");
					//params.add(tCalendarioDiaServicio.getCambioManual());
					//}
					if(tCalendarioDiaServicio.getServicioHabitual() != null){
						query.append(" SERVICIO_HABITUAL_ORI = ?,");
						params.add(tCalendarioDiaServicio.getServicioHabitual());
					}		

					//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
					query.deleteCharAt(query.length()-1);

					//Añadimos where.
					StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
					where.append(" WHERE 1=1 ");
					where.append(" AND IDSESION = ? "
							+ " AND CENTRO = ? "
							+ " AND TRUNC(FECHA) = TRUNC(?)"
							+ " AND CODIGO_SERVICIO = ? ");
					params.add(tCalendarioDiaServicio.getIdSesion());
					params.add(tCalendarioDiaServicio.getCodCentro());
					params.add(tCalendarioDiaServicio.getFechaCalendario());	
					params.add(tCalendarioDiaServicio.getCodigoServicio());
					query.append(where);
					//Ejecutamos update.
					try {
						this.jdbcTemplate.update(query.toString(), params.toArray());
					} catch (Exception e){
						Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
					}
				}			
			}
		}
	}*/

	@Override
	public void updateDiaCalendarioServiciosGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){

			//Parámetros.
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_SERVICIOS_DIAS_CALENDARIO SET ");

			List<Object> params = new ArrayList<Object>();

			//if(tCalendarioDiaServicio.getEstadoDia() != null){
				query.append(" ESTADO_DIA = ?,");
				params.add(8L);		
			//}

			//Actualizamos los datos originales y los ponemos como los actuales.
			//if(tCalendarioDiaServicio.getCambioEstacional() != null){
				query.append(" CAMBIO_ESTACIONAL_ORI = CAMBIO_ESTACIONAL,");
				//params.add(tCalendarioDiaServicio.getCambioEstacional());
			//}
			//if(tCalendarioDiaServicio.getCambioManual() != null){
			query.append(" CAMBIO_MANUAL_ORI = CAMBIO_MANUAL,");
			//params.add(tCalendarioDiaServicio.getCambioManual());
			//}
			//if(tCalendarioDiaServicio.getServicioHabitual() != null){
				query.append(" SERVICIO_HABITUAL_ORI = SERVICIO_HABITUAL_ORI,");
				//params.add(tCalendarioDiaServicio.getServicioHabitual());
			//}		

			//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
			query.deleteCharAt(query.length()-1);

			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");
			where.append(" AND IDSESION = ? "
					+ " AND CENTRO = ? "
					+ " AND TRUNC(FECHA) = TRUNC(?) ");
			params.add(tCalendarioDia.getIdSesion());
			params.add(tCalendarioDia.getCodCentro());
			params.add(tCalendarioDia.getFechaCalendario());	
			query.append(where);
			//Ejecutamos update.
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}			
	}

	@Override
	public void deleteServiciosDia(TCalendarioDia tCalendarioDia) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_SERVICIOS_DIAS_CALENDARIO ");


		//Añadimos where.
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);

		where.append(" WHERE 1=1 ");
		where.append(" AND IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND TRUNC(FECHA) = TRUNC(?)"
				+ " AND EJERCICIO = ? "
				+ " AND TIPO_EJERCICIO = ? ");

		params.add(tCalendarioDia.getIdSesion());
		params.add(tCalendarioDia.getCodCentro());
		params.add(tCalendarioDia.getFechaCalendario());	
		params.add(tCalendarioDia.getEjercicio());
		params.add(tCalendarioDia.getTipoEjercicio());

		query.append(where);

		try{
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}
}
