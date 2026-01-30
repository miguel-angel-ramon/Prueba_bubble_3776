package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class CapraboMotivoNoPedibleArt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long codArticulo;
	private String tipoMovimiento;
	private String pedible;
	private List<CapraboMotivoNoPedibleMotivo> motivos;

	public CapraboMotivoNoPedibleArt() {
		super();
	}

	public CapraboMotivoNoPedibleArt(Long codArticulo, String tipoMovimiento, String pedible,
			List<CapraboMotivoNoPedibleMotivo> motivos) {
		super();
		this.codArticulo = codArticulo;
		this.tipoMovimiento = tipoMovimiento;
		this.pedible = pedible;
		this.motivos = motivos;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getPedible() {
		return this.pedible;
	}

	public void setPedible(String pedible) {
		this.pedible = pedible;
	}

	public List<CapraboMotivoNoPedibleMotivo> getMotivos() {
		return this.motivos;
	}

	public void setMotivos(List<CapraboMotivoNoPedibleMotivo> motivos) {
		this.motivos = motivos;
	}
}
