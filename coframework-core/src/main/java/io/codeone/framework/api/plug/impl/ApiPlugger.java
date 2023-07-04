package io.codeone.framework.api.plug.impl;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.logging.aop.LoggingPlugin;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.GroupPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class ApiPlugger extends AnnotationMethodPlugger<API> {

    @Override
    protected Class<API> getAnnotationType() {
        return API.class;
    }

    @Override
    protected List<Plugging> getPluggingList(Method method, API annotation) {
        return Plugging.asList(GroupPlugging.of(ApiPlugin.GROUP),
                ClassPlugging.of(LoggingPlugin.class));
    }
}
