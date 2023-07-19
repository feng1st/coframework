package io.codeone.framework.chain.node;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class NodeFactory {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<Class<?>, Node> map = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(Node.class).values()
                .forEach(o -> map.put(o.getClass(), o));
    }

    public List<Node> getNodes(List<Class<? extends Node>> nodeClasses) {
        return nodeClasses.stream()
                .map(map::get)
                .peek(Objects::requireNonNull)
                .collect(Collectors.toList());
    }
}
