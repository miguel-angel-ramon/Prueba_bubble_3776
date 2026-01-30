package es.eroski.misumi.model.pda.packingList;

import java.io.Serializable;

public class Palet  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int ceco;
	private String matricula;
	private boolean permiteActualizar;
	private String recepcionado;
	private String usuario;
	private String frecepcion;
	/**
	 * Contiene el valor de "frecepcion" con formato "DD/MM/YYYY".
	 */
	private String frecepcionFormateada; // Contiene el valor de "frecepcion" con formato "DD/MM/YYYY". 
	private Integer hrecepcion;
	/**
	 * Contiene el valor de "hrecepcion" con formato "HH:MI".
	 */
	private String hrecepcionFormateada; // Contiene el valor de "hrecepcion" con formato "HH:MI".
	private String fechaalbaran;
	private int plataforma;
	private boolean visible;
	private String pertenececentro;
	private String errorceco;
	
	private String mac;

	public Palet() {
		super();
	}

	public int getCeco() {
		return ceco;
	}

	public void setCeco(int ceco) {
		this.ceco = ceco;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isPermiteActualizar() {
		return permiteActualizar;
	}
	
	public void setPermiteActualizar(boolean permiteActualizar) {
		this.permiteActualizar = permiteActualizar;
	}

	public String getRecepcionado() {
		return recepcionado;
	}

	public void setRecepcionado(String recepcionado) {
		this.recepcionado = recepcionado;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFrecepcion() {
		return frecepcion;
	}

	public void setFrecepcion(String frecepcion) {
		this.frecepcion = frecepcion;
	}

	public Integer getHrecepcion() {
		return hrecepcion;
	}

	public void setHrecepcion(Integer hrecepcion) {
		this.hrecepcion = hrecepcion;
	}

	public String getFechaalbaran() {
		return fechaalbaran;
	}

	public void setFechaalbaran(String fechaalbaran) {
		this.fechaalbaran = fechaalbaran;
	}

	public int getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(int plataforma) {
		this.plataforma = plataforma;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getPertenececentro() {
		return pertenececentro;
	}

	public void setPertenececentro(String pertenececentro) {
		this.pertenececentro = pertenececentro;
	}

	public String getErrorceco() {
		return errorceco;
	}

	public void setErrorceco(String errorceco) {
		this.errorceco = errorceco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Recupera el valor de "frecepcion" con formato "DD/MM/YYYY".
	 * @return
	 */
	public String getFrecepcionFormateada() {
		return frecepcionFormateada;
	}

	public void setFrecepcionFormateada(String frecepcionFormateada) {
		this.frecepcionFormateada = frecepcionFormateada;
	}

	/**
	 * Recupera el valor de "hrecepcion" con formato "HH:MI".
	 * @return
	 */
	public String getHrecepcionFormateada() {
		return hrecepcionFormateada;
	}

	public void setHrecepcionFormateada(String hrecepcionFormateada) {
		this.hrecepcionFormateada = hrecepcionFormateada;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
