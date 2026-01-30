package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.ArrayList;

public class PdaStock implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<PdaArticulo> listaArticulos;
	private ArrayList<PdaArticulo> listaArticulosPagina;
	private int posicionGrupoArticulos;
	private int totalArticulos;
	private int totalPaginas;
	private String codigoError;
	private String descripcionError;
	private String tipoMensaje;
	private Long codArtOrig;
	private String MMC;
	private String origen;
	private String origenInventario;
	private String origenGISAE;
	private String noGuardar;
	private String seccion;
	

	public ArrayList<PdaArticulo> getListaArticulos() {
		return this.listaArticulos;
	}

	public void setListaArticulos(ArrayList<PdaArticulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public ArrayList<PdaArticulo> getListaArticulosPagina() {
		return this.listaArticulosPagina;
	}

	public void setListaArticulosPagina(ArrayList<PdaArticulo> listaArticulosPagina) {
		this.listaArticulosPagina = listaArticulosPagina;
	}

	public int getTotalArticulos() {
		return this.totalArticulos;
	}

	public void setTotalArticulos(int totalArticulos) {
		this.totalArticulos = totalArticulos;
	}

	public int getTotalPaginas() {
		return this.totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getPosicionGrupoArticulos() {
		return this.posicionGrupoArticulos;
	}

	public void setPosicionGrupoArticulos(int posicionGrupoArticulos) {
		this.posicionGrupoArticulos = posicionGrupoArticulos;
	}

	public String getCodigoError() {
		return this.codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return this.descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Long getCodArtOrig() {
		return this.codArtOrig;
	}

	public void setCodArtOrig(Long codArtOrig) {
		this.codArtOrig = codArtOrig;
	}

	public String getMMC() {
		return this.MMC;
	}

	public void setMMC(String mMC) {
		MMC = mMC;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getOrigenInventario() {
		return this.origenInventario;
	}

	public void setOrigenInventario(String origenInventario) {
		this.origenInventario = origenInventario;
	}

	public String getOrigenGISAE() {
		return origenGISAE;
	}

	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}

	public String getNoGuardar() {
		return noGuardar;
	}

	public void setNoGuardar(String noGuardar) {
		this.noGuardar = noGuardar;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

}
