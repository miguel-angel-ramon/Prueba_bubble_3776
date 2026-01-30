package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CalendarioDao;
import es.eroski.misumi.dao.iface.CalendarioDaoSIA;
import es.eroski.misumi.dao.iface.CalendarioServiciosDao;
import es.eroski.misumi.model.CalendarioActualizado;
import es.eroski.misumi.model.CalendarioAvisos;
import es.eroski.misumi.model.CalendarioCambioEstacional;
import es.eroski.misumi.model.CalendarioDescripcionServicios;
import es.eroski.misumi.model.CalendarioDiaWarning;
import es.eroski.misumi.model.CalendarioEjercicios;
import es.eroski.misumi.model.CalendarioPendienteValidar;
import es.eroski.misumi.model.CalendarioProcesosDiarios;
import es.eroski.misumi.model.CalendarioServicio;
import es.eroski.misumi.model.CalendarioServicioHabitual;
import es.eroski.misumi.model.CalendarioTotal;
import es.eroski.misumi.model.CalendarioValidacion;
import es.eroski.misumi.model.CalendarioValidacionTratado;
import es.eroski.misumi.model.CalendarioValidado;
import es.eroski.misumi.model.CalendarioWarnings;
import es.eroski.misumi.model.TCalendarioDia;
import es.eroski.misumi.model.TCalendarioDiaCambioServicio;
import es.eroski.misumi.model.User;
import es.eroski.misumi.service.iface.CalendarioService;

@Service(value = "CalendarioServiceImpl")
public class CalendarioServiceImpl implements CalendarioService{
	@Autowired
	private CalendarioDaoSIA calendarioDaoSIA;

	@Autowired
	private CalendarioDao calendarioDao;

	@Autowired
	private CalendarioServiciosDao calendarioServiciosDao;

	@Override
	public CalendarioPendienteValidar pendienteValidarCalendario(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return calendarioDaoSIA.pendienteValidarCalendario(codCentro);
	}

	@Override
	public CalendarioDescripcionServicios consultarServiciosCombo(Long codCentro, Long ejercicio, String tipoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		return calendarioDaoSIA.consultarServiciosCombo(codCentro,ejercicio,tipoEjercicio);
	}

	@Override
	public CalendarioTotal consultarCalendario(Long centro, Long ejercicio, Long codigoServicio, String tipoEjercicio) throws Exception{
		// TODO Auto-generated method stub
		return calendarioDaoSIA.consultarCalendario(centro, ejercicio, codigoServicio, tipoEjercicio);
	}

	@Override
	public void deleteHistorico() throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.deleteHistoricoDiasCalendario();
		this.calendarioServiciosDao.deleteHistoricoDiasCalendarioServicios();
	}

	@Override
	public void delete(String idSesion) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.deleteDiasCalendario(idSesion);
		this.calendarioServiciosDao.deleteDiasCalendarioServicios(idSesion);
	}

	@Override
	public void deleteServicios(String idSesion) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioServiciosDao.deleteDiasCalendarioServicios(idSesion);
	}

	@Override
	public void insertAllDiasCalendario(List<TCalendarioDia> listTCalendarioDia) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.insertAllDiasCalendario(listTCalendarioDia);
	}

	@Override
	public List<TCalendarioDia> findDiasCalendario(String idSesion, Long codCentro, String mesAnio, Long estadoDia, Long estadoServicio, Date fechaDia, Long codigoServicio, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioDao.findDiasCalendario(idSesion, codCentro, mesAnio, estadoDia, estadoServicio, fechaDia, codigoServicio, tipoEjercicio, codigoEjercicio);
	}

	@Override
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporal(tCalendarioDiaLst, estado, null);
	}
	
	@Override
	public void updateDiaCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporal(tCalendarioDiaLst, estado, estadoServicio);
	}
	
	@Override
	public void updateDiaCalendarioTemporalYServiciosTemporal(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception {
		this.calendarioDao.updateDiaCalendarioTemporal(tCalendarioDiaLst, estado, estadoServicio);
		this.calendarioServiciosDao.updateDiaCalendarioServicios(tCalendarioDiaLst, estadoServicio);
	}

	@Override
	public void updateDiaCalendarioTemporalYServiciosTemporalGuardado(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		this.calendarioDao.updateDiaCalendarioTemporalGuardado(tCalendarioDiaLst);
		this.calendarioServiciosDao.updateDiaCalendarioServiciosGuardado(tCalendarioDiaLst);		
	}
	
	@Override
	public void updateDiaCalendarioServicios(List<TCalendarioDia> tCalendarioDiaLst, Long estado, Long estadoServicio) throws Exception{
		this.calendarioServiciosDao.updateDiaCalendarioServicios(tCalendarioDiaLst, estadoServicio);
	}

	@Override
	public List<TCalendarioDia> findServiciosCalendario(List<TCalendarioDia> listTCalendarioDia, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioServiciosDao.findServiciosCalendario(listTCalendarioDia, estadoDia);
	}

	@Override
	public CalendarioActualizado guardarCalendario(Long codCentro, String tipoEjer, List<TCalendarioDia> listTCalendarioDiaYServicios) throws Exception{
		// TODO Auto-generated method stub		
		return this.calendarioDaoSIA.guardarCalendario(codCentro, tipoEjer, listTCalendarioDiaYServicios);
	}

	@Override
	public CalendarioValidado validarCalendario(Long codCentro, Long ejercicio) throws Exception {
		// TODO Auto-generated method stub		
		return this.calendarioDaoSIA.validarCalendario(codCentro, ejercicio);
	}

	@Override
	public CalendarioProcesosDiarios consultarProcesosDiarios(Long codCentro, Long ejercicio, Date fecha, String tipoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioDaoSIA.consultarProcesosDiarios(codCentro, ejercicio, fecha, tipoEjercicio);
	}

	@Override
	public void insertAllServiciosDiasCalendario(List<TCalendarioDiaCambioServicio> listTCalendarioDiaCambioServicio) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioServiciosDao.insertAllServiciosDiasCalendario(listTCalendarioDiaCambioServicio);
	}

	@Override
	public void updateDiaCalendarioTemporalFlgServicio(List<TCalendarioDia> tCalendarioDiaLst) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporalFlgServicio(tCalendarioDiaLst);
	}

	@Override
	public void updateDiasVerdesCalendarioTemporal(List<TCalendarioDia> tCalendarioDiaLst,Long numeroDiasAdelanteAtrasVerdeP96, String idSesion, Long estadoDia, Long codCentro, User usuario) throws Exception {
		// TODO Auto-generated method stub
		//Pone los que tienen que ser verdes
		this.calendarioDao.updateDiasVerdesCalendarioTemporal(tCalendarioDiaLst,numeroDiasAdelanteAtrasVerdeP96,idSesion,estadoDia,codCentro, usuario);		 
	}


	@Override
	public CalendarioAvisos cargarAvisosCalendario(Long codCentro) throws Exception {
		return this.calendarioDaoSIA.cargarAvisosCalendario(codCentro);
	}

	@Override
	public CalendarioEjercicios consultarAnioCalendarioCombo(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioDaoSIA.consultarAnioCalendarioCombo(codCentro);
	}

	@Override
	public List<String> findMesAnioCalendario(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioDao.findMesAnioCalendario(idSesion, codCentro, tipoEjercicio, codigoEjercicio);
	}
	
	
	@Override
	public boolean ejercicioConsultado(String idSesion, Long codCentro, String tipoEjercicio, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioDao.ejercicioConsultado(idSesion, codCentro, tipoEjercicio, codigoEjercicio);
	}

	@Override
	public void quitFakeModifiedDays(String idSesion, Long codCentro, Long estadoDia) {
		// TODO Auto-generated method stub
		this.calendarioDao.quitFakeModifiedDays(idSesion, codCentro, estadoDia);
	}

	@Override
	public CalendarioCambioEstacional consultarCambiosEstacionales(Long codCentro, Long codigoEjercicio) throws Exception {
		// TODO Auto-generated method stub
		CalendarioCambioEstacional calendarioCambioEstacional =  this.calendarioDaoSIA.consultarCambiosEstacionales(codCentro, codigoEjercicio);
		if(new Long("0").equals(calendarioCambioEstacional.getpCodError())){
			HashMap<Long,CalendarioValidacionTratado> hashMap = new HashMap<Long,CalendarioValidacionTratado>();
			for(CalendarioValidacion calVal:calendarioCambioEstacional.getListadoValidaciones()){
				if(!hashMap.containsKey(calVal.getCodigoServicio())){					
					//Creamos el primer elemento de los servivios habituales.
					CalendarioServicioHabitual calendarioServicioHabitual = new CalendarioServicioHabitual(calVal.getServicioHabitualLunes(),calVal.getServicioHabitualMartes(),calVal.getServicioHabitualMiercoles(),
							calVal.getServicioHabitualJueves(),calVal.getServicioHabitualViernes(),calVal.getServicioHabitualSabado(),calVal.getServicioHabitualDomingo(),calVal.getCalendarioServicioLst());					

					//Creamos la lista de servicios habituales e insertamos el primer elemento
					List<CalendarioServicioHabitual> listaCalendarioServicioHabitual = new ArrayList<CalendarioServicioHabitual>();
					listaCalendarioServicioHabitual.add(calendarioServicioHabitual);

					//Creamos el objeto de servicio habitual tratado.
					CalendarioValidacionTratado calendarioValidacionTratado = new CalendarioValidacionTratado(calVal.getCodigoServicio(),calVal.getDenominacionServicio(),listaCalendarioServicioHabitual);					

					//Metemos en el hashmap el calendarioValidacionTratado
					hashMap.put(calVal.getCodigoServicio(),calendarioValidacionTratado);
				}else{					
					//Creamos el primer elemento de los servivios habituales.
					CalendarioServicioHabitual calendarioServicioHabitual = new CalendarioServicioHabitual(calVal.getServicioHabitualLunes(),calVal.getServicioHabitualMartes(),calVal.getServicioHabitualMiercoles(),
							calVal.getServicioHabitualJueves(),calVal.getServicioHabitualViernes(),calVal.getServicioHabitualSabado(),calVal.getServicioHabitualDomingo(),calVal.getCalendarioServicioLst());				

					CalendarioValidacionTratado calendarioValidacionTratado = hashMap.get(calVal.getCodigoServicio());
					calendarioValidacionTratado.getCalServHabLst().add(calendarioServicioHabitual);
				}
			}
			calendarioCambioEstacional.setListadoValidacionesTratadas(hashMap);
		}
		return calendarioCambioEstacional;
	}

	@Override
	public CalendarioActualizado guardarCambioEstacional(Long codCentro, Long codigoEjercicio,
			CalendarioCambioEstacional calendarioCambioEstacional) throws Exception {
		// TODO Auto-generated method stub
		List<CalendarioValidacion> calValLst = new ArrayList<CalendarioValidacion>();
		for(Long clave:calendarioCambioEstacional.getListadoValidacionesTratadas().keySet()){
			CalendarioValidacionTratado calValTrat = calendarioCambioEstacional.getListadoValidacionesTratadas().get(clave);

			List<CalendarioServicio> calendarioServListTotal = new ArrayList<CalendarioServicio>();
			for(CalendarioServicioHabitual servHab:calValTrat.getCalServHabLst()){
				calendarioServListTotal.addAll(servHab.getCalendarioServicioLst());
			}
			CalendarioValidacion calVal = new CalendarioValidacion(calValTrat.getCodigoServicio(),calendarioServListTotal);
			calValLst.add(calVal);
		}
		return this.calendarioDaoSIA.guardarCambioEstacional(codCentro, codigoEjercicio, calValLst);
	}

	@Override
	public void quitFakeModifiedServices(String idSesion, Long codCentro, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioServiciosDao.quitFakeModifiedServices(idSesion, codCentro, estadoDia);
	}

	@Override
	public String checkValueOfCambioManualTodos(TCalendarioDia tCalDiaServiciosSN) throws Exception {
		// TODO Auto-generated method stub
		return this.calendarioServiciosDao.checkValueOfCambioManualTodos(tCalDiaServiciosSN);
	}

	@Override
	public void updateDiaCalendarioTemporalServiciosQuitados(TCalendarioDia tCalDiaLstTodos, Long estadoDia) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporalServiciosQuitados(tCalDiaLstTodos,estadoDia);
	}

	@Override
	public List<TCalendarioDia> findDiasModificadosCalendario(String idSesion, Long codCentro,Long estado, Long estadoServicio) throws Exception{
		// TODO Auto-generated method stub
		return this.calendarioDao.findDiasModificadosCalendario(idSesion,codCentro,estado, estadoServicio);
	}

	@Override
	public void updateDiaCalendarioTemporaCambiosEstacionales(List<TCalendarioDia> tCalendarioDiaLst, Long codServicio) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporaCambiosEstacionales(tCalendarioDiaLst, codServicio);
	}

	@Override
	public CalendarioWarnings consultaModificacionWarnings(Long codCentro, final List<CalendarioDiaWarning> listaWarnings) throws Exception {
		return this.calendarioDaoSIA.consultaModificacionWarnings(codCentro, listaWarnings);
	}

	@Override
	public Long countFestivosLocales(String idSesion, Long codCentro, Long codigoEjercicio, String tipoEjercicio) {
		// TODO Auto-generated method stub
		return this.calendarioDao.countFestivosLocales(idSesion, codCentro, codigoEjercicio, tipoEjercicio);
	}

	@Override
	public void updateDiaCalendarioTemporalCambioManual(List<TCalendarioDia> tCalendarioDiaLst, Long estado)
			throws Exception {
		// TODO Auto-generated method stub
		this.calendarioDao.updateDiaCalendarioTemporalCambioManual(tCalendarioDiaLst,estado);
	}

	@Override
	public void deleteServiciosDia(TCalendarioDia tCalendarioDia) throws Exception {
		// TODO Auto-generated method stub
		this.calendarioServiciosDao.deleteServiciosDia(tCalendarioDia);
	}
}
