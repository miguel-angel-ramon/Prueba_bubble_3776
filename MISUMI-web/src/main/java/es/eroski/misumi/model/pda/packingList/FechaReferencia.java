package es.eroski.misumi.model.pda.packingList;

import java.io.Serializable;

import es.eroski.misumi.model.FotosReferencia;
import es.eroski.misumi.model.pda.PdaDatosReferencia;


public class FechaReferencia {
    private Long codCentro;
    private String fechaFestivo;

    public Long getCodCentro() {
        return codCentro;
    }

    public void setCodCentro(Long codCentro2) {
        this.codCentro = codCentro2;
    }

    public String getFechaFestivo() {
        return fechaFestivo;
    }

    public void setFechaFestivo(String fechaFestivo) {
        this.fechaFestivo = fechaFestivo;
    }
}