package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.CentrosRelIntertiendaDao;
import es.eroski.misumi.dao.iface.IntertiendaDao;
import es.eroski.misumi.model.GenericExcelVO;
import es.eroski.misumi.model.Intertienda;
import es.eroski.misumi.model.ListaIntertienda;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.IntertiendaService;
import es.eroski.misumi.util.StackTraceManager;

@Service(value = "IntertiendaService")
public class IntertiendaServiceImpl implements IntertiendaService {
	
	private static Logger logger = Logger.getLogger(IntertiendaServiceImpl.class);
	
	 @Autowired
	private IntertiendaDao intertiendaDao;
	
	@Autowired
	private CentrosRelIntertiendaDao centrosRelIntertiendaDao;
	
	@Override
	public ListaIntertienda listCentroIntertienda(Intertienda intertienda, Pagination pagination) throws Exception {
		
		ListaIntertienda resultado = new ListaIntertienda();
		
        try {
    		List<Intertienda> lista = new ArrayList<Intertienda>();
    		Long count = new Long(0);
    		
			lista = this.intertiendaDao.listCentroIntertienda(intertienda, pagination);
			count = this.intertiendaDao.listCentroIntertiendaCount(intertienda);

    		resultado.setLista(lista);
    		resultado.setNumeroRegistros(count);
        } catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			throw e;
        }
		
		return resultado;
	}

	@Override
	public List<GenericExcelVO> listCentroIntertiendaExcel(Intertienda intertienda, String[] model,  Pagination pagination)
			throws Exception {
		
		List<GenericExcelVO> lista = new ArrayList<GenericExcelVO>();
		
        try {
    		
			lista = this.intertiendaDao.listCentroIntertiendaExcel(intertienda, model, pagination);

        } catch (Exception e) {
			//logger.error(StackTraceManager.getStackTrace(e));
			throw e;
        }
		
		return lista;
	}

	@Override
	public Long findOne(Long codCentro) throws Exception {
		// TODO Auto-generated method stub
		return centrosRelIntertiendaDao.findOne(codCentro);
	}

}
