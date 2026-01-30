package es.eroski.misumi.dao.packingList.iface;

import java.util.List;

import es.eroski.misumi.model.pda.packingList.Palet;

/**
 * Contiene las interfaces relacionadas con los Palets de Packing List.
 * @author BICAGAAN
 *
 */
public interface PaletDao {

	/**
	 * Obtiene la lista de palets a los que se ha dado entrada en el día de hoy.
	 * @return
	 */
	public List<Palet> getEntradasPalets(Long centro, String mac);
	
	/**
	 * Almacenar en BBDD los datos de la nueva Entrada de Palet registrada en el API.
	 * @param palet
	 * @param mac
	 */
	public Boolean saveEntradaPalet(Palet palet, String mac);
}
