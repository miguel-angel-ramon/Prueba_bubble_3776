package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.MotivoTengoMuchoPocoDao;
import es.eroski.misumi.model.MotivoTengoMuchoPoco;
import es.eroski.misumi.model.MotivoTengoMuchoPocoLista;
import es.eroski.misumi.service.iface.MotivoTengoMuchoPocoService;

@Service(value = "MotivoTengoMuchoPocoService")
public class MotivoTengoMuchoPocoServiceImpl implements MotivoTengoMuchoPocoService {

	@Autowired
	private MotivoTengoMuchoPocoDao motivoTengoMuchoPocoDao;
	
	@Override
	 public MotivoTengoMuchoPocoLista consultaMotivosTengoMuchoPoco(MotivoTengoMuchoPoco motivoTengoMuchoPoco) throws Exception {
		return this.motivoTengoMuchoPocoDao.consultaMotivosTengoMuchoPoco(motivoTengoMuchoPoco);
	}
}
