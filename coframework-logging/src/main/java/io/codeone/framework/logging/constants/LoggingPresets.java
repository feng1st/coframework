package io.codeone.framework.logging.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public enum LoggingPresets {
    NONE(false, false, false),
    ARGS_ONLY(true, false, false),
    RESULT_ONLY(false, true, false),
    ERROR_ONLY(false, false, true),
    NO_ERROR(true, true, false),
    NO_RESULT(true, false, true),
    NO_ARGS(true, false, true),
    ALL(true, true, true),
    ;

    /**
     * Whether to log all args of one invocation.
     */
    private final boolean logArgs;

    /**
     * Whether to log the return value of this invocation.
     */
    private final boolean logResult;

    /**
     * Whether to log the stack trace if there is an exception thrown.
     */
    private final boolean logError;
}
