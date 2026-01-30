package es.eroski.misumi.exception;

/**
 * Clase que permite crear excepciones personalizadas para el mensaje que sea necesario.
 * @author BICAGAAN
 *
 */
public class CustomException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor sin argumentos
    public CustomException() {
        super();
    }

    // Constructor con mensaje de error
    public CustomException(String mensaje) {
        super(mensaje);
    }

    // Constructor con mensaje de error y causa
    public CustomException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // Constructor con causa
    public CustomException(Throwable causa) {
        super(causa);
    }
}
