package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.IncluirExcluirRefGama;
import es.eroski.misumi.model.ReferenciasCentro;

public interface IncluirExcluirRefGamaDao  {
	
	/**
	 * 
	 * @param vReferenciasCentro
	 * @param incluirExluir
	 * @return IncluirExcluirRefGama
	 * @throws Exception
	 */
	public IncluirExcluirRefGama incluirExcluirRefGama(ReferenciasCentro vReferenciasCentro, String incluirExluir, String usuario) throws Exception;
}
