package io.codeone.framework.logging.plugin;

import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.common.util.AnnotationUtils;
import io.codeone.framework.common.util.TypeStringUtils;
import io.codeone.framework.logging.Log;
import io.codeone.framework.logging.LogFeature;
import io.codeone.framework.logging.Logging;
import io.codeone.framework.logging.spel.ExpressionOption;
import io.codeone.framework.logging.spel.LoggingExpressionParser;
import io.codeone.framework.plugin.Plug;
import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.Stages;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

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
                log.error("Failed to log invocation of method \"{}\".", TypeStringUtils.toString(method), t);
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

        ExpressionOption expressionOption = null;
        if (logging != null) {
            expressionOption = ExpressionOption.of(new LoggingExpressionParser(args, result))
                    .setExpSuccess(!logging.expSuccess().isEmpty() ? logging.expSuccess() : null)
                    .setExpCode(!logging.expCode().isEmpty() ? logging.expCode() : null)
                    .setExpMessage(!logging.expMessage().isEmpty() ? logging.expMessage() : null)
                    .setExpArgKvs(logging.expArgKvs().length > 0 ? logging.expArgKvs() : null);
        }

        Log.newLog()
                .setLoggerName(logging != null && !logging.name().isEmpty() ? logging.name() : null)
                .setMethod(method)
                .setElapsed(elapsed)
                .setArgs(args)
                .setResult(result)
                .setException(throwable)
                .setExpressionOption(expressionOption)
                .config(LogFeature.LOG_ALL_ARGS, logging == null || logging.logAllArgs())
                .config(LogFeature.LOG_RESULT, logging == null || logging.logResult())
                .config(LogFeature.LOG_STACK_TRACE, logging == null || logging.logStackTrace())
                .log();
    }
}
