package io.codeone.framework.api.plugins;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiPlugin;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.Log;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Stage;
import io.codeone.framework.plugin.util.MethodWrap;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Plug(Stage.AFTER_TARGET)
public class LoggingApiPlugin implements ApiPlugin<Long> {

    private final Logger logger = LoggerFactory.getLogger("coframework.api");

    @Override
    public Long roundBefore(MethodWrap methodWrap, Object[] args)
            throws Throwable {
        return System.currentTimeMillis();
    }

    @Override
    public Object after(MethodWrap methodWrap, Object[] args, Object result,
                        Throwable error, Long before) throws Throwable {
        long elapsed = System.currentTimeMillis() - before;
        try {
            log(methodWrap, args, result, error, elapsed);
        } catch (Throwable t) {
            logger.error("Error logging invocation of '"
                    + methodWrap.getMethod() + "'", t);
        }
        return ApiPlugin.super.after(methodWrap, args, result, error, before);
    }

    private void log(MethodWrap methodWrap, Object[] args, Object result,
                     Throwable error, long elapsed) {
        API api = methodWrap.getAnnotation(API.class);

        Log log = Log.newBuilder();

        log.method(methodWrap.getMethod());

        if (api.loggingPreset().logArgs()) {
            log.args(methodWrap.getParameterNames(), args);
        }

        Throwable cause = null;
        if (error != null) {
            cause = ErrorUtils.getCause(error);
        }
        boolean success = getSuccess(result, cause);
        String code = getCode(result, cause);
        String message = getMessage(result, cause);
        log.code(success, code, message);
        if (error != null) {
            if (api.loggingPreset().logError()) {
                log.errorBody(error);
            } else {
                log.hasError(true);
            }
        } else {
            Object resultBody = getResultBody(result);
            if (api.loggingPreset().logResult()) {
                log.resultBody(resultBody);
            }
        }

        log.elapsed(elapsed);

        log.log();
    }

    private boolean getSuccess(Object result, Throwable cause) {
        if (cause != null) {
            return false;
        }
        if (result instanceof Result) {
            return ((Result<?>) result).isSuccess();
        }
        return true;
    }

    private String getCode(Object result, Throwable cause) {
        if (cause != null) {
            return ErrorUtils.getCode(cause);
        }
        if (result instanceof Result) {
            return ((Result<?>) result).getErrorCode();
        }
        return CommonErrors.SUCCESS.getCode();
    }

    private String getMessage(Object result, Throwable cause) {
        if (cause != null) {
            return cause.getMessage();
        }
        if (result instanceof Result) {
            return ((Result<?>) result).getErrorMessage();
        }
        return null;
    }

    private Object getResultBody(Object result) {
        if (result instanceof Result) {
            return ((Result<?>) result).getData();
        }
        return result;
    }
}
