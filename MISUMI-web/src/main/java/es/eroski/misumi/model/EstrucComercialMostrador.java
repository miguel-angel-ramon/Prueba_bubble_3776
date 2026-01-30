package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;


public class EstrucComercialMostrador implements Serializable {

	private static final long serialVersionUID = 7041614946888955219L;

	private List<OptionSelectBean> lstOpciones; 
	private String fechaEspejo;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<OptionSelectBean> getLstOpciones() {
		return lstOpciones;
	}
	public void setLstOpciones(List<OptionSelectBean> lstOpciones) {
		this.lstOpciones = lstOpciones;
	}
	public String getFechaEspejo() {
		return fechaEspejo;
	}
	public void setFechaEspejo(String fechaEspejo) {
		this.fechaEspejo = fechaEspejo;
	}
	

}