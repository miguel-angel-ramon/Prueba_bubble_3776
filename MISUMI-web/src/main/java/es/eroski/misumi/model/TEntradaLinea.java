package es.eroski.misumi.model;

import java.util.Date;

public class TEntradaLinea {
	//Datos de sesión de usuario y día de creación.
	private String idSesion;
	private Date creationDate;

	//Dato que relaciona la linea de entrada con la cabecera de la entrada.
	private Long codCabPedido;

	//Datos de entradaLinea
	private Long codArticulo;
	private String denomCodArticulo;
	private Long numeroCajasPedidas;
	private Long numeroCajasRecepcionadas;
	private Long uc;
	private Long totalBandejasPedidas;
	private Long totalBandejasRecepcionadas;
	private Double totalUnidadesPedidas;
	private Double totalUnidadesRecepcionadas;

	//Dato de si tiene foto o no
	private String flgFoto;

	//Dato que guarda el error de la línea de entrada y su descripción
	private Long codError;
	private String descError;
	
	//Guardan los datos con el valor original cargado del PLSQL.
	private Long numeroCajasRecepcionadasOrig;
	private Long totalBandejasRecepcionadasOrig;
	private Double totalUnidadesRecepcionadasOrig;

	//Introducimos el centro
	private Long codLoc;
	
	//Constructores
	public TEntradaLinea(String idSesion, Date creationDate, Long codCabPedido, Long codArticulo,
			String denomCodArticulo, Long numeroCajasPedidas, Long numeroCajasRecepcionadas, Long uc,
			Long totalBandejasPedidas, Long totalBandejasRecepcionadas, Double totalUnidadesPedidas,
			Double totalUnidadesRecepcionadas, String flgFoto, Long codError, String descError) {
		this(idSesion, creationDate, codCabPedido, codArticulo,
				denomCodArticulo, numeroCajasPedidas, numeroCajasRecepcionadas, uc,
				totalBandejasPedidas, totalBandejasRecepcionadas, totalUnidadesPedidas,
				totalUnidadesRecepcionadas, flgFoto, codError, descError, null,
				null, null);
	}
	
	public TEntradaLinea(String idSesion, Date creationDate, Long codCabPedido, Long codArticulo,
			String denomCodArticulo, Long numeroCajasPedidas, Long numeroCajasRecepcionadas, Long uc,
			Long totalBandejasPedidas, Long totalBandejasRecepcionadas, Double totalUnidadesPedidas,
			Double totalUnidadesRecepcionadas, String flgFoto, Long codError, String descError, Long numeroCajasRecepcionadasOrig,
			Long totalBandejasRecepcionadasOrig, Double totalUnidadesRecepcionadasOrig) {
		super();
		this.idSesion = idSesion;
		this.creationDate = creationDate;
		this.codCabPedido = codCabPedido;
		this.codArticulo = codArticulo;
		this.denomCodArticulo = denomCodArticulo;
		this.numeroCajasPedidas = numeroCajasPedidas;
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
		this.uc = uc;
		this.totalBandejasPedidas = totalBandejasPedidas;
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
		this.totalUnidadesPedidas = totalUnidadesPedidas;
		this.totalUnidadesRecepcionadas = totalUnidadesRecepcionadas;
		this.flgFoto = flgFoto;
		this.codError = codError;
		this.descError = descError;
		this.numeroCajasRecepcionadasOrig = numeroCajasRecepcionadasOrig;
		this.totalBandejasRecepcionadasOrig = totalBandejasRecepcionadasOrig;
		this.totalUnidadesRecepcionadasOrig = totalUnidadesRecepcionadasOrig;
	}


	public TEntradaLinea() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Datos de sesión de usuario y día de creación.
	public String getIdSesion() {
		return idSesion;
	}
	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	//Dato que relaciona la linea de entrada con la cabecera de la entrada.
	public Long getCodCabPedido() {
		return codCabPedido;
	}
	public void setCodCabPedido(Long codCabPedido) {
		this.codCabPedido = codCabPedido;
	}

	//Datos de entradaLinea
	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getDenomCodArticulo() {
		return denomCodArticulo;
	}

	public void setDenomCodArticulo(String denomCodArticulo) {
		this.denomCodArticulo = denomCodArticulo;
	}

	public Long getNumeroCajasPedidas() {
		return numeroCajasPedidas;
	}
	public void setNumeroCajasPedidas(Long numeroCajasPedidas) {
		this.numeroCajasPedidas = numeroCajasPedidas;
	}
	public Long getNumeroCajasRecepcionadas() {
		return numeroCajasRecepcionadas;
	}
	public void setNumeroCajasRecepcionadas(Long numeroCajasRecepcionadas) {
		this.numeroCajasRecepcionadas = numeroCajasRecepcionadas;
	}
	public Long getUc() {
		return uc;
	}
	public void setUc(Long uc) {
		this.uc = uc;
	}
	public Long getTotalBandejasPedidas() {
		return totalBandejasPedidas;
	}
	public void setTotalBandejasPedidas(Long totalBandejasPedidas) {
		this.totalBandejasPedidas = totalBandejasPedidas;
	}
	public Long getTotalBandejasRecepcionadas() {
		return totalBandejasRecepcionadas;
	}
	public void setTotalBandejasRecepcionadas(Long totalBandejasRecepcionadas) {
		this.totalBandejasRecepcionadas = totalBandejasRecepcionadas;
	}
	public Double getTotalUnidadesPedidas() {
		return totalUnidadesPedidas;
	}
	public void setTotalUnidadesPedidas(Double totalUnidadesPedidas) {
		this.totalUnidadesPedidas = totalUnidadesPedidas;
	}
	public Double getTotalUnidadesRecepcionadas() {
		return totalUnidadesRecepcionadas;
	}
	public void setTotalUnidadesRecepcionadas(Double totalUnidadesRecepcionadas) {
		this.totalUnidadesRecepcionadas = totalUnidadesRecepcionadas;
	}
	//Dato de si tiene foto o no
	public String getFlgFoto() {
		return flgFoto;
	}
	public void setFlgFoto(String flgFoto) {
		this.flgFoto = flgFoto;
	}
	//Dato que guarda el estado de la línea de entrada
	public Long getCodError() {
		return codError;
	}

	public void setCodError(Long codError) {
		this.codError = codError;
	}
	
	public String getDescError() {
		return descError;
	}

	public void setDescError(String descError) {
		this.descError = descError;
	}

	//Guardan los datos con el valor original cargado del PLSQL.
	public Long getNumeroCajasRecepcionadasOrig() {
		return numeroCajasRecepcionadasOrig;
	}
	public void setNumeroCajasRecepcionadasOrig(Long numeroCajasRecepcionadasOrig) {
		this.numeroCajasRecepcionadasOrig = numeroCajasRecepcionadasOrig;
	}
	public Long getTotalBandejasRecepcionadasOrig() {
		return totalBandejasRecepcionadasOrig;
	}
	public void setTotalBandejasRecepcionadasOrig(Long totalBandejasRecepcionadasOrig) {
		this.totalBandejasRecepcionadasOrig = totalBandejasRecepcionadasOrig;
	}
	public Double getTotalUnidadesRecepcionadasOrig() {
		return totalUnidadesRecepcionadasOrig;
	}
	public void setTotalUnidadesRecepcionadasOrig(Double totalUnidadesRecepcionadasOrig) {
		this.totalUnidadesRecepcionadasOrig = totalUnidadesRecepcionadasOrig;
	}

	//Introducimos el centro
	public Long getCodLoc() {
		return codLoc;
	}
	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}	
}
