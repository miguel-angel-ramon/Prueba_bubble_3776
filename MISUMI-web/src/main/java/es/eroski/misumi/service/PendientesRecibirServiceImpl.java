package es.eroski.misumi.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.control.p13ReferenciasCentroController;
import es.eroski.misumi.dao.iface.PendientesRecibirDao;
import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.model.PendientesRecibir;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.service.iface.PendientesRecibirService;

@Service(value = "PendientesRecibirService")
public class PendientesRecibirServiceImpl implements PendientesRecibirService {

	private static Logger logger = Logger.getLogger(p13ReferenciasCentroController.class);
	
    @Autowired
	private PendientesRecibirDao pendientesRecibirDao;
    
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
    
	@Override
	public PendientesRecibir find( PendientesRecibir pr) throws Exception {
		
		PendientesRecibir pendientesRecibir = new PendientesRecibir();
		try {	
			
			pendientesRecibir.setCodArt(pr.getCodArt());
			pendientesRecibir.setCodCentro(pr.getCodCentro());
			List<Long> referencias = new ArrayList<Long>();
			RelacionArticulo relacionArticulo = new RelacionArticulo();
			relacionArticulo.setCodArt(pr.getCodArt());
			relacionArticulo.setCodCentro(pr.getCodCentro());
			referencias = this.relacionArticuloDao.findAll(relacionArticulo);
			referencias.add(pr.getCodArt());
			Long cantHoy = new Long(0);
			Long cantFutura = new Long(0);
			for (Long codArt : referencias){
				PendientesRecibir aux = new PendientesRecibir();
				aux.setCodCentro(pr.getCodCentro());
				aux.setCodArt(codArt);
				PendientesRecibir temp = this.pendientesRecibirDao.find(aux); 
				cantHoy += temp.getCantHoy();
				cantFutura += temp.getCantFutura();
			}
			pendientesRecibir.setCantHoy(cantHoy);
			pendientesRecibir.setCantFutura(cantFutura);
		
		
		} catch (Exception e) {
			logger.error("######################## Error: pendientesRecibir  ############################");
			logger.error("Referencia: " + pr.getCodArt() );
			logger.error("############################################################");
		
		throw e;
		}	
	
		return pendientesRecibir; 
	}

	@Override
	public PendientesRecibir find( PendientesRecibir pr, VRelacionArticulo vRelacionArticulo) throws Exception {
		
		PendientesRecibir pendientesRecibirResultado = new PendientesRecibir();
		PendientesRecibir pendientesRecibirBusqueda = pr;
		int cantHoy = 0;
		int cantFutura = 0;
		
		//Cantidades de artículo
		PendientesRecibir prArticulo = this.pendientesRecibirDao.find(pendientesRecibirBusqueda); 
		if (prArticulo != null){
			if (prArticulo.getCantHoy()!= null){
				cantHoy = cantHoy + prArticulo.getCantHoy().intValue();
			}
			if (prArticulo.getCantFutura()!= null){
				cantFutura = cantFutura + prArticulo.getCantFutura().intValue();
			}
		}
		
		//Añadir cantidades de referencia ffpp
		if (vRelacionArticulo != null && vRelacionArticulo.getCodArt() != null){
			pendientesRecibirBusqueda.setCodArt(vRelacionArticulo.getCodArt());
			PendientesRecibir prArticuloFfpp = this.pendientesRecibirDao.find(pendientesRecibirBusqueda);
			if (prArticuloFfpp != null){
				if (prArticuloFfpp.getCantHoy()!= null){
					cantHoy = cantHoy + prArticuloFfpp.getCantHoy().intValue();
				}
				if (prArticuloFfpp.getCantFutura()!= null){
					cantFutura = cantFutura + prArticuloFfpp.getCantFutura().intValue();
				}
			}
		}

		//Añadir cantidades de referencias relacionadas
		if (pr != null && pr.getReferencias() != null){
			for(Long referencia : pr.getReferencias()){
				pendientesRecibirBusqueda.setCodArt(referencia);
				PendientesRecibir prArticuloRelacionado = this.pendientesRecibirDao.find(pendientesRecibirBusqueda);
				if (prArticuloRelacionado != null){
					if (prArticuloRelacionado.getCantHoy()!= null){
						cantHoy = cantHoy + prArticuloRelacionado.getCantHoy().intValue();
					}
					if (prArticuloRelacionado.getCantFutura()!= null){
						cantFutura = cantFutura + prArticuloRelacionado.getCantFutura().intValue();
					}
				}
			}
		}
		
		pendientesRecibirResultado.setCantHoy(new Long(cantHoy));
		pendientesRecibirResultado.setCantFutura(new Long(cantFutura));
		
		return pendientesRecibirResultado; 
	}

}