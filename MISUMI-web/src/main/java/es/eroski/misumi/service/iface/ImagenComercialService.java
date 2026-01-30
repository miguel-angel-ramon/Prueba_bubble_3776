package es.eroski.misumi.service.iface;

import es.eroski.misumi.model.ImagenComercial;

public interface ImagenComercialService {
	public ImagenComercial consultaImc(final Long codLoc, final Long codArtFormlog);
	public ImagenComercial simularImc(ImagenComercial imagenComercial);
	public ImagenComercial modificarImc(ImagenComercial imagenComercial);
	
}
