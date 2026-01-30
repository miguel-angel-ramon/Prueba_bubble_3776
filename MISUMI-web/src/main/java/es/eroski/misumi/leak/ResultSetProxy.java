package es.eroski.misumi.leak;

import java.lang.reflect.Method;
import java.sql.ResultSet;

import javax.validation.constraints.NotNull;

/**
 * {@code ResultSet} proxy utilities for connection leaking detection.
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public final class ResultSetProxy {

    /** {@code ResultSet.close()} method name. */
    private static final String CLOSE_METHOD = "close";

    /**
     * Utility class private constructor.
     */
    private ResultSetProxy() {
        throw new UnsupportedOperationException("No instances allowed");
    }

    /**
     * Proxies the specified {@code ResultSet} instance to detected connection
     * leaking.
     * 
     * @param obj The {@code ResultSet} instance.
     * @return The proxyed {@code ResultSet} instance.
     */
    public static @NotNull ResultSet proxy(
            final @NotNull ResultSet obj) {
        return ProxyUtils.proxy(obj.getClass(), new Handler(obj));
    }

    /**
     * {@code ResultSet} proxy invocation handler that registers all {@code close()}
     * method invocations.
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     */
    private static class Handler
    extends ProxyUtils.DelegatedHandler<ResultSet> {

        /**
         * Creates a new instance.
         * 
         * @param delegate The delegated {@code ResultSet} instance.
         */
        public Handler(
                final @NotNull ResultSet delegate) {
            super(delegate);
        }

        /**
         * Registers {@code close()} method invocations.
         */
        @Override
        public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args
        ) throws Throwable {
            Object result = super.invoke(proxy, method, args);
            if (CLOSE_METHOD.equals(method.getName())) {
                JdbcLeakDetectionFilter.resultSetClosed();
            }
            return result;
        }
    }
}
