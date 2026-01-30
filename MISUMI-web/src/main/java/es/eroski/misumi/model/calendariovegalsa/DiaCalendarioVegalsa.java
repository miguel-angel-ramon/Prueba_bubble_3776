/**
 * 
 */
package es.eroski.misumi.model.calendariovegalsa;

import java.io.Serializable;
import java.util.Date;

/**
 * MISUMI-301
 * Pojo para la representacion de un dia en el calendario Vegalsa
 * @author BICUGUAL
 *
 */
public class DiaCalendarioVegalsa implements Serializable{
	
	private static final long serialVersionUID = -920061340872816016L;
	
	private Long codCentro;
	//Fecha de calendario sacada de la tabla festivos_centro
	private Date fechaCalendario;
	//Estado del centro. Valores CERRADO y ABIERTO
	private String estado;
	//NULL si la fecha del calendario no es la de el dia actual
	private String diaActual;
	//Fecha del pedido para la entrega formateada
	private String fechaPedido;
	//Hora del pedido para la entrega formateada
	private String horaPedido;
	//Turno de reposicion, valores T, M o null
	private String turnoReposicion;
	//Marcador indica “E” (calendarios eliminados por festivo o día de no apertura)
	//“X” (calendarios modificado por existencia de festivo entre transmisión y reposición)
	private String marcador;//para ver el color del dia
		
	public DiaCalendarioVegalsa() {
		super();
	}
		
	public Long getCodCentro() {
		return codCentro;
	}

	public void setCodCentro(Long codCentro) {
		this.codCentro = codCentro;
	}

	public Date getFechaCalendario() {
		return fechaCalendario;
	}

	public void setFechaCalendario(Date fechaCalendario) {
		this.fechaCalendario = fechaCalendario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDiaActual() {
		return diaActual;
	}

	public void setDiaActual(String diaActual) {
		this.diaActual = diaActual;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getHoraPedido() {
		return horaPedido;
	}

	public void setHoraPedido(String horaPedido) {
		this.horaPedido = horaPedido;
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
