package es.eroski.misumi.dao.iface;

import java.math.BigDecimal;
import java.util.List;

import es.eroski.misumi.model.AlarmaPLU;
import es.eroski.misumi.model.PLU;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ConsultarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSRequestType;
import es.eroski.misumi.model.alarmasPLUSWS.ModificarPLUSResponseType;
import es.eroski.misumi.model.alarmasPLUSWS.ReferenciaType;
import es.eroski.misumi.model.ui.Pagination;

public interface AlarmasPLUDao  {

	public List<AlarmaPLU> findAll(Pagination pagination, Long codCentro, Long codArea, Long codSeccion, Long agrupacion) throws Exception;
	public AlarmaPLU findOne(Long codCentro, Long codArt, Long agrupacion) throws Exception;
	public boolean hayDatosDeHoyEnTablaIntermedia(Long codCentro) throws Exception;
	/**
	 * Comprueba si existe PLU para esa referencia en otro grupo de balanza.
	 * @param codCentro
	 * @param codArt
	 */
	public boolean existeReferenciaDiferenteGrupoBalanza(Long codCentro,Long codArt, Long grupoBalanza) throws Exception;
	
	public ConsultarPLUSResponseType getPLUsGISAE(ConsultarPLUSRequestType request) throws Exception;
	public ModificarPLUSResponseType setPLUsGISAE(ModificarPLUSRequestType request) throws Exception;

	public List<AlarmaPLU> fromGisaeResponseToAlarmasPLU(ReferenciaType[] referencias, Long codCentro);
	
	public void insertarDatosEnTablaIntermedia(List<AlarmaPLU> list);
	
	

	public void asignarPluAnt(Long codCentro, Long plu, Long codArticulo, Long grupoBalanza);

	public List<String> cargaExtraStockAlarmasPLU(Long codCentro);

	public List<String> cargaExtraAlbaranesAlarmasPLU(Long codCentro);

	public void anotarMsgError(Long codCentro, Long codigoReferencia, Long grupoBalanza, String output);
	
	/**
	 * Obtener el n�mero m�ximo de PLUs por cada centro-grupo de balanza.
	 * @param codCentro
	 * @param grupoBalanza
	 * @return
	 */
	public int getMaxPLUS(Long codCentro, Long grupoBalanza);

	public BigDecimal calculaPendienteRecibir(Long codArticulo, Long codCentro);

	/**
	 * Alta de la nueva parametrizaci�n de PLU para un centro-grupo de balanza.
	 * @param objPLU
	 */
	public boolean insertMaxPLUS(PLU plu);

	/**
	 * Actualiza el valor del Max de PLUs por centro-grupo de balanza y si no existe realiza el alta.
	 * @param objPLU
	 */
	public void updateMaxPLUS(PLU plu) throws Exception;

	public void updateStock(Long codCentro, List<AlarmaPLU> alarmas);
	
	public Boolean existeAlarma(Long codCentro, Long referencia, Long grupoBalanza)  throws Exception;
	
	public void updatePLU(List<AlarmaPLU> list) throws Exception;
}
