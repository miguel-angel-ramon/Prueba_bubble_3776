package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.EncargosClienteLista;
import es.eroski.misumi.model.TEncargosClte;

public interface GestionEncargosService {

	public abstract EncargosClienteLista borrarEncargos(
			List<TEncargosClte> encargosClte) throws Exception;

	public abstract EncargosClienteLista modificarEncargo(
			TEncargosClte encargosClte) throws Exception;

}