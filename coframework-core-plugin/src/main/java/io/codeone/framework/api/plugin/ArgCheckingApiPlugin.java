package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiConstants;
import io.codeone.framework.api.request.ApiParam;
import io.codeone.framework.api.util.ApiConversionService;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.TargetMethod;

import javax.annotation.Resource;

/**
 * {@code ArgCheckingApiPlugin} will execute {@link ApiParam#checkArgs()} on all
 * arguments of an API (service/method annotated by {@link API}) call if the
 * type of those parameters is {@link ApiParam}.
 *
 * @see API
 * @see ApiParam
 */
@Plug(value = Stages.ARG_INTERCEPTING, group = ApiConstants.PLUGIN_GROUP)
public class ArgCheckingApiPlugin implements Plugin {

    @Resource
    private ApiConversionService apiConversionService;

    /**
     * Executes {@link ApiParam#checkArgs()} on all arguments. If any exception
     * is thrown, the execution of the plugin chain as well as the target method
     * will be aborted earlier.
     *
     * <p>{@inheritDoc}
     */
    @Override
    public void before(TargetMethod targetMethod, Object[] args) {
        for (Object arg : args) {
            ApiParam apiParam = apiConversionService.convert(arg, ApiParam.class);
            if (apiParam != null) {
                apiParam.checkArgs();
            }
        }
    }
}
