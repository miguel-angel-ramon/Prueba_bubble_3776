package es.eroski.misumi.dao.iface;

public interface PkAprMisumiImcDao {
	
	/**
	 * Consulta si la porcion consumidor es de peso variable.
	 * @param codArticulo Codigo de articulo
	 * @return "S" si es de peso variable.
	 */
	public String consultarPcPesoVariable(Long codArticulo);

	/**
	 * El metodo devuelve el nombre del boton consultando a un paquete de SIA.
	 * En caso de nulo el paquete siempre pretende devolver un valor.
	 * @param codCentro
	 * @return Siempre se espera devolver valor no nulo.
	 */
	public String obtenerMetodosBoton(Long codCentro);
}
