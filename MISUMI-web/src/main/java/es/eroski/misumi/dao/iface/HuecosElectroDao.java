/**
 * 
 */
package es.eroski.misumi.dao.iface;

/**
 * P-50987
 * @author BICUGUAL
 */
public interface HuecosElectroDao {

	/**
	 * Devuelve el numero de huecos de un centro para los parametros de estructura recibidos.
	 * Si exiten registros pero el campo es nulo, el tratamiento ha de ser el mismo que si no tuviese gestion.
	 * de huecos.
	 * @param codCentro
	 * @param codGrupo1
	 * @param codGrupo2
	 * @param codGrupo3
	 * @param codGrupo4
	 * @param codGrupo5
	 * @return
	 * @throws Exception
	 */
	public Integer getHuecosFinalSegByCodCentro(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4,
			Long codGrupo5) throws Exception;
	
	
	public Integer getHuecosFinalSubCatByCodCentro(Long codCentro, Long codGrupo1, Long codGrupo2, Long codGrupo3, Long codGrupo4) throws Exception;
	
}
