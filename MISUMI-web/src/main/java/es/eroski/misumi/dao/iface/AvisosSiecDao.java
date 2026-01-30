package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.Aviso;
import es.eroski.misumi.model.AvisosSiec;
import es.eroski.misumi.model.ui.Pagination;

public interface AvisosSiecDao  {

	public List<AvisosSiec> findAll(Pagination pagination) throws Exception;
	public int deleteRows(AvisosSiec avisosSiec) throws Exception;
	public List<Aviso> obtenerAvisosSiec(Long centro) throws Exception;
	public AvisosSiec findAvisoSiec(String codAviso) throws Exception;
	public String updateLinea(AvisosSiec avisosSiec, String user);
	public String insertLinea(AvisosSiec avisosSiec, String user);
}
