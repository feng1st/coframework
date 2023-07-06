package io.codeone.framework.api.plugins;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;
import io.codeone.framework.request.ApiParam;

/**
 * ArgCheckingApiPlugin will execute the checkArgs() method of 'ApiParam' type
 * args, upon the invocation of a method of a service, which is annotated by
 * '@API'.
 *
 * @see API
 * @see ApiParam
 */
@Plug(value = Stages.ARG_VALIDATION, group = ApiConstants.PLUGIN_GROUP)
public class ArgCheckingApiPlugin implements Plugin {

    @Override
    public void before(TargetMethod targetMethod, Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ApiParam) {
                ((ApiParam) arg).checkArgs();
            }
        }
    }
}
