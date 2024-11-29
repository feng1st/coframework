package io.codeone.framework.logging.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Parses SpEL expressions for logging purposes.
 *
 * <p>Uses method arguments, results, or exceptions to evaluate custom expressions
 * provided in the {@code Logging} annotation.
 */
public class LoggingExpressionParser {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private static final Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    private final Object[] args;

    private final Object result;

    private final Throwable throwable;

    private StandardEvaluationContext context;

    /**
     * Constructs a new {@code LoggingExpressionParser} with method arguments, result,
     * and throwable.
     *
     * @param args      the arguments of the method
     * @param result    the result of the method execution
     * @param throwable the throwable thrown during method execution, if any
     */
    public LoggingExpressionParser(Object[] args, Object result, Throwable throwable) {
        this.args = args;
        this.result = result;
        this.throwable = throwable;
    }

    /**
     * Evaluates the given SpEL expression.
     *
     * @param expressionString the SpEL expression to evaluate
     * @return the result of the evaluation
     */
    public Object evaluate(String expressionString) {
        return EXPRESSION_CACHE.computeIfAbsent(expressionString, PARSER::parseExpression)
                .getValue(lazyLoadContext());
    }

    /**
     * Lazily initializes and loads the evaluation context.
     *
     * @return the evaluation context populated with method arguments, result, and
     * throwable
     */
    private StandardEvaluationContext lazyLoadContext() {
        if (context == null) {
            Map<String, Object> variables = new HashMap<>(args.length * 2 + 2);
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    variables.put("a" + i, args[i]);
                    variables.put("p" + i, args[i]);
                }
            }
            if (result != null) {
                variables.put("r", result);
            }
            if (throwable != null) {
                variables.put("e", throwable);
                variables.put("t", throwable);
            }

            context = new StandardEvaluationContext();
            context.setVariables(variables);
        }
        return context;
    }
}
