package es.eroski.misumi.model;

public class PedidoAdicionalNuevo {

	private Long codCentro;
	private Long codArticulo;
	private String oferta;
	private String fechaInicioMontaje;
	private String fechaFinMontaje;
	private Double cantidad;
	private String user;
	
	public PedidoAdicionalNuevo() {
		super();
	}

	public PedidoAdicionalNuevo( Long codCentro, Long codArticulo ){
		this(codCentro, codArticulo, null, null, null, null, null);		
	}
	
	public PedidoAdicionalNuevo( Long codCentro, Long codArticulo, String oferta, String fechaInicioMontaje
							   , String fechaFinMontaje, Double cantidad, String user) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.oferta = oferta;
		this.fechaInicioMontaje = fechaInicioMontaje;
		this.fechaFinMontaje = fechaFinMontaje;
		this.cantidad = cantidad;
		this.user = user;
	}

	public String getOferta() {
		return oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public String getFechaInicioMontaje() {
		return fechaInicioMontaje;
	}

	public void setFechaInicioMontaje(String fechaInicioMontaje) {
		this.fechaInicioMontaje = fechaInicioMontaje;
	}

	public String getFechaFinMontaje() {
		return fechaFinMontaje;
	}

	public void setFechaFinMontaje(String fechaFinMontaje) {
		this.fechaFinMontaje = fechaFinMontaje;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Long getCodCentro() {
		return codCentro;
	}
	
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public Long getCodArticulo() {
		return codArticulo;
	}
	
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
