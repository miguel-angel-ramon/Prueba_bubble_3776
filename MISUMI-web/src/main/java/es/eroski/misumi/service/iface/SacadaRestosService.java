/**
 * 
 */
package es.eroski.misumi.service.iface;

import javax.servlet.http.HttpSession;

import es.eroski.misumi.model.pda.PdaSacadaRestosForm;

/**
 * @author BICUGUAL
 *
 */
public interface SacadaRestosService {

	public PdaSacadaRestosForm getPageData(Long codCentro, Long referencia, HttpSession session, String guardadoStockOk) throws Exception;

}
