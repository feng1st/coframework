package io.codeone.framework.plugin;

import io.codeone.framework.intercept.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PluginFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Interceptor<?>> pluginMap = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(Plugin.class).values()
                .forEach(o -> pluginMap.put(o.getClass(), o));
    }

    public List<Interceptor<?>> getInterceptors(Class<?>[] classes) {
        Objects.requireNonNull(classes);
        return Arrays.stream(classes)
                .map(pluginMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
