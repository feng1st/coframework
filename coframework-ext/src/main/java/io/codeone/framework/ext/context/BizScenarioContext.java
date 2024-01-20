package io.codeone.framework.ext.context;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.RouteByContext;
import io.codeone.framework.ext.session.ExtensionSession;
import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.VoidInvokable;
import lombok.experimental.UtilityClass;

import java.util.LinkedList;

/**
 * The context which has a stack of the most recently used {@link BizScenario}
 * instances. A {@code BizScenario} instance will be pushed into it whenever an
 * {@link ExtensionSession} resolving or Extension routing happens, and will be
 * popped afterward. And the {@code BizScenario} instance at the top of the
 * stack will be used for Extension routing if the Extensible interface/method
 * is annotated by {@link RouteByContext} or the
 * {@code coframework.ext.route-by-context-by-default} application property is
 * set to {@code true} and all other routing approaches are not available.
 *
 * <p>The stack inside is {@code ThreadLocal} and can not be used in
 * multi-threads situation directly.
 */
@UtilityClass
public class BizScenarioContext {

    private final ThreadLocal<LinkedList<BizScenario>> stack = ThreadLocal.withInitial(LinkedList::new);

    /**
     * Returns the most recent {@link BizScenario} instance used in Extension
     * routing.
     *
     * @return the most recent {@code BizScenario} instance used in Extension
     * routing
     */
    public BizScenario getBizScenario() {
        return stack.get().peek();
    }

    /**
     * Executes an invokable with a {@link BizScenario} instance in this
     * context. The {@code BizScenario} instance will be pushed into the stack
     * before the invocation of the invokable, and will be popped afterward.
     *
     * @param bizScenario the {@code BizScenario} the invokable to be run under
     * @param invokable   the invokable to be run
     * @param <T>         return type of the invokable
     * @return the result of the invokable
     * @throws Throwable any exception the invokable may throw
     */
    public <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        stack.get().push(bizScenario);
        try {
            return invokable.invoke();
        } finally {
            stack.get().pop();
        }
    }

    public void invoke(BizScenario bizScenario, VoidInvokable invokable) throws Throwable {
        invoke(bizScenario, () -> {
            invokable.invoke();
            return null;
        });
    }
}
