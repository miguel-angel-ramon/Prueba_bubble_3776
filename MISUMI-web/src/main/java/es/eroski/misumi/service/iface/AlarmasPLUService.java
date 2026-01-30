package es.eroski.misumi.service.iface;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.NoSuchMessageException;

import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.GenericExcelFieldsVO;
import es.eroski.misumi.model.PLU;
import es.eroski.misumi.model.ui.Pagination;


public interface AlarmasPLUService {

	public List<AlarmaPLU> findAll(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, HttpSession session) throws Exception;
	public List<AlarmaPLU> findAllInMemory(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception;
	public List<AlarmaPLU> findAllAvisos(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception;
	public List<AlarmaPLU> findAllAvisosVacio(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia, Boolean useStatus, HttpSession session) throws Exception;
	public List<GenericExcelFieldsVO> findAllExcel(Pagination pagination, String[] headers, Long codCentro, Long codArea, Long codSeccion, Long agrupacion, String filtroReferencia) throws Exception;
	public Integer[] counters(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception;
	public Map<Long, String> getAreasPLUs(Long codCentro) throws Exception;
	public Map<Long, String> getSeccionesPLUs(Long codCentro, Long codArea) throws Exception;
	public Map<Long, String> getAgrupacionesPLUs(Long codCentro, Long codArea, Long codSeccion) throws Exception;
	public List<Long> plusLibres(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception;
	public void asignar(Long codCentro, Long plu, Long referencia, Long grupoBalanza) throws Exception;
	public void aniadirAlarma(Long codCentro,AlarmaPLU alarma)throws Exception;
	public AlarmaPLU findOne(Long codCentro, Long codArt, Long agrupacion) throws Exception;
	public void eliminar(Long codCentro, Long[] referencias, Long grupoBalanza) throws Exception;
	public List<String> hayAvisosPLU(Long codCentro) throws Exception;
	public boolean hayDatosDeHoyEnTablaIntermedia(Long codCentro) throws Exception;
	public void cargarDatosEnTablaIntermedia(Long codCentro) throws Exception;
	public void initModifiedAlarmList(Long codCentro) throws Exception;
	public List<Long> modificarDatosGisae(Long codCentro, Long agrupacionBalanza, String nombreUsuario) throws NoSuchMessageException, Exception;
	public List<Long> modificarDatosGisaeTodos(Long codCentro, Long agrupacionBalanza, String user) throws NoSuchMessageException, Exception;
	public Long altaReferenciaGisae(Long codCentro, Long plu,Long referencia, Long agrupacionBalanza) throws NoSuchMessageException, Exception;
	public String checkTeclasBalanza(Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception;
	public List<AlarmaPLU> obtenerDatosGisae(Long codCentro, Long codArticulo);
	public AlarmaPLU findOneInMemory(Long codCentro, Long referencia, Long grupoBalanza);
	public AlarmaPLU findOneInModify(Long codCentro, Long referencia, Long grupoBalanza);
	public Boolean getEstaCargando(Long codCentro);
	public void setEstaCargando(Long codCentro, Boolean estaCargando);
	public List<AlarmaPLU> setPendienteRecibir(List<AlarmaPLU> alarmas);
	
	/**
	 * Actualiza el valor del Max de PLUs por centro-grupo de balanza y si no existe realiza el alta.
	 * 
	 * @param plu
	 * @throws Exception
	 */
	public void updateMaxPLUS(PLU plu) throws Exception;
	public void insertarDatosEnTablaIntermedia(List<AlarmaPLU> lista);
	public void initModifiedAlarmList(Long codCentro, List<AlarmaPLU> alarmas) throws Exception;
	public int obtenerTipoPopupStock(Long codCentro, Long codArt, HttpSession session);
	public Boolean existeAlarma(Long codCentro, Long referencia, Long grupoBalanza) throws Exception;
	public void updatePLU(List<AlarmaPLU> lista) throws Exception;
}
