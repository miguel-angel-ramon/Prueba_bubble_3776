package es.eroski.misumi.service.iface;

public interface VReferenciaActiva2Service {

	String getNextDiaPedido(Long codCentro, Long codArt) throws Exception;
	String getNextFechaPedido(Long codCentro, Long codArt) throws Exception;

}