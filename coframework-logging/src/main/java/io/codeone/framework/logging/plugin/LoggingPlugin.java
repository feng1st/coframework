package io.codeone.framework.logging.plugin;

import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageData;
import io.codeone.framework.api.util.ApiExceptionUtils;
import io.codeone.framework.api.util.ApiResultUtils;
import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.common.log.util.LogUtils;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.spel.LoggingExpressionParser;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import io.codeone.framework.plugin.util.AnnotationUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Plugin for logging method invocations and results.
 *
 * <p>Intercepts method calls annotated with {@link Logging} to log details such
 * as method arguments, result, and any exceptions thrown. Supports custom logging
 * behavior through configurable expressions.
 */
@Plug(value = Stages.AFTER_TARGET, targetAnnotations = Logging.class)
@Slf4j(topic = "logging")
public class LoggingPlugin implements Plugin {

    @Override
    public Object around(Method method, Object[] args, Invokable<?> invokable)
            throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        Throwable throwable = null;
        try {
            return (result = invokable.invoke());
        } catch (Throwable t) {
            throw (throwable = t);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            try {
                log(method, args, result, throwable, elapsed);
            } catch (Throwable t) {
                log.error("Error logging invocation of '{}'", method, t);
            }
        }
    }

    /**
     * Logs the details of the method invocation.
     *
     * @param method    the method being logged
     * @param args      the arguments of the method
     * @param result    the result of the method execution
     * @param throwable the throwable thrown during method execution, if any
     * @param elapsed   the elapsed time of the method invocation in milliseconds
     */
    private void log(Method method, Object[] args, Object result, Throwable throwable, long elapsed) {

        Logging logging = AnnotationUtils.getAnnotation(method, Logging.class);

        LoggingExpressionParser expParser = new LoggingExpressionParser(args, result, throwable);

        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        Throwable cause = ApiExceptionUtils.getCause(throwable);

        Boolean success = getSuccess(apiResult, cause, logging, expParser);
        String code = getCode(apiResult, cause, logging, expParser);
        String message = getMessage(apiResult, cause, logging, expParser);
        Map<String, Object> argMap = getArgMap(method, args, logging, expParser);
        Object resultData = getResultData(result, apiResult, logging);
        Level level = getLevel(throwable, success);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("level", level);
        map.put("method", method.getDeclaringClass().getSimpleName() + "." + method.getName());
        if (success != null) {
            map.put("success", success);
        }
        if (code != null) {
            map.put("code", code);
        }
        if (message != null) {
            map.put("message", message);
        }
        map.put("elapsed", elapsed);
        if (argMap != null) {
            map.put("args", argMap);
        }
        if (resultData != null) {
            map.put("result", resultData);
        }
        if (throwable != null) {
            map.put("exception", throwable.toString());
        }

        Logger logger = getLogger(method, logging);
        if (level == Level.ERROR) {
            if (logging == null
                    || logging.logException()) {
                logger.error("{}", LogUtils.format(map), throwable);
            } else {
                logger.error("{}", LogUtils.format(map));
            }
        } else if (level == Level.WARN) {
            logger.warn("{}", LogUtils.format(map));
        } else {
            logger.info("{}", LogUtils.format(map));
        }
    }

    private Level getLevel(Throwable throwable, Boolean success) {
        if (throwable != null) {
            return Level.ERROR;
        }
        if (Objects.equals(success, false)) {
            return Level.WARN;
        }
        return Level.INFO;
    }

    private Boolean getSuccess(ApiResult<?> apiResult, Throwable cause, Logging logging, LoggingExpressionParser expParser) {
        if (cause != null) {
            return false;
        }
        if (apiResult != null) {
            return apiResult.isSuccess();
        }
        if (logging != null && !logging.expSuccess().isEmpty()) {
            return Objects.equals(expParser.evaluate(logging.expSuccess()), true);
        }
        return null;
    }

    private String getCode(ApiResult<?> apiResult, Throwable cause, Logging logging, LoggingExpressionParser expParser) {
        if (cause != null) {
            return ApiExceptionUtils.getCode(cause);
        }
        if (apiResult != null) {
            return apiResult.getErrorCode();
        }
        if (logging != null && !logging.expCode().isEmpty()) {
            return String.valueOf(expParser.evaluate(logging.expCode()));
        }
        return null;
    }

    private String getMessage(ApiResult<?> apiResult, Throwable cause, Logging logging, LoggingExpressionParser expParser) {
        if (cause != null) {
            return cause.getMessage();
        }
        if (apiResult != null) {
            return apiResult.getErrorMessage();
        }
        if (logging != null && !logging.expMessage().isEmpty()) {
            return String.valueOf(expParser.evaluate(logging.expMessage()));
        }
        return null;
    }

    private Map<String, Object> getArgMap(Method method, Object[] args, Logging logging, LoggingExpressionParser expParser) {
        if (logging != null && logging.argKvs().length > 1) {
            Map<String, Object> argMap = new LinkedHashMap<>();
            for (int i = 0; i < logging.argKvs().length - 1; i += 2) {
                argMap.put(logging.argKvs()[i], expParser.evaluate(logging.argKvs()[i + 1]));
            }
            return argMap;
        } else if (logging == null
                || logging.logArgs()) {
            Parameter[] parameters = method.getParameters();
            if (parameters.length > 0) {
                Map<String, Object> argMap = new LinkedHashMap<>();
                for (int i = 0; i < parameters.length; i++) {
                    argMap.put(parameters[i].getName(), args[i]);
                }
                return argMap;
            }
        }
        return null;
    }

    private Object getResultData(Object result, ApiResult<?> apiResult, Logging logging) {
        if (logging == null
                || logging.logResult()) {
            if (result instanceof Page) {
                return result;
            }
            if (result instanceof PageData) {
                return Page.of((PageData<?>) result);
            }
            if (apiResult != null) {
                return apiResult.getData();
            }
            return result;
        }
        return null;
    }

    private Logger getLogger(Method method, Logging logging) {
        if (logging != null && !logging.name().isEmpty()) {
            return LoggerFactory.getLogger(logging.name());
        } else {
            return LoggerFactory.getLogger(method.getDeclaringClass());
        }
    }
}
