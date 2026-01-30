package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ListadoReposicionDao;
import es.eroski.misumi.dao.iface.ListadoReposicionDaoSIA;
import es.eroski.misumi.model.Reposicion;
import es.eroski.misumi.model.ReposicionCmbSeccion;
import es.eroski.misumi.model.ReposicionDatosTalla;
import es.eroski.misumi.model.ReposicionGuardar;
import es.eroski.misumi.model.ReposicionHayDatos;
import es.eroski.misumi.model.ReposicionLinea;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ListadoReposicionService;



@Service(value = "listadoReposicionService")
public class ListadoReposicionServiceImpl implements ListadoReposicionService {

	@Autowired
	private ListadoReposicionDaoSIA ListadoReposicionDaoSIA;
	
	@Autowired
	private ListadoReposicionDao ListadoReposicionDao;

	
	
	@Override
	public ReposicionHayDatos hayDatosReposicion(final Reposicion reposicion) throws Exception {
		

		ReposicionHayDatos reposicionHayDatos = new ReposicionHayDatos();
		
		reposicionHayDatos = this.ListadoReposicionDaoSIA.hayDatosReposicion(reposicion);

		return reposicionHayDatos;
	}


	@Override
	public Reposicion borrarReposicion(final Reposicion reposicion) throws Exception {

		Reposicion reposicionSalida = new Reposicion();
				
		reposicionSalida = this.ListadoReposicionDaoSIA.borrarReposicion(reposicion);

		return reposicionSalida;
	}

	
	@Override
	public ReposicionGuardar guardarReposicion(final ReposicionGuardar reposicionGuardar) throws Exception {

		ReposicionGuardar reposicionGuardarSalida = new ReposicionGuardar();
				
		reposicionGuardarSalida = this.ListadoReposicionDaoSIA.guardarReposicion(reposicionGuardar);

		return reposicionGuardarSalida;
	}
	
	
	@Override
	public Reposicion finalizarTareaReposicion(final Reposicion reposicion) throws Exception {

		Reposicion reposicionSalida = new Reposicion();
				
		reposicionSalida = this.ListadoReposicionDaoSIA.finalizarTareaReposicion(reposicion);

		return reposicionSalida;
	}
	
	
	@Override
	public Reposicion obtenerReposicion(final Reposicion reposicion) throws Exception {

		Reposicion reposicionSalida = new Reposicion();
				
		reposicionSalida = this.ListadoReposicionDaoSIA.obtenerReposicion(reposicion);

		return reposicionSalida;
	}
	
	
	

	@Override
	public void eliminarTempListadoRepo(String codMac) throws Exception {
		
		this.ListadoReposicionDao.eliminarTempListadoRepo(codMac);

	}
	
	
	@Override
	public void insertarTempListadoRepo(Reposicion reposicion) throws Exception {
		
		this.ListadoReposicionDao.insertarTempListadoRepo(reposicion);

	}
	
	
	public List<ReposicionLinea> findTempListadoRepo(Reposicion reposicion, Pagination pagination)  throws Exception {
		
		List<ReposicionLinea> reposicionLineas = new ArrayList<ReposicionLinea>();
	
		reposicionLineas = this.ListadoReposicionDao.findTempListadoRepo(reposicion,pagination);

		return reposicionLineas;
	}
	
	
	public Long countTempListadoRepo(String codMac) throws Exception {
		
		Long totalReg = null;
	
		totalReg = this.ListadoReposicionDao.countTempListadoRepo(codMac);

		return totalReg;
	}


	@Override
	public ReposicionCmbSeccion obtenerSecciones(Reposicion reposicion) throws Exception {
		// TODO Auto-generated method stub
		ReposicionCmbSeccion reposicionCmbSeccion =  this.ListadoReposicionDaoSIA.obtenerSecciones(reposicion);
		return reposicionCmbSeccion ;
	}


	@Override
	public ReposicionDatosTalla obtenerDatosAdicionalesTalla(Reposicion reposicion) throws Exception {
		// TODO Auto-generated method stub
		ReposicionDatosTalla reposicionDatosTalla = this.ListadoReposicionDaoSIA.obtenerDatosAdicionalesTalla(reposicion);
		return reposicionDatosTalla;
	}
}
