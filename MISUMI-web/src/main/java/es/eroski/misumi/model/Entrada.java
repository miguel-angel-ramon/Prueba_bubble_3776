package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import es.eroski.misumi.util.Utilidades;

public class Entrada implements Serializable{
	private static final long serialVersionUID = 1L;

	//Datos entrada procedimientos
	private Long codLoc;
	private Date fechaEntrada;
	private String fechaEntradaStr;
	private Long codCabPedido;
	private Long codProvGen;
	private Long codProvTrab;
	private String denomProvTrab;
	private String codAlbProv;
	private Long numIncidencia;
	private Date fechaTarifa;
	private String fechaTarifaStr;
	private Long tipoRecepcion;
	private List<EntradaLinea> lstEntradaLinea;

	//Atributo creado para guardar las líneas modificadas del grid
	private List<EntradaLineaModificada> lstModificados;

	public Entrada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Entrada(Long codLoc, Date fechaEntrada, Long codCabPedido, Long codProvGen,
			Long codProvTrab, String denomProvTrab, String codAlbProv, Long numIncidencia, Date fechaTarifa,
			Long tipoRecepcion, List<EntradaLinea> lstEntradaLinea,
			List<EntradaLineaModificada> lstModificados) {
		super();
		this.codLoc = codLoc;
		this.fechaEntrada = fechaEntrada;
		this.fechaEntradaStr = (fechaEntrada != null ? Utilidades.formatearFecha_ddMMyyyyBarra(fechaEntrada):null);
		this.codCabPedido = codCabPedido;
		this.codProvGen = codProvGen;
		this.codProvTrab = codProvTrab;
		this.denomProvTrab = denomProvTrab;
		this.codAlbProv = codAlbProv;
		this.numIncidencia = numIncidencia;
		this.fechaTarifa = fechaTarifa;
		this.fechaTarifaStr = (fechaTarifa != null ? Utilidades.formatearFecha_ddMMyyyyBarra(fechaTarifa):null);
		this.tipoRecepcion = tipoRecepcion;
		this.lstEntradaLinea = lstEntradaLinea;
		this.lstModificados = lstModificados;
	}



	public Entrada(Long codLoc, Date fechaEntrada, Long codCabPedido, Long codProvGen, Long codProvTrab,
			String denomProvTrab, String codAlbProv, Long numIncidencia, Date fechaTarifa, 
			Long tipoRecepcion, List<EntradaLinea> lstEntradaLinea) {
		this(codLoc,fechaEntrada,codCabPedido,codProvGen,codProvTrab,denomProvTrab,codAlbProv,numIncidencia,
				fechaTarifa,tipoRecepcion,lstEntradaLinea,null);
	}

	//Datos entrada procedimientos
	public Long getCodLoc() {
		return codLoc;
	}
	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntradaStr = (fechaEntrada != null ? Utilidades.formatearFecha_ddMMyyyyBarra(fechaEntrada):null);
		this.fechaEntrada = fechaEntrada;
	}
	public String getFechaEntradaStr() {
		return fechaEntradaStr;
	}
	public Long getCodCabPedido() {
		return codCabPedido;
	}
	public void setCodCabPedido(Long codCabPedido) {
		this.codCabPedido = codCabPedido;
	}
	public Long getCodProvGen() {
		return codProvGen;
	}
	public void setCodProvGen(Long codProvGen) {
		this.codProvGen = codProvGen;
	}
	public Long getCodProvTrab() {
		return codProvTrab;
	}
	public void setCodProvTrab(Long codProvTrab) {
		this.codProvTrab = codProvTrab;
	}
	public String getDenomProvTrab() {
		return denomProvTrab;
	}
	public void setDenomProvTrab(String denomProvTrab) {
		this.denomProvTrab = denomProvTrab;
	}
	public String getCodAlbProv() {
		return codAlbProv;
	}
	public void setCodAlbProv(String codAlbProv) {
		this.codAlbProv = codAlbProv;
	}
	public Long getNumIncidencia() {
		return numIncidencia;
	}
	public void setNumIncidencia(Long numIncidencia) {
		this.numIncidencia = numIncidencia;
	}
	public Date getFechaTarifa() {
		return fechaTarifa;
	}
	public void setFechaTarifa(Date fechaTarifa) {
		this.fechaTarifaStr = (fechaTarifa != null ? Utilidades.formatearFecha_ddMMyyyyBarra(fechaTarifa):null);
		this.fechaTarifa = fechaTarifa;
	}
	public String getFechaTarifaStr() {
		return fechaTarifaStr;
	}
	public Long getTipoRecepcion() {
		return tipoRecepcion;
	}
	public void setTipoRecepcion(Long tipoRecepcion) {
		this.tipoRecepcion = tipoRecepcion;
	}
	public List<EntradaLinea> getLstEntradaLinea() {
		return lstEntradaLinea;
	}
	public void setLstEntradaLinea(List<EntradaLinea> lstEntradaLinea) {
		this.lstEntradaLinea = lstEntradaLinea;
	}
	//Atributo creado para guardar las líneas modificadas del grid
	public List<EntradaLineaModificada> getLstModificados() {
		return lstModificados;
	}
	public void setLstModificados(List<EntradaLineaModificada> lstModificados) {
		this.lstModificados = lstModificados;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entrada [codLoc=");
		builder.append(codLoc);
		builder.append(", fechaEntrada=");
		builder.append(fechaEntrada);
		builder.append(", fechaEntradaStr=");
		builder.append(fechaEntradaStr);
		builder.append(", codCabPedido=");
		builder.append(codCabPedido);
		builder.append(", codProvGen=");
		builder.append(codProvGen);
		builder.append(", codProvTrab=");
		builder.append(codProvTrab);
		builder.append(", denomProvTrab=");
		builder.append(denomProvTrab);
		builder.append(", codAlbProv=");
		builder.append(codAlbProv);
		builder.append(", numIncidencia=");
		builder.append(numIncidencia);
		builder.append(", fechaTarifa=");
		builder.append(fechaTarifa);
		builder.append(", fechaTarifaStr=");
		builder.append(fechaTarifaStr);
		builder.append(", tipoRecepcion=");
		builder.append(tipoRecepcion);
		builder.append(", lstEntradaLinea=");
		builder.append(lstEntradaLinea);
		builder.append(", lstLinModificadas=");
		builder.append(lstModificados);
		builder.append("]");
		return builder.toString();
	}


}
