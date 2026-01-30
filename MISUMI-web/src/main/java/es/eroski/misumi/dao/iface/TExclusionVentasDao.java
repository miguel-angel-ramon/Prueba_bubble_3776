package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.TExclusionVentas;
import es.eroski.misumi.model.ui.Pagination;

public interface TExclusionVentasDao  {

	public void delete(TExclusionVentas tExclusionVentas) throws Exception;
	public void deleteHistorico() throws Exception;
	public void updateErrores(TExclusionVentas tExclusionVentas) throws Exception;	
	public void insertAll(List<TExclusionVentas> listaTExclusionVentas) throws Exception;
	public List<TExclusionVentas> findAllPaginate(TExclusionVentas tExclusionVentas, Pagination pagination) throws Exception;
	public void update(TExclusionVentas tExclusionVentas) throws Exception;

}
