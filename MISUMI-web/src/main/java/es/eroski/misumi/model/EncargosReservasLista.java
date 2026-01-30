package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class EncargosReservasLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<EncargoReserva> datos;
    private Long estado;
	private String descEstado;
	
	//Parámetros adicionales para llamadas a procedimientos
	private Long codLoc;
	private String codN1;
	private String codN2;
	private String codN3;
	private Long codArtFormlog;
	private Long tipoPedidoAdicional;
	
	public EncargosReservasLista() {
		super();
	}

	public EncargosReservasLista(List<EncargoReserva> datos, Long estado, String descEstado) {
		super();
		this.datos = datos;
		this.estado = estado;
		this.descEstado = descEstado;
	}
	
	public List<EncargoReserva> getDatos() {
		return this.datos;
	}
	
	public void setDatos(List<EncargoReserva> datos) {
		this.datos = datos;
	}
	
	public Long getEstado() {
		return this.estado;
	}
	
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	public String getDescEstado() {
		return this.descEstado;
	}
	
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public Long getCodLoc() {
		return this.codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getCodN1() {
		return this.codN1;
	}

	public void setCodN1(String codN1) {
		this.codN1 = codN1;
	}

	public String getCodN2() {
		return this.codN2;
	}

	public void setCodN2(String codN2) {
		this.codN2 = codN2;
	}

	public String getCodN3() {
		return this.codN3;
	}

	public void setCodN3(String codN3) {
		this.codN3 = codN3;
	}

	public Long getCodArtFormlog() {
		return this.codArtFormlog;
	}

	public void setCodArtFormlog(Long codArtFormlog) {
		this.codArtFormlog = codArtFormlog;
	}

	public Long getTipoPedidoAdicional() {
		return this.tipoPedidoAdicional;
	}

	public void setTipoPedidoAdicional(Long tipoPedidoAdicional) {
		this.tipoPedidoAdicional = tipoPedidoAdicional;
	}
}