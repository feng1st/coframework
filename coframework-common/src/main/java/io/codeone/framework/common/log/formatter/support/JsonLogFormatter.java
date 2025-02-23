package io.codeone.framework.common.log.formatter.support;

import io.codeone.framework.common.json.util.JsonUtils;
import io.codeone.framework.common.log.formatter.LogFormatter;
import io.codeone.framework.common.log.util.LogContentUtils;

import java.util.LinkedHashMap;
import java.util.Map;
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
    public String format(Map<String, Object> map) {
        if (!JsonUtils.isLoaded()) {
            return "(JSON formatting disabled: Jackson not found. Add jackson-databind "
                    + "dependency or set coframework.log.format in application.properties to non-json format)";
        }

        Objects.requireNonNull(map);
        Map<String, Object> logSafeMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            logSafeMap.put(LogContentUtils.toLogSafeKey(entry.getKey()),
                    LogContentUtils.toLogSafeValue(entry.getValue()));
        }
        return JsonUtils.toJsonString(logSafeMap);
    }
}
