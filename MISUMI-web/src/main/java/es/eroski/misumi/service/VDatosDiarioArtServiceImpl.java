package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VDatosDiarioArtDao;
import es.eroski.misumi.model.VDatosDiarioArt;
import es.eroski.misumi.model.ui.Pagination;
import es.eroski.misumi.service.iface.VDatosDiarioArtService;
import es.eroski.misumi.util.Constantes;

@Service(value = "VDatosDiarioArtService")
public class VDatosDiarioArtServiceImpl implements VDatosDiarioArtService {
	//private static Logger logger = LoggerFactory.getLogger(VDatosDiarioArtServiceImpl.class);
	//private static Logger logger = Logger.getLogger(VDatosDiarioArtServiceImpl.class);
    @Autowired
	private VDatosDiarioArtDao vDatosDiarioArtDao;
	
	@Override
	public VDatosDiarioArt findOne(VDatosDiarioArt vDatosDiarioArt) throws Exception {
		VDatosDiarioArt vDatosDiarioArtRes = null;
		List<VDatosDiarioArt> listDatosDiarioArt = this.vDatosDiarioArtDao.findAll(vDatosDiarioArt);
		if (!listDatosDiarioArt.isEmpty()){
			vDatosDiarioArtRes = listDatosDiarioArt.get(0);
		}
		return vDatosDiarioArtRes;
	}
	
	public Long findVidaUtil(VDatosDiarioArt vDatosDiarioArt) throws Exception{
		return this.vDatosDiarioArtDao.findVidaUtil(vDatosDiarioArt);
	}

	public boolean esReferenciaSoloVenta(VDatosDiarioArt vDatosDiarioArt) throws Exception{
		boolean esSoloVenta = false;
		if (vDatosDiarioArt != null && Constantes.TIPO_COMPRA_VENTA_SOLO_VENTA.equals(vDatosDiarioArt.getTipoCompraVenta())){
			esSoloVenta = true;
		}
		return esSoloVenta;
	}

	@Override
	public List<VDatosDiarioArt> findAllVentaRef(VDatosDiarioArt vDatosDiarioArt, Pagination pagination)throws Exception {
		return this.vDatosDiarioArtDao.findAllVentaRef(vDatosDiarioArt, pagination);
	}

	@Override
	public Long findAllVentaRefCont(VDatosDiarioArt vDatosDiarioArt) throws Exception {
		return this.vDatosDiarioArtDao.findAllVentaRefCont(vDatosDiarioArt);
	}

	@Override
	public String obtenerDescripcion(Long codArt) throws Exception{
		String descripcion = "";
		VDatosDiarioArt vDatosDiarioArtRes;
		VDatosDiarioArt vDatosDiarioArt = new VDatosDiarioArt();
		vDatosDiarioArt.setCodArt(codArt);
		vDatosDiarioArtRes = this.findOne(vDatosDiarioArt);
		
		if (vDatosDiarioArtRes!=null){
			descripcion = vDatosDiarioArtRes.getDescripArt();
		}
		
		return descripcion;
	}
}
