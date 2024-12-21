package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.API;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.logging.Logging;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoggingPluginTestService {

    @API
    public Result<Object> apiSuccess() {
        return Result.success("data");
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
        return Result.success("data");
    }

    @Logging
    public Result<Object> loggingFailure() {
        return Result.failure("FAILURE", "Failure");
    }

    @Logging
    public Result<Object> loggingException() {
        throw new IllegalStateException("Exception");
    }

    @Logging
    public Result<Object> loggingDefault(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(name = "customLogger",
            logArgs = false,
            logResult = false,
            logException = false)
    public Result<Object> loggingCustom(Object param1, Object param2) {
        return Result.success("data");
    }

    @Logging(name = "customLogger",
            expSuccess = "#r?.get('success')",
            expCode = "#r?.get('code')",
            expMessage = "#r != null ? #r?.get('message') : #e?.getMessage()",
            argKvs = {"userId", "#a0?.get('userId')"})
    public Map<String, Object> loggingSpEL(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>(param);
        result.put("success", false);
        result.put("code", "CODE");
        result.put("message", "Message");
        return result;
    }

    @Logging(name = "customLogger",
            expSuccess = "#r?.get('success')")
    public Map<String, Object> loggingSpELNoParam() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    @Logging(name = "customLogger",
            expMessage = "#r != null ? #r?.get('message') : #e?.getMessage()")
    public Map<String, Object> loggingSpELException() {
        throw new IllegalStateException("Exception");
    }

    @Logging(expSuccess = "ERROR")
    public void invalidSpEL() {
    }
}
