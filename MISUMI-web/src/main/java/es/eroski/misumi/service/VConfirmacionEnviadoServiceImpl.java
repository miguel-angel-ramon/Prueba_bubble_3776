package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VConfirmacionEnviadoDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoMiPedido;
import es.eroski.misumi.model.SeguimientoMiPedidoDetalle;
import es.eroski.misumi.model.VConfirmacionEnviado;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VConfirmacionEnviadoService;

@Service(value = "VConfirmacionEnviadoService")
public class VConfirmacionEnviadoServiceImpl implements VConfirmacionEnviadoService {
	//private static Logger logger = LoggerFactory.getLogger(VConfirmacionEnviadoServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VConfirmacionEnviadoServiceImpl.class);
	
	@Autowired
	private VConfirmacionEnviadoDao vConfirmacionEnviadoDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<VConfirmacionEnviado> findAll(VConfirmacionEnviado vConfirmacionEnviado) throws Exception {
		return this.vConfirmacionEnviadoDao.findAll(vConfirmacionEnviado);
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
		return this.vConfirmacionEnviadoDao.findSeguimientoMiPedido(seguimientoMiPedido, listaReferencias, pagination);
	}

	@Override
	public Long findSeguimientoMiPedidoCont(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findSeguimientoMiPedidoCont(seguimientoMiPedido);
	}
	
	@Override
	public List<GenericExcelVO> findSeguimientoMiPedidoExcel(SeguimientoMiPedido seguimientoMiPedido, String[] columnModel) throws Exception {
		return this.vConfirmacionEnviadoDao.findSeguimientoMiPedidoExcel(seguimientoMiPedido, columnModel);
	}

	@Override
	public Long findTotalReferenciasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalReferenciasBajoPedido(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasBajoPedido(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalCajasBajoPedido(seguimientoMiPedido);
	}

	@Override
	public Long findTotalReferenciasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalReferenciasEmpuje(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasEmpuje(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalCajasEmpuje(seguimientoMiPedido);
	}

	@Override
	public Long findTotalReferenciasImplCab(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalReferenciasImplCab(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasImplCab(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalCajasImplCab(seguimientoMiPedido);
	}
	
	@Override
	public Long findTotalReferenciasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalReferenciasIntertienda(seguimientoMiPedido);
	}

	@Override
	public Float findTotalCajasIntertienda(SeguimientoMiPedido seguimientoMiPedido) throws Exception {
		return this.vConfirmacionEnviadoDao.findTotalCajasIntertienda(seguimientoMiPedido);
	}

	/*
	 *  MISUMI-355
	 */
	@Override
	public Boolean isCentroVegalsa(Long codCentro) throws Exception {
		return this.vConfirmacionEnviadoDao.countCentroVegalsa(codCentro) > 0 ? true : false;
	}
}
