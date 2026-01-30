package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.CestasNavidad;
import es.eroski.misumi.model.CestasNavidadArticulo;
import es.eroski.misumi.model.ui.Pagination;


public interface CestasNavidadService {

	public List<CestasNavidad> findAll(Long codCentro,boolean esCentroCaprabo) throws Exception;
	public CestasNavidad findOne(Long codCentro, Long codArtLote, boolean esCentroCaprabo) throws Exception;
	public String findDescrition(Long codArtLote) throws Exception;
	public List<CestasNavidad> findAll(Pagination pagination) throws Exception;
	public int updateBorradoLinea(CestasNavidad cestasNavidad) throws Exception;
	public String delete();
	public List<CestasNavidadArticulo> findAllCestasNavidadArticulo(Long codArtLote) throws Exception;
	public String updateLinea(CestasNavidad cestasNavidad) throws Exception;
	public String insertLinea(CestasNavidad cestasNavidad) throws Exception;
	public void resetCestasNavidad() throws Exception;
	public int countSeleccionados();
	public int updateFechas(CestasNavidad cestasNavidad);
}
