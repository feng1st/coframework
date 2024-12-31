package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.exception.ServerErrorCodes;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Service;

@API
@Service
public class ExToResultApiPluginTestService {

    public Result<Object> illegalArgumentException() {
        throw new IllegalArgumentException("Illegal argument");
    }

    public Result<Object> illegalStateException() {
        throw new IllegalStateException("Illegal state");
    }

    public Result<Object> code() {
        throw new ApiException("SYS_ERROR", false);
    }

    public Result<Object> codeMessage() {
        throw new ApiException("SYS_ERROR", false, "System error");
    }

    public Result<Object> codeCause() {
        throw new ApiException("SYS_ERROR", false, new IllegalStateException("Illegal state"));
    }

    public Result<Object> codeMessageCause() {
        throw new ApiException("SYS_ERROR", false, "System error", new IllegalStateException("Illegal state"));
    }

    public Result<Object> apiErrorCode() {
        throw new ApiException(ServerErrorCodes.SERVICE_UNAVAILABLE);
    }

    public Result<Object> apiErrorCodeMessage() {
        throw new ApiException(ServerErrorCodes.SERVICE_UNAVAILABLE,
                "System error");
    }

    public Result<Object> apiErrorCodeCause() {
        throw new ApiException(ServerErrorCodes.SERVICE_UNAVAILABLE,
                new IllegalStateException("Illegal state"));
    }

    public Result<Object> apiErrorCodeMessageCause() {
        throw new ApiException(ServerErrorCodes.SERVICE_UNAVAILABLE,
                "System error",
                new IllegalStateException("Illegal state"));
    }

    @CustomErrorMessage("Custom error message")
    public Result<Object> customErrorMessage() {
        throw new IllegalStateException("Illegal state");
    }
}
