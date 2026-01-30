package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.ui.Pagination;



public interface ConfirmacionNoServidoService {
	public List<StockNoServido> findAllNoServidos(SeguimientoCampanas seguimientoCampanas, List<Long> listaReferencias, Pagination pagination) throws Exception;
	public Long findAllNoServidosCont(SeguimientoCampanas seguimientoCampanas, List<Long> listaReferencias) throws Exception;

}
