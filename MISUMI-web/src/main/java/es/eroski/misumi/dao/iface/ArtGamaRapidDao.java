package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.ui.Pagination;

public interface ArtGamaRapidDao {
	
	public Boolean centroCargado(Long codCentro) throws Exception;
	
	public List<ArtGamaRapid> obtenerDatosCentro(Long codCentro) throws Exception;
	
	public void cargarArticuloCentro(ArtGamaRapid articulo) throws Exception;
	
	public void cargarArticulosCentro(List<ArtGamaRapid> articulos) throws Exception;

	public List<ArtGamaRapid> findAll(ArtGamaRapid artGamaRapid, Pagination pagination) throws Exception;

	public Long findAllCont(ArtGamaRapid artGamaRapid) throws Exception;

	public List<GenericExcelVO> findAllExcel(ArtGamaRapid artGamaRapid, String[] columnModel) throws Exception;

}
