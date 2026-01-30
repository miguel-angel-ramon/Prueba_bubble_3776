package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.List;

import es.eroski.misumi.util.Constantes;

public class Centro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String descripCentro;
	private String negocio;
	private Long codEnsena;
	private Long codArea;
	private Long codRegion;
	private Long codZona;
	private String descripZona;
	private String provincia;
	private Long codNegocio;
	private Long codSoc;
	private boolean esCentroCaprabo;
	private boolean esCentroCapraboEspecial;
	private boolean esCentroCapraboNuevo;

	private Boolean lotesNavidadActivos;

	// Control de centro autoservicio
	private String flgCapacidad;
	private Float porcentajeCapacidad;

	// Control de opciones habilitadas
	private String controlOpciones;
	private String opcHabil;

	// Control de facing del centro
	private String flgFacing;

	// Control de existencia de avisos para el centro
	private int cuentaAvisosCentro; // Número de avisos a mostrar por centro
	private List<String> listaAvisosCentro; // Lista con los avisos a mostrar
											// por centro

	// Listado de informes para el centro
	private InformeListado informeListado;

	// Flag que indica si es centro caprabo
	private Long flgCpbEspecial;
	// Flag que indica si es centro caprabo Nuevo
	private Long flgCpbNuevo;
	/** Descripcion del boton a mostrar para SfmCapacidad */
	private String descripBotonSfmCapacidad;

	private DireccionCentro direccionCentro;

	// Lista de los estado de las diferentes estructuras comerciales de centros
	// parametrizados con Mostrador.
	private EstadoMostrador estadoEstructurasMostrador;

	
	private AreaFacingCero areaFacingCero;
	
	
	public Centro() {
		super();
	}

	public Centro (Long codCentro){
		super();
		this.codCentro = codCentro;
	}
	
	public Centro(Long cod, String des, String negocio, Long codEnsena, Long codArea, Long codRegion, Long codZona,
			String descripZona, String provincia, Long codNegocio, Long codSoc) {
		super();
		this.codCentro = cod;
		this.descripCentro = des;
		this.negocio = negocio;
		this.codEnsena = codEnsena;
		this.codArea = codArea;
		this.codRegion = codRegion;
		this.codZona = codZona;
		this.descripZona = descripZona;
		this.provincia = provincia;
		this.codNegocio = codNegocio;
		this.codSoc = codSoc;
		this.esCentroCaprabo = esCentroCaprabo();
	}

	public Centro(Long cod, String des, String negocio, Long codEnsena, Long codArea, Long codRegion, Long codZona,
			String descripZona, String provincia, Long codNegocio, Long codSoc, Long flgCpbEspecial, Long flgCpbNuevo) {
		super();
		this.codCentro = cod;
		this.descripCentro = des;
		this.negocio = negocio;
		this.codEnsena = codEnsena;
		this.codArea = codArea;
		this.codRegion = codRegion;
		this.codZona = codZona;
		this.descripZona = descripZona;
		this.provincia = provincia;
		this.codNegocio = codNegocio;
		this.codSoc = codSoc;
		this.flgCpbEspecial = flgCpbEspecial;
		this.flgCpbNuevo = flgCpbNuevo;
		this.esCentroCaprabo = esCentroCaprabo();
		this.esCentroCapraboEspecial = esCentroCapraboEspecial();
		this.esCentroCapraboNuevo = esCentroCapraboNuevo();
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getDescripCentro() {
		return this.descripCentro;
	}

	public void setDescripCentro(String descripCentro) {
		this.descripCentro = descripCentro;
	}

	public String getNegocio() {
		return this.negocio;
	}

	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}

	public Long getCodEnsena() {
		return codEnsena;
	}

	public void setCodEnsena(Long codEnsena) {
		this.codEnsena = codEnsena;
	}

	public Long getCodArea() {
		return codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Long getCodRegion() {
		return codRegion;
	}

	public void setCodRegion(Long codRegion) {
		this.codRegion = codRegion;
	}

	public Long getCodZona() {
		return codZona;
	}

	public void setCodZona(Long codZona) {
		this.codZona = codZona;
	}

	public String getDescripZona() {
		return descripZona;
	}

	public void setDescripZona(String descripZona) {
		this.descripZona = descripZona;
	}

	public String getFlgCapacidad() {
		return this.flgCapacidad;
	}

	public void setFlgCapacidad(String flgCapacidad) {
		this.flgCapacidad = flgCapacidad;
	}

	public Float getPorcentajeCapacidad() {
		return this.porcentajeCapacidad;
	}

	public void setPorcentajeCapacidad(Float porcentajeCapacidad) {
		this.porcentajeCapacidad = porcentajeCapacidad;
	}

	public String getControlOpciones() {
		return this.controlOpciones;
	}

	public void setControlOpciones(String controlOpciones) {
		this.controlOpciones = controlOpciones;
	}

	public String getOpcHabil() {
		return this.opcHabil;
	}

	public void setOpcHabil(String opcHabil) {
		this.opcHabil = opcHabil;
	}

	public String getFlgFacing() {
		return flgFacing;
	}

	public void setFlgFacing(String flgFacing) {
		this.flgFacing = flgFacing;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Long getCodNegocio() {
		return this.codNegocio;
	}

	public void setCodNegocio(Long codNegocio) {
		this.codNegocio = codNegocio;
	}

	public int getCuentaAvisosCentro() {
		return this.cuentaAvisosCentro;
	}

	public void setCuentaAvisosCentro(int cuentaAvisosCentro) {
		this.cuentaAvisosCentro = cuentaAvisosCentro;
	}

	public List<String> getListaAvisosCentro() {
		return this.listaAvisosCentro;
	}

	public void setListaAvisosCentro(List<String> listaAvisosCentro) {
		this.listaAvisosCentro = listaAvisosCentro;
	}

	public Boolean getLotesNavidadActivos() {
		return lotesNavidadActivos;
	}

	public void setLotesNavidadActivos(Boolean lotesNavidadActivos) {
		this.lotesNavidadActivos = lotesNavidadActivos;
	}

	public InformeListado getInformeListado() {
		return informeListado;
	}

	public void setInformeListado(InformeListado informeListado) {
		this.informeListado = informeListado;
	}

	public Long getCodSoc() {
		return codSoc;
	}

	public void setCodSoc(Long codSoc) {
		this.codSoc = codSoc;
	}

	public boolean esCentroCaprabo() {
		this.esCentroCaprabo = this.getCodSoc() != null && this.getFlgCpbEspecial() != null
				&& (Constantes.CODIGO_FLG_CAPRABO_ESPECIAL_NO).equals(this.getFlgCpbEspecial())
				&& Constantes.CODIGO_SOCIEDAD_CAPRABO.equals(this.getCodSoc());
		return esCentroCaprabo;
	}

	public boolean esCentroCapraboEspecial() {
		this.esCentroCapraboEspecial = this.getFlgCpbEspecial() != null
				&& (Constantes.CODIGO_FLG_CAPRABO_ESPECIAL_SI).equals(this.getFlgCpbEspecial());
		return esCentroCapraboEspecial;
	}

	public boolean esCentroCapraboNuevo() {
		this.esCentroCapraboNuevo = this.getFlgCpbNuevo() != null
				&& (Constantes.CODIGO_FLG_CAPRABO_NUEVO_SI).equals(this.getFlgCpbNuevo());
		return esCentroCapraboNuevo;
	}

	public boolean getEsCentroCaprabo() {
		return this.esCentroCaprabo();
	}

	public void setEsCentroCaprabo(boolean esCentroCaprabo) {
		this.esCentroCaprabo = esCentroCaprabo;
	}

	public boolean isEsCentroCapraboEspecial() {
		return esCentroCapraboEspecial;
	}

	public void setEsCentroCapraboEspecial(boolean esCentroCapraboEspecial) {
		this.esCentroCapraboEspecial = esCentroCapraboEspecial;
	}

	public boolean isEsCentroCapraboNuevo() {
		return esCentroCapraboNuevo;
	}

	public void setEsCentroCapraboNuevo(boolean esCentroCapraboNuevo) {
		this.esCentroCapraboNuevo = esCentroCapraboNuevo;
	}

	public Long getFlgCpbEspecial() {
		return flgCpbEspecial;
	}

	public void setFlgCpbEspecial(Long flgCpbEspecial) {
		this.flgCpbEspecial = flgCpbEspecial;
	}

	public Long getFlgCpbNuevo() {
		return flgCpbNuevo;
	}

	public void setFlgCpbNuevo(Long flgCpbNuevo) {
		this.flgCpbNuevo = flgCpbNuevo;
	}

	public DireccionCentro getDireccionCentro() {
		return direccionCentro;
	}

	public void setDireccionCentro(DireccionCentro direccionCentro) {
		this.direccionCentro = direccionCentro;
	}

	public String getDescripBotonSfmCapacidad() {
		return descripBotonSfmCapacidad;
	}

	public void setDescripBotonSfmCapacidad(String descripBotonSfmCapacidad) {
		this.descripBotonSfmCapacidad = descripBotonSfmCapacidad;
	}

	public EstadoMostrador getEstadoEstructurasMostrador() {
		return estadoEstructurasMostrador;
	}

	public void setEstadoEstructurasMostrador(EstadoMostrador estadoEstructurasMostrador) {
		this.estadoEstructurasMostrador = estadoEstructurasMostrador;
	}

	public AreaFacingCero getAreaFacingCero() {
		return areaFacingCero;
	}

	public void setAreaFacingCero(AreaFacingCero areaFacingCero) {
		this.areaFacingCero = areaFacingCero;
	}
	
}