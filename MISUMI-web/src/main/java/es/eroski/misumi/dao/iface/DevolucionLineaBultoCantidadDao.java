package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.BultoCantidad;
import es.eroski.misumi.model.DevolucionLinea;
import es.eroski.misumi.model.Proveedor;
import es.eroski.misumi.model.TDevolucionBulto;
import es.eroski.misumi.model.TDevolucionLinea;

public interface DevolucionLineaBultoCantidadDao  extends CentroParametrizadoDao{

	//Método para rellenar la tabla temporal con las líneas de devolución
	public void insertAll(List<TDevolucionBulto> listaTDevolucionLinea) throws Exception;
	
	//Método para borrar los datos de la tabla de bulto/cantidad asociada a una linea
	public void deleteLineasTablaBultoCantidad(String session, Long devolucion, Long codArticulo) throws Exception;
	
	//Método para cargar la lista de bulto cantidad asociada a una linea
	public List<TDevolucionLinea> cargarBultoCantidadLinea(String session, List<TDevolucionLinea> listTDevolucionLinea) throws Exception;
	
	//Método para eliminar los registros 
	public void delete(TDevolucionLinea tDevolLinea) throws Exception;
	
	//Método para eliminar los registros de la tabla temporal antiguos
	public void deleteHistorico() throws Exception;
	
	public List<TDevolucionBulto> findDatosRef(String session, Long devolucion, Long codArticulo) throws Exception;
	
	public List<DevolucionLinea> cargarBultoCantidadLineaEditada(String session, List<DevolucionLinea> listTDevolucionLinea) throws Exception;
	
	/**
	 * Obtiene una cadena con los bultos separados por comas.
	 * @param session
	 * @param tDevolucionLinea
	 * @return
	 * @throws Exception
	 */
	public String cargarListaBultos(String session, TDevolucionLinea tDevolucionLinea) throws Exception;
	
	public Long existenBultosPorbultoProveedor(String session,Long devolucion,Long proveedor,Long proveedorTrabajo,Long bulto)throws Exception;
	
	/**
	 * Actualiza el estado de la tabla T_MIS_DEVOLUCIONES_BULTO. (A)brir, (B)orrar, (C)errar.
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
	 * Invoca al procedimiento de SIA "PK_APR_DEVOLUCIONES_ACT_MISUMI.p_apr_act_devol_prov_bulto()"
	 * que se encarga de actualizar la tabla T_APR_DEVOLUCIONES_LIN_BULTOS y T_APR_DEVOLUCIONES_LIN.
	 * 
	 * @param estado
	 * @param devolucion
	 * @param proveedor
	 * @param proveedorTrabajo
	 * @param bulto
	 * @return
	 * @throws Exception
	 */
	public BultoCantidad procedimientoActualizarEstadoBultoPorProveedor(String estado, Long devolucion,Long proveedor, Long proveedorTrabajo, Long bulto)throws Exception;

	/**
	 * Obtiene una lista con los bultos para una devolucion-proveedor.
	 * 
	 * @param session
	 * @param devolucion
	 * @param proveedor
	 * @return
	 * @throws Exception
	 */
	public List<BultoCantidad> cargarListaBultos(String session, String devolucion, String provrGen, String provTrabajo) throws Exception;

	/**
	 * Borra un bulto para la sesion-devolución-proveedor en curso.
	 * @param session
	 * @param devolucion
	 * @param provrGen
	 * @param provrTrabajo
	 * @param bulto
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBultoPorProvDev(String session, String devolucion, String provrGen, String provrTrabajo, String bulto) throws Exception;

	
}
