package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.EncargosClientePlataforma;
import es.eroski.misumi.model.EncargosClientePlataformaLista;

public interface ReferenciasAltaPlataformaDao {

	public abstract EncargosClientePlataformaLista consultaReferenciasAltaCatalogo(
			EncargosClientePlataforma encargosCliente) throws Exception;

}