package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ListaIntertienda;
import es.eroski.misumi.model.ui.Pagination;



public interface IntertiendaService {

	ListaIntertienda listCentroIntertienda(Intertienda intertienda, Pagination pagination) throws Exception;
	List<GenericExcelVO> listCentroIntertiendaExcel(Intertienda intertienda, String[] model,  Pagination pagination) throws Exception;
	Long findOne(Long codCentro) throws Exception;

}
