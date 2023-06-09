package io.codeone.framework.ext.util;

import org.springframework.beans.factory.BeanFactory;

public class LazyBean<T> {

    private final BeanFactory beanFactory;

    private final Class<T> clazz;

    private T bean;

    public LazyBean(BeanFactory beanFactory, Class<T> clazz) {
        this.beanFactory = beanFactory;
        this.clazz = clazz;
    }

    public T get() {
        if (bean == null) {
            bean = beanFactory.getBean(clazz);
        }
        return bean;
    }
}
