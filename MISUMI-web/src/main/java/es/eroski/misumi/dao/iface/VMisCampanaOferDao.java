package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.SeguimientoCampanasDetalle;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Pagination;

public interface VMisCampanaOferDao  {

	public List<SeguimientoCampanas> findAllReferenciasCampanaOfer(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias) throws Exception;
	public VMisCampanaOfer recargaDatosCampanasOfer(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias) throws Exception;
    public List<GenericExcelVO> findAllReferenciasCampanaOferExcel(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias, String[] columnModel, String empujeLabel, String textil,boolean mostrarColumnas) throws Exception;
	public List<SeguimientoCampanasDetalle> findAllReferenciasCampanaOferPopup(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias, Pagination pagination) throws Exception;
	public Long findAllReferenciasCampanaOferPopupCont(VMisCampanaOfer vMisCampanaOfer, List<Long> listaReferencias) throws Exception ;
}
