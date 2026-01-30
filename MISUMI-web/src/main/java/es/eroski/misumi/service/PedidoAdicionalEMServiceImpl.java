package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalEMDao;
import es.eroski.misumi.dao.iface.PedidosAdCentralDao;
import es.eroski.misumi.model.PedidoAdicionalEM;
import es.eroski.misumi.model.PedidosAdCentral;
import es.eroski.misumi.service.iface.PedidoAdicionalEMService;


@Service(value = "PedidoAdicionalEMService")
public class PedidoAdicionalEMServiceImpl implements PedidoAdicionalEMService {
    @Autowired
	private PedidoAdicionalEMDao pedidoAdicionalDao;
   
    
	@Override
	 public List<PedidoAdicionalEM> findAll(PedidoAdicionalEM pedidoAdicionalEM, HttpSession session) throws Exception {
		List<PedidoAdicionalEM> listPedidoEM =  this.pedidoAdicionalDao.findAll(pedidoAdicionalEM, session);
		
		return listPedidoEM;
	}
}
