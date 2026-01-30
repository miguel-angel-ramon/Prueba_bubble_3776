package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class NuevoPedidoOferta implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	private String tipoAprov;
	private Long codCentro;
	private Long codArticulo;
	private String descriptionArt;
	private String fechaInicio;
	private String fecha2;
	private String fecha3;
	private String fechaPilada;
	private String fechaFin;
	private String fechaMinima;
	private String fechaMaxima;
	private Double implInicial;
	private Double implFinal;
	private Double cantidad1;
	private Double cantidad2;
	private Double cantidad3;
	private Double uniCajaServ;
	
	private String flgTipoListado;
	private String bloqueado;
	private String bloqueoEncargo;
	private String bloqueoPilada;
	private String tipoPedido;

	private Long codError;
	private String descError;
	private String clave;
	private int indice;
	private String usuario;

	private Long anoOferta;
	private Long numOferta;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;

	private Double implInicialOrig;
	private Double implFinalOrig;
	private Double cantidad1Orig;
	private Double cantidad2Orig;
	private Double cantidad3Orig;
	
	private Long perfil;
	private String error;
	
	private List<CamposModificadosNO> listadoModificados;

	private Long clasePedido;
	
	//Campos para filtro de góndola
	private Long nelCodN1;
	private Long nelCodN2;
	private Long nelCodN3;
	private String cabGondola;
	
	//Control de fechas con Bloqueos
	private String fechaBloqueoEncargo;
	private String fechaBloqueoEncargoPilada;

	public NuevoPedidoOferta() {
		super();
	}

	

	public NuevoPedidoOferta(String tipoAprov, Long codCentro,
			Long codArticulo, String descriptionArt, String fechaInicio,
			String fecha2, String fecha3, String fechaPilada, String fechaFin,
			String fechaMinima, String fechaMaxima, Double implInicial,
			Double implFinal, Double cantidad1, Double cantidad2,
			Double cantidad3, Double uniCajaServ, String flgTipoListado,
			String bloqueado, String tipoPedido, Long codError,
			String descError, String clave, int indice, String usuario,
			Long anoOferta, Long numOferta, Long grupo1, Long grupo2,
			Long grupo3, Double implInicialOrig, Double implFinalOrig,
			Double cantidad1Orig, Double cantidad2Orig, Double cantidad3Orig, 
			List<CamposModificadosNO> listadoModificados, Long clasePedido) {
		super();
		this.tipoAprov = tipoAprov;
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.fechaInicio = fechaInicio;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fechaPilada = fechaPilada;
		this.fechaFin = fechaFin;
		this.fechaMinima = fechaMinima;
		this.fechaMaxima = fechaMaxima;
		this.implInicial = implInicial;
		this.implFinal = implFinal;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.uniCajaServ = uniCajaServ;
		this.flgTipoListado = flgTipoListado;
		this.bloqueado = bloqueado;
		this.tipoPedido = tipoPedido;
		this.codError = codError;
		this.descError = descError;
		this.clave = clave;
		this.indice = indice;
		this.usuario = usuario;
		this.anoOferta = anoOferta;
		this.numOferta = numOferta;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.implInicialOrig = implInicialOrig;
		this.implFinalOrig = implFinalOrig;
		this.cantidad1Orig = cantidad1Orig;
		this.cantidad2Orig = cantidad2Orig;
		this.cantidad3Orig = cantidad3Orig;
		this.listadoModificados = listadoModificados;
		this.clasePedido = clasePedido;
	}

	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescriptionArt() {
		return this.descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getFecha3() {
		return this.fecha3;
	}

	public void setFecha3(String fecha3) {
		this.fecha3 = fecha3;
	}

	public String getFechaPilada() {
		return this.fechaPilada;
	}

	public void setFechaPilada(String fechaPilada) {
		this.fechaPilada = fechaPilada;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaMinima() {
		return this.fechaMinima;
	}

	public void setFechaMinima(String fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	public String getFechaMaxima() {
		return this.fechaMaxima;
	}

	public void setFechaMaxima(String fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	public Double getImplInicial() {
		return this.implInicial;
	}

	public void setImplInicial(Double implInicial) {
		this.implInicial = implInicial;
	}

	public Double getImplFinal() {
		return this.implFinal;
	}

	public void setImplFinal(Double implFinal) {
		this.implFinal = implFinal;
	}

	public Double getCantidad1() {
		return this.cantidad1;
	}

	public void setCantidad1(Double cantidad1) {
		this.cantidad1 = cantidad1;
	}

	public Double getCantidad2() {
		return this.cantidad2;
	}

	public void setCantidad2(Double cantidad2) {
		this.cantidad2 = cantidad2;
	}

	public Double getCantidad3() {
		return this.cantidad3;
	}

	public void setCantidad3(Double cantidad3) {
		this.cantidad3 = cantidad3;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public String getFlgTipoListado() {
		return this.flgTipoListado;
	}

	public void setFlgTipoListado(String flgTipoListado) {
		this.flgTipoListado = flgTipoListado;
	}

	public String getBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getBloqueoEncargo() {
		return this.bloqueoEncargo;
	}

	public void setBloqueoEncargo(String bloqueoEncargo) {
		this.bloqueoEncargo = bloqueoEncargo;
	}

	public String getBloqueoPilada() {
		return this.bloqueoPilada;
	}

	public void setBloqueoPilada(String bloqueoPilada) {
		this.bloqueoPilada = bloqueoPilada;
	}

	public String getTipoPedido() {
		return this.tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
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

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getIndice() {
		return this.indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getAnoOferta() {
		return this.anoOferta;
	}

	public void setAnoOferta(Long anoOferta) {
		this.anoOferta = anoOferta;
	}

	public Long getNumOferta() {
		return this.numOferta;
	}

	public void setNumOferta(Long numOferta) {
		this.numOferta = numOferta;
	}

	public Long getGrupo1() {
		return this.grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return this.grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return this.grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	
	
	public List<CamposModificadosNO> getListadoModificados() {
		return this.listadoModificados;
	}

	public void setListadoModificados(List<CamposModificadosNO> listadoModificados) {
		this.listadoModificados = listadoModificados;
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.codArticulo).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof NuevoPedidoOferta)){
			return false;
		}
		NuevoPedidoOferta npo = (NuevoPedidoOferta) obj;
		return new EqualsBuilder().append(this.codArticulo, npo.codArticulo).isEquals();
	}



	public Double getImplInicialOrig() {
		return this.implInicialOrig;
	}



	public void setImplInicialOrig(Double implInicialOrig) {
		this.implInicialOrig = implInicialOrig;
	}



	public Double getImplFinalOrig() {
		return this.implFinalOrig;
	}



	public void setImplFinalOrig(Double implFinalOrig) {
		this.implFinalOrig = implFinalOrig;
	}



	public Double getCantidad1Orig() {
		return this.cantidad1Orig;
	}



	public void setCantidad1Orig(Double cantidad1Orig) {
		this.cantidad1Orig = cantidad1Orig;
	}



	public Double getCantidad2Orig() {
		return this.cantidad2Orig;
	}



	public void setCantidad2Orig(Double cantidad2Orig) {
		this.cantidad2Orig = cantidad2Orig;
	}



	public Double getCantidad3Orig() {
		return this.cantidad3Orig;
	}



	public void setCantidad3Orig(Double cantidad3Orig) {
		this.cantidad3Orig = cantidad3Orig;
	}

	public Long getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}

	public Long getNelCodN1() {
		return this.nelCodN1;
	}

	public void setNelCodN1(Long nelCodN1) {
		this.nelCodN1 = nelCodN1;
	}

	public Long getNelCodN2() {
		return this.nelCodN2;
	}

	public void setNelCodN2(Long nelCodN2) {
		this.nelCodN2 = nelCodN2;
	}

	public Long getNelCodN3() {
		return this.nelCodN3;
	}

	public void setNelCodN3(Long nelCodN3) {
		this.nelCodN3 = nelCodN3;
	}

	public String getCabGondola() {
		return this.cabGondola;
	}

	public void setCabGondola(String cabGondola) {
		this.cabGondola = cabGondola;
	}

	public String getFechaBloqueoEncargo() {
		return this.fechaBloqueoEncargo;
	}

	public void setFechaBloqueoEncargo(String fechaBloqueoEncargo) {
		this.fechaBloqueoEncargo = fechaBloqueoEncargo;
	}

	public String getFechaBloqueoEncargoPilada() {
		return this.fechaBloqueoEncargoPilada;
	}

	public void setFechaBloqueoEncargoPilada(String fechaBloqueoEncargoPilada) {
		this.fechaBloqueoEncargoPilada = fechaBloqueoEncargoPilada;
	}
}