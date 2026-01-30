package es.eroski.misumi.service.iface;

import java.util.Date;

import es.eroski.misumi.model.PlataformaAprovisionamientoMercancia;

public interface PlataformaOrigenSuministroService {
	public PlataformaAprovisionamientoMercancia obtOss(final Long codArtMadre, final Long codLoc, final Date fechaHoy, final Long contador) throws Exception;
}
