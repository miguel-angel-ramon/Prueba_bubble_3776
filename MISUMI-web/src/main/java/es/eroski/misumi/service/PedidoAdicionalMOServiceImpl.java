package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalMODao;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.model.PedidoAdicionalMO;
import es.eroski.misumi.service.iface.PedidoAdicionalMOService;


@Service(value = "PedidoAdicionalMOService")
public class PedidoAdicionalMOServiceImpl implements PedidoAdicionalMOService {
    @Autowired
	private PedidoAdicionalMODao pedidoAdicionalDao;
    
	@Override
	 public List<PedidoAdicionalMO> findAll(PedidoAdicionalMO pedidoAdicionalMO, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.findAll(pedidoAdicionalMO, session);
	}
	@Override
	public List<PedidoAdicionalMO> removeAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.removeAll(listaPedidoAdicionalMO,session);
	}
	@Override
	public List<PedidoAdicionalMO> modifyAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, String tipoModificado, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.modifyAll(listaPedidoAdicionalMO,tipoModificado,session);
	}
	@Override
	public List<PedidoAdicionalMO> validateAll(List<PedidoAdicionalMO> listaPedidoAdicionalMO, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.validateAll(listaPedidoAdicionalMO, session);
	}
	@Override
	public List<PedidoAdicionalMO> findAllVegalsa(PedidoAdicionalMO pedidoAdicionalMO, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.findAllVegalsa(pedidoAdicionalMO, session);
	}
}
