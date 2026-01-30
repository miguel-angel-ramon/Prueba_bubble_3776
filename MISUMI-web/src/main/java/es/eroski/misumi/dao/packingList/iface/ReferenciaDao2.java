package es.eroski.misumi.dao.packingList.iface;

import java.util.List;

import es.eroski.misumi.model.pda.packingList.FechaReferencia;

/**
 * Contiene las interfaces relacionadas con los Palets de Packing List.
 * @author BICAGAAN
 *
 */
public interface ReferenciaDao2 {

	/**
	 * Obtiene la lista de palets a los que se ha dado entrada en el día de hoy.
	 * @return
	 */
	 public String getUltimaFechaFestiva(FechaReferencia fechaReferencia) throws Exception;
}
