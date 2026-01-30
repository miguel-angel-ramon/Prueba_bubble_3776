package es.eroski.misumi.dao.iface;

import java.util.List;

import es.eroski.misumi.model.EstructuraCom;
import es.eroski.misumi.model.SfmCapacidadFacing;
import es.eroski.misumi.model.VArtSfm;

public interface VArtSfmDao  {

	/** 
	 * Consulta la informacion de SFM FAC CAP
	 * a partir de ejecutar <code>PK_APR_MISUMI_IMC.P_APR_OBTENER_SFM_FAC_CAP</code>.
	 * <p> Si el nivel no se indica se calcula con los codigos de N1 a N5 */
	public SfmCapacidadFacing obtenerSfmFacCap(
			final Long codLoc, 
			final Long codArticulo,
			final Number nivel,
			final String codN1,
			final String codN2,
			final String codN3,
			final String codN4,
			final String codN5,
			final String pedir,
			final String lote,
			final Long codArticuloLote
			);

	/** Ejecuta PK_APR_MISUMI_IMC.P_APR_ACTUALIZAR_SFM_FAC_CAP,
	 * Que actualiza la informacion de SFM FAC CAP */
	public SfmCapacidadFacing actualizacionSfmFacCap(List<VArtSfm> listaActualizacion, String tipoEstructura, String usuario);

	public Long recalcularCapacidad(Long codLoc, Long codArticulo, Long facingCentro);
	
	/** Consulta PK_APR_MISUMI_IMC.P_APR_OBTENER_ESTR_COM */
	public EstructuraCom obtenerEstrCom(Long codLoc, Long codN1, Long codN2, Long codN3, Long codN4);

	/**
	 * El metodo devuelve el nombre del boton consultando a un paquete de SIA.
	 * En caso de nulo el paquete siempre pretende devolver un valor.
	 * @param codCentro
	 * @return Siempre se espera devolver valor no nulo.
	 */
	public String obtenerMetodosBoton(Long codCentro);

}
