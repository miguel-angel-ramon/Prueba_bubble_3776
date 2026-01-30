package es.eroski.misumi.model;

import java.io.Serializable;

//Contiene la información de una Devolución y una lista que contiene la información de las líneas de la tabla 
//relacionada con esa devolución
public class ReposicionGuardar implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long codLoc;
	private String codMac;
	private Long tipoListado;
	private String modeloProveedor;
	private Long codArt;
	private String descrColor;
	private Double cantRepo;
	private String flgRevisada;
	private String flgSustituida;

	private Long codError;
	private String descError;

	private String area;

	
	public ReposicionGuardar() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReposicionGuardar( Long codLoc,
	 String codMac,
	 Long tipoListado,
	 String modeloProveedor,
	 Long codArt,
	 String descrColor,
	 Double cantRepo,
	 String flgRevisada,
	 String flgSustituida,
	 Long codError,
	 String descError) {
		
		super();
		
		this.codLoc = codLoc;
		this.codMac = codMac;
		this.tipoListado = tipoListado;
		this.modeloProveedor = modeloProveedor;
		this.codArt = codArt;
		this.descrColor = descrColor;
		this.cantRepo = cantRepo;	
		this.flgRevisada = flgRevisada;
		this.flgSustituida = flgSustituida;		
		this.codError = codError;
		this.descError = descError;
		
	}

	public Long getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getCodMac() {
		return codMac;
	}

	public void setCodMac(String codMac) {
		this.codMac = codMac;
	}

	public Long getTipoListado() {
		return tipoListado;
	}

	public void setTipoListado(Long tipoListado) {
		this.tipoListado = tipoListado;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescrColor() {
		return descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}

	public Double getCantRepo() {
		return cantRepo;
	}

	public void setCantRepo(Double cantRepo) {
		this.cantRepo = cantRepo;
	}

	public String getFlgRevisada() {
		return flgRevisada;
	}

	public void setFlgRevisada(String flgRevisada) {
		this.flgRevisada = flgRevisada;
	}

	public String getFlgSustituida() {
		return flgSustituida;
	}

	public void setFlgSustituida(String flgSustituida) {
		this.flgSustituida = flgSustituida;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}