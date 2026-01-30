package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaAvisos;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaFinalizar;
import es.eroski.misumi.model.EntradasCatalogoEstado;

public interface EntradasDescentralizadasSIADao {
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/***********************************          PK_APR_        ***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	public EntradaCatalogo cargarDenominacionesEntradasDescentralizadas(Long codLoc,String flgHistorico);
	public EntradasCatalogoEstado loadEstadoEntradasDescentralizadas(Entrada entrada, Long codArt, String flgHistorico);
	public EntradaCatalogo cargarAllLineasEntrada(Entrada entrada);
	public EntradasCatalogoEstado loadCabeceraEntradas(final Entrada entrada, final Long codArt, final String flgHistorico);
	public EntradaCatalogo actualizarEntrada(Entrada entrada);
	public EntradaFinalizar finalizarEntrada(Entrada entrada);
	public EntradaAvisos cargarAvisosEntradas(Long codLoc) throws Exception;
}
