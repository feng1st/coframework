package io.codeone.framework.plugin.plug.impl;

import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class DefaultPlugger extends AnnotationMethodPlugger<EnablePlugin> {

    @Override
    protected Class<EnablePlugin> getAnnotationType() {
        return EnablePlugin.class;
    }

    @Override
    protected List<Plugging> getPluggingList(Method method, EnablePlugin annotation) {
        return Plugging.asList(ClassPlugging.of(annotation.value()));
    }
}
