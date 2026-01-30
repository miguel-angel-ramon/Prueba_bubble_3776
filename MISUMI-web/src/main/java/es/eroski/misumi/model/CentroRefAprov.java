package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

public class CentroRefAprov implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private Long codCentro;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private Date fecha;
	private String ambito;
	
	public Long getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getAmbito() {
		return ambito;
	}
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}
	
	public Integer getNivel(){
		Integer nivel = null;
		if (null != this.getGrupo1()){
        	 if (null != this.getGrupo2()){
	            	if (null != this.getGrupo3()){
	            		if (null != this.getGrupo4()){
	            			if (null != this.getGrupo5()){
	            				nivel = 5;
	            			} else {
	            				nivel = 4;
	            			}
	            		} else {
	            			nivel = 3;
	            		}
 	            	
	            	} else {
	            		nivel = 2;
	            	}
	          } else {
	        	  nivel = 1;
	          }
        } else {
        	nivel = 0;
        }
		
		return nivel;
	}
	

}
