package io.codeone.framework.common.log.config;

import io.codeone.framework.common.log.formatter.support.CustomLogFormatter;
import io.codeone.framework.common.log.util.LogFormatUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Centralized configuration for logging behaviors. Binds externalized properties
 * prefixed with {@code coframework.log} to log settings.
 *
 * <p>Example application.properties configuration:
 * <pre>{@code
 * coframework.log.format=json
 * coframework.log.custom.entrySeparator=||
 * coframework.log.custom.keyValueDelimiter==>
 * }</pre>
 */
@Configuration
@ConfigurationProperties(prefix = "coframework.log")
public class LogConfig {

    /**
     * Sets the global log format. Supported values:
     * <ul>
     *   <li>{@code json} - JSON structured logging</li>
     *   <li>{@code logfmt} - Key=value pair formatting</li>
     *   <li>{@code custom} - Custom implementation</li>
     * </ul>
     *
     * @param format Target log format, defaults to {@code LogFormat#JSON} if not
     *               specified
     */
    public void setFormat(String format) {
        LogFormatUtils.format = format;
    }

    /**
     * Configures custom log format parameters. Only takes effect when
     * {@code coframework.log.format=custom}.
     *
     * @param custom configuration object for custom format specifiers
     */
    public void setCustom(Custom custom) {
        // Configuration binding handled via setters
    }

    /**
     * Nested configuration properties for custom log format specification.
     * Defines delimiters used in {@link CustomLogFormatter}.
     */
    public static class Custom {

        /**
         * Sets the separator between log entries in custom format.
         * Default: {@code ||}
         *
         * @param entrySeparator delimiter string (e.g. "||", "##")
         */
        public void setEntrySeparator(String entrySeparator) {
            CustomLogFormatter.entrySeparator = entrySeparator;
        }

        /**
         * Sets the delimiter between keys and values in custom format.
         * Default: {@code =>}
         *
         * @param keyValueDelimiter separator string (e.g. "=>", ":")
         */
        public void setKeyValueDelimiter(String keyValueDelimiter) {
            CustomLogFormatter.keyValueDelimiter = keyValueDelimiter;
        }
    }
}
