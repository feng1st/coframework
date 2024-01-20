package io.codeone.framework.logging.aop;

import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.Log;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.util.LoggingSpelParser;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.TargetMethod;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Plug(Stages.AFTER_TARGET)
@Slf4j(topic = "logging")
public class LoggingPlugin implements Plugin {

    @Override
    public Object around(TargetMethod targetMethod, Object[] args, Invokable<?> invokable)
            throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        Throwable error = null;
        try {
            return (result = invokable.invoke());
        } catch (Throwable t) {
            throw (error = t);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            try {
                log(targetMethod, args, result, error, elapsed);
            } catch (Throwable t) {
                log.error("Error logging invocation of '" + targetMethod.getMethod() + "'", t);
            }
        }
    }

    private void log(TargetMethod targetMethod, Object[] args, Object result, Throwable error, long elapsed) {
        Method method = targetMethod.getMethod();
        Logging logging = targetMethod.getAnnotation(Logging.class);

        LoggingSpelParser spelParser = new LoggingSpelParser(method, args, result, error);

        Log log = Log.newBuilder();

        if (logging != null) {
            log.delimiter(logging.delimiter());

            if (!logging.name().isEmpty()) {
                log.logger(logging.name());
            }
            if (!logging.scene().isEmpty()) {
                log.scene(logging.scene());
            }
        }

        log.method(method);

        if (logging != null) {
            if (logging.keyPairs().length > 1) {
                for (int i = 0; i < logging.keyPairs().length - 1; i += 2) {
                    log.addArg(logging.keyPairs()[i], spelParser.evalString(logging.keyPairs()[i + 1]));
                }
            } else if (logging.value().logArgs()) {
                log.args(targetMethod.getParameterNames(), args);
            }
        }

        Throwable cause = null;
        if (error != null) {
            cause = ErrorUtils.getCause(error);
        }
        boolean success = getSuccess(logging, result, cause, spelParser);
        String code = getCode(logging, result, cause, spelParser);
        String message = getMessage(logging, result, cause, spelParser);
        log.code(success, code, message);
        if (error != null) {
            if (logging != null && logging.value().logError()) {
                log.errorBody(error);
            } else {
                log.hasError(true);
            }
        } else {
            if (logging != null && logging.value().logResult()) {
                Object resultBody = getResultBody(result);
                log.resultBody(resultBody);
            }
        }

        log.elapsed(elapsed);

        log.log();
    }

    private boolean getSuccess(Logging logging, Object result, Throwable cause, LoggingSpelParser spelParser) {
        if (cause != null) {
            return false;
        }
        if (result instanceof Result) {
            return ((Result<?>) result).isSuccess();
        }
        if (logging != null && !logging.expSuccess().isEmpty()) {
            return spelParser.evalBoolean(logging.expSuccess());
        }
        return true;
    }

    private String getCode(Logging logging, Object result, Throwable cause, LoggingSpelParser spelParser) {
        if (cause != null) {
            return ErrorUtils.getCode(cause);
        }
        if (result instanceof Result) {
            return ((Result<?>) result).getErrorCode();
        }
        if (logging != null && !logging.expCode().isEmpty()) {
            return spelParser.evalString(logging.expCode());
        }
        return CommonErrors.SUCCESS.getCode();
    }

    private String getMessage(Logging logging, Object result, Throwable cause, LoggingSpelParser spelParser) {
        if (cause != null) {
            return cause.getMessage();
        }
        if (result instanceof Result) {
            return ((Result<?>) result).getErrorMessage();
        }
        if (logging != null && !logging.expMessage().isEmpty()) {
            return spelParser.evalString(logging.expMessage());
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
