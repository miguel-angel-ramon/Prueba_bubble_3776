package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ConfirmacionNoServidoDao;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.StockNoServido;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ConfirmacionNoServidoService;

@Service(value = "ConfirmacionNoServidoService")
public class ConfirmacionNoServidoServiceImpl implements ConfirmacionNoServidoService {
	//private static Logger logger = LoggerFactory.getLogger(VConfirmacionNoServidoServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VConfirmacionNoServidoServiceImpl.class);
	
	@Autowired
	private ConfirmacionNoServidoDao confirmacionNoServidoDao;
	
	@Override
	public List<StockNoServido> findAllNoServidos(SeguimientoCampanas seguimientoCampanas, List<Long> listaReferencias, Pagination pagination)	throws Exception {
		return this.confirmacionNoServidoDao.findAllNoServidos(seguimientoCampanas, listaReferencias, pagination);
	}

	@Override
	public Long findAllNoServidosCont(SeguimientoCampanas seguimientoCampanas, List<Long> listaReferencias) throws Exception {
		return this.confirmacionNoServidoDao.findAllNoServidosCont(seguimientoCampanas, listaReferencias);
	}

}
