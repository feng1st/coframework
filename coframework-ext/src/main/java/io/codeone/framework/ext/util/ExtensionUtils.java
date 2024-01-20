package io.codeone.framework.ext.util;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.context.BizScenarioContext;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.VoidInvokable;
import lombok.experimental.UtilityClass;

/**
 * This is a wrap of the {@link BizScenarioContext} and is mainly for
 * developers, by contrast, {@code BizScenarioContext} is mainly used by the
 * framework.
 *
 * <p>You can use this utility to, for example, process individual entities with
 * their own various {@code BizScenario}s, or execute an invokable with a
 * {@code BizScenario} in another thread.
 */
@UtilityClass
public class ExtensionUtils {

    /**
     * Returns the most recent {@link BizScenario} instance used in Extension
     * routing.
     *
     * @return the most recent {@code BizScenario} instance used in Extension
     * routing
     */
    public BizScenario getBizScenario() {
        return BizScenarioContext.getBizScenario();
    }

    /**
     * Executes an invokable with a {@link BizScenario} instance. The
     * {@code BizScenario} instance will be pushed into the context before the
     * invocation of the invokable, and will be popped afterward.
     *
     * @param bizScenario the {@code BizScenario} the invokable to be run under
     * @param invokable   the invokable to be run
     * @param <T>         return type of the invokable
     * @return the result of the invokable
     * @throws Throwable any exception the invokable may throw
     */
    public <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        return BizScenarioContext.invoke(bizScenario, invokable);
    }

    /**
     * Executes a void-return invokable with a {@link BizScenario} instance. The
     * {@code BizScenario} instance will be pushed into the context before the
     * invocation of the invokable, and will be popped afterward.
     *
     * @param bizScenario the {@code BizScenario} the invokable to be run under
     * @param invokable   the void-return invokable to be run
     * @throws Throwable any exception the invokable may throw
     */
    public void invoke(BizScenario bizScenario, VoidInvokable invokable) throws Throwable {
        BizScenarioContext.invoke(bizScenario, invokable);
    }
}
