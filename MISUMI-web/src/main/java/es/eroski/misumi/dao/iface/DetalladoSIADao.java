package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.DetalladoRedondeo;
import es.eroski.misumi.model.DetalladoSIA;
import es.eroski.misumi.model.DetalladoSIALista;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.DetallePedidoModificados;
import es.eroski.misumi.model.GestionEuros;
import es.eroski.misumi.model.GestionEurosSIA;

public interface DetalladoSIADao  {
	
	public DetalladoSIALista consultaDetallado(DetalladoSIA detalladoSIA) throws Exception;
	public DetalladoSIALista actualizarDetallado(List<DetalladoSIA> DetalladoSIALista) throws Exception;
	
	public List<DetallePedidoModificados> modifyProcedureSIAData(List<DetallePedido> lista, Long codCentro) throws Exception;
	public DetalladoRedondeo redondeoDetallado(DetalladoSIA detalladoSIA) throws Exception;
	public DetalladoSIALista referenciaNueva(DetalladoSIA detalladoSIA) throws Exception;
	
	public GestionEurosSIA gestionEuros(GestionEuros gestionEuros) throws Exception;
}
