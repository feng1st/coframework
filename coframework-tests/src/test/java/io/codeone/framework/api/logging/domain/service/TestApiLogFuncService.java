package io.codeone.framework.api.logging.domain.service;

import io.codeone.framework.api.API;
import io.codeone.framework.api.logging.domain.exception.MyException;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.LoggingPresets;
import io.codeone.framework.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class TestApiLogFuncService {

    @API(errorMessage = "This message will be returned but won't be logged")
    public Result<Void> customMessage() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }

    @API(loggingPreset = LoggingPresets.ERROR_ONLY)
    public Result<Void> stackTrace() {
        throw new MyException(CommonErrors.INVALID_PARAM);
    }
}
