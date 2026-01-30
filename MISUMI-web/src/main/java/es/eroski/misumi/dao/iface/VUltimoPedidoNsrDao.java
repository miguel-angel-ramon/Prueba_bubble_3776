package es.eroski.misumi.dao.iface;


import java.util.List;

import es.eroski.misumi.model.VArtSfm;

public interface VUltimoPedidoNsrDao  {

	public List<VArtSfm> completarCampoNSR(List<VArtSfm> listaVArtSfm) throws Exception;
	public String obtenerUltimoPedidoNSR(VArtSfm vArtSfm) throws Exception ;
	public Long countUltimoPedidoNSR(VArtSfm vArtSfm) throws Exception ;

}
