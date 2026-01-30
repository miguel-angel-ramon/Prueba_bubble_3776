package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.TEncargosClteDao;
import es.eroski.misumi.model.TEncargosClte;
import es.eroski.misumi.service.iface.TEncargosClteService;

@Service(value = "TEncargosClteService")
public class TEncargosClteServiceImpl implements TEncargosClteService {
    @Autowired
	private TEncargosClteDao tEncargosClteDao;
    
    @Override
	 public void delete(TEncargosClte tEncargosClte) throws Exception {
		this.tEncargosClteDao.delete(tEncargosClte);
	}
    @Override
    public void deleteHistorico() throws Exception {
		this.tEncargosClteDao.deleteHistorico();
	}
    @Override
	 public void deleteEncargo(TEncargosClte tEncargosClte) throws Exception {
		this.tEncargosClteDao.deleteEncargo(tEncargosClte);
	}
    @Override
    public void updateErrorEncargo(TEncargosClte tEncargosClte) throws Exception{
    	this.tEncargosClteDao.updateErrorEncargo(tEncargosClte);
    }

    @Override
    public void updateEncargo(TEncargosClte tEncargosClte) throws Exception{
    	this.tEncargosClteDao.updateEncargo(tEncargosClte);
    }

    @Override
    public void updateErrores(TEncargosClte tEncargosClte) throws Exception{
    	this.tEncargosClteDao.updateErrores(tEncargosClte);
    }
    
    @Override
	 public void insertAll(List<TEncargosClte> listaTEncargosClte) throws Exception {
		this.tEncargosClteDao.insertAll(listaTEncargosClte);
	}
    
    @Override
	 public List<TEncargosClte> findAll(TEncargosClte tEncargosClte) throws Exception {
		return this.tEncargosClteDao.findAll(tEncargosClte);
	}

    @Override
	 public List<TEncargosClte> findAllDetalle(TEncargosClte tEncargosClte) throws Exception {
		return this.tEncargosClteDao.findAllDetalle(tEncargosClte);
	}

    @Override
	 public TEncargosClte findOne(TEncargosClte tEncargosClte) throws Exception {
		return this.tEncargosClteDao.findOne(tEncargosClte);
	}

    @Override
	 public Long findAllCount(TEncargosClte tEncargosClte) throws Exception {
		return this.tEncargosClteDao.findAllCount(tEncargosClte);
	}
    
    @Override
    public List<TEncargosClte> findAllExcel(TEncargosClte tEncargosClte) throws Exception {
    	List<TEncargosClte> listaEncargosExcel = new ArrayList<TEncargosClte>();
    	List<TEncargosClte> listaEncargos =  this.tEncargosClteDao.findAll(tEncargosClte);
    	for(TEncargosClte encargo : listaEncargos){
    		encargo.setPadre(true);
    		listaEncargosExcel.add(encargo);
    		if (encargo.getNivel().equals(new Long(2))){
    			List<TEncargosClte> listaDetalle = this.tEncargosClteDao.findAllDetalle(encargo);
        		listaEncargosExcel.addAll(listaDetalle);	
    		}	
    	}
    	
    	return listaEncargosExcel;
    }
}
