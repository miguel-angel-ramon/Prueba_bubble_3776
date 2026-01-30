package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CampanasOfertasMisumiDao;
import es.eroski.misumi.model.CampanasOfertasMisumi;
import es.eroski.misumi.service.iface.CampanasOfertasMisumiService;

@Service(value = "CampanasOfertasMisumiService")
public class CampanasOfertasMisumiServiceImpl implements CampanasOfertasMisumiService {
    
	@Autowired
	private CampanasOfertasMisumiDao campanasOfertasMisumiDao;
	
	@Override
	 public List<CampanasOfertasMisumi> findAll(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception {
		return this.campanasOfertasMisumiDao.findAll(campanasOfertasMisumi);
	}

	@Override
	 public List<CampanasOfertasMisumi> findCampanas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception {
		return this.campanasOfertasMisumiDao.findCampanas(campanasOfertasMisumi);
	}
	
	@Override
	 public List<CampanasOfertasMisumi> findOfertas(CampanasOfertasMisumi campanasOfertasMisumi) throws Exception {
		return this.campanasOfertasMisumiDao.findOfertas(campanasOfertasMisumi);
	}
}
