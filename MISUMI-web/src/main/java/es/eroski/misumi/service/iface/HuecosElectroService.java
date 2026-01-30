/**
 * 
 */
package es.eroski.misumi.service.iface;

/**
 * @author BICUGUAL
 *
 */
public interface HuecosElectroService {

	/**
	 * Devuelve el numero de huecos de un centro para una estructura pasada como parametros 
	 * 
	 * @param codCentro
	 * @param codGrupo1
	 * @param codGrupo2
	 * @param codGrupo3
	 * @param codGrupo4
	 * @param codGrupo5
	 * @return
	 * @throws Exception
	 */
	public Integer getNumHuecosFinalSeg(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4,
			Long codGrupo5) throws Exception;
	
	public Integer getNumHuecosFinalSubCat(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4) throws Exception;


}
