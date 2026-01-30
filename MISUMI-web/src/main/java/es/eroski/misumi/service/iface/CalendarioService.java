package es.eroski.misumi.service.iface;

import java.util.Date;
import java.util.List;

import es.eroski.misumi.model.CalendarioActualizado;
import es.eroski.misumi.model.CalendarioCambioEstacional;
import es.eroski.misumi.model.CalendarioAvisos;
import es.eroski.misumi.model.CalendarioDescripcionServicios;
import es.eroski.misumi.model.CalendarioDiaWarning;
import es.eroski.misumi.model.CalendarioEjercicios;
import es.eroski.misumi.model.CalendarioPendienteValidar;
import es.eroski.misumi.model.CalendarioProcesosDiarios;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.CalendarioValidado;
import es.eroski.misumi.model.CalendarioWarnings;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.model.User;

public interface CalendarioService {
	
	/**************************************************************************************/
	/**************************************************************************************/
	/********************************* TABLAS TEMPORALES **********************************/
	/**************************************************************************************/
	/**************************************************************************************/	
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistorico() throws Exception;
	
	//Método para eliminar los registros 
	public void delete(String idSesion) throws Exception;
	
	//Método que elimina los registros de los servicios.
	public void deleteServicios(String idSesion) throws Exception; 

	//Método para insertar los registros de los días en la tabla temporal T_DIAS_CALENDARIO.
	public void insertAllDiasCalendario(List<TCalendarioDia> listTCalendarioDia) throws Exception;
	
	//Obtiene los días del calendario de un mes.
	public List<TCalendarioDia> findDiasCalendario(String idSesion, Long codCentro, String mesAnio, Long estadoDia, Long estadoServicio, Date fechaDia, Long codServicio, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Busca los día con servicios modificados o con cambios en los dias. Busca solo las filas con servicio null que equivale a TODOS y es el que queremos guardar en SIA.
	public List<TCalendarioDia> findDiasModificadosCalendario(String idSesion, Long codCentro,Long estado, Long estadoServicio) throws Exception;
		
	//Actualiza los datos de un día de la tabla temporal.
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception;
	
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception;
	
	//Actualiza los cambios manuales.
	public void updateDiaCalendarioTemporalCambioManual(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception;
		
	//Actualiza los datos de un día de la tabla temporal.
	public void updateDiaCalendarioTemporaCambiosEstacionales(List<TCalendarioDia> tCalendarioDiaLst, Long codServicio) throws Exception;
	
	//Obtiene los servicios de una lista de días de calendario.
	public List<TCalendarioDia> findServiciosCalendario(List<TCalendarioDia> listTCalendarioDia, Long estadoDia) throws Exception;
	
	//Actualiza los días delo calendario y sus servicios.
	public void updateDiaCalendarioTemporalYServiciosTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception;
	
	//Actualiza los estados de los flags de estado a guardado.
	public void updateDiaCalendarioTemporalYServiciosTemporalGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception;
	
	//Actualiza los servicios del día del calendario.
	public void updateDiaCalendarioServicios(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception;
	
	//Inserta en la tabla temporal los servicios de los días.
	public void insertAllServiciosDiasCalendario(List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio) throws Exception;
	
	//Actualizamos el día con FlgServicio "S".
	public void updateDiaCalendarioTemporalFlgServicio(List<TCalendarioDia> tCalendarioDiaLst) throws Exception;
	
	//Actualizamos los días verdes
	public void updateDiasVerdesCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst,Long numeroDiasAdelanteAtrasVerdeP96, String idSesion, Long estadoDia, Long codCentro, User usuario) throws Exception;
		
	//Obtiene una lista de años y meses del calendario
	public List<String> findMesAnioCalendario(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Consulta si el ejercicio y tipo de ejercicio exite en la tabla, si existe indica que ha sido cosultado previamente por lo que devolveria un true, en caso contrario false.
	public boolean ejercicioConsultado(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception;
	
	//Quitamos los días modificados que se han quedado como estaban.
	public void quitFakeModifiedDays(String idSesion, Long codCentro, Long estadoDia);
	
	//Quitamos los servicios modificados que se han quedado como estaban.
	public void quitFakeModifiedServices(String idSesion, Long codCentro, Long estadoDia) throws Exception;
	
	//Mira el valor que tiene que tener el cambio manual de todos al actualizar los servicios.
	public String checkValueOfCambioManualTodos(TCalendarioDia tCalDiaServiciosSN) throws Exception;
	
	//Actualiza el cambioManual del total a S o N. 
	public void updateDiaCalendarioTemporalServiciosQuitados(TCalendarioDia tCalDiaLstTodos, Long estadoDia) throws Exception;

	//Cuenta cuántos festivos locales tiene un centro.
	public Long countFestivosLocales(String idSesion, Long codCentro, Long codigoEjercicio, String tipoEjercicio);
	
	//Borra los servicios de un día en concreto
	public void deleteServiciosDia(TCalendarioDia tCalendarioDia) throws Exception;
	
	/**************************************************************************************/
	/**************************************************************************************/
	/**************************************** PLSQL ***************************************/
	/**************************************************************************************/
	/**************************************************************************************/
	
	//Método que obtiene fechas limite.
	public CalendarioPendienteValidar pendienteValidarCalendario(Long codCentro) throws Exception;
	
	//Método para cargar el combo de servicios.
	public CalendarioDescripcionServicios consultarServiciosCombo(Long codCentro, Long ejercicio, String tipoEjercicio) throws Exception;
	
	//Método para cargar el combo de años del calendario.
	public CalendarioEjercicios consultarAnioCalendarioCombo(Long codCentro) throws Exception;
	
	//Método para conseguir TODOS los días de los calendarios
	public CalendarioTotal consultarCalendario(Long centro, Long ejercicio, Long codigoServicio, String tipoEjercicio) throws Exception;
		
	//Método que guarda los días editados y sus servicios.
	public CalendarioActualizado guardarCalendario(Long codCentro, String tipoEjer, List<TCalendarioDia> listTCalendarioDiaYServicios) throws Exception;
	
	//Método que valida los días editados y sus servicios.
	public CalendarioValidado validarCalendario(Long codCentro, Long ejercicio) throws Exception;
	
	//Método que consulta al plsql los servicios de un día específico.
	public CalendarioProcesosDiarios consultarProcesosDiarios(Long codCentro, Long ejercicio, Date fecha, String tipoEjercicio) throws Exception;
	
	//Método que consulta los cambios estacionales de un ejercicio.
	public CalendarioCambioEstacional consultarCambiosEstacionales(Long codCentro, Long codigoEjercicio) throws Exception;
	
	public CalendarioAvisos cargarAvisosCalendario(final Long codCentro) throws Exception;
	
	//Guarda los cambios de los servicios temporales.
	public CalendarioActualizado guardarCambioEstacional(Long codCentro,Long codigoEjercicio, CalendarioCambioEstacional calendarioCambioEstacional) throws Exception;
	
	
	//Consulta y modificación de los warnings de un centro
	public CalendarioWarnings consultaModificacionWarnings(Long codCentro, final List<CalendarioDiaWarning> listaWarnings) throws Exception;

}
