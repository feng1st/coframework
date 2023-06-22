package io.codeone.framework.api.interceptors;

import io.codeone.framework.api.API;
import io.codeone.framework.api.ApiInterceptor;
import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Stage;
import io.codeone.framework.intercept.util.Signature;
import io.codeone.framework.logging.Log;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;
import org.springframework.stereotype.Component;

@Component
@Intercept(Stage.AFTER_TARGET)
public class LoggingApiInterceptor implements ApiInterceptor<Long> {

    @Override
    public Long roundBefore(Signature signature, Object[] args)
            throws Throwable {
        return System.currentTimeMillis();
    }

    @Override
    public Object after(Signature signature, Object[] args, Object result,
                        Throwable error, Long before) throws Throwable {
        long elapsed = System.currentTimeMillis() - before;
        log(signature, args, result, error, elapsed);
        return ApiInterceptor.super.after(
                signature, args, result, error, before);
    }

    private void log(Signature signature, Object[] args, Object result,
                     Throwable error, long elapsed) {
        API api = signature.getAnnotation(API.class);

        Log log = Log.newBuilder();

        log.method(signature.getMethod());

        if (api.loggingPreset().logArgs()) {
            log.args(signature.getParameterNames(), args);
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
