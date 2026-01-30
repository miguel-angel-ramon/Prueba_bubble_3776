package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class CapraboMotivoNoPedibleCentroArt implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long codLoc;
	private List<CapraboMotivoNoPedibleArt> articulos;
	
	public CapraboMotivoNoPedibleCentroArt() {
		super();
	}

	public CapraboMotivoNoPedibleCentroArt(Long codLoc, List<CapraboMotivoNoPedibleArt> articulos) {
		super();
		this.codLoc = codLoc;
		this.articulos = articulos;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public List<CapraboMotivoNoPedibleArt> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<CapraboMotivoNoPedibleArt> articulos) {
		this.articulos = articulos;
	}
}
