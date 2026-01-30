package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.EstructuraCom;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.VArtSfm;

public interface VArtFacingCeroDao  {

	/**
	 * Consulta la informacion de las referencias con Facing Cero
	 * a partir de ejecutar <code>PK_APR_MISUMI_IMC.P_APR_CONSULTA_REF_FACING_0</code>.
	 * <p> Si el nivel no se indica se calcula con los codigos de N1 a N5
	 * 
	 * @param codLoc
	 * @param codN1
	 * @return
	 */
	public SfmCapacidadFacing obtenerFacingCero(final Long codLoc, final String codN1);

	/** Ejecuta PK_APR_MISUMI_IMC.P_APR_GRABAR_REF_VALIDADA,
	 * Que actualiza la informacion de SFM FAC CAP
	 * 
	 * @param listaRefsValidadas
	 * @return
	 */
	public SfmCapacidadFacing grabarRefsValidadas(List<VArtSfm> listaRefsValidadas);
	
	
}
