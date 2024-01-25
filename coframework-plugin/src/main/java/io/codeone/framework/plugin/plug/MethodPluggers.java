package io.codeone.framework.plugin.plug;

import io.codeone.framework.plugin.aop.PluggedMethodInterceptor;
import io.codeone.framework.plugin.aop.PluggedPointcut;
import io.codeone.framework.plugin.factory.PluginChainFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The composite of all {@link MethodPlugger}s registered in the system. And
 * this is the {@code MethodPlugger} used by the framework, to be specific, the
 * {@link PluggedPointcut}, the {@link PluggedMethodInterceptor} and the
 * {@link PluginChainFactory}, to actually decide should a specific method be
 * intercepted and what plugins should be used for the interception.
 */
@Component
public class MethodPluggers implements MethodPlugger {

    @Resource
    private ApplicationContext applicationContext;

    private final List<MethodPlugger> pluggers = new ArrayList<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(MethodPlugger.class).values()
                .forEach(this::registerPlugger);
    }

    private void registerPlugger(MethodPlugger plugger) {
        if (plugger == this) {
            return;
        }
        pluggers.add(plugger);
    }

    /**
     * Combines and returns all {@link Plugging}s returned by composed
     * {@link MethodPlugger}s, that is, all {@code MethodPlugger}s registered in
     * the system.
     *
     * @param method the target method is being tested
     * @return list of {@code Plugging} which defines what plugins should be
     * plugged into the target method
     */
    @Override
    public List<Plugging> getPluggingList(Method method) {
        return pluggers.stream()
                .map(o -> o.getPluggingList(method))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
