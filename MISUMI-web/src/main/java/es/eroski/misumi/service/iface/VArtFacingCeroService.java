package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.SfmCapacidadFacingPagina;
import es.eroski.misumi.model.VArtSfm;

public interface VArtFacingCeroService {

	/**
	 * Recupera las referencias con facing 0.
	 * 
	 * @param codCentro
	 * @param area
	 * @return
	 * @throws Exception
	 */
	public SfmCapacidadFacing consultaFacingCero(Long codCentro, String area) throws Exception;

	/**
	 * Grabar las referencias validadas.
	 * 
	 * @param listaRefsValidadas
	 * @return
	 * @throws Exception
	 */
	public SfmCapacidadFacing grabarRefValidadas(List<VArtSfm> listaRefsValidadas) throws Exception;

	/**
	 * 
	 * @param listaFacingCero
	 * @param index
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	public List<VArtSfm> ordenarLista(List<VArtSfm> listaFacingCero, String index, String sortOrder) throws Exception;
	
	/**
	 * 
	 * @param facingCeroCapacidadGuardada
	 * @param listaFacingCero
	 * @param page
	 * @param max
	 * @param index
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	public SfmCapacidadFacingPagina crearListaPaginada(SfmCapacidadFacing facingCeroCapacidadGuardada, List<VArtSfm> listaFacingCero, Long page, Long max, String index, String sortOrder) throws Exception;
	
}
