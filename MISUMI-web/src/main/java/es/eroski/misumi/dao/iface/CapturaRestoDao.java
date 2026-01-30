/**
 * 
 */
package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.pda.PdaCapturaResto;

/**
 * Interfaz de persistencia del modulo de CapturaRestos
 * @author BICUGUAL
 *
 */
public interface CapturaRestoDao {
	
	/**
	 * Devuelve el registro para la fecha de generacion de SYSDATE
	 * @param codCentro
	 * @param codArt
	 * @return
	 */
	public PdaCapturaResto getCapturaRestosByCodArt(Long codCentro, Long codArt);
	
	/**
	 * Devuelve el registro cuyo rownum coincide con el parametro page para la fecha de generacion de SYSDATE
	 * @param codCentro
	 * @param page
	 * @return
	 */
	public PdaCapturaResto getCapturaRestosByPage(Long codCentro, Long page);
	
	/**
	 * Devuelve la count de T_MIS_CAPTURA_RESTOS para el centro y codArt
	 * @param codCentro
	 * @param codArt
	 * @return
	 */
	public Long countCapturasRestos(Long codCentro, Long codArt);
	/**
	 * Inserta un registro en la tabla T_MIS_CAPTURA_RESTOS
	 * @param capturaResto
	 * @return
	 */
	public int insertCapturaResto(PdaCapturaResto capturaResto);
	/**
	 * Devuelve la count de GONDOLA='G'
	 * @param codCentro
	 * @param codArt
	 * @param anoOferta
	 * @param numeroOferta
	 * @return
	 */
	public Long countGondolaG(Long codCentro, Long codArt, Long anoOferta, Long numeroOferta);

	/**
	 * Modifica los registros de T_MIS_CAPTURA_RESTOS
	 * @param capturaResto
	 * @return
	 */
	public int updateCapturaResto(PdaCapturaResto capturaResto);

	/**
	 * Devuelve el codigo de plataforma un centro/ref
	 * @param codCentro
	 * @param codArt
	 * @return
	 */
	public Long getCodPlataforma(Long codCentro, Long codArt,List<Long> vRelacionArticuloLista);

	/**
	 * Devuelve el empuje de un centro/ref
	 * @param codCentro
	 * @param codArt
	 * @return
	 */
	public String getEmpuje(Long codCentro, Long codArt,List<Long> vRelacionArticuloLista);
	
	/**
	 * Modifica el valor del FLAG CAPACIDAD INCORRECTA
	 * @param codCentro
	 * @param codArt
	 * @param flgCapacidadIncorrecta
	 */
	public void updateFlagCapacidadIncorrecta(Long codCentro, Long codArt, String flgCapacidadIncorrecta);

	
}
