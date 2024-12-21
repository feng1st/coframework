package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.response.Result;
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
}
