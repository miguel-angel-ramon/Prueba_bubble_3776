package es.eroski.misumi.model.pda.packingList;

import java.io.Serializable;

public class RdoListarArticulosWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoRespuesta;
    private Resultado resultado;
    private String codigoError;
    private String token;

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}