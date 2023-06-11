package io.codeone.framework.ext.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Optional;
import java.util.function.Consumer;

public class LazyBean<T> {

    private final BeanFactory beanFactory;

    private final Class<T> clazz;

    private Optional<T> beanHolder;

    public static <T> LazyBean<T> of(BeanFactory beanFactory, Class<T> clazz) {
        return new LazyBean<>(beanFactory, clazz);
    }

    public LazyBean(BeanFactory beanFactory, Class<T> clazz) {
        this.beanFactory = beanFactory;
        this.clazz = clazz;
    }

    public T get() {
        if (beanHolder == null) {
            beanHolder = Optional.of(beanFactory.getBean(clazz));
        }
        return beanHolder.get();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (beanHolder == null) {
            try {
                beanHolder = Optional.of(beanFactory.getBean(clazz));
            } catch (NoSuchBeanDefinitionException e) {
                beanHolder = Optional.empty();
            }
        }
        beanHolder.ifPresent(consumer);
    }
}
