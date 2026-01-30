package es.eroski.misumi.service.prehuecos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.prehuecos.iface.PrehuecosLinealDao;
import es.eroski.misumi.model.pda.prehuecos.PrehuecosLineal;
import es.eroski.misumi.service.prehuecos.iface.PrehuecosLinealService;

@Service
public class PrehuecosLinealServiceImpl implements PrehuecosLinealService{

	@Autowired
	PrehuecosLinealDao	prehuecosLinealDao;
	
	@Override
	public Long getPrehuecosLineal(PrehuecosLineal prehuecosLineal) throws Exception {
    	return prehuecosLinealDao.getPrehuecosSinValidar(prehuecosLineal, Boolean.FALSE);
	}

	@Override
	public Long getPrehuecosSinValidar(PrehuecosLineal prehuecosLineal) throws Exception {
    	return prehuecosLinealDao.getPrehuecosSinValidar(prehuecosLineal, Boolean.TRUE);
	}

	@Override
	public PrehuecosLineal getStockLinealEstadoRef(PrehuecosLineal prehuecosLineal) throws Exception {
		return prehuecosLinealDao.getStockLinealEstadoRef(prehuecosLineal);
	}

	@Override
	public int deletePrehuecos(PrehuecosLineal prehuecosLineal) throws Exception {
		return prehuecosLinealDao.deletePrehuecos(prehuecosLineal);
	}

	@Override
	public int insertPrehuecos(PrehuecosLineal prehuecosLineal) throws Exception {
		return prehuecosLinealDao.insertPrehuecos(prehuecosLineal);
	}

	@Override
	public int updatePrehuecos(PrehuecosLineal prehuecosLineal) {
		return prehuecosLinealDao.updatePrehuecos(prehuecosLineal);
	}

}
