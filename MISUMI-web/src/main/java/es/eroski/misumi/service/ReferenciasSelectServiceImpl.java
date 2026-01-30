package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.ReferenciasSelectDao;
import es.eroski.misumi.model.CategoriaBean;
import es.eroski.misumi.model.NumLoteMpGrid;
import es.eroski.misumi.model.PopUp34ViewerManagerBean;
import es.eroski.misumi.model.QueryReferenciasByDescr;
import es.eroski.misumi.model.ReferenciasByDescr;
import es.eroski.misumi.model.SeccionBean;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.ReferenciasSelectService;

/**
 * Implentacion de busquedas de referencias por descripcion.
 * P49634
 * @author BICUGUAL
 *
 */
@Service
public class ReferenciasSelectServiceImpl implements ReferenciasSelectService {

	@Autowired
	ReferenciasSelectDao referenciasSelectDao;

	@Override
	public NumLoteMpGrid findAllCount(QueryReferenciasByDescr filtros) throws Exception {
		NumLoteMpGrid numLoteMpGrid = numLoteMpGrid=referenciasSelectDao.findAllCount(filtros);
		return numLoteMpGrid;
	}

	@Override
	public List<ReferenciasByDescr> findAll(QueryReferenciasByDescr filtros,
			Pagination pagination) throws Exception {

		List<ReferenciasByDescr> lstReferencias=referenciasSelectDao.findAll(filtros, pagination);
		return lstReferencias;
	}

	@Override
	public PopUp34ViewerManagerBean findLstOptions(QueryReferenciasByDescr filtros)
			throws Exception {
		NumLoteMpGrid numLoteMpGrid = null;
		
		numLoteMpGrid=referenciasSelectDao.findAllCount(filtros);
		List<SeccionBean> lstSecciones=new ArrayList<SeccionBean>();

		if ((numLoteMpGrid.getFilasGrid().compareTo(new Long("0")))>0)
			lstSecciones=referenciasSelectDao.findLstSecciones(filtros);

		PopUp34ViewerManagerBean management=new PopUp34ViewerManagerBean(lstSecciones, numLoteMpGrid);

		return management;
	}

	@Override
	public List<ReferenciasByDescr> findAllTextilN2ByLote(QueryReferenciasByDescr referenciaByDescr) throws Exception {

		List<ReferenciasByDescr> lstReferencias=referenciasSelectDao.findAllTextilN2ByLote(referenciaByDescr);
		return lstReferencias;
	}

	@Override
	public List<ReferenciasByDescr> findEAN(QueryReferenciasByDescr filtros) throws Exception {
		List<ReferenciasByDescr> ref = referenciasSelectDao.findEAN(filtros);
		return ref;
	}

	@Override
	public List<Long> findArea(QueryReferenciasByDescr filtros) throws Exception {
		List<Long>area = referenciasSelectDao.findArea(filtros);
		return area;
	}

	@Override
	public List<CategoriaBean> findLstCategoria(QueryReferenciasByDescr filtros) throws Exception {
		// TODO Auto-generated method stub
		List<CategoriaBean> lstCategoria=referenciasSelectDao.findLstCategoria(filtros);
		return lstCategoria;
	}
}
