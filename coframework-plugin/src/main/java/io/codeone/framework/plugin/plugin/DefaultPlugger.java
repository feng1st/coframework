package io.codeone.framework.plugin.plugin;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Makes {@link EnablePlugin} a plugin enabler, which enables all plugins
 * specified by {@link EnablePlugin#value()} to a specific method.
 */
@Component
public class DefaultPlugger extends AnnotationMethodPlugger<EnablePlugin> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<EnablePlugin> getAnnotationType() {
        return EnablePlugin.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Plugging> getPluggingList(Method method, EnablePlugin annotation) {
        return Collections.singletonList(ClassPlugging.of(annotation.value()));
    }
}
