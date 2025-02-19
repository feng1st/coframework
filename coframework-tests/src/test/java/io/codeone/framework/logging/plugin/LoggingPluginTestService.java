package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.ApiException;
import io.codeone.framework.api.exception.ClientErrors;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.exception.ServerErrors;
import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingPluginTestService {

    @API
    public Result<Object> apiSuccess(Object param1, Object param2) {
        return Result.success("data");
    }

    @API
    public Result<Object> apiFailure(Object param1, Object param2) {
        return Result.failure("CODE", "Message");
    }

    @API
    public Result<Object> apiException(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @API
    @CustomErrorMessage("Custom error message")
    public Result<Object> apiExceptionCustomErrorMessage(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @API
    public Object nonApiResult(Object param1, Object param2) {
        return null;
    }

    @API
    public Object nonApiError(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @Logging
    public Result<Object> loggingSuccess(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging
    public Result<Object> loggingFailure(Object param1, Object param2) {
        return Result.failure("CODE", "Message");
    }

    @Logging
    public Result<Object> loggingException(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @API
    public Result<Object> loggingApiError(Object param1, Object param2) {
        throw new ApiException("CODE", false, "Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingApiErrorCritical(Object param1, Object param2) {
        throw new ApiException("CODE", true, "Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingInvalidState(Object param1, Object param2) {
        throw new ApiException(ClientErrors.INVALID_STATE, "Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingServiceUnavailable(Object param1, Object param2) {
        throw new ApiException(ServerErrors.SERVICE_UNAVAILABLE, "Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingExternalSysError(Object param1, Object param2) {
        throw new ApiException(ServerErrors.EXTERNAL_SYS_ERROR, "Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingIllegalArgumentException(Object param1, Object param2) {
        throw new IllegalArgumentException("Message", new IllegalStateException("Root message"));
    }

    @API
    public Result<Object> loggingRootException(Object param1, Object param2) {
        throw new IllegalStateException("Message", new IllegalStateException("Root message"));
    }

    @Logging
    public Object loggingNoState(Object param1, Object param2) {
        return null;
    }

    @Logging
    public Object loggingPage(Object param1, Object param2) {
        return Page.of(Arrays.asList(1, 2, 3), 1, 20);
    }

    @Logging
    public Object loggingPageResult(Object param1, Object param2) {
        return PageResult.success(Arrays.asList(1, 2, 3), 1, 20);
    }

    @Logging
    public Object loggingMultiLinesString(Object param1, Object param2) {
        return "Line 1\nLine 2";
    }

    @Logging(name = "customLogger")
    public Result<Object> loggingCustomLogger(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(logAllArgs = false,
            logResult = false,
            logStackTrace = false)
    public Result<Object> loggingNoDetailsSuccess(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(logAllArgs = false,
            logResult = false,
            logStackTrace = false)
    public Result<Object> loggingNoDetailsFailure(Object param1, Object param2) {
        return Result.failure("CODE", "Message");
    }

    @Logging(logAllArgs = false,
            logResult = false,
            logStackTrace = false)
    public Result<Object> loggingNoDetailsException(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @Logging(expSuccess = "#r?.get('success')",
            expCode = "#r?.get('code')",
            expMessage = "#r?.get('message')",
            expArgKvs = {"userId", "#a0?.get('userId')", "extra", "#a1"})
    public Map<String, Object> loggingSpEL(Map<String, Object> param1, Object param2) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("code", "CODE");
        result.put("message", "Message");
        return result;
    }

    @Logging(expSuccess = "#r?.get('success')")
    public Map<String, Object> loggingSpELNoParam() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @Logging(expMessage = "INVALID_EXP")
    public void invalidSpEL() {
    }
}
