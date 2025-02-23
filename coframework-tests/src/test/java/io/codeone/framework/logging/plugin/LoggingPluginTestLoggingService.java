package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingPluginTestLoggingService {

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

    @Logging
    public Object edgeMalformedException() {
        throw new LoggingPluginTestMalformedException();
    }
}
