package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.SeguimientoCampanasDetalle;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Pagination;

public interface VMisCampanaOferService {

	public List<SeguimientoCampanas> findAllReferenciasCampanaOfer(VMisCampanaOfer vMisCampanaOfer) throws Exception ;
	public VMisCampanaOfer recargaDatosCampanasOfer(VMisCampanaOfer vmCampanaOfer) throws Exception ;
	public List<SeguimientoCampanasDetalle> findAllReferenciasCampanaOferPopup(VMisCampanaOfer vmCampanaOfer, Pagination pagination) throws Exception ;
	public List<GenericExcelVO> findAllReferenciasCampanaOferExcel(VMisCampanaOfer vMisCampanaOfer, String[] columnModel, String empujeLabel, String textil, boolean mostrarColumnas) throws Exception ;
	public Long findAllReferenciasCampanaOferPopupCont(VMisCampanaOfer vmCampanaOfer) throws Exception ;
}
