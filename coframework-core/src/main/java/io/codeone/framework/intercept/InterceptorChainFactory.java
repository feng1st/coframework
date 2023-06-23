package io.codeone.framework.intercept;

import io.codeone.framework.api.ApiInterceptorFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Component
public class InterceptorChainFactory {

    @Resource
    private ApiInterceptorFactory apiInterceptorFactory;

    private InterceptorChain apiInterceptorChain;

    private final Map<Method, InterceptorChain> methodMap
            = new ConcurrentHashMap<>();

    private final Map<Class<?>, InterceptorChain> classMap
            = new ConcurrentHashMap<>();

    public InterceptorChain getApiInterceptorChain() {
        InterceptorChain chain;
        if ((chain = apiInterceptorChain) == null) {
            synchronized (this) {
                if ((chain = apiInterceptorChain) == null) {
                    chain = new InterceptorChain(apiInterceptorFactory.getInterceptors());
                    apiInterceptorChain = chain;
                }
            }
        }
        return chain;
    }

    public InterceptorChain getPluginChain(Method method,
                                           Supplier<List<Interceptor<?>>> interceptorsSupplier) {
        return methodMap.computeIfAbsent(method, k -> new InterceptorChain(interceptorsSupplier.get()));
    }

    public InterceptorChain getPluginChain(Class<?> clazz,
                                           Supplier<List<Interceptor<?>>> interceptorsSupplier) {
        return classMap.computeIfAbsent(clazz, k -> new InterceptorChain(interceptorsSupplier.get()));
    }
}
