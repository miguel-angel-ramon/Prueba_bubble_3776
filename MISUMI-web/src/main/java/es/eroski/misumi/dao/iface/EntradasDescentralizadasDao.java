package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.TEntradaLinea;
import es.eroski.misumi.model.ui.Pagination;

public interface EntradasDescentralizadasDao {
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/******************************************** GESTIÓN TABLA TEMPORAL ************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	public List<TEntradaLinea> findLineasEntrada(String idSesion, Entrada entrada, Pagination pagination);
	public void deleteHistorico() throws Exception;
	public void delete(TEntradaLinea registro);
	public  void insertAll(List<TEntradaLinea> lstTEntradaLinea);
	public void updateTablaSesionLineasEntrada(String id, Entrada entrada, boolean isSaveData);
	public List<EntradaLinea> findLineasEntradaEditadas(String idSesion, Long codError);
	public void resetEntradaEstados(String idSesion);

}
