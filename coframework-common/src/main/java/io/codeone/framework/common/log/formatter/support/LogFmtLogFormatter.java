package io.codeone.framework.common.log.formatter.support;

import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.util.LogContentUtils;
import io.codeone.framework.common.log.util.LogFmtUtils;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.common.log.util.LogMapUtils;

import java.util.Objects;

/**
 * logfmt format implementation of {@link LogFormatter}. Produces key=value pairs
 * with proper ASCII encoding and whitespace handling. Follows logfmt conventions:
 * <ul>
 *   <li>Keys are sanitized using {@link LogContentUtils#toLogSafeKey(Object)}</li>
 *   <li>Values are encoded with {@link LogFmtUtils#encodeLogFmtValue(String)}</li>
 *   <li>Pairs are space-delimited</li>
 * </ul>
 */
public class LogFmtLogFormatter implements LogFormatter {

    /**
     * {@inheritDoc}
     *
     * @return space-separated key=value pairs in logfmt format
     */
    @Override
    public String format(LogMap<String, Object> logMap) {
        Objects.requireNonNull(logMap);
        StringBuilder sb = new StringBuilder();
        LogMapUtils.flatProcess(logMap, (k, v) -> {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(LogContentUtils.toLogSafeKey(k))
                    .append("=")
                    .append(LogFmtUtils.encodeLogFmtValue(
                            String.valueOf(LogContentUtils.toLogSafeValue(v))));
        });
        return sb.toString();
    }
}
