package io.codeone.framework.common.log.formatter.support;

import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.util.LogContentUtils;
import io.codeone.framework.common.log.util.LogFmtUtils;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.common.log.util.LogMapUtils;

import java.util.Objects;

/**
 * Custom log format implementation using dual separators. Produces entries in the
 * format: {@code key1=>value1||key2=>value2} where:
 * <ul>
 *   <li>{@code =>} separates keys from values
 *   <li>{@code ||} separates different key-value pairs
 *   <li>Keys are sanitized with {@link LogContentUtils#toLogSafeKey(Object)}
 *   <li>Values are encoded with {@link LogFmtUtils#encodeLogFmtValue(String)}
 * </ul>
 */
public class CustomLogFormatter implements LogFormatter {

    /**
     * Separator between different key-value pairs in the log output
     */
    public static String entrySeparator = "||";

    /**
     * Delimiter between keys and values in each log entry
     */
    public static String keyValueDelimiter = "=>";

    /**
     * {@inheritDoc}
     *
     * @return formatted string combining all entries with configured separators
     * @throws NullPointerException if logMap argument is null
     */
    @Override
    public String format(LogMap<String, Object> logMap) {
        Objects.requireNonNull(logMap);
        StringBuilder sb = new StringBuilder();
        LogMapUtils.flatProcess(logMap, (k, v) -> {
            if (sb.length() > 0) {
                sb.append(entrySeparator);
            }
            sb.append(LogContentUtils.toLogSafeKey(k))
                    .append(keyValueDelimiter)
                    .append(LogFmtUtils.encodeLogFmtValue(
                            String.valueOf(LogContentUtils.toLogSafeValue(v))));
        });
        return sb.toString();
    }
}
