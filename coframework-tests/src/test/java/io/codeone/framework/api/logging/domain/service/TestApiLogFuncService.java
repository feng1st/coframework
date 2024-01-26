package io.codeone.framework.api.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonErrors;
import io.codeone.framework.api.logging.domain.exception.MyException;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.constants.LoggingPresets;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiLogFuncService {

    @API(errorMessage = "This message will be returned but won't be logged")
    public Result<Void> customMessage() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }

    @Logging(LoggingPresets.ERROR_ONLY)
    public Result<Void> stackTrace() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }
}
