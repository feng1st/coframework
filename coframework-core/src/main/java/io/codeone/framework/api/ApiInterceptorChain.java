package io.codeone.framework.api;

import io.codeone.framework.intercept.InterceptorChain;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ApiInterceptorChain extends InterceptorChain<ApiInterceptor<?>> {

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiInterceptor.class).values()
                .forEach(super::addInterceptor);
        sortInterceptors();
    }
}
