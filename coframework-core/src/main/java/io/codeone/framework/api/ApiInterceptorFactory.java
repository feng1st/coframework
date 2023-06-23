package io.codeone.framework.api;

import io.codeone.framework.intercept.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiInterceptorFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final List<Interceptor<?>> interceptors = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiInterceptor.class).values()
                .forEach(interceptors::add);
    }

    public List<Interceptor<?>> getInterceptors() {
        return interceptors;
    }
}
