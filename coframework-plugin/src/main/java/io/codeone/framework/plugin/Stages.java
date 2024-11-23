package io.codeone.framework.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Stages {
    PRE_ARG_INTERCEPTING(true),
    ARG_INTERCEPTING(true),
    POST_ARG_INTERCEPTING(true),
    BEFORE_TARGET(true),
    AFTER_TARGET(false),
    PRE_RESULT_INTERCEPTING(false),
    RESULT_INTERCEPTING(false),
    POST_RESULT_INTERCEPTING(false),
    ;

    public static final Stages DEFAULT = BEFORE_TARGET;

    private final boolean beforeTarget;

    public int getOrder() {
        if (beforeTarget) {
            return this.ordinal();
        } else {
            return -this.ordinal();
        }
    }
}
