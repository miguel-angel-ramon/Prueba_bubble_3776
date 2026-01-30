package es.eroski.misumi.service.iface;

import java.math.BigInteger;
import java.util.List;

import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.pda.PdaArticulo;

public interface RelacionArticuloService {

	public abstract List<Long> findAll(RelacionArticulo relacionArticulo)
			throws Exception;
	
	public abstract List<Long> findRefMismoModeloProveedor(Long codArt) throws Exception;
	public abstract List<PdaArticulo> findDatosEspecificosTextil(Long codArt) throws Exception;
	public abstract List<DetallePedido> findDatosEspecificosTextilPC(Long codArt) throws Exception;
	public abstract List<Long> findLoteReferenciaHija(Long codArt) throws Exception;
	public abstract List<Long> esReferenciaLote(Long codArt) throws Exception;
	
	public abstract List<Long> esReferenciaHijaDeLote(Long codArt) throws Exception;
	
	public abstract List<Long> obtenerHijasLote(Long codArt) throws Exception;
	public abstract List<BigInteger> obtenerHijasLoteBI(Long codArt) throws Exception;
	
	public abstract RelacionArticulo findOneProporciones(RelacionArticulo relacionArticulo)
			throws Exception;
	public abstract List<RelacionArticulo> findAllProporciones(RelacionArticulo relacionArticulo)
			throws Exception;
	public abstract List<Long> findRefMismoLote(Long codArt) throws Exception;

	public abstract Long findRefMadrePromocional(Long codArtRela) throws Exception;

}