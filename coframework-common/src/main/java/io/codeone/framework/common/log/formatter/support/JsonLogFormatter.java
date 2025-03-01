package io.codeone.framework.common.log.formatter.support;

import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.util.LogContentUtils;
import io.codeone.framework.common.log.util.LogMap;
import io.codeone.framework.common.log.util.LogMapUtils;

import java.util.Objects;

/**
 * JSON format implementation of {@link LogFormatter}. Requires Jackson library
 * presence.
 *
 * <p>Returns warning message when Jackson is unavailable.
 */
public class JsonLogFormatter implements LogFormatter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(LogMap<String, Object> logMap) {
        if (!JsonUtils.isLoaded()) {
            return "(JSON formatting disabled: Jackson not found. Add jackson-databind "
                    + "dependency or set coframework.log.format in application.properties to non-json format)";
        }

        Objects.requireNonNull(logMap);
        LogMap<Object, Object> logSafeMap = LogMapUtils.wrap(logMap,
                LogContentUtils::toLogSafeKey, LogContentUtils::toLogSafeValue);
        return JsonUtils.toJsonString(logSafeMap);
    }
}
