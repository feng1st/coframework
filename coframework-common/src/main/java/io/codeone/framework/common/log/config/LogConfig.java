package io.codeone.framework.common.log.config;

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
}
