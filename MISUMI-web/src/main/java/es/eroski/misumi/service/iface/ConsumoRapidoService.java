package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ConsumoRapido;


public interface ConsumoRapidoService {
	public List<ConsumoRapido> findAll(Long codCentro, Long area, Long seccion, String fechaIni, String fechaFin, String index, String sortOrder) throws Exception;	
	
}
