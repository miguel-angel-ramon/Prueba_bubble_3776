package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DiasServicio implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long codCentro; 
	private Long codArt;
	private Long codN1;
	private Long codN2;
	private Long codN3;
	private Date fecha;
	private String dFestivo;
	private Long dPedido;
	private String dServicio;
	private Date creationDate;
	private Date lastUpdateDate;
	private String idsesion;
	private Long identificador;
	private Long identificadorSIA;
	private String dEncargo;
	private String dMontaje;
	private String dEncargoCliente;
	private String tipoDiaLN;
	private String bEncargo;
	private String bEncargoPilada;
	
	//fecha de pantalla en formato ddmmyyyy
	private String fechaPantalla;
	//Clase de pedido
	private Long clasePedido;
	//Campos para control de errores
	private Long codError;
	private String descError;
	//Campos para filtro por estructura
	private Long area;
	private Long seccion;
	private Long categoria;
	
	//Campo filtrado nuevo por referencia
	private String uuid;
	
	//Lista de artículos para la recarga de días de servicio por grupo
	private List<Long> listaArticulosRecargaDiasServicio;

	//Se utiliza para buscar desde una fecha los días de servicio.
	private Date fechaDesde;
	
	public DiasServicio() {
		super();
	}

	public DiasServicio(Long codCentro, Long codArt, Long codN1, Long codN2,
			Long codN3, Date fecha, String fechaPantalla, String dFestivo, Long dPedido,
			String dServicio, Date creationDate, Date lastUpdateDate, String idsesion,
			Long identificador, Long identificadorSIA, String dEncargo, String dMontaje, String tipoDiaLN,
			String bEncargo, String bEncargoPilada, String dEncargoCliente) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codN1 = codN1;
		this.codN2 = codN2;
		this.codN3 = codN3;
		this.fecha = fecha;
		this.fechaPantalla = fechaPantalla;
		this.dFestivo = dFestivo;
		this.dPedido = dPedido;
		this.dServicio = dServicio;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.idsesion = idsesion;
		this.identificador = identificador;
		this.identificadorSIA = identificadorSIA;
		this.dEncargo = dEncargo;
		this.dMontaje = dMontaje;
		this.tipoDiaLN = tipoDiaLN;
		this.bEncargo = bEncargo;
		this.bEncargoPilada = bEncargoPilada;
		this.dEncargoCliente = dEncargoCliente;
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

	public Long getCodN1() {
		return this.codN1;
	}

	public void setCodN1(Long codN1) {
		this.codN1 = codN1;
	}

	public Long getCodN2() {
		return this.codN2;
	}

	public void setCodN2(Long codN2) {
		this.codN2 = codN2;
	}

	public Long getCodN3() {
		return this.codN3;
	}

	public void setCodN3(Long codN3) {
		this.codN3 = codN3;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getdFestivo() {
		return this.dFestivo;
	}

	public void setdFestivo(String dFestivo) {
		this.dFestivo = dFestivo;
	}

	public Long getdPedido() {
		return this.dPedido;
	}

	public void setdPedido(Long dPedido) {
		this.dPedido = dPedido;
	}

	public String getdServicio() {
		return this.dServicio;
	}

	public void setdServicio(String dServicio) {
		this.dServicio = dServicio;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Long getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public Long getIdentificadorSIA() {
		return this.identificadorSIA;
	}

	public void setIdentificadorSIA(Long identificadorSIA) {
		this.identificadorSIA = identificadorSIA;
	}

	public String getdEncargo() {
		return this.dEncargo;
	}

	public void setdEncargo(String dEncargo) {
		this.dEncargo = dEncargo;
	}

	public String getdMontaje() {
		return this.dMontaje;
	}

	public void setdMontaje(String dMontaje) {
		this.dMontaje = dMontaje;
	}

	public String getdEncargoCliente() {
		return dEncargoCliente;
	}

	public void setdEncargoCliente(String dEncargoCliente) {
		this.dEncargoCliente = dEncargoCliente;
	}

	public String getTipoDiaLN() {
		return this.tipoDiaLN;
	}

	public void setTipoDiaLN(String tipoDiaLN) {
		this.tipoDiaLN = tipoDiaLN;
	}

	public String getbEncargo() {
		return this.bEncargo;
	}

	public void setbEncargo(String bEncargo) {
		this.bEncargo = bEncargo;
	}

	public String getbEncargoPilada() {
		return this.bEncargoPilada;
	}

	public void setbEncargoPilada(String bEncargoPilada) {
		this.bEncargoPilada = bEncargoPilada;
	}

	public String getFechaPantalla() {
		return this.fechaPantalla;
	}

	public void setFechaPantalla(String fechaPantalla) {
		this.fechaPantalla = fechaPantalla;
	}

	public Long getClasePedido() {
		return this.clasePedido;
	}

	public void setClasePedido(Long clasePedido) {
		this.clasePedido = clasePedido;
	}

	public Long getCodError() {
		return this.codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}

	public String getDescError() {
		return this.descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	public Long getArea() {
		return this.area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public Long getSeccion() {
		return this.seccion;
	}

	public void setSeccion(Long seccion) {
		this.seccion = seccion;
	}

	public Long getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Long> getListaArticulosRecargaDiasServicio() {
		return this.listaArticulosRecargaDiasServicio;
	}

	public void setListaArticulosRecargaDiasServicio(List<Long> listaArticulosRecargaDiasServicio) {
		this.listaArticulosRecargaDiasServicio = listaArticulosRecargaDiasServicio;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
}