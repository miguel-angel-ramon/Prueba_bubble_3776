package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EntradasDescentralizadasDao;
import es.eroski.misumi.dao.iface.EntradasDescentralizadasSIADao;
import es.eroski.misumi.model.DevolucionAvisos;
import es.eroski.misumi.model.Entrada;
import es.eroski.misumi.model.EntradaAvisos;
import es.eroski.misumi.model.EntradaCatalogo;
import es.eroski.misumi.model.EntradaFinalizar;
import es.eroski.misumi.model.EntradaLinea;
import es.eroski.misumi.model.EntradasCatalogoEstado;
import es.eroski.misumi.model.TEntradaLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.EntradasDescentralizadasService;

@Service
public class EntradasDescentralizadasServiceImpl implements EntradasDescentralizadasService{
	@Autowired
	private EntradasDescentralizadasSIADao entradasDescentralizadasSIADao;
	
	@Autowired
	private EntradasDescentralizadasDao entradasDescentralizadasDao;
	
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/***********************************          PK_APR_        ***********************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	
	@Override
	public EntradaCatalogo cargarDenominacionesEntradasDescentralizadas(Long codLoc, String flgHistorico) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.cargarDenominacionesEntradasDescentralizadas(codLoc, flgHistorico);
	}

	@Override
	public EntradasCatalogoEstado loadEstadoEntradasDescentralizadas(Entrada entrada, Long codArt,
			String flgHistorico) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.loadEstadoEntradasDescentralizadas(entrada, codArt, flgHistorico);
	}

	@Override
	public EntradasCatalogoEstado loadCabeceraEntradas(Entrada entrada, Long codArt, String flgHistorico) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.loadCabeceraEntradas(entrada, codArt, flgHistorico);
	}
	
	@Override
	public EntradaCatalogo cargarAllLineasEntrada(Entrada entrada) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.cargarAllLineasEntrada(entrada);
	}
	
	@Override
	public EntradaCatalogo actualizarEntrada(Entrada entrada) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.actualizarEntrada(entrada);
	}
	
	@Override
	public EntradaFinalizar finalizarEntrada(Entrada entrada) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasSIADao.finalizarEntrada(entrada);
	}
	
	@Override
	public EntradaAvisos cargarAvisosEntradas(Long codCentro) throws Exception {
		return this.entradasDescentralizadasSIADao.cargarAvisosEntradas(codCentro);
	}
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	/******************************************** GESTIÓN TABLA TEMPORAL ************************************************/
	/********************************************************************************************************************/
	/********************************************************************************************************************/
	@Override
	public List<TEntradaLinea> findLineasEntrada(String id, Entrada entrada, Pagination pagination) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasDao.findLineasEntrada(id, entrada, pagination);
	}

	@Override
	public void deleteHistorico() throws Exception {
		// TODO Auto-generated method stub
		entradasDescentralizadasDao.deleteHistorico();
	}

	@Override
	public void delete(TEntradaLinea registro) {
		// TODO Auto-generated method stub
		entradasDescentralizadasDao.delete(registro);
	}

	@Override
	public void insertAll(List<TEntradaLinea> lstTEntradaLinea) {
		// TODO Auto-generated method stub
		entradasDescentralizadasDao.insertAll(lstTEntradaLinea);
	}

	@Override
	public void updateTablaSesionLineasEntrada(String id, Entrada entrada, boolean isSaveData) {
		// TODO Auto-generated method stub
		entradasDescentralizadasDao.updateTablaSesionLineasEntrada(id, entrada, isSaveData);
	}

	@Override
	public List<EntradaLinea> findLineasEntradaEditadas(String idSesion, Long codError) {
		// TODO Auto-generated method stub
		return entradasDescentralizadasDao.findLineasEntradaEditadas(idSesion, codError);
	}
	
	@Override
	public void resetEntradaEstados(String idSesion) {
		// TODO Auto-generated method stub
		entradasDescentralizadasDao.resetEntradaEstados(idSesion);
	}
}
