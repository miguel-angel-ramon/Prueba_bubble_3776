package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PedidoAdicionalVC implements Cloneable,Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long identificador;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long codArticulo;
	private String descriptionArt;
	private Long clasePedido;
	private Long perfil;
	private String tipoAprovisionamiento;
	private String agrupacion;
	private String esPlanograma;
	private String fechaInicio;
	private String fecha2;
	private String fecha3;
	private String fecha4;
	private String fecha5;
	private String fechaPilada;
	private String fechaFin;
	private String fechaMinima;
	private Double capMax;
	private Double capMin;
	private Double cantidad1;
	private Double cantidad2;
	private Double cantidad3;
	private Double cantidad4;
	private Double cantidad5;
	private Double cantMin;
	private Double cantMax;
	private String descOferta;
	private Double uniCajaServ;
	private String oferta;
	private Long tipoOferta;
	private boolean excluir;
	private boolean cajas;
	private int indice;
	private String usuario;
	private String modificable;
	private String modificableIndiv;
	private String tipoPedido;
	private String borrable;
	private String codError;
	private String descError;
	private String codigoRespuesta;
	private String descripcionRespuesta;
	private String fechaHasta;
	private List<Long> listaFiltroClasePedido;

	private List<CamposSeleccionadosVC> listadoSeleccionados;
	
	//Campo de control de mca
	private String mca;
	private String denominacionOferta;
	
	private String tratamiento;

	private String noGestionaPbl;
	
	//Estado para bloqueos
	private String estado;
	
	//TEXTIL
	
	private String color;
	private String talla;
	private String modeloProveedor;
	
	//Caprabo
	private Long codArticuloGrid;
	private String descriptionArtGrid;   
	private boolean esCaprabo;
		

	private Long identificadorSIA;
	
	public PedidoAdicionalVC() {
	    super();
	}
	
	public  PedidoAdicionalVC clone() throws CloneNotSupportedException{
		return (PedidoAdicionalVC)super.clone();
	}

	public PedidoAdicionalVC(Long codCentro, Long identificador, Long grupo1, Long grupo2, Long grupo3,
			Long codArticulo, String descriptionArt, Long clasePedido, Long perfil, String agrupacion,
			String esPlanograma, String fechaInicio, String fecha2, String fecha3, String fecha4, String fechaPilada,
			String fechaFin, String fechaMinima, Double capMax, Double capMin,
			Double cantidad1, Double cantidad2, Double cantidad3, Double cantMin, Double cantMax, 
			Double uniCajaServ, String oferta, int indice, String usuario, String modificable, String modificableIndiv, String tipoPedido,
			String borrable,String codError, String descError, String codigoRespuesta, String descripcionRespuesta, String fechaHasta,
			List<CamposSeleccionadosVC> listadoSeleccionados, String noGestionaPbl, Long identificadorSIA) {
		super();
		this.codCentro = codCentro;
		this.identificador = identificador;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.clasePedido = clasePedido;
		this.perfil = perfil;
		this.agrupacion = agrupacion;
		this.esPlanograma = esPlanograma;
		this.fechaInicio = fechaInicio;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fecha4 = fecha4;
		this.fechaPilada = fechaPilada;
		this.fechaFin = fechaFin;
		this.fechaMinima = fechaMinima;
		this.capMax = capMax;
		this.capMin = capMin;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.cantMin = cantMin;
		this.cantMax = cantMax;
		this.uniCajaServ = uniCajaServ;
		this.oferta = oferta;
		this.indice = indice;
		this.usuario = usuario;
		this.modificable = modificable;
		this.modificableIndiv = modificableIndiv;
		this.tipoPedido = tipoPedido;
		this.borrable = borrable;
		this.codError = codError;
		this.descError = descError;
		this.codigoRespuesta = codigoRespuesta;
		this.descripcionRespuesta = descripcionRespuesta;
		this.fechaHasta = fechaHasta;
		this.listadoSeleccionados = listadoSeleccionados;
		this.noGestionaPbl = noGestionaPbl;
		this.identificadorSIA = identificadorSIA;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}
	
	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
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

	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}
	
	public Long getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}
	
	public String getAgrupacion() {
		return this.agrupacion;
	}

	public void setAgrupacion(String agrupacion) {
		this.agrupacion = agrupacion;
	}
	
	public String getEsPlanograma() {
		return this.esPlanograma;
	}

	public void setEsPlanograma(String esPlanograma) {
		this.esPlanograma = esPlanograma;
	}

	public String getTipoAprovisionamiento() {
		return tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
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
	
	public String getFecha4() {
		return this.fecha4;
	}

	public void setFecha4(String fecha4) {
		this.fecha4 = fecha4;
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

	public Double getCapMax() {
		return this.capMax;
	}

	public void setCapMax(Double capMax) {
		this.capMax = capMax;
	}
	
	public Double getCapMin() {
		return this.capMin;
	}

	public void setCapMin(Double capMin) {
		this.capMin = capMin;
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

	public Double getCantMin() {
		return cantMin;
	}

	public void setCantMin(Double cantMin) {
		this.cantMin = cantMin;
	}

	public Double getCantMax() {
		return cantMax;
	}

	public void setCantMax(Double cantMax) {
		this.cantMax = cantMax;
	}

	public String getDescOferta() {
		return descOferta;
	}

	public void setDescOferta(String descOferta) {
		this.descOferta = descOferta;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}
	
	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}
	
	public Long getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(Long tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public boolean isExcluir() {
		return this.excluir;
	}

	public void setExcluir(boolean excluir) {
		this.excluir = excluir;
	}
	
	public boolean isCajas() {
		return this.cajas;
	}

	public void setCajas(boolean cajas) {
		this.cajas = cajas;
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
	
	public String getModificable() {
		return this.modificable;
	}

	public void setModificable(String modificable) {
		this.modificable = modificable;
	}
	
	public String getModificableIndiv() {
		return this.modificableIndiv;
	}

	public void setModificableIndiv(String modificableIndiv) {
		this.modificableIndiv = modificableIndiv;
	}
	
	public String getTipoPedido() {
		return this.tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	
	public String getBorrable() {
		return this.borrable;
	}

	public void setBorrable(String borrable) {
		this.borrable = borrable;
	}
	
	public String getCodError() {
		return this.codError;
	}

	public void setCodError(String codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public String getCodigoRespuesta() {
		return this.codigoRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}
	
	public String getDescripcionRespuesta() {
		return this.descripcionRespuesta;
	}

	public void setDescripcionRespuesta(String descripcionRespuesta) {
		this.descripcionRespuesta = descripcionRespuesta;
	}
	
	public List<Long> getListaFiltroClasePedido() {
		return listaFiltroClasePedido;
	}

	public void setListaFiltroClasePedido(List<Long> listaFiltroClasePedido) {
		this.listaFiltroClasePedido = listaFiltroClasePedido;
	}

	public List<CamposSeleccionadosVC> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosVC> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}

	public String getMca() {
		return this.mca;
	}

	public void setMca(String mca) {
		this.mca = mca;
	}

	public String getDenominacionOferta() {
		return denominacionOferta;
	}

	public void setDenominacionOferta(String denominacionOferta) {
		this.denominacionOferta = denominacionOferta;
	}

	public String getFecha5() {
		return fecha5;
	}

	public void setFecha5(String fecha5) {
		this.fecha5 = fecha5;
	}

	public Double getCantidad4() {
		return cantidad4;
	}

	public void setCantidad4(Double cantidad4) {
		this.cantidad4 = cantidad4;
	}

	public Double getCantidad5() {
		return cantidad5;
	}

	public void setCantidad5(Double cantidad5) {
		this.cantidad5 = cantidad5;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	
	public String getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNoGestionaPbl() {
		return this.noGestionaPbl;
	}

	public void setNoGestionaPbl(String noGestionaPbl) {
		this.noGestionaPbl = noGestionaPbl;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}
	
	public Long getIdentificadorSIA() {
		return this.identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}
	
	public Long getCodArticuloGrid() {
		return codArticuloGrid;
	}

	public void setCodArticuloGrid(Long codArticuloGrid) {
		this.codArticuloGrid = codArticuloGrid;
	}

	public String getDescriptionArtGrid() {
		return descriptionArtGrid;
	}

	public void setDescriptionArtGrid(String descriptionArtGrid) {
		this.descriptionArtGrid = descriptionArtGrid;
	}
	
	public boolean getEsCaprabo() {
		return esCaprabo;
	}

	public void setEsCaprabo(boolean esCaprabo) {
		this.esCaprabo = esCaprabo;
	}
}