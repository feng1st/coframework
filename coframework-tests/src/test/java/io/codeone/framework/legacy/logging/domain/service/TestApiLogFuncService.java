package io.codeone.framework.legacy.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.legacy.logging.domain.exception.MyException;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiLogFuncService {

    @CustomErrorMessage("This message will be returned but won't be logged")
    public Result<Void> customMessage() {
        throw new MyException(CommonCodes.INVALID_ARGS, "Invalid arguments");
    }

    @Logging(logArgs = false, logResult = false)
    public Result<Void> stackTrace() {
        throw new MyException(CommonCodes.INVALID_ARGS, "Invalid arguments");
    }
}
