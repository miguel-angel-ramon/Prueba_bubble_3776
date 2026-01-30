package es.eroski.misumi.dao.iface;

import es.eroski.misumi.model.ImagenComercial;

public interface ImagenComercialDao {
	
	/**
	 * Consulta si la porcion consumidor es de peso variable.
	 * @param codArticulo Codigo de articulo
	 * @return "S" si es de peso variable.
	 */
	public String consultarPcPesoVariable(Long codArticulo);

	public ImagenComercial consultaImc(Long codLoc, Long codArtFormlog);

	public ImagenComercial simularImc(ImagenComercial imagenComercial);

	public ImagenComercial modificarImc(ImagenComercial imagenComercial);
}
