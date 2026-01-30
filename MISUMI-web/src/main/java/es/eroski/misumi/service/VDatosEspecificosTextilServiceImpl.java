package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VDatosEspecificosTextilDao;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.service.iface.VDatosEspecificosTextilService;

@Service
public class VDatosEspecificosTextilServiceImpl implements VDatosEspecificosTextilService {


	@Autowired
	private VDatosEspecificosTextilDao vDatosEspecificosTextilDao;
	
	@Override
	public PedidoAdicionalE findAll(PedidoAdicionalE pedidoAdicionalE)
			throws Exception {
		
		try {
			return this.vDatosEspecificosTextilDao.findTextil(pedidoAdicionalE);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			return new PedidoAdicionalE();
		}
	}

}
