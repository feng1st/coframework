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
 * <p>Uses method arguments or results to evaluate custom expressions provided in
 * the {@code Logging} annotation.
 */
public class LoggingExpressionParser {

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private static final Map<String, Expression> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    private final Object[] args;

    private final Object result;

    private StandardEvaluationContext context;

    /**
     * Constructs a new {@code LoggingExpressionParser} with method arguments and
     * result.
     *
     * @param args   the arguments of the method
     * @param result the result of the method execution
     */
    public LoggingExpressionParser(Object[] args, Object result) {
        this.args = args;
        this.result = result;
    }

    /**
     * Evaluates the given SpEL expression.
     *
     * @param expressionString the SpEL expression to evaluate
     * @return the result of the evaluation
     */
    public Object evaluate(String expressionString) {
        try {
            return EXPRESSION_CACHE.computeIfAbsent(expressionString, PARSER::parseExpression)
                    .getValue(lazyLoadContext());
        } catch (Exception e) {
            return String.format("SPEL_ERROR(%s)", e.getMessage());
        }
    }

    /**
     * Lazily initializes and loads the evaluation context.
     *
     * @return the evaluation context populated with method arguments and result
     */
    private StandardEvaluationContext lazyLoadContext() {
        if (context == null) {
            Map<String, Object> variables = new HashMap<>(args.length * 2 + 1);
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    variables.put("a" + i, args[i]);
                    variables.put("p" + i, args[i]);
                }
            }
            if (result != null) {
                variables.put("r", result);
            }

            context = new StandardEvaluationContext();
            context.setVariables(variables);
        }
        return context;
    }
}
