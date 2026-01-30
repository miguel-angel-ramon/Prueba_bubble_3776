package es.eroski.misumi.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.PedidoAdicionalEDao;
import es.eroski.misumi.model.PedidoAdicionalContadores;
import es.eroski.misumi.model.PedidoAdicionalE;
import es.eroski.misumi.service.iface.PedidoAdicionalEService;
import es.eroski.misumi.util.Constantes;


@Service(value = "PedidoAdicionalEService")
public class PedidoAdicionalEServiceImpl implements PedidoAdicionalEService {
    @Autowired
	private PedidoAdicionalEDao pedidoAdicionalDao;
	@Override
	 public List<PedidoAdicionalE> findAll(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.findAll(pedidoAdicionalE, session);
	}
	@Override
	public List<PedidoAdicionalE> removeAll(List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.removeAll(listaPedidoAdicionalE, session);
	}
	@Override
	public List<PedidoAdicionalE> validateAll(List<PedidoAdicionalE> listaPedidoAdicionalE, HttpSession session) throws Exception {
		return this.pedidoAdicionalDao.validateAll(listaPedidoAdicionalE, session);
	}
	@Override
	public PedidoAdicionalContadores getContadores(PedidoAdicionalE pedidoAdicionalE, HttpSession session) throws Exception {
		PedidoAdicionalContadores resultado = new PedidoAdicionalContadores();
		PedidoAdicionalE pedidoAux = new PedidoAdicionalE();
		pedidoAux.setCodCentro(pedidoAdicionalE.getCodCentro());
		pedidoAux.setGrupo1(pedidoAdicionalE.getGrupo1());
		pedidoAux.setGrupo2(pedidoAdicionalE.getGrupo2());
		pedidoAux.setGrupo3(pedidoAdicionalE.getGrupo3());
		pedidoAux.setCodArticulo(pedidoAdicionalE.getCodArticulo());
		pedidoAux.setClasePedido(pedidoAdicionalE.getClasePedido());
		
		if (pedidoAdicionalE.getMca().equals("S")){
			for(int perf = 0; perf < 3; perf++){
				pedidoAux.setPerfil(new Long(perf));
				PedidoAdicionalContadores resAux = this.pedidoAdicionalDao.getContadores(pedidoAdicionalE,session);
				resultado.setCodError(resAux.getCodError());
				resultado.setDescError(resAux.getDescError());
				resultado.setContadorEncargos(resultado.getContadorEncargos() + resAux.getContadorEncargos());
				resultado.setContadorMontaje(resultado.getContadorMontaje() + resAux.getContadorMontaje());
				resultado.setContadorMontajeOferta(resultado.getContadorMontajeOferta() + resAux.getContadorMontajeOferta());
			}
		} else {
			pedidoAux.setPerfil(Constantes.PERFIL_CENTRO);
			PedidoAdicionalContadores resAux = this.pedidoAdicionalDao.getContadores(pedidoAdicionalE, session);
			resultado.setCodError(resAux.getCodError());
			resultado.setDescError(resAux.getDescError());
			resultado.setContadorEncargos(resultado.getContadorEncargos() + resAux.getContadorEncargos());
			resultado.setContadorMontaje(resultado.getContadorMontaje() + resAux.getContadorMontaje());
			resultado.setContadorMontajeOferta(resultado.getContadorMontajeOferta() + resAux.getContadorMontajeOferta());
			
		}
		return resultado;
	}
}
