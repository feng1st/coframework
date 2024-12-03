package io.codeone.framework.ext;

import io.codeone.framework.ext.bizscenario.BizScenarioStack;
import io.codeone.framework.plugin.function.Invokable;
import io.codeone.framework.plugin.function.VoidInvokable;
import lombok.experimental.UtilityClass;

/**
 * Provides a simplified interface for working with {@link BizScenario} in the current
 * execution context.
 *
 * <p>This utility allows for accessing and invoking operations within the scope
 * of a {@link BizScenario}, leveraging {@link BizScenarioStack} for state management.
 */
@UtilityClass
public class BizScenarioContext {

    /**
     * Retrieves the current {@link BizScenario} from the context.
     *
     * @return the current {@link BizScenario}, or {@code null} if no scenario is
     * active
     */
    public BizScenario getBizScenario() {
        return BizScenarioStack.peek();
    }

    /**
     * Executes the given invokable within the context of the specified {@link BizScenario}.
     *
     * @param bizScenario the {@link BizScenario} to set for the context
     * @param invokable   the operation to execute
     * @param <T>         the type of the operation's return value
     * @return the result of the operation
     * @throws Throwable if the operation throws an exception
     */
    public <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        return BizScenarioStack.invoke(bizScenario, invokable);
    }

    /**
     * Executes the given void invokable within the context of the specified {@link
     * BizScenario}.
     *
     * @param bizScenario the {@link BizScenario} to set for the context
     * @param invokable   the operation to execute
     * @throws Throwable if the operation throws an exception
     */
    public void invoke(BizScenario bizScenario, VoidInvokable invokable) throws Throwable {
        BizScenarioStack.invoke(bizScenario, invokable);
    }
}
