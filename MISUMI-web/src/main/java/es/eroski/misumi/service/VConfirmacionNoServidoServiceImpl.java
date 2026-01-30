package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VConfirmacionNoServidoDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionNoServido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VConfirmacionNoServidoService;

@Service(value = "VConfirmacionNoServidoService")
public class VConfirmacionNoServidoServiceImpl implements VConfirmacionNoServidoService {
	//private static Logger logger = LoggerFactory.getLogger(VConfirmacionNoServidoServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VConfirmacionNoServidoServiceImpl.class);
	
	@Autowired
	private VConfirmacionNoServidoDao vConfirmacionNoServidoDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<VConfirmacionNoServido> findAll(VConfirmacionNoServido vConfirmacionNoServido) throws Exception {
		return this.vConfirmacionNoServidoDao.findAll(vConfirmacionNoServido);
	}
	
	@Override
	public List<SeguimientoMiPedidoDetalle> findSeguimientoMiPedido(SeguimientoMiPedido seguimientoMiPedido, Pagination pagination) throws Exception {
		List<Long> listaReferencias = new ArrayList<Long>();
		if (null != seguimientoMiPedido.getCodArt()){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(seguimientoMiPedido.getCodArt());
			relacionArticulo.setCodCentro(seguimientoMiPedido.getCodCentro());
			listaReferencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		return this.vConfirmacionNoServidoDao.findSeguimientoMiPedido(seguimientoMiPedido, listaReferencias, pagination);
	}

	@Override
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionNoServidoDao.findSeguimientoMiPedidoCont(seguimientoMiPedido);
	}
	
	@Override
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception {
		return this.vConfirmacionNoServidoDao.findSeguimientoMiPedidoExcel(seguimientoMiPedido, columnModel);
	}

	@Override
	public Long findTotalReferenciasNoServidas(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionNoServidoDao.findTotalReferenciasNoServidas(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasNoServidas(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionNoServidoDao.findTotalCajasNoServidas(seguimientoMiPedido);
	}
	
	@Override
	public Boolean checkNSR(SeguimientoMiPedido seguimientoPedido){
		return this.vConfirmacionNoServidoDao.checkNSR(seguimientoPedido);
	}
	
	/*
	 *  MISUMI-355
	 */
	@Override
	public Boolean isCentroVegalsa(Long codCentro) throws Exception {
		return this.vConfirmacionNoServidoDao.countCentroVegalsa(codCentro) > 0 ? true : false;
	}

}
