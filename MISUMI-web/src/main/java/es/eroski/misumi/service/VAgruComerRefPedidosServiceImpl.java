package es.eroski.misumi.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.eroski.misumi.dao.iface.VAgruComerRefPedidosDao;
import es.eroski.misumi.model.VAgruComerRefPedidos;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VAgruComerRefPedidosService;

@Service(value = "VAgruComerRefPedidosService")
public class VAgruComerRefPedidosServiceImpl implements VAgruComerRefPedidosService {
    @Autowired
	private VAgruComerRefPedidosDao vAgruComerRefPedidosDao;
	@Override
	 public List<VAgruComerRefPedidos> findAll(VAgruComerRefPedidos vAgruComerRefPedidos, Pagination pagination) throws Exception {
		return this.vAgruComerRefPedidosDao.findAll(vAgruComerRefPedidos, pagination);
	}
	
}
