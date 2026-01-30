package es.eroski.misumi.dao.iface;

import java.util.Date;
import java.util.List;

import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.User;

public interface CalendarioDao {
	/**************************************************************************************/
	/**************************************************************************************/
	/********************************* TABLAS TEMPORALES **********************************/
	/**************************************************************************************/
	/**************************************************************************************/	
	
	//Método para rellenar la tabla temporal con los días
	public void insertAllDiasCalendario(List<TCalendarioDia> listTCalendarioDia) throws Exception;
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistoricoDiasCalendario() throws Exception;

	//Método para eliminar los registros 
	public void deleteDiasCalendario(String idSesion) throws Exception;
	
	//Método que obtiene los días del mes de la tabla temporal.
	public List<TCalendarioDia> findDiasCalendario(String idSesion, Long codCentro, String mesAnio, Long estadoDia, Long estadoServicio, Date fechaDia, Long codigoServicio, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Busca los días con cambios o con servicios modificados. Busca solo las filas con servicio null que equivale a TODOS y es el que queremos guardar en SIA.
	public List<TCalendarioDia> findDiasModificadosCalendario(String idSesion, Long codCentro,Long estado, Long estadoServicio) throws Exception;
	
	//Actualiza los datos de un día de la tabla temporal.
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception;
	
	//Actualiza los cambios manuales.
	public void updateDiaCalendarioTemporalCambioManual(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception;
	
	//Actualiza los datos de un día de la tabla temporal.
	public void updateDiaCalendarioTemporaCambiosEstacionales(List<TCalendarioDia> tCalendarioDiaLst, Long codServicio) throws Exception;
	
	//Actualiza los flags de estados al guardar calendario
	public void updateDiaCalendarioTemporalGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception;
	
	//Actualizamos el día con FlgServicio "S".
	public void updateDiaCalendarioTemporalFlgServicio(List<TCalendarioDia> tCalendarioDiaLst) throws Exception;
	
	//Obtiene una lista de años y meses del calendario
	public List<String> findMesAnioCalendario(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Consulta si el ejercicio y tipo de ejercicio exite en la tabla, si existe indica que ha sido cosultado previamente por lo que devolveria un true, en caso contrario false.
	public boolean ejercicioConsultado(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Actualizamos los días verdes
	public void updateDiasVerdesCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst,Long numeroDiasAdelanteAtrasVerdeP96, String idSesion, Long estadoDia, Long codCentro, User usuario) throws Exception;

	//Quitamos los elementos que anteriormente han sido modificados pero ahora no lo están porque se handejado igual.
	public void quitFakeModifiedDays(String idSesion, Long codCentro, Long estadoDia);	
	
	//Actualiza el cambioManual del total a S o N. 
	public void updateDiaCalendarioTemporalServiciosQuitados(TCalendarioDia tCalDiaLstTodos, Long estadoDia) throws Exception;
	
	//Cuenta los festivos locales
	public Long countFestivosLocales(String idSesion, Long codCentro, Long codigoEjercicio, String tipoEjercicio);
}
