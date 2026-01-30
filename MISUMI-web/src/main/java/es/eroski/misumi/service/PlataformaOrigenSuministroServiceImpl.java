package es.eroski.misumi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PlataformaOrigenSuministroDaoSIA;
import es.eroski.misumi.model.PlataformaAprovisionamientoMercancia;
import es.eroski.misumi.service.iface.PlataformaOrigenSuministroService;

@Service(value = "PlataformaOrigenSuministroService")
public class PlataformaOrigenSuministroServiceImpl implements PlataformaOrigenSuministroService{
	
	@Autowired
	private PlataformaOrigenSuministroDaoSIA plataformaOrigenSuministroDaoSIA;
	
	public PlataformaAprovisionamientoMercancia obtOss(final Long codArtMadre, final Long codLoc, final Date fechaHoy, final Long contador) throws Exception {
		return plataformaOrigenSuministroDaoSIA.obtOss(codArtMadre, codLoc, fechaHoy, contador);
	}
}
