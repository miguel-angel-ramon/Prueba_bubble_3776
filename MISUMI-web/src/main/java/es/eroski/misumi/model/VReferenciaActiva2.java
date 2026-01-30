package es.eroski.misumi.model;

import java.io.Serializable;

public class VReferenciaActiva2 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String activa;
	private Integer mon;
	private Integer tue;
	private Integer wed;
	private Integer thu;
	private Integer fri;
	private Integer sat;
	private Integer sun;
	
	public String getActiva() {
		return activa;
	}
	public void setActiva(String activa) {
		this.activa = activa;
	}
	public Integer getMon() {
		return mon;
	}
	public void setMon(Integer mon) {
		this.mon = mon;
	}
	public Integer getTue() {
		return tue;
	}
	public void setTue(Integer tue) {
		this.tue = tue;
	}
	public Integer getWed() {
		return wed;
	}
	public void setWed(Integer wed) {
		this.wed = wed;
	}
	public Integer getThu() {
		return thu;
	}
	public void setThu(Integer thu) {
		this.thu = thu;
	}
	public Integer getFri() {
		return fri;
	}
	public void setFri(Integer fri) {
		this.fri = fri;
	}
	public Integer getSat() {
		return sat;
	}
	public void setSat(Integer sat) {
		this.sat = sat;
	}
	public Integer getSun() {
		return sun;
	}
	public void setSun(Integer sun) {
		this.sun = sun;
	}
	
	

}
