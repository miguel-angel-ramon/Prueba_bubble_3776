package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Equivalen a cada línea de la tabla relacionada con una devolución.
public class DevolucionLinea implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long codArticulo;
    private String denominacion;
    private String marca;
    private String seccion;
    private Long provrGen;
    private Long provrTrabajo;
    private String denomProveedor;
    private String familia;
    private String formatoDevuelto;
    private Double formato;
    private String tipoFormato;
    private String pasillo;
    private String estructuraComercial;
    private Double uc;
    private Double stockActual;
    private Double stockTienda;
    private Double stockDevolver;
    private Double stockDevuelto;
    private Double cantAbonada;
    private String flgContinuidad;
    private String lote;  
    private String nLote;
    private String caducidad;
    private String nCaducidad;
    private String descAbonoError;
    private Long bulto;
    private String ubicacion;
    private String tipoReferencia;
	private Long estadoLin;
    private Long codError;
    private String descError;
    private String flgBandejas;
    private String flgPesoVariable; // MISUMI-259
    private Long stockDevueltoBandejas;
    private Long stockDevueltoBandejasOrig;
    //Pet. MISUMI - 114
    //Textil
    private String descrTalla;
    private String descrColor;
    private String modeloProveedor;
    private String modelo;
    //Bazar
    private Double costeUnitario;
    private Double costeFinal;
    private Long area;
	
	//Para el tipo de la línea de devolución. Pet MISUMI-9 combobox
    private Long codTpCa;
    
    //Para el máximo de cantidad permitido. Pet MISUMI-9.
	private Long cantidadMaximaPermitida;
	
	//MISUMI-269
	private Double cantidadMaximaLin;
	
	//MISUMI-527
	private List<BultoCantidad> listaBultoCantidad = new ArrayList<BultoCantidad>();
	private Boolean variosBultos;
	
    public DevolucionLinea() {
		super();
	}

	public DevolucionLinea(Long codArticulo, String denominacion, String marca, String seccion, Long provrGen,
			Long provrTrabajo, String denomProveedor, String familia, String formatoDevuelto, Double formato,
			String tipoFormato, String pasillo, String estructuraComercial, Double uc, Double stockActual, Double stockTienda,
			Double stockDevolver, Double stockDevuelto, Double cantAbonada, String flgContinuidad, String lote, String nLote,
			String caducidad, String nCaducidad, String descAbonoError, Long bulto, String ubicacion, String tipoReferencia, Long estadoLin,
			Long codError, String descError, String flgBandejas, String flgPesoVariable, Long stockDevueltoBandejas) {
		super();
		this.codArticulo = codArticulo;
		this.denominacion = denominacion;
		this.marca = marca;
		this.seccion = seccion;
		this.provrGen = provrGen;
		this.provrTrabajo = provrTrabajo;
		this.denomProveedor = denomProveedor;
		this.familia = familia;
		this.formatoDevuelto = formatoDevuelto;
		this.formato = formato;
		this.tipoFormato = tipoFormato;
		this.pasillo = pasillo;
		this.estructuraComercial = estructuraComercial;
		this.uc = uc;
		this.stockActual = stockActual;
		this.stockTienda = stockTienda;
		this.stockDevolver = stockDevolver;
		this.stockDevuelto = stockDevuelto;
		this.cantAbonada = cantAbonada;
		this.flgContinuidad = flgContinuidad;
		this.lote = lote;
		this.nLote = nLote;
		this.caducidad = caducidad;
		this.nCaducidad = nCaducidad;
		this.descAbonoError = descAbonoError;
		this.bulto = bulto;
		this.ubicacion = ubicacion;
		this.tipoReferencia = tipoReferencia;
		this.estadoLin = estadoLin;
		this.codError = codError;
		this.descError = descError;
		this.flgBandejas = flgBandejas;
		this.flgPesoVariable = flgPesoVariable;
		this.stockDevueltoBandejas = stockDevueltoBandejas;
	}
	
	public DevolucionLinea(Long codArticulo, String denominacion, String marca, String seccion, Long provrGen,
							Long provrTrabajo, String denomProveedor, String familia, String formatoDevuelto, Double formato,
							String tipoFormato, String pasillo, String estructuraComercial, Double uc, Double stockActual, Double stockTienda,
							Double stockDevolver, Double stockDevuelto, Double cantAbonada, String flgContinuidad, String lote, String nLote,
							String caducidad, String nCaducidad, String descAbonoError, Long bulto, String ubicacion, String tipoReferencia, Long estadoLin,
							Long codError, String descError, String flgBandejas, String flgPesoVariable, Long stockDevueltoBandejas, Long codTpCa, 
							String descrTalla, String descrColor, String modeloProveedor, Double costeUnitario, Long area,
							Double cantidadMaximaLin
						  ) {
		super();
		this.codArticulo = codArticulo;
		this.denominacion = denominacion;
		this.marca = marca;
		this.seccion = seccion;
		this.provrGen = provrGen;
		this.provrTrabajo = provrTrabajo;
		this.denomProveedor = denomProveedor;
		this.familia = familia;
		this.formatoDevuelto = formatoDevuelto;
		this.formato = formato;
		this.tipoFormato = tipoFormato;
		this.pasillo = pasillo;
		this.estructuraComercial = estructuraComercial;
		this.uc = uc;
		this.stockActual = stockActual;
		this.stockTienda = stockTienda;
		this.stockDevolver = stockDevolver;
		this.stockDevuelto = stockDevuelto;
		this.cantAbonada = cantAbonada;
		this.flgContinuidad = flgContinuidad;
		this.lote = lote;
		this.nLote = nLote;
		this.caducidad = caducidad;
		this.nCaducidad = nCaducidad;
		this.descAbonoError = descAbonoError;
		this.bulto = bulto;
		this.ubicacion = ubicacion;
		this.tipoReferencia = tipoReferencia;
		this.estadoLin = estadoLin;
		this.codError = codError;
		this.descError = descError;
		this.flgBandejas = flgBandejas;
		this.flgPesoVariable = flgPesoVariable;
		this.stockDevueltoBandejas = stockDevueltoBandejas;
		this.codTpCa = codTpCa;
		this.descrTalla = descrTalla;
		this.descrColor = descrColor;
		this.modeloProveedor = modeloProveedor;
		this.costeUnitario = costeUnitario;
		this.area = area;
		this.cantidadMaximaLin = cantidadMaximaLin;
	}
	
	public Long getCodArticulo() {
		return codArticulo;
	}
	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	public String getDenominacion() {
		return denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public Long getProvrGen() {
		return provrGen;
	}
	public void setProvrGen(Long provrGen) {
		this.provrGen = provrGen;
	}
	public Long getProvrTrabajo() {
		return provrTrabajo;
	}
	public void setProvrTrabajo(Long provrTrabajo) {
		this.provrTrabajo = provrTrabajo;
	}
	public String getDenomProveedor() {
		return denomProveedor;
	}
	public void setDenomProveedor(String denomProveedor) {
		this.denomProveedor = denomProveedor;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getFormatoDevuelto() {
		return formatoDevuelto;
	}
	public void setFormatoDevuelto(String formatoDevuelto) {
		this.formatoDevuelto = formatoDevuelto;
	}
	public Double getFormato() {
		return formato;
	}
	public void setFormato(Double formato) {
		this.formato = formato;
	}
	public String getTipoFormato() {
		return tipoFormato;
	}
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}
	public String getPasillo() {
		return pasillo;
	}
	public void setPasillo(String pasillo) {
		this.pasillo = pasillo;
	}
	public String getEstructuraComercial() {
		return estructuraComercial;
	}
	public void setEstructuraComercial(String estructuraComercial) {
		this.estructuraComercial = estructuraComercial;
	}
	public Double getUc() {
		return uc;
	}
	public void setUc(Double uc) {
		this.uc = uc;
	}
	public Double getStockActual() {
		return stockActual;
	}
	public void setStockActual(Double stockActual) {
		this.stockActual = stockActual;
	}
	public Double getStockTienda() {
		return stockTienda;
	}
	public void setStockTienda(Double stockTienda) {
		this.stockTienda = stockTienda;
	}
	public Double getStockDevolver() {
		return stockDevolver;
	}
	public void setStockDevolver(Double stockDevolver) {
		this.stockDevolver = stockDevolver;
	}
	public Double getStockDevuelto() {
		return stockDevuelto;
	}
	public void setStockDevuelto(Double stockDevuelto) {
		this.stockDevuelto = stockDevuelto;
	}
	public Double getCantAbonada() {
		return cantAbonada;
	}
	public void setCantAbonada(Double cantAbonada) {
		this.cantAbonada = cantAbonada;
	}
	public String getFlgContinuidad() {
		return flgContinuidad;
	}
	public void setFlgContinuidad(String flgContinuidad) {
		this.flgContinuidad = flgContinuidad;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getnLote() {
		return nLote;
	}
	public void setnLote(String nLote) {
		this.nLote = nLote;
	}
	public String getCaducidad() {
		return caducidad;
	}
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	public String getnCaducidad() {
		return nCaducidad;
	}
	public void setnCaducidad(String nCaducidad) {
		this.nCaducidad = nCaducidad;
	}
	public String getDescAbonoError() {
		return descAbonoError;
	}
	public void setDescAbonoError(String descAbonoError) {
		this.descAbonoError = descAbonoError;
	}
	public Long getBulto() {
		return bulto;
	}
	public void setBulto(Long bulto) {
		this.bulto = bulto;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getTipoReferencia() {
		return tipoReferencia;
	}
	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}
	public Long getEstadoLin() {
		return estadoLin;
	}
	public void setEstadoLin(Long estadoLin) {
		this.estadoLin = estadoLin;
	}
	public Long getCodError() {
		return codError;
	}
	public void setCodError(Long codError) {
		this.codError = codError;
	}
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	
	public String getFlgBandejas() {
		return flgBandejas;
	}

	public void setFlgBandejas(String flgBandejas) {
		this.flgBandejas = flgBandejas;
	}

	public String getFlgPesoVariable() {
		return flgPesoVariable;
	}

	public void setFlgPesoVariable(String flgPesoVariable) {
		this.flgPesoVariable = flgPesoVariable;
	}
	
	public Long getStockDevueltoBandejas() {
		return stockDevueltoBandejas;
	}

	public void setStockDevueltoBandejas(Long stockDevueltoBandejas) {
		this.stockDevueltoBandejas = stockDevueltoBandejas;
	}

	public Long getStockDevueltoBandejasOrig() {
		return stockDevueltoBandejasOrig;
	}

	public void setStockDevueltoBandejasOrig(Long stockDevueltoBandejasOrig) {
		this.stockDevueltoBandejasOrig = stockDevueltoBandejasOrig;
	}

	public Long getCodTpCa() {
		return codTpCa;
	}

	public void setCodTpCa(Long codTpCa) {
		this.codTpCa = codTpCa;
	}

	public Long getCantidadMaximaPermitida() {
		return cantidadMaximaPermitida;
	}

	public void setCantidadMaximaPermitida(Long cantidadMaximaPermitida) {
		this.cantidadMaximaPermitida = cantidadMaximaPermitida;
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

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}
	
	public Double getCosteUnitario() {
		return costeUnitario;
	}

	public void setCosteUnitario(Double costeUnitario) {
		this.costeUnitario = costeUnitario;
	}
	
	public Double getCosteFinal() {
		return costeFinal;
	}

	public void setCosteFinal(Double costeFinal) {
		this.costeFinal = costeFinal;
	}
	
	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Double getCantidadMaximaLin() {
		return cantidadMaximaLin;
	}

	public void setCantidadMaximaLin(Double cantidadMaximaLin) {
		this.cantidadMaximaLin = cantidadMaximaLin;
	}

	public List<BultoCantidad> getListaBultoCantidad() {
		return listaBultoCantidad;
	}

	public void setListaBultoCantidad(List<BultoCantidad> listaBultoCantidad) {
		this.listaBultoCantidad = listaBultoCantidad;
	}
	
	public Boolean getVariosBultos() {
		return variosBultos;
	}

	public void setVariosBultos(Boolean variosBultos) {
		this.variosBultos = variosBultos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codArticulo == null) ? 0 : codArticulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevolucionLinea other = (DevolucionLinea) obj;
		if (codArticulo == null) {
			if (other.codArticulo != null)
				return false;
		} else if (!codArticulo.equals(other.codArticulo))
			return false;
		return true;
	}

}
