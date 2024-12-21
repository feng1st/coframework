package io.codeone.framework.legacy.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.legacy.logging.domain.exception.MyException;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiLogCauseService {

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
