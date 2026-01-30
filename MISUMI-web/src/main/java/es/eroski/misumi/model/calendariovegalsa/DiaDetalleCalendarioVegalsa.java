/**
 * 
 */
package es.eroski.misumi.model.calendariovegalsa;

/**
 * MISUMI-301
 * Objeto de modelo para la representacion de un registro en la tabla paginada de detalle de calendario de pedido vegalsa 
 * @author BICUGUAL
 *
 */
public class DiaDetalleCalendarioVegalsa {

	private Long codCentro;
	private Integer codMapa;
	private String fechaPedido;
	private String diaSemPedido;
	private String horaPedido;
	private String fechaReposicion;
	private String diaSemReposicion;
	private String turnoReposicion;
	private String marcador;
	
	public DiaDetalleCalendarioVegalsa() {
		super();
	}

	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Integer getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(Integer codMapa) {
		this.codMapa = codMapa;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getDiaSemPedido() {
		return diaSemPedido;
	}

	public void setDiaSemPedido(String diaSemPedido) {
		this.diaSemPedido = diaSemPedido;
	}

	public String getHoraPedido() {
		return horaPedido;
	}

	public void setHoraPedido(String horaPedido) {
		this.horaPedido = horaPedido;
	}

	public String getFechaReposicion() {
		return fechaReposicion;
	}

	public void setFechaReposicion(String fechaReposicion) {
		this.fechaReposicion = fechaReposicion;
	}

	public String getDiaSemReposicion() {
		return diaSemReposicion;
	}

	public void setDiaSemReposicion(String diaSemReposicion) {
		this.diaSemReposicion = diaSemReposicion;
	}

	public String getTurnoReposicion() {
		return turnoReposicion;
	}

	public void setTurnoReposicion(String turnoReposicion) {
		this.turnoReposicion = turnoReposicion;
	}

	public String getMarcador() {
		return marcador;
	}

	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}
}
