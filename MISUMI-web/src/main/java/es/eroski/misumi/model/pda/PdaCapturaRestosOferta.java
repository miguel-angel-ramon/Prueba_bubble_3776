/**
 * 
 */
package es.eroski.misumi.model.pda;

/**
 * @author BICUGUAL
 *
 */
public class PdaCapturaRestosOferta {

	private Long codOferta;
	private Long anoOferta;
	
	public PdaCapturaRestosOferta(Long codOferta, Long anoOferta, Boolean mostrarCabeceraX) {
		super();
		this.codOferta = codOferta;
		this.anoOferta = anoOferta;
		this.mostrarCabeceraX = mostrarCabeceraX;
	}

	private Boolean mostrarCabeceraX;

	public Long getCodOferta() {
		return codOferta;
	}

	public void setCodOferta(Long codOferta) {
		this.codOferta = codOferta;
	}

	public Long getAnoOferta() {
		return anoOferta;
	}

	public void setAnoOferta(Long anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Boolean getMostrarCabeceraX() {
		return mostrarCabeceraX;
	}

	public void setMostrarCabeceraX(Boolean mostrarCabeceraX) {
		this.mostrarCabeceraX = mostrarCabeceraX;
	}
	
}
