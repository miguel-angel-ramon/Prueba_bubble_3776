package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.InformeListado;
import es.eroski.misumi.model.InformeListadoPesca;
import es.eroski.misumi.model.PescaPedirHoy;
import es.eroski.misumi.model.VPescaMostrador;

public interface InformeListadoService {
	public Long countAllInformeListadoPesca(Long codCentro) throws Exception;
	public List<InformeListadoPesca> findAllInformeListadoPesca(Long codCentro) throws Exception;
	public List<InformeListadoPesca> findAllInformeListadoPesca(Long codCentro,String flgHabitual) throws Exception;
	public List<VPescaMostrador> findAllVPescaMostrador(Long codCentro, List<String> listaSubcategoria) throws Exception;
	public List<VPescaMostrador> findAllVPescaMostrador(Long codCentro, String idSubcategoria, String flgHabitual) throws Exception;
	public Long getInformeListadoCount(Long codCentro) throws Exception;
	public InformeListado obtenerInformeListado(Long codCentro) throws Exception;
	public InformeListado obtenerInformeListado(Long codCentro,String flgHabitual) throws Exception;
	public String obtenerDescSubcategoria(Long codCentro, String idSubcategoria) throws Exception;
	public PescaPedirHoy findPescaPedirHoy(Long codCentro, List<VPescaMostrador> listaPesca) throws Exception;
	public List<VPescaMostrador> searchPescaPedirHoy(List<VPescaMostrador> listaPesca, PescaPedirHoy pescaPedirHoy) throws Exception;
	public List<String> listaCodigosHabituales(Long codCentro, List<String> listaSubcategoria) throws Exception ;
}
