package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.IncluirExcluirRefGama;
import es.eroski.misumi.model.ReferenciasCentro;

/**
 * Interface de acceso a incluir/excluir una referencia en gama
 * @author MAPALAPA
 *
 */
public interface IncluirExcluirRefGamaService{

	/**
     * Invoca al WS de incluir/excluir una referencia en gama.
     * @param incluirExluir
     * @return IncluirExcluirRefGama
     * @throws Exception
     */
	public IncluirExcluirRefGama incluirExluirRefGama(ReferenciasCentro vReferenciasCentro, String incluirExluir, String usuario) throws Exception;

}
