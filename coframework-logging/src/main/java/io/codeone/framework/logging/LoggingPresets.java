package io.codeone.framework.logging;

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
    private final boolean args;

    /**
     * Whether to log the return value of this invocation.
     */
    private final boolean result;

    /**
     * Whether to log the stack trace if there is an exception thrown.
     */
    private final boolean error;

    LoggingPresets(boolean args, boolean result, boolean error) {
        this.args = args;
        this.result = result;
        this.error = error;
    }

    public boolean logArgs() {
        return args;
    }

    public boolean logResult() {
        return result;
    }

    public boolean logError() {
        return error;
    }
}
