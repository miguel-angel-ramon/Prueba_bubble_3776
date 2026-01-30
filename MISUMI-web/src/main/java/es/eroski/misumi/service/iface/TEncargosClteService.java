package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.TEncargosClte;


public interface TEncargosClteService {

	  public void delete(TEncargosClte tEncargosClte) throws Exception;
	  public void deleteHistorico() throws Exception;
	  public void deleteEncargo(TEncargosClte tEncargosClte) throws Exception;
	  public void updateErrorEncargo(TEncargosClte tEncargosClte) throws Exception;
	  public void updateErrores(TEncargosClte tEncargosClte) throws Exception;
	  public void updateEncargo(TEncargosClte tEncargosClte) throws Exception;
	  public void insertAll(List<TEncargosClte> listaTEncargosClte) throws Exception;
	  public List<TEncargosClte> findAll(TEncargosClte tEncargosClte) throws Exception;
	  public List<TEncargosClte> findAllDetalle(TEncargosClte tEncargosClte) throws Exception;
	  public TEncargosClte findOne(TEncargosClte tEncargosClte) throws Exception;
	  public Long findAllCount(TEncargosClte tEncargosClte) throws Exception;
	  public List<TEncargosClte> findAllExcel(TEncargosClte tEncargosClte) throws Exception;
}
