package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.DetalladoEuros;
import es.eroski.misumi.model.VMisDetalladoEuros;

public interface VMisDetalladoEurosDao {
	public List<DetalladoEuros> findAllReferenciasDetalladoEuros(VMisDetalladoEuros vMisDetalladoEuros, List<Long> listaReferencias) throws Exception;
}
