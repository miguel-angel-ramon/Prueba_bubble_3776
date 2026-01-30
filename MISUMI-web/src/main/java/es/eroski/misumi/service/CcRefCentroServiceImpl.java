package es.eroski.misumi.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CcRefCentroDao;
import es.eroski.misumi.model.CcRefCentro;
import es.eroski.misumi.service.iface.CcRefCentroService;

@Service(value = "CcRefCentroService")
public class CcRefCentroServiceImpl implements CcRefCentroService {
	
	private static Logger logger = Logger.getLogger(CcRefCentroServiceImpl.class);

    @Autowired
	private CcRefCentroDao ccRefCentroDao;
	
    @Override
    public Long consultaCc(CcRefCentro ccRefCentro) throws Exception {
		return this.ccRefCentroDao.consultaCc(ccRefCentro);
	}
    
}
