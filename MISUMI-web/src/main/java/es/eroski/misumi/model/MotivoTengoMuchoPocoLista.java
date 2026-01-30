package es.eroski.misumi.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MotivoTengoMuchoPocoLista implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private List<MotivoTengoMuchoPoco> datos;
    private String mapaActivo;
    private Long nMaxPed;
    private Date fUltPed;
    private Double ventaMedia;
    private String flgFGen;
    private Long estado;
	private String descEstado;
	
	public MotivoTengoMuchoPocoLista() {
		super();
	}

	public MotivoTengoMuchoPocoLista(List<MotivoTengoMuchoPoco> datos,
			String mapaActivo, Long nMaxPed, Date fUltPed, Double ventaMedia,
			String flgFGen, Long estado, String descEstado) {
		super();
		this.datos = datos;
		this.mapaActivo = mapaActivo;
		this.nMaxPed = nMaxPed;
		this.fUltPed = fUltPed;
		this.ventaMedia = ventaMedia;
		this.flgFGen = flgFGen;
		this.estado = estado;
		this.descEstado = descEstado;
	}

	public List<MotivoTengoMuchoPoco> getDatos() {
		return this.datos;
	}

	public void setDatos(List<MotivoTengoMuchoPoco> datos) {
		this.datos = datos;
	}

	public String getMapaActivo() {
		return this.mapaActivo;
	}

	public void setMapaActivo(String mapaActivo) {
		this.mapaActivo = mapaActivo;
	}

	public Long getnMaxPed() {
		return this.nMaxPed;
	}

	public void setnMaxPed(Long nMaxPed) {
		this.nMaxPed = nMaxPed;
	}

	public Date getfUltPed() {
		return this.fUltPed;
	}

	public void setfUltPed(Date fUltPed) {
		this.fUltPed = fUltPed;
	}

	public Double getVentaMedia() {
		return this.ventaMedia;
	}

	public void setVentaMedia(Double ventaMedia) {
		this.ventaMedia = ventaMedia;
	}

	public String getFlgFGen() {
		return this.flgFGen;
	}

	public void setFlgFGen(String flgFGen) {
		this.flgFGen = flgFGen;
	}

	public Long getEstado() {
		return this.estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public String getDescEstado() {
		return this.descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	

}