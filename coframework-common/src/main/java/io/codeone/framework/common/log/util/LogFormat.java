package io.codeone.framework.common.log.util;

/**
 * Defines supported log formatting types.
 * Contains constants for different log serialization formats.
 */
public interface LogFormat {

    /**
     * JSON format representation (key-value pairs in JSON structure)
     */
    String JSON = "json";

    /**
     * Custom format implementation (vendor-specific structure)
     */
    String CUSTOM = "custom";

    /**
     * logfmt format (key=value pairs with ASCII sanitation)
     */
    String LOG_FMT = "logfmt";
}
