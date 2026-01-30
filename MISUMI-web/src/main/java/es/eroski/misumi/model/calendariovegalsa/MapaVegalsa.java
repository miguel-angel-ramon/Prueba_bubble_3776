/**
 * 
 */
package es.eroski.misumi.model.calendariovegalsa;

/**
 * MISUMI-301
 * Pojo para el tratamiento de regitros de la tabla T_MIS_MAPAS_VEGALSA
 * @author BICUGUAL
 *
 */
public class MapaVegalsa {

	private Integer codMapa;
	private String descMapa;
	
	public MapaVegalsa() {
		super();
	}
	public MapaVegalsa(Integer codMapa, String descMapa) {
		super();
		this.codMapa = codMapa;
		this.descMapa = descMapa;
	}
	public Integer getCodMapa() {
		return codMapa;
	}
	public void setCodMapa(Integer codMapa) {
		this.codMapa = codMapa;
	}
	public String getDescMapa() {
		return descMapa;
	}
	public void setDescMapa(String descMapa) {
		this.descMapa = descMapa;
	}
	
	
}
