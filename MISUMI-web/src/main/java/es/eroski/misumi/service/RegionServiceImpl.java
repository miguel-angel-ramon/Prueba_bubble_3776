package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RegionDao;
import es.eroski.misumi.model.Region;
import es.eroski.misumi.service.iface.RegionService;

@Service(value = "RegionService")
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;
	
	@Override
	 public List<Region> findAll(Region region) throws Exception {
		return this.regionDao.findAll(region);
	}
	
}
