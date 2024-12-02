package io.codeone.framework.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents the stages of method interception available for plugins.
 *
 * <p>Each stage indicates whether it occurs before or after the target method invocation
 * and defines the appropriate point for execution within the interception lifecycle.
 *
 * <p>If multiple plugins are assigned to the same stage, their execution order
 * can be further controlled using {@link org.springframework.core.annotation.Order}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Stages {

    /**
     * Intercepts the method execution before any argument processing begins.
     */
    PRE_ARG_INTERCEPTING(true),

    /**
     * Enables modifications or validation of arguments before they are passed to
     * the method.
     */
    ARG_INTERCEPTING(true),

    /**
     * Executes after argument processing but before the method invocation.
     */
    POST_ARG_INTERCEPTING(true),

    /**
     * Executes immediately prior to the invocation of the target method.
     */
    BEFORE_TARGET(true),

    /**
     * Executes immediately after the target method completes.
     */
    AFTER_TARGET(false),

    /**
     * Intercepts the method execution before any result processing begins.
     */
    PRE_RESULT_INTERCEPTING(false),

    /**
     * Allows modifications or handling of the method's result after execution.
     */
    RESULT_INTERCEPTING(false),

    /**
     * Executes after all result processing has been completed.
     */
    POST_RESULT_INTERCEPTING(false);

    /**
     * The default stage of method interception, set to execute before the target
     * method.
     */
    public static final Stages DEFAULT = BEFORE_TARGET;

    private final boolean beforeTarget;

    /**
     * Determines the execution order of this stage relative to others.
     *
     * <p>Stages occurring before the target method are ordered positively, while
     * those occurring after are ordered negatively. This ensures that stages executed
     * after the target method are processed in reverse order due to stack behavior.
     *
     * @return the relative order of the stage
     */
    public int getOrder() {
        if (beforeTarget) {
            return this.ordinal();
        } else {
            return -this.ordinal();
        }
    }
}
