package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TDevolucionLinea implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String idSesion;
	private Date creationDate;
	private Long devolucion;
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
    private Double stockDevueltoOrig;
    private Double cantAbonada;
    private String flgContinuidad;
    private String lote;  
    private String nLote;
    private String caducidad;
    private String nCaducidad;
    private String descAbonoError;
    private Long bulto;
    private Long bultoOrig;
    private String bultoStr;
    private String ubicacion;
    private String tipoReferencia;
	private Long estadoLin;
    private Long codError;
    private String descError;
    private String flgBandejas;
    private String flgPesoVariable; // MISUMI-259
    private Long stockDevueltoBandejas;
    private Long stockDevueltoBandejasOrig;
    
    //Este atributo solo estará disponble para la primera fila de cada paginación del grid. En el caso de ser la 1 página
    //guardará un 1. Si no guardará el bulto del último elemento de la página anterior.
    private Long primerElementoBulto;
    private Double primerElementoStockDevuelto;
    
	private Long numRefProveedor;
    private Long contador;
    
    //Para comparar en los JSP los códigos de error
    private String codErrorStr;
	
    //Para realizar la ordenación en orden de recogida y disponer las referencias no retiradas en primer lugar
    private Long flgRefRetiradas;
    
    private String flgFoto;
    
	//Lista que guarda los bultos por proveedor
	private List<String> bultoPorProveedorLst;
	
    //Para el tipo de la línea de devolución. Pet MISUMI-9 combobox
    private Long codTpCa;
    
    //Para el máximo de cantidad permitido. Pet MISUMI-9.
	private Long cantidadMaximaPermitida;
	
    //Pet. MISUMI - 114
	//textil
	private String descrTalla;
	private String descrColor;
	private String modeloProveedor;
	private String modelo;
	//Bazar
	private Double costeUnitario;
	private Double costeFinal;
	
	private Long area;
	
	
	//MISUMI-269
	private Double cantidadMaximaLin;
	
	private String codEAN;
	
	List<TDevolucionBulto> listTDevolucionLinea = new ArrayList<TDevolucionBulto>();
	private Boolean variosBultos;
	private String estadoCerrado;
	
	private String bultoEstadoCerrado;
	
	private String listaBultos;
	
	private String hayRefsPdtes;
    
	public TDevolucionLinea(String idSesion, Date creationDate, Long devolucion, Long codArticulo, String denominacion,
							String marca, String seccion, Long provrGen, Long provrTrabajo, String denomProveedor, String familia,
							String formatoDevuelto, Double formato, String tipoFormato, String pasillo, String estructuraComercial,
							Double uc, Double stockActual, Double stockTienda, Double stockDevolver, Double stockDevuelto,
							Double stockDevueltoOrig, Double cantAbonada, String flgContinuidad, String lote, String nLote,
							String caducidad, String nCaducidad, String descAbonoError, Long bulto,Long bultoOrig, String ubicacion,
							String tipoReferencia, Long estadoLin, Long codError, String descError, String flgBandejas, String flgPesoVariable, 
							Long stockDevueltoBandejas, Long stockDevueltoBandejasOrig, Long codTpCa, Long cantidadMaximaPermitida
						   ) {
		super();
		this.idSesion = idSesion;
		this.creationDate = creationDate;
		this.devolucion = devolucion;
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
		this.stockDevueltoOrig = stockDevueltoOrig;
		this.cantAbonada = cantAbonada;
		this.flgContinuidad = flgContinuidad;
		this.lote = lote;
		this.nLote = nLote;
		this.caducidad = caducidad;
		this.nCaducidad = nCaducidad;
		this.descAbonoError = descAbonoError;
		this.bulto = bulto;
		this.bultoOrig = bultoOrig;
		this.ubicacion = ubicacion;
		this.tipoReferencia = tipoReferencia;
		this.estadoLin = estadoLin;
		this.codError = codError;
		this.descError = descError;
		this.flgBandejas = flgBandejas;
		this.flgPesoVariable = flgPesoVariable;
		this.stockDevueltoBandejas = stockDevueltoBandejas;
		this.stockDevueltoBandejasOrig = stockDevueltoBandejasOrig;
		this.codTpCa = codTpCa;
		this.cantidadMaximaPermitida = cantidadMaximaPermitida;
	}
	
	public TDevolucionLinea(String idSesion, Date creationDate, Long devolucion, Long codArticulo, String denominacion,
			String marca, String seccion, Long provrGen, Long provrTrabajo, String denomProveedor, String familia,
			String formatoDevuelto, Double formato, String tipoFormato, String pasillo, String estructuraComercial,
			Double uc, Double stockActual, Double stockTienda, Double stockDevolver, Double stockDevuelto,
			Double cantAbonada, String flgContinuidad, String lote, String nLote, String caducidad, String nCaducidad,
			String descAbonoError, Long bulto, String ubicacion, Long estadoLin, Long codError, String descError, Long numRefProveedor,
			Long flgRefRetiradas) {
		super();
		this.idSesion = idSesion;
		this.creationDate = creationDate;
		this.devolucion = devolucion;
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
		this.estadoLin = estadoLin;
		this.codError = codError;
		this.descError = descError;
		this.numRefProveedor = numRefProveedor;
		this.flgRefRetiradas = flgRefRetiradas;
	}
	

	public String getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Long devolucion) {
		this.devolucion = devolucion;
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
	public Double getStockDevueltoOrig() {
		return stockDevueltoOrig;
	}
	public void setStockDevueltoOrig(Double stockDevueltoOrig) {
		this.stockDevueltoOrig = stockDevueltoOrig;
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
	public Long getBultoOrig() {
		return bultoOrig;
	}
	public void setBultoOrig(Long bultoOrig) {
		this.bultoOrig = bultoOrig;
	}
	public String getBultoStr() {
		return bultoStr;
	}
	public void setBultoStr(String bultoStr) {
		this.bultoStr = bultoStr;
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

	public Long getPrimerElementoBulto() {
		return primerElementoBulto;
	}
	public void setPrimerElementoBulto(Long primerElementoBulto) {
		this.primerElementoBulto = primerElementoBulto;
	}  
	public Double getPrimerElementoStockDevuelto() {
		return primerElementoStockDevuelto;
	}

	public void setPrimerElementoStockDevuelto(Double primerElementoStockDevuelto) {
		this.primerElementoStockDevuelto = primerElementoStockDevuelto;
	}
	public Long getNumRefProveedor() {
		return numRefProveedor;
	}

	public void setNumRefProveedor(Long numRefProveedor) {
		this.numRefProveedor = numRefProveedor;
	}
	
	public Long getContador() {
		return contador;
	}

	public void setContador(Long contador) {
		this.contador = contador;
	}
	
	public String getProveedorFormated() {
    	StringBuilder sbProveedor = new StringBuilder();
    	sbProveedor.append(this.getProvrGen() != null ? this.getProvrGen().toString() : "");
    	return sbProveedor.toString();
	}

	public String getProveedorGenTrabajoFormated() {
    	StringBuilder sbProveedor = new StringBuilder();
    	sbProveedor.append(this.getProvrGen() != null ? this.getProvrGen().toString() : "");
    	sbProveedor.append(this.getProvrGen() != null && this.getProvrTrabajo() != null? "-" : "");
    	sbProveedor.append(this.getProvrTrabajo() != null ? this.getProvrTrabajo().toString() : "");
    	return sbProveedor.toString();
	}

	public String getFormatoFormated() {
    	StringBuilder sbFormato = new StringBuilder();
    	sbFormato.append(this.getFormato() != null ? this.getFormato().toString() : "");
    	sbFormato.append(this.getFormato() != null && this.getTipoFormato() != null? " " : "");
    	sbFormato.append(this.getTipoFormato() != null ? this.getTipoFormato().toString() : "");
    	return sbFormato.toString();
	}

	public String getCodErrorStr() {
		return codErrorStr;
	}

	public void setCodErrorStr(String codErrorStr) {
		this.codErrorStr = codErrorStr;
	}

	public String getFlgFoto() {
		return flgFoto;
	}

	public void setFlgFoto(String flgFoto) {
		this.flgFoto = flgFoto;
	}

	public Long getFlgRefRetiradas() {
		return this.flgRefRetiradas;
	}

	public void setFlgRefRetiradas(Long flgRefRetiradas) {
		this.flgRefRetiradas = flgRefRetiradas;
	}
	public List<String> getBultoPorProveedorLst() {
		return bultoPorProveedorLst;
	}

	public void setBultoPorProveedorLst(List<String> bultoPorProveedorLst) {
		this.bultoPorProveedorLst = bultoPorProveedorLst;
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

	public TDevolucionLinea() {
		super();
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

	public String getCodEAN() {
		return codEAN;
	}

	public void setCodEAN(String codEAN) {
		this.codEAN = codEAN;
	}

	public List<TDevolucionBulto> getListTDevolucionLinea() {
		return listTDevolucionLinea;
	}

	public void setListTDevolucionLinea(List<TDevolucionBulto> listTDevolucionLinea) {
		this.listTDevolucionLinea = listTDevolucionLinea;
	}

	public Boolean getVariosBultos() {
		return variosBultos;
	}

	public void setVariosBultos(Boolean variosBultos) {
		this.variosBultos = variosBultos;
	}

	public String getEstadoCerrado() {
		return estadoCerrado;
	}

	public void setEstadoCerrado(String estadoCerrado) {
		this.estadoCerrado = estadoCerrado;
	}

	public String getListaBultos() {
		return listaBultos;
	}

	public void setListaBultos(String listaBultos) {
		this.listaBultos = listaBultos;
	}
	public String getBultoEstadoCerrado() {
		return bultoEstadoCerrado;
	}

	public void setBultoEstadoCerrado(String bultoEstadoCerrado) {
		this.bultoEstadoCerrado = bultoEstadoCerrado;
	}
	
	public String getHayRefsPdtes() {
		return hayRefsPdtes;
	}

	public void setHayRefsPdtes(String hayRefsPdtes) {
		this.hayRefsPdtes = hayRefsPdtes;
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
		TDevolucionLinea other = (TDevolucionLinea) obj;
		if (codArticulo == null) {
			if (other.codArticulo != null)
				return false;
		} else if (!codArticulo.equals(other.codArticulo))
			return false;
		return true;
	}

	
}
