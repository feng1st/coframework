package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.exception.ClientErrors;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.exception.ServerErrors;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Service;

@Service
@API
public class LoggingPluginTestApiService {

    public Result<Object> apiSuccess(Object param1, Object param2) {
        return Result.success("data");
    }

    public Result<Object> apiFailure(Object param1, Object param2) {
        return Result.failure("CODE", "Message");
    }

    public Result<Object> apiException(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @CustomErrorMessage("Custom error message")
    public Result<Object> apiExceptionCustomErrorMessage(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    public Object nonApiResult(Object param1, Object param2) {
        return null;
    }

    public Object nonApiError(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    public Result<Object> apiErrorNonCritical(Object param1, Object param2) {
        throw new ApiException("CODE", false, "Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorCritical(Object param1, Object param2) {
        throw new ApiException("CODE", true, "Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorInvalidState(Object param1, Object param2) {
        throw new ApiException(ClientErrors.INVALID_STATE, "Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorServiceUnavailable(Object param1, Object param2) {
        throw new ApiException(ServerErrors.SERVICE_UNAVAILABLE, "Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorExternalSysError(Object param1, Object param2) {
        throw new ApiException(ServerErrors.EXTERNAL_SYS_ERROR, "Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorIllegalArgumentException(Object param1, Object param2) {
        throw new IllegalArgumentException("Message", new IllegalStateException("Root message"));
    }

    public Result<Object> apiErrorRootException(Object param1, Object param2) {
        throw new IllegalStateException("Message", new IllegalStateException("Root message"));
    }
}
