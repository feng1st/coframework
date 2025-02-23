package io.codeone.framework.common.log.formatter.support;

import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.util.LogContentUtils;
import io.codeone.framework.common.log.util.LogFmtUtils;

import java.util.Map;
import java.util.Objects;

/**
 * logfmt format implementation of {@link LogFormatter}. Produces key=value pairs
 * with proper ASCII encoding and whitespace handling. Follows logfmt conventions:
 * <ul>
 *   <li>Keys are sanitized using {@link LogContentUtils#toLogSafeKey(Object)}
 *   <li>Values are encoded with {@link LogFmtUtils#encodeLogFmtValue(String)}
 *   <li>Pairs are space-delimited
 * </ul>
 */
public class LogFmtLogFormatter implements LogFormatter {

    /**
     * {@inheritDoc}
     *
     * @return space-separated key=value pairs in logfmt format
     */
    @Override
    public String format(Map<String, Object> map) {
        Objects.requireNonNull(map);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(LogContentUtils.toLogSafeKey(entry.getKey()))
                    .append("=")
                    .append(LogFmtUtils.encodeLogFmtValue(
                            String.valueOf(LogContentUtils.toLogSafeValue(entry.getValue()))));
        }
        return sb.toString();
    }
}
