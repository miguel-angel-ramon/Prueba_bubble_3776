package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VConfirmacionPedidoDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionPedido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VConfirmacionPedidoService;

@Service(value = "VConfirmacionPedidoService")
public class VConfirmacionPedidoServiceImpl implements VConfirmacionPedidoService {
	//private static Logger logger = LoggerFactory.getLogger(VConfirmacionPedidoServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VConfirmacionPedidoServiceImpl.class);
	
	@Autowired
	private VConfirmacionPedidoDao vConfirmacionPedidoDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<VConfirmacionPedido> findAll(VConfirmacionPedido vConfirmacionPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findAll(vConfirmacionPedido);
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
		return this.vConfirmacionPedidoDao.findSeguimientoMiPedido(seguimientoMiPedido, listaReferencias, pagination);
	}

	@Override
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findSeguimientoMiPedidoCont(seguimientoMiPedido);
	}
	
	@Override
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception {
		return this.vConfirmacionPedidoDao.findSeguimientoMiPedidoExcel(seguimientoMiPedido, columnModel);
	}

	@Override
	public Long findTotalReferenciasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalReferenciasBajoPedido(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalCajasBajoPedido(seguimientoMiPedido);
	}

	@Override
	public Long findTotalReferenciasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalReferenciasEmpuje(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalCajasEmpuje(seguimientoMiPedido);
	}

	@Override
	public Long findTotalReferenciasImplCab(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalReferenciasImplCab(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalCajasImplCab(seguimientoMiPedido);
	}
	


	@Override
	public Long findTotalReferenciasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalReferenciasIntertienda(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findTotalCajasIntertienda(seguimientoMiPedido);
	}

    
	@Override
	public VConfirmacionPedido findDetalleTipoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionPedidoDao.findDetalleTipoPedido(seguimientoMiPedido);
	}
	
	/*
	 *  MISUMI-355
	 */
	@Override
	public Boolean isCentroVegalsa(Long codCentro) throws Exception {
		return this.vConfirmacionPedidoDao.countCentroVegalsa(codCentro) > 0 ? true : false;
	}
	
	@Override
	public boolean hasAjustePedido(Long codCentro) {
		return this.vConfirmacionPedidoDao.findLineasPedidoAjusteCont(codCentro) > 0 ? true : false; 
	}

}
