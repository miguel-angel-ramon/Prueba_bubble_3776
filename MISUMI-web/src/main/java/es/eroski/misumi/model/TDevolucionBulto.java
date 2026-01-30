package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class TDevolucionBulto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String idSesion;
	private Date fechaGen;
	private Long devolucion;
	private Long bulto;
	private Long codArticulo;
	private Long bultoOri;
	
	private Double stock;
    private Double stockOri;
    private String estadoCerrado;
	private Long codError;
	private String descError;
	private Date creationDate;
	
	public TDevolucionBulto() {
		super();
	}
	
	public TDevolucionBulto(String idSesion, Date fechaGen, Long devolucion, Long bulto, Long codArticulo, 
			Long bultoOri, Double stock, Double stockOri, String estadoCerrado, Long codError, String descError, Date creationDate) {
		super();
		this.idSesion = idSesion;
		this.fechaGen = fechaGen;
		this.devolucion = devolucion;
		this.bulto = bulto;
		this.codArticulo = codArticulo;
		this.bultoOri = bultoOri;
		this.stock = stock;
		this.stockOri = stockOri;
		this.estadoCerrado = estadoCerrado;
		this.codError = codError;
		this.descError = descError;
		this.creationDate = creationDate;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Date getFechaGen() {
		return fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public Long getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Long devolucion) {
		this.devolucion = devolucion;
	}

	public Long getBulto() {
		return bulto;
	}

	public void setBulto(Long bulto) {
		this.bulto = bulto;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getBultoOri() {
		return bultoOri;
	}

	public void setBultoOri(Long bultoOri) {
		this.bultoOri = bultoOri;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getStockOri() {
		return stockOri;
	}

	public void setStockOri(Double stockOri) {
		this.stockOri = stockOri;
	}

	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getEstadoCerrado() {
		return estadoCerrado;
	}

	public void setEstadoCerrado(String estadoCerrado) {
		this.estadoCerrado = estadoCerrado;
	}

	
}
