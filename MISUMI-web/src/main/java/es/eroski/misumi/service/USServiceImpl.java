package es.eroski.misumi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.USSDaoSIA;
import es.eroski.misumi.service.iface.USService;
import es.eroski.misumi.util.Constantes;

@Service(value = "USService")
public class USServiceImpl implements USService{
	 @Autowired
		private USSDaoSIA ussDaoSIA;
		 
		@Override
		public String esUSS(Long codArt,Long area) {
			// TODO Auto-generated method stub
			Long codAgrup = (new Long(Constantes.AREA_BAZAR).equals(area)) ?  Constantes.AGRUP_BAZAR:Constantes.AGRUP_ELECTRO; 
			return ussDaoSIA.esUSS(codArt, codAgrup);
		}
}
