package io.codeone.framework.common.log.formatter;

import io.codeone.framework.common.log.util.LogMap;

/**
 * Defines a contract for formatting structured log data into different text representations.
 * Implementations handle specific serialization formats like JSON or logfmt.
 */
public interface LogFormatter {

    /**
     * Converts a structured log map to formatted string output.
     *
     * @param logMap key-value pairs representing log context
     * @return formatted log message in implementation-specific format
     * @throws NullPointerException if logMap argument is null
     */
    String format(LogMap<String, Object> logMap);
}
