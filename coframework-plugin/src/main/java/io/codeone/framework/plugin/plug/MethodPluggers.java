package io.codeone.framework.plugin.plug;

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
        pluggers.add(plugger);
    }

    @Override
    public List<Plugging> getPluggingList(Method method) {
        return pluggers.stream()
                .map(o -> o.getPluggingList(method))
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
