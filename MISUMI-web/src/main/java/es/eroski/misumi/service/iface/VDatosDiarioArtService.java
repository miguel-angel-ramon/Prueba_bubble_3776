package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;


public interface VDatosDiarioArtService {

	/**
	 * 
	 * @param vDatosDiarioArt
	 * @return
	 * @throws Exception
	 */
	public VDatosDiarioArt findOne(VDatosDiarioArt vDatosDiarioArt) throws Exception  ;
	/**
	 * 
	 * @param vDatosDiarioArt
	 * @return
	 * @throws Exception
	 */
	public Long findVidaUtil(VDatosDiarioArt vDatosDiarioArt) throws Exception;
	/**
	 * 
	 * @param vDatosDiarioArt
	 * @return
	 * @throws Exception
	 */
	public boolean esReferenciaSoloVenta(VDatosDiarioArt vDatosDiarioArt) throws Exception;
	/**
	 * 
	 * @param vDatosDiarioArt
	 * @param pagination
	 * @return
	 * @throws Exception
	 */
	public List<VDatosDiarioArt> findAllVentaRef(VDatosDiarioArt vDatosDiarioArt, Pagination pagination) throws Exception;
	/**
	 * 
	 * @param vDatosDiarioArt
	 * @return
	 * @throws Exception
	 */
	public Long findAllVentaRefCont(VDatosDiarioArt vDatosDiarioArt) throws Exception;
	/**
	 *   
	 * @param codArt
	 * @return
	 * @throws Exception
	 */
	public String obtenerDescripcion(Long codArt) throws Exception;
}
