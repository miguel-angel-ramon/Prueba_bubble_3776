package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaAvisos;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaFinalizar;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.EntradasCatalogoEstado;
import es.eroski.misumi.model.TEntradaLinea;
import es.eroski.misumi.model.ui.Pagination;

public interface EntradasDescentralizadasService {

	/********************************************************************************************************************/
	/***********************************          PK_APR_       ***********************************/
	/********************************************************************************************************************/
	public EntradaCatalogo cargarDenominacionesEntradasDescentralizadas(Long codLoc, String flgHistorico); 
	public EntradasCatalogoEstado loadEstadoEntradasDescentralizadas(Entrada entrada, Long codArt, String flgHistorico); 
	public EntradaCatalogo cargarAllLineasEntrada(Entrada entrada);
	public EntradasCatalogoEstado loadCabeceraEntradas(final Entrada entrada, final Long codArt, final String flgHistorico);
	public EntradaCatalogo actualizarEntrada(Entrada entrada);
	public EntradaFinalizar finalizarEntrada(Entrada entrada);
	public EntradaAvisos cargarAvisosEntradas(Long codCentro) throws Exception;
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/******************************************** GESTIÓN TABLA TEMPORAL************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	public List<TEntradaLinea> findLineasEntrada(String id, Entrada entrada, Pagination pagination);
	public void deleteHistorico() throws Exception;
	public void delete(TEntradaLinea registro);
	public void insertAll(List<TEntradaLinea> lstTEntradaLinea);
	public void updateTablaSesionLineasEntrada(String id, Entrada entrada, boolean isSaveData);
	public List<EntradaLinea> findLineasEntradaEditadas(String idSesion, Long codError);
	public void resetEntradaEstados(String idSesion);

}
