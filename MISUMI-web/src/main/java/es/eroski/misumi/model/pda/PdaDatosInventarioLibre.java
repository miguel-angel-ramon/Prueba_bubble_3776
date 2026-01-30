package es.eroski.misumi.model.pda;

import java.io.Serializable;
import java.util.Date;

import es.eroski.misumi.util.Constantes;

public class PdaDatosInventarioLibre implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private String codMac;
	private Long codArea;
	private Long codSeccion;
	private Long codArticulo;
	private String flgUnica;
	private Long codArticuloRela;
	private String flgStockPrincipal;
	private String flgNoGuardar;
	private String camaraStock;
	private String camaraBandeja;
	private String salaStock;
	private String salaBandeja;
	private Long proporRefCompra;
	private Long proporRefVenta;
	private String flgVariasUnitarias;
	private String aviso;
	private String error;
	private String createdBy;
	private Date creationDate;
	private Long tecleCreation;
	private String lastUpdatedBy;
	private Date lastUpdateDate;
	private Long lastUpdateLogin;
	private Long tecle;
	private Long tcn;
	
	private String descArt;
	private String descArtConCodigo; //Utilizado para formatear la descripción de pantalla
	private String mmc;
	
	//Posición en pantalla
	private int posicion;
	private int total;
	
	//Descripciones de stock actual y diferencia
	private String descStockActual; //Utilizado para formatear la descripción de stock de pantalla
	private String descDiferencia; //Utilizado para formatear la diferencia de pantalla
	
	private Double stockActual;
	private Double diferencia;
	
	//Totales
	private String totalStock;
	private String totalBandeja;

	//Errores de validación
	private String esErrorBandejas;
	private String esErrorStock;
	
	//Check de no guardar
	private boolean chkNoGuardar;
	
	private String origenGISAE;
	
	//Kgs por bandeja
	private String kgs;
	
	//Tipo rotación
	private String tipoRotacion;
	
	public PdaDatosInventarioLibre() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PdaDatosInventarioLibre(Long codCentro, String codMac, Long codArea,
			Long codSeccion, Long codArticulo, String flgUnica,
			Long codArticuloRela, String flgStockPrincipal,
			String flgNoGuardar, String camaraStock, String camaraBandeja,
			String salaStock, String salaBandeja, Long proporRefCompra,
			Long proporRefVenta, String flgVariasUnitarias, String aviso,
			String error, String createdBy, Date creationDate,
			Long tecleCreation, String lastUpdatedBy, Date lastUpdateDate,
			Long lastUpdateLogin, Long tecle, Long tcn) {
		super();
		this.codCentro = codCentro;
		this.codMac = codMac;
		this.codArea = codArea;
		this.codSeccion = codSeccion;
		this.codArticulo = codArticulo;
		this.flgUnica = flgUnica;
		this.codArticuloRela = codArticuloRela;
		this.flgStockPrincipal = flgStockPrincipal;
		this.flgNoGuardar = flgNoGuardar;
		this.camaraStock = camaraStock;
		this.camaraBandeja = camaraBandeja;
		this.salaStock = salaStock;
		this.salaBandeja = salaBandeja;
		this.proporRefCompra = proporRefCompra;
		this.proporRefVenta = proporRefVenta;
		this.flgVariasUnitarias = flgVariasUnitarias;
		this.aviso = aviso;
		this.error = error;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.tecleCreation = tecleCreation;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateLogin = lastUpdateLogin;
		this.tecle = tecle;
		this.tcn = tcn;
	}

	public Long getCodCentro() {
		return this.codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public String getCodMac() {
		return this.codMac;
	}

	public void setCodMac(String codMac) {
		this.codMac = codMac;
	}

	public Long getCodArea() {
		return this.codArea;
	}

	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}

	public Long getCodSeccion() {
		return this.codSeccion;
	}

	public void setCodSeccion(Long codSeccion) {
		this.codSeccion = codSeccion;
	}

	public Long getCodArticulo() {
		return this.codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getFlgUnica() {
		return this.flgUnica;
	}

	public void setFlgUnica(String flgUnica) {
		this.flgUnica = flgUnica;
	}

	public Long getCodArticuloRela() {
		return this.codArticuloRela;
	}

	public void setCodArticuloRela(Long codArticuloRela) {
		this.codArticuloRela = codArticuloRela;
	}

	public String getFlgStockPrincipal() {
		return this.flgStockPrincipal;
	}

	public void setFlgStockPrincipal(String flgStockPrincipal) {
		this.flgStockPrincipal = flgStockPrincipal;
	}

	public String getFlgNoGuardar() {
		return this.flgNoGuardar;
	}

	public void setFlgNoGuardar(String flgNoGuardar) {
		this.flgNoGuardar = flgNoGuardar;
	}

	public String getCamaraStock() {
		return this.camaraStock;
	}

	public void setCamaraStock(String camaraStock) {
		this.camaraStock = camaraStock;
	}

	public String getCamaraBandeja() {
		return this.camaraBandeja;
	}

	public void setCamaraBandeja(String camaraBandeja) {
		this.camaraBandeja = camaraBandeja;
	}

	public String getSalaStock() {
		return this.salaStock;
	}

	public void setSalaStock(String salaStock) {
		this.salaStock = salaStock;
	}

	public String getSalaBandeja() {
		return this.salaBandeja;
	}

	public void setSalaBandeja(String salaBandeja) {
		this.salaBandeja = salaBandeja;
	}

	public Long getProporRefCompra() {
		return this.proporRefCompra;
	}

	public void setProporRefCompra(Long proporRefCompra) {
		this.proporRefCompra = proporRefCompra;
	}

	public Long getProporRefVenta() {
		return this.proporRefVenta;
	}

	public void setProporRefVenta(Long proporRefVenta) {
		this.proporRefVenta = proporRefVenta;
	}

	public String getFlgVariasUnitarias() {
		return this.flgVariasUnitarias;
	}

	public void setFlgVariasUnitarias(String flgVariasUnitarias) {
		this.flgVariasUnitarias = flgVariasUnitarias;
	}

	public String getAviso() {
		return this.aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getTecleCreation() {
		return this.tecleCreation;
	}

	public void setTecleCreation(Long tecleCreation) {
		this.tecleCreation = tecleCreation;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateLogin() {
		return this.lastUpdateLogin;
	}

	public void setLastUpdateLogin(Long lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public Long getTecle() {
		return this.tecle;
	}

	public void setTecle(Long tecle) {
		this.tecle = tecle;
	}

	public Long getTcn() {
		return this.tcn;
	}

	public void setTcn(Long tcn) {
		this.tcn = tcn;
	}
	
	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getDescArt() {
		return this.descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public String getDescArtConCodigo() {
		return this.codArticulo + "-" + this.descArt;
	}

	public void setDescArtConCodigo(String descArtConCodigo) {
		this.descArtConCodigo = descArtConCodigo;
	}

	public String getDescStockActual() {
		return this.descStockActual;
	}

	public void setDescStockActual(String descStockActual) {
		this.descStockActual = descStockActual;
	}

	public String getDescDiferencia() {
		return this.descDiferencia;
	}

	public void setDescDiferencia(String descDiferencia) {
		this.descDiferencia = descDiferencia;
	}

	public String getTotalStock() {
		return this.totalStock;
	}

	public void setTotalStock(String totalStock) {
		this.totalStock = totalStock;
	}

	public String getTotalBandeja() {
		return this.totalBandeja;
	}

	public void setTotalBandeja(String totalBandeja) {
		this.totalBandeja = totalBandeja;
	}

	public Double getStockActual() {
		return this.stockActual;
	}

	public void setStockActual(Double stockActual) {
		this.stockActual = stockActual;
	}

	public Double getDiferencia() {
		return this.diferencia;
	}

	public void setDiferencia(Double diferencia) {
		this.diferencia = diferencia;
	}

	public String getMmc() {
		return this.mmc;
	}

	public void setMmc(String mmc) {
		this.mmc = mmc;
	}

	public String getEsErrorBandejas() {
		return this.esErrorBandejas;
	}

	public void setEsErrorBandejas(String esErrorBandejas) {
		this.esErrorBandejas = esErrorBandejas;
	}

	public String getEsErrorStock() {
		return this.esErrorStock;
	}

	public void setEsErrorStock(String esErrorStock) {
		this.esErrorStock = esErrorStock;
	}

	public boolean getChkNoGuardar() {
		return Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI.equals(this.flgNoGuardar);
	}

	public void setChkNoGuardar(boolean chkNoGuardar) {
		this.chkNoGuardar = chkNoGuardar;
		if (chkNoGuardar){
			this.flgNoGuardar = Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_SI;
		}else{
			this.flgNoGuardar = Constantes.INVENTARIO_LIBRE_FLG_NO_GUARDAR_NO;
		}
	}

	public String getOrigenGISAE() {
		return origenGISAE;
	}

	public void setOrigenGISAE(String origenGISAE) {
		this.origenGISAE = origenGISAE;
	}

	public String getKgs() {
		return kgs;
	}

	public void setKgs(String kgs) {
		this.kgs = kgs;
	}

	public String getTipoRotacion() {
		return this.tipoRotacion;
	}

	public void setTipoRotacion(String tipoRotacion) {
		this.tipoRotacion = tipoRotacion;
	}

}