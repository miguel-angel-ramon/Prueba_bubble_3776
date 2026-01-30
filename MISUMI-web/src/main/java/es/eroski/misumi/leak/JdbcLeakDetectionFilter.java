package es.eroski.misumi.leak;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter for JDBC connections leaks detection.
 * Logs HTTP request information when JDBC connections
 * have been opened but not closed when HTTP request ends.
 * <p>
 * Add to web application descriptor (web.xml).
 * <p>
 * Example:
 * <pre>
 * {@literal <}web-app ...{@literal >}
 *   {@literal <}filter{@literal >}
 *     {@literal <}filter-name{@literal >}leakDetectionFilter{@literal <}/filter-name{@literal >}
 *     {@literal <}filter-class{@literal >}es.eroski.misumi.leak.JdbcLeakDetectionFilter{@literal <}/filter-class{@literal >}
 *   {@literal <}/filter{@literal >}
 *   {@literal <}filter-mapping{@literal >}
 *     {@literal <}filter-name{@literal >}leakFilter{@literal <}/filter-name{@literal >}
 *     {@literal <}url-pattern{@literal >}/*{@literal <}/url-pattern{@literal >}
 *   {@literal <}/filter-mapping{@literal >}
 * {@literal <}/web-app{@literal >}
 * </pre>
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public class JdbcLeakDetectionFilter
extends OncePerRequestFilter {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(JdbcLeakDetectionFilter.class);

    /** The connection leak data by thread. */
    private static final ThreadLocal<RequestData> LOCAL = new ThreadLocal<RequestData>();

    /**
     * Filter the HTTP request to detect leaked JDBC connections.
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
    throws ServletException, IOException {
        final RequestData data = new RequestData();
        LOCAL.set(data);
        try {
            filterChain.doFilter(request, response);
        } finally {
            logLeaks(request, data);
            LOCAL.remove();
        }
    }

    /**
     * Registers an opened JDBC connection on current thread.
     */
    public static void connectionOpened() {
        try {
            requireData().openConnections.incrementAndGet();
//            LOG.debug("Connection opened");
            LeakLogger.logConexionAbierta("Conexi\u00f3n abierta.");
        } catch (final IllegalStateException e) {
//            LOG.warn("Connection opened in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("Conexi\u00f3n abierta en el hilo, no filtrada.", e);
        }
    }

    /**
     * Registers an opened JDBC statement on current thread.
     */
    public static void statementOpened() {
        try {
            requireData().openStatements.incrementAndGet();
//            LOG.debug("Statement created");
            LeakLogger.logConexionAbierta("Sentencia creada.");
        } catch (final IllegalStateException e) {
//            LOG.warn("Statement opened in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("Sentencia abierta en un hilo, no filtrado.",e);
        }
    }

    /**
     * Registers an opened JDBC result set on current thread.
     */
    public static void resultSetOpened() {
        try {
            requireData().openResultSets.incrementAndGet();
//            LOG.debug("ResultSet created");
            LeakLogger.logConexionAbierta("ResultSet creado.");
        } catch (final IllegalStateException e) {
//            LOG.warn("ResultSet opened in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("ResultSet abierto en un hilo, no filtrado.", e);
        }
    }

    /**
     * Registers an closed JDBC result set on current thread.
     */
    public static void resultSetClosed() {
        try {
            requireData().closedResultSets.incrementAndGet();
//            LOG.debug("ResultSet closed");
            LeakLogger.logConexionAbierta("ResultSet cerrado.");
        } catch (final IllegalStateException e) {
//            LOG.warn("ResultSet closed in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("ResultSet cerrado en un hilo, no filtrado.", e);
        }
    }

    /**
     * Registers an closed JDBC statement on current thread.
     */
    public static void statementClosed() {
        try {
            requireData().closedStatements.incrementAndGet();
//            LOG.debug("Statement closed");
            LeakLogger.logConexionAbierta("Sentencia cerrada.");
        } catch (final IllegalStateException e) {
//            LOG.warn("Statement closed in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("Sentencia cerrada en un hilo, no filtrada.", e);
        }
    }

    /**
     * Registers an closed JDBC connection on current thread.
     */
    public static void connectionClosed() {
        try {
            requireData().closedConnections.incrementAndGet();
//            LOG.debug("Connection closed");
            LeakLogger.logRecursoCerrado("Conexi\u00f3n cerrada.");
        } catch (final IllegalStateException e) {
//            LOG.warn("Connection closed in non filtered thread", e);
            LeakLogger.logUsoNoRastreado("Conexi\u00f3n cerrada en un hilo, no filtrada.", e);
        }
    }

    /**
     * Returns the connection leak data of the current thread.
     * 
     * @return The connection leak data of the current thread.
     * @throws IllegalStateException If no connection leak data has been
     * registered for the current thread.
     */
    protected static @NotNull RequestData requireData() {
        final RequestData data = LOCAL.get();
        if (data == null) {
            throw new IllegalStateException("Hilo NO filtrado.");
        }
        return data;
    }

    /**
     * Logs any JDBC connection leaks on specified HTTP request.
     * 
     * @param request The HTTP request.
     * @param data The connection leak data.
     */
    protected void logLeaks(
            final @NotNull HttpServletRequest request,
            final @NotNull RequestData data) {
        boolean unclosedConnections = data.openConnections.get() != data.closedConnections.get();
        boolean unclosedStatements = data.openStatements.get() != data.closedStatements.get();
        boolean unclosedResultSets = data.openResultSets.get() != data.closedResultSets.get();
        boolean leaks = unclosedConnections || unclosedStatements || unclosedResultSets;
        if (leaks) {
            if (unclosedConnections) {
                LOG.error(
                        "Conexi\u00f3n CON leak [{}/{}]: {} {} {}",
                        data.closedConnections.get(),
                        data.openConnections.get(),
                        request.getMethod(),
                        request.getRequestURI(),
                        request.getQueryString());
            }
            if (unclosedStatements) {
                LOG.error(
                        "Sentencia CON leak [{}/{}]: {} {} {}",
                        data.closedStatements.get(),
                        data.openStatements.get(),
                        request.getMethod(),
                        request.getRequestURI(),
                        request.getQueryString());
            }
            if (unclosedResultSets) {
                LOG.error(
                        "ResultSet CON leak [{}/{}]: {} {} {}",
                        data.closedResultSets.get(),
                        data.openResultSets.get(),
                        request.getMethod(),
                        request.getRequestURI(),
                        request.getQueryString());
            }
        } else {
            LOG.trace(
                    "Petici\u00f3n SIN leaks [{}/{} {}/{} {}/{}]: {} {} {}",
                    data.closedConnections.get(),
                    data.openConnections.get(),
                    data.closedStatements.get(),
                    data.openStatements.get(),
                    data.closedResultSets.get(),
                    data.openResultSets.get(),
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getQueryString());
        }
    }

    /**
     * JDBC connection leak data for a tread.
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     */
    private static class RequestData {

        /** The JDBC connections opened by current thread. */
        private final AtomicInteger openConnections = new AtomicInteger(0);
        /** The JDBC connections closed by current thread. */
        private final AtomicInteger closedConnections = new AtomicInteger(0);
        /** The JDBC statements opened by current thread. */
        private final AtomicInteger openStatements = new AtomicInteger(0);
        /** The JDBC statements closed by current thread. */
        private final AtomicInteger closedStatements = new AtomicInteger(0);
        /** The JDBC result set opened by current thread. */
        private final AtomicInteger openResultSets = new AtomicInteger(0);
        /** The JDBC result set closed by current thread. */
        private final AtomicInteger closedResultSets = new AtomicInteger(0);
    }
}
