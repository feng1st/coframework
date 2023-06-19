package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.LoggingPresets;
import io.codeone.framework.logging.domain.param.MyParam;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestLogPresetService {

    @Logging(LoggingPresets.NONE)
    public Result<Long> none(MyParam param) {
        return Result.success(param.getId());
    }

    @Logging(LoggingPresets.ALL)
    public Result<Long> all(MyParam param) {
        return Result.success(param.getId());
    }
}
