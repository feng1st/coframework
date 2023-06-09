package io.codeone.framework.ext.proxy;

import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ExtProxyBeanFactory {

    public static final String FACTORY_METHOD_NAME = "getProxy";

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(BeanFactory beanFactory, Class<T> extensibleClass) {
        ClassLoader classLoader = extensibleClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{extensibleClass};
        InvocationHandler invocationHandler = new ExtProxyInvocationHandler<>(beanFactory, extensibleClass);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
