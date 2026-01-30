package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VAlbaranesDao;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.VAlbaran;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAlbaranesService;

@Service(value = "VAlbaranesService")
public class VAlbaranesServiceImpl implements VAlbaranesService {
	
	@Autowired
	private VAlbaranesDao vAlbaranesDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<VAlbaran> findAll(VAlbaran vAlbaran, Pagination pagination) throws Exception {
		return this.vAlbaranesDao.findAll(vAlbaran,pagination);
	}
	
	@Override
	public Long findAllCont(VAlbaran vAlbaran) throws Exception {
		return this.vAlbaranesDao.findAllCont(vAlbaran);
	}
	
	@Override
	public List<VAlbaran> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, Pagination pagination) throws Exception {
		List<Long> listaReferencias = new ArrayList<Long>();
		if (null != seguimientoMiPedido.getCodArt()){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(seguimientoMiPedido.getCodArt());
			relacionArticulo.setCodCentro(seguimientoMiPedido.getCodCentro());
			listaReferencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		return this.vAlbaranesDao.findSeguimientoMiPedido(seguimientoMiPedido, listaReferencias, pagination);
	}
	
	@Override
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vAlbaranesDao.findSeguimientoMiPedidoCont(seguimientoMiPedido);
	}
}
