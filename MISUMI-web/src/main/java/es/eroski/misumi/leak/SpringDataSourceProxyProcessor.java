package es.eroski.misumi.leak;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Spring {@code BeanPostProcessor} that proxy Spring context
 * {@code DataSource} beans.
 * <p>
 * Add to Spring context where the data sources are exposed.
 * <p>
 * Example:
 * <pre>
 * {@literal <}beans ...{@literal >}
 *     ...
 *     {@literal <}bean class="es.eroski.misumi.leak.SpringDataSourceProxyProcessor" /{@literal >}
 *     ...
 *     {@literal <}jee:jndi-lookup id="dataSource" jndi-name="java:jboss/datasources/myDataSource" resource-ref="false" /{@literal >}
 *     ...
 * {@literal <}/beans{@literal >}
 * </pre>
 * 
 * @author <a href="mailto:ihernaez@ayesa.com">(w) Iker Hernaez</a>
 * @version 1.0, 2025-05
 */
//@Component
public class SpringDataSourceProxyProcessor
implements BeanPostProcessor {

    /** The class logger. */
    private static final Logger LOG = LoggerFactory.getLogger(SpringDataSourceProxyProcessor.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessBeforeInitialization(
            final Object bean,
            final String beanName) {
        return bean;
    }

    /**
     * Creates a JDBC connection leak detection proxy for {@code DataSource}
     * beans exposed in the Spring context.
     */
    @Override
    public Object postProcessAfterInitialization(
            final Object bean,
            final String beanName) {
        if (bean instanceof DataSource) {
            final DataSource proxy = DataSourceProxy.proxy((DataSource) bean);
            LOG.info("Created connection leak detection proxy for DataSource '{}'", beanName);
            return proxy;
        } else {
            return bean;
        }
    }
}
