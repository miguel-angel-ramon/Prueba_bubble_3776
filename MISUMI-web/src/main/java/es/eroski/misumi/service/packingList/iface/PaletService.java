package es.eroski.misumi.service.packingList.iface;

import java.sql.SQLException;
import java.util.List;

import es.eroski.misumi.model.pda.packingList.Palet;
import es.eroski.misumi.model.pda.packingList.RdoRecepcionadoWrapper;
import es.eroski.misumi.model.pda.packingList.RdoValidarCecoWrapper;

/**
 * 
 * @author BICAGAAN
 *
 */
public interface PaletService {

	/**
	 * Obtiene la lista de palets a los que se ha dado entrada en el día de hoy.
	 * @return
	 * @throws SQLException 
	 */
	public List<Palet> getEntradasPalets(Long centro, String mac) throws SQLException;
	
	/**
	 * Almacenar en BBDD los datos de la nueva Entrada de Palet recepcionada en el API.
	 * @param palet
	 * @param mac
	 */
	public void saveEntradaPalet(Palet palet, String mac);
	
	/**
	 * Validación del ceco. Se obtiene el token para el resto de servicios.
	 * @param ceco
	 * @return
	 */
	public RdoValidarCecoWrapper validarCeco(String ceco);
	
	/**
	 * Dar entrada a las matrículas.
	 * @param matricula
	 * @param sesion
	 * @return
	 * @throws Exception 
	 */
	public RdoRecepcionadoWrapper recepcionarPalet(String matricula, String sesion) throws Exception;

	/**
	 * Recuperar todas las matrículas recepcionadas en el día de HOY.
	 * @param numRows
	 * @param page
	 * @param code
	 * @param description
	 * @param matricula
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String listarArticulos(int numRows, int page, String code, String description, String matricula, String startDate, String endDate);

}
