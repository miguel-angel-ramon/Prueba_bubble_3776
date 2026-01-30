package es.eroski.misumi.model;

public class CestasNavidadArticulo {
	//Código de la cesta del lote
	private Long codArtLote;

	//Id unica tabla CESTAS_NAVIDAD_ARTICULOS
	private Long idCestasNavidadArticulo;

	//Título y descripción de los artículos del lote
	private String tituloArticuloLote;
	private String descrArticuloLote;
	
	public CestasNavidadArticulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CestasNavidadArticulo(Long codArtLote, Long idCestasNavidadArticulo, String tituloArticuloLote,
			String descrArticuloLote) {
		super();
		this.codArtLote = codArtLote;
		this.idCestasNavidadArticulo = idCestasNavidadArticulo;
		this.tituloArticuloLote = tituloArticuloLote;
		this.descrArticuloLote = descrArticuloLote;
	}
	
	public Long getIdCestasNavidadArticulo() {
		return idCestasNavidadArticulo;
	}
	public void setIdCestasNavidadArticulo(Long idCestasNavidadArticulo) {
		this.idCestasNavidadArticulo = idCestasNavidadArticulo;
	}
	
	public Long getCodArtLote() {
		return codArtLote;
	}
	public void setCodArtLote(Long codArtLote) {
		this.codArtLote = codArtLote;
	}
	public String getTituloArticuloLote() {
		return tituloArticuloLote;
	}
	public void setTituloArticuloLote(String tituloArticuloLote) {
		this.tituloArticuloLote = tituloArticuloLote;
	}
	public String getDescrArticuloLote() {
		return descrArticuloLote;
	}
	public void setDescrArticuloLote(String descrArticuloLote) {
		this.descrArticuloLote = descrArticuloLote;
	}
}
