package io.codeone.framework.logging.spel;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Encapsulates expression-driven configuration for dynamic log field evaluation.
 *
 * <p>Used with {@code Log} to enable SpEL-based determination of success states,
 * error codes, messages, and argument values. Created via {@link #of(LoggingExpressionParser)}
 * with a configured expression parser.
 *
 * <p><strong>Expression Context Variables:</strong>
 * <ul>
 *   <li>{@code r} - Method return value ({@code null} for void methods)
 *   <li>{@code a0}, {@code a1}, ... - Method arguments by index (alias: {@code
 *   p0}, {@code p1})
 * </ul>
 *
 * <p>Typical usage:
 * <pre>{@code
 * ExpressionOption.of(parser)
 *     .setExpSuccess("#r?.success")
 *     .setExpCode("#a0.userId + '_error'") // Access first argument
 *     .setExpArgKvs("user", "#p0.userId"); // Alias for a0
 * }</pre>
 *
 * <p>All evaluation methods require prior validation with corresponding {@code
 * supportXX()} checks.
 */
@RequiredArgsConstructor(staticName = "of")
@Setter
@Accessors(chain = true)
public class ExpressionOption {

    private final LoggingExpressionParser parser;

    /**
     * SpEL expression to determine operation success.
     */
    private String expSuccess;

    /**
     * SpEL expression to derive error codes.
     */
    private String expCode;

    /**
     * SpEL expression to construct error messages.
     */
    private String expMessage;

    /**
     * SpEL argument key-value pairs in alternating format: {@code [key1, expr1,
     * key2, expr2, ...]}. Evaluated expressions become named arguments in log entries.
     */
    private String[] expArgKvs;

    /**
     * Checks if success expression is configured and parseable.
     *
     * @return true if valid success expression exists
     */
    public boolean supportSuccess() {
        return parser != null && expSuccess != null && !expSuccess.isEmpty();
    }

    /**
     * Evaluates success expression.
     *
     * @return boolean interpretation of expression result
     * @throws IllegalStateException if preconditions not met
     */
    public Boolean evaluateSuccess() {
        if (!supportSuccess()) {
            return null;
        }
        return Objects.equals(parser.evaluate(expSuccess), true);
    }

    /**
     * Checks if error code expression is configured and parseable.
     *
     * @return true if valid code expression exists and parser is available
     */
    public boolean supportCode() {
        return parser != null && expCode != null && !expCode.isEmpty();
    }

    /**
     * Evaluates code expression to derive an error identifier.
     *
     * @return string representation of the evaluated expression result
     * @throws IllegalStateException if preconditions not met or expression invalid
     */
    public String evaluateCode() {
        if (!supportCode()) {
            return null;
        }
        return String.valueOf(parser.evaluate(expCode));
    }

    /**
     * Checks if message expression is configured and parseable.
     *
     * @return true if valid message expression exists and parser is available
     */
    public boolean supportMessage() {
        return parser != null && expMessage != null && !expMessage.isEmpty();
    }

    /**
     * Evaluates message expression to construct descriptive text.
     *
     * @return string representation of the evaluated expression result
     * @throws IllegalStateException if preconditions not met or expression invalid
     */
    public String evaluateMessage() {
        if (!supportMessage()) {
            return null;
        }
        return String.valueOf(parser.evaluate(expMessage));
    }

    /**
     * Validates argument key-value expressions configuration.
     *
     * @return true if expArgKvs contains valid pairs (even-length non-empty array)
     * and parser is available
     */
    public boolean supportArgKvs() {
        return parser != null && expArgKvs != null && expArgKvs.length > 1;
    }

    /**
     * Evaluates argument expressions into key-value pairs.
     *
     * @return immutable map of evaluated arguments
     * @throws IllegalStateException if expArgKvs is malformed or unparseable
     */
    public Map<String, Object> evaluateArgMap() {
        if (!supportArgKvs()) {
            return null;
        }
        Map<String, Object> argMap = new LinkedHashMap<>();
        for (int i = 0; i < expArgKvs.length - 1; i += 2) {
            argMap.put(expArgKvs[i], parser.evaluate(expArgKvs[i + 1]));
        }
        return argMap;
    }
}
