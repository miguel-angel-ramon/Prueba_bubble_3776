package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.VArtCentroAlta;
import es.eroski.misumi.model.ui.Pagination;


public interface VArtCentroAltaService {

	public List<VArtCentroAlta> findAll(VArtCentroAlta vArtCentroAlta, Pagination pagination) throws Exception;
	public Long findAllCont(VArtCentroAlta vArtCentroAlta) throws Exception;
	public List<GenericExcelVO> findAllExcel(VArtCentroAlta vArtCentroAlta,String[] columnModel) throws Exception;
	public List<VArtCentroAlta> findAllExcelTextil(VArtCentroAlta vArtCentroAlta,String[] columnModel) throws Exception;
	public boolean esTextilPedible(Centro centro, Long codArticulo) throws Exception;
	public List<VArtCentroAlta> findArticulo(VArtCentroAlta vArtCentroAlta)throws Exception;
	public Long findAllTextilN1Cont(VArtCentroAlta vArtCentroAlta) throws Exception;
	public List<VArtCentroAlta> findAllTextilN2ByLote(VArtCentroAlta vArtCentroAlta) throws Exception;
	
	
}
