package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.RefEnDepositoBrita;

public interface RefEnDepositoBritaDao  {

	public List<RefEnDepositoBrita> findAll(RefEnDepositoBrita refEnDepositoBrita) throws Exception ;
	
	public Long findAllCont(RefEnDepositoBrita refEnDepositoBrita) throws Exception;
}
