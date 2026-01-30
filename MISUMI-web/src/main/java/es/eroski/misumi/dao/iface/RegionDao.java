package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Region;

public interface RegionDao  {

	  public List<Region> findAll(Region region) throws Exception;
}
