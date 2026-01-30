package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VMisSeguimientosDao;
import es.eroski.misumi.model.ReferenciasPedido;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.VMisSeguimientos;
import es.eroski.misumi.service.iface.VMisSeguimientosService;

@Service(value = "VMisSeguimientosService")
public class VMisSeguimientosServiceImpl implements VMisSeguimientosService {
	
	@Autowired
	private VMisSeguimientosDao vMisSeguimientosDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<VMisSeguimientos> findAll(VMisSeguimientos vMisSeguimientos, boolean controlFechaActual) throws Exception {
		return this.vMisSeguimientosDao.findAll(vMisSeguimientos, controlFechaActual);
	}

	@Override
	public List<ReferenciasPedido> findAllReferenciasPedido(VMisSeguimientos vMisSeguimientos) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (vMisSeguimientos.getTipo().equals("A")){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisSeguimientos.getCodArt());
			relacionArticulo.setCodCentro(vMisSeguimientos.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
	
		return this.vMisSeguimientosDao.findAllReferenciasPedido(vMisSeguimientos, referencias);
	}

	@Override
	public VMisSeguimientos recargaDatosTienda(VMisSeguimientos vMisSeguimientos) throws Exception {
		return this.vMisSeguimientosDao.recargaDatosTienda(vMisSeguimientos);
	}
}
