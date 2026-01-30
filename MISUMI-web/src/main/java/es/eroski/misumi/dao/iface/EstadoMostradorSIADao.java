package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.EstadoMostradorSIALista;

public interface EstadoMostradorSIADao {

	public EstadoMostradorSIALista consultaEstados(Long codCentro) throws Exception;

}
