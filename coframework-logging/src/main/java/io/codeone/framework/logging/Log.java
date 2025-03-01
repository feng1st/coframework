package io.codeone.framework.logging;

import io.codeone.framework.api.exception.ApiError;
import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.util.ApiErrorUtils;
import io.codeone.framework.api.util.ApiResultUtils;
import io.codeone.framework.common.log.util.LogFormatUtils;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.common.util.ClassUtils;
import io.codeone.framework.logging.spel.ExpressionOption;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A configurable logging facade for structured application logging.
 *
 * <p>Provides fluent configuration of log entries with support for method context
 * tracking, argument capturing, and result/exception analysis. Integrates with
 * {@code ApiResult} and {@code ApiError} for consistent error reporting.
 *
 * <p>Typical usage:
 * <pre>{@code
 * Log.newLog()
 *    .tag("Payment")
 *    .method(processPaymentMethod)
 *    .args(paymentRequest)
 *    .log();
 * }</pre>
 *
 * <p>Features include:
 * <ul>
 *   <li>Chainable configuration through fluent setters
 *   <li>Automatic parameter name detection via reflection
 *   <li>Dynamic log level determination based on execution outcome
 *   <li>Structured logging with predefined fields (tag, method, code, elapsed,
 *   etc.)
 *   <li>Integration with logging frameworks through SLF4J
 * </ul>
 *
 * <p>Logging behavior can be controlled through feature flags ({@link LogFeature}):
 * <ul>
 *   <li>{@code LOG_ALL_ARGS} - Automatic argument logging (default enabled)
 *   <li>{@code LOG_RESULT} - Result value logging (default enabled)
 *   <li>{@code LOG_STACK_TRACE} - Full stack trace logging (default enabled)
 * </ul>
 */
@NoArgsConstructor(staticName = "newLog")
@Accessors(chain = true)
public class Log {

    private static final String DEFAULT_LOGGER_NAME = "default";

    private static final Object NO_RESULT = new Object();

    /**
     * SLF4J Logger instance. Auto-initialized if not provided, prioritized by:
     * <ol>
     *   <li>Explicitly set logger
     *   <li>loggerName
     *   <li>Class/Method context
     *   <li>Default logger
     * </ol>
     */
    @Setter
    private Logger logger;

    /**
     * Custom logger name. Used only if no explicit logger is set.
     */
    @Setter
    private String loggerName;

    /**
     * Logging level. Auto-determined by severity if not set:
     * <ul>
     *   <li>ERROR: Critical exceptions
     *   <li>WARN: Non-critical exceptions or failures
     *   <li>INFO: Successful operations (default)
     * </ul>
     */
    @Setter
    private Level level;

    /**
     * Class context for logger naming and method resolution.
     */
    @Setter
    private Class<?> clazz;

    /**
     * Method context for parameter name resolution.
     */
    @Setter
    private Method method;

    /**
     * Fallback method name if method reflection isn't available.
     */
    @Setter
    private String methodName;

    /**
     * Explicit success/failure status. Overrides auto-detection.
     */
    @Setter
    private Boolean success;

    /**
     * Business error code. Auto-populated from ApiResult/ApiError if not set.
     */
    @Setter
    private String code;

    /**
     * Human-readable message. Auto-populated from context if not set.
     */
    @Setter
    private String message;

    /**
     * Operation duration in milliseconds. Typically set via external timing.
     */
    @Setter
    private Long elapsed;

    /**
     * Mapped diagnostic context. Appears as 'ctx' in logs.
     */
    @Setter
    private Map<String, Object> context;

    /**
     * Raw method arguments. Processed into argMap based on features.
     */
    @Setter
    private Object[] args;

    /**
     * Processed argument key-value pairs. Takes precedence over raw args.
     */
    @Setter
    private Map<String, Object> argMap;

    /**
     * Method return value. Processed according to LOG_RESULT feature.
     */
    private Object result;

    private boolean result$set = false;

    /**
     * Exception instance. Triggers stack trace logging per LOG_STACK_TRACE.
     */
    @Setter
    private Throwable exception;

    /**
     * SpEL expression configuration for dynamic field evaluation.
     */
    @Setter
    private ExpressionOption expressionOption;

    /**
     * Bitmask of enabled logging features using constants from {@link LogFeature}.
     *
     * <p>Default enabled features:
     * {@link LogFeature#LOG_ALL_ARGS} |
     * {@link LogFeature#LOG_RESULT} |
     * {@link LogFeature#LOG_STACK_TRACE}
     */
    private long features = LogFeature.LOG_ALL_ARGS
            | LogFeature.LOG_RESULT
            | LogFeature.LOG_STACK_TRACE;

    /**
     * Adds a key-value pair to the log context.
     *
     * @param key   the key
     * @param value the value
     * @return this Log instance for chaining
     */
    public Log putContext(String key, Object value) {
        if (context == null) {
            context = new LinkedHashMap<>();
        }
        context.put(key, value);
        return this;
    }

    /**
     * Adds a custom argument to the log entry.
     *
     * @param key   argument identifier (appears in 'args' map)
     * @param value argument value (null-safe)
     * @return this Log instance for chaining
     */
    public Log addArg(String key, Object value) {
        if (argMap == null) {
            argMap = new LinkedHashMap<>();
        }
        argMap.put(key, value);
        return this;
    }

    /**
     * Set method return value. Processed according to LOG_RESULT feature.
     *
     * @param result the method return value
     * @return this Log instance for chaining
     */
    public Log setResult(Object result) {
        this.result = result;
        this.result$set = true;
        return this;
    }

    /**
     * Configures logging features using bitwise flags.
     *
     * @param feature flag from {@link LogFeature} to modify
     * @param value   true to enable, false to disable
     * @return this Log instance for chaining
     */
    public Log config(long feature, boolean value) {
        if (value) {
            this.features |= feature;
        } else {
            this.features &= ~feature;
        }
        return this;
    }

    /**
     * Executes the logging operation with configured parameters.
     *
     * <p>Performs in sequence:
     * <ol>
     *   <li>Logger initialization
     *   <li>Success/code/message resolution
     *   <li>Log level determination
     *   <li>Argument processing (raw args -> argMap)
     *   <li>Log map construction
     *   <li>SLF4J logging invocation
     * </ol>
     *
     * <p>Logging behavior is controlled by:
     * <ul>
     *   <li>Explicit setters (level, success, code, etc.)
     *   <li>Feature flags from {@link LogFeature}
     *   <li>Integrated exception/result analysis
     * </ul>
     */
    public void log() {
        ApiResult<?> apiResult = ApiResultUtils.toApiResult(result);
        ApiError cause = ApiErrorUtils.getCause(exception);

        initLogger(loggerName, clazz, method);

        initSuccess(apiResult, cause, expressionOption);
        initCode(apiResult, cause, expressionOption);
        initMessage(apiResult, cause, expressionOption);

        initLevel(cause, success);

        initArgMap(method, args, expressionOption);

        String loggedMethodName = resolveLoggedMethodName(method, clazz, methodName);
        Object loggedResult = resolveLoggedResult(apiResult, result, result$set, success, method);

        LogMap<String, Object> logMap = buildLogMap(loggedMethodName, loggedResult);

        doLog(logMap);
    }

    private void initLogger(String loggerName, Class<?> clazz, Method method) {
        if (logger == null) {
            if (loggerName != null && !loggerName.isEmpty()) {
                logger = LoggerFactory.getLogger(loggerName);
            } else if (clazz != null) {
                logger = LoggerFactory.getLogger(clazz);
            } else if (method != null) {
                logger = LoggerFactory.getLogger(method.getDeclaringClass());
            } else {
                logger = LoggerFactory.getLogger(DEFAULT_LOGGER_NAME);
            }
        }
    }

    private void initSuccess(ApiResult<?> apiResult, ApiError cause, ExpressionOption expressionOption) {
        if (success == null) {
            if (cause != null) {
                success = false;
            } else if (apiResult != null) {
                success = apiResult.isSuccess();
            } else if (expressionOption != null && expressionOption.supportSuccess()) {
                success = expressionOption.evaluateSuccess();
            }
        }
    }

    private void initCode(ApiResult<?> apiResult, ApiError cause, ExpressionOption expressionOption) {
        if (code == null) {
            if (cause != null) {
                code = cause.getCode();
            } else if (apiResult != null) {
                code = apiResult.getErrorCode();
            } else if (expressionOption != null && expressionOption.supportCode()) {
                code = expressionOption.evaluateCode();
            }
        }
    }

    private void initMessage(ApiResult<?> apiResult, ApiError cause, ExpressionOption expressionOption) {
        if (message == null) {
            if (cause != null) {
                message = cause.getMessage();
            } else if (apiResult != null) {
                message = apiResult.getErrorMessage();
            } else if (expressionOption != null && expressionOption.supportMessage()) {
                message = expressionOption.evaluateMessage();
            }
        }
    }

    private void initLevel(ApiError cause, Boolean success) {
        if (level == null) {
            if (cause != null) {
                if (cause.isCritical()) {
                    level = Level.ERROR;
                } else {
                    level = Level.WARN;
                }
            } else if (Objects.equals(success, false)) {
                level = Level.WARN;
            } else {
                level = Level.INFO;
            }
        }
    }

    private void initArgMap(Method method, Object[] args, ExpressionOption expressionOption) {
        if (argMap == null) {
            if (expressionOption != null && expressionOption.supportArgKvs()) {
                argMap = expressionOption.evaluateArgMap();
            } else if (args != null
                    && hasFeature(LogFeature.LOG_ALL_ARGS)) {
                Parameter[] parameters = null;
                if (method != null) {
                    parameters = method.getParameters();
                }
                argMap = new LinkedHashMap<>();
                for (int i = 0; i < args.length; i++) {
                    if (parameters != null && i < parameters.length) {
                        argMap.put(parameters[i].getName(), args[i]);
                    } else {
                        argMap.put("p" + i, args[i]);
                    }
                }
            }
        }
    }

    private String resolveLoggedMethodName(Method method, Class<?> clazz, String methodName) {
        if (method != null) {
            return ClassUtils.getSimpleName(method);
        }
        if (methodName != null && !methodName.isEmpty()) {
            if (clazz != null
                    && !methodName.contains(".")) {
                return ClassUtils.getSimpleName(clazz) + "." + methodName;
            }
            return methodName;
        }
        if (clazz != null) {
            return ClassUtils.getSimpleName(clazz);
        }
        return "anonymous";
    }

    private Object resolveLoggedResult(ApiResult<?> apiResult, Object result,
                                       boolean result$set, Boolean success, Method method) {
        if (result$set && hasFeature(LogFeature.LOG_RESULT)) {
            if (apiResult != null) {
                if (apiResult.getData() != null
                        || !Objects.equals(success, false)) {
                    return apiResult.getData();
                }
            } else {
                if (method == null
                        || method.getReturnType() != Void.TYPE) {
                    return result;
                }
            }
        }
        return NO_RESULT;
    }

    private LogMap<String, Object> buildLogMap(String loggedMethodName, Object loggedResult) {
        LogMap<String, Object> logMap = new LogMap<>();
        logMap.put("level", level);
        logMap.put("method", loggedMethodName);
        if (success != null) {
            logMap.put("success", success);
        }
        if (code != null) {
            logMap.put("code", code);
        }
        if (message != null) {
            logMap.put("message", message);
        }
        if (elapsed != null) {
            logMap.put("elapsed", elapsed);
        }
        if (!CollectionUtils.isEmpty(context)) {
            logMap.put("ctx", new LogMap<>(context));
        }
        if (argMap != null) {
            logMap.put("args", new LogMap<>(argMap));
        }
        if (exception != null) {
            logMap.put("exception", exception.toString());
        } else if (loggedResult != NO_RESULT) {
            logMap.put("result", loggedResult);
        }
        return logMap;
    }

    private void doLog(LogMap<String, Object> logMap) {
        if (exception != null
                && hasFeature(LogFeature.LOG_STACK_TRACE)) {
            if (level == Level.ERROR) {
                logger.error("{}", LogFormatUtils.format(logMap), exception);
            } else {
                logger.warn("{}", LogFormatUtils.format(logMap), exception);
            }
        } else {
            if (level == Level.ERROR) {
                logger.error("{}", LogFormatUtils.format(logMap));
            } else if (level == Level.WARN) {
                logger.warn("{}", LogFormatUtils.format(logMap));
            } else {
                logger.info("{}", LogFormatUtils.format(logMap));
            }
        }
    }

    private boolean hasFeature(long feature) {
        return (this.features & feature) == feature;
    }
}
