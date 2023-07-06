package io.codeone.framework.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Stages of interceptions, decide the order of plugins in a chain.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Stages {
    /**
     * Initializes or supplies any args that not supposed to be provided by the
     * caller. For example, the account id of the operator which should be
     * retrieved only from the logged-in session.
     * <p>
     * The interception is mainly on the 'before' part of the plugin.
     */
    ARG_INITIALIZATION(false),
    /**
     * Validates args and aborts the execution of the target if something went
     * wrong, by for example throwing an IllegalArgumentException.
     * <p>
     * The interception is mainly on the 'before' part of the plugin.
     */
    ARG_VALIDATION(false),
    /**
     * Modifies any args after they get validated.
     * <p>
     * The interception is mainly on the 'before' part of the plugin.
     */
    ARG_UPDATING(false),
    /**
     * Just before the execution of the target method.
     * <p>
     * The interception is mainly on the 'before' part of the plugin.
     */
    BEFORE_TARGET(false),
    /**
     * Just after the execution of the target method.
     * <p>
     * The interception is mainly on the 'after' part of the plugin.
     * <p>
     * This is VERY IMPORTANT: Since the FIFO nature of the chain, in order to
     * execute its 'after' part in a desired order, the plugin decorated by
     * this stage is actually put in front of the chain. As the result, you may
     * get an unexpected outcome if you put the main interception logic in the
     * 'before' part of the plugin.
     */
    AFTER_TARGET(true),
    /**
     * Validates the result before it get returned to the caller. For example,
     * throws an exception if it contains classified information.
     * <p>
     * The interception is mainly on the 'after' part of the plugin.
     */
    RESULT_VALIDATION(true),
    /**
     * Modifies the result, for example, desensitizes some information.
     * <p>
     * Or modifies the exception, for example, clarifies the error message.
     * <p>
     * The interception is mainly on the 'after' part of the plugin.
     */
    RESULT_UPDATING(true),
    /**
     * Handles any exception thrown by the target or during the interception,
     * and this is the chance to wrap the thrown exception.
     * <p>
     * The interception is mainly on the 'after' part of the plugin.
     */
    EXCEPTION_HANDLING(true),
    ;

    /**
     * Whether the interception logic happened mainly in the 'after' part of
     * the plugin.
     * <p>
     * Since the FIFO nature of the chain, in order to execute the 'after' part
     * of the plugin in the right order, the plugin need to be put in front of
     * the chain, as we do in the 'getOrder()'.
     *
     * @see #getOrder()
     */
    private final boolean after;

    public int getOrder() {
        if (after) {
            return -this.ordinal();
        } else {
            return this.ordinal();
        }
    }
}
