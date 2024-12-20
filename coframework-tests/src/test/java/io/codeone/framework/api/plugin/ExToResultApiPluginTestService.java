package io.codeone.framework.api.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CommonCodes;
import io.codeone.framework.api.exception.CustomErrorMessage;
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

    public Result<Object> apiExceptionCode() {
        throw new ExToResultApiPluginTestApiException(CommonCodes.SYS_ERROR);
    }

    public Result<Object> apiExceptionCodeMessage() {
        throw new ExToResultApiPluginTestApiException(CommonCodes.SYS_ERROR,
                "System error");
    }

    public Result<Object> apiExceptionCodeCause() {
        throw new ExToResultApiPluginTestApiException(CommonCodes.SYS_ERROR,
                new IllegalStateException("Illegal state"));
    }

    public Result<Object> apiExceptionCodeMessageCause() {
        throw new ExToResultApiPluginTestApiException(CommonCodes.SYS_ERROR,
                "System error",
                new IllegalStateException("Illegal state"));
    }

    @CustomErrorMessage("Custom error message")
    public Result<Object> customErrorMessage() {
        throw new IllegalStateException("Illegal state");
    }
}
