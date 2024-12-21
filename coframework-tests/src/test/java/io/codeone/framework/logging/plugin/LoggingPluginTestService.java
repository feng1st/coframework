package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.exception.CustomErrorMessage;
import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.api.shared.BizException;
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
    public Object nonApiException(Object param1, Object param2) {
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
    public Result<Object> loggingApiException(Object param1, Object param2) {
        throw new BizException("CODE", "Message", new IllegalStateException("Root message"));
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

    @Logging(name = "customLogger")
    public Result<Object> loggingCustomLogger(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(logArgs = false,
            logResult = false,
            logException = false)
    public Result<Object> loggingNoDetailsSuccess(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(logArgs = false,
            logResult = false,
            logException = false)
    public Result<Object> loggingNoDetailsFailure(Object param1, Object param2) {
        return Result.failure("CODE", "Message");
    }

    @Logging(logArgs = false,
            logResult = false,
            logException = false)
    public Result<Object> loggingNoDetailsException(Object param1, Object param2) {
        throw new IllegalStateException("Message");
    }

    @Logging(expSuccess = "#r?.get('success')",
            expCode = "#r?.get('code')",
            expMessage = "#r?.get('message')",
            argKvs = {"userId", "#a0?.get('userId')", "extra", "#a1"})
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

    @Logging(expSuccess = "INVALID_EXP")
    public void invalidSpEL() {
    }
}
