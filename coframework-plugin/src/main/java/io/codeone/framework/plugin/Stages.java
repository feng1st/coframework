package io.codeone.framework.plugin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Standard stages of interceptions, which decides the order of plugins in a
 * chain.
 *
 * <p>The order of plugins is not equal to the order of their methods (plus the
 * target method). Methods of plugins in a chain are executed in a FIFO order:
 * The {@code before} method of the first plugin will be executed first, and the
 * {@code after} method of that plugin will be executed last. The target method
 * will be executed after the {@code before} method of the last plugin in the
 * chain, and followed by the {@code after} methods of that plugin.
 *
 * <p>Because of that, the numerical order of the stage (the order of plugins in
 * a chain) is a result of the natural order of the stage (the order of this
 * enumeration) and on what method the interception is focus combined.
 *
 * @see Plug#value()
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Stages {
    /**
     * Initializing or supplying any argument that is not supposed to be
     * provided by the caller, for example, the operator of a logged-in session.
     *
     * <p>The interception should be focus on the BEFORE method of this plugin.
     */
    ARG_INITIALIZATION(false),
    /**
     * Validating arguments and throwing {@code IllegalArgumentException} if
     * failed.
     *
     * <p>The interception should be focus on the BEFORE method of this plugin.
     */
    ARG_VALIDATION(false),
    /**
     * Modifying any argument after argument validation.
     *
     * <p>The interception should be focus on the BEFORE method of this plugin.
     */
    ARG_UPDATING(false),
    /**
     * Just before the execution of the target method.
     *
     * <p>The interception should be focus on the BEFORE method of this plugin.
     */
    BEFORE_TARGET(false),
    /**
     * Just after the execution of the target method.
     *
     * <p>The interception should be focus on the AFTER method of this plugin.
     */
    AFTER_TARGET(true),
    /**
     * Validating the result and throwing exception if failed.
     *
     * <p>The interception should be focus on the AFTER method of this plugin.
     */
    RESULT_VALIDATION(true),
    /**
     * Modifying the result or the exception as needed.
     *
     * <p>The interception should be focus on the AFTER method of this plugin.
     */
    RESULT_UPDATING(true),
    /**
     * Handling any exception thrown by the target method or during the
     * interception, and this is the chance to wrap exceptions.
     *
     * <p>The interception should be focus on the AFTER method of this plugin.
     */
    EXCEPTION_HANDLING(true),
    ;

    private final boolean after;

    /**
     * Returns the numerical order of the stage (the order of plugins in a
     * chain), which is a result of the natural order of the stage (the order of
     * this enumeration) and on what method the interception is focus combined.
     *
     * @return the numerical order of the stage (the order of plugins in a
     * chain)
     */
    public int getOrder() {
        if (after) {
            return -this.ordinal();
        } else {
            return this.ordinal();
        }
    }
}
