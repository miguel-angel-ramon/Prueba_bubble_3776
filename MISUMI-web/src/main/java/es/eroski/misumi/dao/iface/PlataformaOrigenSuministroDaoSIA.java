package es.eroski.misumi.dao.iface;

import java.util.Date;

import es.eroski.misumi.model.PlataformaAprovisionamientoMercancia;

public interface PlataformaOrigenSuministroDaoSIA {

	public PlataformaAprovisionamientoMercancia obtOss(Long codArtMadre, Long codLoc, Date fechaHoy, Long contador) throws Exception;
}
