package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.DetalladoEuros;
import es.eroski.misumi.model.VMisDetalladoEuros;

public interface VMisDetalladoEurosService {
	public List<DetalladoEuros> findAllReferenciasDetalladoEuros(VMisDetalladoEuros vMisDetalladoEuros) throws Exception;
}
