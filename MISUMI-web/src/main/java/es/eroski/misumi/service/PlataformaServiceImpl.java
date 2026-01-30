package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlataformaDaoSIA;
import es.eroski.misumi.service.iface.PlataformaService;

@Service(value = "PlataformaService")
public class PlataformaServiceImpl implements PlataformaService{
	 @Autowired
	private PlataformaDaoSIA plataformaDaoSIA;
	 
	@Override
	public String estaEnModoEmpuje(Long codCentro, Long codArt) {
		// TODO Auto-generated method stub
		return plataformaDaoSIA.estaEnModoEmpuje(codCentro, codArt);
	}

}
