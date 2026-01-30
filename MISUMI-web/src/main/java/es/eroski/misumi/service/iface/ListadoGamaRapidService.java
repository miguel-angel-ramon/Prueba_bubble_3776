package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.ArtGamaRapid;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.ui.Pagination;


public interface ListadoGamaRapidService {

	public List<ArtGamaRapid> findAll(ArtGamaRapid artGamaRapid, Pagination pagination) throws Exception;
	public Long findAllCont(ArtGamaRapid artGamaRapid) throws Exception;
	public List<GenericExcelVO> findAllExcel(ArtGamaRapid artGamaRapid,String[] columnModel) throws Exception;
	public List<ArtGamaRapid> findArticulo(ArtGamaRapid artGamaRapid)throws Exception;
	public void cargarDatosCentro(Long codCentro) throws Exception;
}
