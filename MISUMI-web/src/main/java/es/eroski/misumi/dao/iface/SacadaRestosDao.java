/**
 * 
 */
package es.eroski.misumi.dao.iface;

/**
 * Interfaz de persistencia del modulo de CapturaRestos
 * @author BICUGUAL
 *
 */
public interface SacadaRestosDao {
	
	/**
	 * Devuelve la count de GONDOLA='G'
	 * @param codCentro
	 * @param codArt
	 * @param anoOferta
	 * @param numeroOferta
	 * @return
	 */
	public Long countGondolaG(Long codCentro, Long codArt, Long anoOferta, Long numeroOferta);

	
}
