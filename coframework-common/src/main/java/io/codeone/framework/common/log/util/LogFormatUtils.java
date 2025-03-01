package io.codeone.framework.common.log.util;

import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.formatter.support.CustomLogFormatter;
import io.codeone.framework.common.log.formatter.support.JsonLogFormatter;
import io.codeone.framework.common.log.formatter.support.LogFmtLogFormatter;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * Central utility for log message formatting. Provides configurable serialization
 * of log context maps to different structured formats. Defaults to JSON format
 * with fallback to logfmt if JSON dependencies are unavailable.
 *
 * <p>Format selection is determined by the {@code format} field and automatically
 * validates JSON capability during class initialization.
 */
@UtilityClass
public class LogFormatUtils {

    private final LogFormatter JSON_LOG_FORMATTER = new JsonLogFormatter();

    private final LogFormatter CUSTOM_LOG_FORMATTER = new CustomLogFormatter();

    private final LogFormatter LOG_FMT_LOG_FORMATTER = new LogFmtLogFormatter();

    /**
     * Currently active log format.
     *
     * <p>Defaults to {@value LogFormat#JSON} unless JSON support is unavailable
     *
     * @see LogFormat
     */
    public String format = initFormat();

    /**
     * Serializes a context map to the configured log format
     *
     * @param logMap key-value pairs representing log context. Map entries will
     *               be formatted according to the active format strategy
     * @return formatted log message ready for output
     * @throws NullPointerException if logMap argument is null
     */
    public String format(LogMap<String, Object> logMap) {
        Objects.requireNonNull(logMap);
        if (LogFormat.JSON.equals(format)) {
            return JSON_LOG_FORMATTER.format(logMap);
        }
        if (LogFormat.CUSTOM.equals(format)) {
            return CUSTOM_LOG_FORMATTER.format(logMap);
        }
        return LOG_FMT_LOG_FORMATTER.format(logMap);
    }

    /**
     * Initializes format. Fallbacks to logfmt if JSON serialization capabilities
     * are not present in the runtime environment.
     */
    private String initFormat() {
        if (JsonUtils.isLoaded()) {
            return LogFormat.JSON;
        }
        return LogFormat.LOG_FMT;
    }
}
