package io.codeone.framework.api.enableplugins.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.enableplugins.domain.param.MyParam;
import io.codeone.framework.api.enableplugins.ext.plugin.InitMyParamTestPlugin;
import io.codeone.framework.plugin.EnablePlugin;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiEnablePluginsService {

    public Result<Long> noArgIniting(MyParam param) {
        return Result.success(param.getId());
    }

    @EnablePlugin(InitMyParamTestPlugin.class)
    public Result<Long> argIniting(MyParam param) {
        return Result.success(param.getId());
    }
}
