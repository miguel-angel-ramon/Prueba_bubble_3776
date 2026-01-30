package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ExclusionVentas;

public interface ExclusionVentasDao  {

	public List<ExclusionVentas> findAll(ExclusionVentas exclusionVentas) throws Exception ;
	
	public List<ExclusionVentas> insertAll(List<ExclusionVentas> listaExclusionVentas) throws Exception ;	
	
	public List<ExclusionVentas> removeAll(List<ExclusionVentas> listaExclusionVentas) throws Exception ;
}
