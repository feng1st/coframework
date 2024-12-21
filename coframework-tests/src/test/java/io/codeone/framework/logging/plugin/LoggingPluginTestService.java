package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

@Service
public class LoggingPluginTestService {

    @API
    public Result<Object> apiSuccess() {
        return Result.success("success");
    }

    @API
    public Result<Object> apiFailure() {
        return Result.failure("FAILURE", "Failure");
    }

    @API
    public Result<Object> apiException() {
        throw new IllegalStateException("Exception");
    }

    @Logging
    public Result<Object> loggingSuccess() {
        return Result.success("success");
    }

    @Logging
    public Result<Object> loggingFailure() {
        return Result.failure("FAILURE", "Failure");
    }

    @API
    @Logging
    public Result<Object> loggingException() {
        throw new IllegalStateException("Exception");
    }
}
