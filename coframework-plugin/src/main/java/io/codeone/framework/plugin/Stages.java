package io.codeone.framework.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Stages {
    PRE_ARG_INTERCEPTING(false),
    ARG_INTERCEPTING(false),
    POST_ARG_INTERCEPTING(false),
    BEFORE_TARGET(false),
    AFTER_TARGET(true),
    PRE_RESULT_INTERCEPTING(true),
    RESULT_INTERCEPTING(true),
    POST_RESULT_INTERCEPTING(true),
    ;

    private final boolean after;

    public int getOrder() {
        if (after) {
            return -this.ordinal();
        } else {
            return this.ordinal();
        }
    }
}
