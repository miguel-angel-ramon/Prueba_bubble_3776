package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CausasInactividadDao;
import es.eroski.misumi.model.CausaInactividad;
import es.eroski.misumi.service.iface.CausasInactividadService;

@Service(value = " CausasInactividadService")
public class CausasInactividadServiceImpl implements CausasInactividadService  {
	
	@Autowired
	private CausasInactividadDao causasInactividadDao;

	/* (non-Javadoc)
	 * @see es.eroski.misumi.dao.CausasInactividadDao#findAll()
	 */

	/* (non-Javadoc)
	 * @see es.eroski.misumi.service.CausasInactividadService#findAll()
	 */
	@Override
	public List<CausaInactividad> findAll() throws Exception {
		return this.causasInactividadDao.findAll();
	}

	
}
