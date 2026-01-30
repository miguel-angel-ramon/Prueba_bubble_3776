package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ui.Pagination;

public interface IntertiendaDao  {

	List<Intertienda> listCentroIntertienda(Intertienda intertienda, Pagination pagination) throws Exception;
	
	Long listCentroIntertiendaCount(Intertienda intertienda) throws Exception;

	List<GenericExcelVO> listCentroIntertiendaExcel(Intertienda intertienda, String[] model, Pagination pagination) throws Exception;
}
