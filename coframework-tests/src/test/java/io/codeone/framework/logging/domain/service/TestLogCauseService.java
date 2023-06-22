package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.domain.exception.MyException;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestLogCauseService {

    public Result<Void> apiError() {
        throw new RuntimeException(new MyException(CommonErrors.INVALID_PARAM, new Exception("not the cause")));
    }

    public Result<Void> invalidParam() {
        throw new RuntimeException(new IllegalArgumentException("Negative", new Exception("ignored")));
    }

    public Result<Void> otherException() {
        throw new RuntimeException(new Exception(new Exception(new Exception("Deeply sorry"))));
    }
}
