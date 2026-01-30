package es.eroski.misumi.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import es.eroski.misumi.dao.iface.CalendarioDao;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.User;
import es.eroski.misumi.util.Constantes;
import es.eroski.misumi.util.Utilidades;

@Repository
public class CalendarioDaoImpl implements CalendarioDao{
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	} 

	private RowMapper<String> rwAnioMesCalendario = new RowMapper<String>() {
		public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return resultSet.getString("ANO_MES");   
		}
	};

	private RowMapper<TCalendarioDia> rwTCalendarioDia = new RowMapper<TCalendarioDia>() {
		public TCalendarioDia mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			tCalendarioDia.setIdSesion(resultSet.getString("IDSESION"));
			tCalendarioDia.setCreationDate(resultSet.getDate("CREATION_DATE"));
			tCalendarioDia.setFechaCalendario(resultSet.getDate("FECHA"));
			tCalendarioDia.setFestivo(resultSet.getString("FESTIVO"));
			//tCalendarioDia.setPonerDiaVerde(resultSet.getString("PONER_DIA_VERDE"));
			tCalendarioDia.setPonerDiaVerde(resultSet.getString("PONER_DIA_VERDE_CASE")); //Si es un dia pasado tendra el valor 'N' sino el valor que tenga en la tabla el campo 'PONER_DIA_VERDE'
			tCalendarioDia.setCerrado(resultSet.getString("CERRADO"));
			tCalendarioDia.setServicioHabitual(resultSet.getString("SERVICIO_HABITUAL"));
			tCalendarioDia.setCambioEstacional(resultSet.getString("CAMBIO_ESTACIONAL"));
			tCalendarioDia.setCambioManual(resultSet.getString("CAMBIO_MANUAL"));
			//tCalendarioDia.setSuministro(resultSet.getString("SUMINISTRO"));
			tCalendarioDia.setCodCentro(resultSet.getLong("CENTRO"));
			tCalendarioDia.setFlgServiciosBuscados(resultSet.getString("FLG_SERVICIOS_BUSCADOS"));
			tCalendarioDia.setEstado(resultSet.getLong("ESTADO_DIA"));
			tCalendarioDia.setEstadoServicio(resultSet.getLong("ESTADO_SERVICIO"));
			tCalendarioDia.setCodError(resultSet.getLong("COD_ERROR"));
			tCalendarioDia.setCambioManualOri(resultSet.getString("CAMBIO_MANUAL_ANT"));
			tCalendarioDia.setCambioManualServiciosNulos(resultSet.getString("CAMBIO_MANUAL_SERVICIOS_NULOS"));
			tCalendarioDia.setCodServicio(resultSet.getLong("COD_SERVICIO"));
			tCalendarioDia.setTipoEjercicio(resultSet.getString("TIPO_EJERCICIO"));
			tCalendarioDia.setECambioPlataforma(resultSet.getString("E_CAMBIO_PLATAFORMA"));
			tCalendarioDia.setESePuedeModificarServicio(resultSet.getString("E_SE_PUEDE_MODIFICAR_SERVICIO"));
			tCalendarioDia.setEAprobadoCambio(resultSet.getString("E_APROBADO_CAMBIO"));
			tCalendarioDia.setDiaPasado(resultSet.getString("PASADO"));
			tCalendarioDia.setMesAnio(resultSet.getString("ANO_MES"));
			tCalendarioDia.setPuedeSolicitarServicio(resultSet.getString("PUEDE_SOLICITAR_SERVICIO"));
			tCalendarioDia.setNoServicio(resultSet.getString("NO_SERVICIO"));
			tCalendarioDia.setVerdePlataforma(resultSet.getString("VERDE_PLATAFORMA"));
			return tCalendarioDia;
		}
	};

	private RowMapper<TCalendarioDia> rwTCalendarioDiaModif = new RowMapper<TCalendarioDia>() {
		public TCalendarioDia mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TCalendarioDia tCalendarioDia = new TCalendarioDia();
			tCalendarioDia.setIdSesion(resultSet.getString("IDSESION"));
			tCalendarioDia.setCreationDate(resultSet.getDate("CREATION_DATE"));
			tCalendarioDia.setFechaCalendario(resultSet.getDate("FECHA"));
			tCalendarioDia.setFestivo(resultSet.getString("FESTIVO"));
			tCalendarioDia.setPonerDiaVerde(resultSet.getString("PONER_DIA_VERDE"));
			tCalendarioDia.setCerrado(resultSet.getString("CERRADO"));
			tCalendarioDia.setServicioHabitual(resultSet.getString("SERVICIO_HABITUAL"));
			tCalendarioDia.setCambioEstacional(resultSet.getString("CAMBIO_ESTACIONAL"));
			tCalendarioDia.setCambioManual(resultSet.getString("CAMBIO_MANUAL"));
			//tCalendarioDia.setSuministro(resultSet.getString("SUMINISTRO"));
			tCalendarioDia.setCodCentro(resultSet.getLong("CENTRO"));
			tCalendarioDia.setFlgServiciosBuscados(resultSet.getString("FLG_SERVICIOS_BUSCADOS"));
			tCalendarioDia.setEstado(resultSet.getLong("ESTADO_DIA"));
			tCalendarioDia.setEstadoServicio(resultSet.getLong("ESTADO_SERVICIO"));
			tCalendarioDia.setCodError(resultSet.getLong("COD_ERROR"));
			tCalendarioDia.setCambioManualOri(resultSet.getString("CAMBIO_MANUAL_ANT"));
			tCalendarioDia.setCambioManualServiciosNulos(resultSet.getString("CAMBIO_MANUAL_SERVICIOS_NULOS"));
			tCalendarioDia.setCodServicio(resultSet.getLong("COD_SERVICIO"));
			tCalendarioDia.setTipoEjercicio(resultSet.getString("TIPO_EJERCICIO"));
			tCalendarioDia.setECambioPlataforma(resultSet.getString("E_CAMBIO_PLATAFORMA"));
			tCalendarioDia.setESePuedeModificarServicio(resultSet.getString("E_SE_PUEDE_MODIFICAR_SERVICIO"));
			tCalendarioDia.setEAprobadoCambio(resultSet.getString("E_APROBADO_CAMBIO"));
			tCalendarioDia.setNoServicio((resultSet.getString("NO_SERVICIO")));
			return tCalendarioDia;
		}
	};


	@Override
	public void insertAllDiasCalendario(final List<TCalendarioDia> listTCalendarioDia) throws Exception {
		//Esto es infinitamente más rápido que hacerlo en blucle. ¡Viva el batchUpdate!
		String query = " INSERT INTO T_DIAS_CALENDARIO (CREATION_DATE, IDSESION, FECHA, "
				+ " FESTIVO, PONER_DIA_VERDE, CERRADO, SERVICIO_HABITUAL, CAMBIO_MANUAL, CAMBIO_ESTACIONAL, CENTRO, ANO_MES, FESTIVO_ANT, PONER_DIA_VERDE_ANT, "
				+ " CERRADO_ANT, CAMBIO_MANUAL_ANT, COD_SERVICIO, E_CAMBIO_PLATAFORMA, E_SE_PUEDE_MODIFICAR_SERVICIO, E_APROBADO_CAMBIO, TIPO_EJERCICIO, EJERCICIO, VERDE_PLATAFORMA, PUEDE_SOLICITAR_SERVICIO, NO_SERVICIO) "		
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

		if (listTCalendarioDia != null && listTCalendarioDia.size()>0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					TCalendarioDia myPojo = listTCalendarioDia.get(i);

					if (null != myPojo.getCreationDate()){
						Date da = new java.sql.Date(myPojo.getCreationDate().getTime());
						ps.setDate(1, new java.sql.Date(myPojo.getCreationDate().getTime()));

					} else {
						ps.setNull(1, Types.DATE);
					}			

					ps.setString(2, myPojo.getIdSesion());

					if (null != myPojo.getFechaCalendario()){
						ps.setDate(3, new java.sql.Date(myPojo.getFechaCalendario().getTime()));
					} else {
						ps.setNull(3, Types.DATE);
					}

					ps.setString(4, myPojo.getFestivo());
					ps.setString(5, myPojo.getPonerDiaVerde());
					ps.setString(6, myPojo.getCerrado());
					ps.setString(7, myPojo.getServicioHabitual());
					ps.setString(8, myPojo.getCambioManual());
					ps.setString(9, myPojo.getCambioEstacional());

					if (null != myPojo.getCodCentro()){
						ps.setLong(10, myPojo.getCodCentro());
					} else {
						ps.setNull(10, Types.NUMERIC);
					}

					ps.setString(11, myPojo.getMesAnio());
					ps.setString(12, myPojo.getFestivo());
					ps.setString(13, myPojo.getPonerDiaVerde());
					ps.setString(14, myPojo.getCerrado());
					ps.setString(15, myPojo.getCambioManual());

					if (null != myPojo.getCodServicio()){
						ps.setLong(16, myPojo.getCodServicio());
					} else {
						ps.setNull(16, Types.NUMERIC);
					}

					ps.setString(17, myPojo.getECambioPlataforma());
					ps.setString(18, myPojo.getESePuedeModificarServicio());
					ps.setString(19, myPojo.getEAprobadoCambio());
					ps.setString(20, myPojo.getTipoEjercicio());

					if (null != myPojo.getEjercicio()){
						ps.setLong(21, myPojo.getEjercicio());
					} else {
						ps.setNull(21, Types.NUMERIC);
					}
					
					ps.setString(22, myPojo.getVerdePlataforma());
					ps.setString(23, myPojo.getPuedeSolicitarServicio());
					ps.setString(24, myPojo.getNoServicio());
				}

				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return listTCalendarioDia.size();
				}
			});
		}
	}

	@Override
	public void deleteHistoricoDiasCalendario() throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_DIAS_CALENDARIO WHERE CREATION_DATE < (SYSDATE - ?) ");

		params.add(Constantes.DIAS_ELIMINAR);
		try{
			//this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public void deleteDiasCalendario(String idSesion) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();

		StringBuffer query = new StringBuffer(" DELETE FROM T_DIAS_CALENDARIO WHERE IDSESION = ? ");
		params.add(idSesion);

		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){

			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public List<TCalendarioDia> findDiasCalendario(String idSesion, Long codCentro, String mesAnio, Long estadoDia, Long estadoServicio, Date fechaDia, Long codigoServicio, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT T.*, " 
				+ " CASE WHEN FECHA < TRUNC(sysdate) THEN 'S' ELSE 'N' END PASADO, " 
				+ " CASE WHEN FECHA < TRUNC(sysdate) THEN 'N' ELSE T.PONER_DIA_VERDE END PONER_DIA_VERDE_CASE" 
				+ " FROM T_DIAS_CALENDARIO T ");

		where.append(" AND IDSESION = ? ");
		params.add(idSesion);

		if(mesAnio != null){
			where.append(" AND FECHA BETWEEN TO_DATE('01'||?,'DDYYYY-MM') AND LAST_DAY(TO_DATE('01'||?,'DDYYYY-MM')) ");
			params.add(mesAnio);
			params.add(mesAnio);
		}if(estadoDia != null){
			where.append(" AND ESTADO_DIA = ? ");
			params.add(estadoDia);
		}if(estadoServicio != null){
			where.append(" AND ESTADO_SERVICIO = ? ");
			params.add(estadoServicio);
		}
		if(fechaDia != null){
			where.append(" AND TRUNC(FECHA) = TRUNC(?) ");
			params.add(fechaDia);
		}
		where.append(" AND CENTRO = ? ");
		params.add(codCentro);  

		if(codigoServicio != null){
			where.append(" AND COD_SERVICIO = ? ");
			params.add(codigoServicio);
		}else{
			where.append(" AND COD_SERVICIO IS NULL ");
		}  

		where.append(" AND TIPO_EJERCICIO = ? ");
		params.add(tipoEjercicio);  

		if(codigoEjercicio != null){
			where.append(" AND EJERCICIO = ? ");
			params.add(codigoEjercicio);
		} 

		where.append(" ORDER BY FECHA ");	       	       
		query.append(where);
		List<TCalendarioDia> lista = null;
		try {
			lista = (List<TCalendarioDia>) this.jdbcTemplate.query(query.toString(),this.rwTCalendarioDia, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public List<TCalendarioDia> findDiasModificadosCalendario(String idSesion, Long codCentro,Long estado, Long estadoServicio) throws Exception {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append("WHERE 1=1 ");


		StringBuffer query = new StringBuffer(" SELECT * " 
				+ " FROM T_DIAS_CALENDARIO todosLosDias, (SELECT DISTINCT(FECHA)"
				+ " FROM T_DIAS_CALENDARIO"
				+ " WHERE  IDSESION = ? ");

		params.add(idSesion);

		if(estadoServicio != null){
			query.append("       AND ESTADO_SERVICIO = ? ");
			params.add(estadoServicio);
		}
		if(estado != null){
			query.append("       AND ESTADO_DIA = ? ");
			params.add(estado);
		}
		//query.append("       AND COD_SERVICIO IS NULL ");
		query.append("       AND CENTRO = ?) fechasDistintas ");
		params.add(codCentro);

		where.append(" AND todosLosDias.IDSESION = ? ");
		params.add(idSesion);

		where.append(" AND TRUNC(todosLosDias.FECHA) = TRUNC(fechasDistintas.FECHA) ");

		where.append(" AND todosLosDias.CENTRO = ? ");
		params.add(codCentro);  

		where.append(" AND todosLosDias.COD_SERVICIO IS NULL ");

		where.append(" ORDER BY todosLosDias.FECHA ");	       	       
		query.append(where);

		List<TCalendarioDia> lista = null;
		try {
			lista = (List<TCalendarioDia>) this.jdbcTemplate.query(query.toString(),this.rwTCalendarioDiaModif, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}

	@Override
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");

			//Parámetros.
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET  ");

			List<Object> params = new ArrayList<Object>();
			if(tCalendarioDia.getFestivo() != null){
				query.append(" FESTIVO = ?,");
				params.add(tCalendarioDia.getFestivo());
			}
			if(tCalendarioDia.getPonerDiaVerde() != null){
				query.append(" PONER_DIA_VERDE = ?,");
				params.add(tCalendarioDia.getPonerDiaVerde());
			}
			if(tCalendarioDia.getCerrado() != null){
				query.append(" CERRADO = ?,");
				params.add(tCalendarioDia.getCerrado());
			}
			if(tCalendarioDia.getServicioHabitual() != null){
				query.append(" SERVICIO_HABITUAL = ?,");
				params.add(tCalendarioDia.getServicioHabitual());
			}
			if(tCalendarioDia.getEAprobadoCambio() != null){
				query.append(" E_APROBADO_CAMBIO = ?,");
				params.add(tCalendarioDia.getEAprobadoCambio());
			}
			if(tCalendarioDia.getCambioEstacional() != null){
				query.append(" CAMBIO_ESTACIONAL = ?,");
				params.add(tCalendarioDia.getCambioEstacional());
			}
			if(tCalendarioDia.getECambioPlataforma() != null){
				query.append(" E_CAMBIO_PLATAFORMA = ?,");
				params.add(tCalendarioDia.getECambioPlataforma());
			}		
			if(estado != null){
				query.append(" ESTADO_DIA = ?,");
				params.add(estado);		
			}
			//Guarda el estado de sus servicios.
			if(estadoServicio != null){
				query.append(" ESTADO_SERVICIO = ?,");
				params.add(estadoServicio);		
			}

			if(new Long("10").equals(estadoServicio)){
				//Quitamos las posibles SQ de campo CAMBIO_MANUAL_SERVICIOS_NULOS,
				//pues en caso de haber modificado los servicios, cuando reabramos el popup queremos que guarde los estados.
				query.append("  CAMBIO_MANUAL_SERVICIOS_NULOS = ?,");
				params.add(null);
			}

			//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
			query.deleteCharAt(query.length()-1);

			//Creamos el where.
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

			//Añadimos el where.
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
	public void updateDiaCalendarioTemporalCambioManual(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");

			//Parámetros.
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET  ");

			List<Object> params = new ArrayList<Object>();

			if(tCalendarioDia.getCambioManual() != null){
				query.append(" CAMBIO_MANUAL = ?,");
				params.add(tCalendarioDia.getCambioManual());

				//Cuando cambiemos el cambio manual, queremos poner el SQ, siempre que no sea un guardado (8) que nos interesa quitarlo, o que sea una actualización
				//de datos de la temporal para sincronizar datos estado = null. Además, queremos insertar el SQ solo si se ha cambiado el camión en el servicio TODOS.
				//En los servicios normales, no queremos meter el SQ porque puede darse el caso de que en TODOS no exista el camión, vayamos por ejemplo a fruta e
				//insertemos el camión. En ese caso sin el control de codigoServicio == null, insertaría el SQ, por lo que al ir a TODOS y abrir el popup, resetearía todo
				//y pasaría de lo que hemos hecho en fruta, por lo que el SQ solo se pone cuando el codigo de servicio es null.
				if(!new Long("8").equals(estado) && estado != null && tCalendarioDia.getCodServicio() == null){
					//Si actualizo el cambio manual, introduzco un 'SQ' en este campo.
					query.append(" CAMBIO_MANUAL_SERVICIOS_NULOS = ?,");
					params.add("SQ");
				}
			}

			if(estado != null){
				query.append(" ESTADO_DIA = ?,");
				params.add(estado);		
			}

			//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
			query.deleteCharAt(query.length()-1);

			//Creamos el where.
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

			//Si estamos cambiando el cambio manual, queremos que solo actualice el servicio del cambio manual
			//en cuestión y no todos. Eso se debe a que el cambio manual cuando buscamos por servicio representa
			//al servicio en cuestión y cuando buscamos por todos, representa a todos los servicios. En el caso de
			//tener seleccionado todos, si quitamos el camión, queremos que se actualicen todos los servicios con el N
			//para que no salga ningún camión. En el caso de ser S en todos, solo queremos actualizar el cambioManual de todos
			//para que salga el camión ahí, pero en los servicios normales no queremos que salgan los camiones, pues se le abrirá
			//un popup para seleccionar los servicios que quiera o que busque el servicio en cuestión y ponga a mano el camión.

			//Si estamos cambiando el cambio estacional, significa que estamos en el cambio estacional y quermos actualizar los estacionales de
			//ese servicio en concreto.

			//Si estamos en un servicio y abrimos el centro, el cambio manual se vuelve N en todos los servicios
			if(tCalendarioDia.getCodServicio() != null){
				//Si el codigo de servicio es distinto de nulo y el cambio manual es S, actualizamos también a S 
				//TODOS, porque si no, en caso de no existir el camión en todos, pondríamos el camión en el servicio,
				//pero al ir a todos no saldría el camión. Si el cambio manual se cambia a N, solo afecta al propio servicio.

				//Si se cambia el cambio estacional es solo para un servicio en concreto.

				if(("S").equals(tCalendarioDia.getCambioManual())){
					where.append(" AND (COD_SERVICIO = ? OR COD_SERVICIO IS NULL) ");
					params.add(tCalendarioDia.getCodServicio());
				}
				//Si se abre un centro en un servicio, y ese día es festivo y verde, además de actualizar el cerrado a N, queremos poner todos los cambios Manuales de los
				//servicios a N. Por eso controlamos que el cerrado sea null si solo queremos actualizar un servicio. Cuando se abre un centro en un dia festivo y verde, ya estemos
				//en todos como en un servicio, queremos poner todos los cambios manuales a N. Por eso es necesario este control. Ahora cuando abrimos un centro si es un festivo, pasamos
				//cambio manual a N y el cerrado a N. Si el cambio manual viene por poner o quitar el camión en el servicio de un día, entra aquí, ya que el flag de cerrado es null.
				else if((("N").equals(tCalendarioDia.getCambioManual()) && tCalendarioDia.getCerrado() == null)|| tCalendarioDia.getCambioEstacional() != null){
					where.append(" AND COD_SERVICIO = ? ");
					params.add(tCalendarioDia.getCodServicio());
				}
			}else{
				//Si se pone el cambio manual en todos, queremos que solo ponga S en todos porque solo queremos que exista ahí el camión.
				//Así podemos meter los servicios a mano.

				//Si se cambia el cambio estacional de todos, actualizamos solo el cambio estacional de todos, porque puede que cada servicio tenga los suyos y no se quieran modificar.				
				if(("S").equals(tCalendarioDia.getCambioManual()) || tCalendarioDia.getCambioEstacional() != null){
					where.append(" AND COD_SERVICIO IS NULL ");
				}
			}

			//Si quitamos el cerrado, pasamos cambio manual a N y queremos que solo lo ponga donde ecambioplataforma es null
			if(("N").equals(tCalendarioDia.getCambioManual()) && tCalendarioDia.getCerrado() != null){
				where.append(" AND E_CAMBIO_PLATAFORMA IS NULL ");
			}

			//Añadimos el where.
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
	public void updateDiaCalendarioTemporaCambiosEstacionales(final List<TCalendarioDia> tCalendarioDiaLst, final Long codServicio) throws Exception {
		// TODO Auto-generated method stub
		//Parámetros.
		//Query de update.
		String query = " UPDATE T_DIAS_CALENDARIO SET CAMBIO_ESTACIONAL = ? ";

		//Creamos el where.
		query = query + " WHERE IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND TRUNC(FECHA) = TRUNC(?) ";

		if(codServicio != null){
			query = query + " AND COD_SERVICIO = ? ";
		}else{
			query = query + " AND COD_SERVICIO IS NULL ";
		};

		if(tCalendarioDiaLst != null && tCalendarioDiaLst.size() > 0){
			jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {	        	
					TCalendarioDia myPojo = tCalendarioDiaLst.get(i);

					ps.setString(1,myPojo.getCambioEstacional());
					ps.setString(2,myPojo.getIdSesion());

					if (null != myPojo.getCodCentro()){
						ps.setLong(3, myPojo.getCodCentro());
					} else {
						ps.setNull(3, Types.NUMERIC);
					}

					if (null != myPojo.getFechaCalendario()){
						ps.setDate(4, new java.sql.Date(myPojo.getFechaCalendario().getTime()));
					} else {
						ps.setNull(4, Types.DATE);
					}
					if(codServicio != null){
						ps.setLong(5, codServicio);						 
					}
				}
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return tCalendarioDiaLst.size();	
				}
			});
		}
	}
	/*@Override
	public void updateDiaCalendarioTemporalGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");

			//Parámetros.
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET  ");

			List<Object> params = new ArrayList<Object>();

			//Guardar estados 
			if(tCalendarioDia.getEstado() != null){
				query.append(" ESTADO_DIA = ?,");
				params.add(8L);		
			}

			if(tCalendarioDia.getEstadoServicio() != null){
				//Guarda el estado de sus servicios.
				query.append(" ESTADO_SERVICIO = ?,");
				params.add(8L);		
			}

			//Quitamos las posibles SQ de campo CAMBIO_MANUAL_SERVICIOS_NULOS,
			//pues en caso de haber modificado los servicios, cuando reabramos el popup queremos que guarde los estados.
			query.append("  CAMBIO_MANUAL_SERVICIOS_NULOS = ?,");
			params.add(null);

			//Actualizamos los datos originales y los ponemos como los actuales.
			if(tCalendarioDia.getFestivo() != null){
				query.append(" FESTIVO_ANT = ?,");
				params.add(tCalendarioDia.getFestivo());
			}
			if(tCalendarioDia.getPonerDiaVerde() != null){
				query.append(" PONER_DIA_VERDE_ANT = ?,");
				params.add(tCalendarioDia.getPonerDiaVerde());
			}
			if(tCalendarioDia.getCerrado() != null){
				query.append(" CERRADO_ANT = ?,");
				params.add(tCalendarioDia.getCerrado());
			}	
			//if(tCalendarioDia.getCambioManual() != null){
			query.append(" CAMBIO_MANUAL_ANT = ?,");
			params.add(tCalendarioDia.getCambioManual());
			//}	

			//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
			query.deleteCharAt(query.length()-1);

			//Creamos el where.
			where.append(" AND IDSESION = ? "
					+ " AND CENTRO = ? "
					+ " AND TRUNC(FECHA) = TRUNC(?) ");

			params.add(tCalendarioDia.getIdSesion());
			params.add(tCalendarioDia.getCodCentro());
			params.add(tCalendarioDia.getFechaCalendario());	

			//Añadimos el where.
			query.append(where);

			//Ejecutamos update.
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}
	}*/

	@Override
	public void updateDiaCalendarioTemporalGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");

			//Parámetros.
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET  ");

			List<Object> params = new ArrayList<Object>();

			//Guardar estados 
			//if(tCalendarioDia.getEstado() != null){
			query.append(" ESTADO_DIA = ?,");
			params.add(8L);		
			//}

			//if(tCalendarioDia.getEstadoServicio() != null){
			//Guarda el estado de sus servicios.
			query.append(" ESTADO_SERVICIO = ?,");
			params.add(8L);		
			//}

			//Quitamos las posibles SQ de campo CAMBIO_MANUAL_SERVICIOS_NULOS,
			//pues en caso de haber modificado los servicios, cuando reabramos el popup queremos que guarde los estados.
			query.append("  CAMBIO_MANUAL_SERVICIOS_NULOS = ?,");
			params.add(null);

			//Actualizamos los datos originales y los ponemos como los actuales.
			if(tCalendarioDia.getFestivo() != null){
				query.append(" FESTIVO_ANT = ?,");
				params.add(tCalendarioDia.getFestivo());
			}
			if(tCalendarioDia.getPonerDiaVerde() != null){
				query.append(" PONER_DIA_VERDE_ANT = ?,");
				params.add(tCalendarioDia.getPonerDiaVerde());
			}
			if(tCalendarioDia.getCerrado() != null){
				query.append(" CERRADO_ANT = ?,");
				params.add(tCalendarioDia.getCerrado());
			}	
			//if(tCalendarioDia.getCambioManual() != null){
			query.append(" CAMBIO_MANUAL_ANT = CAMBIO_MANUAL,");
			//params.add(tCalendarioDia.getCambioManual());
			//}	

			//Quitamos la última coma del set. Como no sabemos cuál es la última columna que vamos a modificar, les ponemos la coma a todas
			query.deleteCharAt(query.length()-1);

			//Creamos el where.
			where.append(" AND IDSESION = ? "
					+ " AND CENTRO = ? "
					+ " AND TRUNC(FECHA) = TRUNC(?) ");

			params.add(tCalendarioDia.getIdSesion());
			params.add(tCalendarioDia.getCodCentro());
			params.add(tCalendarioDia.getFechaCalendario());	

			//Añadimos el where.
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
	public void updateDiaCalendarioTemporalFlgServicio(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			//Query de update.
			StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET FLG_SERVICIOS_BUSCADOS = ? ");

			List<Object> params = new ArrayList<Object>();
			params.add(tCalendarioDia.getFlgServiciosBuscados());

			//Añadimos where.
			StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
			where.append(" WHERE 1=1 ");
			where.append(" AND IDSESION = ? "
					+ " AND CENTRO = ? "
					+ " AND TRUNC(FECHA) = TRUNC(?) "
					+ " AND EJERCICIO = ? "
					+ " AND TIPO_EJERCICIO = ? ");

			params.add(tCalendarioDia.getIdSesion());
			params.add(tCalendarioDia.getCodCentro());
			params.add(tCalendarioDia.getFechaCalendario());					
			params.add(tCalendarioDia.getEjercicio());	
			params.add(tCalendarioDia.getTipoEjercicio());	

			query.append(where);
			//Ejecutamos update.
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}
		}
	}

	public void updateDiasVerdesCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst,Long numeroDiasAdelanteAtrasVerdeP96, String idSesion, Long estadoDia, Long codCentro, User usuario) throws Exception{
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();

		//Cuando ponemos/quitamos un día como festivo, en ocasiones hay que cambiar el cambio manual. Imaginemos que tenemos el día 6,7,8 y 9 y ninguno es festivo. Ponemos el 9 como festivo,
		//por lo que el 6,7 y 8 se ponen verdes y aceptan poner camiones. Si quitamos el festivo del día 9 por lo que fuera, ya no puede haber camión, porque los camiones son fijos en los días
		//marcados como habituales y se quedaría un camión que no pinta nada, por lo que al quitar el dñia verde, también hay que quitar el camión que habíamos puesto. Para eso se cambia el cambio
		//manual en la update. Como se cambia el cambio manual, hay que tener cuidado también con el SQ.	
		
		//MISUMI-237 - Un día lo pintamos en verde cuando es día verde o verde plataforma(es un festivo plataforma). 
		//Solo queremos modificar PONER_DIA_VERDE en S, cuando jugamos con festivos locales, nacionales, provinciales, etc. pero no cuando son domingos y plataforma.
		//Por eso, al modificar los días verdes al poner o quitar un festivo local, nacional, plataforma, etc, no hay que tener en cuenta el domingo o la plataforma para
		//actualizar el flag PONER_DIA_VERDE -> (AND NVL(T2.FESTIVO, 'N') NOT IN ('D', 'X')")
		
		//El cambio manual sin embargo, sí que hay que tenerlo en cuenta en festivo plataforma al igual que en los festivos locales, nacionales, etc. ya que un festivo plataforma,
		//también genera días verdes aunque sea con el flag VERDE_PLATAFORMA y queremos que se comporte igual que los festivos locales,etc. -> (AND NVL(T2.FESTIVO, 'N') NOT IN ('D') ")
		
		StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO T "
				+ "SET PONER_DIA_VERDE = "
				+ " 	NVL((SELECT 'S'"
				+ " 		FROM T_DIAS_CALENDARIO T2 "
				+ " 		WHERE T2.IDSESION = T.IDSESION "
				+ " 		AND TRUNC(T2.FECHA) BETWEEN TRUNC(T.FECHA - ?) AND TRUNC(T.FECHA + ?) "
				+ " 		AND T2.FESTIVO IS NOT NULL "
				+ " 		AND NVL(T2.FESTIVO, 'N') NOT IN ('D', 'X')"
				+ " 		AND CENTRO =  ? "
				+ " 		AND T2.EJERCICIO = T.EJERCICIO "
				+ "         AND T2.TIPO_EJERCICIO = T.TIPO_EJERCICIO "
				+ "			/*AND (T2.TIPO_EJERCICIO = 'P' "
				+ "    			OR (T2.TIPO_EJERCICIO = 'E' AND T2.E_SE_PUEDE_MODIFICAR_SERVICIO = 'S'))*/ "		
				+ " 		AND ROWNUM < 2),'N'), "
				+ " ESTADO_DIA = ?, "
				+ " CAMBIO_MANUAL = "
				+ " 	DECODE((SELECT 'S' "
				+ "				FROM T_DIAS_CALENDARIO T2 "
				+ " 			where T2.IDSESION = T.IDSESION "
				+ "             AND TRUNC(T2.FECHA) BETWEEN "
				+ "				TRUNC(T.FECHA - ?) AND "
				+ " 			TRUNC(T.FECHA + ?) "
				+ "				AND T2.FESTIVO IS NOT NULL "
				+ "				AND NVL(T2.FESTIVO, 'N') NOT IN ('D','X') "
				+ "				AND CENTRO = ? "
				+ " 		    AND T2.EJERCICIO = T.EJERCICIO "
				+ "             AND T2.TIPO_EJERCICIO = T.TIPO_EJERCICIO "
				+ "				AND (T2.TIPO_EJERCICIO = 'P' "
				+ "    			OR (T2.TIPO_EJERCICIO = 'E' AND T2.E_SE_PUEDE_MODIFICAR_SERVICIO = 'S') "		
				+ "    			OR  ? = 4 )"
				+ "				AND ROWNUM < 2), "
				+ " 			'S', "
				+ "             CAMBIO_MANUAL, "
				+ "             NULL),"
				+ " T.CAMBIO_MANUAL_SERVICIOS_NULOS = "
				+ " CASE WHEN T.CAMBIO_MANUAL_SERVICIOS_NULOS IS NULL AND "
				+ " T.PONER_DIA_VERDE <> NVL((SELECT 'S' "
				+ "                      FROM T_DIAS_CALENDARIO T2 "
				+ "                     WHERE T2.IDSESION = T.IDSESION "
				+ "                     AND TRUNC(T2.FECHA) BETWEEN "
				+ "						TRUNC(T.FECHA - ?) AND "
				+ " 					TRUNC(T.FECHA + ?) "
				+ "                       AND T2.FESTIVO IS NOT NULL "
				+ " 		              AND T2.EJERCICIO = T.EJERCICIO "
				+ "                       AND T2.TIPO_EJERCICIO = T.TIPO_EJERCICIO "
				+ "                       AND NVL(T2.FESTIVO,'N') NOT IN ('D','X') "
				+ "						  AND (T2.TIPO_EJERCICIO = 'P' "
				+ "    							OR (T2.TIPO_EJERCICIO = 'E' AND T2.E_SE_PUEDE_MODIFICAR_SERVICIO = 'S') "
				+ "								OR  ? = 4 ) "		
				+ "                       AND ROWNUM < 2), 'N')  "
				+ " THEN 'SQ' "
				+ " WHEN  trunc(T.FECHA) = trunc(?) "
				+ " THEN 'SQ' "
				+ " ELSE T.CAMBIO_MANUAL_SERVICIOS_NULOS END ");

		where.append(" WHERE T.IDSESION = ? ");
		where.append(" AND TRUNC(T.FECHA) BETWEEN TRUNC(? - ?) AND TRUNC(? + ?) ");
		where.append(" AND CENTRO =  ? ");
		where.append(" AND T.EJERCICIO = ? ");
		where.append(" AND T.TIPO_EJERCICIO = ? ");
		where.append(" AND (T.E_SE_PUEDE_MODIFICAR_SERVICIO = 'S' OR ? = 4 ) ");

		query.append(where);

		for(TCalendarioDia tCalendarioDia:tCalendarioDiaLst){
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(codCentro);
			params.add(estadoDia);
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(codCentro);
			params.add(usuario.getPerfil());
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(usuario.getPerfil());
			params.add(tCalendarioDia.getFechaCalendario());
			params.add(idSesion);
			params.add(tCalendarioDia.getFechaCalendario());		
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(tCalendarioDia.getFechaCalendario());	
			params.add(numeroDiasAdelanteAtrasVerdeP96);
			params.add(codCentro);
			params.add(tCalendarioDia.getEjercicio());
			params.add(tCalendarioDia.getTipoEjercicio());
			params.add(usuario.getPerfil());
			
			//Ejecutamos update.
			try {
				this.jdbcTemplate.update(query.toString(), params.toArray());
			} catch (Exception e){
				Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
			}	
		}
	}


	@Override
	public List<String> findMesAnioCalendario(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer orderby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT DISTINCT(ANO_MES) " 
				+ " FROM T_DIAS_CALENDARIO ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND CENTRO = ? ");
		where.append(" AND TIPO_EJERCICIO = ? ");
		where.append(" AND EJERCICIO = ? ");
		params.add(idSesion);
		params.add(codCentro);
		params.add(tipoEjercicio);
		params.add(codigoEjercicio);

		orderby.append(" ORDER BY ANO_MES ASC ");
		query.append(where);
		query.append(orderby);

		List<String> lista = null;
		try {

			lista = (List<String>) this.jdbcTemplate.query(query.toString(),this.rwAnioMesCalendario, params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return lista;
	}



	public boolean ejercicioConsultado(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer orderby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
				+ " FROM T_DIAS_CALENDARIO ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND CENTRO = ? ");
		where.append(" AND TIPO_EJERCICIO = ? ");
		where.append(" AND EJERCICIO = ? ");
		params.add(idSesion);
		params.add(codCentro);
		params.add(tipoEjercicio);
		params.add(codigoEjercicio);

		query.append(where);

		try {

			return (this.jdbcTemplate.queryForLong(query.toString(), params.toArray())) > 0;
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}

		return false;
	}



	@Override
	public void quitFakeModifiedDays(String idSesion, Long codCentro, Long estadoDia) {
		// TODO Auto-generated method stub
		//Query de update.
		StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET ESTADO_DIA = NULL ");
		List<Object> params = new ArrayList<Object>();

		//Añadimos where.
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		/*where.append(" WHERE 1=1 ");
		where.append(" AND IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND ESTADO_DIA = ?"
				+ " AND   NVL(festivo_ant,'?') = nvl(festivo,'?') "
				+ " AND   NVL(poner_dia_verde_ant,'?') = NVL(poner_dia_verde,'?') "
				+ " AND   NVL(cerrado_ant,'?') = NVL(cerrado,'?') "
				+ " AND   NVL(cambio_manual_ant,'?') = NVL(cambio_manual,'?') "
				+ " AND CAMBIO_MANUAL_SERVICIOS_NULOS IS NULL ");		*/			

		where.append(" WHERE 1=1 ");
		where.append(" AND IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND ESTADO_DIA = ?"
				+ " AND   NVL(festivo_ant,'?') = nvl(festivo,'?') "
				+ " AND   NVL(poner_dia_verde_ant,'?') = NVL(poner_dia_verde,'?') "
				+ " AND   NVL(cerrado_ant,'?') = NVL(cerrado,'?') "
				+ " AND ((CAMBIO_MANUAL_SERVICIOS_NULOS IS NULL AND "
				+ " NVL(E_CAMBIO_PLATAFORMA, NVL(CAMBIO_MANUAL_ANT, NVL(CAMBIO_ESTACIONAL, SERVICIO_HABITUAL))) = NVL(cambio_manual, '?')) "
				+ " OR (CAMBIO_MANUAL_SERVICIOS_NULOS = 'SQ' AND NOT EXISTS( SELECT 'X'"
				+ " FROM T_SERVICIOS_DIAS_CALENDARIO "
				+ " WHERE "
				+ " T_DIAS_CALENDARIO.CENTRO = T_SERVICIOS_DIAS_CALENDARIO.CENTRO "
				+ " AND T_DIAS_CALENDARIO.FECHA = T_SERVICIOS_DIAS_CALENDARIO.FECHA "
				+ " AND T_DIAS_CALENDARIO.IDSESION = T_SERVICIOS_DIAS_CALENDARIO.IDSESION "
				+ " AND NVL(SERVICIO_HABITUAL_ORI,'?') || NVL(CAMBIO_ESTACIONAL_ORI,'?') || NVL(CAMBIO_MANUAL_ORI,'?') <> "
				+ " NVL(SERVICIO_HABITUAL,'?') || NVL(CAMBIO_ESTACIONAL,'?') || NVL(CAMBIO_MANUAL,'?')))) ");		
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
	public void updateDiaCalendarioTemporalServiciosQuitados(TCalendarioDia tCalDiaLstTodos, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub

		//Definimos la lista de parametros.
		List<Object> params = new ArrayList<Object>();

		//Añadimos where.
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		where.append(" WHERE 1=1 ");

		//Parámetros.
		//Query de update.
		StringBuffer query = new StringBuffer(" UPDATE T_DIAS_CALENDARIO SET  ");

		if(tCalDiaLstTodos.getECambioPlataforma() != null){
			query.append(" E_CAMBIO_PLATAFORMA = ?,");
			params.add(tCalDiaLstTodos.getECambioPlataforma());
		}

		query.append(" CAMBIO_MANUAL = ? ");
		params.add(tCalDiaLstTodos.getCambioManual());



		//Creamos el where.
		where.append(" AND IDSESION = ? "
				+ " AND CENTRO = ? "
				+ " AND TRUNC(FECHA) = TRUNC(?) "
				+ " AND TIPO_EJERCICIO = ? "
				+ " AND EJERCICIO = ? ");

		params.add(tCalDiaLstTodos.getIdSesion());
		params.add(tCalDiaLstTodos.getCodCentro());
		params.add(tCalDiaLstTodos.getFechaCalendario());	
		params.add(tCalDiaLstTodos.getTipoEjercicio());
		params.add(tCalDiaLstTodos.getEjercicio());

		where.append(" AND COD_SERVICIO IS NULL ");


		//Añadimos el where.
		query.append(where);

		//Ejecutamos update.
		try {
			this.jdbcTemplate.update(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
	}

	@Override
	public Long countFestivosLocales(String idSesion, Long codCentro, Long codigoEjercicio, String tipoEjercicio) {
		// TODO Auto-generated method stub
		StringBuffer where = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		StringBuffer orderby = new StringBuffer(Constantes.INITIAL_BUFFER_SIZE);
		List<Object> params = new ArrayList<Object>();
		where.append("WHERE 1=1 ");

		StringBuffer query = new StringBuffer(" SELECT COUNT(1) " 
				+ " FROM T_DIAS_CALENDARIO ");

		where.append(" AND IDSESION = ? ");
		where.append(" AND CENTRO = ? ");
		where.append(" AND TIPO_EJERCICIO = ? ");
		where.append(" AND EJERCICIO = ? ");
		where.append(" AND FESTIVO = 'L' ");
		where.append(" AND COD_SERVICIO IS NULL ");
		params.add(idSesion);
		params.add(codCentro);
		params.add(tipoEjercicio);
		params.add(codigoEjercicio);

		query.append(where);

		Long countFestivosLocales = 0L;
		try {
			countFestivosLocales = this.jdbcTemplate.queryForLong(query.toString(), params.toArray());
		} catch (Exception e){
			Utilidades.mostrarMensajeErrorSQL(query.toString(), params ,e);
		}
		return countFestivosLocales;
	}
}
