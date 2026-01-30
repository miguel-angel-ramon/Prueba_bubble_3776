package es.eroski.misumi.dao.iface;

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
import es.eroski.misumi.model.CalendarioValidacion;
import es.eroski.misumi.model.CalendarioValidado;
import es.eroski.misumi.model.CalendarioWarnings;
import es.eroski.misumi.model.TCalendarioDia;

public interface CalendarioDaoSIA {

	/**************************************************************************************/
	/**************************************************************************************/
	/**************************************** PLSQL ***************************************/
	/**************************************************************************************/
	/**************************************************************************************/

	//Método que obtiene fechas limite.
	public CalendarioPendienteValidar pendienteValidarCalendario(Long codCentro) throws Exception;

	//Método para cargar el combo de servicios.
	public CalendarioDescripcionServicios consultarServiciosCombo(Long codCentro, Long ejercicio, String tipoEjercicio) throws Exception;

	//Método para conseguir TODOS los días de los calendarios
	public CalendarioTotal consultarCalendario(Long centro, Long ejercicio, Long codigoServicio, String tipoEjercicio) throws Exception;

	//Método que guarda los días editados y sus servicios.
	public CalendarioActualizado guardarCalendario(Long codCentro, String tipoEjer, List<TCalendarioDia> listTCalendarioDiaYServicios) throws Exception;
	
	//Método que valida los días editados y sus servicios.
	public CalendarioValidado validarCalendario(Long codCentro, Long ejercicio) throws Exception;
	
	//Método que consulta los servicios de un día específico.
	public CalendarioProcesosDiarios consultarProcesosDiarios(Long codCentro, Long ejercicio, Date fecha, String tipoEjercicio) throws Exception;
	
	public CalendarioAvisos cargarAvisosCalendario(final Long codCentro) throws Exception;
	
	//Método para consultar los años del calendario.
	public CalendarioEjercicios consultarAnioCalendarioCombo(Long codCentro) throws Exception;
	
	//Método para consultar los cambios estacionales de un ejercicio.
	public CalendarioCambioEstacional consultarCambiosEstacionales(Long codCentro, Long codigoEjercicio) throws Exception;
	
	public CalendarioActualizado guardarCambioEstacional(Long codCentro, Long codigoEjercicio, List<CalendarioValidacion> calValLst) throws Exception;
	

	public CalendarioWarnings consultaModificacionWarnings(Long codCentro, List<CalendarioDiaWarning> listaWarnings) throws Exception;
	
}