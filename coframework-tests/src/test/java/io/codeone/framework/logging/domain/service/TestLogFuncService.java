package io.codeone.framework.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.LoggingPresets;
import io.codeone.framework.logging.domain.exception.MyException;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Logging
@Service
public class TestLogFuncService {

    @API(errorMessage = "This message will be returned but won't be logged")
    public Result<Void> customMessage() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }

    @Logging(LoggingPresets.ERROR_ONLY)
    public Result<Void> stackTrace() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }
}
