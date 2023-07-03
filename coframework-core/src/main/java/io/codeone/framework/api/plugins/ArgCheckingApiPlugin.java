package io.codeone.framework.api.plugins;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.MethodWrap;
import io.codeone.framework.request.ApiParam;

/**
 * ArgCheckingApiPlugin will execute the checkArgs() method of 'ApiParam' type
 * args, upon the invocation of a method of a service, which is annotated by
 * '@API'.
 *
 * @see API
 * @see ApiParam
 */
@Plug(Stages.ARG_VALIDATING)
public class ArgCheckingApiPlugin implements ApiPlugin {

    @Override
    public void before(MethodWrap methodWrap, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
