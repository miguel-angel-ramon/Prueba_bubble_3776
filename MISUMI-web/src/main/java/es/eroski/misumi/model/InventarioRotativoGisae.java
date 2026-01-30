package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class InventarioRotativoGisae implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArea;
	private String descArea;
	private Long codSeccion;
	private String descSeccion;
	private Long codArticulo;
	private Boolean nsr;
	private String tipoListado;
	private Date fechaGen;
	private Boolean tratado;

	
	public InventarioRotativoGisae() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventarioRotativoGisae(Long codCentro, Long codArea,
			String descArea, Long codSeccion, String descSeccion,Long codArticulo,
			Boolean nsr,String tipoListado,Date fechaGen) {
		super();
		this.codCentro = codCentro;
		this.codArea = codArea;
		this.descArea = descArea;
		this.codSeccion = codSeccion;
		this.descSeccion = descSeccion;
		this.codArticulo = codArticulo;
		this.nsr = nsr;
		this.tipoListado = tipoListado;
		this.fechaGen = fechaGen;

	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public String getDescArea() {
		return descArea;
	}

	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}

	public Long getCodSeccion() {
		return codSeccion;
	}

	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}

	public String getDescSeccion() {
		return descSeccion;
	}

	public void setDescSeccion(String descSeccion) {
		this.descSeccion = descSeccion;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Boolean getNsr() {
		return nsr;
	}

	public void setNsr(Boolean nsr) {
		this.nsr = nsr;
	}

	public String getTipoListado() {
		return tipoListado;
	}

	public void setTipoListado(String tipoListado) {
		this.tipoListado = tipoListado;
	}

	public Date getFechaGen() {
		return fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public Boolean getTratado() {
		return tratado;
	}

	public void setTratado(Boolean tratado) {
		this.tratado = tratado;
	}

}