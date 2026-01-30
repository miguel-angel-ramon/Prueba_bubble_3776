package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.EstadoConvergenciaDao;
import es.eroski.misumi.model.EstadoConvergencia;
import es.eroski.misumi.service.iface.EstadoConvergenciaService;

@Service(value = "EstadoConvergenciaService")
public class EstadoConvergenciaServiceImpl implements EstadoConvergenciaService {
	
    @Autowired
	private EstadoConvergenciaDao estadoConvergenciaDao;
	
    @Override
    public String consultaEstadoConvergencia(EstadoConvergencia estadoConvergencia) throws Exception {
		return this.estadoConvergenciaDao.consultaEstadoConvergencia(estadoConvergencia);
	}
    
}
