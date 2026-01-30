package es.eroski.misumi.model;

import java.io.Serializable;

import es.eroski.misumi.model.VMisCampanaOfer;

public class TreeGridCampana implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nodeid ;
	private String parentid ;
	private int n_level ;
	private VMisCampanaOfer datosCampana;
	
	
	public String getNodeid() {
		return this.nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	public String getParentid() {
		return this.parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public int getN_level() {
		return this.n_level;
	}
	public void setN_level(int n_level) {
		this.n_level = n_level;
	}
	
	public VMisCampanaOfer getDatosCampana() {
		return this.datosCampana;
	}
	public void setDatosCampana(VMisCampanaOfer datosCampana) {
		this.datosCampana = datosCampana;
	}
	
	
	
}
