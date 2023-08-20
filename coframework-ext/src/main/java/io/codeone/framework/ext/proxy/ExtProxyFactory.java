package io.codeone.framework.ext.proxy;

import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * The factory to create Extension-routing-and-executing proxy for Extensible
 * interface.
 */
public class ExtProxyFactory {

    /**
     * Name of the {@link #getProxy(BeanFactory, Class)}.
     */
    public static final String FACTORY_METHOD_NAME = "getProxy";

    /**
     * Creates a new Extension-routing-and-executing proxy of the specified
     * Extensible interface.
     *
     * @param beanFactory     the spring bean container
     * @param extensibleClass the target Extensible interface
     * @param <T>             type of the target Extensible interface
     * @return an Extension-routing-and-executing proxy of the Extensible
     * interface
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(BeanFactory beanFactory, Class<T> extensibleClass) {
        ClassLoader classLoader = extensibleClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{extensibleClass};
        InvocationHandler invocationHandler = beanFactory.getBean(ExtProxyInvocationHandler.class, extensibleClass);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
