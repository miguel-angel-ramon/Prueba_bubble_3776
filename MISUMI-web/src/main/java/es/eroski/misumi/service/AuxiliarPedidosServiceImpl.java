package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.AuxiliarPedidosDao;
import es.eroski.misumi.service.iface.AuxiliarPedidosService;

@Service(value = "AuxiliarPedidosService")
public class AuxiliarPedidosServiceImpl implements AuxiliarPedidosService {
    @Autowired
	private AuxiliarPedidosDao auxiliarPedidosDao;
	
    @Override
    public boolean existsAuxiliarPedidos() {
		return this.auxiliarPedidosDao.existsAuxiliarPedidos();
	}
	
   
}
