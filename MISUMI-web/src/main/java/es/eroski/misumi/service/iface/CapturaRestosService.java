/**
 * 
 */
package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.VSurtidoTienda;
import es.eroski.misumi.model.pda.PdaCapturaRestosForm;

/**
 * @author BICUGUAL
 *
 */
public interface CapturaRestosService {

	/**
	 * Obtiene los datos a rendirizar en la pagina de la jsp pda_p98_captura_restos
	 *  
	 * @param codCentro
	 * @param page
	 * @param referencia
	 * @param session
	 * @param guardadoStockOk Flag que indica Si se viene de una pagina en la que se ha actualizado el stock para guardarlo en T_MIS_CAPTURA_RESTOS.STOCK_DESPUES 
	 * @param guardadoImcOK Flag que indica Si se viene de una pagina en la que se ha actualizado el imc para guardarlo en los campos de IMC de T_MIS_CAPTURA_RESTOS
	 * @param guardadoSfm Flag que indica Si se viene de una pagina en la que se ha actualizado el SFM. Si ademas el metodo es <> de 2 mismas operaciones que guardadoImcOK = SI
	 * @return
	 * @throws Exception
	 */
	public PdaCapturaRestosForm getPageData(Long codCentro, Long page, Long referencia, HttpSession session, String guardadoStockOk, String guardadoImcOK, String guardadoSfm) throws Exception;
	
	/**
	 * Obtiene surtido
	 * @param codArt
	 * @param centro
	 * @param isTratamientoVegalsa
	 * @return
	 * @throws Exception
	 */
	public VSurtidoTienda getSurtidoTienda (Long codArt, Centro centro, Boolean isTratamientoVegalsa) throws Exception;
	
	/**
	 * Modifica flag capacidad incorrecta
	 * @param codCentro
	 * @param codArt
	 * @param flgCapacidadIncorrecta
	 * @throws Exception
	 */
	public void updateFlgCapacidadIncorrecta (Long codCentro, Long codArt, String flgCapacidadIncorrecta) throws Exception;

}
