package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ConsumoRapidoDao;
import es.eroski.misumi.model.ConsumoRapido;
import es.eroski.misumi.service.iface.ConsumoRapidoService;


@Service(value = "ConsumoRapidoService")
public class ConsumoRapidoServiceImpl implements ConsumoRapidoService {
	
	@Autowired
	private ConsumoRapidoDao consumoRapidoDao;

	@Override
	public List<ConsumoRapido> findAll(Long codCentro, Long area, Long seccion, String fechaIni, String fechaFin, String index, String sortOrder) throws Exception {
		return consumoRapidoDao.findAll(codCentro, area, seccion, fechaIni, fechaFin, index, sortOrder);
	}

}
