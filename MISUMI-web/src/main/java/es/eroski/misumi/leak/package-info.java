/**
 * JDBC connection leak detection classes.
 * <p>
 * Configure {@link SpringDataSourceProxyProcessor} in Spring context
 * to proxy exposed DataSources.
 * <p>
 * Configure {@link JdbcLeakDetectionFilter} in Spring context
 * to detect JDBC connection leaks in HTTP requests.
 * <p>
 * Configure {@code es.eroski.misumi.leak} package logging to log
 * connection leaks (ERROR), untracked data source interactions (WARN),
 * life-cycle events (INFO), HTTP requests without leaks (TRACE) or
 * connection, statement or result set openning or closing (DEBUG).
 * <p>
 * An dedicated appender is recommended.
 * <p>
 * Example:
 * <pre>
 * {@literal <}configuration ...{@literal >}
 *   ...
 *   {@literal <}logger name="es.eroski.misumi.leak" level="INFO"{@literal >}
 *     {@literal <}appender-ref ref="LEAKS" /{@literal >}
 *   {@literal <}/logger{@literal >}
 *   ...
 * {@literal <}/configuration{@literal >}
 * </pre>
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 * @see SpringDataSourceProxyProcessor
 * @see JdbcLeakDetectionFilter
 */
package es.eroski.misumi.leak;
