package es.eroski.misumi.leak;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.ClassUtils;

/**
 * Utilities for proxy instances construction.
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
public final class ProxyUtils {

    /** Cache of class interfaces. */
    private static final WeakHashMap<Class<?>, Class<?>[]> IFACES_CACHE =
            new WeakHashMap<Class<?>, Class<?>[]>();

    /**
     * Utility class private constructor.
     */
    private ProxyUtils() {
        throw new UnsupportedOperationException("No instances allowed");
    }

    /**
     * Retrieves all the interfaces implemented by the specified type.
     * 
     * @param type The type to extract the interfaces from.
     * @return All the interfaces implemented by the specified type.
     */
    private static synchronized @NotNull Class<?>[] getInterfaces(
            final @NotNull Class<?> type) {
        Class<?>[] result = IFACES_CACHE.get(type);
        if (result == null) {
            @SuppressWarnings("unchecked")
            final List<Class<?>> allIfaces = ClassUtils.getAllInterfaces(type);
            final ClassLoader cl = Thread.currentThread().getContextClassLoader();

            final List<Class<?>> visibleIfaces = new ArrayList<Class<?>>();
            for (Class<?> iface : allIfaces) {
                try {
                    // Intentar cargar la interface para chequear su visibilidad.
                    Class<?> loaded = Class.forName(iface.getName(), false, cl);
                    if (loaded == iface) {
                        visibleIfaces.add(iface);
                    }
                } catch (ClassNotFoundException e) {
                    // Interface no visible, nos la saltamos.
                } catch (LinkageError le){
                    // Interface no visible, nos la saltamos.
                }
            }

            result = visibleIfaces.toArray(new Class<?>[0]);
            IFACES_CACHE.put(type, result);
        }
        return result;
    }

    /**
     * Creates a new proxy of the specified type
     * 
     * @param <T> The expected interface type.
     * @param type The target class type.
     * @param handler The invocation handler.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static @NotNull <T> T proxy(
            final @NotNull Class<? extends T> type,
            final @NotNull InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                getInterfaces(type),
                handler);
    }

    /**
     * Invocation handler that by delegates all method invocations
     * to the delegated instance. 
     * 
     * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
     * @version 1.0, 2025-05
     * @param <T> The type of the delegated instance.
     */
    public static class DelegatedHandler<T>
    implements InvocationHandler {

        /** The delegated instance. */
        private final @NotNull T delegated;

        /**
         * Creates a new instance.
         * 
         * @param delegated The delegated instance.
         */
        public DelegatedHandler(
                final @NotNull T delegated) {
            super();
            this.delegated = delegated;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args)
        throws Throwable {
            return method.invoke(this.delegated, args);
        }
    }
}
