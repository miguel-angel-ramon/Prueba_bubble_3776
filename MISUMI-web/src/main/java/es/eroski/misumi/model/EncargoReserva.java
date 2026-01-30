package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class EncargoReserva implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long identificador;
	private String tipo;
	private String origenEncargo;
	private String oferta;
	private Long codigoCentro;
	private Long articulo;
	private String descripcion;
	private Double unidadesCaja;
	private String estructuraComercial;
	//private Double cantidadUnidades; Borrado en Pet.55674

	
	private Double implantacionInicial;
	private Double implantacionFinal;
	private Date fechaInicio;
	private Date fechaFin;
	private String tratamiento;
	private Long tipoPedidoAdicional;
	private String flgForzarUnitaria;
	private String flgExcluirVentas;
	//private String flgModificable; Borrado en Pet.55674
	private String flgDetallado;
	private String usuario;
	private String tipoAprovisionamiento;
	private Long codError;

	private String descError;
	
	//Campos nuevos Pet. 55674
	private Double cantidad1; 
	private Double cantidad2; 
	private Double cantidad3; 
	private Date fecha2;
	private Date fecha3;
	private Date fechaInicioPilada;
	private String flgModificableEnc1; 
	private String flgModificableEnc2; 
	private String flgModificableEnc3; 
	private String flgModificablePilada; 
	
	//Parámetros adicionales para llamadas a procedimientos
	private Long codLoc;
	private String codN1;
	private String codN2;
	private String codN3;
	private Long codArtFormlog;
	private Date fechaVenta;
	private Boolean bloqueoEncargo;
	private Boolean bloqueoPilada;
	private Boolean bloqueoDetallado;

	//Para el contador
	private Long numeroOcurrencias;
	
	private String descPeriodo;
	private String espacioPromo;

	public EncargoReserva() {
		super();
	}

	public EncargoReserva(Long identificador, String tipo, String origenEncargo, String oferta, Long codigoCentro,
			Long articulo, String descripcion, Double unidadesCaja, String estructuraComercial, Double cantidad1, Double cantidad2, Double cantidad3,
			Double implantacionInicial, Double implantacionFinal, Date fechaInicio, Date fechaFin, String tratamiento,
			Long tipoPedidoAdicional, String flgForzarUnitaria, String flgExcluirVentas, 
			String flgDetallado, String usuario, String tipoAprovisionamiento, Long codError, String descError, Date fecha2, Date fecha3, 
			Date fechaInicioPilada, String flgModificableEnc1, String flgModificableEnc2, String flgModificableEnc3, String flgModificablePilada,
			String descPeriodo, String espacioPromo) {
		super();
		this.identificador = identificador;
		this.tipo = tipo;
		this.origenEncargo = origenEncargo;
		this.oferta = oferta;
		this.codigoCentro = codigoCentro;
		this.articulo = articulo;
		this.descripcion = descripcion;
		this.unidadesCaja = unidadesCaja;
		this.estructuraComercial = estructuraComercial;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.implantacionInicial = implantacionInicial;
		this.implantacionFinal = implantacionFinal;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tratamiento = tratamiento;
		this.tipoPedidoAdicional = tipoPedidoAdicional;
		this.flgForzarUnitaria = flgForzarUnitaria;
		this.flgExcluirVentas = flgExcluirVentas;
		this.flgDetallado = flgDetallado;
		this.codError = codError;
		this.descError = descError;
		this.fecha2=fecha2;
		this.fecha3=fecha3;
		this.fechaInicioPilada=fechaInicioPilada;
		this.flgModificableEnc1=flgModificableEnc1;
		this.flgModificableEnc2=flgModificableEnc2;
		this.flgModificableEnc3=flgModificableEnc3;
		this.flgModificablePilada=flgModificablePilada;
		this.descPeriodo=descPeriodo;
		this.espacioPromo=espacioPromo;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrigenEncargo() {
		return this.origenEncargo;
	}

	public void setOrigenEncargo(String origenEncargo) {
		this.origenEncargo = origenEncargo;
	}

	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public Long getCodigoCentro() {
		return this.codigoCentro;
	}

	public void setCodigoCentro(Long codigoCentro) {
		this.codigoCentro = codigoCentro;
	}

	public Long getArticulo() {
		return this.articulo;
	}

	public void setArticulo(Long articulo) {
		this.articulo = articulo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getUnidadesCaja() {
		return this.unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}

	public String getEstructuraComercial() {
		return this.estructuraComercial;
	}

	public void setEstructuraComercial(String estructuraComercial) {
		this.estructuraComercial = estructuraComercial;
	}

	public Double getImplantacionInicial() {
		return this.implantacionInicial;
	}

	public void setImplantacionInicial(Double implantacionInicial) {
		this.implantacionInicial = implantacionInicial;
	}

	public Double getImplantacionFinal() {
		return this.implantacionFinal;
	}

	public void setImplantacionFinal(Double implantacionFinal) {
		this.implantacionFinal = implantacionFinal;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getTratamiento() {
		return this.tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Long getTipoPedidoAdicional() {
		return this.tipoPedidoAdicional;
	}

	public void setTipoPedidoAdicional(Long tipoPedidoAdicional) {
		this.tipoPedidoAdicional = tipoPedidoAdicional;
	}

	public String getFlgForzarUnitaria() {
		return this.flgForzarUnitaria;
	}

	public void setFlgForzarUnitaria(String flgForzarUnitaria) {
		this.flgForzarUnitaria = flgForzarUnitaria;
	}

	public String getFlgExcluirVentas() {
		return this.flgExcluirVentas;
	}

	public void setFlgExcluirVentas(String flgExcluirVentas) {
		this.flgExcluirVentas = flgExcluirVentas;
	}

	public String getFlgDetallado() {
		return this.flgDetallado;
	}

	public void setFlgDetallado(String flgDetallado) {
		this.flgDetallado = flgDetallado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getTipoAprovisionamiento() {
		return this.tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
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

	public Long getNumeroOcurrencias() {
		return this.numeroOcurrencias;
	}

	public void setNumeroOcurrencias(Long numeroOcurrencias) {
		this.numeroOcurrencias = numeroOcurrencias;
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

	public Date getFechaVenta() {
		return this.fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Boolean getBloqueoEncargo() {
		return this.bloqueoEncargo;
	}

	public void setBloqueoEncargo(Boolean bloqueoEncargo) {
		this.bloqueoEncargo = bloqueoEncargo;
	}

	public Boolean getBloqueoPilada() {
		return this.bloqueoPilada;
	}

	public void setBloqueoPilada(Boolean bloqueoPilada) {
		this.bloqueoPilada = bloqueoPilada;
	}

	public Boolean getBloqueoDetallado() {
		return this.bloqueoDetallado;
	}

	public void setBloqueoDetallado(Boolean bloqueoDetallado) {
		this.bloqueoDetallado = bloqueoDetallado;
	}
	
	public Double getCantidad1() {
		return cantidad1;
	}

	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Double getCantidad2() {
		return cantidad2;
	}

	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public Double getCantidad3() {
		return cantidad3;
	}

	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}

	public Date getFecha2() {
		return fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public Date getFecha3() {
		return fecha3;
	}

	public void setFecha3(Date fecha3) {
		this.fecha3 = fecha3;
	}

	public Date getFechaInicioPilada() {
		return fechaInicioPilada;
	}

	public void setFechaInicioPilada(Date fechaInicioPilada) {
		this.fechaInicioPilada = fechaInicioPilada;
	}

	public String getFlgModificableEnc1() {
		return flgModificableEnc1;
	}

	public void setFlgModificableEnc1(String flgModificableEnc1) {
		this.flgModificableEnc1 = flgModificableEnc1;
	}

	public String getFlgModificableEnc2() {
		return flgModificableEnc2;
	}

	public void setFlgModificableEnc2(String flgModificableEnc2) {
		this.flgModificableEnc2 = flgModificableEnc2;
	}

	public String getFlgModificableEnc3() {
		return flgModificableEnc3;
	}

	public void setFlgModificableEnc3(String flgModificableEnc3) {
		this.flgModificableEnc3 = flgModificableEnc3;
	}

	public String getFlgModificablePilada() {
		return flgModificablePilada;
	}

	public void setFlgModificablePilada(String flgModificablePilada) {
		this.flgModificablePilada = flgModificablePilada;
	}

	public String getDescPeriodo() {
		return descPeriodo;
	}

	public void setDescPeriodo(String descPeriodo) {
		this.descPeriodo = descPeriodo;
	}

	public String getEspacioPromo() {
		return espacioPromo;
	}

	public void setEspacioPromo(String espacioPromo) {
		this.espacioPromo = espacioPromo;
	}

}