package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TPedidoAdicional implements Serializable{

	private static final long serialVersionUID = 1L;

	private String idSesion;          
	private Long clasePedido;
	private Long codCentro;
	private Long codArticulo;
	private String pantalla;
	private Long identificador;             
	private String descriptionArt;          
	private Double uniCajaServ;             
	private String usuario;                 
	private Long perfil;                    
	private String agrupacion; 
	private String oferta;
	private String tipoAprovisionamiento;   
	private String borrable;                
	private String modificable;             
	private String modificableIndiv;        
	private Double cajasPedidas;            
	private String fecEntrega;   
	private String fechaMinima;
	private String fechaInicio;             
	private String fechaFin;                
	private String fecha2;                  
	private String fecha3;                  
	private String fecha4;   
	private String fecha5;
	private Double capMax;                  
	private Double capMin;                  
	private Double cantidad1;               
	private Double cantidad2;               
	private Double cantidad3;
	private Double cantidad4;
	private Double cantidad5;
	private String fechaPilada;
	private String tipoPedido;              
	private String cajas;                  
	private String excluir;                
	private Date fechaCreacion; 
	private String codError;        
	private String descError;
	private String esPlanograma;
	private String MAC;
	private String orderBy;
	private String sortOrder;
	private List<Long> listaFiltroClasePedido;
	private Double cantMax;
	private Double cantMin;
	private String descOferta;
	private String tratamiento;
	private String fechaHasta;
	private String flgValidado;
	private String estado;
	private String noGestionaPbl;
	private String descPeriodo;
	private String espacioPromo;
	
	private Long codArticuloGrid;
	private String descriptionArtGrid;      
	
	
	//TEXTIL
	private String color;
	private String talla;
	private String modeloProveedor;
	private String seccion;
	
	private String uuid;
	
	private Long identificadorSIA;
	private Long identificadorVegalsa;

	public TPedidoAdicional() {
	    super();
	}

	public TPedidoAdicional(String idSesion, Long clasePedido, 
			Long codCentro, Long codArticulo, String pantalla,
			Long identificador, String descriptionArt, Double uniCajaServ,
			String usuario, Long perfil, 
			String agrupacion, String oferta, String tipoAprovisionamiento,
			String borrable, String modificable, String modificableIndiv,
			Double cajasPedidas, String fecEntrega, String fechaInicio,
			String fechaFin, String fecha2, String fecha3, String fecha4,
			Double capMax, Double capMin, Double cantidad1, Double cantidad2,
			Double cantidad3, String tipoPedido, String cajas, String excluir, Date fechaCreacion,
			String codError, String descError, String esPlanograma,
			String descOferta, Double cantMax, Double cantMin, String fecha5, String fechaPilada,
			Double cantidad4, Double cantidad5, String tratamiento, String fechaHasta, String estado,
			String noGestionaPbl, String descPeriodo,String espacioPromo, Long codArticuloGrid, String descriptionArtGrid, Long identificadorSIA, Long identificadorVegalsa) {
		super();
		this.idSesion = idSesion;
		this.clasePedido = clasePedido;
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.pantalla = pantalla;
		this.identificador = identificador;
		this.descriptionArt = descriptionArt;
		this.uniCajaServ = uniCajaServ;
		this.usuario = usuario;
		this.perfil = perfil;
		this.agrupacion = agrupacion;
		this.oferta = oferta;
		this.tipoAprovisionamiento = tipoAprovisionamiento;
		this.borrable = borrable;
		this.modificable = modificable;
		this.modificableIndiv = modificableIndiv;
		this.cajasPedidas = cajasPedidas;
		this.fecEntrega = fecEntrega;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fecha2 = fecha2;
		this.fecha3 = fecha3;
		this.fecha4 = fecha4;
		this.capMax = capMax;
		this.capMin = capMin;
		this.cantidad1 = cantidad1;
		this.cantidad2 = cantidad2;
		this.cantidad3 = cantidad3;
		this.tipoPedido = tipoPedido;
		this.cajas = cajas;
		this.excluir = excluir;
		this.fechaCreacion = fechaCreacion;
		this.codError = codError;
		this.descError = descError;
		this.esPlanograma = esPlanograma;
		this.descOferta = descOferta;
		this.cantMax = cantMax;
		this.cantMin = cantMin;
		this.fecha5 = fecha5;
		this.fechaPilada = fechaPilada;
		this.cantidad4 = cantidad4;
		this.cantidad5 = cantidad5;
		this.tratamiento = tratamiento;
		this.fechaHasta = fechaHasta;
		this.estado = estado;
		this.noGestionaPbl = noGestionaPbl;
		this.descPeriodo = descPeriodo;
		this.espacioPromo = espacioPromo;
		this.codArticuloGrid = codArticuloGrid;
		this.descriptionArtGrid = descriptionArtGrid;
		this.identificadorSIA = identificadorSIA;
		this.identificadorVegalsa = identificadorVegalsa;
	}

	public String getIdSesion() {
		return this.idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
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

	public String getPantalla() {
		return this.pantalla;
	}

	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getDescriptionArt() {
		return this.descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public String getTipoAprovisionamiento() {
		return this.tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}

	public String getBorrable() {
		return this.borrable;
	}

	public void setBorrable(String borrable) {
		this.borrable = borrable;
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

	public Double getCajasPedidas() {
		return this.cajasPedidas;
	}

	public void setCajasPedidas(Double cajasPedidas) {
		this.cajasPedidas = cajasPedidas;
	}

	public String getFecEntrega() {
		return this.fecEntrega;
	}

	public void setFecEntrega(String fecEntrega) {
		this.fecEntrega = fecEntrega;
	}

	public String getFechaMinima() {
		return fechaMinima;
	}

	public void setFechaMinima(String fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	public String getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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

	public String getTipoPedido() {
		return this.tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getCajas() {
		return this.cajas;
	}

	public void setCajas(String cajas) {
		this.cajas = cajas;
	}

	public String getExcluir() {
		return this.excluir;
	}

	public void setExcluir(String excluir) {
		this.excluir = excluir;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	
	public String getEsPlanograma() {
		return this.esPlanograma;
	}

	public void setEsPlanograma(String esPlanograma) {
		this.esPlanograma = esPlanograma;
	}
	
	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Double getCantMax() {
		return cantMax;
	}

	public void setCantMax(Double cantMax) {
		this.cantMax = cantMax;
	}

	public Double getCantMin() {
		return cantMin;
	}

	public void setCantMin(Double cantMin) {
		this.cantMin = cantMin;
	}

	public String getDescOferta() {
		return descOferta;
	}

	public void setDescOferta(String descOferta) {
		this.descOferta = descOferta;
	}

	public List<Long> getListaFiltroClasePedido() {
		return listaFiltroClasePedido;
	}

	public void setListaFiltroClasePedido(List<Long> listaFiltroClasePedido) {
		this.listaFiltroClasePedido = listaFiltroClasePedido;
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

	public String getFechaPilada() {
		return fechaPilada;
	}

	public void setFechaPilada(String fechaPilada) {
		this.fechaPilada = fechaPilada;
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
	
	public String getFlgValidado() {
		return this.flgValidado;
	}

	public void setFlgValidado(String flgValidado) {
		this.flgValidado = flgValidado;
	}

	public String getEstado() {
		return this.estado;
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

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Long getIdentificadorVegalsa() {
		return identificadorVegalsa;
	}

	public void setIdentificadorVegalsa(Long identificadorVegalsa) {
		this.identificadorVegalsa = identificadorVegalsa;
	}

}