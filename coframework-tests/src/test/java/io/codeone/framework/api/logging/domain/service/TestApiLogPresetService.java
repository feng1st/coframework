package io.codeone.framework.api.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.logging.domain.param.MyParam;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiLogPresetService {

    @Logging(logArgs = false, logResult = false, logException = false)
    public Result<Long> none(MyParam param) {
        return Result.success(param.getId());
    }

    @Logging
    public Result<Long> all(MyParam param) {
        return Result.success(param.getId());
    }
}
