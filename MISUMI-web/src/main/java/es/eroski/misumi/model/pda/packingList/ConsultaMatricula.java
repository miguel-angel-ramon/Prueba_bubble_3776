package es.eroski.misumi.model.pda.packingList;

import java.io.Serializable;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.pda.PdaDatosReferencia;


public class ConsultaMatricula implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private long articulo;
    private String descripcion;
    private Integer fechaAlbaran;
    private String albaran;
    private int bultos;
    private int cantidad;
    private String tipoCantidad;
    private boolean alta;
    private String formattedFechaAlbaran;
    
    private Boolean tieneFoto;
    
    private int plataforma;

   
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public long getArticulo() {
        return articulo;
    }

    public void setArticulo(long articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Integer getFechaAlbaran() {
		return fechaAlbaran;
	}

	public void setFechaAlbaran(Integer fechaAlbaran) {
		this.fechaAlbaran = fechaAlbaran;
	}

	public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public int getBultos() {
        return bultos;
    }

    public void setBultos(int bultos) {
        this.bultos = bultos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

	public String getFormattedFechaAlbaran() {
		return formattedFechaAlbaran;
	}

	public void setFormattedFechaAlbaran(String formattedFechaAlbaran) {
		this.formattedFechaAlbaran = formattedFechaAlbaran;
	}

	public Boolean getTieneFoto() {
		return tieneFoto;
	}

	public void setTieneFoto(Boolean tieneFoto) {
		this.tieneFoto = tieneFoto;
	}

	public int getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(int plataforma) {
		this.plataforma = plataforma;
	}

	
	
	
    
}