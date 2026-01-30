package es.eroski.misumi.model;

import java.io.Serializable;

public class ReferenciasPedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String descripcionNivel;
	private String hoy;
	private String nsr;
	private String confirm;
	private String gisae;
	private int cajasCortadas;
	
	//Calculados para llamadas a popups
	private String fechaPrevisEntDDMMYYYY;
	private Long codArea; 
	private String descArea;
	private Long codSeccion;
	private String descSeccion;
	private Long codCategoria;
	private String descCategoria;

	private String mapa;
	private String descMapa;
	
	//Campos del �rbol
	private int level;
	private String parent;
	private boolean isLeaf;
	private boolean expanded;
	private boolean loaded;

	public ReferenciasPedido(){
		super();
	}
	
	public ReferenciasPedido(String id, String descripcionNivel, String hoy,
			String nsr, String confirm, String gisae, 
			String fechaPrevisEntDDMMYYYY, Long codArea, String descArea,
			Long codSeccion, String descSeccion, Long codCategoria,	String descCategoria,
			int level, String parent,
			boolean isLeaf, boolean expanded, boolean loaded, String mapa, String descMapa, int cajasCortadas) {
		super();
		this.id = id;
		this.descripcionNivel = descripcionNivel;
		this.hoy = hoy;
		this.nsr = nsr;
		this.confirm = confirm;
		this.gisae = gisae;
		
		this.fechaPrevisEntDDMMYYYY = fechaPrevisEntDDMMYYYY;
		this.codArea = codArea;
		this.descArea = descArea;
		this.codSeccion = codSeccion;
		this.descSeccion = descSeccion;
		this.codCategoria = codCategoria;
		this.descCategoria = descCategoria;
		
		this.level = level;
		this.parent = parent;
		this.isLeaf = isLeaf;
		this.expanded = expanded;
		this.loaded = loaded;
		
		this.mapa=mapa;
		this.descMapa=descMapa;
		this.cajasCortadas = cajasCortadas;
	}		
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescripcionNivel() {
		return this.descripcionNivel;
	}
	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}
	public String getHoy() {
		return this.hoy;
	}
	public void setHoy(String hoy) {
		this.hoy = hoy;
	}
	public String getNsr() {
		return this.nsr;
	}
	public void setNsr(String nsr) {
		this.nsr = nsr;
	}
	public String getConfirm() {
		return this.confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getGisae() {
		return this.gisae;
	}
	public void setGisae(String gisae) {
		this.gisae = gisae;
	}
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getParent() {
		return this.parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public boolean isLeaf() {
		return this.isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isExpanded() {
		return this.expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isLoaded() {
		return this.loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public String getFechaPrevisEntDDMMYYYY() {
		return this.fechaPrevisEntDDMMYYYY;
	}
	public void setFechaPrevisEntDDMMYYYY(String fechaPrevisEntDDMMYYYY) {
		this.fechaPrevisEntDDMMYYYY = fechaPrevisEntDDMMYYYY;
	}
	public Long getCodArea() {
		return this.codArea;
	}
	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}
	public String getDescArea() {
		return this.descArea;
	}
	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}
	public Long getCodSeccion() {
		return this.codSeccion;
	}
	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}
	public String getDescSeccion() {
		return this.descSeccion;
	}
	public void setDescSeccion(String descSeccion) {
		this.descSeccion = descSeccion;
	}
	public Long getCodCategoria() {
		return this.codCategoria;
	}
	public void setCodCategoria(Long codCategoria) {
		this.codCategoria = codCategoria;
	}
	public String getDescCategoria() {
		return this.descCategoria;
	}
	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public String getDescMapa() {
		return descMapa;
	}

	public void setDescMapa(String descMapa) {
		this.descMapa = descMapa;
	}

	public int getCajasCortadas() {
		return cajasCortadas;
	}

	public void setCajasCortadas(int cajasCortadas) {
		this.cajasCortadas = cajasCortadas;
	}

}
