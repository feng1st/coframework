package io.codeone.framework.legacy.domain.service;

import io.codeone.framework.api.response.Result;
import io.codeone.framework.legacy.domain.param.MyParam;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Logging
@Service
public class TestLogPresetService {

    @Logging(logArgs = false, logResult = false, logException = false)
    public Result<Long> none(MyParam param) {
        Objects.requireNonNull(param.getId());
        return Result.success(param.getId());
    }

    @Logging
    public Result<Long> all(MyParam param) {
        Objects.requireNonNull(param.getId());
        return Result.success(param.getId());
    }
}
