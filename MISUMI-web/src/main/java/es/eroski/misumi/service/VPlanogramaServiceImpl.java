package es.eroski.misumi.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VPlanogramaDao;
import es.eroski.misumi.dao.iface.VPlanogramaService;
import es.eroski.misumi.model.PlanogramaVigente;
import es.eroski.misumi.model.VPlanograma;
import es.eroski.misumi.util.Constantes;
@Service(value = "VPlanogramaService")
public class VPlanogramaServiceImpl implements VPlanogramaService{

	@Autowired
	private VPlanogramaDao vPlanopramaDao;

	@Override
	public List<VPlanograma> findAll(VPlanograma vPlanograma) throws Exception {
		// TODO Auto-generated method stub
		return this.vPlanopramaDao.findAll(vPlanograma);
	}

	@Override
	public VPlanograma findOne(VPlanograma vPlanograma) throws Exception {
		VPlanograma vPlanogramaRes = null;
		List<VPlanograma> lisvPlanograma = this.vPlanopramaDao.findAll(vPlanograma);
		if (lisvPlanograma!= null && !lisvPlanograma.isEmpty()){
			vPlanogramaRes = lisvPlanograma.get(0);						
		}
		return vPlanogramaRes;
	}

	public VPlanograma findTipoP(VPlanograma vPlanograma) throws Exception {
		VPlanograma vPlanogramaTipoP = this.findOne(vPlanograma);
		if(vPlanogramaTipoP != null){
			if(!((Constantes.TIPO_PLANOGRAMA).equals(vPlanogramaTipoP.getTipoPlano()) && vPlanogramaTipoP.getFacingAlto() != null && vPlanogramaTipoP.getFacingAncho() != null)){
				vPlanogramaTipoP = null;
			}
		}
		return vPlanogramaTipoP;
	}
	
	public PlanogramaVigente findDatosVegalsa(PlanogramaVigente planogramaVigente) throws Exception{
		PlanogramaVigente planogramaVigor = this.vPlanopramaDao.findDatosVegalsa(planogramaVigente);
		return this.vPlanopramaDao.findDatosIMCVegalsa(planogramaVigor==null?planogramaVigente:planogramaVigor);
	}

	@Override
	public Boolean vieneDeSIA(Long codCentro, Long codArt) throws Exception {
		return vPlanopramaDao.vieneDeSIA(codCentro, codArt);
	}
	
	
}
