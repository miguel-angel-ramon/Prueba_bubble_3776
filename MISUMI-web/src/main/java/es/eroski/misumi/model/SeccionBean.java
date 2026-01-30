/**
 * 
 */
package es.eroski.misumi.model;

/**
 * Bean de maniobra para la carga de optiones en el combo del popup de referencias de la busqueda alfanumerica 
 * @author BICUGUAL
 *
 */
public class SeccionBean {

	//Codigo de seccion (value del option)
	public Long codigo;
	//Descripcion (label del option)
	public String descripcion;
	/*
	 * P-52083
	 * Codigo del area al que pertenece (atributo incluido en el data del option para evitar una nueva consulta)
	 * @author BICUGUAL
	 */
	public Long codArea;
		
	public SeccionBean() {
		super();
	}
	
	public SeccionBean(Long codigo, String descripcion, Long codArea) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.codArea = codArea;
	}

	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getCodArea() {
		return codArea;
	}
	public void setCodArea(Long codArea) {
		this.codArea = codArea;
	}
	
}
