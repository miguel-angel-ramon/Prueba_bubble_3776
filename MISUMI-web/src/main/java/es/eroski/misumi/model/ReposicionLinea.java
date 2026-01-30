package es.eroski.misumi.model;

import java.io.Serializable;

//Equivalen a cada línea de la tabla relacionada con una devolución.
public class ReposicionLinea implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private Long codLoc;
	private String codMac;
	
	private Long codArticulo;
    private String descrTalla;
    private String cantRepo;
    private String stock;
    private String flgPantCorrStock;
    
    private String modeloProveedor;
    private String descrColor;
    
    private Long subPosicion;  //Es necesario para ordenar la lista de tallas que se pinta en pantalla. Indicar el orden en el que nos manda las lineas el procedimiento, que sera el orden en que tengamos que pintarlo.
  

   
	public ReposicionLinea() {
		super();
	}

	public ReposicionLinea( Long codArticulo,
     String descrTalla,
     Double cantRepo,
     Double stock,
     String flgPantCorrStock) {
		
		super();
		
		this.codArticulo = codArticulo;
		this.descrTalla = descrTalla;
		if(cantRepo!=null){
			this.cantRepo = cantRepo.toString();
		}else{
			this.cantRepo=null;
		}
		
		if(stock!=null){
			this.stock = stock.toString();
		}else{
			this.stock=null;
		}
		this.stock = stock.toString();
		this.flgPantCorrStock = flgPantCorrStock;
	
	}

	public Long getCodLoc() {
		return codLoc;
	}

	public void setCodLoc(Long codLoc) {
		this.codLoc = codLoc;
	}

	public String getCodMac() {
		return codMac;
	}

	public void setCodMac(String codMac) {
		this.codMac = codMac;
	}

	public Long getCodArticulo() {
		return codArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		this.codArticulo = codArticulo;
	}

	public String getDescrTalla() {
		return descrTalla;
	}

	public void setDescrTalla(String descrTalla) {
		this.descrTalla = descrTalla;
	}

	public String getCantRepo() {
		return cantRepo;
	}

	public void setCantRepo(String cantRepo) {
		this.cantRepo = cantRepo;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getFlgPantCorrStock() {
		return flgPantCorrStock;
	}

	public void setFlgPantCorrStock(String flgPantCorrStock) {
		this.flgPantCorrStock = flgPantCorrStock;
	}

	public String getModeloProveedor() {
		return modeloProveedor;
	}

	public void setModeloProveedor(String modeloProveedor) {
		this.modeloProveedor = modeloProveedor;
	}

	public String getDescrColor() {
		return descrColor;
	}

	public void setDescrColor(String descrColor) {
		this.descrColor = descrColor;
	}

	 public Long getSubPosicion() {
		return subPosicion;
	}

	public void setSubPosicion(Long subPosicion) {
		this.subPosicion = subPosicion;
	}

	
	
	
}
