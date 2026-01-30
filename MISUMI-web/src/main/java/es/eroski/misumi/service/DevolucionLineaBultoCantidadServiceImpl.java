package es.eroski.misumi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.eroski.misumi.dao.iface.DevolucionLineaBultoCantidadDao;
import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.Proveedor;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;
import es.eroski.misumi.service.iface.DevolucionLineaBultoCantidadService;


@Service(value = "devolucionLineaBultoCantidadService")
public class DevolucionLineaBultoCantidadServiceImpl implements DevolucionLineaBultoCantidadService {

	@Autowired
	private DevolucionLineaBultoCantidadDao devolucionLineaBultoCantidadDao;


	@Override
	public void insertAll(List<TDevolucionBulto> listaTDevolucionLinea) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionLineaBultoCantidadDao.insertAll(listaTDevolucionLinea);
	}
	
	@Override
	public void deleteLineasTablaBultoCantidad(String session, Long devolucion, Long codArticulo) throws Exception{
		// TODO Auto-generated method stub
		this.devolucionLineaBultoCantidadDao.deleteLineasTablaBultoCantidad(session, devolucion, codArticulo);
	}
	
	@Override
	public List<TDevolucionLinea> cargarBultoCantidadLinea(String session, List<TDevolucionLinea> listTDevolucionLinea) throws Exception{
		// TODO Auto-generated method stub
		return this.devolucionLineaBultoCantidadDao.cargarBultoCantidadLinea(session, listTDevolucionLinea);
	}
	
	@Override
	public void delete(TDevolucionLinea tDevolLinea) throws Exception {
		// TODO Auto-generated method stub
		this.devolucionLineaBultoCantidadDao.delete(tDevolLinea);

	}
	
	@Override
	public void deleteHistorico() throws Exception {
		// TODO Auto-generated method stub
		this.devolucionLineaBultoCantidadDao.deleteHistorico();
	}
	
	public List<TDevolucionBulto> findDatosRef(String session, Long devolucion, Long codArticulo) throws Exception{
		return this.devolucionLineaBultoCantidadDao.findDatosRef(session, devolucion, codArticulo);
	
	}
	
	@Override
	public List<DevolucionLinea> cargarBultoCantidadLineaEditada(String session, List<DevolucionLinea> listDevolucionLinea) throws Exception{
		// TODO Auto-generated method stub
		return this.devolucionLineaBultoCantidadDao.cargarBultoCantidadLineaEditada(session, listDevolucionLinea);
	}
	
	@Override
	public String cargarListaBultos(String session, TDevolucionLinea tDevolucionLinea) throws Exception{
		// TODO Auto-generated method stub
		return this.devolucionLineaBultoCantidadDao.cargarListaBultos(session, tDevolucionLinea);
	}
	
	public Long existenBultosPorbultoProveedor(String session,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto) throws Exception{
		// TODO Auto-generated method stub
		return this.devolucionLineaBultoCantidadDao.existenBultosPorbultoProveedor(session, devolucion,proveedor,proveedorTrabajo,bulto);
	}
	
	public void actualizarEstadoBultoPorProveedor(String session,String estado,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception{
		// TODO Auto-generated method stub
		this.devolucionLineaBultoCantidadDao.actualizarEstadoBultoPorProveedor(session,estado,devolucion,proveedor,proveedorTrabajo,bulto);
	}
	
	public BultoCantidad procedimientoActualizarEstadoBultoPorProveedor(String estado,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception{
		// TODO Auto-generated method stub
		return this.devolucionLineaBultoCantidadDao.procedimientoActualizarEstadoBultoPorProveedor(estado,devolucion,proveedor,proveedorTrabajo,bulto);
	}

	@Override
	public Proveedor cargarListaBultos(String session, String devolucion, Proveedor proveedor, String provrGen, String provrTrabajo) throws Exception {
		List<BultoCantidad> listaBultos = devolucionLineaBultoCantidadDao.cargarListaBultos(session, devolucion, provrGen, provrTrabajo);
		
		proveedor.setListaBultos(listaBultos);
		return proveedor;
	}

	@Override
	public boolean deleteBultoPorProvDev(String session, String devolucion, String provrGen, String provrTrabajo,
			String bulto) throws Exception {
		return this.devolucionLineaBultoCantidadDao.deleteBultoPorProvDev(session, devolucion, provrGen, provrTrabajo, bulto);
	}

	
	@Override
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception {
		return devolucionLineaBultoCantidadDao.centroParametrizado(codCentro, tipoPermiso);
	}
}
