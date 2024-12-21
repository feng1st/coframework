package io.codeone.framework.legacy.domain.service;

import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.legacy.domain.exception.MyException;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

@Logging
@Service
public class TestLogCauseService {

    public Result<Void> apiError() {
        throw new RuntimeException(new MyException(CommonCodes.INVALID_ARGS, "Invalid arguments", new Exception("not the cause")));
    }

    public Result<Void> invalidParam() {
        throw new RuntimeException(new IllegalArgumentException("Negative", new Exception("ignored")));
    }

    public Result<Void> otherException() {
        throw new RuntimeException(new Exception(new Exception(new Exception("Deeply sorry"))));
    }
}
