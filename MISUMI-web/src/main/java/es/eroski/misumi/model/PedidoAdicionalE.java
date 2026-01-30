package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PedidoAdicionalE implements Serializable{

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
	private String fecEntrega;
	private String fechaMinima;
	private Double unidadesPedidas;
	private Double uniCajaServ;
	private boolean cajas;
	private boolean excluir;
	private int indice;
	private String usuario;
	private String modificable;
	private String codError;
	private String descError;
	private String codigoRespuesta;
	private String descripcionRespuesta;
	private List<CamposSeleccionadosE> listadoSeleccionados;
	private Double stock;
	private Boolean consultaAlmacenada;
	private List<Long> listaFiltroClasePedido;
	private String tratamiento;
	
	//Campo de control de mca
	private String mca;

	//Campo de control de fecha modificación
	private String fechaHasta;
	
	private String estado;
	private String mensaje;

	//Control de fechas con Bloqueos
	private String fechaBloqueoEncargo;
	
	//Control de leyendas de Bloqueos
	private String mostrarLeyendaBloqueo;
	
	//Textil
	private String color;
	private String talla;
	private String modeloProveedor;

	private Long identificadorSIA;
	
	//Caprabo
	private Long codArticuloGrid;
	private String descriptionArtGrid;   
	private boolean esCaprabo;  
	
	private boolean showExcluirAndCajas;
	

	

	public PedidoAdicionalE() {
	    super();
	}
	
	public PedidoAdicionalE(String color, String talla, String modeloProveedor) {
		super();
		this.talla = talla;
		this.color = color;
		this.modeloProveedor = modeloProveedor;
		
	}

	public PedidoAdicionalE(Long codCentro, Long identificador, Long grupo1, Long grupo2, Long grupo3,
			Long codArticulo, String descriptionArt, Long clasePedido, Long perfil, String agrupacion, 
			String fecEntrega, String fechaMinima, Double unidadesPedidas,Double uniCajaServ,
			boolean cajas, boolean excluir, int indice, String usuario, String modificable,
			String codError, String descError, String codigoRespuesta, String descripcionRespuesta, 
			List<CamposSeleccionadosE> listadoSeleccionados) {
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
		this.fecEntrega = fecEntrega;
		this.fechaMinima = fechaMinima;
		this.unidadesPedidas = unidadesPedidas;
		this.uniCajaServ = uniCajaServ;
		this.cajas = cajas;
		this.excluir = excluir;
		this.indice = indice;
		this.usuario = usuario;
		this.modificable = modificable;
		this.codError = codError;
		this.descError = descError;
		this.codigoRespuesta = codigoRespuesta;
		this.descripcionRespuesta = descripcionRespuesta;
		this.listadoSeleccionados = listadoSeleccionados;
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

	public String getTipoAprovisionamiento() {
		return tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}

	public String getFecEntrega() {
		return this.fecEntrega;
	}

	public void setFecEntrega(String fecEntrega) {
		this.fecEntrega = fecEntrega;
	}
	
	public String getFechaMinima() {
		return this.fechaMinima;
	}

	public void setFechaMinima(String fechaMinima) {
		this.fechaMinima = fechaMinima;
	}

	public Double getUnidadesPedidas() {
		return this.unidadesPedidas;
	}

	public void setUnidadesPedidas(Double unidadesPedidas) {
		this.unidadesPedidas = unidadesPedidas;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public boolean isCajas() {
		return this.cajas;
	}

	public void setCajas(boolean cajas) {
		this.cajas = cajas;
	}

	public boolean isExcluir() {
		return this.excluir;
	}

	public void setExcluir(boolean excluir) {
		this.excluir = excluir;
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
	
	public List<CamposSeleccionadosE> getListadoSeleccionados() {
		return this.listadoSeleccionados;
	}

	public void setListadoSeleccionados(List<CamposSeleccionadosE> listadoSeleccionados) {
		this.listadoSeleccionados = listadoSeleccionados;
	}
	
	public Double getCajasPedidas(){
		return this.unidadesPedidas/this.uniCajaServ;
	}

	public String getMca() {
		return this.mca;
	}

	public void setMca(String mca) {
		this.mca = mca;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Boolean getConsultaAlmacenada() {
		return consultaAlmacenada;
	}

	public void setConsultaAlmacenada(Boolean consultaAlmacenada) {
		this.consultaAlmacenada = consultaAlmacenada;
	}

	public List<Long> getListaFiltroClasePedido() {
		return listaFiltroClasePedido;
	}

	public void setListaFiltroClasePedido(List<Long> listaFiltroClasePedido) {
		this.listaFiltroClasePedido = listaFiltroClasePedido;
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
	
	public String getFechaBloqueoEncargo() {
		return this.fechaBloqueoEncargo;
	}

	public void setFechaBloqueoEncargo(String fechaBloqueoEncargo) {
		this.fechaBloqueoEncargo = fechaBloqueoEncargo;
	}
	
	public String getMostrarLeyendaBloqueo() {
		return this.mostrarLeyendaBloqueo;
	}

	public void setMostrarLeyendaBloqueo(String mostrarLeyendaBloqueo) {
		this.mostrarLeyendaBloqueo = mostrarLeyendaBloqueo;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public boolean isShowExcluirAndCajas() {
		return showExcluirAndCajas;
	}

	public void setShowExcluirAndCajas(boolean showExcluirAndCajas) {
		this.showExcluirAndCajas = showExcluirAndCajas;
	}
	
	
}