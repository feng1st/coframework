package io.codeone.framework.plugin;

/**
 * Stages of interceptions, decide the order of these interceptions.
 */
public enum Stages {
    /**
     * Modifies any args in this stage, before they get validated.
     * <p>
     * The interception is mainly on the 'before' method of the plugin.
     */
    ARG_PREPARING(false),
    /**
     * Validates args in this stage.
     * <p>
     * The interception is mainly on the 'before' method of the plugin.
     */
    ARG_VALIDATING(false),
    /**
     * Modifies any args in this stage, after they get validated.
     * <p>
     * The interception is mainly on the 'before' method of the plugin.
     */
    ARG_ADJUSTING(false),
    /**
     * Just before the execution of the target method.
     * <p>
     * The interception is mainly on the 'before' method of the plugin.
     */
    BEFORE_TARGET(false),
    /**
     * Just after the execution of the target method.
     * <p>
     * The interception is mainly on the 'after' method of the plugin. This is
     * very important: Since the FIFO nature of the chain, in order to execute
     * its 'after' method in the order as expected, the plugin decorated by
     * this stage is actually in front of the chain, as well as the execution
     * of its 'before' method.
     */
    AFTER_TARGET(true),
    /**
     * Validates the result in this stage.
     * <p>
     * The interception is mainly on the 'after' method of the plugin.
     */
    RESULT_VALIDATING(true),
    /**
     * Modifies the result or exception in this stage.
     * <p>
     * The interception is mainly on the 'after' method of the plugin.
     */
    RESULT_ADJUSTING(true),
    /**
     * Handles any exception thrown by the target or during the interception,
     * and this is the chance to wrap the thrown exception.
     * <p>
     * The interception is mainly on the 'after' method of the plugin.
     */
    EXCEPTION_HANDLING(true),
    ;

    /**
     * Whether the interception logic happened mainly in the 'after' method of
     * the plugin.
     * <p>
     * Since the FIFO nature of the chain, in order to put the 'after' method
     * of the plugin in the right order, the plugin need to be put in front of
     * the chain, as we do in the 'getOrder'.
     *
     * @see #getOrder()
     */
    private final boolean after;

    Stages(boolean after) {
        this.after = after;
    }

    public int getOrder() {
        if (after) {
            return -this.ordinal();
        } else {
            return this.ordinal();
        }
    }
}
