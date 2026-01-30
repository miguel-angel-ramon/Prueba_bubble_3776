package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;

public interface CalendarioServiciosDao {
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistoricoDiasCalendarioServicios() throws Exception;

	//Método para eliminar los registros 
	public void deleteDiasCalendarioServicios(String idSesion) throws Exception;

	//Actualiza los servicios por los días del calendario.
	public void updateDiaCalendarioServicios(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception;

	//Actualiza los flags de estados al guardar calendario
	public void updateDiaCalendarioServiciosGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception;
			
	//Método para rellenar la tabla temporal con los servicios de los días.
	public void insertAllServiciosDiasCalendario(List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio) throws Exception;
	
	//Busca los servicios de cada día de la lista.
	public List<TCalendarioDia> findServiciosCalendario(List<TCalendarioDia> listTCalendarioDia, Long estadoDia) throws Exception;
	
	//Quitamos los servicios que anteriormente han sido modificados pero ahora no están porque se han dejado igual.
	public void quitFakeModifiedServices(String idSesion, Long codCentro, Long estadoDia) throws Exception;
	
	//Mira el valor que tiene que tener el cambio manual de todos al actualizar los servicios.
	public String checkValueOfCambioManualTodos(TCalendarioDia tCalDiaServiciosSN) throws Exception;
	
	//Borra los servicios de un día en concreto
	public void deleteServiciosDia(TCalendarioDia tCalendarioDia) throws Exception;
}
