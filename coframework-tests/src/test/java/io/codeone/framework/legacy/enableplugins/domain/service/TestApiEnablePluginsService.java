package io.codeone.framework.legacy.enableplugins.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.legacy.enableplugins.domain.param.MyBizParam;
import io.codeone.framework.legacy.enableplugins.ext.plugin.InitMyParamTestPlugin;
import io.codeone.framework.plugin.binding.EnablePlugin;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiEnablePluginsService {

    public Result<Long> noArgIniting(MyBizParam param) {
        return Result.success(param.getId());
    }

    @EnablePlugin(InitMyParamTestPlugin.class)
    public Result<Long> argIniting(MyBizParam param) {
        return Result.success(param.getId());
    }
}
