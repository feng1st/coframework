package io.codeone.framework.ext.extensible;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Factory bean for creating proxies of {@code Extensible} interfaces.
 *
 * <p>Proxies created by this factory dynamically route method calls to the appropriate
 * {@code Extension} implementation based on the current {@code BizScenario}.
 *
 * @param <T> the type of the extensible interface
 */
public class ExtensibleProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> extensibleInterface;

    private final BeanFactory beanFactory;

    /**
     * Constructs a factory bean for the given extensible interface.
     *
     * @param extensibleInterface the extensible interface to proxy
     * @param beanFactory         the Spring bean factory for dependency resolution
     */
    public ExtensibleProxyFactoryBean(Class<T> extensibleInterface, BeanFactory beanFactory) {
        this.extensibleInterface = extensibleInterface;
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a proxy instance for the extensible interface.
     *
     * @return the proxy instance
     * @throws Exception if the proxy cannot be created
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        ClassLoader classLoader = extensibleInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{extensibleInterface};
        InvocationHandler invocationHandler = beanFactory.getBean(ExtensibleInvocationHandler.class, extensibleInterface);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    /**
     * Returns the type of the object created by this factory.
     *
     * @return the type of the extensible interface
     */
    @Override
    public Class<?> getObjectType() {
        return extensibleInterface;
    }
}
