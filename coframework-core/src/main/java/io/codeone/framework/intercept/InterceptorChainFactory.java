package io.codeone.framework.intercept;

import io.codeone.framework.api.ApiInterceptorFactory;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.PluginFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InterceptorChainFactory {

    @Resource
    private ApiInterceptorFactory apiInterceptorFactory;

    @Resource
    private PluginFactory pluginFactory;

    private InterceptorChain apiInterceptorChain;

    private final Map<Method, InterceptorChain> methodPluginMap
            = new ConcurrentHashMap<>();

    private final Map<Class<?>, InterceptorChain> classPluginMap
            = new ConcurrentHashMap<>();

    public InterceptorChain getApiInterceptorChain() {
        InterceptorChain chain;
        if ((chain = apiInterceptorChain) == null) {
            synchronized (this) {
                if ((chain = apiInterceptorChain) == null) {
                    chain = new InterceptorChain(
                            apiInterceptorFactory.getApiInterceptors());
                    apiInterceptorChain = chain;
                }
            }
        }
        return chain;
    }

    public InterceptorChain getPluginChainOfMethod(
            Method method, Class<? extends Plugin<?>>[] pluginClasses,
            boolean isApi) {
        return methodPluginMap.computeIfAbsent(method,
                k -> getPluginChain(pluginClasses, isApi));
    }

    public InterceptorChain getPluginChainOfClass(
            Class<?> clazz, Class<? extends Plugin<?>>[] pluginClasses,
            boolean isApi) {
        return classPluginMap.computeIfAbsent(clazz,
                k -> getPluginChain(pluginClasses, isApi));
    }

    private InterceptorChain getPluginChain(
            Class<? extends Plugin<?>>[] pluginClasses, boolean isApi) {
        List<Interceptor<?>> interceptors = new ArrayList<>();
        interceptors.addAll(pluginFactory.getPlugins(pluginClasses));
        if (isApi) {
            interceptors.addAll(apiInterceptorFactory.getApiInterceptors());
        }
        return new InterceptorChain(interceptors);
    }
}
