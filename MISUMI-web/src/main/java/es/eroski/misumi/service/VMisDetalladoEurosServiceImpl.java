package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VMisDetalladoEurosDao;
import es.eroski.misumi.model.DetalladoEuros;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.VMisDetalladoEuros;
import es.eroski.misumi.service.iface.VMisDetalladoEurosService;

@Service(value = "VMisDetalladoEurosService")
public class VMisDetalladoEurosServiceImpl implements VMisDetalladoEurosService{
	@Autowired
	private VMisDetalladoEurosDao vMisDetalladoEurosDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<DetalladoEuros> findAllReferenciasDetalladoEuros(VMisDetalladoEuros vMisDetalladoEuros)
			throws Exception {
		// TODO Auto-generated method stub
		List<Long> referencias = new ArrayList<Long>();
		if (vMisDetalladoEuros.getTipo().equals("A")){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisDetalladoEuros.getCodArt());
			relacionArticulo.setCodCentro(vMisDetalladoEuros.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
	
		return this.vMisDetalladoEurosDao.findAllReferenciasDetalladoEuros(vMisDetalladoEuros, referencias);
	}

}
