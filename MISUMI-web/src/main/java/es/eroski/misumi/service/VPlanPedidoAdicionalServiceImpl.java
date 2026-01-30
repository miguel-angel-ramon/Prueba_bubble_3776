package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VPlanPedidoAdicionalDao;
import es.eroski.misumi.model.VPlanPedidoAdicional;
import es.eroski.misumi.service.iface.VPlanPedidoAdicionalService;

@Service(value = "VPlanPedidoAdicionalService")
public class VPlanPedidoAdicionalServiceImpl implements VPlanPedidoAdicionalService {
    @Autowired
	private VPlanPedidoAdicionalDao vPlanPedidoAdicionalDao;
	
    @Override
	 public List<VPlanPedidoAdicional> findAll(VPlanPedidoAdicional vPlanPedidoAdicional) throws Exception {
		return this.vPlanPedidoAdicionalDao.findAll(vPlanPedidoAdicional);
	}
}
