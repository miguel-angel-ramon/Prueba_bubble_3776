package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.ui.Pagination;

public interface VArtCentroAltaDao  {

	public List<VArtCentroAlta> findAll(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception ;

	public List<VArtCentroAlta> findAllTextilN1(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception ;
	
	public List<VArtCentroAlta> findAllTextilN2ByLote(VArtCentroAlta vArtCentroAlta) throws Exception ;
	
	public List<VArtCentroAlta> isReferenciaTextilN2(VArtCentroAlta vArtCentroAlta) throws Exception ;
	
	public Long findAllCont(VArtCentroAlta vArtCentroAlta) throws Exception;
	
	public Long findAllTextilN1Cont(VArtCentroAlta vArtCentroAlta) throws Exception;

	public List<GenericExcelVO> findAllExcel(VArtCentroAlta vArtCentroAlta,String[] columnModel)
			throws Exception;
	

}
