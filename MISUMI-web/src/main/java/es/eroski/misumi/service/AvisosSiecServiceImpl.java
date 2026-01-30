package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.AvisosSiecDao;
import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.AvisosSiec;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.AvisosSiecService;

@Service(value = "AvisosSiecService")
public class AvisosSiecServiceImpl implements AvisosSiecService {

	@Autowired
	private AvisosSiecDao avisosSiecDao;


	@Override
	public List<AvisosSiec> findAll(Pagination pagination) throws Exception {
		return this.avisosSiecDao.findAll(pagination);
	}

	@Override
	public int deleteRows(AvisosSiec avisosSiec) throws Exception{
		// TODO Auto-generated method stub
		return this.avisosSiecDao.deleteRows(avisosSiec);
	}
	
	@Override
	public List<Aviso> obtenerAvisosSiec(Long centro) throws Exception{
		// TODO Auto-generated method stub
		return this.avisosSiecDao.obtenerAvisosSiec(centro);
	}
	
	@Override
	public AvisosSiec findAvisoSiec(String codAviso) throws Exception {
		// TODO Auto-generated method stub
		return this.avisosSiecDao.findAvisoSiec(codAviso);
	}
	
	@Override
	public String updateLinea(AvisosSiec avisosSiec, String user) throws Exception {
		return this.avisosSiecDao.updateLinea(avisosSiec, user);		
	}

	@Override
	public String insertLinea(AvisosSiec avisosSiec, String user) throws Exception{
		return this.avisosSiecDao.insertLinea(avisosSiec, user);
	}
	
}
