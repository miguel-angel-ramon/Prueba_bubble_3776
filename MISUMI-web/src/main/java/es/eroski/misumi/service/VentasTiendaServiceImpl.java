package es.eroski.misumi.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.VentasTiendaDao;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaRequestType;
import es.eroski.misumi.model.ventasTiendaWS.VentasTiendaResponseType;
import es.eroski.misumi.service.iface.VentasTiendaService;

@Service(value = "VentasTiendaService")
public class VentasTiendaServiceImpl implements VentasTiendaService  {
	
    @Autowired
	private VentasTiendaDao ventasTiendaDao;
    
    @Override
    public VentasTiendaResponseType consultaVentas(VentasTiendaRequestType ventasTiendaRequest, HttpSession session) throws Exception{
    	return ventasTiendaDao.consultaVentas(ventasTiendaRequest, session);
    }

	@Override
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception {
		return ventasTiendaDao.centroParametrizado(codCentro, tipoPermiso);
	}
}
