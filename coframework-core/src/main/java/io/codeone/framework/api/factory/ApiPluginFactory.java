package io.codeone.framework.api.factory;

import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.plugin.Plugin;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiPluginFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final List<Plugin<?>> plugins = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiPlugin.class).values()
                .forEach(plugins::add);
    }

    public List<Plugin<?>> getPlugins() {
        return plugins;
    }
}
