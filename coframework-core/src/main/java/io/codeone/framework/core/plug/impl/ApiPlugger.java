package io.codeone.framework.core.plug.impl;

import io.codeone.framework.core.API;
import io.codeone.framework.core.ApiConstants;
import io.codeone.framework.logging.aop.LoggingPlugin;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.plug.AnnotationMethodPlugger;
import io.codeone.framework.plugin.plug.ClassPlugging;
import io.codeone.framework.plugin.plug.GroupPlugging;
import io.codeone.framework.plugin.plug.Plugging;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Makes {@link API} a plugin enabler, which enables all plugins with
 * {@link ApiConstants#PLUGIN_GROUP} as {@link Plug#group()}'s value, as well as
 * the individual {@link LoggingPlugin} to a specific method.
 */
@Component
public class ApiPlugger extends AnnotationMethodPlugger<API> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<API> getAnnotationType() {
        return API.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<Plugging> getPluggingList(Method method, API annotation) {
        return Arrays.asList(GroupPlugging.of(ApiConstants.PLUGIN_GROUP),
                ClassPlugging.of(LoggingPlugin.class));
    }
}
