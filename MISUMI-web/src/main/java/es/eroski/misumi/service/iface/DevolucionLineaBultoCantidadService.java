package es.eroski.misumi.service.iface;

import java.util.List;

import es.eroski.misumi.model.Proveedor;
import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;

public interface DevolucionLineaBultoCantidadService{
	//Método para rellenar la tabla temporal con los stocks y los bultos de una referencia
	public void insertAll(List<TDevolucionBulto> listaTDevolucionLinea) throws Exception;
	
	//Método para borrar los datos de la tabla de bulto/cantidad asociada a una linea
	public void deleteLineasTablaBultoCantidad(String session, Long devolucion, Long codArticulo) throws Exception;
	
	//Método para cargar la lista de bulto cantidad asociada a una linea
	public List<TDevolucionLinea> cargarBultoCantidadLinea(String session, List<TDevolucionLinea> listTDevolucionLinea) throws Exception;
	
	//Método para borrar los registros existentes de un usuario de la tabla de bulto/cantidad 
	public void delete(TDevolucionLinea tDevolLinea) throws Exception;
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistorico() throws Exception;
	
	//Método para obtener las cantidades y los bultos asociados a una referencia
	public List<TDevolucionBulto> findDatosRef(String session, Long devolucion, Long codArticulo) throws Exception;
	
	//Método para cargar la lista de bulto cantidad asociada a una linea
	public List<DevolucionLinea> cargarBultoCantidadLineaEditada(String session, List<DevolucionLinea> listDevolucionLinea) throws Exception;
	
	//Cargar la lista de bultos. 
	public String cargarListaBultos(String session, TDevolucionLinea tDevolucionLinea) throws Exception;

	public Long existenBultosPorbultoProveedor(String session,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception;
	
	/**
	 * 
	 * @param session
	 * @param estado
	 * @param devolucion
	 * @param proveedor
	 * @param proveedorTrabajo
	 * @param bulto
	 * @throws Exception
	 */
	public void actualizarEstadoBultoPorProveedor(String session,String estado,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception;
	/**
	 * Invoca al paquete de SIA para realizar sobre el bulto la operación que se le indique.
	 * 
	 * 'A' - Abrir bulto, 
	 * 'B' - Borrar bulto, 
	 * 'C' - Cerrar bulto
	 * 
	 * @param estado
	 * @param devolucion
	 * @param proveedor
	 * @param proveedorTrabajo
	 * @param bulto
	 * @return
	 * @throws Exception
	 */
	public BultoCantidad procedimientoActualizarEstadoBultoPorProveedor(String estado,Long devolucion, Long proveedor, Long proveedorTrabajo, Long bulto)throws Exception;
	/**
	 * 
	 * @param session
	 * @param devolucion
	 * @param proveedor
	 * @param provrGen
	 * @param provrTrabajo
	 * @return
	 * @throws Exception
	 */
	Proveedor cargarListaBultos(String session, String devolucion, Proveedor proveedor, String provrGen, String provrTrabajo) throws Exception;
	/**
	 * 
	 * @param session
	 * @param devolucion
	 * @param provrGen
	 * @param provrTrabajo
	 * @param bulto
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBultoPorProvDev(String session, String devolucion, String provrGen, String provrTrabajo, String bulto) throws Exception;
	/**
	 * Indica si el centro está parametrizado.
	 * @param codCentro
	 * @param tipoPermiso
	 * @return
	 * @throws Exception
	 */
	public boolean isCentroParametrizado(Long codCentro, String tipoPermiso) throws Exception;	
}
