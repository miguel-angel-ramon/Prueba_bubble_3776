package es.eroski.misumi.leak;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;

import javax.validation.constraints.NotNull;

/**
 * {@code Connection} proxy utilities for connection leaking detection.
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public class ConnectionProxy {

    /** {@code Connection.close()} method name. */
    private static final String CLOSE_METHOD = "close";

    /**
     * Utility class private constructor.
     */
    private ConnectionProxy() {
        throw new UnsupportedOperationException("No instances allowed");
    }

    /**
     * Proxies the specified {@code Connection} instance to detected connection
     * leaking.
     * 
     * @param obj The {@code Connection} instance.
     * @return The proxyed {@code Connection} instance.
     */
    public static @NotNull Connection proxy(
            final @NotNull Connection obj) {
        return ProxyUtils.proxy(obj.getClass(), new Handler(obj));
    }

    /**
     * {@code Connection} proxy invocation handler that registers all {@code close()}
     * method invocations.
     * Also detects all opened {@code Statement} and proxies the returned instance.
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     */
    private static class Handler
    extends ProxyUtils.DelegatedHandler<Connection> {

        /**
         * Creates a new instance.
         * 
         * @param delegate The delegated {@code Connection} instance.
         */
        public Handler(
                final @NotNull Connection delegate) {
            super(delegate);
        }

        /**
         * Registers {@code close()} method invocations.
         * Registers and proxies returned {@code Statement} instances.
         */
        @Override
        public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args
        ) throws Throwable {
            Object result = super.invoke(proxy, method, args);
            if (CLOSE_METHOD.equals(method.getName())) {
                JdbcLeakDetectionFilter.connectionClosed();
            } else if (result instanceof Statement) {
                result = StatementProxy.proxy((Statement) result);
                JdbcLeakDetectionFilter.statementOpened();
            }
            return result;
        }
    }
}
