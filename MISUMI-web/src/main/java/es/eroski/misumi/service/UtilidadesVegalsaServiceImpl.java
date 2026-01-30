package es.eroski.misumi.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.model.Centro;
import es.eroski.misumi.model.User;
import es.eroski.misumi.model.VReferenciasPedirSIA;
import es.eroski.misumi.service.iface.UtilidadesVegalsaService;
import es.eroski.misumi.service.iface.VReferenciasPedirSIAService;
import es.eroski.misumi.util.Constantes;

/**
 * 
 * Clase que contiene los metodos de obtención y transformacion de datos para Vegalsa
 *
 */
@Service(value = "UtilidadesVegalsaService")
public class UtilidadesVegalsaServiceImpl implements UtilidadesVegalsaService{

	
	@Autowired
	private VReferenciasPedirSIAService vReferenciasPedirSIAService;

	@Override
	public boolean esTratamientoVegalsa(Centro centro, Long codArticulo) throws Exception{
		VReferenciasPedirSIA vReferenciasPedirSIARes = new VReferenciasPedirSIA();
		
		if (Constantes.CODIGO_SOCIEDAD_VEGALSA.compareTo(centro.getCodSoc())==0){
			//Comprobación de si es una referencia NO_ALI tratada por SIA
			VReferenciasPedirSIA vReferenciasPedirSIA = new VReferenciasPedirSIA();
			vReferenciasPedirSIA.setCodCentro(centro.getCodCentro());
			vReferenciasPedirSIA.setCodArt(codArticulo);
			//vReferenciasPedirSIA.setTipoPedido(new Integer(Constantes.CLASE_PEDIDO_MONTAJE));

			vReferenciasPedirSIARes = this.vReferenciasPedirSIAService.findOne(vReferenciasPedirSIA); 
		} 
		
		return (vReferenciasPedirSIARes == null);
	}

	@Override
	public Boolean esCentroVegalsa(HttpSession session) {
		User user = (User) session.getAttribute("user");
		return user.getCentro().getCodSoc().equals(Constantes.CODIGO_SOCIEDAD_VEGALSA);
	}
	
}