package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.parameter.ApiParam;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;

import java.lang.reflect.Method;

/**
 * Plugin for argument validation of methods annotated with {@link API}.
 *
 * <p>Intercepts method arguments before execution to validate any {@link ApiParam}
 * parameters.
 */
@Plug(value = Stages.ARG_INTERCEPTING, targetAnnotations = API.class)
public class ArgValidatingApiPlugin implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).validate();
            }
        }
    }
}
