package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.RefEnDepositoBrita;


public interface RefEnDepositoBritaService {

	  public List<RefEnDepositoBrita> findAll(RefEnDepositoBrita refEnDepositoBrita) throws Exception;
		  
	  public RefEnDepositoBrita findOne(RefEnDepositoBrita refEnDepositoBrita) throws Exception;
	  
	  public Long findAllCont(RefEnDepositoBrita refEnDepositoBrita) throws Exception;
	  
	  public boolean enDepositoBrita(RefEnDepositoBrita refEnDepositoBrita) throws Exception;
}
