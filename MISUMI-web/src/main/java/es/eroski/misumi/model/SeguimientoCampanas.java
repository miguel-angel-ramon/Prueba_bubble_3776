package es.eroski.misumi.model;

import java.io.Serializable;

public class SeguimientoCampanas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String descripcionNivel;
	private String servidoPendiente;
	private String noServido;
	private String ventasPrevision;
	
	//Calculados para llamadas a popups
	private String campana;
	private String oferta;
	private String tipoOC;
	private String fechaInicioDDMMYYYY;
	private String fechaInicioPantalla;
	private String fechaFinDDMMYYYY;
	private String fechaFinPantalla;
	private Long codArea; 
	private String descArea;
	private Long codSeccion;
	private String descSeccion;
	private Long codCategoria;
	private String descCategoria;
	private Long codSubcategoria;
	private String descSubcategoria;
	private Long codSegmento;
	private String descSegmento;
    private Long codArt;
    private String descCodArt;

	//Campos del árbol
	private int level;
	private String parent;
	private boolean isLeaf;
	private boolean expanded;
	private boolean loaded;

	private Long codCentro;
	
	public SeguimientoCampanas(){
		super();
	}

	public SeguimientoCampanas(String id, String descripcionNivel,
			String servidoPendiente, String noServido, String ventasPrevision,
			String campana, String oferta, String tipoOC, String fechaInicioDDMMYYYY, String fechaInicioPantalla,
			String fechaFinDDMMYYYY, String fechaFinPantalla, Long codArea, String descArea,
			Long codSeccion, String descSeccion, Long codCategoria,
			String descCategoria, Long codSubcategoria,
			String descSubcategoria, Long codSegmento, String descSegmento, Long codArt, String descCodArt,
			int level, String parent, boolean isLeaf, boolean expanded,
			boolean loaded, Long codCentro) {
		super();
		this.id = id;
		this.descripcionNivel = descripcionNivel;
		this.servidoPendiente = servidoPendiente;
		this.noServido = noServido;
		this.ventasPrevision = ventasPrevision;
		this.campana = campana;
		this.oferta = oferta;
		this.tipoOC = tipoOC;
		this.fechaInicioDDMMYYYY = fechaInicioDDMMYYYY;
		this.fechaInicioPantalla = fechaInicioPantalla;
		this.fechaFinDDMMYYYY = fechaFinDDMMYYYY;
		this.fechaFinPantalla = fechaFinPantalla;
		this.codArea = codArea;
		this.descArea = descArea;
		this.codSeccion = codSeccion;
		this.descSeccion = descSeccion;
		this.codCategoria = codCategoria;
		this.descCategoria = descCategoria;
		this.codSubcategoria = codSubcategoria;
		this.descSubcategoria = descSubcategoria;
		this.codSegmento = codSegmento;
		this.descSegmento = descSegmento;
		this.codArt = codArt;
		this.descCodArt = descCodArt;
		this.level = level;
		this.parent = parent;
		this.isLeaf = isLeaf;
		this.expanded = expanded;
		this.loaded = loaded;
		this.codCentro = codCentro;
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

	public String getServidoPendiente() {
		return this.servidoPendiente;
	}

	public void setServidoPendiente(String servidoPendiente) {
		this.servidoPendiente = servidoPendiente;
	}

	public String getNoServido() {
		return this.noServido;
	}

	public void setNoServido(String noServido) {
		this.noServido = noServido;
	}

	public String getVentasPrevision() {
		return this.ventasPrevision;
	}

	public void setVentasPrevision(String ventasPrevision) {
		this.ventasPrevision = ventasPrevision;
	}

	public String getCampana() {
		return this.campana;
	}

	public void setCampana(String campana) {
		this.campana = campana;
	}

	public String getOferta() {
		return this.oferta;
	}

	public void setOferta(String oferta) {
		this.oferta = oferta;
	}

	public String getTipoOC() {
		return tipoOC;
	}

	public void setTipoOC(String tipoOC) {
		this.tipoOC = tipoOC;
	}

	public String getFechaInicioDDMMYYYY() {
		return this.fechaInicioDDMMYYYY;
	}

	public void setFechaInicioDDMMYYYY(String fechaInicioDDMMYYYY) {
		this.fechaInicioDDMMYYYY = fechaInicioDDMMYYYY;
	}

	public String getFechaInicioPantalla() {
		return this.fechaInicioPantalla;
	}

	public void setFechaInicioPantalla(String fechaInicioPantalla) {
		this.fechaInicioPantalla = fechaInicioPantalla;
	}

	public String getFechaFinDDMMYYYY() {
		return this.fechaFinDDMMYYYY;
	}

	public void setFechaFinDDMMYYYY(String fechaFinDDMMYYYY) {
		this.fechaFinDDMMYYYY = fechaFinDDMMYYYY;
	}

	public String getFechaFinPantalla() {
		return this.fechaFinPantalla;
	}

	public void setFechaFinPantalla(String fechaFinPantalla) {
		this.fechaFinPantalla = fechaFinPantalla;
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

	public Long getCodSubcategoria() {
		return this.codSubcategoria;
	}

	public void setCodSubcategoria(Long codSubcategoria) {
		this.codSubcategoria = codSubcategoria;
	}

	public String getDescSubcategoria() {
		return this.descSubcategoria;
	}

	public void setDescSubcategoria(String descSubcategoria) {
		this.descSubcategoria = descSubcategoria;
	}

	public Long getCodSegmento() {
		return this.codSegmento;
	}

	public void setCodSegmento(Long codSegmento) {
		this.codSegmento = codSegmento;
	}

	public String getDescSegmento() {
		return this.descSegmento;
	}

	public void setDescSegmento(String descSegmento) {
		this.descSegmento = descSegmento;
	}

	public Long getCodArt() {
		return this.codArt;
	}

	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}

	public String getDescCodArt() {
		return this.descCodArt;
	}

	public void setDescCodArt(String descCodArt) {
		this.descCodArt = descCodArt;
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

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

}
