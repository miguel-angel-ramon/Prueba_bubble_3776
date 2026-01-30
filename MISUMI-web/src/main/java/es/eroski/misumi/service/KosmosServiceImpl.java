package es.eroski.misumi.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.KosmosDao;
import es.eroski.misumi.model.OfertaPVP;
import es.eroski.misumi.service.iface.KosmosService;

@Service(value = "KosmosService")
public class KosmosServiceImpl implements KosmosService {
	
	private static Logger logger = Logger.getLogger(KosmosServiceImpl.class);

    @Autowired
	private KosmosDao kosmosDao;
	
    @Override
    public OfertaPVP obtenerDatosPVP(OfertaPVP ofertaPVP) throws Exception {
		OfertaPVP ofertaPVPRes = this.kosmosDao.obtenerDatosPVP(ofertaPVP);
		
		//Obtenemos la fecha de hoy inicializada a 00:00:00
		Calendar cal = Calendar.getInstance();  
        cal.setTime(new Date());  
        cal.set(Calendar.HOUR_OF_DAY, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
		 
        Date hoy = cal.getTime();
        
        if (ofertaPVPRes != null) {
			//Si hoy está entre la fecha de inicio y de fin de la oferta y la oferta existe, devolvemos 'S', si no 'N'
	        if(ofertaPVPRes.getCodOferta() != null){
	        	String flgMostrarOfertaPistola = ofertaPVPRes.getCodOferta() != null ? "S" : "N";
				ofertaPVPRes.setFlgMostrarOfertaPistola(flgMostrarOfertaPistola);
	        }
			
			//Si la oferta existe, devolvemos 'S', si no 'N'
			//String flgMostrarOfertaPC = ofertaPVPRes.getCodOferta() != null ? "S" : "N";
			if(ofertaPVPRes.getCodOferta() != null){
				String flgMostrarOfertaPC = ofertaPVPRes.getCodOferta() != null ? "S" : "N";
				ofertaPVPRes.setFlgMostrarOfertaPC(flgMostrarOfertaPC);
			}
			
			//Insertamos tarifa y pvp oferta con comas.
			if(ofertaPVPRes.getTarifa() != null){
				ofertaPVPRes.setTarifaStr(ofertaPVPRes.getTarifa().toString().replace(".",","));
			}
			if(ofertaPVPRes.getPvpOfer() != null){
				ofertaPVPRes.setPvpOferStr(ofertaPVPRes.getPvpOfer().toString().replace(".",","));
			}
        }
        
		return ofertaPVPRes;
	}
    
}
