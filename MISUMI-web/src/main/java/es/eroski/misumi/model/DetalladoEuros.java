package es.eroski.misumi.model;

import java.io.Serializable;

public class DetalladoEuros implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long nivel;
	private Long ident;
	private Long parentident;
	private Long cod_centro;
	private String idsesion;
	private Long grupo1;
	private Long grupo2;
	private Long grupo3;
	private Long grupo4;
	private Long grupo5;
	private String descripcion;
	private Long precioCostoInicial;
	private Long cajasInicial;
	private Long precioCostoFinal;
	private Long cajasFinales;
	private Long codArt;
	private String tipo;
	private String fecha;
	private String area;
	private String seccion;
	private String categoria;
	private String subcategoria;
	private String segmento;

	//Campos del árbol
	private int level;
	private String parent;
	private boolean isLeaf;
	private boolean expanded;
	private boolean loaded;

	private String estado;
	
	public DetalladoEuros(Long id, Long nivel, Long ident, Long parentident, Long cod_centro, String idsesion, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5, String descripcion, Long precioCostoInicial, Long cajasInicial,
			Long precioCostoFinal, Long cajasFinales, Long codArt, String tipo) {
		super();
		this.id = id;
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.cod_centro = cod_centro;
		this.idsesion = idsesion;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.precioCostoInicial = precioCostoInicial;
		this.cajasInicial = cajasInicial;
		this.precioCostoFinal = precioCostoFinal;
		this.cajasFinales = cajasFinales;
		this.codArt = codArt;
		this.tipo = tipo;
	}
	
	public DetalladoEuros(Long id, Long nivel, Long ident, Long parentident, Long cod_centro, String idsesion, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5, String descripcion, Long precioCostoInicial, Long cajasInicial,
			Long precioCostoFinal, Long cajasFinales, Long codArt, String tipo, int level, String parent,
			boolean isLeaf, boolean expanded, boolean loaded, String fecha, String area, String seccion, String categoria, String subcategoria, String segmento) {
		super();
		this.id = id;
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.cod_centro = cod_centro;
		this.idsesion = idsesion;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.precioCostoInicial = precioCostoInicial;
		this.cajasInicial = cajasInicial;
		this.precioCostoFinal = precioCostoFinal;
		this.cajasFinales = cajasFinales;
		this.codArt = codArt;
		this.tipo = tipo;
		this.level = level;
		this.parent = parent;
		this.isLeaf = isLeaf;
		this.expanded = expanded;
		this.loaded = loaded;
		this.fecha = fecha;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
	}

	public DetalladoEuros(Long id, Long nivel, Long ident, Long parentident, Long cod_centro, String idsesion, Long grupo1,
			Long grupo2, Long grupo3, Long grupo4, Long grupo5, String descripcion, Long precioCostoInicial, Long cajasInicial,
			Long precioCostoFinal, Long cajasFinales, Long codArt, String tipo, int level, String parent,
			boolean isLeaf, boolean expanded, boolean loaded, String fecha, String area, String seccion, String categoria, String subcategoria, String segmento, String estado) {
		super();
		this.id = id;
		this.nivel = nivel;
		this.ident = ident;
		this.parentident = parentident;
		this.cod_centro = cod_centro;
		this.idsesion = idsesion;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.grupo3 = grupo3;
		this.grupo4 = grupo4;
		this.grupo5 = grupo5;
		this.descripcion = descripcion;
		this.precioCostoInicial = precioCostoInicial;
		this.cajasInicial = cajasInicial;
		this.precioCostoFinal = precioCostoFinal;
		this.cajasFinales = cajasFinales;
		this.codArt = codArt;
		this.tipo = tipo;
		this.level = level;
		this.parent = parent;
		this.isLeaf = isLeaf;
		this.expanded = expanded;
		this.loaded = loaded;
		this.fecha = fecha;
		this.area = area;
		this.seccion = seccion;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.segmento = segmento;
		this.estado = estado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNivel() {
		return nivel;
	}
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	public Long getIdent() {
		return ident;
	}
	public void setIdent(Long ident) {
		this.ident = ident;
	}
	public Long getParentident() {
		return parentident;
	}
	public void setParentident(Long parentident) {
		this.parentident = parentident;
	}
	public Long getCod_centro() {
		return cod_centro;
	}
	public void setCod_centro(Long cod_centro) {
		this.cod_centro = cod_centro;
	}
	public String getIdsesion() {
		return idsesion;
	}
	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getPrecioCostoInicial() {
		return precioCostoInicial;
	}
	public void setPrecioCostoInicial(Long precioCostoInicial) {
		this.precioCostoInicial = precioCostoInicial;
	}
	public Long getCajasInicial() {
		return cajasInicial;
	}
	public void setCajasInicial(Long cajasInicial) {
		this.cajasInicial = cajasInicial;
	}
	public Long getPrecioCostoFinal() {
		return precioCostoFinal;
	}
	public void setPrecioCostoFinal(Long precioCostoFinal) {
		this.precioCostoFinal = precioCostoFinal;
	}
	public Long getCajasFinales() {
		return cajasFinales;
	}
	public void setCajasFinales(Long cajasFinales) {
		this.cajasFinales = cajasFinales;
	}
	public Long getCodArt() {
		return codArt;
	}
	public void setCodArt(Long codArt) {
		this.codArt = codArt;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
