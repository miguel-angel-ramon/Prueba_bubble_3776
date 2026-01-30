package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

//Contiene la información de una Devolución y una lista que contiene la información de las líneas de la tabla 
//relacionada con esa devolución
public class Reposicion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	

	private Long codLoc;
	private String codMac;
	private Long tipoListado;
	
	private String modeloProveedor;
	private String denominacion;
	private String descrColor;
	private Long codArtFoto; 
	private Long estado;
	
	private List<ReposicionLinea> reposicionLineas;
	
	//Campos para simular la paginacion en la pistola
	private Long paginasTotales;
	private Long posicion;

	private String flgSigPosVacia;
	
	private String flgRevisada;
	private String flgSustituida;
	
	private Long codArt; //Para el borrar en el caso NO TEXTIL
	

	
	private Long codError;
	private String descError;

	private String area;

	//Misumi-195
	private String seccion;
	
	public Reposicion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Reposicion( Long codLoc,
	 String codMac,
	 Long tipoListado,
	 String modeloProveedor,
	 String denominacion,
	 String descrColor,
	 Long codArtFoto,
	 Long estado,
	 List<ReposicionLinea> reposicionLineas,
	 Long paginasTotales,
	 Long posicion,
	 String flgRevisada,
	 String flgSustituida,
	 Long codError,
	 String descError) {
		
		super();
		
		this.codLoc = codLoc;
		this.codMac = codMac;
		this.tipoListado = tipoListado;
		
		this.modeloProveedor = modeloProveedor;
		this.denominacion = denominacion;
		this.descrColor = descrColor;
		this.codArtFoto = codArtFoto;
		this.estado = estado;
		
		this.reposicionLineas = reposicionLineas;
		
		this.paginasTotales = paginasTotales;
		this.posicion = posicion;
		
		this.flgRevisada = flgRevisada;
		this.flgSustituida = flgSustituida;
		
		this.codError = codError;
		this.descError = descError;
	}

	public Reposicion( Long codLoc,
			 String codMac,
			 Long tipoListado,
			 String modeloProveedor,
			 String denominacion,
			 String descrColor,
			 Long codArtFoto,
			 Long estado,
			 List<ReposicionLinea> reposicionLineas,
			 Long paginasTotales,
			 Long posicion,
			 String flgSigPosVacia,
			 String flgRevisada,
			 String flgSustituida,
			 String area,
			 Long codError,
			 String descError) {
				
				super();
				
				this.codLoc = codLoc;
				this.codMac = codMac;
				this.tipoListado = tipoListado;
				
				this.modeloProveedor = modeloProveedor;
				this.denominacion = denominacion;
				this.descrColor = descrColor;
				this.codArtFoto = codArtFoto;
				this.estado = estado;
				
				this.reposicionLineas = reposicionLineas;
				
				this.paginasTotales = paginasTotales;
				this.posicion = posicion;
				this.flgSigPosVacia = flgSigPosVacia;
				
				this.flgRevisada = flgRevisada;
				this.flgSustituida = flgSustituida;
				
				this.area = area;
				this.codError = codError;
				this.descError = descError;
			}

	public Reposicion( Long codLoc,
			 String codMac,
			 Long tipoListado,
			 String modeloProveedor,
			 String denominacion,
			 String descrColor,
			 Long codArtFoto,
			 Long estado,
			 List<ReposicionLinea> reposicionLineas,
			 Long paginasTotales,
			 Long posicion,
			 String flgSigPosVacia,
			 String flgRevisada,
			 String flgSustituida,
			 String area,
			 String seccion,
			 Long codError,
			 String descError) {
				
				super();
				
				this.codLoc = codLoc;
				this.codMac = codMac;
				this.tipoListado = tipoListado;
				
				this.modeloProveedor = modeloProveedor;
				this.denominacion = denominacion;
				this.descrColor = descrColor;
				this.codArtFoto = codArtFoto;
				this.estado = estado;
				
				this.reposicionLineas = reposicionLineas;
				
				this.paginasTotales = paginasTotales;
				this.posicion = posicion;
				this.flgSigPosVacia = flgSigPosVacia;
				
				this.flgRevisada = flgRevisada;
				this.flgSustituida = flgSustituida;
				
				this.area = area;
				
				this.seccion = seccion;
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

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescrColor() {
		return descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}

	public Long getCodArtFoto() {
		return codArtFoto;
	}

	public void setCodArtFoto(Long codArtFoto) {
		this.codArtFoto = codArtFoto;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public List<ReposicionLinea> getReposicionLineas() {
		return reposicionLineas;
	}

	public void setReposicionLineas(List<ReposicionLinea> reposicionLineas) {
		this.reposicionLineas = reposicionLineas;
	}

	public Long getPaginasTotales() {
		return paginasTotales;
	}

	public void setPaginasTotales(Long paginasTotales) {
		this.paginasTotales = paginasTotales;
	}

	public Long getPosicion() {
		return posicion;
	}

	public void setPosicion(Long posicion) {
		this.posicion = posicion;
	}
	
	public String getFlgSigPosVacia() {
		return flgSigPosVacia;
	}

	public void setFlgSigPosVacia(String flgSigPosVacia) {
		this.flgSigPosVacia = flgSigPosVacia;
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

	public Long getCodArt() {
		return codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	
	
}