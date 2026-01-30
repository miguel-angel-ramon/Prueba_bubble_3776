package es.eroski.misumi.model.pda;

import java.io.Serializable;

public class PdaUltimosEnvios implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fechaEnvio;
	private Long artPed;
	private String tipoPedido;
	private Long artNsr;
	
	
	public String getFechaEnvio() {
		return this.fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	public Long getArtPed() {
		return this.artPed;
	}
	public void setArtPed(Long artPed) {
		this.artPed = artPed;
	}
	public String getTipoPedido() {
		return this.tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public Long getArtNsr() {
		return this.artNsr;
	}
	public void setArtNsr(Long artNsr) {
		this.artNsr = artNsr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
