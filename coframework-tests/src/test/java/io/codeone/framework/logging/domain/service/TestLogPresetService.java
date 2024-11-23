package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.domain.param.MyParam;
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
