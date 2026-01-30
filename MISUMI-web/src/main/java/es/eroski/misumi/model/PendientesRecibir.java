package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

public class PendientesRecibir implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long codArt;
	private Long cantHoy;
	private Long cantFutura;
	private String tipoAprov;
	  
	//Referencias para promocionales
	private List<Long> referencias;

	public PendientesRecibir(){
			super();
	}
	
    public PendientesRecibir(Long codCentro, Long codArt, Long cantHoy, Long cantFutura, String tipoAprov){
    	
    	super();
    	this.codCentro = codCentro;
    	this.codArt = codArt;
    	this.cantHoy = cantHoy;
    	this.cantFutura = cantFutura;
    	this.tipoAprov = tipoAprov;
    	
    }
    

    public PendientesRecibir(Long codCentro, Long codArt){

    	super();
    	this.codCentro = codCentro;
		this.codArt = codArt;
		this.cantHoy = null;
		this.cantFutura = null;
}
	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public Long getCantHoy() {
		return this.cantHoy;
	}

	public void setCantHoy(Long cantHoy) {
		this.cantHoy = cantHoy;
	}

	public Long getCantFutura() {
		return this.cantFutura;
	}

	public void setCantFutura(Long cantFutura) {
		this.cantFutura = cantFutura;
	}

	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}
    
	public List<Long> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Long> referencias) {
		this.referencias = referencias;
	}
}
