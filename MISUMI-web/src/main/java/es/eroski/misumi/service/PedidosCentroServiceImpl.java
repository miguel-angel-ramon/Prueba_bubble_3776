package es.eroski.misumi.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoCentroDao;
import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.model.Pedido;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.model.pedidosCentroWS.ValidarReferenciasResponseType;
import es.eroski.misumi.service.iface.PedidosCentroService;


@Service(value = "PedidosCentroService")
public class PedidosCentroServiceImpl implements PedidosCentroService {
	@Autowired
	private PedidoCentroDao pedidoCentroDao;
	   
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	   
	@Override
	public ValidarReferenciasResponseType findPedidosCentroWS(ReferenciasCentro vReferenciasCentro) throws Exception {
		// TODO Auto-generated method stub
		return this.pedidoCentroDao.findPedidosCentroWS(vReferenciasCentro);
	}
	public Pedido findAllBasicInfo(Pedido pedido) throws Exception
	{
		RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(pedido.getArticulo().getCodArt());
		relacionArticulo.setCodCentro(pedido.getArticulo().getCentro().getCodCentro());
		List<Long> referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		return this.pedidoCentroDao.findAllUltimosEnvios(pedido, referencias);
	}
	
	public Pedido findAllSegPedidosPda(Pedido pedido, VRelacionArticulo vRelacionArticulo) throws Exception
	{
		RelacionArticulo relacionArticulo = new RelacionArticulo();
		relacionArticulo.setCodArt(pedido.getArticulo().getCodArt());
		relacionArticulo.setCodCentro(pedido.getArticulo().getCentro().getCodCentro());
		List<Long> referencias = this.relacionArticuloDao.findAll(relacionArticulo);
		
		//Se añade la referecia ffpp a la lista de referencias relacionadas
		if (vRelacionArticulo != null && vRelacionArticulo.getCodArt() != null){
			if (referencias == null){
				referencias = new ArrayList<Long>();
			}
			referencias.add(vRelacionArticulo.getCodArt());
		}
		
		return this.pedidoCentroDao.findAllUltimosEnvios(pedido, referencias);
	}

}
