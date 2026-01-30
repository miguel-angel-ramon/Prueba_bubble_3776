package es.eroski.misumi.model.pda.prehuecos;

import java.sql.Date;

import es.eroski.misumi.util.Constantes;

/**
 * Modelo de la tabla T_MIS_PREHUECOS_LINEAL.
 * 
 * @author BICAGAAN
 *
 */
public class PrehuecosLineal {

	private Long codCentro;
	private String mac;
	private Long codArticulo;
	private Long stockLineal;
	private String estado;
	private Date fecValidado;
	private Long numPrehuecos;
	private Long estadoRef;
	
	public PrehuecosLineal() {
		super();
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
//		this.mac = mac+Constantes.PREHUECOS;
		this.mac = mac;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public Long getStockLineal() {
		return stockLineal;
	}

	public void setEstadoRef(Long estadoRef) {
		this.estadoRef = estadoRef;
	}

	public Long getEstadoRef() {
		return estadoRef;
	}

	public void setStockLineal(Long stockLineal) {
		this.stockLineal = stockLineal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecValidado() {
		return fecValidado;
	}

	public void setFecValidado(Date fecValidado) {
		this.fecValidado = fecValidado;
	}
	
	public Long getNumPrehuecos() {
		return numPrehuecos;
	}

	public void setNumPrehuecos(Long numPrehuecos) {
		this.numPrehuecos = numPrehuecos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PrehuecosLineal [codCentro=");
		builder.append(codCentro);
		builder.append(", mac=");
		builder.append(mac);
		builder.append(", codArticulo=");
		builder.append(codArticulo);
		builder.append(", stockLineal=");
		builder.append(stockLineal);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", fecValidado=");
		builder.append(fecValidado);
		builder.append(", numPrehuecos=");
		builder.append(numPrehuecos);
		builder.append("]");
		return builder.toString();
	}

}
