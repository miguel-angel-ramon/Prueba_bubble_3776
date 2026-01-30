package es.eroski.misumi.service.iface;

import java.util.LinkedHashMap;
import java.util.List;

import es.eroski.misumi.model.InventarioRotativo;
import es.eroski.misumi.model.pda.PdaDatosInventarioLibre;
import es.eroski.misumi.model.ui.Pagination;


public interface InventarioRotativoService {
	
	 public void insert(InventarioRotativo inventarioRotativo) throws Exception;  
	 public void insertUpdate(InventarioRotativo inventarioRotativo) throws Exception;
	 public void updateRefConRelaciones(InventarioRotativo inventarioRotativo) throws Exception;
	 public void delete(InventarioRotativo inventarioRotativo) throws Exception;
	 public void deleteGuardados(InventarioRotativo inventarioRotativo) throws Exception;
	 public void deleteRefSinRelaciones(InventarioRotativo inventarioRotativo) throws Exception;
	 public void deleteUnitario(InventarioRotativo inventarioRotativo) throws Exception;
	 public void updateErrorAvisoArticulo(InventarioRotativo inventarioRotativo) throws Exception;
	 public List<InventarioRotativo> findAll(InventarioRotativo inventarioRotativo) throws Exception;
	 public List<InventarioRotativo> findAllPaginate(InventarioRotativo inventarioRotativo, Pagination pagination) throws Exception;
	 public Long findAllCount(InventarioRotativo inventarioRotativo) throws Exception;
	 public List<PdaDatosInventarioLibre> findAllPda(PdaDatosInventarioLibre pdaDatosInventarioLibre, boolean bolPrincipal) throws Exception;
	 public LinkedHashMap<Long, String> findSeccionesPda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
	 public boolean existenDatosInventarioLibrePda(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
	 public PdaDatosInventarioLibre findSumaCantidades(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
	 public PdaDatosInventarioLibre findSumaCantidadesRefConRelaciones(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
     public PdaDatosInventarioLibre actualizarCantidadesRef(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
     public PdaDatosInventarioLibre actualizarCantidadesRefConRelaciones(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
	 public InventarioRotativo findOne(InventarioRotativo inventarioRotativo) throws Exception;
	 public List<PdaDatosInventarioLibre> findAllPdaGISAE(PdaDatosInventarioLibre pdaDatosInventarioLibre) throws Exception;
     public void updateFlgNoGuardarPda(String flgNoGuardar, InventarioRotativo inventarioRotativo) throws Exception;
     void updateAvisoPda(String aviso, InventarioRotativo inventarioRotativo) throws Exception;
     void updateAvisoPdaAll(String aviso, InventarioRotativo inventarioRotativo) throws Exception;
}
