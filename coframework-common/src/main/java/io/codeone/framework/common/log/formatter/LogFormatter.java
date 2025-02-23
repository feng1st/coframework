package io.codeone.framework.common.log.formatter;

import java.util.Map;

/**
 * Defines a contract for formatting structured log data into different text representations.
 * Implementations handle specific serialization formats like JSON or logfmt.
 */
public interface LogFormatter {

    /**
     * Converts a structured log map to formatted string output.
     *
     * @param map key-value pairs representing log context
     * @return formatted log message in implementation-specific format
     * @throws NullPointerException if map argument is null
     */
    String format(Map<String, Object> map);
}
