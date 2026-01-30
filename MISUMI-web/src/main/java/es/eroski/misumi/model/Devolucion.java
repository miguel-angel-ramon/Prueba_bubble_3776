package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;

//Contiene la información de una Devolución y una lista que contiene la información de las líneas de la tabla 
//relacionada con esa devolución
public class Devolucion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**Datos de la devolucion**/ 
	//0 -> CREADO,1 -> PEPARAR_MERCANCIA, 2 -> PLATAFORMA, 3 -> ABONADO, 4 -> INCIDENCIA
	private Long devolucion;
	private String localizador;
	private Long centro;
	private Date fechaDesde;
	private Date fechaHasta;
	private String flgRecogida;
	private String abono;
	private String recogida;
	private Long codPlataforma;
	private String titulo1;
	private String descripcion;
	private String motivo;
	private String titulo2;
	private Date fechaPrecio;
	private Long estadoCab;
	private Long flgFueraFechas;
	//private Long codTpCA;
	private Long codError;
	private String descError;
	//Pet. MISUMI - 114
	private Double costeMaximo;
	private String codRMA;
	private String tipoRMA;
	
	private List<DevolucionLinea> devLineas;
	
	//Atributo creado para guardar las líneas modificadas del grid
	private List<DevolucionLineaModificada> devLineasModificadas;
	//Valor del combobox proveedor
	private String proveedor;
	
	//Valor del combobox REF.PERMANENTES
	private String flagRefPermanentes;
	
	private String fechaDesdeStr;
	private String fechaHastaStr;
	private String fechaPrecioStr;

	//Datos de ayuda para obtener Devoluciones
	private String flagHistorico;
	private Long codArticulo;
	
	//Impresion PDFs
	//Se utiliza para paginar los pdf
	private String filtroProveedor;
	private String tipoPaginacionPDF;
	private boolean esOrdenRecogidaPDF;
	
	//Para poder mandar mediante un formulario más de una línea de 
	//devolucion en pda_p62
	private List<TDevolucionLinea> tDevLineasLst;
	
	//Para saber si la fecha actual es mayor que la fecha de fin (fechaHasta) de devolucion.
	private boolean fechaDeDevolucionPasada;

	//Lista que guarda los bultos por proveedor
	private List<String> bultoPorProveedorLst;

	/*
	 * Lista de bultos por proveedor.
	 */
	private List<Proveedor> listaProv;
	
	// Tipo de devolución seleccionada; Fin de Campaña u Orden de Retirada.
	private String tipoDevolucion;

	// almacena el número de bulto seleccionado en la pistola a la hora de mostrar
	// las referencias por bulto para un proveedor elegido.
	private int bultoSeleccionado;
	
	private String hayRefsPdtes;

	public Devolucion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas, Long codError, String descError,
			List<DevolucionLinea> devLineas, String fechaDesdeStr, String fechaHastaStr, String fechaPrecioStr) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codError = codError;
		this.descError = descError;
		this.devLineas = devLineas;
		this.fechaDesdeStr = fechaDesdeStr;
		this.fechaHastaStr = fechaHastaStr;
		this.fechaPrecioStr = fechaPrecioStr;
	}
	
	/*public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas,  Long codTpCA, Long codError, String descError,
			List<DevolucionLinea> devLineas, String fechaDesdeStr, String fechaHastaStr, String fechaPrecioStr,boolean fechaDeDevolucionPasada) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codTpCA = codTpCA;
		this.codError = codError;
		this.descError = descError;
		this.devLineas = devLineas;
		this.fechaDesdeStr = fechaDesdeStr;
		this.fechaHastaStr = fechaHastaStr;
		this.fechaPrecioStr = fechaPrecioStr;
		this.fechaDeDevolucionPasada = fechaDeDevolucionPasada;
	}*/
	public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas, Long codError, String descError, Double costeMaximo, String codRMA, String tipoRMA,
			List<DevolucionLinea> devLineas, String fechaDesdeStr, String fechaHastaStr, String fechaPrecioStr,boolean fechaDeDevolucionPasada) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codError = codError;
		this.costeMaximo = costeMaximo;
		this.codRMA = codRMA;
		this.tipoRMA = tipoRMA;
		this.descError = descError;
		this.devLineas = devLineas;
		this.fechaDesdeStr = fechaDesdeStr;
		this.fechaHastaStr = fechaHastaStr;
		this.fechaPrecioStr = fechaPrecioStr;
		this.fechaDeDevolucionPasada = fechaDeDevolucionPasada;
	}
	public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas, Long codError, String descError,
			List<DevolucionLinea> devLineas) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codError = codError;
		this.descError = descError;
		this.devLineas = devLineas;
	}

	public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas, Long codError, String descError,
			List<DevolucionLinea> devLineas, String flagHistorico, Long codArticulo) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codError = codError;
		this.descError = descError;
		this.devLineas = devLineas;
		this.flagHistorico = flagHistorico;
		this.codArticulo = codArticulo;
	}	
	
	public Devolucion(Long devolucion, String localizador, Long centro, Date fechaDesde, Date fechaHasta,
			String flgRecogida, String abono, String recogida, Long codPlataforma, String titulo1, String descripcion,
			String motivo, String titulo2, Date fechaPrecio, Long estadoCab, Long flgFueraFechas, Long codError, String descError,
			List<DevolucionLinea> devLineas, List<DevolucionLineaModificada> devLineasModificadas, String fechaDesdeStr,
			String fechaHastaStr, String fechaPrecioStr, String flagHistorico, Long codArticulo) {
		super();
		this.devolucion = devolucion;
		this.localizador = localizador;
		this.centro = centro;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.flgRecogida = flgRecogida;
		this.abono = abono;
		this.recogida = recogida;
		this.codPlataforma = codPlataforma;
		this.titulo1 = titulo1;
		this.descripcion = descripcion;
		this.motivo = motivo;
		this.titulo2 = titulo2;
		this.fechaPrecio = fechaPrecio;
		this.estadoCab = estadoCab;
		this.flgFueraFechas = flgFueraFechas;
		this.codError = codError;
		this.descError = descError;
		this.devLineas = devLineas;
		this.devLineasModificadas = devLineasModificadas;
		this.fechaDesdeStr = fechaDesdeStr;
		this.fechaHastaStr = fechaHastaStr;
		this.fechaPrecioStr = fechaPrecioStr;
		this.flagHistorico = flagHistorico;
		this.codArticulo = codArticulo;
	}

	public Long getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Long devolucion) {
		this.devolucion = devolucion;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public Long getCentro() {
		return centro;
	}

	public void setCentro(Long centro) {
		this.centro = centro;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getFlgRecogida() {
		return flgRecogida;
	}

	public void setFlgRecogida(String flgRecogida) {
		this.flgRecogida = flgRecogida;
	}

	public String getAbono() {
		return abono;
	}

	public void setAbono(String abono) {
		this.abono = abono;
	}

	public String getRecogida() {
		return recogida;
	}

	public void setRecogida(String recogida) {
		this.recogida = recogida;
	}

	public Long getCodPlataforma() {
		return codPlataforma;
	}

	public void setCodPlataforma(Long codPlataforma) {
		this.codPlataforma = codPlataforma;
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTitulo2() {
		return titulo2;
	}

	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}

	public Date getFechaPrecio() {
		return fechaPrecio;
	}

	public void setFechaPrecio(Date fechaPrecio) {
		this.fechaPrecio = fechaPrecio;
	}

	public Long getEstadoCab() {
		return estadoCab;
	}

	public void setEstadoCab(Long estadoCab) {
		this.estadoCab = estadoCab;
	}

	public Long getFlgFueraFechas() {
		return flgFueraFechas;
	}

	public void setFlgFueraFechas(Long flgFueraFechas) {
		this.flgFueraFechas = flgFueraFechas;
	}
	
	/*public Long getCodTpCA() {
		return codTpCA;
	}

	public void setCodTpCA(Long codTpCA) {
		this.codTpCA = codTpCA;
	}*/


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
	
	public Double getCosteMaximo() {
		return costeMaximo;
	}

	public void setCosteMaximo(Double costeMaximo) {
		this.costeMaximo = costeMaximo;
	}

	public String getCodRMA() {
		return codRMA;
	}

	public void setCodRMA(String codRMA) {
		this.codRMA = codRMA;
	}

	public String getTipoRMA() {
		return tipoRMA;
	}

	public void setTipoRMA(String tipoRMA) {
		this.tipoRMA = tipoRMA;
	}
	public List<DevolucionLinea> getDevLineas() {
		return devLineas;
	}

	public void setDevLineas(List<DevolucionLinea> devLineas) {
		this.devLineas = devLineas;
	}

	public List<DevolucionLineaModificada> getDevLineasModificadas() {
		return devLineasModificadas;
	}

	public void setDevLineasModificadas(List<DevolucionLineaModificada> devLineasModificadas) {
		this.devLineasModificadas = devLineasModificadas;
	}	
	
	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
	public String getFlagRefPermanentes() {
		return flagRefPermanentes;
	}

	public void setFlagRefPermanentes(String flagRefPermanentes) {
		this.flagRefPermanentes = flagRefPermanentes;
	}

	public String getFlagHistorico() {
		return flagHistorico;
	}

	public void setFlagHistorico(String flagHistorico) {
		this.flagHistorico = flagHistorico;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getFechaDesdeStr() {
		return fechaDesdeStr;
	}

	public void setFechaDesdeStr(String fechaDesdeStr) {
		this.fechaDesdeStr = fechaDesdeStr;
	}

	public String getFechaHastaStr() {
		return fechaHastaStr;
	}

	public void setFechaHastaStr(String fechaHastaStr) {
		this.fechaHastaStr = fechaHastaStr;
	}

	public String getFechaPrecioStr() {
		return fechaPrecioStr;
	}

	public void setFechaPrecioStr(String fechaPrecioStr) {
		this.fechaPrecioStr = fechaPrecioStr;
	}
	
	public String getFiltroProveedor() {
		return filtroProveedor;
	}

	public void setFiltroProveedor(String filtroProveedor) {
		this.filtroProveedor = filtroProveedor;
	}
	

	public String getTipoPaginacionPDF() {
		return tipoPaginacionPDF;
	}

	public void setTipoPaginacionPDF(String tipoPaginacionPDF) {
		this.tipoPaginacionPDF = tipoPaginacionPDF;
	}
	
	public boolean isEsOrdenRecogidaPDF() {
		return esOrdenRecogidaPDF;
	}

	public void setIsOrdenRecogidaPDF(boolean esOrdenRecogidaPDF) {
		this.esOrdenRecogidaPDF = esOrdenRecogidaPDF;
	}		

	public List<TDevolucionLinea> gettDevLineasLst() {
		return tDevLineasLst;
	}

	public void settDevLineasLst(List<TDevolucionLinea> tDevLineasLst) {
		this.tDevLineasLst = tDevLineasLst;
	}
		

	public boolean isFechaDeDevolucionPasada() {
		return fechaDeDevolucionPasada;
	}

	public void setFechaDeDevolucionPasada(boolean fechaDeDevolucionPasada) {
		this.fechaDeDevolucionPasada = fechaDeDevolucionPasada;
	}
	
	public List<String> getBultoPorProveedorLst() {
		return bultoPorProveedorLst;
	}

	public void setBultoPorProveedorLst(List<String> bultoPorProveedorLst) {
		this.bultoPorProveedorLst = bultoPorProveedorLst;
	}

	public List<Proveedor> getListaProv() {
		return listaProv;
	}

	public void setListaProv(List<Proveedor> listaProv) {
		this.listaProv = listaProv;
	}

	public String getTipoDevolucion() {
		return tipoDevolucion;
	}

	public void setTipoDevolucion(String tipoDevolucion) {
		this.tipoDevolucion = tipoDevolucion;
	}

	public int getBultoSeleccionado() {
		return bultoSeleccionado;
	}

	public void setBultoSeleccionado(int bultoSeleccionado) {
		this.bultoSeleccionado = bultoSeleccionado;
	}

	
	public String getHayRefsPdtes() {
		return hayRefsPdtes;
	}

	public void setHayRefsPdtes(String hayRefsPdtes) {
		this.hayRefsPdtes = hayRefsPdtes;
	}

	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (!(obj instanceof Devolucion)){
			return false;
		}
		Devolucion dev = (Devolucion) obj;
		return new EqualsBuilder().append(this.devolucion, dev.devolucion).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Devolucion [devolucion=");
		builder.append(devolucion);
		builder.append(", localizador=");
		builder.append(localizador);
		builder.append(", centro=");
		builder.append(centro);
		builder.append(", fechaDesde=");
		builder.append(fechaDesde);
		builder.append(", fechaHasta=");
		builder.append(fechaHasta);
		builder.append(", flgRecogida=");
		builder.append(flgRecogida);
		builder.append(", abono=");
		builder.append(abono);
		builder.append(", recogida=");
		builder.append(recogida);
		builder.append(", codPlataforma=");
		builder.append(codPlataforma);
		builder.append(", titulo1=");
		builder.append(titulo1);
		builder.append(", descripcion=");
		builder.append(descripcion);
		builder.append(", motivo=");
		builder.append(motivo);
		builder.append(", titulo2=");
		builder.append(titulo2);
		builder.append(", fechaPrecio=");
		builder.append(fechaPrecio);
		builder.append(", estadoCab=");
		builder.append(estadoCab);
		builder.append(", flgFueraFechas=");
		builder.append(flgFueraFechas);
		builder.append(", codError=");
		builder.append(codError);
		builder.append(", descError=");
		builder.append(descError);
		builder.append(", costeMaximo=");
		builder.append(costeMaximo);
		builder.append(", codRMA=");
		builder.append(codRMA);
		builder.append(", tipoRMA=");
		builder.append(tipoRMA);
		builder.append(", devLineas=");
		builder.append(devLineas);
		builder.append(", devLineasModificadas=");
		builder.append(devLineasModificadas);
		builder.append(", proveedor=");
		builder.append(proveedor);
		builder.append(", flagRefPermanentes=");
		builder.append(flagRefPermanentes);
		builder.append(", fechaDesdeStr=");
		builder.append(fechaDesdeStr);
		builder.append(", fechaHastaStr=");
		builder.append(fechaHastaStr);
		builder.append(", fechaPrecioStr=");
		builder.append(fechaPrecioStr);
		builder.append(", flagHistorico=");
		builder.append(flagHistorico);
		builder.append(", codArticulo=");
		builder.append(codArticulo);
		builder.append(", filtroProveedor=");
		builder.append(filtroProveedor);
		builder.append(", tipoPaginacionPDF=");
		builder.append(tipoPaginacionPDF);
		builder.append(", esOrdenRecogidaPDF=");
		builder.append(esOrdenRecogidaPDF);
		builder.append(", tDevLineasLst=");
		builder.append(tDevLineasLst);
		builder.append(", fechaDeDevolucionPasada=");
		builder.append(fechaDeDevolucionPasada);
		builder.append(", bultoPorProveedorLst=");
		builder.append(bultoPorProveedorLst);
		builder.append(", listaProv=");
		builder.append(listaProv);
		builder.append(", tipoDevolucion=");
		builder.append(tipoDevolucion);
		builder.append(", bultoSeleccionado=");
		builder.append(bultoSeleccionado);
		builder.append(", hayRefsPdtes=");
		builder.append(hayRefsPdtes);
		builder.append("]");
		return builder.toString();
	}

}