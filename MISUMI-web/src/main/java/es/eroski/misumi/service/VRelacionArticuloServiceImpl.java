package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VRelacionArticuloDao;
import es.eroski.misumi.model.ReferenciasCentro;
import es.eroski.misumi.model.VRelacionArticulo;
import es.eroski.misumi.service.iface.VRelacionArticuloService;

@Service(value = "VRelacionArticuloService")
public class VRelacionArticuloServiceImpl implements VRelacionArticuloService {
	//private static Logger logger = LoggerFactory.getLogger(VRelacionArticuloServiceImpl.class);
    @Autowired
	private VRelacionArticuloDao vRelacionArticuloDao;
	
    @Override
	 public List<VRelacionArticulo> findAll(VRelacionArticulo vRelacionArticulo) throws Exception {
		return this.vRelacionArticuloDao.findAll(vRelacionArticulo);
	}
	
	@Override
	public VRelacionArticulo findOne(VRelacionArticulo vRelacionArticulo) throws Exception {
		VRelacionArticulo vRelacionArticuloRes = null;
		List<VRelacionArticulo> listVRelacionArticulo = this.vRelacionArticuloDao.findAll(vRelacionArticulo);
		if (!listVRelacionArticulo.isEmpty()){
			vRelacionArticuloRes = listVRelacionArticulo.get(0);
		}
		return vRelacionArticuloRes;

	}

	@Override
	public ReferenciasCentro obtenerFfppActivaOUnitaria(ReferenciasCentro referenciasCentro, boolean tratamientoVegalsa) throws Exception{
		//Se inicializan los valores utilizados para el mensaje de ffpp o unitaria
		referenciasCentro.setTieneFfppActivo(false);
		referenciasCentro.setTieneUnitaria(false);

		//Controlamos si tiene un artículo relacionado
		VRelacionArticulo relacionArticulo = obtenerRelacionArticulo(referenciasCentro);
		if (relacionArticulo != null && relacionArticulo.getCodArtRela() != null)
		{
			if(tratamientoVegalsa){
				if(relacionArticulo.getFormatoProductivoActivo() !=null && relacionArticulo.getFormatoProductivoActivo().equals("S")){
					referenciasCentro.setTieneFfppActivo(true);
					referenciasCentro.setCodArtRelacionado(relacionArticulo.getCodArtRela());
				}
			}else{
				referenciasCentro.setTieneFfppActivo(true);
				referenciasCentro.setCodArtRelacionado(relacionArticulo.getCodArtRela());
			}
		}else{ 
			//Comprobación de artículo unitario
			VRelacionArticulo relacionArticuloUnitario = obtenerRelacionArticuloUnitario(referenciasCentro);
			if (relacionArticuloUnitario != null && relacionArticuloUnitario.getCodArt() != null){
				if(tratamientoVegalsa){
					if(relacionArticuloUnitario.getFormatoProductivoActivo() !=null && relacionArticuloUnitario.getFormatoProductivoActivo().equals("S")){
						referenciasCentro.setTieneUnitaria(true);
						referenciasCentro.setCodArtRelacionado(relacionArticuloUnitario.getCodArt());
					}
				}else{
					referenciasCentro.setTieneUnitaria(true);
					referenciasCentro.setCodArtRelacionado(relacionArticuloUnitario.getCodArt());
				}
			}
		}
		return referenciasCentro;
	}
	
	private VRelacionArticulo obtenerRelacionArticulo(ReferenciasCentro referenciasCentro) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();
		
		relacionArticulo.setCodArt(referenciasCentro.getCodArt());
		relacionArticulo.setCodCentro(referenciasCentro.getCodCentro());
		relacionArticuloRes = this.findOne(relacionArticulo);
		
		return relacionArticuloRes;
	}
	
	private VRelacionArticulo obtenerRelacionArticuloUnitario(ReferenciasCentro referenciasCentro) throws Exception{
		VRelacionArticulo relacionArticuloRes = null;
		VRelacionArticulo relacionArticulo = new VRelacionArticulo();
		
		relacionArticulo.setCodArtRela(referenciasCentro.getCodArt());
		relacionArticulo.setCodCentro(referenciasCentro.getCodCentro());
		relacionArticuloRes = this.findOne(relacionArticulo);
		
		return relacionArticuloRes;
	}
}
