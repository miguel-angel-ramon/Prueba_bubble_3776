package es.eroski.misumi.leak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger utilitario para eventos relacionados con fugas JDBC.
 *
 * <p>Registra eventos con niveles específicos y mensajes en castellano:
 * <ul>
 *   <li>SEVERE - Fugas de conexión</li>
 *   <li>WARNING - Uso del datasource sin seguimiento</li>
 *   <li>INFO - Eventos de ciclo de vida</li>
 *   <li>FINEST - Peticiones HTTP sin fugas</li>
 *   <li>FINE / FINER - Apertura o cierre de recursos JDBC</li>
 * </ul>
 */
public final class LeakLogger {

    private static final Logger LOG = LoggerFactory.getLogger(LeakLogger.class);

    private LeakLogger() {
        throw new UnsupportedOperationException("No se permite instanciar esta clase");
    }

    // ===== CONEXIONES FUGADAS (ERROR) =====
    public static void logConexionFuga(String mensaje) {
        LOG.error(mensaje);
    }

    public static void logConexionFuga(String mensaje, Throwable error) {
        LOG.error(mensaje, error);
    }

    // ===== USO NO RASTREADO DEL DATASOURCE (WARN) =====
    public static void logUsoNoRastreado(String mensaje) {
        LOG.warn(mensaje);
    }

    public static void logUsoNoRastreado(String mensaje, Throwable error) {
        LOG.warn(mensaje, error);
    }

    // ===== EVENTOS DEL CICLO DE VIDA (INFO) =====
    public static void logEventoCicloVida(String mensaje) {
        LOG.info(mensaje);
    }

    public static void logEventoCicloVida(String mensaje, Throwable error) {
        LOG.info(mensaje, error);
    }

    // ===== PETICIONES SIN FUGAS (TRACE) =====
    public static void logPeticionHttpSinFugas(String mensaje) {
        LOG.trace(mensaje);
    }

    public static void logPeticionHttpSinFugas(String mensaje, Throwable error) {
        LOG.trace(mensaje, error);
    }

    // ===== APERTURA O CIERRE DE CONEXIONES, STATEMENTS O RESULTSETS (DEBUG) =====
    public static void logConexionAbierta(String mensaje) {
        LOG.debug(mensaje);
    }

    public static void logConexionAbierta(String mensaje, Throwable error) {
        LOG.debug(mensaje, error);
    }

    public static void logRecursoCerrado(String mensaje) {
        LOG.debug(mensaje);
    }

    public static void logRecursoCerrado(String mensaje, Throwable error) {
        LOG.debug(mensaje, error);
    }   
}