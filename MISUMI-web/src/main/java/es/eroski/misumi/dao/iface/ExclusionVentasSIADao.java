package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ExclusionVentas;
import es.eroski.misumi.model.ExclusionVentasSIA;

public interface ExclusionVentasSIADao {

	ExclusionVentasSIA consultaExclusion(ExclusionVentas exclusionVentas) throws Exception;

	ExclusionVentasSIA insertarExclusion(List<ExclusionVentas> listaExclusion) throws Exception;

	ExclusionVentasSIA borrarExclusion(List<ExclusionVentas> listaExclusion) throws Exception;

}