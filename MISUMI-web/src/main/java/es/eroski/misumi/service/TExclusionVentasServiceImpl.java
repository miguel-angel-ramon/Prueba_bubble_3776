package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.TExclusionVentasDao;
import es.eroski.misumi.model.TExclusionVentas;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.TExclusionVentasService;

@Service(value = "TExclusionVentasService")
public class TExclusionVentasServiceImpl implements TExclusionVentasService {
    @Autowired
	private TExclusionVentasDao tExclusionVentasDao;
    
    @Override
	 public void delete(TExclusionVentas tExclusionVentas) throws Exception {
		this.tExclusionVentasDao.delete(tExclusionVentas);
	}
    @Override
    public void deleteHistorico() throws Exception {
		this.tExclusionVentasDao.deleteHistorico();
	}
    @Override
    public void updateErrores(TExclusionVentas tExclusionVentas) throws Exception{
    	this.tExclusionVentasDao.updateErrores(tExclusionVentas);
    }
    @Override
	 public void insertAll(List<TExclusionVentas> listaTExclusionVentas) throws Exception {
		this.tExclusionVentasDao.insertAll(listaTExclusionVentas);
	}
    
    @Override
	public TExclusionVentas findOne(TExclusionVentas tExclusionVentas) throws Exception {
    	TExclusionVentas tExclusionVentasRes = null; 
		List<TExclusionVentas> lista = this.tExclusionVentasDao.findAllPaginate(tExclusionVentas, null);
		if (lista != null && lista.size() > 0){
			tExclusionVentasRes = lista.get(0);
		}
		return tExclusionVentasRes;
	}

    @Override
	 public List<TExclusionVentas> findAllPaginate(TExclusionVentas tExclusionVentas, Pagination pagination) throws Exception {
		return this.tExclusionVentasDao.findAllPaginate(tExclusionVentas, pagination);
	}
    
    @Override
    public void update(TExclusionVentas tExclusionVentas) throws Exception{
    	this.tExclusionVentasDao.update(tExclusionVentas);
    }
}
