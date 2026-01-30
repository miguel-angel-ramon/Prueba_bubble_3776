package es.eroski.misumi.leak;

import java.lang.reflect.Method;
import java.sql.Connection;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

/**
 * {@code DataSource} proxy utilities for connection leaking detection.
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public final class DataSourceProxy {

    /**
     * Utility class private constructor.
     */
    private DataSourceProxy() {
        throw new UnsupportedOperationException("No instances allowed");
    }

    /**
     * Proxies the specified {@code DataSource} instance to detected connection
     * leaking.
     * 
     * @param obj The {@code DataSource} instance.
     * @return The proxyed {@code DataSource} instance.
     */
    public static @NotNull DataSource proxy(
            final @NotNull DataSource obj) {
        return ProxyUtils.proxy(obj.getClass(), new Handler(obj));
    }

    /**
     * {@code DataSource} proxy invocation handler that detects all opened
     * {@code Connection} and proxies the returned instance.
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     */
    private static class Handler
    extends ProxyUtils.DelegatedHandler<DataSource> {

        /**
         * Creates a new instance.
         * 
         * @param delegate The delegated {@code DataSource} instance.
         */
        public Handler(
                final @NotNull DataSource delegate) {
            super(delegate);
        }

        /**
         * Registers and proxies returned {@code Connection} instances.
         */
        @Override
        public Object invoke(
                final @NotNull Object proxy,
                final @NotNull Method method,
                final @NotNull Object[] args
        ) throws Throwable {
            Object result = super.invoke(proxy, method, args);
            if (result instanceof Connection) {
                result = ConnectionProxy.proxy((Connection) result);
                JdbcLeakDetectionFilter.connectionOpened();
            }
            return result;
        }
    }
}
