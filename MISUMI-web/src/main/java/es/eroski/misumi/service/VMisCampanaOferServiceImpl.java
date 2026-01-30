package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.dao.iface.VMisCampanaOferDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.SeguimientoCampanas;
import es.eroski.misumi.model.SeguimientoCampanasDetalle;
import es.eroski.misumi.model.VMisCampanaOfer;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VMisCampanaOferService;
import es.eroski.misumi.util.Constantes;

@Service(value = "VMisCampanaOferService")
public class VMisCampanaOferServiceImpl implements VMisCampanaOferService {
	private static Logger logger = Logger.getLogger(VMisCampanaOferServiceImpl.class);
	
	@Autowired
	private VMisCampanaOferDao vMisCampanaOferDao;
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<SeguimientoCampanas> findAllReferenciasCampanaOfer(VMisCampanaOfer vMisCampanaOfer) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (vMisCampanaOfer != null && Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA.equals(vMisCampanaOfer.getTipo()) && vMisCampanaOfer.getCodArt() != null && vMisCampanaOfer.getCodCentro() != null){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisCampanaOfer.getCodArt());
			relacionArticulo.setCodCentro(vMisCampanaOfer.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		return this.vMisCampanaOferDao.findAllReferenciasCampanaOfer(vMisCampanaOfer, referencias);
	}

	@Override
	public VMisCampanaOfer recargaDatosCampanasOfer(VMisCampanaOfer vMisCampanaOfer) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA.equals(vMisCampanaOfer.getTipo())){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisCampanaOfer.getCodArt());
			relacionArticulo.setCodCentro(vMisCampanaOfer.getCodCentro());
			
		
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
			
			stopWatch.stop();
			
		}
		//Añadimos referencia actual a la lista para recargarla	
		if (vMisCampanaOfer.getCodArt() != null && !"".equals(vMisCampanaOfer.getCodArt())){
			referencias.add(vMisCampanaOfer.getCodArt());
		}
		
		
		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		
		VMisCampanaOfer vMisCampanaOfer2 =  this.vMisCampanaOferDao.recargaDatosCampanasOfer(vMisCampanaOfer, referencias);
		
		stopWatch1.stop();
		
		
		return vMisCampanaOfer2;
	}
	
	@Override
	public List<SeguimientoCampanasDetalle> findAllReferenciasCampanaOferPopup(VMisCampanaOfer vMisCampanaOfer, Pagination pagination) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (vMisCampanaOfer != null && Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA.equals(vMisCampanaOfer.getTipo()) && vMisCampanaOfer.getCodArt() != null && vMisCampanaOfer.getCodCentro() != null){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisCampanaOfer.getCodArt());
			relacionArticulo.setCodCentro(vMisCampanaOfer.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		vMisCampanaOfer.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA);
		return this.vMisCampanaOferDao.findAllReferenciasCampanaOferPopup(vMisCampanaOfer, referencias, pagination);
	}

	@Override
	public List<GenericExcelVO> findAllReferenciasCampanaOferExcel(VMisCampanaOfer vMisCampanaOfer, String[] columnModel, String empujeLabel, String textil, boolean mostrarColumnas) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (vMisCampanaOfer != null && Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA.equals(vMisCampanaOfer.getTipo()) && vMisCampanaOfer.getCodArt() != null && vMisCampanaOfer.getCodCentro() != null){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisCampanaOfer.getCodArt());
			relacionArticulo.setCodCentro(vMisCampanaOfer.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		return this.vMisCampanaOferDao.findAllReferenciasCampanaOferExcel(vMisCampanaOfer, referencias, columnModel, empujeLabel, textil, mostrarColumnas);
	}
	
	@Override
	public Long findAllReferenciasCampanaOferPopupCont(VMisCampanaOfer vMisCampanaOfer) throws Exception {
		List<Long> referencias = new ArrayList<Long>();
		if (vMisCampanaOfer != null && Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA.equals(vMisCampanaOfer.getTipo()) && vMisCampanaOfer.getCodArt() != null && vMisCampanaOfer.getCodCentro() != null){
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(vMisCampanaOfer.getCodArt());
			relacionArticulo.setCodCentro(vMisCampanaOfer.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		}
		vMisCampanaOfer.setTipo(Constantes.SEGUIMIENTO_CAMPANAS_FILTRO_REFERENCIA);

		return this.vMisCampanaOferDao.findAllReferenciasCampanaOferPopupCont(vMisCampanaOfer, referencias);
	}
}
