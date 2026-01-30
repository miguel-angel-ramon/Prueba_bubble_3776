package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.CestasNavidadArticulo;

public interface CestasNavidadArticuloDao {
	public List<CestasNavidadArticulo> findAll(Long codArtLote)throws Exception;
	public void deleteCestasNavidadArticulo(Long codArtLote, Long idCestasNavidadArticulo) throws Exception;

	public int deleteArticuloLote(List<CestasNavidadArticulo> lstBorrados);
	public int updateArticuloLote(List<CestasNavidadArticulo> lstModificados);
	public int newArticuloLote(List<CestasNavidadArticulo> lstNuevos);
}
