package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.StockPlataformaDao;
import es.eroski.misumi.model.StockPlataforma;
import es.eroski.misumi.service.iface.StockPlataformaService;

@Service (value = "StockPlataformaService")
public class StockPlataformaServiceImpl implements StockPlataformaService{
	
	@Autowired
	private StockPlataformaDao stockPlataformaDao;

	@Override  
	public StockPlataforma find(StockPlataforma sp) throws Exception{
		return stockPlataformaDao.find(sp);
	}

}