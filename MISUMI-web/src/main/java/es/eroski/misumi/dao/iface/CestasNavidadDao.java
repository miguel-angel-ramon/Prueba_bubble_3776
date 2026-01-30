package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.ui.Pagination;

public interface CestasNavidadDao  {

	public List<CestasNavidad> findAll(Long codCentro, boolean esCentroCaprabo) throws Exception;

	public CestasNavidad findOne(Long codCentro, Long codArticuloLote, boolean esCentroCaprabo) throws Exception;

	public String findDescription(Long codArtLote) throws Exception;

	public List<CestasNavidad> findAll(Pagination pagination) throws Exception;

	public int updateBorradoLinea(CestasNavidad cestasNavidad) throws Exception;

	public String delete();

	public int updateLinea(CestasNavidad cestasNavidad);
	
	public int insertLinea(CestasNavidad cestasNavidad);

	public int countSeleccionados();

	public int updateFechas(CestasNavidad cestasNavidad);
}
