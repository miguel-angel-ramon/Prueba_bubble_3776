package es.eroski.misumi.model.pda;

import java.io.Serializable;

import es.eroski.misumi.util.Utilidades;

public class PdaUltimosMovStocks implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String fecha;
	private Float ventas;
	private Float corr1;
	private Float corr2;
	
	private String fila; //Para la paginación
	
	public PdaUltimosMovStocks() {
		super();
	}

	public PdaUltimosMovStocks(String fecha, Float ventas, Float corr1,
			Float corr2, String fila) {
		super();
		this.fecha = fecha;
		this.ventas = ventas;
		this.corr1 = corr1;
		this.corr2 = corr2;
		this.fila = fila;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public Float getVentas() {
		return this.ventas;
	}
	
	public void setVentas(Float ventas) {
		this.ventas = ventas;
	}
	
	public Float getCorr1() {
		return this.corr1;
	}
	
	public void setCorr1(Float corr1) {
		this.corr1 = corr1;
	}
	
	public Float getCorr2() {
		return this.corr2;
	}
	
	public void setCorr2(Float corr2) {
		this.corr2 = corr2;
	}
	
	public String getFila() {
		return this.fila;
	}
	
	public void setFila(String fila) {
		this.fila = fila;
	}

	public String getVentasConFormato() {
		return Utilidades.convertirDoubleAString(this.ventas.doubleValue(),"#####0.0");
	}
	
	public String getCorr1ConFormato() {
		return Utilidades.convertirDoubleAString(this.corr1.doubleValue(),"#####0.0");
	}
	
	public String getCorr2ConFormato() {
		return Utilidades.convertirDoubleAString(this.corr2.doubleValue(),"#####0.0");
	}
}
