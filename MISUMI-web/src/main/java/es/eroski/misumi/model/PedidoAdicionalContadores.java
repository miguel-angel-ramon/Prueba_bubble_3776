package es.eroski.misumi.model;

import java.io.Serializable;

public class PedidoAdicionalContadores implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long contadorEncargos;
	private Long contadorMontaje;
	private Long contadorMontajeOferta;
	private Long contadorEmpuje;
	private Long contadorValidarCantExtra;
	private Long contadorEncargosCliente;
	private Long contadorMAC;
	
	//Campos para control de errores
   	private Long codError;
   	private String descError;
   	
   	//Campo para el valor por defecto combo Validar Cantidad Extra
   	private String defaultDescription;
	
	
	public PedidoAdicionalContadores() {
	    super();
	    this.contadorEncargos = new Long(0);
	    this.contadorMontaje = new Long(0);
	    this.contadorMontajeOferta = new Long(0);
	}

	public PedidoAdicionalContadores(Long contadorEncargos, Long contadorMontaje, Long contadorMontajeOferta, Long codError, String descError) {
		super();
		this.contadorEncargos = contadorEncargos;
		this.contadorMontaje = contadorMontaje;
		this.contadorMontajeOferta = contadorMontajeOferta;
		this.codError = codError;
		this.descError = descError;
	}

	public Long getContadorEncargos() {
		return this.contadorEncargos;
	}

	public void setContadorEncargos(Long contadorEncargos) {
		this.contadorEncargos = contadorEncargos;
	}

	public Long getContadorMontaje() {
		return this.contadorMontaje;
	}

	public void setContadorMontaje(Long contadorMontaje) {
		this.contadorMontaje = contadorMontaje;
	}

	public Long getContadorMontajeOferta() {
		return this.contadorMontajeOferta;
	}
	
	public Long getContadorEmpuje() {
		return this.contadorEmpuje;
	}

	public void setContadorEmpuje(Long contadorEmpuje) {
		this.contadorEmpuje = contadorEmpuje;
	}

	public Long getContadorValidarCantExtra() {
		return this.contadorValidarCantExtra;
	}

	public void setContadorValidarCantExtra(Long contadorValidarCantExtra) {
		this.contadorValidarCantExtra = contadorValidarCantExtra;
	}

	public Long getContadorEncargosCliente() {
		return this.contadorEncargosCliente;
	}

	public void setContadorEncargosCliente(Long contadorEncargosCliente) {
		this.contadorEncargosCliente = contadorEncargosCliente;
	}

	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public void setContadorMontajeOferta(Long contadorMontajeOferta) {
		this.contadorMontajeOferta = contadorMontajeOferta;
	}

	public Long getContadorMAC() {
		return contadorMAC;
	}

	public void setContadorMAC(Long contadorMAC) {
		this.contadorMAC = contadorMAC;
	}

	public String getDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(String defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

}