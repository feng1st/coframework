package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.Plugin;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Pluggers {

    @Resource
    private ApplicationContext applicationContext;

    private final List<Plugger> pluggers = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(Plugger.class).values()
                .forEach(this::registerPlugger);
    }

    private void registerPlugger(Plugger plugger) {
        pluggers.add(plugger);
    }

    public boolean isPlugged(Method method) {
        for (Plugger plugger : pluggers) {
            if (plugger.isPlugged(method)) {
                return true;
            }
        }
        return false;
    }

    public List<Plugin<?>> getPlugins(Method method) {
        return pluggers.stream()
                .map(o -> o.getPlugins(method))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
