package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalMDao;
import es.eroski.misumi.model.PedidoAdicionalM;
import es.eroski.misumi.service.iface.PedidoAdicionalMService;


@Service(value = "PedidoAdicionalMService")
public class PedidoAdicionalMServiceImpl implements PedidoAdicionalMService {
    @Autowired
	private PedidoAdicionalMDao pedidoAdicionalDao;
	@Override
	 public List<PedidoAdicionalM> findAll(PedidoAdicionalM pedidoAdicionalM, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.findAll(pedidoAdicionalM, session);
	}
	@Override
	public List<PedidoAdicionalM> removeAll(List<PedidoAdicionalM> listaPedidoAdicionalM, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.removeAll(listaPedidoAdicionalM,session);
	}
	@Override
	public List<PedidoAdicionalM> modifyAll(List<PedidoAdicionalM> listaPedidoAdicionalM, String tipoModificado, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.modifyAll(listaPedidoAdicionalM, tipoModificado, session);
	}
	@Override
	public List<PedidoAdicionalM> validateAll(List<PedidoAdicionalM> listaPedidoAdicionalM, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.validateAll(listaPedidoAdicionalM, session);
	}
	@Override
	public List<PedidoAdicionalM> findAllVegalsa(PedidoAdicionalM pedidoAdicionalM, HttpSession session) {
		return this.pedidoAdicionalDao.findAllVegalsa(pedidoAdicionalM, session);
	}
}
