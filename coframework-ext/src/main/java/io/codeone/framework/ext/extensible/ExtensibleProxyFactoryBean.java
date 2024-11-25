package io.codeone.framework.ext.extensible;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ExtensibleProxyFactoryBean<T> implements FactoryBean<T> {

    private final Class<T> extensibleInterface;

    private final BeanFactory beanFactory;

    public ExtensibleProxyFactoryBean(Class<T> extensibleInterface, BeanFactory beanFactory) {
        this.extensibleInterface = extensibleInterface;
        this.beanFactory = beanFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        ClassLoader classLoader = extensibleInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{extensibleInterface};
        InvocationHandler invocationHandler = beanFactory.getBean(ExtensibleInvocationHandler.class, extensibleInterface);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return extensibleInterface;
    }
}
