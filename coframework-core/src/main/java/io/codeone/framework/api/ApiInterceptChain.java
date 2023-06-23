package io.codeone.framework.api;

import io.codeone.framework.intercept.BaseInterceptChain;
import io.codeone.framework.intercept.util.InterceptorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiInterceptChain extends BaseInterceptChain<ApiInterceptor<?>> {

    @Resource
    private ApplicationContext applicationContext;

    private final List<ApiInterceptor<?>> interceptors = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiInterceptor.class).values()
                .forEach(interceptors::add);
        InterceptorUtils.sortInterceptors(interceptors);
    }

    @Override
    protected List<ApiInterceptor<?>> getInterceptors() {
        return interceptors;
    }
}
