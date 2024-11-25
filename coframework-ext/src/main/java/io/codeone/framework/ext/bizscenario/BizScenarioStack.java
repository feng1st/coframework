package io.codeone.framework.ext.bizscenario;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.plugin.function.Invokable;
import io.codeone.framework.plugin.function.VoidInvokable;
import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.LinkedList;

@UtilityClass
public class BizScenarioStack {

    private final ThreadLocal<Deque<BizScenario>> stack = ThreadLocal.withInitial(LinkedList::new);

    public BizScenario peek() {
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
