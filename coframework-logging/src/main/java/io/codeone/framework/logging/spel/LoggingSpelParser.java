package io.codeone.framework.logging.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LoggingSpelParser {

    private static final String ARG_NAME_PREFIX = "arg";

    private static final String RESULT_NAME = "ret";

    private static final String ERROR_NAME = "err";

    private static final String SPEL_ERROR = "#SPEL_ERR";

    private static final SpelExpressionParser PARSER = new SpelExpressionParser(
            new SpelParserConfiguration(SpelCompilerMode.MIXED, null));

    private static final Map<Method, Map<String, ValueOrThrowable<Expression, ParseException>>> EXPRESSION_CACHE
            = new ConcurrentHashMap<>();

    private final Method method;

    private final Object[] args;

    private final Object result;

    private final Throwable error;

    private StandardEvaluationContext context;

    public LoggingSpelParser(Method method, Object[] args, Object result, Throwable error) {
        this.method = method;
        this.args = args;
        this.result = result;
        this.error = error;
    }

    public String evalString(String expressionString) {
        try {
            return Optional.ofNullable(evaluate(expressionString)).map(Object::toString).orElse(null);
        } catch (Exception e) {
            return SPEL_ERROR;
        }
    }

    public boolean evalBoolean(String expressionString) {
        try {
            return Objects.equals(evaluate(expressionString), true);
        } catch (Exception e) {
            return false;
        }
    }

    private Object evaluate(String expressionString) {
        Map<String, ValueOrThrowable<Expression, ParseException>> subCache
                = EXPRESSION_CACHE.computeIfAbsent(method, k -> new ConcurrentHashMap<>());

        ValueOrThrowable<Expression, ParseException> expressionOrException
                = subCache.computeIfAbsent(expressionString, k -> {
            try {
                return new ValueOrThrowable<>(PARSER.parseExpression(k));
            } catch (ParseException e) {
                return new ValueOrThrowable<>(e);
            }
        });
        expressionOrException.throwIfThrowable();

        return expressionOrException.getValue().getValue(getOrBuildContext());
    }

    private StandardEvaluationContext getOrBuildContext() {
        if (context == null) {
            Map<String, Object> variables = new HashMap<>(args.length + 2);
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    variables.put(ARG_NAME_PREFIX + i, args[i]);
                }
            }
            if (result != null) {
                variables.put(RESULT_NAME, result);
            }
            if (error != null) {
                variables.put(ERROR_NAME, error);
            }

            context = new StandardEvaluationContext();
            context.setVariables(variables);
        }
        return context;
    }
}
