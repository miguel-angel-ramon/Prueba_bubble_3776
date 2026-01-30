package es.eroski.misumi.model;

import java.io.Serializable;

public class SeguimientoMiPedidoDetalle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Centro centro;
	private Long referencia;
    private String descripcion;
    private Float total;
    private Float cajasNormales;
    private Float cajasEmpuje;
    private Float cajasCabecera;
    private Float cajasNoServidas;
    private String motivo;
    // MISUMI-299
    private String motivoPedido;
    private Float cajasIntertienda;
    private String codMapa;
    private String descMapa;
    
    //Campos Para Textil
    private String color;
	private String talla;
    private String modeloProveedor;
    
    //Campos totales
    private Long pedTotalRefBajoPedido;
    private Float pedTotalCajasBajoPedido;
    private Float pedTotalCajasBajoPedidoExcel;
    
    private Long pedTotalRefEmpuje;
    private Float pedTotalCajasEmpuje;
    private Float pedTotalCajasEmpujeExcel;
    
    private Long pedTotalRefIntertienda;
    private Float pedTotalCajasIntertienda;
    private Float pedTotalCajasIntertiendaExcel;
    
    
    private Long pedTotalRefImplCab;
    private Float pedTotalCajasImplCab;
    private Float pedTotalCajasImplCabExcel;
    
    private Long nsrTotalRefNoServidas;
    private Float nsrTotalCajasNoServidas;
    private Float nsrTotalCajasNoServidasExcel;
    
    private Long confTotalRefBajoPedido;
    private Float confTotalCajasBajoPedido;
    private Float confTotalCajasBajoPedidoExcel;
    
    private Long confTotalRefEmpuje;
    private Float confTotalCajasEmpuje;
    private Float confTotalCajasEmpujeExcel;
    
    private Long confTotalRefIntertienda;

	private Float confTotalCajasIntertienda;
    private Float confTotalCajasIntertiendaExcel;
    
    private Long confTotalRefImplCab;
    private Float confTotalCajasImplCab;
    private Float confTotalCajasImplCabExcel;

	private Long cajasCortadas;
	private String incPrevisionVenta;
	private Long smEstatico;
	private Long facing;
	private String origenPedido;

	private Long cajasAntesAjuste;

    public SeguimientoMiPedidoDetalle() {
		super();
	}

	public SeguimientoMiPedidoDetalle( Centro centro, Long referencia, String descripcion
									 , Float total, Float cajasNormales, Float cajasEmpuje
									 , Float cajasCabecera, Float cajasNoServidas, String motivo
									 , Float cajasIntertienda, String color, String talla
									 , String modeloProveedor, String motivoPedido
									 , Long cajasCortadas, String incPrevisionVenta
									 , Long smEstatico, Long facing, String origenPedido
									 ) {
		super();
		this.centro = centro;
		this.referencia = referencia;
		this.descripcion = descripcion;
		this.total = total;
		this.cajasNormales = cajasNormales;
		this.cajasEmpuje = cajasEmpuje;
		this.cajasCabecera = cajasCabecera;
		this.cajasNoServidas = cajasNoServidas;
		this.motivo = motivo;
		this.cajasIntertienda = cajasIntertienda;
		this.color = color;
		this.talla= talla;
		this.modeloProveedor= modeloProveedor;
		this.motivoPedido = motivoPedido;
		this.cajasCortadas = cajasCortadas;
		this.incPrevisionVenta = incPrevisionVenta;
		this.smEstatico = smEstatico;
		this.facing = facing;
		this.origenPedido = origenPedido;
	}

	public SeguimientoMiPedidoDetalle( Centro centro, Long referencia, String descripcion
			 , Float total, Float cajasNormales, Float cajasEmpuje
			 , Float cajasCabecera, Float cajasNoServidas, String motivo
			 , Float cajasIntertienda, String color, String talla
			 , String modeloProveedor, String motivoPedido
			 , Long cajasCortadas, String incPrevisionVenta
			 , Long smEstatico, Long facing, String origenPedido,Long cajasAntesAjuste
			 ) {
			super();
			this.centro = centro;
			this.referencia = referencia;
			this.descripcion = descripcion;
			this.total = total;
			this.cajasNormales = cajasNormales;
			this.cajasEmpuje = cajasEmpuje;
			this.cajasCabecera = cajasCabecera;
			this.cajasNoServidas = cajasNoServidas;
			this.motivo = motivo;
			this.cajasIntertienda = cajasIntertienda;
			this.color = color;
			this.talla= talla;
			this.modeloProveedor= modeloProveedor;
			this.motivoPedido = motivoPedido;
			this.cajasCortadas = cajasCortadas;
			this.incPrevisionVenta = incPrevisionVenta;
			this.smEstatico = smEstatico;
			this.facing = facing;
			this.origenPedido = origenPedido;
			this.cajasAntesAjuste = cajasAntesAjuste;
	}
	
    public Long getPedTotalRefIntertienda() {
		return pedTotalRefIntertienda;
	}

	public void setPedTotalRefIntertienda(Long pedTotalRefIntertienda) {
		this.pedTotalRefIntertienda = pedTotalRefIntertienda;
	}

	public Float getPedTotalCajasIntertienda() {
		return pedTotalCajasIntertienda;
	}

	public void setPedTotalCajasIntertienda(Float pedTotalCajasIntertienda) {
		this.pedTotalCajasIntertienda = pedTotalCajasIntertienda;
	}

	public Float getPedTotalCajasIntertiendaExcel() {
		return pedTotalCajasIntertiendaExcel;
	}

	public void setPedTotalCajasIntertiendaExcel(Float pedTotalCajasIntertiendaExcel) {
		this.pedTotalCajasIntertiendaExcel = pedTotalCajasIntertiendaExcel;
	}

	public Long getConfTotalRefIntertienda() {
		return confTotalRefIntertienda;
	}

	public void setConfTotalRefIntertienda(Long confTotalRefIntertienda) {
		this.confTotalRefIntertienda = confTotalRefIntertienda;
	}

	public Float getConfTotalCajasIntertienda() {
		return confTotalCajasIntertienda;
	}

	public void setConfTotalCajasIntertienda(Float confTotalCajasIntertienda) {
		this.confTotalCajasIntertienda = confTotalCajasIntertienda;
	}

	public Float getConfTotalCajasIntertiendaExcel() {
		return confTotalCajasIntertiendaExcel;
	}

	public void setConfTotalCajasIntertiendaExcel(
			Float confTotalCajasIntertiendaExcel) {
		this.confTotalCajasIntertiendaExcel = confTotalCajasIntertiendaExcel;
	}

	public Float getCajasIntertienda() {
		return cajasIntertienda;
	}

	public void setCajasIntertienda(Float cajasIntertienda) {
		this.cajasIntertienda = cajasIntertienda;
	}

	public Centro getCentro() {
		return this.centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Long getReferencia() {
		return this.referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getTotal() {
		return this.total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getCajasNormales() {
		return this.cajasNormales;
	}

	public void setCajasNormales(Float cajasNormales) {
		this.cajasNormales = cajasNormales;
	}

	public Float getCajasEmpuje() {
		return this.cajasEmpuje;
	}

	public void setCajasEmpuje(Float cajasEmpuje) {
		this.cajasEmpuje = cajasEmpuje;
	}

	public Float getCajasCabecera() {
		return this.cajasCabecera;
	}

	public void setCajasCabecera(Float cajasCabecera) {
		this.cajasCabecera = cajasCabecera;
	}

	public Float getCajasNoServidas() {
		return this.cajasNoServidas;
	}

	public void setCajasNoServidas(Float cajasNoServidas) {
		this.cajasNoServidas = cajasNoServidas;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Long getPedTotalRefBajoPedido() {
		return this.pedTotalRefBajoPedido;
	}

	public void setPedTotalRefBajoPedido(Long pedTotalRefBajoPedido) {
		this.pedTotalRefBajoPedido = pedTotalRefBajoPedido;
	}

	public Float getPedTotalCajasBajoPedido() {
		return this.pedTotalCajasBajoPedido;
	}

	public void setPedTotalCajasBajoPedido(Float pedTotalCajasBajoPedido) {
		this.pedTotalCajasBajoPedido = pedTotalCajasBajoPedido;
	}

	public Long getPedTotalRefEmpuje() {
		return this.pedTotalRefEmpuje;
	}

	public void setPedTotalRefEmpuje(Long pedTotalRefEmpuje) {
		this.pedTotalRefEmpuje = pedTotalRefEmpuje;
	}

	public Float getPedTotalCajasEmpuje() {
		return this.pedTotalCajasEmpuje;
	}

	public void setPedTotalCajasEmpuje(Float pedTotalCajasEmpuje) {
		this.pedTotalCajasEmpuje = pedTotalCajasEmpuje;
	}

	public Long getPedTotalRefImplCab() {
		return this.pedTotalRefImplCab;
	}

	public void setPedTotalRefImplCab(Long pedTotalRefImplCab) {
		this.pedTotalRefImplCab = pedTotalRefImplCab;
	}

	public Float getPedTotalCajasImplCab() {
		return this.pedTotalCajasImplCab;
	}

	public void setPedTotalCajasImplCab(Float pedTotalCajasImplCab) {
		this.pedTotalCajasImplCab = pedTotalCajasImplCab;
	}

	public Long getNsrTotalRefNoServidas() {
		return this.nsrTotalRefNoServidas;
	}

	public void setNsrTotalRefNoServidas(Long nsrTotalRefNoServidas) {
		this.nsrTotalRefNoServidas = nsrTotalRefNoServidas;
	}

	public Float getNsrTotalCajasNoServidas() {
		return this.nsrTotalCajasNoServidas;
	}

	public void setNsrTotalCajasNoServidas(Float nsrTotalCajasNoServidas) {
		this.nsrTotalCajasNoServidas = nsrTotalCajasNoServidas;
	}

	public Long getConfTotalRefBajoPedido() {
		return this.confTotalRefBajoPedido;
	}

	public void setConfTotalRefBajoPedido(Long confTotalRefBajoPedido) {
		this.confTotalRefBajoPedido = confTotalRefBajoPedido;
	}

	public Float getConfTotalCajasBajoPedido() {
		return this.confTotalCajasBajoPedido;
	}

	public void setConfTotalCajasBajoPedido(Float confTotalCajasBajoPedido) {
		this.confTotalCajasBajoPedido = confTotalCajasBajoPedido;
	}

	public Long getConfTotalRefEmpuje() {
		return this.confTotalRefEmpuje;
	}

	public void setConfTotalRefEmpuje(Long confTotalRefEmpuje) {
		this.confTotalRefEmpuje = confTotalRefEmpuje;
	}

	public Float getConfTotalCajasEmpuje() {
		return this.confTotalCajasEmpuje;
	}

	public void setConfTotalCajasEmpuje(Float confTotalCajasEmpuje) {
		this.confTotalCajasEmpuje = confTotalCajasEmpuje;
	}

	public Long getConfTotalRefImplCab() {
		return this.confTotalRefImplCab;
	}

	public void setConfTotalRefImplCab(Long confTotalRefImplCab) {
		this.confTotalRefImplCab = confTotalRefImplCab;
	}

	public Float getConfTotalCajasImplCab() {
		return this.confTotalCajasImplCab;
	}

	public void setConfTotalCajasImplCab(Float confTotalCajasImplCab) {
		this.confTotalCajasImplCab = confTotalCajasImplCab;
	}

	public Float getPedTotalCajasBajoPedidoExcel() {
		return this.pedTotalCajasBajoPedidoExcel;
	}

	public void setPedTotalCajasBajoPedidoExcel(Float pedTotalCajasBajoPedidoExcel) {
		this.pedTotalCajasBajoPedidoExcel = pedTotalCajasBajoPedidoExcel;
	}

	public Float getPedTotalCajasEmpujeExcel() {
		return this.pedTotalCajasEmpujeExcel;
	}

	public void setPedTotalCajasEmpujeExcel(Float pedTotalCajasEmpujeExcel) {
		this.pedTotalCajasEmpujeExcel = pedTotalCajasEmpujeExcel;
	}

	public Float getPedTotalCajasImplCabExcel() {
		return this.pedTotalCajasImplCabExcel;
	}

	public void setPedTotalCajasImplCabExcel(Float pedTotalCajasImplCabExcel) {
		this.pedTotalCajasImplCabExcel = pedTotalCajasImplCabExcel;
	}

	public Float getNsrTotalCajasNoServidasExcel() {
		return this.nsrTotalCajasNoServidasExcel;
	}

	public void setNsrTotalCajasNoServidasExcel(Float nsrTotalCajasNoServidasExcel) {
		this.nsrTotalCajasNoServidasExcel = nsrTotalCajasNoServidasExcel;
	}

	public Float getConfTotalCajasBajoPedidoExcel() {
		return this.confTotalCajasBajoPedidoExcel;
	}

	public void setConfTotalCajasBajoPedidoExcel(
			Float confTotalCajasBajoPedidoExcel) {
		this.confTotalCajasBajoPedidoExcel = confTotalCajasBajoPedidoExcel;
	}

	public Float getConfTotalCajasEmpujeExcel() {
		return this.confTotalCajasEmpujeExcel;
	}

	public void setConfTotalCajasEmpujeExcel(Float confTotalCajasEmpujeExcel) {
		this.confTotalCajasEmpujeExcel = confTotalCajasEmpujeExcel;
	}

	public Float getConfTotalCajasImplCabExcel() {
		return this.confTotalCajasImplCabExcel;
	}

	public void setConfTotalCajasImplCabExcel(Float confTotalCajasImplCabExcel) {
		this.confTotalCajasImplCabExcel = confTotalCajasImplCabExcel;
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

	public String getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(String codMapa) {
		this.codMapa = codMapa;
	}

	public String getDescMapa() {
		return descMapa;
	}

	public void setDescMapa(String descMapa) {
		this.descMapa = descMapa;
	}

	public String getMotivoPedido() {
		return motivoPedido;
	}

	public void setMotivoPedido(String motivoPedido) {
		this.motivoPedido = motivoPedido;
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

	public Long getFacing() {
		return facing;
	}

	public void setFacing(Long facing) {
		this.facing = facing;
	}

	public String getOrigenPedido() {
		return origenPedido;
	}

	public void setOrigenPedido(String origenPedido) {
		this.origenPedido = origenPedido;
	}

	public Long getCajasAntesAjuste() {
		return cajasAntesAjuste;
	}

	public void setCajasAntesAjuste(Long cajasAntesAjuste) {
		this.cajasAntesAjuste = cajasAntesAjuste;
	}
}