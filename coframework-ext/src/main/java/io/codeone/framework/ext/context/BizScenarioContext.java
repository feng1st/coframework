package io.codeone.framework.ext.context;

import io.codeone.framework.ext.BizScenario;

import java.util.LinkedList;

public class BizScenarioContext {

    private static final ThreadLocal<LinkedList<BizScenario>> stack = ThreadLocal.withInitial(LinkedList::new);

    public static BizScenario getBizScenario() {
        return stack.get().peek();
    }

    public static void push(BizScenario bizScenario) {
        stack.get().push(bizScenario);
    }

    public static void pop() {
        stack.get().pop();
    }

    public static <T> T invoke(BizScenario bizScenario, Invokable<T> invokable) throws Throwable {
        push(bizScenario);
        try {
            return invokable.invoke();
        } finally {
            pop();
        }
    }
}
