package es.eroski.misumi.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.RelacionArticuloDao;
import es.eroski.misumi.model.DetallePedido;
import es.eroski.misumi.model.RelacionArticulo;
import es.eroski.misumi.model.pda.PdaArticulo;
import es.eroski.misumi.service.iface.RelacionArticuloService;

@Service(value = "RelacionArticuloService")
public class RelacionArticuloServiceImpl implements RelacionArticuloService {
	
	@Autowired
	private RelacionArticuloDao relacionArticuloDao;
	
	@Override
	public List<Long> findAll(RelacionArticulo relacionArticulo) throws Exception{
		return this.relacionArticuloDao.findAll(relacionArticulo);
	}
	
	@Override
	public List<Long> findRefMismoModeloProveedor(Long codArt) throws Exception{
		return this.relacionArticuloDao.findRefMismoModeloProveedor(codArt);
	}
	
	@Override
	public List<PdaArticulo> findDatosEspecificosTextil(Long codArt) throws Exception{
		return this.relacionArticuloDao.findDatosEspecificosTextil(codArt);
	}
	
	@Override
	public List<DetallePedido> findDatosEspecificosTextilPC(Long codArt) throws Exception{
		return this.relacionArticuloDao.findDatosEspecificosTextilPC(codArt);
	}
	
	@Override
	public List<Long> findLoteReferenciaHija(Long codArt) throws Exception{
		return this.relacionArticuloDao.findLoteReferenciaHija(codArt);
	}
	
	@Override
	public List<Long> esReferenciaLote(Long codArt) throws Exception{
		return this.relacionArticuloDao.esReferenciaLote(codArt);
	}
	
	@Override
	public List<Long> obtenerHijasLote(Long codArt) throws Exception{
		return this.relacionArticuloDao.obtenerHijasLote(codArt);
	}
	
	@Override
	public List<BigInteger> obtenerHijasLoteBI(Long codArt) throws Exception{
		return this.relacionArticuloDao.obtenerHijasLoteBI(codArt);
	}
	
	
	@Override
	public List<Long> esReferenciaHijaDeLote(Long codArt) throws Exception{
		return this.relacionArticuloDao.esReferenciaHijaDeLote(codArt);
	}

	@Override
	public RelacionArticulo findOneProporciones(RelacionArticulo relacionArticulo) throws Exception{
		return this.relacionArticuloDao.findOneProporciones(relacionArticulo);
	}
	
	@Override
	public List<RelacionArticulo> findAllProporciones(RelacionArticulo relacionArticulo) throws Exception{
		return this.relacionArticuloDao.findAllProporciones(relacionArticulo);
	}
	
	@Override
	public List<Long> findRefMismoLote(Long codArt) throws Exception{
		return this.relacionArticuloDao.findRefMismoLote(codArt);
	}

	@Override
	public Long findRefMadrePromocional(Long codArtRela) throws Exception {
		// TODO Auto-generated method stub
		return this.relacionArticuloDao.findRefMadrePromocional(codArtRela);
	}
}
