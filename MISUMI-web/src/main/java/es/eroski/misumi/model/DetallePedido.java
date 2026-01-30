package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DetallePedido implements Serializable {

	private static final long serialVersionUID = 1L;


	// referencia
	private Long codCentro;
	private Long codArticulo;
	private Long codArticuloEroski;

	private String descriptionArt;
	private String descriptionArtEroski;
	// private String nivel;
	// Area
	private Long grupo1;
	// Seccion
	private Long grupo2;
	// Categoria
	private Long grupo3;
	// SubCategoria
	private Long grupo4;
	// Segmento
	private Long grupo5;
	
	private String fechaPedido;
	private Double stock;
	private Double enCurso1;
	private Double enCurso2;
	private Double unidadesCaja;
	private Long cajasPedidas;
	private Long propuesta;
	private Long cantidad;
	private Long cantidadOriginal;
	private Long cantidadUltValida;
	
	private String tipoDetallado;
	private String estadoGrid;
	private String estadoPedido;
	private String horaLimite;
	private String resultadoWS;
	
	private Integer totalGuardar;
	private Integer totalError;
	
	private Long cantidadAnt;
	
	// TEXTIL
	private String temporada;
	private Long numOrden;
	private String modeloProveedor;
	private String descrTalla;
	private String descrColor;
	private Double facing;
	
	private List <DetallePedidoModificados> listaModificados= new ArrayList<DetallePedidoModificados>();
	
	//TIPO DE APROVISIONAMIENTO
	private String tipoAprovisionamiento;
	
	private Double ufp;
	private String FFPP;
	private Double dexenx; //Contendra la division entre UFP y U/C en el caso de que tipo_UFP se U. (Se decide que el campo contenga tambien la parte entera lo mismmo que DEXENXENTERO)
	private Double dexenxEntero; //Contendra la division entre UFP y U/C en el caso de que tipo_UFP se U. SOLO LA PARTE ENTERA

	private String tipo;
	
	private Long nivelLote;
	private String id;  //id para enlazar con las referencias de un lote.
	
	private String tipoUFP;
	
	private String nextDayPedido;
	
	private String flgSIA;

	//HORIZONTE Detallado
	private Date fechaEntrega;
	private Long unidFlEmpuje;  
	private Long codNecesidad;  
	private Long codErrorRedondeo;
	private String descErrorRedondeo;
	
	//Sirve para colorear las filas de azul en el grid
	private String flgOferta;
	
	//Sirve para mostrar el estado del checkbox.•	Si el check está habilitado muestra TODO
	//Si el check está deshabilitado NO tiene que mostrar las propuestas de pedido = 0 SIN cantidad.
	private String flgIncluirPropPed;
	private String flgPropuesta; //Lo manda el procedimiento

	private String oferta;
	private Long tipoOferta;
	
	
	private String converArt;
	
	//GESTION DE EUROS
	private String denomArea;
	private String denomSeccion;
	private String denomCategoria;
	private String denomSubcategoria;
	private String denomSegmento;
	private Double precioCostoArticulo;  
	private Double precioCostoInicial;  
	private Double precioCostoFinal;  
	private Double precioCostoFinalFinal;	
	
	private String rotacion;
	private String respetarIMC;
	private Long diferencia;
	private String refCumple;
	private String aviso;

	//Se ha creado este error para que avise si el ws de modificacion ha fallado.
	private Integer errorModificarWS;
	
	//Se ha creado este error para que avise si el procedimiento ha fallado.
	private Integer errorModificarProc;
	
	//Se ha creado este error para que avise si el procedimiento ha fallado.
	private Integer codError;

	/**
	 * 
	 */
	private String flgNoRealizarUpdate;

	/**
	 * MISUMI-300
	 */
	private Integer codMapa;
	private String motivoPedido;
	private boolean filtroMapa;
	/**
	 * MISUMI-444
	 */
	private Long codGrupoBalanza;
	private String descGrupoBalanza;

	private Long cajasCortadas;
	private String incPrevisionVenta;
	private Long smEstatico;

	public DetallePedido() {
		super();
	}

	public DetallePedido( Long codArticulo
			) {
		super();
		this.codArticulo = codArticulo;
	}
	  
	public DetallePedido( Long codCentro,  Long grupo1, Long grupo2, Long grupo3
						, Long grupo4, Long grupo5, Long codArticulo
						, String descriptionArt, Double stock
						, Double enCurso1, Double enCurso2, Double unidadesCaja
						, Long cajasPedidas, Long propuesta, Long cantidad
						, String tipoDetallado, String estadoGrid, String estadoPedido) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.stock = stock;
		this.enCurso1 = enCurso1;
		this.enCurso2 = enCurso2;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.propuesta = propuesta;
		this.cantidad = cantidad;
		this.tipoDetallado = tipoDetallado;
		this.estadoGrid = estadoGrid;
		this.estadoPedido = estadoPedido;
		
	}
	public DetallePedido(Long codCentro,  Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, Long codArticulo,
			String descriptionArt, Double stock,
			Double enCurso1, Double enCurso2, Double unidadesCaja,
			Long cajasPedidas, Long propuesta, Long cantidad,  Long cantidadOriginal, Long cantidadUltValida,
			String tipoDetallado, String estadoGrid, String estadoPedido, String horalimite, String resultadoWS, Long cantidadAnt,
			String temporada, Long numOrden,String modeloProveedor, String descrTalla,String descrColor,Double facing,String aprovisionamiento, String tipo,
			 Long nivelLote, String id, String tipoUFP, Double ufp) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.stock = stock;
		this.enCurso1 = enCurso1;
		this.enCurso2 = enCurso2;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.propuesta = propuesta;
		this.cantidad = cantidad;
		this.cantidadOriginal = cantidadOriginal;
		this.cantidadUltValida = cantidadUltValida;
		this.tipoDetallado = tipoDetallado;
		this.estadoGrid = estadoGrid;
		this.estadoPedido = estadoPedido;
		this.horaLimite=horalimite;
		this.resultadoWS=resultadoWS;
		this.cantidadAnt = cantidadAnt;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
		this.facing = facing;
		this.tipoAprovisionamiento = aprovisionamiento;
		this.tipo = tipo;
		this.nivelLote = nivelLote;
		this.id = id;
		this.tipoUFP = tipoUFP;
		this.ufp = ufp;
		
		
	}

	public DetallePedido(Long codCentro,  Long grupo1, Long grupo2, Long grupo3,
			Long grupo4, Long grupo5, Long codArticulo,
			String descriptionArt, Double stock,
			Double enCurso1, Double enCurso2, Double unidadesCaja,
			Long cajasPedidas, Long propuesta, Long cantidad,  Long cantidadOriginal, Long cantidadUltValida,
			String tipoDetallado, String estadoGrid, String estadoPedido, String horalimite, String resultadoWS, Long cantidadAnt,
			String temporada, Long numOrden,String modeloProveedor, String descrTalla,String descrColor,Double facing,String aprovisionamiento, String tipo,
			 Long nivelLote, String id, String tipoUFP, Double ufp, Long codArticuloEroski, String descriptionArtEroski) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.descriptionArt = descriptionArt;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.stock = stock;
		this.enCurso1 = enCurso1;
		this.enCurso2 = enCurso2;
		this.unidadesCaja = unidadesCaja;
		this.cajasPedidas = cajasPedidas;
		this.propuesta = propuesta;
		this.cantidad = cantidad;
		this.cantidadOriginal = cantidadOriginal;
		this.cantidadUltValida = cantidadUltValida;
		this.tipoDetallado = tipoDetallado;
		this.estadoGrid = estadoGrid;
		this.estadoPedido = estadoPedido;
		this.horaLimite=horalimite;
		this.resultadoWS=resultadoWS;
		this.cantidadAnt = cantidadAnt;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
		this.facing = facing;
		this.tipoAprovisionamiento = aprovisionamiento;
		this.tipo = tipo;
		this.nivelLote = nivelLote;
		this.id = id;
		this.tipoUFP = tipoUFP;
		this.ufp = ufp;
		this.codArticuloEroski = codArticuloEroski;
		this.descriptionArtEroski = descriptionArtEroski;
		
	}
	
	
			public DetallePedido(Long codCentro, Long codArticulo, String descriptionArt, Long grupo1, Long grupo2, Long grupo3,
					Long grupo4, Long grupo5, Double stock, String aprovisionamiento,
					Double unidadesCaja, Double facing,
					String descrColor, String descrTalla,
					String temporada, Long numOrden,String modeloProveedor) {
				super();
				this.codCentro = codCentro;
				this.codArticulo = codArticulo;
				this.descriptionArt = descriptionArt;
				this.grupo1 = grupo1;
				this.grupo2 = grupo2;
				this.grupo3 = grupo3;
				this.grupo4 = grupo4;
				this.grupo5 = grupo5;
				this.stock = stock;
				this.unidadesCaja = unidadesCaja;
				this.temporada = temporada;
				this.numOrden = numOrden;
				this.modeloProveedor = modeloProveedor;
				this.descrTalla = descrTalla;
				this.descrColor = descrColor;
				this.facing = facing;
				this.tipoAprovisionamiento = aprovisionamiento;
				
			}
	
	//Constructor para textil
	public DetallePedido(Long codCentro,  Long codArticulo,
			String temporada, Long numOrden,
			String modeloProveedor, String descrTalla,String descrColor,
			Double facing
			) {
		super();
		this.codCentro = codCentro;
		this.codArticulo = codArticulo;
		this.temporada = temporada;
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
		this.facing = facing;
		
		
	}
	
	public DetallePedido(Long codArticulo, String temporada,
			Long numOrden,
			String modeloProveedor, String descrTalla,String descrColor) {
		super();
		this.codArticulo = codArticulo;
		this.temporada = temporada;
		
		this.numOrden = numOrden;
		this.modeloProveedor = modeloProveedor;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
}
	
	//Constructor para Tipo de Aprovisonamiento
		public DetallePedido(String Aprovisionamiento) {
			super();
			this.tipoAprovisionamiento = Aprovisionamiento;
			
			
		}


		public DetallePedido(Long codCentro,  Long grupo1, Long grupo2, Long grupo3,
				Long grupo4, Long grupo5, Long codArticulo,
				String descriptionArt, Double stock,
				Double enCurso1, Double enCurso2, Double unidadesCaja,
				Long cajasPedidas, Long propuesta, Long cantidad,  Long cantidadOriginal, Long cantidadUltValida,
				String tipoDetallado, String estadoGrid, String estadoPedido, String horalimite, String resultadoWS, Long cantidadAnt,
				String temporada, Long numOrden,String modeloProveedor, String descrTalla,String descrColor,Double facing,String aprovisionamiento, String tipo,
				 Long nivelLote, String id, String tipoUFP, Double ufp, Long codArticuloEroski, String descriptionArtEroski,String flgOferta) {
			super();
			this.codCentro = codCentro;
			this.codArticulo = codArticulo;
			this.descriptionArt = descriptionArt;
			this.grupo1 = grupo1;
			this.grupo2 = grupo2;
			this.grupo3 = grupo3;
			this.grupo4 = grupo4;
			this.grupo5 = grupo5;
			this.stock = stock;
			this.enCurso1 = enCurso1;
			this.enCurso2 = enCurso2;
			this.unidadesCaja = unidadesCaja;
			this.cajasPedidas = cajasPedidas;
			this.propuesta = propuesta;
			this.cantidad = cantidad;
			this.cantidadOriginal = cantidadOriginal;
			this.cantidadUltValida = cantidadUltValida;
			this.tipoDetallado = tipoDetallado;
			this.estadoGrid = estadoGrid;
			this.estadoPedido = estadoPedido;
			this.horaLimite=horalimite;
			this.resultadoWS=resultadoWS;
			this.cantidadAnt = cantidadAnt;
			this.temporada = temporada;
			this.numOrden = numOrden;
			this.modeloProveedor = modeloProveedor;
			this.descrTalla = descrTalla;
			this.descrColor = descrColor;
			this.facing = facing;
			this.tipoAprovisionamiento = aprovisionamiento;
			this.tipo = tipo;
			this.nivelLote = nivelLote;
			this.id = id;
			this.tipoUFP = tipoUFP;
			this.ufp = ufp;
			this.codArticuloEroski = codArticuloEroski;
			this.descriptionArtEroski = descriptionArtEroski;
			this.flgOferta = flgOferta;
		}
		
		public DetallePedido(Long codCentro,  Long grupo1, Long grupo2, Long grupo3,
				Long grupo4, Long grupo5, Long codArticulo,
				String descriptionArt, Double stock,
				Double enCurso1, Double enCurso2, Double unidadesCaja,
				Long cajasPedidas, Long propuesta, Long cantidad,  Long cantidadOriginal, Long cantidadUltValida,
				String tipoDetallado, String estadoGrid, String estadoPedido, String horalimite, String resultadoWS, Long cantidadAnt,
				String temporada, Long numOrden,String modeloProveedor, String descrTalla,String descrColor,Double facing,String aprovisionamiento, String tipo,
				 Long nivelLote, String id, String tipoUFP, Double ufp, Long codArticuloEroski, String descriptionArtEroski,String flgOferta, String oferta) {
			super();
			this.codCentro = codCentro;
			this.codArticulo = codArticulo;
			this.descriptionArt = descriptionArt;
			this.grupo1 = grupo1;
			this.grupo2 = grupo2;
			this.grupo3 = grupo3;
			this.grupo4 = grupo4;
			this.grupo5 = grupo5;
			this.stock = stock;
			this.enCurso1 = enCurso1;
			this.enCurso2 = enCurso2;
			this.unidadesCaja = unidadesCaja;
			this.cajasPedidas = cajasPedidas;
			this.propuesta = propuesta;
			this.cantidad = cantidad;
			this.cantidadOriginal = cantidadOriginal;
			this.cantidadUltValida = cantidadUltValida;
			this.tipoDetallado = tipoDetallado;
			this.estadoGrid = estadoGrid;
			this.estadoPedido = estadoPedido;
			this.horaLimite=horalimite;
			this.resultadoWS=resultadoWS;
			this.cantidadAnt = cantidadAnt;
			this.temporada = temporada;
			this.numOrden = numOrden;
			this.modeloProveedor = modeloProveedor;
			this.descrTalla = descrTalla;
			this.descrColor = descrColor;
			this.facing = facing;
			this.tipoAprovisionamiento = aprovisionamiento;
			this.tipo = tipo;
			this.nivelLote = nivelLote;
			this.id = id;
			this.tipoUFP = tipoUFP;
			this.ufp = ufp;
			this.codArticuloEroski = codArticuloEroski;
			this.descriptionArtEroski = descriptionArtEroski;
			this.flgOferta = flgOferta;
			this.oferta = oferta;
		}

		public DetallePedido(Long codGrupoBalanza, String descGrupoBalanza, Long codCentro,  Long grupo1, Long grupo2, Long grupo3,
				Long grupo4, Long grupo5, Long codArticulo,
				String descriptionArt, Double stock,
				Double enCurso1, Double enCurso2, Double unidadesCaja,
				Long cajasPedidas, Long propuesta, Long cantidad,  Long cantidadOriginal, Long cantidadUltValida,
				String tipoDetallado, String estadoGrid, String estadoPedido, String horalimite, String resultadoWS, Long cantidadAnt,
				String temporada, Long numOrden,String modeloProveedor, String descrTalla,String descrColor,Double facing,String aprovisionamiento, String tipo,
				 Long nivelLote, String id, String tipoUFP, Double ufp, Long codArticuloEroski, String descriptionArtEroski,String flgOferta, String oferta) {
			super();
			this.codGrupoBalanza = codGrupoBalanza;
			this.descGrupoBalanza = descGrupoBalanza;
			this.codCentro = codCentro;
			this.codArticulo = codArticulo;
			this.descriptionArt = descriptionArt;
			this.grupo1 = grupo1;
			this.grupo2 = grupo2;
			this.grupo3 = grupo3;
			this.grupo4 = grupo4;
			this.grupo5 = grupo5;
			this.stock = stock;
			this.enCurso1 = enCurso1;
			this.enCurso2 = enCurso2;
			this.unidadesCaja = unidadesCaja;
			this.cajasPedidas = cajasPedidas;
			this.propuesta = propuesta;
			this.cantidad = cantidad;
			this.cantidadOriginal = cantidadOriginal;
			this.cantidadUltValida = cantidadUltValida;
			this.tipoDetallado = tipoDetallado;
			this.estadoGrid = estadoGrid;
			this.estadoPedido = estadoPedido;
			this.horaLimite=horalimite;
			this.resultadoWS=resultadoWS;
			this.cantidadAnt = cantidadAnt;
			this.temporada = temporada;
			this.numOrden = numOrden;
			this.modeloProveedor = modeloProveedor;
			this.descrTalla = descrTalla;
			this.descrColor = descrColor;
			this.facing = facing;
			this.tipoAprovisionamiento = aprovisionamiento;
			this.tipo = tipo;
			this.nivelLote = nivelLote;
			this.id = id;
			this.tipoUFP = tipoUFP;
			this.ufp = ufp;
			this.codArticuloEroski = codArticuloEroski;
			this.descriptionArtEroski = descriptionArtEroski;
			this.flgOferta = flgOferta;
			this.oferta = oferta;
		}
		
	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getCodArticuloEroski() {
		return codArticuloEroski;
	}

	public void setCodArticuloEroski(Long codArticuloEroski) {
		this.codArticuloEroski = codArticuloEroski;
	}

	public String getDescriptionArt() {
		return descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
	}

	public String getDescriptionArtEroski() {
		return descriptionArtEroski;
	}

	public void setDescriptionArtEroski(String descriptionArtEroski) {
		this.descriptionArtEroski = descriptionArtEroski;
	}

	public Long getGrupo1() {
		return grupo1;
	}

	public void setGrupo1(Long grupo1) {
		this.grupo1 = grupo1;
	}

	public Long getGrupo2() {
		return grupo2;
	}

	public void setGrupo2(Long grupo2) {
		this.grupo2 = grupo2;
	}

	public Long getGrupo3() {
		return grupo3;
	}

	public void setGrupo3(Long grupo3) {
		this.grupo3 = grupo3;
	}

	public Long getGrupo4() {
		return grupo4;
	}

	public void setGrupo4(Long grupo4) {
		this.grupo4 = grupo4;
	}

	public Long getGrupo5() {
		return grupo5;
	}

	public void setGrupo5(Long grupo5) {
		this.grupo5 = grupo5;
	}
	public String getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Double getEnCurso1() {
		return enCurso1;
	}

	public void setEnCurso1(Double enCurso1) {
		this.enCurso1 = enCurso1;
	}

	public Double getEnCurso2() {
		return enCurso2;
	}

	public void setEnCurso2(Double enCurso2) {
		this.enCurso2 = enCurso2;
	}

	public Double getUnidadesCaja() {
		return unidadesCaja;
	}

	public void setUnidadesCaja(Double unidadesCaja) {
		this.unidadesCaja = unidadesCaja;
	}

	public Long getCajasPedidas() {
		return cajasPedidas;
	}

	public void setCajasPedidas(Long cajasPedidas) {
		this.cajasPedidas = cajasPedidas;
	}

	public Long getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Long propuesta) {
		this.propuesta = propuesta;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	
	public Long getCantidadOriginal() {
		return cantidadOriginal;
	}

	public void setCantidadOriginal(Long cantidadOriginal) {
		this.cantidadOriginal = cantidadOriginal;
	}

	public Long getCantidadUltValida() {
		return cantidadUltValida;
	}

	public void setCantidadUltValida(Long cantidadUltValida) {
		this.cantidadUltValida = cantidadUltValida;
	}
	
	public String getTipoDetallado() {
		return tipoDetallado;
	}

	public void setTipoDetallado(String tipoDetallado) {
		this.tipoDetallado = tipoDetallado;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public List<DetallePedidoModificados> getListaModificados() {
		return listaModificados;
	}

	public void setListaModificados(List<DetallePedidoModificados> listaModificados) {
		this.listaModificados = listaModificados;
	}

	public String getEstadoGrid() {
		return estadoGrid;
	}

	public void setEstadoGrid(String estadoGrid) {
		this.estadoGrid = estadoGrid;
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getHoraLimite() {
		return horaLimite;
	}

	public void setHoraLimite(String horaLimite) {
		this.horaLimite = horaLimite;
	}

	public String getResultadoWS() {
		return resultadoWS;
	}

	public void setResultadoWS(String resultadoWS) {
		this.resultadoWS = resultadoWS;
	}
	
	public Integer getTotalGuardar() {
		return totalGuardar;
	}

	public void setTotalGuardar(Integer totalGuardar) {
		this.totalGuardar = totalGuardar;
	}

	public Integer getTotalError() {
		return totalError;
	}

	public void setTotalError(Integer totalError) {
		this.totalError = totalError;
	}

	public Long getNivelLote() {
		return nivelLote;
	}

	public void setNivelLote(Long nivelLote) {
		this.nivelLote = nivelLote;
	}
	
	
	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}
	
	public Long getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Long numOrden) {
		this.numOrden = numOrden;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public String getDescrTalla() {
		return descrTalla;
	}

	public void setDescrTalla(String descrTalla) {
		this.descrTalla = descrTalla;
	}

	public String getDescrColor() {
		return descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}
	
	public Double getFacing() {
		return facing;
	}

	public void setFacing(Double facing) {
		this.facing = facing;
	}
	
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.codArticulo).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof DetallePedido)){
			return false;
		}
		DetallePedido dp = (DetallePedido) obj;
		return new EqualsBuilder().append(this.codArticulo, dp.codArticulo).isEquals();
	}

	public String getTipoAprovisionamiento() {
		return tipoAprovisionamiento;
	}

	public void setTipoAprovisionamiento(String tipoAprovisionamiento) {
		this.tipoAprovisionamiento = tipoAprovisionamiento;
	}

	public Double getUfp() {
		return ufp;
	}

	public void setUfp(Double ufp) {
		this.ufp = ufp;
	}
	
	public Double getDexenx() {
		return dexenx;
	}

	public void setDexenx(Double dexenx) {
		this.dexenx = dexenx;
	}

	public Double getDexenxEntero() {
		return dexenxEntero;
	}

	public void setDexenxEntero(Double dexenxEntero) {
		this.dexenxEntero = dexenxEntero;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Long getCantidadAnt() {
		return cantidadAnt;
	}

	public void setCantidadAnt(Long cantidadAnt) {
		this.cantidadAnt = cantidadAnt;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoUFP() {
		return tipoUFP;
	}

	public void setTipoUFP(String tipoUFP) {
		this.tipoUFP = tipoUFP;
	}

	public String getNextDayPedido() {
		return nextDayPedido;
	}

	public void setNextDayPedido(String nextDayPedido) {
		this.nextDayPedido = nextDayPedido;
	}
	
	public String getFlgSIA() {
		return flgSIA;
	}

	public void setFlgSIA(String flgSIA) {
		this.flgSIA = flgSIA;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Long getUnidFlEmpuje() {
		return unidFlEmpuje;
	}

	public void setUnidFlEmpuje(Long unidFlEmpuje) {
		this.unidFlEmpuje = unidFlEmpuje;
	}

	public Long getCodNecesidad() {
		return codNecesidad;
	}

	public void setCodNecesidad(Long codNecesidad) {
		this.codNecesidad = codNecesidad;
	}
	
	public Long getCodErrorRedondeo() {
		return codErrorRedondeo;
	}

	public void setCodErrorRedondeo(Long codErrorRedondeo) {
		this.codErrorRedondeo = codErrorRedondeo;
	}

	public String getDescErrorRedondeo() {
		return descErrorRedondeo;
	}

	public void setDescErrorRedondeo(String descErrorRedondeo) {
		this.descErrorRedondeo = descErrorRedondeo;
	}

	public String getFlgOferta() {
		return flgOferta;
	}

	public void setFlgOferta(String flgOferta) {
		this.flgOferta = flgOferta;
	}

	public String getFlgIncluirPropPed() {
		return flgIncluirPropPed;
	}

	public void setFlgIncluirPropPed(String flgIncluirPropPed) {
		this.flgIncluirPropPed = flgIncluirPropPed;
	}
	
	public String getFlgPropuesta() {
		return flgPropuesta;
	}

	public void setFlgPropuesta(String flgPropuesta) {
		this.flgPropuesta = flgPropuesta;
	}

	public String getOferta() {
		return oferta;
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

	
	public String getConverArt() {
		return converArt;
	}

	public void setConverArt(String converArt) {
		this.converArt = converArt;
	}
	
	
	public String getDenomArea() {
		return denomArea;
	}

	public void setDenomArea(String denomArea) {
		this.denomArea = denomArea;
	}

	public String getDenomSeccion() {
		return denomSeccion;
	}

	public void setDenomSeccion(String denomSeccion) {
		this.denomSeccion = denomSeccion;
	}

	public String getDenomCategoria() {
		return denomCategoria;
	}

	public void setDenomCategoria(String denomCategoria) {
		this.denomCategoria = denomCategoria;
	}

	public String getDenomSubcategoria() {
		return denomSubcategoria;
	}

	public void setDenomSubcategoria(String denomSubcategoria) {
		this.denomSubcategoria = denomSubcategoria;
	}

	public String getDenomSegmento() {
		return denomSegmento;
	}

	public void setDenomSegmento(String denomSegmento) {
		this.denomSegmento = denomSegmento;
	}
	

	public Double getPrecioCostoArticulo() {
		return precioCostoArticulo;
	}

	public void setPrecioCostoArticulo(Double precioCostoArticulo) {
		this.precioCostoArticulo = precioCostoArticulo;
	}

	public Double getPrecioCostoInicial() {
		return precioCostoInicial;
	}

	public void setPrecioCostoInicial(Double precioCostoInicial) {
		this.precioCostoInicial = precioCostoInicial;
	}

	public Double getPrecioCostoFinal() {
		return precioCostoFinal;
	}

	public void setPrecioCostoFinal(Double precioCostoFinal) {
		this.precioCostoFinal = precioCostoFinal;
	}

	
	public String getRotacion() {
		return rotacion;
	}

	public void setRotacion(String rotacion) {
		this.rotacion = rotacion;
	}
	

	public Integer getErrorModificarWS() {
		return errorModificarWS;
	}

	public void setErrorModificarWS(Integer errorModificarWS) {
		this.errorModificarWS = errorModificarWS;
	}

	public Integer getErrorModificarProc() {
		return errorModificarProc;
	}

	public void setErrorModificarProc(Integer errorModificarProc) {
		this.errorModificarProc = errorModificarProc;
	}
	
	public String getFlgNoRealizarUpdate() {
		return flgNoRealizarUpdate;
	}

	public void setFlgNoRealizarUpdate(String flgNoRealizarUpdate) {
		this.flgNoRealizarUpdate = flgNoRealizarUpdate;
	}

	public Double getPrecioCostoFinalFinal() {
		return precioCostoFinalFinal;
	}

	public void setPrecioCostoFinalFinal(Double precioCostoFinalFinal) {
		this.precioCostoFinalFinal = precioCostoFinalFinal;
	}

	public String getRespetarIMC() {
		return respetarIMC;
	}

	public void setRespetarIMC(String respetarIMC) {
		this.respetarIMC = respetarIMC;
	}

	public Long getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(Long diferencia) {
		this.diferencia = diferencia;
	}
	
	public String getRefCumple() {
		return refCumple;
	}

	public void setRefCumple(String refCumple) {
		this.refCumple = refCumple;
	}


	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}
	
	public Integer getCodError() {
		return codError;
	}

	public void setCodError(Integer codError) {
		this.codError = codError;
	}

	public Integer getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(Integer codMapa) {
		this.codMapa = codMapa;
	}

	public String getMotivoPedido() {
		return motivoPedido;
	}

	public void setMotivoPedido(String motivoPedido) {
		this.motivoPedido = motivoPedido;
	}

	public String getFFPP() {
		return FFPP;
	}

	public void setFFPP(String fFPP) {
		FFPP = fFPP;
	}

	public boolean isFiltroMapa() {
		return filtroMapa;
	}

	public void setFiltroMapa(boolean filtroMapa) {
		this.filtroMapa = filtroMapa;
	}

	public Long getCodGrupoBalanza() {
		return codGrupoBalanza;
	}

	public void setCodGrupoBalanza(Long codGrupoBalanza) {
		this.codGrupoBalanza = codGrupoBalanza;
	}

	public String getDescGrupoBalanza() {
		return descGrupoBalanza;
	}

	public void setDescGrupoBalanza(String descGrupoBalanza) {
		this.descGrupoBalanza = descGrupoBalanza;
	}
	
	public Long getCajasCortadas() {
		return cajasCortadas;
	}

	public void setCajasCortadas(Long cajasCortadas) {
		this.cajasCortadas = cajasCortadas;
	}

	public String getIncPrevisionVenta() {
		return incPrevisionVenta;
	}

	public void setIncPrevisionVenta(String incPrevisionVenta) {
		this.incPrevisionVenta = incPrevisionVenta;
	}

	public Long getSmEstatico() {
		return smEstatico;
	}

	public void setSmEstatico(Long smEstatico) {
		this.smEstatico = smEstatico;
	}

}