package es.eroski.misumi.leak;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.validation.constraints.NotNull;

/**
 * {@code Statement} proxy utilities for connection leaking detection.
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public final class StatementProxy {

    /** {@code Statement.close()} method name. */
    private static final String CLOSE_METHOD = "close";

    /**
     * Utility class private constructor.
     */
    private StatementProxy() {
        throw new UnsupportedOperationException("No instances allowed");
    }

    /**
     * Proxies the specified {@code Statement} instance to detected connection
     * leaking.
     * 
     * @param obj The {@code Statement} instance.
     * @return The proxyed {@code Statement} instance.
     */
    public static @NotNull Statement proxy(
            final @NotNull Statement obj) {
        return ProxyUtils.proxy(obj.getClass(), new Handler(obj));
    }

    /**
     * {@code Statement} proxy invocation handler that registers all {@code close()}
     * method invocations.
     * Also detects all opened {@code ResultSet} and proxies the returned instance.
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     */
    private static class Handler
    extends ProxyUtils.DelegatedHandler<Statement> {

        /**
         * Creates a new instance.
         * 
         * @param delegate The delegated {@code Statement} instance.
         */
        public Handler(
                final @NotNull Statement delegate) {
            super(delegate);
        }

        /**
         * Registers {@code close()} method invocations.
         * Registers and proxies returned {@code ResultSet} instances.
         */
        @Override
        public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args
        ) throws Throwable {
            Object result = super.invoke(proxy, method, args);
            if (CLOSE_METHOD.equals(method.getName())) {
                JdbcLeakDetectionFilter.statementClosed();
            } else if (result instanceof ResultSet) {
                result = ResultSetProxy.proxy((ResultSet) result);
                JdbcLeakDetectionFilter.resultSetOpened();
            }
            return result;
        }
    }
}
