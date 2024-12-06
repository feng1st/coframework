package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.common.function.Invokable;
import io.codeone.framework.common.function.VoidInvokable;
import io.codeone.framework.ext.BizScenario;
import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.LinkedList;

/**
 * A thread-local stack for managing {@link BizScenario} instances.
 *
 * <p>This utility supports nesting and propagation of {@link BizScenario} within
 * the execution context, ensuring the correct scenario is used during nested calls.
 */
@UtilityClass
public class BizScenarioStack {

    private final ThreadLocal<Deque<BizScenario>> THREAD_LOCAL = ThreadLocal.withInitial(LinkedList::new);

    /**
     * Retrieves the current {@link BizScenario} at the top of the stack.
     *
     * @return the current {@link BizScenario}, or {@code null} if the stack is
     * empty
     */
    public BizScenario peek() {
        return THREAD_LOCAL.get().peek();
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
        Deque<BizScenario> stack = THREAD_LOCAL.get();
        stack.push(bizScenario);
        try {
            return invokable.invoke();
        } finally {
            stack.pop();
        }
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
        invoke(bizScenario, () -> {
            invokable.invoke();
            return null;
        });
    }
}
