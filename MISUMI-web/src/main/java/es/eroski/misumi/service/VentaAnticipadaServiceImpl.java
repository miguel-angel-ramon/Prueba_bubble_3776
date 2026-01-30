package es.eroski.misumi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;

import es.eroski.misumi.dao.iface.VFestivoCentroDao;
import es.eroski.misumi.dao.iface.VentaAnticipadaDao;
import es.eroski.misumi.model.VFestivoCentro;
import es.eroski.misumi.model.VentaAnticipada;
import es.eroski.misumi.service.iface.VentaAnticipadaService;


@Service(value = "VentaAnticipadaService")
public class VentaAnticipadaServiceImpl implements VentaAnticipadaService {
	
	@Autowired
	private VentaAnticipadaDao ventaAnticipadaDao;
	
	@Autowired
	private VFestivoCentroDao vFestivoCentroDao;

	@Override
	public VentaAnticipada obtenerVentaAnticipada(VentaAnticipada ventaAnticipada) throws Exception{
		
		VFestivoCentro vFestivoCentro = new VFestivoCentro();
		vFestivoCentro.setCodCentro(ventaAnticipada.getCodCentro());
		vFestivoCentro.setFechaFestivo(new Date());
		ventaAnticipada.setFechaGen(this.vFestivoCentroDao.getNextDay(vFestivoCentro));
		VentaAnticipada vAnt = this.ventaAnticipadaDao.find(ventaAnticipada);
		if (null != vAnt){
			vAnt.setExiste(true);
		} else {
			vAnt = ventaAnticipada;
			vAnt.setExiste(false);
		}

		return vAnt;
	}

	@Override
	public void guardarVentaAnticipada(VentaAnticipada ventaAnticipada) throws Exception {
		
		if (ventaAnticipada.getCantidad().equals(new Double(0)) && ventaAnticipada.getExiste()){
			this.ventaAnticipadaDao.delete(ventaAnticipada);
		} else if (ventaAnticipada.getCantidad().compareTo(new Double (0)) == 1 && !ventaAnticipada.getExiste()){
			ventaAnticipada.setCreationDate(new Date());
			ventaAnticipada.setLastUpdate(new Date());
			try {
			this.ventaAnticipadaDao.insert(ventaAnticipada);
			} catch (DuplicateKeyException dke){
				this.ventaAnticipadaDao.update(ventaAnticipada);
			}
		} else if (ventaAnticipada.getCantidad().compareTo(new Double (0)) == 1){
			ventaAnticipada.setLastUpdate(new Date());
			this.ventaAnticipadaDao.update(ventaAnticipada);
		}

	}

}
