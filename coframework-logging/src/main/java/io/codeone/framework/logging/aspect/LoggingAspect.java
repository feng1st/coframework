package io.codeone.framework.logging.aspect;

import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.logging.Log;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.util.LoggingSpelParser;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.AspectOrders;
import io.codeone.framework.util.ErrorUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(AspectOrders.LOGGING)
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger("coframework.logging");

    @Around("@within(logging) && !@annotation(io.codeone.framework.logging.Logging)")
    public Object aroundClass(ProceedingJoinPoint pjp, Logging logging) throws Throwable {
        return around(pjp, logging);
    }

    @Around("@annotation(logging)")
    public Object aroundMethod(ProceedingJoinPoint pjp, Logging logging) throws Throwable {
        return around(pjp, logging);
    }

    public Object around(ProceedingJoinPoint pjp, Logging logging) throws Throwable {
        Object result = null;
        Throwable error = null;
        long start = System.currentTimeMillis();
        try {
            return (result = pjp.proceed());
        } catch (Throwable t) {
            throw (error = t);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            try {
                log(pjp, logging, result, error, elapsed);
            } catch (Exception e) {
                logger.error("Error logging invocation of '"
                        + ((MethodSignature) pjp.getSignature()).getMethod() + "'");
            }
        }
    }

    private void log(ProceedingJoinPoint pjp, Logging logging, Object result, Throwable error, long elapsed) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        LoggingSpelParser spelParser = new LoggingSpelParser(method, pjp.getArgs(), result, error);

        Log log = Log.newBuilder();

        log.delimiter(logging.delimiter());

        if (!logging.name().isEmpty()) {
            log.logger(logging.name());
        }
        log.method(method);

        if (logging.keyPairs().length > 1) {
            for (int i = 0; i < logging.keyPairs().length - 1; i += 2) {
                log.addArg(logging.keyPairs()[i], spelParser.evalString(logging.keyPairs()[i + 1]));
            }
        } else if (logging.value().logArgs()) {
            log.args(methodSignature.getParameterNames(), pjp.getArgs());
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
            if (logging.value().logError()) {
                log.errorBody(error);
            } else {
                log.hasError(true);
            }
        } else {
            Object resultBody = getResultBody(result);
            if (logging.value().logResult()) {
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
        if (!logging.expSuccess().isEmpty()) {
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
        if (!logging.expCode().isEmpty()) {
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
        if (!logging.expMessage().isEmpty()) {
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
