package es.eroski.misumi.model;

public class NumLoteMpGrid {
	private Long filasGrid;
	private Long nivelLote;
	private Long nivelModProv;

	public NumLoteMpGrid() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NumLoteMpGrid(Long filasGrid, Long nivelLote, Long nivelModProv) {
		super();
		this.filasGrid = filasGrid;
		this.nivelLote = nivelLote;
		this.nivelModProv = nivelModProv;
	}

	public Long getFilasGrid() {
		return filasGrid;
	}
	public void setFilasGrid(Long filasGrid) {
		this.filasGrid = filasGrid;
	}
	public Long getNivelLote() {
		return nivelLote;
	}
	public void setNivelLote(Long nivelLote) {
		this.nivelLote = nivelLote;
	}
	public Long getNivelModProv() {
		return nivelModProv;
	}
	public void setNivelModProv(Long nivelModProv) {
		this.nivelModProv = nivelModProv;
	}
}
