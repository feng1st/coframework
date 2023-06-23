package io.codeone.framework.intercept;

public enum Stage {
    ARG_PREPARING(false),
    ARG_VALIDATING(false),
    ARG_ADJUSTING(false),
    BEFORE_TARGET(false),
    AFTER_TARGET(true),
    RESULT_VALIDATING(true),
    RESULT_ADJUSTING(true),
    EXCEPTION_HANDLING(true),
    ;

    private final boolean after;

    Stage(boolean after) {
        this.after = after;
    }

    public int order() {
        if (after) {
            return -this.ordinal();
        } else {
            return this.ordinal();
        }
    }
}
