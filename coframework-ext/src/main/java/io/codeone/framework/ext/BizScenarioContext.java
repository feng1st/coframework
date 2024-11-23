package io.codeone.framework.ext;

import io.codeone.framework.plugin.util.Invokable;
import io.codeone.framework.plugin.util.VoidInvokable;
import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.LinkedList;

@UtilityClass
public class BizScenarioContext {

    private final ThreadLocal<Deque<BizScenario>> stack = ThreadLocal.withInitial(LinkedList::new);

    public BizScenario getBizScenario() {
        return stack.get().peek();
    }

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
