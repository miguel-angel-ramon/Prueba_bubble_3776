package es.eroski.misumi.model.pda.prehuecos;

import java.io.Serializable;
import java.sql.Date;

public class AlmacenPrehuecos  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int codCentro;            
    private String mac;               
    private int codArt;   
    private String descArt;
    private int stockLineal;          
    private int estado;               
    private Date creationDate;        
    private Date lastUpdateDate;      
    private Date fecValidado; 
	private String stock;
	private String stockActual;
	private String calculoCC;
	private String MMC;
	private String stockActivo;
	private String flgPantCorrStock;
	private Boolean TieneFoto;
	private String origenPantalla;


	public int getCodCentro() {
        return codCentro;
    }

    public void setCodCentro(int codCentro) {
        this.codCentro = codCentro;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getCodArt() {
        return codArt;
    }

    public void setCodArt(int codArt) {
        this.codArt = codArt;
    }
    
    public String getDescArt() {
		return descArt;
	}

	public void setDescArt(String descArt) {
		this.descArt = descArt;
	}

	public int getStockLineal() {
        return stockLineal;
    }

    public void setStockLineal(int stockLineal) {
        this.stockLineal = stockLineal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getFecValidado() {
        return fecValidado;
    }

    public void setFecValidado(Date fecValidado) {
        this.fecValidado = fecValidado;
    }

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStockActual() {
		return stockActual;
	}

	public void setStockActual(String stockActual) {
		this.stockActual = stockActual;
	}

	public String getCalculoCC() {
		return calculoCC;
	}

	public void setCalculoCC(String calculoCC) {
		this.calculoCC = calculoCC;
	}

	public String getMMC() {
		return MMC;
	}

	public void setMMC(String mMC) {
		MMC = mMC;
	}
	
    public String getStockActivo() {
		return stockActivo;
	}

	public void setStockActivo(String stockActivo) {
		this.stockActivo = stockActivo;
	}

	public String getFlgPantCorrStock() {
		return flgPantCorrStock;
	}

	public void setFlgPantCorrStock(String flgPantCorrStock) {
		this.flgPantCorrStock = flgPantCorrStock;
	}

	public Boolean getTieneFoto() {
		return TieneFoto;
	}

	public void setTieneFoto(Boolean tieneFoto) {
		TieneFoto = tieneFoto;
	}

	public String getOrigenPantalla() {
		return origenPantalla;
	}

	public void setOrigenPantalla(String origenPantalla) {
		this.origenPantalla = origenPantalla;
	}

   
	
}