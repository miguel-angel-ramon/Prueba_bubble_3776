package es.eroski.misumi.model;

import java.util.Date;

public class PropuestaDetalladoVegalsa {
/**
 * Para mapear la tabla T_MIS_RESUMEN_DETALLADO Y LOS VALORES DE FFPP+UFP editado Y Cj Ped editado DE LA TABLA T_MIS_DETALLADO_VEGALSA_MODIF
 * 
-MAPA
-HORA LANZAMIENTO
-F.REPOSICION
-JORNADA REPO
-UMBRAL EN PALETS
-PALETS PEDIDOS
-PALETS CORTADOS
-PALETS ADELANTADOS
-FFPP+UFP
-FFPP+UFP edit
-CAJAS PEDIDAS
-CAJAS PEDIDAS edit
-CAJAS CORTADAS
-CAJAS ADELANTADAS
-SIG. DÍA DE PEDIDO
 */
	Long idPedido;
	Long codMapa;
	String horaLanzamiento;
	Date fechaReposicion;
	String jornadaReposicion;
	Double umbralPalets;
	Double paletsPedidos;
	Double paletsCortados;
	Double paletsAdelantados;
	Long ffppUfp;
	Long ffppUfpEdit;
	Long cajasPedidas;
	Long cajasPedidasEdit;
	Long cajasCortadas;
	Long cajasAdelantadas;
	Date fechaSigPedido;
	
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	public Long getCodMapa() {
		return codMapa;
	}
	public void setCodMapa(Long codMapa) {
		this.codMapa = codMapa;
	}
	public String getHoraLanzamiento() {
		return horaLanzamiento;
	}
	public void setHoraLanzamiento(String horaLanzamiento) {
		this.horaLanzamiento = horaLanzamiento;
	}
	public Date getFechaReposicion() {
		return fechaReposicion;
	}
	public void setFechaReposicion(Date fechaReposicion) {
		this.fechaReposicion = fechaReposicion;
	}
	public String getJornadaReposicion() {
		return jornadaReposicion;
	}
	public void setJornadaReposicion(String jornadaReposicion) {
		this.jornadaReposicion = jornadaReposicion;
	}
	public Double getUmbralPalets() {
		return umbralPalets;
	}
	public void setUmbralPalets(Double umbralPalets) {
		this.umbralPalets = umbralPalets;
	}
	public Double getPaletsPedidos() {
		return paletsPedidos;
	}
	public void setPaletsPedidos(Double paletsPedidos) {
		this.paletsPedidos = paletsPedidos;
	}
	public Double getPaletsCortados() {
		return paletsCortados;
	}
	public void setPaletsCortados(Double paletsCortados) {
		this.paletsCortados = paletsCortados;
	}
	public Double getPaletsAdelantados() {
		return paletsAdelantados;
	}
	public void setPaletsAdelantados(Double paletsAdelantados) {
		this.paletsAdelantados = paletsAdelantados;
	}
	public Long getFfppUfp() {
		return ffppUfp;
	}
	public void setFfppUfp(Long ffppUfp) {
		this.ffppUfp = ffppUfp;
	}
	public Long getFfppUfpEdit() {
		return ffppUfpEdit;
	}
	public void setFfppUfpEdit(Long ffppUfpEdit) {
		this.ffppUfpEdit = ffppUfpEdit;
	}
	public Long getCajasPedidas() {
		return cajasPedidas;
	}
	public void setCajasPedidas(Long cajasPedidas) {
		this.cajasPedidas = cajasPedidas;
	}
	public Long getCajasPedidasEdit() {
		return cajasPedidasEdit;
	}
	public void setCajasPedidasEdit(Long cajasPedidasEdit) {
		this.cajasPedidasEdit = cajasPedidasEdit;
	}
	public Long getCajasCortadas() {
		return cajasCortadas;
	}
	public void setCajasCortadas(Long cajasCortadas) {
		this.cajasCortadas = cajasCortadas;
	}
	public Long getCajasAdelantadas() {
		return cajasAdelantadas;
	}
	public void setCajasAdelantadas(Long cajasAdelantadas) {
		this.cajasAdelantadas = cajasAdelantadas;
	}
	public Date getFechaSigPedido() {
		return fechaSigPedido;
	}
	public void setFechaSigPedido(Date fechaSigPedido) {
		this.fechaSigPedido = fechaSigPedido;
	}

}
