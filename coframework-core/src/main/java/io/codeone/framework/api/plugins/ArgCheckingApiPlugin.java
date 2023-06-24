package io.codeone.framework.api.plugins;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import io.codeone.framework.request.ApiParam;
import org.springframework.stereotype.Component;

/**
 * ArgCheckingApiPlugin will execute the checkArgs() method of 'ApiParam' type
 * args, upon the invocation of a method of a service, which is annotated by
 * '@API'.
 *
 * @see API
 * @see ApiParam
 */
@Component
@Plug(Stages.ARG_VALIDATING)
public class ArgCheckingApiPlugin implements ApiPlugin<Void> {

    @Override
    public void before(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
