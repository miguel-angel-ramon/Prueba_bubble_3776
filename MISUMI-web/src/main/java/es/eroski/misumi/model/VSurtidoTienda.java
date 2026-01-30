package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import es.eroski.misumi.util.Utilidades;

public class VSurtidoTienda implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codCentro;
	private Long codArt;
	private Long codArtCaprabo;
	private Double uniCajaServ;
	private String tipoAprov;
	private String marcaMaestroCentro;
	private String catalogo;
	private String pedir;
	private Long tipoGama;
	private String descGama;
	private Date fechaGen;

	private String gamaDiscontinua;
	private Long gamaDiscLunes;
	private Long gamaDiscMartes;
	private Long gamaDiscMiercoles;
	private Long gamaDiscJueves;
	private Long gamaDiscViernes;
	private Long gamaDiscSabado;
	private Long gamaDiscDomingo;
	private Float ufp;

	private String soloReparto;
	private Date fechaMmc;
	private Long cc;

	// Campo calculado
	private String mapaHoy;
	private Long numeroPedidosOtroDia;

	public VSurtidoTienda() {
		super();
	}

	public VSurtidoTienda( Long codCentro, Long codArt){
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
	} 
			
	public VSurtidoTienda( Long codCentro, Long codArt, Long codArtCaprabo, Double uniCajaServ, String tipoAprov
						 , String marcaMaestroCentro, String catalogo, String pedir, Long tipoGama, String descGama
						 , String aprovisionamiento, Date fechaGen, Float ufp ) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.codArtCaprabo = codArtCaprabo;
		this.uniCajaServ = uniCajaServ;
		this.tipoAprov = tipoAprov;
		this.marcaMaestroCentro = marcaMaestroCentro;
		this.catalogo = catalogo;
		this.pedir = pedir;
		this.tipoGama = tipoGama;
		this.descGama = descGama;
		this.tipoAprov = aprovisionamiento;
		this.fechaGen = fechaGen;
		this.ufp = ufp;
	}

	public VSurtidoTienda(Long codCentro, Long codArt, String gamaDiscontinua, Long gamaDiscLunes, Long gamaDiscMartes,
			Long gamaDiscMiercoles, Long gamaDiscJueves, Long gamaDiscViernes, Long gamaDiscSabado,
			Long gamaDiscDomingo, Float ufp) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.gamaDiscontinua = gamaDiscontinua;
		this.gamaDiscLunes = gamaDiscLunes;
		this.gamaDiscMartes = gamaDiscMartes;
		this.gamaDiscMiercoles = gamaDiscMiercoles;
		this.gamaDiscJueves = gamaDiscJueves;
		this.gamaDiscViernes = gamaDiscViernes;
		this.gamaDiscSabado = gamaDiscSabado;
		this.gamaDiscDomingo = gamaDiscDomingo;
		this.ufp = ufp;
	}

	public VSurtidoTienda(Long codCentro, Long codArt, Date fechaGen, String pedir) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.fechaGen = fechaGen;
		this.pedir = pedir;
	}

	public VSurtidoTienda( Long codCentro, Long codArt, Double uniCajaServ, String tipoAprov, String marcaMaestroCentro
						 , String catalogo, String reapro, Date fechaMmc, Long cc, Float ufp) {
		super();
		this.codCentro = codCentro;
		this.codArt = codArt;
		this.uniCajaServ = uniCajaServ;
		this.tipoAprov = tipoAprov;
		this.marcaMaestroCentro = marcaMaestroCentro;
		this.catalogo = catalogo;
		this.soloReparto = StringUtils.equals(reapro,"N")?"S":StringUtils.equals(reapro," ")?"S":"N";
		this.fechaMmc = fechaMmc;
		this.cc = cc;
		this.ufp = ufp;
		this.pedir = "";
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

	public Long getCodArtCaprabo() {
		return codArtCaprabo;
	}

	public void setCodArtCaprabo(Long codArtCaprabo) {
		this.codArtCaprabo = codArtCaprabo;
	}

	public Double getUniCajaServ() {
		return this.uniCajaServ;
	}

	public void setUniCajaServ(Double uniCajaServ) {
		this.uniCajaServ = uniCajaServ;
	}

	public String getTipoAprov() {
		return this.tipoAprov;
	}

	public void setTipoAprov(String tipoAprov) {
		this.tipoAprov = tipoAprov;
	}

	public String getMarcaMaestroCentro() {
		return this.marcaMaestroCentro;
	}

	public void setMarcaMaestroCentro(String marcaMaestroCentro) {
		this.marcaMaestroCentro = marcaMaestroCentro;
	}

	public String getCatalogo() {
		return this.catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}

	public String getPedir() {
		return this.pedir;
	}

	public void setPedir(String pedir) {
		this.pedir = pedir;
	}

	public Long getTipoGama() {
		return this.tipoGama;
	}

	public void setTipoGama(Long tipoGama) {
		this.tipoGama = tipoGama;
	}

	public String getDescGama() {
		return this.descGama;
	}

	public void setDescGama(String descGama) {
		this.descGama = descGama;
	}

	public Date getFechaGen() {
		return this.fechaGen;
	}

	public void setFechaGen(Date fechaGen) {
		this.fechaGen = fechaGen;
	}

	public String getGamaDiscontinua() {
		return gamaDiscontinua;
	}

	public void setGamaDiscontinua(String gamaDiscontinua) {
		this.gamaDiscontinua = gamaDiscontinua;
	}

	public Long getGamaDiscLunes() {
		return gamaDiscLunes;
	}

	public void setGamaDiscLunes(Long gamaDiscLunes) {
		this.gamaDiscLunes = gamaDiscLunes;
	}

	public Long getGamaDiscMartes() {
		return gamaDiscMartes;
	}

	public void setGamaDiscMartes(Long gamaDiscMartes) {
		this.gamaDiscMartes = gamaDiscMartes;
	}

	public Long getGamaDiscMiercoles() {
		return gamaDiscMiercoles;
	}

	public void setGamaDiscMiercoles(Long gamaDiscMiercoles) {
		this.gamaDiscMiercoles = gamaDiscMiercoles;
	}

	public Long getGamaDiscJueves() {
		return gamaDiscJueves;
	}

	public void setGamaDiscJueves(Long gamaDiscJueves) {
		this.gamaDiscJueves = gamaDiscJueves;
	}

	public Long getGamaDiscViernes() {
		return gamaDiscViernes;
	}

	public void setGamaDiscViernes(Long gamaDiscViernes) {
		this.gamaDiscViernes = gamaDiscViernes;
	}

	public Long getGamaDiscSabado() {
		return gamaDiscSabado;
	}

	public void setGamaDiscSabado(Long gamaDiscSabado) {
		this.gamaDiscSabado = gamaDiscSabado;
	}

	public Long getGamaDiscDomingo() {
		return gamaDiscDomingo;
	}

	public void setGamaDiscDomingo(Long gamaDiscDomingo) {
		this.gamaDiscDomingo = gamaDiscDomingo;
	}

	public Float getUfp() {
		return ufp;
	}

	public void setUfp(Float ufp) {
		this.ufp = ufp;
	}

	public String getSoloReparto() {
		return soloReparto;
	}

	public void setSoloReparto(String soloReparto) {
		this.soloReparto = soloReparto;
	}

	public Date getFechaMmc() {
		return fechaMmc;
	}
	
	public String getFechaMmcStr() {
		if (null != fechaMmc) {
			return Utilidades.formatearFecha_ddMMyyyyBarra(fechaMmc);
		} else {
			return "";
		}
	}

	public void setFechaMmc(Date fechaMmc) {
		this.fechaMmc = fechaMmc;
	}

	public Long getCc() {
		return cc;
	}

	public void setCc(Long cc) {
		this.cc = cc;
	}

	public String getMapaHoy() {
		return this.mapaHoy;
	}

	public void setMapaHoy(String mapaHoy) {
		this.mapaHoy = mapaHoy;
	}

	public Long getNumeroPedidosOtroDia() {
		return this.numeroPedidosOtroDia;
	}

	public void setNumeroPedidosOtroDia(Long numeroPedidosOtroDia) {
		this.numeroPedidosOtroDia = numeroPedidosOtroDia;
	}

}