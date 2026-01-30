package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidosAdCentralSPDao;
import es.eroski.misumi.dao.iface.TPedidoAdicionalDao;
import es.eroski.misumi.model.TPedidoAdicional;
import es.eroski.misumi.service.iface.PedidoAdicionalVCService;
import es.eroski.misumi.util.Constantes;


@Service(value = "PedidoAdicionalVCService")
public class PedidoAdicionalVCServiceImpl implements PedidoAdicionalVCService {
     @Autowired
	private TPedidoAdicionalDao tPedidoAdicionalDao;
    
    @Autowired
    private PedidosAdCentralSPDao pedidosAdCentralSPDao;

	@Override
	public void modifyAll(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception {
		List<TPedidoAdicional> listaPedidosAdicionales = this.tPedidoAdicionalDao.findAll(tPedidoAdicional);
		for(TPedidoAdicional pedidoAdicional: listaPedidosAdicionales){
			this.modifyOne(pedidoAdicional,session);
			
		}
	}
	
	private void modifyOne(TPedidoAdicional tPedidoAdicional, HttpSession session) throws Exception{
		//Indicamos que va a ser validado
		tPedidoAdicional.setFlgValidado("S");
		this.pedidosAdCentralSPDao.actualizacionPedidosAd(tPedidoAdicional,session);
		if (tPedidoAdicional.getCodError().equals(Constantes.SFMCAP_CODIGO_GUARDADO_CORRECTO.toString())){
			this.tPedidoAdicionalDao.deleteArticulo(tPedidoAdicional);
		} else {
			this.tPedidoAdicionalDao.updateErrorArticulo(tPedidoAdicional);
		}
		
		
		
		//Actualizar TPedidoAdicional;
	}

}
