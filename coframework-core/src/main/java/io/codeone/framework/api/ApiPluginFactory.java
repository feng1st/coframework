package io.codeone.framework.api;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

// FIXME
@Component
public class ApiPluginFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final List<ApiPlugin<?>> plugins = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiPlugin.class).values()
                .forEach(plugins::add);
    }

    public List<ApiPlugin<?>> getApiPlugins() {
        return plugins;
    }
}
