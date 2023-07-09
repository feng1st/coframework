package io.codeone.framework.logging;

import io.codeone.framework.exception.CommonErrors;
import io.codeone.framework.response.Result;
import io.codeone.framework.util.ErrorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Log {

    private static final String DEFAULT_LOGGER_NAME = "logging";

    private Logger logger;

    private String loggerName;

    private Level level;

    private Class<?> clazz;

    private String method;

    private boolean success;

    private boolean hasError;

    private String code;

    private String message;

    private long elapsed;

    private Map<String, Object> args;

    private Object result;

    private Throwable error;

    private LogDelimiters delimiter = LogDelimiters.DEFAULT;

    public static Log newBuilder() {
        return new Log();
    }

    private Log() {
    }

    public Log logger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public Log logger(String loggerName) {
        this.loggerName = loggerName;
        return this;
    }

    public Log level(Level level) {
        this.level = level;
        return this;
    }

    public Log method(Method method) {
        return method(method.getDeclaringClass(), method.getName());
    }

    public Log method(Class<?> clazz, String method) {
        this.clazz = clazz;
        this.method = clazz.getSimpleName() + "." + method;
        return this;
    }

    public Log method(String method) {
        this.method = method;
        return this;
    }

    public Log args(String[] parameterNames, Object[] args) {
        if (this.args == null) {
            this.args = new LinkedHashMap<>();
        }
        for (int i = 0; i < parameterNames.length && i < args.length; i++) {
            this.args.put(parameterNames[i], args[i]);
        }
        return this;
    }

    public Log addArg(String parameterName, Object arg) {
        if (this.args == null) {
            this.args = new LinkedHashMap<>();
        }
        this.args.put(parameterName, arg);
        return this;
    }

    public Log success() {
        code(true, CommonErrors.SUCCESS.getCode(), null);
        return this;
    }

    public Log success(Object result) {
        code(true, CommonErrors.SUCCESS.getCode(), null);
        resultBody(result);
        return this;
    }

    public Log fail(String errorCode, String errorMessage) {
        code(false, errorCode, errorMessage);
        return this;
    }

    public Log result(Result<?> result) {
        code(result.isSuccess(), result.getErrorCode(), result.getErrorMessage());
        resultBody(result.getData());
        return this;
    }

    public Log error(Throwable error) {
        Throwable cause = ErrorUtils.getCause(error);
        code(false, ErrorUtils.getCode(cause), cause.getMessage());
        errorBody(error);
        return this;
    }

    public Log code(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        return this;
    }

    public Log hasError(boolean hasError) {
        this.hasError = hasError;
        return this;
    }

    public Log resultBody(Object result) {
        this.result = result;
        return this;
    }

    public Log errorBody(Throwable error) {
        this.hasError = true;
        this.error = error;
        return this;
    }

    public Log elapsed(long elapsed) {
        this.elapsed = elapsed;
        return this;
    }

    public Log delimiter(LogDelimiters delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public void log() {
        initLogger();
        initLevel();
        String msg = buildMsg();
        doLog(msg);
    }

    private void initLogger() {
        if (logger == null) {
            if (loggerName != null && !loggerName.isEmpty()) {
                logger = LoggerFactory.getLogger(loggerName);
            } else if (clazz != null) {
                logger = LoggerFactory.getLogger(clazz);
            } else {
                logger = LoggerFactory.getLogger(DEFAULT_LOGGER_NAME);
            }
        }
    }

    private void initLevel() {
        if (level == null) {
            if (success) {
                level = Level.INFO;
            } else if (hasError) {
                level = Level.ERROR;
            } else {
                level = Level.WARN;
            }
        }
    }

    private String buildMsg() {
        StringBuilder builder = new StringBuilder();
        append(builder, "level", level);
        append(builder, "method", method);
        append(builder, "success", success);
        append(builder, "code", code);
        append(builder, "message", message);
        append(builder, "elapsed", elapsed);
        appendMap(builder, "arg.", args);
        append(builder, "result", result);
        append(builder, "error", error);
        return builder.toString();
    }

    private void doLog(String msg) {
        if (level == Level.ERROR) {
            logger.error(msg, error);
        } else if (level == Level.WARN) {
            logger.warn(msg, error);
        } else if (level == Level.INFO) {
            logger.info(msg, error);
        } else {
            logger.debug(msg, error);
        }
    }

    private void append(StringBuilder builder, String key, Object value) {
        if (value == null) {
            return;
        }
        builder.append(delimiter.getField())
                .append(key)
                .append(delimiter.getKv())
                .append(mergeLines(value.toString()));
    }

    private void appendMap(StringBuilder builder, String prefix, Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach((k, v) -> append(builder, prefix + k, v));
    }

    private static final Pattern PATTERN_NEW_LINES = Pattern.compile("[\r\n]+");

    private static String mergeLines(String lines) {
        return PATTERN_NEW_LINES.matcher(lines).replaceAll(" ");
    }
}
